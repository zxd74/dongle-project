<template>
    <div class="table">
        <div class="container">
            <div class="handle-box">
                <el-select v-model="select_word" placeholder="请选择APP">
                      <el-option
                        v-for="item in FlowOptionsSeach"
                        :key="item.id"
                        :label="item.appName"
                        :value="item.id">
                      </el-option>
                </el-select>
                <!-- <el-input v-model="templateId" placeholder="请输入模板名称" class="handle-input mr10"></el-input> -->
                <el-input v-model="select_name" placeholder="请输入广告位名称" class="handle-input mr10"></el-input>
                <el-input v-model="select_uuid" placeholder="请输入uuid" class="handle-input mr10"></el-input>
                <el-select v-model="AD_type" clearable placeholder="广告类型"  class="handle-input mr10">
                  <el-option
                    v-for="item in OptionsType"
                    :key="item.value"
                    :label="item.label"
                    :value="item.value">
                  </el-option>
                </el-select>
                <el-select v-model="value"  placeholder="状态"  class="handle-input mr10">
                  <el-option
                    v-for="item in options_"
                    :key="item.value"
                    :label="item.label"
                    :value="item.value">
                  </el-option>
                </el-select>
                <el-button type="primary" icon="search" @click="search">搜索</el-button>
                <el-button type="primary" icon="search" @click="create"  v-if="this.readonly !=1">新建广告位</el-button>
            </div>
            <el-table :data="data" border style="width: 100%" ref="multipleTable">
                <el-table-column prop="id" label="ID"  align="center" width="50px">
                </el-table-column>
                <el-table-column prop="uuid" label="uuid"  align="center" width="60px">
                </el-table-column>
                <el-table-column prop="name" label="广告位名称"  width="150px" align="center"> 
                </el-table-column>
                <el-table-column prop="appName" label="APP名称"  width="" align="center">
                </el-table-column>
                <el-table-column prop="os" label="操作系统"  width="" align="center" :formatter=formattermediaType>
                </el-table-column>
                <el-table-column prop="typeName" label="广告形式"  width="" align="center">
                </el-table-column>
                <el-table-column prop="isSupportDynamic" label="是否支持动态广告"  align="center" :formatter=formatType>
                </el-table-column>
                <el-table-column prop="forecastExposure" label="预估每日库存"  width="" align="center">
                </el-table-column>
                <el-table-column  label="状态"  width="60px" align="center"  v-if="this.readonly !=1">
                  <template slot-scope="scope">
                     <el-switch v-model="scope.row.status" :active-value=1 :inactive-value=0  @change="changeStatus($event,scope.row)"></el-switch>
                  </template>
                </el-table-column>
                <el-table-column label="操作"   align="center">
                    <template slot-scope="scope">
                        <el-button size="small" type="success" @click="handleEdit(scope.$index, scope.row)">编辑</el-button>
                        <!-- <el-button size="small" type="danger" @click="handleDelete(scope.$index, scope.row)">删除</el-button> -->
                        <!-- <el-button size="small" type="success" @click="look_report(scope.$index, scope.row)">查看报告</el-button> -->
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
                <el-form-item label="* 广告位名称:">
                      <el-input v-model="form.ADname" placeholder="请输入广告位名称" style="width:50%"></el-input> 
                </el-form-item>
                <el-form-item label="* APP:">
                  <template>
                    <el-select v-model="form.flow_Name" placeholder="请选择">
                      <el-option
                        v-for="item in FlowOptions"
                        :key="item.id"
                        :label="item.appName"
                        :value="item.id">
                      </el-option>
                    </el-select>
                  </template>
                </el-form-item>
                <!-- <el-form-item label="* 操作系统:">
                    <el-radio-group v-model="form.os">
                        <el-radio :label="182">ios</el-radio>
                        <el-radio :label="183">android</el-radio>
                    </el-radio-group>
                </el-form-item> -->
                <el-form-item label="* 广告形式:">
                   <el-select v-model="form.display_form" placeholder="请选择" disabled>
                      <el-option
                        v-for="item in OptionsType"
                        :key="item.value"
                        :label="item.label"
                        :value="item.value">
                      </el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="标题:" v-if="this.form.display_form == 216">
                    限 <el-input style="width:100px" v-model="form.title" size="mini"  type="number"></el-input><span class='font'>字</span>
                </el-form-item> 
                <el-form-item label="视频:" v-if="this.form.display_form == 4">
                    宽 <el-input style="width:100px" v-model="form.videoWidth" size="mini" type="number"></el-input><span class='font'>px</span>
                    高 <el-input style="width:100px" v-model="form.videoHeight" size="mini" type="number"></el-input><span class='font'>px</span>
                    时长 <el-input style="width:100px" v-model="form.duration" size="mini" type="number"></el-input><span class='font'>S</span>
                </el-form-item> 
                <el-form-item label="主图片:" v-if="this.form.display_form == 5||this.form.display_form == 6||this.form.display_form == 218">
                    宽 <el-input style="width:100px" v-model="form.picWidth" size="mini" type="number"></el-input><span class='font'>px</span>
                    高 <el-input style="width:100px" v-model="form.picHeight" size="mini" type="number"></el-input><span class='font'>px</span>
                </el-form-item> 
                <el-form-item label="* 选择模板:" v-if="this.form.display_form == 217||this.form.display_form == 7">
               
                    <div v-for="(item,index) in template" :key="index">
                      <el-input style="width:50%" v-model="item.name" ></el-input>
                      <el-button type="primary" @click="select_template2(index)">选择</el-button>
                      <el-button type="primary" @click="add_template">+</el-button>
                      <el-button type="primary" @click="del_template2(index)">-</el-button>

                   
                    </div>
                      <!-- 选择模板弹出框  -->
                     <el-dialog title="选择模板" :visible.sync="template_dialog"  :modal="false" width="40%" center>
                      <div>
                         <el-autocomplete
                            class="inline-input"
                            v-model="selectTP"
                            value-key="name"
                            :fetch-suggestions="querySearchTemplate"
                            placeholder="请输入模板名称"
                            @select="handleSelectemplate" 
                         ></el-autocomplete>
                         <el-button type="primary" icon="search" @click="searchTemplate">搜索</el-button>
                      </div>
                      <el-table
                            ref="singleTable"
                            :data="tableData"
                            highlight-current-row
                            @current-change="handleChange"
                            style="width: 100%">
                            <el-table-column
                              width="55">
                              <template slot-scope="scope">
                                   <el-radio v-model="checked.id" :label="scope.row.id">&nbsp;</el-radio>
                              </template>
                            </el-table-column> 
                            <el-table-column
                              property="name"
                              label="模板名称"
                              width="120">
                            </el-table-column> 
                            <el-table-column
                              property="materialName"
                              label="包含元素">
                            </el-table-column>
                       </el-table>
                        <div class="pagination">
                            <el-pagination @current-change="handleCurrentChange_tamplate" layout="total,prev, pager, next" :total="m_total"  ref="pagination">
                            </el-pagination>
                        </div>
                        <span slot="footer" class="dialog-footer">
                            <el-button @click="template_dialog = false">取 消</el-button>
                            <el-button type="primary" @click="template_save()">确 定</el-button>
                        </span>
                    </el-dialog>
             
                </el-form-item>
                <el-form-item label="预估ctr:">
                     <el-input v-model="form.forecastCtr" style="width:34%"></el-input>%
                </el-form-item>
                <el-form-item label="支持动态广告:">
                    <el-radio-group v-model="form.isSupportDynamic">
                        <el-radio :label="1">是</el-radio>
                        <el-radio :label="0">否</el-radio>
                    </el-radio-group>
                </el-form-item>
                <!-- <el-form-item label="上传预览截图">
                    <el-input v-model="form.screenshot"  style="width:44%"></el-input>
                    <el-upload
                        class="upload-demo"
                        :data="{type:1}"
                        :action="upload"
                        name="myFile"
                        :on-success="handleSuccess"
                    >
                    <el-button size="small" type="primary">选择文件</el-button>
                    </el-upload>
                </el-form-item> -->
                <!-- <el-form-item label="广告插入位置:">
                    X <el-input style="width:100px" v-model="form.locationX" size="mini" type="number"></el-input>
                    Y <el-input style="width:100px" v-model="form.locationY" size="mini" type="number"></el-input>
                </el-form-item>  -->
                <el-form-item label="备注:">
                      <el-input v-model="form.comment" style="width:50%"></el-input>
                </el-form-item>
            </el-form> 
            <span slot="footer" class="dialog-footer">
                <el-button @click="editVisible = false">取 消</el-button>
                <el-button type="primary" @click="saveEdit"  v-if="this.readonly !=1">确 定</el-button>
            </span>
        </el-dialog>

          <!-- 新建弹出框 -->
        <el-dialog title="新建广告位" :visible.sync="newVisible" width="50%">
            <el-form ref="form" :model="form" label-width="120px" :label-position="labelPosition">
                <el-form-item label="* 广告位名称:">
                      <el-input v-model="form.ADname" placeholder="请输入广告位名称" style="width:50%"></el-input> 
                </el-form-item>
                <el-form-item label="* APP:">
                  <template>
                    <el-select v-model="form.flow_Name" placeholder="请选择">
                      <el-option
                        v-for="item in FlowOptions"
                        :key="item.id"
                        :label="item.appName"
                        :value="item.id">
                      </el-option>
                    </el-select>
                  </template>
                </el-form-item>
                <el-form-item label="* 广告形式:">
                   <el-select v-model="form.display_form" placeholder="请选择">
                      <el-option
                        v-for="item in OptionsType"
                        :key="item.value"
                        :label="item.label"
                        :value="item.value">
                      </el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="标题:" v-if="this.form.display_form == 216">
                    限 <el-input style="width:100px" v-model="form.title" size="mini"  type="number"></el-input><span class='font'>字</span>
                </el-form-item> 
                <el-form-item label="视频:" v-if="this.form.display_form == 4 || this.form.display_form == 6">
                    宽 <el-input style="width:100px" v-model="form.videoWidth" size="mini" type="number"></el-input><span class='font'>px</span>
                    高 <el-input style="width:100px" v-model="form.videoHeight" size="mini" type="number"></el-input><span class='font'>px</span>
                    时长 <el-input style="width:100px" v-model="form.duration" size="mini" type="number"></el-input><span class='font'>S</span>
                </el-form-item> 
                <el-form-item label="主图片:" v-if="this.form.display_form == 5||this.form.display_form == 6||this.form.display_form == 218">
                    宽 <el-input style="width:100px" v-model="form.picWidth" size="mini" type="number"></el-input><span class='font'>px</span>
                    高 <el-input style="width:100px" v-model="form.picHeight" size="mini" type="number"></el-input><span class='font'>px</span>
                </el-form-item> 
                <el-form-item label="* 选择模板:" v-if="this.form.display_form == 217||this.form.display_form == 7">
               
                    <div v-for="(item,index) in template" :key="index">
                      <el-input style="width:50%" v-model="item.name" ></el-input>
                      <el-button type="primary" @click="select_template(index)">选择</el-button>
                      <el-button type="primary" @click="add_template">+</el-button>
                      <el-button type="primary" @click="del_template(index)">-</el-button>

                   
                    </div>
                      <!-- 选择模板弹出框  -->
                     <el-dialog title="选择模板" :visible.sync="template_dialog"  :modal="false" width="40%" center>
                      <div>
                         <el-autocomplete
                            class="inline-input"
                            v-model="selectTP"
                            value-key="name"
                            :fetch-suggestions="querySearchTemplate"
                            placeholder="请输入模板名称"
                            @select="handleSelectemplate" 
                         ></el-autocomplete>
                         <el-button type="primary" icon="search" @click="searchTemplate">搜索</el-button>
                      </div>
                      <el-table
                            ref="singleTable"
                            :data="tableData"
                            highlight-current-row
                            @current-change="handleChange"
                            style="width: 100%">
                            <el-table-column
                              width="55">
                              <template slot-scope="scope">
                                   <el-radio v-model="checked.id" :label="scope.row.id">&nbsp;</el-radio>
                              </template>
                            </el-table-column> 
                            <el-table-column
                              property="name"
                              label="模板名称"
                              width="120">
                            </el-table-column> 
                            <el-table-column
                              property="materialName"
                              label="包含元素">
                            </el-table-column>
                       </el-table>
                        <div class="pagination">
                            <el-pagination @current-change="handleCurrentChange_tamplate" layout="total,prev, pager, next" :total="m_total"  ref="pagination">
                            </el-pagination>
                        </div>
                        <span slot="footer" class="dialog-footer">
                            <el-button @click="template_dialog = false">取 消</el-button>
                            <el-button type="primary" @click="template_save()">确 定</el-button>
                        </span>
                    </el-dialog>
             
                </el-form-item>
                <el-form-item label="预估ctr:">
                      <el-input v-model="form.forecastCtr" style="width:34%"></el-input>%
                </el-form-item>
                <el-form-item label="支持动态广告:">
                    <el-radio-group v-model="form.isSupportDynamic">
                        <el-radio :label="1">是</el-radio>
                        <el-radio :label="0">否</el-radio>
                    </el-radio-group>
                </el-form-item>
                <!-- <el-form-item label="上传预览截图">
                    <el-input v-model="form.screenshot"  style="width:44%"></el-input>
                    <el-upload
                        class="upload-demo"
                        :data="{type:1}"
                        :action="upload"
                        name="myFile"
                        :on-success="handleSuccess"
                    >
                    <el-button size="small" type="primary">选择文件</el-button>
                    </el-upload>
                </el-form-item> -->
                <!-- <el-form-item label="广告插入位置:">
                    X <el-input style="width:100px" v-model="form.locationX" size="mini" type="number"></el-input>
                    Y <el-input style="width:100px" v-model="form.locationY" size="mini" type="number"></el-input>
                </el-form-item>  -->
                <el-form-item label="备注:">
                      <el-input v-model="form.comment" style="width:50%"></el-input>
                </el-form-item>
            </el-form> 
            <span slot="footer" class="dialog-footer">
                <el-button @click="newVisible = false">取 消</el-button>
                <el-button type="primary" @click="saveNew">确 定</el-button>
            </span>
        </el-dialog>
    </div>
