<template>
  <div class="table">
    <div class="container">
      <div class="handle-box">
        <el-input v-model="select_name" placeholder="请输入投放名称" class="handle-input mr10"></el-input>
        <el-autocomplete
          class="inline-input"
          v-model="select_word"
          value-key="name"
          :fetch-suggestions="querySearchCustomer"
          placeholder="请输入订单名称"
          @select="handleSelectCustomer"
        ></el-autocomplete>
        <el-autocomplete
          class="inline-input"
          v-model="select_Customer"
          value-key="name"
          :fetch-suggestions="SearchCustomer"
          placeholder="广告位"
          @select="customer_id"
        ></el-autocomplete>

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
        <el-table-column prop="id" label="投放ID" align="center"></el-table-column>
        <el-table-column prop="putName" label="投放名称" align="center"></el-table-column>
        <el-table-column prop="oid" label="订单ID" align="center"></el-table-column>
        <el-table-column prop="orderName" label="订单名称" align="center"></el-table-column>
        <el-table-column prop="costTypeName" label="计费方式" align="center"></el-table-column>
        <el-table-column label="广告位" align="center">
          <template slot-scope="scope">
            <el-button
              size="mini"
              type="text"
              @click="newPrice(scope.$index, scope.row)"
            >{{scope.row.positionNum}}</el-button>
          </template>
        </el-table-column>
        <el-table-column prop label="运行状态" align="center" v-if="this.readonly !=1">
          <template slot-scope="scope">
            <el-switch
              v-model="scope.row.runState"
              :active-value="1"
              :inactive-value="0"
              @change="changeStatus($event,scope.row)"
            ></el-switch>
          </template>
        </el-table-column>
        <el-table-column label="操作" width="230" align="center">
          <template slot-scope="scope">
            <el-button size="mini" type="success" @click="handleEdit(scope.$index, scope.row,)">编辑</el-button>
            <el-button size="mini" type="success" @click="handleDelete(scope.$index, scope.row)">删除</el-button>
            <!-- <el-button size="mini" type="success" @click="schedule(scope.$index, scope.row)" v-if="this.dateTable">排期</el-button> -->
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
            <el-form-item label="* 投放类型:">订单投放</el-form-item>
            <el-form-item label="* 投放名称:">
              <el-input v-model="form.putName" style="width:40%"></el-input>
            </el-form-item>
            <el-form-item label="* 订单名称:">
              <el-select v-model="form.select_plan">
                <el-option
                  v-for="item in options_plan"
                  :key="item.id"
                  :label="item.name"
                  :value="item.id"
                ></el-option>
              </el-select>
            </el-form-item>
            <el-form-item label="计费方式:">
              <el-radio v-model="costType" :label="113" disabled>CPT</el-radio>
              <el-radio v-model="costType" :label="140" disabled>CPM</el-radio>
              <el-radio v-model="costType" :label="141" disabled>CPC</el-radio>
            </el-form-item>
            <el-form-item label="设备频次控制:" v-if="this.costType !=113">
              <el-radio v-model="isFrequency" :label="0">关闭</el-radio>
              <el-radio v-model="isFrequency" :label="1">开启</el-radio>
              <el-form-item label="频次值:" v-if="this.isFrequency == 1">
                <el-input-number v-model="frequencNum" style="width:40%"></el-input-number>
              </el-form-item>
            </el-form-item>
            <el-form-item label="* 刊例价:" v-if="this.costType == 113">
              <el-input-number v-model="form.price" style="width:40%"></el-input-number>元
            </el-form-item>
            <el-form-item label="投放类型:">
              <el-radio v-model="extensionType" :label="119">网站</el-radio>
              <el-radio v-model="extensionType" :label="120">IOS应用</el-radio>
              <el-radio v-model="extensionType" :label="121">安卓应用</el-radio>
              <el-radio v-model="extensionType" :label="122">deeplink</el-radio>
              <el-form-item
                label="* 推广链接:"
                v-if="this.extensionType== 119||this.extensionType == 120||this.extensionType == 121||this.extensionType == 122"
              >
                <el-input v-model="landUrl" style="width:80%" placeholder="开头为http://或https://的链接"></el-input>
              </el-form-item>
              <el-form-item label="应用id:" v-if="this.extensionType == 120">
                <el-input v-model="appId" style="width:80%"></el-input>
              </el-form-item>
              <el-form-item label="应用包名:" v-if="this.extensionType == 121||extensionType == 122">
                <el-input v-model="pkgName" style="width:80%"></el-input>
              </el-form-item>
            </el-form-item>
          </el-form>
        </el-tab-pane>
        <el-tab-pane label="定向设置" name="second_2">
          <el-form
            ref="form"
            :model="form"
            status-icon
            label-width="100px"
            :label-position="labelPosition"
          >
            <el-form-item label="APP名称:">
              <el-radio-group v-model="AppName" disabled>
                <el-radio :label="1">不限</el-radio>
                <el-radio :label="2">自定义</el-radio>
              </el-radio-group>
              <br>
              <el-radio-group
                class="Radio_"
                v-model="appIds"
                @change="getAdposition"
                v-if="this.AppName == 2"
                disabled
              >
                <el-radio
                  :label="item.id"
                  v-for="(item,indexMT) in optionAppName"
                  :key="indexMT"
                >{{item.appName}}</el-radio>
              </el-radio-group>
            </el-form-item>
            <el-form-item label="版本号:">
              <el-radio-group v-model="AppVersion">
                <el-radio :label="1">不限</el-radio>
                <el-radio :label="2">自定义</el-radio>
              </el-radio-group>
              <br>
              <el-checkbox-group v-model="versions" v-if="this.AppVersion == 2">
                <el-checkbox
                  :label="item.id"
                  v-for="(item,indexMT) in optionsAppVersion"
                  :key="indexMT"
                >{{item.name}}</el-checkbox>
              </el-checkbox-group>
            </el-form-item>
            <el-form-item label="渠道号:">
              <el-radio-group v-model="AppChannel">
                <el-radio :label="1">不限</el-radio>
                <el-radio :label="2">自定义</el-radio>
              </el-radio-group>
              <br>
              <el-checkbox-group v-model="Channel" v-if="this.AppChannel == 2">
                <el-checkbox
                  :label="item.id"
                  v-for="(item,indexMT) in optionsAppChannel"
                  :key="indexMT"
                >{{item.cname}}</el-checkbox>
              </el-checkbox-group>
            </el-form-item>
            <el-form-item label="机型:">
              <el-radio-group v-model="models">
                <el-radio :label="1">不限</el-radio>
                <el-radio :label="2">自定义</el-radio>
              </el-radio-group>
              <br>
              <el-checkbox-group v-model="TypeModels" v-if="this.models == 2">
                <el-checkbox
                  :label="item.id"
                  v-for="(item,indexMT) in optionsModels"
                  :key="indexMT"
                >{{item.modelName}}</el-checkbox>
              </el-checkbox-group>
            </el-form-item>
            <el-form-item label="图书分类:">
              <el-radio-group v-model="book">
                <el-radio :label="1">不限</el-radio>
                <el-radio :label="2">自定义</el-radio>
              </el-radio-group>
              <br>
              <div class="custom-tree-container" v-if="this.book == 2">
                <div class="block">
                  <el-tree
                    :data="data5"
                    show-checkbox
                    :props="props"
                    node-key="id"
                    ref="tree"
                    default-expand-all
                    :expand-on-click-node="false"
                    @check-change="handleCheckChange"
                    :default-checked-keys="this.checkedKey"
                    :check-strictly="true"
                  ></el-tree>
                </div>
              </div>
            </el-form-item>
            <el-form-item label="网络:">
              <el-radio v-model="dxWl" label>不限</el-radio>
              <el-radio v-model="dxWl" label="132">Wi-Fi</el-radio>
              <el-radio v-model="dxWl" label="134">3G</el-radio>
              <el-radio v-model="dxWl" label="135">4G</el-radio>
            </el-form-item>
            <el-tag>地域定向</el-tag>
            <el-form-item label>
              <el-radio-group v-model="form.Crowd" @change="diquSech2">
                <el-radio :label="1">不限</el-radio>
                <el-radio :label="2">自定义</el-radio>
              </el-radio-group>
              <el-form-item v-if="this.form.Crowd == 2">
                <el-form-item label="判定:">
                  <el-radio-group v-model="form.dqRule">
                    <el-radio :label="1">选定区域</el-radio>
                    <el-radio :label="2">排除区域</el-radio>
                  </el-radio-group>
                </el-form-item>
                <el-form-item label="选择省市:">
                  <el-radio-group v-model="form.findType">
                    <el-radio :label="1">省/直辖市</el-radio>
                    <el-radio :label="2">市</el-radio>
                  </el-radio-group>
                </el-form-item>
                <div>
                  <el-form-item label="搜索">
                    <el-input v-model="city" class="mrl11"></el-input>
                    <el-button @click="getcity" class="btn">搜索</el-button>
                  </el-form-item>
                  <el-form-item label="城市">
                    <template>
                      <tree-transfer
                        :title="title"
                        :from_data="areas"
                        pid="areaCode"
                        :to_data="value2"
                        :defaultProps="{label:'areaName'}"
                        @addBtn="add"
                        @removeBtn="remove"
                        :mode="mode"
                        height="540px"
                        filter
                        openAll
                      ></tree-transfer>
                    </template>
                  </el-form-item>
                </div>
              </el-form-item>
            </el-form-item>
          </el-form>
        </el-tab-pane>
        <el-tab-pane label="广告位选择" name="second_3">
          <el-form
            ref="form"
            :model="form"
            status-icon
            label-width="100px"
            :label-position="labelPosition"
          >
            <el-form-item label="* 广告位:">
              <el-checkbox-group v-model="AD_position" disabled>
                <el-checkbox
                  :label="item.id"
                  v-for="(item,indexAD) in ADposition"
                  :key="indexAD"
                >{{item.name}}</el-checkbox>
              </el-checkbox-group>
            </el-form-item>
          </el-form>
        </el-tab-pane>
        <el-tab-pane label="设置排期" name="second_4">
          <el-form
            ref="form"
            :model="form"
            status-icon
            label-width="100px"
            :label-position="labelPosition"
          >
            <el-form-item label="设置排期:" v-if="this.costType == 113">
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
                    <td colspan="3">{{item.name}}</td>
                    <td v-for="(sub_item,i) in item.times" :key="i">
                      <template>
                        <div
                          @click="clickColor(sub_item,i,index)"
                          class="bgWhite"
                          v-if="sub_item==0"
                        >{{i+1}}</div>
                        <div
                          @click="clickColor(sub_item,i,index)"
                          class="bgtrue"
                          v-if="sub_item==1"
                        >{{i+1}}</div>
                        <div
                          @click="clickColor(sub_item,i,index)"
                          class="bgFalse"
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
                    <td colspan="3">{{item.name}}</td>
                    <td colspan="31">
                      <span>
                        曝光时长s:
                        <el-input-number
                          v-model.number="item.expDuration"
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
                        v-model.number="item.changePage"
                        controls-position="right"
                        :min="0"
                        :max="100"
                        type="number"
                        style="width:15%"
                      ></el-input-number>
                      <span>翻章</span>
                      <el-input-number
                        v-model.number="item.changeChapter"
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
            </el-form-item>
            <el-form-item label="设置排期:" v-if="this.costType ==   140 ||this.costType ==141">
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
                <el-form-item label="广告曝光设置:">
                  <span>
                    曝光时长s:
                    <el-input-number
                      v-model.number="adp.expDuration"
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
                    v-model.number="adp.changePage"
                    controls-position="right"
                    :min="0"
                    :max="100"
                    type="number"
                    style="width:15%"
                  ></el-input-number>
                  <span>翻章</span>
                  <el-input-number
                    v-model.number="adp.changeChapter"
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
                  <Calendar
                    :range="true"
                    lunar
                    :events="end_div.events"
                    @next="(n1,n2,index_) => {next(n1,n2,adp,end_div)}"
                    @prev="(n1,n2,index_) => {prev(n1,n2,adp,end_div)}"
                    :begin="begin"
                    :value="end_div.value"
                    @select="(v1,v2,index_) => {select(v1,v2,index,index_)}"
                  />
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
                  <el-form-item label="* 每日限额:">
                    <el-input-number v-model="end_div.limit" style="width:40%" :min="1"></el-input-number>
                  </el-form-item>
                </div>
              </div>
            </el-form-item>
          </el-form>
        </el-tab-pane>
        <el-tab-pane label="确认提交" name="second_5">
          <el-form ref="form" :model="last" status-icon :label-position="labelPosition">
            <!-- <el-form-item label="计划名称:">
              <span>{{last.name}}</span>
            </el-form-item>-->
            <el-form-item label="投放名称:">
              <span>{{last.unitName}}</span>
            </el-form-item>
            <!-- <el-form-item label="投放时间:" v-if="this.costType !=113">
              <span>{{last.times}}</span>
            </el-form-item>-->
            <!-- <el-form-item label="每日限额:">
              <span>{{last.v_price}}</span>
            </el-form-item>-->
            <el-form-item label="投放方式:" v-if="this.costType != 113">
              <span>{{last.deliveryMode}}</span>
            </el-form-item>
            <el-form-item label="计费方式:">
              <span>{{last.costType}}</span>
            </el-form-item>
            <el-form-item label="投放类型:">
              <span>{{last.extensionType}}</span>
            </el-form-item>
            <el-form-item label="广告位:">
              <span>{{last.appositionName}}</span>
            </el-form-item>
            <el-form-item label="定向设置:">
              <div class="r_div" v-if="this.form.dqRule == 1">
                <el-form-item label="选定区域:">
                  <span>{{last.area}}</span>
                </el-form-item>
              </div>
              <div class="r_div" v-if="this.form.dqRule == 2">
                <el-form-item label="排除区域:">
                  <span>{{last.area}}</span>
                </el-form-item>
              </div>
            </el-form-item>
          </el-form>
        </el-tab-pane>
      </el-tabs>
      <span slot="footer" class="dialog-footer">
        <el-button style="margin-top: 12px;" @click="last_" v-if="activeName != 'second_1'">上一步</el-button>
        <el-button style="margin-top: 12px;" @click="next_" v-if="activeName != 'second_5'">下一步</el-button>
        <!-- <el-button @click="newVisible = false">取 消</el-button> -->
        <el-button
          type="primary"
          @click="saveEdit"
          v-if="activeName == 'second_5' && this.readonly !=1 "
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
            <el-form-item label="* 投放类型:">订单投放</el-form-item>
            <el-form-item label="* 投放名称:">
              <el-input v-model="form.putName" style="width:40%"></el-input>
            </el-form-item>
            <el-form-item label="* 订单名称:">
              <el-select v-model="form.select_plan" class="handle-input">
                <el-option
                  v-for="item in options_plan"
                  :key="item.id"
                  :label="item.name"
                  :value="item.id"
                ></el-option>
              </el-select>
            </el-form-item>
            <el-form-item label="计费方式:">
              <el-radio v-model="costType" :label="113">CPT</el-radio>
              <el-radio v-model="costType" :label="140">CPM</el-radio>
              <el-radio v-model="costType" :label="141">CPC</el-radio>
            </el-form-item>
            <el-form-item label="设备频次控制:" v-if="this.costType !=113">
              <el-radio v-model="isFrequency" :label="0" @change="searchs">关闭</el-radio>
              <el-radio v-model="isFrequency" :label="1" @change="searchs">开启</el-radio>
              <el-form-item label="频次值:" v-if="this.isFrequency == 1">
                <el-input-number v-model="frequencNum" style="width:40%"></el-input-number>
              </el-form-item>
            </el-form-item>
            <el-form-item label="* 刊例价:" v-if="this.costType == 113">
              <el-input-number v-model="form.price" style="width:40%"></el-input-number>元
            </el-form-item>
            <el-form-item label="投放类型:">
              <el-radio v-model="extensionType" :label="119">网站</el-radio>
              <el-radio v-model="extensionType" :label="120">IOS应用</el-radio>
              <el-radio v-model="extensionType" :label="121">安卓应用</el-radio>
              <el-radio v-model="extensionType" :label="122">deeplink</el-radio>
              <el-form-item
                label="* 推广链接:"
                v-if="this.extensionType== 119||this.extensionType == 120||this.extensionType == 121||this.extensionType == 122"
              >
                <el-input v-model="landUrl" style="width:80%" placeholder="开头为http://或https://的链接"></el-input>
              </el-form-item>
              <el-form-item label="应用id:" v-if="this.extensionType == 120">
                <el-input v-model="appId" style="width:80%"></el-input>
              </el-form-item>
              <el-form-item label="应用包名:" v-if="this.extensionType == 121||extensionType == 122">
                <el-input v-model="pkgName" style="width:80%"></el-input>
              </el-form-item>
            </el-form-item>
          </el-form>
        </el-tab-pane>
        <el-tab-pane label="定向设置" name="second_2">
          <el-form
            ref="form"
            :model="form"
            status-icon
            label-width="100px"
            :label-position="labelPosition"
          >
            <!-- <el-form-item label="终端类型:">
              <el-radio-group v-model="dxZdlx" @change="terminalType">
                  <el-radio  label="158">APP</el-radio>
              </el-radio-group>
            </el-form-item>-->
            <el-form-item label="APP名称:">
              <el-radio-group v-model="AppName" @change="getAdposition2">
                <el-radio :label="1">不限</el-radio>
                <el-radio :label="2">自定义</el-radio>
              </el-radio-group>
              <br>
              <el-radio-group v-model="appIds" @change="getAdposition" v-if="this.AppName == 2">
                <el-radio
                  :label="item.id"
                  v-for="(item,indexMT) in optionAppName"
                  :key="indexMT"
                >{{item.appName}}</el-radio>
              </el-radio-group>
            </el-form-item>
            <el-form-item label="版本号:">
              <el-radio-group v-model="AppVersion">
                <el-radio :label="1">不限</el-radio>
                <el-radio :label="2">自定义</el-radio>
              </el-radio-group>
              <br>
              <el-checkbox-group v-model="versions" v-if="this.AppVersion == 2">
                <el-checkbox
                  :label="item.id"
                  v-for="(item,indexMT) in optionsAppVersion"
                  :key="indexMT"
                >{{item.name}}</el-checkbox>
              </el-checkbox-group>
            </el-form-item>
            <el-form-item label="渠道号:">
              <el-radio-group v-model="AppChannel">
                <el-radio :label="1">不限</el-radio>
                <el-radio :label="2">自定义</el-radio>
              </el-radio-group>
              <br>
              <el-checkbox-group v-model="Channel" v-if="this.AppChannel == 2">
                <el-checkbox
                  :label="item.id"
                  v-for="(item,indexMT) in optionsAppChannel"
                  :key="indexMT"
                >{{item.cname}}</el-checkbox>
              </el-checkbox-group>
            </el-form-item>
            <el-form-item label="机型:">
              <el-radio-group v-model="models">
                <el-radio :label="1">不限</el-radio>
                <el-radio :label="2">自定义</el-radio>
              </el-radio-group>
              <br>
              <el-checkbox-group v-model="TypeModels" v-if="this.models == 2">
                <el-checkbox
                  :label="item.id"
                  v-for="(item,indexMT) in optionsModels"
                  :key="indexMT"
                >{{item.modelName}}</el-checkbox>
              </el-checkbox-group>
            </el-form-item>
            <el-form-item label="图书分类:">
              <el-radio-group v-model="book">
                <el-radio :label="1">不限</el-radio>
                <el-radio :label="2">自定义</el-radio>
              </el-radio-group>
              <br>
              <div class="custom-tree-container" v-if="this.book == 2">
                <div class="block">
                  <el-tree
                    :data="data5"
                    show-checkbox
                    :props="props"
                    node-key="id"
                    ref="tree"
                    default-expand-all
                    :expand-on-click-node="false"
                    @check-change="handleCheckChange"
                    :default-checked-keys="this.checkedKey"
                    :check-strictly="true"
                  ></el-tree>
                </div>
              </div>
            </el-form-item>
            <!-- <el-form-item label="操作系统:" v-if="this.dxZdlx == 158">
              <el-radio v-model="dxCzxt" label>不限</el-radio>
              <el-radio v-model="dxCzxt" label="143">IOS</el-radio>
              <el-radio v-model="dxCzxt" label="144">Android</el-radio>
            </el-form-item>-->
            <el-form-item label="网络:">
              <el-radio v-model="dxWl" label>不限</el-radio>
              <el-radio v-model="dxWl" label="132">Wi-Fi</el-radio>
              <el-radio v-model="dxWl" label="134">3G</el-radio>
              <el-radio v-model="dxWl" label="135">4G</el-radio>
            </el-form-item>
            <el-tag>地域定向</el-tag>
            <el-form-item label>
              <el-radio-group v-model="form.Crowd" @change="diquSech2">
                <el-radio :label="1">不限</el-radio>
                <el-radio :label="2">自定义</el-radio>
              </el-radio-group>
              <el-form-item v-if="this.form.Crowd == 2">
                <el-form-item label="判定:">
                  <el-radio-group v-model="form.dqRule">
                    <el-radio :label="1">选定区域</el-radio>
                    <el-radio :label="2">排除区域</el-radio>
                  </el-radio-group>
                </el-form-item>
                <el-form-item label="选择省市:">
                  <el-radio-group v-model="form.findType">
                    <el-radio :label="1">省/直辖市</el-radio>
                    <el-radio :label="2">市</el-radio>
                  </el-radio-group>
                </el-form-item>
                <div>
                  <el-form-item label="搜索">
                    <el-input v-model="city" class="mrl11"></el-input>
                    <el-button @click="getcity" class="btn">搜索</el-button>
                  </el-form-item>
                  <el-form-item label="城市">
                    <template>
                      <tree-transfer
                        :title="title"
                        :from_data="areas"
                        pid="areaCode"
                        :to_data="value2"
                        :defaultProps="{label:'areaName'}"
                        @addBtn="add"
                        @removeBtn="remove"
                        :mode="mode"
                        height="540px"
                        filter
                        openAll
                      ></tree-transfer>
                    </template>
                  </el-form-item>
                </div>
              </el-form-item>
            </el-form-item>
          </el-form>
        </el-tab-pane>
        <el-tab-pane label="广告位选择" name="second_3">
          <el-form
            ref="form"
            :model="form"
            status-icon
            label-width="100px"
            :label-position="labelPosition"
          >
            <el-form-item label="* 广告位:">
              <el-checkbox-group v-model="AD_position" @change="searchAD">
                <el-checkbox
                  :label="item.id"
                  v-for="(item,indexAD) in ADposition"
                  :key="indexAD"
                >{{item.name}}</el-checkbox>
              </el-checkbox-group>
            </el-form-item>
          </el-form>
        </el-tab-pane>
        <el-tab-pane label="设置排期" name="second_4">
          <el-form
            ref="form"
            :model="form"
            status-icon
            label-width="100px"
            :label-position="labelPosition"
          >
            <el-form-item label="设置排期:" v-if="this.costType == 113">
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
                    <td colspan="3">{{item.name}}</td>
                    <td v-for="(sub_item,i) in item.times" :key="i">
                      <template>
                        <div
                          @click="clickColor(sub_item,i,index)"
                          class="bgWhite"
                          v-if="sub_item==0"
                        >{{i+1}}</div>
                        <div
                          @click="clickColor(sub_item,i,index)"
                          class="bgtrue"
                          v-if="sub_item==1"
                        >{{i+1}}</div>
                        <div
                          @click="clickColor(sub_item,i,index)"
                          class="bgFalse"
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
                    <td colspan="3">{{item.name}}</td>
                    <td colspan="31">
                      <span>
                        曝光时长s:
                        <el-input-number
                          v-model.number="item.expDuration"
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
                        v-model.number="item.changePage"
                        controls-position="right"
                        :min="0"
                        :max="100"
                        type="number"
                        style="width:15%"
                      ></el-input-number>
                      <span>翻章</span>
                      <el-input-number
                        v-model.number="item.changeChapter"
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
            </el-form-item>
            <el-form-item label="设置排期:" v-if="this.costType ==140 ||this.costType ==141">
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
                <el-form-item label="广告曝光设置:">
                  <span>
                    曝光时长s:
                    <el-input-number
                      v-model.number="adp.expDuration"
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
                    v-model.number="adp.changePage"
                    controls-position="right"
                    :min="0"
                    :max="100"
                    type="number"
                    style="width:15%"
                  ></el-input-number>
                  <span>翻章</span>
                  <el-input-number
                    v-model.number="adp.changeChapter"
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
                  <div></div>
                  <Calendar
                    :range="true"
                    lunar
                    :events="end_div.events"
                    @next="(n1,n2,index_) => {next(n1,n2,adp,end_div)}"
                    @prev="(n1,n2,index_) => {prev(n1,n2,adp,end_div)}"
                    :begin="begin"
                    :value="end_div.value"
                    @select="(v1,v2,index_) => {select(v1,v2,index,index_)}"
                  />
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
                  <el-form-item label="* 每日限额:">
                    <el-input-number v-model="end_div.limit" style="width:40%" :min="1"></el-input-number>
                  </el-form-item>
                </div>
              </div>
            </el-form-item>
          </el-form>
        </el-tab-pane>
        <el-tab-pane label="确认提交" name="second_5">
          <el-form ref="form" :model="last" status-icon :label-position="labelPosition">
            <!-- <el-form-item label="计划名称:">
              <span>{{last.name}}</span>
            </el-form-item>-->
            <el-form-item label="投放名称:">
              <span>{{last.unitName}}</span>
            </el-form-item>
            <!-- <el-form-item label="投放时间:" v-if="this.costType !=113">
              <span>{{last.times}}</span>
            </el-form-item>-->
            <!-- <el-form-item label="每日限额:">
              <span>{{last.v_price}}</span>
            </el-form-item>-->
            <el-form-item label="投放方式:">
              <span>{{last.deliveryMode}}</span>
            </el-form-item>
            <el-form-item label="计费方式:">
              <span>{{last.costType}}</span>
            </el-form-item>
            <el-form-item label="投放类型:">
              <span>{{last.extensionType}}</span>
            </el-form-item>
            <el-form-item label="广告位:">
              <span>{{last.appositionName}}</span>
            </el-form-item>
            <el-form-item label="定向设置:">
              <div class="r_div" v-if="this.form.dqRule == 1">
                <el-form-item label="选定区域:">
                  <span>{{last.area}}</span>
                </el-form-item>
              </div>
              <div class="r_div" v-if="this.form.dqRule == 2">
                <el-form-item label="排除区域:">
                  <span>{{last.area}}</span>
                </el-form-item>
              </div>
            </el-form-item>
          </el-form>
        </el-tab-pane>
      </el-tabs>
      <span slot="footer" class="dialog-footer">
        <el-button style="margin-top: 12px;" @click="last_" v-if="activeName != 'second_1'">上一步</el-button>
        <el-button style="margin-top: 12px;" @click="next_" v-if="activeName != 'second_5'">下一步</el-button>
        <!-- <el-button @click="newVisible = false">取 消</el-button> -->
        <el-button type="primary" @click="saveNews" v-if="activeName == 'second_5'">确 定</el-button>
      </span>
    </el-dialog>

    <el-dialog title="提示" :visible.sync="delVisible" width="300px" center>
      <div class="del-dialog-cnt">删除不可恢复，是否确定删除？</div>
      <span slot="footer" class="dialog-footer">
        <el-button @click="delVisible = false">取 消</el-button>
        <el-button type="primary" @click="deleteRow" v-if="this.readonly !=1">确 定</el-button>
      </span>
    </el-dialog>

    <!-- 广告位 -->
    <el-dialog title="提示" :visible.sync="lookVisible" width="500px" center>
      <el-table :data="tableDataAD" border style="width: 100%" ref="multipleTable">
        <el-table-column prop="name" label="广告位名称" align="center"></el-table-column>
        <el-table-column prop="state" label="状态" align="center" :formatter="formatterTyperunStatus"></el-table-column>
        <el-table-column prop="num" label="创意个数" align="center"></el-table-column>
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
  orderPutdelete,
  diclistByIdStr,
  orderPutupdateRunState,
  getAreas,
  adPositionstock,
  appgetall,
  AppVersionselect,
  AppChannelselect,
  devicemodelsselect,
  bookcategorysselect,
  orderPutpositions
} from "@/api/Api.js";

