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
                    v-model="select_word1"
                    value-key="name"
                    :fetch-suggestions="querySearchCustomer"
                    placeholder="请输入订单名称"
                    @select="handleSelectCustomer"
                  ></el-autocomplete> -->
                <el-select v-model="value" placeholder="状态"  class="handle-input mr10">
                  <el-option
                    v-for="item in options_"
                    :key="item.value"
                    :label="item.label"
                    :value="item.value">
                  </el-option>
                </el-select>
                <el-button type="primary" icon="search" @click="search">搜索</el-button>
                <el-button type="primary" icon="search" @click="create" v-if="this.readonly !=1">新建创意</el-button>
  
            </div>
            <el-table :data="tableData" border style="width: 100%" ref="multipleTable">
                <el-table-column prop="id" label="创意ID" align="center">
                </el-table-column>
                <el-table-column prop="entityName" label="创意名称" align="center">
                </el-table-column>
                <el-table-column prop="putName" label="投放名称" align="center">
                </el-table-column>
                <el-table-column prop="orderName" label="订单名称" align="center">
                </el-table-column>
                <el-table-column prop="adverName" label="客户" align="center">
                </el-table-column>
                <el-table-column prop="entityState" label="创意状态" align="center" :formatter=formatterTypesentityState>                 
                </el-table-column>
                <el-table-column  label="运行状态" align="center" v-if="this.readonly !=1">
                   <template slot-scope="scope">
                     <el-switch v-model="scope.row.runState" :active-value=1 :inactive-value=0   @change="changeStatus($event,scope.row)"></el-switch>
                  </template>
                </el-table-column>
                <el-table-column label="操作"  width="220" align="center">
                    <template slot-scope="scope">
                        <el-button size="small" type="success" @click="handleEdit(scope.$index, scope.row)">编辑</el-button>
                        <el-button size="small" type="danger" @click="handleDelete(scope.$index, scope.row)">删除</el-button>
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
        <el-dialog title="新建创意" :visible.sync="newVisible" width="50%">
             <el-form ref="form" :model="form"   status-icon label-width="120px" :label-position="labelPosition">
                <el-form-item label="* 订单名称:" class="handle-input">
                         <el-autocomplete
                          class="inline-input"
                          v-model="select_word"
                          value-key="name"
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
                            :value="item.id">
                          </el-option>
                        </el-select>
                 </el-form-item>
                 <el-form-item label="* 广告位名称:">
                       <el-select v-model="form.ADname" style="width:80%" @change="putADtemplate">
                          <el-option
                            v-for="item in optionName"
                            :key="item.id"
                            :label="item.name"
                            :value="item.id">
                          </el-option>
                        </el-select>
                 </el-form-item>
                 <el-form-item label="* 模板名称:" v-if="this.indexID == 2">
                       <el-select v-model="form.TemplatesById" style="width:80%"  @change="putADtemplateTwo">
                          <el-option
                            v-for="item in Templates"
                            :key="item.id"
                            :label="item.name"
                            :value="item.id">
                          </el-option>
                        </el-select>
                 </el-form-item>
                 <el-form-item label="* 行业:">
                    <el-cascader
                        :change-on-select="true"
                        :props="defaultParams"
                        :options="options"
                        v-model="selectedOptions"
                        :clearable="true"
                    ></el-cascader>
                 </el-form-item>
                 <el-form-item label="* 创意名称:">
                          <el-input v-model="form.entityName" style="width:80%"></el-input>
                 </el-form-item>
                 <el-form-item label="* 上传素材:">
                          <!-- <el-radio v-model="form.readonly" :label="1">普通素材</el-radio>
                          <el-radio v-model="form.readonly" :label="0">JS代码</el-radio> -->
                          <div>
                                  <el-form-item label="标题:" v-if="map[1]">
                                            <el-input v-model="form.threadTitle" style="width:80%" :maxlength="map[1].wordLimit" :placeholder="map[1].placeHolderStr"></el-input>
                                  </el-form-item>
                                  <el-form-item label="描述:" v-if="map[2]">
                                            <el-input v-model="form.threadContent" style="width:80%" :maxlength="map[2].wordLimit" :placeholder="map[2].placeHolderStr"></el-input>
                                  </el-form-item>
                                  <el-form-item label="头像:" v-if="map[3]">
                                          <el-input v-model="form.userPortrait" style="width:50%" :placeholder="map[3].placeHolderStr"></el-input>
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
                                              :data="{type:5,s:this.map[4].duration}"
                                              :action="upload"
                                              name="myFile"
                                              :on-success="handleAvatarSuccessVideo"
                                              >
                                            <el-button size="small" type="primary">点击上传</el-button>
                                            </el-upload>
                                  </el-form-item>
                                  <el-form-item label="* 图片1:" v-if="map[5]">
                                            <el-input v-model="form.threadPic1" :placeholder="map[5].placeHolderStr" style="width:50%"></el-input>
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
                                            <el-input v-model="form.threadPic2" :placeholder="map[6].placeHolderStr" style="width:50%"></el-input>
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
                                            <el-input v-model="form.threadPic3" :placeholder="map[7].placeHolderStr" style="width:50%"></el-input>
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
                                  <el-form-item label="按钮文案:" v-if="map[8]">
                                            <el-input v-model="form.threadPic4" :placeholder="map[8].placeHolderStr" style="width:50%"></el-input>
                                            <!-- <el-upload
                                              class="upload-demo"
                                              :data="{type:1,w:this.map[8].width,h:this.map[8].height}"
                                              :action="upload"
                                              name="myFile"
                                              :on-success="handleAvatarSuccessPicture4"
                                              >
                                            <el-button size="small" type="primary">点击上传</el-button>
                                            </el-upload> -->
                                  </el-form-item>
                                  <el-form-item label="图片5:" v-if="map[9]">
                                            <el-input v-model="form.threadPic5" :placeholder="map[9].placeHolderStr" style="width:50%"></el-input>
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
                                            <el-input v-model="form.threadPic6" :placeholder="map[10].placeHolderStr" style="width:50%"></el-input>
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
                                            <el-input v-model="form.threadPic7" :placeholder="map[11].placeHolderStr" style="width:50%"></el-input>
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
                                            <el-input v-model="form.threadPic8" :placeholder="map[12].placeHolderStr" style="width:50%"></el-input>
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
                                            <el-input v-model="form.threadPic9" :placeholder="map[13].placeHolderStr" style="width:50%"></el-input>
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
                          </div>
                          <div>
                              <el-input v-if="map[14]" type="textarea" :rows="9" v-model="form.dynamicCode" style="width:80%"></el-input>
                          </div>
                 </el-form-item>
               
             </el-form> 
            <span slot="footer" class="dialog-footer">
                    <el-button @click="newVisible = false">取 消</el-button>
                    <el-button type="primary" @click="saveNews" v-if="this.readonly !=1">确 定</el-button>
              </span>
        </el-dialog>

          <!-- 编辑弹出框 -->
        <el-dialog title="编辑" :visible.sync="editVisible" width="40%">
               <el-form ref="form" :model="form"   status-icon label-width="120px" :label-position="labelPosition">
                <el-form-item label="* 订单名称:" class="handle-input" >
                         <el-autocomplete
                          disabled
                          class="inline-input"
                          v-model="select_word"
                          value-key="name"
                          :fetch-suggestions="querySearchCustomer"
                          placeholder="请输入订单名称"
                          @select="handleSelectCustomer"
                        ></el-autocomplete>
                       <!-- <el-select v-model="select_word" style="width:80%">
                          <el-option
                            v-for="item in PlanName"
                            :key="item.id"
                            :label="item.name"
                            :value="item.id">
                          </el-option>
                        </el-select> -->
                 </el-form-item>
                 <el-form-item label="* 投放名称:">
                       <el-select v-model="form.putNAme" style="width:80%" @change="putAdPosition" disabled>
                          <el-option
                            v-for="item in putNameSelect"
                            :key="item.id"
                            :label="item.putName"
                            :value="item.id">
                          </el-option>
                        </el-select>
                 </el-form-item>
                 <el-form-item label="* 广告位名称:">
                       <el-select v-model="form.ADname" style="width:80%" @change="putADtemplate" disabled>
                          <el-option
                            v-for="item in optionName"
                            :key="item.id"
                            :label="item.name"
                            :value="item.id">
                          </el-option>
                        </el-select>
                 </el-form-item>
                 <el-form-item label="* 模板名称:" v-if="this.indexID == 2">
                       <el-select v-model="form.TemplatesById" style="width:80%"  @change="putADtemplateTwo" disabled>
                          <el-option
                            v-for="item in Templates"
                            :key="item.id"
                            :label="item.name"
                            :value="item.id">
                          </el-option>
                        </el-select>
                 </el-form-item>
                 <el-form-item label="* 行业:">
                    <el-cascader
                        :change-on-select="true"
                        :props="defaultParams"
                        :options="options"
                        v-model="selectedOptions"
                        :clearable="true"
                    ></el-cascader>
                 </el-form-item>
                 <el-form-item label="* 创意名称:">
                          <el-input v-model="form.entityName" style="width:80%"></el-input>
                 </el-form-item>
                 <el-form-item label="* 上传素材:">
                          <!-- <el-radio v-model="form.readonly" :label="1">普通素材</el-radio>
                          <el-radio v-model="form.readonly" :label="0">JS代码</el-radio> -->
                          <div>
                                  <el-form-item label="标题:" v-if="map[1]">
                                            <el-input v-model="form.threadTitle" style="width:80%" :maxlength="map[1].wordLimit" :placeholder="map[1].placeHolderStr"></el-input>
                                  </el-form-item>
                                  <el-form-item label="描述:" v-if="map[2]">
                                            <el-input v-model="form.threadContent" style="width:80%" :maxlength="map[2].wordLimit" :placeholder="map[2].placeHolderStr"></el-input>
                                  </el-form-item>
                                  <el-form-item label="头像:" v-if="map[3]">
                                          <el-input v-model="form.userPortrait" style="width:50%" :placeholder="map[3].placeHolderStr"></el-input>
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
                                              :data="{type:5,s:this.map[4].duration}"
                                              :action="upload"
                                              name="myFile"
                                              :on-success="handleAvatarSuccessVideo"
                                              >
                                            <el-button size="small" type="primary">点击上传</el-button>
                                            </el-upload>
                                  </el-form-item>
                                  <el-form-item label="* 图片1:" v-if="map[5]">
                                            <el-input v-model="form.threadPic1" :placeholder="map[5].placeHolderStr" style="width:50%"></el-input>
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
                                            <el-input v-model="form.threadPic2" :placeholder="map[6].placeHolderStr" style="width:50%"></el-input>
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
                                            <el-input v-model="form.threadPic3" :placeholder="map[7].placeHolderStr" style="width:50%"></el-input>
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
                                  <el-form-item label="按钮文案:" v-if="map[8]">
                                            <el-input v-model="form.threadPic4" :placeholder="map[8].placeHolderStr" style="width:50%"></el-input>
                                            <!-- <el-upload
                                              class="upload-demo"
                                              :data="{type:1,w:this.map[8].width,h:this.map[8].height}"
                                              :action="upload"
                                              name="myFile"
                                              :on-success="handleAvatarSuccessPicture4"
                                              >
                                            <el-button size="small" type="primary">点击上传</el-button>
                                            </el-upload> -->
                                  </el-form-item>
                                  <el-form-item label="图片5:" v-if="map[9]">
                                            <el-input v-model="form.threadPic5" :placeholder="map[9].placeHolderStr" style="width:50%"></el-input>
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
                                            <el-input v-model="form.threadPic6" :placeholder="map[10].placeHolderStr" style="width:50%"></el-input>
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
                                            <el-input v-model="form.threadPic7" :placeholder="map[11].placeHolderStr" style="width:50%"></el-input>
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
                                            <el-input v-model="form.threadPic8" :placeholder="map[12].placeHolderStr" style="width:50%"></el-input>
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
                                            <el-input v-model="form.threadPic9" :placeholder="map[13].placeHolderStr" style="width:50%"></el-input>
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
                                  <el-form-item  v-if="map[14]">
                                            <el-input v-if="map[14]" type="textarea" :rows="9" v-model="form.dynamicCode" style="width:80%"></el-input>
                                  </el-form-item>
                          </div>
                          <!-- <div>
                              <el-input v-if="map[14]" type="textarea" :rows="9" v-model="form.dynamicCode" style="width:80%"></el-input>
                          </div> -->
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
                <el-button type="primary" @click="deleteRow" v-if="this.readonly !=1">确 定</el-button>
            </span>
        </el-dialog>

    </div>
