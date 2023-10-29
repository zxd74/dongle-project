<template>
    <div class="table">
        <div class="container">
            <div class="handle-box">
                <el-input v-model="select_word" placeholder="请输入数据源名称进行搜索" class="handle-input mr10"></el-input>
                <el-button type="primary" icon="search" @click="search">搜索</el-button>
                <el-button type="primary" icon="search" @click="create" v-if="this.readonly !=1">新建</el-button>
            </div>
            <el-table :data="tableData" border style="width: 100%" ref="multipleTable">
                <el-table-column prop="name" label="数据源名称" align="center">
                </el-table-column>
                <el-table-column prop="type" label="数据供应方" align="center" :formatter=formatterType>
                </el-table-column>
                <el-table-column prop="downStatus" label="下载状态" align="center">
                </el-table-column>
                <el-table-column prop="remark" label="数据源描述" align="center">
                </el-table-column>
                <el-table-column prop="createrName" label="维护人" align="center">
                </el-table-column>
                <el-table-column label="状态" width="" align="center">
                    <template slot-scope="scope">
                        <el-switch v-model="scope.row.status" :active-value=1 :inactive-value=0
                                   @change="changeStatus($event,scope.row)"></el-switch>
                    </template>
                </el-table-column>
                <el-table-column label="操作" width="200" align="center">
                    <template slot-scope="scope">
                        <el-button size="small" type="success" @click="handleEdit(scope.$index, scope.row)">编辑
                        </el-button>
                        <el-button size="small" type="success" @click="handleUpdate(scope.$index, scope.row)">更新数据
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
        <el-dialog title="编辑" :visible.sync="editVisible" width="40%">
            <el-form ref="form" :model="form" status-icon label-width="100px">
                <el-tag>基本信息</el-tag>
                <el-form-item label="数据源名称:">
                    <el-input v-model="form.name" placeholder="请输入数据源名称" style="width:80%"></el-input>
                </el-form-item>
                <el-form-item label="数据供应方:">
                    <el-radio v-model="form.type" :label="1">自有数据</el-radio>
                    <el-radio v-model="form.type" :label="2">外部数据</el-radio>
                </el-form-item>
                <el-form-item label="数据源描述:">
                    <el-input v-model="form.remark" placeholder="请输入数据源名称" style="width:80%"></el-input>
                </el-form-item>
                <el-tag>数据源配置</el-tag>
                <el-form-item label="维护人 :">
                    <el-input v-model="form.createrName" placeholder="请输入销售人员" :disabled="true"
                              style="width:80%"></el-input>
                </el-form-item>
                <el-form-item label="数据路径:">
                    <el-input v-model="form.paths" style="width:80%" disabled></el-input>
                    <div v-for="(item,index) in paths" :key="index">
                        <el-input v-model="paths[index]" style="width:80%" ></el-input>
                    </div>
                    <el-button type="h" icon="el-icon-plus" @click="data_address2"></el-button>
                </el-form-item>
            </el-form>
            <span slot="footer" class="dialog-footer">
                <el-button @click="editVisible = false">取 消</el-button>
                <el-button type="primary" @click="saveEdit" v-if="this.readonly !=1">确 定</el-button>
            </span>
        </el-dialog>

        <!-- 新建弹出框 -->
        <el-dialog :visible.sync="newVisible" width="45%">
            <el-form ref="form" :model="form" status-icon label-width="100px">
                <el-tag>基本信息</el-tag>
                <el-form-item label="* 数据源名称:">
                    <el-input v-model="form.name" placeholder="请输入数据源名称" style="width:80%"></el-input>
                </el-form-item>
                <el-form-item label="* 数据供应方:">
                    <el-radio v-model="form.type" :label="1">自有数据</el-radio>
                    <el-radio v-model="form.type" :label="2">外部数据</el-radio>
                </el-form-item>
                <el-form-item label="数据源描述:">
                    <el-input v-model="form.remark" placeholder="请输入数据源名称" style="width:80%"></el-input>
                </el-form-item>
                <el-tag>数据源配置</el-tag>
                <el-form-item label="* 数据路径:">
                    <div v-for="(item,index) in form.paths" :key="index">
                        <el-input v-model="form.paths[index]" style="width:80%"></el-input>
                    </div>
                    <el-button type="h" icon="el-icon-plus" @click="data_address"></el-button>
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
    import {datasourcelist,datasourceupdate,datasourceadd,datasourcerejudge} from '@/api/Api.js';

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
                is_search: false,
                editVisible: false,
                newVisible: false,
                newaddAD: false,
                template_dialog: false,
                currentRow: null,
                add_time: "",
                        paths: [],
                form: {
                    id:"",
                    name: "",
                    date: "",
                    createrName: "",
                    type: 1,
                    remark: '',
                    paths: [],

                },
                idx: -1,
                value: "1",
                readonly:''
            };
        },
        created() {
            this.readonly = this.$store.state.map.get(this.$store.state.currentPath);
            this.getData();
        },
        methods: {
            // 分页导航
            handleCurrentChange(val) {
                this.cur_page = val;
                 let params = {
                    name: this.select_word,
                    status: this.value,
                }
                datasourcelist(params).then(res => {
                  let data = res.data;
                    if (data.code != 'A000000') {
                        this.$message.error(data.message);
                    }
                    this.tableData = data.data.data;
                    this.total = data.data.totalItemNum;
                });
            },
            // 开关
            changeStatus(val, row) {
                let params = {
                    id: row.id,
                    status: row.status
                };
                datasourceupdate(params).then(res => {
                  let data = res.data;
                    if (data.code != 'A000000') {
                        this.$message.error(data.message);
                    }
                    // this.getData();
                });
            },
            //初始列表
            getData() {
                let params = {
                    cp: this.cur_page,
                    ps: this.ps
                };
                datasourcelist(params).then(res => {
                  let data = res.data;
                    if (data.code != 'A000000') {
                        this.$message.error(data.message);
                    }
                    this.tableData = data.data.data;
                    this.total = data.data.totalItemNum;
                });
            },
            // 搜索
            search() {
                  this.$refs.pagination.lastEmittedPage = 1
                  this.cur_page = 1;
                let params = {
                    name: this.select_word,
                    status: this.value,
                }
                datasourcelist(params).then(res => {
                  let data = res.data;
                    if (data.code != 'A000000') {
                        this.$message.error(data.message);
                    }
                    this.tableData = data.data.data;
                    this.total = data.data.totalItemNum;
                });
            },
            // 格式化数据
            formatterType(row, column) {
                if(row.type == 1) {
                    return "自有数据";
                } else if(row.type == 2){
                    return "外部数据";
                }

            },
            handleEdit(index, row) {
                this.idx = index;
                const item = this.tableData[index];
                console.log(item)
                this.form = {
                    id: item.id,
                    name: item.name,
                    type: item.type,
                    remark: item.remark,
                    createrName: item.createrName,
                    paths: item.paths.split(',')
                };
                this.paths =[],
                this.editVisible = true;
            },
            handleUpdate(index, row) {
                let params = {
                    id : row.id
                };
                datasourcerejudge(params).then(res => {
                    let data = res.data;
                    if (data.code != 'A000000') {
                        this.$message.error(data.message);
                    } else {
                        this.$message.success("正在更新");
                    }
                })
            },
            // 保存编辑
            saveEdit() {
                this.paths.forEach(element => {
                    if(element && element.trim()){
                            this.form.paths.push(element.trim())
                    }
                });
                let param = {
                    id: this.form.id,
                    remark: this.form.remark,
                    type:this.form.type,
                    paths: this.form.paths.join(',')
                };
                datasourceupdate(param).then(res => {
                  let data = res.data;
                    if (data.code != 'A000000') {
                        this.$message.error(data.message);
                    }
                    this.editVisible = false;
                    this.getData();
                });
            },
            saveNews() {
                // 参数判断
                if (!this.form.name) {
                    this.$message.error("名称不能为空");
                    return;
                }
                if (!this.form.type) {
                    this.$message.error("类型不能为空");
                    return;
                }
                if (this.form.paths == []) {
                    this.$message.error("路径不能为空");
                    return;
                }
                let param = {
                    id: this.form.id,
                    name: this.form.name,
                    type: this.form.type,
                    remark: this.form.remark,
                    paths: this.form.paths.join(',')
                };
                datasourceadd(param).then(res => {
                  let data = res.data;
                    if (data.code != 'A000000') {
                        this.$message.error(data.message);
                    }
                    this.newVisible = false;
                    this.getData();
                });
            },
            // 新建
            create() {
                this.newVisible = true;
                this.form.paths = [];
                this.form.name = '';
                this.form.type = '';
                this.form.remark = '';
                this.form.id = '';
            },
            data_address() {
                this.form.paths.push("");
            },
             data_address2() {
                this.paths.push("");
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
</style>
<style>
    /* .el-form-item__label {
    width: 120px !important;
} */
</style>




