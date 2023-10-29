<template>
  <div class="table">
    <div class="container">
      <div class="handle-box">
        <el-input v-model="select_name" placeholder="请输入订单名称" class="handle-input"></el-input>
        <el-autocomplete
          class="inline-input"
          v-model="select_word"
          value-key="fullName"
          :fetch-suggestions="querySearchCustomer"
          placeholder="请输入客户名称"
          @select="handleSelectCustomer"
        ></el-autocomplete>
        <el-select v-model="value" placeholder="状态" class="handle-input mr10">
          <el-option
            v-for="item in options_"
            :key="item.value"
            :label="item.label"
            :value="item.value"
          ></el-option>
        </el-select>
        <el-button type="primary" icon="search" @click="search">搜索</el-button>
        <el-button type="primary" icon="search" @click="create" v-if="this.readonly !=1">新建订单</el-button>
      </div>
      <el-table :data="tableData" border style="width: 100%" ref="multipleTable">
        <el-table-column prop="id" label="序号" align="center"></el-table-column>
        <el-table-column prop="name" label="订单名称" width="160" align="center"></el-table-column>
        <el-table-column prop="createTime" label="创建日期" align="center"></el-table-column>
        <el-table-column prop="custName" label="广告客户" align="center"></el-table-column>
        <el-table-column prop="saleName" label="销售人员" align="center"></el-table-column>
        <el-table-column prop="putName" label="投放人员" align="center"></el-table-column>
        <!-- <el-table-column prop="startTime" label="开始日期" align="center"></el-table-column> -->
        <!-- <el-table-column prop="endTime" label="结束日期" align="center"></el-table-column> -->
        <!-- <el-table-column
          prop="ordersState"
          label="投放状态"
          align="center"
          :formatter="formatterTypeputStatus"
        ></el-table-column> -->
        <el-table-column prop label="运行状态" align="center">
          <template slot-scope="scope">
            <el-switch
              v-model="scope.row.runState"
              :active-value="1"
              :inactive-value="0"
              @change="changeStatus($event,scope.row)"
            ></el-switch>
          </template>
        </el-table-column>
         <el-table-column label="操作" width="220" align="center">
            <template slot-scope="scope">
              <el-button size="mini" type="success" @click="handleEdit(scope.$index, scope.row)">编辑</el-button>
              <el-button size="mini" type="danger" @click="handleDelete(scope.$index, scope.row)">删除</el-button>
              <el-button size="mini" type="success" @click="copy(scope.$index, scope.row)">复制</el-button>
            </template>
        </el-table-column>
      </el-table>
      <div class="pagination">
        <el-pagination
          @current-change="handleCurrentChange"
          layout="total,prev, pager, next,jumper"
          :total="total"
          :current-page="cur_page"
          :page-size="ps"
          ref="pagination"
        ></el-pagination>
      </div>
    </div>
      <!-- 新建 -->
    <el-dialog title="新建订单" :visible.sync="newVisible" width="50%">
             <el-form ref="form" :model="form"   status-icon label-width="90px"  :label-position="labelPosition">
                 <el-form-item label="* 订单名称:">
                      <el-input v-model="form.name" style="width:80%" placeholder="请输入订单名称"></el-input>
                 </el-form-item>
                 <el-form-item label="* 客户名称:">
                      <el-autocomplete
                        class="inline-input"
                        v-model="select_words2"
                        value-key="fullName"
                        :fetch-suggestions="querySearchCustomer2"
                        placeholder="请输入客户名称"
                        @select="handleSelectCustomer2"
                      ></el-autocomplete>
                 </el-form-item>
                 <el-form-item label=" 销售人员:">
                      <el-input v-model="form.saleName" style="width:80%" placeholder="请输入销售人员"></el-input>
                 </el-form-item>
                 <el-form-item label=" 投放人员:">
                      <el-input v-model="form.putName" style="width:80%" placeholder="请输入投放人员"></el-input>
                 </el-form-item>
                 <el-form-item label=" 订单金额:">
                      <el-input v-model="form.money" style="width:80%" placeholder="请输入订单金额"></el-input>
                 </el-form-item>
                 <el-form-item label=" 备注:">
                      <el-input v-model="form.remark" style="width:80%" placeholder=""></el-input>
                 </el-form-item>
             </el-form> 
            <span slot="footer" class="dialog-footer">
                    <el-button @click="newVisible = false">取 消</el-button>
                    <el-button type="primary" @click="saveNews"  v-if="this.readonly !=1">确 定</el-button>
              </span>
        </el-dialog>

              <!-- 编辑 -->
    <el-dialog title="编辑" :visible.sync="editVisible" width="50%">
              <el-form ref="form" :model="form"   status-icon label-width="90px"  :label-position="labelPosition">
                 <el-form-item label="* 订单名称:">
                      <el-input v-model="form.name" style="width:80%" placeholder="请输入订单名称"></el-input>
                 </el-form-item>
                 <el-form-item label="* 客户名称:">
                      <el-autocomplete
                        class="inline-input"
                        v-model="select_words2"
                        value-key="fullName"
                        :fetch-suggestions="querySearchCustomer"
                        placeholder="请输入客户名称"
                        @select="handleSelectCustomer"
                        disabled
                      ></el-autocomplete>
                 </el-form-item>
                 <el-form-item label=" 销售人员:">
                      <el-input v-model="form.saleName" style="width:80%" placeholder="请输入销售人员"></el-input>
                 </el-form-item>
                 <el-form-item label=" 投放人员:">
                      <el-input v-model="form.putName" style="width:80%" placeholder="请输入投放人员"></el-input>
                 </el-form-item>
                 <el-form-item label=" 订单金额:">
                      <el-input v-model="form.money" style="width:80%" placeholder="请输入订单金额"></el-input>
                 </el-form-item>
                 <el-form-item label=" 备注:">
                      <el-input v-model="form.remark" style="width:80%" placeholder=""></el-input>
                 </el-form-item>
             </el-form> 
            <span slot="footer" class="dialog-footer">
                    <el-button @click="editVisible = false">取 消</el-button>
                    <el-button type="primary" @click="saveEdit"  v-if="this.readonly !=1">确 定</el-button>
              </span>
        </el-dialog>
        <!-- 删除提示框 -->
    <el-dialog title="提示" :visible.sync="delVisible" width="300px" center>
      <div class="del-dialog-cnt">删除不可恢复，是否确定删除？</div>
      <span slot="footer" class="dialog-footer">
        <el-button @click="delVisible = false">取 消</el-button>
        <el-button type="primary" @click="deleteRow" v-if="this.readonly !=1">确 定</el-button>
      </span>
    </el-dialog> 
  </div>
