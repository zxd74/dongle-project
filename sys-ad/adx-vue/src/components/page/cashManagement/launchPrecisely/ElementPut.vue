<template>
  <div class="table">
    <div class="container">
      <div class="handle-box">
        <el-input v-model="name" placeholder="请输入单元名称" class="mrl11"></el-input>
        <el-autocomplete
           class="mrl11"
          v-model="select_word"
          value-key="planName"
          :fetch-suggestions="querySearchName"
          placeholder="请输入计划名称"
          @select="handleSelectName"
        ></el-autocomplete>
        <el-autocomplete
          class="mrl11"
          v-model="select_cate"
          value-key="fullName"
          :fetch-suggestions="querySearchCustomer"
          placeholder="请输入客户名称"
          @select="handleSelectCustomer"
        ></el-autocomplete>
        <el-select v-model="value" placeholder="状态" class="mrl11">
          <el-option
            v-for="item in options_"
            :key="item.value"
            :label="item.label"
            :value="item.value"
          ></el-option>
        </el-select>
        <el-button type="primary" icon="search" @click="search">搜索</el-button>
        <el-button type="primary" icon="search" @click="create" v-if="this.readonly !=1">新建单元</el-button>
      </div>
      <el-table :data="tableData" border style="width: 100%" ref="multipleTable">
        <el-table-column prop="id" label="序号" width="50" align="center"></el-table-column>
        <el-table-column prop="putName" label="单元名称" align="center"></el-table-column>
        <el-table-column prop="planName" label="计划名称" align="center"></el-table-column>
        <el-table-column prop="adverName" label="客户" align="center"></el-table-column>
        <el-table-column prop="putLimit" label="每日限额" align="center"></el-table-column>
        <el-table-column prop="price" label="价格" align="center"></el-table-column>
        <el-table-column label="匀速投放" width align="center" v-if="this.readonly !=1">
          <template slot-scope="scope">
            <el-switch
              v-model="scope.row.deliveryMode "
              :active-value="105"
              :inactive-value="104"
              @change="changeStatus($event,scope.row)"
            ></el-switch>
          </template>
        </el-table-column>
        <el-table-column label="运行状态" width align="center" v-if="this.readonly !=1">
          <template slot-scope="scope">
            <el-switch
              v-model="scope.row.runState"
              :active-value="1"
              :inactive-value="0"
              @change="changerunState($event,scope.row)"
            ></el-switch>
          </template>
        </el-table-column>
        <el-table-column
          prop="putState"
          label="单元状态"
          align="center"
          :formatter="formattermediaType"
        ></el-table-column>
        <el-table-column label="操作" width="230" align="center">
          <template slot-scope="scope">
            <el-button size="mini" type="success" @click="handleEdit(scope.$index, scope.row)">编辑</el-button>
            <el-button size="mini" type="danger" @click="handleDelete(scope.$index, scope.row)" v-if="this.readonly !=1">删除</el-button>
            <el-button size="mini" type="danger" @click="Editinfo(scope.$index, scope.row)">报告</el-button>
          </template>
        </el-table-column>
      </el-table>
      <div class="pagination">
       <el-pagination @current-change="handleCurrentChange" layout="total,prev, pager, next,jumper" :total="total"   ref="pagination">
       </el-pagination>
      </div>
    </div>

    <!-- 编辑弹出框 -->
    <el-dialog title="编辑" :visible.sync="editVisible" width="65%">
      <el-tabs v-model="activeName" @tab-click="handleClick">
        <el-tab-pane label="基本信息" name="second_1">
          <el-form ref="form" :model="form" status-icon label-width="100px">
            <el-form-item label="* 选择计划:">
              <el-select v-model="form.select_plan" disabled>
                <el-option
                  v-for="item in options_plan"
                  :key="item.id"
                  :label="item.planName"
                  :value="item.id"
                ></el-option>
              </el-select>
            </el-form-item>
            <el-form-item label="* 单元名称:">
              <el-input v-model="form.putName" style="width:40%"></el-input>
            </el-form-item>
            <div class="add_task" v-for="(end_div,index_) in append_div" :key="index_">
              <div class="btn_right">
                <el-button type="primary" icon="search" @click="add_div">+</el-button>
                <el-button
                  type="primary"
                  icon="search"
                  @click="delete_div(index_)"
                  v-show="index_ != 0"
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
                <el-radio v-model="end_div.time_frame" :label="2">指定时段</el-radio>
                <el-form-item v-if="end_div.time_frame == 2">
                  <div v-for="(time,value_i) in end_div.set_time" :key="value_i" class="DIVS">
                    <el-select v-model="time.begin" placeholder="请选择" change="hores">
                      <el-option
                        v-for="(item,i_index) in hours"
                        :key="i_index"
                        :label="item.label"
                        :value="item.value"
                      ></el-option>
                    </el-select>至
                    <el-select v-model="time.end" placeholder="请选择">
                      <el-option
                        v-for="(item,e_index) in hours"
                        :key="e_index"
                        :label="item.label"
                        :value="item.value"
                      ></el-option>
                    </el-select>
                    <!-- 添加和删除时间按钮  -->
                    <el-button type="primary" icon="search" @click="new_time(index_)">+</el-button>
                    <el-button
                      type="primary"
                      icon="search"
                      @click="delete_time(index_,value_i)"
                      v-show="value_i != 0"
                    >X</el-button>
                  </div>
                </el-form-item>
              </el-form-item>
              <el-form-item label="* 每日限额:">
                <el-input-number v-model="end_div.limits" style="width:40%"></el-input-number>
              </el-form-item>
            </div>
            <el-form-item label="投放方式:">
              <el-radio-group v-model="deliveryMode" @change="TYPEtime">
                <el-radio :label="104">标准投放</el-radio>
                <el-radio :label="105">匀速投放</el-radio>
              </el-radio-group>
            </el-form-item>
            <el-form-item label="计费方式:">
              <el-radio v-model="costType" :label="111">CPM</el-radio>
              <!-- <el-radio v-model="costType" :label="112">CPC</el-radio> -->
            </el-form-item>
            <el-form-item label="* 价格:">
              <el-input-number v-model="prices" style="width:40%"></el-input-number>元
            </el-form-item>
            <el-form-item label="设备频次控制:">
              <el-radio v-model="isFrequency" :label="0">关闭</el-radio>
              <el-radio v-model="isFrequency" :label="1">开启</el-radio>
              <el-form-item label="周期:" v-if="this.isFrequency == 1">
                <el-radio v-model="frequencPeriod" :label="115">天</el-radio>
                <el-radio v-model="frequencPeriod" :label="116">周</el-radio>
                <el-radio v-model="frequencPeriod" :label="117">月</el-radio>
              </el-form-item>
              <el-form-item label="频次值:" v-if="this.isFrequency == 1">
                <el-input-number v-model="frequencNum" style="width:40%"></el-input-number>
              </el-form-item>
            </el-form-item>
            <el-form-item label="ip频次控制:">
              <el-radio v-model="isFrequencyIp" :label="0">关闭</el-radio>
              <el-radio v-model="isFrequencyIp" :label="1">开启</el-radio>
              <el-form-item label="周期:" v-if="this.isFrequencyIp == 1">
                <el-radio v-model="frequencyNumIp" :label="115">天</el-radio>
                <el-radio v-model="frequencyNumIp" :label="116">周</el-radio>
                <el-radio v-model="frequencyNumIp" :label="117">月</el-radio>
              </el-form-item>
              <el-form-item label="频次值:" v-if="this.isFrequencyIp == 1">
                <el-input-number v-model="frequencyPeriodIp" style="width:40%"></el-input-number>
              </el-form-item>
            </el-form-item>
            <el-form-item label="过滤非法设备ID:">
              <el-radio v-model="isfilterDeviceCode" :label="0">关闭</el-radio>
              <el-radio v-model="isfilterDeviceCode" :label="1">开启</el-radio>
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
                <el-input v-model="landUrl" style="width:80%"></el-input>
              </el-form-item>
              <el-form-item label="应用id:" v-if="this.extensionType == 120">
                <el-input v-model="appId" style="width:80%"></el-input>
              </el-form-item>
              <el-form-item label="应用包名:" v-if="this.extensionType == 121||extensionType == 122">
                <el-input v-model="pkgName" style="width:80%"></el-input>
              </el-form-item>
              <el-form-item label="版本号:" v-if="this.extensionType == 121">
                <el-input v-model="versioncode" style="width:80%"></el-input>
              </el-form-item>
              <el-form-item label="版本名称:" v-if="this.extensionType == 121">
                <el-input v-model="versionname" style="width:80%"></el-input>
              </el-form-item>
              <el-form-item label="包大小(字节):" v-if="this.extensionType == 121">
                <el-input v-model="size" style="width:80%"></el-input>
              </el-form-item>
              <el-form-item label="包签名:" v-if="this.extensionType == 121">
                <el-input v-model="sign" style="width:80%"></el-input>
              </el-form-item>
              <el-form-item label="包MD5值:" v-if="this.extensionType == 121">
                <el-input v-model="md5" style="width:80%"></el-input>
              </el-form-item>
              <el-form-item label="系统最低版本要求:" v-if="this.extensionType == 121">
                <el-input v-model="minsdklevel" style="width:80%"></el-input>
              </el-form-item>
            </el-form-item>
            <div class="add_task">
              <el-form-item label="曝光上报地址:">
                <div v-for="(address1,index_) in form.make_address1" :key="index_">
                  <el-input v-model="form.make_address1[index_]" style="width:80%"></el-input>
                  <el-button type="primary" icon="el-icon-plus" @click="make_address"></el-button>
                  <el-button
                    type="primary"
                    icon="el-icon-close"
                    @click="delete_task(index_)"
                    v-show="index_!= 0"
                  ></el-button>
                </div>
              </el-form-item>
            </div>
            <div class="add_task">
              <el-form-item label="点击上报地址:">
                <div v-for="(address2,index_address) in form.click_address1" :key="index_address">
                  <el-input v-model="form.click_address1[index_address]" style="width:80%"></el-input>
                  <el-button type="primary" icon="el-icon-plus" @click="click_address"></el-button>
                  <el-button
                    type="primary"
                    icon="el-icon-close"
                    @click="delete_click(index_address)"
                    v-show="index_address != 0"
                  ></el-button>
                </div>
              </el-form-item>
            </div>
            <el-form-item label="PDB/PD投放:">
              <el-radio v-model="isPdb" :label="0">否</el-radio>
              <el-radio v-model="isPdb" :label="1">是</el-radio>
            </el-form-item>
            <el-form-item label="私有化交易ID:" v-if="this.isPdb ==1">
              <el-input v-model="dealId" style="width:80%"></el-input>
            </el-form-item>
          </el-form>
        </el-tab-pane>
        <el-tab-pane label="定向设置" name="second_2">
          <el-form ref="form" :model="form" status-icon label-width="100px">
            <el-tag>频道定向</el-tag>
            <el-form-item label="主频道:">
              <el-radio v-model="dx_pindao" :label="0">不限</el-radio>
              <el-radio v-model="dx_pindao" :label="1">自定义</el-radio>
              <el-checkbox-group v-model="direction_pd" v-if="this.dx_pindao == 1">
                <el-checkbox
                  :label="item.id"
                  v-for="(item,index1) in channel"
                  :key="index1"
                >{{item.name}}</el-checkbox>
              </el-checkbox-group>
            </el-form-item>
            <el-form-item label="车系:">
              <el-radio v-model="dx_chexi" :label="0">不限</el-radio>
              <el-radio v-model="dx_chexi" :label="1">自定义</el-radio>
              <el-checkbox-group v-model="direction_cx" v-if="this.dx_chexi == 1">
                <el-checkbox
                  :label="item.id"
                  v-for="(item,index2) in cars"
                  :key="index2"
                >{{item.name}}</el-checkbox>
              </el-checkbox-group>
            </el-form-item>
            <el-form-item label="车级别:">
              <el-radio v-model="dx_chejibie" :label="0">不限</el-radio>
              <el-radio v-model="dx_chejibie" :label="1">自定义</el-radio>
              <el-checkbox-group v-model="direction_cjb" v-if="this.dx_chejibie == 1">
                <el-checkbox
                  :label="item.id"
                  v-for="(item,index3) in carrank"
                  :key="index3"
                >{{item.name}}</el-checkbox>
              </el-checkbox-group>
            </el-form-item>
            <el-form-item label="价格:">
              <el-radio v-model="dx_jiage" :label="0">不限</el-radio>
              <el-radio v-model="dx_jiage" :label="1">自定义</el-radio>
              <el-checkbox-group v-model="direction_jg" v-if="this.dx_jiage == 1">
                <el-checkbox
                  :label="item.id"
                  v-for="(item,index4) in price"
                  :key="index4"
                >{{item.name}}</el-checkbox>
              </el-checkbox-group>
            </el-form-item>
            <el-form-item label="地域:">
              <el-radio v-model="dx_diyu" :label="0">不限</el-radio>
              <el-radio v-model="dx_diyu" :label="1">自定义</el-radio>
              <el-checkbox-group v-model="direction_dy" v-if="this.dx_diyu == 1">
                <el-checkbox
                  :label="item.id"
                  v-for="(item,index5) in territory"
                  :key="index5"
                >{{item.name}}</el-checkbox>
              </el-checkbox-group>
            </el-form-item>
            <el-tag>设备定向</el-tag>
            <el-form-item label="终端类型:">
              <el-radio-group v-model="dxZdlx" @change="terminalType">
                  <el-radio  label="22">PC</el-radio>
                  <el-radio  label="23">wap</el-radio>
                  <el-radio  label="158">APP</el-radio>
              </el-radio-group>
            </el-form-item>
             <el-form-item label="操作系统:" v-if="this.dxZdlx == '158'">
              <el-radio v-model="dxCzxt" label>不限</el-radio>
              <el-radio v-model="dxCzxt" label="143">IOS</el-radio>
              <el-radio v-model="dxCzxt" label="144">Android</el-radio>
            </el-form-item>
            <el-form-item label="运营商:">
              <el-radio v-model="dxYys" label>不限</el-radio>
              <el-radio v-model="dxYys" label="128">移动</el-radio>
              <el-radio v-model="dxYys" label="129">联通</el-radio>
              <el-radio v-model="dxYys" label="130">电信</el-radio>
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
                  <el-radio-group v-model="form.dqRule" @change="diquSech">
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
            <el-tag>DMP标签定向</el-tag>
            <div>
              <div v-for="(item,tags) in labelTag" :key="tags">
                <el-form-item :label="item.name">
                  <el-checkbox-group v-model="DMP_list">
                    <el-checkbox
                      :label="item1.id"
                      v-for="(item1,indextag) in item.tagList"
                      :key="indextag"
                    >{{item1.name}}</el-checkbox>
                  </el-checkbox-group>
                </el-form-item>
              </div>
            </div>
          </el-form>
        </el-tab-pane>
        <el-tab-pane label="广告位选择" name="second_3">
          <el-form ref="form" :model="form" status-icon label-width="100px">
            <el-form-item label="* 媒体类型:">
              <el-radio-group v-model="form.media_type" @change="getmdiiaType" disabled>
                <el-radio :label="68">普通媒体</el-radio>
                <el-radio :label="69">联盟媒体</el-radio>
              </el-radio-group>
            </el-form-item>
            <el-form-item label="* 媒体:">
              <el-radio-group v-model="form.media" @change="getAdposition" disabled>
                <el-radio
                  :label="item.mediaUuid"
                  v-for="(item,indexMT) in medias"
                  :key="indexMT"
                >{{item.mediaName}}</el-radio>
              </el-radio-group>
            </el-form-item>
            <el-form-item label="* 广告位:">
              <el-radio-group v-model="form.AD_position" @change="sechAD">
                <el-radio
                  :label="item.id"
                  v-for="(item,indexAD) in ADposition"
                  :key="indexAD"
                  class="radio"
                >{{item.name}}</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-form>
        </el-tab-pane>
        <el-tab-pane label="确认提交" name="second_4">
          <el-form ref="form" :model="last" status-icon>
            <el-form-item label="计划名称:">
              <span>{{last.name}}</span>
            </el-form-item>
            <el-form-item label="单元名称:">
              <span>{{last.unitName}}</span>
            </el-form-item>
            <el-form-item label="投放时间:">
              <span>{{last.times}}</span>
            </el-form-item>
            <el-form-item label="每日限额:">
              <span>{{last.v_price}}</span>
            </el-form-item>
            <el-form-item label="投放方式:">
              <span>{{last.deliveryMode}}</span>
            </el-form-item>
            <el-form-item label="计费方式:">
              <span>{{last.costType}}</span>
            </el-form-item>
            <el-form-item label="投放类型:">
              <span>{{last.extensionType}}</span>
            </el-form-item>
            <el-form-item label="曝光上报地址:">
              <span>{{last.bgAddress}}</span>
            </el-form-item>
            <el-form-item label="点击上报地址:">
              <span>{{last.Clkaddress}}</span>
            </el-form-item>
            <el-form-item label="私有化交易ID:">
              <span>{{last.dealId}}</span>
            </el-form-item>
            <el-form-item label="广告位:">
              <span>{{last.appositionName}}</span>
            </el-form-item>
            <el-form-item label="定向设置:">
              <div class="r_div">
                <el-form-item label="主频道:">
                  <span>{{last.direction_pd}}</span>
                </el-form-item>
                <el-form-item label="车系:">
                  <span>{{last.direction_cx}}</span>
                </el-form-item>
                <el-form-item label="车级别:">
                  <span>{{last.direction_cjb}}</span>
                </el-form-item>
                <el-form-item label="价格:">
                  <span>{{last.direction_jg}}</span>
                </el-form-item>
                <el-form-item label="地域:">
                  <span>{{last.direction_dy}}</span>
                </el-form-item>
                <el-form-item label="终端类型:">
                  <span>{{last.dxZdlx}}</span>
                </el-form-item>
                <el-form-item label="操作系统:">
                  <span>{{last.dxCzxt}}</span>
                </el-form-item>
                <el-form-item label="运营商:">
                  <span>{{last.dxYys}}</span>
                </el-form-item>
                <el-form-item label="网络:">
                  <span>{{last.dxWl}}</span>
                </el-form-item>
                <el-form-item label="定向地区:">
                  <span>{{last.area}}</span>
                </el-form-item>
                <el-form-item label="DMP标签:">
                  <span>{{last.dmp}}</span>
                </el-form-item>
              </div>
            </el-form-item>
          </el-form>
        </el-tab-pane>
      </el-tabs>
      <span slot="footer" class="dialog-footer" v-if="this.readonly !=1">
        <el-button @click="editVisible = false">取 消</el-button>
        <el-button type="primary" @click="saveEdit">确 定</el-button>
      </span>
    </el-dialog>

    <!-- 新建弹出框 -->
    <el-dialog title="新建单元" :visible.sync="newVisible" width="65%">
      <el-tabs v-model="activeName" @tab-click="handleClick">
        <el-tab-pane label="基本信息" name="second_1">
          <el-form ref="form" :model="form" status-icon label-width="100px">
            <el-form-item label="* 选择计划:">
              <el-select v-model="form.select_plan" @change="palanSech">
                <el-option
                  v-for="item in options_plan"
                  :key="item.id"
                  :label="item.planName"
                  :value="item.id"
                ></el-option>
              </el-select>
            </el-form-item>
            <el-form-item label="* 单元名称:">
              <el-input v-model="form.putName" style="width:40%"></el-input>
            </el-form-item>
            <div class="add_task" v-for="(end_div,index_) in append_div" :key="index_">
              <div class="btn_right">
                <el-button type="primary" icon="search" @click="add_div">+</el-button>
                <el-button
                  type="primary"
                  icon="search"
                  @click="delete_div(index_)"
                  v-show="index_ != 0"
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
                <el-radio v-model="end_div.time_frame" :label="2">指定时段</el-radio>
                <el-form-item v-if="end_div.time_frame == 2">
                  <div v-for="(time,value_i) in end_div.set_time" :key="value_i" class="DIVS">
                    <el-select v-model="time.begin" placeholder="请选择">
                      <el-option
                        v-for="(item,i_index) in hours"
                        :key="i_index"
                        :label="item.label"
                        :value="item.value"
                      ></el-option>
                    </el-select>至
                    <el-select v-model="time.end" placeholder="请选择">
                      <el-option
                        v-for="(item,e_index) in hours"
                        :key="e_index"
                        :label="item.label"
                        :value="item.value"
                      ></el-option>
                    </el-select>
                    <!-- 添加和删除时间按钮  -->
                    <el-button type="primary" icon="search" @click="new_time(index_)">+</el-button>
                    <el-button
                      type="primary"
                      icon="search"
                      @click="delete_time(index_,value_i)"
                      v-show="value_i != 0"
                    >X</el-button>
                  </div>
                </el-form-item>
              </el-form-item>
              <el-form-item label="* 每日限额:">
                <el-input-number v-model="end_div.limits" style="width:40%"></el-input-number>
              </el-form-item>
            </div>
            <el-form-item label="投放方式:">
              <el-radio-group v-model="deliveryMode" @change="TYPEtime">
                <el-radio :label="104">标准投放</el-radio>
                <el-radio :label="105">匀速投放</el-radio>
              </el-radio-group>
            </el-form-item>
            <el-form-item label="计费方式:">
              <el-radio v-model="costType" :label="111">CPM</el-radio>
              <!-- <el-radio v-model="costType" :label="112">CPC</el-radio> -->
            </el-form-item>
            <el-form-item label="* 价格:">
              <el-input-number v-model="prices" style="width:40%"></el-input-number>元
            </el-form-item>
            <el-form-item label="设备频次控制:">
              <el-radio v-model="isFrequency" :label="0">关闭</el-radio>
              <el-radio v-model="isFrequency" :label="1">开启</el-radio>
              <el-form-item label="周期:" v-if="this.isFrequency == 1">
                <el-radio v-model="frequencPeriod" :label="115">天</el-radio>
                <el-radio v-model="frequencPeriod" :label="116">周</el-radio>
                <el-radio v-model="frequencPeriod" :label="117">月</el-radio>
              </el-form-item>
              <el-form-item label="频次值:" v-if="this.isFrequency == 1">
                <el-input-number v-model="frequencNum" style="width:40%"></el-input-number>
              </el-form-item>
            </el-form-item>
            <el-form-item label="ip频次控制:">
              <el-radio v-model="isFrequencyIp" :label="0">关闭</el-radio>
              <el-radio v-model="isFrequencyIp" :label="1">开启</el-radio>
              <el-form-item label="周期:" v-if="this.isFrequencyIp == 1">
                <el-radio v-model="frequencyNumIp" :label="115">天</el-radio>
                <el-radio v-model="frequencyNumIp" :label="116">周</el-radio>
                <el-radio v-model="frequencyNumIp" :label="117">月</el-radio>
              </el-form-item>
              <el-form-item label="频次值:" v-if="this.isFrequencyIp == 1">
                <el-input-number v-model="frequencyPeriodIp" style="width:40%"></el-input-number>
              </el-form-item>
            </el-form-item>
            <el-form-item label="过滤非法设备ID:">
              <el-radio v-model="isfilterDeviceCode" :label="0">关闭</el-radio>
              <el-radio v-model="isfilterDeviceCode" :label="1">开启</el-radio>
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
              <el-form-item label="版本号:" v-if="this.extensionType == 121">
                <el-input v-model="versioncode" style="width:80%"></el-input>
              </el-form-item>
              <el-form-item label="版本名称:" v-if="this.extensionType == 121">
                <el-input v-model="versionname" style="width:80%"></el-input>
              </el-form-item>
              <el-form-item label="包大小(字节):" v-if="this.extensionType == 121">
                <el-input v-model="size" style="width:80%"></el-input>
              </el-form-item>
              <el-form-item label="包签名:" v-if="this.extensionType == 121">
                <el-input v-model="sign" style="width:80%"></el-input>
              </el-form-item>
              <el-form-item label="包MD5值:" v-if="this.extensionType == 121">
                <el-input v-model="md5" style="width:80%"></el-input>
              </el-form-item>
              <el-form-item label="系统最低版本要求:" v-if="this.extensionType == 121">
                <el-input v-model="minsdklevel" style="width:80%"></el-input>
              </el-form-item>
            </el-form-item>
            <div class="add_task">
              <el-form-item label="曝光上报地址:">
                <div v-for="(address1,index_) in form.make_address1" :key="index_">
                  <el-input v-model="form.make_address1[index_]" style="width:80%"></el-input>
                  <el-button type="primary" icon="el-icon-plus" @click="make_address"></el-button>
                  <el-button
                    type="primary"
                    icon="el-icon-close"
                    @click="delete_task(index_)"
                    v-show="index_!= 0"
                  ></el-button>
                </div>
              </el-form-item>
            </div>
            <div class="add_task">
              <el-form-item label="点击上报地址:">
                <div v-for="(address2,index_address) in form.click_address1" :key="index_address">
                  <el-input v-model="form.click_address1[index_address]" style="width:80%"></el-input>
                  <el-button type="primary" icon="el-icon-plus" @click="click_address"></el-button>
                  <el-button
                    type="primary"
                    icon="el-icon-close"
                    @click="delete_click(index_address)"
                    v-show="index_address != 0"
                  ></el-button>
                </div>
              </el-form-item>
            </div>
            <el-form-item label="PDB/PD投放:">
              <el-radio v-model="isPdb" :label="0">否</el-radio>
              <el-radio v-model="isPdb" :label="1">是</el-radio>
            </el-form-item>
            <el-form-item label="私有化交易ID:" v-if="this.isPdb ==1">
              <el-input v-model="dealId" style="width:80%"></el-input>
            </el-form-item>
          </el-form>
        </el-tab-pane>
        <el-tab-pane label="定向设置" name="second_2">
          <el-form ref="form" :model="form" status-icon label-width="100px">
            <el-tag>频道定向</el-tag>
            <el-form-item label="主频道:">
              <el-radio v-model="dx_pindao" :label="0">不限</el-radio>
              <el-radio v-model="dx_pindao" :label="1">自定义</el-radio>
              <el-checkbox-group v-model="direction_pd" v-if="this.dx_pindao == 1">
                <el-checkbox
                  :label="item.id"
                  v-for="(item,index1) in channel"
                  :key="index1"
                >{{item.name}}</el-checkbox>
              </el-checkbox-group>
            </el-form-item>
            <el-form-item label="车系:">
              <el-radio v-model="dx_chexi" :label="0">不限</el-radio>
              <el-radio v-model="dx_chexi" :label="1">自定义</el-radio>
              <el-checkbox-group v-model="direction_cx" v-if="this.dx_chexi == 1">
                <el-checkbox
                  :label="item.id"
                  v-for="(item,index2) in cars"
                  :key="index2"
                >{{item.name}}</el-checkbox>
              </el-checkbox-group>
            </el-form-item>
            <el-form-item label="车级别:">
              <el-radio v-model="dx_chejibie" :label="0">不限</el-radio>
              <el-radio v-model="dx_chejibie" :label="1">自定义</el-radio>
              <el-checkbox-group v-model="direction_cjb" v-if="this.dx_chejibie == 1">
                <el-checkbox
                  :label="item.id"
                  v-for="(item,index3) in carrank"
                  :key="index3"
                >{{item.name}}</el-checkbox>
              </el-checkbox-group>
            </el-form-item>
            <el-form-item label="价格:">
              <el-radio v-model="dx_jiage" :label="0">不限</el-radio>
              <el-radio v-model="dx_jiage" :label="1">自定义</el-radio>
              <el-checkbox-group v-model="direction_jg" v-if="this.dx_jiage == 1">
                <el-checkbox
                  :label="item.id"
                  v-for="(item,index4) in price"
                  :key="index4"
                >{{item.name}}</el-checkbox>
              </el-checkbox-group>
            </el-form-item>
            <el-form-item label="地域:">
              <el-radio v-model="dx_diyu" :label="0">不限</el-radio>
              <el-radio v-model="dx_diyu" :label="1">自定义</el-radio>
              <el-checkbox-group v-model="direction_dy" v-if="this.dx_diyu == 1">
                <el-checkbox
                  :label="item.id"
                  v-for="(item,index5) in territory"
                  :key="index5"
                >{{item.name}}</el-checkbox>
              </el-checkbox-group>
            </el-form-item>
            <el-tag>设备定向</el-tag>
            <el-form-item label="终端类型:">
               <el-radio-group v-model="dxZdlx" @change="terminalType">
                  <el-radio  label="22">PC</el-radio>
                  <el-radio  label="23">wap</el-radio>
                  <el-radio  label="158">APP</el-radio>
              </el-radio-group>
            </el-form-item>
            <el-form-item label="操作系统:" v-if="this.dxZdlx == '158'">
              <el-radio v-model="dxCzxt" label>不限</el-radio>
              <el-radio v-model="dxCzxt" label="143">IOS</el-radio>
              <el-radio v-model="dxCzxt" label="144">Android</el-radio>
            </el-form-item>
            <el-form-item label="运营商:">
              <el-radio v-model="dxYys" label>不限</el-radio>
              <el-radio v-model="dxYys" label="128">移动</el-radio>
              <el-radio v-model="dxYys" label="129">联通</el-radio>
              <el-radio v-model="dxYys" label="130">电信</el-radio>
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
            <el-tag>DMP标签定向</el-tag>
            <div>
              <div v-for="(item,tags) in labelTag" :key="tags">
                <el-form-item :label="item.name">
                  <el-checkbox-group v-model="DMP_list">
                    <el-checkbox
                      :label="item1.id"
                      v-for="(item1,indextag) in item.tagList"
                      :key="indextag"
                    >{{item1.name}}</el-checkbox>
                  </el-checkbox-group>
                </el-form-item>
              </div>
            </div>
          </el-form>
        </el-tab-pane>
        <el-tab-pane label="广告位选择" name="second_3">
          <el-form ref="form" :model="form" status-icon label-width="100px">
            <el-form-item label="媒体类型:">
              <el-radio-group v-model="form.media_type" @change="getmdiiaType">
                <el-radio :label="68">普通媒体</el-radio>
                <el-radio :label="69">联盟媒体</el-radio>
              </el-radio-group>
            </el-form-item>
            <el-form-item label="媒体:">
              <el-radio-group v-model="form.media" @change="getAdposition">
                <el-radio
                  :label="item.mediaUuid"
                  v-for="(item,indexMT) in medias"
                  :key="indexMT"
                >{{item.mediaName}}</el-radio>
              </el-radio-group>
            </el-form-item>
            <el-form-item label="* 广告位:">
              <el-radio-group v-model="form.AD_position" @change="sechAD">
                <el-radio
                  :label="item.id"
                  v-for="(item,indexAD) in ADposition"
                  :key="indexAD"
                  class="radio"
                >{{item.name}}</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-form>
        </el-tab-pane>
        <el-tab-pane label="确认提交" name="second_4">
          <el-form ref="form" :model="last" status-icon>
            <el-form-item label="计划名称:">
              <span>{{last.name}}</span>
            </el-form-item>
            <el-form-item label="单元名称:">
              <span>{{last.unitName}}</span>
            </el-form-item>
            <el-form-item label="投放时间:">
              <span>{{last.times}}</span>
            </el-form-item>
            <el-form-item label="每日限额:">
              <span>{{last.v_price}}</span>
            </el-form-item>
            <el-form-item label="投放方式:">
              <span>{{last.deliveryMode}}</span>
            </el-form-item>
            <el-form-item label="计费方式:">
              <span>{{last.costType}}</span>
            </el-form-item>
            <el-form-item label="投放类型:">
              <span>{{last.extensionType}}</span>
            </el-form-item>
            <el-form-item label="曝光上报地址:">
              <span>{{last.bgAddress}}</span>
            </el-form-item>
            <el-form-item label="点击上报地址:">
              <span>{{last.Clkaddress}}</span>
            </el-form-item>
            <el-form-item label="私有化交易ID:">
              <span>{{last.dealId}}</span>
            </el-form-item>
            <el-form-item label="广告位:">
              <span>{{last.appositionName}}</span>
            </el-form-item>
            <el-form-item label="定向设置:">
              <div class="r_div">
                <el-form-item label="主频道:">
                  <span>{{last.direction_pd}}</span>
                </el-form-item>
                <el-form-item label="车系:">
                  <span>{{last.direction_cx}}</span>
                </el-form-item>
                <el-form-item label="车级别:">
                  <span>{{last.direction_cjb}}</span>
                </el-form-item>
                <el-form-item label="价格:">
                  <span>{{last.direction_jg}}</span>
                </el-form-item>
                <el-form-item label="地域:">
                  <span>{{last.direction_dy}}</span>
                </el-form-item>
                <el-form-item label="终端类型:">
                  <span>{{last.dxZdlx}}</span>
                </el-form-item>
                <el-form-item label="操作系统:">
                  <span>{{last.dxCzxt}}</span>
                </el-form-item>
                <el-form-item label="运营商:">
                  <span>{{last.dxYys}}</span>
                </el-form-item>
                <el-form-item label="网络:">
                  <span>{{last.dxWl}}</span>
                </el-form-item>
                <el-form-item label="定向地区:">
                  <span>{{last.area}}</span>
                </el-form-item>
                <el-form-item label="DMP标签:">
                  <span>{{last.dmp}}</span>
                </el-form-item>
              </div>
            </el-form-item>
          </el-form>
        </el-tab-pane>
      </el-tabs>
      <span slot="footer" class="dialog-footer">
        <el-button style="margin-top: 12px;" @click="last_" v-if="activeName != 'second_1'">上一步</el-button>
        <el-button style="margin-top: 12px;" @click="next_" v-if="activeName != 'second_4'">下一步</el-button>
        <!-- <el-button @click="newVisible = false">取 消</el-button> -->
        <el-button type="primary" @click="saveNews" v-if="activeName == 'second_4'">确 定</el-button>
      </span>
    </el-dialog>

    <!-- 删除提示框 -->
    <el-dialog title="提示" :visible.sync="delVisible" width="300px" center>
      <div class="del-dialog-cnt">删除不可恢复，是否确定删除？</div>
      <span slot="footer" class="dialog-footer">
        <el-button @click="delVisible = false">取 消</el-button>
        <el-button type="primary" @click="deleteRow">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import {
  putpage,
  planlist,
  dicmapList,
  tagsgetDxTags,
  areaGroupgetAreasByNameAndType,
  adPositiongetList
} from "@/api/Api.js";
import {
  putadd,
  putinfo,
  putupdate,
  putdelete,
  diclistByIdStr,
  planpage,
  CustomerList,
  flowsourcegetallbytype
} from "@/api/Api.js";

