
<template>
    <div class="box">
        <el-table :data="tableData" border style="width: 100%" ref="multipleTable" id="table">
               <el-table-column prop="date" label="日期">
                </el-table-column>
                <!-- <el-table-column prop="itemName" label="平台ID">
                </el-table-column> -->
                <el-table-column prop="req" label="请求量">
                </el-table-column>
                <el-table-column prop="reqUV" label="请求UV" >
                </el-table-column> 
                <el-table-column prop="exp" label="曝光量">
                </el-table-column>
                <el-table-column prop="expUV" label="expUV">
                </el-table-column>
                <el-table-column prop="fr" label="填充率%">
                </el-table-column>
                <el-table-column prop="clk" label="点击量">
                </el-table-column>
                <el-table-column prop="ctr" label="点击率%">
                </el-table-column>
                 <el-table-column prop="expPer" label="人均曝光">
                </el-table-column>
                 <el-table-column prop="expIncome" label="曝光收入">
                </el-table-column>
                 <el-table-column prop="clkIncome" label="点击收入">
                </el-table-column>
                <el-table-column prop="income" label="总收入">
                </el-table-column>
                 <el-table-column prop="cpm" label="cpm">
                </el-table-column>
                 <el-table-column prop="cpc" label="cpc">
                </el-table-column>
                <el-table-column prop="ecpm" label="ecpm">
                </el-table-column>
                <el-table-column prop="clkDesire" label="点击意愿">
                </el-table-column>
            </el-table>
    </div>
</template>

<script>
import { orderslist,quotaOrderlistByInit, } from "@/api/Api.js";

import { sdkReportdetail } from "@/api/Api.js";

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
      let params = {
                id:this.$route.query.itemId,
                flowConId:this.$route.query.flowCons,
                appsId:this.$route.query.appsId,
                pid:this.$route.query.pid,
                flowPosId:this.$route.query.flowPosId,
                sDate:this.$route.query.sDate,
                eDate:this.$route.query.eDate,
        }
        sdkReportdetail(params).then(res=>{
              console.log(res)
              let data = res.data
              this.tableData = data.data
            // this.total = res.data.result.totalItemNum;
      
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
        //let data = res.data;
            // console.log(data)
            // 调用 callback 返回建议列表的数据
          cb(res.data.result);
           res.data.result.forEach(element => {
            NameID.set(element.name,element.id);
        });
        if(res.data.code != 'A000000') {
            this.$message.error(res.data.code.message);
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




























