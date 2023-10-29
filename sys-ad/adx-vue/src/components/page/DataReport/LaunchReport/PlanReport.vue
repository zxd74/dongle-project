<template>
    <div class="box">
        <div class="block">
           <div class="header-select">
                 <template>
                      <el-tag size="medium">计划ID:</el-tag>
                      <el-input v-model="plan_ID"  class="handle-input mr10"></el-input>
                      <el-tag size="medium">计划:</el-tag>
                      <el-input v-model="plan_"  class="handle-input mr10"></el-input>
                      <el-tag size="medium">日期:</el-tag>    
                        <el-date-picker
                                    v-model="add_time"
                                    type="daterange"
                                    range-separator="至"
                                    start-placeholder="开始日期"
                                    end-placeholder="结束日期">
                            </el-date-picker>
                      <el-button type="primary" icon="search" @click="search">搜索</el-button>
                      <el-button type="primary" icon="search" @click="search">导出</el-button>
                </template>
            </div>
        </div>
        
        <!-- ECharts图表测试 -->
        <div id="charts" style="width:'100%',height:'500px'">
            <div id="main" :style="{width:'100%',height:'500px'}"></div>
        </div>
        <el-table :data="data" border style="width: 100%" ref="multipleTable">
                <el-table-column prop="date" label="日期">
                </el-table-column>
                <el-table-column prop="date" label="展现量">
                </el-table-column>
                <el-table-column prop="date" label="点击量" >
                </el-table-column> 
                <el-table-column prop="date" label="点击率">
                </el-table-column>
                <el-table-column prop="date" label="收入(元)">
                </el-table-column>
                <el-table-column prop="date" label="CPM(元)">
                </el-table-column>
                <el-table-column prop="date" label="CPC(元)">
                </el-table-column>
            </el-table>
    </div>
</template>

<script>
// import Schart from "vue-schart";
// 引入 ECharts 主模块
var echarts = require("echarts/lib/echarts");

require("echarts/lib/chart/line");
require("echarts/lib/component/legend");
require("echarts/lib/component/tooltip");
require("echarts/lib/component/title");
// require("echarts/lib/component/toolbox");

// import { tianqi } from "@/api/Api.js";
const cityOptions = ['请求量', '填充量', '展现量', '填充率','展现率%', '点击量', '点击率', 'CPM','CPC'];
export default {
  name: "basecharts",
  components: {
    // Schart
  },
  data: () => ({
    startDate: "",
    endDate: "",
    radio:'1',
    checkAll: false,
    cities: cityOptions,
    isIndeterminate: true,
    flow_sueers:'',
    add_time:'',
    plan_ID:'',
    plan_:'',
    from:{

    },
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
    value2:'',
     options2: [],
     value3:'',
         options3: [
         {
          value: '1',
          label: '请求量'
        }, {
          value: '2',
          label: '填充量'
        }, {
          value: '3',
          label: '展现量'
        },
        {
          value: '4',
          label: '填充率'
        },
        {
          value: '5',
          label: '展现率%'
        },
        {
          value: '6',
          label: '点击量'
        },
        {
          value: '7',
          label: '点击率'
        },
        {
          value: '8',
          label: '展现CPM量'
        },
        {
          value: '9',
          label: 'CPC'
        },

        ],
    // titleList:['曝光数','点击数','激活数','点击率(%)','转化率(%)'],
      
  }),

  // created() {
  //   this.$axios.get("/api", {}).then(res => {
  //     console.log(res);
  //   });
  // },
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
        {
            name:'请求量',
            type:'line',
            stack: '总量',
            areaStyle: {normal: {}},
            data:[120, 132, 101, 134, 90, 230, 210]
        },
        {
            name:'展现量',
            type:'line',
            stack: '总量',
            areaStyle: {normal: {}},
            data:[150, 232, 201, 154, 190, 330, 410]
        },
        {
            name:'点击量',
            type:'line',
            stack: '总量',
            areaStyle: {normal: {}},
            data:[320, 332, 301, 334, 390, 330, 320]
        },
         {
            name:'展现率%',
            type:'line',
            stack: '总量',
            areaStyle: {normal: {}},
            data:[320, 332, 301, 334, 390, 330, 320]
        },
        {
            name:'点击率',
            type:'line',
            stack: '总量',
            areaStyle: {normal: {}},
            data:[320, 332, 301, 334, 390, 330, 320]
        },
         {
            name:'收入',
            type:'line',
            stack: '总量',
            areaStyle: {normal: {}},
            data:[320, 332, 301, 334, 390, 330, 320]
        },
         {
            name:'CPM',
            type:'line',
            stack: '总量',
            areaStyle: {normal: {}},
            data:[320, 332, 301, 334, 390, 330, 320]
        },
         {
            name:'CPC',
            type:'line',
            stack: '总量',
            areaStyle: {normal: {}},
            data:[320, 332, 301, 334, 390, 330, 320]
        },
    ]
    });


  },
   methods: {
      handleCheckAllChange(val) {
        this.checkedCities = val ? cityOptions : [];
        this.isIndeterminate = false;
      },
      handleCheckedCitiesChange(value) {
        let checkedCount = value.length;
        this.checkAll = checkedCount === this.cities.length;
        this.isIndeterminate = checkedCount > 0 && checkedCount < this.cities.length;
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
</style>