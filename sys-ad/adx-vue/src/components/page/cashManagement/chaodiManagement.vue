<template>
  <div class="table">
    <div class="container">
      <div class="handle-box">
        <el-input v-model="select_word" placeholder="请输入投放名称" class="handle-input mr10"></el-input>
        <el-button type="primary" icon="search" @click="search">搜索</el-button>
        <el-button type="primary" icon="search" @click="create" v-if="this.readonly !=1">新建投放</el-button>
      </div>

      <el-table :data="tableData" border style="width: 100%" ref="multipleTable">
        <el-table-column prop="id" label="序号" align="center"></el-table-column>
        <el-table-column prop="putName" label="抄底名称" align="center"></el-table-column>
        <el-table-column prop="appName" label="APP名称" align="center"></el-table-column>
        <el-table-column prop="adPositionName" label="广告位" align="center"></el-table-column>
        <el-table-column label="操作" width="150" align="center">
          <template slot-scope="scope">
            <el-button size="small" type="success" @click="handleEdit(scope.$index, scope.row)">编辑</el-button>
            <!-- <el-button size="small" type="danger" @click="handIdear(scope.$index, scope.row)">创意</el-button> -->
            <el-button size="small" type="danger" @click="handleDelete(scope.$index, scope.row)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
      <div class="pagination">
          <el-pagination @current-change="handleCurrentChange" 
         layout="total,prev, pager, next,jumper" 
         :total="total" 
         :current-page="cur_page" 
         :page-size="ps"
         ref="pagination"
         >
         </el-pagination>
      </div>
    </div>

    <!-- 编辑弹出框 -->
    <el-dialog title="编辑" :visible.sync="editVisible" width="65%">
      <el-tabs v-model="activeName" @tab-click="handleClick">
        <el-tab-pane label="新建抄底" name="second_1">
          <el-form ref="form" :model="form" status-icon label-width="100px">
            <el-form-item label="* 抄底名称:">
              <el-input v-model="form.cd_name" style="width:40%"></el-input>
            </el-form-item>
            <el-form-item label="* 推广链接:">
              <el-input v-model="form.landUrl" style="width:40%" placeholder="开头为http://或https://的链接"></el-input>
            </el-form-item>
            <!-- <el-tag>频道定向</el-tag>
            <el-form-item label="地域:">
              <el-radio v-model="dx_diyu" :label="0">不限</el-radio>
              <el-radio v-model="dx_diyu" :label="1">自定义</el-radio>
              <el-checkbox-group v-model="direction_dy" v-if="this.dx_diyu == 1">
                <el-checkbox
                  :label="item.id"
                  v-for="(item,index5) in territory"
                  :key="index5"
                >{{item.name}}</el-checkbox>
              </el-checkbox-group>
            </el-form-item> -->
            <!-- <el-form-item label="终端类型:">
              <el-radio-group v-model="dxZdlx" @change="getAdposition">
                <el-radio label="22">PC</el-radio>
                <el-radio label="23">wap</el-radio>
                <el-radio label="158">APP</el-radio>
              </el-radio-group>
            </el-form-item> -->
            <el-form-item label="* APP:">
                <el-radio-group v-model="flowUuid" @change="getAdposition" disabled>
                    <el-radio
                    :label="item.id"
                    v-for="(item,indexMT) in optionsd_APP"
                    :key="indexMT"
                  >{{item.appName}}</el-radio>
                </el-radio-group>
            </el-form-item>
            <el-form-item label="* 选择广告位:">
              <el-radio-group v-model="AD_position" @change="getIdears" disabled> 
                <el-radio
                  :label="item.id"
                  v-for="(item,indexAD) in ADposition"
                  :key="indexAD"
                  class="radio"
                >{{item.name}}</el-radio>
              </el-radio-group>
            </el-form-item>
            <el-form-item label="* 模板名称:" v-if="this.indexID == 2">
                  <el-select v-model="TemplatesById" style="width:40%"  @change="putADtemplateTwo" disabled>
                    <el-option
                      v-for="item in Templates"
                      :key="item.id"
                      :label="item.name"
                      :value="item.id">
                    </el-option>
                  </el-select>
            </el-form-item>
          </el-form>
        </el-tab-pane>
        <el-tab-pane label="上传创意" name="second_2">
          <el-form ref="form" :model="form" status-icon label-width="100px">
            <el-form-item label="标题:" v-if="map[1]">
              <el-input
                v-model="form.threadTitle"
                style="width:80%"
                :maxlength="map[1].wordLimit"
                :placeholder="map[1].placeHolderStr"
              ></el-input>
            </el-form-item>
            <el-form-item label="描述:" v-if="map[2]">
              <el-input
                v-model="form.threadContent"
                style="width:80%"
                :maxlength="map[2].wordLimit"
                :placeholder="map[2].placeHolderStr"
              ></el-input>
            </el-form-item>
            <el-form-item label="头像:" v-if="map[3]">
              <el-input
                v-model="form.userPortrait"
                style="width:50%"
                :placeholder="map[3].placeHolderStr"
              ></el-input>
              <el-upload
                class="upload-demo"
                name="myFile"
                :on-preview="handlePreview"
                :data="{type:1,w:this.map[3].width,h:this.map[3].height}"
                :action="upload"
                :on-success="handleAvatarSuccessHead"
              >
                <el-button size="small" type="primary">点击上传</el-button>
              </el-upload>
            </el-form-item>
            <el-form-item label="视频:" v-if="map[4]">
              <el-input
                v-model="form.entityUrl"
                :placeholder="map[4].placeHolderStr"
                style="width:50%"
              ></el-input>
              <el-upload
                class="upload-demo"
                :data="{type:5}"
                :action="upload"
                name="myFile"
                :on-success="handleAvatarSuccessVideo"
              >
                <el-button size="small" type="primary">点击上传</el-button>
              </el-upload>
            </el-form-item>
            <el-form-item label="图片1:" v-if="map[5]">
              <el-input
                v-model="form.threadPic1"
                :placeholder="map[5].placeHolderStr"
                style="width:50%"
              ></el-input>
              <el-upload
                class="upload-demo"
                :data="{type:1,w:this.map[5].width,h:this.map[5].height}"
                :action="upload"
                name="myFile"
                :on-preview="handlePreview"
                :on-success="handleAvatarSuccessPicture1"
              >
                <el-button size="small" type="primary">点击上传</el-button>
              </el-upload>
            </el-form-item>
            <el-form-item label="图片2:" v-if="map[6]">
              <el-input
                v-model="form.threadPic2"
                :placeholder="map[6].placeHolderStr"
                style="width:50%"
              ></el-input>
              <el-upload
                class="upload-demo"
                :data="{type:1,w:this.map[6].width,h:this.map[6].height}"
                :action="upload"
                name="myFile"
                :on-success="handleAvatarSuccessPicture2"
              >
                <el-button size="small" type="primary">点击上传</el-button>
              </el-upload>
            </el-form-item>
            <el-form-item label="图片3:" v-if="map[7]">
              <el-input
                v-model="form.threadPic3"
                :placeholder="map[7].placeHolderStr"
                style="width:50%"
              ></el-input>
              <el-upload
                class="upload-demo"
                :data="{type:1,w:this.map[7].width,h:this.map[7].height}"
                :action="upload"
                name="myFile"
                :on-success="handleAvatarSuccessPicture3"
              >
                <el-button size="small" type="primary">点击上传</el-button>
              </el-upload>
            </el-form-item>
            <el-form-item label="图片4:" v-if="map[8]">
              <el-input
                v-model="form.threadPic4"
                :placeholder="map[8].placeHolderStr"
                style="width:50%"
              ></el-input>
              <el-upload
                class="upload-demo"
                :data="{type:1,w:this.map[8].width,h:this.map[8].height}"
                :action="upload"
                name="myFile"
                :on-success="handleAvatarSuccessPicture4"
              >
                <el-button size="small" type="primary">点击上传</el-button>
              </el-upload>
            </el-form-item>
            <el-form-item label="图片5:" v-if="map[9]">
              <el-input
                v-model="form.threadPic5"
                :placeholder="map[9].placeHolderStr"
                style="width:50%"
              ></el-input>
              <el-upload
                class="upload-demo"
                :data="{type:1,w:this.map[9].width,h:this.map[9].height}"
                :action="upload"
                :on-success="handleAvatarSuccessPicture5"
              >
                <el-button size="small" type="primary">点击上传</el-button>
              </el-upload>
            </el-form-item>
            <el-form-item label="图片6:" v-if="map[10]">
              <el-input
                v-model="form.threadPic6"
                :placeholder="map[10].placeHolderStr"
                style="width:50%"
              ></el-input>
              <el-upload
                class="upload-demo"
                :data="{type:1,w:this.map[10].width,h:this.map[10].height}"
                :action="upload"
                name="myFile"
                :on-success="handleAvatarSuccessPicture6"
              >
                <el-button size="small" type="primary">点击上传</el-button>
              </el-upload>
            </el-form-item>
            <el-form-item label="图片7:" v-if="map[11]">
              <el-input
                v-model="form.threadPic7"
                :placeholder="map[11].placeHolderStr"
                style="width:50%"
              ></el-input>
              <el-upload
                class="upload-demo"
                :data="{type:1,w:this.map[11].width,h:this.map[11].height}"
                :action="upload"
                name="myFile"
                :on-success="handleAvatarSuc7essPicture8"
              >
                <el-button size="small" type="primary">点击上传</el-button>
              </el-upload>
            </el-form-item>
            <el-form-item label="图片8:" v-if="map[12]">
              <el-input
                v-model="form.threadPic8"
                :placeholder="map[12].placeHolderStr"
                style="width:50%"
              ></el-input>
              <el-upload
                class="upload-demo"
                :data="{type:1,w:this.map[12].width,h:this.map[12].height}"
                :action="upload"
                name="myFile"
                :on-success="handleAvatarSuccessPicture8"
              >
                <el-button size="small" type="primary">点击上传</el-button>
              </el-upload>
            </el-form-item>
            <el-form-item label="图片9:" v-if="map[13]">
              <el-input
                v-model="form.threadPic9"
                :placeholder="map[13].placeHolderStr"
                style="width:50%"
              ></el-input>
              <el-upload
                class="upload-demo"
                :data="{type:1,w:this.map[13].width,h:this.map[13].height}"
                :action="upload"
                name="myFile"
                :on-success="handleAvatarSuccessPicture9"
              >
                <el-button size="small" type="primary">点击上传</el-button>
              </el-upload>
            </el-form-item>
          </el-form>
        </el-tab-pane>
      </el-tabs>
      <span slot="footer" class="dialog-footer" v-if="this.readonly !=1">
        <el-button style="margin-top: 12px;" @click="last_" v-if="activeName != 'second_1'">上一步</el-button>
        <el-button style="margin-top: 12px;" @click="next_" v-if="activeName != 'second_2'">下一步</el-button>
        <!-- <el-button @click="newVisible = false">取 消</el-button> -->
        <el-button type="primary" @click="saveEdit" v-if="activeName == 'second_2' || this.readonly != 1">确 定</el-button>
      </span>
    </el-dialog>

    <!-- 新建弹出框 -->
    <el-dialog title="新建投放" :visible.sync="newVisible" width="65%">
      <el-tabs v-model="activeName" @tab-click="handleClick">
        <el-tab-pane label="新建抄底" name="second_1">
          <el-form ref="form" :model="form" status-icon label-width="100px" :label-position="labelPosition">
            <el-form-item label="* 抄底名称:">
              <el-input v-model="form.cd_name" style="width:40%"></el-input>
            </el-form-item>
            <el-form-item label="* 推广链接:">
              <el-input v-model="form.landUrl" style="width:40%" placeholder="开头为http://或https://的链接"></el-input>
            </el-form-item>
            <el-form-item label="* APP:">
                <el-radio-group v-model="flowUuid" @change="getAdposition">
                    <el-radio
                    :label="item.id"
                    v-for="(item,indexMT) in optionsd_APP"
                    :key="indexMT"
                  >{{item.appName}}</el-radio>
                </el-radio-group>
            </el-form-item>
            <el-form-item label="* 选择广告位:">
              <el-radio-group v-model="AD_position" @change="getIdears">
                <el-radio
                  :label="item.id"
                  v-for="(item,indexAD) in ADposition"
                  :key="indexAD"
                  class="radio"
                >{{item.name}}</el-radio>
              </el-radio-group>
            </el-form-item>
            <el-form-item label="* 模板名称:" v-if="this.indexID == 2">
                  <el-select v-model="TemplatesById" style="width:40%"  @change="putADtemplateTwo">
                    <el-option
                      v-for="item in Templates"
                      :key="item.id"
                      :label="item.name"
                      :value="item.id">
                    </el-option>
                  </el-select>
             </el-form-item>
          </el-form>
        </el-tab-pane>
        <el-tab-pane label="上传创意" name="second_2">
          <el-form ref="form" :model="form" status-icon label-width="100px">
            <el-form-item label="标题:" v-if="map[1]">
              <el-input
                v-model="form.threadTitle"
                style="width:80%"
                :maxlength="map[1].wordLimit"
                :placeholder="map[1].placeHolderStr"
              ></el-input>
            </el-form-item>
            <el-form-item label="描述:" v-if="map[2]">
              <el-input
                v-model="form.threadContent"
                style="width:80%"
                :maxlength="map[2].wordLimit"
                :placeholder="map[2].placeHolderStr"
              ></el-input>
            </el-form-item>
            <el-form-item label="头像:" v-if="map[3]">
              <el-input
                v-model="form.userPortrait"
                style="width:50%"
                :placeholder="map[3].placeHolderStr"
              ></el-input>
              <el-upload
                class="upload-demo"
                name="myFile"
                :on-preview="handlePreview"
                :data="{type:1,w:this.map[3].width,h:this.map[3].height}"
                :action="upload"
                :on-success="handleAvatarSuccessHead"
              >
                <el-button size="small" type="primary">点击上传</el-button>
              </el-upload>
            </el-form-item>
            <el-form-item label="视频:" v-if="map[4]">
              <el-input
                v-model="form.entityUrl"
                :placeholder="map[4].placeHolderStr"
                style="width:50%"
              ></el-input>
              <el-upload
                class="upload-demo"
                :data="{type:5}"
                :action="upload"
                name="myFile"
                :on-success="handleAvatarSuccessVideo"
              >
                <el-button size="small" type="primary">点击上传</el-button>
              </el-upload>
            </el-form-item>
            <el-form-item label="图片1:" v-if="map[5]">
              <el-input
                v-model="form.threadPic1"
                :placeholder="map[5].placeHolderStr"
                style="width:50%"
              ></el-input>
              <el-upload
                class="upload-demo"
                :data="{type:1,w:this.map[5].width,h:this.map[5].height}"
                :action="upload"
                name="myFile"
                :on-preview="handlePreview"
                :on-success="handleAvatarSuccessPicture1"
              >
                <el-button size="small" type="primary">点击上传</el-button>
              </el-upload>
            </el-form-item>
            <el-form-item label="图片2:" v-if="map[6]">
              <el-input
                v-model="form.threadPic2"
                :placeholder="map[6].placeHolderStr"
                style="width:50%"
              ></el-input>
              <el-upload
                class="upload-demo"
                :data="{type:1,w:this.map[6].width,h:this.map[6].height}"
                :action="upload"
                name="myFile"
                :on-success="handleAvatarSuccessPicture2"
              >
                <el-button size="small" type="primary">点击上传</el-button>
              </el-upload>
            </el-form-item>
            <el-form-item label="图片3:" v-if="map[7]">
              <el-input
                v-model="form.threadPic3"
                :placeholder="map[7].placeHolderStr"
                style="width:50%"
              ></el-input>
              <el-upload
                class="upload-demo"
                :data="{type:1,w:this.map[7].width,h:this.map[7].height}"
                :action="upload"
                name="myFile"
                :on-success="handleAvatarSuccessPicture3"
              >
                <el-button size="small" type="primary">点击上传</el-button>
              </el-upload>
            </el-form-item>
            <el-form-item label="图片4:" v-if="map[8]">
              <el-input
                v-model="form.threadPic4"
                :placeholder="map[8].placeHolderStr"
                style="width:50%"
              ></el-input>
              <el-upload
                class="upload-demo"
                :data="{type:1,w:this.map[8].width,h:this.map[8].height}"
                :action="upload"
                name="myFile"
                :on-success="handleAvatarSuccessPicture4"
              >
                <el-button size="small" type="primary">点击上传</el-button>
              </el-upload>
            </el-form-item>
            <el-form-item label="图片5:" v-if="map[9]">
              <el-input
                v-model="form.threadPic5"
                :placeholder="map[9].placeHolderStr"
                style="width:50%"
              ></el-input>
              <el-upload
                class="upload-demo"
                :data="{type:1,w:this.map[9].width,h:this.map[9].height}"
                :action="upload"
                :on-success="handleAvatarSuccessPicture5"
              >
                <el-button size="small" type="primary">点击上传</el-button>
              </el-upload>
            </el-form-item>
            <el-form-item label="图片6:" v-if="map[10]">
              <el-input
                v-model="form.threadPic6"
                :placeholder="map[10].placeHolderStr"
                style="width:50%"
              ></el-input>
              <el-upload
                class="upload-demo"
                :data="{type:1,w:this.map[10].width,h:this.map[10].height}"
                :action="upload"
                name="myFile"
                :on-success="handleAvatarSuccessPicture6"
              >
                <el-button size="small" type="primary">点击上传</el-button>
              </el-upload>
            </el-form-item>
            <el-form-item label="图片7:" v-if="map[11]">
              <el-input
                v-model="form.threadPic7"
                :placeholder="map[11].placeHolderStr"
                style="width:50%"
              ></el-input>
              <el-upload
                class="upload-demo"
                :data="{type:1,w:this.map[11].width,h:this.map[11].height}"
                :action="upload"
                name="myFile"
                :on-success="handleAvatarSuc7essPicture8"
              >
                <el-button size="small" type="primary">点击上传</el-button>
              </el-upload>
            </el-form-item>
            <el-form-item label="图片8:" v-if="map[12]">
              <el-input
                v-model="form.threadPic8"
                :placeholder="map[12].placeHolderStr"
                style="width:50%"
              ></el-input>
              <el-upload
                class="upload-demo"
                :data="{type:1,w:this.map[12].width,h:this.map[12].height}"
                :action="upload"
                name="myFile"
                :on-success="handleAvatarSuccessPicture8"
              >
                <el-button size="small" type="primary">点击上传</el-button>
              </el-upload>
            </el-form-item>
            <el-form-item label="图片9:" v-if="map[13]">
              <el-input
                v-model="form.threadPic9"
                :placeholder="map[13].placeHolderStr"
                style="width:50%"
              ></el-input>
              <el-upload
                class="upload-demo"
                :data="{type:1,w:this.map[13].width,h:this.map[13].height}"
                :action="upload"
                name="myFile"
                :on-success="handleAvatarSuccessPicture9"
              >
                <el-button size="small" type="primary">点击上传</el-button>
              </el-upload>
            </el-form-item>
          </el-form>
        </el-tab-pane>
      </el-tabs>

      <span slot="footer" class="dialog-footer">
        <el-button style="margin-top: 12px;" @click="last_" v-if="activeName != 'second_1'">上一步</el-button>
        <el-button style="margin-top: 12px;" @click="next_" v-if="activeName != 'second_2'">下一步</el-button>
        <!-- <el-button @click="newVisible = false">取 消</el-button> -->
        <el-button type="primary" @click="saveNews" v-if="activeName == 'second_2' || this.readonly != 1">确 定</el-button>
      </span>
    </el-dialog>

    <!-- 删除提示框 -->
    <el-dialog title="提示" :visible.sync="delVisible" width="300px" center>
      <div class="del-dialog-cnt">删除不可恢复，是否确定删除？</div>
      <span slot="footer" class="dialog-footer">
        <el-button @click="delVisible = false">取 消</el-button>
        <el-button type="primary" @click="deleteRow" v-if="this.readonly !=1">确 定</el-button>
      </span>
    </el-dialog>

    <!-- 创意 -->
    <el-dialog title="提示" :visible.sync="template_dialog" width="65%" center>
      <el-form ref="form" :model="form" status-icon label-width="100px">
        <el-form-item label="标题:" v-if="map[1]">
          <el-input
            v-model="form.threadTitle"
            style="width:80%"
            :maxlength="map[1].wordLimit"
            :placeholder="map[1].placeHolderStr"
          ></el-input>
        </el-form-item>
        <el-form-item label="描述:" v-if="map[2]">
          <el-input
            v-model="form.threadContent"
            style="width:80%"
            :maxlength="map[2].wordLimit"
            :placeholder="map[2].placeHolderStr"
          ></el-input>
        </el-form-item>
        <el-form-item label="头像:" v-if="map[3]">
          <el-input
            v-model="form.userPortrait"
            style="width:50%"
            :placeholder="map[3].placeHolderStr"
          ></el-input>
          <el-upload
            class="upload-demo"
            name="myFile"
            :on-preview="handlePreview"
            :data="{type:1,w:this.map[3].width,h:this.map[3].height}"
            :action="upload"
            :on-success="handleAvatarSuccessHead"
          >
            <el-button size="small" type="primary">点击上传</el-button>
          </el-upload>
        </el-form-item>
        <el-form-item label="视频:" v-if="map[4]">
          <el-input v-model="form.entityUrl" :placeholder="map[4].placeHolderStr" style="width:50%"></el-input>
          <el-upload
            class="upload-demo"
            :data="{type:5}"
            :action="upload"
            name="myFile"
            :on-success="handleAvatarSuccessVideo"
          >
            <el-button size="small" type="primary">点击上传</el-button>
          </el-upload>
        </el-form-item>
        <el-form-item label="图片1:" v-if="map[5]">
          <el-input
            v-model="form.threadPic1"
            :placeholder="map[5].placeHolderStr"
            style="width:50%"
          ></el-input>
          <el-upload
            class="upload-demo"
            :data="{type:1,w:this.map[5].width,h:this.map[5].height}"
            :action="upload"
            name="myFile"
            :on-preview="handlePreview"
            :on-success="handleAvatarSuccessPicture1"
          >
            <el-button size="small" type="primary">点击上传</el-button>
          </el-upload>
        </el-form-item>
        <el-form-item label="图片2:" v-if="map[6]">
          <el-input
            v-model="form.threadPic2"
            :placeholder="map[6].placeHolderStr"
            style="width:50%"
          ></el-input>
          <el-upload
            class="upload-demo"
            :data="{type:1,w:this.map[6].width,h:this.map[6].height}"
            :action="upload"
            name="myFile"
            :on-success="handleAvatarSuccessPicture2"
          >
            <el-button size="small" type="primary">点击上传</el-button>
          </el-upload>
        </el-form-item>
        <el-form-item label="图片3:" v-if="map[7]">
          <el-input
            v-model="form.threadPic3"
            :placeholder="map[7].placeHolderStr"
            style="width:50%"
          ></el-input>
          <el-upload
            class="upload-demo"
            :data="{type:1,w:this.map[7].width,h:this.map[7].height}"
            :action="upload"
            name="myFile"
            :on-success="handleAvatarSuccessPicture3"
          >
            <el-button size="small" type="primary">点击上传</el-button>
          </el-upload>
        </el-form-item>
        <el-form-item label="图片4:" v-if="map[8]">
          <el-input
            v-model="form.threadPic4"
            :placeholder="map[8].placeHolderStr"
            style="width:50%"
          ></el-input>
          <el-upload
            class="upload-demo"
            :data="{type:1,w:this.map[8].width,h:this.map[8].height}"
            :action="upload"
            name="myFile"
            :on-success="handleAvatarSuccessPicture4"
          >
            <el-button size="small" type="primary">点击上传</el-button>
          </el-upload>
        </el-form-item>
        <el-form-item label="图片5:" v-if="map[9]">
          <el-input
            v-model="form.threadPic5"
            :placeholder="map[9].placeHolderStr"
            style="width:50%"
          ></el-input>
          <el-upload
            class="upload-demo"
            :data="{type:1,w:this.map[9].width,h:this.map[9].height}"
            :action="upload"
            :on-success="handleAvatarSuccessPicture5"
          >
            <el-button size="small" type="primary">点击上传</el-button>
          </el-upload>
        </el-form-item>
        <el-form-item label="图片6:" v-if="map[10]">
          <el-input
            v-model="form.threadPic6"
            :placeholder="map[10].placeHolderStr"
            style="width:50%"
          ></el-input>
          <el-upload
            class="upload-demo"
            :data="{type:1,w:this.map[10].width,h:this.map[10].height}"
            :action="upload"
            name="myFile"
            :on-success="handleAvatarSuccessPicture6"
          >
            <el-button size="small" type="primary">点击上传</el-button>
          </el-upload>
        </el-form-item>
        <el-form-item label="图片7:" v-if="map[11]">
          <el-input
            v-model="form.threadPic7"
            :placeholder="map[11].placeHolderStr"
            style="width:50%"
          ></el-input>
          <el-upload
            class="upload-demo"
            :data="{type:1,w:this.map[11].width,h:this.map[11].height}"
            :action="upload"
            name="myFile"
            :on-success="handleAvatarSuc7essPicture8"
          >
            <el-button size="small" type="primary">点击上传</el-button>
          </el-upload>
        </el-form-item>
        <el-form-item label="图片8:" v-if="map[12]">
          <el-input
            v-model="form.threadPic8"
            :placeholder="map[12].placeHolderStr"
            style="width:50%"
          ></el-input>
          <el-upload
            class="upload-demo"
            :data="{type:1,w:this.map[12].width,h:this.map[12].height}"
            :action="upload"
            name="myFile"
            :on-success="handleAvatarSuccessPicture8"
          >
            <el-button size="small" type="primary">点击上传</el-button>
          </el-upload>
        </el-form-item>
        <el-form-item label="图片9:" v-if="map[13]">
          <el-input
            v-model="form.threadPic9"
            :placeholder="map[13].placeHolderStr"
            style="width:50%"
          ></el-input>
          <el-upload
            class="upload-demo"
            :data="{type:1,w:this.map[13].width,h:this.map[13].height}"
            :action="upload"
            name="myFile"
            :on-success="handleAvatarSuccessPicture9"
          >
            <el-button size="small" type="primary">点击上传</el-button>
          </el-upload>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="template_dialog = false">取 消</el-button>
        <el-button type="primary" @click="EditIdear" v-if="this.readonly !=1">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import {
  putpage,
  dicmapList,
  adPositiongetList,
  adPositiongetModules,
  uploaduploadWithSize,
  entityadd,
  putadd,
  putinfo,
  putupdate
} from "@/api/Api.js";
import {
  entityinfo,
  entitygetOneByPid,
  entityupdate,
  putdelete,appgetall,adPositiongetTemplatesById,adPositiongetModulesByTemplateId
} from "@/api/Api.js";

