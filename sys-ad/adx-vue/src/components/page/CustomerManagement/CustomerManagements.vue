<template>
  <div class="table">
    <div class="container">
      <div class="handle-box">
        <el-autocomplete
          class="inline-input"
          v-model="select_word"
          value-key="fullName"
          :fetch-suggestions="querySearchCustomer"
          placeholder="请输入客户名称"
          @select="handleSelectCustomer"
        ></el-autocomplete>
        <el-button type="primary" icon="search" @click="search">搜索</el-button>
        <el-button type="primary" icon="search" @click="create">新建客户</el-button>
      </div>
      <el-table :data="tableData" border style="width: 100%" ref="multipleTable">
        <el-table-column prop="id" label="序号" width="50" align="center"></el-table-column>
        <el-table-column prop="fullName" label="公司名称" width="100" align="center"></el-table-column>
        <!-- <el-table-column prop="agName" label="所属代理商" align="center">
        </el-table-column>-->
        <!-- <el-table-column prop="money" label="余额" align="center">
        </el-table-column>-->
        <el-table-column prop="industry" label="所属行业" align="center"></el-table-column>
        <el-table-column prop="readonly" label="客户权限" align="center" :formatter="formatterType"></el-table-column>
        <el-table-column prop label="用户权限" align="center" v-if="this.readonly !=1">
          <template slot-scope="scope">
            <img
              src="../../../assets/u518.png"
              alt="用户权限"
              @click="edit_quanxian(scope.$index, scope.row)"
            >
          </template>
        </el-table-column>
        <!-- <el-table-column prop="auditComment" label="审核状态" align="center" v-if="this.readonly !=1">
        </el-table-column>-->
        <el-table-column label="状态" width="60" align="center" v-if="this.readonly !=1">
          <template slot-scope="scope">
            <el-switch
              v-model="scope.row.status"
              :active-value="1"
              :inactive-value="0"
              @change="changeStatus($event,scope.row)"
            ></el-switch>
          </template>
        </el-table-column>
        <el-table-column label="操作" width="200" align="center" v-if="this.readonly == 1">
          <template slot-scope="scope">
            <el-button size="small" type="danger" @click="handleEdit(scope.$index, scope.row)">编辑</el-button>
          </template>
        </el-table-column>
        <el-table-column label="操作" width="200" align="center" v-if="this.readonly !=1">
          <template slot-scope="scope">
            <!-- <el-button size="small" type="success" @click="finance_manage(scope.$index, scope.row)">财务管理
            </el-button>-->
            <el-button size="small" type="danger" @click="handleDelete(scope.$index, scope.row)">删除</el-button>
            <el-button size="small" type="success" @click="handleEdit(scope.$index, scope.row)">编辑</el-button>
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

    <!-- 修改密码弹出框 -->
    <el-dialog title="修改密码" :visible.sync="editVisible" width="40%">
      <el-form
        ref="form"
        :model="form"
        :rules="rules2"
        status-icon
        label-width="100px"
        class="demo-ruleForm"
      >
        <el-tag type="success">修改密码</el-tag>
        <el-form-item label="旧密码:" prop="user_name">
          <el-input v-model="form.password" placeholder="请输入旧密码" style="width:80%"></el-input>
        </el-form-item>
        <el-form-item label="新密码:" prop="password">
          <el-input v-model="form.password" placeholder="请设置登录密码" style="width:80%"></el-input>
        </el-form-item>
        <el-form-item label="密码确认:" prop="checkPass">
          <el-input v-model="form.checkPass" placeholder="请再一次输入登录密码" style="width:80%"></el-input>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="editVisible = false">取 消</el-button>
        <el-button type="primary" @click="saveEdit">确 定</el-button>
      </span>
    </el-dialog>

    <!-- 新建弹出框 -->
    <el-dialog title="新建客户" :visible.sync="newVisible" width="45%" class="demo-ruleForm">
      <el-form
        ref="form"
        :model="form"
        :rules="rules2"
        status-icon
        label-width="100px"
        :label-position="labelPosition"
      >
        <el-tag type="success">基本信息</el-tag>
        <el-form-item label="用户名:" prop="userName">
          <el-input v-model="form.userName" placeholder="请输入用户名" style="width:80%"></el-input>
        </el-form-item>
        <el-form-item label="账户密码:" prop="password">
          <el-input v-model="form.password" placeholder="请设置登录密码" style="width:80%" type="password"></el-input>
        </el-form-item>
        <el-form-item label="密码确认:" prop="checkPass">
          <el-input
            v-model="form.checkPass"
            placeholder="请再一次输入登录密码"
            style="width:80%"
            type="password"
          ></el-input>
        </el-form-item>
        <el-form-item label="* 客户权限:">
          <el-radio v-model="form.readonly" :label="1">只读</el-radio>
          <el-radio v-model="form.readonly" :label="0">可修改</el-radio>
        </el-form-item>
        <el-form-item label="* 公司名称:">
          <el-input v-model="form.fullName" placeholder="请输入公司名称" style="width:80%"></el-input>
        </el-form-item>
        <el-form-item label="联系人:">
          <el-input v-model="form.linkMan" placeholder="请输入联系人" style="width:80%"></el-input>
        </el-form-item>
        <el-form-item label="联系方式:">
          <el-input v-model="form.phone" placeholder="请输入联系方式" style="width:80%" type="number"></el-input>
        </el-form-item>
        <el-form-item label="联系地址:">
          <el-input v-model="form.address" placeholder="请输入联系方式" style="width:80%"></el-input>
        </el-form-item>
        <el-form-item label="* 行业类型:">
          <el-select v-model="form.industryType" class="handle-input">
            <el-option v-for="item in options" :key="item.id" :label="item.name" :value="item.id"></el-option>
          </el-select>
        </el-form-item>
        <!-- <div v-if="this.limits == 1">
                    <el-tag type="success">财务系数</el-tag>
                    <el-form-item label="竞价系数:">
                        <el-input-number :min="0" :max="1" v-model="form.bidDiscount" style="width:80%"></el-input-number>
                    </el-form-item>
                    <el-form-item label="扣费系数:">
                        <el-input-number :min="0" :max="1" v-model="form.payDiscount" style="width:80%"></el-input-number>
                    </el-form-item>
        </div>-->
        <el-tag type="success">资质信息</el-tag>
        <el-form-item label="* 营业执照:">
          <el-input v-model="form.businesslicense" style="width:50%"></el-input>
          <el-upload
            class="upload-demo"
            :data="{type:1}"
            :action="upload"
            name="myFile"
            :on-success="handleAvatarSuccess"
          >
            <el-button size="small" type="primary">点击上传</el-button>
          </el-upload>
        </el-form-item>
        <el-form-item label="其他资质:">
          <!-- <div class="add_task" v-for="(q,i) in form.qualifications" :key="i"> -->
          <el-input v-model="form.qualifications" style="width:50%"></el-input>
          <el-upload
            class="upload-demo"
            :data="{type:6}"
            :action="upload"
            name="myFile"
            :on-success="handleAvatar"
          >
            <el-button size="small" type="primary">点击上传</el-button>
          </el-upload>
          <!-- </div> -->
          <!-- <el-button type="primary" icon="search" @click="add_div">+</el-button> -->
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

    <!-- 权限提示框 -->
    <el-dialog title="只有授权的用户可以操作该客户" :visible.sync="QX_Visible" width="30%" center>
      <el-autocomplete
        class="inline-input"
        v-model="state1"
        value-key="userName"
        :fetch-suggestions="querySearch"
        placeholder="请输入内容"
        @select="handleSelect"
      ></el-autocomplete>
      <!-- <el-button type="primary" @click="saveNews">搜 索</el-button> -->
      <div class="div">
        <el-tag
          :key="tag.id"
          v-for="tag in dynamicTags"
          closable
          :disable-transitions="false"
          @close="handleClose(tag)"
        >{{tag.uName}}</el-tag>
      </div>
      <span slot="footer" class="dialog-footer">
        <el-button @click="QX_Visible = false">取 消</el-button>
        <el-button type="primary" @click="QX_save">确 定</el-button>
      </span>
    </el-dialog>

    <!-- 媒体审核提示框 -->
    <el-dialog title="媒体审核" :visible.sync="media_Audit" width="60%">
      <el-form
        ref="form"
        :model="form"
        status-icon
        label-width="100px"
        :label-position="labelPosition"
      >
        <el-form-item label="客户ID:">{{form.id}}</el-form-item>
        <el-form-item label="客户权限:">
          <el-radio v-model="form.readonly" :label="1">只读</el-radio>
          <el-radio v-model="form.readonly" :label="0">可修改</el-radio>
        </el-form-item>
        <el-form-item label="公司名称:">
          <el-input
            v-model="form.fullName"
            placeholder="请输入公司名称"
            style="width:80%"
            :disabled="true"
          ></el-input>
        </el-form-item>
        <el-form-item label="行业类型:">
          <el-select v-model="form.industryType" class="handle-input">
            <el-option v-for="item in options" :key="item.id" :label="item.name" :value="item.id"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="联系人:">
          <el-input v-model="form.linkMan" placeholder="请输入联系人" style="width:80%"></el-input>
        </el-form-item>
        <el-form-item label="联系电话:">
          <el-input v-model="form.phone" placeholder="请输入联系电话" style="width:80%" type="number"></el-input>
        </el-form-item>
        <el-form-item label="联系地址:">
          <el-input v-model="form.address" placeholder="请输入联系地址" style="width:80%"></el-input>
        </el-form-item>
        <!-- <div v-if="this.limits == 1">
                    <el-form-item label="竞价系数:">
                        <el-input-number :max="1" :min="0" v-model="form.bidDiscount"
                                         style="width:80%"></el-input-number>
                    </el-form-item>
                    <el-form-item label="扣费系数:">
                        <el-input-number :max="1" :min="0" v-model="form.payDiscount" style="width:80%"></el-input-number>
                    </el-form-item>
        </div>-->
        <el-form-item label="营业执照:">
          <el-input v-model="form.businesslicense" style="width:50%"></el-input>
          <el-upload
            class="upload-demo"
            :data="{type:1}"
            :action="upload"
            name="myFile"
            :on-success="handleAvatarSuccess"
          >
            <el-button size="small" type="primary">点击上传</el-button>
          </el-upload>
        </el-form-item>
        <el-form-item label="其他资质:">
          <el-input v-model="form.qualifications" style="width:50%"></el-input>
          <el-upload
            class="upload-demo"
            :data="{type:6}"
            :action="upload"
            name="myFile"
            :on-success="handleAvatar"
          >
            <el-button size="small" type="primary">点击上传</el-button>
          </el-upload>
        </el-form-item>
        <div class="splice"></div>
        <div class="dialog_footer">
          <el-button type="primary" @click="saveEdit" v-if="this.readonly !=1">保 存</el-button>
          <el-button @click="media_Audit = false">返 回</el-button>
        </div>
        <el-tag type="success" v-if="this.readonly !=1">媒体提审</el-tag>
        <div class="splice" v-if="this.readonly !=1"></div>
        <el-table
          :data="media"
          border
          style="width: 100%"
          ref="multipleTable"
          v-if="this.readonly !=1"
        >
          <el-table-column prop="mediaName" label="媒体名称" align="center"></el-table-column>
          <el-table-column
            prop="auditState"
            label="审核状态"
            align="center"
            :formatter="formatterAuditState"
          ></el-table-column>
          <el-table-column prop="auditComments" label="审核备注" align="center"></el-table-column>
          <el-table-column label="操作" align="center">
            <template slot-scope="scope">
              <el-button
                size="small"
                type="success"
                @click="commit_Audit(scope.$index, scope.row)"
              >提交审核</el-button>
            </template>
          </el-table-column>
        </el-table>
      </el-form>
    </el-dialog>
  </div>
