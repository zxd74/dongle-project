<template>
    <div class="table">
        <div class="container">
            <div class="handle-box">
                <el-input v-model="select_word" placeholder="请输入账户名称" class="handle-input mr10"></el-input>
                <el-button type="primary" icon="search" @click="search">搜索</el-button>
                <el-button type="primary" icon="search" @click="create"  v-if="this.readonly !=1">新建代理商</el-button>
            </div>
            <el-table :data="tableData" border style="width: 100%" ref="multipleTable">
                <el-table-column prop="id" label="序号" width="50" align="center">
                </el-table-column>
                <el-table-column prop="fullName" label="公司名称" align="center">
                </el-table-column>
                <el-table-column prop="agType" label="类型" align="center" :formatter=formatterType>
                </el-table-column>
                <el-table-column prop="linkMan" label="联系人" align="center">
                </el-table-column>
                <el-table-column prop="phone" label="联系方式" align="center">
                </el-table-column>
                <el-table-column prop="money" label="金额" align="center" width="60">
                </el-table-column>
                <el-table-column label="状态" width="60" align="center">
                    <template slot-scope="scope">
                        <el-switch v-model="scope.row.status" :active-value=1 :inactive-value=0 @change="changeStatus($event,scope.row)"></el-switch>
                    </template>
                </el-table-column>
                <el-table-column label="操作" width="340" align="center">
                    <template slot-scope="scope">
                        <el-button size="small" type="success" @click="Financial_Management(scope.$index, scope.row)">
                            财务管理
                        </el-button>
                        <el-button size="small" type="success" @click="handleEdit(scope.$index, scope.row)">编辑
                        </el-button>
                        <el-button size="small" type="danger" @click="handleDelete(scope.$index, scope.row)"  v-if="this.readonly !=1">删除
                        </el-button>
                        <el-button size="small" type="danger" @click="price_manage(scope.$index, scope.row)"  v-if="this.readonly !=1">底价管理
                        </el-button>
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
            <el-form ref="form" :model="form" :rules="rules2" status-icon label-width="100px">
                <el-tag type="success" size="medium"> 基本信息</el-tag>
                <!-- <el-form-item label="用户名:" prop="userName">
                  <el-input v-model="form.userName" placeholder="请输入用户名" style="width:80%"></el-input>
                </el-form-item> -->
                <!-- <el-form-item label="账户密码:" prop="password">
                  <el-input v-model="form.password" placeholder="请设置登录密码" style="width:80%"></el-input>
                </el-form-item> -->
                <!-- <el-form-item label="密码确认:" prop="checkPass">
                  <el-input v-model="form.checkPass" placeholder="请再一次输入登录密码" style="width:80%"></el-input>
                </el-form-item> -->
                <el-form-item label="公司名称:">
                    <el-input v-model="form.fullName" :disabled="true" placeholder="请输入公司名称"
                              style="width:80%"></el-input>
                </el-form-item>
                <el-form-item label="代理商类型:">
                    <el-radio-group v-model="form.agType" size="medium">
                        <el-radio-button label="1">直客代理商</el-radio-button>
                        <el-radio-button label="2">普通代理商</el-radio-button>
                    </el-radio-group>
                </el-form-item>
                <el-form-item label="联系人:">
                    <el-input v-model="form.linkMan" placeholder="请输入联系人" style="width:80%"></el-input>
                </el-form-item>
                <el-form-item label="联系方式:">
                    <el-input v-model="form.phone" placeholder="请输入联系方式" style="width:80%" type="number"></el-input>
                </el-form-item>
                <el-form-item label="利润率:">
                    <el-input-number v-model="form.profitMargin" size="small" placeholder="请输入利润率" type="number" :min="0"
                              :max="1" :step="0.01" style="width:80%"></el-input-number>
                </el-form-item>
                <el-tag type="success" size="medium"> 资质信息</el-tag>
                <el-form-item label="营业执照:">
                    <el-input v-model="form.businesslicense" style="width:50%"></el-input>
                    <el-upload
                        class="upload-demo"
                        :action="upload"
                        :data="{type:1}"
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
                        :action="upload"
                        :data="{type:6}"
                        name="myFile"
                        :on-success="handleAvatar"
                    >
                        <el-button size="small" type="primary">点击上传</el-button>
                    </el-upload>
                </el-form-item>
            </el-form>
            <span slot="footer" class="dialog-footer">
                <el-button @click="editVisible = false">取 消</el-button>
                <el-button type="primary" @click="saveEdit"  v-if="this.readonly !=1">确 定</el-button>
            </span>
        </el-dialog>

        <!-- 新建弹出框 -->
        <el-dialog title="新建代理商" :visible.sync="newVisible" width="45%">
            <el-form ref="form" :model="form" :rules="rules2" status-icon label-width="100px">
                <el-tag type="success" size="medium"> 基本信息</el-tag>
                <el-form-item label="用户名:" prop="userName">
                    <el-input v-model="form.userName" placeholder="请输入用户名" style="width:80%"></el-input>
                </el-form-item>
                <el-form-item label="账户密码:" prop="password">
                    <el-input v-model="form.password" type="password" placeholder="请设置登录密码"
                              style="width:80%"></el-input>
                </el-form-item>
                <el-form-item label="密码确认:" prop="checkPass">
                    <el-input v-model="form.checkPass" type="password"  placeholder="请再一次输入登录密码" style="width:80%"></el-input>
                </el-form-item>
                <el-form-item label="* 公司名称:">
                    <el-input v-model="form.fullName" placeholder="请输入公司名称" style="width:80%"></el-input>
                </el-form-item>
                <el-form-item label="* 代理商类型:">
                    <el-radio-group v-model="form.agType" size="medium">
                        <el-radio-button label="1">直客代理商</el-radio-button>
                        <el-radio-button label="2">普通代理商</el-radio-button>
                    </el-radio-group>
                </el-form-item>
                <el-form-item label="联系人:">
                    <el-input v-model="form.linkMan" placeholder="请输入联系人" style="width:80%"></el-input>
                </el-form-item>
                <el-form-item label="联系方式:">
                    <el-input v-model="form.phone" placeholder="请输入联系方式" style="width:80%" type="number"></el-input>
                </el-form-item>
                <el-form-item label="* 利润率:">
                    <el-input-number v-model="form.profitMargin" size="small"  placeholder="请输入利润率" :min="0"
                              :max="1" :step="0.01" style="width:80%"></el-input-number>
                </el-form-item>
                <el-tag type="success" size="medium"> 资质信息</el-tag>
                <el-form-item label="* 营业执照:">
                    <el-input v-model="form.businesslicense" style="width:50%"></el-input>
                    <el-upload
                        class="upload-demo"
                        :action="upload"
                        :data="{type:1}"
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
                        :action="upload"
                        :data="{type:6}"
                        name="myFile"
                        :on-success="handleAvatar"
                    >
                        <el-button size="small" type="primary">点击上传</el-button>
                    </el-upload>
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
                <el-button type="primary" @click="deleteRow(index,row)">确 定</el-button>
            </span>
        </el-dialog>

    </div>
