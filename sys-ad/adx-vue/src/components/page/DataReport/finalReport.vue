
<template>
  <div class="box">
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
          <el-button type="primary" icon="search" @click="search">搜索</el-button>
          <el-button type="primary" icon="search" @click="edit">导入</el-button>
          <el-select v-model="line" clearable placeholder="分列显示">
            <el-option
              v-for="item in optionsLine"
              :key="item.value"
              :label="item.label"
              :value="item.value"
            ></el-option>
          </el-select>
        </template>
      </div>
      <div>
        <el-select
          v-model="appIds"
          multiple
          placeholder="APP名称"
          class="handle-input"
          @focus="getAppName"
          @change="getOSName"
          @remove-tag="deleteTag2"
        >
          <el-option
            v-for="item in optionAppName"
            :key="item.appId"
            :label="item.appName"
            :value="item.appId"
          ></el-option>
        </el-select>
        <el-select
          v-model="systemAD"
          multiple
          placeholder="平台"
          @focus="getOSName"
          @change="getplatformAD"
          @remove-tag="deleteTag"
        >
          <el-option
            v-for="item in optionsOs"
            :key="item.consumerUuid"
            :label="item.consumerName"
            :value="item.consumerUuid"
          ></el-option>
        </el-select>
        <el-select v-model="platformAD" multiple placeholder="平台广告位" @focus="getplatformAD">
          <el-option
            v-for="item in optionsPlatform"
            :key="item.consumerPositionId"
            :label="item.consumerPositionName"
            :value="item.consumerPositionId"
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
    <div class="handle-box">
      <el-select v-model="custom" multiple placeholder="自定义显示" @change="handCustom">
        <el-option
          v-for="item in optionsCustom"
          :key="item.value"
          :label="item.label"
          :value="item.value"
        ></el-option>
      </el-select>
      <el-button type="primary" icon="search" @click="exportExcel">导出</el-button>
    </div>

    <el-table :data="tableData" border style="width: 100%" ref="multipleTable" id="allTable">
      <el-table-column prop="itemId" label="ID"></el-table-column>
      <el-table-column prop="flowConName" label="广告平台" v-if="this.line == 'flow_con'"></el-table-column>
      <el-table-column prop="appName" label="APP名称" v-if="this.line == 'app'"></el-table-column>
      <el-table-column prop="adpName" label="系统广告位" v-if="this.line == 'pid'"></el-table-column>
      <el-table-column prop="positionName" label="平台广告位" v-if="this.line == 'position_id'"></el-table-column>
      <el-table-column prop="exp" label="曝光量" v-if="this.custom.indexOf('exp') > -1"></el-table-column>
      <el-table-column prop="fr" label="填充率%" v-if="this.custom.indexOf('fr') > -1"></el-table-column>
      <el-table-column prop="clk" label="点击量" v-if="this.custom.indexOf('clk') > -1"></el-table-column>
      <el-table-column prop="ctr" label="点击率%" v-if="this.custom.indexOf('ctr') > -1"></el-table-column>
      <el-table-column prop="income" label="收入" v-if="this.custom.indexOf('income') > -1"></el-table-column>
      <el-table-column prop="cpm" label="eCPM" v-if="this.custom.indexOf('cpm') > -1"></el-table-column>
      <el-table-column prop="cpc" label="CPC" v-if="this.custom.indexOf('cpc') > -1"></el-table-column>
      <el-table-column prop="expIncome" label="曝光收入" v-if="this.custom.indexOf('expIncome') > -1"></el-table-column>
      <el-table-column prop="clkIncome" label="点击收入" v-if="this.custom.indexOf('clkIncome') > -1"></el-table-column>
      <el-table-column label width="150" align="center">
        <template slot-scope="scope">
          <el-button size="mini" type="success" @click="handleEdit(scope.$index, scope.row,)">详细</el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-dialog title="导入" :visible.sync="newVisible" width="40%">
      <el-form ref="form" :model="form" status-icon label-width="100px">
        <el-form-item label="模板下载">
          <a
            href="https://iwanvi.oss-cn-beijing.aliyuncs.com/document/reportTemp/%E7%BB%93%E7%AE%97%E6%8A%A5%E8%A1%A8.xls"
          >
            <el-button type="primary">下载</el-button>
          </a>
        </el-form-item>
        <el-form-item label="上传文件">
          <!-- <el-input v-model="entityUrl"  style="width:44%"></el-input> -->
          <el-upload class="upload-demo" :action="upload" name="file" :on-success="handleSuccess">
            <el-button size="small" type="primary">选择文件</el-button>
          </el-upload>
        </el-form-item>
      </el-form>
      <span slot="footer">
        <!-- <el-button @click="newVisible = false">取 消</el-button> -->
        <!-- <el-button type="primary" @click="ToLead">确 定</el-button> -->
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
  settleupload,
  sdkReportsum,
  flowsourcegetlist,
  AppVersionselect,
  AppChannelselect,
  quotaFlowsum,
  flowconsumergetpage,
  settlesumByDay,
  settlesum,
  flowconsumerlistbyapp,
  appchannelsselectTwo
} from "@/api/Api.js";

