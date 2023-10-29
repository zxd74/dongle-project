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
            <!-- <div class="flot_right">
                <template>
                    <el-select v-model="value" placeholder="请选择">
                        <el-option
                        v-for="item in options"
                        :key="item.value"
                        :label="item.label"
                        :value="item.value">
                        </el-option>
                    </el-select>
                </template>
            </div> -->
        </div>
        <!-- ECharts图表测试 -->
        <div id="charts" style="width:'100%',height:'500px'">
            <div id="main" :style="{width:'100%',height:'500px'}"></div>
        </div>
    </div>
</template>

<script>
import { companyquotaByDay } from "@/api/Api.js";

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
    // creDay:[],
    // exp:[],
    // clk:[],
    // cost:[],
    // rate:[],
    titleList:['展现量','点击量','点击率%','消耗'],

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
            type:1,
            sDay:this.sDay,
            eDay:this.eDay
            }
        companyquotaByDay(params).then(res=>{
          let data = res.data;
            if(data.code != 'A000000') {
              this.$message.error(data.message);
               return;
            }
            // this.item = data.data;


            var reqs=[];var clks=[];var cost=[];var costs=[];
            for(var item of data.data){
                console.log(item);
                echartsDate.push(item.creDay);
                reqs.push(item.exp);//展现量
                clks.push(item.clk); //点击量
                if (item.exp && item.exp > 0) {
                    costs.push(((item.clk / item.exp) * 100).toFixed(2));
                } else {
                    costs.push(0);
                }//点击率
                cost.push((item.cost/100000).toFixed(2));//消耗
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
                {
                    name:this.titleList[1],
                    type:'line',
                    // stack: '总量',
                    data:clks,
                },
                {
                    name:this.titleList[2],
                    type:'line',
                    // stack: '总量',
                    data:costs,
                },
                {
                    name:this.titleList[3],
                    type:'line',
                    // stack: '总量',
                    data:cost
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
                text: '折线图堆叠'
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
