<template>
  <div class="table">
    <div class="container">
      <div class="handle-box">
        <el-autocomplete
          class="inline-input"
          v-model="select_word"
          value-key="consumerName"
          :fetch-suggestions="querySearchName"
          placeholder="广告平台名称"
          @select="handleSelectName"
        ></el-autocomplete>
        <el-select v-model="FormNewData.classify" placeholder="请选择类型">
          <el-option
            v-for="item in optionsType2"
            :key="item.id"
            :label="item.dicValue"
            :value="item.id"
          ></el-option>
        </el-select>
        <el-select v-model="value" placeholder="状态">
          <el-option
            v-for="item in options_"
            :key="item.value"
            :label="item.label"
            :value="item.value"
          ></el-option>
        </el-select>
        <el-button type="primary" icon="search" @click="search">搜索</el-button>
        <el-button type="primary" icon="search" @click="create" v-if="this.readonly !=1">新建广告平台</el-button>
      </div>
      <el-table :data="tableData" border style="width: 100%" ref="multipleTable">
        <el-table-column prop="id" label="序号" width="100" align="center"></el-table-column>
        <el-table-column prop="consumerName" label="广告平台名称" width="160" align="center"></el-table-column>
        <el-table-column prop="typeName" label="类型" width="160" align="center"></el-table-column>
        <!-- <el-table-column
          prop="consumerType"
          label="类型"
          width
          align="center"
          :formatter="formattermediaType"
        ></el-table-column> -->
        <el-table-column prop="remark" label="备注" width align="center"></el-table-column>
        <el-table-column prop="dspId" label="用户名" width align="center"></el-table-column>
        <el-table-column prop="token" label="密钥" width align="center"></el-table-column>
        <el-table-column label="状态" width align="center" v-if="this.readonly !=1">
          <template slot-scope="scope">
            <el-switch
              v-model="scope.row.runState"
              :active-value="1"
              :inactive-value="0"
              @change="changeStatus($event,scope.row)"
            ></el-switch>
          </template>
        </el-table-column>
        <el-table-column prop="date" label="广告位管理" width align="center" v-if="this.readonly !=1">
          <template slot-scope="scope">
            <el-button size="small" type="text" @click="AD_Edit(scope.$index, scope.row)">选择</el-button>
          </template>
        </el-table-column>
        <el-table-column label="操作" width="150" align="center">
          <template slot-scope="scope">
            <el-button size="small" type="success" @click="handleEdit(scope.$index, scope.row)">编辑</el-button>
            <el-button size="small" type="success" @click="handlePrice(scope.$index, scope.row)" v-if="scope.row.isDeal == 235">价格</el-button>
            <!-- <el-button size="small" type="danger" @click="handleDelete(scope.$index, scope.row)">删除</el-button> -->
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

    <!-- 编辑弹出框 -->
    <el-dialog title="编辑" :visible.sync="editVisible" width="40%">
      <el-form ref="FormNewData" :model="FormNewData" label-width="120px" :label-position="labelPosition">
        <el-form-item label="* 平台名称:">
          <el-input v-model="FormNewData.consumerName" placeholder="请输入广告平台名称" style="width:80%"></el-input>
        </el-form-item>
        <el-form-item label="* 平台类型:">
          <template>
            <el-radio v-model="FormNewData.consumerType" :label="97" disabled>SDK</el-radio>
            <el-radio v-model="FormNewData.consumerType" :label="98" disabled>DSP</el-radio>
          </template>
        <el-form-item label="* 私有协议:" v-if="FormNewData.consumerType == 98">
            <el-radio v-model="FormNewData.isPrivate" :label="214">是</el-radio>
            <el-radio v-model="FormNewData.isPrivate" :label="215">否</el-radio>
        </el-form-item>
        </el-form-item>
        <el-form-item label="QPS上限:">
          <el-input v-model="FormNewData.qps" placeholder="请输入整数" style="width:80%" type="number"></el-input>
        </el-form-item>
        <el-form-item label="RTB Url:">
          <el-input v-model="FormNewData.rtbUrl" placeholder="请输入RTB Url" style="width:80%"></el-input>
        </el-form-item>
        <el-form-item label="* 是否固定价格:">
            <el-radio v-model="FormNewData.isDeal" :label="235" disabled>是</el-radio>
            <el-radio v-model="FormNewData.isDeal" :label="236" disabled>否</el-radio>
        </el-form-item>
        <el-form-item label="* 是否免审:">
            <el-radio v-model="FormNewData.isCheck" :label="238">免审</el-radio>
            <el-radio v-model="FormNewData.isCheck" :label="239">先投后审</el-radio>
            <el-radio v-model="FormNewData.isCheck" :label="240">审核</el-radio>
        </el-form-item>
        <el-form-item label="* 是否测试:">
            <el-radio v-model="FormNewData.isTest" :label="1">是</el-radio>
            <el-radio v-model="FormNewData.isTest" :label="0">否</el-radio>
        </el-form-item>
        <el-form-item label="广告平台标识:">
          <el-input v-model="FormNewData.consumerMark" placeholder="请输入广告平台标识" style="width:80%"></el-input>
        </el-form-item>
        <el-form-item label="备注:">
          <el-input v-model="FormNewData.remark" style="width:80%"></el-input>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="editVisible = false">取 消</el-button>
        <el-button type="primary" @click="saveEdit" v-if="this.readonly !=1">确 定</el-button>
      </span>
    </el-dialog>

    <!-- 新建弹出框 -->
    <el-dialog title="新建广告平台" :visible.sync="newVisible" width="40%">
      <el-form ref="FormNewData" :model="FormNewData" label-width="120px" :label-position="labelPosition">
        <el-form-item label="* 平台名称:">
          <el-input v-model="FormNewData.consumerName" placeholder="请输入广告平台名称" style="width:80%"></el-input>
        </el-form-item>
        <el-form-item label="* 平台类型:">
          <template>
            <el-radio v-model="FormNewData.consumerType" :label="97">SDK</el-radio>
            <el-radio v-model="FormNewData.consumerType" :label="98">DSP</el-radio>
          </template>
        <el-form-item label="* 私有协议:" v-if="FormNewData.consumerType == 98">
            <el-radio v-model="FormNewData.isPrivate" :label="214">是</el-radio>
            <el-radio v-model="FormNewData.isPrivate" :label="215">否</el-radio>
        </el-form-item>
        </el-form-item>
        <el-form-item label="QPS上限:">
          <el-input v-model="FormNewData.qps" placeholder="请输入整数" style="width:80%" type="number"></el-input>
        </el-form-item>
        <el-form-item label="* RTB Url:">
          <el-input v-model="FormNewData.rtbUrl" placeholder="请输入RTB Url" style="width:80%"></el-input>
        </el-form-item>
        <el-form-item label="* 是否固定价格:">
            <el-radio v-model="FormNewData.isDeal" :label="235">是</el-radio>
            <el-radio v-model="FormNewData.isDeal" :label="236">否</el-radio>
        </el-form-item>
        <el-form-item label="* 是否免审:">
            <el-radio v-model="FormNewData.isCheck" :label="238">免审</el-radio>
            <el-radio v-model="FormNewData.isCheck" :label="239">先投后审</el-radio>
            <el-radio v-model="FormNewData.isCheck" :label="240">审核</el-radio>
        </el-form-item>
        <el-form-item label="广告平台标识:">
          <el-input v-model="FormNewData.consumerMark" placeholder="请输入广告平台标识" style="width:80%"></el-input>
        </el-form-item>
        <el-form-item label="备注:">
          <el-input v-model="FormNewData.remark" style="width:80%"></el-input>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="newVisible = false">取 消</el-button>
        <el-button type="primary" @click="saveNews">确 定</el-button>
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

    <!-- 广告位管理提示框 -->
    <el-dialog title="广告位管理" :visible.sync="AD_Visible" width="80%" center>
      <el-tabs v-model="activeName" @tab-click="handleClick">
          <el-tab-pane label="选择APP" name="second_1">
                <el-checkbox-group v-model="APPid">
                    <el-checkbox v-for="(item,index_) in APPname" :key="index_" :label="item.id">{{item.appName}}</el-checkbox>
                </el-checkbox-group>
          </el-tab-pane>
          <el-tab-pane label="基本信息" name="second_2">
            <el-form ref="ADmanageData" :model="ADmanageData" label-width="100px" :label-position="labelPosition">
              <el-form-item label="广告平台名称:">
                    <span>{{consumer_name}}</span>
              </el-form-item>
              <div  v-for="(appAD,index) in moduleList" :key="index">
                        <el-form-item label="APP名称:">
                            <span>{{appAD.appName}}</span>
                        </el-form-item>
                        <el-form-item label="操作系统:">
                            <span>{{appAD.platformName}}</span>
                        </el-form-item>
                        <el-form-item label="广告位:">
                          <el-radio-group v-model="appAD.ADposition" @change="seachAD">
                              <el-radio :label="-1">全选</el-radio>
                              <el-radio :label="2">自定义</el-radio>
                            </el-radio-group>
                            <br>
                            <el-checkbox-group v-model="appAD.baseid" v-if="appAD.ADposition == 2">
                              <el-checkbox v-for="(item,index_) in appAD.adpList" :key="index_" :label="item.id">{{item.name}}</el-checkbox>
                            </el-checkbox-group>
                        </el-form-item>
                        <br>
                </div>
                <div>
                      <el-form-item label="渠道定向:">
                            <el-checkbox  v-model="checkAll1" @change="handleCheckAllQD">全选</el-checkbox>
                            <div style="margin: 15px 0;"></div>
                            <el-checkbox-group v-model="AppChannel">
                              <el-checkbox v-for="(item,indexs) in optionsAppChannel" :label="item.id" :key="indexs" >{{item.cname}}</el-checkbox>
                            </el-checkbox-group>
                      </el-form-item>
                        <el-form-item label="设备定向:">
                           <el-input v-model="shebeiDX" type="textarea"  :rows="2" style="width:50%"></el-input>
                        </el-form-item>
                </div> 
                
                <div>
                        <el-tag>地域定向</el-tag>
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
                  <div>
                      <el-tag>图书类目定向</el-tag>
                      <div class="containerCenter">
                          <div class="custom-tree-container">
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
                                      >
                                      </el-tree>
                                  </div>
                              </div>
                      </div>
                    </div>
            </el-form>
          </el-tab-pane>
        </el-tabs>
      <span slot="footer" class="dialog-footer">
        <el-button style="margin-top: 12px;" @click="last_" v-if="activeName != 'second_1'">上一步</el-button>
        <el-button style="margin-top: 12px;" @click="next_" v-if="activeName != 'second_2'">下一步</el-button>
        <!-- <el-button @click="newVisible = false">取 消</el-button> -->
        <el-button type="primary" @click="ADmanageDataEdit" v-if="activeName == 'second_2' && this.readonly !=1 ">确 定</el-button>
      </span>
    </el-dialog>


    <!-- 固定价格提示框 -->
    <el-dialog title="广告位管理" :visible.sync="price_Visible" width="65%" center>
           <el-form ref="ADprice" :model="ADprice" label-width="120px" :label-position="labelPosition">
              <el-form-item label="广告平台名称:">
                    <span>{{this.name}}</span>
              </el-form-item>
              <br>
              <div  v-for="(appAD,index) in PriceList" :key="index">
                        <el-form-item label="APP名称:">
                            <span>{{appAD.app_name}}</span>
                        </el-form-item>
                        <!-- <div v-for="(item,index) in appAD[1]" :key="index"> -->
                          <el-form-item label="操作系统:">
                              <span>{{appAD.platform}}</span>
                          </el-form-item>
                          <el-form-item label="广告位:">
                            <span v-for="(item,index) in appAD.adp_list" :key="index" style="display:flex">
                              <span style="flex:2">{{item.p_name}}</span>
                              <el-input-number v-model="item.price_double" style="flex:1" :max="500"></el-input-number>元
                            </span>
                          </el-form-item>
                        <!-- </div> -->
                        <br>
                </div> 
            </el-form>
            <span slot="footer" class="dialog-footer">
              <el-button @click="price_Visible = false">取 消</el-button>
              <el-button type="primary" @click="savePrice" v-if="this.readonly !=1 ">保 存</el-button>
            </span>

    </el-dialog>
  </div>
