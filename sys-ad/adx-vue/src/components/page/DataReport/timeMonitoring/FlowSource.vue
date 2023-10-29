<template>
  <div class="box">
    <div class="block">
      <el-form ref="form" :model="form" status-icon label-width="100px">
        <el-form-item label="系统广告位:">
          <el-autocomplete
            style="width:300px"
            class="inline-input"
            v-model="posUuid"
            value-key="positionAppName"
            :fetch-suggestions="querySearchName"
            placeholder="请输入广告位名称"
            @select="handleSelectName"
          ></el-autocomplete>
          <el-button type="primary" @click="BtnEchers">搜 索</el-button>
        </el-form-item>
        <!-- <el-form-item label="流量源:" v-if="flowSource == 1"> -->
        <!-- <el-select v-model="classify" placeholder="请选择">
                        <el-option
                            v-for="item in optionsType"
                            :key="item.id"
                            :label="item.mediaName"
                            :value="item.id">
                        </el-option>
        </el-select>-->
        <!-- </el-form-item> -->
        <!-- <el-form-item label="流量源:" v-if="flowSource == 2">
                         <el-checkbox :indeterminate="isIndeterminate" v-model="checkAll" @change="handleCheckAllChange">全选</el-checkbox>
                         <el-checkbox-group v-model="checkList" @change="handleCheckedCitiesChange">
                           <el-checkbox v-for="(item,index) in types" :key="index" :label="item.id">{{item.mediaName}}</el-checkbox>
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
        </el-form-item>-->
      </el-form>
    </div>

    <!-- ECharts图表测试 -->
    <div id="charts" style="width:'100%',height:'500px'">
      <div id="main" :style="{width:'100%',height:'500px'}"></div>
    </div>
    <el-table
      :data="tableData"
      border
      style="width: 100%"
      ref="multipleTable"
      v-if="this.flowSource == 1||this.flowSource == 2"
    >
      <!--<el-table-column prop="fsName" label="流量源">-->
      <!--</el-table-column>-->
      <el-table-column prop="req" label="请求量"></el-table-column>
      <!-- <el-table-column prop="bid" label="填充量">
      </el-table-column>-->
      <el-table-column prop="exp" label="展现量"></el-table-column>
      <el-table-column prop="clk" label="点击量"></el-table-column>
      <!-- <el-table-column prop="bid_rate" label="填充率">
      </el-table-column>-->
      <el-table-column prop="exp_rate" label="展现率%"></el-table-column>
      <el-table-column prop="clk_rate" label="点击率%"></el-table-column>
      <el-table-column prop="investment" label="收入"></el-table-column>
    </el-table>
  </div>
</template>

<script>
import { flowsourcegetlist, adPositiongetList } from "@/api/Api.js";
import {
  flowdatagetoneflowdatabyhour,
  flowdatagetflowdatabyhourandflow,
  flowdatagetflowdataonedaybyfs
} from "@/api/Api.js";
// import Schart from "vue-schart";
// 引入 ECharts 主模块
var echarts = require("echarts/lib/echarts");

require("echarts/lib/chart/line");
require("echarts/lib/component/legend");
require("echarts/lib/component/tooltip");
require("echarts/lib/component/title");

const options = [];

