
<template>
    <div class="box">
      <div v-show="this.index == 1">
        <div class="block">
           <div class="header-select">
                 <template>
                      <el-tag size="medium">广告平台:</el-tag>
                       <el-select v-model="classify" multiple placeholder="请选择" @change="getname">
                            <el-option
                                v-for="item in optionsTypeAD"
                                :key="item.id"
                                :label="item.consumerName"
                                :value="item.id">
                            </el-option>
                        </el-select>
                      <el-tag size="medium">日期:</el-tag>
                         <el-date-picker
                            v-model="startDay"
                            type="date"
                            placeholder="选择日期"
                            format="yyyy 年 MM 月 dd 日"
                            value-format="yyyyMMdd"
                            :picker-options="pickerOptions1"
                            >
                        </el-date-picker> -
                        <el-date-picker
                            v-model="endDay"
                            type="date"
                            placeholder="选择日期"
                            format="yyyy 年 MM 月 dd 日"
                            value-format="yyyyMMdd"
                            :picker-options="pickerOptions1"
                            >
                        </el-date-picker>
                      <el-button type="primary" icon="search" @click="search">搜索</el-button>
                      <el-button type="primary" icon="search" @click="edit">导入</el-button>
                </template>
            </div>
            <div>
               <el-select v-model="appIds" multiple placeholder="APP名称" class="handle-input" @focus=getAppName @change="getOSName"  @remove-tag="deleteTag">
                        <el-option
                          v-for="item in optionAppName"
                          :key="item.id"
                          :label="item.appName"
                          :value="item.id">
                        </el-option>
                </el-select>
                <el-select v-model="systemAD" multiple placeholder="系统广告位" @focus=getOSName @change="getplatformAD"  @remove-tag="deleteTag2">
                            <el-option
                                v-for="item in optionsOs"
                                :key="item.id"
                                :label="item.name"
                                :value="item.id">
                            </el-option>
                </el-select>
                <el-select v-model="platformAD" multiple placeholder="平台广告位"  @focus=getplatformAD>
                            <el-option
                                v-for="item in optionsPlatform"
                                :key="item.consumerPositionId"
                                :label="item.consumerPositionName"
                                :value="item.consumerPositionId">
                            </el-option>
                </el-select>
                <el-select v-model="line" clearable placeholder="分列显示" @change="GetSeach">
                            <el-option
                                v-for="item in optionsLine"
                                :key="item.value"
                                :label="item.label"
                                :value="item.value">
                            </el-option>
                </el-select>
            </div>
        </div>

        <!-- ECharts图表测试 -->
        <div id="charts" style="width:'100%',height:'500px'">
            <div id="main" :style="{width:'100%',height:'500px'}"></div>
        </div>
        <div class="handle-box">
             <el-select v-model="custom" multiple placeholder="自定义显示" @change="handCustom">
                            <el-option
                                v-for="item in optionsCustom"
                                :key="item.value"
                                :label="item.label"
                                :value="item.value">
                            </el-option>
             </el-select>
             <el-button type="primary" icon="search" @click="exportExcel">导出</el-button>
        </div>
        <el-tag size="medium">广告平台数据:</el-tag>
        <el-table :data="tableData" border style="width: 100%" ref="multipleTable"  key="tableDataInstall" id="allTable">
                <el-table-column prop="itemId" label="ID" >
                </el-table-column>
                <el-table-column prop="mediaName" label="广告平台名称" v-if="this.line == 'type'">
                </el-table-column>
                <el-table-column prop="appName" label="APP名称" v-if="this.line == 'app_id'">
                </el-table-column>
                <el-table-column prop="adpName" label="系统广告位" v-if="this.line == 'pid'">
                </el-table-column>
                <el-table-column prop="positionName" label="平台广告位" v-if="this.line == 'position_id'">
                </el-table-column>
                <el-table-column prop="req" label="请求量"  v-if="this.custom.indexOf('req') > -1">
                </el-table-column>
                <el-table-column prop="reqUV" label="请求UV"  v-if="this.custom.indexOf('reqUV') > -1">
                </el-table-column>
                <el-table-column prop="exp" label="展现量" v-if="this.custom.indexOf('exp') > -1">
                </el-table-column>
                <el-table-column prop="expUV" label="展现UV" v-if="this.custom.indexOf('expUV') > -1">
                </el-table-column>
                <el-table-column prop="fr" label="展现率%" v-if="this.custom.indexOf('fr') > -1">
                </el-table-column>
                <el-table-column prop="clk" label="点击量" v-if="this.custom.indexOf('clk') > -1">
                </el-table-column>
                <el-table-column prop="ctr" label="点击率%" v-if="this.custom.indexOf('ctr') > -1">
                </el-table-column>
                 <el-table-column prop="expPer" label="人均曝光" v-if="this.custom.indexOf('expPer') > -1">
                </el-table-column>
                 <el-table-column prop="expIncome" label="曝光收入" v-if="this.custom.indexOf('expIncome') > -1">
                </el-table-column>
                 <el-table-column prop="clkIncome" label="点击收入" v-if="this.custom.indexOf('clkIncome') > -1">
                </el-table-column>
                 <el-table-column prop="income" label="收入" v-if="this.custom.indexOf('income') > -1">
                </el-table-column>
                 <el-table-column prop="cpm" label="cpm" v-if="this.custom.indexOf('cpm') > -1">
                </el-table-column>
                 <el-table-column prop="cpc" label="cpc" v-if="this.custom.indexOf('cpc') > -1">
                </el-table-column>
                <el-table-column prop="ecpm" label="ecpm" v-if="this.custom.indexOf('ecpm') > -1">
                </el-table-column>
                <el-table-column prop="clkDesire" label="点击意愿" v-if="this.custom.indexOf('clkDesire') > -1">
                </el-table-column>
                <el-table-column label=""  width="150" align="center">
                  <template slot-scope="scope">
                    <el-button size="mini" type="success" @click="handleEdit(scope.$index, scope.row,)">详细</el-button>
                  </template>
                </el-table-column>
         </el-table>
         </div>
         <div v-show="this.index == 2">
            <div class="header-select">
                 <template>
                      <el-button type="primary" icon="search" @click="goback">返回</el-button>
                      <el-button type="primary" icon="search" @click="exportExcel2">导出</el-button> 
                </template>
            </div>
            <el-table :data="tableData2" border style="width: 100%" ref="multipleTable"  key="tableDataInstall2" id="table">
               <el-table-column prop="date" label="日期">
                </el-table-column>
                <!-- <el-table-column prop="itemName" label="平台ID">
                </el-table-column> -->
                <el-table-column prop="req" label="请求量">
                </el-table-column>
                <el-table-column prop="reqUV" label="请求UV" >
                </el-table-column> 
                <el-table-column prop="exp" label="曝光量">
                </el-table-column>
                <el-table-column prop="expUV" label="expUV">
                </el-table-column>
                <el-table-column prop="fr" label="填充率%">
                </el-table-column>
                <el-table-column prop="clk" label="点击量">
                </el-table-column>
                <el-table-column prop="ctr" label="点击率%">
                </el-table-column>
                 <el-table-column prop="expPer" label="人均曝光">
                </el-table-column>
                 <el-table-column prop="expIncome" label="曝光收入">
                </el-table-column>
                 <el-table-column prop="clkIncome" label="点击收入">
                </el-table-column>
                <el-table-column prop="income" label="总收入">
                </el-table-column>
                 <el-table-column prop="cpm" label="cpm">
                </el-table-column>
                 <el-table-column prop="cpc" label="cpc">
                </el-table-column>
                <el-table-column prop="ecpm" label="ecpm">
                </el-table-column>
                <el-table-column prop="clkDesire" label="点击意愿">
                </el-table-column>
            </el-table>
         </div>
        <el-dialog title="导入" :visible.sync="newVisible" width="40%">
            <el-form ref="form" :model="form" status-icon label-width="100px">
                <el-form-item label="选择模板:">
                    <el-select v-model="line1" clearable placeholder="">
                            <el-option
                                v-for="item in optionsType"
                                :key="item.value"
                                :label="item.label"
                                :value="item.value">
                            </el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="模板下载">
                    <a href="https://iwanvi.oss-cn-beijing.aliyuncs.com/document/reportTemp/%E5%B9%BF%E7%82%B9%E9%80%9A.xls">
                         <el-button type="primary" >广点通</el-button>
                    </a>
                    <a href="https://iwanvi.oss-cn-beijing.aliyuncs.com/document/reportTemp/%E5%A4%B4%E6%9D%A1.xls">
                         <el-button type="primary" >头条</el-button>
                    </a>
                    <a href="https://iwanvi.oss-cn-beijing.aliyuncs.com/document/reportTemp/%E7%82%B9%E5%86%A0.xls">
                         <el-button type="primary" >点冠</el-button>
                    </a>
                    <a href="https://iwanvi.oss-cn-beijing.aliyuncs.com/document/reportTemp/%E7%99%BE%E5%BA%A6.xls">
                         <el-button type="primary" >百度</el-button>
                    </a>
                    <a href="https://iwanvi.oss-cn-beijing.aliyuncs.com/document/reportTemp/360.xls">
                         <el-button type="primary" >360</el-button>
                    </a>
                    <a href="https://iwanvi.oss-cn-beijing.aliyuncs.com/document/reportTemp/%E9%80%9A%E7%94%A8.xls">
                         <el-button type="primary" >通用</el-button>
                    </a>
                </el-form-item>
                <el-form-item label="上传文件">
                    <!-- <el-input v-model="entityUrl"  style="width:44%"></el-input> -->
                    <el-upload
                        class="upload-demo"
                        :data="{type:this.line1}"
                        :action="upload"
                        name="file"
                        :on-success="handleSuccess"
                    >
                    <el-button size="small" type="primary">选择文件</el-button>
                    </el-upload>
                </el-form-item>
            </el-form>
            <span slot="footer">
                <!-- <el-button @click="newVisible = false">取 消</el-button> -->
                <!-- <el-button type="primary" @click="ToLead">确 定</el-button> -->
            </span>
        </el-dialog>
    </div>

