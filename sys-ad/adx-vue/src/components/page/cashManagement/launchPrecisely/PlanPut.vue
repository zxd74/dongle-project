<template>
  <div class="table">
    <div class="container">
      <div class="handle-box">
        <!-- <el-autocomplete
          class="inline-input"
          v-model="select_word"
          value-key="planName"
          :fetch-suggestions="querySearchName"
          placeholder="请输入计划名称"
          @select="handleSelectName"
        ></el-autocomplete> -->
        <el-input v-model="select_word" placeholder="请输入计划名称" style="width:20%"></el-input>
        <el-autocomplete
          class="inline-input"
          v-model="select_cate"
          value-key="fullName"
          :fetch-suggestions="querySearchCustomer"
          placeholder="请输入客户名称"
          @select="handleSelectCustomer"
        ></el-autocomplete>
        <el-select v-model="value" placeholder="状态">
          <el-option
            v-for="item in options_"
            :key="item.value"
            :label="item.label"
            :value="item.value"
          ></el-option>
        </el-select>
        <el-button type="primary" icon="search" @click="search">搜索</el-button>
        <el-button type="primary" icon="search" @click="create" v-if="this.readonly !=1">新建计划</el-button>
      </div>
      <el-table :data="tableData" border style="width: 100%" ref="multipleTable">
        <el-table-column prop="id" label="序号" align="center"></el-table-column>
        <el-table-column prop="planName" label="计划名称" width="200" align="center"></el-table-column>
        <el-table-column prop="custName" label="客户" align="center"></el-table-column>
        <el-table-column  label="每日限额" align="center">
         <template slot-scope="scope">
            <span>{{scope.row.planLimit}}</span>
          </template>
        </el-table-column>
        <el-table-column
          prop="limitState"
          label="限额状态"
          align="center"
          :formatter="formattermediaType"
        ></el-table-column>
        <el-table-column label="运行状态" width align="center" v-if="this.readonly !=1">
          <template slot-scope="scope">
            <el-switch
              v-model="scope.row.runState"
              :active-value="1"
              :inactive-value="0"
              @change="changerunState($event,scope.row)"
            ></el-switch>
          </template>
        </el-table-column>
        <el-table-column
          prop="planState"
          label="计划状态"
          align="center"
          :formatter="formattermediaplan"
        ></el-table-column>
        <el-table-column label="操作" width="150" align="center">
          <template slot-scope="scope">
            <el-button size="small" type="success" @click="handleEdit(scope.$index, scope.row)">编辑</el-button>
            <el-button size="small" type="danger" @click="handleDelete(scope.$index, scope.row)" v-if="this.readonly !=1">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
      <div class="pagination">
         <el-pagination @current-change="handleCurrentChange" :current-page="cur_page"
         layout="total,prev, pager, next,jumper" :total="total" :page-size="ps" ref="pagination">
        </el-pagination>
      </div>
    </div>

    <!-- 编辑弹出框 -->
    <el-dialog title="编辑" :visible.sync="editVisible" width="40%">
      <el-form ref="form" :model="form" status-icon label-width="100px" class="demo-ruleForm">
        <el-form-item label="* 选择客户:" v-if="this.userTypes !=16" >
          <el-select v-model="form.custName" disabled>
            <el-option
              v-for="item in customerOpition"
              :key="item.id"
              :label="item.fullName"
              :value="item.id"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="* 计划名称:">
          <el-input v-model="form.planName" style="width:80%"></el-input>
        </el-form-item>
        <el-form-item label="* 计划限额:">
          <el-input v-model="form.planLimit" style="width:80%"></el-input>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="editVisible = false">取 消</el-button>
        <el-button type="primary" @click="saveEdit" v-if="this.readonly !=1">确 定</el-button>
      </span>
    </el-dialog>

    <!-- 新建弹出框 -->
    <el-dialog title="新建计划" :visible.sync="newVisible" width="40%">
      <el-form ref="form" :model="form" status-icon label-width="100px" class="demo-ruleForm">
        <el-form-item label="* 选择客户:"  v-if="this.userTypes !=16">
          <el-select v-model="form.custName">
            <el-option
              v-for="item in customerOpition"
              :key="item.id"
              :label="item.fullName"
              :value="item.id"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="* 计划名称:">
          <el-input v-model="form.planName" style="width:80%"></el-input>
        </el-form-item>
        <el-form-item label="* 计划限额:">
          <el-input v-model="form.planLimit" style="width:80%"></el-input>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="newVisible = false">取 消</el-button>
        <el-button type="primary" @click="saveNews">确 定</el-button>
      </span>
    </el-dialog>

    <!-- 删除提示框 -->
    <el-dialog title="提示" :visible.sync="delVisible" width="300px" center>
      <div class="del-dialog-cnt">删除不可恢复，是否确定删除？</div>
      <span slot="footer" class="dialog-footer">
        <el-button @click="delVisible = false">取 消</el-button>
        <el-button type="primary" @click="deleteRow">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import {
  planpage,
  CustomerList,
  planadd,
  planinfo,
  planupdate,
  plandelete
} from "@/api/Api.js";


