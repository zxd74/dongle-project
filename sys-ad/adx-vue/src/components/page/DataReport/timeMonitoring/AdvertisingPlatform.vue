<template>
    <div class="box">
        <div class="block">
            <el-form ref="form" :model="form"  status-icon label-width="100px">
                <el-form-item  label="查询条件:">
                    <el-radio-group v-model="flowSource">
                        <el-radio :label="1">单广告平台</el-radio>
                        <el-radio :label="2">多广告平台</el-radio>
                    </el-radio-group>
                    <el-button type="primary" @click="BtnEchers">搜 索</el-button>
                 </el-form-item>
                <el-form-item label="广告平台:" v-if="flowSource == 1">
                    <el-select v-model="classify" placeholder="请选择">
                        <el-option
                            v-for="item in optionsType"
                            :key="item.id"
                            :label="item.consumerName"
                            :value="item.id">
                        </el-option>
                    </el-select>
                </el-form-item>
                 <el-form-item label="广告平台:" v-if="flowSource == 2">
                         <!-- 循环选项 -->
                         <el-checkbox :indeterminate="isIndeterminate" v-model="checkAll" @change="handleCheckAllChange">全选</el-checkbox>
                         <el-checkbox-group v-model="checkList">
                           <el-checkbox v-for="(item,index) in types" :key="index" :label="item.id">{{item.consumerName}}</el-checkbox>
                          <!-- <el-checkbox v-for="(item,index) in types" :key="index">{{item.name}}</el-checkbox> -->
                       </el-checkbox-group>

                </el-form-item>
                <!-- <el-form-item label="查询字段:" v-if="flowSource == 1">
                    <el-checkbox :indeterminate="isIndeterminate" v-model="checkAll" @change="handleCheckAllChange">全选</el-checkbox>
                    <div style="margin: 15px 0;"></div>
                    <el-checkbox-group v-model="checkedCities" @change="handleCheckedCitiesChange">
                        <el-checkbox v-for="city in cities" :label="city" :key="city">{{city}}</el-checkbox>
                    </el-checkbox-group>
                </el-form-item> -->
                <el-form-item label="查询字段:" v-if="flowSource == 2">
                     <el-select v-model="value3" placeholder="请选择">
                        <el-option
                        v-for="item in options3"
                        :key="item.value"
                        :label="item.label"
                        :value="item.value">
                        </el-option>
                     </el-select>
                </el-form-item>
            </el-form>
        </div>

        <!-- ECharts图表测试 -->
        <div id="charts" style="width:'100%',height:'500px'"  v-loading="dialogVisible">
            <div id="main" :style="{width:'100%',height:'500px'}"></div>
        </div>
        <el-table :data="tableData" border style="width: 100%" ref="multipleTable" v-if="this.flowSource == 1||this.flowSource == 2"  v-loading="dialogVisible2">
                <el-table-column prop="consumerName" label="广告平台">
                </el-table-column>
                <el-table-column prop="req" label="请求量">
                </el-table-column>
                <el-table-column prop="bid" label="填充量">
                </el-table-column>
                <el-table-column prop="bid_rate" label="填充率%">
                </el-table-column>
                <el-table-column prop="exp" label="展现量" >
                </el-table-column>
                <el-table-column prop="exp_rate" label="展现率%">
                </el-table-column>
                <el-table-column prop="clk" label="点击量">
                </el-table-column>
                <el-table-column prop="clk_rate" label="点击率%">
                </el-table-column>
                <el-table-column prop="cpm" label="CPM">
                </el-table-column>
                <el-table-column prop="cpc" label="CPC">
                </el-table-column>
                <el-table-column prop="timeout" label="超时">
                </el-table-column>
                <el-table-column prop="" label="超时率%">
                    <template slot-scope="scope">
                         {{scope.row.timeout !=0 && scope.row.req != 0?((scope.row.timeout/scope.row.req)*100).toFixed(2):'0.00'}}
                    </template>
                </el-table-column>
                <el-table-column prop="nobid" label="不竞价" >
                </el-table-column>
                <el-table-column prop="lower" label="低于底价">
                </el-table-column>
                <el-table-column prop="overqps" label="超过QPS限制">
                </el-table-column>
                <el-table-column prop="error" label="异常">
                </el-table-column>
                <el-table-column prop="win" label="竞价成功数">
                </el-table-column>
                <el-table-column prop="" label="竞价成功率%">
                    <template slot-scope="scope">
                         {{scope.row.win !=0 && scope.row.bid != 0?((scope.row.win/scope.row.bid)*100).toFixed(2):'0.00'}}
                    </template>
                </el-table-column>
                <el-table-column prop="investment" label="收入">
                </el-table-column>
            </el-table>
            <!-- <el-dialog
            title="提示"
            :visible.sync="dialogVisible"
            width="30%"
            :show-close='false'
            :close-on-click-modal='false'
            :close-on-press-escape='false'
            >
            <span>正在加载....</span>
            <span slot="footer" class="dialog-footer">
            </span>
        </el-dialog> -->
    </div>