// import Schart from "vue-schart";
// 引入 ECharts 主模块
var echarts = require("echarts/lib/echarts");

require("echarts/lib/chart/line");
require("echarts/lib/component/legend");
require("echarts/lib/component/tooltip");
require("echarts/lib/component/title");

// 导出模块
import FileSaver from "file-saver";
import XLSX from "xlsx";
// require("echarts/lib/component/toolbox");

// import { tianqi } from "@/api/Api.js";
var id = new Map();
var uuids = new Map();
export default {
  name: "basecharts",
  components: {
    // Schart
  },
  data: () => ({
    pickerOptions1: {
      disabledDate(time) {
        return time.getTime() > Date.now();
      }
    },
    optionsAppChannelTwo: [],
    AppChannelTwo: "",
    AppChannel: [],
    startDay: new Date().Format("yyyyMMdd"),
    endDay: new Date().Format("yyyyMMdd"),
    radio: "1",
    cur_page: 1,
    total: 1,
    ps: 10,
    checkAll: false,
    isIndeterminate: true,
    flow_sueers: "",
    add_time: "",
    classify: [],
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
    optionsLine: [
      {
        value: "flow_con",
        label: "广告平台"
      },
      {
        value: "app",
        label: "APP名称"
      },
      {
        value: "channel",
        label: "渠道"
      },
      {
        value: "position_id",
        label: "平台广告位"
      }
    ],
    optionsCustom: [
      {
        value: "exp",
        label: "曝光量"
      },
      {
        value: "fr",
        label: "填充率"
      },
      {
        value: "clk",
        label: "点击量"
      },
      {
        value: "ctr",
        label: "点击率"
      },
      {
        value: "income",
        label: "收入"
      },
      {
        value: "cpm",
        label: "eCPM"
      },
      {
        value: "cpc",
        label: "CPC"
      },
      {
        value: "expIncome",
        label: "曝光收入"
      },
      {
        value: "clkIncome",
        label: "点击收入"
      }
    ],
    appIds: [],
    systemAD: [],
    platformAD: [],
    line: "",
    custom: ["exp", "fr", "clk", "ctr", "income"],
    upload: settleupload,
    entityUrl: "",
    titleList: [
      "展现量",
      "填充率",
      "点击量",
      "点击率",
      "收入",
      "eCPM",
      "CPC",
      "曝光收入",
      "点击收入"
    ]
  }),

  created() {
    this.getList();
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
    handCustom(value) {
      console.log(value);
      // var set = new set()
    },
    // 跳转
    handleEdit(index, row) {
      this.idx = index;
      let item = row;
      this.$router.push({
        path: "/datareport/finalReportIn",
        query: {
          id: item.itemId,
          sDate: this.startDay,
          eDate: this.endDay,
          flowCons: this.systemAD.join(","),
          flowPosIds: this.platformAD.join(","),
          appIds: this.appIds.join(","),
          group: this.line
        }
      });
    },
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
        data.data.forEach(element => {
          uuids.set(element.appId, element.id);
        });
        console.log(uuids);
      });
    },
    deleteTag2() {
      this.systemAD = [];
      this.platformAD = [];
    },
    deleteTag() {
      this.platformAD = [];
    },
    // 模糊搜索
    querySearch(queryString, cb) {
      let url = "?name=" + queryString;
      appchannelsselectTwo(url, [])
        .then(res => {
          for (var i = 0; i < res.data.data.length; i++) {
            res.data.data[i].name =
              res.data.data[i].name + " - " + res.data.data[i].cname;
          }
          cb(res.data.data);
          // this.optionsAppChannelTwo = res.data.data
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
    },
    handleClose(tag) {
      console.log(tag);
      this.optionsAppChannelTwo.splice(
        this.optionsAppChannelTwo.indexOf(tag),
        1
      );
    },
    // 平台下拉框
    getOSName() {
      let arrs = [];
      this.appIds.forEach(element => {
        arrs.push(uuids.get(element));
      });
      let par = {
        appIds: arrs.join(",")
      };
      flowconsumerlistbyapp(par).then(res => {
        console.log(res);
        let data = res.data;
        if (data.code != "A000000") {
          this.$message.error(data.message);
          return;
        }
        this.optionsOs = data.data;
        this.optionsOs.forEach(element => {
          id.set(element.consumerUuid, element.id);
        });
      });
    },

    // 平台广告位下拉框
    getplatformAD() {
      let arr = [];
      this.systemAD.forEach(element => {
        arr.push(id.get(element));
      });
      let params = {
        consumerIds: arr.join(",")
      };
      positionmappinglist(params).then(res => {
        console.log(res);
        let data = res.data;
        if (data.code != "A000000") {
          this.$message.error(data.message);
          return;
        }
        this.optionsPlatform = data.data;
      });
    },

    edit() {
      this.line = "";
      this.entityUrl = "";
      this.newVisible = true;
    },
    ToLead() {
      //  orderPutupdate(params).then(res => {
      //           console.log(res);
      //           let data = res.data;
      //           if (data.code != "A000000") {
      //             this.$message.error(data.message);
      //           }
      this.newVisible = false;
      // });
    },
    handleSuccess(res, file) {
      let data = res;
      if (data.code != "A000000") {
        this.$message.error(data.message);
        return;
      } else {
        // this.entityUrl = data.data.url;
        this.newVisible = false;
        this.$$message("上传成功");
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
        // this.types = data.data
      });
    },
    //   搜索
    search() {
      if (!this.classify) {
        this.$message("名称不能为空");
        return;
      }
      let arr = [];
      this.optionsAppChannelTwo.forEach(element => {
        arr.push(element.id);
      });
      var beginDt = new Date();
      var echartsDate = [];
      let params = {
        sDate: this.startDay,
        eDate: this.endDay,
        flowCons: this.systemAD.join(","),
        flowPosIds: this.platformAD.join(","),
        apps: this.appIds.join(","),
        group: this.line,
        channels: arr.join(",")
      };
      settlesumByDay(params).then(res => {
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
        var fr = [];
        var clk = [];
        var ctr = [];
        var expPer = [];
        var expIncome = [];
        var clkIncome = [];
        var cost = [];
        var cpm = [];
        var cpc = [];
        var ecpm = [];
        var clkDesire = [];
        var income = [];
        for (var item of data.data) {
          if (item.date) {
            echartsDate.push(item.date); // 名称
            req.push(item.req); //请求量
            reqUV.push(item.reqUV); //请求UV
            exp.push(item.exp); //曝光
            expUV.push(item.expUV); // 曝光uv
            fr.push(item.fr); //填充率
            clk.push(item.clk); //点击
            ctr.push(item.ctr); //点击率
            expPer.push(item.expPer); //人均曝光
            expIncome.push(item.expIncome); //曝光收入
            clkIncome.push(item.clkIncome); //点击收入
            income.push(item.income); //点击收入
            cpm.push(item.cpm); //cpm
            cpc.push(item.cpc); //cpc
            ecpm.push(item.ecpm); //ecpm
            clkDesire.push(item.clkDesire); //点击意愿
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
              data: exp
            },
            {
              name: this.titleList[1],
              type: "line",
              // stack: '总量',
              data: fr
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
              data: ctr
            },
            {
              name: this.titleList[4],
              type: "line",
              // stack: '总量',
              data: income
            },
            {
              name: this.titleList[5],
              type: "line",
              // stack: '总量',
              data: cpm
            },
            {
              name: this.titleList[6],
              type: "line",
              // stack: '总量',
              data: cpc
            },
            {
              name: this.titleList[7],
              type: "line",
              // stack: '总量',
              data: expIncome
            },
            {
              name: this.titleList[8],
              type: "line",
              // stack: '总量',
              data: clkIncome
            }
          ]
        });
        let par = {
          sDate: this.startDay,
          eDate: this.endDay,
          flowCons: this.systemAD.join(","),
          flowPosIds: this.platformAD.join(","),
          apps: this.appIds.join(","),
          group: this.line,
          channels: arr.join(",")
        };
        settlesum(par).then(res => {
          console.log(res);
          let data = res.data;
          this.tableData = data.data;
        });
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
          "广告平台数据.xlsx"
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
.el-tag--small {
  margin-top: 20px;
}
</style>