</template>

<script>
import {
  orderspage,
  orderslist,
  CustomerList,
  ordersupdate,
  dicmapList,
  planlist,
  orderPutinfo
} from "@/api/Api.js";

import {
  ordersadd,
  ordersinfo,
  ordersdelete,
} from "@/api/Api.js";

// import picker from "../../../../components/common/picker/index.vue";
// import diqu from "../../../../components/common/diqu.vue";

const uuid = new Map();
const nameID = new Map();
const names = new Map();
export default {
  name: "basetable",
  data() {
    return {
      labelPosition: 'left',
      options_plan: [
        {
          value: "",
          label: ""
        }
      ],
      pickerOptions1: {
        disabledDate(time) {
          return time.getTime() + 3600 * 1000 * 24 <= Date.now();
        }
      },
      select_words2:'',
      tableData: [],
      cur_page: 1,
      total: 1,
      ps: 10,
      select_name: "",
      select_word: "",
      select_words:'',
      del_list: [],
      is_search: false,
      editVisible: false,
      delVisible: false,
      newVisible: false,
      newaddAD: false,
      template_dialog: false,
      currentRow: null,
      idx: -1,

      radio: "",
      form: {
        id:'',
        name: "",
        saleName: "",
        putName: "",
        money: "",
        remark: "",

      },
      options_: [
         {
          value: '',
          label: "全部状态"
        },
        {
          value: 1,
          label: "开启"
        },
        {
          value: 0,
          label: "暂停"
        }
      ],
      value: "",
      readonly: '',
     
    };
  },
  created() {
    this.getList();
    this.querySearchCustomer();
    // this.getPlan();
    this.readonly = this.$store.state.map.get(this.$store.state.currentPath);
  },

  methods: {
    // 初始化订单
    getPlan() {
      let params = {
        planName: this.form.select_plan
      };
      planlist(params).then(res => {
        this.options_plan = res.data.result;
        res.data.result.forEach(element => {
          names.set(element.id, element.planName);
        });
      });
    },
   
    // 编辑
    handleEdit(index, row) {
        this.querySearchCustomer();
        let item = row;
          let params = {
          id: item.id
        };
        ordersinfo(params).then(res => {
          console.log(res)
          let data = res.data; 
             if(data.code != 'A000000') {
              this.$message.error(data.message);
            }
           this.form = data.data
           this.select_words2 = nameID.get(data.data.custId)
           console.log(this.select_words2)
           this.editVisible = true;
        });
    },
    // 复制
    copy(index, row){
          let item = row;
          let params = {
          id: item.id
        };
        ordersinfo(params).then(res => {
          console.log(res)
          let data = res.data; 
             if(data.code != 'A000000') {
              this.$message.error(data.message);
            }
           this.form = data.data
           this.select_words2 = nameID.get(data.data.custId)
           this.newVisible = true;
        });
    },
    saveEdit(){
        if(!this.form.name) {
          this.$message.error("订单名称不能为空");
          return;
        }
        let params = {
          id:this.form.id,
          name: this.form.name,
          custId: uuid.get(this.select_words2),
          saleName: this.form.saleName,
          putName: this.form.putName,
          money: this.form.money,
          remark: this.form.remark,
        };
      ordersupdate(params).then(res => {
        console.log(res);
        // if (res.data.code != "A000000") {
        //   this.$message.error(res.data.message);
        //   return;
        // }
         this.editVisible = false;
         this.search();
      });
    },
    // 开关
    changeStatus(val, row) {
      let item = row;
      let params = {
        id: item.id,
        runState: item.runState
      };
      ordersupdate(params).then(res => {
        console.log(res);
        // this.getList();
      });
    },

    formatterTypeputStatus(row, column) {
      if (row.ordersState == 1) {
        return "正常";
      } else if (row.ordersState == 0) {
        return "无效";
      } else if (row.ordersState == 3) {
        return "待修改";
      }
    },
    // 模糊客户名称
    querySearchCustomer(queryString, cb) {
      let params = {
        fullName: this.select_word,
        cp: 1,
        ps: this.ps,
        type: 2
      };
      CustomerList(params).then(res => {
        let data = res.data;
        // console.log(data)
        // 调用 callback 返回建议列表的数据
        // console.log(res);
  
        data.data.data.forEach(element => {
          uuid.set(element.fullName, element.id);
          nameID.set(element.id,element.fullName,)
        });
        if (data.code != "A000000") {
          this.$message.error(data.message);
        }
        cb(data.data.data);
      });
    },
    handleSelectCustomer(item) {
      // console.log(item);
      this.id = item.id;
    },
        // 模糊客户名称
    querySearchCustomer2(queryString, cb) {
      let params = {
        fullName: this.select_words2,
        cp: 1,
        ps: this.ps,
        type: 2
      };
      CustomerList(params).then(res => {
        let data = res.data;
        // console.log(data)
        // 调用 callback 返回建议列表的数据
        // console.log(res);
  
        data.data.data.forEach(element => {
          uuid.set(element.fullName, element.id);
          nameID.set(element.id,element.fullName,)
        });
        if (data.code != "A000000") {
          this.$message.error(data.message);
        }
        cb(data.data.data);
      });
    },
     handleSelectCustomer2(item) {
      // console.log(item);
      this.id = item.id;
    },
    // 分页导航
    handleCurrentChange(val) {
      this.cur_page = val;
      let params = {
        name: this.select_name,
        custId: uuid.get(this.select_word),
        runState: this.value,
        cp: this.cur_page,
        ps: this.ps
      };
      orderspage(params).then(res => {
        let data = res.data; 
             if(data.code != 'A000000') {
              this.$message.error(data.message);
          }  
        this.tableData = data.data.data;
        this.total = data.data.totalItemNum;
      });
    },
    getList() {
      let params = { cp: this.cur_page, ps: this.ps };
      orderspage(params).then(res => {
        console.log(res)
         let data = res.data; 
             if(data.code != 'A000000') {
              this.$message.error(data.message);
          }  
        this.tableData = data.data.data;
        this.total = data.data.totalItemNum;

      });
    },
    search() {
      this.$refs.pagination.lastEmittedPage = 1;
      this.cur_page = 1;
      let params = {
        name: this.select_name,
        custId: uuid.get(this.select_word),
        runState: this.value,
        cp: this.cur_page,
        ps: this.ps
      };
      orderspage(params).then(res => {
        let data = res.data; 
             if(data.code != 'A000000') {
              this.$message.error(data.message);
          } 
        this.tableData = data.data.data;
        this.total = data.data.totalItemNum;

      });
    },

    // 删除
     handleDelete(index, row) {
      this.idx = index;
      this.row = row;
      this.delVisible = true;
    },
       // 确定删除
    deleteRow() {
      let params = {
        id: this.row.id
      };
      ordersdelete(params).then(res => {
        console.log(res);
        //let data = res;
        if (res.data.code != "A000000") {
          this.$message.error(res.data.message);
        } else {
          this.$message.success("删除成功");
          this.delVisible = false;
          this.getList();
        }
      });
    },
    create(){
      this.newVisible = true
      this.select_words =''
      this.select_words2 =''
      this.form ={}
    },
    saveNews(){
        if(!this.form.name) {
          this.$message.error("订单名称不能为空");
          return;
        }

      let params = {
        name: this.form.name,
        custId: uuid.get(this.select_words2),
        saleName: this.form.saleName,
        putName: this.form.putName,
        money: this.form.money,
        remark: this.form.remark,
      };
        ordersadd(params).then(res => {
           console.log(res);
        if (res.data.code != "A000000") {
          this.$message.error(res.data.message);
          return;
        }
        // 掉查询接口
        this.newVisible = false;
        this.getList();
      });
    },
  
  }
};
</script>