import treeTransfer from "el-tree-transfer"; // 引入
// import picker from "../../../../components/common/picker/index.vue";

import Calendar from "mpvue-calendar";
import "mpvue-calendar/src/browser-style.css";
const plannames = new Map();
const costrmNames = new Map();
const medioType = new Map();

const uuid = new Map();
const orderId = new Map();
const names = new Map();
var idCookies = new Map();
var dataMap = new Map();
var regs = "/" + 2 + "/g";
var data = [];
var mySet = new Set();
export default {
  name: "basetable",
  data() {
    return {
      labelPosition: "left",
      begin: [],
      tableDataAD: [],
      AppName: 1,
      AppVersion: 1,
      AppChannel: 1,
      models: 1,
      book: 1,
      list_: [],
      appIds: "",
      versions: [],
      Channel: [],
      TypeModels: [],
      dateTable: false,
      lookVisible: false,
      timeVisbie: false,

      optionAppName: [],
      optionsAppVersion: [],
      optionsAppChannel: [],
      optionsModels: [],
      optionsbook: [],

      index: "",
      newdays: new Map(),
      daysarray: [],
      events: {},
      props: {
        label: "name",
        children: "children"
      },
      data5: JSON.parse(JSON.stringify(data)),
      valueBeginEnd: [[2019, 3, 8], [2019, 3, 10]],
      getMonth: "",
      isactive: 0,
      getDate: "",
      table_data: [],
      Ad_setting: [],

      pickerOptions1: {
        disabledDate(time) {
          // console.log(time.getTime())
          return time.getTime() + 3600 * 1000 * 24 <= Date.now();
        }
      },
      title: ["城市", "已选城市"],
      mode: "transfer",

      url: "./static/areas.json",
      append_div: [
        {
          data: "",
          times: "",
          limit: "",
          time_frame: 1,
          set_time: [{}],
          limitArray: [
            {
              value: []
            }
          ],
          events: {}
        }
      ],
      tableData: [],
      cur_page: 1,
      total: 1,
      ps: 10,
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
      form: {
        id: "",
        select_plan: "",
        putName: "",
        price: "",
        media_type: "68",
        media: "",
        findType: "",
        deliveryMode: "",
        Crowd: 1
      },
      AD_position: [],
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
      options_plan: [
        {
          value: "",
          label: ""
        }
      ],
      value: "",
      readonly: "",
      par: [],
      checkedKey: [],
      item: {},
      isFrequency: "",
      frequencNum: ""
    };
  },
  created() {
    this.getList();
    this.getPlan();
    this.terminalType();
    // this.dicmapList();
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
    this.days = this.getDays();
    var m =
      this.date.getMonth() + 1 < 10
        ? "0" + (this.date.getMonth() + 1)
        : this.date.getMonth() + 1;
    //  var  d = this.date.getDate()<10?"0"+this.date.getDate():this.date.getDate()
    this.getMonth = this.date.getFullYear() + "" + m;
    this.getData();
    this.getAdposition2();
    this.begin = [this.year, this.month, this.day];
  },

  watch: {
    "form.deliveryMode": function(newValue, oldValue) {
      this.form = Object.assign({}, this.form);
    }
  },
  components: { treeTransfer, Calendar }, // 注册

  methods: {
    // 查看排期
    schedule(index, row) {
      //获取当前
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
      this.item = row;

      let params = {
        adIds: this.item.adPosition,
        monthStr: this.getMonth,
        putId: this.item.id
      };

      //  if(dataMap.get(year+month+d)) {
      //     this.table_data  = dataMap.get(dataMap.get(year+month+d));
      //  }else {
      //     positionTimescheduling(params).then(res => {
      //       this.table_data = res.data.data
      //       dataMap.set(year+month+d,res.data.data)
      //     });
      //  }

      this.dateTable = true;
    },

    changTime() {
      this.timeVisbie = true;
    },
    searchs() {
      this.$forceUpdate();
    },
    newPrice(index, row) {
      let params = {
        id: row.id
      };
      orderPutpositions(params).then(res => {
        let data = res.data;
        if (data.code != "A000000") {
          this.$message.error(data.message);
          return;
        }
        this.tableDataAD = data.data;
      });
      this.lookVisible = true;
    },
    // 获取tree
    getData() {
      bookcategorysselect().then(res => {
        console.log(res);
        data = res.data.data;
        this.data5 = data;
        //    console.log(this.data5)
      });
    },
    handleCheckChange(data, checked, indeterminate) {
      console.log(checked);
      console.log(data);
      let arr = [];
      for (var i in data) {
        if (checked != false) {
          mySet.add(data.id);
        } else if (checked != true) {
          mySet.delete(data.id);
        }
      }
      this.par = Array.from(mySet);
      console.log(this.par);
    },
    prev(year, month, index) {
      index.limitArray.value = [];
      console.log(year, month, index);
      var data = month < 10 ? "0" + month : month;
      let arr = [];
      let ss = {
        id: index.id,
        time: year +''+data
      };
      arr.push(ss);
      let adID = {
        type: 2,
        position: arr
      };
      let _this = this;
      adPositionstock(adID).then(res => {
        _this.append_div.forEach(element => {
          element.limitArray.forEach(elementArry => {
            if (element.id == index.id) {
              let map = res.data.data[element.id];
              if (map) {
                console.log(map);
                let time = year +''+data;
                let array = map[time];
                console.log(array);
                let obj = {};
                if (array) {
                  array.forEach(element => {
                    for (let key in element) {
                      obj[key] = element[key] + "";
                    }
                  });
                  console.log(obj);
                  elementArry["events"] = obj;
                  console.log(elementArry["events"]);
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
      console.log(year, month, index);
      var data = month < 10 ? "0" + month : month;
      let arr = [];
      let ss = {
        id: index.id,
        time: year +''+data
      };
      arr.push(ss);
      let adID = {
        type: 2,
        position: arr
      };
      let _this = this;
      adPositionstock(adID).then(res => {
        _this.append_div.forEach(element => {
          element.limitArray.forEach(elementArry => {
            if (element.id == index.id) {
              let map = res.data.data[element.id];
              if (map) {
                console.log(map);
                let time = year +''+data;
                let array = map[time];
                console.log(array);
                let obj = {};
                if (array) {
                  array.forEach(element => {
                    for (let key in element) {
                      obj[key] = element[key] + "";
                    }
                  });
                  console.log(obj);
                  elementArry["events"] = obj;
                  console.log(elementArry["events"]);
                  this.$forceUpdate();
                }
              }
            }

            // let list = _this.append_div;
            // console.log(list)
            // _this.append_div = [];
            // setTimeout(function(){
            //   _this.append_div = list;
            //   console.log( _this.append_div)
            // },10);
          });
        });
      });
    },

    select(val, val2, index_, index) {
      console.log(val);
      console.log(val2);
      console.log(index_);
      console.log(index);
    },
    // 开关
    changeStatus(val, row) {
      let item = row;
      let params = {
        id: item.id,
        runState: item.runState
      };
      orderPutupdateRunState(params).then(res => {
        console.log(res);
        // this.getList();
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
      console.log(data1, data2);
      console.log(i);
      console.log(item);
      console.log(index);
      if (item === 2) {
        return;
      }

      if (parseInt(data2) < parseInt(data1)) {
        this.$message("不可操作早于当前日期");
        return;
      }
      if (item == 1) {
        item = 0;
      } else {
        item = 1;
      }
      this.table_data[index].times[i] = item;
      this.daysarray = JSON.parse(JSON.stringify(this.table_data));
      // idCookies.set(this.getMonth,this.daysarray)
      this.table_data = Object.assign([], this.table_data);
    },
    //  下一月
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
      console.log(this.getMonth);
      this.sechAD();
      this.$forceUpdate();
    },
    //  上一月
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
      this.$forceUpdate();
    },
    searchAD() {
      dataMap = new Map();
      let params = {
        adIds: this.AD_position.join(","),
        monthStr: this.getMonth,
        putId: this.form.id
      };

      positionTimescheduling(params).then(res => {
        this.table_data = res.data.data;
        dataMap.set(this.getMonth, res.data.data);
        this.list_ = res.data.data;
      });
    },
    // 点击广告位排期获取
    sechAD() {
      let params = {
        adIds: this.AD_position.join(","),
        monthStr: this.getMonth,
        putId: this.form.id
      };
      if (dataMap.get(this.getMonth)) {
        this.table_data = dataMap.get(this.getMonth);
      } else {
        positionTimescheduling(params).then(res => {
          this.table_data = res.data.data;
          dataMap.set(this.getMonth, res.data.data);
        });
      }
    },
    diquSech2() {
      this.form = Object.assign({}, this.form);
    },
    // 监听穿梭框组件添加
    add(fromData, toData, obj) {
      // 树形穿梭框模式transfer时，返回参数为左侧树移动后数据、右侧树移动后数据、移动的        {keys,nodes,halfKeys,halfNodes}对象
      // 通讯录模式addressList时，返回参数为右侧收件人列表、右侧抄送人列表、右侧密送人列表
      console.log("fromData:", fromData);
      console.log("toData:", toData);
      this.value2 = toData;
      console.log("obj:", obj);
    },
    // 监听穿梭框组件移除
    remove(fromData, toData, obj) {
      // 树形穿梭框模式transfer时，返回参数为左侧树移动后数据、右侧树移动后数据、移动的{keys,nodes,halfKeys,halfNodes}对象
      // 通讯录模式addressList时，返回参数为右侧收件人列表、右侧抄送人列表、右侧密送人列表
      console.log("fromData:", fromData);
      console.log("toData:", toData);
      this.value2 = toData;
      console.log("obj:", obj);
    },
    // 定向城市
    getcity() {
      let params = {
        findType: this.form.findType,
        areaName: this.city
      };
      areaGroupgetAreasByNameAndType(params).then(res => {
        var areastr = res.data;
        if (areastr.code != "A000000") {
          this.$message.error(areastr.message);
        }
        this.newlist = areastr.data;
        console.log(areastr.data);
        this.areas = areastr.data;
        console.log(this.areas);
      });
    },
    last_() {
      let value = this.activeName.split("_");
      this.activeName = value[0] + "_" + parseInt(parseInt(value[1]) - 1);
      this.handleClick({ name: this.activeName });
    },
    next_() {
      // console.log(this.append_div);
      let value = this.activeName.split("_");
      this.activeName = value[0] + "_" + parseInt(parseInt(value[1]) + 1);
      if (this.activeName == "second_4") {
        if(this.AD_position.length < 1){
          this.$message.error('请先选择广告位')
          this.last_();
        }else{
          if (this.costType == 113) {
            this.sechAD();
            this.searchAD();
          }
          this.handleClick({ name: this.activeName });
        }

      }
      if (this.activeName == "second_5") {
        this.handleClick({ name: this.activeName });
      }
    },

    // 初始化订单
    getPlan() {
      let params = {
        planName: this.form.select_plan
      };
      orderslist(params).then(res => {
        let data = res.data;
        if (data.code != "A000000") {
          this.$message.error(data.message);
        }
        this.options_plan = data.data;
        data.data.forEach(element => {
          names.set(element.id, element.planName);
        });
      });
    },

    map2Ary(map) {
      var list = [];
      for (var key in map) {
        list.push([key, map[key]]);
      }
      return list;
    },

    // 编辑
    handleEdit(index, row, i) {
      this.index = 0;
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
      let params = {
        id: row.id
      };
      let that = this;
      orderPutinfo(params).then(res => {
        if (res.data.code != "A000000") {
          this.$message.error(res.data.message);
        }
        if (res.data.data.dxApp == "") {
          this.AppName = 1;
          this.getAdposition2()
        } else {
          this.AppName = 2;
          this.appIds = parseInt(res.data.data.dxApp);
          this.getAdposition();
        }
        var arrs = res.data.data.adPosition;
        arrs = arrs.substring(1, arrs.length - 1);
        arrs = arrs.split(",");
        arrs = arrs.map(Number);

        that.form = res.data.data;
        (that.form.select_plan = res.data.data.oid),
          (that.costType = res.data.data.costType);
        (that.extensionType = res.data.data.extensionType),
          (that.AD_position = arrs),
          (that.form.media_type = res.data.data.dxMediaType),
          (that.dxZdlx = res.data.data.dxZdlx),
          (that.dxWl = res.data.data.dxWl),
          (that.landUrl = res.data.data.landUrl),
          (that.isFrequency = res.data.data.isFrequency),
          (that.frequencNum = res.data.data.frequencNum),
          res.data.data.dxBb
            ? (this.stringToArrayOfNum(this.versions, res.data.data.dxBb),
              (this.AppVersion = 2))
            : ((this.versions = []), (this.AppVersion = 1));
        res.data.data.dxQd
          ? (this.stringToArrayOfNum(this.Channel, res.data.data.dxQd),
            (this.AppChannel = 2))
          : ((this.Channel = []), (this.AppChannel = 1));
        res.data.data.dxJx
          ? (this.stringToArrayOfNum(this.TypeModels, res.data.data.dxJx),
            (this.models = 2))
          : ((this.TypeModels = []), (this.models = 1));
        res.data.data.dxTs
          ? (this.stringToArrayOfNum(this.checkedKey, res.data.data.dxTs),
            (this.book = 2))
          : ((this.checkedKey = []), (this.book = 1));
        this.append_div = [];
        this.IDs = [];
        if (that.costType != 113) {
          // 转化数据格式
          this.append_div = JSON.parse(res.data.data.limits);
          this.append_div.forEach(element => {
            this.IDs.push(element.id);
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
              let bgYY = parseInt(datas[0].substring(0, 4));
              let bgHH = parseInt(datas[0].substring(4, 6));
              let bgMM = parseInt(datas[0].substring(6, 8));

              let endYY = parseInt(datas[1].substring(0, 4));
              let endHH = parseInt(datas[1].substring(4, 6));
              let endMM = parseInt(datas[1].substring(6, 8));
              let obj = {
                id: element.id,
                startDay: datas[0],
                endDay: datas[1],
                value: [[bgYY, bgHH, bgMM], [endYY, endHH, endMM]],
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
          // 库存
          let adID = {
            type: 2,
            position: arr
          };
          adPositionstock(adID).then(res => {
            this.append_div.forEach(element => {
              element.limitArray.forEach(elementArry => {
                let map = res.data.data[elementArry.id];
                let time = elementArry.startDay.substring(0, 6);
                let array = map[time];
                let obj = {};
                array.forEach(element => {
                  for (let key in element) {
                    obj[key] = element[key] + "";
                  }
                });
                elementArry["events"] = obj;
              });
            });
          });
        } else {
          this.searchAD();
        }

        let par = {
          idStr: res.data.data.preDq
        };
        diclistByIdStr(par).then(res => {
          if (res.data.data && res.data.data.length != 0) {
            this.form.Crowd = 2;
          } else {
            this.form.Crowd = 1;
          }
          this.value2 = res.data.data;
        });
        this.editVisible = true;
      });
    },
    // 保存编辑
    saveEdit() {
      //     // 参数判断
      let reg = /(http|ftp|https):\/\/[\w\-_]+(\.[\w\-_]+)+([\w\-\.,@?^=%&:/~\+#]*[\w\-\@?^=%&/~\+#])?/;
      if (!reg.test(this.landUrl)) {
        this.$message("请输入正确的推广链接");
        return;
      }
      if (!this.form.putName) {
        this.$message.error("投放名称不能为空");
        return;
      }
      if (!this.form.select_plan) {
        this.$message.error("订单不能为空");
        return;
      }
      if (this.AD_position.length <= 0) {
        this.$message.error("广告位为空");
        return;
      }

      if (this.AppName == 1) {
        this.versions = [];
      }
      if (this.AppVersion == 1) {
        this.appIds = [];
      }
      if (this.AppChannel == 1) {
        this.Channel = [];
      }
      if (this.models == 1) {
        this.TypeModels = [];
      }
      if (this.form.Crowd == 1) {
        keys = "";
      }

      // 城市
      let keys = "";
      if (this.value2) {
        this.value2.forEach(element => {
          keys = keys + element.id + ",";
        });
        keys = keys.substring(0, keys.lastIndexOf(","));
      }
      if (this.form.Crowd == 1) {
        keys = "";
      }

      if (this.costType == 113) {
        let that_ = this;
        let positionTime = [];
        let id = this.AD_position;
        dataMap.forEach(function(value, key, map) {
          let years = key;
          value.forEach(function(element, index, value) {
            var puttime = element.times;
            var id = element.adId;
            var expDuration = that_.list_[index].expDuration;
            var changePage = that_.list_[index].changePage;
            var changeChapter = that_.list_[index].changeChapter;
            let obj = {
              adPositionId: id,
              putTime: puttime.join("").replace(eval(regs), 0),
              years: years,
              expDuration: expDuration,
              changePage: changePage,
              changeChapter: changeChapter
            };
            positionTime.push(obj);
          });
        });

        let params = {
          id: this.form.id,
          putName: this.form.putName,
          oid: this.form.select_plan,
          costType: this.costType,
          price: this.form.price,
          extensionType: this.extensionType,
          landUrl: this.landUrl,
          appId: this.appId,
          pkgName: this.pkgName,
          dxZdlx: this.dxZdlx,
          dxApp: this.appIds,
          dxQd: this.Channel.join(","),
          dxBb: this.versions.join(","),
          dxJx: this.TypeModels.join(","),
          dxWl: this.dxWl,
          dxTs: this.par.join(","),
          preDq: keys,
          dqRule: this.form.dqRule,
          dxMediaType: this.form.media_type,
          adPosition: this.AD_position.join(","),
          adPositionTimes: positionTime
        };
        orderPutupdate(params).then(res => {
          let data = res.data;
          if (data.code != "A000000") {
            this.$message.error(data.message);
            return;
          }
          this.editVisible = false;
          this.search();
        });
      } else {
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
            let YY = elementS.value[0][1];
            if (YY < 10) {
              elementS.value[0][1] = "0" + elementS.value[0][1];
            }
            let MM = elementS.value[0][2];
            if (MM < 10) {
              elementS.value[0][2] = "0" + elementS.value[0][2];
            }

            let YY2 = elementS.value[1][1];
            if (YY2 < 10) {
              elementS.value[1][1] = "0" + elementS.value[1][1];
            }
            let MM2 = elementS.value[1][2];
            if (MM2 < 10) {
              elementS.value[1][2] = "0" + elementS.value[1][2];
            }
            (obj.date =
              elementS.value[0].join("") + "-" + elementS.value[1].join("")),
              (obj.times = times.substring(0, times.lastIndexOf(","))),
              (obj.limit = elementS.limit);
            element.limits.push(obj);
          });
        });
        let params = {
          id: this.form.id,
          putName: this.form.putName,
          oid: this.form.select_plan,
          costType: this.costType,
          price: this.form.price,
          extensionType: this.extensionType,
          landUrl: this.landUrl,
          appId: this.appId,
          pkgName: this.pkgName,
          dxZdlx: this.dxZdlx,
          dxApp: this.appIds,
          dxQd: this.Channel.join(","),
          dxBb: this.versions.join(","),
          dxJx: this.TypeModels.join(","),
          dxWl: this.dxWl,
          dxTs: this.par.join(","),
          preDq: keys,
          dqRule: this.form.dqRule,
          limits: this.append_div,
          adPosition: this.AD_position.join(","),
          isFrequency: this.isFrequency,
          frequencPeriod: 115,
          frequencNum: this.frequencNum
        };

        orderPutupdate(params).then(res => {
          let data = res.data;
          if (data.code != "A000000") {
            this.$message.error(data.message);
          }
          this.editVisible = false;
          this.search();
        });
      }
    },
    NEWtime(i) {
      this.$forceUpdate();
      this.append_div = Object.assign([], this.append_div);
    },
    // 新建
    create() {
      this.getAdposition2()
      this.index = 1;
      this.newVisible = true;
      //获取当前
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
      this.form = {};
      this.costType = 113;
      (this.form.findType = 1),
        (this.form.dqRule = 1),
        (this.extensionType = 119),
        (this.landUrl = ""),
        (this.appId = ""),
        (this.pkgName = ""),
        (this.versioncode = ""),
        (this.versionname = ""),
        (this.size = ""),
        (this.sign = ""),
        (this.md5 = ""),
        (this.areas = []);
      this.value2 = [];
      this.AD_position = [];
      this.appIds = "";
      this.versions = [];
      this.Channel = [];
      this.TypeModels = [];
      this.checkedKey = [];
      this.AppName = 1;
      this.AppVersion = 1;
      this.AppChannel = 1;
      this.models = 1;
      this.book = 1;
      this.dxWl = "";
      this.isFrequency = 0;
      this.frequencPeriod = 115;
      this.frequencNum = "";

      let obj = {
        startDay: "",
        endDay: "",
        value: [],
        set_time: [{}],
        limits: 0,
        time_frame: 1,
        id:'',
        events:{}
      };
      this.append_div = [
        {
          id: "",
          name: "",
          expDuration: null,
          changePage: null,
          changeChapter: null,
          changeType: 1,
          deliveryMode: 105,
          limitArray: [obj],
          limits: [],
        }
      ];
      (this.form.Crowd = 1), (this.dxZdlx = "158");
      this.form.deliveryMode = 104;
    },
    stringToArrayOfNum(numArray, str) {
      if (str) {
        let array = new String(str).split(",");
        for (let item of array) {
          numArray.push(parseInt(item));
        }
      }
    },
    // 确认新建
    saveNews() {
      // 参数判断
      let reg = /http[s]{0,1}:\/\/([\w.]+\/?)\S*/;
      if (!reg.test(this.landUrl)) {
        this.$message("请输入正确的推广链接");
        return;
      }
      if (!this.form.putName) {
        this.$message.error("投放名称不能为空");
        return;
      }
      if (!this.form.select_plan) {
        this.$message.error("订单不能为空");
        return;
      }
      if (this.AD_position.length <= 0) {
        this.$message.error("广告位不能为空");
        return;
      }

      let keys = "";
      if (this.value2) {
        this.value2.forEach(element => {
          keys = keys + element.id + ",";
        });
        keys = keys.substring(0, keys.lastIndexOf(","));
      }
      let that = this;
      let positionTime = [];
      let id = this.AD_position;
      dataMap.forEach(function(value, key, map) {
        let years = key;
        value.forEach(function(element, index, value) {
          var puttime = element.times;
          var id = element.adId;
          var expDuration = that.list_[index].expDuration;
          var changePage = that.list_[index].changePage;
          var changeChapter = that.list_[index].changeChapter;
          let obj = {
            adPositionId: id,
            putTime: puttime.join("").replace(eval(regs), 0),
            years: years,
            expDuration: expDuration,
            changePage: changePage,
            changeChapter: changeChapter
          };
          positionTime.push(obj);
        });
      });

      if (this.costType == 113) {
        let stopSchedule = false;
        positionTime.forEach(element => {
          if (element.putTime.indexOf("1") == -1) {
            this.$message.error("排期不能为空");
            stopSchedule = true;
            return false;
          }
        });
        if (stopSchedule) {
          return;
        }
        let params = {
          putName: this.form.putName,
          oid: this.form.select_plan,
          costType: this.costType,
          price: this.form.price,
          extensionType: this.extensionType,
          landUrl: this.landUrl,
          appId: this.appId,
          pkgName: this.pkgName,
          dxZdlx: this.dxZdlx,
          dxApp: this.appIds,
          dxQd: this.Channel.join(","),
          dxBb: this.versions.join(","),
          dxJx: this.TypeModels.join(","),
          dxWl: this.dxWl,
          dxTs: this.par.join(","),
          preDq: keys,
          dqRule: this.form.dqRule,
          dxMediaType: this.form.media_type,
          adPosition: this.AD_position.join(","),
          adPositionTimes: positionTime
        };
        orderPutadd(params).then(res => {
          let data = res.data;
          if (data.code != "A000000") {
            this.$message.error(data.message);
          }
          this.newVisible = false;
          this.getList();
        });
      } else {
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
            let YY = elementS.value[0][1];
            if (YY < 10) {
              elementS.value[0][1] = "0" + elementS.value[0][1];
            }
            let MM = elementS.value[0][2];
            if (MM < 10) {
              elementS.value[0][2] = "0" + elementS.value[0][2];
            }

            let YY2 = elementS.value[1][1];
            if (YY2 < 10) {
              elementS.value[1][1] = "0" + elementS.value[1][1];
            }
            let MM2 = elementS.value[1][2];
            if (MM2 < 10) {
              elementS.value[1][2] = "0" + elementS.value[1][2];
            }
            (obj.date =
              elementS.value[0].join("") + "-" + elementS.value[1].join("")),
              (obj.times = times.substring(0, times.lastIndexOf(","))),
              (obj.limit = elementS.limit);
            element.limits.push(obj);
          });
        });
        let params = {
          putName: this.form.putName,
          oid: this.form.select_plan,
          costType: this.costType,
          price: this.form.price,
          extensionType: this.extensionType,
          landUrl: this.landUrl,
          appId: this.appId,
          pkgName: this.pkgName,
          dxZdlx: this.dxZdlx,
          dxApp: this.appIds,
          dxQd: this.Channel.join(","),
          dxBb: this.versions.join(","),
          dxJx: this.TypeModels.join(","),
          dxWl: this.dxWl,
          dxTs: this.checkedKey.join(","),
          preDq: keys,
          dqRule: this.form.dqRule,
          limits: this.append_div,
          adPosition: this.AD_position.join(","),
          isFrequency: this.isFrequency,
          frequencPeriod: 115,
          frequencNum: this.frequencNum
        };

        orderPutadd(params).then(res => {
          let data = res.data;
          if (data.code != "A000000") {
            this.$message.error(data.message);
          }
          this.newVisible = false;
          this.getList();
        });
      }
    },

    formatterTypesputStatus(row, column) {
      if (row.putState == 8) {
        return "待投放";
      } else if (row.putState == 6) {
        return "投放结束";
      } else if (row.putState == 1) {
        return "正常";
      } else if (row.putState == 3) {
        return "待确认";
      }
    },
    formatterTyperunStatus(row, column) {
      if (row.state == 0) {
        return "无效";
      } else if (row.state == 1) {
        return "正在投放";
      } else if (row.state == 6) {
        return "已过期";
      } else if (row.state == 8) {
        return "未启动";
      } else if (row.state == 9) {
        return "投放超限额";
      }
    },
    // 模糊订单名称
    querySearchCustomer(queryString, cb) {
      let params = {
        name: this.select_word
      };
      orderslist(params).then(res => {
        //let data = res.data;
        // 调用 callback 返回建议列表的数据
        cb(res.data.data);
        res.data.data.forEach(element => {
          orderId.set(element.name, element.id);
        });
        // if(res.data.code != 'A000000') {
        //   this.$message.error(res.data.message);
        //     }
      });
    },
    handleSelectCustomer(item) {
      this.id = item.id;
    },
    // 模糊广告位
    SearchCustomer(queryString, cb) {
      let params = {
        name: this.select_Customer,
        cp: 1,
        ps: this.ps
      };
      adPositiongetList(params).then(res => {
        let data = res.data;
        // 调用 callback 返回建议列表的数据
        cb(data.data.data);
        data.data.data.forEach(element => {
          uuid.set(element.name, element.id);
        });
        if (data.code != "A000000") {
          this.$message.error(data.message);
        }
      });
    },
    customer_id(item) {
      this.coustom_id = item.id;
      this.coyput;
    },
    // 分页导航
    handleCurrentChange(val) {
      this.cur_page = val;
      let params = {
        putName: this.select_name,
        oid: orderId.get(this.select_word),
        adPosition: uuid.get(this.select_Customer),
        runState: this.value,
        cp: this.cur_page,
        ps: this.ps
      };
      orderPutpages(params).then(res => {
        let data = res.data;
        if (data.code != "A000000") {
          this.$message.error(data.message);
        }
        this.tableData = data.data.data;
        this.total = data.data.totalItemNum;
      });
    },
    getList() {
      let params = { cp: this.cur_page, ps: this.ps };
      orderPutpages(params).then(res => {
        let data = res.data;
        if (data.code != "A000000") {
          this.$message.error(data.message);
        }
        this.tableData = data.data.data;
        this.total = data.data.totalItemNum;
      });
    },
    search() {
      this.$refs.pagination.lastEmittedPage = 1;
      this.cur_page = 1;
      let params = {
        putName: this.select_name,
        oid: orderId.get(this.select_word),
        adPosition: uuid.get(this.select_Customer),
        runState: this.value,
        cp: this.cur_page,
        ps: this.ps
      };
      orderPutpages(params).then(res => {
        let data = res.data;
        this.tableData = data.data.data;
        this.total = data.data.totalItemNum;
      });
    },
    // 广告位
    getAdposition(pid) {
      let params = {
        appId: this.appIds,
        status: 1
      };
      adPositiongetList(params).then(res => {
        let data = res.data;
        if (data.code != "A000000") {
          this.$message.error(data.message);
        }
        this.ADposition = data.data.data;

        data.data.data.forEach(element => {
          uuid.set(element.id, element.name);
        });
        if (pid) {
          this.appositionName = uuid.get(pid);
        }
        this.$forceUpdate();
      });
    },
    getAdposition2(pid) {
      this.appIds = "";
      let params = {
        // appId:this.appIds,
        status: 1
      };
      adPositiongetList(params).then(res => {
        let data = res.data;
        if (data.code != "A000000") {
          this.$message.error(data.message);
        }
        this.ADposition = data.data.data;

        data.data.data.forEach(element => {
          uuid.set(element.id, element.name);
        });
        if (pid) {
          this.appositionName = uuid.get(pid);
        }
        this.$forceUpdate();
      });
    },
    // 定向设置
    terminalType() {
      // let params = {
      //   appId:this.appIds,
      //   status:1
      // };
      // adPositiongetList(params).then(res => {
      // let data = res.data;
      //   if (data.code != "A000000") {
      //     this.$message.error(data.message);
      //   }
      //   this.ADposition = data.data.data;

      //   data.data.data.forEach(element => {
      //     uuid.set(element.id, element.name);
      //   });
      //   if (pid) {
      //     this.appositionName = uuid.get(pid);
      //   }
      //  APP名称
      appgetall().then(res => {
        let data = res.data;
        if (data.code != "A000000") {
          this.$message.error(data.message);
          return;
        }
        this.optionAppName = data.data;
      });
      //  版本号
      AppVersionselect().then(res => {
        let data = res.data;
        if (data.code != "A000000") {
          this.$message.error(data.message);
          return;
        }
        this.optionsAppVersion = data.data;
      });
      //  渠道号
      AppChannelselect().then(res => {
        console.log(res);
        let data = res.data;
        if (data.code != "A000000") {
          this.$message.error(data.message);
          return;
        }
        this.optionsAppChannel = data.data;
      });
      //  机型

      devicemodelsselect().then(res => {
        console.log(res);
        let data = res.data;
        if (data.code != "A000000") {
          this.$message.error(data.message);
          return;
        }
        this.optionsModels = data.data;
      });
      // });
    },

    handleClick(tab, event) {
      console.log(tab);
      if (tab.name == "second_4") {
        if(this.AD_position.length < 1){
          this.$message.error('请先选择广告位')
          this.last_();
        }
        this.sechAD();
        if (this.index == 1) {
          if (this.AD_position.length > 0) {
            this.append_div = [];
          }
          let list = this.append_div;
          this.append_div = [];
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
              name: uuid.get(element),
              deliveryMode: 104,
              limitArray: [limit],
              limits: []
            };
            obj.name = uuid.get(element);
            list.push(obj);
          });
          let _this = this;
          setTimeout(function() {
            _this.append_div = list;
          }, 10);

          // 库存
          let arr = [];
          this.AD_position.forEach(element => {
            let objs = {
              id: element,
              time: this.getMonth
            };
            arr.push(objs);
          });
          let adID = {
            type: 2,
            position: arr
          };
          adPositionstock(adID).then(res => {
            console.log(res);
            this.append_div.forEach(element => {
              console.log(this.append_div);
              element.limitArray.forEach(elementArry => {
                console.log(elementArry);
                let map = res.data.data[element.id];
                console.log(map);
                let time = this.getMonth;
                let array = map[time];
                let obj = {};
                array.forEach(element => {
                  for (let key in element) {
                    obj[key] = element[key] + "";
                  }
                });
                elementArry["events"] = obj;
                this.$forceUpdate();
              });
            });
          });
        }
      }
      if (tab.name == "second_5") {
        let area = "";
        if (this.value2) {
          this.value2.forEach(element => {
            area = area + element.areaName + " ";
          });
        }
        let extensionType = "";
        if (this.extensionType == 119) {
          extensionType = "网站";
        } else if (this.extensionType == 120) {
          extensionType = "IOS应用";
        } else if (this.extensionType == 121) {
          extensionType = "安卓应用";
        } else if (this.extensionType == 122) {
          extensionType = "deeplink";
        }
        let costType = "";
        if (this.costType == 113) {
          costType = "CPT";
        } else if (this.costType == 140) {
          costType = "CPM";
        } else if (this.costType == 141) {
          costType = "CPC";
        }
        let times;
        //  if(this.costType !=113){
        //       times = this.append_div[0].limitArray[0].value[0]+'--'+this.append_div[0].limitArray[0].value[1]
        //   }else{
        //       times = ''
        //   }
        let arr = [];
        this.AD_position.forEach(element => {
          arr.push(uuid.get(parseInt(element)));
        });

        console.log(arr);
        this.last = {
          unitName: this.form.putName,
          times: times,
          deliveryMode: this.deliveryMode == 104 ? "标准投放" : "匀速投放",
          costType: costType,
          extensionType: extensionType,
          dealId: this.dealId,
          appositionName: arr.join(","),
          area: area
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
        putState: 0
      };
      orderPutdelete(params).then(res => {
        console.log(res);
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
      this.date = new Date();
      this.year = this.date.getFullYear();
      this.month = this.date.getMonth() + 1;
      this.day = this.date.getDate();
      this.append_div[row].limitArray.push({
        date: "",
        times: "",
        limit: 0,
        time_frame: 1,
        set_time: [{}],
        value: [
          [this.year, this.month, this.day],
          [this.year, this.month, this.day]
        ],
        events: {}
      });
      this.append_div[row].limits = [];
      // 库存
      let arr = [];
      this.AD_position.forEach(element => {
        let objs = {
          id: element,
          time: this.getMonth
        };
        arr.push(objs);
      });
      let adID = {
        type: 2,
        position: arr
      };
      adPositionstock(adID).then(res => {
        this.append_div.forEach(element => {
          element.limitArray.forEach(elementArry => {
            console.log(elementArry);
            let map = res.data.data[element.id];
            console.log(map);
            let time = this.getMonth;
            let array = map[time];
            let obj = {};
            array.forEach(element => {
              for (let key in element) {
                obj[key] = element[key] + "";
              }
            });
            elementArry["events"] = obj;
            this.$forceUpdate();
            console.log(elementArry["events"]);
          });
        });
      });
      this.$forceUpdate();
    },
    delete_div(row, index) {
      console.log(row, index);
      let list = this.append_div;
      this.append_div = [];
      list[row].limitArray.splice(index, 1);
      list[row].limits = [];

      let _this = this;
      setTimeout(function() {
        _this.append_div = list;
      }, 10);
    },
    // 强制刷新投放时间段
    select1() {
      console.log(111);
      this.$forceUpdate();
    },
    new_time(row, index_, index) {
      console.log(row, index);
      let data = this.append_div[index_].limitArray[index].set_time;
      this.$forceUpdate();
      data.push({ begin: "", end: "" });
    },
    delete_time(row, index_, index) {
      console.log(index);
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
  margin-left: 90%;
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
.mc-day {
  width: 64px !important;
  height: 47px !important;
}
/* .Radio_{
  padding-left: 30px;
} */
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
</style>




