<template>
  <div class="table">
    <div class="container">
      <div class="handle-box">
        <el-input v-model="select_PutName" placeholder="请输入投放名称" class="handle-input mr10"></el-input>
        <el-select v-model="select_platform" placeholder="广告平台">
          <el-option
            v-for="item in optionsTypeAD2"
            :key="item.platformId"
            :label="item.platformName"
            :value="item.platformId"
          ></el-option>
        </el-select>
        <!-- <el-select v-model="select_APP"  placeholder="app">
                    <el-option
                        v-for="item in options_APP"
                        :key="item.appId"
                        :label="item.appName"
                        :value="item.appId">
                    </el-option>
        </el-select>-->
        <el-select v-model="value" placeholder="状态" class="handle-input mr10">
          <el-option
            v-for="item in options_"
            :key="item.value"
            :label="item.label"
            :value="item.value"
          ></el-option>
        </el-select>
        <el-button type="primary" icon="search" @click="search">搜索</el-button>
        <el-button type="primary" icon="search" @click="create" v-if="this.readonly !=1">新建投放</el-button>
      </div>
      <el-table :data="tableData" border style="width: 100%" ref="multipleTable">
        <el-table-column prop="id" label="序号" align="center"></el-table-column>
        <el-table-column prop="allotmentName" label="投放名称" align="center"></el-table-column>
        <el-table-column prop="plaName" label="广告平台" align="center"></el-table-column>
        <el-table-column prop="appName" label="APP" align="center"></el-table-column>
        <el-table-column prop="costTypeName" label="广告位数量" align="center">
          <template slot-scope="scope">
            <el-button size="mini" type="success" @click="look(scope.$index, scope.row)">查看</el-button>
          </template>
        </el-table-column>
        <el-table-column prop label="运行状态" align="center" v-if="this.readonly !=1">
          <template slot-scope="scope">
            <el-switch
              v-model="scope.row.status"
              :active-value="1"
              :inactive-value="0"
              @change="changeStatus($event,scope.row)"
            ></el-switch>
          </template>
        </el-table-column>
        <el-table-column label="操作" width="150" align="center">
          <template slot-scope="scope">
            <el-button size="mini" type="success" @click="handleEdit(scope.$index, scope.row)">编辑</el-button>
            <el-button size="mini" type="success" @click="handleDelete(scope.$index, scope.row)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
      <div class="pagination">
        <el-pagination
          @current-change="handleCurrentChange"
          layout="total,prev, pager, next,jumper"
          :total="total"
          :current-page="cur_page"
          :page-size="ps"
          ref="pagination"
        ></el-pagination>
      </div>
    </div>

    <!-- 编辑弹出框 -->
    <el-dialog title="编辑" :visible.sync="editVisible" width="80%">
      <el-tabs v-model="activeName" @tab-click="handleClick">
        <el-tab-pane label="基本信息" name="second_1">
          <el-form
            ref="form"
            :model="form"
            status-icon
            label-width="100px"
            :label-position="labelPosition"
          >
            <el-form-item label="* 投放名称:">
              <el-input v-model="form.putName" style="width:40%"></el-input>
            </el-form-item>
            <el-form-item label="* APP:">
              <el-select v-model="form.select_APP" @change="seachAdPositin" disabled>
                <el-option
                  v-for="item in options_APP"
                  :key="item.appId"
                  :label="item.appName"
                  :value="item.appId"
                ></el-option>
              </el-select>
            </el-form-item>
            <el-form-item label="* 广告平台:">
              <el-select v-model="form.select_AD" @change="seachAdPositin" disabled>
                <el-option
                  v-for="item in optionsTypeAD"
                  :key="item.platformId"
                  :label="item.platformName"
                  :value="item.platformId"
                ></el-option>
              </el-select>
            </el-form-item>
            <el-form-item label="* 版本号:">
              <el-checkbox v-model="checkAll" @change="handleCheckAllBB">全选</el-checkbox>
              <div style="margin: 15px 0;"></div>
              <el-checkbox-group v-model="versions">
                <el-checkbox
                  v-for="(item,index) in optionsAppVersion"
                  :label="item.id"
                  :key="index"
                >{{item.name}}</el-checkbox>
              </el-checkbox-group>
            </el-form-item>
            <el-form-item label="* 渠道号:">
              <el-checkbox v-model="checkAll1" @change="handleCheckAllQD">全选</el-checkbox>
              <div style="margin: 15px 0;"></div>
              <el-checkbox-group v-model="AppChannel">
                <el-checkbox
                  v-for="(item,index) in optionsAppChannel"
                  :label="item.id"
                  :key="index"
                >{{item.cname}}</el-checkbox>
              </el-checkbox-group>
            </el-form-item>
            <el-form-item label="单设备频次控制:">
              <el-input v-model="select_name" placeholder class="handle-input mr10"></el-input>次/天
            </el-form-item>
            <el-form-item label="广告位:">
              <el-checkbox-group v-model="AD_position" disabled>
                <el-checkbox
                  :label="item.adPosId"
                  v-for="(item,indexAD) in ADposition"
                  :key="indexAD"
                >{{item.adPosName}}</el-checkbox>
              </el-checkbox-group>
            </el-form-item>
          </el-form>
        </el-tab-pane>
        <el-tab-pane label="设置排期" name="second_2">
          <table table border="1" cellspacing="0" cellpadding="0" class="table">
            <thead>
              <tr>
                <td colspan="3">广告位名称</td>
                <td colspan="31">
                  <div>
                    <el-button size="mini" type="success" @click="reduceMonth()">上一月</el-button>
                    <span>{{getMonth}}</span>
                    <el-button size="mini" type="success" @click="addMonth()">下一月</el-button>
                  </div>
                </td>
              </tr>
            </thead>
            <tbody>
              <tr v-for="(item,index) in table_data" :key="index">
                <td colspan="3">{{item.posName}}</td>
                <td v-for="(sub_item,i) in item.times" :key="i">
                  <template>
                    <div
                      @click="clickColor(sub_item,i,index)"
                      class="bgWhite"
                      v-if="sub_item==0"
                    >{{i+1}}</div>
                    <div
                      @click="clickColor(sub_item,i,index)"
                      class="bgFalse"
                      v-if="sub_item==1"
                    >{{i+1}}</div>
                    <div
                      @click="clickColor(sub_item,i,index)"
                      class="bgtrue"
                      v-if="sub_item==2"
                    >{{i+1}}</div>
                  </template>
                </td>
              </tr>
            </tbody>
          </table>
          <div class="td_color">
            <div class="diamonds"></div>未排期
            <div class="diamonds bg_color"></div>已排期
            <div class="diamonds active_color"></div>已选
          </div>
          <!-- 曝光设置 -->
          <table table border="1" cellspacing="0" cellpadding="0" class="table">
            <thead>
              <tr>
                <td colspan="3">广告位名称</td>
                <td colspan="31">广告曝光设置</td>
              </tr>
            </thead>
            <tbody>
              <tr v-for="(item,index) in list_" :key="index">
                <td colspan="3">{{item.posName}}</td>
                <td colspan="31">
                  <span>
                    曝光时长s:
                    <el-input-number
                      v-model.number="item.straTime"
                      controls-position="right"
                      type="number"
                      :min="0"
                      :max="3600"
                      style="width:15%"
                    ></el-input-number>
                  </span>
                  <span style="margin-left: 10%;">变更类型:</span>
                  <span>翻页</span>
                  <el-input-number
                    v-model.number="item.straPage"
                    controls-position="right"
                    :min="0"
                    :max="100"
                    type="number"
                    style="width:15%"
                  ></el-input-number>
                  <span>翻章</span>
                  <el-input-number
                    v-model.number="item.straChapter"
                    controls-position="right"
                    :min="0"
                    :max="100"
                    type="number"
                    style="width:15%"
                  ></el-input-number>
                </td>
              </tr>
            </tbody>
          </table>
        </el-tab-pane>
        <el-tab-pane label="确认提交" name="second_3">
          <el-form ref="form" :model="last" status-icon :label-position="labelPosition">
            <el-form-item label="投放名称:">
              <span>{{last.unitName}}</span>
            </el-form-item>
            <el-form-item label="广告平台:">
              <span>{{last.ADplatform}}</span>
            </el-form-item>
            <el-form-item label="APP:">
              <span>{{last.APP}}</span>
            </el-form-item>
            <el-form-item label="广告位:">
              <span>{{last.appositionName}}</span>
            </el-form-item>
            <el-form-item label="版本:">
              <span>{{last.BbName}}</span>
            </el-form-item>
            <el-form-item label="渠道:">
              <span>{{last.QdName}}</span>
            </el-form-item>
          </el-form>
        </el-tab-pane>
      </el-tabs>
      <span slot="footer" class="dialog-footer">
        <el-button style="margin-top: 12px;" @click="last_" v-if="activeName != 'second_1'">上一步</el-button>
        <el-button style="margin-top: 12px;" @click="next_" v-if="activeName != 'second_3'">下一步</el-button>
        <!-- <el-button @click="newVisible = false">取 消</el-button> -->
        <el-button
          type="primary"
          @click="saveEdit"
          v-if="activeName == 'second_3' && this.readonly !=1"
        >确 定</el-button>
      </span>
    </el-dialog>

    <!-- 新建弹出框 -->
    <el-dialog title="新建投放" :visible.sync="newVisible" width="80%">
      <el-tabs v-model="activeName" @tab-click="handleClick">
        <el-tab-pane label="基本信息" name="second_1">
          <el-form
            ref="form"
            :model="form"
            status-icon
            label-width="100px"
            :label-position="labelPosition"
          >
            <el-form-item label="* 投放名称:">
              <el-input v-model="form.putName" style="width:40%"></el-input>
            </el-form-item>
            <el-form-item label="* APP:">
              <el-select v-model="form.select_APP" @change="seachAdPositin">
                <el-option
                  v-for="item in options_APP"
                  :key="item.appId"
                  :label="item.appName"
                  :value="item.appId"
                ></el-option>
              </el-select>
            </el-form-item>
            <el-form-item label="* 广告平台:">
              <el-select v-model="form.select_AD" @change="seachAdPositin">
                <el-option
                  v-for="item in optionsTypeAD"
                  :key="item.platformId"
                  :label="item.platformName"
                  :value="item.platformId"
                ></el-option>
              </el-select>
            </el-form-item>
            <el-form-item label="* 版本号:">
              <el-checkbox v-model="checkAll" @change="handleCheckAllBB">全选</el-checkbox>
              <div style="margin: 15px 0;"></div>
              <el-checkbox-group v-model="versions">
                <el-checkbox
                  v-for="(item,index) in optionsAppVersion"
                  :label="item.id"
                  :key="index"
                >{{item.name}}</el-checkbox>
              </el-checkbox-group>
            </el-form-item>
            <el-form-item label="* 渠道号:">
              <el-checkbox v-model="checkAll1" @change="handleCheckAllQD">全选</el-checkbox>
              <div style="margin: 15px 0;"></div>
              <el-checkbox-group v-model="AppChannel">
                <el-checkbox
                  v-for="(item,index) in optionsAppChannel"
                  :label="item.id"
                  :key="index"
                >{{item.cname}}</el-checkbox>
              </el-checkbox-group>
            </el-form-item>
            <el-form-item label="单设备频次控制:">
              <el-input v-model="select_name" placeholder class="handle-input mr10"></el-input>次/天
            </el-form-item>
            <el-form-item label="广告位:">
              <el-checkbox-group v-model="AD_position" @change="searchAD">
                <el-checkbox
                  :label="item.adPosId"
                  v-for="(item,indexAD) in ADposition"
                  :key="indexAD"
                >{{item.adPosName}}</el-checkbox>
              </el-checkbox-group>
            </el-form-item>
          </el-form>
        </el-tab-pane>
        <el-tab-pane label="设置排期" name="second_2">
          <table table border="1" cellspacing="0" cellpadding="0" class="table">
            <thead>
              <tr>
                <td colspan="3">广告位名称</td>
                <td colspan="31">
                  <div>
                    <el-button size="mini" type="success" @click="reduceMonth()">上一月</el-button>
                    <span>{{getMonth}}</span>
                    <el-button size="mini" type="success" @click="addMonth()">下一月</el-button>
                  </div>
                </td>
              </tr>
            </thead>
            <tbody>
              <tr v-for="(item,index) in table_data" :key="index">
                <td colspan="3">{{item.posName}}</td>
                <td v-for="(sub_item,i) in item.times" :key="i">
                  <template>
                    <div
                      @click="clickColor(sub_item,i,index)"
                      class="bgWhite"
                      v-if="sub_item==0"
                    >{{i+1}}</div>
                    <div
                      @click="clickColor(sub_item,i,index)"
                      class="bgFalse"
                      v-if="sub_item==1"
                    >{{i+1}}</div>
                    <div
                      @click="clickColor(sub_item,i,index)"
                      class="bgtrue"
                      v-if="sub_item==2"
                    >{{i+1}}</div>
                  </template>
                </td>
              </tr>
            </tbody>
          </table>
          <div class="td_color">
            <div class="diamonds"></div>未排期
            <div class="diamonds bg_color"></div>已排期
            <div class="diamonds active_color"></div>已选
          </div>
          <!-- 曝光设置 -->
          <table table border="1" cellspacing="0" cellpadding="0" class="table">
            <thead>
              <tr>
                <td colspan="3">广告位名称</td>
                <td colspan="31">广告曝光设置</td>
              </tr>
            </thead>
            <tbody>
              <tr v-for="(item,index) in list_" :key="index">
                <td colspan="3">{{item.posName}}</td>
                <td colspan="31">
                  <span>
                    曝光时长s:
                    <el-input-number
                      v-model.number="item.straTime"
                      controls-position="right"
                      type="number"
                      :min="0"
                      :max="3600"
                      style="width:15%"
                    ></el-input-number>
                  </span>
                  <span style="margin-left: 10%;">变更类型:</span>
                  <span>翻页</span>
                  <el-input-number
                    v-model.number="item.straPage"
                    controls-position="right"
                    :min="0"
                    :max="100"
                    type="number"
                    style="width:15%"
                  ></el-input-number>
                  <span>翻章</span>
                  <el-input-number
                    v-model.number="item.straChapter"
                    controls-position="right"
                    :min="0"
                    :max="100"
                    type="number"
                    style="width:15%"
                  ></el-input-number>
                </td>
              </tr>
            </tbody>
          </table>
        </el-tab-pane>
        <el-tab-pane label="确认提交" name="second_3">
          <el-form ref="form" :model="last" status-icon :label-position="labelPosition">
            <el-form-item label="投放名称:">
              <span>{{last.unitName}}</span>
            </el-form-item>
            <el-form-item label="广告平台:">
              <span>{{last.ADplatform}}</span>
            </el-form-item>
            <el-form-item label="APP:">
              <span>{{last.APP}}</span>
            </el-form-item>
            <el-form-item label="广告位:">
              <span>{{last.appositionName}}</span>
            </el-form-item>
            <el-form-item label="版本:">
              <span>{{last.BbName}}</span>
            </el-form-item>
            <el-form-item label="渠道:">
              <span>{{last.QdName}}</span>
            </el-form-item>
          </el-form>
        </el-tab-pane>
      </el-tabs>
      <span slot="footer" class="dialog-footer">
        <el-button style="margin-top: 12px;" @click="last_" v-if="activeName != 'second_1'">上一步</el-button>
        <el-button style="margin-top: 12px;" @click="next_" v-if="activeName != 'second_3'">下一步</el-button>
        <!-- <el-button @click="newVisible = false">取 消</el-button> -->
        <el-button type="primary" @click="saveNews" v-if="activeName == 'second_3'">确 定</el-button>
      </span>
    </el-dialog>

    <el-dialog title="提示" :visible.sync="delVisible" width="300px" center>
      <div class="del-dialog-cnt">删除不可恢复，是否确定删除？</div>
      <span slot="footer" class="dialog-footer">
        <el-button @click="delVisible = false">取 消</el-button>
        <el-button type="primary" @click="deleteRow" v-if="this.readonly !=1">确 定</el-button>
      </span>
    </el-dialog>

    <el-dialog title="提示" :visible.sync="lookVisible" width="500px" center>
      <el-table :data="tableDataAD" border style="width: 100%" ref="multipleTable">
        <el-table-column prop="posName" label="广告位名称" align="center"></el-table-column>
        <el-table-column
          prop="status"
          label="状态"
          align="center"
          :formatter="formatterTyperunStatus"
        ></el-table-column>
      </el-table>
    </el-dialog>
  </div>