</template>

<script>
import { orderslist,orderPutupdate,entityadd,entityupdate,entitydelete,entityinfo} from "@/api/Api.js";
import { entitypage,orderPutpages,orderPutlist,adPositiongetModules,uploaduploadWithSize,
orderPutpositions,industrysselect,adPositiongetTemplatesById,adPositiongetModulesByTemplateId
} from "@/api/Api.js";

const uuid = new Map
const putName = new Map
let putNames = new Map();
const urlId =new Map()
var ADtype = new Map()
export default {
  name: "basetable",
  data() {
    return {
      indexID:1,
      labelPosition: 'left',
      options:[],
      selectedOptions:[],
      defaultParams: {
         label: 'name',
         value: 'id',
         children: 'children'
      },
      upload: uploaduploadWithSize,
      tableData: [],
      cur_page: 1,
      total: 1,
      ps:10,
      map: {1:null,2:null,3:null,4:null,5:null,6:null,7:null,8:null,9:null,10:null,11:null,12:null,13:null,14:null},
      select_name: "",
      select_word: "",
      select_launch:'',
      del_list: [],
      is_search: false,
      editVisible: false,
      delVisible: false,
      newVisible: false,
      newaddAD: false,
      template_dialog: false,
      currentRow: null,
      idx: -1,
      runStatus:'',
      radio:'',
      // select_word1:'',
      form: {
        readonly:'',
        orderName:'',
        putNAme:null,
        landUrl:'',
        entityName:'',
        imageUrl:'',
        threadTitle:'',
        threadContent:'',
        entityUrl:'',
        userPortrait:'',
        threadPic1:'',
        threadPic2:'',
        threadPic3:'',
        threadPic4:'', 
        threadPic5:'', 
        threadPic6:'', 
        threadPic7:'', 
        threadPic8:'', 
        threadPic9:'', 
        dynamicCode:'',
        name: "",
        date: "",
        ADname:'',
        TemplatesById:'',
      },
      options_:[
        {
          value: '',
          label: "全部状态"
        },
        {
          value: 1,
          label: '开启'
        },
        {
          value: 0,
          label: '暂停'
        },
       
      ],
      PlanName: [
        {
          value: '',
          label: ''
        },
      ],
      putNameSelect: [
        {
          value: '',
          label: ''
        },
      ],
      optionName: [
        {
          value: '',
          label: ''
        },
      ],
      Templates: [
        {
          value: '',
          label: ''
        },
      ],
      value:'',
      readonly:''
    };
  },
  created() {
      this.readonly = this.$store.state.map.get(this.$store.state.currentPath);
      this.getList();
      this.getIndustry();
      this.putADtemplateName();
      this.putADtemplateTwo()
      // this.querySearchCustomer();
      // this.querySearch();
      // this.querySearchplan()
  },

  methods: {
     Editinfo(index, row) {
      this.$router.push({
        path: "/datareport/idearone",
        query: { entId: row.id }
      });
    },
      
      formatterTypesentityState(row, column) {
            if(row.entityState == 2) {
                return "人工审核未通过";
            } else if(row.entityState == 3){
                return "人工待审核";
            }else if(row.entityState == 13){
                return "机审待审核";
            }else if(row.entityState == 14){
                return "机审未通过";
            }else if(row.entityState == 15){
                return "黑名单";
            }else if(row.entityState == 16){
                return "创意待修改";
            }
            else if(row.entityState == 1){
                return "正常";
            }
        },
            // 开关
    changeStatus(val, row) {
      console.log(row)
        let params = {
            id: row.id,
            runState: row.runState,
        };
        entityupdate(params).then(res => {
          let data = res.data;
            if (data.code != 'A000000') {
                this.$message.error(data.message);
                return;
            }
            // this.getList();
        });
    },
          // 模糊投放名称
     querySearch(queryString, cb){
   
        let params = {
          putName :this.select_launch,
          }
         orderPutlist(params).then(res=>{
               console.log(res)
      //let data = res.data;
          // console.log(data)
          // 调用 callback 返回建议列表的数据
          // this.putNameSelect = res.data.result.data
          let  data =res.data
        data.data.forEach(element => {
            putName.set(element.putName,element.id)
        });
        cb(data.data);
        if(data.code != 'A000000') {
          this.$message.error(data.message);
            } 
        });
    },
    handleSelect(item){
      // console.log(item);
      this.id = item.id
    },
      // 模糊订单名称
     querySearchCustomer(queryString, cb){
        let params = {
          name :this.select_word,
          }
         orderslist(params).then(res=>{
           console.log(res)
           let data = res.data;
          // console.log(data)
          // 调用 callback 返回建议列表的数据
          this.PlanName = data.data
         data.data.forEach(element => {
            uuid.set(element.name,element.id)
        });
        cb(data.data);
        if(data.code != "A000000") {
          this.$message.error(data.message);
            } 
        });
    },
    handleSelectCustomer(item){
      this.form.putNAme = ''
       this.id = item.id
       let params = {
              oid :this.id,
          }     
         orderPutlist(params).then(res=>{
           console.log(res)
          if(res.data.code != 'A000000') {
                this.$message.error(res.data.message);
            } 
            res.data.data.forEach(element => {
              putNames.set(element.id,element.adPosition);
              urlId.set(element.id,element.landUrl)
            });
          this.putNameSelect = res.data.data
          console.log(res.data.data)
          // this.adPosition = res.data.result.adPosition
       
        });
    },
      // 获取行业
    getIndustry(){
        industrysselect().then(res=>{
              console.log(res)
              this.options = this.getTreeData(res.data.data);
              this.options.splice(0,1)
          });
    },
    // 递归
     getTreeData(data){
                // 循环遍历json数据
                for(var i=0;i<data.length;i++){
                    
                    if(data[i].children.length<1){
                        // children若为空数组，则将children设为undefined
                        data[i].children=undefined;
                    }else {
                        // children若不为空数组，则继续 递归调用 本方法
                        this.getTreeData(data[i].children);
                    }
                }
                return data;
     },
     // 获取 广告位 
    putAdPosition(row){
      this.form.ADname =''
      this.form.TemplatesById =''
      this.map = {1:null,2:null,3:null,4:null,5:null,6:null,7:null,8:null,9:null,10:null,11:null,12:null,13:null,14:null};
        let params = {
              id:this.form.putNAme
        }
        orderPutpositions(params).then(res=>{
              console.log(res)
              let data = res.data
              this.optionName = data.data
              data.data.forEach(element => {
                  ADtype.set(element.id,element.type)
              });
          });
    },
        // 获取 广告位模板列表
    putADtemplateName(){
       console.log(this.form.putNAme);
        let params = {
              id:this.form.ADname
        }
        adPositiongetTemplatesById(params).then(res=>{
              console.log(res)
              let data = res.data
              this.Templates = data.data 
          });
    },
      // 获取创意模板
    putADtemplate(row){
            this.form.TemplatesById =''
            this.indexID = 1
            this.map = {1:null,2:null,3:null,4:null,5:null,6:null,7:null,8:null,9:null,10:null,11:null,12:null,13:null,14:null};
            // 判断信息流,加载模板
            if(ADtype.get(this.form.ADname) == 7 || ADtype.get(this.form.ADname) == 217){
                this.indexID = 2
                this.putADtemplateName()
            }else{
                this.indexID = 1
                let params ={
                    adPositionId:this.form.ADname
                  }
            adPositiongetModules(params).then(res=>{ 
                console.log(res) 
                let data = res.data
                let list = data.data; 
                list.forEach(element => {
                  console.log(element)
                  this.map[element.mId] = element;
                  // if(ADtype.get(this.form.ADname) == 217){
                  //     this.form.dynamicCode = element.code
                  //   }
                  });         
                });
            }
    },
    putADtemplateTwo(){
               this.map = {1:null,2:null,3:null,4:null,5:null,6:null,7:null,8:null,9:null,10:null,11:null,12:null,13:null,14:null};
               let params ={
                    templateId:this.form.TemplatesById
                  }
            adPositiongetModulesByTemplateId(params).then(res=>{ 
                console.log(res) 
                let data = res.data
                let list = data.data; 
                list.forEach(element => {
                  console.log(element)
                  this.map[element.mId] = element;
                  if(ADtype.get(this.form.ADname) == 217){
                      this.form.dynamicCode = element.code
                    }
                  });         
                });
    },
    // 分页导航
    handleCurrentChange(val) {
      this.cur_page = val;
        let params = {
          entityName:this.select_name,
          putAllId:putName.get(this.select_launch),
          entityState:this.value,
          cp:this.cur_page,
          ps:this.ps
          }
          entitypage(params).then(res=>{
             let data = res.data;     
             this.tableData = data.data.data
             this.total = data.data.totalItemNum;
          });
    },
     getList() {
      let params = {cp:this.cur_page,ps:this.ps}
        entitypage(params).then(res=>{  
          let  data = res.data
          this.tableData = data.data.data
          this.total = data.data.totalItemNum;
    
          });
    },
    // 搜索
    search() {
      this.$refs.pagination.lastEmittedPage = 1
      this.cur_page = 1;
     let params = {
       entityName:this.select_name,
       putAllId:putName.get(this.select_launch),
       entityState:this.value,
       cp:this.cur_page,
       ps:this.ps
       }
      entitypage(params).then(res=>{
        let data = res.data;     
       this.tableData = data.data.data
       this.total = data.data.totalItemNum;
      // console.log(res)
      });
    },
    // 新建
    create() {
      this.newVisible = true;
      this.form = {};
      this.select_word = '';
      this.form = {}
      this.selectedOptions = []
      this.map = {1:null,2:null,3:null,4:null,5:null,6:null,7:null,8:null,9:null,10:null,11:null,12:null,13:null,14:null};

    },
    saveNews() {
      // if(!this.form.putNAme) {
      //   this.$message.error("请选择投放");
      //   return;
      // }
      // if(!this.form.entityName) {
      //   this.$message.error("创意名称不能为空");
      //   return;
      // }
      console.log(ADtype.get(this.form.ADname))
      if(ADtype.get(this.form.ADname) == 6){
          if(this.form.entityUrl!= undefined && this.form.threadPic1 != undefined){
                 this.$message.error("开屏幕广告位只能填写一种素材");
                  return;
          }
      }
           let params= {
          // id:this.id,
          putType: 17,
          putAllId:this.form.putNAme,
          adPosition:this.form.ADname,
          entityName:this.form.entityName,
          industry:this.selectedOptions.join(','),
          templateId:this.form.TemplatesById,

          threadTitle:this.form.threadTitle,
          threadContent:this.form.threadContent,
          entityUrl:this.form.entityUrl,
          userPortrait:this.form.userPortrait,
          threadPic1:this.form.threadPic1,
          threadPic2:this.form.threadPic2,
          threadPic3:this.form.threadPic3,
          buttonText:this.form.threadPic4, 
          threadPic5:this.form.threadPic5, 
          threadPic6:this.form.threadPic6, 
          threadPic7:this.form.threadPic7, 
          threadPic8:this.form.threadPic8, 
          threadPic9:this.form.threadPic9, 
          dynamicCode:this.form.dynamicCode
      }
      entityadd(params).then(res => {
         console.log(res)
          //let data = res;
          if (res.data.code != 'A000000') {
              this.$message.error(res.data.message);
              return;
          }
          this.getList() 
          this.newVisible = false;            
        });
     
    },
  // 编辑
    handleEdit(index, row) {
      this.map =  {1:null,2:null,3:null,4:null,5:null,6:null,7:null,8:null,9:null,10:null,11:null,12:null,13:null,14:null},
      this.row = row
      let params = {
          id:row.id
      } 
       entityinfo(params).then(res=>{
            console.log(res)
            let data = res.data
            if (data.code != 'A000000') {
                this.$message.error(data.message);
            }
         
              this.form.putNAme = data.data.putAllId;
              console.log(this.form.putNAme);
                let par = {
                        id:this.form.putNAme
                  }
                  orderPutpositions(par).then(res=>{
                        console.log(res)
                        let data = res.data
                        this.optionName = data.data
                        data.data.forEach(element => {
                            ADtype.set(element.id,element.type)
                        });
                    });
              
              this.form.ADname = data.data.adPosition;
              let list = data.data.moduleRelations; 
              // 模板名称
              this.putADtemplateName()
              console.log(data.data.templateId )
              if(data.data.templateId != null ){
                    this.indexID = 2
                    this.form.TemplatesById = data.data.templateId;
              }else{
                   this.indexID = 1
              }
                list.forEach(element => {
                 
                this.map[element.mId] = element;
                this.select_word = data.data.orderName       
                // let item ={id:data.data.orderId}
                // this.handleSelectCustomer(item);
                   let param = {
                            oid :data.data.orderId,
                        }     
                      orderPutlist(param).then(res=>{
                        console.log(res)
                        if(res.data.code != 'A000000') {
                              this.$message.error(res.data.message);
                          } 
                          res.data.data.forEach(element => {
                            putNames.set(element.id,element.adPosition);
                            urlId.set(element.id,element.landUrl)
                          });
                        this.putNameSelect = res.data.data
                        console.log(res.data.data)
                        // this.adPosition = res.data.result.adPosition
                    
                      });

                var number 
                this.selectedOptions = data.data.industry.split(',');
                number =  this.selectedOptions.map(Number)
                this.selectedOptions = number

                this.form.entityName = data.data.entityName;
                this.form.threadTitle= data.data.threadTitle;
                this.form.threadContent= data.data.threadContent;
                this.form.entityUrl=data.data.entityUrl;
                this.form.userPortrait = data.data.userPortrait;
                this.form.threadPic1=data.data.threadPic1;
                this.form.threadPic2=data.data.threadPic2;
                this.form.threadPic3=data.data.threadPic3;
                this.form.threadPic4=data.data.buttonText;
                this.form.threadPic5=data.data.threadPic5;
                this.form.threadPic6=data.data.threadPic6;
                this.form.threadPic7=data.data.threadPic7;
                this.threadPic8=data.data.threadPic8;
                this.form.threadPic9=data.data.threadPic9;
                this.form.dynamicCode=data.data.dynamicCode;
              });
                
               this.editVisible = true;
        });  
    },
    saveEdit() {
      //   let reg = /(http|ftp|https):\/\/[\w\-_]+(\.[\w\-_]+)+([\w\-\.,@?^=%&:/~\+#]*[\w\-\@?^=%&/~\+#])?/
      //  if(!reg.test(this.form.landUrl)){
      //   this.$message('请输入正确的落地页链接')
      //   return;
      // }
      if(!this.form.entityName) {
        this.$message.error("创意名称不能为空");
        return;
      }
       let par = {
         id:this.form.putNAme,
         landUrl:this.form.landUrl,
      }
        //  orderPutupdate(par).then(res => {
            //  console.log(res) 
        // let data = res.data;
          //  console.log(data.data.data[0].tagList)
          // if (data.code != 'A000000') {
          //     this.$message.error(data.message);
          //     return
          // }
             let params = {
            putType: 17,
            // select_word:item.orderName,
            // putName:item.putNAme,
            id:this.row.id,
            adPosition:this.form.ADname,
            putAllId:this.form.putNAme,
            entityName:this.form.entityName,
            industry:this.selectedOptions.join(','),
            threadTitle:this.form.threadTitle,
            threadContent:this.form.threadContent,
            entityUrl:this.form.entityUrl,
            userPortrait:this.form.userPortrait,
            threadPic1:this.form.threadPic1,
            threadPic2:this.form.threadPic2,
            threadPic3:this.form.threadPic3,
            buttonText:this.form.threadPic4, 
            threadPic5:this.form.threadPic5, 
            threadPic6:this.form.threadPic6, 
            threadPic7:this.form.threadPic7, 
            threadPic8:this.form.threadPic8, 
            threadPic9:this.form.threadPic9,  
            dynamicCode:this.form.dynamicCode,
          } 
          entityupdate(params).then(res=>{
            console.log(res)
            //let data = res;
            if (res.data.code != 'A000000') {
                this.$message.error(res.data.message);
            }
            this.search() 
            this.editVisible = false;
        }); 
      // });
       
      
      },
      // 删除
    handleDelete(index, row) {
      console.log(row)
      this.rows = row;
      this.delVisible = true;
    },
     // 确定删除
    deleteRow() {
        let params = {
            id:this.rows.id
        }
        entitydelete(params).then(res=>{
            console.log(res)
            //let data = res;
            if (res.data.code != 'A000000') {
                this.$message.error(res.data.message);
                return
            }else{
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
           let data = JSON.parse(res.data) ;
          if (data.code != 'A000000') {
              this.$message.error(data.message);
              return;
          }else {
              this.form.userPortrait = data.data.url
          }
          this.$forceUpdate() 
      },
      // 视频
      handleAvatarSuccessVideo(res, file){
       let data = JSON.parse(res.data) ;
          if (data.code != 'A000000') {
              this.$message.error(data.message);
              return;
          }else {
              this.form.entityUrl = data.data.url
          }
          this.$forceUpdate() 
      },
      // 图片
      handleAvatarSuccessPicture1(res, file){
        console.log(res)
           let data = JSON.parse(res.data) ;
          if (data.code != 'A000000') {
              this.$message.error(data.message);
              return;
          }else {
              this.form.threadPic1 = data.data.url
          }
          this.$forceUpdate() 
      },
      handleAvatarSuccessPicture2(res, file){
             let data = JSON.parse(res.data) ;
          if (data.code != 'A000000') {
              this.$message.error(data.message);
              return;
          }else {
              this.form.threadPic2 = data.data.url
          }
          this.$forceUpdate() 
      },
      handleAvatarSuccessPicture3(res, file){
              let data = JSON.parse(res.data) ;
          if (data.code != 'A000000') {
              this.$message.error(data.message);
              return;
          }else {
              this.form.threadPic3 = data.data.url
          }
          this.$forceUpdate() 
      },
       handleAvatarSuccessPicture4(res, file){
            let data = JSON.parse(res.data) ;
          if (data.code != 'A000000') {
              this.$message.error(data.message);
              return;
          }else {
              this.form.threadPic4 = data.data.url
          }
          this.$forceUpdate() 
      },
      handleAvatarSuccessPicture5(res, file){
               let data = JSON.parse(res.data) ;
          if (data.code != 'A000000') {
              this.$message.error(data.message);
              return;
          }else {
              this.form.threadPic5 = data.data.url
          }
          this.$forceUpdate() 
      },
      handleAvatarSuccessPicture6(res, file){
            let data = JSON.parse(res.data) ;
          if (data.code != 'A000000') {
              this.$message.error(data.message);
              return;
          }else {
              this.form.threadPic6 = data.data.url
          }
          this.$forceUpdate() 
      },
      handleAvatarSuccessPicture7(res, file){
               let data = JSON.parse(res.data) ;
          if (data.code != 'A000000') {
              this.$message.error(data.message);
              return;
          }else {
              this.form.threadPic7 = data.data.url
          }
          this.$forceUpdate() 
      },
      handleAvatarSuccessPicture8(res, file){
           let data = JSON.parse(res.data) ;
          if (data.code != 'A000000') {
              this.$message.error(data.message);
              return;
          }else {
              this.form.threadPic8 = data.data.url
          }
          this.$forceUpdate() 
      },
       handleAvatarSuccessPicture9(res, file){
             let data = JSON.parse(res.data) ;
          if (data.code != 'A000000') {
              this.$message.error(data.message);
              return;
          }else {
              this.form.threadPic9 = data.data.url
          }
          this.$forceUpdate() 
      },
 
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
.span{
  color: red
}
.el-checkbox+.el-checkbox{
  margin-left: 0px;
}
.r_div{
    margin-left: 80px
}
.border{
  border: 2px dashed #26a580;
  padding: 10px;
}
.div{
  margin-top: 10px;
}
.td_color{
    margin-left: 47%;
}
.diamonds{
   display: inline-block;
   width: 20px;
   height: 20px;
   border: 1px solid silver;

}
.bg_color{
  background: silver;
}
.mr10{
  width: 200px;
}

</style>
<style>
.el-upload--text{
    border: 0px dashed #d9d9d9 !important;
    width: 80px !important;
    height: 32px !important;
}
.upload-demo{
  display: inline-flex;
}
.el-upload-list{
  display: none;
}


</style>




