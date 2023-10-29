<template>
    <div class="box">
        <div class="block">
           <div class="header-select">
                 <template>
                      <el-tag size="medium">客户名称:</el-tag>
                        <el-autocomplete
                          class="inline-input"
                          v-model="state1"
                          value-key="fullName"
                          :fetch-suggestions="querySearch"
                          placeholder="请输入客户名称"
                          @select="handleSelect"
                        ></el-autocomplete>
                      <el-tag size="medium">日期:</el-tag>
                        <el-date-picker
                          :picker-options="pickerOptions1"
                            v-model="sDay"
                            type="date"
                            placeholder="选择日期"
                            format="yyyy 年 MM 月 dd 日"
                            value-format="yyyyMMdd">
                        </el-date-picker> -
                        <el-date-picker
                          :picker-options="pickerOptions1"
                            v-model="eDay"
                            type="date"
                            placeholder="选择日期"
                            format="yyyy 年 MM 月 dd 日"
                            value-format="yyyyMMdd">
                        </el-date-picker>
                      <el-button type="primary" icon="search" @click="search">搜索</el-button>
                      <el-button type="primary" icon="search" @click="exportExcel">导出</el-button>
                </template>
            </div>
        </div>

        <!-- ECharts图表测试 -->
        <div id="charts" style="width:'100%',height:'500px'">
            <div id="main" :style="{width:'100%',height:'500px'}"></div>
        </div>
        <el-tag size="medium">客户数据:</el-tag>
        <el-table :data="tableCustom" border style="width: 100%" ref="multipleTable" id="tabs">
                <!-- <el-table-column prop="id" label="ID">
                </el-table-column> -->
                <!-- <el-table-column prop="name" label="客户名称">
                </el-table-column> -->
                <el-table-column prop="exp" label="展现量">
                  <template slot-scope="scope">
                     {{scope.row.exp}}
                  </template>
                </el-table-column>
                <el-table-column prop="clk" label="点击量">
                    <template slot-scope="scope">
                     {{scope.row.clk}}
                  </template>
                </el-table-column>
                <el-table-column prop="costs" label="点击率">
                    <template slot-scope="scope">
                     {{scope.row.costs.toFixed(2)+'%'}}
                  </template>
                </el-table-column>
                <el-table-column prop="cost" label="花费">
                    <template slot-scope="scope">
                     {{(scope.row.cost/100000).toFixed(2)}}
                  </template>
                </el-table-column>
                <el-table-column prop="CPM" label="CPM">
                    <template slot-scope="scope">
                     {{scope.row.CPM.toFixed(2)}}
                  </template>
                </el-table-column>
                <el-table-column prop="CPC" label="CPC">
                     <template slot-scope="scope">
                     {{scope.row.CPC.toFixed(2)}}
                  </template>
                </el-table-column>
                <!-- <el-table-column prop="active" label="激活数">
                     <template slot-scope="scope">
                     {{scope.row.active.toFixed(2)}}
                  </template>
                </el-table-column>
                <el-table-column prop="CPA" label="CPA">
                     <template slot-scope="scope">
                     {{scope.row.CPA.toFixed(2)}}
                  </template>
                </el-table-column>
                <el-table-column prop="CVR" label="CVR">
                     <template slot-scope="scope">
                     {{scope.row.CVR.toFixed(2)}}
                  </template>
                </el-table-column> -->
        </el-table>
        <div class="border"></div>
        <el-tag size="medium">客户分日数据:</el-tag>
        <el-table :data="tableDate" border style="width: 100%" ref="multipleTable" id="table">
                <el-table-column prop="creDay" label="日期">
                </el-table-column>
                <el-table-column prop="exp" label="展现量">
                </el-table-column>
                <el-table-column prop="clk" label="点击量">
                </el-table-column>
                <el-table-column  label="点击率" >
                  <template slot-scope="scope">
                     {{scope.row.clk !=0 && scope.row.exp != 0?((scope.row.clk/scope.row.exp)*100).toFixed(2)+'%':'0.00'+'%'}}
                  </template>
                </el-table-column>
                <el-table-column label="花费">
                    <template slot-scope="scope">
                     {{scope.row.cost !=0 ?(scope.row.cost/100000).toFixed(2):0}}
                  </template>
                </el-table-column>
                <!-- <el-table-column prop="active" label="激活数">
                </el-table-column> -->
        </el-table>
    </div>
</template>

<script>
// 导出模块
import FileSaver from 'file-saver'
import XLSX from 'xlsx'

import { companyquotaByUser } from "@/api/Api.js";
import { AgentList } from "@/api/Api.js";

// import Schart from "vue-schart";
// 引入 ECharts 主模块
var echarts = require("echarts/lib/echarts");

require("echarts/lib/chart/line");
require("echarts/lib/component/legend");
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
       pickerOptions1: {
         disabledDate(time) {
            return time.getTime() > Date.now();
          },
    },
    tableDate:[],
    tableCustom:[],
    sDay: new Date().Format("yyyyMMdd"),
    eDay: new Date().Format("yyyyMMdd"),
    radio:'1',
    checkAll: false,
    // cities: cityOptions,
    isIndeterminate: true,
    flow_sueers:'',
    add_time:'',
    idea_ID:'',
    idea_:'',
    from:{

    },
    state1:'',
    id:'',
    rate:'',
    costs:'',

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
    titleList:[ '展现量','点击量','点击率%','花费', 'CPM','CPC'],
  }),
