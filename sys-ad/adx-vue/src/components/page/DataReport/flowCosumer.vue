<template>
    <div class="box">
    <div v-show="this.index == 1">
        <div class="block">
             <el-form ref="form" :model="form"  status-icon label-width="100px">
                <el-form-item  label="查询方式:">
                    <el-radio-group v-model="flowSource"  @change='radioLabel'>
                        <el-radio :label="1">单广告平台</el-radio>
                        <el-radio :label="2">多广告平台</el-radio>
                    </el-radio-group>
                 </el-form-item>
                <el-form-item label="广告平台:" v-if="flowSource == 1">
                    <el-select v-model="classify" placeholder="请选择"  @change='delete1'>
                        <el-option
                            v-for="item in optionsType"
                            :key="item.dspId"
                            :label="item.consumerName"
                            :value="item.dspId">
                        </el-option>
                    </el-select>
                </el-form-item>
                 <el-form-item label="广告平台:" v-if="flowSource == 2">
                         <!-- 循环选项 -->
                         <el-checkbox :indeterminate="isIndeterminate" v-model="checkAll" @change="handleCheckAllChange">全选</el-checkbox>
                         <el-checkbox-group v-model="checkList" @change='delete2'>
                           <el-checkbox v-for="(item,index) in types" :key="index" :label="item.dspId">{{item.consumerName}}</el-checkbox>
                          <!-- <el-checkbox v-for="(item,index) in types" :key="index">{{item.name}}</el-checkbox> -->
                       </el-checkbox-group>

                </el-form-item>
                <el-form-item label="查询字段:" v-if="flowSource == 2">
                     <el-select v-model="value3" placeholder="请选择">
                        <el-option
                        v-for="item in options3"
                        :key="item.value"
                        :label="item.label"
                        :value="item.value">
                        </el-option>
                     </el-select>
                </el-form-item>
            </el-form>
            <div style="margin-left: 26px;">
                <el-date-picker
                    style="width: 180px"
                    v-model="startDay"
                    type="date"
                    placeholder="选择日期"
                    format="yyyy 年 MM 月 dd 日"
                    value-format="yyyyMMdd"
                    :picker-options="pickerOptions1"
                >
                </el-date-picker> -
                <el-date-picker
                    style="width: 180px"
                    v-model="endDay"
                    type="date"
                    placeholder="选择日期"
                    format="yyyy 年 MM 月 dd 日"
                    value-format="yyyyMMdd"
                    :picker-options="pickerOptions1"
                >
                </el-date-picker>
                <el-select v-model="appIds" multiple placeholder="APP名称" class="handle-input" @focus=getAppName @change="getOSName" @remove-tag="deleteTag" style="width:150px">
                        <el-option
                          v-for="item in optionAppName"
                          :key="item.appId"
                          :label="item.appName"
                          :value="item.appId">
                        </el-option>
                </el-select>
                <el-select v-model="systemAD" multiple placeholder="系统广告位"  @focus=getOSName>
                            <el-option
                                v-for="item in optionsOs"
                                :key="item.uuid"
                                :label="item.name"
                                :value="item.uuid">
                            </el-option>
                </el-select>
                <el-select v-model="line" clearable placeholder="分列显示">
                        <el-option
                            v-for="item in optionsLine"
                            :key="item.value"
                            :label="item.label"
                            :value="item.value">
                        </el-option>
                </el-select>
                <el-button type="primary" @click="search">搜 索</el-button>
                <el-button type="primary" icon="search" @click="exportExcel">导出</el-button>
             
            </div>
        </div>
        <!-- ECharts图表测试 -->
        <div id="charts" style="width:'100%',height:'500px'"  v-loading="dialogVisible">
            <div id="main" :style="{width:'100%',height:'500px'}"></div>
        </div>
        <el-tag size="medium">广告平台数据:</el-tag>
        <div class="handle-box" style="width:100%">
             <el-select v-model="custom" multiple placeholder="自定义显示" style="width:100%">
                            <el-option
                                v-for="item in optionsCustom"
                                :key="item.value"
                                :label="item.label"
                                :value="item.value">
                            </el-option>
             </el-select>
        </div>
        <el-table :data="tableData" border style="width: 100%" ref="multipleTable" id="allTable"  key="tableDataInstall"   v-loading="dialogVisible">
            <el-table-column prop="appName" label="APP名称" v-if="this.line == 'appid'">
            </el-table-column>
            <el-table-column prop="adpName" label="系统广告位" v-if="this.line == 'adpid'">
            </el-table-column>
            <el-table-column prop="dspName" label="dsp平台" v-if="this.line == 'platform_id'">
            </el-table-column>
            <el-table-column prop="req" label="请求量"  v-if="this.custom.indexOf('req') > -1">
            </el-table-column>
            <el-table-column prop="timeout" label="超时" v-if="this.custom.indexOf('timeout') > -1">
            </el-table-column>
            <el-table-column prop="" label="超时率%" v-if="this.custom.indexOf('timeout_rate') > -1">
                    <template slot-scope="scope">
                         {{scope.row.timeout !=0 && scope.row.req != 0?((scope.row.timeout/scope.row.req)*100).toFixed(2)+'%':'0.00'}}
                    </template>
            </el-table-column>
            <el-table-column prop="nobid" label="不竞价"  v-if="this.custom.indexOf('nobid') > -1">
            </el-table-column>
            <el-table-column prop="lower" label="低于底价" v-if="this.custom.indexOf('lower') > -1">
            </el-table-column>
            <el-table-column prop="overqps" label="超过QPS限制" v-if="this.custom.indexOf('overqps') > -1">
            </el-table-column>
            <el-table-column prop="error" label="异常" v-if="this.custom.indexOf('error') > -1">
            </el-table-column>
            <el-table-column prop="win" label="竞价成功数" v-if="this.custom.indexOf('win') > -1">
            </el-table-column>
            <el-table-column prop="" label="竞价成功率%" v-if="this.custom.indexOf('win_rate') > -1">
                    <template slot-scope="scope">
                         {{scope.row.win !=0 && scope.row.bid != 0?((scope.row.win/scope.row.bid)*100).toFixed(2)+'%':'0.00'}}
                    </template>
            </el-table-column>
            <el-table-column prop="exp" label="展现量" v-if="this.custom.indexOf('exp') > -1">
            </el-table-column>
            <el-table-column prop="clk" label="点击量" v-if="this.custom.indexOf('clk') > -1">
            </el-table-column>
            <el-table-column prop="clk_rate" label="点击率%" v-if="this.custom.indexOf('clk_rate') > -1">
            </el-table-column>
            <el-table-column prop="bid" label="填充量" v-if="this.custom.indexOf('bid') > -1">
            </el-table-column>
            <el-table-column prop="bid_rate" label="填充率%" v-if="this.custom.indexOf('bid_rate') > -1">
            </el-table-column>
            <el-table-column prop="investment" label="收入" v-if="this.custom.indexOf('investment') > -1">
            </el-table-column>
            <el-table-column prop="cpm" label="CPM" v-if="this.custom.indexOf('cpm') > -1">
            </el-table-column>
            <el-table-column prop="cpc" label="CPC" v-if="this.custom.indexOf('cpc') > -1">
            </el-table-column>
            <el-table-column label=""  width="150" align="center">
                <template slot-scope="scope">
                <el-button size="mini" type="success" @click="handleEdit(scope.$index, scope.row,)">详细</el-button>
                </template>
            </el-table-column>
        </el-table>
    </div>
    <div v-show="this.index == 2">
            <div class="header-select">
                 <template>
                      <el-button type="primary" icon="search" @click="goback">返回</el-button>
                      <el-button type="primary" icon="search" @click="exportExcel2">导出</el-button> 
                </template>
            </div>
            <el-table :data="tableData2" border style="width: 100%" ref="multipleTable" id="allTable2"  key="tableDataInstall2"  v-loading="dialogVisible">
                <el-table-column prop="creDay" label="日期">
                </el-table-column>
                <el-table-column prop="itemName" label="名称">
                </el-table-column>
                <el-table-column prop="req" label="请求量">
                </el-table-column>
                <el-table-column prop="timeout" label="超时">
                </el-table-column>
                <el-table-column prop="" label="超时率%">
                        <template slot-scope="scope">
                            {{scope.row.timeout !=0 && scope.row.req != 0?((scope.row.timeout/scope.row.req)*100).toFixed(2):'0.00'}}
                        </template>
                </el-table-column>
                <el-table-column prop="nobid" label="不竞价">
                </el-table-column>
                <el-table-column prop="lower" label="低于底价">
                </el-table-column>
                <el-table-column prop="overqps" label="超过QPS限制">
                </el-table-column>
                <el-table-column prop="error" label="异常">
                </el-table-column>
                <el-table-column prop="win" label="竞价成功数">
                </el-table-column>
                <el-table-column prop="" label="竞价成功率%">
                        <template slot-scope="scope">
                            {{scope.row.win !=0 && scope.row.bid != 0?((scope.row.win/scope.row.bid)*100).toFixed(2):'0.00'}}
                        </template>
                </el-table-column>
                <el-table-column prop="exp" label="曝光量" >
                </el-table-column>
                <el-table-column prop="exp_rate" label="填充率%" >
                </el-table-column>
                <el-table-column prop="clk" label="点击量">
                </el-table-column>
                 <el-table-column prop="clk_rate" label="点击率%" >
                </el-table-column>
                 <el-table-column prop="investment" label="总收入" >
                </el-table-column>
                 <el-table-column prop="cpm" label="cpm" >
                </el-table-column>
                 <el-table-column prop="cpc" label="cpc" >
                </el-table-column>
         </el-table>
    </div>
    </div>
