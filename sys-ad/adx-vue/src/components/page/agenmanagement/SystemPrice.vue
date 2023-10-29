<template>
    <div class="table">
        <div class="container">
            <div class="handle-box">
                <el-select v-model="form.mediaName1" placeholder="请选择媒体名称">
                    <el-option
                        v-for="item in flowsource"
                        :key="item.id"
                        :label="item.mediaName"
                        :value="item.id">
                    </el-option>
                </el-select>
                <!-- <el-select v-model="terminal" placeholder="终端类型" class="handle-input">
                    <el-option
                        v-for="item in terminalType"
                        :key="item.value"
                        :label="item.label"
                        :value="item.value">
                    </el-option>
                </el-select> -->
                <el-autocomplete
                    class="inline-input"
                    v-model="state1"
                    value-key="name"
                    :fetch-suggestions="querySearch"
                    placeholder="广告位名称"
                    @select="handleSelect"
                ></el-autocomplete>
                <el-select v-model="form.industryType" placeholder="广告主行业" class="handle-input">
                    <el-option
                        v-for="item in options"
                        :key="item.id"
                        :label="item.dicValue"
                        :value="item.id">
                    </el-option>
                </el-select>
                <el-button type="primary" icon="search" @click="search">搜索</el-button>
                <el-button type="primary" icon="search" @click="create" v-if="this.readonly !=1">新建底价</el-button>
            </div>
            <el-table :data="tableData" border style="width: 100%" ref="multipleTable">
                <el-table-column prop="id" label="序号" width="100" align="center">
                </el-table-column>
                <el-table-column prop="mName" label="媒体" width="100" align="center">
                </el-table-column>
                <!--<el-table-column prop="osName" label="终端类型" width="" align="center">-->
                <!--</el-table-column>-->
                <el-table-column prop="pName" label="广告位" width="" align="center">
                </el-table-column>
                <el-table-column prop="industry" label="广告主行业" width="" align="center">
                </el-table-column>
                <el-table-column prop="area" label="地域等级" width="" align="center">
                </el-table-column>
                <el-table-column prop="payTypeName" label="付费方式" width="" align="center">
                </el-table-column>
                <el-table-column prop="price" label="价格" width="" align="center">
                    <template slot-scope="scope">
                        <el-button size="mini" type="text" @click="newPrice(scope.$index, scope.row)">{{scope.row.price}}</el-button>
                    </template>
                </el-table-column>
                <el-table-column prop="profitMargin" label="利润率" width="" align="center">
                    <template slot-scope="scope">
                        <el-button size="mini" type="text" @click="newPrice(scope.$index, scope.row)">
                            {{scope.row.profitMargin}}
                        </el-button>
                    </template>
                </el-table-column>
                <!--<el-table-column prop="price" label="价格" width="" align="center">-->
                <!--</el-table-column>-->
                <!--<el-table-column prop="profitMargin" label="利润率" width="" align="center">-->
                <!--</el-table-column>-->
                <el-table-column label="操作" width="200" align="center" v-if="this.readonly !=1">
                    <template slot-scope="scope">
                        <el-button size="mini" type="success" @click="syncPrice(scope.$index, scope.row)">同步价格
                        </el-button>
                        <el-button size="mini" type="danger" @click="syncProfit(scope.$index, scope.row)">同步利润率
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
        <!-- 新建底价弹出框 -->
        <el-dialog title="新增广告位底价" :visible.sync="newVisible" width="40%">
            <el-form ref="form" :model="form" label-width="100px">
                <el-form-item label="* 媒体:">
                    <el-select v-model="form.mid" placeholder="请选择媒体" @change="getADPosition">
                        <el-option
                            v-for="item in flowsource"
                            :key="item.id"
                            :label="item.mediaName"
                            :value="item.id">
                        </el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="* 广告位:">
                    <el-autocomplete
                        class="inline-input"
                        v-model="form.pidName"
                        value-key="name"
                        :fetch-suggestions="querySearchNew"
                        placeholder="广告位名称"
                        @select="handleSelectNew"
                    ></el-autocomplete>
                </el-form-item>
                <el-form-item label="* 价格:">
                    <el-input-number :min="0" v-model="form.price" placeholder="请输入价格"></el-input-number>
                </el-form-item>
                <el-form-item label="* 利润率:">
                    <el-input-number :min="0" :max="1" v-model="form.profitMargin"
                                     placeholder="请输入利润率"></el-input-number>
                </el-form-item>
            </el-form>
            <span slot="footer" class="dialog-footer">
                <el-button @click="newVisible = false">取 消</el-button>
                <el-button type="primary" @click="saveNew">确 定</el-button>
            </span>
        </el-dialog>
        <!-- 编辑弹出框 -->
        <el-dialog title="" :visible.sync="editVisible" width="40%">
            <el-form ref="form" :model="form" label-width="100px">
                <el-form-item label="价格:">
                    <el-input v-model="form.price" placeholder="请输入新价格"></el-input>
                </el-form-item>
                <el-form-item label="利润率:">
                    <el-input v-model="form.profitMargin" placeholder="请输入新利润率"></el-input>
                </el-form-item>
            </el-form>
            <span slot="footer" class="dialog-footer">
                <el-button @click="editVisible = false">取 消</el-button>
                <el-button type="primary" @click="saveEdit">确 定</el-button>
            </span>
        </el-dialog>
    </div>