export default {
  name: "basecharts",
  components: {
    // Schart
  },
  data: () => ({
    radio: "",
    tableData: [],
    checkAll: false,
    isIndeterminate: true,
    flowSource: "",
    isAll: "",
    checkList: [],
    types: null,
    checkAll: false,
    from: {},
    classify: "",
    optionsType: [
      {
        value: "",
        label: ""
      }
    ],
    value: "选项1",
    value2: "",
    value3: "",
    options3: [
      {
        value: "req",
        label: "请求量"
      },
      {
        value: "exp",
        label: "展现量"
      },
      {
        value: "exp_rate",
        label: "展现率%"
      },
      {
        value: "clk",
        label: "点击量"
      },
      {
        value: "clk_rate",
        label: "点击率"
      }
    ],
    titleList: [
      "请求量",
      "展现量",
      "点击量",
      "展现率%",
      "点击率%",
      "收入",
      "CPM",
      "CPC"
    ],
    form: {},
    index: 1,
    optionsOs: [
      {
        value: "",
        label: ""
      }
    ],
    posUuid: ""
  }),

  created() {},
  methods: {
    // 系统广告位下拉框
    //模糊名字
    querySearchName(queryString, cb) {
      let params = {
        status: 1,
        name: this.posUuid,
        ps:20
      };
      adPositiongetList(params).then(res => {
        let data = res.data;
        console.log(data.data.data);
        // 调用 callback 返回建议列表的数据
        cb(data.data.data);
        if (data.code != "A000000") {
          this.$message.error(data.message);
        }
      });
    },
    handleSelectName(item) {
      this.id = item.uuid;
    },
    //   流量源名称
    getList() {
      this.index = 1;
      flowsourcegetlist().then(res => {
        let data = res.data;
        this.optionsType = data.data;
        this.types = data.data;
        data.data.forEach(element => {
          options.push(element.id);
        });
        this.classify = data.data[0].id;
      });
    },
    //   全选
    handleCheckAllChange(val) {
      this.checkList = val ? options : [];
      this.isIndeterminate = false;
    },
    handleCheckedCitiesChange(value) {
      let checkedCount = value.length;
      this.checkAll = checkedCount === options.length;
      this.isIndeterminate = checkedCount > 0 && checkedCount < options.length;
    },
    //  查询流量源
    BtnEchers() {
      if(this.posUuid == ''){
          this.id = ''
      }
      this.flowSource = 1;
      var beginDt = new Date();
      var echartsDate = [];
      //获取当前日期
      this.date = new Date();                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                 
      var m =
        this.date.getMonth() + 1 < 10
          ? "0" + (this.date.getMonth() + 1)
          : this.date.getMonth() + 1;
      var d =
        this.date.getDate() < 10
          ? "0" + this.date.getDate()
          : this.date.getDate();
      this.eDay = this.date.getFullYear() + "" + m + "" + d;
      // if(this.flowSource == 1){
      let params = {
        startDate: this.eDay,
        endDate: this.eDay,
        mediaId: 1,
        isAll: 0,
        posUuid: this.id
      };

      flowdatagetoneflowdatabyhour(params).then(res => {
        let data = res.data;
        if (data.code != "A000000") {
          this.$message.error(data.message);
        }
        console.log(data);

        var reqs = [];
        var exp = [];
        var clk = [];
        var exp_rate = [];
        var clk_rate = [];
        var cpm = [];
        var cpc = [];
        var cost = [];
        for (var item of data.data) {
          echartsDate.push(item.creHour); // 日期
          reqs.push(item.req); //请求量
          // bid.push(item.bid); //填充量
          exp.push(item.exp); //展现量
          clk.push(item.clk); //点击量
          // bid_rate.push(item.bid_rate);//填充率
          exp_rate.push(item.exp_rate); //曝光率
          clk_rate.push(item.clk_rate); //点击率
          cost.push(item.investment); //收入
          cpm.push(item.cpm); //cpm
          cpc.push(item.cpc); //cpc
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
              data: reqs
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
              data: cost
            }
          ]
        });
        let params = {
          mediaId: 1,
          startDate: this.eDay,
          endDate: this.eDay,
          isAll: 1,
          posUuid: this.id
        };
        flowdatagetoneflowdatabyhour(params).then(res => {
          let data = res.data;
          this.tableData = data.data;
          // this.total = data.data.totalItemNum;
          // console.log(res)
        });
      });
    }
  },
// 初始化
  mounted() {
    var myChart = echarts.init(document.getElementById("main"));
    myChart.setOption({
      title: {
        text: ""
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
        data: []
      },
      yAxis: {
        type: "value"
      },
      series: [
        {
          name: this.titleList[0],
          type: "line",
          // stack: '总量',
          data: []
        },
        {
          name: this.titleList[1],
          type: "line",
          // stack: '总量',
          data: []
        },
        {
          name: this.titleList[2],
          type: "line",
          // stack: '总量',
          data: []
        },
        {
          name: this.titleList[3],
          type: "line",
          // stack: '总量',
          data: []
        },
        {
          name: this.titleList[4],
          type: "line",
          // stack: '总量',
          data: []
        },
        {
          name: this.titleList[5],
          type: "line",
          // stack: '总量',
          data: []
        }
      ]
    });
  }
};
</script>

<style scoped>
.block {
  margin-bottom: 3%;
}
</style>