</template>

<script>
    import { flowconsumergetlist,consumerdatagetoneplatformdatabyday,consumerdatagetoneplatformpagedatabyday,
    adPositiongetList,appgetall,modifiableByPosition,consumerdatasumByDay,consumerdatasumByDayWithGroup,consumerdatasumItemByDay,
    adPositiongetlistByConsumerAndApp,consumerdatadetailReport
    } from "@/api/Api.js";
    // import Schart from "vue-schart";
    // 引入 ECharts 主模块
    var echarts = require("echarts/lib/echarts");

    require("echarts/lib/chart/line");
    require("echarts/lib/component/legend");
    require("echarts/lib/component/tooltip");
    require("echarts/lib/component/title");

    // 导出模块
    import FileSaver from 'file-saver'
    import XLSX from 'xlsx'
    // require("echarts/lib/component/toolbox");

    // import { tianqi } from "@/api/Api.js";
    var uuid = new Map()
    var dsp = new Map()
    const options = [];
    export default {
        name: "basecharts",
        components: {
            // Schart
        },
        data: () => ({
        pickerOptions1: {
            disabledDate(time) {
                return time.getTime() > Date.now();
            },
        },
        index:1,
        tableData2:[],
        dialogVisible:false,
        checkAll: false,
        isIndeterminate: true,
        checkList:[],
        value3:'',
        form:{},
        flowSource:1,
        types:null,
        line:'',
        appIds:[],
        systemAD:[],
        startDay: new Date().Format("yyyyMMdd"),
        endDay: new Date().Format("yyyyMMdd"),
        radio:'1',
        cur_page: 1,
        total: 1,
        ps:10,
        checkAll: false,
        isIndeterminate: true,
        flow_sueers:'',
        add_time:'',
        classify:'',
        data:[],
        tableData:[],
        optionsType:[
            {
                value: '',
                label: ''
            }
        ],
        optionAppName:[
            {
                value: '',
                label: ''
            }
        ],
        optionsOs:[
            {
            value: '',
            label: ''
            }
        ],
         optionsLine:[
         {
          value: 'appid',
          label: 'APP名称'
         },
         {
          value: 'adpid',
          label: '系统广告位'
         },
         {
          value: 'platform_id',
          label: 'dsp平台'
         },
      ],
        value: '选项1',
        value2:'',
        options2: [],
        value3:'',
        custom:['req','exp','bid_rate','clk','clk_rate','investment'],
        custom2:['req','exp','bid_rate','clk','clk_rate','investment'],
        optionsCustom:[
                {
                value: 'req',
                label: '请求量'
                },
                {
                value: 'timeout',
                label: '超时'
                },
                {
                value: 'timeout_rate',
                label: '超时率'
                },
                {
                value: 'nobid',
                label: '不竞价'
                },
                {
                value: 'lower',
                label: '低于底价'
                },
                {
                value: 'overqps',
                label: '超过QPS限制'
                },
                {
                value: 'error',
                label: '异常'
                },
                {
                value: 'win',
                label: '竞价成功数'
                },
                {
                value: 'win_rate',
                label: '竞价成功率'
                },
                {
                value: 'exp',
                label: '展现量'
                },
                {
                value: 'clk',
                label: '点击量'
                },
                {
                value: 'clk_rate',
                label: '点击率'
                },
                {
                value: 'bid',
                label: '填充量'
                },
                {
                value: 'bid_rate',
                label: '填充率'
                }, 
                {
                value: 'investment',
                label: '收入'
                },
                {
                value: 'cpm',
                label: 'cpm'
                },
                {
                value: 'cpc',
                label: 'cpc'
                },
        ],
        titleList:['请求量','填充量','展现量','点击量','填充率%','展现率%','点击率%','收入','CPM','CPC','超时','超时率%', '不竞价','低于底价','超过QPS限制','异常','竞价成功数','竞价成功率%'],
        options3: [
                {
                value: 'req',
                label: '请求量'
                },
                {
                  value: 'bid',
                  label: '填充量'
                },
                {
                value: 'exp',
                label: '展现量'
                },
                {
                  value: 'bid_rate',
                  label: '填充率%'
                },
                {
                value: 'exp_rate',
                label: '展现率%'
                },
                {
                value: 'clk',
                label: '点击量'
                },
                {
                value: 'clk_rate',
                label: '点击率'
                },
                {
                value: 'timeout',
                label: '超时'
                },
                {
                value: 'nobid',
                label: '不竞价'
                },
                {
                value: 'lower',
                label: '低于底价'
                },
                {
                value: 'overqps',
                label: '超过QPS限制'
                },
                {
                value: 'error',
                label: '异常'
                },
                {
                value: 'win',
                label: '竞价成功数'
                },
        ],
    }),

    created() {
        this.getList()
        // this.getAppName()
        this.getOSName()
    },
    mounted() {
        /*ECharts图表*/
        var myChart = echarts.init(document.getElementById("main"));
        myChart.setOption({
            title: {
                // text: '流量数据'
            },
            toolbox: {
                show : true,
                x:'950',//下载的字体显示不全
                feature : {
                    dataView : {show: false, readOnly: false},
                    magicType : {show: true, type: ['line', 'bar']},
                    restore : {show: true},
                    saveAsImage : {show: true}
                }
            },

            tooltip : {
                trigger: 'axis',
                axisPointer: {
                    type: 'cross',
                    label: {
                        backgroundColor: '#6a7985'
                    }
                }
            },
            legend: {
                data:['请求量','展现量','点击量','展现率%','点击率','收入','CPM','CPC'],
                // y: 'bottom', // 'center' | 'bottom' | {number}
                // padding: [100,0,0,0],
            },
            toolbox: {
                feature: {
                    saveAsImage: {}
                }
            },
            grid: {
                left: '1%',
                right: '4%',
                bottom: '3%',
                containLabel: true
            },
            xAxis : [
                {
                    type : 'category',
                    boundaryGap : false,
                    data : ['周一','周二','周三','周四','周五','周六','周日']
                }
            ],
            yAxis : [
                {
                    type : 'value'
                }
            ],
            series : [
                // {
                //     name:'请求量',
                //     type:'line',
                //     stack: '总量',
                //     areaStyle: {normal: {}},
                //     data:[120, 132, 101, 134, 90, 230, 210]
                // },

            ]
        });


    },
    methods: {
        // app名称下拉框
        getAppName(){
            appgetall().then(res=>{
                let data = res.data; 
                if(data.code != 'A000000') {
                this.$message.error(data.message);
                return;
                }    
                this.optionAppName = data.data
                this.optionAppName.forEach(element => {
                    uuid.set(element.appId,element.id)
                });
            });
        },
        delete1(){
            this.appIds = []
            this.systemAD = []
            this.line = ''
            this.getOSName()
        },
        delete2(){
            this.appIds = []
            this.systemAD = []
            this.line = ''
            this.getOSName()
        },
                // 系统广告位下拉框
        getOSName(){
            let par = {}
            if(this.flowSource == 1){
                let arr= []
                this.appIds.forEach(element => {
                    arr.push(uuid.get(element))
                });
                par = {
                    consumerIds :dsp.get(this.classify),
                    appIds :arr.join(',')
                }
            }else if(this.flowSource == 2){
                let arr = []
                this.checkList.forEach(element => {
                    arr.push(dsp.get(element))
                });
                let arrAppId= []
                this.appIds.forEach(element => {
                    arrAppId.push(uuid.get(element))
                });
                 par = {
                    consumerIds :arr.join(','),
                    appIds :arrAppId.join(',')
                }
            }      
            adPositiongetlistByConsumerAndApp(par).then(res=>{
            console.log(res)
                let data = res.data;
                if(data.code != 'A000000') {
                this.$message.error(data.message);
                return;
                }
                this.optionsOs = data.data
            });
        },
        getAdPosition(){
                let arr= []
                this.appIds.forEach(element => {
                    arr.push(uuid.get(element))
                });
                let  par = {
                    status:1,
                    appIds:arr.join(',')
                }

                adPositiongetList(par).then(res=>{
                    let data = res.data; 
                    if(data.code != 'A000000') {
                    this.$message.error(data.message);
                    return;
                    }    
                    this.optionsOs = data.data.data
                });
        },
         deleteTag(){
                this.systemAD = []
        },
         GetSeach(){
            this.search()
        },
         //   全选
        handleCheckAllChange(val){
            this.checkList = val ? options : [];
            this.isIndeterminate = false;
        },
        
        // 分页导航
        handleCurrentChange(val) {
            this.cur_page = val;
            this.getList();
        },
        //   广告平台名称
        getList(){
            let param = {consumerType:98};
            flowconsumergetlist(param).then(res=>{
                let data = res.data;
            this.optionsType = data.data
            this.types = data.data
            data.data.forEach(element => {
                options.push(element.dspId)
                dsp.set(element.dspId,element.id)
                });
            });
        },
        radioLabel(){
            this.classify =''
            this.checkList =[]
            this.value3 =''
            this.appIds =[]
            this.systemAD =[]
            this.line =''
        },
        //   搜索
        search(){
       
            this.tableData =[]
            var beginDt = new Date();
            var echartsDate  = [];
                // 单平台
        if(this.flowSource == 1){
              if(!this.classify){
                this.$message('名称不能为空')
                return;
                }
                this.dialogVisible = true 
                let params = {
                    sDate:this.startDay,
                    eDate:this.endDay,
                    pid:this.classify,
                    appids:this.appIds.join(','),
                    adpids:this.systemAD.join(','),
                    }                         
            consumerdatasumByDay(params).then(res=>{
                let data = res.data;
                if(data.code != 'A000000') {
                    this.$message.error(data.message);
                }

            var reqs=[];var bid=[];var exp=[];var clk=[];var bid_rate=[];var exp_rate=[];var clk_rate=[];var cpm=[];var cpc=[];var cost=[];
            var timeout=[];      var nobid=[];      var lower=[];      var overqps=[]; var error=[];  var win=[]; 
            var timeout_rate = [];  var win_rate = []
            for(var item of data.data){
                if(item.creDay){
                    echartsDate.push(item.creDay); // 日期
                    reqs.push(item.req);//请求量
                    bid.push(item.bid); //填充量
                    exp.push(item.exp); //展现量
                    clk.push(item.clk); //点击量
                    bid_rate.push(item.bid_rate);//填充率
                    exp_rate.push(item.exp_rate);//曝光率
                    clk_rate.push(item.clk_rate);//点击率
                    cost.push(item.investment);//成本
                    timeout.push(item.timeout);//timeout
                    nobid.push(item.nobid);//nobid
                    lower.push(item.lower);//lower
                    overqps.push(item.overqps);//overqps
                    error.push(item.error);//error
                    win.push(item.win);//win
                    timeout_rate.push(((item.timeout/item.req)*100).toFixed(2));//超时率
                    if(item.bid == 0 ||item.win == 0){
                        win_rate.push(0)
                    }
                    win_rate.push(((item.win/item.bid)*100).toFixed(2));//竞价成功率
                }

            }
            var myChart = echarts.init(document.getElementById("main"));
            myChart.setOption({
                title: {
                    // text: '客户数据'
                },
                tooltip: {
                    trigger: 'axis'
                },
                legend: {
                    data:this.titleList
                },
                grid: {
                    left: '3%',
                    right: '4%',
                    bottom: '3%',
                    containLabel: true
                },
                toolbox: {
                    feature: {
                        saveAsImage: {}
                    }
                },
                xAxis: {
                    type: 'category',
                    boundaryGap: false,
                    data: echartsDate
                },
                yAxis: {
                    type: 'value'
                },
                series: [
                    {
                        name:this.titleList[0],
                        type:'line',
                        // stack: '总量',
                        data:reqs,
                    },
                    {
                        name:this.titleList[1],
                        type:'line',
                        // stack: '总量',
                        data:bid,
                    },
                    {
                        name:this.titleList[2],
                        type:'line',
                        // stack: '总量',
                        data:exp,
                    },
                    {
                        name:this.titleList[3],
                        type:'line',
                        // stack: '总量',
                        data:clk
                    },
                    {
                        name:this.titleList[4],
                        type:'line',
                        // stack: '总量',
                        data:bid_rate
                    },
                    {
                        name:this.titleList[5],
                        type:'line',
                        // stack: '总量',
                        data:exp_rate
                    },
                    {
                        name:this.titleList[6],
                        type:'line',
                        // stack: '总量',
                        data:clk_rate
                    },
                    {
                        name:this.titleList[7],
                        type:'line',
                        // stack: '总量',
                        data:cost
                    },
                    {
                        name:this.titleList[8],
                        type:'line',
                        // stack: '总量',
                        data:cpm
                    },
                    {
                        name:this.titleList[9],
                        type:'line',
                        // stack: '总量',
                        data:cpc
                    },
                    {
                        name:this.titleList[10],
                        type:'line',
                        // stack: '总量',
                        data:timeout
                    },
                    {
                        name:this.titleList[11],
                        type:'line',
                        // stack: '总量',
                        data:timeout_rate
                    },
                    {
                        name:this.titleList[12],
                        type:'line',
                        // stack: '总量',
                        data:nobid
                    },
                    {
                        name:this.titleList[13],
                        type:'line',
                        // stack: '总量',
                        data:lower
                    },
                    {
                        name:this.titleList[14],
                        type:'line',
                        // stack: '总量',
                        data:overqps
                    },
                    {
                        name:this.titleList[15],
                        type:'line',
                        // stack: '总量',
                        data:error
                    },
                    {
                        name:this.titleList[16],
                        type:'line',
                        // stack: '总量',
                        data:win
                    },
                    {
                        name:this.titleList[17],
                        type:'line',
                        // stack: '总量',
                        data:win_rate
                    },
                ]
                    });               
                });
                   let par = {
                        sDate:this.startDay,
                        eDate:this.endDay,
                        pids:this.classify,
                        appids:this.appIds.join(','),
                        adpids:this.systemAD.join(','),
                        group:this.line,
                    }
                    consumerdatasumByDayWithGroup(par).then(res=>{
                        let data = res.data;
                        this.tableData = data.data
                        this.dialogVisible = false 
                    }); 
            }else if(this.flowSource == 2){
                if(!this.line){
                    this.$message.error('分列显示不能为空')
                    return
                }
                if(!this.value3){
                    this.$message.error('查询字段不能为空')
                    return
                }
                if(this.checkList.length <= 0){
                    this.$message.error('广告平台不能为空')
                    return
                }
                    this.dialogVisible = true 
                    let params = {
                        sDate:this.startDay,
                        eDate:this.endDay,
                        pids:this.checkList.join(','),
                        appids:this.appIds.join(','),
                        adpids:this.systemAD.join(','),
                        item:this.value3,
                        group:this.line,
                    }
                    consumerdatasumItemByDay(params).then(res=>{
                        let data = res.data;
                            if(data.code != 'A000000') {
                            this.$message.error(data.message);
                            }
                            var series= []; var legends =[];
                            echartsDate =  data.data.categories 
                            series = data.data.series
                            for(let serie of series){
                                legends.push(serie.name);
                            }
                            var myChart = echarts.init(document.getElementById("main"));
                            myChart.clear()
                            myChart.setOption({
                            title: {
                                // text: '客户数据'
                            },
                            tooltip: {
                                trigger: 'axis'
                            },
                            legend: {
                                data:legends
                            },
                            grid: {
                                left: '3%',
                                right: '4%',
                                bottom: '3%',
                                containLabel: true
                            },
                            toolbox: {
                                feature: {
                                    saveAsImage: {}
                                }
                            },
                            xAxis: {
                                type: 'category',
                                boundaryGap: false,
                                data: echartsDate
                            },
                            yAxis: {
                                type: 'value'
                            },
                            series: series
                            });
                            var mediaSet = new Set(this.checkList);
                            });
                                var temp = []; //一个新的临时数组
                                for(var i = 0; i < this.checkList.length; i++){
                                    if(temp.indexOf(this.checkList[i]) == -1){
                                        temp.push(this.checkList[i]);
                                    }
                                }
                                let pars = {
                                    sDate:this.startDay,
                                    eDate:this.endDay,
                                    pids:temp.join(','),
                                    appids:this.appIds.join(','),
                                    adpids:this.systemAD.join(','),
                                    group:this.line,
                                }
                            consumerdatasumByDayWithGroup(pars).then(res=>{
                                    let data = res.data;
                                    this.tableData = data.data
                                    this.dialogVisible = false 
                                });
            }
 
        },
         // 跳转
        handleEdit(index,row){
            this.idx = index;
            let item = row;
            if(this.flowSource == 1){
                   this.index =2
                   this.dialogVisible = true
                // this.$router.push({ 
                // path:'/datareport/flowCosumer2', 
                // query:{
                //     id:item.itemId,
                //     sDate:this.startDay,
                //     eDate:this.endDay,
                //     pids:this.classify,
                //     appids:this.appIds.join(','),
                //     adpids:this.systemAD.join(','),
                //     group:this.line,
                //     }
                // })
                    let par = {
                        id:item.itemId,
                        sDate:this.startDay,
                        eDate:this.endDay,
                        pids:this.classify,
                        appids:this.appIds.join(','),
                        adpids:this.systemAD.join(','),
                        group:this.line,
                    }
                consumerdatadetailReport(par).then(res=>{
                    let data = res.data;     
                    this.tableData2 = data.data
                    this.dialogVisible = false 
                });
            }else{
                this.index =2
                this.dialogVisible = true
                // this.$router.push({ 
                // path:'/datareport/flowCosumer2', 
                // query:{
                //     id:item.itemId,
                //     sDate:this.startDay,
                //     eDate:this.endDay,
                //     pids:this.checkList.join(','),
                //     appids:this.appIds.join(','),
                //     adpids:this.systemAD.join(','),
                //     group:this.line,
                //     }
                // })
                       let par = {
                        id:item.itemId,
                        sDate:this.startDay,
                        eDate:this.endDay,
                        pids:this.checkList.join(','),
                        appids:this.appIds.join(','),
                        adpids:this.systemAD.join(','),
                        group:this.line,
                    }
                consumerdatadetailReport(par).then(res=>{
                    let data = res.data;     
                    this.tableData2 = data.data
                    this.dialogVisible = false 
                });
            }
          
        },
        goback(){
            this.index =1
        },

        //   导出table方法
        exportExcel () {
            /* generate workbook object from table */
            var wb = XLSX.utils.table_to_book(document.querySelector('#allTable'))
            // var wb1 = XLSX.utils.table_to_book(document.querySelector('#table'))
            /* get binary string as output */
            var wbout = XLSX.write(wb, { bookType: 'xlsx', bookSST: true, type: 'array' })
            // var wbout1 = XLSX.write(wb1, { bookType: 'xlsx', bookSST: true, type: 'array' })
            try {
                FileSaver.saveAs(new Blob([wbout], { type: 'application/octet-stream' }), '广告平台数据.xlsx')
                // FileSaver.saveAs(new Blob([wbout1], { type: 'application/octet-stream' }), '广告平台分日数据.xlsx')
            } catch (e) { if (typeof console !== 'undefined') console.log(e, wbout) }

            return wbout
        },
        exportExcel2 () {
            /* generate workbook object from table */
            var wb = XLSX.utils.table_to_book(document.querySelector('#allTable2'))
            // var wb1 = XLSX.utils.table_to_book(document.querySelector('#table'))
            /* get binary string as output */
            var wbout = XLSX.write(wb, { bookType: 'xlsx', bookSST: true, type: 'array' })
            // var wbout1 = XLSX.write(wb1, { bookType: 'xlsx', bookSST: true, type: 'array' })
            try {
                FileSaver.saveAs(new Blob([wbout], { type: 'application/octet-stream' }), '广告平台分日数据.xlsx')
                // FileSaver.saveAs(new Blob([wbout1], { type: 'application/octet-stream' }), '广告平台分日数据.xlsx')
            } catch (e) { if (typeof console !== 'undefined') console.log(e, wbout) }

            return wbout
        },

    }
    };
</script>

<style scoped>
    .block {
        margin-bottom: 3%;
    }
    .flot_right{
        float: right
    }
    .header-select {
        margin-bottom: 20px
    }
    .mr10{
        width: 180px;
    }
    .border{
        padding-top: 50px
    }
</style>
<style>
.el-checkbox+.el-checkbox{
  padding-left: 0px !important;
  margin-left: 0px !important;
}
.el-radio+.el-radio{
    margin-left: 30px !important;
}
</style>

