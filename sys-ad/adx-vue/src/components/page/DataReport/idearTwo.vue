<template>
    <div class="box">
        <div class="block">
           <div class="header-select">
                 <template>
                      <el-tag size="medium">日期:</el-tag>    
                        <el-date-picker
                          :picker-options="pickerOptions1"
                            v-model="startDay"
                            type="date"
                            placeholder="选择日期"
                            format="yyyy 年 MM 月 dd 日"
                            value-format="yyyyMMdd">
                        </el-date-picker> -
                        <el-date-picker
                          :picker-options="pickerOptions1"
                            v-model="endDay"
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
      
        <div class="border">创意分日数据:</div>
        <el-table :summary-method="getSummaries" show-summary :data="OrderDayData" border style="width: 100%" ref="multipleTable" id="table3">
                <el-table-column prop="creDay" label="日期" align="center">
                </el-table-column>
                <el-table-column prop="exp" label="展现量" align="center">
                </el-table-column>
                <el-table-column prop="clk" label="点击量" align="center">
                </el-table-column> 
                <el-table-column prop="clkRate" label="点击率" align="center">
                    <!-- <template slot-scope="scope" >
                        <span v-if="scope.row.exp!=0&&scope.row.clk!=0">
                            {{((scope.row.clk/scope.row.exp)*100).toFixed(2)+'%'}}
                        </span>
                        <span v-if="scope.row.exp==0||scope.row.clk==0">0</span>
                    </template>                   -->
                </el-table-column>
                 <el-table-column prop="realCost" label="花费" align="center">
                    <!-- <template slot-scope="scope" v-if="scope.row.clk != 0">
                        {{scope.row.realCost}}
                    </template> -->
                </el-table-column>
                <!-- <el-table-column prop="putActive" label="激活数" align="center">
                </el-table-column> -->
        </el-table>
    </div>
</template>

<script>
// import { quotaOrderlistByDay,quotaOrderlistByInit, quotaPutlistByOidOrPid} from "@/api/Api.js";
import { quotaEntityreportByDay, } from "@/api/Api.js";
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


const cityOptions = [ '展现量', '点击量', '点击率%', '花费', 'CPM','CPC'];
var  m = (new Date().getMonth()+1)<10?"0"+(new Date().getMonth()+1):(new Date().getMonth()+1)
var  d = (new Date().getDate()<10?"0"+new Date().getDate():new Date().getDate())
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
    stance:0,
    tableData: [],
    checkAll: false,
    cities: cityOptions,
    isIndeterminate: true,
    flow_sueers:'',
    add_time:'',
    OrderData:[],
    IdearList:[],
    OrderDayData:[],     
    titleList:['展现量','点击量','点击率%','花费','CPM','CPC'],
      
  }),

  created() {
    this.search();
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
           getSummaries(param) {
        const { columns, data } = param;
        const sums = [];
        columns.forEach((column, index) => {
          if (index === 0) {
            sums[index] = '合计';
            return;
          }
          const values = data.map(item => Number(parseFloat(item[column.property])));
          if (!values.every(value => isNaN(value))) {
            sums[index] = values.reduce((prev, curr) => {
              const value = Number(curr);
              if (!isNaN(value)) {
                return prev + curr;
              } else {
                return prev;
              }
            }, 0);
            if(index == 3) {
             let  num = ((sums[2]/sums[1])*100).toFixed(2)+'%'
               sums[index] = num;
            }else {
                sums[index] += ' ';
            }
          } else {
            sums[index] = '';
          }
        });

        return sums;
      },
      
        //   搜索
      search(){
        var beginDt = new Date();
        var echartsDate  = [];
          let params = {
            entId:this.$route.query.entId,
            sTime:this.startDay,
            eTime:this.endDay,
            }
          quotaEntityreportByDay(params).then(res=>{
            // this.tableData = res.data.result.data
            // this.total = res.data.result.totalItemNum;
            if(res.data.code != 'A000000') {
              this.$message.error(data.code);
            }
             // 创意分日数据
            res.data.result.forEach(element => {
                if(element.exp!=0&&element.clk!=0) {
                    element.clkRate = ((element.clk/element.exp)*100).toFixed(2)+'%';
                }
            }); 
            this.OrderDayData = res.data.result 
            console.log(res)

            var reqs=[];var bid=[];var exp=[];var clk=[];var bid_rate=[];var exp_rate=[];var clk_rate=[];var cpm=[];var cpc=[];var cost=[];
            for(var item of res.data.result){
                if(item.creDay){
                echartsDate.push(item.creDay); // 日期
                // reqs.push(item.req);//请求量
                // bid.push(item.bid); //填充量
                exp.push(item.exp); //展现量
                clk.push(item.clk); //点击量
                // bid_rate.push(item.bid_rate);//填充率
                // exp_rate.push(item.exp_rate);//曝光率
                clk_rate.push((item.clk/item.exp).toFixed(2));//点击率   clk/exp
                cost.push(item.realCost);//花费
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
                    // stack: '展现量',
                    data:exp,
                },
                {
                    name:this.titleList[1],
                    type:'line',               
                    // stack: '点击量',
                    data:clk,
                },
                {
                    name:this.titleList[2],
                    type:'line',
                    // stack: '点击率',
                    data:clk_rate,
                },
                {
                    name:this.titleList[3],
                    type:'line',
                    // stack: '花费',                    
                    data:cost
                },
                {
                    name:this.titleList[4],
                    type:'line',
                    // stack: 'CPM',                    
                    data:cpm
                },
                {
                    name:this.titleList[5],
                    type:'line',
                    // stack: 'CPC',                    
                    data:cpc
                },
                ]
                });
            //  
                //  let param = {
                //         sTime:this.startDay,
                //         eTime:this.endDay,
                //         oid:this.$route.query.orderId,
                //         putType:17
                //     }
                // quotaPutlistByOidOrPid(param).then(res=>{
                //    if(res.data.code != 'A000000') {
                //         this.$message.error(data.code);
                //         }
                //         console.log(res)
                //     this.IdearList = res.data.result.data
                // });
             
          });
      },
    //   IdearReport(index,row){
    //     this.$router.push({path:'/datareport/ideareport', query:{id:row.putId}})
    //   },
          //   导出table方法
    exportExcel () {
            /* generate workbook object from table */

            // var wb = XLSX.utils.table_to_book(document.querySelector('#table2'))

            var wb1 = XLSX.utils.table_to_book(document.querySelector('#table3'))

            /* get binary string as output */

            // var wbout = XLSX.write(wb, { bookType: 'xlsx', bookSST: true, type: 'array' })
            var wbout1 = XLSX.write(wb1, { bookType: 'xlsx', bookSST: true, type: 'array' })


            try {

            // FileSaver.saveAs(new Blob([wbout], { type: 'application/octet-stream' }), '订单投放列表.xlsx')

            FileSaver.saveAs(new Blob([wbout1], { type: 'application/octet-stream' }), '创意分日数据.xlsx')

            } catch (e) { if (typeof console !== 'undefined') console.log(e, wbout1) }

            return wbout1
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
    padding: 20px
}
</style>