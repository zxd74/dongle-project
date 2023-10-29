<template>
    <div class="table">
        <div class="container">
            <div class="header-select">
                <template>
                    <el-tag size="medium">客户名称:</el-tag>
                    <el-autocomplete
                        class="inline-input"
                        v-model="state1"
                        value-key="fullName"
                        :fetch-suggestions="querySearch"
                        placeholder="请输入客户名称"
                        @select="handleSelect"
                    ></el-autocomplete>
                    <el-tag size="medium">日期:</el-tag>
                    <el-date-picker
                        v-model="startDay"
                        type="date"
                        placeholder="选择日期"
                        format="yyyy 年 MM 月 dd 日"
                        value-format="yyyyMMdd">
                    </el-date-picker>
                    -
                    <el-date-picker
                        v-model="endDay"
                        type="date"
                        placeholder="选择日期"
                        format="yyyy 年 MM 月 dd 日"
                        value-format="yyyyMMdd">
                    </el-date-picker>
                    <el-button type="primary" icon="search" @click="search">搜索</el-button>
                </template>
            </div>
            <!-- <el-tag size="medium">代理商名称:</el-tag>
                 <span class="font">{{name}}</span>元 -->
            <div class="header_money">
                <div>
                    <el-tag type="success" size="medium">当前余额:</el-tag>
                </div>
                <div>
                    <span class="qian">￥{{balance}}</span>
                    <el-button type="primary" @click="Recharge" v-if="this.readonly !=1">充值</el-button>
                    <el-button type="primary" @click="Refund" v-if="this.readonly !=1">退款</el-button>
                </div>
            </div>
            <div class="header_money">
                <div>
                    <el-tag type="success" size="medium">历史账单:</el-tag>
                </div>
                <div class="xiaofei_left" style="margin: 0 20px 20px 0;">
                    <div class="conter">
                        <div class="left" style="width:40%">
                            <img class="tubiao" src="../../../../src/assets/mony1.png" alt="">
                            <dir style="float:right">
                                <span class="blue">{{sumCostNow}}</span>
                                <p></p>
                                <span class="mini">累计消费</span>
                            </dir>
                        </div>
                        <div class="right" style="width:40%">
                            <dir style="float:right">
                                <span class="blue">{{sumCostBefore}}</span>
                                <p></p>
                                <span class="mini">上时段累计</span>
                            </dir>
                        </div>
                    </div>

                </div>
                <div class="xiaofei_left" style="margin: 0 20px 20px 0;">
                    <div class="conter">
                        <div class="left" style="width:40%">
                            <img class="tubiao" src="../../../../src/assets/mony2.png" alt="">
                            <dir style="float:right">
                                <span class="green">{{sumRechargeNow}}</span>
                                <p></p>
                                <span class="mini">累计充值</span>
                            </dir>
                        </div>
                        <div class="right" style="width:40%">
                            <dir style="float:right">
                                <span class="blue">{{sumRechargeBefore}}</span>
                                <p></p>
                                <span class="mini">上时段累计</span>
                            </dir>
                        </div>
                    </div>
                </div>
                <div class="xiaofei_left" style="margin: 0 20px 20px 0;">
                    <div class="conter">
                        <div class="left" style="width:40%">
                            <img class="tubiao" src="../../../../src/assets/mony3.png" alt="">
                            <dir style="float:right">
                                <span class="orange">{{sumRefundNow}}</span>
                                <p></p>
                                <span class="mini">累计退款</span>
                            </dir>
                        </div>
                        <div class="right" style="width:40%">
                            <dir style="float:right">
                                <span class="blue">{{sumRefundBefore}}</span>
                                <p></p>
                                <span class="mini">上时段累计</span>
                            </dir>
                        </div>
                    </div>
                </div>
            </div>
            <div>
                <el-radio-group v-model="record" @change="tendency">
                    <el-radio-button label="1">消费</el-radio-button>
                    <el-radio-button label="2">充值</el-radio-button>
                    <el-radio-button label="3">退款</el-radio-button>
                </el-radio-group>
            </div>
            <div id="charts" style="width:'100%',height:'300px'" v-if="this.record == 1||this.record ==2">
                <div id="main" :style="{width:'100%',height:'300px'}"></div>
            </div>
             <!-- 充值 -->
            <el-table :data="tableData" border style="width: 100%" ref="multipleTable" v-show="this.record ==2">
                <el-table-column prop="createTime" label="账单生成时间">
                </el-table-column>
                <el-table-column prop="type" label="交易类型" :formatter="formatter"
                                 v-if="this.record == 2||this.record == 3">
                </el-table-column>
                <el-table-column prop="operateUser" label="操作人" v-if="this.record == 2||this.record == 3">
                </el-table-column>
                <el-table-column prop="price" label="账单金额(元)">
                </el-table-column>
            </el-table>
            <div class="pagination" >
                <el-pagination @current-change="handleCurrentChange2" layout="prev, pager, next" :total="total2">
                </el-pagination>
            </div>
            <!-- 消费 -->
            <el-table :data="datas" border style="width: 100%" ref="multipleTable" v-show="this.record ==1">
                <el-table-column prop="createTime" label="账单生成时间">
                </el-table-column>
                <el-table-column prop="type" label="交易类型" :formatter="formatter"
                                 v-if="this.record == 2||this.record == 3">
                </el-table-column>
                <el-table-column prop="operateUser" label="操作人" v-if="this.record == 2||this.record == 3">
                </el-table-column>
                <el-table-column prop="price" label="账单金额(元)">
                </el-table-column>
            </el-table>
            <div class="pagination" v-if="this.record == 1">
                <el-pagination @current-change="handleCurrentChange1" layout="prev, pager, next" :total="total1">
                </el-pagination>
            </div>
            <!-- 退款 -->
            <el-table :data="refund" border style="width: 100%" ref="multipleTable" v-show="this.record ==3">
                <el-table-column prop="createTime" label="账单生成时间">
                </el-table-column>
                <el-table-column prop="type" label="交易类型" :formatter="formatter"
                                 v-if="this.record == 2||this.record == 3">
                </el-table-column>
                <el-table-column prop="operateUser" label="操作人" v-if="this.record == 2||this.record == 3">
                </el-table-column>
                <el-table-column prop="price" label="账单金额(元)">
                </el-table-column>
                
            </el-table>
            <div class="pagination" v-if="this.record == 3">
                        <el-pagination @current-change="handleCurrentChange3" layout="prev, pager, next" :total="total3">
                </el-pagination>
                </div>
            <!-- 充值 -->
            <el-dialog title="提示" :visible.sync="delVisible" width="30%" center>
                <el-form ref="form" :model="form" label-width="100px">
                    <el-form-item label="当前余额:">
                        <span class="font">{{balance}}</span>元
                    </el-form-item>
                    <el-form-item label="充值金额:">
                        <el-input v-model="prices" @blur="compute"></el-input>
                    </el-form-item>
                    <div class="border_"></div>
                    <el-form-item label="充值后余额:">
                        <span class="font">{{add_balance}}</span>元
                    </el-form-item>
                </el-form>
                <span slot="footer" class="dialog-footer">
                <el-button @click="delVisible = false">取 消</el-button>
                <el-button type="primary" @click="recharge">确 定</el-button>
            </span>
            </el-dialog>
            <!-- 退款 -->
            <el-dialog title="提示" :visible.sync="newVisible" width="30%" center>
                <el-form ref="form" :model="form" label-width="100px">
                    <el-form-item label="当前余额:">
                        <span class="font">{{balance}}</span>元
                    </el-form-item>
                    <el-form-item label="退款金额:">
                        <el-input v-model="refundPrice"></el-input>
                        <!-- <el-button type="text">全部退款</el-button> -->
                    </el-form-item>
                    <div class="border_"></div>
                    <!-- <el-form-item label="退款后余额:">
                        <span class="font">{{refund_balance}}</span>元
                    </el-form-item> -->
                </el-form>
                <span slot="footer" class="dialog-footer">
                <el-button @click="newVisible = false">取 消</el-button>
                <el-button type="primary" @click="EditRefund">确 定</el-button>
            </span>
            </el-dialog>

        </div>
    </div>
