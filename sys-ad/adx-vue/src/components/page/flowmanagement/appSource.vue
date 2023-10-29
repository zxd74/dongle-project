<template>
    <div class="table">
        <div class="container">
            <div class="handle-box">
                <el-autocomplete
                    class="inline-input"
                    v-model="select_word"
                    value-key="appName"
                    :fetch-suggestions="querySearchName"
                    placeholder="请输入应用名称"
                    @select="handleSelectName"
                  ></el-autocomplete>
                <el-select v-model="Platform" placeholder="平台"  class="inline-input">
                  <el-option
                    v-for="item in optionsPlatform"
                    :key="item.value"
                    :label="item.label"
                    :value="item.value">
                  </el-option>
                </el-select>
                <el-select v-model="flow" placeholder="媒体名称"  class="inline-input">
                      <el-option
                        v-for="item in options"
                        :key="item.mediaUuid"
                        :label="item.mediaName"
                        :value="item.mediaUuid">
                      </el-option>
                </el-select>
                <el-select v-model="value" placeholder="状态"  class="inline-input">
                  <el-option
                    v-for="item in options_"
                    :key="item.value"
                    :label="item.label"
                    :value="item.value">
                  </el-option>
                </el-select>
                <el-button type="primary" icon="search" @click="search">搜索</el-button>
                <el-button type="primary" icon="search" @click="create" v-if="this.readonly !=1">新建流量源App</el-button>
            </div>
            <el-table :data="tableData" border style="width: 100%" ref="multipleTable">
                <el-table-column prop="id" label="ID"  width="100" align="center">
                </el-table-column>
                <el-table-column prop="appId" label="APPID"  width="100" align="center">
                </el-table-column>
                <el-table-column prop="appName" label="应用名称"  width="200" align="center">
                </el-table-column>
                <el-table-column prop="platformName" label="平台"  width="" align="center">
                </el-table-column>
                <el-table-column prop="mediaName" label="流量源名称"  width="" align="center">
                </el-table-column>
                <el-table-column prop="typeName" label="APP分类"  width="" align="center">
                </el-table-column>
                <el-table-column prop="adPosNum" label="广告位统计"  width="" align="center">
                </el-table-column>
                <el-table-column  label="状态"  width="" align="center" v-if="this.readonly !=1">
                  <template slot-scope="scope">
                     <el-switch v-model="scope.row.status" :active-value=1 :inactive-value=0 @change="changeStatus($event,scope.row)"></el-switch>
                  </template>
                </el-table-column>
                <el-table-column label="操作" width="150" align="center">
                    <template slot-scope="scope">
                        <el-button size="small" type="success" @click="handleEdit(scope.$index, scope.row)">编辑</el-button>
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
        <el-dialog title="编辑" :visible.sync="editVisible" width="50%">
           <el-form ref="form" :model="form" label-width="100px" :label-position="labelPosition">
                 <el-form-item label="选择流量源:">
                     <el-select v-model="form.flow" placeholder="" style="width:60%" @change="selectFlow" disabled>
                      <el-option
                        v-for="item in options"
                        :key="item.mediaUuid"
                        :label="item.mediaName"
                        :value="item.mediaUuid">
                      </el-option>
                    </el-select>
                </el-form-item>
                <div v-if="this.joinType == 71">
                  <el-form-item label="应用名称:">
                      <el-input v-model="form.appName" placeholder=""  style="width:60%"></el-input>
                  </el-form-item>
                  <!-- <el-form-item label="应用ID:">
                      <el-input v-model="form.appId" placeholder=""  style="width:60%"></el-input>
                  </el-form-item> -->
                  <el-form-item label="系统:" disabled>
                      <template>
                          <el-radio v-model="form.platform" :label="182" disabled>iOS</el-radio>
                          <el-radio v-model="form.platform" :label="183" disabled>Android</el-radio>
                      </template>
                  </el-form-item>
                  <el-form-item label="分类:">
                        <el-select v-model="form.classify" placeholder="请选择App分类" style="width:60%">
                              <el-option
                                  v-for="item in optionsType"
                                  :key="item.id"
                                  :label="item.dicValue"
                                  :value="item.id">
                              </el-option>
                        </el-select>
                  </el-form-item>
                  <el-form-item label="状态:">
                        <el-select v-model="form.status" style="width:60%">
                              <el-option
                                  v-for="item in options_type"
                                  :key="item.value"
                                  :label="item.label"
                                  :value="item.value">
                              </el-option>
                        </el-select>
                  </el-form-item>
                  <el-form-item label="* 广告平台映射:">
                         <div v-for="(video,index) in appMapped" :key="index">
                              <el-select v-model="video.appId" placeholder="选择广告平台" class="handle-input">
                                  <el-option
                                    v-for="item in optionType"
                                    :key="item.id"
                                    :label="item.consumerName"
                                    :value="item.id">
                                  </el-option>
                              </el-select>
                              <el-input style="width:120px" v-model="video.consumerPositionId" size="mini"  placeholder="广告平台App ID" ></el-input>
                              <el-button type="primary" @click="addImg">添加</el-button>
                              <el-button type="primary"  @click="DeleteImg(index)" v-show=" index!=0 ">删除</el-button>
                          </div>
                  </el-form-item>
                </div>
                <div v-if="this.joinType == 72">
                  <el-form-item label="应用名称:">
                      <el-input v-model="form.appName" placeholder=""  style="width:60%"></el-input>
                  </el-form-item>
                  <el-form-item label="分类:">
                      <el-select v-model="form.classify" placeholder="请选择App分类" style="width:60%">
                              <el-option
                                  v-for="item in optionsType"
                                  :key="item.id"
                                  :label="item.dicValue"
                                  :value="item.id">
                              </el-option>
                        </el-select>
                  </el-form-item>
                  <el-form-item label="系统:">
                      <template>
                          <el-radio v-model="form.platform" :label="182" disabled>iOS</el-radio>
                          <el-radio v-model="form.platform" :label="183"  disabled>Android</el-radio>
                      </template>
                  </el-form-item>
                  <el-form-item label="下载地址:">
                      <el-input v-model="form.dowUrl" placeholder=""  style="width:60%"></el-input>
                  </el-form-item>
                  <el-form-item label="上传应用包:">
                      <el-input v-model="form.apkUrl" placeholder=""  style="width:60%"></el-input>
                      <el-upload
                        class="upload-demo"
                        :data="{type:4}"
                        :action="upload"
                        name="myFile"
                        :on-preview="handlePreview"
                        :on-success="handleAvatarSuccess"
                        :on-remove="handleRemove"
                        :before-remove="beforeRemove"
                        >
                        <el-button size="small" type="primary">点击上传</el-button>
                        <div slot="tip" class="el-upload__tip"></div>
                      </el-upload>
                  </el-form-item>
                  <el-form-item label="状态:">
                        <el-select v-model="form.status" style="width:60%">
                              <el-option
                                  v-for="item in options_"
                                  :key="item.value"
                                  :label="item.label"
                                  :value="item.value">
                              </el-option>
                        </el-select>
                  </el-form-item>
                  <!-- <el-form-item label="关联广告位:">
                        <el-checkbox-group v-model="checkList">
                           <el-checkbox v-for="(item,index) in types" :key="index" :label="item.id" @change="chlickes">{{item.name}}</el-checkbox>
                       </el-checkbox-group>
                  </el-form-item> -->
                  <el-form-item label="* 广告平台映射:">
                         <div v-for="(video,index) in appMapped" :key="index">
                              <el-select v-model="video.appId" placeholder="选择广告平台" class="handle-input">
                                  <el-option
                                    v-for="item in optionType"
                                    :key="item.id"
                                    :label="item.consumerName"
                                    :value="item.id">
                                  </el-option>
                              </el-select>
                              <el-input style="width:120px" v-model="video.consumerPositionId" size="mini"  placeholder="广告平台App ID" ></el-input>
                              <el-button type="primary" @click="addImg">添加</el-button>
                              <el-button type="primary"  @click="DeleteImg(index)" v-show=" index!=0 ">删除</el-button>
                          </div>
                  </el-form-item>
                </div>
            </el-form>
            <span slot="footer" class="dialog-footer">
                <el-button @click="editVisible = false">取 消</el-button>
                <el-button type="primary" @click="saveEdit" v-if="this.readonly !=1">确 定</el-button>
            </span>
        </el-dialog>

          <!-- 新建弹出框 -->
        <el-dialog title="新建流量源APP" :visible.sync="newVisible" width="50%">
            <el-form ref="form" :model="form" label-width="100px" :label-position="labelPosition">
                <el-form-item label="* 选择流量源:">
                     <el-select v-model="form.flow" placeholder="" style="width:60%" @change="selectFlow">
                      <el-option
                        v-for="item in options"
                        :key="item.mediaUuid"
                        :label="item.mediaName"
                        :value="item.mediaUuid">
                      </el-option>
                    </el-select>
                </el-form-item>
                <div v-if="this.joinType == 71">
                  <el-form-item label="* 应用名称:">
                      <el-input v-model="form.appName" placeholder=""  style="width:60%"></el-input>
                  </el-form-item>
                  <!-- <el-form-item label="* 应用ID:">
                      <el-input v-model="form.appId" placeholder=""  style="width:60%"></el-input>
                  </el-form-item> -->
                  <el-form-item label="* 系统:">
                      <template>
                          <el-radio v-model="form.platform" :label="182" >iOS</el-radio>
                          <el-radio v-model="form.platform" :label="183" >Android</el-radio>
                      </template>
                  </el-form-item>
                  <el-form-item label="* 分类:">
                        <el-select v-model="form.classify" placeholder="请选择App分类" style="width:60%">
                              <el-option
                                  v-for="item in optionsType"
                                  :key="item.id"
                                  :label="item.dicValue"
                                  :value="item.id">
                              </el-option>
                        </el-select>
                  </el-form-item>
                  <el-form-item label="* 状态:">
                        <el-select v-model="form.status" style="width:60%">
                              <el-option
                                  v-for="item in options_type"
                                  :key="item.value"
                                  :label="item.label"
                                  :value="item.value">
                              </el-option>
                        </el-select>
                  </el-form-item>
                  <el-form-item label="* 广告平台映射:">
                         <div v-for="(video,index) in appMapped" :key="index">
                              <el-select v-model="video.appId" placeholder="选择广告平台" class="handle-input">
                                  <el-option
                                    v-for="item in optionType"
                                    :key="item.id"
                                    :label="item.consumerName"
                                    :value="item.id">
                                  </el-option>
                              </el-select>
                              <el-input style="width:120px" v-model="video.consumerPositionId" size="mini"  placeholder="广告平台App ID" ></el-input>
                              <el-button type="primary" @click="addImg">添加</el-button>
                              <el-button type="primary"  @click="DeleteImg(index)" v-show=" index!=0 ">删除</el-button>
                          </div>
                  </el-form-item>
                </div>
                <div v-if="this.joinType == 72">
                  <el-form-item label="* 应用名称:">
                      <el-input v-model="form.appName" placeholder=""  style="width:60%"></el-input>
                  </el-form-item>
                  <el-form-item label="* 分类:">
                      <el-select v-model="form.classify" placeholder="请选择App分类" style="width:60%">
                              <el-option
                                  v-for="item in optionsType"
                                  :key="item.id"
                                  :label="item.dicValue"
                                  :value="item.id">
                              </el-option>
                        </el-select>
                  </el-form-item>
                  <el-form-item label="* 系统:">
                      <template>
                          <el-radio v-model="form.platform" :label="182" >iOS</el-radio>
                          <el-radio v-model="form.platform" :label="183" >Android</el-radio>
                      </template>
                  </el-form-item>
                  <el-form-item label="* 下载地址:">
                      <el-input v-model="form.dowUrl" placeholder=""  style="width:60%"></el-input>
                  </el-form-item>
                  <el-form-item label="* 上传应用包:">
                      <el-input v-model="form.apkUrl" placeholder=""  style="width:60%"></el-input>
                      <el-upload
                        class="upload-demo"
                        :data="{type:4}"
                        :action="upload"
                        name="myFile"
                        :on-preview="handlePreview"
                        :on-success="handleAvatarSuccess"
                        :on-remove="handleRemove"
                        :before-remove="beforeRemove"
                        >
                        <el-button size="small" type="primary">点击上传</el-button>
                        <div slot="tip" class="el-upload__tip"></div>
                      </el-upload>
                  </el-form-item>
                  <el-form-item label="* 状态:">
                        <el-select v-model="form.status" style="width:60%">
                              <el-option
                                  v-for="item in options_"
                                  :key="item.value"
                                  :label="item.label"
                                  :value="item.value">
                              </el-option>
                        </el-select>
                  </el-form-item>
                  <!-- <el-form-item label="* 关联广告位:">
                        <el-checkbox-group v-model="checkList">
                           <el-checkbox v-for="(item,index) in types" :key="index" :label="item.id">{{item.name}}</el-checkbox>
                       </el-checkbox-group>
                  </el-form-item> -->
                  <el-form-item label="广告平台映射:">
                         <div v-for="(video,index) in appMapped" :key="index">
                              <el-select v-model="video.appId" placeholder="选择广告平台" class="handle-input">
                                  <el-option
                                    v-for="item in optionType"
                                    :key="item.id"
                                    :label="item.consumerName"
                                    :value="item.id">
                                  </el-option>
                              </el-select>
                              <el-input style="width:120px" v-model="video.consumerPositionId" size="mini"  placeholder="广告平台App ID" ></el-input>
                              <el-button type="primary" @click="addImg">添加</el-button>
                              <el-button type="primary"  @click="DeleteImg(index)" v-show=" index!=0 ">删除</el-button>
                          </div>
                  </el-form-item>
                </div>

            </el-form>
            <span slot="footer" class="dialog-footer">
                <el-button @click="newVisible = false">取 消</el-button>
                <el-button type="primary" @click="saveNew">确 定</el-button>
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

    </div>
