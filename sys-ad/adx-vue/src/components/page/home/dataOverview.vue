<template>
    <div class="box">
        <div class="block">
            <template>
                <span class="demonstration">时间维度:</span>
                <el-radio-group v-model="radio" @change="RequestTime">
                    <el-radio :label="1">最近3天</el-radio>
                    <el-radio :label="2">最近7天</el-radio>
                    <el-radio :label="3">最近30天</el-radio>
                </el-radio-group>
            </template>
        </div>
        <!-- ECharts图表测试 -->
        <div id="charts" style="width:'100%',height:'500px'">
            <div id="main" :style="{width:'100%',height:'500px'}"></div>
        </div>
    </div>
</template>

<script>
import { flowdatagetflowalldata } from "@/api/Api.js";

// import Schart from "vue-schart";
// 引入 ECharts 主模块
var echarts = require("echarts/lib/echarts");
require("echarts/lib/chart/line");
require("echarts/lib/component/legend");
require("echarts/lib/component/tooltip");
require("echarts/lib/component/title");

export default {
  name: "basecharts",
  components: {
    // Schart
  },
  data: () => ({
    startDate: "",
    endDate: "",
    radio:1,
    value: '选项1',
    sDay:'',
    eDay:'',
    list:[],
    xAxis:[],
    item: [],
    titleList:['请求量','展现量','点击量','展现率%','点击率%','花费','CPM','CPC'],

  }),

  created() {
    this.RequestTime()
  },
  methods: {



    RequestTime() {
         var beginDt = new Date();
        var echartsDate  = [];

        if(this.radio == 1){
            //获取当前日期
        this.date = new Date();
        var  m = (this.date.getMonth()+1)<10?"0"+(this.date.getMonth()+1):(this.date.getMonth()+1)
        var  d = this.date.getDate()<10?"0"+this.date.getDate():this.date.getDate()
        this.eDay=this.date.getFullYear()+""+m+""+d;
            //获取3天前得日期
        var lw = new Date(new Date() - 1000 * 60 * 60 * 24 * 2);//最后一个数字30可改，30天的意思
        var lastY = lw.getFullYear();
        var lastM = lw.getMonth()+1;
        var lastD = lw.getDate();
        this.sDay=lastY+""+(lastM<10 ? "0" + lastM : lastM)+""+(lastD<10 ? "0"+ lastD : lastD);

        }
        if(this.radio == 2){
            //获取当前日期
        this.date = new Date();
        var  m = (this.date.getMonth()+1)<10?"0"+(this.date.getMonth()+1):(this.date.getMonth()+1)
        var  d = this.date.getDate()<10?"0"+this.date.getDate():this.date.getDate()
        this.eDay=this.date.getFullYear()+""+m+""+d;
        var lw = new Date(new Date() - 1000 * 60 * 60 * 24 * 6);//最后一个数字30可改，30天的意思
        var lastY = lw.getFullYear();
        var lastM = lw.getMonth()+1;
        var lastD = lw.getDate();
        this.sDay=lastY+""+(lastM<10 ? "0" + lastM : lastM)+""+(lastD<10 ? "0"+ lastD : lastD);
        }
        if(this.radio == 3){
        //获取当前日期
        //获取当前日期
        this.date = new Date();
        var  m = (this.date.getMonth()+1)<10?"0"+(this.date.getMonth()+1):(this.date.getMonth()+1)
        var  d = this.date.getDate()<10?"0"+this.date.getDate():this.date.getDate()
        this.eDay=this.date.getFullYear()+""+m+""+d;
         // 获取30天前得日期
        var lw = new Date(new Date() - 1000 * 60 * 60 * 24 * 29);//最后一个数字30可改，30天的意思
        var lastY = lw.getFullYear();
        var lastM = lw.getMonth()+1;
        var lastD = lw.getDate();
        this.sDay=lastY+""+(lastM<10 ? "0" + lastM : lastM)+""+(lastD<10 ? "0"+ lastD : lastD);
        }
         let params = {
            startDate:this.sDay,
            endDate:this.eDay
            }
        flowdatagetflowalldata(params).then(res=>{
          let data = res.data;
            if(data.code != 'A000000') {
              this.$message.error(data.message);
              return;
            }
           console.log(data)


        var reqs=[];var exp=[];var clk=[];var exp_rate=[];var clk_rate=[];var cost=[];var cpm=[];var cpc=[];
            for(var item of data.data){
                console.log(item);
                echartsDate.push(item.creDay); // 日期
                reqs.push(item.req);//请求量
                // bid.push(item.bid); //填充量
                exp.push(item.exp); //展现量
                clk.push(item.clk); //点击量
                // bid_rate.push(item.bid_rate);//填充率
                exp_rate.push(item.exp_rate);//曝光率
                clk_rate.push(item.clk_rate);//点击率
                cost.push(item.investment);//花费
                cpm.push(item.cpm);//cpm
                cpc.push(item.cpc);//cpc
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
                    data:reqs,
                },
                // {
                //     name:this.titleList[1],
                //     type:'line',
                //     // stack: '总量',
                //     data:bid,
                // },
                {
                    name:this.titleList[1],
                    type:'line',
                    // stack: '总量',
                    data:exp,
                },
                {
                    name:this.titleList[2],
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
                    name:this.titleList[3],
                    type:'line',
                    // stack: '总量',
                    data:exp_rate
                },
                {
                    name:this.titleList[4],
                    type:'line',
                    // stack: '总量',
                    data:clk_rate
                },
                {
                    name:this.titleList[5],
                    type:'line',
                    // stack: '总量',
                    data:cost
                },
                {
                    name:this.titleList[6],
                    type:'line',
                    // stack: '总量',
                    data:cpm
                },
                {
                    name:this.titleList[7],
                    type:'line',
                    // stack: '总量',
                    data:cpc
                },
            ]
            });

        });

      },

  },
  mounted() {
    var myChart = echarts.init(document.getElementById("main"));
    myChart.setOption({
            title: {
                text: ''
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
                data: []
            },
            yAxis: {
                type: 'value'
            },
            series: [
                {
                    name:this.titleList[0],
                    type:'line',
                    // stack: '总量',
                    data: []
                },
                {
                    name:this.titleList[1],
                    type:'line',
                    // stack: '总量',
                    data:[]
                },
                {
                    name:this.titleList[2],
                    type:'line',
                    // stack: '总量',
                    data:[]
                },
                {
                    name:this.titleList[3],
                    type:'line',
                    // stack: '总量',
                    data:[]
                },
            ]
            });
  }
};
</script>

<style scoped>
.block {
  margin-bottom: 3%;
}
.demonstration{
    font-size: 18px;
    font-weight: 500
}
.flot_right{
    float: right
}
</style>
