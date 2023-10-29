<template>
    <div class="table">
        <div class="container">
            <div class="handle-box">
                 <el-autocomplete
                    class="inline-input"
                    v-model="select_word"
                    value-key="name"
                    :fetch-suggestions="querySearchName"
                    placeholder="广告位名称"
                    @select="handleSelectName"
                  ></el-autocomplete>
                <el-select v-model="FormNewData.classify" placeholder="操作系统">
                    <el-option
                        v-for="item in optionsType"
                        :key="item.value"
                        :label="item.label"
                        :value="item.value">
                    </el-option>
                 </el-select>
                <el-button type="primary" icon="search" @click="search">搜索</el-button>
                <el-button type="primary" icon="search" @click="create">新增策略</el-button>
            </div>
            <el-table :data="tableData" border style="width: 100%" ref="multipleTable">
                <el-table-column prop="appName" label="APP"  align="center">
                </el-table-column>
                <el-table-column prop="os" label="操作系统"  align="center">
                </el-table-column>
                <el-table-column prop="adPositionName" label="广告位名称"  align="center">
                </el-table-column>
                <el-table-column prop="adTypeName" label="广告形式"  align="center">
                </el-table-column>
                <el-table-column prop="setValue" label="设定值"  align="center">
                </el-table-column>
                <el-table-column label="操作" width="150" align="center">
                    <template slot-scope="scope"> 
                        <el-button size="small" type="success" @click="handleEdit(scope.$index, scope.row)">编辑</el-button>
                    </template>
                </el-table-column>
            </el-table>
             <div class="pagination">
                <el-pagination @current-change="handleCurrentChange" layout="total,prev, pager, next,jumper" :total="total" ref="pagination">
                </el-pagination>
            </div>
        </div>

        <!-- 编辑弹出框 -->
        <el-dialog title="编辑" :visible.sync="editVisible" width="40%">
            <el-form ref="FormNewData" :model="FormNewData" label-width="100px" :label-position="labelPosition">
                <el-form-item label="APP:">
                  <span>{{FormNewData.appName}}</span> 
                </el-form-item>
                <el-form-item label="操作系统:">
                  <span>{{FormNewData.os}}</span> 
                </el-form-item>
                <el-form-item label="广告位名称:">
                  <span>{{FormNewData.adPositionName}}</span> 
                </el-form-item>
               <el-form-item label="显示时间s:">
                     <el-input-number controls-position="right" v-model="FormNewData.expTime" :min="1" :max="3600" class="handle-input"></el-input-number>
                </el-form-item>
                <el-form-item label="插页间隔章数:">
                    <el-input-number controls-position="right" v-model="FormNewData.chapterNum" :min="0" :max="100" class="handle-input"></el-input-number>
                </el-form-item>
                <el-form-item label="插页间隔页数:">
                    <el-input-number controls-position="right" v-model="FormNewData.pageNum" :min="0" :max="100" class="handle-input"></el-input-number>
                </el-form-item>
            </el-form> 
            <span slot="footer" class="dialog-footer">
                <el-button @click="editVisible = false">取 消</el-button>
                <el-button type="primary" @click="saveEdit">确 定</el-button>
            </span>
        </el-dialog>

          <!-- 新建策略 -->
        <el-dialog title="新建设置" :visible.sync="newVisible" width="50%">
            <el-form ref="FormNewData2" :model="FormNewData" label-width="100px" :label-position="labelPosition">
                <el-form-item label="选择APP:">
                      <el-select v-model="FormNewData.APP" @change="getAdposition" placeholder="APP名称" class="handle-input">
                            <el-option
                              v-for="item in optionAPP"
                              :key="item.id"
                              :label="item.appName"
                              :value="item.id">
                            </el-option>
                        </el-select>
                </el-form-item>
                <el-form-item label="操作系统:">
                    <span>{{FormNewData.platformName}}</span>
                </el-form-item>
                <el-form-item label="选择广告位:">
                     <el-radio-group v-model="FormNewData.ADposition" @change="getID">
                        <el-radio
                          :label="item.id"
                          v-for="item in optionAD"
                          :key="item.id"
                        >{{item.name}}</el-radio>
                      </el-radio-group>
                </el-form-item>
                <el-form-item label="显示时间s:">
                     <el-input-number controls-position="right" v-model="FormNewData.expTime" :min="1" :max="3600" class="handle-input"></el-input-number>
                </el-form-item>
                <el-form-item label="插页间隔章数:">
                    <el-input-number controls-position="right" v-model="FormNewData.chapterNum" :min="0" :max="100" class="handle-input"></el-input-number>
                </el-form-item>
                <el-form-item label="插页间隔页数:">
                    <el-input-number controls-position="right" v-model="FormNewData.pageNum" :min="0" :max="100" class="handle-input"></el-input-number>
                </el-form-item>
            </el-form> 
            <span slot="footer" class="dialog-footer">
                <el-button @click="newVisible = false">取 消</el-button>
                <el-button type="primary" @click="saveNews">确 定</el-button>
            </span>
        </el-dialog>

        <!-- 默认设置-->
        <el-dialog title="全局默认设置" :visible.sync="delVisible" width="40%" center>
            <el-form ref="FormNewData" :model="form" label-width="100px" :label-position="labelPosition">
                <el-form-item label="翻页默认值:">
                    <el-input v-model="form.pageValue" placeholder="页" style="width:80%"></el-input>
                </el-form-item>
                <el-form-item label="时长默认值:">
                    <el-input v-model="form.durationValue" placeholder="秒" style="width:80%"></el-input>
                </el-form-item>
                <el-form-item label="广告形式:">
                    <div v-for="(item,index_) in getpage" :key="index_">
                    <el-form-item label="">
                          <span>
                            {{item.adTypeName}}
                          </span>
                          <br>
                          <el-radio v-model="item.changeType" :label="211" >翻页</el-radio>
                          <el-radio v-model="item.changeType" :label="212" >时长（秒）</el-radio>
                    </el-form-item>
                </div>
                </el-form-item>
            </el-form> 
            <span slot="footer" class="dialog-footer">
                <el-button @click="delVisible = false">取 消</el-button>
                <el-button type="primary" @click="savaPage">确 定</el-button>
            </span>
        </el-dialog>

        <!-- 广告位管理提示框 -->
         <el-dialog title="广告位管理"  :visible.sync="AD_Visible" width="40%" center>
            <el-form ref="ADmanageData" :model="ADmanageData" label-width="100px">
                  <el-form-item label="流量源:">
                      爱卡汽车
                  </el-form-item>
                  <div v-for="(ad,index) in adList" :key="index">
                    <el-form-item label="平台:">
                        {{ad[0]}}
                    </el-form-item>
                    <el-form-item label="广告位:">
                          <el-checkbox-group v-model="adCheckList[index]" >
                            <el-checkbox v-for="(item,index) in ad[1]" :key="index" :label="item.id">{{item.name}}</el-checkbox>
                          </el-checkbox-group>
                    </el-form-item>
                    <el-form-item>
                          <div class="border"></div>
                    </el-form-item>
                  </div>
            </el-form> 
            <span slot="footer" class="dialog-footer">
                <el-button @click="AD_Visible = false">取 消</el-button>
                <el-button type="primary" @click="ADmanageDataEdit">保 存</el-button>
            </span>
        </el-dialog>
    </div>
