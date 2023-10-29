<template>
  <div class="table">
    <div class="container">
      <div class="handle-box">
        <el-input v-model="select_name" placeholder="请输入创意名称" class="handle-input mr10"></el-input>
        <el-autocomplete
          class="inline-input"
          v-model="select_launch"
          value-key="putName"
          :fetch-suggestions="querySearch"
          placeholder="请输入投放名称"
          @select="handleSelect"
        ></el-autocomplete>
        <!-- <el-autocomplete
          class="inline-input"
          v-model="select_word"
          value-key="planName"
          :fetch-suggestions="querySearchCustomer"
          placeholder="请输入计划名称"
          @select="handleSelectCustomer"
        ></el-autocomplete> -->
        <el-select v-model="value" placeholder="运行状态" class="handle-input mr10">
          <el-option
            v-for="item in options_"
            :key="item.value"
            :label="item.label"
            :value="item.value"
          ></el-option>
        </el-select>
        <el-button type="primary" icon="search" @click="search">搜索</el-button>
        <el-button type="primary" icon="search" @click="create" v-if="this.readonly !=1">新建创意</el-button>
      </div>
      <el-table :data="tableData" border style="width: 100%" ref="multipleTable">
        <el-table-column prop="id" label="创意ID" align="center"></el-table-column>
        <el-table-column prop="entityName" label="创意名称" align="center"></el-table-column>
        <el-table-column prop="putName" label="投放名称" align="center"></el-table-column>
        <el-table-column prop="planName" label="计划名称" align="center"></el-table-column>
        <el-table-column prop="adverName" label="客户" align="center"></el-table-column>
        <el-table-column
          prop="entityState"
          label="创意状态"
          align="center"
          :formatter="formatterTypesentityState"
        ></el-table-column>
        <el-table-column prop label="运行状态" align="center" v-if="this.readonly !=1">
          <template slot-scope="scope">
            <el-switch
              v-model="scope.row.runState"
              :active-value="1"
              :inactive-value="0"
              @change="changeStatus($event,scope.row)"
            ></el-switch>
          </template>
        </el-table-column>
        <el-table-column label="操作" width="290" align="center">
          <template slot-scope="scope">
            <el-button size="small" type="success" @click="Audit(scope.$index, scope.row)">提审</el-button>
            <el-button size="small" type="success" @click="handleEdit(scope.$index, scope.row)">编辑</el-button>
            <el-button size="small" type="danger" @click="handleDelete(scope.$index, scope.row)" v-if="this.readonly !=1">删除</el-button>
            <el-button size="small" type="danger" @click="Editinfo(scope.$index, scope.row)">报告</el-button>
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
    <!-- 新建 -->
    <el-dialog title="新增创意" :visible.sync="newVisible" width="50%">
      <el-form ref="form" :model="form" status-icon label-width="100px">
        <el-form-item label="* 计划名称:" class="handle-input">
          <el-autocomplete
            class="inline-input"
            v-model="select_word"
            value-key="planName"
            :fetch-suggestions="querySearchCustomer"
            placeholder="请输入订单名称"
            @select="handleSelectCustomer"
          ></el-autocomplete>
        </el-form-item>
        <el-form-item label="* 投放名称:">
          <el-select v-model="form.putNAme" style="width:80%" @change="putAdPosition">
            <el-option
              v-for="item in putNameSelect"
              :key="item.id"
              :label="item.putName"
              :value="item.id"
            ></el-option>
          </el-select>
        </el-form-item>
        <!-- <el-form-item label="落地页地址:">
          <el-input v-model="form.landUrl" style="width:80%"></el-input>
        </el-form-item> -->
        <el-form-item label="* 创意名称:">
          <el-input v-model="form.entityName" style="width:80%"></el-input>
        </el-form-item>
        <!-- <el-form-item v-for="(item,index) in list" :key="index" :label="item.moduleName">
                      <div v-if="item.moduleType == 1">
                           <el-input v-model="form[item.moduleKey]" style="width:80%"></el-input>
                      </div>
                      <div v-if="item.moduleType == 2">
                      </div> 
                      <div v-if="item.moduleType == 3">
                      </div>     
        </el-form-item>-->
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
        <el-button @click="newVisible = false">取 消</el-button>
        <el-button type="primary" @click="saveNews">确 定</el-button>
      </span>
    </el-dialog>

    <!-- 编辑弹出框 -->
    <el-dialog title="编辑" :visible.sync="editVisible" width="40%">
      <el-form ref="form" :model="form" status-icon label-width="100px">
        <el-form-item label="* 计划名称:" class="handle-input">
            <el-autocomplete
            :disabled="true"
            class="inline-input"
            v-model="select_plan"
            value-key="planName"
            :fetch-suggestions="querySearchCustomer"
            placeholder="请输入计划名称"
            @select="handleSelectCustomer"
          ></el-autocomplete>
        </el-form-item>
        <el-form-item label="* 投放名称:">
          <el-select
            v-model="form.putNAme"
            style="width:80%"
            @change="putAdPosition"
            :disabled="true"
          >
            <el-option
              v-for="item in putNameSelect"
              :key="item.id"
              :label="item.putName"
              :value="item.id"
            ></el-option>
          </el-select>
        </el-form-item>
        <!-- <el-form-item label="落地页地址:">
          <el-input v-model="form.landUrl" style="width:80%"></el-input>
        </el-form-item> -->
        <el-form-item label="* 创意名称:">
          <el-input v-model="form.entityName" style="width:80%"></el-input>
        </el-form-item>
        <!-- <el-form-item v-for="(item,index) in list" :key="index" :label="item.moduleName">
                      <div v-if="item.moduleType == 1">
                           <el-input v-model="form[item.moduleKey]" style="width:80%"></el-input>
                      </div>
                      <div v-if="item.moduleType == 2">
                      </div> 
                      <div v-if="item.moduleType == 3">
                      </div>     
        </el-form-item>-->
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
        <el-form-item label="* 视频:" v-if="map[4]">
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
        <el-form-item label="* 图片1:" v-if="map[5]">
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
        <el-button @click="editVisible = false">取 消</el-button>
        <el-button type="primary" @click="saveEdit" v-if="this.readonly !=1">确 定</el-button>
      </span>
    </el-dialog>

    <!-- 删除提示框 -->
    <el-dialog title="提示" :visible.sync="delVisible" width="300px" center>
      <div class="del-dialog-cnt">删除不可恢复，是否确定删除？</div>
      <span slot="footer" class="dialog-footer">
        <el-button @click="delVisible = false">取 消</el-button>
        <el-button type="primary" @click="deleteRow">确 定</el-button>
      </span>
    </el-dialog>


    <!-- 创意审核出框 -->
    <el-dialog title="创意审核" :visible.sync="template_dialog" width="60%">
      <el-form ref="form" :model="form" status-icon label-width="100px">
        <el-form-item label="创意名称:">{{entityName}}</el-form-item>
        <el-form-item label="广告位名称:">{{posName}}</el-form-item>
        <el-form-item label="落地页:">{{landUrl}}</el-form-item>
        <el-form-item label="投放时间段:">{{timeInterval}}</el-form-item>
        <el-form-item label="投放日期:">
          <!-- {{beginTime-endTime}} -->
          {{Time}}
        </el-form-item>
        <el-form-item label="媒体定向:">{{mediaName}}</el-form-item>
        <el-form-item label="广告主:">{{adverName}}</el-form-item>
        <el-form-item label="代理商:">{{agentName}}</el-form-item>

        <el-form-item label="物料地址:">{{entityUrl}}</el-form-item>
        <!-- <el-form-item label="物料预览:">
                    <div class="bg_img" v-for="(item,index) in list_url" :key="index">
                        <img :src="entityUrl_img" alt="" v-if="this.layout == 'jpg'||this.layout == 'png'||this.layout == 'jpeg'||this.layout == 'gif'">
                        <video :src="entityUrl_mp4" class="video" v-if="this.layout == 'mp4'"></video>
                    </div>
        </el-form-item>-->
        <el-form-item label="标题:" v-if="map[1]">
          <el-input v-model="map[1].moduleValue" style="width:80%"></el-input>
        </el-form-item>
        <el-form-item label="描述:" v-if="map[2]">
          <el-input v-model="map[2].moduleValue"></el-input>
        </el-form-item>
        <el-form-item label="头像:" v-if="map[3]">
          <!-- <el-input v-model="form.userPortrait" style="width:50%"></el-input> -->
          <a :href="map[3].moduleValue" target="_block">
            <img :src="map[3].moduleValue" class="bg_img">
          </a>
        </el-form-item>
        <el-form-item label="视频:" v-if="map[4]" target="_block">
          <!-- <el-input v-model="form.entityUrl"></el-input> -->
          <a :href="map[4].moduleValue" target="_blank">
            <video :src="map[4].moduleValue" class="video"></video>
          </a>
        </el-form-item>
        <el-form-item label="图片1:" v-if="map[5]">
          <!-- <el-input v-model="form.threadPic1"></el-input> -->
          <a :href="map[5].moduleValue" target="_blank">
            <img :src="map[5].moduleValue" class="bg_img">
          </a>
        </el-form-item>
        <el-form-item label="图片2:" v-if="map[6]">
          <!-- <el-input v-model="form.threadPic1"></el-input> -->
          <a :href="map[6].moduleValue" target="_blank">
            <img :src="map[6].moduleValue" class="bg_img">
          </a>
        </el-form-item>
        <el-form-item label="图片3:" v-if="map[7]">
          <a :href="map[7].moduleValue" target="_blank">
            <img :src="map[7].moduleValue" class="bg_img">
          </a>
        </el-form-item>
        <el-form-item label="图片4:" v-if="map[8]">
          <a :href="map[8].moduleValue" target="_blank">
            <img :src="map[8].moduleValue" class="bg_img">
          </a>
        </el-form-item>
        <el-form-item label="图片5:" v-if="map[9]">
          <a :href="map[9].moduleValue" target="_blank">
            <img :src="map[9].moduleValue" class="bg_img">
          </a>
        </el-form-item>
        <el-form-item label="图片6:" v-if="map[10]">
          <a :href="map[10].moduleValue" target="_blank">
            <img :src="map[10].moduleValue" class="bg_img">
          </a>
        </el-form-item>
        <el-form-item label="图片7:" v-if="map[11]">
          <a :href="map[11].moduleValue" target="_blank">
            <img :src="map[11].moduleValue" class="bg_img">
          </a>
        </el-form-item>
        <el-form-item label="图片8:" v-if="map[12]">
          <a :href="map[12].moduleValue" target="_blank">
            <img :src="map[12].moduleValue" class="bg_img">
          </a>
        </el-form-item>
        <el-form-item label="图片9:" v-if="map[13]">
          <a :href="map[13].moduleValue" target="_blank">
            <img :src="map[13].moduleValue" class="bg_img">
          </a>
        </el-form-item>
      </el-form>
      <el-tag type="success">媒体提审</el-tag>
      <div class="splice"></div>
      <el-table :data="media" border style="width: 100%" ref="multipleTable">
                    <!-- <el-table-column prop="id" label="媒体ID" align="center">
                    </el-table-column> -->
                    <el-table-column prop="mediaName" label="媒体名称" align="center">
                    </el-table-column>
                    <el-table-column prop="auditState" label="审核状态" align="center" :formatter=formatterAuditState>
                    </el-table-column>
                    <el-table-column prop="auditComments" label="审核备注" align="center">
                    </el-table-column>
                    <el-table-column label="行业类型" align="center">
                        <template slot-scope="scope">
                            <el-select v-model="scope.row.industryType" placeholder="请选择">
                                <el-option
                                    v-for="item in options"
                                    :key="item.id"
                                    :label="item.dicValue"
                                    :value="item.id">
                                </el-option>
                            </el-select>
                        </template>
                    </el-table-column>
                    <el-table-column label="操作" align="center" v-if="this.readonly !=1">
                        <template slot-scope="scope">
                            <el-button size="small" type="success" @click="commit_Audit(scope.$index, scope.row)">提交审核
                            </el-button>
                        </template>
                    </el-table-column>
                </el-table>
      <!-- <span class="footer">
        <el-button type="primary" @click="saveEditPass()">确 定(通过）</el-button>
        <el-button @click="saveEditReject()">取 消（拒绝）</el-button>
        <el-input v-model="auditComments" placeholder="请输入拒绝理由" style="width:40%"></el-input>
      </span> -->
    </el-dialog>


  </div>
</template>

<script>
import {
  planlist,
  orderPutupdate,
  entityadd,
  entityupdate,
  entitydelete,
  entityinfo
} from "@/api/Api.js";
import {
  entitypage,
  orderPutpages,
  putlist,
  adPositiongetModules,
  uploaduploadWithSize,
  orderPutlist,
  auditentityauditinfo,
  industryManage,
  entityauditAdx
} from "@/api/Api.js";

const uuid = new Map();
const putName = new Map();
let putNames = new Map();
export default {
  name: "basetable",
  data() {
    return {
      upload: uploaduploadWithSize,
      tableData: [],
      cur_page: 1,
      total: 1,
      ps: 10,
      select_plan:'',
      media: [],
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
      posName:'',
      industryType: '',
      entityName: "",
        imageUrl: "",
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
        adverName:'',
        mediaName:'',
        agentName:'',
        Time:'',
        timeInterval:'',
        landUrl:'',
        entityType:'',
      select_name: "",
      select_word: "",
      select_launch: "",
      del_list: [],
      is_search: false,
      editVisible: false,
      delVisible: false,
      newVisible: false,
      newaddAD: false,
      template_dialog: false,
      currentRow: null,
      idx: -1,
      runStatus: "",
      radio: "",
      form: {
          industryType: '',
        orderName: "",
        putNAme: "",
        landUrl: "",
        entityName: "",
        imageUrl: "",
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
        date: ""
      },
       options:[
        {
            value: '',
            label: ''
        }
      ],
      options_: [
         {
          value: 1,
          label: "开启"
        },
        {
          value: 0,
          label: "暂停"
        },
       
      ],
      PlanName: [
        {
          value: "",
          label: ""
        }
      ],
      putNameSelect: [
        {
          value: "",
          label: ""
        }
      ],
      value: "",
      readonly:'',
    };
  },
  created() {
    this.getList();
    this.getIndustry()
    this.readonly = this.$store.state.map.get(this.$store.state.currentPath);
    // this.querySearchCustomer(); 
    // this.querySearch();
  },

  methods: {
    Editinfo(index, row) {
      this.$router.push({
        path: "/datareport/ideartwo",
        query: { entId: row.id }
      });
    },
     // 提审
    Audit(index, row) {
      let item = row;
      let params = { id: item.id };
      this.id = item.id;
      auditentityauditinfo(params).then(res => {
        console.log(res)
        let list = res.data.result.moduleRelations;
        list.forEach(element => {
          // if(element.mid>=5) {
          // }
          this.map[element.mId] = element;
          this.form.putNAme = res.data.result.pid;
          this.form.landUrl = res.data.result.landUrl;
          this.form.entityName = res.data.result.entityName;
          this.form.threadTitle = res.data.result.threadTitle;
          this.form.threadContent = res.data.result.threadContent;
          this.form.entityUrl = res.data.result.entityUrl;
          this.form.userPortrait = res.data.result.userPortrait;
          this.form.threadPic1 = res.data.result.threadPic1;
          this.form.threadPic2 = res.data.result.threadPic2;
          this.form.threadPic3 = res.data.result.threadPic3;
          this.form.threadPic4 = res.data.result.threadPic4;
          this.form.threadPic5 = res.data.result.threadPic5;
          this.form.threadPic6 = res.data.result.threadPic6;
          this.form.threadPic7 = res.data.result.threadPic7;
          this.threadPic8 = res.data.result.threadPic8;
          this.form.threadPic9 = res.data.result.threadPic9;
        });

        this.entityName = res.data.result.entityName;
        this.entityType = res.data.result.mediaName;
        this.posName = res.data.result.posName;
        this.landUrl = res.data.result.landUrl;
        this.timeInterval = res.data.result.timeInterval;
        this.Time =
          res.data.result.beginTime.substring(0, 10) +
          "至" +
          res.data.result.endTime.substring(0, 10);
        this.mediaName = res.data.result.mediaName;
        this.adverName = res.data.result.adverName;
        this.agentName = res.data.result.agentName;
        this.entityUrl = res.data.result.entityUrl;
        // this.media = res.data.result
        let dates = [{
          mediaName:res.data.result.mediaName,
          auditState:res.data.result.auditState,
          auditComments:res.data.result.auditComments,
          objId:res.data.result.id,
          adxType:res.data.result.medId,
          industryType:res.data.result.industry
        }]
        this.getIndustry()
        this.media = dates
      

      });
      this.template_dialog = true;
    },
    // 提交审核
     commit_Audit(index, row) {
           let item = row
           console.log(item)
           if(!item.industryType){
             this.$message('行业不能为空')
             return;
           }
                let params = {
                    adxType: item.adxType,
                    objId: item.objId,
                    industry: item.industryType
                }
                console.log(params)
                entityauditAdx(params).then(res => {
                    let data = res.data;
                     if (data.code != "A000000") {
                        this.$message.error(data.message);
                        return;
                      }
                      this.$message.success("提交成功")
                      this.template_dialog = false;
                });
            },
    formatterTypesentityState(row, column) {
      if (row.entityState == 0) {
        return "无效";
      } else if (row.entityState == 1) {
        return "正常";
      }else if (row.entityState == 3) {
        return "待审核";
      }else if (row.entityState == 2) {
        return "审核未通过";
      }
    },
     formatterAuditState(row, column) {
                if (row.auditState == 1) {
                    return "审核通过";
                } else if (row.auditState == 2) {
                    return "审核未通过";
                } else if (row.auditState == 3) {
                    return "待审核";
                } else if (row.auditState == 7) {
                    return "审核拒绝";
                }
            },
     // 行业选项
      getIndustry() {
          industryManage().then(res => {
              let data = res.data;
              this.options = data.data
          });
      },
    // 开关
    changeStatus(val, row) {
      let params = {
        id: row.id,
        runState: row.runState
      };
      entityupdate(params).then(res => {
      let data = res.data;
        if (data.code != "A000000") {
          this.$message.error(data.message);
        }
        // this.getList();
      });
    },
    // 模糊投放名称
    querySearch(queryString, cb) {
      let params = {
        putName: this.select_launch,
        putType:19
      };
      putlist(params).then(res => {
        console.log(res);
        //let data = res.data;
        // console.log(data)
        // 调用 callback 返回建议列表的数据
        // this.putNameSelect = res.data.result.data
        res.data.result.forEach(element => {
          element.putName ? putName.set(element.putName, element.id) : '';
        });
        cb(res.data.result);
        if (res.data.code != "A000000") {
          this.$message.error(res.data.message);
        }
      });
    },
    handleSelect(item) {
      console.log(putName.get(this.select_launch));
      this.id = item.id;
    },
    // 模糊计划名称
    querySearchCustomer(queryString, cb) {
      let params = {
        planName: this.select_word
      };
      planlist(params).then(res => {
        console.log(res);
        //let data = res.data;
        // console.log(data)
        // 调用 callback 返回建议列表的数据
        this.planName = res.data.result;
        res.data.result.forEach(element => {
          uuid.set(element.name, element.id);  
        });
        cb(res.data.result);
        if (res.data.code != "A000000") {
          this.$message.error(res.data.message);
        }
      });
    },
    handleSelectCustomer(item) {
      // console.log(item);
      this.id = item.id;
      let params = {
        pid: this.id
      };
      putlist(params).then(res => {
        if (res.data.code != "A000000") {
          this.$message.error(res.data.message);
        }
        res.data.result.forEach(element => {
          putNames.set(element.id, element.adPosition);
        });
        this.putNameSelect = res.data.result;
        // this.adPosition = res.data.result.adPosition
      });
    },
    // 获取创意模板
    putAdPosition(row) {
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
      let adPositionId = putNames.get(row);
      let params = {
        // id:this.form.putNAme,
        adPositionId: adPositionId
      };
      adPositiongetModules(params).then(res => {
      let data = res.data;
        let list = data.data;
        if(list){ 
            list.forEach(element => {
              // if(element.mid>=5) {
              // }
              this.map[element.mId] = element;
              // this.map.set(element.mId,element);
            });
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
        // this.$forceUpdate()
        console.log(this.map)
         console.log(list)
        // this.list = list;
        // this.total = res.data.result.totalItemNum;
        // console.log(list)
      });
    },
    // 分页导航
    handleCurrentChange(val) {
         this.cur_page = val;
         let params = {
        entityName: this.select_name,
        pid:putName.get(this.select_launch),
        putType: 19,
        runState: this.value,
        cp: this.cur_page,
        ps: this.ps
      };
      console.log(params)
      entitypage(params).then(res => {
        this.tableData = res.data.result.data;
        this.total = res.data.result.totalItemNum;
        this.$forceUpdate()
      });
    },
    getList() {
      let params = {
         cp: this.cur_page,
         ps: this.ps,
         putType: 19 ,
        };
      entitypage(params).then(res => {
        console.log(res)
        let data = res.data
        this.tableData = data.data.data;
        this.total = data.data.totalItemNum;
        // this.cur_page = res.data.result.currentPathNum;
      });
    },
    // 搜索
    search() {
      this.$refs.pagination.lastEmittedPage = 1
      this.cur_page = 1;
      console.log(this.select_launch);
      console.log(putName);
      let params = {
        entityName: this.select_name,
        pid:putName.get(this.select_launch),
        putType: 19,
        runState: this.value,
        cp: this.cur_page,
        ps: this.ps
      };
      console.log(params)

      entitypage(params).then(res => {
        //let data = res.data;
        if (res.data.code != "A000000") {
          this.$message.error(res.data.message);
        }
        this.tableData = res.data.result.data;
        this.total = res.data.result.totalItemNum;

      });
    },
    // 新建
    create() {
      this.newVisible = true;
      this.form = {};
      this.select_word = "";
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
    },
    saveNews() {
      if (!this.form.putNAme) {
        this.$message.error("计划名称不能为空");
        return;
      }
      if (!this.form.entityName) {
        this.$message.error("投放名称不能为空");
        return;
      }
      if (!this.form.entityName) {
        this.$message.error("创意名称不能为空");
        return;
      }
   
        let params = {
          // id:this.id,
          putType: 19,
          pid: this.form.putNAme,
          // landUrl: this.form.landUrl,
          entityName: this.form.entityName,
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
        console.log(params)
        entityadd(params).then(res => {
          console.log(res);
          //let data = res;
          if (res.data.code != "A000000") {
            this.$message.error(res.data.message);
            return;
          }
          this.getList();
          this.newVisible = false;
        });
      // });
    },
    // 编辑
    handleEdit(index, row) {
      this.row = row;
      this.select_plan = row.planName
      let params = {
        id: row.id
      };
      entityinfo(params).then(res => {
        console.log(res);
        if (res.data.code != "A000000") {
          this.$message.error(res.data.message);
        }
        // let item = { id: res.data.result.pid };
        let params = {
          oid: row.pid
        };
        putlist(params).then(res => {
          if (res.data.code != "A000000") {
            this.$message.error(res.data.message);
          }
          // res.data.result.forEach(element => {
          //   putNames.set(element.planId, element.adPosition);
        
          // });
          this.putNameSelect = res.data.result;
          console.log(this.putNameSelect)
          // this.adPosition = res.data.result.adPosition
        });

        let list = res.data.result.moduleRelations;
        list.forEach(element => {
          // if(element.mid>=5) {
          // }
          this.map[element.mId] = element;
          this.form.putNAme = res.data.result.pid;
          // this.form.landUrl = res.data.result.landUrl;
          this.form.entityName = res.data.result.entityName;
          this.form.threadTitle = res.data.result.threadTitle;
          this.form.threadContent = res.data.result.threadContent;
          this.form.entityUrl = res.data.result.entityUrl;
          this.form.userPortrait = res.data.result.userPortrait;
          this.form.threadPic1 = res.data.result.threadPic1;
          this.form.threadPic2 = res.data.result.threadPic2;
          this.form.threadPic3 = res.data.result.threadPic3;
          this.form.threadPic4 = res.data.result.threadPic4;
          this.form.threadPic5 = res.data.result.threadPic5;
          this.form.threadPic6 = res.data.result.threadPic6;
          this.form.threadPic7 = res.data.result.threadPic7;
          this.form.threadPic8 = res.data.result.threadPic8;
          this.form.threadPic9 = res.data.result.threadPic9;
        });
        this.editVisible = true;
      });
    },
    saveEdit() {
        if (!this.form.entityName) {
        this.$message.error("创意名称不能为空");
        return;
      }
      let params = {
        putType: 19,
        // select_word:item.orderName,
        // putName:item.putNAme,
        id: this.row.id,
        pid: this.form.putNAme,
        // landUrl: this.form.landUrl,
        entityName: this.form.entityName,
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
        this.editVisible = false;
      });
    },
    // 删除
    handleDelete(index, row) {
      console.log(row);
      this.rows = row;
      this.delVisible = true;
    },
    // 确定删除
    deleteRow() {
      let params = {
        putType: 19,
        id: this.rows.id
      };
      entitydelete(params).then(res => {
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
    handleRemove(file, fileList) {
      // console.log(file, fileList);
    },
    handlePreview(file) {
      console.log(file);
    },
    // 头像
    handleAvatarSuccessHead(res, file) {
      let data = res;
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
      let data = res;
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
      let data = res;
      if (data.code != "A000000") {
        this.$message.error(data.message);
        return;
      } else {
        this.form.threadPic1 = data.data.url;
      }
      this.$forceUpdate();
    },
    handleAvatarSuccessPicture2(res, file) {
      let data = res;
      if (data.code != "A000000") {
        this.$message.error(data.message);
        return;
      } else {
        this.form.threadPic2 = data.data.url;
      }
      this.$forceUpdate();
    },
    handleAvatarSuccessPicture3(res, file) {
      let data = res;
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
      let data = res;
      if (data.code != "A000000") {
        this.$message.error(data.message);
        return;
      } else {
        this.form.threadPic5 = data.data.url;
      }
      this.$forceUpdate();
    },
    handleAvatarSuccessPicture6(res, file) {
      let data = res;
      if (data.code != "A000000") {
        this.$message.error(data.message);
        return;
      } else {
        this.form.threadPic6 = data.data.url;
      }
      this.$forceUpdate();
    },
    handleAvatarSuccessPicture7(res, file) {
      let data = res;
      if (data.code != "A000000") {
        this.$message.error(data.message);
        return;
      } else {
        this.form.threadPic7 = data.data.url;
      }
      this.$forceUpdate();
    },
    handleAvatarSuccessPicture8(res, file) {
      let data = res;
      if (data.code != "A000000") {
        this.$message.error(data.message);
        return;
      } else {
        this.form.threadPic8 = data.data.url;
      }
      this.$forceUpdate();
    },
    handleAvatarSuccessPicture9(res, file) {
      let data = res;
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
.r_div {
  margin-left: 80px;
}
.border {
  border: 2px dashed #26a580;
  padding: 10px;
}
.div {
  margin-top: 10px;
}
.td_color {
  margin-left: 47%;
}
.diamonds {
  display: inline-block;
  width: 20px;
  height: 20px;
  border: 1px solid silver;
}
.bg_color {
  background: silver;
}
.mr10 {
  width: 200px;
}
.bg_img {
  width: 450px;
  height: 300px;
  /* background: skyblue */
}
 .splice {
        height: 2px;
        background: silver;
        margin-top: 10px;
        margin-bottom: 10px;
    }
</style>
<style>
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




