<template>
    <div class="table">
        <div class="container">
            <div class="handle-box">
                <el-input v-model="select_word" placeholder="请输入用户名称" class="handle-input mr10"></el-input>
                <el-button type="primary" icon="search" @click="search">搜索</el-button>
                 <el-button type="primary" icon="search" @click="create">创建账户</el-button>
            </div>
            <el-table :data="tableData" border style="width: 100%" ref="multipleTable">
                <el-table-column prop="id" label="序号"  width="50" align="center">
                </el-table-column>
                <el-table-column prop="userName" label="用户名" align="center" width="150">
                </el-table-column>
                <el-table-column prop="company" label="公司名称" align="center"  width="200">
                </el-table-column>
                <el-table-column prop="type" label="账户类型" width="150" align="center" :formatter=formatterType>
                </el-table-column>
                <el-table-column prop="linkMan" label="联系人" align="center">
                </el-table-column>
                <el-table-column prop="phone" label="联系方式" align="center">
                </el-table-column>
                <el-table-column  label="状态"  width="60"  align="center">
                  <template slot-scope="scope">
                     <el-switch v-model="scope.row.status"  :active-value=1 :inactive-value=0></el-switch>
                  </template>
                </el-table-column>
                 <el-table-column prop="remark" label="备注" align="center">
                </el-table-column>
                <el-table-column label="操作"  width="150" align="center">
                    <template slot-scope="scope">
                        <el-button size="small" type="success" @click="handleEdit(scope.$index, scope.row)">编辑</el-button>
                        <el-button size="small" type="danger" @click="handleDelete(scope.$index, scope.row)">删除</el-button>
                        <!-- <el-button size="small" type="success" @click="handleEdit(scope.$index, scope.row)">修改密码</el-button> -->
                    </template>
                </el-table-column>
            </el-table>
            <div class="pagination">
                <el-pagination @current-change="handleCurrentChange" layout="prev, pager, next" :total="total" :current-page.sync="cur_page">
                </el-pagination>
            </div>
        </div>

        <!-- 编辑弹出框 -->
        <el-dialog title="编辑" :visible.sync="editVisible" width="45%">
             <el-form ref="form" :model="form" :rules="rules2"  status-icon label-width="100px" class="demo-ruleForm">
                <!-- <el-form-item label="用户名:" prop="userName" :disabled="true">
                  <el-input v-model="form.userName" placeholder="" style="width:80%" :disabled="true"></el-input>
                </el-form-item> -->
                <el-form-item label="账户密码:" prop="password">
                  <el-input v-model="form.password" placeholder="" style="width:80%"></el-input>
                </el-form-item>
                <!-- <el-form-item label="密码确认:" prop="checkPass">
                  <el-input v-model="form.checkPass" placeholder="请再一次输入登录密码" style="width:80%"></el-input>
                </el-form-item> -->
                <el-form-item label="账户类型:">
                    <el-select v-model="form.type" placeholder="请选择" @change="getTypes">
                      <el-option
                        v-for="item in options"
                        :key="item.value"
                        :label="item.label"
                        :value="item.value">
                      </el-option>
                    </el-select>
                    <p class="border_"></p>
                    <el-form-item label="" v-if="form.type == 10 ">
                          <el-select v-model="value2" placeholder="请选择名称" >
                            <el-option
                              v-for="item in options"
                              :key="item.value"
                              :label="item.label"
                              :value="item.value">
                             </el-option>
                           </el-select>
                    </el-form-item>
                    <div v-for="(item,index) in types" :key="index">
                        <el-form-item :label="item.name">
                          <el-checkbox v-model="item.status" true-label="1" false-label="0" :checked="item.status == 1">不可见</el-checkbox>
                          <el-radio v-model="item.readonly" :label="1">只读</el-radio>
                          <el-radio v-model="item.readonly" :label="0" >可修改</el-radio>
                          <div v-for="(item,ind) in item.childs" :key="ind">
                            <el-form-item :label="item.name">
                                <el-checkbox v-model="item.status" true-label="1" false-label="0" :checked="item.status == 1">不可见</el-checkbox>
                                <el-radio v-model="item.readonly" :label="1">只读</el-radio>
                                <el-radio v-model="item.readonly" :label="0" >可修改</el-radio>
                            </el-form-item>
                          </div>
                        </el-form-item>
                    </div>
                    <!-- <el-form-item label="数据概览:" v-if="value == 1|| value == 2|| value == 4|| value ==5">
                          <el-radio v-model="form.data_view" label="1">不可见</el-radio>
                          <el-radio v-model="form.data_view" label="2">只读</el-radio>
                    </el-form-item> 
                    <el-form-item label="流量管理:" v-if="value == 1||value == 4">
                          <el-radio v-model="form.flow_manage" label="1">不可见</el-radio>
                          <el-radio v-model="form.flow_manage" label="2">只读</el-radio>
                          <el-radio v-model="form.flow_manage" label="3">可修改</el-radio>
                    </el-form-item>
                    <el-form-item label="用户管理:" v-if="value == 1">
                          <el-radio v-model="form.user_manage" label="1">不可见</el-radio>
                          <el-radio v-model="form.user_manage" label="2">只读</el-radio>
                          <el-radio v-model="form.user_manage" label="3">可修改</el-radio>
                    </el-form-item>
                    <el-form-item label="客户管理:" v-if="value == 1||value ==2|| value ==5">
                            <el-radio v-model="form.KH_manage" label="1">不可见</el-radio>
                            <el-radio v-model="form.KH_manage" label="2">只读</el-radio>
                            <el-radio v-model="form.KH_manage" label="3">可修改</el-radio>
                    </el-form-item>
                    <el-form-item label="代理商管理:" v-if="value ==1">
                          <el-radio v-model="form.factor_manage" label="1">不可见</el-radio>
                          <el-radio v-model="form.factor_manage" label="2">只读</el-radio>
                          <el-radio v-model="form.factor_manage" label="3">可修改</el-radio>
                    </el-form-item>
                    <el-form-item label="广告管理:" v-if="value ==1|| value ==2||value ==4|| value ==5">
                          <el-radio v-model="form.AD_manage" label="1">不可见</el-radio>
                          <el-radio v-model="form.AD_manage" label="2">只读</el-radio>
                          <el-radio v-model="form.AD_manage" label="3">可修改</el-radio>
                    </el-form-item>
                    <el-form-item label="审核管理:" v-if="value ==1|| value == 3">
                          <el-radio v-model="form.SH_manage" label="1">不可见</el-radio>
                          <el-radio v-model="form.SH_manage" label="2">只读</el-radio>
                          <el-radio v-model="form.SH_manage" label="3">可修改</el-radio>
                    </el-form-item>
                    <el-form-item label="财务管理:" v-if="value == 1|| value ==2|| value ==5">
                          <el-radio v-model="form.finance_manage" label="1">不可见</el-radio>
                          <el-radio v-model="form.finance_manage" label="2">只读</el-radio>
                          <el-radio v-model="form.finance_manage" label="3">可修改</el-radio>
                    </el-form-item>
                    <el-form-item label="数据报告:" v-if="value ==1 || value ==2 || value ==4 || value ==5">
                         <el-radio v-model="form.data_report" label="1">不可见</el-radio>
                         <el-radio v-model="form.data_report" label="2">只读</el-radio>
                    </el-form-item>
                    <el-form-item label="DMP平台:" v-if="value ==1">
                          <el-radio v-model="form.DMP_" label="1">不可见</el-radio>
                          <el-radio v-model="form.DMP_" label="2">只读</el-radio>
                          <el-radio v-model="form.DMP_" label="3">可修改</el-radio>
                    </el-form-item>  -->
                </el-form-item>
                <el-form-item label="联系人:">
                    <el-input v-model="form.linkMan" placeholder="请输入联系人 " style="width:80%"></el-input>
                </el-form-item>
                <el-form-item label="联系方式:">
                    <el-input v-model="form.phone" placeholder="请输入联系方式" style="width:80%"></el-input>
                </el-form-item>
                <el-form-item label="备注:">
                    <el-input v-model="form.remark" style="width:80%"></el-input>
                </el-form-item>
            </el-form> 
            <span slot="footer" class="dialog-footer">
                <el-button @click="editVisible = false">取 消</el-button>
                <el-button type="primary" @click="saveEdit">确 定</el-button>
            </span>
        </el-dialog>

          <!-- 新建弹出框 -->
        <el-dialog title="新建账户" :visible.sync="newVisible" width="45%">
            <el-form ref="form" :model="form" :rules="rules2"  status-icon label-width="100px" class="demo-ruleForm">
                <el-form-item label="用户名:" prop="userName">
                  <el-input v-model="form.userName" placeholder="请输入用户名" style="width:80%"></el-input>
                </el-form-item>
                <el-form-item label="账户密码:" prop="password">
                  <el-input v-model="form.password" placeholder="请设置登录密码" style="width:80%"></el-input>
                </el-form-item>
                <el-form-item label="密码确认:" prop="checkPass">
                  <el-input v-model="form.checkPass" placeholder="请再一次输入登录密码" style="width:80%"></el-input>
                </el-form-item>
                <el-form-item label="账户类型:">
                    <el-select v-model="form.type" placeholder="请选择" @change="getTypes">
                      <el-option
                        v-for="item in options"
                        :key="item.value"
                        :label="item.label"
                        :value="item.value">
                      </el-option>
                    </el-select>
                    <p class="border_"></p>
                    <el-form-item label="" v-if="form.type == 10">
                          <el-select v-model="form.mediaName" placeholder="请选择名称" >
                            <el-option
                              v-for="item in flowsource"
                              :key="item.id"
                              :label="item.mediaName"
                              :value="item.id">
                             </el-option>
                           </el-select>
                    </el-form-item>
                    <div v-for="(item,index) in types" :key="index">
                        <el-form-item :label="item.name">
                          <el-checkbox v-model="item.status" true-label="1" false-label="0" :checked="item.status == 1">不可见</el-checkbox>
                          <el-radio v-model="item.readonly" :label="1">只读</el-radio>
                          <el-radio v-model="item.readonly" :label="0" >可修改</el-radio>
                          <div v-for="(item,ind) in item.childs" :key="ind">
                            <el-form-item :label="item.name">
                                <el-checkbox v-model="item.status" true-label="1" false-label="0" :checked="item.status == 1">不可见</el-checkbox>
                                <el-radio v-model="item.readonly" :label="1">只读</el-radio>
                                <el-radio v-model="item.readonly" :label="0" >可修改</el-radio>
                            </el-form-item>
                          </div>
                        </el-form-item>
                    </div>
                </el-form-item>
                <el-form-item label="联系人:">
                    <el-input v-model="form.linkMan" placeholder="请输入联系人 " style="width:80%"></el-input>
                </el-form-item>
                <el-form-item label="联系方式:">
                    <el-input v-model="form.phone" placeholder="请输入联系方式" style="width:80%"></el-input>
                </el-form-item>
                <el-form-item label="备注:">
                    <el-input v-model="form.remark" style="width:80%"></el-input>
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
             <!-- 修改密码 -->
         <!-- <el-dialog title="提示" :visible.sync="Editmima" width="300px" center>
            <el-form ref="form" :model="form" :rules="rules2"  status-icon label-width="100px">
                <el-tag type="success" size="medium"> 基本信息 </el-tag>
                <el-form-item label="旧密码:" prop="password">
                  <el-input v-model="form.password" placeholder="请输入用户名" style="width:80%"></el-input>
                </el-form-item>
                <el-form-item label="新密码:" prop="password">
                  <el-input v-model="form.password" placeholder="请设置登录密码" style="width:80%"></el-input>
                </el-form-item>
                 <span slot="footer" class="dialog-footer">
                    <el-button @click="Editmima = false">取 消</el-button>
                    <el-button type="primary" @click="savePassword(index,row)">确 定</el-button>
                 </span>
            </el-form> 
        </el-dialog> -->
    </div>
