<template>
    <div class="table">
        <div class="container">
            <div class="handle-box">
                <!-- <el-input v-model="select_name" placeholder="渠道名称" class="handle-input mr10"></el-input> -->
                <el-select v-model="appId" placeholder="APP名称" class="handle-input mr10">
                    <el-option
                      v-for="item in optionAppName2"
                      :key="item.id"
                      :label="item.appName"
                      :value="item.id">
                    </el-option>
                </el-select>
                <!-- <el-select v-model="AppVersion" placeholder="版本号" class="handle-input mr10"  @focus=getAppVersion>
                  <el-option
                    v-for="item in optionsAppVersion"
                    :key="item.id"
                    :label="item.name"
                    :value="item.id">
                  </el-option>
                </el-select> -->
                <el-select v-model="AppChannel" placeholder="渠道号" class="handle-input mr10">
                  <el-option
                    v-for="item in optionsAppChannel2"
                    :key="item.id"
                    :label="item.cname"
                    :value="item.id">
                  </el-option>
                </el-select>
                <el-select v-model="optionsState" placeholder="状态" class="inline-input mr10">
                  <el-option
                    v-for="item in options_"
                    :key="item.value"
                    :label="item.label"
                    :value="item.value">
                  </el-option>
                </el-select>
                <el-button type="primary" icon="search" @click="search">搜索</el-button>
                <!-- <el-button type="primary" icon="search" @click="EditRecord" v-if="this.readonly != 1 ">批量操作</el-button>
                <el-button type="primary" icon="search" @click="createTime" v-if="this.readonly != 1 ">特殊日期设置</el-button> -->
                <el-button type="primary" icon="search" @click="Newflowcontrol">新建</el-button>
            </div>
            <el-table :data="tableData" border style="width: 100%" ref="multipleTable">
                <el-table-column prop="app_name" label="APP名称"  width="100" align="center">
                </el-table-column>
                <el-table-column prop="channel_name" label="渠道名称"  width="100" align="center">
                </el-table-column>
                <el-table-column prop="pv_hb_name" label="PV环比预警浮动值"  width="" align="center">
                </el-table-column>
                <el-table-column prop="pv_tb_name" label="PV同比预警浮动值"  width="" align="center">
                </el-table-column>
                <el-table-column prop="pv_tj_name" label="PV预警条件"  width="" align="center">
                </el-table-column>
                <el-table-column prop="clk_hb_name" label="点击环比预警浮动值"  width="" align="center">
                </el-table-column>
                <el-table-column prop="clk_tb_name" label="点击同比预警浮动值"  width="" align="center">
                </el-table-column>
                <el-table-column prop="clk_tj_name" label="点击预警条件"  width="" align="center">
                </el-table-column>
                <el-table-column  label="状态"  width="" align="center" v-if="this.readonly !=1">
                  <template slot-scope="scope">
                     <el-switch v-model="scope.row.status" :active-value=1 :inactive-value=0 @change="changeStatus($event,scope.row)"></el-switch>
                  </template>
                </el-table-column>
                <el-table-column label="操作" width="150" align="center">
                    <template slot-scope="scope">
                        <el-button size="small" type="success" @click="handleEdit(scope.$index, scope.row)">编辑</el-button>
                        <!-- <el-button size="small" type="danger" @click="handleDelete(scope.$index, scope.row)">删除</el-button> -->
                        <!-- <el-button size="small" type="success" @click="handAD(scope.$index, scope.row)">添加广告位</el-button> -->
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
        <!-- 新建弹出框 -->
        <el-dialog title="新建" :visible.sync="QX_Visible" width="700px">
              <el-form ref="form" :model="form"  label-width="25%" :label-position="labelPosition">
                  <el-form-item label="APP名称">
                        <el-select v-model="form.AppName" placeholder="APP名称" class="handle-input mr10" @focus=getAppName @change="GetInit">
                            <el-option
                              v-for="item in optionAppName"
                              :key="item.id"
                              :label="item.appName"
                              :value="item.id">
                            </el-option>
                        </el-select>
                    </el-form-item>
                    <el-form-item label="渠道名称">
                        <el-select v-model="form.channelId" class="handle-input mr10"  @focus=getAppChannel  @change="GetInit">
                            <el-option
                                v-for="item in optionsAppChannel"
                                :key="item.id"
                                :label="item.cname"
                                :value="item.id">
                            </el-option>
                        </el-select>
                    </el-form-item>
                    <el-form-item label="前1日单日PV:">
                          <span>{{this.pv_one}}</span>
                    </el-form-item>
                    <el-form-item label="7日前单日PV:">
                          <span>{{this.pv_seven}}</span>
                    </el-form-item>
                    <el-form-item label="PV环比浮动值">
                        <el-input-number v-model="form.pvHb" :min="0" :max="10000" ></el-input-number>%
                    </el-form-item>
                    <el-form-item label="PV同比浮动值">
                        <el-input-number v-model="form.pvTb" :min="0" :max="10000" ></el-input-number>%
                    </el-form-item>
                    <el-form-item label="环比同比预警条件:">
                          <el-radio v-model="form.pvTj" :label="232">任意成立</el-radio>
                          <el-radio v-model="form.pvTj" :label="233">同时成立</el-radio>
                    </el-form-item>
                    <el-form-item label="前1日单日点击:">
                          <span>{{this.clk_one}}</span>
                    </el-form-item>
                    <el-form-item label="七日前单日点击:">
                          <span>{{this.clk_seven}}</span>
                    </el-form-item>
                    <el-form-item label="点击环比浮动值">
                            <el-input-number v-model="form.clkHb" :min="0" :max="10000" ></el-input-number>%
                    </el-form-item>
                    <el-form-item label="点击同比浮动值">
                            <el-input-number v-model="form.clkTb" :min="0" :max="10000" ></el-input-number>%
                    </el-form-item>
                    <el-form-item label="环比同比预警条件:">
                          <el-radio v-model="form.clkTj" :label="232">任意成立</el-radio>
                          <el-radio v-model="form.clkTj" :label="233">同时成立</el-radio>
                    </el-form-item>
                    <el-form-item label="报警接收地址">
                        <el-input type="textarea" :rows="2" v-model="form.email"></el-input>
                    </el-form-item>
                    <el-form-item label="状态:">
                          <el-radio v-model="form.status" :label="1">启用</el-radio>
                          <el-radio v-model="form.status" :label="0">停用</el-radio>
                    </el-form-item>
                </el-form>
                <span slot="footer" class="dialog-footer">
                    <el-button @click="QX_Visible = false">取 消</el-button>
                    <el-button type="primary" @click="QX_save">确 定</el-button>
                </span>
            
        </el-dialog>

        <!-- 编辑弹出框 -->
        <el-dialog title="编辑" :visible.sync="editVisible" width="700px">
          <el-form ref="form" :model="form" label-width="25%"  :label-position="labelPosition">
                 <el-form-item label="APP名称">
                        <el-select v-model="form.AppName" placeholder="APP名称" disabled class="handle-input mr10" @focus=getAppName> 
                            <el-option
                              v-for="item in optionAppName"
                              :key="item.id"
                              :label="item.appName"
                              :value="item.id">
                            </el-option>
                        </el-select>
                    </el-form-item>
                    <el-form-item label="渠道名称">
                        <el-select v-model="form.channelId" class="handle-input mr10" disabled @focus=getAppChannel  @change="GetInit">
                            <el-option
                                v-for="item in optionsAppChannel"
                                :key="item.id"
                                :label="item.cname"
                                :value="item.id">
                            </el-option>
                        </el-select>
                    </el-form-item>
                    <el-form-item label="前1日单日PV:">
                          <span>{{this.pv_one}}</span>
                    </el-form-item>
                    <el-form-item label="7日前单日PV:">
                          <span>{{this.pv_seven}}</span>
                    </el-form-item>
                    <el-form-item label="PV环比浮动值">
                        <el-input-number v-model="form.pvHb"  :max="10000" ></el-input-number>%
                    </el-form-item>
                    <el-form-item label="PV同比浮动值">
                        <el-input-number v-model="form.pvTb"  :max="10000" ></el-input-number>%
                    </el-form-item>
                    <el-form-item label="环比同比预警条件:">
                          <el-radio v-model="form.pvTj" :label="232">任意成立</el-radio>
                          <el-radio v-model="form.pvTj" :label="233">同时成立</el-radio>
                    </el-form-item>
                    <el-form-item label="前1日单日点击:">
                          <span>{{this.clk_one}}</span>
                    </el-form-item>
                    <el-form-item label="七日前单日点击:">
                          <span>{{this.clk_seven}}</span>
                    </el-form-item>
                    <el-form-item label="点击环比浮动值">
                            <el-input-number v-model="form.clkHb"  :max="10000" ></el-input-number>%
                    </el-form-item>
                    <el-form-item label="点击同比浮动值">
                            <el-input-number v-model="form.clkTb" :max="10000" ></el-input-number>%
                    </el-form-item>
                    <el-form-item label="环比同比预警条件:">
                          <el-radio v-model="form.clkTj" :label="232">任意成立</el-radio>
                          <el-radio v-model="form.clkTj" :label="233">同时成立</el-radio>
                    </el-form-item>
                    <el-form-item label="报警接收地址">
                        <el-input type="textarea" :rows="2" v-model="form.email"></el-input>
                    </el-form-item>
                    <el-form-item label="状态:">
                          <el-radio v-model="form.status" :label="1">启用</el-radio>
                          <el-radio v-model="form.status" :label="0">停用</el-radio>
                    </el-form-item>
          </el-form>
            <span slot="footer" class="dialog-footer">
                <el-button @click="editVisible = false">取 消</el-button>
                <el-button type="primary" @click="saveEdit" v-if="this.readonly !=1">确 定</el-button>
            </span>
        </el-dialog>

    </div>
