<template>
    <div class="table">
        <div class="container">
            <div class="handle-box">
                <el-autocomplete
                    class="inline-input"
                    v-model="select_word"
                    value-key="userName"
                    :fetch-suggestions="querySearchName"
                    placeholder="请输入用户名称"
                    @select="handleSelectName"
                ></el-autocomplete>
                <el-button type="primary" icon="search" @click="search">搜索</el-button>
                <el-button type="primary" icon="search" @click="create">创建账户</el-button>
            </div>
            <el-table :data="tableData" border style="width: 100%" ref="multipleTable">
                <el-table-column prop="id" label="序号" width="50" align="center">
                </el-table-column>
                <el-table-column prop="userName" label="用户名" align="center" width="150">
                </el-table-column>
                <el-table-column prop="companyName" label="公司名称" align="center" width="150">
                </el-table-column>
                <el-table-column prop="groupName" label="账户类型" width="100" align="center">
                </el-table-column>
                <el-table-column prop="realName" label="联系人" align="center">
                </el-table-column>
                <el-table-column prop="phone" label="联系方式" align="center">
                </el-table-column>
                <el-table-column label="状态" width="60" align="center">
                    <template slot-scope="scope">
                        <el-switch v-model="scope.row.status" :active-value=1 :inactive-value=0
                                   @change="changeStatus($event,scope.row)"></el-switch>
                    </template>
                </el-table-column>
                <el-table-column prop="remark" label="备注" align="center">
                </el-table-column>
                <el-table-column label="操作" width="150" align="center">
                    <template slot-scope="scope">
                        <el-button size="small" type="success" @click="handleEdit(scope.$index, scope.row)">编辑
                        </el-button>
                        <el-button size="small" type="danger" @click="handleDelete(scope.$index, scope.row)">删除
                        </el-button>
                        <!-- <el-button size="small" type="success" @click="handleEdit(scope.$index, scope.row)">修改密码</el-button> -->
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
        <el-dialog title="编辑" :visible.sync="editVisible" width="45%">
            <el-form ref="form" :model="form" status-icon label-width="80px" class="demo-ruleForm" :label-position="labelPosition">
                <el-form-item label="用户名:" prop="userName" :disabled="true">
                    <el-input v-model="form.userName" style="width:80%" :disabled="true"></el-input>
                    <el-input type="hidden" v-model="form.id"></el-input>
                </el-form-item>
                <el-form-item label="账户密码:" prop="password">
                    <el-input type="password" v-model="form.password" placeholder="" style="width:80%"></el-input>
                </el-form-item>
                <el-form-item label="账户类型:" v-if="userTypes !=12">
                  <!-- <el-input  style="width:80%"  v-model="form.groupName" disabled
                             v-if="form.type == 12 || form.type ==16"></el-input> -->
                    <el-select v-model="form.type" placeholder="请选择" @change="getTypes" v-if="form.type != 12 && form.type !=16" disabled>
                        <el-option
                            v-for="item in groups"
                            :key="item.id"
                            :label="item.name"
                            :value="item.id">
                        </el-option>
                    </el-select>
                    <p class="border_"></p>
                    <el-form-item label="" v-if="form.gType === 14">
                        <el-select v-model="form.company" placeholder="请选择名称">
                            <el-option
                                v-for="item in companies"
                                :key="item.id"
                                :label="item.fullName"
                                :value="item.id">
                            </el-option>
                        </el-select>
                    </el-form-item>
                    <div v-for="(item,index) in types" :key="index">
                        <el-form-item :label="item.name" :value="item.id">
                            <!-- <el-checkbox v-model="item.status" :true-label="1" :false-label="0" :checked="item.status ===
                           1">可见
                            </el-checkbox>
                            <el-radio v-model="item.readonly" :label="1">只读</el-radio>
                            <el-radio v-model="item.readonly" :label="0">可修改</el-radio> -->
                            <div v-for="(item1,ind) in item.childs" :key="ind">
                                <el-form-item :label="item1.name" :value="item1.id">
                                    <el-checkbox v-model="item1.status" :true-label="1" :false-label="0"
                                                 :checked="item1.status === 1">可见
                                    </el-checkbox>
                                    <el-radio v-model="item1.readonly" :label="1">只读</el-radio>
                                    <el-radio v-model="item1.readonly" :label="0">可修改</el-radio>
                                </el-form-item>
                            </div>
                        </el-form-item>
                    </div>
                </el-form-item>
                <el-form-item label="* 账户类型:" v-if="this.userTypes == 12" >
                  <el-input  style="width:80%"  v-model="form.groupName" disabled></el-input>
                    <!-- <el-select v-model="form.type" placeholder="请选择" @change="getTypes" v-if="form.type !=16">
                       <el-option
                            label="代理商运营"
                            value="14">
                        </el-option>
                    </el-select> -->
                    <p class="border_"></p>
                    <div v-for="(item,index) in types" :key="index">
                        <el-form-item :label="item.name" :value="item.id">
                            <br>
                            <div v-for="(item1,ind) in item.childs" :key="ind">
                                <el-form-item :label="item1.name">
                                    <el-checkbox v-model="item1.status" :true-label="1" :false-label="0">可见</el-checkbox>
                                    <el-radio v-model="item1.readonly" :label="1">只读</el-radio>
                                    <el-radio v-model="item1.readonly" :label="0">可修改</el-radio>
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
                <el-button @click="editVisible = false">取 消</el-button>
                <el-button type="primary" @click="saveEdit">确 定</el-button>
            </span>
        </el-dialog>

        <!-- 新建弹出框 -->
        <el-dialog title="新建账户" :visible.sync="newVisible" width="45%">
            <el-form ref="form" :model="form" :rules="rules2" status-icon label-width="100px" class="demo-ruleForm" :label-position="labelPosition">
                <el-form-item label="用户名:" prop="userName">
                    <el-input v-model="form.userName" placeholder="请输入用户名" style="width:80%"></el-input>
                </el-form-item>
                <el-form-item label="账户密码:" prop="password">
                    <el-input v-model="form.password" type="password"  placeholder="请设置登录密码" style="width:80%"></el-input>
                </el-form-item>
                <el-form-item label="密码确认:" prop="checkPass">
                    <el-input v-model="form.checkPass" type="password"  placeholder="请再一次输入登录密码" style="width:80%"></el-input>
                </el-form-item>
                <el-form-item label="* 账户类型:" v-if="this.userTypes != 12" >
                    <el-select v-model="form.type" placeholder="请选择" @change="getTypes">
                        <el-option
                            v-for="item in groups"
                            :key="item.id"
                            :label="item.name"
                            :value="item.id">
                        </el-option>
                    </el-select>
                    <p class="border_"></p>
                    <el-form-item label="" v-if="form.gType === 14">
                        <el-select v-model="form.company" placeholder="请选择代理商">
                            <el-option
                                v-for="item in companies"
                                :key="item.id"
                                :label="item.fullName"
                                :value="item.id">
                            </el-option>
                        </el-select>
                    </el-form-item>
                    <div v-for="(item,index) in types" :key="index">
                        <el-form-item :label="item.name" :value="item.id">
                            <br>
                            <div v-for="(item1,ind) in item.childs" :key="ind">
                                <el-form-item :label="item1.name">
                                    <el-checkbox v-model="item1.status" :true-label="1" :false-label="0">可见</el-checkbox>
                                    <el-radio v-model="item1.readonly" :label="1">只读</el-radio>
                                    <el-radio v-model="item1.readonly" :label="0">可修改</el-radio>
                                </el-form-item>
                            </div>
                        </el-form-item>
                    </div>
                </el-form-item>
                <el-form-item label="* 账户类型:" v-if="this.userTypes == 12" >
                    <el-select v-model="form.type" placeholder="请选择" @change="getTypes">
                       <el-option
                            label="代理商运营"
                            value="14">
                        </el-option>
                    </el-select>
                    <p class="border_"></p>
                    <div v-for="(item,index) in types" :key="index">
                        <el-form-item :label="item.name" :value="item.id">
                            <br>
                            <div v-for="(item1,ind) in item.childs" :key="ind">
                                <el-form-item :label="item1.name">
                                    <el-checkbox v-model="item1.status" :true-label="1" :false-label="0">可见</el-checkbox>
                                    <el-radio v-model="item1.readonly" :label="1">只读</el-radio>
                                    <el-radio v-model="item1.readonly" :label="0">可修改</el-radio>
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
    </div>
