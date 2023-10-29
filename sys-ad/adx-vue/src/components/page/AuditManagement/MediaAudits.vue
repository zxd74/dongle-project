<template>
    <div class="table">
        <div class="container">
            <div class="header-select">
                 <template>
                        <el-input v-model="select_word" placeholder="ID" class="handle-input mr10"></el-input>
                        <el-input v-model="select_word" placeholder="客户名称" class="handle-input mr10"></el-input>
                        <el-input v-model="select_word" placeholder="代理商名称" class="handle-input mr10"></el-input>
                        <el-select v-model="options4" placeholder="状态">
                        <el-option
                            v-for="item in options"
                            :key="item.value"
                            :label="item.label"
                            :value="item.value">
                        </el-option>
                        </el-select>
                        <el-button type="primary" icon="search" @click="search">搜索</el-button>
              </template>
            </div>
            <el-table :data="data" border style="width: 100%" ref="multipleTable">
                <el-table-column prop="date" label="ID">
                </el-table-column>
                <el-table-column prop="date" label="客户名称">
                </el-table-column>
                <el-table-column prop="date" label="行业" >
                </el-table-column>
                <el-table-column prop="date" label="代理商">
                </el-table-column>
                <el-table-column prop="date" label="组织机构代码">
                </el-table-column>
                <el-table-column prop="date" label="状态">
                </el-table-column>
                <el-table-column  width="150">
                    <template slot-scope="scope">
                        <el-button size="small" type="success" @click="handleEdit(scope.$index, scope.row)">媒体审核</el-button>
                        <!-- <el-button size="small" type="danger" @click="handleDelete(scope.$index, scope.row)">删除</el-button> -->
                    </template>
                </el-table-column>
            </el-table>
            <div class="pagination">
               <el-pagination @current-change="handleCurrentChange" layout="total,prev, pager, next,jumper" :total="total">
               </el-pagination>
            </div>
        </div>

        <!-- 编辑弹出框 -->
        <el-dialog title="媒体审核" :visible.sync="editVisible" width="60%">
            <el-form ref="form" :model="form"   status-icon label-width="100px">
                <el-form-item label="客户ID:">
                    {{111}}
                </el-form-item>
                <el-form-item label="客户名称:">
                    {{111}}
                </el-form-item>
                <el-form-item label="行业类型:">
                    {{111}}
                </el-form-item>
                <el-form-item label="营业执照号:">
                    {{111}}
                </el-form-item>
                <el-form-item label="股东姓名:">
                    {{111}}
                </el-form-item>
                <el-form-item label="联系人:">
                    {{111}}
                </el-form-item>
                <el-form-item label="联系电话:">
                    {{111}}
                </el-form-item>
                <el-form-item label="联系地址:">
                    {{111}}
                </el-form-item>
                <el-form-item label="营业执照:">
                    <div class="bg_img">
                        <img src="" alt="">
                    </div>
                </el-form-item>
                <el-form-item label="其他资质:">
                    <el-upload
                        class="upload-demo"
                        action="https://jsonplaceholder.typicode.com/posts/"
                        :on-preview="handlePreview"
                        :on-remove="handleRemove"
                        :file-list="fileList2"
                        list-type="picture">
                        <el-button size="small" type="primary">点击上传</el-button>
                        <div slot="tip" class="el-upload__tip">只能上传jpg/png文件，且不超过500kb</div>
                    </el-upload>
                </el-form-item>
                <!-- <div class="splice"></div> -->
                <el-table :data="data" border style="width: 100%" ref="multipleTable">
                    <el-table-column prop="name" label="媒体ID">
                    </el-table-column>
                    <el-table-column prop="name" label="媒体名称">
                    </el-table-column>
                    <el-table-column prop="name" label="行业类型">
                    </el-table-column>
                    <!-- <el-table-column  width="150">
                        <template slot-scope="scope">
                            <el-button size="small" type="success" @click="handleEdit(scope.$index, scope.row)">提交审核</el-button>
                             <el-button size="small" type="danger" @click="handleDelete(scope.$index, scope.row)">删除</el-button>
                        </template>
                    </el-table-column> -->
                </el-table>
            </el-form> 
            <span class="footer" >
                <el-button type="primary" @click="saveEdit">确 定(通过）</el-button>
                <el-button @click="editVisible = false">取 消（拒绝）</el-button>
                <el-input v-model="form.user_name" placeholder="请输入拒绝理由" style="width:40%"></el-input>
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
       fileList2: [{name: 'food.jpeg', url: 'https://fuss10.elemecdn.com/3/63/4e7f3a15429bfda99bce42a18cdd1jpeg.jpeg?imageMogr2/thumbnail/360x360/format/webp/quality/100'}, {name: 'food2.jpeg', url: 'https://fuss10.elemecdn.com/3/63/4e7f3a15429bfda99bce42a18cdd1jpeg.jpeg?imageMogr2/thumbnail/360x360/format/webp/quality/100'}],
      form: {
        name: "",
        date: "",
        switch: false,
        add_time:"",
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
     handleRemove(file, fileList) {
        console.log(file, fileList);
      },
      handlePreview(file) {
        console.log(file);
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
.mr10{
    width: 200px;
}
.bg_img{
    width: 300px;
    height: 200px;
    background: skyblue
}
.bg_img img{
    width: 300px;
    height: 200px;
}
.footer{
    margin-left: 100px
}
/* .splice{
    width: 650px;
    height: 2px;
    background: silver;
    padding-bottom: 10px;
} */
</style>