</template>

<script>
import { orderPutpages, orderslist, dicmapList, planlist } from "@/api/Api.js";
import { CustomerList, areaGroupgetAreasByNameAndType } from "@/api/Api.js";

import {
  positionTimescheduling,
  orderPutadd,
  adPositiongetList,
  flowsourcegetallbytype,
  orderPutinfo,
  orderPutupdate,
  diclistByIdStr,
  sdkallotadpstatus
} from "@/api/Api.js";

import {
  sdkallotpre,
  AppVersionselect,
  AppChannelselect,
  sdkallotlist,
  sdkallotadp,
  sdkallotadd,
  sdkallotstatus,
  sdkallotsche,
  sdkallotget,
  sdkallotupdate
} from "@/api/Api.js";

import treeTransfer from "el-tree-transfer"; // 引入
import picker from "../../../components/common/picker/index.vue";
import Calendar from "mpvue-calendar";
import "mpvue-calendar/src/browser-style.css";

//  import eleCalendar from 'ele-calendar'
//  import 'ele-calendar/dist/vue-calendar.css'

const plannames = new Map();
const costrmNames = new Map();
const medioType = new Map();

const uuid = new Map();
const names = new Map();
var idCookies = new Map();
var dataMap = new Map();
var regs = "/" + 1 + "/g";
const optionsVersions = [];
const optionsChannel = [];