</template>

<script>
import { flowconsumergetlist } from "@/api/Api.js";
import { consumerdatagetonefcbyhour,consumerdatagetplatformsdatabyhour ,consumerdatagetPlatformsdatatoday} from "@/api/Api.js";
// import Schart from "vue-schart";
// 引入 ECharts 主模块
var echarts = require("echarts/lib/echarts");

require("echarts/lib/chart/line");
require("echarts/lib/component/legend");
require("echarts/lib/component/tooltip");
require("echarts/lib/component/title");

const options = [];

export default {
  name: "basecharts",
  components: {
    // Schart
  },
  data: () => ({
    radio:'',
    tableData:[],
    checkAll: false,
    isIndeterminate: true,
    flowSource:1,
    isAll:'',
    checkList:[],
    types:null,
    from:{

    },
    dialogVisible:false,
    dialogVisible2:false,
    classify:'',
    optionsType:[
         {
          value: '',
          label: ''
        }
      ],
    value: '选项1',
    value2:'',

     value3:'',
         options3: [
         {
          value: 'req',
          label: '请求量'
        },
        // {
        //   value: 'bid',
        //   label: '填充量'
        // },
         {
          value: 'exp',
          label: '展现量'
        },
        // {
        //   value: 'bid_rate',
        //   label: '填充率%'
        // },
        {
          value: 'exp_rate',
          label: '展现率%'
        },
        {
          value: 'clk',
          label: '点击量'
        },
        {
          value: 'clk_rate',
          label: '点击率'
        },
        {
          value: 'timeout',
          label: '超时'
        },
        {
          value: 'nobid',
          label: '不竞价'
        },
        {
          value: 'lower',
          label: '低于底价'
        },
        {
          value: 'overqps',
          label: '超过QPS限制'
        },
        {
          value: 'error',
          label: '异常'
        },
        {
          value: 'win',
          label: '竞价成功数'
        },
        ],
     titleList:['请求量','填充量','填充率%','展现量','点击量','展现率%','点击率%','CPM','CPC','超时','超时率%', '不竞价','低于底价','超过QPS限制','异常','竞价成功数','竞价成功率%','收入'],
     form:{},

  }),



  created() {
    this.getList()
  },
  methods: {
    //   广告平台名称
      getList(){
          let param = {consumerType:98};
        flowconsumergetlist(param).then(res=>{
          let data = res.data;
            this.optionsType = data.data
            this.types = data.data
            data.data.forEach(element => {
                options.push(element.id)
            });
        });
      },
      //   全选
      handleCheckAllChange(val){
          this.checkList = val ? options : [];
          this.isIndeterminate = false;
      },
      handleCheckedCitiesChange(value) {
        let checkedCount = value.length;
        this.checkAll = checkedCount === options.length;
        this.isIndeterminate = checkedCount > 0 && checkedCount < options.length;
      },
    //  查询
     BtnEchers(){
         var beginDt = new Date();
         var echartsDate  = [];
              //获取当前日期
        this.date = new Date();
        var  m = (this.date.getMonth()+1)<10?"0"+(this.date.getMonth()+1):(this.date.getMonth()+1)
        var  d = this.date.getDate()<10?"0"+this.date.getDate():this.date.getDate()
        this.eDay=this.date.getFullYear()+""+m+""+d;    
        if(this.flowSource == 1){
            if(!this.classify){
                this.$message('请选择广告平台')
                return;
            }
              this.dialogVisible = true
              this.dialogVisible2 = true
                let params = {
                    startDate:this.eDay,
                    endDate:this.eDay,
                    mediaId:this.classify,
                    isAll:0
            }
        consumerdatagetonefcbyhour(params).then(res=>{
          let data = res.data;
            if(data.code != 'A000000') {
              this.$message.error(data.message);
            }
           console.log(data)

              this.dialogVisible = false
        var reqs=[];var bid=[];var exp=[];var clk=[];var bid_rate=[];var exp_rate=[];var clk_rate=[];var cpm=[];var cpc=[];
        var timeout=[];      var nobid=[];      var lower=[];      var overqps=[]; var error=[];  var win=[]; 
        var timeout_rate = [];  var win_rate = [];  var cost = []
            for(var item of data.data){
                echartsDate.push(item.creHour); // 日期
                reqs.push(item.req);//请求量
                bid.push(item.bid); //填充量
                exp.push(item.exp); //展现量
                clk.push(item.clk); //点击量
                bid_rate.push(item.bid_rate);//填充率
                exp_rate.push(item.exp_rate);//曝光率
                clk_rate.push(item.clk_rate);//点击率
                cost.push(item.investment);//花费
                cpm.push(item.cpm);//cpm
                cpc.push(item.cpc);//cpc
                timeout.push(item.timeout);//timeout
                nobid.push(item.nobid);//nobid
                lower.push(item.lower);//lower
                overqps.push(item.overqps);//overqps
                error.push(item.error);//error
                win.push(item.win);//win
                timeout_rate.push(((item.timeout/item.req)*100).toFixed(2));//超时率
                win_rate.push(((item.win/item.bid)*100).toFixed(2));//竞价成功率

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
            option:true,
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
                    data:reqs,
                },
                {
                    name:this.titleList[1],
                    type:'line',
                    // stack: '总量',
                    data:bid
                },
                {
                    name:this.titleList[2],
                    type:'line',
                    // stack: '总量',
                    data:bid_rate
                },
                {
                    name:this.titleList[3],
                    type:'line',
                    // stack: '总量',
                    data:exp,
                },
                {
                    name:this.titleList[4],
                    type:'line',
                    // stack: '总量',
                    data:clk
                },
                // {
                //     name:this.titleList[4],
                //     type:'line',
                //     // stack: '总量',
                //     data:bid_rate
                // },
                {
                    name:this.titleList[5],
                    type:'line',
                    // stack: '总量',
                    data:exp_rate
                },
                {
                    name:this.titleList[6],
                    type:'line',
                    // stack: '总量',
                    data:clk_rate
                },
                {
                    name:this.titleList[7],
                    type:'line',
                    // stack: '总量',
                    data:cpm
                },
                {
                    name:this.titleList[8],
                    type:'line',
                    // stack: '总量',
                    data:cpc
                },
                {
                    name:this.titleList[9],
                    type:'line',
                    // stack: '总量',
                    data:timeout
                },
                {
                    name:this.titleList[10],
                    type:'line',
                    // stack: '总量',
                    data:timeout_rate
                },
                {
                    name:this.titleList[11],
                    type:'line',
                    // stack: '总量',
                    data:nobid
                },
                {
                    name:this.titleList[12],
                    type:'line',
                    // stack: '总量',
                    data:lower
                },
                {
                    name:this.titleList[13],
                    type:'line',
                    // stack: '总量',
                    data:overqps
                },
                {
                    name:this.titleList[14],
                    type:'line',
                    // stack: '总量',
                    data:error
                },
                {
                    name:this.titleList[15],
                    type:'line',
                    // stack: '总量',
                    data:win
                },
                {
                    name:this.titleList[16],
                    type:'line',
                    // stack: '总量',
                    data:win_rate
                },
                {
                    name:this.titleList[17],
                    type:'line',
                    // stack: '总量',
                    data:cost
                },
                ]
                });
          

            
            });
            let params1 = {
                mediaId:this.classify,
                startDate:this.eDay,
                endDate:this.eDay,
                isAll:1
            }
        consumerdatagetonefcbyhour(params1).then(res=>{
            let data = res.data;
            this.tableData = data.data
            this.dialogVisible2 = false
        });
        }

        if(this.flowSource == 2){
            if(!this.value3){
                this.$message.error('查询字段不能为空')
                return
            }
            if(this.checkList.length <= 0){
                this.$message.error('广告平台不能为空')
                return
            }
            this.dialogVisible = true
            this.dialogVisible2 = true
            let params2;
            if(this.checkAll){
                  params2 = {
                    startDate:this.eDay,
                    endDate:this.eDay,
                    mediaIds:options.join(','),
                    type:this.value3,
                    isAll:true
                }
            }else{
                 params2 = {
                    startDate:this.eDay,
                    endDate:this.eDay,
                    mediaIds:this.checkList.join(','),
                    type:this.value3,
                }
            }
        // console.log(params);

        consumerdatagetplatformsdatabyhour(params2).then(res=>{
          let data = res.data;
            if(data.code != 'A000000') {
              this.$message.error(data.message);
            }
            this.dialogVisible = false
            var series= []; var legends =[];
            echartsDate =  data.data.categories 
            series = data.data.series
            for(let serie of series){
                legends.push(serie.name);
            }
            var myChart = echarts.init(document.getElementById("main"));
            myChart.clear()
            myChart.setOption({
            title: {
                // text: '客户数据'
            },
            tooltip: {
                trigger: 'axis'
            },
            legend: {
                data:legends
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
            series: series


            });
            
            });
   
            var mediaSet = new Set(this.checkList);
            let params3 = {
                        mediaIds:Array.from(mediaSet).join(','),
                        startDate:this.eDay,
                        endDate:this.eDay,
                }
            consumerdatagetPlatformsdatatoday(params3).then(res=>{
                  let data = res.data;
                    this.tableData = data.data
                    this.dialogVisible2 = false
                });
     }

    },


    },