</template>

<script>
import { adPositiongetList,flowsourcegetall,diclist,adPositionadd,adPositionupdate} from "@/api/Api.js";
import { templateList,adPositionupdateStatus,appgetall,upload,adPositionmodifiable,templatemodifiable,modifiableByPosition } from "@/api/Api.js";


const uuid = new Map
const tpid = new Map
export default {
  name: "basetable",
  
  data() {
    return {
      template:[{name:'',id:''}],
      labelPosition: 'left',
      upload: upload,
      m_total: 1,
      url: "./static/vuetable.json",
      data:[],
      tableData: [],
      cur_page: 1,
      total: 1,
      ps:10,
      select_word: "",
      selectTP:'',
      // templateId:"",
      select_name:"",
      select_uuid:"",
      checked: {name:'',id:''},
      del_list: [],
      value1:'',
      is_search: false,
      editVisible: false,
      MutuallVisible: false,
      newVisible: false,
      newaddAD: false,
      template_dialog: false,
      currentRow: null,
      status:'',
      indexs:'',
      form: {
        id:'',
        flow_Name:'',
        ADname:'',
        flowSource:'',
        display_form: '',
        remark:'',
        name: "",
        date: "",
        address: "",
        switch: true,
        comment:'',
        flowPositionId:'',
        os:'',
        title:'',
        videoWidth:'',
        videoHeight:'',
        duration:'',
        picWidth:'',
        picHeight:'',
        forecastCtr:'',
        isSupportDynamic:'',
        screenshot:'',
        locationX:'',
        locationY:'',
      },
      idx: -1,
      flow_type: '',
      type: 1,
      system: 1,
      Access: "API",
    
      options_: [
        {
          value: "",
          label: "全部状态"
        },
        {
          value: 1,
          label: '启用'
        },
        {
          value: 0,
          label: '停用'
        },
       
      ],
      FlowOptions:[
          {
          value: '',
          label: ''
        },
      ],
      FlowOptionsSeach:[
          {
          value: '',
          label: ''
        },
      ],
      AD_type:'',
      OptionsType:[
          {
          value: 4,
          label: '全屏视频'
          },
          {
          value: 5,
          label: '横幅'
          },
          {
          value: 6,
          label: '开屏'
          },
          {
          value: 7,
          label: '信息流'
          },
          {
          value: 217,
          label: '激励视频'
          },
          {
          value: 218,
          label: '插屏'
          },
      ],
      value: "",
      readonly:'',
    };
  },
  created() {
        let readonly = this.$store.state.map.get(this.$store.state.currentPath);
        this.getList();
        this.getType();
        this.getType2();
  },

  methods: {
    add_template(){
        this.template.push({name:'',id:''})
        console.log(this.template)
    },
    del_template(i) {
        this.template.splice(i,1);
    },
    del_template2(i) {
          console.log(this.template[i].id)
        if(this.template[i].name == ''){
                  this.template.splice(i,1);
        }else{
              let params = {
              id:this.template[i].id,
              positionId:this.form.id
                }
          modifiableByPosition(params).then(res=>{
                console.log(res)
                let data = res.data;
                if(data.code != 'A000000') {
                    this.$message.error(data.message);
                    return;
                }
                if(data.data == 1){
                      this.template.splice(i,1);
                }else if(data.data == 0){
                      this.$message('此模板不可删除')
                      return;
                }
            });
        }
      

    },
          // 开关
    changeStatus(val,row){
             let params = {
                id:row.id,
                status:row.status,
                name:row.name
            }
     adPositionupdateStatus(params).then(res=>{
     let data = res.data;
          if(data.code != 'A000000') {
              this.$message.error(data.message);
          }
          // this.getList();
      });
    },
    // 分页导航
    handleCurrentChange(val) {
      this.cur_page = val;
        let params = {
        flowUuid:uuid.get(this.select_word),
        // templateId:this.templateId,
        name:this.select_name,
        appId:this.select_word,
        uuid:this.select_uuid,
        status:this.value,
        cp:this.cur_page,
        ps:this.ps
      }
       adPositiongetList(params).then(res=>{
     let data = res.data;     
       this.data = data.data.data
       this.total = data.data.totalItemNum;
      // console.log(res)
      });
    },
    handleCurrentChange_tamplate(val){
      this.cur_page = val;
      this.select_template();
    },
    // 格式化数据
    formattermediaType(row, column) {
      if(row.os == 182) {
        return "ios";
      } else if(row.os == 183){
        return "android";
      }
    },
    formatType(row, column) {
      if(row.isSupportDynamic == 1) {
        return "是";
      } else if(row.isSupportDynamic == 0){
        return "否";
      }
    },
       //模糊名字
    querySearchName(queryString, cb){
        let params = {
          appName:this.select_word,
          }
         appgetall(params).then(res=>{
          let data = res.data;
          console.log(data.data)
          // 调用 callback 返回建议列表的数据
        cb(data.data);
        data.data.forEach(element => {
          uuid.set(element.appName,element.id)
        });
       if(data.code != 'A000000') {
          this.$message.error(data.message);
            } 
        });
    },
     handleSelectName(item){
      this.id = item.id
    },
    // 模糊模板
     querySearchTemplate(queryString, cb){
         let params = {
            name:this.selectTP,
            cp:1,
            ps:this.ps,
            type:this.form.display_form,
          }
          templateList(params).then(res=>{
     let data = res.data;
          console.log(data.data.data)
          // 调用 callback 返回建议列表的数据
        cb(data.data.data);
        data.data.data.forEach(element => {
          tpid.set(element.name,element.id)
        });
       if(data.code != 'A000000') {
          this.$message.error(data.message);
            } 
        });
    },
    handleSelectemplate(item){
      this.id = item.id
    },
    getType(){
      appgetall().then(res=>{
        console.log(res)
       let data = res.data;     
       this.FlowOptions = data.data
      });
    },
    getType2(){
      appgetall().then(res=>{
        console.log(res)
       let data = res.data;     
       this.FlowOptionsSeach = data.data
       let  obj = {
          id: '',
          appName: '全部APP'
       }
        this.FlowOptionsSeach.push(obj)
        console.log(this.FlowOptionsSeach)
      });
    },
    getList() {
      let params = {cp:this.cur_page,ps:this.ps}
      adPositiongetList(params).then(res=>{
        console.log(res)
       let data = res.data;     
       this.data = data.data.data
       this.total = data.data.totalItemNum;
      console.log(res)
      });
    },
    // 搜索
    search() {
      this.$refs.pagination.lastEmittedPage = 1
      this.cur_page = 1;
      let params = {
        appId:this.select_word,
        // appId:uuid.get(this.select_word),
        // templateId:this.templateId,
        type:this.AD_type,
        name:this.select_name,
        uuid:this.select_uuid,
        status:this.value,
        cp:this.cur_page,
        ps:this.ps
      }
       adPositiongetList(params).then(res=>{
     let data = res.data;     
       this.data = data.data.data
       this.total = data.data.totalItemNum;
      // console.log(res)
      });
    },
    searchTemplate(){
        let params = {
          // name:tpid.get(this.selectTP),
          name: this.selectTP,
          cp:this.cur_page,
          ps:this.ps
        }
     templateList(params).then(res=>{
     let data = res.data;     
       this.tableData = data.data.data
       this.total = data.data.totalItemNum;
      console.log(res)
      });
    },
     // 选择模板
    select_template(i) {
      this.indexs = i
      console.log(i)
      // this.$refs.pagination.lastEmittedPage = 1
      this.cur_page = 1;
      let params = {
        status:1,
        cp:this.cur_page,
        ps:this.ps,
        type:this.form.display_form,
      }
      templateList(params).then(res=>{
        let data = res.data;     
          this.tableData = data.data.data
          this.m_total = data.data.totalItemNum;
          // let that =this;
          // setTimeout(function() {
            this.template_dialog = true;
          // },1000)       
        });
    },
         // 选择模板
    select_template2(i) {
      this.indexs = i
      if(this.template[i].name ==''){
          this.cur_page = 1;
          let params = {
            status:1,
            cp:this.cur_page,
            ps:this.ps,
            type:this.form.display_form,
          }
          templateList(params).then(res=>{
            let data = res.data;     
              this.tableData = data.data.data
              this.m_total = data.data.totalItemNum;
              this.template_dialog = true;    
            });
      }else{
              let params = {
                  id:this.template[i].id,
                  positionId:this.form.id
                }
          modifiableByPosition(params).then(res=>{
                console.log(res)
                let data = res.data;
                if(data.code != 'A000000') {
                    this.$message.error(data.message);
                    return;
                }
                if(data.data == 1){
                        this.indexs = i
                      // this.$refs.pagination.lastEmittedPage = 1
                      this.cur_page = 1;
                      let params = {
                        status:1,
                        cp:this.cur_page,
                        ps:this.ps,
                        type:this.form.display_form,
                      }
                      templateList(params).then(res=>{
                        let data = res.data;     
                          this.tableData = data.data.data
                          this.m_total = data.data.totalItemNum;
                          this.template_dialog = true;    
                        });

                }else if(data.data == 0){
                      this.$message('此模板不可编辑')
                      return;
                }
            });
      }
    
  
    },
   
    // 模板确认
    template_save(){
         console.log(this.indexs)
         this.template[this.indexs].name = this.checked.name
         this.template[this.indexs].id = this.checked.id
        console.log(this.template)
       
        //  this.form.name = this.checked.name
         this.template_dialog = false;

    },
    handleChange(val){
       if(val) {
         this.checked = val;
       }
    },
    // 编辑
    handleEdit(index, row) {
      this.$forceUpdate()
      this.idx = index;
      let item = row;
      this.ids = row.id
      this.getList()
      this.form = {
        id:item.id,
        ADname:item.name,
        flow_Name:item.appId,
        os:item.os,
        display_form:item.type,
        name:item.templateName,
        title:item.title,
        videoWidth:item.videoWidth,
        videoHeight:item.videoHeight,
        duration:item.duration,
        picWidth:item.picWidth,
        picHeight:item.picHeight,
        forecastCtr:item.forecastCtr,
        isSupportDynamic:item.isSupportDynamic,
        screenshot:item.screenshot,
        locationX:item.locationX,
        locationY:item.locationY,
        comment:item.comment,
      };
      if(item.templateList.length <= 0){
          //  this.template.push({name:'',id:''})
          this.template =[]
      }else{
           this.template = item.templateList
      };
  
      this.editVisible = true;
    },    
    // 保存编辑
      saveEdit() {
      if(!this.form.flow_Name) {
        this.$message.error("流量源不能为空");
        return;
      }
      if(!this.form.ADname) {
        this.$message.error("广告位名称不能为空");
        return;
      }
      if(!this.form.display_form) {
        this.$message.error("展现形式不能为空");
        return;
      }
      if(!this.form.display_form) {
        this.$message.error("广告位名称不能为空");
        return;
      }
        if(this.form.display_form == 217 || this.form.display_form == 7) {
        if(this.template.length < 1){
              this.$message.error("模板不能为空");
              return;
        }
      }
      
   
     
      // let arr = []
      //  this.template.forEach(element => {
      //     arr.push(element.id) 
      // }); 
      //  let s = arr.join(",")+",";
      //   for(let i=0;i<arr.length;i++) {
      //       if(s.replace(arr[i]+",","").indexOf(arr[i]+",")>-1) {
      //           this.$message.error('模板不能重复选择')
      //           return;
      //       }
      //   } 
     
      let par = {
          id:this.form.id
      }
      if(!this.form.display_form == 7 && !this.form.display_form == 217){
           adPositionmodifiable(par).then(res=>{
            console.log(res)
            let data = res.data;
            if(data.code != 'A000000') {
                this.$message.error(data.message);
                return
            }
            if(data.data == 1){
              // this.template.forEach(element => {
              //    let  ids = element.id
              // });
              let params = {
                id:this.ids,
                name:this.form.ADname,
                appId:this.form.flow_Name,
                os:this.form.os,
                type:this.form.display_form,
                title:this.form.title,
                videoWidth:this.form.videoWidth,
                videoHeight:this.form.videoHeight,
                duration:this.form.duration,
                picWidth:this.form.picWidth,
                picHeight:this.form.picHeight,
                templateList:this.template,
                forecastCtr:this.form.forecastCtr,
                isSupportDynamic:this.form.isSupportDynamic,
                screenshot:this.form.screenshot,
                locationX:this.form.locationX,
                locationY:this.form.locationY,
                comment:this.form.comment,
            }
              adPositionupdate(params).then(res=>{
                let data = res.data;
                    if(data.code != 'A000000') {
                        this.$message.error(data.message);
                    }
                    this.editVisible = false;
                    this.search();
                });
            }else if(data.data == 0){
                this.$message.error('此广告位不可修改')
            }       
        });
      }else{
              let par = {
                id:this.ids,
                name:this.form.ADname,
                appId:this.form.flow_Name,
                os:this.form.os,
                type:this.form.display_form,
                title:this.form.title,
                videoWidth:this.form.videoWidth,
                videoHeight:this.form.videoHeight,
                duration:this.form.duration,
                picWidth:this.form.picWidth,
                picHeight:this.form.picHeight,
                templateList:this.template,
                forecastCtr:this.form.forecastCtr,
                isSupportDynamic:this.form.isSupportDynamic,
                screenshot:this.form.screenshot,
                locationX:this.form.locationX,
                locationY:this.form.locationY,
                comment:this.form.comment,
            }
            adPositionupdate(par).then(res=>{
                let data = res.data;
                    if(data.code != 'A000000') {
                        this.$message.error(data.message);
                    }
                    this.editVisible = false;
                    this.search();
                });
      }
     
     
    },
    handleDelete(index, row) {
      this.idx = index;
      this.delVisible = true;
    },

    // 新建
    create() {
      this.newVisible = true;
      this.form = {
         isSupportDynamic:0,
         os:182
      };
         this.template = [{name:'',id:''}],
      
      this.selectTP = ''
    },
    // 确认
    saveNew() {
      if(!this.form.ADname) {
        this.$message.error("广告位名称不能为空");
        return;
      }
      
      if(!this.form.flow_Name) {
        this.$message.error("APP不能为空");
        return;
      }
      if(!this.form.display_form) {
        this.$message.error("广告形式不能为空");
        return;
      }
      if(!this.form.forecastCtr) {
        this.$message.error("预估CTR不能为空");
        return;
      }
   
      if(this.form.display_form == 4) {
        if(this.form.videoWidth == undefined || this.form.videoHeight == undefined || this.form.duration == undefined){
              this.$message.error("视频不能为空");
              return;
        }
      }
      if(this.form.display_form == 5 || this.form.display_form == 6 || this.form.display_form == 218) {
        if(this.form.picWidth == undefined && this.form.picHeight == undefined){
              this.$message.error("图片不能为空");
              return;
        }
      }
      if(this.form.display_form == 217 || this.form.display_form == 7) {
        if(this.template.length < 1){
              this.$message.error("模板不能为空");
              return;
        }
      }
      if(this.form.display_form == 6) {
       if( (this.form.videoWidth ==  undefined|| this.form.videoHeight == undefined || this.form.duration == undefined) && (this.form.picWidth == undefined|| this.form.picHeight == undefined)){
              this.$message.error("视频或图片不能同时为空");
              return;
        }
      }
         
      // let arr = []
      //  this.template.forEach(element => {
      //     arr.push(element.id) 
      // }); 
      //  let s = arr.join(",")+",";
      //   for(let i=0;i<arr.length;i++) {
      //       if(s.replace(arr[i]+",","").indexOf(arr[i]+",")>-1) {
      //           this.$message.error('模板不能重复选择')
      //           return;
      //       }
      //   } 
    
    
      let params = {
        name:this.form.ADname,
        appId:this.form.flow_Name,
        os:this.form.os,
        type:this.form.display_form,
        title:this.form.title,
        videoWidth:this.form.videoWidth,
        videoHeight:this.form.videoHeight,
        duration:this.form.duration,
        picWidth:this.form.picWidth,
        picHeight:this.form.picHeight,
        templateList:this.template,
        forecastCtr:this.form.forecastCtr,
        isSupportDynamic:this.form.isSupportDynamic,
        screenshot:this.form.screenshot,
        locationX:this.form.locationX,
        locationY:this.form.locationY,
        comment:this.form.comment,
        // flowPositionId:this.form.flowPositionId
      }
    adPositionadd(params).then(res=>{
      let data = res.data;     
         if(data.code != 'A000000') {
              this.$message.error(data.message);
          }
          this.newVisible = false;
          this.getList()
      });
     
    },
    saveAD() {
      this.newaddAD = false;
    },
    // 确定删除
    deleteRow() {
      this.tableData.splice(this.idx, 1);
      this.$message.success("删除成功");
      this.delVisible = false;
    },

       // 查看报告
    look_report() {
      this.$router.push({path:'advertisingreport'})
    },
    handleSuccess(res, file) {
            let data = res;
                if (data.code != 'A000000') {
                    this.$message.error(data.message);
                    return;
                } else {
                    this.form.screenshot = data.data.url
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
  width: 150px;
  display: inline-block;
}
/* .mr10{
  width: 100px;
} */
.del-dialog-cnt {
  font-size: 16px;
  text-align: center;
}
.span{
  display: inline-block
}
.div{
  padding-bottom: 10px
}
.border{
  padding-bottom: 20px
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