</template>

<script>
import { flowconsumergetpage } from "@/api/Api.js";
import {
  flowconsumerget,
  flowconsumerupdate,
  flowconsumeradd,
  flowconsumerdel,appgetallfc,consumerpositionpriceget,consumerpositionpriceset
} from "@/api/Api.js";
import { diclist } from "@/api/Api.js";
import { flowconsumergetadps,flowconsumergetflowdx,areaGroupgetAreasByNameAndType,bookcategorysselect,flowconsumersetflowdx,AppChannelselect } from "@/api/Api.js";
import treeTransfer from "el-tree-transfer"; // 引入

var data = []
var  mySet =  new Set()
var  map = new Map()
var  QD = new Map()
const optionsChannel = [];
export default {
  name: "basetable",
  data() {
    return {
      checkAll1:false,
      AppChannel:[],
      optionsAppChannel:[],
      shebeiDX:'',
      name:'',
      id:'',
      price_Visible:false,
      activeName: "second_1",
      labelPosition: 'left',
      par:[],
      title: ["城市", "已选城市"],
      mode: "transfer",
      city:'',
      areas: [],
      value2: [],
      props: {
            label: 'name',
            children: 'children',
        },
      checkedKey:[],
      ADposition:'',
      data5: JSON.parse(JSON.stringify(data)),
      url: "./static/vuetable.json",
      tableData: [],
      cur_page: 1,
      total: 1,
      ps: 10,
      consumer_name:'',
      activeName: "1",
      select_cate: "",
      select_word: "",
      del_list: [],
      is_search: false,
      editVisible: false,
      delVisible: false,
      newVisible: false,
      newaddAD: false,
      template_dialog: false,
      AD_Visible: false,
      currentRow: null,
      adList: [],
      baseid: [],
      type_: "",
      // form
      FormNewData: {
        id: "",
        classify: "",
        dspId: "",
        consumerName: "",
        consumerUuid: "",
        consumerType: "",
        rtbUrl: "",
        runState: "",
        adposId: [],
        qps: "",
        remark: "",
        cookiemappingurl: "",
        typeName: "",
        private:'',
        isCheck:240,
        consumerMark:'',
        isTest:1
      },
      // 广告位管理form
      ADmanageData: {
        appName:'',
        platformName: ""
      },
      ADprice: {
        platformName: "",
      },
      moduleList:[],
      PriceList:[],
      form: {},
      idx: -1,
      flow_type: 1,
      type: 1,
      Access: "API",
      display_form: 1,

      optionsType: [
        {
          value: "",
          label: ""
        }
      ],
      optionsType2: [
        {
          value: "",
          label: ""
        }
      ],
      options_: [
        {
          value: '',
          label: "全部状态"
        },
        {
          value: 1,
          label: "启用"
        },
        {
          value: 0,
          label: "停用"
        }
      ],
      value: "",
      types: [],
      terminals: [],
      terminalID:0,
      readonly:'',
      APPid:[],
      APPname:[],
    };
  },
  created() {
    this.readonly = this.$store.state.map.get(this.$store.state.currentPath);
    this.getTerminals();
    this.getList();
    this.Datatypes();
    this.getData();
    this.Datatypes2();
  },
  components: { treeTransfer }, // 注册

  methods: {
    handleCheckAllQD(val) {
       console.log(val)
        this.AppChannel = val ? optionsChannel: [];
      },

    seach(i){
      console.log(i)
    },
    last_() {
      let value = this.activeName.split("_");
      this.activeName = value[0] + "_" + parseInt(parseInt(value[1]) - 1);
    },
    next_() {
      let value = this.activeName.split("_");
      this.activeName = value[0] + "_" + parseInt(parseInt(value[1]) + 1);
      if(this.activeName == "second_2"){
        if(this.APPid.length >=1 ){
             let params ={
                    id:this.ids,
                    aids:this.APPid
                }
              flowconsumergetflowdx(params).then(res => {
                console.log(res)
                let data = res.data
                if(data.code != 'A000000') {
                    this.$message.error(data.message);
                    return;
                }
          

                this.moduleList = data.data.dx_app
                this.moduleList.forEach(element => {
                  console.log(element)
                    //  element.baseid?element['ADposition'] =-1:2
                    element['ADposition'] = element.baseid.length == 0 ?-1:2 
                });
                this.consumer_name = data.data.consumer_name
                if(data.data.dx_area != null){
                      this.value2 = data.data.dx_area
                } 
                this.checkedKey = data.data.dx_book 
                if(data.data.dx_did != null){
                    this.shebeiDX = data.data.dx_did
                }else{
                   this.shebeiDX = ''
                }
                this.optionsAppChannel = data.data.dx_channel
                 data.data.dx_channel.forEach(element => {
                        optionsChannel.push(element.id)
                      });
                this.AppChannel =  data.data.dx_channel[0].baseId
                this.handleClick({name:this.activeName})
              });
        }else{
            this.$message('APP不能为空')
            this.activeName ='second_1'
            this.handleClick({name:this.activeName})
        }
   
     
      }
    },
    handleClick(tab, event) {
      console.log(tab.name)
      if(tab.name == "second_2"){   
          if(this.APPid.length >=1 ){
             let params ={
                    id:this.ids,
                    aids:this.APPid
                }
              flowconsumergetflowdx(params).then(res => {
                console.log(res)
                let data = res.data
                if(data.code != 'A000000') {
                    this.$message.error(data.message);
                    return;
                }
                this.optionsAppChannel = data.data.dx_channel
                  data.data.dx_channel.forEach(element => {
                        optionsChannel.push(element.id)
                      });
                this.moduleList = data.data.dx_app
                this.moduleList.forEach(element => {
                  console.log(element)
                    //  element.baseid?element['ADposition'] =-1:2
                    element['ADposition'] = element.baseid.length == 0 ?-1:2 
                });
                this.consumer_name = data.data.consumer_name
                if(data.data.dx_area != null){
                      this.value2 = data.data.dx_area
                } 
                this.checkedKey = data.data.dx_book
                this.AppChannel =  data.data.dx_channel[0].baseId
                if(data.data.dx_did != null){
                    this.shebeiDX = data.data.dx_did
                }else{
                   this.shebeiDX = ''
                }
              });
        }else{
            this.$message('APP不能为空')
            this.activeName ='second_1'
        }
      }
    },
    // 价格
    handlePrice(index,row){
      console.log(row)
      this.name = row.consumerName
      this.id = row.id
          let params = {
                    id:row.id,
                }
      consumerpositionpriceget(params).then(res => {
        console.log(res)
        let data = res.data
        if(data.code != 'A000000') {
            this.$message.error(data.message);
            return;
        }
          this.PriceList = data.data
          console.log(this.PriceList)
      });
        this.price_Visible = true
    },
    savePrice(){
      let params = {
          apps:this.PriceList,
          cid:this.id
      }
      consumerpositionpriceset(params).then(res => {
        console.log(res)
        let data = res.data
        if(data.code != 'A000000') {
            this.$message.error(data.message);
            return;
        }
          this.price_Visible = false
      });
    },
     // 获取tree
    getData() {
            bookcategorysselect().then(res=>{
                console.log(res)
                data = res.data.data
                this.data5 = data
            //    console.log(this.data5)
            })
        },
    // 图书类目定向
      handleCheckChange(data,checked,indeterminate) {
          console.log(checked)
          console.log(data)
          for(var i in data){
              if(checked != false){
                    mySet.add(data.id)
              }else if(checked != true){
                    mySet.delete(data.id)
              }

          }
              this.par = Array.from(mySet)
              console.log(this.par)
              // this.form.name = data.name
              // this.form.industryID = data.id
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
        findType: 1,
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
    getTerminals() {
      diclist({ gid: 169 }).then(res => {
      let data = res.data;
        if (data.code != "A000000") {
          this.$message.error(data.message);
        }
        this.terminals = data.data;
      });
    },
    // 开关
    changeStatus(val, row) {
      let item = row;
      let params = {
        id: item.id,
        runState: item.runState
        // consumerName:item.consumerName,
        // consumerType:item.consumerType,
        // remark:item.remark,
      };
      flowconsumerupdate(params).then(res => {
        //let data = res.data;
        // if(data.code != 'A000000') {
        //     this.$message.error(data.message);
        // }
        console.log(res);
        // this.getList();
      });
    },
    // 分类
    Datatypes() {
      let par = {
        gid: 96
      };
      diclist(par).then(res => {
      let data = res.data;
        this.optionsType = data.data;
      });
    },
     Datatypes2() {
      let par = {
        gid: 96
      };
      diclist(par).then(res => {
      let data = res.data;
        this.optionsType2 = data.data;
             let  obj = {
                  id: '',
                  dicValue: '全部类型'
              }
              this.optionsType2.push(obj)
      });
    },
    // 格式化数据
    formattermediaType(row, column) {
      if (row.consumerType == 97) {
        return "ADX";
      } else if (row.consumerType == 98) {
        return "DSP";
      } else if (row.consumerType == 99) {
        return "第三方聚合";
      }
    },
    // 分页导航
    handleCurrentChange(val) {
      this.cur_page = val;
      let params = {
        consumerName: this.select_word,
        consumerType: this.FormNewData.classify,
        runState: this.value,
        currentPage: this.cur_page,
        pageSize: this.ps
      };
      flowconsumergetpage(params).then(res => {
      let data = res.data;
        this.tableData = data.data.data;
        this.total = data.data.totalItemNum;
        // console.log(this.total)
      });
    },
    getList() {
      let params = { currentPage: this.cur_page, pageSize: this.ps };
      flowconsumergetpage(params).then(res => {
      let data = res.data;
        this.tableData = data.data.data;
        this.total = data.data.totalItemNum;
        // console.log(res)
      });
    },
    // 搜索
    search() {
      this.$refs.pagination.lastEmittedPage = 1
      this.cur_page = 1;
      let params = {
        consumerName: this.select_word,
        consumerType: this.FormNewData.classify,
        runState: this.value,
        currentPage: this.cur_page,
        pageSize: this.ps
      };
      flowconsumergetpage(params).then(res => {
      let data = res.data;
        this.tableData = data.data.data;
        this.total = data.data.totalItemNum;
        // console.log(this.total)
      });
    },
    // 编辑
    handleEdit(index, row) {
      this.idx = index;
      let item = row;
      this.FormNewData = {
        id: item.id,
        consumerName: item.consumerName,
        consumerType: item.consumerType,
        qps: item.qps,
        rtbUrl: item.rtbUrl,
        remark: item.remark,
        isPrivate:item.isPrivate,
        isDeal:item.isDeal,
        isCheck:item.isCheck,
        consumerMark:item.consumerMark,
        isTest:item.isTest
      };
      this.editVisible = true;
    },
    // 保存编辑
    saveEdit() {
      if (!this.FormNewData.consumerName) {
        this.$message.error("平台名称不能为空");
        return;
      }
      if (!this.FormNewData.consumerType) {
        this.$message.error("类型不能为空");
        return;
      }
      if (!this.FormNewData.rtbUrl) {
        this.$message.error("RTB Url不能为空");
        return;
      }
      let reg= /^[0-9]+$/;
      if(!reg.test(this.FormNewData.qps) ){
        this.$message('请输入正确的qps数字')
        return;
      }
      let params = {
        id: this.FormNewData.id,
        consumerName: this.FormNewData.consumerName,
        consumerType: this.FormNewData.consumerType,
        qps: this.FormNewData.qps,
        rtbUrl: this.FormNewData.rtbUrl,
        cookiemappingurl: this.FormNewData.cookiemappingurl,
        remark: this.FormNewData.remark,
        isPrivate:this.FormNewData.isPrivate,
        isDeal:this.FormNewData.isDeal,
        isCheck:this.FormNewData.isCheck,
        consumerMark:this.FormNewData.consumerMark,
        isTest:this.FormNewData.isTest
      };
      flowconsumerupdate(params).then(res => {
      let data = res.data;
        if (data.code != "A000000") {
          this.$message.error(data.message);
        }
        this.editVisible = false;
        this.search();
      });
    },

    // 删除
    handleDelete(index, row) {
      this.delVisible = true;
      this.row = row;
    },
    // 确定删除
    deleteRow() {
      let params = { id: this.row.id };
      flowconsumerdel(params).then(res => {
      let data = res.data;
        if (data.code != "A000000") {
          this.$message.error(data.message);
          return;
        } else {
          this.$message.success("删除成功");
          this.delVisible = false;
        }
        this.getList();
      });
    },
    // 广告位管理
    AD_Edit(index, row) {
      this.activeName = 'second_1'
      this.ids = row.id
      let  params = {
          cid:row.id
      }
      appgetallfc(params).then(res => {
           let  data = res.data
           if(data.code != 'A000000') {
             console.log(res)
            this.$message.error(data.message);
            return;
           }
           this.APPname = data.data.apps
           this.APPid = data.data.select
       
      })
      this.AD_Visible = true;
    },
    seachAD(){
      this.$forceUpdate();
    },
    // 保存
    ADmanageDataEdit() {
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
      let arr = []   
     
      this.moduleList.forEach(element => {
            // console.log(element.id)
            console.log(element)
              let obj = {} 
            if(element.ADposition == -1){
                obj[element.id] = [-1]
            }else{
                obj[element.id] = element.baseid
            }
           arr.push(obj)
           console.log(arr)
       });
      let params = {
        dx_adposion:arr,
        aids:keys,
        bids:this.par,
        id:this.ids,
        select_app:this.APPid,
        dids:this.shebeiDX,
        cids:this.AppChannel,
      };
      console.log(params)
      flowconsumersetflowdx(params).then(res => {
        console.log(res)
         if(res.data.code != 'A000000') {
            this.$message.error(res.data.message);
        }
        this.getList()
        this.AD_Visible = false;
        // this.getList();
      });
    },

    getAdPositions(terminal) {
      let params = {
        id: this.FormNewData.id,
        terminal: terminal
      };
      flowconsumergetadps(params).then(res => {
      let data = res.data;
        if (data.code != "A000000") {
          this.$message.error(data.message);
        }
        var map = data.data;
        var list = [];
        this.adCheckList = [];
        for (var key in map) {
          var list_check = [];
          let adPList = map[key];
          for (var ad in adPList) {
            if (adPList[ad].status == 3) {
              list_check.push(adPList[ad].id);
            }
          }
          list.push([key, map[key]]);
          this.adCheckList.push(list_check);
        }
        this.adList = list;
      });
    },


    // 新建
    create() {
      this.newVisible = true;
      this.FormNewData = {
        consumerType:97,
        isDeal:236,
        isPrivate:215,
        isCheck:240,

      };
    },
    //确认
    saveNews() { 
      if (!this.FormNewData.consumerName) {
        this.$message.error("平台名称不能为空");
        return;
      }
      if (!this.FormNewData.consumerType) {
        this.$message.error("类型不能为空");
        return;
      }
      let regs = /(http|https):\/\/([\w.]+\/?)\S*/
       if(!regs.test(this.FormNewData.rtbUrl)){
        this.$message('请输入正确的url')
        return;
      }
    
     let reg= /^[0-9]+$/;
      if(!reg.test(this.FormNewData.qps) ){
        this.$message('请输入正确的qps数字')
        return;
      }
   
      let params = {
        consumerName: this.FormNewData.consumerName,
        consumerType: this.FormNewData.consumerType,
        qps: this.FormNewData.qps,
        rtbUrl: this.FormNewData.rtbUrl,
        remark: this.FormNewData.remark,
        isPrivate:this.FormNewData.isPrivate,
        isDeal:this.FormNewData.isDeal,
        isCheck:this.FormNewData.isCheck,
        consumerMark:this.FormNewData.consumerMark,
      };
      flowconsumeradd(params).then(res => {
      let data = res.data;
        // console.log(res)
        if (data.code != "A000000") {
          this.$message.error(data.message);
          return;
        }
        // 掉查询接口
        this.newVisible = false;
        this.getList();
      });
    },
    //模糊名字
    querySearchName(queryString, cb) {
      let params = {
        consumerName: this.select_word,
        currentPage: this.cur_page,
        pageSize: this.ps
      };
      flowconsumergetpage(params).then(res => {
      let data = res.data;
        console.log(data);
        // 调用 callback 返回建议列表的数据
        cb(data.data.data);
        if (data.code != "A000000") {
          this.$message.error(data.message);
        }
      });
    },
    handleSelectName(item) {
      // console.log(item);
      this.id = item.id;
    },

    saveAD() {
      this.newaddAD = false;
    },

    template_() {}
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
  width: 300px;
  display: inline-block;
}
.del-dialog-cnt {
  font-size: 16px;
  text-align: center;
}
.border {
  width: 100%;
  height: 2px;
  background: silver;
}

.mrl11{
  width: 100px;;
}
.span{
  width: 10%;
}
</style>
<style>
  .transfer{
  height: 300px !important;
}
</style>