// import picker from "../../../components/common/picker/index.vue";
// import diqu from '../../../components/common/diqu.vue'
var ADtype = new Map()
export default {
  name: "basetable",
  data() {
    return {
      labelPosition:'left',
      upload: uploaduploadWithSize,
      tableData: [],
      cur_page: 1,
      total: 1,
      ps: 10,
      select_cate: "",
      select_word: "",
      del_list: [],
      map: {
        1: null,
        2: null,
        3: null,
        4: null,
        5: null,
        6: null,
        7: null,
        8: null,
        9: null,
        10: null,
        11: null,
        12: null,
        13: null
      },
      dx_pindao: 0,
      dx_chexi: 0,
      dx_chejibie: 0,
      dx_jiage: 0,
      dx_diyu: 0,
      is_search: false,
      editVisible: false,
      delVisible: false,
      newVisible: false,
      newaddAD: false,
      template_dialog: false,
      currentRow: null,
      idx: -1,
      activeName: "second_1",
      time: "",
      hour: "",
      hour2: "",
      unitLimit: "",
      deliveryMode: "1",
      price: "",
      v_planName: "",
      v_unitName: "",
      v_deliveryMode: "",
      v_price: "",
      v_unitLimit: "",
      v_star_end: "",
      v_extensionType: "",
      v_impMonitorUrl: "",
      v_clkMonitorUrl: "",
      v_extCreativeId: "",
      v_adCollection: "",
      click_hour: [{ hour: "", hour2: "" }],
      ADposition: [],
      direction_pd: [],
      direction_cx: [],
      direction_cjb: [],
      direction_jg: [],
      direction_dy: [],
      channel: [],
      cars: [],
      carrank: [],
      price: [],
      territory: [],
      dxZdlx: 22,
      AD_position: "",
      optionsd_APP: [],
      appIds:'',
      form: {
        landUrl: "",
        id: "",
        cd_name: "",
        threadTitle: "",
        threadContent: "",
        entityUrl: "",
        userPortrait: "",
        threadPic1: "",
        threadPic2: "",
        threadPic3: "",
        threadPic4: "",
        threadPic5: "",
        threadPic6: "",
        threadPic7: "",
        threadPic8: "",
        threadPic9: "",

        name: "",
        date: "",
        switch: true,
        add_time: "",
        options1: "",
        type: 1,
        types: null,
        flow_management: null,
        data_report: null,
        make_address1: [{ make_address: "" }],
        click_address1: [{ click_address: "" }],
        click_shebei1: [{ click_shebei: "" }],
        put_PDB: "1",
        charge_mode: 1,
        number_facility: 1,
        number_ip: 1,
        number_id: 2,
        number_guiyin: 1,
        put_type: 1,
        age: "1",
        checked: false,
        sex: "1",
        Education: "1",
        Action: "1",
        Crowd: "1",
        interest: "1",
        channel: "1",
        order: "1",
        position: "1",
        guolv: "1",
        zdlx: "1",
        diqu: "1",
        system: "1",
        yys: "1",
        network: "1",
        direction: "1",
        rule: "",
        media_type: "1",
        AD_position: "",
        find: "1",
        APP_zy: "",
        find_location1: "1",
        guolv_APP: "1",
        xtyh: "1",
        cpa: "",
        time: "",
        time_frame: "1",
        syID: "",

        options: [
          {
            value: "1",
            label: "流量管理员"
          },
          {
            value: "2",
            label: "广告客户"
          },
          {
            value: "3",
            label: "直客客户"
          },
          {
            value: "4",
            label: "代理商"
          }
        ],
        value: "1"
      },
      options1: "1",
      options2: "2",
      options3: "3",
      options: [
        {
          value: "1",
          label: "流量管理员"
        },
        {
          value: "2",
          label: "广告客户"
        },
        {
          value: "3",
          label: "直客客户"
        },
        {
          value: "4",
          label: "代理商"
        }
      ],
        Templates: [
        {
          value: '',
          label: ''
        },
      ],
      TemplatesById:'',
      indexID:1,
      flowUuid: "M7nuyi",
      readonly:''
    };
  },
  created() {
    this.readonly = this.$store.state.map.get(this.$store.state.currentPath);
    this.getList();
    this.dicmapList();
  },

  methods: {
    
    // 定向频道
    dicmapList() {
       //  APP名称
      appgetall().then(res=>{
        console.log(res)
          let data = res.data; 
            if(data.code != 'A000000') {
            this.$message.error(data.message);
            return;
          }    
          this.optionsd_APP = data.data
        });
      // 频道
      let params = {
        gid: 149
      };
      dicmapList(params).then(res => {
      let data = res.data;
        if (data.code != "A000000") {
          this.$message.error(data.message);
        }
        let list = [];
        for (var key in data.data) {
          let obj = { id: data.data[key], name: key };
          list.push(obj);
        }
        // console.log(res)
        this.channel = list;
      });
      // 车系
      let cars = {
        gid: 150
      };
      dicmapList(cars).then(res => {
      let data = res.data;
        if (data.code != "A000000") {
          this.$message.error(data.message);
        }
        let list = [];
        for (var key in data.data) {
          let obj = { id: data.data[key], name: key };
          list.push(obj);
        }
        this.cars = list;
        console.log(list)
      });
      // 车级别
      let carrank = {
        gid: 77
      };
      dicmapList(carrank).then(res => {
      let data = res.data;
        if (data.code != "A000000") {
          this.$message.error(data.message);
        }
        let list = [];
        for (var key in data.data) {
          let obj = { id: data.data[key], name: key };
          list.push(obj);
        }
        // console.log(list)
        this.carrank = list;
      });
      // 价格
      let price = {
        gid: 78
      };
      dicmapList(price).then(res => {
      let data = res.data;
        if (data.code != "A000000") {
          this.$message.error(data.message);
        }
        let list = [];
        for (var key in data.data) {
          let obj = { id: data.data[key], name: key };
          list.push(obj);
        }
        // console.log(list)
        this.price = list;
      });
      // 地域
      let territory = {
        gid: 151
      };
      dicmapList(territory).then(res => {
      let data = res.data;
        if (data.code != "A000000") {
          this.$message.error(data.message);
        }
        let list = [];
        for (var key in data.data) {
          let obj = { id: data.data[key], name: key };
          list.push(obj);
        }
        // console.log(list)
        this.territory = list;
      });
    },
    // 获取广告位
    getAdposition(pid) {
      this.AD_position = ''
      this.TemplatesById = ''
      let params = {
        appId: this.flowUuid,
      };
      adPositiongetList(params).then(res => {
      let data = res.data;
        if (data.code != "A000000") {
          this.$message.error(data.message);
        }
        this.ADposition = data.data.data;
       data.data.data.forEach(element => {
            ADtype.set(element.id,element.type)
        });
        if (pid) {
          this.AD_position = pid;
        }
        console.log(this.AD_position)
      });
    },
    // 获取 广告位模板列表
    putADtemplateName(){
        let params = {
              id:this.AD_position
        }
        adPositiongetTemplatesById(params).then(res=>{
              console.log(res)
              let data = res.data
              this.Templates = data.data 
          });
    },
      putADtemplateTwo(){
               this.map = {1:null,2:null,3:null,4:null,5:null,6:null,7:null,8:null,9:null,10:null,11:null,12:null,13:null,14:null};
               let params ={
                    templateId:this.TemplatesById
                  }
            adPositiongetModulesByTemplateId(params).then(res=>{ 
                console.log(res) 
                let data = res.data
                let list = data.data; 
                list.forEach(element => {
                  console.log(element)
                  this.map[element.mId] = element;
                  });         
                });
    },
    //  获取创意广告位模板
    getIdears() {
      this.TemplatesById = ''
      this.map = {1:null,2:null,3:null,4:null,5:null,6:null,7:null,8:null,9:null,10:null,11:null,12:null,13:null,14:null};
      if(ADtype.get(this.AD_position) == 7 || ADtype.get(this.AD_position) == 217 ){
          this.indexID = 2
          this.putADtemplateName()
      } else{
           this.indexID = 1
          let params = {
            adPositionId: this.AD_position
          };
          adPositiongetModules(params).then(res => {
          let data = res.data;
            console.log(data);  
            if (data.code != 'A000000') {
                  this.$message.error(data.message);
                  return;
              }  
            let list = data.data;
            this.map = new Map();

            if(list) {
              list.forEach(element => {
                // if(element.mid>=5) {
                // }
                this.map[element.mId] = element;
                console.log(element)
                // this.map.set(element.mId,element);
              });
            }else {
                this.map =  {
                      1: null,
                      2: null,
                      3: null,
                      4: null,
                      5: null,
                      6: null,
                      7: null,
                      8: null,
                      9: null,
                      10: null,
                      11: null,
                      12: null,
                      13: null
                      }
              } 
          });
      }
 
    },
    // 新建
    create() {
      this.indexID =1
      this.flowUuid=''
      this.Templates= []
      this.TemplatesById =''
      this.newVisible = true;
      this.form = {};
      this.map = {
            1: null,
            2: null,
            3: null,
            4: null,
            5: null,
            6: null,
            7: null,
            8: null,
            9: null,
            10: null,
            11: null,
            12: null,
            13: null
          };
      this.activeName = "second_1";
      this.direction_pd = [];
      this.direction_cx = [];
      this.direction_cjb = [];
      this.direction_jg = [];
      this.direction_dy = [];
      this.dxZdlx = "";
      this.AD_position = "";
      this.dx_pindao = 0;
      this.dx_chexi = 0;
      this.dx_chejibie = 0;
      this.dx_jiage = 0;
      this.dx_diyu = 0;
      this.ADposition =[];
      this.dxZdlx = '22'
    },

    saveNews() {
          // 参数判断
      if(!this.form.cd_name) {
        this.$message.error("抄底名称不能为空");
        return;
      }
      let reg = /(http|ftp|https):\/\/[\w\-_]+(\.[\w\-_]+)+([\w\-\.,@?^=%&:/~\+#]*[\w\-\@?^=%&/~\+#])?/
       if(!reg.test(this.form.landUrl)){
        this.$message('请输入正确的推广链接')
        return;
      }
      if(!this.AD_position) {
        this.$message.error("广告位不能为空");
        return;
      }

      if(!this.form.entityUrl && !this.form.threadPic1) {
          this.$message.error("主素材不能为空");
          return;
      }

      let params = {
        // putType: 18,
        // dxMedia: this.flowUuid,
        putName: this.form.cd_name,
        landUrl: this.form.landUrl,
        // dxChannel: this.direction_pd.join(","),
        // dxGgwSeriesId: this.direction_cx.join(","),
        // dxGgwLevelId: this.direction_cjb.join(","),
        // dxGgwPriceTagId: this.direction_jg.join(","),
        // dxGgwAreaId: this.direction_dy.join(","),
        // dxZdlx: this.dxZdlx,
        dxApp:this.flowUuid,
        adPosition: this.AD_position
      };

      putadd(params).then(res => {
      let data = res.data;
        // console.log(data)
        if (data.code != "A000000") {
          this.$message.error(data.message);
          return;
          console.log(this.datas);
        } else {
          this.datas = data.data;
          // 保存成功掉保存创意
          let par = {
            putType: 18,
            pid: this.datas,
            templateId:this.TemplatesById,
            threadTitle: this.form.threadTitle,
            threadContent: this.form.threadContent,
            userPortrait: this.form.userPortrait,
            entityUrl: this.form.entityUrl,
            threadPic1: this.form.threadPic1,
            threadPic2: this.form.threadPic2,
            threadPic3: this.form.threadPic3,
            threadPic4: this.form.threadPic4,
            threadPic5: this.form.threadPic5,
            threadPic6: this.form.threadPic6,
            threadPic7: this.form.threadPic7,
            threadPic8: this.form.threadPic8,
            threadPic9: this.form.threadPic9
          };
          entityadd(par).then(res => {
            console.log(res);
            //let data = res;
            if (res.data.code != "A000000") {
              this.$message.error(res.data.message);
              return;
            }
          });
        }
        this.newVisible = false;
        this.getList();
      });
    },

    // 分页导航
    handleCurrentChange(val) {
      this.cur_page = val;
      let params = { cp: this.cur_page, ps: this.ps, putType: 18,putName:this.select_word };
      putpage(params).then(res => {
        let data = res.data
          if(data.code != "A000000"){
            this.$message.error(data.message);
            return;
        }
             this.tableData = data.data.data;
             this.total = data.data.data.totalItemNum;
        // console.log(res)
      });
    },
    // 初始化列表
    getList() {
      let params = { cp: this.cur_page, ps: this.ps, putType: 18 };
      putpage(params).then(res => {
        console.log(res)
        let data = res.data
          if(data.code != "A000000"){
            this.$message.error(data.message);
            return;
        }
        this.tableData = data.data.data;
        this.total = data.data.data.totalItemNum;
        // console.log(res)
      });
    },
    stringToArrayOfNum(numArray,str) {
      if (str) {
        let array = new String(str).split(",");
        for (let item of array) {
          numArray.push(parseInt(item));
        }
      }
    },
    // 搜索
    search() {
       this.$refs.pagination.lastEmittedPage = 1
      this.cur_page = 1;
      let params = { cp: this.cur_page, ps: this.ps, putType: 18,putName:this.select_word };
      putpage(params).then(res => {
        let data = res.data
          if(data.code != "A000000"){
            this.$message.error(data.message);
            return;
        }
           this.tableData = data.data.data;
        this.total = data.data.data.totalItemNum;
        // console.log(res)
      });
    },

    // 编辑
    handleEdit(index, row) {

      this.activeName = "second_1";
      this.item = row;
      this.id = this.item.id;
      let params = {
        id: this.item.id,
        // putType: 18
      };
      this.direction_pd = [];
      this.direction_cx = [];
      this.direction_cjb = [];
      this.direction_jg = [];
      this.direction_dy = [];
      putinfo(params).then(res => {
        console.log(res)
        let data = res.data
        if (data.code != "A000000") {
            this.$message.error(data.message);
        }
        this.form.cd_name = data.data.putName;
        this.form.landUrl = data.data.landUrl;
        this.flowUuid = parseInt(data.data.dxApp);
        // this.AD_position = parseInt(res.data.result.adPosition)
        let pid = data.data.adPosition;
        this.AD_position = data.data.adPosition
        let IDS = data.data.id
        this.getAdposition(pid);
          let params = {
              id: IDS
            };
       entitygetOneByPid(params).then(res => {
                  console.log(res);
                let data = res.data;
                  if (data.code != "A000000") {
                    this.$message.error(data.message);
                  }
                  this.putADtemplateName()
                  if(data.data.templateId != null ){
                    this.indexID = 2
                    this.TemplatesById = data.data.templateId;
                  }else{
                      this.indexID = 1
                  }
                  if(data.data){
                    this.map = new Map();
                      let list = data.data.moduleRelations;
                      list.forEach(element => {
                          this.map[element.mId] = element;
                      });   
                      this.form.threadTitle = data.data.threadTitle;
                      this.form.threadContent =data.data.threadContent;
                      this.form.entityUrl = data.data.entityUrl;
                      this.form.userPortrait = data.data.userPortrait;
                      this.form.threadPic1 = data.data.threadPic1;
                      this.form.threadPic2 = data.data.threadPic2;
                      this.form.threadPic3 = data.data.threadPic3;
                      this.form.threadPic4 = data.data.threadPic4;
                      this.form.threadPic5 = data.data.threadPic5;
                      this.form.threadPic6 = data.data.threadPic6;
                      this.form.threadPic7 = data.data.threadPic7;
                      this.form.threadPic8 = data.data.threadPic8;
                      this.form.threadPic9 = data.data.threadPic9;
                      this.ids = data.data.id
                  }else{
                    this.map = {
                          1: null,
                          2: null,
                          3: null,
                          4: null,
                          5: null,
                          6: null,
                          7: null,
                          8: null,
                          9: null,
                          10: null,
                          11: null,
                          12: null,
                          13: null
                        };
                  }
        
        // console.log(this.form);
        // this.template_dialog = true;
        });
      });
      this.editVisible = true;
    },
    // 保存编辑
    saveEdit() {
      if(this.dx_pindao == 0){
          this.direction_pd =[]
      }
      if(this.dx_chexi == 0){
          this.direction_cx =[]
      }
      if(this.dx_chejibie == 0){
          this.direction_cjb =[]
      }
      if(this.dx_jiage == 0){
          this.direction_jg =[]
      }
      if(this.dx_diyu == 0){
          this.direction_dy =[]
      }
       if(!this.form.cd_name) {
        this.$message.error("抄底名称不能为空");
        return;
      }
      let reg = /(http|ftp|https):\/\/[\w\-_]+(\.[\w\-_]+)+([\w\-\.,@?^=%&:/~\+#]*[\w\-\@?^=%&/~\+#])?/
       if(!reg.test(this.form.landUrl)){
        this.$message('请输入正确的推广链接')
        return;
      }
      if(!this.AD_position) {
        this.$message.error("广告位不能为空");
        return;
      }
      let params = {
        putType: 18,
        id: this.id,
        landUrl:this.form.landUrl,
        dxApp:this.flowUuid,
        putName:this.form.cd_name,
        adPosition: this.AD_position
      };
      putupdate(params).then(res => {
        console.log(res);
        //let data = res;
        // console.log(data)
        if (res.data.code != "A000000") {
          this.$message.error(res.data.message);
        }
        // 保存创意
        let par = {
            putType: 18,
            id: this.ids,
            threadTitle: this.form.threadTitle,
            threadContent: this.form.threadContent,
            entityUrl: this.form.entityUrl,
            userPortrait: this.form.userPortrait,
            threadPic1: this.form.threadPic1,
            threadPic2: this.form.threadPic2,
            threadPic3: this.form.threadPic3,
            threadPic4: this.form.threadPic4,
            threadPic5: this.form.threadPic5,
            threadPic6: this.form.threadPic6,
            threadPic7: this.form.threadPic7,
            threadPic8: this.form.threadPic8,
            threadPic9: this.form.threadPic9
          };
          entityupdate(par).then(res => {
            console.log(res);
            //let data = res;
            if (res.data.code != "A000000") {
              this.$message.error(res.data.message);
            }
             this.search();
             this.editVisible = false;
          });
      });
    },

    // handIdear(index, row) {
    //   console.log(row);
      
    //   let params = {
    //     pid: row.id
    //     // putType: 18
    //   };
    //   entitygetOneByPid(params).then(res => {
    //     console.log(res);
    //   let data = res.data;
    //     if (data.code != "A000000") {
    //       this.$message.error(data.message);
    //     }
       
    //     if(data.data){
    //       this.map = new Map();
    //         let list = data.data.moduleRelations;
    //         list.forEach(element => {
    //             this.map[element.mId] = element;
    //         });   
    //         this.form.threadTitle = data.data.threadTitle;
    //         this.form.threadContent =data.data.threadContent;
    //         this.form.entityUrl = data.data.entityUrl;
    //         this.form.userPortrait = data.data.userPortrait;
    //         this.form.threadPic1 = data.data.threadPic1;
    //         this.form.threadPic2 = data.data.threadPic2;
    //         this.form.threadPic3 = data.data.threadPic3;
    //         this.form.threadPic4 = data.data.threadPic4;
    //         this.form.threadPic5 = data.data.threadPic5;
    //         this.form.threadPic6 = data.data.threadPic6;
    //         this.form.threadPic7 = data.data.threadPic7;
    //         this.form.threadPic8 = data.data.threadPic8;
    //         this.form.threadPic9 = data.data.threadPic9;
    //         this.ids = data.data.id
    //     }else{
    //       this.map = {
    //             1: null,
    //             2: null,
    //             3: null,
    //             4: null,
    //             5: null,
    //             6: null,
    //             7: null,
    //             8: null,
    //             9: null,
    //             10: null,
    //             11: null,
    //             12: null,
    //             13: null
    //           };
    //     }
        
    //     console.log(this.form);
    //     this.template_dialog = true;
    //   });

    //   // });
    // },
    EditIdear() {
      let params = {
        // putType: 18,
        id: this.ids,
        threadTitle: this.form.threadTitle,
        threadContent: this.form.threadContent,
        entityUrl: this.form.entityUrl,
        userPortrait: this.form.userPortrait,
        threadPic1: this.form.threadPic1,
        threadPic2: this.form.threadPic2,
        threadPic3: this.form.threadPic3,
        threadPic4: this.form.threadPic4,
        threadPic5: this.form.threadPic5,
        threadPic6: this.form.threadPic6,
        threadPic7: this.form.threadPic7,
        threadPic8: this.form.threadPic8,
        threadPic9: this.form.threadPic9
      };
      entityupdate(params).then(res => {
        console.log(res);
        //let data = res;
        if (res.data.code != "A000000") {
          this.$message.error(res.data.message);
        }
        this.getList();
        this.template_dialog = false;
      });
    },

    handleDelete(index, row) {
      this.idx = index;
      this.row = row;
      this.delVisible = true;
    },
    // 确定删除
    deleteRow() {
      let params = {
        putType: 18,
        id: this.row.id
      };
      putdelete(params).then(res => {
        console.log(res);
        //let data = res;
        if (res.data.code != "A000000") {
          this.$message.error(res.data.message);
        } else {
          this.$message.success("删除成功");
          this.delVisible = false;
          this.getList();
        }
      });
    },

    template_() {},
    handleClick(tab, event) {
      console.log(tab, event);
    },
    make_address() {
      this.form.make_address1.push({ make_address: "" });
    },
    click_address() {
      this.form.click_address1.push({ click_address: "" });
    },
    click_shebei() {
      console.log(this.form);
      this.form.click_shebei1.push({ click_shebei: "" });
    },
    add_hours() {
      this.form.click_hour.push({ hour: "", hour2: "" });
    },
    last_() {
      let value = this.activeName.split("_");
      this.activeName = value[0] + "_" + parseInt(parseInt(value[1]) - 1);
    },
    next_() {
      let value = this.activeName.split("_");
      this.activeName = value[0] + "_" + parseInt(parseInt(value[1]) + 1);
      console.log(this.activeName);
    },
    handleRemove(file, fileList) {
      // console.log(file, fileList);
    },
    handlePreview(file) {
      console.log(file);
    },
    // 头像
    handleAvatarSuccessHead(res, file) {
      let data =JSON.parse(res.data);
      if (data.code != "A000000") {
        this.$message.error(data.message);
        return;
      } else {
        this.form.userPortrait = data.data.url;
      }
      this.$forceUpdate();
    },
    // 视频
    handleAvatarSuccessVideo(res, file) {
           let data =JSON.parse(res.data);
      if (data.code != "A000000") {
        this.$message.error(data.message);
        return;
      } else {
        this.form.entityUrl = data.data.url;
      }
      this.$forceUpdate();
    },
    // 图片
    handleAvatarSuccessPicture1(res, file) {
      console.log(res);
           let data =JSON.parse(res.data);
      if (data.code != "A000000") {
        this.$message.error(data.message);
        return;
      } else {
        this.form.threadPic1 = data.data.url;
      }
      this.$forceUpdate();
    },
    handleAvatarSuccessPicture2(res, file) {
           let data =JSON.parse(res.data);
      if (data.code != "A000000") {
        this.$message.error(data.message);
        return;
      } else {
        this.form.threadPic2 = data.data.url;
      }
      this.$forceUpdate();
    },
    handleAvatarSuccessPicture3(res, file) {
          let data =JSON.parse(res.data);
      if (data.code != "A000000") {
        this.$message.error(data.message);
        return;
      } else {
        this.form.threadPic3 = data.data.url;
      }
      this.$forceUpdate();
    },
    handleAvatarSuccessPicture4(res, file) {
      let data = res;
      if (data.code != "A000000") {
        this.$message.error(data.message);
        return;
      } else {
        this.form.threadPic4 = data.data.url;
      }
      this.$forceUpdate();
    },
    handleAvatarSuccessPicture5(res, file) {
          let data =JSON.parse(res.data);
      if (data.code != "A000000") {
        this.$message.error(data.message);
        return;
      } else {
        this.form.threadPic5 = data.data.url;
      }
      this.$forceUpdate();
    },
    handleAvatarSuccessPicture6(res, file) {
         let data =JSON.parse(res.data);
      if (data.code != "A000000") {
        this.$message.error(data.message);
        return;
      } else {
        this.form.threadPic6 = data.data.url;
      }
      this.$forceUpdate();
    },
    handleAvatarSuccessPicture7(res, file) {
          let data =JSON.parse(res.data);
      if (data.code != "A000000") {
        this.$message.error(data.message);
        return;
      } else {
        this.form.threadPic7 = data.data.url;
      }
      this.$forceUpdate();
    },
    handleAvatarSuccessPicture8(res, file) {
           let data =JSON.parse(res.data);
      if (data.code != "A000000") {
        this.$message.error(data.message);
        return;
      } else {
        this.form.threadPic8 = data.data.url;
      }
      this.$forceUpdate();
    },
    handleAvatarSuccessPicture9(res, file) {
      let data =JSON.parse(res.data);
      if (data.code != "A000000") {
        this.$message.error(data.message);
        return;
      } else {
        this.form.threadPic9 = data.data.url;
      }
      this.$forceUpdate();
    }
  }
};
</script>

<style scoped>
.handle-box {
  margin-bottom: 20px;
}

.handle-select {
  width: 120px;
}

.handle-input {
  width: 300px;
  display: inline-block;
}
.del-dialog-cnt {
  font-size: 16px;
  text-align: center;
}
.header-select {
  margin-bottom: 20px;
}
.btn_plan {
  text-align: center;
  margin: 0 auto;
  width: 100px;
  height: 50px;
  background-color: #249cd3;
  border: 1px silver solid;
  color: aliceblue;
  line-height: 50px;
}
.btn_plan_2 {
  text-align: center;
  margin: 0 auto;
  width: 100px;
  height: 50px;
  background-color: #ffffff;
  border: 1px silver solid;
  color: black;
  line-height: 50px;
  margin-top: 20px;
}
.span {
  color: red;
}
.el-checkbox + .el-checkbox {
  margin-left: 0px;
}
.btn_right {
  float: right;
}
.r_div {
  margin-left: 80px;
}
.el-radio+.el-radio {
    margin-left: 0px !important;
}
</style>
<style>
.radio {
  width: 200px !important;
  padding-top: 20px !important;
  color: #606266;
  font-weight: 500;
  line-height: 1;
  cursor: pointer;
  white-space: nowrap;
  outline: 0;
}

.el-upload--text {
  border: 0px dashed #d9d9d9 !important;
  width: 80px !important;
  height: 32px !important;
}
.upload-demo {
  display: inline-flex;
}
.el-upload-list {
  display: none;
}
</style>