var APPID = new Map();
var platformsID = new Map();
var BB = new Map();
var QD = new Map();
export default {
  name: "basetable",
  data() {
    return {
      labelPosition: "left",
      card: 1,
      events: { "2019-3-8": "12", "2019-3-9": "13" },
      prop: "date",
      getMonth: "",
      isactive: 0,
      getDate: "",
      table_data: [{ name: "" }],
      pickerOptions1: {
        disabledDate(time) {
          // 
          return time.getTime() + 3600 * 1000 * 24 <= Date.now();
        }
      },
      title: ["城市", "已选城市"],
      mode: "transfer",
      lookVisible: false,
      tableDataAD: [],
      url: "./static/vuetable.json",
      append_div: [{ set_time: [{}], times: "" }],
      tableData: [],
      cur_page: 1,
      total: 1,
      ps: 10,
      select_PutName: "",
      select_name: "",
      select_platform: "",
      select_Customer: "",
      del_list: [],
      is_search: false,
      editVisible: false,
      delVisible: false,
      newVisible: false,
      newaddAD: false,
      template_dialog: false,
      currentRow: null,
      isIndeterminate: true,
      isIndeterminate2: true,
      checkAll: false,
      checkAll1: false,
      idx: -1,
      select_APP: "",
      radio: "",
      extensionType: "",
      landUrl: "",
      appId: "",
      pkgName: "",
      versioncode: "",
      versionname: "",
      size: "",
      sign: "",
      md5: "",
      minsdklevel: "",
      medias: [],
      ADposition: [],
      activeName: "second_1",
      dxCzxt: "",
      dxWl: "",
      city: "",
      areas: [],
      value2: [],
      last: {},
      dxZdlx: "",
      costType: "",
      AD_position: [],
      form: {
        id: "",
        select_plan: [],
        putName: "",
        price: "",
        media_type: "68",
        media: "",
        AD_position: [],
        findType: "",
        deliveryMode: "",
        Crowd: 1,
        select_APP: "",
        select_AD: ""
      },
      options_APP: [
        {
          value: "",
          label: ""
        }
      ],
      optionsTypeAD: [
        {
          value: "",
          label: ""
        }
      ],
      optionsTypeAD2: [
        {
          value: "",
          label: ""
        }
      ],
      options_: [
        {
          value: "",
          label: "全部状态"
        },
        {
          value: 1,
          label: "开启"
        },
        {
          value: 0,
          label: "暂停"
        }
      ],
      options_plan: null,
      value: "",
      readonly: "",
      optionsAppVersion: [],
      optionsAppChannel: [],
      versions: [],
      AppChannel: [],
      list_: []
    };
  },
  created() {
    this.getList();
    this.readonly = this.$store.state.map.get(this.$store.state.currentPath);
    this.date = new Date();
    this.month = this.date.getMonth() + 1;
    this.year = this.date.getFullYear();
    //输出所选月份的第一天
    var start = this.year + "-" + this.month + "-" + "1";
    //获取当前
    var date = new Date(start);
    var year = date.getFullYear();
    var month = date.getMonth() + 1;
    var d = new Date(year, month, 0); //下一个月的前一天
    this.days = this.getDays();
    var m =
      this.date.getMonth() + 1 < 10
        ? "0" + (this.date.getMonth() + 1)
        : this.date.getMonth() + 1;
    //  var  d = this.date.getDate()<10?"0"+this.date.getDate():this.date.getDate()
    this.getMonth = this.date.getFullYear() + "" + m;
    this.terminalType1();
    this.getADPosition();
  },
  components: { treeTransfer, picker, Calendar }, // 注册

  methods: {
    look(index, row) {
      
      let params = {
        allotId: row.id
      };
      sdkallotadpstatus(params).then(res => {
        let data = res.data;
        if (data.code != "A000000") {
          this.$message.error(data.message);
          return;
        }
        this.tableDataAD = data.data;
      });
      this.lookVisible = true;
    },
    terminalType1() {
      //  版本号
      AppVersionselect().then(res => {
        
        let data = res.data;
        if (data.code != "A000000") {
          this.$message.error(data.message);
          return;
        }
        this.optionsAppVersion = data.data;
        data.data.forEach(element => {
          optionsVersions.push(element.id);
          BB.set(element.id, element.name);
        });
      });
      //  渠道号
      AppChannelselect().then(res => {
        
        let data = res.data;
        if (data.code != "A000000") {
          this.$message.error(data.message);
          return;
        }
        this.optionsAppChannel = data.data;
        data.data.forEach(element => {
          optionsChannel.push(element.id);
          QD.set(element.id, element.cname);
        });
      });
      // app 平台
      sdkallotpre().then(res => {
        
        let data = res.data;
        if (data.code != "A000000") {
          this.$message.error(data.message);
          return;
        }
        this.options_APP = data.data.apps;
        this.optionsTypeAD = data.data.platforms;
        data.data.apps.forEach(element => {
          APPID.set(element.appId, element.appName);
        });
        data.data.platforms.forEach(element => {
          platformsID.set(element.platformId, element.platformName);
        });
      });
    },
    getADPosition() {
      sdkallotpre().then(res => {
        
        let data = res.data;
        if (data.code != "A000000") {
          this.$message.error(data.message);
          return;
        }
        this.optionsTypeAD2 = data.data.platforms;
        let obj = {
          platformId: "",
          platformName: "全部广告平台"
        };
        this.optionsTypeAD2.push(obj);
      });
    },
    // 获取广告位
    seachAdPositin() {
      this.AD_position = [];
      let params = {
        appId: this.form.select_APP,
        platformId: this.form.select_AD
      };
      sdkallotadp(params).then(res => {
        
        this.ADposition = res.data.data;
        res.data.data.forEach(element => {
          uuid.set(element.adPosId, element.adPosName);
          
        });
      });
    },
    getDays() {
      //输出所选月份的第一天
      var start = this.year + "-" + this.month + "-" + "1";
      //获取当前
      var date = new Date(start);
      var year = date.getFullYear();
      var month = date.getMonth() + 1;
      var d = new Date(year, month, 0); //下一个月的前一天
      return d.getDate();
    },
    // 上下月切换
    addMonth() {
      //debugger
      if (this.month <= 12) {
        if (this.month == 12) {
          this.year = this.year + 1;
          this.month = 1;
        } else {
          this.month = this.month + 1;
        }
      }
      var m = this.month < 10 ? "0" + this.month : this.month;
      this.getMonth = this.year + "" + m;
      this.days = this.getDays();
      
      this.sechAD();
      this.$forceUpdate();
    },
    reduceMonth() {
      if (this.month >= 1) {
        if (this.month == 1) {
          this.year = this.year - 1;
          this.month = 12;
        } else {
          this.month = this.month - 1;
        }
      }
      var m = this.month < 10 ? "0" + this.month : this.month;
      this.getMonth = this.year + "" + m;
      this.days = this.getDays();
      this.sechAD();
    },
    // 点击日期
    clickColor(item, i, index) {
      let is = i + 1 < 10 ? "0" + (i + 1) : i + 1;
      var date = new Date();
      let month = date.getMonth() + 1;
      var m = month < 10 ? "0" + month : month;
      var d = date.getDate() < 10 ? "0" + date.getDate() : date.getDate();
      let data1 = date.getFullYear() + "" + m + "" + d;
      let data2 =
        this.year +
        "" +
        (this.month < 10 ? "0" + this.month : this.month) +
        "" +
        is;
      
      
      
      
      if (item === 1) {
        return;
      }
      if (parseInt(data2) < parseInt(data1)) {
        this.$message("不可操作早于当前日期");
        return;
      }

      if (item == 2) {
        item = 0;
      } else {
        item = 2;
      }
      this.table_data[index].times[i] = item;
      this.daysarray = JSON.parse(JSON.stringify(this.table_data));
      idCookies.set(this.getMonth, this.daysarray);
      this.table_data = Object.assign([], this.table_data);
    },
    // 编辑排期
    sechAD() {
      let params = {
        posIds: this.AD_position.join(","),
        month: this.getMonth,
        type: 1,
        allotId: this.form.id
      };
      if (dataMap.get(this.getMonth)) {
        this.table_data = dataMap.get(this.getMonth);
      } else {
        sdkallotsche(params).then(res => {
          this.table_data = res.data.data;
          dataMap.set(this.getMonth, res.data.data);
        });
      }
    },
    // 新建排期
    searchAD() {
      dataMap = new Map();
      let params = {
        posIds: this.AD_position.join(","),
        month: this.getMonth,
        type: 1,
        allotId: this.form.id
      };
      sdkallotsche(params).then(res => {
        this.table_data = res.data.data;
        dataMap.set(this.getMonth, res.data.data);
        this.list_ = res.data.data;
      });
    },
    last_() {
      let value = this.activeName.split("_");
      this.activeName = value[0] + "_" + parseInt(parseInt(value[1]) - 1);
    },
    next_() {
      let value = this.activeName.split("_");
      this.activeName = value[0] + "_" + parseInt(parseInt(value[1]) + 1);
      if (this.activeName == "second_2") {
        // if(this.card == 1){
        //        this.NewSech()
        // }
        // this.sechAD();
      }
      if (this.activeName == "second_3") {
        this.handleClick({ name: this.activeName });
      }
    },

    // prev(year, month, weekIndex) {
    //   
    // },
    // next(year, month, weekIndex) {
    //   
    // },
    select(val, val2) {
      
      
    },
    handleCheckAllBB(val) {
      
      this.versions = val ? optionsVersions : [];
      this.isIndeterminate = false;
    },
    handleCheckAllQD(val) {
      
      this.AppChannel = val ? optionsChannel : [];
      this.isIndeterminate2 = false;
    },

    // 开关
    changeStatus(val, row) {
      let item = row;
      let params = {
        id: item.id,
        status: item.status
      };
      sdkallotstatus(params).then(res => {
        
        // this.getList();
      });
    },

    diquSech2() {
      this.form = Object.assign({}, this.form);
    },

    // 编辑
    handleEdit(index, row) {
      
      var item = row;
      //debugger
      //获取当前
      this.card = 2;
      this.ids = row.id;
      var date = new Date();
      var year = date.getFullYear();
      var month = date.getMonth() + 1;
      var d = new Date(year, month, 0); //下一个月的前一天
      this.days = this.getDays();
      var m =
        this.date.getMonth() + 1 < 10
          ? "0" + (this.date.getMonth() + 1)
          : this.date.getMonth() + 1;
      this.getMonth = this.date.getFullYear() + "" + m;
      this.month = month;

      this.activeName = "second_1";
      this.editVisible = true;
      this.form = {};
      this.idx = index;
      this.append_div = [];
      this.form.Crowd = 1;
      var number;
      var number1;
      var number2;
      this.form = {
        id: item.id,
        putName: item.allotmentName,
        select_APP: item.appId,
        select_AD: item.platformId
      };
      this.seachAdPositin();
      this.select_name = row.frequency;
      (this.AD_position = row.posIds.split(",")),
        (number2 = this.AD_position.map(Number));
      this.AD_position = number2;

      (this.versions = row.appVersion.split(",")),
        (number = this.versions.map(Number));
      this.versions = number;
      (this.AppChannel = row.channelId.split(",")),
        (number1 = this.AppChannel.map(Number));
      this.AppChannel = number1;
      this.table_data = row.scheduleList;
      this.searchAD();
      this.editVisible = true;
    },
    // 保存编辑
    saveEdit() {
      let that_ = this;
      let positionTime = [];
      // let id = this.AD_position
      dataMap.forEach(function(value, key, map) {
        
        
        let years = key;
        value.forEach(function(element, index, value) {
          var puttime = element.times;
          var adPosId = element.adPosId;
          var straTime = that_.list_[index].straTime;
          var straPage = that_.list_[index].straPage;
          var straChapter = that_.list_[index].straChapter;

          let obj = {
            adPosId: adPosId,
            period: puttime.join("").replace(eval(regs), 0),
            monthPeriod: years,
            straTime: straTime,
            straPage: straPage,
            straChapter: straChapter
          };
          positionTime.push(obj);
        });
      });
      let params = {
        type: 1,
        id: this.ids,
        allotmentName: this.form.putName,
        appId: this.form.select_APP,
        platformId: this.form.select_AD,
        appVersion: this.versions.join(","),
        channelId: this.AppChannel.join(","),
        frequency: this.select_name,
        adPosId: this.AD_position.join(","),
        scheduleList: positionTime
      };
      sdkallotupdate(params).then(res => {
        
        let data = res.data;
        // 
        if (data.code != "A000000") {
          this.$message.error(data.message);
        }
        this.editVisible = false;
        this.search();
      });
    },
    // 新建
    create() {
      //获取当前
      this.card = 1;
      var date = new Date();
      var year = date.getFullYear();
      var month = date.getMonth() + 1;
      var d = new Date(year, month, 0); //下一个月的前一天
      this.days = this.getDays();
      var m =
        this.date.getMonth() + 1 < 10
          ? "0" + (this.date.getMonth() + 1)
          : this.date.getMonth() + 1;
      this.getMonth = this.date.getFullYear() + "" + m;
      this.month = month;

      this.newVisible = true;
      this.activeName = "second_1";
      this.form = {};
      this.versions = [];
      this.AppChannel = [];
      this.select_name = "";
      this.AD_position = [];
      this.ADposition = [];
    },
    // 确认新建
    saveNews() {
      let that_ = this;
      let positionTime = [];
      dataMap.forEach(function(value, key, map) {
        
        
        let years = key;
        value.forEach(function(element, index, value) {
          
          var puttime = element.times;
          var adPosId = element.adPosId;
          var straTime = that_.list_[index].straTime;
          var straPage = that_.list_[index].straPage;
          var straChapter = that_.list_[index].straChapter;

          let obj = {
            adPosId: adPosId,
            period: puttime.join("").replace(eval(regs), 0),
            monthPeriod: years,
            straTime: straTime,
            straPage: straPage,
            straChapter: straChapter
          };
          positionTime.push(obj);
        });
      });
      // 参数判断
      // let  stopSchedule =false
      //   positionTime.forEach(element => {
      //     
      //     if(element.period.indexOf('1') == -1){
      //         this.$message.error("排期不能为空");
      //         stopSchedule = true
      //         return false
      //     }
      //   });
      //   if(stopSchedule){
      //       return
      //   }
      let params = {
        type: 1,
        allotmentName: this.form.putName,
        appId: this.form.select_APP,
        platformId: this.form.select_AD,
        appVersion: this.versions.join(","),
        channelId: this.AppChannel.join(","),
        frequency: this.select_name,
        adPosId: this.AD_position.join(","),
        scheduleList: positionTime
      };
      sdkallotadd(params).then(res => {
        
        let data = res.data;
        // 
        if (data.code != "A000000") {
          this.$message.error(data.message);
        }
        this.newVisible = false;
        this.getList();
      });
    },

    formatterTyperunStatus(row, column) {
      if (row.status == 0) {
        return "未开始";
      } else if (row.status == 1) {
        return "投放中";
      } else if (row.status == 2) {
        return "投放完成";
      } else if (row.status == -1) {
        return "失效/超限额";
      }
    },
    handleSelectCustomer(item) {
      // 
      this.id = item.id;
    },
    // 模糊客户名称
    SearchCustomer(queryString, cb) {
      let params = {
        fullName: this.select_word,
        cp: 1,
        ps: this.ps,
        type: 2
      };
      CustomerList(params).then(res => {
        let data = res.data;
        // 
        // 调用 callback 返回建议列表的数据
        cb(data.data.data);
        data.data.data.forEach(element => {
          uuid.set(element.fullName, element.id);
        });
        if (data.code != "A000000") {
          this.$message.error(data.message);
        }
      });
    },
    customer_id(item) {
      // 
      this.coustom_id = item.id;
      this.coyput;
    },
    // 分页导航
    handleCurrentChange(val) {
      this.cur_page = val;
      let params = {
        type: 1,
        pageNum: this.cur_page,
        pageSize: this.ps
      };
      sdkallotlist(params).then(res => {
        
        let data = res.data;
        if (data.code != "A000000") {
          this.$message.error(data.message);
          return;
        }
        this.tableData = data.data.data;
        this.total = data.data.totalItemNum;
      });
    },
    getList() {
      let params = { pageNum: this.cur_page, pageSize: this.ps, type: 1 };
      sdkallotlist(params).then(res => {
        
        let data = res.data;

        this.tableData = data.data.data;
        this.total = data.data.totalItemNum;
      });
    },
    search() {
      this.$refs.pagination.lastEmittedPage = 1;
      this.cur_page = 1;
      let params = {
        name: this.select_PutName,
        platformId: this.select_platform,
        status: this.value,
        pageNum: this.cur_page,
        pageSize: this.ps,
        type: 1
      };
      sdkallotlist(params).then(res => {
        
        let data = res.data;
        if (data.code != "A000000") {
          this.$message.error(data.message);
          return;
        }
        this.tableData = data.data.data;
        this.total = data.data.totalItemNum;
      });
    },
    handleClick(tab, event) {
      if (tab.name == "second_2") {
        // if(this.card == 1){
        //     this.NewSech()
        // }
        // this.sechAD()
      }
      if (tab.name == "second_3") {
        console.log(111)
        let arr = [];
        this.AD_position.forEach(element => {
          arr.push(uuid.get(parseInt(element)));
        });
        let bn = [];
        this.versions.forEach(element => {
          bn.push(BB.get(element));
        });
        let qd = [];
        this.AppChannel.forEach(element => {
          qd.push(QD.get(element));
        });


        this.last = {
          unitName: this.form.putName,
          APP: APPID.get(this.form.select_APP),
          ADplatform: platformsID.get(this.form.select_AD),
          appositionName: arr.join(","),
          BbName: bn.join(","),
          QdName: qd.join(",")
        };
      }

      this.$forceUpdate();
      
    },
    handleDelete(index, row) {
      this.idx = index;
      this.row = row;
      this.delVisible = true;
    },
    // 确定删除
    deleteRow() {
      let params = {
        id: this.row.id,
        status: -1,
      };
      sdkallotstatus(params).then(res => {
        if (res.data.code != "A000000") {
          this.$message.error(res.data.message);
        } else {
          this.$message.success("删除成功");
          this.delVisible = false;
          this.getList();
        }
      });
    }
  }
};
</script>

