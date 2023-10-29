
<template>
    <div class="box">
        <div class="block">
           <div class="header-select">
                 <template>
                      <el-tag size="medium">订单:</el-tag>
                      <el-autocomplete
                        class="inline-input"
                        v-model="select_cate"
                        value-key="name"
                        :fetch-suggestions="querySearchName"
                        placeholder="请输入订单名称"
                        @select="handleSelectName"
                      ></el-autocomplete>
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
        <el-table :data="tableData" border style="width: 100%" ref="multipleTable" id="table">
                <el-table-column prop="orderId" label="订单ID" align="center">
                </el-table-column>
                <el-table-column prop="orderName" label="订单名称" align="center">
                </el-table-column>
                <!-- <el-table-column prop="custName" label="客户名称" align="center">
                </el-table-column> -->
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
                <!-- <el-table-column prop="orderActive" label="激活数" align="center">
                </el-table-column>
                <el-table-column prop="cpa" label="CPA" align="center">
                </el-table-column>
                <el-table-column prop="cvr" label="CVR" align="center">
                </el-table-column> -->
                <el-table-column align="center">
                    <template slot-scope="scope">
                        <el-button size="small" type="success" @click="detail(scope.$index, scope.row)">详情</el-button>
                    </template>
                </el-table-column>
            </el-table>
            <div class="pagination">
                <el-pagination @current-change="handleCurrentChange" layout="total,prev, pager, next,jumper" :total="total">
                </el-pagination>
            </div>
    </div>
</template>

<script>
import { orderslist,quotaOrderlistByInit, } from "@/api/Api.js";

// 导出模块
import FileSaver from 'file-saver'
import XLSX from 'xlsx'

const NameID = new Map()
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
    tableData: [],
    startDay: new Date().Format("yyyyMMdd"),
    endDay: new Date().Format("yyyyMMdd"),
    cur_page: 1,
    total: 1,
    ps:10,
    radio:'1',
    select_cate:'',
    from:{
       
    },
  }),
  created() {
     this.getList();

  },
 
   methods: {
     getList() {
        // this.date = new Date();
        // var  m = (this.date.getMonth()+1)<10?"0"+(this.date.getMonth()+1):(this.date.getMonth()+1)
        // var  d = this.date.getDate()<10?"0"+this.date.getDate():this.date.getDate()
        // this.startDay=this.date.getFullYear()+""+m+""+d;
        // this.endDay=this.date.getFullYear()+""+m+""+d;
      let params = {
        sTime:this.startDay,
        eTime:this.endDay,
        cp:this.cur_page,
        ps:this.ps
        }
        quotaOrderlistByInit(params).then(res=>{
              console.log(res)
            this.tableData = res.data.result.data
            this.total = res.data.result.totalItemNum;
      
        });
    },
     //  搜索
      search(){
          let params = {
            id:NameID.get(this.select_cate),
            sTime:this.startDay,
            eTime:this.endDay,
            cp:this.cur_page,
            ps:this.ps
            }
            quotaOrderlistByInit(params).then(res=>{
            this.tableData = res.data.result.data
            this.total = res.data.result.totalItemNum;
            // console.log(res)
          });
      },
      //模糊搜索
      querySearchName(queryString, cb){
          let params = {
              name:this.select_cate,
            }
          orderslist(params).then(res=>{
            console.log(res)
        let data = res.data;
            // console.log(data)
            // 调用 callback 返回建议列表的数据
          cb(data.data);
          data.data.forEach(element => {
            NameID.set(element.name,element.id);
        });
        if(data.code != 'A000000') {
            this.$message.error(data.message);
              } 
          });
      },
      handleSelectName(item){
        // console.log(item);
        this.id = item.id
      },
        // 分页导航
      handleCurrentChange(val) {
        this.cur_page = val;
        this.getList();
      },
     
    //详细按钮跳转
      detail(index, row){
         this.idx = index;
         let item = row;
         this.$router.push({path:'/datareport/orderdetail', query:{orderId:item.orderId}})
      },
    //   导出table方法
      exportExcel () {
            /* generate workbook object from table */

            var wb = XLSX.utils.table_to_book(document.querySelector('#table'))

            /* get binary string as output */

            var wbout = XLSX.write(wb, { bookType: 'xlsx', bookSST: true, type: 'array' })

            try {

            FileSaver.saveAs(new Blob([wbout], { type: 'application/octet-stream' }), 'sheetjs.xlsx')

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
</style>




























