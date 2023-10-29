<template>
    <div class="table">
        <div class="container">
            <div class="handle-box">
                <el-input v-model="select_word" placeholder="请输入账户名称" class="handle-input mr10"></el-input>
                <el-button type="primary" icon="search" @click="search">搜索</el-button>
                 <el-button type="primary" icon="search" @click="create">新建订单</el-button>
            </div>
            <div class="header-select">
                 <template>
                        <el-select v-model="options1" placeholder="请选择">
                        <el-option
                            v-for="item in options"
                            :key="item.value"
                            :label="item.label"
                            :value="item.value">
                        </el-option>
                        </el-select>
                        <el-select v-model="options2" placeholder="请选择">
                        <el-option
                            v-for="item in options"
                            :key="item.value"
                            :label="item.label"
                            :value="item.value">
                        </el-option>
                        </el-select>
                        <el-select v-model="options3" placeholder="请选择">
                        <el-option
                            v-for="item in options"
                            :key="item.value"
                            :label="item.label"
                            :value="item.value">
                        </el-option>
                        </el-select>
                        <el-select v-model="options4" placeholder="请选择">
                        <el-option
                            v-for="item in options"
                            :key="item.value"
                            :label="item.label"
                            :value="item.value">
                        </el-option>
                        </el-select>
              </template>
            </div>
            <el-table :data="data" border style="width: 100%" ref="multipleTable">
                <el-table-column prop="name" label="序号" align="center">
                </el-table-column>
                <el-table-column prop="address" label="订单名称"  align="center">
                </el-table-column>
                <el-table-column prop="date" label="投放时间"  width="200"  align="center">
                </el-table-column>
                <el-table-column prop="date" label="广告客户"  align="center">
                </el-table-column>
                <el-table-column prop="date" label="销售人员"  align="center">
                </el-table-column>
                <el-table-column prop="date" label="投放人员"  align="center">
                </el-table-column>
                <el-table-column prop="date" label="投放状态"  align="center">
                </el-table-column>
                <el-table-column label="操作"  width="150"  align="center">
                    <template slot-scope="scope">
                        <el-button size="small" type="success" @click="handleEdit(scope.$index, scope.row)">编辑</el-button>
                        <el-button size="small" type="danger" @click="handleDelete(scope.$index, scope.row)">删除</el-button>
                    </template>
                </el-table-column>
            </el-table>
            <div class="pagination">
                <el-pagination @current-change="handleCurrentChange" layout="prev, pager, next" :total="1000">
                </el-pagination>
            </div>
        </div>

        <!-- 编辑弹出框 -->
        <el-dialog title="编辑" :visible.sync="editVisible" width="40%">
            <el-form ref="form" :model="form"   status-icon label-width="100px">
                <el-form-item label="订单名称:">
                  <el-input v-model="form.user_name" placeholder="请输入订单名称" style="width:80%"></el-input>
                </el-form-item>
                <el-form-item label="客户名称:">
                   <el-select v-model="options1" placeholder="请选择">
                        <el-option
                            v-for="item in options"
                            :key="item.value"
                            :label="item.label"
                            :value="item.value">
                        </el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="投放时间:">
                   <el-date-picker
                        v-model="add_time"
                        type="datetimerange"
                        range-separator="至"
                        start-placeholder="开始日期"
                        end-placeholder="结束日期">
                    </el-date-picker>
                </el-form-item>
                <el-form-item label="销售人员:">
                   <el-input v-model="form.name" placeholder="请输入销售人员" style="width:80%"></el-input>
                </el-form-item>
                <el-form-item label="投放人员:">
                    <el-input v-model="form.name" placeholder="请输入投放人员" style="width:80%"></el-input>
                </el-form-item>
                <el-form-item label="订单金额:">
                    <el-input v-model="form.name" placeholder="请输入订单金额" style="width:80%"></el-input>
                </el-form-item>
                <el-form-item label="备注:">
                    <el-input v-model="form.name" style="width:80%"></el-input>
                </el-form-item>
            </el-form> 
            <span slot="footer" class="dialog-footer">
                <el-button @click="editVisible = false">取 消</el-button>
                <el-button type="primary" @click="saveEdit">确 定</el-button>
            </span>
        </el-dialog>

          <!-- 新建弹出框 -->
        <el-dialog title="新建订单" :visible.sync="newVisible" width="45%">
            <el-form ref="form" :model="form"  status-icon label-width="100px">
                <el-form-item label="订单名称:">
                  <el-input v-model="form.user_name" placeholder="请输入订单名称" style="width:80%"></el-input>
                </el-form-item>
                <el-form-item label="客户名称:">
                   <el-select v-model="options1" placeholder="请选择">
                        <el-option
                            v-for="item in options"
                            :key="item.value"
                            :label="item.label"
                            :value="item.value">
                        </el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="投放时间:">
                   <el-date-picker
                        v-model="add_time"
                        type="datetimerange"
                        range-separator="至"
                        start-placeholder="开始日期"
                        end-placeholder="结束日期">
                    </el-date-picker>
                </el-form-item>
                <el-form-item label="销售人员:">
                   <el-input v-model="form.name" placeholder="请输入销售人员" style="width:80%"></el-input>
                </el-form-item>
                <el-form-item label="投放人员:">
                    <el-input v-model="form.name" placeholder="请输入投放人员" style="width:80%"></el-input>
                </el-form-item>
                <el-form-item label="订单金额:">
                    <el-input v-model="form.name" placeholder="请输入订单金额" style="width:80%"></el-input>
                </el-form-item>
                <el-form-item label="备注:">
                    <el-input v-model="form.name" style="width:80%"></el-input>
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
export default {
  name: "basetable",
  data() {
    return {
      url: "./static/vuetable.json",
      tableData: [],
      cur_page: 1,
      select_cate: "",
      select_word: "",
      del_list: [],
      is_search: false,
      editVisible: false,
      delVisible: false,
      newVisible: false,
      newaddAD: false,
      template_dialog: false,
      currentRow: null,
      add_time:"",
      form: {
        name:'',
        data:'',
        address:''
      },

      idx: -1,
      type: 1,
      types: null,
      flow_management: null,
      data_report:null,
      options1:'1',
      options2:'2',
      options3:'3',
      options4:'4',
  
      options: [
        {
          value: "1",
          label: "流量管理员"
        },
        {
          value: "2",
          label: "广告客户"
        },
          {
          value: "3",
          label: "直客客户"
        },
          {
          value: "4",
          label: "代理商"
        },
      ],
      value: "1",
    
    };
  },
  created() {
    this.getData();
  },
  computed: {
    data() {
      return this.tableData.filter(d => {
        let is_del = false;
        for (let i = 0; i < this.del_list.length; i++) {
          if (d.name === this.del_list[i].name) {
            is_del = true;
            break;
          }
        }
        if (!is_del) {
          // if (
          //   d.address.indexOf(this.select_cate) > -1 &&
          //   (d.name.indexOf(this.select_word) > -1 ||
          //     d.address.indexOf(this.select_word) > -1)
          // ) {
            return d;
          // }
        }
      });
    }
  },
  methods: {
    // 分页导航
    handleCurrentChange(val) {
      this.cur_page = val;
      this.getData();
    },
    // 获取 easy-mock 的模拟数据
    getData() {
      // 开发环境使用 easy-mock 数据，正式环境使用 json 文件
      if (process.env.NODE_ENV === "development") {
        this.url = "/ms/table/list";
      }

      this.$axios
        .post(this.url, {
          page: this.cur_page
        })
        .then(res => {
          this.tableData = res.data.list;
        });
    },
    search() {
      this.is_search = true;
    },
    formatter(row, column) {
      return row.address;
    },
    filterTag(value, row) {
      return row.tag === value;
    },
    handleEdit(index, row) {
      this.idx = index;
      const item = this.tableData[index];
      this.form = {
        name: item.name,
        date: item.date,
        address: item.address
      };
      this.editVisible = true;
    },
    handleDelete(index, row) {
      this.idx = index;
      this.delVisible = true;
    },
   
    delAll() {
      const length = this.multipleSelection.length;
      let str = "";
      this.del_list = this.del_list.concat(this.multipleSelection);
      for (let i = 0; i < length; i++) {
        str += this.multipleSelection[i].name + " ";
      }
      this.$message.error("删除了" + str);
      this.multipleSelection = [];
    },
    handleSelectionChange(val) {
      this.multipleSelection = val;
    },
    // 保存编辑
    saveEdit() {
      if(!this.form.name || !this.form.data || !this.form.address ){
          this.$message.error('订单名称,客户名称,投放时间,销售人员,不能为空') 
      }
      this.$set(this.tableData, this.idx, this.form);
      this.editVisible = false;
      this.$message.success(`修改第 ${this.idx + 1} 行成功`);
    },
    // 新建
    create() {
      this.newVisible = true;
      this.form = {};
    },
    // 新建确定
    saveNews() {
       if(!this.form.name || !this.form.data || !this.form.address ){
          this.$message.error('订单名称,客户名称,投放时间,销售人员,不能为空') 
      } else {
        this.tableData.push(this.form);
        this.newVisible = false;
      }
    },
    saveAD() {
      this.newaddAD = false;
    },
    // 确定删除
    deleteRow() {
      this.tableData.splice(this.idx, 1);
      this.$message.success("删除成功");
      this.delVisible = false;
    },
    handleCurrentChange(val) {
        this.currentRow = val;
    },
    template_(){

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
    margin-bottom: 20px
}
</style>




