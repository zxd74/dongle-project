<template>
    <div class="table">
        <div class="container">
            <div class="handle-box">
                <el-input v-model="select_word" placeholder="请输人群名称进行搜索" class="handle-input mr10"></el-input>
                <el-button type="primary" icon="search" @click="search">搜索</el-button>
                <el-button type="primary" icon="search" @click="create" v-if="this.readonly !=1">新建</el-button>
            </div>
            <el-table :data="tableData" border style="width: 100%" ref="multipleTable">
                <el-table-column prop="id" label="序号" align="center">
                </el-table-column>
                <el-table-column prop="name" label="人群名称" align="center">
                </el-table-column>
                <!-- <el-table-column prop="type" label="生成方式" align="center">
                </el-table-column> -->
                <el-table-column prop="remark" label="人群描述" align="center">
                </el-table-column>
                <el-table-column prop="num" label="覆盖人数" align="center">
                </el-table-column>
                <el-table-column label="操作" width="200" align="center">
                    <template slot-scope="scope">
                        <el-button size="small" type="success" @click="handleEdit(scope.$index, scope.row)">编辑
                        </el-button>
                        <el-button size="small" type="success" @click="handlebreak(scope.$index, scope.row)">查看画像
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
                <el-form-item label="人群名称:">
                    <el-input v-model="form.name" placeholder="" style="width:40%"></el-input>
                </el-form-item>
                <el-form-item label="人群描述:">
                    <el-input v-model="form.remark" placeholder="" style="width:40%"></el-input>
                </el-form-item>
                <el-tag>数据集及标签</el-tag>
                <el-form-item label="数据集">
                    <el-autocomplete
                        class="inline-input"
                        v-model="DateSet"
                        value-key="name"
                        :fetch-suggestions="querySearch"
                        placeholder="请输入数据集"
                        @select="handleSelect"
                    ></el-autocomplete>
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
                <el-form-item label="标签组">
                    <el-form-item label="">
                        <el-select v-model="form.labelGroup" placeholder="请选择标签" @change="GetGroup">
                            <el-option
                                v-for="item in optionsGruop"
                                :key="item.id"
                                :label="item.name"
                                :value="item.id">
                            </el-option>
                        </el-select>
                    </el-form-item>
                    <el-form-item label="">
                        <el-checkbox-group v-model="checkList" @change="getTags">
                            <el-checkbox :label="item.id" v-for="(item,index) in checkbox" :key="index">{{item.name}}
                            </el-checkbox>
                        </el-checkbox-group>
                    </el-form-item>
                    <div class="div">
                        <el-tag
                            :key="tags.id"
                            v-for="tags in labelTags"
                            closable
                            :disable-transitions="false"
                            @close="handleCloseTag(tags)">
                            {{tags.name}}
                        </el-tag>
                    </div>
                </el-form-item>
                <el-form-item label="标签关系:">
                    <el-radio v-model="form.relation" :label="0">交集</el-radio>
                    <el-radio v-model="form.relation" :label="1">并集</el-radio>
                </el-form-item>
            </el-form>
            <span slot="footer" class="dialog-footer">
                <el-button @click="editVisible = false">取 消</el-button>
                <el-button type="primary" @click="saveEdit" v-if="this.readonly !=1">确 定</el-button>
            </span>
        </el-dialog>

        <!-- 新建弹出框 -->
        <el-dialog :visible.sync="newVisible" width="45%" title="">
            <el-form ref="form" :model="form" status-icon label-width="100px">
                <el-tag>基本信息</el-tag>
                <el-form-item label="* 人群名称:">
                    <el-input v-model="form.name" placeholder="" style="width:40%"></el-input>
                </el-form-item>
                <el-form-item label="人群描述:">
                    <el-input v-model="form.remark" placeholder="" style="width:40%"></el-input>
                </el-form-item>
                <el-tag>数据集及标签</el-tag>
                <el-form-item label="* 数据集">
                    <el-autocomplete
                        class="inline-input"
                        v-model="DateSet"
                        value-key="name"
                        :fetch-suggestions="querySearch"
                        placeholder="请输入数据集"
                        @select="handleSelect"
                    ></el-autocomplete>
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
                <el-form-item label="* 标签组">
                    <el-form-item label="">
                        <el-select v-model="form.labelGroup" placeholder="请选择标签" @change="GetGroup">
                            <el-option
                                v-for="item in optionsGruop"
                                :key="item.id"
                                :label="item.name"
                                :value="item.id">
                            </el-option>
                        </el-select>
                    </el-form-item>
                    <el-form-item label="">
                        <el-checkbox-group v-model="checkList" @change="getTags">
                            <el-checkbox :label="item.id" v-for="(item,index) in checkbox" :key="index">{{item.name}}
                            </el-checkbox>
                        </el-checkbox-group>
                    </el-form-item>
                    <div class="div">
                        <el-tag
                            :key="tags.id"
                            v-for="tags in labelTags"
                            closable
                            :disable-transitions="false"
                            @close="handleCloseTag(tags)">
                            {{tags.name}}
                        </el-tag>
                    </div>
                </el-form-item>
                <el-form-item label="* 标签关系:">
                    <el-radio v-model="form.relation" :label="0">交集</el-radio>
                    <el-radio v-model="form.relation" :label="1">并集</el-radio>
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
    import {personslist, dataslist, tagslist, personsadd, personsupdate} from '@/api/Api.js';

    const tagsID = new Map()
    const Names = new Map()
    export default {
        name: "basetable",
        data() {
            return {
                url: "./static/vuetable.json",
                tableData: [],
                cur_page: 1,
                total: 1,
                ps: 10,
                DateSet: '',
                dynamicTags: [],
                dynamicDatas: [],
                checkList: [],
                checkbox: [],
                labelTags: [],
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
                add_time: "",
                form: {
                    id: '',
                    name: "",
                    remark: "",
                    labelGroup: "",
                    relation: '',

                    switch: false,
                    add_time: "",
                    data_name: '',
                    data_supplier: '1',
                    data_describe: '',
                    data_Url: [{url: ''}],
                    add_user: '',
                },
                optionsGruop: [
                    {
                        value: "",
                        label: ""
                    },
                ],
                value: "1",
                list: [],
                tagsSet: new Set(),
                readonly:''
            };
        },
        created() {
            this.readonly = this.$store.state.map.get(this.$store.state.currentPath);
            this.getData();
            this.getLable();
        },

        methods: {
            // 分页导航
            handleCurrentChange(val) {
                this.cur_page = val;
                 let params = {
                    name: this.select_word,
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
            // 模糊搜索数据集
            querySearch(queryString, cb) {
                let params = {
                    name: this.DateSet,
                }
                dataslist(params).then(res => {
                    let data = res.data;
                    cb(data.data.data);
                    if (data.code != 'A000000') {
                        this.$message.error(data.message);
                    }
                });
            },
            // 添加
            handleSelect(item) {
                console.log(item)
                let id = item.id

                let obj = {id: id, name: this.DateSet};
                this.dynamicTags.push(obj)
            },
            // 删除
            handleClose(tag) {
                this.dynamicTags.splice(this.dynamicTags.indexOf(tag), 1);
            },
            // 删除
            handleCloseTag(tag) {
                this.labelTags.splice(this.labelTags.indexOf(tag), 1);
                this.checkList.splice(this.checkList.indexOf(tag.id), 1);
                this.tagsSet.delete(tag.id);
            },
            // 标签
            getLable() {
                let params = {
                    cp: this.cur_page,
                    ps: this.ps
                };
                tagslist(params).then(res => {
                    let data = res.data;
                    if (data.code != 'A000000') {
                        this.$message.error(data.message);
                    }
                    this.optionsGruop = data.data.data;
                    data.data.data.forEach(element => {
                        tagsID.set(element.id, element.tagList);
                    });
                });
            },
            GetGroup() {
                this.checkbox = tagsID.get(this.form.labelGroup)
                this.checkbox.forEach(element => {
                    Names.set(element.id, element.name);
                });

            },
            getTags(checkTags) {
                // let list = [];this.labelTags=[];
                checkTags.forEach(item_ => {
                    if (!this.tagsSet.has(item_)) {
                        this.tagsSet.add(item_);
                        let name = Names.get(item_);
                        let item = {id: item_, name: name}
                        // list.push(item);
                         this.labelTags.push(item);
                    }
                });
                console.log(checkTags)
                // this.tagsSet.forEach(v => {
                //       let name = Names.get(v);
                //       let item = {id: v, name: name}
                //       this.labelTags.push(item)
                // });
                // this.labelTags = this.labelTags.concat(list);
                // console.log()

            },
            handlebreak(index, row) {
                this.item = row
                console.log(row)
                this.$router.push({path: '/dmp/pnhto', query: {id: row.id}})
            },

            search() {
                this.$refs.pagination.lastEmittedPage = 1
                this.cur_page = 1;
                let params = {
                    name: this.select_word,
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


            handleEdit(index, row) {
                this.idx = index;
                let item = row;
                this.form = {
                    id: item.id,
                    name: item.name,
                    remark: item.remark,
                    relation: item.relation,
                };
                this.dynamicTags = item.datasList;
                this.labelTags = item.tagList;
                item.tagList.forEach(element => {
                   Names.set(element.id,element.name)
                });
                this.editVisible = true;
                item.tags.split(',').forEach(item => {
                    this.tagsSet.add(parseInt(item));
                })
            },
            // 保存编辑
            saveEdit() {
                let list = [];
                for (let item of this.dynamicTags) {
                    list.push(item.id);
                }
                let tagList = [];
                for (let item of this.labelTags) {
                    tagList.push(item.id);
                }
                if (list.length == 0) {
                    this.$message.error("请选择数据集");
                    return;
                }
                if (tagList.length == 0) {
                    this.$message.error("请选择标签集");
                    return;
                }
                let params = {
                    id: this.form.id,
                    name: this.form.name,
                    remark: this.form.remark,
                    did: list.join(','),
                    tags: tagList.join(','),
                    relation: this.form.relation,
                }

                personsupdate(JSON.stringify(params)).then(res => {
                    let data = res.data;
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
                this.form = {
                    relation: 0
                };
                this.checkList = []
                this.dynamicTags = []
                this.labelTags = []
            },
            saveNews() {
                if (!this.form.name) {
                    this.$message.error("人群名称不能为空");
                    return;
                }
                // if(!this.form.relation) {
                //   this.$message.error("标签关系不能为空");
                //   return;
                // }
                let didList = [];
                this.dynamicTags.forEach(item_ => {
                    didList.push(item_.id);
                });
                let tagList = [];
                this.labelTags.forEach(item_ => {
                    tagList.push(item_.id);
                });

                if (didList.length == 0) {
                    this.$message.error("请选择数据集");
                    return;
                }
                if (tagList.length == 0) {
                    this.$message.error("请选择标签集");
                    return;
                }
                let params = {
                    name: this.form.name,
                    remark: this.form.remark,
                    did: didList.join(','),
                    tags: tagList.join(','),
                    relation: this.form.relation
                }
                personsadd(params).then(res => {
                    let data = res.data;
                    //  console.log(data.data.data[0].tagList)
                    if (data.code != 'A000000') {
                        this.$message.error(data.message);
                    }
                    this.getData()
                    this.newVisible = false;
                });

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

            template_() {

            },
            data_address() {
                this.form.data_Url.push({url: ""});
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

    .div {
        padding: 20px;
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

    .mr11 {
        width: 50px;
    }
</style>




