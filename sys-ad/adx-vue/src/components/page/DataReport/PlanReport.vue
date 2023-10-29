
<template>
    <div class="box">
        <div class="block">
           <div class="header-select">
                 <template>
                      <el-tag size="medium">计划ID:</el-tag>
                      <el-input v-model="planId" placeholder="请输入id"  class="clI10"></el-input>
                      <el-tag size="medium">计划:</el-tag>
                       <el-input v-model="planName" placeholder="请输入名称"  class="clI10"></el-input>
                      <el-tag size="medium">日期:</el-tag>    
                           <el-date-picker
                             :picker-options="pickerOptions1"
                           class="inline-input"
                            v-model="startDay"
                            type="date"
                            placeholder="选择日期"
                            format="yyyy 年 MM 月 dd 日"
                            value-format="yyyyMMdd">
                        </el-date-picker> -
                        <el-date-picker
                          :picker-options="pickerOptions1"
                        class="inline-input"
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
                <el-table-column prop="planId" label="计划ID" align="center">
                </el-table-column>
                <el-table-column prop="planName" label="计划名称" align="center">
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
                <!-- <el-table-column prop="orderActive" label="激活数" align="center">
                </el-table-column> -->
                <!-- <el-table-column prop="cpa" label="CPA" align="center">
                </el-table-column> -->
                <!-- <el-table-column prop="cvr" label="CVR" align="center">
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
// import { orderslist,quotaOrderlistByInit, } from "@/api/Api.js";
import { quotaPlanlistByInit,quotaPlanlistByDay} from "@/api/Api.js";

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
    planId:'',
    planName:'',
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
        sTime:this.startDay,
        eTime:this.endDay,
        cp:this.cur_page,
        ps:this.ps
        }
        quotaPlanlistByInit(params).then(res=>{
            this.tableData = res.data.result.data
            this.total = res.data.result.totalItemNum;
        console.log(res)
        });
    },
     //  搜索
      search(){
          let  reg=/^\+?[1-9][0-9]*$/;
            if(this.planId != ''){
                if(!reg.test(this.planId)){
                this.$message('请输入正确的ID')
                return;
                }
            }
          let params = {
            id:this.planId,
            planName:this.planName,
            sTime:this.startDay,
            eTime:this.endDay,
            cp:this.cur_page,
            ps:this.ps
            }
            quotaPlanlistByInit(params).then(res=>{
            this.tableData = res.data.result.data
            this.total = res.data.result.totalItemNum;
            console.log(res)
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
        //    res.data.result.forEach(element => {
        //    NameID.set(element.orderName,element.id);
        // });
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
         this.$router.push({path:'/datareport/plandetail', query:{planId:item.planId}})
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
.inline-input{
  width: 165px;
}
.clI10{
    width: 130px;
}
</style>




























