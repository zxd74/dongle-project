<template>
    <div class="table">
        <div class="container">
            <div class="handle-box">
                <el-autocomplete
                    class="inline-input"
                    v-model="select_word"
                    value-key="name"
                    :fetch-suggestions="querySearchName"
                    placeholder="请输入用户组名称"
                    @select="handleSelectName"
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
                <el-button type="primary" icon="search" @click="create">创建用户组</el-button>
            </div>
            <el-table :data="tableData" border style="width: 100%" ref="multipleTable">
                <el-table-column prop="id" label="ID" align="center"></el-table-column>
                <el-table-column prop="name" label="用户组名称" align="center"></el-table-column>
                <el-table-column prop="personNum" label="启用用户数" align="center"></el-table-column>
                <el-table-column label="状态" align="center">
                    <template slot-scope="scope" >
                        <el-switch
                           :disabled="scope.row.id <= 16?true:false"
                            v-model="scope.row.status"
                            :active-value="1"
                            :inactive-value="0"
                            @change="changeStatus($event,scope.row)"
                        ></el-switch>
                    </template>
                </el-table-column>
                <el-table-column label="操作" align="center">
                    <template slot-scope="scope">
                        <el-button size="small" type="success" @click="handleEdit(scope.$index, scope.row)">编辑
                        </el-button>
                        <!-- <el-button size="small" type="danger" @click="handleDelete(scope.$index, scope.row)">删除</el-button> -->
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
            <el-form
                ref="editform"
                :model="form"
                :rules="rules2"
                status-icon
                label-width="100px"
                class="demo-ruleForm"
                :label-position="labelPosition"
            >
                <el-form-item label="用户组名称:">
                    <el-input v-model="form.name" disabled placeholder="请输入用户名" style="width:80%"></el-input>
                </el-form-item>
                <el-form-item label="账户类型:">
                    <el-input  style="width:80%"  v-model="form.typeName" disabled v-if="form.type == 12 || form.type ==16"></el-input>
                    <el-select v-model="form.type" placeholder="请选择"  v-if="form.type != 12 && form.type !=16">
                        <el-option
                            v-for="item in groupTypes"
                            :key="item.id"
                            :label="item.name"
                            :value="item.id"
                        ></el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="用户组默认权限:">
                    <div v-for="(item,index) in types" :key="index">
                        <el-form-item :label="item.name" :value="item.id">
                            <!-- <el-checkbox
                              v-model="item.status"
                              :true-label="1"
                              :false-label="0"
                              :checked="item.status == 1"
                              @change="refresh"
                            >启用</el-checkbox> -->
                            <br>
                            <div v-for="(item1,ind) in item.childs" :key="ind">
                                <el-form-item :label="item1.name" :value="item1.id">
                                    <el-checkbox
                                        v-model="item1.status"
                                        :true-label="1"
                                        :false-label="0"
                                        :checked="item1.status == 1"
                                        @change="refresh"
                                    >启用
                                    </el-checkbox>
                                </el-form-item>
                            </div>
                        </el-form-item>
                    </div>
                </el-form-item>
            </el-form>
            <span slot="footer" class="dialog-footer">
        <el-button @click="editVisible = false">取 消</el-button>
        <el-button type="primary" @click="saveEdit">确 定</el-button>
      </span>
        </el-dialog>

        <!-- 新建弹出框 -->
        <el-dialog title="新建" :visible.sync="newVisible" width="45%">
            <el-form
                ref="form"
                :model="form"
                :rules="rules2"
                status-icon
                label-width="100px"
                class="demo-ruleForm"
                :label-position="labelPosition"
            >
                <el-form-item label="* 用户组名称:">
                    <el-input v-model="form.name" placeholder="请输入用户名" style="width:80%"></el-input>
                </el-form-item>
                <el-form-item label="* 账户类型:">
                    <el-select v-model="form.type" placeholder="请选择">
                        <el-option
                            v-for="item in groupTypes"
                            :key="item.id"
                            :label="item.name"
                            :value="item.id"
                        ></el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="用户组权限:">
                    <div v-for="(item,index) in types" :key="index">
                        <el-form-item :label="item.name">
                            <!-- <el-checkbox v-model="item.status" :true-label="1" :false-label="0">启用</el-checkbox> -->
                            <br>
                            <div v-for="(item1,ind) in item.childs" :key="ind">
                                <el-form-item :label="item1.name">
                                    <el-checkbox v-model="item1.status" :true-label="1" :false-label="0">启用
                                    </el-checkbox>
                                </el-form-item>
                            </div>
                        </el-form-item>
                    </div>
                </el-form-item>
            </el-form>
            <span slot="footer" class="dialog-footer">
        <el-button @click="newVisible = false">取 消</el-button>
        <el-button type="primary" @click="saveNews">确 定</el-button>
      </span>
        </el-dialog>
    </div>
</template>