</template>

<script>

    var echarts = require("echarts/lib/echarts");
    require("echarts/lib/chart/line");
    require("echarts/lib/component/legend");
    require("echarts/lib/component/tooltip");
    require("echarts/lib/component/title");


    import {getBalance} from "@/api/Api.js";
    import {AgentList} from "@/api/Api.js";
    import {rechargesumHistory} from "@/api/Api.js";
    import {rechargesumByDate} from "@/api/Api.js";
    import {rechargegetList} from "@/api/Api.js";
    import {rechargeAdd} from "@/api/Api.js";
    import {reportForCast} from "@/api/Api.js";

    export default {
        name: "basetable",
        data() {
            return {
                url: "./static/vuetable.json",
                tableData: [],
                cur_page: 1,
                total1: 1,
                total2: 2,  
                total3: 3,
                ps: 10,
                select_cate: "",
                select_word: "",
                del_list: [],
                name: '',
                is_search: false,
                editVisible: false,
                delVisible: false,
                newVisible: false,
                newaddAD: false,
                template_dialog: false,
                currentRow: null,
                startDay: '',
                endDay: '',
                record: '',
                balance: '',
                add_balance: '',
                prices: null,
                refundPrice: null,
                refund_balance: '',
                state1: '',
                id: '',
                sumRechargeNow: '',
                sumRechargeBefore: '',
                sumRefundNow: '',
                sumRefundBefore: '',
                sumCostNow: '',
                sumCostBefore: '',
                createDay: [],
                price: [],
                fileList2: [{
                    name: 'food.jpeg',
                    url: 'https://fuss10.elemecdn.com/3/63/4e7f3a15429bfda99bce42a18cdd1jpeg.jpeg?imageMogr2/thumbnail/360x360/format/webp/quality/100'
                }, {
                    name: 'food2.jpeg',
                    url: 'https://fuss10.elemecdn.com/3/63/4e7f3a15429bfda99bce42a18cdd1jpeg.jpeg?imageMogr2/thumbnail/360x360/format/webp/quality/100'
                }],
                form: {
                    name: "",
                    date: "",
                    switch: false,
                    add_time: "",
                },
                data: '',
                idx: -1,
                type: 1,
                types: null,
                flow_management: null,
                data_report: null,
                options1: '1',
                options2: '2',
                options3: '3',
                options4: '4',

                options: [
                    {
                        value: "1",
                        label: "流量管理员"
                    },
                    {
                        value: "2",
                        label: "广告客户"
                    },
                    {
                        value: "3",
                        label: "直客客户"
                    },
                    {
                        value: "4",
                        label: "代理商"
                    },
                ],
                options1: [
                    {
                        value: "1",
                        label: "品牌"
                    },
                    {
                        value: "2",
                        label: "游戏"
                    },
                    {
                        value: "3",
                        label: "电商"
                    },
                    {
                        value: "4",
                        label: "中小"
                    },
                    {
                        value: "5",
                        label: "网服"
                    },
                    {
                        value: "6",
                        label: "金融贸易"
                    },
                ],
                value: "1",
                startDay: '',
                endDay: new Date().Format("yyyyMMdd"),
                readonly:''

            };
        },
        created() {
            this.readonly = this.$store.state.map.get(this.$store.state.currentPath);
            this.getParams();
            this.getList();
            this.getPRice();
            
        },

        methods: {
            // 格式化数据
            formatter(row, column) {
                if (row.type == 1) {
                    return "充值";
                } else if (row.type == 2) {
                    return "退款";
                }
            },
            // 分页导航
            handleCurrentChange1(val) {
                this.cur_page = val;
                this.tendency();
            },
            handleCurrentChange2(val) {
                this.cur_page = val;
                this.tendency();
            },
            handleCurrentChange3(val) {
                this.cur_page = val;
                this.tendency();
            },

            getParams () {
                var routerParams = this.$route.query.id;
                this.id = routerParams;
            },
            getPRice(){
                    this.date = new Date();
                    var lw = new Date(new Date() - 1000 * 60 * 60 * 24 * 7);//最后一个数字30可改，30天的意思
                    var lastY = lw.getFullYear();
                    var lastM = lw.getMonth()+1;
                    var lastD = lw.getDate();
                    this.sDay=lastY+""+(lastM<10 ? "0" + lastM : lastM)+""+(lastD<10 ? "0"+ lastD : lastD);
                    this.startDay =  this.sDay
                    // console.log(this.sDay)
            },
            getList() {
                // if (!this.$route.query.id && !this.id) {
                //     return
                // }
                // if (this.$route.query.id) {
                //     this.id = this.$route.query.id
                // }
                // console.log(this.id)
                this.state1 = this.$route.query.fullName
                // this.name = fullName
                let params = {
                    id: this.id
                }
                console.log(params)
                //  余额
                getBalance(params).then(res => {
                        //  console.log(res)
                    let data = res.data;
                    this.balance = data.data.balance
                    console.log(this.balance)
                    console.log(res)
                });
             
          

            },
            //  搜索
            search() {
                  let par = {
                    id: this.id
                }
                console.log(params)
                //  余额
                getBalance(par).then(res => {
                        //  console.log(res)
                    let data = res.data;
                    this.balance = data.data.balance
                    console.log(this.balance)
                    console.log(res)
                });
                // 历史账单
                let params = {
                    advertiserId: this.id,
                    // type:1,
                    startDay: this.startDay,
                    endDay: this.endDay
                }
                rechargesumHistory(params).then(res => {
                    let data = res.data;
                    this.sumCostNow = data.data.sumCostNow
                    this.sumCostBefore = data.data.sumCostBefore
                    this.sumRechargeNow = data.data.sumRechargeNow
                    this.sumRechargeBefore = data.data.sumRechargeBefore
                    this.sumRefundNow = data.data.sumRefundNow
                    this.sumRefundBefore = data.data.sumRefundBefore
                    // console.log(res)
                });
                 this.record =''
               


            },
            // 模糊搜索
            querySearch(queryString, cb) {
                let params = {
                    fullName: this.state1,
                    type: 2,
                    cp: 1,
                    ps: 20
                }
                AgentList(params).then(res => {
                    let data = res.data;
                    // 调用 callback 返回建议列表的数据
                    cb(data.data.data);
                    if (data.code != 'A000000') {
                        this.$message.error(data.message);
                    }
                    //  this.dynamicTags = data.data
                    //  this.QX_Visible = true;
                });
            },
            handleSelect(item) {
                // console.log(item);
                this.id = item.id
            },

            filterTag(value, row) {
                return row.tag === value;
            },
            handleEdit(index, row) {
                this.idx = index;
                const item = this.tableData[index];
                this.form = {
                    name: item.name,
                    date: item.date,
                    address: item.address
                };
                this.editVisible = true;
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
            // 充值
            Recharge() {
                this.delVisible = true;
            },
            // 确认
            recharge() {
                let id = this.$route.query.id
                // 代理商充值/退款
                let params = {advertiserId: this.id, type: 1, rechargePrice: this.prices}
                rechargeAdd(params).then(res => {
                    let data = res.data;
                    if (data.code != 'A000000') {
                        this.$message.error(data.message);
                    } else {
                        this.$message.success("充值成功");
                    }
                    this.tendency();
                    this.getList();
                    this.delVisible = false;
                });

            },
            // 计算充值
            compute() {
                this.add_balance = (parseFloat(this.prices) + this.balance).toFixed(2)
            },
            // 计算退款
            refundCompute() {
                // if(this.refundPrice > this.balance){
                //   this.$message.error('退款金额有误，请重新填写');
                //   return
                // } else{
                this.refund_balance = (parseFloat(this.refundPrice) - this.balance).toFixed(2)
                // }

            },
            // 退款
            Refund() {
                this.newVisible = true
            },
            // 退款确认
            EditRefund() {
                let id = this.$route.query.id
                // 代理商充值/退款
                let params = {advertiserId: this.id, type: 2, rechargePrice: this.refundPrice}
                rechargeAdd(params).then(res => {
                    let data = res.data;
                    if (data.code != 'A000000') {
                        this.$message.error(data.message);
                        return;
                    } else {
                        this.$message.success("退款成功");
                    }
                    this.tendency();
                    this.getList();
                    this.newVisible = false
                });
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
            tendency() {
                if (this.record == 1) {
                    let par = {
                        id: this.id,
                        startDay: this.startDay,  
                        endDay: this.endDay,
                        cp: this.cur_page,
                        ps: this.ps
                    }
                    reportForCast(par).then(res => {
                        let data = res.data;
                        this.createDay = data.data.date
                        this.price = data.data.cost
                        this.datas = data.data.page.data
                        this.total1 = data.data.page.totalItemNum;

                        // console.log(this.price)
                        /*ECharts图表*/
                        var myChart = echarts.init(document.getElementById("main"));
                        myChart.setOption({
                            //   title: {
                            //     text: '流量数据'
                            // },
                            xAxis: {
                                type: 'category',
                                data: this.createDay
                            },
                            yAxis: {
                                type: 'value'
                            },
                            series: [{
                                data: this.price,
                                type: 'line'
                            }]

                        });
                    });

                }
                if (this.record == 2) {
                     // echaers
                let par = {
                    advertiserId: this.id,
                    type: 1,
                    startDay: this.startDay,
                    endDay: this.endDay
                }
                rechargesumByDate(par).then(res => {
                    let data = res.data;
                    this.createDay = data.data.date
                    this.price = data.data.money
                    console.log(this.price)
                    /*ECharts图表*/
                    var myChart = echarts.init(document.getElementById("main"));
                    myChart.setOption({
                        //   title: {
                        //     text: '流量数据'
                        // },
                        xAxis: {
                            type: 'category',
                            data: this.createDay
                        },
                        yAxis: {
                            type: 'value'
                        },
                        series: [{
                            data: this.price,
                            type: 'line'
                        }]

                    });
                     //  table
                let table = {
                    advertiserId: this.id,
                    type: 1,
                    startDay: this.startDay,
                    endDay: this.endDay,
                    cp: this.cur_page,
                    ps: this.ps
                }
                rechargegetList(table).then(res => {
                    let data = res.data;
                    this.tableData = data.data.data
                    this.total2 = data.data.totalItemNum;
                    // console.log(res)
                });

                });
               
                }
                if (this.record == 3) {
                    let table = {
                        advertiserId: this.id,
                        type: 2,
                        startDay: this.startDay,
                        endDay: this.endDay,
                        cp:this.cur_page,
                        ps:this.ps
                    }
                    rechargegetList(table).then(res => {
                        let data = res.data;
                         this.refund = data.data.data
                        this.total3 = data.data.totalItemNum;
                        // console.log(res)
                    });
                }
            }
        },

        mounted() {
            /*ECharts图表*/
            var myChart = echarts.init(document.getElementById("main"));
            myChart.setOption({
                //   title: {
                //     text: '流量数据'
                // },
                xAxis: {
                    type: 'category',
                    data: this.createDay
                },
                yAxis: {
                    type: 'value'
                },
                series: [{
                    data: this.price,
                    type: 'line'
                }]

            });


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
        width: 200px;
        display: inline-block;
    }

    .del-dialog-cnt {
        font-size: 16px;
        text-align: center;
    }

    .header-select {
        margin-bottom: 20px
    }

    .mr10 {
        width: 150px;
    }

    .bg_img {
        width: 450px;
        height: 300px;
        background: skyblue
    }

    .bg_img img {
        width: 300px;
        height: 200px;
    }

    .footer {
        margin-left: 100px
    }

    .img_ {
        width: 80px;
        height: 40px;
    }

    .header_money div {
        margin: 20px;
    }

    .qian {
        font-size: 24px;
        color: #249cd3
    }

    .xiaofei_left {
        /* display: inline-block; */
        float: left;
        /* display: flex; */
        width: 30%;
        height: 120px;
        background: #f6f7fb;

    }

    .tubiao {
        width: 40px;
        height: 40px;
        padding-right: 20px;
    }

    .xiaofei_left .conter {
        display: flex;
        width: 100%;
        height: 100%;

    }

    .xiaofei_left .left {
        display: flex;
    }

    .xiaofei_left .right {
        display: flex;
        width: 40%;
    }

    .xiaofei_left .mini {
        font-size: 12px;
        color: silver;
        text-align: center;
        margin: 0 auto;
    }

    .blue {
        color: #269fd6;
        font-size: 18px;
        font-weight: 600;
    }

    .green {
        color: #60b94e;
        font-size: 18px;
        font-weight: 600;
    }

    .orange {
        color: #f38a2c;
        font-size: 18px;
        font-weight: 600;
    }

    .black {
        font-size: 18px;
        font-weight: 500;
        color: rgba(0, 0, 0, 0.712)
    }

    .border_ {
        width: 100%;
        /* height: 1px; */
        border: 1px dashed silver
    }

    .font {
        font-size: 16px;
        font-weight: 600
    }

    /* .splice{
        width: 650px;
        height: 2px;
        background: silver;
        padding-bottom: 10px;
    } */
</style>




