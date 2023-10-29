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
       
        <div class="border">投放列表:</div>
        <el-table :data="IdearList" border style="width: 100%" ref="multipleTable" id="table2">
                <el-table-column prop="putId" label="投放ID" align="center">
                </el-table-column>
                <el-table-column prop="putName" label="订单投放名称" align="center">
                </el-table-column>
                <el-table-column prop="exp" label="展现量" align="center">
                </el-table-column> 
                <el-table-column prop="clk" label="点击量" align="center">
                </el-table-column>
                <el-table-column prop="" label="点击率" align="center">
                    <template slot-scope="scope">
                         {{scope.row.clk !=0 && scope.row.exp != 0?((scope.row.clk/scope.row.exp)*100).toFixed(2)+'%':'0.00'+'%'}}
                    </template>
                </el-table-column>
                <el-table-column prop="" label="花费" align="center">
                    <template slot-scope="scope">
                        {{scope.row.realCost}}
                    </template>
                </el-table-column>
                <el-table-column prop="cpm" label="CPM" align="center">
                </el-table-column>
                <el-table-column prop="cpc" label="CPC" align="center">
                </el-table-column>
                <!-- <el-table-column prop="putActive" label="激活数" align="center">
                </el-table-column>
                <el-table-column prop="cpa" label="CPA" align="center">
                </el-table-column>
                <el-table-column prop="cvr" label="CVR" align="center">
                </el-table-column> -->
                <el-table-column align="center" width="110">
                    <template slot-scope="scope" align="center">
                        <el-button size="small" type="success" @click="IdearReport(scope.$index, scope.row)">投放报告</el-button>
                    </template>
                </el-table-column>
        </el-table>
        <div class="pagination">
           <el-pagination @current-change="handleCurrentChange" layout="total,prev, pager, next,jumper" :total="total">
           </el-pagination>
        </div>
        <div class="border">计划分日数据:</div>
        <el-table :summary-method="getSummaries" show-summary :data="OrderDayData" border   style="width: 100%" ref="multipleTable" id="table3">
                <el-table-column prop="creDay" label="日期" align="center">
                </el-table-column>
                <el-table-column prop="exp" label="展现量" align="center">
                </el-table-column>
                <el-table-column prop="clk" label="点击量" align="center">
                </el-table-column> 
               <el-table-column prop="clkRate" label="点击率" align="center">
                    <!-- <template slot-scope="scope" v-if="scope.row.clk != 0">
                       <span v-if="scope.row.exp!=0&&scope.row.clk!=0">
                            {{((scope.row.clk/scope.row.exp)*100).toFixed(2)+'%'}}
                        </span>
                        <span v-if="scope.row.exp==0||scope.row.clk==0">0</span>
                    </template> -->
                </el-table-column>
                <el-table-column prop="realCost" label="花费" align="center">
                    <!-- <template slot-scope="scope" v-if="scope.row.clk != 0">
                        {{scope.row.realCost}}
                    </template> -->
                </el-table-column>
                <!-- <el-table-column prop="planActive" label="激活数" align="center">
                </el-table-column> -->
        </el-table>
    </div>
</template>

<script>
// import { quotaOrderlistByDay,quotaOrderlistByInit, quotaPutlistByOidOrPid} from "@/api/Api.js";
import { quotaPlanlistByDay,quotaPutlistByOidOrPid} from "@/api/Api.js";
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
const cityOptions = [ '展现量', '点击量', '点击率', '花费', 'CPM','CPC'];
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
    tableData: [],
    checkAll: false,
    cities: cityOptions,
    isIndeterminate: true,
    flow_sueers:'',
    add_time:'',
    OrderData:[],
    IdearList:[],
    OrderDayData:[],
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
        
      titleList:['展现量','点击量','点击率%','花费','CPM','CPC'],
      
  }),

  created() {
    this.search();
    this.getOrder()
    // console.log(111)
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
         // 分页导航
      handleCurrentChange(val) {
        console.log(val)
        this.cur_page = val;
        this.getOrder();
      },
      
        //   搜索
      search(){
        var beginDt = new Date();
        var echartsDate  = [];
          let params = {
            id:this.$route.query.planId,
            sTime:this.startDay,
            eTime:this.endDay,
            }
            console.log(11)
          quotaPlanlistByDay(params).then(res=>{
            // this.tableData = res.data.result.data
            // this.total = res.data.result.totalItemNum;
            if(res.data.code != 'A000000') {
              this.$message.error(data.code);
            }
             // 分日数据
            res.data.result.forEach(element => {
                if(element.exp!=0&&element.clk!=0) {
                    element.clkRate = ((element.clk/element.exp)*100).toFixed(2)+'%';
                }
            }); 
            this.OrderDayData = res.data.result;
         
           
            var reqs=[];var bid=[];var exp=[];var clk=[];var bid_rate=[];var exp_rate=[];var clk_rate=[];var cpm=[];var cpc=[];var cost=[];
            console.log(res.data.result)
            for(var item of res.data.result){
                console.log(item)
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
            //   投放列表
                 let param = {
                        sTime:this.startDay,
                        eTime:this.endDay,
                        pid:this.$route.query.planId,
                        putType:19,
                        cp:1,
                        ps:10
                    }
                quotaPutlistByOidOrPid(param).then(res=>{
                   if(res.data.code != 'A000000') {
                        this.$message.error(data.code);
                        }
                        console.log(res)
                    this.IdearList = res.data.result.data
                    this.total = res.data.result.totalItemNum
                    console.log(this.total)
                });
             
          });
      },
      IdearReport(index,row){
        this.$router.push({path:'/datareport/ideaplan', query:{id:row.putId}})
      },
      getOrder(){
        let param = {
                sTime:this.startDay,
                eTime:this.endDay,
                pid:this.$route.query.planId,
                putType:19,
                cp:this.cur_page,
                ps:this.ps,
            }
        quotaPutlistByOidOrPid(param).then(res=>{
            if(res.data.code != 'A000000') {
                this.$message.error(data.code);
                }
                console.log(res)
            this.IdearList = res.data.result.data
            this.total = res.data.result.totalItemNum
            console.log(this.total)
        });
      },
          //   导出table方法
    exportExcel () {
            /* generate workbook object from table */

            var wb = XLSX.utils.table_to_book(document.querySelector('#table2'))

            var wb1 = XLSX.utils.table_to_book(document.querySelector('#table3'))

            /* get binary string as output */

            var wbout = XLSX.write(wb, { bookType: 'xlsx', bookSST: true, type: 'array' })
            var wbout1 = XLSX.write(wb1, { bookType: 'xlsx', bookSST: true, type: 'array' })


            try {

            FileSaver.saveAs(new Blob([wbout], { type: 'application/octet-stream' }), '订单投放列表.xlsx')

            FileSaver.saveAs(new Blob([wbout1], { type: 'application/octet-stream' }), '订单分日数据.xlsx')

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
    padding: 20px
}
</style>