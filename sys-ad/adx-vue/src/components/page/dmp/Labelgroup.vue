<template>
    <div class="table">
        <div class="container">
            <div class="handle-box">
                <el-input v-model="select_word" placeholder="请输入标签组组名称进行搜索" class="handle-input mr10"></el-input>
                <el-select v-model="options1" placeholder="状态">
                    <el-option
                        v-for="item in options"
                        :key="item.value"
                        :label="item.label"
                        :value="item.value">
                    </el-option>
                </el-select>
                <el-button type="primary" icon="search" @click="search">搜索</el-button>
                <el-button type="primary" icon="search" @click="create" v-if="this.readonly !=1">新建</el-button>
            </div>
            <el-table :data="tableData" border style="width: 100%" ref="multipleTable">
                <el-table-column prop="id" label="序号" align="center">
                </el-table-column>
                <el-table-column prop="name" label="标签组名称" align="center">
                </el-table-column>
                <el-table-column prop="tags" label="所含标签" align="center">
                </el-table-column>
                <el-table-column label="状态" width="" align="center" v-if="this.readonly !=1">
                    <template slot-scope="scope">
                        <el-switch v-model="scope.row.status" :active-value=1 :inactive-value=0
                                   @change="changeStatus($event,scope.row)"></el-switch>
                    </template>
                </el-table-column>
                <el-table-column label="同步投放定向" width="" align="center" v-if="this.readonly !=1">
                    <template slot-scope="scope">
                        <el-switch v-model="scope.row.isDx" :active-value=1 :inactive-value=0
                                   @change="changeisDx($event,scope.row)"></el-switch>
                    </template>
                </el-table-column>
                <el-table-column label="操作" align="center">
                    <template slot-scope="scope">
                        <el-button size="small" type="success" @click="handleEdit(scope.$index, scope.row)">编辑
                        </el-button>
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
        <el-dialog title="编辑" :visible.sync="editVisible" width="40%">
            <el-form ref="form" :model="form" status-icon label-width="100px">
                <el-form-item label="标签组名称:">
                    <el-input v-model="form.name" placeholder="" style="width:40%"></el-input>
                </el-form-item>
                <el-form-item label="标签:">
                    <div class="add_HY">
                        <el-input v-model="state1" style="width:40%"></el-input>
                        <el-button @click="Add_label" class="btn">添 加</el-button>
                    </div>
                </el-form-item>
                <el-form-item>
                    <div class="div">
                        <el-tag
                            :key="tag.id"
                            v-for="tag in dynamicTags"
                            closable
                            :disable-transitions="false"
                            @close="handleClose(tag)">
                            {{tag.name}}
                        </el-tag>
                    </div>
                </el-form-item>
                <!-- <el-form-item label="是否定向:">
                    <el-radio v-model="form.isDx" :label="1">是</el-radio>
                    <el-radio v-model="form.isDx" :label="0">否</el-radio>
                </el-form-item> -->
                <!-- <el-form-item label="定向字段名:">
                     <el-input v-model="form.dxKey" placeholder="" style="width:40%"></el-input>
                </el-form-item> -->
            </el-form>
            <span slot="footer" class="dialog-footer">
                <el-button @click="editVisible = false">取 消</el-button>
                <el-button type="primary" @click="saveEdit" v-if="this.readonly !=1">确 定</el-button>
            </span>
        </el-dialog>

        <!-- 新建弹出框 -->
        <el-dialog title="新建" :visible.sync="newVisible" width="45%">
            <el-form ref="form" :model="form" status-icon label-width="100px">
                <el-form-item label="* 标签组名称:">
                    <el-input v-model="form.name" placeholder="" style="width:40%"></el-input>
                </el-form-item>
                <el-form-item label="* 标签">
                    <div class="add_HY">
                        <el-input v-model="state1" style="width:40%"></el-input>
                        <el-button @click="Add_label" class="btn">添 加</el-button>
                    </div>
                </el-form-item>
                <el-form-item>
                    <div class="div">
                        <el-tag
                            :key="tag.id"
                            v-for="tag in dynamicTags"
                            closable
                            :disable-transitions="false"
                            @close="handleClose1(tag)">
                            {{tag.name}}
                        </el-tag>
                    </div>
                </el-form-item>
                <!-- <el-form-item label="是否定向:">
                    <el-radio v-model="form.isDx" :label="1">是</el-radio>
                    <el-radio v-model="form.isDx" :label="0">否</el-radio>
                </el-form-item> -->
                <!-- <el-form-item label="定向字段名:">
                     <el-input v-model="form.dxKey" placeholder="" style="width:40%"></el-input>
                </el-form-item> -->
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
    import {tagslist, tagsaddTag, tagsadd, tagsupdateTag, tagsupdate} from '@/api/Api.js';

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
                state1: '',
                dynamicTags: [{name: '', id: ''}],
                is_search: false,
                editVisible: false,
                delVisible: false,
                newVisible: false,
                newaddAD: false,
                template_dialog: false,
                currentRow: null,
                add_time: "",
                form: {
                    id: '',
                    name: "",
                    label_name: '',
                    date: "",
                    switch: false,
                    add_time: "",
                    data_name: '',
                    data_supplier: '1',
                    data_describe: '',
                    data_Url: [{url: ''}],
                    delimiter: '1',
                    zidingyi: '',
                    isDx: '',
                    dxKey: '',
                },

                idx: -1,
                type: 1,
                types: null,
                flow_management: null,
                data_report: null,
                options1: '',
                options: [
                    {
                        value: "1",
                        label: "有效"
                    },
                    {
                        value: "0",
                        label: "无效"
                    },
                ],
                value: "1",
                readonly:''

            };
        },
        created() {
            this.readonly = this.$store.state.map.get(this.$store.state.currentPath);
            this.getData();
        },

        methods: {

            changeStatus(val, row) {
                let params = {
                    id: row.id,
                    status: row.status
                };
                tagsupdate(params).then(res => {
                    let data = res.data;
                    if (data.code != 'A000000') {
                        this.$message.error(data.message);
                    }
                    // this.getData();
                });
            },
            changeisDx(val, row) {
                let params = {
                    id: row.id,
                    isDx: row.isDx
                };
                tagsupdate(params).then(res => {
                    let data = res.data;
                    if (data.code != 'A000000') {
                        this.$message.error(data.message);
                    }
                    // this.getData();
                });
            },
            // 分页导航
            handleCurrentChange(val) {
                this.cur_page = val;
                 let params = {
                    name: this.select_word,
                    status: this.options1,
                    cp: this.cur_page,
                    ps: this.ps
                };
                tagslist(params).then(res => {
                    let data = res.data;
                    console.log(res)
                    if (data.code != 'A000000') {
                        this.$message.error(data.message);
                    }
                    this.tableData = data.data.data;
                    console.log(this.tableData)
                    this.total = data.data.totalItemNum;
                });
            },
            //初始列表
            getData() {
                let params = {
                    cp: this.cur_page,
                    ps: this.ps
                };
                tagslist(params).then(res => {
                    let data = res.data;
                    console.log(res)
                    if (data.code != 'A000000') {
                        this.$message.error(data.message);
                    }
                    this.tableData = data.data.data;
                    // this.dynamicTags = data.data.data.tagList
                    this.total = data.data.totalItemNum;
                });
            },

            // 添加标签
            Add_label() {
                let obj = {name: this.state1};
                this.dynamicTags.push(obj)
                console.log(this.dynamicTags)
            },
            // 删除标签
            handleClose(tag) {
                console.log(tag)
                let params = {
                    id: tag.id,
                }
                console.log(params)
                tagsupdateTag(params).then(res => {
                    let data = res.data;
                    console.log(res)
                    if (data.code != 'A000000') {
                        this.$message.error(data.message);
                    }
                    this.$message.success('删除成功');
                    // this.handleEdit();
                    // this.getData();
                    this.saveEdit();
                });
            },
            handleClose1(tag){
                this.dynamicTags.splice(this.dynamicTags.indexOf(tag), 1);
            },
            // 搜索
            search() {
                this.$refs.pagination.lastEmittedPage = 1
                this.cur_page = 1;
                let params = {
                    name: this.select_word,
                    status: this.options1,
                    cp: this.cur_page,
                    ps: this.ps
                };
                tagslist(params).then(res => {
                    let data = res.data;
                    console.log(res)
                    if (data.code != 'A000000') {
                        this.$message.error(data.message);
                    }
                    this.tableData = data.data.data;
                    console.log(this.tableData)
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
                this.form.name = row.name;
                this.form.id = row.id
                this.dynamicTags = row.tagList;
                this.state1 = "";
                this.editVisible = true;
            },
            // 保存编辑
            saveEdit() {
                let params = {
                    id: this.form.id,
                    name: this.form.name,
                    tagList: this.dynamicTags,
                    name:this.form.name
                }
                tagsupdate(params).then(res => {
                    let data = res.data;
                    console.log(res)
                    if (data.code != 'A000000') {
                        this.$message.error(data.message);
                    }
                    this.getData();
                    this.editVisible = false;
                });

            },
            handleDelete(index, row) {
                this.idx = index;
                this.delVisible = true;
            },

            // 新建
            create() {
                this.newVisible = true;
                this.form = {};
                this.dynamicTags = [];
                this.state1 = "";
            },
            saveNews() {
                let params = {
                    name: this.form.name,
                    tagList: this.dynamicTags
                }
                tagsadd(params).then(res => {
                    let data = res.data;
                    if (data.code != 'A000000') {
                        this.$message.error(data.message);
                        return;
                    }
                    this.getData();
                    this.newVisible = false;
                });
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

    .add_HY {
        display: flex;
        /* padding-top: 20px */
    }

</style>