</template>

<script>
import { UserList } from "@/api/Api.js";
import { UserAdd } from "@/api/Api.js";
import { UserUpdate } from "@/api/Api.js";
import { UserDelete } from "@/api/Api.js";
import { UserGetAuth } from "@/api/Api.js";
import { GetallFlowsource } from "@/api/Api.js";

export default {
  name: "basetable",
  data() {
    var validatePass2 = (rule, value, callback) => {
        if (value === '') {
          callback(new Error('请再次输入密码'));
        } else if (value !== this.form.password) {
          callback(new Error('两次输入密码不一致!'));
        } else {
          callback();
        }
      };
    return {
      url: "./static/vuetable.json",
      tableData: [],
      cur_page: 1,
      total: 1,
      ps:10,
      select_word: "",
      is_search: false,
      editVisible: false,
      delVisible: false,
      newVisible: false,
      template_dialog: false,
      currentRow: null,
      form: {
        name: "",
        date: "",
        userName:'',
        password:'',
        checkPass:'',
        linkMan:'',
        phone:'',
        remark:'',
        status: 1,
        readonly: 1,
        type:'',
        aid:'',
        authsList:[],
        data_view:'2',
        flow_manage:'3',
        user_manage:'3',
        KH_manage:'3',
        factor_manage:'3',
        AD_manage:'3',
        SH_manage:'3',
        finance_manage:'3',
        data_report:'2',
        DMP_:'3',
        mediaName:'',
      },
      rules2: {
          userName: [
            { required: true, message: '请输入用户名', trigger: 'blur' },
          ],
           password: [
            { required: true, message: '请输入用户名', trigger: 'blur' },
          ],
          checkPass: [
             { validator: validatePass2,message: '两次输入不一致', trigger: 'blur' }
          ],
        },
      idx: -1,
      type: 1,
      types: null,
      typesChild: null,
      flow_management: null,
      data_report:null,
  
      options: [
        {
          value: 9,
          label: "管理员"
        },
        {
          value: 13,
          label: "直客运营"
        },
          {
          value: 15,
          label: "审核员"
        },
          {
          value: 10,
          label: "流量管理员"
        },
        {
          value: 14,
          label: "代理商运营"
        },
         {
          value: 16,
          label: "客户"
        },
      ],
      flowsource:[],
      value: "1",
      value2:'',
      value3:'',

    };
  },
  created() {
    this.getList();
  },

  methods: {
    getTypes(value) {
      // console.log(value);
     let params = {platform:1,gid:value}
     UserGetAuth(params).then(res=>{
      //let data = res.data;
          // console.log(res)
       if(res.data.code != 'A000000') {
          this.$message.error(res.data.message);
       }
       this.types = res.data.data;
       if(value == 10){
          GetallFlowsource().then(res=>{
        let data = res.data;     
          this.flowsource = data.data
          console.log(data)
          });
       }
    });
    },
    // 分页导航
    handleCurrentChange(val) {
      console.log(val);
      this.cur_page = val;
      this.getList();
    },
    // 获取 table
     getList() {
      let params = {
        cp:this.cur_page,
        ps:this.ps
      }
      UserList(params).then(res=>{
     let data = res.data;     
       this.tableData = data.data.data
       this.total = data.data.totalItemNum;
      // console.log(res)
      });
    },
      // 格式化数据
    formatterType(row, column) {
      if(row.type == 9) {
        return "管理员";
      } else if(row.type == 13){
        return "直客运营";
      } else if(row.type == 15){
        return "审核员";
      } else if(row.type == 10){
        return "流量管理员";
      } else if(row.type == 14){
        return "代理商运营";
      }
      
    },
     // 搜索
    search() {
     let params = {userName:this.select_word}
      UserList(params).then(res=>{
     let data = res.data;     
       this.tableData = data.data.data
    });
    },
    formatter(row, column) {
      return row.address;
    },
    filterTag(value, row) {
      return row.tag === value;
    },
    // 编辑
      handleEdit(index, row) {
      this.idx = index;
      let item = row;
      this.form = {
        id:item.id,
        type:item.type,
        password: item.password,
        linkMan:item.linkMan,
        phone:item.phone,
        remark:item.remark,
        authsList:this.types,
        status: item.status
      };
      this.editVisible = true;
      this.types = null;
      this.getTypes(item.type);
    },
    // 保存编辑
      saveEdit() {
        console.log(JSON.stringify(this.types))
        let params = {
        id:this.form.id,
        type:this.form.type,
        status:this.form.status,
        password: this.form.password,
        linkMan:this.form.linkMan,
        phone:this.form.phone,
        remark:this.form.remark,
        authsList:JSON.stringify(this.types)
        }
    
       UserUpdate(JSON.stringify(params)).then(res=>{
        let data = res.data;
          if(data.code != 'A000000') {
              this.$message.error(data.message);
          }
             this.getList();
        });
      this.editVisible = false;
      this.$message.success(`修改成功`);
      },
        // 删除
      handleDelete(index, row) {
          this.delVisible = true;
          this.row = row;
      },
        // 确定删除
      deleteRow() {
         let params = {id:this.row.id}
          // console.log(row)
          UserDelete(params).then(res=>{
              let data = res.data;
                if(data.code != 'A000000') {
                    this.$message.error(data.message);
                    return
                }else{
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
       
      };
      this.types = null;
    },
    saveNews() {
       // 参数判断
      if(!this.form.userName) {
        this.$message.error("用户名不能为空");
        return;
      }
      if(!this.form.password) {
        this.$message.error("密码不能为空");
        return;
      }
      if(!this.form.type) {
        this.$message.error("用户类型不能为空");
        return;
      }
        let params = {
            userName:this.form.userName,
            password:this.form.password,
            type:this.form.type,
            linkMan:this.form.linkMan,
            phone:this.form.phone,
            aid:this.form.aid,
            remark:this.form.remark,
            authsList:JSON.stringify(this.types)
            }
       UserAdd(JSON.stringify(params)).then(res=>{
     let data = res.data;
          console.log(res)
       if(data.code != 'A000000') {
          this.$message.error(data.message);
       }
       // 掉查询接口
       this.newVisible = false;
       this.getList();
    });
    },
    // handleCurrentChange(val) {
    //     this.currentRow = val;
    // },
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
.border_{
      padding-top: 10px;
}
</style>
<style>
    .el-form-item__content{
        margin-left:''
    }
</style>



