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
        <!-- <el-table-column prop="chargeType" label="投放类型" align="center" :formatter=formatterTypesputStatus>
        </el-table-column>-->
        <el-table-column prop="beginTime" label="广告位统计" align="center">
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
        </el-table-column>0
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
              <el-select v-model="form.select_APP" disabled>
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
            <!-- <el-form-item label="投放类型:">
                    <el-radio v-model="chargeType" :label="141">CPC</el-radio>
                    <el-radio v-model="chargeType" :label="140">CPM</el-radio>
            </el-form-item>-->
            <el-form-item label="* 版本号:">
              <el-checkbox
                :indeterminate="isIndeterminate"
                v-model="checkAll"
                @change="handleCheckAllBB"
              >全选</el-checkbox>
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
              <el-checkbox
                :indeterminate="isIndeterminate2"
                v-model="checkAll1"
                @change="handleCheckAllQD"
              >全选</el-checkbox>
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
          <el-form
            ref="form"
            :model="form"
            status-icon
            label-width="100px"
            :label-position="labelPosition"
          >
            <el-form-item label="设置排期:">
              <div class="add_task" v-for="(adp,index_) in append_div" :key="index_">
                <el-form-item label="广告位名称:">
                  <span>{{adp.name}}</span>
                </el-form-item>
                <el-form-item label="投放方式:">
                  <el-radio-group v-model="adp.deliveryMode">
                    <el-radio :label="104">标准投放</el-radio>
                    <el-radio :label="105">匀速投放</el-radio>
                  </el-radio-group>
                </el-form-item>
                <el-form-item label="权重:">
                  <el-input-number
                    controls-position="right"
                    v-model="adp.priority"
                    :min="1"
                    :max="100"
                    class="handle-input"
                  ></el-input-number>
                </el-form-item>
                <el-form-item label="广告曝光设置:">
                  <span>
                    曝光时长s:
                    <el-input-number
                      v-model.number="adp.straTime"
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
                    v-model.number="adp.straPage"
                    controls-position="right"
                    :min="0"
                    :max="100"
                    type="number"
                    style="width:15%"
                  ></el-input-number>
                  <span>翻章</span>
                  <el-input-number
                    v-model.number="adp.straChapter"
                    controls-position="right"
                    :min="0"
                    :max="100"
                    type="number"
                    style="width:15%"
                  ></el-input-number>
                </el-form-item>
                <div v-for="(end_div,index) in adp.limitArray" :key="index">
                  <div class="btn_right">
                    <el-button type="primary" @click="add_div(index_,index)">+</el-button>
                    <el-button
                      type="primary"
                      @click="delete_div(index_,index)"
                      v-if="adp.limitArray !== undefined && adp.limitArray.length > 1"
                    >X</el-button>
                  </div>
                  <el-form-item label="* 日期:">
                    <el-date-picker
                      v-model="end_div.startDay"
                      type="date"
                      placeholder="选择日期"
                      format="yyyy 年 MM 月 dd 日"
                      value-format="yyyyMMdd"
                      :picker-options="pickerOptions1"
                      @blur="select1"
                    ></el-date-picker>-
                    <el-date-picker
                      v-model="end_div.endDay"
                      type="date"
                      placeholder="选择日期"
                      format="yyyy 年 MM 月 dd 日"
                      value-format="yyyyMMdd"
                      :picker-options="pickerOptions1"
                      @blur="select1"
                    ></el-date-picker>
                  </el-form-item>
                  <el-form-item label="投放时段:">
                    <el-radio v-model="end_div.time_frame" :label="1" @change="NEWtime(end_div)">全时段</el-radio>
                    <el-radio
                      v-model="end_div.time_frame"
                      :label="2"
                      @change="NEWtime(end_div)"
                    >指定时段</el-radio>
                    <el-form-item v-if="end_div.time_frame == 2">
                      <div v-for="(time,value_i) in end_div.set_time" :key="value_i" class="DIVS">
                        <el-select v-model="time.begin" placeholder="请选择" @change="select1">
                          <el-option
                            v-for="(item,i_index) in hours"
                            :key="i_index"
                            :label="item.label"
                            :value="item.value"
                          ></el-option>
                        </el-select>至
                        <el-select v-model="time.end" placeholder="请选择" @change="select1">
                          <el-option
                            v-for="(item,e_index) in hours"
                            :key="e_index"
                            :label="item.label"
                            :value="item.value"
                          ></el-option>
                        </el-select>
                        <el-button
                          type="primary"
                          icon="search"
                          @click="new_time(value_i,index_,index)"
                        >+</el-button>
                        <el-button
                          v-if="value_i>0"
                          type="primary"
                          icon="search"
                          @click="delete_time(value_i,index_,index)"
                        >X</el-button>
                      </div>
                    </el-form-item>
                  </el-form-item>
                  <el-form-item label="* 每日发送请求量:">
                    <el-input-number v-model="end_div.limit" style="width:40%"></el-input-number>
                  </el-form-item>
                </div>
              </div>
            </el-form-item>
          </el-form>
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
          v-if="activeName == 'second_3'  && this.readonly !=1 "
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
            <!-- <el-form-item label="投放类型:">
                    <el-radio v-model="chargeType" :label="141">CPC</el-radio>
                    <el-radio v-model="chargeType" :label="140">CPM</el-radio>
            </el-form-item>-->
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
              <el-checkbox-group v-model="AD_position">
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
          <el-form
            ref="form"
            :model="form"
            status-icon
            label-width="100px"
            :label-position="labelPosition"
          >
            <el-form-item label="设置排期:">
              <div class="add_task" v-for="(adp,index_) in append_div" :key="index_">
                <el-form-item label="广告位名称:">
                  <span>{{adp.name}}</span>
                </el-form-item>
                <el-form-item label="投放方式:">
                  <el-radio-group v-model="adp.deliveryMode">
                    <el-radio :label="104">标准投放</el-radio>
                    <el-radio :label="105">匀速投放</el-radio>
                  </el-radio-group>
                </el-form-item>
                <el-form-item label="权重:">
                  <el-input-number
                    controls-position="right"
                    v-model="adp.priority"
                    :min="1"
                    :max="100"
                    class="handle-input"
                  ></el-input-number>
                </el-form-item>
                <el-form-item label="广告曝光设置:">
                  <span>
                    曝光时长s:
                    <el-input-number
                      v-model.number="adp.straTime"
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
                    v-model.number="adp.straPage"
                    controls-position="right"
                    :min="0"
                    :max="100"
                    type="number"
                    style="width:15%"
                  ></el-input-number>
                  <span>翻章</span>
                  <el-input-number
                    v-model.number="adp.straChapter"
                    controls-position="right"
                    :min="0"
                    :max="100"
                    type="number"
                    style="width:15%"
                  ></el-input-number>
                </el-form-item>
                <div v-for="(end_div,index) in adp.limitArray" :key="index">
                  <div class="btn_right">
                    <el-button type="primary" @click="add_div(index_,index)">+</el-button>
                    <el-button
                      type="primary"
                      @click="delete_div(index_,index)"
                      v-if="adp.limitArray !== undefined && adp.limitArray.length > 1"
                    >X</el-button>
                  </div>
                  <el-form-item label="* 日期:">
                    <el-date-picker
                      v-model="end_div.startDay"
                      type="date"
                      placeholder="选择日期"
                      format="yyyy 年 MM 月 dd 日"
                      value-format="yyyyMMdd"
                      :picker-options="pickerOptions1"
                    ></el-date-picker>-
                    <el-date-picker
                      v-model="end_div.endDay"
                      type="date"
                      placeholder="选择日期"
                      format="yyyy 年 MM 月 dd 日"
                      value-format="yyyyMMdd"
                      :picker-options="pickerOptions1"
                    ></el-date-picker>
                  </el-form-item>
                  <el-form-item label="投放时段:">
                    <el-radio v-model="end_div.time_frame" :label="1">全时段</el-radio>
                    <el-radio
                      v-model="end_div.time_frame"
                      :label="2"
                      @change="NEWtime(end_div)"
                    >指定时段</el-radio>
                    <el-form-item v-if="end_div.time_frame == 2">
                      <div v-for="(time,value_i) in end_div.set_time" :key="value_i" class="DIVS">
                        <el-select v-model="time.begin" placeholder="请选择" @change="select1">
                          <el-option
                            v-for="(item,i_index) in hours"
                            :key="i_index"
                            :label="item.label"
                            :value="item.value"
                          ></el-option>
                        </el-select>至
                        <el-select v-model="time.end" placeholder="请选择" @change="select1">
                          <el-option
                            v-for="(item,e_index) in hours"
                            :key="e_index"
                            :label="item.label"
                            :value="item.value"
                          ></el-option>
                        </el-select>
                        <el-button
                          type="primary"
                          icon="search"
                          @click="new_time(value_i,index_,index)"
                        >+</el-button>
                        <el-button
                          v-if="value_i>0"
                          type="primary"
                          icon="search"
                          @click="delete_time(value_i,index_,index)"
                        >X</el-button>
                      </div>
                    </el-form-item>
                  </el-form-item>
                  <el-form-item label="* 每日发送请求量:">
                    <el-input-number v-model="end_div.limit" style="width:40%"></el-input-number>
                  </el-form-item>
                </div>
              </div>
            </el-form-item>
          </el-form>
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
        <el-table-column prop="priority" label="权重" align="center"></el-table-column>
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
  sdkallotlist,
  AppVersionselect,
  AppChannelselect,
  sdkallotpre,
  sdkallotadp,
  sdkallotadd,
  sdkallotget,
  sdkallotstatus,
  addFixed,
  updateFixed,
  sdkallotadpstatus,
  adPositionstock
} from "@/api/Api.js";