import treeTransfer from "el-tree-transfer"; // 引入

// import picker from "../../../../components/common/picker/index.vue";

const uuid = new Map();
const names = new Map();
const plannames = new Map();
const costrmNames = new Map();
const medioType =new Map();
export default {
  name: "basetable",
  data() {
    return {
      title: ["城市", "已选城市"],
      mode: "transfer",
      fromData: [],
      toData: [],
      areas: [],
      value2: [],
      tableData: [],
      data: [],
      medias: [],
      times: "",
      ADposition: [],
      cur_page: 1,
      total: 1,
      ps: 10,
      city: "",
      name: "",
      pickerOptions1: {
          disabledDate(time) {
            return time.getTime() + 3600 * 1000 * 24 <= Date.now();
          },
        },
      //
      last: {},
      // 初始化选项
      time_frame: 1,
      deliveryMode: 104,
      costType: 111,
      prices: "",
      isFrequency: 0,
      isFrequencyIp: 0,
      isfilterDeviceCode: 0,
      isPdb: 0,
      frequencPeriod: "",
      frequencNum: "",
      frequencyNumIp: "",
      frequencyPeriodIp: "",
      extensionType: 119,
      dx_pindao: 0,
      dx_chexi: 0,
      dx_chejibie: 0,
      dx_jiage: 0,
      dx_diyu: 0,
      dxZdlx: 22,
      media: "",
      AD_position: "",

      select_cate: "",
      select_word: "",
      del_list: [],
      is_search: false,
      editVisible: false,
      delVisible: false,
      newVisible: false,
      template_dialog: false,
      currentRow: null,
      idx: -1,
      activeName: "second_1",
      time: "",
      unitLimit: "",
      labelTag: [],
      checkTag: [],
      DMP_list: [],
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
      channel: [],
      cars: [],
      carrank: [],
      price: [],
      territory: [],
      // temps:[1],
      direction_pd: [],
      direction_cx: [],
      direction_cjb: [],
      direction_jg: [],
      direction_dy: [],
      bg_address: "",
      sb_address: "",
      appositionName: "",

      appId: "",
      pkgName: "",
      versioncode: "",
      versionname: "",
      size: "",
      sign: "",
      md5: "",
      minsdklevel: "",
      landUrl: "",
      dealId: "",

      dxCzxt: "",
      dxYys: "",
      dxWl: "",

      findType: "",

      interest: "1",
      channel: "1",
      order: "1",
      position: "1",
      guolv: "1",
      zdlx: "1",
      diqu: "1",
      system: "1",
      yys: "1",
      network: "1",
      direction: "1",
      rule: "",
    
      AD_position: "1",
      find: "1",
      APP_zy: "",
      find_location1: "1",
      guolv_APP: "1",
      xtyh: "1",
      cpa: "",
      time: "",

      syID: "",
      data_report: null,
      // make_address1: [{ impMonitorUrl: "" }],
      // click_address1: [{ clkMonitorUrl: "" }],
      // click_shebei1: [{ click_shebei: "" }],
      put_PDB: "1",
      charge_mode: "1",
      number_facility: "1",
      number_ip: "1",
      number_id: "2",
      number_guiyin: "1",
      put_type: "1",
      age: "1",
      checked: false,
      sex: "1",
      Education: "1",

      interest: "1",
      channel: "1",
      order: "1",
      position: "1",
      guolv: "1",
      zdlx: "1",
      diqu: "1",
      system: "1",
      yys: "1",
      network: "1",
      direction: "1",
      rule: "",

      AD_position: "1",
      find: "1",
      APP_zy: "",
      find_location1: "1",
      guolv_APP: "1",
      xtyh: "1",
      cpa: "",
      time: "",

      syID: "",

      form: {
        id: 0,
        select_plan: "",
        putName: "",
        dqRule: "1",
        Crowd: 1,

        name: "",
        date: "",
        switch: true,
        add_time: "",
        options1: "",
        type: 1,
        types: null,
        flow_management: null,
        data_report: null,
        make_address1: [""],
        click_address1: [""],
        // click_shebei1: [{ click_shebei: "" }],
        put_PDB: "1",
        charge_mode: "1",
        number_facility: "1",
        number_ip: "1",
        number_id: "2",
        number_guiyin: "1",
        put_type: "1",
        age: "1",
        checked: false,
        sex: "1",
        Education: "1",
        interest: "1",
        channel: "1",
        order: "1",
        position: "1",
        guolv: "1",
        zdlx: "1",
        diqu: "1",
        system: "1",
        yys: "1",
        network: "1",
        direction: "1",
        rule: "",
        media_type: 68,
        AD_position: "1",
        find: "1",
        APP_zy: "",
        find_location1: "1",
        guolv_APP: "1",
        xtyh: "1",
        cpa: "",
        time: "",

        syID: "",

        value: "",
    
      },
      options_: [
        {
          value: 0,
          label: "暂停"
        },
        {
          value: 1,
          label: "启用"
        }
      ],
      options_plan: [
        {
          value: "",
          label: ""
        }
      ],
      set_time: [{ begin: "", end: "" }],
      value: "",
      append_div: [{ set_time: [{}],times:'' }],
      planname: "",
      readonly:'',
    };
  },
  watch: {
    "form.deliveryMode": function(newValue, oldValue) {
      console.log(newValue);
      this.form = Object.assign({}, this.form);
    }
  },

  created() {
    this.getList();
    this.getPlan();
    this.dicmapList();
    this.getTags();
    this.getmdiiaType();
    this.readonly = this.$store.state.map.get(this.$store.state.currentPath);
    this.terminalType();
  },

  methods: {
    terminalType(){
        // this.form.media =''
        // this.form.AD_position =''
        // this.ADposition =[]
        // this.$forceUpdate()
       let params = {
        flowUuid: this.form.media,
        terminal: this.dxZdlx,
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
      });
    },   
    sechAD(value){
      console.log(0)
      this.$forceUpdate()
    },
    hores(value){
       this.$forceUpdate()
    },
    palanSech(value){
        console.log(value)
       this.$forceUpdate()
    },
    //模糊搜索计划名称
    querySearchName(queryString, cb) {
      let params = {
        planName: this.select_word,
        currentPage: this.cur_page,
        pageSize: this.ps
      };
      planpage(params).then(res => {
        //let data = res.data;
        // console.log(data)
        // 调用 callback 返回建议列表的数据
        res.data.result.data.forEach(element => {
          plannames.set(element.planName, element.id);
        });
        cb(res.data.result.data);
        if (res.data.code != "A000000") {
          this.$message.error(data.message);
        }
      });
    },
    handleSelectName(item) {
      // console.log(item);
      this.id = item.id;
    },
    querySearchCustomer(queryString, cb) {
      let params = {
        fullName: this.select_cate,
        cp: 1,
        ps: 20,
        type: 2,
        putType:19
      };
      CustomerList(params).then(res => {
      let data = res.data;
        // console.log(data)
        // 调用 callback 返回建议列表的数据
        data.data.data.forEach(element => {
          costrmNames.set(element.fullName, element.id);
        });
        cb(data.data.data);
        if (data.code != "A000000") {
          this.$message.error(data.message);
        }
      });
    },
    handleSelectCustomer(item) {
      // console.log(item);
      this.id = item.id;
    },
    // 开关
    changeStatus(val, row) {
      let item = row;
      let params = {
        id: item.id,
        deliveryMode: item.deliveryMode,
        putType:19,
      };
      putupdate(params).then(res => {
        console.log(res);
        // this.getList();
      });
    },
    // 开关
    changerunState(val, row) {
      let item = row;
      let params = {
        id: item.id,
        runState: item.runState,
        putType:19,
      };
      putupdate(params).then(res => {
        console.log(res);
        // this.getList();
      });
    },
    // 搜索
    search() {
      this.$refs.pagination.lastEmittedPage = 1
      this.cur_page = 1;
      let params = {
        putType:19,
        putName: this.name,
        pid: plannames.get(this.select_word),
        adverId: costrmNames.get(this.select_cate),
        runState: this.value,
        cp: this.cur_page,
        ps: this.ps
      };
      putpage(params).then(res => {
        console.log(res)
        // let data = res
        if (res.data.code != 'A000000') {
              this.$message.error(res.data.message);
              return;
          }
        this.tableData = res.data.result.data;
        this.total = res.data.result.totalItemNum;
      });
    },

    TYPEtime(value) {
      // this.form = Object.assign({}, this.form)
    },
    diquSech() {
      // console.log(123);
      this.form = Object.assign({}, this.form);
    },
    diquSech2() {
      console.log(123);
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
    // 定向频道
    dicmapList() {
      // 频道
      let params = {
        gid: 149
      };
      dicmapList(params).then(res => {
      let data = res.data;
        if (data.code != "A000000") {
          this.$message.error(data.message);
        }
        let list = [];
        for (var key in data.data) {
          let obj = { id: data.data[key], name: key };
          list.push(obj);
        }
        // console.log(res)
        this.channel = list;
      });
      // 车系
      let cars = {
        gid: 150
      };
      dicmapList(cars).then(res => {
      let data = res.data;
        if (data.code != "A000000") {
          this.$message.error(data.message);
        }
        let list = [];
        for (var key in data.data) {
          let obj = { id: data.data[key], name: key };
          list.push(obj);
        }
        this.cars = list;
      });
      // 车级别
      let carrank = {
        gid: 77
      };
      dicmapList(carrank).then(res => {
      let data = res.data;
        if (data.code != "A000000") {
          this.$message.error(data.message);
        }
        let list = [];
        for (var key in data.data) {
          let obj = { id: data.data[key], name: key };
          list.push(obj);
        }
        // console.log(list)
        this.carrank = list;
      });
      // 价格
      let price = {
        gid: 78
      };
      dicmapList(price).then(res => {
      let data = res.data;
        if (data.code != "A000000") {
          this.$message.error(data.message);
        }
        let list = [];
        for (var key in data.data) {
          let obj = { id: data.data[key], name: key };
          list.push(obj);
        }
        // console.log(list)
        this.price = list;
      });
      // 地域
      let territory = {
        gid: 151
      };
      dicmapList(territory).then(res => {
      let data = res.data;
        if (data.code != "A000000") {
          this.$message.error(data.message);
        }
        let list = [];
        for (var key in data.data) {
          let obj = { id: data.data[key], name: key };
          list.push(obj);
        }
        // console.log(list)
        this.territory = list;
      });
    },
    // 定向标签
    getTags() {
      tagsgetDxTags().then(res => {
      let data = res.data;
        if (data.code != "A000000") {
          this.$message.error(data.message);
        }
        // data.data.forEach(element => {
        //   element.DMP_list = [];
        // });
        this.labelTag = data.data;

        // console.log(data)
      });
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
    // 媒体
    getmdiiaType() {
      let params = {
        type: this.form.media_type
      };
      flowsourcegetallbytype(params).then(res => {
      let data = res.data;
        if (data.code != "A000000") {
          this.$message.error(data.message);
        }
        this.medias = data.data;

        // console.log(data)
      });
    },
    // 广告位
    getAdposition(pid) {
      let params = {
        flowUuid: this.form.media,
        terminal: this.dxZdlx,
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
        // console.log(this.ADposition)
      });
    },

    // 初始化订单
    getPlan() {
      let params = {
        planName: this.form.select_plan
      };
      planlist(params).then(res => {
        this.options_plan = res.data.result;
        res.data.result.forEach(element => {
          names.set(element.id, element.planName);
        });
      });
    },
    // 格式化数据
    formattermediaType(row, column) {
      if (row.putState == 1) {
        return "正常";
      } else if (row.putState == 8) {
        return "未启动";
      } else if (row.putState == 6) {
        return "未开始";
      } else if (row.putState == 9) {
        return "单元超限额";
      } else if (row.putState == 5) {
        return "账户余额不足";
      }
    },

    // 分页导航
    handleCurrentChange(val) {
          this.cur_page = val;
       let params = {
        putType:19,
        putName: this.name,
        pid: plannames.get(this.select_word),
        adverId: costrmNames.get(this.select_cate),
        runState: this.value,
        cp: this.cur_page,
        ps: this.ps
      };
      putpage(params).then(res => {
        console.log(res)
        // let data = res
        if (res.data.code != 'A000000') {
              this.$message.error(res.data.message);
              return;
          }
        this.tableData = res.data.result.data;
        this.total = res.data.result.totalItemNum;
      });
    },
    // 初始化列表
    getList() {
      let params = { cp: this.cur_page, ps: this.ps, putType: 19 };
      putpage(params).then(res => {
          if (res.data.code != 'A000000') {
              this.$message.error(res.data.message);
              return;
          }
        this.tableData = res.data.result.data;
        this.total = res.data.result.totalItemNum;
        // console.log(res)
      });
    },
    // 新建
    create() {
   
        this.newVisible = true;
        this.areas =[],
        this.value2 =[],
        this.form.media = this.medias[0].mediaUuid ,

        this.form.make_address1= [""],
        this.form.click_address1= [""],
        (this.form.select_plan = ""),
        (this.form.putName = ""),
        (this.activeName = "second_1");
        this.form.findType =1,
        this.form.dqRule =1,
        this.extensionType = 119,
        this.landUrl = "",
        this.appId = "",
        this.pkgName = "",
        this.versioncode = "",
        this.versionname = "",
        this.size = "",
        this.sign = "",
        this.md5 = "",
        this.frequencyNumIp =115,
        this.frequencPeriod =115,
        this.dxZdlx = '22',
        this.minsdklevel = "",
        (this.dealId = ""),
        (this.dxYys = ""),
        (this.dxWl = ""),
        (this.deliveryMode = 104),
        (this.costType = 111),
        (this.prices = ""),
        (this.isFrequency = 0),
        (this.isFrequencyIp = 0),
        (this.isfilterDeviceCode = 0),
        (this.isPdb = 0),
        (this.frequencNum = ""),
        (this.frequencyPeriodIp = ""),
        (this.form.Crowd = 1),
        (this.dx_pindao = 0),
        (this.dx_chexi = 0),
        (this.dx_chejibie = 0),
        (this.dx_jiage = 0),
        (this.dx_diyu = 0),
        (this.dxZdlx = '22'),
        (this.direction_pd = []),
        (this.direction_cx = []),
        (this.direction_cjb = []),
        (this.direction_jg = []),
        (this.direction_dy = []),
        (this.form.media_type = 68), 
        (this.ADposition = []),
        (this.DMP_list = []),
        (this.append_div = [{ time_frame: 1, set_time: [{}] }]);

         let params = {
        flowUuid:this.medias[0].mediaUuid,
        terminal: this.dxZdlx,
      };
      adPositiongetList(params).then(res => {
      let data = res.data;
        if (data.code != "A000000") {
          this.$message.error(data.message);
        }
        this.ADposition = data.data.data;
      
      });
   
    },
    saveNews() {
      let reg = /(http|ftp|https):\/\/[\w\-_]+(\.[\w\-_]+)+([\w\-\.,@?^=%&:/~\+#]*[\w\-\@?^=%&/~\+#])?/
       if(!reg.test(this.landUrl)){
        this.$message('请输入正确的推广链接')
        return;
      }
        let flag = true;
        this.append_div.forEach(element => {
        console.log(element)
        if(!element.startDay) {
            this.$message.error('开始日期不能为空');
            flag = false;
            return;
        }
        if(!element.endDay) {
            this.$message.error('结束日期不能为空');
            flag = false;
            return;
        }
        if(!element.limits) {
            this.$message.error('限额不能为空');
            flag = false;
            return;
        }
      });
      if(!flag) {
        return;
      }
      // 参数判断
      if (!this.form.select_plan) {
        this.$message.error("计划不能为空");
        return;
      }
      if (!this.form.putName) {
        this.$message.error("单元名称不能为空");
        return;
      }
    
      if (!this.prices) {
        this.$message.error("价格为空");
        return;
      }
      if (!this.form.AD_position) {
        this.$message.error("广告位为空");
        return;
      }
    
      // console.log(this.append_div)
      this.limits = [];
      let list = [];
      this.append_div.forEach(element => {
        let times = "";
        if (element.set_time) {
          element.set_time.forEach(element1 => {
              if (element1.begin && element1.end) {
                  times = times + element1.begin + "-" + element1.end + ",";
              }
          });
        }

        let obj = {
          date: element.startDay + "-" + element.endDay,
          times: times.substring(0, times.lastIndexOf(',')),
          limit: element.limits
          // time_frame: element.time_frame,
        };
        list.push(obj);
      });
      console.log(list)
      // 城市
      let keys = "";
      if (this.value2) {
        this.value2.forEach(element => {
          keys = keys + element.id + ",";
        });
       keys = keys.substring(0, keys.lastIndexOf(','));
      }
      // console.log(this.value2)
      let dmps = new Map();
      let params = {
        dxMediaType:this.form.media_type,
        putType: 19,
        pid: this.form.select_plan,
        putName: this.form.putName,
        limits: list,
        deliveryMode: this.deliveryMode,
        costType: this.costType,
        price: this.prices,
        isFrequency: this.isFrequency,
        frequencPeriod: this.frequencPeriod,
        frequencNum: this.frequencNum,
        isFrequencyIp: this.isFrequencyIp,
        frequencyNumIp: this.frequencyNumIp,
        requencyPeriodIp: this.requencyPeriodIp,
        isfilterDeviceCode: this.isfilterDeviceCode,
        extensionType: this.extensionType,
        landUrl: this.landUrl,
        appId: this.appId,
        pkgName: this.pkgName,
        versioncode: this.versioncode,
        versionname: this.versionname,
        size: this.size,
        sign: this.sign,
        md5: this.md5,
        minsdklevel: this.minsdklevel,
        impMonitorUrl: this.form.make_address1.join(','),
        clkMonitorUrl: this.form.click_address1.join(','),

        isPdb: this.isPdb,
        dealId: this.dealId,
        dxChannel: this.direction_pd.join(","),
        dxGgwSeriesId: this.direction_cx.join(","),
        dxGgwLevelId: this.direction_cjb.join(","),
        dxGgwPriceTagId: this.direction_jg.join(","),
        dxGgwAreaId: this.direction_dy.join(","),
        dxZdlx: this.dxZdlx,
        dxYys: this.dxYys,
        dxWl: this.dxWl,
        dqRule: this.form.dqRule,
        preDq: keys,
        // putCustomDxs:this.DMP_list.join(','),
        dxMedia: this.form.media,
        adPosition: this.form.AD_position,
        dxDmp: this.DMP_list.join(",")
      };
      // console.log(params);
      putadd(params).then(res => {
        console.log(res);
      let data = res.data;
        // console.log(data)
        if (data.code != "A000000") {
          this.$message.error(data.message);
        }
        this.newVisible = false;
        this.getList();
      });
    },
     stringToArrayOfNum(numArray,str) {
      if (str) {
        let array = new String(str).split(",");
        for (let item of array) {
          numArray.push(parseInt(item));
        }
      }
    },
    // 编辑
    handleEdit(index, row) {
      this.activeName = "second_1";
      this.form = {};
      this.idx = index;
      this.form.id = row.id;
      let params = {
        id: row.id
        // putType: 19
      };
      this.DMP_list = [];
      this.bg_address = "";
      this.sb_address = "";
      this.times = "";
      this.direction_pd = [];
      this.direction_cx = [];
      this.direction_cjb = [];
      this.direction_jg = [];
      this.direction_dy = [];
      this.areas =[],
      this.value2 =[],
      this.city ="",
      this.append_div =[]
      this.form.findType =1,
      this.form.findType =1
      putinfo(params).then(res => {
        if (res.data.code != "A000000") {
          this.$message.error(res.data.message);
        }
        let lists = JSON.parse(res.data.result.limits);
        lists.forEach(element => {
          let m_times = [];
          if (element.times) {
            let times = element.times.split(",");
            times.forEach(_element => {
              if (_element) {
                let o = _element.split("-");
                let time = { begin: o[0], end: o[1] };
                m_times.push(time);
              }
            });
          }
          let datas = ["", ""];
          if (element.date) {
            datas = element.date.split("-");
            this.times = this.times + element.date + ",";
          }
          let obj = {
            startDay: datas[0],
            endDay: datas[1],
            set_time: m_times.length == 0 ? [{}] : m_times,
            limits: element.limit,
            time_frame: m_times.length > 0 ? 2 : 1
          };
          this.append_div.push(obj);
        });
          this.form.media_type = res.data.result.dxMediaType,
          (this.form.select_plan = res.data.result.pid),
          (this.form.putName = res.data.result.putName),
          // this.append_div = res.data.result.limits,
          (this.deliveryMode = res.data.result.deliveryMode),
          (this.costType = res.data.result.costType),
          (this.prices = res.data.result.price),
          (this.isFrequency = res.data.result.isFrequency),
          (this.frequencPeriod = res.data.result.frequencPeriod),
          (this.frequencNum = res.data.result.frequencNum),
          (this.isFrequencyIp = res.data.result.isFrequencyIp),
          (this.frequencyNumIp = res.data.result.frequencyNumIp),
          (this.requencyPeriodIp = res.data.result.requencyPeriodIp),
          (this.isfilterDeviceCode = res.data.result.isfilterDeviceCode),
          (this.extensionType = res.data.result.extensionType),
          (this.landUrl = res.data.result.landUrl),
          (this.appId = res.data.result.appId),
          (this.pkgName = res.data.result.pkgName),
          (this.versioncode = res.data.result.versioncode),
          (this.versionname = res.data.result.versionname),
          (this.size = res.data.result.size),
          (this.sign = res.data.result.sign),
          (this.md5 = res.data.result.md5),
          (this.minsdklevel = res.data.result.minsdklevel),
          (this.form.make_address1 = res.data.result.impMonitorUrl
            ? res.data.result.impMonitorUrl.split(',')
            : [""]),
          (this.form.click_address1 = res.data.result.clkMonitorUrl
            ? res.data.result.clkMonitorUrl.split(',')
            : [""]),
          (this.isPdb = res.data.result.isPdb),
          (this.dealId = res.data.result.dealId),
          res.data.result.dxChannel
          ? ((this.stringToArrayOfNum(this.direction_pd,res.data.result.dxChannel)),this.dx_pindao =1)
          : (this.direction_pd = [],this.dx_pindao =0);
        res.data.result.dxGgwSeriesId
          ? (this.stringToArrayOfNum(this.direction_cx,res.data.result.dxGgwSeriesId),this.dx_chexi =1)
          : (this.direction_cx = [],this.dx_chexi =0);
        res.data.result.dxGgwLevelId
          ? ((this.stringToArrayOfNum(this.direction_cjb,res.data.result.dxGgwLevelId)),this.dx_chejibie =1)
          : (this.direction_cjb = [],this.dx_chejibie =0);

        res.data.result.dxGgwPriceTagId
          ? ((this.stringToArrayOfNum(this.direction_jg,res.data.result.dxGgwPriceTagId)),this.dx_jiage =1)
          : (this.direction_jg = [],this.dx_jiage =0);
        res.data.result.dxGgwAreaId
          ? ((this.stringToArrayOfNum(this.direction_dy,res.data.result.dxGgwAreaId)),this.dx_diyu =1)
          : (this.direction_dy = [],this.dx_diyu =0 );
          (this.dxZdlx = res.data.result.dxZdlx),
          (this.dxYys = res.data.result.dxYys),
          (this.dxWl = res.data.result.dxWl),
          (this.form.dqRule = res.data.result.dqRule),
          // res.data.result.putCustomDxs ? this.DMP_list = res.data.result.putCustomDxs.split(",") : this.DMP_list = [],
          (this.form.media = res.data.result.dxMedia),
          (this.form.AD_position = res.data.result.adPosition),
          (this.checkTag = res.data.result.dxDmp.split(",")),
          this.checkTag.forEach(element => {
            if(!isNaN(parseInt(element))) {
                this.DMP_list.push(parseInt(element));
            }
          });
        // this.labelTag.forEach(element => {
        //   element.DMP_list = this.checkTag;
        // });
        this.getAdposition(res.data.result.adPosition);
        let par = {
          idStr: res.data.result.preDq
        };
        diclistByIdStr(par).then(res => {
          console.log(res);
          if(res.data.data && res.data.data.length !=0){
              this.form.Crowd = 2
          }else{
               this.form.Crowd = 1
          }
          this.value2 = res.data.data;
          // this.tableData = res.data.result.data
          // this.total = res.data.result.totalItemNum;
          // console.log(res)
        });
        this.editVisible = true;
      });
    },
    // 保存编辑
    saveEdit() {
       if (!this.form.putName) {
        this.$message.error("单元名称不能为空");
        return;
      }
      if (!this.prices) {
        this.$message.error("价格为空");
        return;
      }
      if (!this.form.AD_position) {
        this.$message.error("广告位为空");
        return;
      }
    let reg = /(http|ftp|https):\/\/[\w\-_]+(\.[\w\-_]+)+([\w\-\.,@?^=%&:/~\+#]*[\w\-\@?^=%&/~\+#])?/
       if(!reg.test(this.landUrl)){
        this.$message('请输入正确的推广链接')
        return;
      }
      this.limits = [];
      let list = [];
      this.append_div.forEach(element => {
        let times = "";
        if (element.startDay && element.endDay) {
          if (element.time_frame == 2) {
            element.set_time.forEach(element1 => {
              times = times + element1.begin + "-" + element1.end + ",";
            });
          }
          let obj = {
            date: element.startDay + "-" + element.endDay,
            times: times.substring(0, times.lastIndexOf(',')),
            limit: element.limits
          };
          list.push(obj);
        }
      });
      console.log(list);
      // 城市
      let keys = "";
      if (this.value2) {
        this.value2.forEach(element => {
          keys = keys + element.id + ",";
        });
        keys = keys.substring(0, keys.lastIndexOf(','));
        console.log(this.value2);
        console.log(keys);
      }
      if (this.isFrequency == 0) {
        (this.frequencPeriod = ""), (this.frequencNum = "");
      }
      if (this.isFrequencyIp == 0) {
        this.frequencyNumIp = "";
        this.requencyPeriodIp = "";
      }
      if(this.dx_pindao == 0){
          this.direction_pd =[]
      }
      if(this.dx_chexi == 0){
          this.direction_cx =[]
      }
      if(this.dx_chejibie == 0){
          this.direction_cjb =[]
      }
      if(this.dx_jiage == 0){
          this.direction_jg =[]
      }
      if(this.dx_diyu == 0){
          this.direction_dy =[]
      }
      if(this.form.Crowd == 1){
          keys = ''
      }
      
      

      let params = {
        putType: 19,
        pid: this.form.select_plan,
        putName: this.form.putName,
        limits: JSON.stringify(list),
        deliveryMode: this.deliveryMode,
        costType: this.costType,
        price: this.prices,
        isFrequency: this.isFrequency,
        frequencPeriod: this.frequencPeriod,
        frequencNum: this.frequencNum,
        isFrequencyIp: this.isFrequencyIp,
        frequencyNumIp: this.frequencyNumIp,
        requencyPeriodIp: this.requencyPeriodIp,
        isfilterDeviceCode: this.isfilterDeviceCode,
        extensionType: this.extensionType,
        landUrl: this.landUrl,
        appId: this.appId,
        pkgName: this.pkgName,
        versioncode: this.versioncode,
        versionname: this.versionname,
        size: this.size,
        sign: this.sign,
        md5: this.md5,
        minsdklevel: this.minsdklevel,
        impMonitorUrl: this.form.make_address1.join(','),
        clkMonitorUrl: this.form.click_address1.join(','),
        isPdb: this.isPdb,
        dealId: this.dealId,
        dxChannel: this.direction_pd.join(","),
        dxGgwSeriesId: this.direction_cx.join(","),
        dxGgwLevelId: this.direction_cjb.join(","),
        dxGgwPriceTagId: this.direction_jg.join(","),
        dxGgwAreaId: this.direction_dy.join(","),
        dxZdlx: this.dxZdlx,
        dxYys: this.dxYys,
        dxWl: this.dxWl,
        dqRule: this.form.dqRule,
        preDq: keys,
        dxDmp: this.DMP_list.join(","),
        dxMedia: this.form.media,
        adPosition: this.form.AD_position,
        id: this.form.id
      };
      putupdate(params).then(res => {
        //  console.log(res)
        //let data = res;
        // console.log(data)
        if (res.data.code != "A000000") {
          this.$message.error(res.data.message);
        }
        this.editVisible = false;
        this.getList();
      });
    },

    handleDelete(index, row) {
      this.idx = index;
      this.row = row;
      this.delVisible = true;
    },
    // 确定删除
    deleteRow() {
      let params = {
        // putType: 19,
        id: this.row.id
      };
      putdelete(params).then(res => {
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

    template_() {},
    handleClick(tab, event) {
      console.log(111)
      //  console.log(this.direction_pd)
      if (tab.name == "second_4") {
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
        let dxZdlx = "";
        if (this.dxZdlx == '22') {
          dxZdlx = "PC";
        } else if (this.dxZdlx == '23') {
          dxZdlx = "wap";
        } else if (this.dxZdlx == '158') {
          dxZdlx = "APP";
        }
        let dxCzxt = "";
        if (this.dxCzxt == "") {
          dxCzxt = "不限";
        } else if (this.dxCzxt == 143) {
          dxCzxt = "IOS";
        } else if (this.dxCzxt == 144) {
          dxCzxt = "Android";
        }
        let dxYys = "";
        if (this.dxYys == "") {
          dxYys = "不限";
        } else if (this.dxYys == 128) {
          dxYys = "移动";
        } else if (this.dxYys == 129) {
          dxYys = "联通";
        } else if (this.dxYys == 130) {
          dxYys = "电信";
        }
        let dxWl = "";
        if (this.dxWl == "") {
          dxWl = "不限";
        } else if (this.dxWl == 132) {
          dxWl = "Wi-Fi";
        } else if (this.dxWl == 134) {
          dxWl = "3G";
        } else if (this.dxWl == 135) {
          dxWl = "4G";
        }

        let bgAddress = "";
        if (this.form.make_address1[0] == "") {
          bgAddress = "";
        } else {
          bgAddress = this.form.make_address1[0];
        }
        let Clkaddress = "";
        if (this.form.click_address1[0] == "") {
          Clkaddress = "";
        } else {
          Clkaddress = this.form.click_address1[0];
        }
        this.pindao = new Map();
        this.channel.forEach(element => {
          this.pindao.set(element.id, element.name);
        });
        let PDname = [];
        for (let i = 0; i < this.direction_pd.length; i++) {
          // console.log(this.pindao.get(this.direction_pd[i]))
          PDname.push(this.pindao.get(this.direction_pd[i]));
        }

        this.chexi = new Map();
        this.cars.forEach(element => {
          this.chexi.set(element.id, element.name);
        });
        let CXname = [];
        for (let i = 0; i < this.direction_cx.length; i++) {
          // console.log(this.pindao.get(this.direction_cx[i]))
          CXname.push(this.chexi.get(this.direction_cx[i]));
        }

        this.chejibie = new Map();
        this.carrank.forEach(element => {
          this.chejibie.set(element.id, element.name);
        });
        let CJBname = [];
        for (let i = 0; i < this.direction_cjb.length; i++) {
          // console.log(this.pindao.get(this.direction_cjb[i]))
          CJBname.push(this.chejibie.get(this.direction_cjb[i]));
        }

        this.jiage = new Map();
        this.price.forEach(element => {
          this.jiage.set(element.id, element.name);
        });
        let JGname = [];
        for (let i = 0; i < this.direction_jg.length; i++) {
          // console.log(this.pindao.get(this.direction_jg[i]))
          JGname.push(this.jiage.get(this.direction_jg[i]));
        }

        this.diyu = new Map();
        this.territory.forEach(element => {
          this.diyu.set(element.id, element.name);
        });
        let DYname = [];
        for (let i = 0; i < this.direction_dy.length; i++) {
          // console.log(this.pindao.get(this.direction_jg[i]))
          DYname.push(this.diyu.get(this.direction_dy[i]));
        }
        let area = "";
        if (this.value2) {
          this.value2.forEach(element => {
            area = area + element.areaName + " ";
          });
        }

        let dmpNmae = new Map();
        for (let i = 0; i < this.labelTag.length; i++) {
          for (let j = 0; j < this.labelTag[i].tagList.length; j++) {
            dmpNmae.set(
              this.labelTag[i].tagList[j].id,
              this.labelTag[i].tagList[j].name
            );
          }
        }

        let dmps = [];
        this.DMP_list.forEach(element => {
          dmps.push(dmpNmae.get(element));
        });
        // console.log(this.append_div)
        this.last = {
          name: names.get(this.form.select_plan),
          unitName: this.form.putName,
          // times: this.append_div[0].startDay.substring(0,4)+'-'+this.append_div[0].endDay,
          times: this.append_div[0].startDay.substring(0,4)+'-'+this.append_div[0].startDay.substring(4,6)+'-'+this.append_div[0].startDay.substring(6,8)+'至'+this.append_div[0].endDay.substring(0,4)+'-'+this.append_div[0].endDay.substring(4,6)+'-'+this.append_div[0].endDay.substring(6,8),
          
          v_price: this.append_div[0].limits,
          deliveryMode: this.deliveryMode == 104 ? "标准投放" : "匀速投放",
          costType: this.costType == 111 ? "CPM" : "CPC",
          extensionType: extensionType,
          bgAddress: bgAddress,
          Clkaddress: Clkaddress,
          dealId: this.dealId,
          appositionName: uuid.get(this.form.AD_position),
          direction_pd: PDname.join(","),
          direction_cx: CXname.join(","),
          direction_cjb: CJBname.join(","),
          direction_jg: JGname.join(","),
          direction_dy: DYname.join(","),
          dxZdlx: dxZdlx,
          dxCzxt: dxCzxt,
          dxYys: dxYys,
          dxWl: dxWl,
          area: area,
          dmp: dmps.join(' ')
        };
        this.$forceUpdate()
        //  this.bg_address="";this.sb_address="";this.times="";
        //  if(this.form.make_address1) {
        //     let urls = this.form.make_address1;
        //     // urls.forEach(element => {
        //       // this.bg_address =  this.bg_address+element.impMonitorUrl+","
        //       this.bg_address =  this.form.make_address1.join(",");
        //     // });
        //   }
        // if(this.form.click_address1) {
        //   let urls = this.form.click_address1;
        //   // urls.forEach(element => {
        //     // this.sb_address =  this.sb_address+element.clkMonitorUrl+","
        //     this.sb_address =  this.form.click_address1.join(",");
        //   // });
        // }
        // if(this.append_div) {
        //   let times = this.append_div;
        //   times.forEach(element => {
        //      if(element.startDay && element.endDay) {
        //         this.times = this.times+(element.startDay+"-"+element.endDay)+",";
        //      }
        //   });
        // }
      }
      console.log(tab, event);
    },
    Editinfo(index, row) {
      this.$router.push({
        path: "/datareport/ideaplan",
        query: { id: row.id }
      });
    },
    last_() {
      let value = this.activeName.split("_");
      this.activeName = value[0] + "_" + parseInt(parseInt(value[1]) - 1);
    },
    next_() {
      let value = this.activeName.split("_");
      this.activeName = value[0] + "_" + parseInt(parseInt(value[1]) + 1);
      if(this.activeName == "second_4"){
            this.handleClick({name:this.activeName})
      }
     
      console.log(this.activeName);
    },
    make_address() {
      this.form.make_address1.push("");
      this.form = Object.assign({}, this.form);
    },
    click_address() {
      this.form.click_address1.push("");
    },
    // click_shebei() {
    //   console.log(this.form);
    //   this.form.click_shebei1.push({ click_shebei: "" });
    // },
    delete_task(j) {
      this.form.make_address1.splice(j, 1);
    },
    delete_click(z) {
      this.form.click_address1.splice(z, 1);
    },
    add_div() {
      this.append_div.push({ time_frame: 1,set_time:[{}]});
    },
    delete_div(g) {
      this.append_div.splice(g, 1);
    },
    new_time(index) {
      let data = Object.assign({}, JSON.parse(JSON.stringify(this.append_div[index])));
      data.set_time.push({begin: "",end: ""});
      this.append_div[index] = data;
      this.append_div = Object.assign([], JSON.parse(JSON.stringify(this.append_div)));
    },
    delete_time(i, j) {
      let data = Object.assign([], JSON.parse(JSON.stringify(this.append_div[i].set_time)));
      data.splice(j, 1);
      this.append_div[i].set_time = data;
      this.append_div = Object.assign([], JSON.parse(JSON.stringify(this.append_div)));
    }
  },

  components: { treeTransfer } // 注册
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
.add_task {
  border: 2px dashed green;
  padding: 10px;
  margin: 10px;
}
.btn_right {
  margin-left: 84%;
}
.r_div {
  margin-left: 80px;
}
.mrl11 {
  width: 170px;
}
.DIVS {
  margin-left: 0px !important;
}
</style>
<style>

.radio {
  width: 300px !important;
  /* padding-left: 10px !important; */
  padding-top: 20px !important;
  color: #606266;
  font-weight: 500;
  line-height: 1;
  cursor: pointer;
  white-space: nowrap;
  outline: 0;
}
/* .el-radio + .el-radio {
  margin-left: 0px !important;
} */
.filter-tree {
  display: none;
}
.el-radio {
    margin-left: 0px !important;
}
</style>