//     mounted() {
//         var myChart = echarts.init(document.getElementById("main"));
//         myChart.setOption({
//                 title: {
//                     text: ''
//                 },
//                 tooltip: {
//                     trigger: 'axis'
//                 },
//                 legend: {
//                     data:this.titleList
//                 },
//                 grid: {
//                     left: '3%',
//                     right: '4%',
//                     bottom: '3%',
//                     containLabel: true
//                 },
//                 toolbox: {
//                     feature: {
//                         saveAsImage: {}
//                     }
//                 },
//                 xAxis: {
//                     type: 'category',
//                     boundaryGap: false,
//                     data: []
//                 },
//                 yAxis: {
//                     type: 'value'
//                 },
//                 series: [
//                     {
//                         name:this.titleList[0],
//                         type:'line',
//                         // stack: '总量',
//                         data: []
//                     },
//                     {
//                         name:this.titleList[1],
//                         type:'line',
//                         // stack: '总量',
//                         data:[]
//                     },
//                     {
//                         name:this.titleList[2],
//                         type:'line',
//                         // stack: '总量',
//                         data:[]
//                     },
//                     {
//                         name:this.titleList[3],
//                         type:'line',
//                         // stack: '总量',
//                         data:[]
//                     },
//                 ]
//                 });
//   },
};
</script>

<style scoped>
.block {
  margin-bottom: 3%;
}

</style>
