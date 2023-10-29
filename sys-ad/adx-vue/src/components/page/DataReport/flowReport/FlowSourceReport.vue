
<template>
  <div class="box">
    <div v-show="this.index == 1">
      <div class="block">
        <div class="header-select">
          <template>
            <el-tag size="medium">日期:</el-tag>
            <el-date-picker
              v-model="startDay"
              type="date"
              placeholder="选择日期"
              format="yyyy 年 MM 月 dd 日"
              value-format="yyyyMMdd"
              :picker-options="pickerOptions1"
            ></el-date-picker>-
            <el-date-picker
              v-model="endDay"
              type="date"
              placeholder="选择日期"
              format="yyyy 年 MM 月 dd 日"
              value-format="yyyyMMdd"
              :picker-options="pickerOptions1"
            ></el-date-picker>
            <el-select
              v-model="line"
              clearable
              placeholder="分列显示"
              @change="GetSeach"
              style="width:150px"
            >
              <el-option
                v-for="item in optionsLine"
                :key="item.value"
                :label="item.label"
                :value="item.value"
              ></el-option>
            </el-select>
            <el-button type="primary" icon="search" @click="search">搜索</el-button>
            <el-button type="primary" icon="search" @click="exportExcel">导出</el-button>
          </template>
        </div>
        <div>
          <el-select
            v-model="appIds"
            multiple
            placeholder="APP名称"
            class="handle-input"
            @focus="getAppName"
            @change="getAdPosition"
            @remove-tag="deleteTag"
            style="width:150px"
          >
            <el-option
              v-for="item in optionAppName"
              :key="item.appId"
              :label="item.appName"
              :value="item.appId"
            ></el-option>
          </el-select>
          <el-select v-model="systemAD" multiple placeholder="系统广告位" style="width:300px">
            <el-option
              v-for="item in optionsOs"
              :key="item.uuid"
              :label="item.positionAppName"
              :value="item.uuid"
            ></el-option>
          </el-select>
          <el-select
            v-model="AppVersion"
            multiple
            placeholder="版本号"
            class="handle-input"
            @focus="getAppVersion"
            style="width:150px"
          >
            <el-option
              v-for="item in optionsAppVersion"
              :key="item.name"
              :label="item.name"
              :value="item.name"
            ></el-option>
          </el-select>
          <el-select
            v-model="AppChannel"
            multiple
            placeholder="一级渠道号"
            class="handle-input"
            @focus="getAppChannel"
            style="width:150px"
          >
            <el-option
              v-for="item in optionsAppChannel"
              :key="item.cname"
              :label="item.name+' - '+item.cname"
              :value="item.cname"
            ></el-option>
          </el-select>
          <el-autocomplete
            style="width:180px"
            v-model="AppChannelTwo"
            value-key="name"
            :fetch-suggestions="querySearch"
            placeholder="二级渠道"
            @select="handleSelect"
          ></el-autocomplete>
          <!-- <el-button type="primary" @click="saveNews">搜 索</el-button> -->
          <div class="div">
            <el-tag
              :key="tag.cname"
              v-for="tag in optionsAppChannelTwo"
              closable
              :disable-transitions="false"
              @close="handleClose(tag)"
            >{{tag.name}}</el-tag>
          </div>
        </div>
      </div>
      <!-- ECharts图表测试 -->
      <div id="charts" style="width:'100%',height:'500px'">
        <div id="main" :style="{width:'100%',height:'500px'}"></div>
      </div>

      <!-- <el-tag size="medium">流量源数据:</el-tag> -->
      <!-- <div class="handle-box"> -->
      <el-select
        v-model="custom"
        multiple
        placeholder="自定义显示"
        @change="handCustom"
        style="width:100%"
      >
        <el-option
          v-for="item in optionsCustom"
          :key="item.value"
          :label="item.label"
          :value="item.value"
        ></el-option>
      </el-select>
      <!-- </div> -->
      <el-table
        :data="tableData"
        border
        style="width: 100%"
        ref="multipleTable"
        id="allTable"
        key="tableDataInstall"
      >
        <el-table-column prop="appName" label="APP名称" v-if="this.line == 'appid'"></el-table-column>
        <el-table-column prop="adpName" label="系统广告位" v-if="this.line == 'adpid'"></el-table-column>
        <el-table-column prop="channel1Name" label="一级渠道" v-if="this.line == 'channel1'"></el-table-column>
        <el-table-column prop="channel2Name" label="二级渠道" v-if="this.line == 'channel2'"></el-table-column>
        <el-table-column prop="version" label="版本" v-if="this.line == 'versions'"></el-table-column>
        <el-table-column prop="req" label="请求量" v-if="this.custom.indexOf('req') > -1"></el-table-column>
        <el-table-column prop="requv" label="请求UV" v-if="this.custom.indexOf('reqUV') > -1"></el-table-column>
        <el-table-column prop="exp" label="展现量" v-if="this.custom.indexOf('exp') > -1"></el-table-column>
        <el-table-column prop="expuv" label="展现UV" v-if="this.custom.indexOf('expUV') > -1"></el-table-column>
        <el-table-column prop="exp_rate" label="展现率%" v-if="this.custom.indexOf('exp_rate') > -1"></el-table-column>
        <el-table-column prop="clk" label="点击量" v-if="this.custom.indexOf('clk') > -1"></el-table-column>
        <el-table-column prop="clkuv" label="点击uv" v-if="this.custom.indexOf('clkUV') > -1"></el-table-column>
        <el-table-column prop="clk_rate" label="点击率%" v-if="this.custom.indexOf('clk_rate') > -1"></el-table-column>
        <el-table-column prop="investment" label="收入" v-if="this.custom.indexOf('investment') > -1"></el-table-column>
        <el-table-column prop="cpm" label="cpm" v-if="this.custom.indexOf('cpm') > -1"></el-table-column>
        <el-table-column prop="cpc" label="cpc" v-if="this.custom.indexOf('cpc') > -1"></el-table-column>
        <el-table-column prop="expPer" label="人均曝光数" v-if="this.custom.indexOf('expPer') > -1"></el-table-column>
        <el-table-column label width="150" align="center">
          <template slot-scope="scope">
            <el-button size="mini" type="success" @click="handleEdit(scope.$index, scope.row,)">详细</el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>
    <div v-show="this.index == 2">
      <div class="header-select">
        <template>
          <el-button type="primary" icon="search" @click="goBack">返回</el-button>
          <el-button type="primary" icon="search" @click="exportExcel2">导出</el-button>
        </template>
      </div>
      <el-table
        :data="tableData2"
        border
        style="width: 100%"
        ref="multipleTable"
        id="allTable2"
        key="tableDataInstall2"
      >
        <el-table-column prop="creDay" label="日期"></el-table-column>
        <el-table-column prop="itemName" label="名称"></el-table-column>
        <el-table-column prop="req" label="请求量"></el-table-column>
        <el-table-column prop="requv" label="请求UV"></el-table-column>
        <el-table-column prop="exp" label="曝光量"></el-table-column>
        <el-table-column prop="expuv" label="expUV"></el-table-column>
        <el-table-column prop="exp_rate" label="填充率%"></el-table-column>
        <el-table-column prop="clk" label="点击量"></el-table-column>
        <el-table-column prop="clkuv" label="点击uv"></el-table-column>
        <el-table-column prop="clk_rate" label="点击率%"></el-table-column>
        <el-table-column prop="investment" label="总收入"></el-table-column>
        <el-table-column prop="cpm" label="cpm"></el-table-column>
        <el-table-column prop="cpc" label="cpc"></el-table-column>
        <el-table-column prop="expPer" label="人均曝光数"></el-table-column>
      </el-table>
    </div>
    <el-dialog title="导入" :visible.sync="newVisible" width="40%">
      <el-form ref="form" :model="form" status-icon label-width="100px">
        <el-form-item label="选择模板:">
          <el-select v-model="line" placeholder>
            <el-option
              v-for="item in optionsType"
              :key="item.value"
              :label="item.label"
              :value="item.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="上传文件">
          <el-input v-model="entityUrl" style="width:44%"></el-input>
          <el-upload
            class="upload-demo"
            :data="{type:this.line}"
            :action="upload"
            name="file"
            :on-success="handleSuccess"
          >
            <el-button size="small" type="primary">选择文件</el-button>
          </el-upload>
        </el-form-item>
      </el-form>
      <span slot="footer">
        <el-button @click="newVisible = false">取 消</el-button>
        <el-button type="primary" @click="ToLead">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import {
  flowconsumergetlist,
  consumerdatagetoneplatformpagedatabyday
} from "@/api/Api.js";