</template>

<script>
    import {PriceList} from "@/api/Api.js";
    import {PriceAdd} from "@/api/Api.js";
    import {PriceUpdate} from "@/api/Api.js";
    import {PriceSyncPrice} from "@/api/Api.js";
    import {PriceSyncProfit} from "@/api/Api.js";

    import {GetallFlowsource} from "@/api/Api.js";
    import {industryManage} from "@/api/Api.js";
    import {adPositiongetList} from "@/api/Api.js";


   const ids = new Map();

    export default {
        name: "basetable",
        data() {
            return {
                tableData: [],
                cur_page: 1,
                total: 1,
                ps: 10,
                select_cate: "",
                select_word: "",
                del_list: [],
                is_search: false,
                editVisible: false,
                newVisible: false,
                newaddAD: false,
                template_dialog: false,
                currentRow: null,
                state1: '',
                adPosition: '',
                form: {
                    mid: "",
                    os: "",
                    pid: "",
                    industry: '',
                    price: '',
                    profitMargin: '',
                    areaLevel: '',
                    mediaName: '',
                    mediaName1: '',
                    industryType: '',
                    name: '',
                    mName: '',
                },
                flowsource: [],
                flowMap:{},
                adPosList: [],
                adName: [],
                idx: -1,
                flow_type: 1,
                type: 1,
                system: 1,
                Access: "API",
                display_form: 1,
                options: [
                    {
                        value: '',
                        label: ''
                    }
                ],
                industryType: '',
                value: "",
                terminal: '',
                terminalType: [
                    {
                        value: '146',
                        label: 'PC站'
                    },
                    {
                        value: '147',
                        label: 'WAP站'
                    },
                    {
                        value: '148',
                        label: 'App端'
                    }
                ],
                readonly:''


            };
        },
        created() {
            this.readonly = this.$store.state.map.get(this.$store.state.currentPath);
            this.getList();
            this.getIndustry();
            this.getFlowsource();
        },

        methods: {
            // 分页导航
            handleCurrentChange(val) {
                this.cur_page = val;
                 let params = {
                    mid: this.form.mediaName1,
                    // os: this.terminal,
                     pid: ids.get(this.state1),
                    industryid: this.form.industryType,
                    sysFlag: 1,
                    cp: this.cur_page,
                    ps: this.ps
                }
                PriceList(params).then(res => {
                  let data = res.data;
                    this.tableData = data.data.data;
                    this.total = data.data.totalItemNum;
                });
            },
            // 获取 table
            getList() {
                let params = {sysFlag: 1, cp: this.cur_page, ps: this.ps}
                PriceList(params).then(res => {
                  let data = res.data;
                    this.tableData = data.data.data
                    this.total = data.data.totalItemNum;
                });
            },
            // 获取行业选项
            getIndustry() {
                industryManage().then(res => {
                  let data = res.data;
                    this.options = data.data
                });
            },
            // 获取媒体
            getFlowsource() {
                GetallFlowsource().then(res => {
                  let data = res.data;
                    this.flowsource = data.data
                    this.flowMap = new Map();
                    for (var flow of data.data) {
                        this.flowMap.set(flow.id, flow.mediaUuid);
                    }
                });
            },
            // 搜索
            search() {
                    this.$refs.pagination.lastEmittedPage = 1
                    this.cur_page = 1;
                let params = {
                    mid: this.form.mediaName1,
                    // os: this.terminal,
                    pid: ids.get(this.state1),
                    industryid: this.form.industryType,
                    sysFlag: 1,
                    cp: this.cur_page,
                    ps: this.ps
                }
                PriceList(params).then(res => {
                  let data = res.data;
                    this.tableData = data.data.data;
                    this.total = data.data.totalItemNum;
                });
            },
            // 编辑价格利润率
            newPrice(index, row) {
                this.editVisible = true
                let item = row;
                this.form = {
                    price: item.price,
                    profitMargin: item.profitMargin,
                    id: item.id,
                }
            },
            // 保存编辑
            saveEdit() {
                let params = {
                    price: this.form.price,
                    profitMargin: this.form.profitMargin,
                    id: this.form.id,
                }
                PriceUpdate(params).then(res => {
                  let data = res.data;
                    if (data.code != 'A000000') {
                        this.$message.error(data.message);
                        return
                    } else {
                        this.$message.success("修改成功");
                    }
                    this.getList();
                    this.editVisible = false
                });
            },
            // 同步价格
            syncPrice(index, row) {
                this.row = row;
                let params = {id: this.row.id}
                PriceSyncPrice(params).then(res => {
                  let data = res.data;
                    if (data.code != 'A000000') {
                        this.$message.error(data.message);
                        return
                    } else {
                        this.$message.success("同步价格成功");
                    }
                });
            },
            // 同步利润率
            syncProfit(index, row) {
                this.row = row;
                let params = {id: this.row.id}
                PriceSyncProfit(params).then(res => {
                  let data = res.data;
                    if (data.code != 'A000000') {
                        this.$message.error(data.message);
                        return
                    } else {
                        this.$message.success("同步价格成功");
                    }
                });
            },
            // 模糊搜索
            querySearch(queryString, cb) {
                let value = this.form.mediaName1;
                let uuid = this.flowMap.get(value);
                let params = {
                    // flowUuid: uuid,
                    // name: queryString,
                    // cp: 1,
                    // ps: 20
                }
                adPositiongetList().then(res => {
                  let data = res.data;
                    // if (data.code != 'A000000') {
                    //     this.$message.error(data.message);
                    // } else {
                        // 调用 callback 返回建议列表的数据
                        cb(data.data.data);
                            console.log(data.data.data)
                         data.data.data.forEach(element => {
                            ids.set(element.name,element.id);
                          });
                        
                    // }
                });
            },
            // 模糊搜索
            querySearchNew(queryString, cb) {
                let value = this.form.mid;
                let uuid = this.flowMap.get(value);
                let params = {
                    flowUuid: uuid,
                    name: queryString,
                    cp: 1,
                    ps: 20
                }
                adPositiongetList(params).then(res => {
                  let data = res.data;
                    if (data.code != 'A000000') {
                        this.$message.error(data.message);
                    } else {
                        // 调用 callback 返回建议列表的数据
                        cb(data.data.data);
                    }
                });
            },
            handleSelect(item) {
                this.adPosition = item.id;
            },
            handleSelectNew(item) {
                this.form.pid = item.id;
            },
            // 获取广告位
            getADPosition(value) {
                let uuid = this.flowMap.get(value);
                let params = {flowUuid:uuid, cp: 1, ps: 20};
                adPositiongetList(params).then(res => {
                  let data = res.data;
                    this.adPosList = data.data.data
                });
            },
            // 新建底价
            create() {
                this.newVisible = true;
                this.form = {};
                this.getFlowsource();
            },
            saveNew() {
                // 参数判断
                 if (!this.form.mid) {
                    this.$message.error("媒体不能为空");
                    return;
                }
                if (!this.form.pid) {
                    this.$message.error("广告位不能为空");
                    return;
                }
                if (!this.form.price) {
                    this.$message.error("价格不能为空");
                    return;
                }
                if (!this.form.profitMargin) {
                    this.$message.error("利润率不能为空");
                    return;
                }
                let params = {
                    sysFlag: 1,
                    mid: this.form.mid,
                    pid: this.form.pid,
                    price: this.form.price,
                    profitMargin: this.form.profitMargin,
                }
                PriceAdd(params).then(res => {
                  let data = res.data;
                    console.log(res)
                    if (data.code != 'A000000') {
                        this.$message.error(data.message);
                    }
                    // 掉查询接口
                    this.newVisible = false;
                    this.getList();
                });

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
        width: 160px;
        display: inline-block;
    }

    .del-dialog-cnt {
        font-size: 16px;
        text-align: center;
    }

    .el-button + .el-button {
        margin-left: 1px;
    }

    .add_HY {
        display: flex;
        padding-top: 20px
    }

    .border {
        width: 100%;
        height: 2px;
        margin-top: 20px;
        margin-bottom: 20px
    }

</style>
<style>
    input, button, select, textarea {
        outline: none
    }

    textarea {
        resize: none
    }
</style>