</template>

<script>
import { CustomerList } from "@/api/Api.js";
import { CustomerAdd } from "@/api/Api.js";
import { CustomerUpdate } from "@/api/Api.js";
import { CustomerDelete } from "@/api/Api.js";
import { CustomerGet } from "@/api/Api.js";
import { industryManage } from "@/api/Api.js";
import { CustomergetGrand } from "@/api/Api.js";
import { CustomerGrand } from "@/api/Api.js";
import { CustomermoveGrand } from "@/api/Api.js";
import { UserList } from "@/api/Api.js";

import { GetallFlowsource, flowsourcegetallbytype } from "@/api/Api.js";
import { relationAdd, upload, usergetAgInfo } from "@/api/Api.js";

export default {
  name: "basetable",
  data() {
    var validatePass2 = (rule, value, callback) => {
      if (value === "") {
        callback(new Error("请再次输入密码"));
      } else if (value != this.form.password) {
        callback(new Error("两次输入密码不一致!"));
      } else {
        callback();
      }
    };
    return {
      labelPosition: "left",
      upload: upload,
      tableData: [],
      media: [],
      cur_page: 1,
      total: 1,
      ps: 10,
      append_div: [],
      select_cate: "",
      select_word: "",
      del_list: [],
      editVisible: false,
      delVisible: false,
      newVisible: false,
      QX_Visible: false,
      media_Audit: false,
      currentRow: null,
      dynamicTags: [],
      inputVisible: false,
      inputValue: "",
      state1: "",
      form: {
        id: "",
        fullName: "",
        readonly: "",
        linkMan: "",
        phone: "",
        status: 1,
        industryType: "",
        bidDiscount: "1.0",
        payDiscount: "1.0",
        businesslicense: "",
        qualifications: "",
        userName: "",
        password: ""
      },
      rules2: {
        userName: [
          { required: true, message: "请输入用户名", trigger: "blur" }
        ],
        password: [{ required: true, message: "请输入密码", trigger: "blur" }],
        checkPass: [
          {
            validator: validatePass2,
            message: "两次输入不一致",
            trigger: "blur"
          }
        ]
      },
      idx: -1,
      type: 1,
      types: null,
      flow_management: null,
      data_report: null,

      options: [
        {
          value: "",
          label: ""
        }
      ],
      industryType: "",
      readonly: "",
      limits: ""
    };
  },
  created() {
    this.readonly = this.$store.state.map.get(this.$store.state.currentPath);
    this.getList();
    this.getIndustry();
    this.getinfo();
  },

  methods: {
    getinfo() {
      usergetAgInfo().then(res => {
        console.log(res);
        let data = res.data;
        if (data.code != "A000000") {
          this.$message.error(data.message);
          return;
        }
        this.limits = data.data;
        console.log(this.limits);
      });
    },
    // 模糊
    querySearchCustomer(queryString, cb) {
      let params = {
        fullName: this.select_word,
        cp: 1,
        ps: this.ps,
        type: 2
      };
      CustomerList(params).then(res => {
        let data = res.data;
        if (data.code != "A000000") {
          this.$message.error(data.message);
          return;
        }
        // 调用 callback 返回建议列表的数据
        cb(data.data.data);
      });
    },
    handleSelectCustomer(item) {
      this.id = item.id;
    },
    // 格式化数据
    formatterType(row, column) {
      if (row.readonly == 1) {
        return "只读";
      } else if (row.readonly == 0) {
        return "可修改";
      }
    },
    formatterAuditState(row, column) {
      if (row.auditState == 1) {
        return "审核通过";
      } else if (row.auditState == 2) {
        return "审核未通过";
      } else if (row.auditState == 3) {
        return "待审核";
      } else if (row.auditState == 7) {
        return "审核拒绝";
      }
    },
    // 分页导航
    handleCurrentChange(val) {
      this.cur_page = val;
      let params = {
        fullName: this.select_word,
        type: 2,
        cp: this.cur_page,
        ps: this.ps
      };
      CustomerList(params).then(res => {
        let data = res.data;
        if (data.code != "A000000") {
          this.$message.error(data.message);
          return;
        }
        this.tableData = data.data.data;
        this.total = data.data.totalItemNum;
      });
    },
    getList() {
      let params = { type: 2, cp: this.cur_page, ps: this.ps };
      CustomerList(params).then(res => {
        let data = res.data;
        this.tableData = data.data.data;
        this.total = data.data.totalItemNum;
      });
    },
    // 行业选项
    getIndustry() {
      industryManage().then(res => {
        let data = res.data;
        this.options = data.data;
      });
    },
    // 搜索
    search() {
      this.$refs.pagination.lastEmittedPage = 1;
      this.cur_page = 1;
      let params = {
        fullName: this.select_word,
        type: 2,
        cp: this.cur_page,
        ps: this.ps
      };
      CustomerList(params).then(res => {
        let data = res.data;
        if (data.code != "A000000") {
          this.$message.error(data.message);
          return;
        }
        this.tableData = data.data.data;
        this.total = data.data.totalItemNum;
      });
    },
    //  编辑（媒体审核）
    handleEdit(index, row) {
      this.idx = index;
      let item = row;
      this.form = {
        id: item.id,
        fullName: item.fullName,
        readonly: item.readonly,
        linkMan: item.linkMan,
        phone: item.phone,
        address: item.address,
        industryType: item.industryType,
        bidDiscount: item.bidDiscount,
        payDiscount: item.payDiscount,
        businesslicense: item.businesslicense,
        qualifications: item.qualifications,
        // qualifications:item.qualifications?item.qualifications.split(",") : [],
        aid: 1
      };
      this.media_Audit = true;
      // 获取媒体名称
      flowsourcegetallbytype().then(res => {
        let data = res.data;
        let media = data.data;
        // for(var i=0;i<data.data.length;i++) {
        // 获取审核状态
        let params = { id: row.id };
        CustomerGet(params).then(res => {
          let data = res.data;
          for (var i = 0; i < media.length; i++) {
            for (var j = 0; j < data.data.relations.length; j++) {
              if (data.data.relations[j].adxType == media[i].id) {
                media[i].auditState = data.data.relations[j].auditState;
                media[i].auditComments = data.data.relations[j].auditComments;
                media[i].industryType = data.data.relations[j].industry;
              }
            }
          }
          this.media = media;
        });
        // }
      });
    },
    // 保存编辑
    saveEdit() {
      let params = {
        id: this.form.id,
        fullName: this.form.fullName,
        readonly: this.form.readonly,
        linkMan: this.form.linkMan,
        phone: this.form.phone,
        address: this.form.address,
        industryType: this.form.industryType,
        bidDiscount: this.form.bidDiscount,
        payDiscount: this.form.payDiscount,
        businesslicense: this.form.businesslicense,
        qualifications: this.form.qualifications,
        aid: 1
      };
      CustomerUpdate(params).then(res => {
        let data = res.data;
        if (data.code != "A000000") {
          this.$message.error(data.message);
          return;
        }
        this.search();
        this.media_Audit = false;
        this.$message.success(`修改成功`);
      });

      // this.search();
    },
    // 开关
    changeStatus(val, row) {
      let params = {
        id: row.id,
        status: row.status
      };
      CustomerUpdate(params).then(res => {
        let data = res.data;
        if (data.code != "A000000") {
          this.$message.error(data.message);
          return;
        }
        // this.getList();
      });
    },
    // 提交审核
    commit_Audit(index, row) {
      let item = row;
      let params = {
        adxType: item.id,
        objId: this.form.id,
        objType: 1,
        industry: item.industryType
      };
      relationAdd(params).then(res => {
        let data = res.data;
        if (data.code != "A000000") {
          this.$message.error(data.message);
        } else {
          this.$message.success("提交成功");
        }
        // this.flowsource = data.data
      });
    },
    // add_div() {
    //     this.form.qualifications.push('');
    //     this.$forceUpdate()
    // },
    // 删除
    handleDelete(index, row) {
      this.delVisible = true;
      this.row = row;
    },
    // 确定删除
    deleteRow() {
      let params = { id: this.row.id };
      CustomerDelete(params).then(res => {
        let data = res.data;
        if (data.code != "A000000") {
          this.$message.error(data.message);
          return;
        } else {
          this.$message.success("删除成功");
          this.delVisible = false;
        }
        this.getList();
      });
    },

    // 新建
    create() {
      this.newVisible = true;
      this.form = {
        bidDiscount: "1.0",
        payDiscount: "1.0",
        readonly: 0
      };
      // this.form.qualifications = [''];
    },
    saveNews() {
      // 参数判断
      if (!this.form.userName) {
        this.$message.error("用户名不能为空");
        return;
      }
      if (!this.form.password) {
        this.$message.error("密码不能为空");
        return;
      }
      // if (!this.form.industryType) {
      //     this.$message.error("行业类型不能为空");
      //     return;
      // }
      if (!this.form.fullName) {
        this.$message.error("公司名称不能为空");
        return;
      }
      if (!this.form.businesslicense) {
        this.$message.error("营业执照不能为空");
        return;
      }
      let params = {
        type: 2,
        fullName: this.form.fullName,
        readonly: this.form.readonly,
        linkMan: this.form.linkMan,
        phone: this.form.phone,
        address: this.form.address,
        industryType: this.form.industryType,
        bidDiscount: this.form.bidDiscount,
        payDiscount: this.form.payDiscount,
        businesslicense: this.form.businesslicense,
        qualifications: this.form.qualifications,
        userName: this.form.userName,
        password: this.form.password,
        aid: 1
      };
      CustomerAdd(params).then(res => {
        let data = res.data;
        if (data.code != "A000000") {
          this.$message.error(data.message);
          return;
        }
        // 掉查询接口
        this.newVisible = false;
        this.getList();
      });
    },

    // 权限弹出
    edit_quanxian(index, row) {
      this.id = row.id;
      let params = {
        aid: row.id
      };
      CustomergetGrand(params).then(res => {
        let data = res.data;
        if (data.code != "A000000") {
          this.$message.error(data.message);
          return;
        }
        this.dynamicTags = data.data;
        this.QX_Visible = true;
      });
    },
    // 模糊搜索
    querySearch(queryString, cb) {
      let params = {
        userName: this.state1
      };
      UserList(params).then(res => {
        let data = res.data;
        if (data.code != "A000000") {
          this.$message.error(data.message);
          return;
        }
        // 调用 callback 返回建议列表的数据
        cb(data.data.data);
        //  this.dynamicTags = data.data
        //  this.QX_Visible = true;
      });
    },
    // 添加
    handleSelect(item) {
      let params = {
        aid: this.id,
        uid: item.id
      };
      CustomerGrand(params).then(res => {
        let data = res.data;
        if (data.code != "A000000") {
          this.$message.error(data.message);
          return;
        }
        let parm = {
          aid: this.id
        };
        CustomergetGrand(parm).then(res => {
          let data = res.data;

          if (data.code != "A000000") {
            this.$message.error(data.message);
            return;
          }
          this.dynamicTags = data.data;
        });
      });
    },
    // 权限删除
    handleClose(tag) {
      let params = {
        id: tag.id
      };
      CustomermoveGrand(params).then(res => {
        let data = res.data;
        if (data.code != "A000000") {
          this.$message.error(data.message);
          return;
        }
        let parm = {
          aid: this.id
        };
        CustomergetGrand(parm).then(res => {
          let data = res.data;

          if (data.code != "A000000") {
            this.$message.error(data.message);
            return;
          }
          this.dynamicTags = data.data;
        });
      });
    },
    // 权限保存
    QX_save() {
      this.QX_Visible = false;
    },
    finance_manage(index, row) {
      this.$router.push({
        path: "/moneymanagement/customer",
        query: { id: row.id, fullName: row.fullName }
      });
    },
    // 文件上传
    handleAvatarSuccess(res, file) {
      let data = res;
      if (data.code != "A000000") {
        this.$message.error(data.message);
        return;
      } else {
        this.form.businesslicense = data.data.url;
      }
      this.$forceUpdate();
    },
    handleAvatar(res, file) {
      let data = res;
      if (data.code != "A000000") {
        this.$message.error(data.message);
        return;
      } else {
        this.form.qualifications = data.data.url;
      }
      this.$forceUpdate();
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

.div {
  padding: 20px;
}

.el-tag + .el-tag {
  margin-left: 10px;
}

.size {
  font-size: 24px;
}

.bg_img {
  width: 300px;
  height: 200px;
  background: skyblue;
}

.el-button + .el-button {
  margin-left: 0px;
}

.splice {
  height: 2px;
  background: silver;
  margin-top: 10px;
  margin-bottom: 10px;
}

.dialog_footer {
  margin-left: 80%;
  margin-bottom: 20px;
}
</style>
<style>
.el-upload--text {
  border: 0px dashed #d9d9d9 !important;
  width: 80px !important;
  height: 32px !important;
}

.upload-demo {
  display: inline-flex;
}

.el-upload-list {
  display: none;
}
</style>