</template>

<script>
import { flowconsumergetpage } from "@/api/Api.js";
import { flowconsumerget,flowconsumerupdate,flowconsumeradd,flowconsumerdel } from "@/api/Api.js";
import { diclist } from "@/api/Api.js";
import { flowconsumergetadps } from "@/api/Api.js";

import { adcarouselpage,appgetall,appgetone,adcarousel ,adcarousel2,adcarouselgetdefult,
    adcarouselsetdefult,adcarouselgetadpositionlist,appgetsdkone
} from "@/api/Api.js";

const uuid = new Map
var map = new Map()
export default {
  name: "basetable",
  data() {
    return {
      radio:1,
      labelPosition: 'left',
      tableData: [],
      cur_page: 1,
      total: 1,
      ps:10,
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
      adCheckList: [],
      type_: "",
      // form
      FormNewData: {
        APP:'',
        appName:'',
        platformName:'',
        ADposition:'',
        changeType:'',
        value:'',
        os:'',
        expTime:'',
        pageNum:'',
        chapterNum:'',
        
        id: "",
        classify:'',
        consumerName: "",
        consumerUuid: "",
        consumerType: "",
        rtbUrl: "",
        runState: '',
        adposId: [],
        qps: '',
        remark: '',
        cookiemappingurl: '',
        typeName: '',
        adType:null,
      },
      // 广告位管理form
      ADmanageData: {
        platformName:'',
        durationValue:'',
      
      },
      form: {
        pageValue:'',
        durationValue:'',

      },
      idx: -1,
      flow_type: 1,
      type: 1,
      Access: "API",
      display_form: 1,

      optionsType:[
         {
          value: '',
          label: '全部操作系统'
        },
         {
          value: 182,
          label: 'ios'
        },
         {
          value: 183,
          label: 'andrid'
        }
      ],
      options_: [
        {
          value: 1,
          label: '启用'
        },
        {
          value: 0,
          label: '停用'
        }
      ],
      optionAPP: [
        {
          value: "",
          label: ""
        },
      ],
      optionAD: [
        {
          value: "",
          label: ""
        },
      ],
      value: "",
      types:[],
      getpage:[],
      type:null
    };
  },
  created() {
     this.getList();
     this.getApp();
  },
 
  methods: {
    getAdposition(){
      this.FormNewData.ADposition = ''
      this.type = ''
      let params = {
        id: this.FormNewData.APP,
      };
      appgetsdkone(params).then(res => {  
      let data = res.data;
        if (data.code != "A000000") {
          this.$message.error(data.message);
        }
        this.optionAD = data.data.adpList;
        data.data.adpList.forEach(element => {
          map.set(element.id,element.type)
        });
        this.FormNewData.platformName = data.data.platformName           
      });
    },
    getID(){
        this.type = map.get(this.FormNewData.ADposition)
        this.$forceUpdate()
    },
    getApp(){
        appgetall().then(res=>{
          
            let data = res.data; 
             if(data.code != 'A000000') {
              this.$message.error(data.message);
            }    
            this.optionAPP = data.data
          });
    },
       // 开关
    changeStatus(val,row){
       let item = row;
             let params = {
                id:item.id,
                runState:item.runState,
            }
     flowconsumerupdate(params).then(res=>{
             this.getList();
      });
    },
          // 格式化数据
    formattermediaType(row, column) {
      if(row.consumerType == 97) {
        return "ADX";
      } else if(row.consumerType == 98){
        return "DSP";
      }
      else if(row.consumerType == 99){
        return "第三方聚合";
      }
    },
    // 分页导航
    handleCurrentChange(val) {
      this.cur_page = val;
      this.getList();
    },
    getList() {
      let params = {currentPage:this.cur_page,pageSize:this.ps}
      adcarouselpage(params).then(res=>{     
     let data = res.data;     
       this.tableData = data.data.data
       this.total = data.data.totalItemNum;
      // 
      });
    },
    // 搜索
     search() {
        this.$refs.pagination.lastEmittedPage = 1
        this.cur_page = 1;
       let params = {
         adPositionName:this.select_word,
         platform:this.FormNewData.classify,
         currentPage:this.cur_page,
         pageSize:this.ps
         }
      adcarouselpage(params).then(res=>{
       let data = res.data;     
       this.tableData = data.data.data
       this.total = data.data.totalItemNum;
      // 
      });
    },
    // 编辑
    handleEdit(index, row) {
      let item = row;
      let params = {
         id:item.id,
         }
      adcarousel2(params).then(res=>{        
           let data = res.data;
        if(data.code != 'A000000') {
            this.$message.error(data.message);
            return;
        }
        // 掉查询接口
        this.editVisible = true;
        this.FormNewData = data.data

        

      });
    },
    // 保存编辑
    saveEdit() {
      if(!this.FormNewData.expTime) {
        this.$message.error("显示时间不能为空");
        return;
      }
      if((this.FormNewData.chapterNum == undefined||this.FormNewData.chapterNum == 0)  &&  (this.FormNewData.pageNum  == undefined||this.FormNewData.pageNum == 0)) {
        this.$message.error("插页间隔章数，插页间隔页数不能同时为空/0");
        return;
      }
        let params = {
            id:this.FormNewData.id,
            adPosition:this.FormNewData.adPosition,
            expTime:this.FormNewData.expTime,
            chapterNum:this.FormNewData.chapterNum,
            pageNum:this.FormNewData.pageNum,
            }
        adcarousel(params).then(res=>{
         let data = res.data;
          if(data.code != 'A000000') {
              this.$message.error(data.message);
          }
          this.editVisible = false;
          this.seach();
          this.getList()
      });
    },
   
  //  默认策略
    defaulStrategy(index, row) {
       adcarouselgetdefult().then(res=>{
        
        let data = res.data;
          if(data.code != 'A000000') {
              this.$message.error(data.message);
              return
          }   
          this.getpage = data.data
          this.form.pageValue = data.data[0].pageValue
          this.form.durationValue = data.data[0].durationValue
          this.delVisible = true;
        });
    },
  
    savaPage() {
      for(var i = 0;i<this.getpage.length;i++){
          this.getpage[i].durationValue = this.form.durationValue
          this.getpage[i].pageValue = this.form.pageValue
      }
         
      adcarouselsetdefult(this.getpage).then(res=>{
        
        let data = res.data;
          if(data.code != 'A000000') {
              this.$message.error(data.message);
              return
          }   
              this.delVisible = false;
        });
    },
      // 广告位管理
    AD_Edit(index, row) {
      this.row = row
      this.FormNewData.id = row.id
      this.ADmanageData.checkLists = [];
        let params ={
          id:row.id
        }
      flowconsumergetadps(params).then(res=>{
     let data = res.data;
          if(data.code != 'A000000') {
              this.$message.error(data.message);
          }
          var map = data.data
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
            this.AD_Visible = true;
          // this.types = data.data   
      }); 
    },
    ADmanageDataEdit(){
           let params = {
            id:this.FormNewData.id,
            adposId:this.adCheckList.join(','),
            }
           
        flowconsumerupdate(params).then(res=>{
          //  if(res.code != 'A000000') {
          //     this.$message.error(res.message);
          // }
          this.AD_Visible = false;
          // this.getList();
      });
      
    },
    seach(){
      this.$forceUpdate()
    },
    // 新建
    create() {
      this.newVisible = true;
      this.optionAD =[];
      this.FormNewData = {}
    },
    //确认
    saveNews() {
      if(!this.FormNewData.ADposition) {
        this.$message.error("广告位不能为空");
        return;
      }
      if(!this.FormNewData.expTime) {
        this.$message.error("显示时间不能为空");
        return;
      }
     if(this.FormNewData.chapterNum == undefined||this.FormNewData.chapterNum == 0 && this.FormNewData.pageNum  == undefined||this.FormNewData.pageNum == 0) {
        console.log(this.FormNewData.chapterNum)
        this.$message.error("插页间隔章数，插页间隔页数不能同时为空/0");
        return;
      }
        let params = {
            adPosition:this.FormNewData.ADposition,
            expTime:this.FormNewData.expTime,
            chapterNum:this.FormNewData.chapterNum,
            pageNum:this.FormNewData.pageNum,
            }
    adcarousel(params).then(res=>{
      let data = res.data;
        if(data.code != 'A000000') {
            this.$message.error(data.message);
            return;
        }
        // 掉查询接口
        this.newVisible = false;
        this.getList();
          });
      },
      //模糊名字
    querySearchName(queryString, cb){
        let params = {
          name:this.select_word,
          currentPage:this.cur_page,
          pageSize:this.ps
          }
         adcarouselgetadpositionlist(params).then(res=>{
           
       let data = res.data;
          
          // 调用 callback 返回建议列表的数据
        cb(data.data);
       if(data.code != 'A000000') {
          this.$message.error(data.message);
            } 
        });
    },
    handleSelectName(item){
      // 
      this.id = item.id
    },
    
    saveAD() {
      this.newaddAD = false;
    },
  
    template_() {},
  
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
.el-radio{
   width: 200px;;
}
</style>
<style>
  .el-radio {
    margin-left: 30px !important;
}
</style>