</template>

<script>
    import {AgentList} from "@/api/Api.js";
    import {AgentAdd} from "@/api/Api.js";
    import {AgentDelete} from "@/api/Api.js";
    import {AgentUpdate, upload} from "@/api/Api.js";

    export default {
        name: "basetable",
        data() {
            var validatePass2 = (rule, value, callback) => {
                if (value === '') {
                    callback(new Error('请再次输入密码'));
                } else if (value != this.form.password) {
                    callback(new Error('两次输入密码不一致!'));
                } else {
                    callback();
                }
            };
            return {
                upload: upload,
                tableData: [],
                cur_page: 1,
                total: 1,
                ps: 10,
                select_word: "",
                editVisible: false,
                delVisible: false,
                newVisible: false,
                newaddAD: false,
                template_dialog: false,
                Editmima: false,
                currentRow: null,
                form: {
                    id: '',
                    fullName: '',
                    agType: '2',
                    linkMan: '',
                    phone: '',
                    userName: '',
                    password: '',
                    checkPass: '',
                    status: 1,
                    businesslicense: '',
                    qualifications: '',
                    profitMargin: 0
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
                type: 1,
                readonly:''

            };
        },
        created() {
            this.readonly = this.$store.state.map.get(this.$store.state.currentPath);
            this.getList();
        },
        methods: {
               // 状态开关
            changeStatus(val,row){
                let item = row;
                        let params = {
                        id:item.id,
                        status:item.status,
                    }
                AgentUpdate(params).then(res=>{
                let data = res.data;
                    if(data.code != 'A000000') {
                        this.$message.error(data.message);
                    }
                //   console.log(res)
                    // this.getList();
                });
                },
            getList() {
                let params = {type: 1, cp: this.cur_page, ps: this.ps}
                AgentList(params).then(res => {
                    let data = res.data;
                    this.tableData = data.data.data
                });
            },
            // 格式化数据
            formatterType(row, column) {
                if (row.agType == 1) {
                    return "直客代理商";
                } else if (row.agType == 2) {
                    return "普通代理商";
                }
            },

            // 分页导航
            handleCurrentChange(val) {
                this.cur_page = val;
                let params = {fullName: this.select_word, type: 1,cp:this.cur_page,ps:this.ps}
                AgentList(params).then(res => {
                    let data = res.data;
                    this.tableData = data.data.data
                });
            },
            // 搜索
            search() {
                    this.$refs.pagination.lastEmittedPage = 1
                    this.cur_page = 1;
                let params = {fullName: this.select_word, type: 1,cp:this.cur_page,ps:this.ps}
                AgentList(params).then(res => {
                    let data = res.data;
                    this.tableData = data.data.data
                });
            },
            // 编辑
            handleEdit(index, row) {
                this.idx = index;
                let item = row;
                this.form = {
                    id: item.id,
                    fullName: item.fullName,
                    agType: item.agType,
                    linkMan: item.linkMan,
                    phone: item.phone,
                    userName: item.userName,
                    password: item.password,
                    checkPass: item.checkPass,
                    status: item.status,
                    businesslicense: item.businesslicense,
                    qualifications: item.qualifications,
                    profitMargin: item.profitMargin
                };
                this.editVisible = true;
            },
            // 保存编辑
            saveEdit() {
                   // 参数判断
                if (!this.form.businesslicense) {
                    this.$message.error("营业执照不能为空");
                    return;
                }
                let params = {
                    id: this.form.id,
                    // userName:this.form.userName,
                    // password:this.form.password,
                    // fullName:this.form.fullName,
                    agType: this.form.agType,
                    linkMan: this.form.linkMan,
                    phone: this.form.phone,
                    businesslicense: this.form.businesslicense,
                    qualifications: this.form.qualifications,
                    profitMargin: this.form.profitMargin
                }
                AgentUpdate(params).then(res => {
                    let data = res.data;
                    if (data.code != 'A000000') {
                        this.$message.error(data.message);
                        return;
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
                let params = {id: this.row.id}
                AgentDelete(params).then(res => {
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
                // this.tableData.splice(this.idx, 1);
                // this.$message.success("删除成功");
                // this.delVisible = false;
                // this.AgentList();
            },

            // 新建
            create() {
                this.newVisible = true;
                this.form = {
                    agType: '2',
                };
            },
            // 新建确认
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
                if (!this.form.businesslicense) {
                    this.$message.error("营业执照不能为空");
                    return;
                }
                if (!this.form.agType) {
                    this.$message.error("代理商类型不能为空");
                    return;
                }
                if (!this.form.fullName) {
                    this.$message.error("公司名称不能为空");
                    return;
                }
                let params = {
                    type: 1,
                    userName: this.form.userName,
                    password: this.form.password,
                    fullName: this.form.fullName,
                    agType: this.form.agType,
                    linkMan: this.form.linkMan,
                    phone: this.form.phone,
                    businesslicense: this.form.businesslicense,
                    qualifications: this.form.qualifications,
                    profitMargin: this.form.profitMargin
                }
                AgentAdd(params).then(res => {
                    let data = res.data;
                    if (data.code != 'A000000') {
                        this.$message.error(data.message);
                        return;
                    }
                    // 掉查询接口
                    this.newVisible = false;
                    this.getList();
                });
            },
            saveAD() {
                this.newaddAD = false;
            },
            Financial_Management(index, row) {
                this.$router.push({path: '/moneymanagement/agent', query: {id: row.id, fullName: row.fullName}})
            },
            price_manage(index, row) {
                // console.log(row.id)
                this.$router.push({path: '/agenmanagement/agentmanageprice', query: {id: row.id}})
            },

            // 文件上传
            handleAvatarSuccess(res, file) {
                let data = res;
                if (data.code != 'A000000') {
                    this.$message.error(data.message);
                    return;
                } else {
                    this.form.businesslicense = data.data.url
                }
                this.$forceUpdate()
            },
            handleAvatar(res, file) {
                let data = res;
                if (data.code != 'A000000') {
                    this.$message.error(data.message);
                    return;
                } else {
                    this.form.qualifications = data.data.url
                }
                this.$forceUpdate()

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