methods: {
      // 模糊搜索
      querySearch(queryString, cb) {
       let params = {
            fullName:this.state1,
            type:2,
            cp:1,
            ps:20
            }
      AgentList(params).then(res=>{
     let data = res.data;
        // 调用 callback 返回建议列表的数据
        cb(data.data.data);
       if(data.code != 'A000000') {
          this.$message.error(data.message);
            }
        });
      },
    handleSelect(item){
      // console.log(item);
      this.id = item.id
    },
    // 搜索
    search(){
         if(!this.state1){
              this.$message('名称不能为空')
              return;
          }
        var beginDt = new Date();
        var echartsDate  = [];
         let params = {
            uid:this.id,
            sDay:this.sDay,
            eDay:this.eDay
            }
        companyquotaByUser(params).then(res=>{
          let data = res.data;
            if(data.code != 'A000000') {
              this.$message.error(data.message);
            }
            this.tableDate = data.data
            this.tableCustom = [];
            let tableCustom = {id:'',name:'',exp: 0, clk: 0,costs:0,cost:0,CPM:0,CPC:0,active:0,CPA:0,CVR:0};
            // this.tableCustom[0] = data.data.custId
            // this.tableCustom[1] = data.data.name
            for(item of this.tableDate){
                tableCustom.id = item.custId;
                // console.log(item.custId)
                tableCustom.name = item.name;
                tableCustom.exp += item.exp;
                tableCustom.clk += item.clk;
                // if(item.clk ==0 || item.exp ==0) {
                //     tableCustom.costs = 0;
                // }else {
                //     tableCustom.costs += parseFloat(((item.clk/item.exp)*100).toFixed(2));
                // }
                tableCustom.cost += item.cost
                //
                // if(item.cost ==0 || item.exp ==0) {
                //     tableCustom.CPM = 0;
                // }else {
                //      tableCustom.CPM += parseFloat(((item.cost/item.exp)/100).toFixed(2))
                // }
                //
                // if(item.clk ==0 || item.cost ==0) {
                //     tableCustom.CPC = 0;
                // }else {
                //     tableCustom.CPC += parseFloat((item.cost/item.clk/100000).toFixed(2))
                // }
                //
                // tableCustom.active += item.active
                // if(item.active != 0){
                //      tableCustom.CPA += parseFloat((item.cost/100000)/item.active)
                // }
                // if(item.active != 0){
                //     tableCustom.CVR += parseFloat(item.active/item.clk)
                // }
             }
            tableCustom.costs = tableCustom.exp == 0 ? 0 : ((tableCustom.clk / tableCustom.exp)*100);
            tableCustom.CPM = tableCustom.exp == 0 ? 0 : ((tableCustom.cost / tableCustom.exp)/100);
            tableCustom.CPC = tableCustom.clk == 0 ? 0 : ((tableCustom.cost / tableCustom.clk)/100000);
            this.tableCustom.push(tableCustom);

            var reqs=[];var clks=[];var cost=[];var costs=[];var cost=[];var CPM=[];var CPC=[];

            for(var item of data.data){
                echartsDate.push(item.creDay);
                reqs.push(item.exp);//展现量
                clks.push(item.clk); //点击量
                if(item.clk == 0 || item.exp == 0){
                    costs.push(0);
                }else{
                    costs.push(((item.clk/item.exp)*100).toFixed(2)); //点击率
                }
                cost.push((item.cost/100000).toFixed(2)); //花费
                CPM.push(((item.cost/item.exp)/100).toFixed(2)); //CPM
                CPC.push((item.cost/item.clk/100000).toFixed(2)); //CPC
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
                {
                    name:this.titleList[4],
                    type:'line',
                    // stack: '总量',
                    data:CPM
                },
                {
                    name:this.titleList[5],
                    type:'line',
                    // stack: '总量',
                    data:CPC
                },
            ]
            });

        });
    },
       //   导出table方法
    exportExcel () {
            /* generate workbook object from table */

            var wb = XLSX.utils.table_to_book(document.querySelector('#table'))

            var wb1 = XLSX.utils.table_to_book(document.querySelector('#tabs'))

            /* get binary string as output */

            var wbout = XLSX.write(wb, { bookType: 'xlsx', bookSST: true, type: 'array' })
            var wbout1 = XLSX.write(wb1, { bookType: 'xlsx', bookSST: true, type: 'array' })


            try {

            FileSaver.saveAs(new Blob([wbout], { type: 'application/octet-stream' }), '客户分日数据.xlsx')

            FileSaver.saveAs(new Blob([wbout1], { type: 'application/octet-stream' }), '客户数据.xlsx')

            } catch (e) { if (typeof console !== 'undefined') console.log(e, wbout) }

            return wbout
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
                    {
                    name:this.titleList[4],
                    type:'line',
                    // stack: '总量',
                    data:[]
                },
                {
                    name:this.titleList[5],
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
</style>