<style scoped>
.handle-box {
  margin-bottom: 20px;
}

.handle-select {
  width: 120px;
}

.handle-input {
  width: 300px;
  display: inline-block;
}
.inline-input{
  width: 250px;
}
.del-dialog-cnt {
  font-size: 16px;
  text-align: center;
}
.header-select {
  margin-bottom: 20px;
}
.btn_plan {
  text-align: center;
  margin: 0 auto;
  width: 100px;
  height: 50px;
  background-color: #249cd3;
  border: 1px silver solid;
  color: aliceblue;
  line-height: 50px;
}
.btn_plan_2 {
  text-align: center;
  margin: 0 auto;
  width: 100px;
  height: 50px;
  background-color: #ffffff;
  border: 1px silver solid;
  color: black;
  line-height: 50px;
  margin-top: 20px;
}
.span {
  color: red;
}
.el-checkbox + .el-checkbox {
  margin-left: 0px;
}
.r_div {
  margin-left: 80px;
}
.border {
  border: 2px dashed #26a580;
  padding: 10px;
}
.div {
  margin-top: 10px;
}
.td_color {
  margin-left: 47%;
}
.diamonds {
  display: inline-block;
  width: 20px;
  height: 20px;
  border: 1px solid silver;
}
.bg_color {
  background: silver;
}
.mr10 {
  width: 180px;
}
.handle-box-footer{
  margin-top: 10px;
}
</style>