import {
  appgetall,
  adPositiongetList,
  sdkReportsumByDay,
  positionmappinglist,
  sdkReportdetail,
  sdkReportupload,
  sdkReportsum,
  flowsourcegetlist,
  AppVersionselect,
  AppChannelselect,
  quotaFlowsum,
  quotaFlowsumByDay,
  AppChannelselectOne,
  AppChannelselectTwo,
  quotaFlowdetail,
  appchannelsselectTwo
} from "@/api/Api.js";

// import Schart from "vue-schart";
// 引入 ECharts 主模块
var echarts = require("echarts/lib/echarts");

require("echarts/lib/chart/line");
require("echarts/lib/component/legend");
require("echarts/lib/component/tooltip");
require("echarts/lib/component/title");
import axios from "axios";

// 导出模块
import FileSaver from "file-saver";
import XLSX from "xlsx";
// require("echarts/lib/component/toolbox");

// import { tianqi } from "@/api/Api.js";
var time = new Date().getTime() - 24 * 60 * 60 * 1000;
var yesday = new Date(time); // 获取的是前一天日期
yesday =
  yesday.getFullYear() +
  "" +
  (yesday.getMonth() > 9
    ? yesday.getMonth() + 1
    : "0" + (yesday.getMonth() + 1)) +
  "" +
  (yesday.getDate() > 9 ? yesday.getDate() : "0" + yesday.getDate()); //字符串拼接转格式：例如2018-08-19

