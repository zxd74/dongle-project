
<template>
    <div class="box">
        <div class="block">
           <div class="header-select">
                 <template>
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
                       <el-select v-model="line" clearable placeholder="分列显示">
                            <el-option
                                v-for="item in optionsLine"
                                :key="item.value"
                                :label="item.label"
                                :value="item.value">
                            </el-option>
                        </el-select>
                      <!-- <el-button type="primary" icon="search" @click="edit">导入</el-button> -->
                </template>
            </div>
            <div>
               <el-select v-model="appIds" multiple placeholder="图书一级" class="handle-input" @focus=getAppName>
                        <el-option
                          v-for="item in optionAppName"
                          :key="item.id"
                          :label="item.name"
                          :value="item.id">
                        </el-option>
                </el-select>
                <el-select v-model="systemAD" multiple placeholder="图书二级" @focus=getOSName>
                            <el-option
                                v-for="item in optionsOs"
                                :key="item.id"
                                :label="item.name"
                                :value="item.id">
                            </el-option>
                </el-select>
                <el-select v-model="AppVersion" multiple placeholder="图书三级" class="handle-input"  @focus=getAppVersion>
                    <el-option
                        v-for="item in optionsAppVersion"
                        :key="item.id"
                        :label="item.name"
                        :value="item.id">
                    </el-option>
                    </el-select>
                    <!-- <el-select v-model="AppChannel" multiple placeholder="渠道号" class="handle-input"  @focus=getAppChannel>
                    <el-option
                        v-for="item in optionsAppChannel"
                        :key="item.cname"
                        :label="item.name"
                        :value="item.cname">
                    </el-option>
                </el-select> -->
               
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
        <el-tag size="medium">汇总数据:</el-tag>
        <el-table :data="tableData" border style="width: 100%" ref="multipleTable" id="allTable">
                <el-table-column prop="itemId" label="ID" >
                </el-table-column>
                <el-table-column prop="level1Name" label="一级" v-if="this.line == 'level1'">
                </el-table-column>
                <el-table-column prop="level2Name" label="二级" v-if="this.line == 'level2'">
                </el-table-column>
                <el-table-column prop="level3Name" label="三级" v-if="this.line == 'level3'">
                </el-table-column>
                <el-table-column prop="requv" label="请求量" v-if="this.custom.indexOf('req') > -1">
                </el-table-column> 
                <el-table-column prop="requv" label="请求UV" v-if="this.custom.indexOf('requv') > -1">
                </el-table-column> 
                <el-table-column prop="exp" label="展现量" v-if="this.custom.indexOf('exp') > -1">
                </el-table-column>
                <el-table-column prop="expuv" label="展现UV" v-if="this.custom.indexOf('expUV') > -1">
                </el-table-column>
                <el-table-column prop="exp_rate" label="展现率%" v-if="this.custom.indexOf('exp_rate') > -1">
                </el-table-column>
                <el-table-column prop="clk" label="点击量" v-if="this.custom.indexOf('clk') > -1">
                </el-table-column>
                <el-table-column prop="clkuv" label="点击uv" v-if="this.custom.indexOf('clkUV') > -1">
                </el-table-column>
                 <el-table-column prop="clk_rate" label="点击率%" v-if="this.custom.indexOf('clk_rate') > -1">
                </el-table-column>
                 <el-table-column prop="investment" label="收入" v-if="this.custom.indexOf('investment') > -1">
                </el-table-column>
                 <el-table-column prop="cpm" label="cpm" v-if="this.custom.indexOf('cpm') > -1">
                </el-table-column>
                 <el-table-column prop="cpc" label="cpc" v-if="this.custom.indexOf('cpc') > -1">
                </el-table-column>
                <el-table-column prop="expPer" label="人均曝光数" v-if="this.custom.indexOf('expPer') > -1">
                </el-table-column>
                <el-table-column label=""  width="150" align="center">
                  <template slot-scope="scope">
                    <el-button size="mini" type="success" @click="handleEdit(scope.$index, scope.row,)">详细</el-button>
                  </template>
                </el-table-column>
         </el-table>
        <el-dialog title="导入" :visible.sync="newVisible" width="40%">
            <el-form ref="form" :model="form" status-icon label-width="100px">
                <el-form-item label="选择模板:">
                    <el-select v-model="line" placeholder="">
                            <el-option
                                v-for="item in optionsType"
                                :key="item.value"
                                :label="item.label"
                                :value="item.value">
                            </el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="上传文件">
                    <el-input v-model="entityUrl"  style="width:44%"></el-input>
                    <el-upload
                        class="upload-demo"
                        :data="{type:this.line}"
                        :action="upload"
                        name="file"
                        :on-success="handleSuccess"
                    >
                    <el-button size="small" type="primary">选择文件</el-button>
                    </el-upload>
                </el-form-item>
            </el-form>
            <span slot="footer">
                <el-button @click="newVisible = false">取 消</el-button>
                <el-button type="primary" @click="ToLead">确 定</el-button>
            </span>
        </el-dialog>
    </div>
  