</template>

<script>

import { flowsourcegetall } from "@/api/Api.js";
import { flowsourceadd } from "@/api/Api.js";
import { flowsourceget } from "@/api/Api.js";
import { flowsourcedel } from "@/api/Api.js";
import { flowsourcegetusers } from "@/api/Api.js";
import { flowsourceupdate } from "@/api/Api.js";
import { flowsourcegetgrand } from "@/api/Api.js";
import { flowsourcegrand } from "@/api/Api.js";
import { flowsourcemovegrand } from "@/api/Api.js";

import { prohibitedateget,prohibitedateset,appgetall,AppVersionselect,AppChannelselect,flowconsumersdkList,flowcontrolpage,
  flowcontrolbatchinsert,flowcontrol,flowcontrolflowswitch,flowcontrolbatchupdate,warningsettingpage,
  warningsetting,warningsettingInfo,warninginitpage,warningsettingstatus
} from "@/api/Api.js";

const options = [];
var idCookies = new Map()
export default {
  name: "basetable",
  data() {
    return {
      labelPosition: 'left',
      checkList:[],
      checked:false,
      getMonth:'',
      table_data:[],
      days:[],
      appId:'',
      AppVersion:'',
      AppChannel:'',
      ADplatform:'',
      appIds:[],
      AppVersions:[],
      AppChannels:[],
      State:1,
      select_name:'',
      app:'',
      bb:'',
      qd:'',
      options_plan:null,
      select_plan:[],
      select_word:'',
      tableData: [],
      cur_page: 1,
      total: 1,
      ps:10,
      companyName: "",
      del_list: [],
      is_search: false,
      editVisible: false,
      QX_Visible: false,
      delVisible: false,
      newVisible: false,
      newaddAD: false,
      template_dialog: false,
      isIndeterminate:true,
          checkAll:false,
      currentRow: null,
      runState:'',
      state1:'',
      dynamicTags:[],
      form: {
        id: "",
        mediaName: "",
        mediaType: "",
        type: '',
        joinType:'',
        websiteUrl:'',
        companyName:'',
        companyAddr:'',
        companyLinkman:'',
        linkmanTel:'',

        channelId:'',
        AppName:'',
        dimension:'',
        nexus:'',
        time:'',
        pvValue:'',
        clkValue:'',
        status:'',
        email:'',
        pvHb:0,
        pvTb:0,
        pvTj:'',
        clkTb:0,
        clkHb:0,
        clkTj:'',
      },
      pv_one:'',
      pv_seven:'',
      clk_one:'',
      clk_seven:'',
      idx: -1,
      flow_type: 1,
      type: 1,
      system: 1,
      Access: "API",
      display_form: 1,
      
     
      optionAppName: [
        {
          value: "",
          label: ""
        },
      ],
      optionAppName2: [
        {
          value: "",
          label: ""
        },
      ],
      optionsAppVersion: [
        {
          value: "",
          label: ""
        },
      ],
      optionsAppChannel: [
        {
          value: "",
          label: ""
        },
      ],
      optionsAppChannel2: [
        {
          value: "",
          label: ""
        },
      ],
      optionsSdkList: [
        {
          value: "",
          label: ""
        },
      ],
       options_: [
         {
          value: '',
          label: "全部状态"
        },
        {
          value: 0,
          label: "未开启"
        },
        {
          value: 1,
          label: "开启"
        },
      ],
      value: "",
      optionsState:'',
      readonly:'',

    };
  },
  // created() {
  //   this.getData();
  // },
  created() {
       this.readonly = this.$store.state.map.get(this.$store.state.currentPath);
       this.getList();
      this.date = new Date();
      this.month = this.date.getMonth() + 1;
      this.year = this.date.getFullYear();
      //输出所选月份的第一天
      var start = this.year + "-" + this.month + "-" + "1";
      //获取当前
      var date = new Date(start);
      var year = date.getFullYear();
      var month = date.getMonth()+1;
      var d = new Date(year,month,0);   //下一个月的前一天
      this.days = this.getDays();
      var  m = (this.date.getMonth()+1)<10?"0"+(this.date.getMonth()+1):(this.date.getMonth()+1)
      //  var  d = this.date.getDate()<10?"0"+this.date.getDate():this.date.getDate()
      this.getMonth=this.date.getFullYear()+""+m
      this.getAppChannel2()
      this.getAppName2()
      
  },

  methods: {
      handleCheckAllChange(val) {
       console.log(val)
        this.select_plan = val ? options: [];
        this.isIndeterminate = false;
      },
    getAppName2(){
        appgetall().then(res=>{
          console.log(res)
            let data = res.data; 
             if(data.code != 'A000000') {
              this.$message.error(data.message);
              return;
            }   
            this.optionAppName2 = data.data 
              let  obj = {
                  id: '',
                  appName: '全部APP名称'
              }
            this.optionAppName2.push(obj)   
          });
    },
           // 版本号拉框
    getAppVersion(){
        AppVersionselect().then(res=>{
          console.log(res)
            let data = res.data; 
             if(data.code != 'A000000') {
              this.$message.error(data.message);
              return;
            }    
            this.optionsAppVersion = data.data
          });
    },
             // 渠道号拉框
    getAppChannel(){
        AppChannelselect().then(res=>{
          console.log(res)
            let data = res.data; 
             if(data.code != 'A000000') {
              this.$message.error(data.message);
              return;
            }    
            this.optionsAppChannel = data.data
          });
    },
              // app名称下拉框
    getAppName(){
        appgetall().then(res=>{
          console.log(res)
            let data = res.data; 
             if(data.code != 'A000000') {
              this.$message.error(data.message);
              return;
            }    
            this.optionAppName = data.data
          });
    },
    getAppChannel2(){
        AppChannelselect().then(res=>{
          console.log(res)
            let data = res.data; 
             if(data.code != 'A000000') {
              this.$message.error(data.message);
              return;
            }    
            this.optionsAppChannel2 = data.data
            let  obj = {
                  id: '',
                  cname: '全部渠道号'
              }
            this.optionsAppChannel2.push(obj)   
          });
    },
    GetInit(){
        if(!this.form.AppName){
            return
        }
        if(!this.form.channelId){
            return
        }
         let params = {
                aid:this.form.AppName,
                cid:this.form.channelId
            } 
            warninginitpage(params).then(res=>{
                    console.log(res)
                      let data = res.data; 
                      if(data.code != 'A000000') {
                        this.$message.error(data.message);
                        return;
                      }    
                      this.pv_one = data.data.pv_one
                      this.pv_seven = data.data.pv_seven
                      this.clk_one = data.data.clk_one
                      this.clk_seven = data.data.clk_seven
                    });
    },
    getSdkList(){
        flowconsumersdkList().then(res=>{
          console.log(res)
            let data = res.data; 
             if(data.code != 'A000000') {
              this.$message.error(data.message);
              return;
            }    
            this.optionsSdkList = data.data
            this.options_plan = data.data
             data.data.forEach(element => {
                options.push(element.id)
            });
          });
    },
    getDays() {
      //输出所选月份的第一天
      var start = this.year + "-" + this.month + "-" + "1";
      //获取当前
      var date = new Date(start);
      var year = date.getFullYear();
      var month = date.getMonth()+1;
      var d = new Date(year,month,0);   //下一个月的前一天
      return d.getDate();
    },
    clickColor(item,i){
      if(item===2) {
         return;
      }
      if(item == 1) {
        item =0;
      }else {
        item =1;
      }
      this.days[i] = item;
      idCookies.set(this.getMonth,this.days)
      this.$forceUpdate()
      // this.days = Object.assign([],this.days);
      
      console.log(this.days)
    },
    addMonth(){
      if(this.month<=12) {
        if(this.month == 12) {
           this.year = this.year +1;
           this.month = 1;
        }else {
          this.month = this.month+1;
        }
      }
      var  m = (this.month)<10?"0"+(this.month):(this.month)
      this.getMonth=this.year+""+m
      this.days  = this.getDays();
      console.log(this.getMonth);
      this.createTime();
        this.$forceUpdate()
    },
    reduceMonth(){
      if(this.month>=1) {
        if(this.month == 1) {
           this.year = this.year -1;
           this.month = 12;
        }else {
          this.month = this.month-1;
        }
      }
      var  m = (this.month)<10?"0"+(this.month):(this.month)
      this.getMonth=this.year+""+m
      this.days  = this.getDays();
      this.createTime();
        this.$forceUpdate()
    },

    // 开关
    changeStatus(val,row){
             let params = {
                id:row.id,
                status:row.status,
            }
     warningsettingstatus(params).then(res=>{
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
          appId:this.appId,
          versionId:this.AppVersion,
          channelId:this.AppChannel,
          channelId:this.optionsState,
          adPlatformIds:this.ADplatform,
          status:this.optionsState,
          currentPage:this.cur_page,
          pageSize:this.ps
       }
      flowcontrolpage(params).then(res=>{
      let data = res.data;
        this.tableData = data.data.data
      });
    },

     getList() {
      let params = {currentPage:this.cur_page,pageSize:this.ps}
      warningsettingpage(params).then(res=>{
        console.log(res)
        let data = res.data;
          if(data.code != 'A000000') {
              this.$message.error(data.message);
              return;
          }
        this.tableData = data.data.data
        this.total = data.data.totalItemNum;
      });
    },
     // 搜索
    search() {
      this.$refs.pagination.lastEmittedPage = 1
      this.cur_page = 1;
      let params = {
          appId:this.appId,
          channelId:this.AppChannel,
          status:this.optionsState,
          currentPage:this.cur_page,
          pageSize:this.ps
       }
      warningsettingpage(params).then(res=>{
      let data = res.data;
        this.tableData = data.data.data
        this.total = data.data.totalItemNum;
      });
    },
 
    // 编辑
    handleEdit(index, row) {
      this.idx = index;
      let item = row;
      this.ids = item.id
      let params = {
            id:item.id,
          }
     warningsettingInfo(params).then(res=>{
       console.log(res)
         let data = res.data;
          if(data.code != 'A000000') {
              this.$message.error(data.message);
              return;
          }
          this.form.AppName = data.data.appId
          this.form.channelId = data.data.channelId
          this.form.pvHb = data.data.pvHb
          this.form.pvTb = data.data.pvTb
          this.form.pvTj = data.data.pvTj
          this.form.clkHb = data.data.clkHb
          this.form.clkTb = data.data.clkTb
          this.form.clkTj = data.data.clkTj
          this.form.email = data.data.email
          this.form.status = data.data.status
          this.getAppName()
          this.getAppChannel()
          this.GetInit()
          this.editVisible = true;
  
      });
    },
    // 保存编辑
    saveEdit() {
       if(!this.form.pvHb && !this.form.pvTb && !this.form.clkHb && !this.form.clkTb) {
        this.$message.error("浮动值不能同时为空");
        return;
      }
       let params = {
            id:this.ids,
            appId:this.form.AppName,
            channelId:this.form.channelId,
            pvHb:this.form.pvHb,
            pvTb:this.form.pvTb,
            pvTj:this.form.pvTj,
            clkHb:this.form.clkHb,
            clkTb:this.form.clkTb,
            clkTj:this.form.clkTj,
            email:this.form.email,
            status:this.form.status,
            }
     warningsetting(params).then(res=>{
     let data = res.data;
          if(data.code != 'A000000') {
              this.$message.error(data.message);
          }
          this.editVisible = false;
          this.search();
      });
    },
     // 批量操作
    EditRecord() {
      this.getSdkList()
      this.appIds =[],
      this.AppVersions =[],
      this.AppChannels =[],
      this.select_plan =[],
      this.delVisible = true;
    },
    FlowOpen(){
        let params = {
            aids:this.appIds.join(','),
            vids:this.AppVersions.join(','),
            cids:this.AppChannels.join(','),
            status:1,
            }
     flowcontrolbatchupdate(params).then(res=>{
     let data = res.data;
          if(data.code != 'A000000') {
              this.$message.error(data.message);
          }
      
      });
    },
    FlowClose(){
        let params = {
            aids:this.appIds.join(','),
            vids:this.AppVersions.join(','),
            cids:this.AppChannels.join(','),
            status:0,
            }
     flowcontrolbatchupdate(params).then(res=>{
     let data = res.data;
          if(data.code != 'A000000') {
              this.$message.error(data.message);
          }
 
      });
    },
    batchUpdate(){
        let params = {
            aids:this.appIds.join(','),
            vids:this.AppVersions.join(','),
            cids:this.AppChannels.join(','),
            adPlatformIds:this.select_plan.join(','),
            }
     flowcontrolbatchupdate(params).then(res=>{
     let data = res.data;
          if(data.code != 'A000000') {
              this.$message.error(data.message);
          }
   
      });
    },

    // 日期设置
    createTime() {
      let params = {
         month:this.getMonth,
       }
      prohibitedateget(params).then(res => {
        console.log(res)
        // this.table_data = res.data.data
        // this.table_data[0].name = res.data.data.name;
         this.days = res.data.data.time;
         this.days = idCookies.get(this.getMonth)?idCookies.get(this.getMonth):res.data.data.time
        // this.$forceUpdate()
        // this.days = Object.assign([], this.days);
      });
      this.newVisible = true;
    },
    // 确认
    saveTime() {
      let positionTime = []
        idCookies.forEach(function (value, key, map) {
         console.log(value)
         console.log(key)
          var value = value
          var key = key
            let obj = {}
            obj[key] = value
            positionTime.push(obj)
        });
       prohibitedateset(positionTime).then(res=>{
          let data = res.data;
                // console.log(res)
            if(data.code != 'A000000') {
                this.$message.error(data.message);
            }
            this.newVisible = false;
          });
        },

     // 新建
    Newflowcontrol(){
        this.appIds=[],
        this.AppVersions=[],
        this.AppChannels=[],
        this.select_plan=[],
        this.State =1,
        this.form = {
          pvTj:232,
          clkTj:232,
          status:1,
          pvHb:0,
          pvTb:0,
          clkHb:0,
          clkTb:0,
        }
        // this.getSdkList()
        this.QX_Visible = true;

    },
     // 保存
    QX_save(){
      if(!this.form.AppName) {
        this.$message.error("APP名称不能为空");
        return;
      }
       if(!this.form.channelId) {
        this.$message.error("渠道名称不能为空");
        return;
      }
            // 参数判断
      if(this.form.pvHb == 0 && this.form.pvTb   == 0 && this.form.clkHb   == 0 && this.form.clkTb   == 0) {
        this.$message.error("浮动值不能同时为空");
        return;
      }
       if(!this.form.email) {
        this.$message.error("接收地址不能为空");
        return;
      }
         let params = {
            appId:this.form.AppName,
            channelId:this.form.channelId,
            pvHb:this.form.pvHb,
            pvTb:this.form.pvTb,
            pvTj:this.form.pvTj,
            clkHb:this.form.clkHb,
            clkTb:this.form.clkTb,
            clkTj:this.form.clkTj,
            email:this.form.email,
            status:this.form.status,
            }
        warningsetting(params).then(res=>{
          console.log(res)
            let data = res.data; 
             if(data.code != 'A000000') {
              this.$message.error(data.message);
              return;
            }    
              this.getList()
              this.QX_Visible = false;
          });

    },
    // 模糊搜索权限
    querySearch(queryString, cb) {
       let params = {
            uname:this.state1,
            }
      flowsourcegetusers(params).then(res=>{
     let data = res.data;
          console.log(data.data)
          // 调用 callback 返回建议列表的数据
        cb(data.data);
       if(data.code != 'A000000') {
          this.$message.error(data.message);
          }
        });
      },
       // 添加
      handleSelect(item) {
        console.log(item);
         let params = {
            aid:this.id,
            uid:item.id
            }
        flowsourcegrand(params).then(res=>{
      let data = res.data;
            // console.log(res)
        if(data.code != 'A000000') {
            this.$message.error(data.message);
          }
        let parm =  {
         aid:this.id,
          }
        flowsourcegetgrand(parm).then(res=>{
      let data = res.data;
        if(data.code != 'A000000') {
            this.$message.error(data.message);
        }
          this.dynamicTags = data.data
        console.log(this.dynamicTags);
         });
       });
      },
  }
};
</script>

<style scoped>
.handle-box {
  margin-bottom: 10px;
}

.handle-select {
  width: 120px;
}

.handle-input {
  width: 200px;
  display: inline-block;
}
.del-dialog-cnt {
  font-size: 16px;
  text-align: center;
}
 .el-tag + .el-tag {
    margin-left: 10px;
  }
  .div{
  padding: 20px;

}
.inline-input{
  width: 160px;
}
.bg_color{
  background: silver;
}
.table{
  width: 100%;
}
.table{
  border-collapse: collapse;
  table-layout: fixed;
  border-collapse: separate;
}
thead{
    color: #909399;
    font-weight: 500;
}
table,td {
   text-align: center;
   height: 32px;
   overflow: hidden;
   /* border: 1px solid rgba(0, 0, 0, 0.336); */
   border-left:1px solid #909399;border-top:1px solid #909399
}
.el-date-editor{
  border: none
}
.bgFalse{
  width: 100%;
  height: 100%;
    background: #909399
}

.bgtrue{
  width: 100%;
  height: 100%;
  background: #258cd3
}
.bgWhite{
  width: 100%;
  height: 100%;
  background: white
}
.border{
    width: 100%;
    height: 2px;
    background: silver
}
.mr10{
    width: 150px;
}
</style>
<style>
  .must::before{
    content: "*";
    color: #f56c6c;
    margin-right: 4px;
  }

</style>