const costrmNames = new Map();
export default {
  name: "basetable",
  data() {
    return {
      userTypes:'',
      tableData: [],
      cur_page: 1,
      total: 1,
      ps: 10,
      select_cate: "",
      select_word: "",
      runState: "",
      del_list: [],
      is_search: false,
      editVisible: false,
      delVisible: false,
      newVisible: false,
      options_: [
        {
          value: 1,
          label: "启用"
        },
        {
          value: 0,
          label: "停用"
        }
      ],
      customerOpition: [
        {
          value: "",
          label: ""
        }
      ],
      form: {
        id: "",
        custName: "",
        planName: "",
        planLimit: ""
      },
      value: "",
      readonly:''
    };
  },
  created() {
    this.getList();
    this.getIndustry();
    this.cookie();
      this.readonly = this.$store.state.map.get(this.$store.state.currentPath);
      console.log(this.readonly)
  },

  methods: {
     cookie(){
          this.userTypes = localStorage.getItem("type")
          console.log(this.userTypes)
          },
     // 开关
    changerunState(val, row) {
      let item = row;
      let params = {
        id: item.id,
        runState: item.runState
      };
      planupdate(params).then(res => {
        console.log(res);
        // this.getList();
      });
    },
    //模糊搜索
    // querySearchName(queryString, cb) {
    //   let params = {
    //     planName: this.select_word,
    //     cp: this.cur_page,
    //     ps: this.ps
    //   };
    //   planpage(params).then(res => {
    //     //let data = res.data;
    //     // console.log(data)
    //     // 调用 callback 返回建议列表的数据
    //     cb(res.data.result.data);
    //     if (res.data.code != "A000000") {
    //       this.$message.error(data.message);
    //     }
    //   });
    // },
    handleSelectName(item) {
      // console.log(item);
      this.id = item.id;
    },
    querySearchCustomer(queryString, cb) {
      let params = {
        fullName: this.select_cate,
        // cp: this.cur_page,
        // ps: this.ps,
        type: 2
      };
      CustomerList(params).then(res => {
      let data = res.data;
        // console.log(data)
        // 调用 callback 返回建议列表的数据
        cb(data.data.data);
        data.data.data.forEach(element => {
          costrmNames.set(element.fullName, element.id);
        });
        if (data.code != "A000000") {
          this.$message.error(data.message);
        }
      });
    },
    handleSelectCustomer(item) {
      console.log(costrmNames.get(this.select_cate));
      this.id = item.id;
    },
    // 分页导航
    handleCurrentChange(val) {
        console.log(val);
        this.cur_page = val;
        let params = {
        planName: this.select_word,
        adverId:costrmNames.get(this.select_cate),
        runState: this.value,
        cp: this.cur_page,
        ps: this.ps,
      };
      planpage(params).then(res => { 
        this.tableData = res.data.result.data;
        this.total = res.data.result.totalItemNum;
        this.$forceUpdate()
      });
   
    },
    // 格式化数据
    formattermediaType(row, column) {
      if (row.limitState == 1) {
        return "正常";
      } else if (row.limitState == 0) {
        return "无效";
      }
    },
    formattermediaplan(row, column) {
      if (row.planState == 1) {
        return "正常";
      } else if (row.planState == 0) {
        return "无效";
      }
      else if (row.planState == 5) {
        return "账户余额不足";
      }
    },
    // 获取 table
    getList() {
      let params = {
        cp: this.cur_page,
        ps: this.ps
      };
      planpage(params).then(res => {
        this.tableData = res.data.result.data;
        this.total = res.data.result.totalItemNum;
        // console.log(res)
      });
    },
    // 客户选项
    getIndustry() {
      let params = { type: 2, cp: this.cur_page, ps: this.ps };
      CustomerList(params).then(res => {
      let data = res.data;
        this.customerOpition = data.data.data;
        //  console.log(this.customerOpition);
      });
    },
    // 搜索
    search() {
      this.$refs.pagination.lastEmittedPage = 1
      this.cur_page = 1;
      let params = {
        planName: this.select_word,
        adverId:costrmNames.get(this.select_cate),
        runState: this.value,
        cp: this.cur_page,
        ps: this.ps,
      };
      planpage(params).then(res => { 
        this.tableData = res.data.result.data;
        this.total = res.data.result.totalItemNum;
        this.$forceUpdate()
      });
    },

    // 编辑
    handleEdit(index, row) {
      let item = row;
      let params = {
            id:item.id,
            }
       this.form = {
        id: item.id,
        custName: item.custName,
        planName: item.planName,
        planLimit: item.planLimit
      };
      planinfo(params).then(res=>{
        console.log(res)
          if(res.data.code != 'A000000') {
              this.$message.error(data.message);
          }
          // this.form.custName = res.data.result.id;
          this.form.planName = res.data.result.planName;
          this.form.planLimit = res.data.result.planLimit;
          this.ids = res.data.result.adverId
          console.log(this.form.planName)

      });
     
      this.editVisible = true;
    },
    // 保存编辑
    saveEdit() {
      if (!this.form.planName) {
        this.$message.error("计划名称不能为空");
        return;
      }
      if (!this.form.planLimit) {
        this.$message.error("计划限额不能为空");
        return;
      }
      let params = {
        id: this.form.id,
        adverId: this.ids,
        planName: this.form.planName,
        planLimit: this.form.planLimit
      };
      planupdate(params).then(res => {
        if (res.data.code != "A000000") {
          this.$message.error(data.message);
        }
        // 掉查询接口
        this.editVisible = false;
        this.getList();
      });
    },

    handleDelete(index, row) {
      this.delVisible = true;
      this.row = row;
    },
    // 确定删除
    deleteRow() {
      let params = { id: this.row.id };
      plandelete(params).then(res => {
        if (res.data.code != "A000000") {
          this.$message.error(data.message);
        }
        // 掉查询接口
        this.delVisible = false;
        this.getList();
      });
    },

    // 新建
    create() {
      this.newVisible = true;
      this.form = {};
    },
    // 确认
    saveNews() {
      // 参数判断
      if(this.userTypes != 16){
        if (!this.form.custName) {
        this.$message.error("客户不能为空");
        return;
        }
      }

      if (!this.form.planName) {
        this.$message.error("计划名称不能为空");
        return;
      }
      if (!this.form.planLimit) {
        this.$message.error("计划限额不能为空");
        return;
      }
      let params = {
        adverId: this.form.custName,
        planName: this.form.planName,
        planLimit: this.form.planLimit
      };
      planadd(params).then(res => {
        console.log(res);
        if (res.data.code != "A000000") {
          this.$message.error(res.data.message);
        }
        // 掉查询接口
        this.newVisible = false;
        this.getList();
      });
    }
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
.add_task {
  border: 2px dashed green;
  padding: 10px;
  margin: 10px;
}
.btn_right {
  margin-left: 88%;
}
.r_div {
  margin-left: 80px;
}
</style>