</template>

<script>
import { flowconsumergetlist,consumerdatagetoneplatformpagedatabyday } from "@/api/Api.js";

import { appgetall,adPositiongetList,sdkReportsumByDay,positionmappinglist,
sdkReportdetail,sdkReportupload,sdkReportsum,flowsourcegetlist,AppVersionselect,AppChannelselect,quotaFlowsum,
quotaBooksumByDay,quotaBooksum,bookcategorysselectOne,bookcategorysselectTwo,bookcategorysselectThree
} from "@/api/Api.js";

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
    optionsAppVersion:[
         {
          value: '',
          label: ''
        }
      ],
    optionsAppChannel:[
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
          value: 'level1',
          label: '一级'
         },
         {
          value: 'level2',
          label: '二级'
         },
         {
          value: 'level3',
          label: '三级'
         },
      ],
    optionsCustom:[
          {
          value: 'req',
          label: '请求量'
         },
          {
          value: 'requv',
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
          value: 'exp_rate',
          label: '展现率'
         },
        {
          value: 'clk',
          label: '点击量'
         },
          {
          value: 'clkUV',
          label: '点击uv'
         },
          {
          value: 'clk_rate',
          label: '点击率'
         },
        {
          value: 'investment',
          label: '收入'
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
          value: 'expPer',
          label: '人均曝光数'
         },
      ],
    appIds:[],
    systemAD:[],
    AppVersion:[],
    AppChannel:[],
    line:'',
    custom:['exp','exp_rate','clk','clk_rate','investment'],
    upload:sdkReportupload,
    entityUrl:'',
    titleList:['请求量','请求uv','展现量','展现uv','展现率','点击量','点击uv','点击率','收入','CPM','CPC','人均曝光数'],
      
  }),

  created() {
        //  this.getList()
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
    handCustom(value){
        console.log(value)
        // var set = new set()
       
    },
    // 跳转
    handleEdit(index,row){
         this.idx = index;
         let item = row;
         this.$router.push({
             path:'/datareport/bookReportIn', 
             query:{
                 id:item.itemId,
                 sDate:this.startDay,
                 eDate:this.endDay,
                 level1s:this.appIds.join(','),
                 level2s:this.systemAD.join(','),
                 level3s:this.AppVersion.join(','),
                 group:this.line,
                 }
             })
    },
             // 一级
    getAppName(){
        bookcategorysselectOne().then(res=>{
          console.log(res)
            let data = res.data; 
             if(data.code != 'A000000') {
              this.$message.error(data.message);
              return;
            }    
            this.optionAppName = data.data
          });
    },
    getOSName(){
        bookcategorysselectTwo().then(res=>{
          console.log(res)
            let data = res.data; 
             if(data.code != 'A000000') {
              this.$message.error(data.message);
              return;
            }    
            this.optionsOs = data.data
          });
    },
    getAppVersion(){
        bookcategorysselectThree().then(res=>{
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
    edit(){
        this.line = ''
        this.entityUrl = ''
        this.newVisible = true
    },
    ToLead(){
        //  orderPutupdate(params).then(res => {
        //           console.log(res);
        //           let data = res.data;
        //           if (data.code != "A000000") {
        //             this.$message.error(data.message);
        //           }
                  this.newVisible = false;
            // });  
    },
    handleSuccess(res, file) {
      let data = res;
      if (data.code != "A000000") {
        this.$message.error(data.message);
        return;
      } else {
        this.entityUrl = data.data.url;
      }
      this.$forceUpdate();
    },
         // 分页导航
    handleCurrentChange(val) {
      console.log(val);
      this.cur_page = val;
      this.getList();
    },
   
      getList(){
        flowsourcegetlist().then(res=>{
            console.log(res)
          let data = res.data;     
            this.optionsTypeAD = data.data
            // this.types = data.data
            });
      },
    //   搜索
      search(){
        var beginDt = new Date();
        var echartsDate  = [];
         let params = {
                sDate:this.startDay,
                eDate:this.endDay,
                group:this.line
            }
             quotaBooksumByDay(params).then(res=>{
                console.log(res)
                let data = res.data;
                    if(data.code != 'A000000') {
                    this.$message.error(data.message);
                    }   
                console.log(data)
        var req=[];var requv=[];var exp=[];var expuv=[];var  exp_rate=[];var clk=[];var clk_rate=[];var expPer=[];var expIncome=[];var clkIncome=[];var cost=[];
        var cpm=[];var cpc=[];var ecpm=[];var clkDesire=[];var clkuv=[];var income=[];
            for(var item of data.data){
                if(item.creDay){
                    echartsDate.push(item.creDay); // 名称
                    req.push(item.req);//请求量
                    requv.push(item.requv); //请求UV
                    exp.push(item.exp); //曝光
                    expuv.push(item.expuv); // 曝光uv
                    exp_rate.push(item. exp_rate);//填充率
                    clk.push(item.clk);//点击
                    clkuv.push(item.clkuv);//点击uv
                    clk_rate.push(item.clk_rate);//点击率
                    expPer.push(item.expPer);//人均曝光
                    expIncome.push(item.expIncome);//曝光收入
                    clkIncome.push(item.clkIncome);//点击收入
                    cpm.push(item.cpm);//cpm
                    cpc.push(item.cpc);//cpc
                    ecpm.push(item.ecpm);//ecpm
                    clkDesire.push(item.clkDesire);//点击意愿
                    income.push(item.income);//总收入
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
                    data:req,
                },
                {
                    name:this.titleList[1],
                    type:'line',               
                    // stack: '总量',
                    data:requv,
                },
                {
                    name:this.titleList[2],
                    type:'line',
                    // stack: '总量',
                    data:exp,
                },
                {
                    name:this.titleList[3],
                    type:'line',
                    // stack: '总量',                    
                    data:expuv
                },
                {
                    name:this.titleList[4],
                    type:'line',
                    // stack: '总量',                    
                    data:exp_rate
                },
                {
                    name:this.titleList[5],
                    type:'line',
                    // stack: '总量',                    
                    data:clk
                },
                {
                    name:this.titleList[6],
                    type:'line',
                    // stack: '总量',                    
                    data:clkuv
                },
                {
                    name:this.titleList[7], 
                    type:'line',
                    // stack: '总量',                    
                    data:clk_rate
                },
                 {
                    name:this.titleList[8],
                    type:'line',
                    // stack: '总量',                    
                    data:income
                },
                {
                    name:this.titleList[9], 
                    type:'line',
                    // stack: '总量',                    
                    data:cpm
                },
                 {
                    name:this.titleList[10], 
                    type:'line',
                    // stack: '总量',                    
                    data:cpc
                },
                  {
                    name:this.titleList[11], 
                    type:'line',
                    // stack: '总量',                    
                    data:expPer
                },
                ]
                });
                 let par = {
                        sDate:this.startDay,
                        eDate:this.endDay,
                        level1s:this.appIds.join(','),
                        level2s:this.systemAD.join(','),
                        level3s:this.AppVersion.join(','),
                        group:this.line
                    }
                quotaBooksum(par).then(res=>{
                    console.log(res)
                    let data = res.data;     
                    this.tableData = data.data
                });
            
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

            FileSaver.saveAs(new Blob([wbout], { type: 'application/octet-stream' }), '图书报表.xlsx')

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