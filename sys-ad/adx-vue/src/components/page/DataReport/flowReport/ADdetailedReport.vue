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
                        </el-date-picker>  -->
                       <el-button type="primary" icon="search" @click="goBack">返回</el-button> 
                      <el-button type="primary" icon="search" @click="exportExcel">导出</el-button> 
                </template>
            </div>
        </div>
        
        <!-- ECharts图表测试 -->
        <!-- <div id="charts" style="width:'100%',height:'500px'">
            <div id="main" :style="{width:'100%',height:'500px'}"></div>
        </div> -->
         <!-- <el-tag size="medium">流量源数据:</el-tag> -->
           <el-table :data="tableData" border style="width: 100%" ref="multipleTable" id="allTable">
                <el-table-column prop="creDay" label="日期">
                </el-table-column>
                <el-table-column prop="itemName" label="名称">
                </el-table-column>
                <el-table-column prop="req" label="请求量">
                </el-table-column>
                <el-table-column prop="requv" label="请求UV">
                </el-table-column> 
                <el-table-column prop="exp" label="曝光量" >
                </el-table-column>
                <el-table-column prop="expuv" label="expUV" >
                </el-table-column>
                <el-table-column prop="exp_rate" label="填充率%" >
                </el-table-column>
                <el-table-column prop="clk" label="点击量">
                </el-table-column>
                <el-table-column prop="clkuv" label="点击uv">
                </el-table-column>
                 <el-table-column prop="clk_rate" label="点击率%" >
                </el-table-column>
                 <el-table-column prop="investment" label="总收入" >
                </el-table-column>
                 <el-table-column prop="cpm" label="cpm" >
                </el-table-column>
                 <el-table-column prop="cpc" label="cpc" >
                </el-table-column>
                <el-table-column prop="expPer" label="人均曝光数" >
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
import { flowdatagetflowdatabyday,flowdatagetflowdatapagebyday,
quotaFlowdetail
} from "@/api/Api.js";

// import Schart from "vue-schart";
// 引入 ECharts 主模块
var echarts = require("echarts/lib/echarts");
// 导出模块
import FileSaver from 'file-saver'
import XLSX from 'xlsx'

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
        let par = {
                // sDate:this.startDay,
                // eDate:this.endDay,
                // flowId:this.classify.join(','),
                // appids:this.appIds.join(','),
                // adpids:this.systemAD.join(','),
                // channels:this.AppChannel.join(','),
                // versions:this.AppVersion.join(','),
                group:this.$route.query.group,
                id:this.$route.query.id,
                flowId:this.$route.query.flowId,
                appids:this.$route.query.appids,
                adpids:this.$route.query.adpids,
                channels:this.$route.query.channels,
                versions:this.$route.query.versions,
                sDate:this.$route.query.sDate,
                eDate:this.$route.query.eDate,
            }
        quotaFlowdetail(par).then(res=>{
            let data = res.data;     
            this.tableData = data.data
        });
       },
    goBack(){
        window.history.back(-1)
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
    //   搜索
      search(){
           this.$refs.pagination.lastEmittedPage = 1
           this.cur_page = 1;
        var beginDt = new Date();
        var echartsDate  = [];
        let params = {
                startDate:this.startDay,
                endDate:this.endDay,
                mediaId:this.$route.query.mediaIds,
                isAll:false
            }
             flowdatagetflowdatabyday(params).then(res=>{
          let data = res.data;
            if(data.code != 'A000000') {
              this.$message.error(data.message);
            }   
        //    console.log(data)

        var reqs=[];var bid=[];var exp=[];var clk=[];var bid_rate=[];var exp_rate=[];var clk_rate=[];var cpm=[];var cpc=[];var cost=[];
            for(var item of data.data){
                echartsDate.push(item.creDay); // 日期
                reqs.push(item.req);//请求量
                // bid.push(item.bid); //填充量
                exp.push(item.exp); //展现量
                clk.push(item.clk); //点击量
                // bid_rate.push(item.bid_rate);//填充率
                exp_rate.push(item.exp_rate);//曝光率
                clk_rate.push(item.clk_rate);//点击率
                cost.push(item.investment);//成本
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
                    data:cpm
                },
                {
                    name:this.titleList[6],
                    type:'line',
                    // stack: '总量',                    
                    data:cpc
                },
                {
                    name:this.titleList[7],
                    type:'line',
                    // stack: '总量',                    
                    data:cost
                },
                ]
                });
                 let params = {
                       startDate:this.startDay,
                       endDate:this.endDay,
                       mediaId:this.$route.query.mediaIds,
                       isAll:true
                    }
                flowdatagetflowdatabyday(params).then(res=>{
                  let data = res.data;     
                    this.tableData = data.data
                });
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

            FileSaver.saveAs(new Blob([wbout], { type: 'application/octet-stream' }), '流量源数据.xlsx')

            // FileSaver.saveAs(new Blob([wbout1], { type: 'application/octet-stream' }), '流量源分日数据.xlsx')

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