</template>

<script>
import { appgetlist,upload,getlistbyfsorplatForm} from "@/api/Api.js";
import { flowsourcegetlist } from "@/api/Api.js";
import { appadd,appgetapp,appupdate,appdelete } from "@/api/Api.js";
import { diclist,appupdateStaus,
  flowconsumerList
 } from "@/api/Api.js";

var map = new Map();

export default {
  name: "basetable",
  data() {
    return {
      labelPosition: 'left',
      upload: upload,
      // url: "./static/vuetable.json",
      tableData: [],
      select_word: "",
      cur_page: 1,
      total: 1,
      ps:10,
      status:1,
      joinType:1,
      mediaUuids:'',
      types:null,
      Platform:'',
      checkList:[],

      fileList:[{}],
      select_cate: "",
      select_word: "",
      del_list: [],
      is_search: false,
      editVisible: false,
       editVisible2: true,
       flow:'',
      delVisible: false,
      newVisible: false,
      newaddAD: false,
      template_dialog: false,
      currentRow: null,
        dynamicTags: ['标签一', '标签二', '标签三'],
      form: {
        flow:'',
        id: "",
        ad:[],
        appName: "",
        platform:'',
        type:'',
        Types:'',
        status:1,
        dowUrl:'',
        apkUrl:'',
        classify:1,
        address: "",
        adPosIds:'',
        switch: false,
        quanxian:'sss'
      },
      idx: -1,
      flow_type: 1,
      type: 1,
      system: 1,
      Access: "API",
      display_form: 1,
      // 添加广告位选择流量源
        options: [
        {
          value: '',
          label: ''
        }
      ],
      optionsType:[
         {
          value: '',
          label: ''
        }
      ],
      optionsPlatform:[
         {
          value: '',
          label: '全部平台'
        },
        {
          value: 182,
          label: 'IOS'
        },
        {
          value: 183,
          label: 'Android'
        },
      ],
      options_: [
        {
          value: 1,
          label: '启用'
        },
        {
          value: 0,
          label: '停用'
        },
         {
          value: '',
          label: '全部状态'
        }
      ],
      options_type: [
        {
          value: 1,
          label: '启用'
        },
        {
          value: 0,
          label: '停用'
        },
      ],
      optionType: [
        {
          value: "",
          label: ""
        },
      ],
      appMapped: [
            {
                appId:'',
                consumerPositionId:'',
            }
          ],
      value: "",
      readonly:'',
    };

  },
  created() {
      this.readonly = this.$store.state.map.get(this.$store.state.currentPath);
      console.log(  this.readonly)
      this.getList();
      this.getName();
      this.getsdkList();
  },
  methods: {
    chlickes(value){
      console.log(value)
    },
       // app名称下拉框
    getsdkList(){
        flowconsumerList().then(res=>{
          console.log(res)
            let data = res.data; 
             if(data.code != 'A000000') {
              this.$message.error(data.message);
              return;
            }    
            this.optionType = data.data
          });
    },
      // 开关
    changeStatus(val,row){
             let params = {
                id:row.id,
                status:row.status,
            }
     appupdateStaus(params).then(res=>{
     let data = res.data;
          if(data.code != 'A000000') {
              this.$message.error(data.message);
          }
            //  this.getList();
      });
    },
    // 分页导航
    handleCurrentChange(val) {
      this.cur_page = val;
       let params = {
         appName:this.select_word,
         platform:this.Platform,
         medias:this.flow,
         status:this.value,
         currentPage:this.cur_page,
         pageSize:this.ps
         }
      appgetlist(params).then(res=>{
     let data = res.data;
       this.tableData = data.data.data
       this.total = data.data.totalItemNum;
      // console.log(this.total)
      });
    },
    getList() {
      let params = {currentPage:this.cur_page,pageSize:this.ps}
      appgetlist(params).then(res=>{
     let data = res.data;
          if(data.code != 'A000000') {
              this.$message.error(data.message);
              return
          }
       this.tableData = data.data.data
       this.total = data.data.totalItemNum;
      // console.log(this.total)
      });
    },
    // 搜索
    search() {
      this.$refs.pagination.lastEmittedPage = 1
      this.cur_page = 1;
       let params = {
         appName:this.select_word,
         platform:this.Platform,
         medias:this.flow,
         status:this.value,
         currentPage:this.cur_page,
         pageSize:this.ps
         }
      appgetlist(params).then(res=>{
     let data = res.data;
       this.tableData = data.data.data
       this.total = data.data.totalItemNum;
      // console.log(this.total)
      });
    },
    // 编辑
    handleEdit(index, row) {
      this.appMapped =[]
      this.checkList = [];
       this.idx = index;
      let item = row;
            // 分类
          let par = {
            gid : 79
          }
          let that = this;
          diclist(par).then(res=>{
        let data = res.data;
          this.optionsType = data.data
          });
      this.form.id = item.id
      let params = {
          id:item.id
        }
      appgetapp(params).then(res=>{
        console.log(res)
        let data = res.data;
        // console.log(data)
        // this.selectFlow(data.data.medias);
       this.joinType = data.data.joinType
       this.form.flow = data.data.mediaName
       this.form.appName = res.data.data.appName
       this.form.platform = data.data.platform
       this.form.classify = data.data.type
       this.form.status = data.data.status
       this.form.dowUrl = data.data.dowUrl
       this.form.apkUrl = data.data.apkUrl
       this.mediaUuids = data.data.medias
                 console.log(data.data.echoMapped)
        if(data.data.echoMapped.length < 1){

              this.appMapped = [
                    {
                        appId:'',
                        consumerPositionId:'',
                    }
              ]
        }else{
              data.data.echoMapped.forEach(element => {
                    console.log(element)
                      let obj= {
                          appId:null,
                          consumerPositionId:'',
                        }
                    for(let i in element){
                      obj.appId = parseInt(i) 
                      obj.consumerPositionId = element[i]
                      }
                      console.log(obj);	
                    this.appMapped.push(obj)
                    console.log( this.appMapped)
                  });
        }
  
      //  this.systemType();
       if(data.data.adPosIds) {
        data.data.adPosIds.split(",").map(function (value) {
             if(value) {
                 that.checkList.push(parseInt(value));
                // return parseInt(value);
             }

        });
       }
      });
      this.editVisible = true;
    },

    // 保存编辑
    saveEdit() {
        // 参数判断
      if(!this.form.appName) {
        this.$message.error("应用名称不能为空");
        return;
      }
      if(!this.form.appName) {
        this.$message.error("应用名称不能为空");
        return;
      }
      if(!this.form.classify) {
        this.$message.error("分类不能为空");
        return;
      }
      let list = [];
      if(this.appMapped.length <0){
          list = []
      }else{
           this.appMapped.map(function (item) {
            if(item.appId == "" || item.consumerPositionId == ""){
                return;
            }else{
              let obj ={};
              obj[item.appId] = item.consumerPositionId;
              list.push(obj);
            }
            
          });
      }
      let params = {
            id:this.form.id,
            appId : this.form.appId,
            appName : this.form.appName,
            type:this.form.classify,
            platform:this.form.platform,
            dowUrl:this.form.dowUrl,
            apkUrl:this.form.apkUrl,
            status:this.form.status,
            medias:this.mediaUuids,
            // adPosIds:this.checkList.join()
            appMapped:list
          }

       appupdate(params).then(res=>{
         console.log(res)
     let data = res.data;
          if(data.code != 'A000000') {
              this.$message.error(data.message);
              return;
          }
        this.search()
      });
      this.editVisible = false;
      this.$message.success(`修改成功`);

    },
    // 删除
    handleDelete(index, row) {
          this.delVisible = true;
          this.row = row;
      },
       // 确定删除
    deleteRow() {
     let params = {id:this.row.id}
          // console.log(row)
          appdelete(params).then(res=>{
              let data = res.data;
                if(data.code != 'A000000') {
                    this.$message.error(data.message);
                    return
                }else{
                    this.$message.success("删除成功");
                    this.delVisible = false;
                }
                this.getList();
            });
    },
    // 选择流量源
    getName(){
        let params = {
        type : 69
      }
      flowsourcegetlist(params).then(res=>{
     let data = res.data;
     if(data.code != 'A000000') {
         this.$message.error(data.message);
         return
     }
       this.options = data.data
       // 设置map
       data.data.forEach(e => {
         map.set(e.mediaUuid,e);
       });
      });
    },
    // 新建
    create() {
      this.newVisible = true;
      this.form = {status :1,platform:182};
      this.joinType = '';
      this.appMapped= [
            {
                appId:'',
                consumerPositionId:'',
            }
          ];
      let params = {
        type : 69
      }
      flowsourcegetlist(params).then(res=>{
     let data = res.data;
       this.options = data.data
     
       // 设置map
       console.log(this.options)
       data.data.forEach(e => {
         map.set(e.mediaUuid,e);
       });
      });

      // 分类
      let par = {
        gid : 79
      }
       diclist(par).then(res=>{
     let data = res.data;
       this.optionsType = data.data
      });
    },
    // 确认
    saveNew() {
      console.log(this.appMapped)
        // 参数判断
      if(!this.form.appName) {
        this.$message.error("应用名称不能为空");
        return;
      }
      if(!this.form.appName) {
        this.$message.error("应用名称不能为空");
        return;
      }
      if(!this.form.classify) {
        this.$message.error("分类不能为空");
        return;
      }
      let list = [];
      if(this.appMapped.length <0){
          list = []
      }else{
           this.appMapped.map(function (item) {
            if(item.appId == "" || item.consumerPositionId == ""){
                return;
            }else{
              let obj ={};
              obj[item.appId] = item.consumerPositionId;
              list.push(obj);
            }
            
          });
        }
    
          console.log(list);
         let params = {
            appName : this.form.appName,
            type:this.form.classify,
            platform:this.form.platform,
            dowUrl:this.form.dowUrl,
            apkUrl:this.form.apkUrl,
            status:this.form.status,
            medias:this.form.flow,
            appMapped:list
          
          }
      console.log(this.checkList)
     appadd(params).then(res=>{
     let data = res.data;
          if(data.code != 'A000000') {
              this.$message.error(data.message);
              return;
          }
        this.getList()
      });
      this.newVisible = false;
    },
  // 渲染广告位
    selectFlow(val){
       let data = map.get(val);
       this.joinType = data.joinType
       this.mediaUuids = data.mediaUuid
        let params = {
          mediaUuids: this.mediaUuids
        }
     getlistbyfsorplatForm(params).then(res=>{
            console.log(res)
       let data = res.data;
       this.types = data.data
       console.log(this.types)
      });
  
    },
    // systemType(){
    //    let params = {
    //       mediaUuids: this.mediaUuids,
    //       platForm:this.form.platform
    //     }
    //  getlistbyfsorplatForm(params).then(res=>{
    //  let data = res.data;
    //    this.types = data.data
    //   });

    // },
     //模糊名字
    querySearchName(queryString, cb){
        let params = {
          appName:this.select_word,
          currentPage:this.cur_page,
          pageSize:this.ps
          }
         appgetlist(params).then(res=>{
     let data = res.data;
          console.log(data)
          // 调用 callback 返回建议列表的数据
        cb(data.data.data);
       if(data.code != 'A000000') {
          this.$message.error(data.message);
            }
        });
    },
    handleSelectName(item){
      // console.log(item);
      this.id = item.id
    },

 
      addImg(){
        this.appMapped.push({appId:'',consumerPositionId:''});
      },
      DeleteImg(i){
          this.appMapped.splice(i,1)
      },

      handleRemove(file, fileList) {
        // console.log(file, fileList);
      },
      handlePreview(file) {
        // console.log(this.upload);
      },
      beforeRemove(file, fileList) {
        return this.$confirm(`确定移除 ${ file.name }？`);
      },
      handleAvatarSuccess(res, file) {
         let data = res;
        if(data.code != 'A000000'){
            this.$message(data.message)
            return;
        }
          this.form.apkUrl = data.data.url
          console.log(this.form.apkUrl)
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
  width: 183px;
  display: inline-block;
}
.del-dialog-cnt {
  font-size: 16px;
  text-align: center;
}
.inline-input{
  width: 160px;
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
