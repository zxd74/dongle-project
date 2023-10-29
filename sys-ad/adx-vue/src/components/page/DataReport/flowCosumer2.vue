<template>
    <div class="box">
        <div class="block">
           <div class="header-select">
                 <template>
                     <!-- <el-tag size="medium">日期:</el-tag>    
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
                      <el-button type="primary" icon="search" @click="search">搜索</el-button> -->
                      <el-button type="primary" icon="search" @click="exportExcel">导出</el-button> 
                </template>
            </div>
        </div>
        
        <!-- ECharts图表测试 -->
        <!-- <div id="charts" style="width:'100%',height:'500px'">
            <div id="main" :style="{width:'100%',height:'500px'}"></div>
        </div> -->
         <!-- <el-tag size="medium">流量源数据:</el-tag> -->
           <el-table :data="tableData" border style="width: 100%" ref="multipleTable" id="allTable"  v-loading="dialogVisible">
                <el-table-column prop="creDay" label="日期">
                </el-table-column>
                <el-table-column prop="itemName" label="名称">
                </el-table-column>
                <el-table-column prop="req" label="请求量">
                </el-table-column>
                <el-table-column prop="timeout" label="超时">
                </el-table-column>
                <el-table-column prop="" label="超时率%">
                        <template slot-scope="scope">
                            {{scope.row.timeout !=0 && scope.row.req != 0?((scope.row.timeout/scope.row.req)*100).toFixed(2):'0.00'}}
                        </template>
                </el-table-column>
                <el-table-column prop="nobid" label="不竞价">
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
                <el-table-column prop="exp" label="曝光量" >
                </el-table-column>
                <el-table-column prop="exp_rate" label="填充率%" >
                </el-table-column>
                <el-table-column prop="clk" label="点击量">
                </el-table-column>
                 <el-table-column prop="clk_rate" label="点击率%" >
                </el-table-column>
                 <el-table-column prop="investment" label="总收入" >
                </el-table-column>
                 <el-table-column prop="cpm" label="cpm" >
                </el-table-column>
                 <el-table-column prop="cpc" label="cpc" >
                </el-table-column>
         </el-table>
         <!-- <div class="border"></div>
        <el-tag size="medium">流量源分日数据:</el-tag>
        <el-table :data="data" border style="width: 100%" ref="multipleTable" id="table">
                <el-table-column prop="creDay" label="日期">
                </el-table-column>
                <el-table-column prop="req" label="请求量">
                </el-table-column>
                <el-table-column prop="exp" label="展现量">
                </el-table-column>
                <el-table-column prop="exp_rate" label="展现率%">
                </el-table-column>
                <el-table-column prop="clk" label="点击量">
                </el-table-column>
                <el-table-column prop="clk_rate" label="点击率">
                </el-table-column>
                <el-table-column prop="investment" label="花费">
                </el-table-column>
         </el-table>   -->
        <!-- <div class="pagination">
            <el-pagination @current-change="handleCurrentChange" 
                layout="total,prev, pager, next,jumper" 
                :total="total" 
                :current-page="cur_page" 
                :page-size="ps"
                ref="pagination"
                >
            </el-pagination>
        </div> -->
    </div>
</template>

<script>
// import { flowsourcegetlist ,flowdatagetfsbyday,flowdatagetlistbyfs} from "@/api/Api.js";
import { consumerdatadetailReport
} from "@/api/Api.js";

   // 导出模块
import FileSaver from 'file-saver'
import XLSX from 'xlsx'
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
    dialogVisible:false,
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
    classify:'',
    data:[],
    tableData:[],
    from:{

    },
   optionsType:[
         {
          value: '',
          label: ''
        }
      ],
    value: '选项1',
    value2:'',
     options2: [],
     value3:'',
     ss:'',
     
    
    titleList:['请求量','展现量','点击量','展现率%','点击率','花费','CPM','CPC'],
      
  }),

  created() {
        //  this.search()
        this.gerlist()
  },
 
   methods: {
       gerlist(){
               this.dialogVisible = true 
               let par = {
                        group:this.$route.query.group,
                        id:this.$route.query.id,
                        pids:this.$route.query.pids,
                        appids:this.$route.query.appids,
                        adpids:this.$route.query.adpids,
                        sDate:this.$route.query.sDate,
                        eDate:this.$route.query.eDate,
                    }
                consumerdatadetailReport(par).then(res=>{
                    let data = res.data;     
                    this.tableData = data.data
                    this.dialogVisible = false 
                });
       },
    //  分页导航
    handleCurrentChange(val) {
    //   console.log(val);
      this.cur_page = val;
       let par ={
            startDate:this.startDay,
            endDate:this.endDay,
            mediaId:this.$route.query.mediaIds,
            currentPage:this.cur_page,
            pageSize:this.ps 
    }
    flowdatagetflowdatapagebyday(par).then(res=>{
        let data = res.data;     
        this.data = data.data.data
        this.total = data.data.totalItemNum;
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

                FileSaver.saveAs(new Blob([wbout], { type: 'application/octet-stream' }), '广告平台分日数据.xlsx')

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
</style>