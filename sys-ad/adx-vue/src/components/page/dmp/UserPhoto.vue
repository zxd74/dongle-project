<template>
    <div class="table">
        <div class="container">
            <div class="handle-box">
                <el-input v-model="select_word" placeholder="请输人群名称进行搜索" class="handle-input mr10"></el-input>
                <el-button type="primary" icon="search" @click="search">搜索</el-button>
                <el-button type="primary" icon="search" @click="create">新建</el-button>
            </div>
            <el-table :data="tableData" border style="width: 100%" ref="multipleTable">
                <el-table-column prop="date" label="画像名称" align="center">
                </el-table-column>
                <el-table-column prop="date" label="人群名称" align="center">
                </el-table-column>
                <el-table-column prop="date" label="说明" align="center">
                </el-table-column>
                <el-table-column prop="date" label="更新时间" align="center">
                </el-table-column>
                <el-table-column label="操作"  align="center">
                    <template slot-scope="scope">
                        <el-button size="small" type="success" @click="handleEdit(scope.$index, scope.row)">详细</el-button>
                        <el-button size="small" type="danger" @click="handleDelete(scope.$index, scope.row)">删除</el-button>
                    </template>
                </el-table-column>
            </el-table>
            <div class="pagination">
                <el-pagination @current-change="handleCurrentChange" layout="total,prev, pager, next,jumper" :total="total">
                </el-pagination>
            </div>
        </div>


          <!-- 新建弹出框 -->
        <el-dialog :visible.sync="newVisible" width="45%" title="选择添加方式">
             <el-form ref="form" :model="form"  status-icon label-width="100px">
                <el-form-item label="* 选择人群:">
                      <el-select v-model="options1" placeholder="请选择">
                           <el-option
                            v-for="item in options"
                            :key="item.value"
                            :label="item.label"
                            :value="item.value">
                           </el-option>
                      </el-select>
                </el-form-item>
            </el-form> 
            <span slot="footer" class="dialog-footer">
                <el-button @click="newVisible = false">取 消</el-button>
                <el-button type="primary" @click="saveNews">生成画像</el-button>
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
import {personslist} from '@/api/Api.js';

export default {
  name: "basetable",
  data() {
    return {
      url: "./static/vuetable.json",
      tableData: [],
      cur_page: 1,
      total: 1,
      ps: 10,
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
        name: "",
        date: "",
        switch: false,
        add_time:"",
        data_name:'',
        data_supplier:'1',
        data_describe:'',
        data_Url:[{url:''}],
        add_user:'',  
   
      },
      options1:'',
      options:[],
      idx: -1,
      type: 1,
      types: null,
      flow_management: null,
      data_report:null,
   
      value: "1",
    
    };
  },
  created() {
    this.getData();
  },
  methods: {
    // 分页导航
    handleCurrentChange(val) {
      this.cur_page = val;
      this.getData();
    },
    //初始列表
    getData() {
        let params = {
            cp: this.cur_page,
            ps: this.ps
        };
        personslist(params).then(res => {
          let data = res.data;
            if (data.code != 'A000000') {
                this.$message.error(data.message);
            }
            console.log(data)
            this.tableData = data.data.data;
            this.total = data.data.totalItemNum;
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
      this.$router.push({path: '/dmp/pnhto'})
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
      this.$set(this.tableData, this.idx, this.form);
      this.editVisible = false;
      this.$message.success(`修改第 ${this.idx + 1} 行成功`);
    },
    // 新建
    create() {
      this.newVisible = true;
      this.form = {};
    },
    saveNews() {
      this.tableData.push(this.form);
      this.newVisible = false;
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

    },
     data_address() {
      this.form.data_Url.push({ url: "" });
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
.del-dialog-cnt {
  font-size: 16px;
  text-align: center;
}
.header-select {
    margin-bottom: 20px
}
.mr11{
    width: 50px;
}
</style>