</template>

<script>
    import {UserList} from "@/api/Api.js";
    import {UserAdd} from "@/api/Api.js";
    import {UserUpdate} from "@/api/Api.js";
    import {UserDelete} from "@/api/Api.js";
    import {authgetAuth} from "@/api/Api.js";
    import {GetallFlowsource, grouplistForAdd, userget, AgentList} from "@/api/Api.js";

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
                labelPosition: 'left',
                userTypes:'',
                url: "./static/vuetable.json",
                tableData: [],
                cur_page: 1,
                total: 1,
                ps: 10,
                select_word: "",
                is_search: false,
                editVisible: false,
                delVisible: false,
                newVisible: false,
                template_dialog: false,
                currentRow: null,
                form: {
                    groupName:'',
                    id: '',
                    name: "",
                    date: "",
                    userName: '',
                    password: '',
                    checkPass: '',
                    linkMan: '',
                    phone: '',
                    remark: '',
                    status: 1,
                    readonly: 1,
                    type: '',
                    gType: '',
                    aid: '',
                    authsList: [],
                    data_view: '2',
                    flow_manage: '3',
                    user_manage: '3',
                    company:'',
                    finance_manage: '3',
                    data_report: '2',
                    DMP_: '3',
                    mediaName: '',
                },
                rules2: {
                    userName: [
                        {required: true, message: '请输入用户名', trigger: 'blur'},
                    ],
                    password: [
                        {required: true, message: '请输入密码', trigger: 'blur'},
                    ],
                    checkPass: [
                        {validator: validatePass2, message: '两次输入不一致', trigger: 'blur'}
                    ],
                },
                idx: -1,
                types: null,
                typesChild: null,
                flow_management: null,
                data_report: null,
                groups: [
                    {
                        value: '',
                        label: ''
                    }
                ],
                companies: [],
                value: "1",
                value2: '',
                value3: '',

            };
        },
        created() {
            this.readonly = this.$store.state.map.get(this.$store.state.currentPath);
            this.getList();
            this.getName();
            this.cookie();
            console.log(this.userTypes)
        },
        methods: {
            cookie(){
                this.userTypes = localStorage.getItem("type")
            },
            // 开关
            changeStatus(val, row) {
                let params = {
                    id: row.id,
                    status: row.status,
                }
                UserUpdate(params).then(res => {
                  let data = res.data;
                    if (data.code != 'A000000') {
                        this.$message.error(data.message);
                    }
                    // this.getList();
                });
            },
            getTypes(value) {
                for (let i of this.groups) {
                    if (i.id == value) {
                        this.form.gType = i.type;
                    }
                }
                if (this.form.gType == 14) {
                    AgentList({type:1}).then(res => {
                      let data = res.data;
                        this.companies = data.data.data
                    });
                }
                let params = {gid: value}
                authgetAuth(params).then(res => {
                  let data = res.data;
                    if (data.code != 'A000000') {
                        this.$message.error(res.message);
                    }
                    this.types = [];
                    this.types = data.data;
                });0
            },
            //模糊名字
            querySearchName(queryString, cb) {
                let params = {
                    userName: this.select_word,
                    currentPage: this.cur_page,
                    pageSize: this.ps
                }
                UserList(params).then(res => {
                  let data = res.data;
                    // 调用 callback 返回建议列表的数据
                    cb(data.data.data);
                    if (data.code != 'A000000') {
                        this.$message.error(data.message);
                    }
                });
            },
            handleSelectName(item) {
                this.id = item.id
            },
            // 分页导航
            handleCurrentChange(val) {
                this.cur_page = val;
                   let params = {
                    userName: this.select_word,
                    cp:this.cur_page,
                    ps:this.ps
                    }
                UserList(params).then(res => {
                  let data = res.data;
                    this.tableData = data.data.data
                    this.total = data.data.totalItemNum;
                });
            },
            // 获取 table
            getList() {
                let params = {
                    cp: this.cur_page,
                    ps: this.ps
                }
                UserList(params).then(res => {
                  let data = res.data;
                    if (data.code != 'A000000') {
                        this.$message.error(data.message);
                        return;
                    }
                    this.tableData = data.data.data
                    this.total = data.data.totalItemNum;
                });
            },
            // 选择账户类型
            getName() {
                grouplistForAdd().then(res => {
                  let data = res.data;
                    this.groups = data.data
                });
            },
            // 搜索
            search() {
                this.cur_page =1 
                this.$refs.pagination.lastEmittedPage = 1
                let params = {
                    userName: this.select_word,
                    cp:this.cur_page,
                    ps:this.ps
                    }
                UserList(params).then(res => {
                  let data = res.data;
                    this.tableData = data.data.data
                    this.total = data.data.totalItemNum;
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
                let params = {
                    id: row.id
                };
                userget(params).then(res => {
                  let data = res.data;
                    this.types = data.data.auths;
                    this.form.id = data.data.id;
                    this.form.userName = data.data.userName;
                    this.form.type = data.data.type;
                    this.form.linkMan = data.data.realName;
                    this.form.phone = data.data.phone;
                    this.form.remark = data.data.remark;
                    this.form.status = data.data.status;
                    this.form.gType = data.data.groupType;
                    // this.form.password = data.data.password;
                    this.form.groupName = data.data.groupName
                    if (this.form.gType == 14) {
                        AgentList({type:1}).then(res => {
                          let data = res.data;
                            this.companies = data.data.data
                        });
                    }
                    this.form.company = data.data.company;
                });
                this.editVisible = true;
            },
            // 保存编辑
            saveEdit() {
                let params = {
                    id: this.form.id,
                    type: this.form.type,
                    company: this.form.company,
                    status: this.form.status,
                    password: this.form.password,
                    realName: this.form.linkMan,
                    phone: this.form.phone,
                    remark: this.form.remark,
                    auths: this.types
                }

                UserUpdate(params).then(res => {
                  let data = res.data;
                    if (data.code != 'A000000') {
                        this.$message.error(data.message);
                    }
                    this.search();
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
                let params = {id: this.row.id}
                UserDelete(params).then(res => {
                  let data = res.data;
                    if (data.code != 'A000000') {
                        this.$message.error(data.message);
                        return
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
                this.form = {};
                this.types = null;
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
                if (!this.form.type && this.userTypes != 12) {
                    this.$message.error("用户类型不能为空");
                    return;
                }
                if(this.userTypes == 9){
                    if (this.form.type == 14 && !this.form.company) {                   
                        this.$message.error("代理商不能为空");
                        return;
                    }
                }
               
                let params = {
                    userName: this.form.userName,
                    password: this.form.password,
                    company: this.form.company,
                    type: this.form.type,
                    realName: this.form.linkMan,
                    phone: this.form.phone,
                    aid: this.form.aid,
                    remark: this.form.remark,
                    auths: this.types
                }

                UserAdd(params).then(res => {
                  let data = res.data;
                    if (data.code != 'A000000') {
                        this.$message.error(data.message);
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

    .del-dialog-cnt {
        font-size: 16px;
        text-align: center;
    }

    .border_ {
        padding-top: 10px;
    }
</style>




