<template>
    <div class="box">
        <!-- <div class='div'> -->
             <!-- 表格 -->
             <div class="left">
                <el-table :data="data" border  ref="multipleTable" id="table">
                        <el-table-column prop="id" label="序号" align="center">
                        </el-table-column>
                        <el-table-column prop="" label="人群所选标签组" align="center">
                            <template slot-scope="scope">
                                <el-button size="small" type="text" @click="handleEdit(scope.$index, scope.row)">{{scope.row.name}}</el-button>
                            </template>
                        </el-table-column>
                </el-table>
            </div>
            <div class="right">
                <!-- ECharts图表测试 -->
                <div id="charts" style="width:'100%',height:'500px'">
                    <div id="main" :style="{width:'100%',height:'500px'}"></div>
                </div>
            </div>
        <!-- </div> -->
    </div>
</template>

<script>
import {personsgetTagsd,personsgetTagPercent} from '@/api/Api.js';


// import Schart from "vue-schart";
// 引入 ECharts 主模块
var echarts = require("echarts/lib/echarts");
require('echarts/lib/chart/bar');

// require("echarts/lib/chart/line");
// require("echarts/lib/component/legend");
require("echarts/lib/component/tooltip");
require("echarts/lib/component/title");
// require("echarts/lib/component/toolbox");

// import { tianqi } from "@/api/Api.js";

export default {
  name: "basecharts",
  components: {
    // Schart
  },
  data: () => ({
    startDate: "",
    data: [],
    endDate: "",
    radio:'1',
     options: [{
          value: '选项1',
          label: '流量源：全部'
        }, {
          value: '选项2',
          label: '站点1'
        }, {
          value: '选项3',
          label: '站点2'
        }, {
          value: '选项4',
          label: 'APP1'
        },],
    value: '选项1',
    data:[
        {data:''},
    ],
    // titleList:['曝光数','点击数','激活数','点击率(%)','转化率(%)'],

  }),

  created() {
   this.getData();
  },
  methods: {
    //初始列表
    getData() {
        let params = {
            id:this.$route.query.id
        };
        personsgetTagsd(params).then(res => {
            console.log(res)
          let data = res.data;
            // if (data.code != 'A000000') {
            //     this.$message.error(data.message);
            // }
            console.log(data)
            this.data = data.data;
            // this.total = data.data.totalItemNum;
        });
    },
    handleEdit(index, row){
            var echartsDate  = [];
            var datas = [];
            var nums = [];
            let params = {
                pid:this.$route.query.id,
                tid:row.id
            }
         personsgetTagPercent(params).then(res => {
            console.log(res)
          let data = res.data;
            if (data.code != 'A000000') {
                this.$message.error(data.message);
            }
            for(var item of data.data){
                echartsDate.push(item.tagName  + " 占比(％)：" + (item.percent*100).toFixed(2));
                datas.push(item.num);
            }

            var myChart = echarts.init(document.getElementById("main"));
            myChart.setOption({
            color: ['#3398DB'],
            title: {
                // text: '客户数据'
            },
            tooltip : {
                trigger: 'axis',
                axisPointer : {            // 坐标轴指示器，坐标轴触发有效
                    type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
                }
            },
            // legend: {
            //     data:this.titleList
            // },
            grid: {
                left: '3%',
                right: '4%',
                bottom: '3%',
                containLabel: true
            },
              xAxis : [
                    {
                        type : 'category',
                        data : echartsDate,
                        axisTick: {
                            alignWithLabel: true
                        }
                    }
                ],
            yAxis: {
                type: 'value'
            },
            series : [
                    {
                        name:'覆盖人数',
                        type:'bar',
                        barWidth: '20%',
                        data:datas
                    }
                ]

            });
        });

    }

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
.div{
    display: flex
}
/* #charts{
    flex: 0.8
}
#table{
    flex: 0.2
} */
.left{
    float: left;
    width: 20%;
    margin-top: 5%;
}
.right{
    float: right;
    width: 70%;
}
</style>