<script>
    import {grouplist} from "@/api/Api.js";
    import {groupadd, groupupdate, groupget} from "@/api/Api.js";
    import {UserUpdate} from "@/api/Api.js";
    import {UserDelete} from "@/api/Api.js";
    import {UserGetAuth} from "@/api/Api.js";
    import {authgetAllAuth} from "@/api/Api.js";

    export default {
        name: "basetable",
        data() {
            var validatePass2 = (rule, value, callback) => {
                if (value === "") {
                    callback(new Error("请再次输入密码"));
                } else if (value !== this.form.password) {
                    callback(new Error("两次输入密码不一致!"));
                } else {
                    callback();
                }
            };
            return {
                labelPosition: 'left',
                cussterm:'客户',
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
                    typeName:'',
                    id: "",
                    name: "",
                    date: "",
                    userName: "",
                    password: "",
                    checkPass: "",
                    linkMan: "",
                    phone: "",
                    remark: "",
                    status: 1,
                    readonly: 1,
                    type: "",
                    aid: "",
                    authList: [],
                    data_view: "2",
                    flow_manage: "3",
                    user_manage: "3",
                    KH_manage: "3",
                    factor_manage: "3",
                    AD_manage: "3",
                    SH_manage: "3",
                    finance_manage: "3",
                    data_report: "2",
                    DMP_: "3",
                    mediaName: ""
                },
                rules2: {
                    userName: [
                        {required: true, message: "请输入用户名", trigger: "blur"}
                    ],
                    password: [
                        {required: true, message: "请输入用户名", trigger: "blur"}
                    ],
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
                groupTypes: [],
                typesChild: null,
                flow_management: null,
                data_report: null,
                options: [
                    {
                        value: 1,
                        label: "启用"
                    },
                    {
                        value: 0,
                        label: "停用"
                    }
                ],
                options_: [
                    {
                        value: '',
                        label: "全部状态"
                    },
                    {
                        value: 1,
                        label: "启用"
                    },
                    {
                        value: 0,
                        label: "停用"
                    }
                ],
                value: "",
                value2: "",
                value3: ""
            };
        },
        created() {
            this.getList();

            this.getGroupTypes();
        },

        methods: {
            refresh() {
                this.form = Object.assign({}, this.form);
            },
            getGroupTypes() {
                this.groupTypes = [
                    {id: 9, name: "管理员"},
                    {id: 10, name: "流量管理员"},
                    {id: 14, name: "代理商运营"},
                    {id: 13, name: "直客运营"},
                    {id: 15, name: "审核员"}
                ];
            },
            // 开关
            changeStatus(val, row) {
                let params = {
                    id: row.id,
                    status: row.status
                };
                groupupdate(params).then(res => {
                    let data = res.data;
                    if (data.code != "A000000") {
                        this.$message.error(data.message);
                    }
                    // this.search();
                });
            },
            // 分页导航
            handleCurrentChange(val) {
                this.cur_page = val;
                   let params = {
                    name: this.select_word,
                    status: this.value,
                    cp:this.cur_page,
                    ps:this.ps
                };
                grouplist(params).then(res => {
                    let data = res.data;
                    this.tableData = data.data.data;
                    this.total = data.data.totalItemNum;
                });
            },
            // 获取 table
            getList() {
                let params = {
                    cp: this.cur_page,
                    ps: this.ps
                };
                grouplist(params).then(res => {
                    // let data = res.data;
                    let data = res.data;
                    this.tableData = data.data.data;
                    this.total = data.data.totalItemNum;
                });
            },
            // 搜索
            search() {
                  this.cur_page =1 
                  this.$refs.pagination.lastEmittedPage = 1
                let params = {
                    name: this.select_word,
                    status: this.value,
                    cp:this.cur_page,
                    ps:this.ps
                };
                grouplist(params).then(res => {
                    let data = res.data;
                    this.tableData = data.data.data;
                    this.total = data.data.totalItemNum;
                });
            },
            //模糊搜索
            querySearchName(queryString, cb) {
                let params = {
                    name: this.select_word,
                    cp: this.cur_page,
                    ps: this.ps
                };
                grouplist(params).then(res => {
                    let data = res.data;
                    // 调用 callback 返回建议列表的数据
                    cb(data.data.data);
                    if (data.code != "A000000") {
                        this.$message.error(data.message);
                    }
                });
            },
            handleSelectName(item) {
                this.id = item.id;
            },
            // 编辑
            handleEdit(index, row) {
                // this.form = Object.assign({}, this.form);
                this.idx = index;
                let item = row;
                groupget({id: item.id}).then(res => {
                    let data = res.data;
                    if (data.code == "A000000") {
                        this.form.id = data.data.id,
                            this.form.name = data.data.name,
                            this.form.type = data.data.type,
                            this.types = data.data.authList,
                            this.form.typeName = data.data.typeName,
                        console.log(data.data.typeName)
                        this.editVisible = true;
                    }
                });

            },
            // 保存编辑
            saveEdit() {
                let params = {
                    id: this.form.id,
                    name: this.form.name,
                    type: this.form.type,
                    authList: this.types
                };
                groupupdate(params).then(res => {
                    let data = res.data;
                    if (data.code != "A000000") {
                        this.$message.error(data.message);
                    }
                    this.search();
                });
                this.editVisible = false;
                this.$message.success(`修改成功`);
            },
            // 新建
            create() {
                this.newVisible = true;
                this.types = null;
                this.form = {};
                authgetAllAuth().then(res => {
                    let data = res.data;
                    if (data.code != "A000000") {
                        this.$message.error(data.message);
                    }
                    this.types = data.data;
                });
            },
            saveNews() {
                // 参数判断
                if (!this.form.name) {
                    this.$message.error("用户组不能为空");
                    return;
                }
                if (!this.form.type) {
                    this.$message.error("账户类型不能为空");
                    return;
                }

                let params = {
                    name: this.form.name,
                    type: this.form.type,
                    authList: this.types
                };
                groupadd(params).then(res => {
                    let data = res.data;
                    if (data.code != "A000000") {
                        this.$message.error(data.message);
                    }
                    // 查询接口
                    this.newVisible = false;
                    this.getList();
                });
            }
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

    .border_ {
        padding-top: 10px;
    }
</style>