<style scoped>
.handle-box {
  margin-bottom: 20px;
}
.add_task {
  border: 2px dashed green;
  padding: 10px;
  margin: 10px;
}
.btn_right {
  margin-left: 84%;
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
  margin-bottom: 20px;
}
.btn_plan {
  text-align: center;
  margin: 0 auto;
  width: 100px;
  height: 50px;
  background-color: #249cd3;
  border: 1px silver solid;
  color: aliceblue;
  line-height: 50px;
}
.btn_plan_2 {
  text-align: center;
  margin: 0 auto;
  width: 100px;
  height: 50px;
  background-color: #ffffff;
  border: 1px silver solid;
  color: black;
  line-height: 50px;
  margin-top: 20px;
}
.span {
  color: red;
}
.el-checkbox + .el-checkbox {
  margin-left: 0px;
}
.r_div {
  margin-left: 80px;
}
.border {
  border: 2px dashed #26a580;
  padding: 10px;
}
.div {
  margin-top: 10px;
}
.td_color {
  margin-left: 47%;
}
.diamonds {
  display: inline-block;
  width: 20px;
  height: 20px;
  margin-left: 10px;
  border: 1px solid silver;
}
.bg_color {
  background: #909399;
}
.active_color {
  background: #258cd3;
}
.mr10 {
  width: 180px;
}
.table {
  /* margin-left: -10px; */
  /* margin-top: 20px; */
  /* border: 1px solid #909399; */
  border-collapse: collapse;
  table-layout: fixed;
  border-collapse: separate;
  /* border-right:1px solid #909399;border-bottom:1px solid #909399 */
}
thead {
  color: #909399;
  font-weight: 500;
}
table,
td {
  text-align: center;
  height: 32px;
  overflow: hidden;
  /* border: 1px solid rgba(0, 0, 0, 0.336); */
  border-left: 1px solid #909399;
  border-top: 1px solid #909399;
}
.el-date-editor {
  border: none;
}
.td_color {
  margin-left: 70%;
  padding: 10px;
  margin-top: 20px;
}
.diamonds {
  display: inline-block;
  width: 20px;
  height: 20px;
  border: 1px solid silver;
}
.bg_color {
  background: silver;
}
.table {
  width: 100%;
}
.bgFalse {
  width: 100%;
  height: 100%;
  background: #909399;
}
.bgtrue {
  width: 100%;
  height: 100%;
  background: #258cd3;
}
.bgWhite {
  width: 100%;
  height: 100%;
  background: white;
}
.mrl11 {
  width: 170px;
}
.mpvue-calendar {
  width: 40%;
}
</style>
<style>
.el-checkbox {
  padding-right: 18px;
}
</style>