</template>

<script>
import { flowconsumergetlist,consumerdatagetoneplatformpagedatabyday } from "@/api/Api.js";

import { appgetall,adPositiongetList,sdkReportsumByDay,positionmappinglist,
adPositiongetlistByConsumerAndApp,
sdkReportdetail,sdkReportupload,sdkReportsum } from "@/api/Api.js";

// import Schart from "vue-schart";
// 引入 ECharts 主模块
var echarts = require("echarts/lib/echarts");

require("echarts/lib/chart/line");
require("echarts/lib/component/legend");
require("echarts/lib/component/tooltip");
require("echarts/lib/component/title");

// 导出模块
import FileSaver from 'file-saver'
import XLSX from 'xlsx'
// require("echarts/lib/component/toolbox");

// import { tianqi } from "@/api/Api.js";

export default {
  name: "basecharts",
  components: {
    // Schart
  },
  data: () => ({
    pickerOptions1: {
         disabledDate(time) {
            return time.getTime() > Date.now();
          },
    },
    index:1,
    tableData2:[],
    startDay: new Date().Format("yyyyMMdd"),
    endDay: new Date().Format("yyyyMMdd"),
    radio:'1',
    cur_page: 1,
    total: 1,
    ps:10,
    checkAll: false,
    isIndeterminate: true,
    flow_sueers:'',
    add_time:'',
    classify:[],
    data:[],
    tableData:[],
    line1:'',
    form:{

     },
    newVisible:false,
    optionsTypeAD:[
         {
          value: '',
          label: ''
        }
      ],
    optionAppName:[
         {
          value: '',
          label: ''
        }
      ],
    optionsOs:[
         {
          value: '',
          label: ''
        }
      ],
    optionsPlatform:[
         {
          value: '',
          label: ''
        }
      ],
    optionsType:[
         {
          value: 1,
          label: '广点通'
         },
         {
          value: 2,
          label: '头条'
         },
          {
          value: 3,
          label: '点冠'
         },
          {
          value: 4,
          label: '百度'
         },
           {
          value: 5,
          label: '360'
         },
           {
          value: 6,
          label: '通用'
         },
      ],
    optionsLine:[
         {
          value: 'type',
          label: '广告平台'
         },
         {
          value: 'app_id',
          label: 'APP名称'
         },
         {
          value: 'pid',
          label: '系统广告位'
         },
         {
          value: 'position_id',
          label: '平台广告位'
         },
      ],
    optionsCustom:[
         {
          value: 'req',
          label: '请求量'
         },
        {
          value: 'reqUV',
          label: '请求UV'
         },
         {
          value: 'exp',
          label: '展现量'
         },
          {
          value: 'expUV',
          label: '展现UV'
         },
           {
          value: 'fr',
          label: '展现率'
         },
        {
          value: 'clk',
          label: '点击量'
         },
          {
          value: 'ctr',
          label: '点击率'
         },
          {
          value: 'expPer',
          label: '人均曝光'
         },
          {
          value: 'expIncome',
          label: '曝光收入'
         },
        {
          value: 'clkIncome',
          label: '点击收入'
         },
        {
          value: 'cpm',
          label: 'cpm'
         },
        {
          value: 'cpc',
          label: 'cpc'
         },
        {
          value: 'ecpm',
          label: 'ecpm'
         },
        {
          value: 'clkDesire',
          label: '点击意愿'
         },
          {
          value: 'income',
          label: '收入'
         },
      ],
    appIds:[],
    systemAD:[],
    platformAD:[],
    line:'type',
    custom:['req','reqUV','exp','fr','clk','ctr','income'],
    upload:sdkReportupload,
    entityUrl:'',
    titleList:['展现量','点击量','点击率','收入','cpm','cpc'],

  }),

  created() {
         this.getList()
  },
  mounted() {
    /*ECharts图表*/
    var myChart = echarts.init(document.getElementById("main"));
    myChart.setOption({
      title: {
        text: '流量数据'
    },
    toolbox: {
              show : true,
              x:'950',//下载的字体显示不全
             feature : {
                      dataView : {show: false, readOnly: false},
                       magicType : {show: true, type: ['line', 'bar']},
                        restore : {show: true},
                        saveAsImage : {show: true}
                     }
         },

    tooltip : {
        trigger: 'axis',
        axisPointer: {
            type: 'cross',
            label: {
                backgroundColor: '#6a7985'
            }
        }
    },
    legend: {
        data:['请求量','展现量','点击量','展现率%','点击率','收入','CPM','CPC'],
        // y: 'bottom', // 'center' | 'bottom' | {number}
        // padding: [100,0,0,0],
    },
    toolbox: {
        feature: {
            saveAsImage: {}
        }
    },
    grid: {
        left: '1%',
        right: '4%',
        bottom: '3%',
        containLabel: true
    },
    xAxis : [
        {
            type : 'category',
            boundaryGap : false,
            data : ['周一','周二','周三','周四','周五','周六','周日']
        }
    ],
    yAxis : [
        {
            type : 'value'
        }
    ],
    series : [
        // {
        //     name:'请求量',
        //     type:'line',
        //     stack: '总量',
        //     areaStyle: {normal: {}},
        //     data:[120, 132, 101, 134, 90, 230, 210]
        // },

    ]
    });


  },
   methods: {
     GetSeach(){
        this.search()
     },
    handCustom(value){
        console.log(value)
        // var set = new set()
    },
    goback(){
        this.index = 1
    },
    handleEdit(index,row){
         this.idx = index;
         let item = row;
         let params = {
                id:item.itemId,
                flowConId:this.classify.join(','),
                appsId:this.appIds.join(','),
                pid:this.systemAD.join(','),
                flowPosId:this.platformAD.join(','),
                sDate:this.startDay,
                eDate:this.endDay,
        }
        sdkReportdetail(params).then(res=>{
              console.log(res)
              let data = res.data
              this.tableData = data.data
              this.index = 2
            // this.total = res.data.result.totalItemNum;
      
        });
        //  this.$router.push({
        //      path:'/datareport/ADplatformIn',
        //      query:{
        //          itemId:item.itemId,
        //          sDate:this.startDay,
        //          eDate:this.endDay,
        //          flowCons:this.classify.join(','),
        //          appsId:this.appIds.join(','),
        //          pid:this.systemAD.join(','),
        //          flowPosIds:this.platformAD.join(','),
        //          }
        //      })
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
    getname(){
        this.appIds = []
        this.systemAD = []
        this.platformAD = []
        this.getOSName()
    },
    deleteTag(){
            this.systemAD = []
            this.platformAD = []
    },
    deleteTag2(){
            this.platformAD = []
    },
              // 系统广告位下拉框
    getOSName(){    
        let  par = {
            consumerIds :this.classify.join(','),
            appIds :this.appIds.join(',')
        }
        adPositiongetlistByConsumerAndApp(par).then(res=>{
          console.log(res)
            let data = res.data;
             if(data.code != 'A000000') {
              this.$message.error(data.message);
              return;
            }
            this.optionsOs = data.data
          });
    },
              // 平台广告位下拉框
    getplatformAD(){
        let params = {
            consumerIds :this.classify.join(','),
            // positionIds :this.systemAD.join(',')
        }
        positionmappinglist(params).then(res=>{
          console.log(res)
            let data = res.data;
             if(data.code != 'A000000') {
              this.$message.error(data.message);
              return;
            }
            this.optionsPlatform = data.data
          });
    },
    edit(){
        this.line1 = ''
        this.entityUrl = ''
        this.newVisible = true
    },
  
    handleSuccess(res, file) {
      let data = res;
      if (data.code != "A000000") {
        this.$message.error(data.message);
        return;
      } else {
        // this.entityUrl = data.data.url;
        this.newVisible = false
      }
      this.$forceUpdate();
    },
         // 分页导航
    handleCurrentChange(val) {
      console.log(val);
      this.cur_page = val;
      this.getList();
    },
     //   广告平台名称
      getList(){
          let param = {consumerType:97};
        flowconsumergetlist(param).then(res=>{
          let data = res.data;
            this.optionsTypeAD = data.data
            // this.types = data.data
            });
      },
    //   搜索
      search(){
          if(!this.classify){
              this.$message('名称不能为空')
              return;
          }
        var beginDt = new Date();
        var echartsDate  = [];
        let params = {
                sDate:this.startDay,
                eDate:this.endDay,
                flowCons:this.classify.join(','),
                apps:this.appIds.join(','),
                pids:this.systemAD.join(','),
                flowPosIds:this.platformAD.join(','),
                group:this.line
            }
             sdkReportsumByDay(params).then(res=>{
                console.log(res)
                let data = res.data;
                    if(data.code != 'A000000') {
                    this.$message.error(data.message);
                    }
                console.log(data)

        var req=[];var reqUV=[];var exp=[];var expUV=[];var fr=[];var clk=[];var ctr=[];var expPer=[];var income=[];var clkIncome=[];var cost=[];
        var cpm=[];var cpc=[];var ecpm=[];var clkDesire=[];
            for(var item of data.data){
                if(item.date){
                    echartsDate.push(item.date); // 名称
                    req.push(item.req);//请求量
                    reqUV.push(item.reqUV); //请求UV
                    exp.push(item.exp); //曝光
                    expUV.push(item.expUV); // 曝光uv
                    fr.push(item.fr);//填充率
                    clk.push(item.clk);//点击
                    ctr.push(item.ctr);//点击率

                    income.push(item.income);//点击收入
                    cpm.push(item.cpm);//cpm
                    cpc.push(item.cpc);//cpc


                }

            }

            // console.log(reqs);console.log(clks);

            var myChart = echarts.init(document.getElementById("main"));
            myChart.setOption({
            title: {
                // text: '客户数据'
            },
            tooltip: {
                trigger: 'axis'
            },
            legend: {
                data:this.titleList
            },
            grid: {
                left: '3%',
                right: '4%',
                bottom: '3%',
                containLabel: true
            },
            toolbox: {
                feature: {
                    saveAsImage: {}
                }
            },
            xAxis: {
                type: 'category',
                boundaryGap: false,
                data: echartsDate
            },
            yAxis: {
                type: 'value'
            },
            series: [
                {
                    name:this.titleList[0],
                    type:'line',
                    // stack: '总量',
                    data:exp,
                },
                {
                    name:this.titleList[1],
                    type:'line',
                    // stack: '总量',
                    data:clk,
                },
                // {
                //     name:this.titleList[2],
                //     type:'line',
                //     // stack: '总量',
                //     data:fr
                // },
                {
                    name:this.titleList[2],
                    type:'line',
                    // stack: '总量',
                    data:ctr
                },
                {
                    name:this.titleList[3],
                    type:'line',
                    // stack: '总量',
                    data:income
                },
                {
                    name:this.titleList[4],
                    type:'line',
                    // stack: '总量',
                    data:cpm
                },
                {
                    name:this.titleList[5],
                    type:'line',
                    // stack: '总量',
                    data:cpc
                },
                ]
                });
                 let par = {
                        sDate:this.startDay,
                        eDate:this.endDay,
                        flowCons:this.classify.join(','),
                        apps:this.appIds.join(','),
                        pids:this.systemAD.join(','),
                        flowPosIds:this.platformAD.join(','),
                        group:this.line
                    }
                sdkReportsum(par).then(res=>{
                  let data = res.data;
                    this.tableData = data.data
                });
                //   let par = {
                //         startDate:this.startDay,
                //         endDate:this.endDay,
                //         mediaId:this.classify,
                //     }
                // consumerdatagetoneplatformpagedatabyday(par).then(res=>{
                //   let data = res.data;
                //     this.data = data.data.data
                // });
            });
      },

         //   导出table方法
    exportExcel () {
            /* generate workbook object from table */

            var wb = XLSX.utils.table_to_book(document.querySelector('#allTable'))

            // var wb1 = XLSX.utils.table_to_book(document.querySelector('#table'))

            /* get binary string as output */

            var wbout = XLSX.write(wb, { bookType: 'xlsx', bookSST: true, type: 'array' })
            // var wbout1 = XLSX.write(wb1, { bookType: 'xlsx', bookSST: true, type: 'array' })


            try {

            FileSaver.saveAs(new Blob([wbout], { type: 'application/octet-stream' }), '汇总数据.xlsx')

            // FileSaver.saveAs(new Blob([wbout1], { type: 'application/octet-stream' }), '广告平台分日数据.xlsx')

            } catch (e) { if (typeof console !== 'undefined') console.log(e, wbout) }

            return wbout
        },
      exportExcel2 () {
            /* generate workbook object from table */
            var wb = XLSX.utils.table_to_book(document.querySelector('#table'))
            // var wb1 = XLSX.utils.table_to_book(document.querySelector('#table'))
            /* get binary string as output */
            var wbout = XLSX.write(wb, { bookType: 'xlsx', bookSST: true, type: 'array' })
            // var wbout1 = XLSX.write(wb1, { bookType: 'xlsx', bookSST: true, type: 'array' })
            try {
                FileSaver.saveAs(new Blob([wbout], { type: 'application/octet-stream' }), '分日数据.xlsx')
                // FileSaver.saveAs(new Blob([wbout1], { type: 'application/octet-stream' }), '广告平台分日数据.xlsx')
            } catch (e) { if (typeof console !== 'undefined') console.log(e, wbout) }

            return wbout
        },

    }
};
</script>

<style scoped>
.block {
  margin-bottom: 3%;
}
.flot_right{
    float: right
}
.header-select {
    margin-bottom: 20px
}
.mr10{
    width: 150px;
}
.border{
    padding-top: 50px
}
.handle-box {
  margin-bottom: 20px;
}
</style>
<style >
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
