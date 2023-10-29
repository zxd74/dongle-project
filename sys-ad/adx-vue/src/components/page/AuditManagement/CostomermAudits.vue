<template>
  <div class="table">
    <div class="container">
      <div class="header-select">
        <template>
          <el-input v-model="select_id" placeholder="ID" class="handle-input mr10"></el-input>
          <el-input v-model="select_name" placeholder="客户名称" class="handle-input mr10"></el-input>
          <el-autocomplete
            class="inline-input"
            v-model="Dls_name"
            value-key="fullName"
            :fetch-suggestions="querySearch"
            placeholder="代理商名称"
            @select="handleSelect"
          ></el-autocomplete>
          <el-select v-model="optionsState" placeholder="状态">
            <el-option
              v-for="item in options"
              :key="item.value"
              :label="item.label"
              :value="item.value"
            ></el-option>
          </el-select>
          <el-button type="primary" icon="search" @click="search">搜索</el-button>
        </template>
      </div>
      <el-table :data="tableData" border style="width: 100%" ref="multipleTable">
        <el-table-column prop="id" label="ID" align="center"></el-table-column>
        <el-table-column prop="fullName" label="客户名称" align="center"></el-table-column>
        <el-table-column prop="industry" label="行业" align="center"></el-table-column>
        <el-table-column prop="auditStatus" label="状态" align="center" :formatter="formatterType"></el-table-column>
        <el-table-column width="150" align="center" v-if="this.readonly !=1">
          <template slot-scope="scope">
            <el-button size="small" type="success" @click="handleEdit(scope.$index, scope.row)">审核</el-button>
            <!-- <el-button size="small" type="danger" @click="handleDelete(scope.$index, scope.row)">删除</el-button> -->
          </template>
        </el-table-column>
      </el-table>
      <div class="pagination">
          <el-pagination @current-change="handleCurrentChange" 
         layout="total,prev, pager, next,jumper" 
         :total="total" 
         :current-page="cur_page" 
         :page-size="ps"
         ref="pagination"
         >
         </el-pagination>
      </div>
    </div>

    <!-- 编辑弹出框 -->
    <el-dialog title="客户审核" :visible.sync="editVisible" width="60%">
      <el-form ref="form" :model="form" status-icon label-width="100px" :label-position="labelPosition">
        <el-form-item label="客户ID:">{{id}}</el-form-item>
        <el-form-item label="客户名称:">{{fullName}}</el-form-item>
        <!-- <el-form-item label="营业执照号:">
                    {{111}}
        </el-form-item>-->
        <!-- <el-form-item label="股东姓名:">
                    {{111}}
        </el-form-item>-->
        <el-form-item label="联系人:">{{linkMan}}</el-form-item>
        <el-form-item label="联系电话:">{{phone}}</el-form-item>
        <el-form-item label="联系地址:">{{address}}</el-form-item>
        <el-form-item label="营业执照:">
          <div class="bg_img">
            <a :href="businesslicense" target="_blank">
              <img :src="businesslicense" alt>
            </a>
          </div>
        </el-form-item>
        <el-form-item label="其他资质:">
          <div class="bg_img">
            <a :href="qualifications" target="_blank">
              <!-- <img :src="businesslicense" alt=""> -->
            </a>
          </div>
        </el-form-item>
        <el-form-item label="行业类型:">{{industry}}</el-form-item>
      </el-form>
      <span class="footer">
        <el-button type="primary" @click="saveEditPass()">确 定(通过）</el-button>
        <el-button @click="saveEditReject()">取 消（拒绝）</el-button>
        <el-input v-model="auditComments" placeholder="请输入拒绝理由" style="width:40%"></el-input>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import {
  auditAdverpages,
  auditAdverauditInfo,
  auditAdveraudit,
  AgentList
} from "@/api/Api.js";




const uuid = new Map()
export default {
  name: "basetable",
  data() {
    return {
      labelPosition: 'left',
      url: "./static/vuetable.json",
      tableData: [],
      date: "",
      cur_page: 1,
      total: 1,
      ps: 10,
      select_id: "",
      select_name: "",
      Dls_name: "",
      select_cate: "",
      select_word: "",
      del_list: [],
      optionsState: "",
      is_search: false,
      editVisible: false,
      delVisible: false,
      newVisible: false,
      newaddAD: false,
      template_dialog: false,
      currentRow: null,
      add_time: "",
      auditComments: "",
      id: "",
      fullName: "",
      industry: "",
      linkMan: "",
      phone: "",
      address: "",
      businesslicense: "",
      qualifications: "",
      form: {
        name: "",
        date: "",
        switch: false,
        add_time: ""
      },

      idx: -1,
      type: 1,
      types: null,
      flow_management: null,
      data_report: null,
      options: [
         {
          value: "",
          label: "全部状态"
        },
        {
          value: "1",
          label: "通过"
        },
        {
          value: "2",
          label: "未通过"
        },
        {
          value: "3",
          label: "待审核"
        },
      ],
      value: "1",
      readonly:''
    };
  },
  created() {
    this.readonly = this.$store.state.map.get(this.$store.state.currentPath);
    this.getList();
  },

  methods: {
    // 模糊搜索代理商名称
    querySearch(queryString, cb) {
      let params = { type: 1, cp: this.cur_page, ps: this.ps,fullName:this.Dls_name };
      AgentList(params).then(res => {
      let data = res.data;
        // 调用 callback 返回建议列表的数据
        cb(data.data.data);
         data.data.data.forEach(element => {
          uuid.set(element.fullName,element.id)
        });
        if (data.code != "A000000") {
          this.$message.error(data.message);
        }
        //  this.dynamicTags = data.data
        //  this.QX_Visible = true;
      });
    },
    handleSelect(item) {
      // console.log(item);
      this.row = item;
    },
    // 格式化数据
    formatterType(row, column) {
      if (row.auditStatus == 1) {
        return <span class="bule">通过</span>;
      } else if (row.auditStatus == 2) {
        return <span class="red">未通过</span>;
      } else if (row.auditStatus == 3) {
        return <span class="yeloow">待审核</span>;
      } else if (row.auditStatus == 10) {
        return <span class="green">媒体审核中</span>;
      }
    },
    // 分页导航
    handleCurrentChange(val) {
      this.cur_page = val;
      let params = {
        id: this.select_id,
        fullName: this.select_name,
        aid: uuid.get(this.Dls_name),
        auditStatus: this.optionsState,
        cp: this.cur_page,
        ps: this.ps
      };
      auditAdverpages(params).then(res => {
      let data = res.data;
        this.tableData = data.data.data;
        this.total = data.data.totalItemNum;
        // console.log(data)
      });
    },
    // 获取数据
    getList() {
      let params = { cp: this.cur_page, ps: this.ps };
      auditAdverpages(params).then(res => {
      let data = res.data;
        this.tableData = data.data.data;
        this.total = data.data.totalItemNum;
        console.log(data);
      });
    },

    search() {
       let  reg=/^\+?[1-9][0-9]*$/;
      if(this.select_id != ''){
          if(!reg.test(this.select_id)){
          this.$message('请输入正确的ID')
          return;
        }
      }
      this.$refs.pagination.lastEmittedPage = 1
      this.cur_page = 1;
      let params = {
        id: this.select_id,
        fullName: this.select_name,
        aid: uuid.get(this.Dls_name),
        auditStatus: this.optionsState,
        cp: this.cur_page,
        ps: this.ps
      };
      auditAdverpages(params).then(res => {
      let data = res.data;
        this.tableData = data.data.data;
        this.total = data.data.totalItemNum;
        // console.log(data)
      });
    },
    // 编辑
    handleEdit(index, row) {
      let item = row;
      let params = { id: item.id };
      this.id = item.id;
      auditAdverauditInfo(params).then(res => {
      let data = res.data;
        console.log(data);
        this.id = data.data.id;
        this.fullName = data.data.fullName;
        this.industry = data.data.industry;
        this.linkMan = data.data.linkMan;
        this.phone = data.data.phone;
        this.address = data.data.address;
        this.businesslicense = data.data.businesslicense;
        this.qualifications = data.data.qualifications;
        this.auditComments = data.data.auditComment
      });
      this.editVisible = true;
    },
    //通过
    saveEditPass() {
      let params = {
        objId: this.id,
        auditState: 1
      };
      auditAdveraudit(params).then(res => {
        console.log(res);
      let data = res.data;
        if (data.code != "A000000") {
          this.$message.error(data.message);
        }
         setTimeout(() => {
          
        }, 1000); 
      this.editVisible = false;
      this.search();
      });
    
    },
    //拒绝
    saveEditReject() {
      if (this.auditComments == "") {
        this.$message.error("拒绝理由不能为空");
        return;
      }
      let params = {
        objId: this.id,
        auditState: 0,
        auditComments: this.auditComments
      };

      auditAdveraudit(params).then(res => {
        if (res.data.code != "A000000") {
          this.$message.error(res.data.message);
        }
        // console.log(res)
        setTimeout(() => {
          
        }, 1000);
        this.search();
        this.editVisible = false;
      });
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

    template_() {},
    handleRemove(file, fileList) {
      console.log(file, fileList);
    },
    handlePreview(file) {
      console.log(file);
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
.mr10 {
  width: 200px;
}
.bg_img {
  width: 300px;
  height: 200px;
  /* background: skyblue */
}
.bg_img img {
  width: 300px;
  height: 200px;
}
.footer {
  margin-left: 100px;
}
/* .splice{
    width: 650px;
    height: 2px;
    background: silver;
    padding-bottom: 10px;
} */
</style>