var uuid = new Map();
var map = new Map();
export default {
  name: "basecharts",
  components: {
    // Schart
  },
  data: () => ({
    pickerOptions1: {
      disabledDate(time) {
        return time.getTime() >= Date.now();
      }
    },
    index: 1,
    tableData2: [],
    startDay: yesday,
    endDay: yesday,
    radio: "1",
    cur_page: 1,
    total: 1,
    ps: 10,
    checkAll: false,
    isIndeterminate: true,
    flow_sueers: "",
    add_time: "",
    classify: "",
    data: [],
    tableData: [],
    form: {},
    newVisible: false,
    optionsTypeAD: [
      {
        value: "",
        label: ""
      }
    ],
    optionAppName: [
      {
        value: "",
        label: ""
      }
    ],
    optionsOs: [
      {
        value: "",
        label: ""
      }
    ],
    optionsPlatform: [
      {
        value: "",
        label: ""
      }
    ],
    optionsAppVersion: [
      {
        value: "",
        label: ""
      }
    ],
    optionsAppChannel: [
      {
        value: "",
        label: ""
      }
    ],
    optionsAppChannelTwo: [],
    optionsType: [
      {
        value: 1,
        label: "广点通"
      },
      {
        value: 2,
        label: "头条"
      },
      {
        value: 3,
        label: "点冠"
      },
      {
        value: 4,
        label: "百度"
      },
      {
        value: 5,
        label: "360"
      },
      {
        value: 6,
        label: "通用"
      }
    ],
    optionsLine: [
      {
        value: "appid",
        label: "APP名称"
      },
      {
        value: "adpid",
        label: "系统广告位"
      },
      {
        value: "channel1",
        label: "一级渠道"
      },
      {
        value: "channel2",
        label: "二级渠道"
      },
      {
        value: "versions",
        label: "版本"
      }
    ],
    optionsCustom: [
      {
        value: "req",
        label: "请求量"
      },
      {
        value: "reqUV",
        label: "请求UV"
      },
      {
        value: "exp",
        label: "展现量"
      },
      {
        value: "expUV",
        label: "展现UV"
      },
      {
        value: "exp_rate",
        label: "展现率"
      },
      {
        value: "clk",
        label: "点击量"
      },
      {
        value: "clkUV",
        label: "点击uv"
      },
      {
        value: "clk_rate",
        label: "点击率"
      },
      {
        value: "investment",
        label: "收入"
      },
      {
        value: "cpm",
        label: "cpm"
      },
      {
        value: "cpc",
        label: "cpc"
      },
      {
        value: "expPer",
        label: "人均曝光数"
      }
    ],
    appIds: [],
    systemAD: [],
    AppVersion: [],
    AppChannel: [],
    AppChannelTwo: "",
    line: "",
    custom: ["req", "exp", "exp_rate", "clk", "clk_rate", "investment"],
    upload: sdkReportupload,
    entityUrl: "",
    titleList: [
      "请求量",
      "展现量",
      "点击量",
      "展现率%",
      "点击率%",
      "收入",
      "cpm",
      "cpc"
    ],
    history: []
  }),

  created() {
    this.getList();
    this.getAppName();
    this.getOSName();
  },
  mounted() {
    /*ECharts图表*/
    var myChart = echarts.init(document.getElementById("main"));
    myChart.setOption({
      title: {
        text: "流量数据"
      },
      toolbox: {
        show: true,
        x: "950", //下载的字体显示不全
        feature: {
          dataView: { show: false, readOnly: false },
          magicType: { show: true, type: ["line", "bar"] },
          restore: { show: true },
          saveAsImage: { show: true }
        }
      },

      tooltip: {
        trigger: "axis",
        axisPointer: {
          type: "cross",
          label: {
            backgroundColor: "#6a7985"
          }
        }
      },
      legend: {
        data: [
          "请求量",
          "展现量",
          "点击量",
          "展现率%",
          "点击率",
          "收入",
          "CPM",
          "CPC"
        ]
        // y: 'bottom', // 'center' | 'bottom' | {number}
        // padding: [100,0,0,0],
      },
      toolbox: {
        feature: {
          saveAsImage: {}
        }
      },
      grid: {
        left: "1%",
        right: "4%",
        bottom: "3%",
        containLabel: true
      },
      xAxis: [
        {
          type: "category",
          boundaryGap: false,
          data: ["周一", "周二", "周三", "周四", "周五", "周六", "周日"]
        }
      ],
      yAxis: [
        {
          type: "value"
        }
      ],
      series: [
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
    getPageData() {
      this.history = JSON.parse(localStorage.getItem("this.history")); //使用getItem方法的前提是，你再自己需要的地方使用了setItem方法
    },
    handCustom(value) {
      console.log(value);
      // var set = new set()
    },
    deleteTag() {
      this.systemAD = [];
    },
    goBack() {
      this.index = 1;
    },
    handleEdit(index, row) {
      this.idx = index;
      let item = row;
      let par = {
        id: item.itemId,
        sDate: this.startDay,
        eDate: this.endDay,
        flowId: this.classify,
        appids: this.appIds.join(","),
        adpids: this.systemAD.join(","),
        channels: this.AppChannel.join(","),
        versions: this.AppVersion.join(","),
        group: this.line
      };
      quotaFlowdetail(par).then(res => {
        let data = res.data;
        this.tableData2 = data.data;
        this.index = 2;
      });
    },
    // 跳转
    // handleEdit(index,row){
    //      this.idx = index;
    //      let item = row;
    //      this.$router.push({
    //          path:'/datareport/flowreport/addetailedreport',
    //          query:{
    //              id:item.itemId,
    //              sDate:this.startDay,
    //              eDate:this.endDay,
    //              flowId:this.classify,
    //              appids:this.appIds.join(','),
    //              adpids:this.systemAD.join(','),
    //              channels:this.AppChannel.join(','),
    //              versions:this.AppVersion.join(','),
    //              group:this.line,
    //              }
    //          })
    // },
    // app名称下拉框
    getAppName() {
      appgetall().then(res => {
        console.log(res);
        let data = res.data;
        if (data.code != "A000000") {
          this.$message.error(data.message);
          return;
        }
        this.optionAppName = data.data;
        this.optionAppName.forEach(element => {
          uuid.set(element.appId, element.id);
        });
        console.log(uuid);
      });
    },
    // 系统广告位下拉框
    getOSName() {
      let par = {
        status: 1
      };
      adPositiongetList(par).then(res => {
        console.log(res);
        let data = res.data;
        if (data.code != "A000000") {
          this.$message.error(data.message);
          return;
        }
        this.optionsOs = data.data.data;
      });
    },
    getAdPosition() {
      let arr = [];
      this.appIds.forEach(element => {
        arr.push(uuid.get(element));
      });
      let par = {
        status: 1,
        appIds: arr.join(",")
      };

      adPositiongetList(par).then(res => {
        console.log(res);
        let data = res.data;
        if (data.code != "A000000") {
          this.$message.error(data.message);
          return;
        }
        this.optionsOs = data.data.data;
      });
    },
    // 版本号拉框
    getAppVersion() {
      AppVersionselect().then(res => {
        console.log(res);
        let data = res.data;
        if (data.code != "A000000") {
          this.$message.error(data.message);
          return;
        }
        this.optionsAppVersion = data.data;
      });
    },
    // 一级渠道号拉框
    getAppChannel() {
      let params = {
        name: ""
      };
      AppChannelselectOne(params).then(res => {
        console.log(res);
        let data = res.data;
        if (data.code != "A000000") {
          this.$message.error(data.message);
          return;
        }
        this.optionsAppChannel = data.data;
        data.data.forEach(element => {
          map.set(element.cname, element.name);
        });
      });
    },
    // 模糊搜索
    querySearch(queryString, cb) {
      let arr = [];
      this.AppChannel.forEach(element => {
        arr.push(map.get(element));
      });
      let url = "?name=" + this.AppChannelTwo;
      appchannelsselectTwo(url, arr)
        .then(res => {
          for (var i = 0; i < res.data.data.length; i++) {
            res.data.data[i].name =
              res.data.data[i].name + " - " + res.data.data[i].cname;
          }
          cb(res.data.data);
        })
        .catch(err => {
          console.log(err);
        });
    },
    // 添加
    handleSelect(item) {
      console.log(item);
      let params = {
        name: item.name,
        cname: item.cname,
        id: item.id
      };
      this.optionsAppChannelTwo.push(params);
      this.AppChannelTwo = "";
      console.log(this.optionsAppChannelTwo);
    },
    handleClose(tag) {
      console.log(tag);
      this.optionsAppChannelTwo.splice(
        this.optionsAppChannelTwo.indexOf(tag),
        1
      );
    },
    edit() {
      this.line = "";
      this.entityUrl = "";
      this.newVisible = true;
    },
    ToLead() {
      this.newVisible = false;
    },
    handleSuccess(res, file) {
      let data = res;
      if (data.code != "A000000") {
        this.$message.error(data.message);
        return;
      } else {
        this.entityUrl = data.data.url;
      }
      this.$forceUpdate();
    },
    // 分页导航
    handleCurrentChange(val) {
      console.log(val);
      this.cur_page = val;
      this.getList();
    },

    getList() {
      flowsourcegetlist().then(res => {
        console.log(res);
        let data = res.data;
        this.optionsTypeAD = data.data;
        this.classify = data.data[0].mediaUuid;
        // this.types = data.data
      });
    },
    GetSeach() {
      this.search();
    },
    //   搜索
    search() {
      if (!this.classify) {
        this.$message("名称不能为空");
        return;
      }
      let M7nuyi = "M7nuyi";
      var beginDt = new Date();
      var echartsDate = [];
      let arr = [];
      this.optionsAppChannelTwo.forEach(element => {
        arr.push(element.cname);
      });
      let params = {
        sDate: this.startDay,
        eDate: this.endDay,
        flowId: M7nuyi,
        appids: this.appIds.join(","),
        adpids: this.systemAD.join(","),
        versions: this.AppVersion.join(","),
        channels1: this.AppChannel.join(","),
        channels2: arr.join(","),
        group: this.line
      };
      quotaFlowsumByDay(params).then(res => {
        console.log(res);
        let data = res.data;
        if (data.code != "A000000") {
          this.$message.error(data.message);
        }
        console.log(data);

        var req = [];
        var reqUV = [];
        var exp = [];
        var expUV = [];
        var exp_rate = [];
        var clk = [];
        var clk_rate = [];
        var expPer = [];
        var investment = [];
        var cost = [];
        var cpm = [];
        var cpc = [];
        var ecpm = [];
        var clkDesire = [];
        for (var item of data.data) {
          if (item.creDay) {
            echartsDate.push(item.creDay); // 名称
            req.push(item.req); //请求量
            reqUV.push(item.reqUV); //请求UV
            exp.push(item.exp); //曝光
            expUV.push(item.expUV); // 曝光uv
            exp_rate.push(item.exp_rate); //填充率
            clk.push(item.clk); //点击
            clk_rate.push(item.clk_rate); //点击率
            investment.push(item.investment); //点击收入
            cpm.push(item.cpm); //cpm
            cpc.push(item.cpc); //cpc
          }
        }

        // console.log(reqs);console.log(clks);

        var myChart = echarts.init(document.getElementById("main"));
        myChart.setOption({
          title: {
            // text: '客户数据'
          },
          tooltip: {
            trigger: "axis"
          },
          legend: {
            data: this.titleList
          },
          grid: {
            left: "3%",
            right: "4%",
            bottom: "3%",
            containLabel: true
          },
          toolbox: {
            feature: {
              saveAsImage: {}
            }
          },
          xAxis: {
            type: "category",
            boundaryGap: false,
            data: echartsDate
          },
          yAxis: {
            type: "value"
          },
          series: [
            {
              name: this.titleList[0],
              type: "line",
              // stack: '总量',
              data: req
            },
            {
              name: this.titleList[1],
              type: "line",
              // stack: '总量',
              data: exp
            },
            {
              name: this.titleList[2],
              type: "line",
              // stack: '总量',
              data: clk
            },
            {
              name: this.titleList[3],
              type: "line",
              // stack: '总量',
              data: exp_rate
            },
            {
              name: this.titleList[4],
              type: "line",
              // stack: '总量',
              data: clk_rate
            },
            {
              name: this.titleList[5],
              type: "line",
              // stack: '总量',
              data: investment
            },
            {
              name: this.titleList[6],
              type: "line",
              // stack: '总量',
              data: cpm
            },
            {
              name: this.titleList[7],
              type: "line",
              // stack: '总量',
              data: cpc
            }
          ]
        });
      });
      let par = {
        sDate: this.startDay,
        eDate: this.endDay,
        flowId: M7nuyi,
        appids: this.appIds.join(","),
        adpids: this.systemAD.join(","),
        versions: this.AppVersion.join(","),
        channels1: this.AppChannel.join(","),
        channels2: arr.join(","),
        group: this.line
      };
      quotaFlowsum(par).then(res => {
        let data = res.data;
        this.tableData = data.data;
      });
    },

    //   导出table方法
    exportExcel() {
      /* generate workbook object from table */

      var wb = XLSX.utils.table_to_book(document.querySelector("#allTable"));

      // var wb1 = XLSX.utils.table_to_book(document.querySelector('#table'))

      /* get binary string as output */

      var wbout = XLSX.write(wb, {
        bookType: "xlsx",
        bookSST: true,
        type: "array"
      });
      // var wbout1 = XLSX.write(wb1, { bookType: 'xlsx', bookSST: true, type: 'array' })
      try {
        FileSaver.saveAs(
          new Blob([wbout], { type: "application/octet-stream" }),
          "流量报表.xlsx"
        );

        // FileSaver.saveAs(new Blob([wbout1], { type: 'application/octet-stream' }), '广告平台分日数据.xlsx')
      } catch (e) {
        if (typeof console !== "undefined") console.log(e, wbout);
      }

      return wbout;
    },
    exportExcel2() {
      /* generate workbook object from table */
      var wb = XLSX.utils.table_to_book(document.querySelector("#allTable2"));
      // var wb1 = XLSX.utils.table_to_book(document.querySelector('#table'))
      /* get binary string as output */
      var wbout = XLSX.write(wb, {
        bookType: "xlsx",
        bookSST: true,
        type: "array"
      });
      // var wbout1 = XLSX.write(wb1, { bookType: 'xlsx', bookSST: true, type: 'array' })
      try {
        FileSaver.saveAs(
          new Blob([wbout], { type: "application/octet-stream" }),
          "流量源分日数据.xlsx"
        );
        // FileSaver.saveAs(new Blob([wbout1], { type: 'application/octet-stream' }), '广告平台分日数据.xlsx')
      } catch (e) {
        if (typeof console !== "undefined") console.log(e, wbout);
      }
      return wbout;
    }
  }
};
</script>

<style scoped>
.block {
  margin-bottom: 3%;
}
.flot_right {
  float: right;
}
.header-select {
  margin-bottom: 20px;
}
.mr10 {
  width: 150px;
}
.border {
  padding-top: 50px;
}
.handle-box {
  margin-bottom: 20px;
}
.div {
  margin-top: 15px;
}
</style>
<style >
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