import treeTransfer from "el-tree-transfer"; // 引入
import Calendar from "mpvue-calendar";
import "mpvue-calendar/src/browser-style.css";
const plannames = new Map();
const costrmNames = new Map();
const medioType = new Map();

const uuid = new Map();
const names = new Map();
var idCookies = new Map();
var regs = "/" + 2 + "/g";
const options = [];
const optionsVersions = [];
const optionsChannel = [];
var APPID = new Map();
var platformsID = new Map();
var BB = new Map();
var QD = new Map();
var ADname = new Map();
export default {
  name: "basetable",
  data() {
    return {
      labelPosition: "left",
      begin: [],
      tableDataAD: [],
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

      events: {},
      select_platform: "",
      lookVisible: false,
      append_div: [],
      tableData: [],
      cur_page: 1,
      total: 1,
      ps: 10,
      AD_position: [],
      select_PutName: "",
      select_name: "",
      select_word: "",
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
      checkAll: false,
      idx: -1,
      hours: [
        {
          value: "0",
          label: "0"
        },
        {
          value: "1",
          label: "1"
        },
        {
          value: "2",
          label: "2"
        },
        {
          value: "3",
          label: "3"
        },
        {
          value: "4",
          label: "4"
        },
        {
          value: "5",
          label: "5"
        },
        {
          value: "6",
          label: "6"
        },
        {
          value: "7",
          label: "7"
        },
        {
          value: "8",
          label: "8"
        },
        {
          value: "9",
          label: "9"
        },
        {
          value: "10",
          label: "10"
        },
        {
          value: "11",
          label: "11"
        },
        {
          value: "12",
          label: "12"
        },
        {
          value: "13",
          label: "13"
        },
        {
          value: "14",
          label: "14"
        },
        {
          value: "15",
          label: "15"
        },
        {
          value: "16",
          label: "16"
        },
        {
          value: "17",
          label: "17"
        },
        {
          value: "18",
          label: "18"
        },
        {
          value: "19",
          label: "19"
        },
        {
          value: "20",
          label: "20"
        },
        {
          value: "21",
          label: "21"
        },
        {
          value: "22",
          label: "22"
        },
        {
          value: "23",
          label: "23"
        },
        {
          value: "24",
          label: "24"
        }
      ],
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
      chargeType: "",
      form: {
        id: "",
        select_plan: [],
        putName: "",
        price: "",
        media_type: "68",
        media: "",
        AD_position: "",
        findType: "",
        deliveryMode: "",
        select_APP: "",
        select_AD: ""
      },
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
      options_plan: null,
      value: "",
      readonly: "",
      optionsAppVersion: [],
      optionsAppChannel: [],
      versions: [],
      AppChannel: [],
      isIndeterminate: true,
      isIndeterminate2: true,
      checkAll: false,
      checkAll1: false
    };
  },
  created() {
    this.getList();
    this.readonly = this.$store.state.map.get(this.$store.state.currentPath);
    this.date = new Date();
    this.month = this.date.getMonth() + 1;
    this.year = this.date.getFullYear();
    this.day = this.date.getDate();
    //输出所选月份的第一天
    var start = this.year + "-" + this.month + "-" + "1";
    //获取当前
    var date = new Date(start);
    var year = date.getFullYear();
    var month = date.getMonth() + 1;
    var d = new Date(year, month, 0); //下一个月的前一天
    // this.days = this.getDays();
    var m =
      this.date.getMonth() + 1 < 10
        ? "0" + (this.date.getMonth() + 1)
        : this.date.getMonth() + 1;
    //  var  d = this.date.getDate()<10?"0"+this.date.getDate():this.date.getDate()
    this.getMonth = this.date.getFullYear() + "" + m;
    this.terminalType1();
    this.getADPosition();
    this.begin = [this.year, this.month, this.day];
  },

  watch: {
    "form.deliveryMode": function(newValue, oldValue) {
      
      this.form = Object.assign({}, this.form);
    }
  },
  components: { treeTransfer, Calendar }, // 注册

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
    // 强制刷新
    NEWtime(i) {
      
      this.$forceUpdate();
      this.append_div = Object.assign([], this.append_div);
    },
    // 强制刷新投放时间段
    select1() {
      
      this.$forceUpdate();
    },
    prev(year, month, index) {
      index.limitArray.value = [];
      
      var data = month < 10 ? "0" + month : month;
      let arr = [];
      let ss = {
        id: index.id,
        time: year + data
      };
      arr.push(ss);
      let adID = {
        type: 1,
        position: arr
      };
      let _this = this;
      adPositionstock(adID).then(res => {
        
        _this.append_div.forEach(element => {
          element.limitArray.forEach(elementArry => {
            if (element.id == index.id) {
              let map = res.data.data[element.id];
              if (map) {
                
                let time = year + data;
                let array = map[time];
                
                let obj = {};
                if (array) {
                  array.forEach(element => {
                    for (let key in element) {
                      obj[key] = element[key] + "";
                    }
                  });
                  
                  elementArry["events"] = obj;
                  
                  this.$forceUpdate();
                }
              }
            }
          });
        });
      });
    },
    next(year, month, index) {
      index.limitArray.value = [];
      
      var data = month < 10 ? "0" + month : month;
      let arr = [];
      let ss = {
        id: index.id,
        time: year + data
      };
      arr.push(ss);
      let adID = {
        type: 1,
        position: arr
      };
      let _this = this;
      adPositionstock(adID).then(res => {
        
        _this.append_div.forEach(element => {
          element.limitArray.forEach(elementArry => {
            if (element.id == index.id) {
              let map = res.data.data[element.id];
              if (map) {
                
                let time = year + data;
                let array = map[time];
                
                let obj = {};
                if (array) {
                  array.forEach(element => {
                    for (let key in element) {
                      obj[key] = element[key] + "";
                    }
                  });
                  
                  elementArry["events"] = obj;
                  
                  this.$forceUpdate();
                }
              }
            }
          });
        });
      });
    },
    select(val, val2, index_, index) {
      
      
      
      
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
          ADname.set(element.adPosId, element.adPosName);
        });
      });
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

    last_() {
      let value = this.activeName.split("_");
      this.activeName = value[0] + "_" + parseInt(parseInt(value[1]) - 1);
    },
    next_() {
      let value = this.activeName.split("_");
      this.activeName = value[0] + "_" + parseInt(parseInt(value[1]) + 1);
      if (this.activeName == "second_2") {
        this.handleClick({ name: this.activeName });
      }
      if (this.activeName == "second_3") {
        this.handleClick({ name: this.activeName });
      }
    },

    // 编辑
    handleEdit(index, row) {
      //debugger
      //获取当前
      this.index = 0;
      this.ids = row.id;
      this.activeName = "second_1";
      this.editVisible = true;
      this.form = {};
      this.idx = index;
      this.append_div = [];
      (this.form.Crowd = 1), (idCookies = new Map());
      let params = {
        id: row.id
      };
      sdkallotget(params).then(res => {
        
        if (res.status != 200) {
          this.$message.error(res.statusText);
        }
        this.form = {
          id: res.data.id,
          putName: res.data.allotmentName,
          select_APP: res.data.appId,
          select_AD: res.data.platformId
        };
        var number;
        var number1;
        var number2;
        (this.chargeType = res.data.chargeType),
          (this.select_name = res.data.frequency);
        (this.AD_position = res.data.posIds.split(",")),
          (number2 = this.AD_position.map(Number));
        this.AD_position = number2;
        
        (this.versions = res.data.appVersion.split(",")),
          (number = this.versions.map(Number));
        this.versions = number;
        (this.AppChannel = res.data.channelId.split(",")),
          (number1 = this.AppChannel.map(Number));
        this.AppChannel = number1;
        // 转化数据格式
        
        this.append_div = res.data.scheduleList;
        this.append_div.forEach(element => {
          // 
          // this.IDs.push(element.posIds)
          let list = [];
          element.limits.forEach(limit => {
            
            let m_times = [];
            if (limit.times) {
              let times = limit.times.split(",");
              times.forEach(_element => {
                if (_element) {
                  let o = _element.split("-");
                  let time = { begin: o[0], end: o[1] };
                  m_times.push(time);
                }
              });
            }
            let datas = ["", ""];
            if (limit.date) {
              datas = limit.date.split("-");
              this.times = this.times + limit.date + ",";
            }
            // let bgYY = parseInt(datas[0].substring(0,4))
            // let bgHH = parseInt(datas[0].substring(4,6))
            // let bgMM = parseInt(datas[0].substring(6,8))

            // let endYY = parseInt(datas[1].substring(0,4));
            // let endHH = parseInt(datas[1].substring(4,6))
            // let endMM = parseInt(datas[1].substring(6,8))
            let obj = {
              id: element.id,
              startDay: datas[0],
              endDay: datas[1],
              // value: [[bgYY,bgHH,bgMM],[endYY,endHH,endMM]],
              set_time: m_times.length == 0 ? [{}] : m_times,
              limit: limit.limit,
              time_frame: m_times.length > 0 ? 2 : 1
            };
            list.push(obj);
          });
          element.limitArray = list;
        });
        // 清空limits
        this.append_div.forEach(el => {
          el.limits = [];
        });
        let arr = [];
        this.append_div.forEach(element => {
          element.limitArray.forEach(elements => {
            let obj = {
              id: elements.id,
              time: elements.startDay.substring(0, 6)
            };
            arr.push(obj);
          });
        });

        this.editVisible = true;
        this.seachAdPositin();
      });
    },
    // 保存编辑
    saveEdit() {
      // 参数判断

      if (!this.form.putName) {
        this.$message.error("投放名称不能为空");
        return;
      }
      if (!this.AD_position) {
        this.$message.error("广告位为空");
        return;
      }
      this.append_div.forEach((element, index) => {
        element.limitArray.forEach(elementS => {
          
          
          let times = "";
          if (elementS.set_time) {
            elementS.set_time.forEach(element1 => {
              if (element1.begin && element1.end) {
                times = times + element1.begin + "-" + element1.end + ",";
              }
            });
          }
          let obj = {};
          // let YY = elementS.value[0][1]
          // if(YY<10){
          //   elementS.value[0][1] =  '0'+elementS.value[0][1]
          // }
          // let MM = elementS.value[0][2]
          // if(MM<10){
          //   elementS.value[0][2] =  '0'+elementS.value[0][2]
          // }

          // let YY2 = elementS.value[1][1]
          // if(YY2<10){
          //   elementS.value[1][1] =  '0'+elementS.value[1][1]
          // }
          // let MM2 = elementS.value[1][2]
          // if(MM2<10){
          //   elementS.value[1][2] =  '0'+elementS.value[1][2]
          // }
          (obj.date = elementS.startDay + "-" + elementS.endDay),
            (obj.times = times.substring(0, times.lastIndexOf(","))),
            (obj.limit = elementS.limit);
          element.limits.push(obj);
        });
      });
      let params = {
        type: 2,
        id: this.ids,
        allotmentName: this.form.putName,
        appId: this.form.select_APP,
        platformId: this.form.select_AD,
        chargeType: this.chargeType,
        appVersion: this.versions.join(","),
        channelId: this.AppChannel.join(","),
        frequency: this.select_name,
        adPosId: this.AD_position.join(","),
        scheduleList: this.append_div
      };
      updateFixed(params).then(res => {
        
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
      this.index = 1;
      this.activeName = "second_1";
      idCookies = new Map();
      this.form = {};
      this.newVisible = true;
      this.form = {};
      this.versions = [];
      this.AppChannel = [];
      this.select_name = "";
      this.AD_position = [];
      this.ADposition = [];
      this.chargeType = 141;
      let obj = {
        set_time: [{}],
        limit: 0,
        time_frame: 1
      };
      this.append_div = [
        {
          id: "",
          name: "",
          straTime: "",
          straChapter: "",
          straPage: "",
          deliveryMode: 104,
          priority: "",
          limitArray: [obj],
          limits: []
        }
      ];
    },
    // 确认新建
    saveNews() {
      if (!this.form.putName) {
        this.$message.error("投放名称不能为空");
        return;
      }
      if (!this.AD_position) {
        this.$message.error("广告位为空");
        return;
      }
      this.append_div.forEach((element, index) => {
        element.limitArray.forEach(elementS => {
          
          let times = "";
          if (elementS.set_time) {
            elementS.set_time.forEach(element1 => {
              if (element1.begin && element1.end) {
                times = times + element1.begin + "-" + element1.end + ",";
              }
            });
          }
          let obj = {};
          // let YY = elementS.value[0][1]
          // if(YY<10){
          //   elementS.value[0][1] =  '0'+elementS.value[0][1]
          // }
          // let MM = elementS.value[0][2]
          // if(MM<10){
          //   elementS.value[0][2] =  '0'+elementS.value[0][2]
          // }

          // let YY2 = elementS.value[1][1]
          // if(YY2<10){
          //   elementS.value[1][1] =  '0'+elementS.value[1][1]
          // }
          // let MM2 = elementS.value[1][2]
          // if(MM2<10){
          //   elementS.value[1][2] =  '0'+elementS.value[1][2]
          // }
          (obj.date = elementS.startDay + "-" + elementS.endDay),
            (obj.times = times.substring(0, times.lastIndexOf(","))),
            (obj.limit = elementS.limit);
          element.limits.push(obj);
        });
      });

      let params = {
        type: 2,
        allotmentName: this.form.putName,
        appId: this.form.select_APP,
        platformId: this.form.select_AD,
        chargeType: this.chargeType,
        appVersion: this.versions.join(","),
        channelId: this.AppChannel.join(","),
        frequency: this.select_name,
        adPosId: this.AD_position.join(","),
        scheduleList: this.append_div
      };

      addFixed(params).then(res => {
        
        let data = res.data;
        // 
        if (data.code != "A000000") {
          this.$message.error(data.message);
          return;
        }
        this.newVisible = false;
        this.getList();
      });
    },

    formatterTypesputStatus(row, column) {
      if (row.chargeType == 141) {
        return "CPC";
      } else if (row.chargeType == 140) {
        return "CPM";
      }
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
    // 模糊订单名称
    querySearchCustomer(queryString, cb) {
      let params = {
        name: this.select_word
      };
      orderslist(params).then(res => {
        
        //let data = res.data;
        // 
        // 调用 callback 返回建议列表的数据
        cb(res.data);
        res.data.forEach(element => {
          uuid.set(element.name, element.id);
        });
        // if(res.data.code != 'A000000') {
        //   this.$message.error(res.data.message);
        //     }
      });
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
        pageNum: this.cur_page,
        pageSize: this.ps,
        type: 2
      };
      sdkallotlist(params).then(res => {
        let data = res.data;
        this.tableData = data.data.data;
        this.total = data.data.totalItemNum;
      });
    },
    getList() {
      let params = { pageNum: this.cur_page, pageSize: this.ps, type: 2 };
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
        type: 2
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
        if (this.index == 1) {
          if (this.AD_position.length > 0) {
            this.append_div = [];
          }
          this.AD_position.forEach(element => {        
            let limit = {
              startDay: "",
              endDay: "",
              value: [],
              set_time: [{}],
              limits: 0,
              time_frame: 1
            };
            let obj = {
              id: element,
              name: ADname.get(element),
              deliveryMode: 104,
              limitArray: [limit],
              limits: []
            };
            obj.name = ADname.get(element);
            this.append_div.push(obj);
          });
          // 库存
          //  let  arr = []
          //  this.AD_position.forEach(element => {
          //    let objs = {
          //      id:element,
          //      time:this.getMonth
          //    }
          //     arr.push(objs)
          //  });
          //   let adID= {
          //       type:1,
          //       position:arr
          //     }
          //   adPositionstock(adID).then(res => {
          //     
          //     this.append_div.forEach(element => {
          //       
          //         element.limitArray.forEach(elementArry => {
          //             
          //             let map =  res.data.data[element.id];
          //             
          //             let time = this.getMonth
          //             let array = map[time];
          //             let obj = {};
          //             array.forEach(element => {
          //               for(let key  in element){
          //                   obj[key] = element[key]+'';
          //               }
          //             });
          //             elementArry['events'] = obj;
          //             this.$forceUpdate()
          //             
          //           });
          //       });
          //   })
        }
      }

      if (tab.name == "second_3") {
        let arr = [];
        this.AD_position.forEach(element => {
          arr.push(ADname.get(parseInt(element)));
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
        //let data = res;
        if (res.data.code != "A000000") {
          this.$message.error(res.data.message);
        } else {
          this.$message.success("删除成功");
          this.delVisible = false;
          this.getList();
        }
      });
    },
    add_div(row, index) {
      
      this.append_div[row].limitArray.push({
        date: "",
        times: "",
        limit: 0,
        time_frame: 1,
        set_time: [{}]
      });
      this.append_div[row].limits = [];
      this.$forceUpdate();
    },
    delete_div(row, index) {
      
      let list = this.append_div;
      // this.append_div = [];
      this.append_div[row].limitArray.splice(index, 1);
      this.$forceUpdate();
      // list[row].limits = []

      // // this.append_div = JSON.parse(JSON.stringify(this.append_div));
      // let _this=this
      // setTimeout(function(){
      //    _this.append_div = list;
      //   //  _this.$forceUpdate()
      // },10);
    },
    new_time(row, index_, index) {
      
      let data = this.append_div[index_].limitArray[index].set_time;
      this.$forceUpdate();
      data.push({ begin: "", end: "" });
    },
    delete_time(row, index_, index) {
      
      this.$forceUpdate();
      let data = this.append_div[index_].limitArray[index].set_time;
      data.splice(index + 1, 1);
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
</style>
<style>
.mpvue-calendar {
  width: 500px !important;
  height: 400px !important;
}
.mc-day {
  width: 64px !important;
  height: 47px !important;
}

.mc-range-mode .selected .mc-range-bg {
  /* width: 65% !important; */
  background-color: #ffffff !important;
}
.mc-range-begin.mc-range-second-to-last {
  background-color: #ffffff !important;
}
.mpvue-calendar .mc-range-mode .selected .calendar-date {
  background-color: #3b75fb !important;
}
.mpvue-calendar .mc-range-mode .mc-range-row-last .calendar-date,
.mpvue-calendar .mc-range-mode .mc-range-row-first .calendar-date {
  border-radius: 50% !important;
}
.mpvue-calendar .mc-range-mode .selected.mc-range-second-to-last span {
  border-radius: 50% !important;
}

.el-checkbox {
  padding-right: 18px;
}
</style>




