<template>
    <div class="table">
        <div class="container">
            <div class="handle-box">
              <el-input v-model="select_name" placeholder="广告位名称" class="handle-input mr10"></el-input>
              <el-select v-model="app_Name" placeholder="app名称" class="handle-input">
                      <el-option
                        v-for="item in FlowOptions2"
                        :key="item.id"
                        :label="item.appName"
                        :value="item.id">
                      </el-option>
               </el-select>
               <el-input v-model="select_consumerPositionId" placeholder="广告平台广告位ID" class="handle-input mr10"></el-input>
               <el-input v-model="select_consumerPositionName" placeholder="广告平台广告位名称" class="handle-input mr10"></el-input>
               <el-select v-model="mapPlatform" placeholder="映射平台" class="handle-input">
                  <el-option
                    v-for="item in ADStatus2"
                    :key="item.id"
                    :label="item.consumerName"
                    :value="item.id">
                  </el-option>
                </el-select>
                <el-select v-model="status" placeholder="状态" class="handle-input">
                  <el-option
                    v-for="item in optionStatus"
                    :key="item.value"
                    :label="item.label"
                    :value="item.value">
                  </el-option>
                </el-select>
                <el-button type="primary" icon="search" @click="search">搜索</el-button>
                 <!-- <el-button type="primary" icon="search" @click="create"  v-if="this.readonly !=1">新建广告模板</el-button> -->
            </div>
            <el-table :data="tableData" border style="width: 100%" ref="multipleTable">
                <el-table-column prop="id" label="ID" align="center">
                </el-table-column>
                <el-table-column prop="name" label="广告位名称"  width="" align="center">
                </el-table-column>
                <el-table-column prop="appName" label="APP名称"  width="" align="center">
                </el-table-column>
                <el-table-column prop="terminal" label="终端类型"  width="" align="center" :formatter=formatterType>
                </el-table-column>
                <el-table-column prop="os" label="操作系统"  width="" align="center" :formatter=formatterOS>
                </el-table-column>
                <el-table-column prop="uuid" label="广告位uuid"  width="" align="center">
                </el-table-column>
                <el-table-column prop="mappingConsumer" label="映射平台"  width="" align="center">
                </el-table-column>
                <el-table-column  label="映射开关"  width="" align="center"  v-if="this.readonly !=1">
                  <template slot-scope="scope">
                     <el-switch v-model="scope.row.mappingSwitch"  :active-value=1 :inactive-value=0  @change="changeStatus($event,scope.row)"></el-switch>
                  </template>
                </el-table-column>
                <el-table-column label="操作" width="" align="center">
                    <template slot-scope="scope">
                        <el-button size="small" type="success" @click="handleEdit(scope.$index, scope.row)">编辑</el-button>
                        <!-- <el-button size="small" type="danger" @click="handleDelete(scope.$index, scope.row)">删除</el-button> -->
                        <!-- <el-button size="small" type="success" @click="handAD(scope.$index, scope.row)"  v-if="this.readonly !=1">复制</el-button> -->
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
        <el-dialog title="编辑" :visible.sync="editVisible" width="92%">
             <el-form ref="form" :model="form" label-width="100px" :label-position="labelPosition">
                <el-form-item label="广告位ID:">
                    <span>{{form.id}}</span>
                </el-form-item>
                <el-form-item label="广告位名称:">
                     <span>{{form.name}}</span>
                </el-form-item>
                <el-form-item label="app名称:">
                     <span>{{form.appName}}</span>
                </el-form-item>
                <el-form-item label="操作系统:">
                     <span>{{form.osName}}</span>
                </el-form-item>
                <el-form-item label="映射平台:">
                  <div v-for="(video,index) in mappingList" :key="index">
                        <!-- <el-select v-model="video.appId" placeholder="APP名称" class="handle-input">
                            <el-option
                              v-for="item in optionType"
                              :key="item.id"
                              :label="item.appName"
                              :value="item.id">
                            </el-option>
                        </el-select> -->
                        <el-select v-model="video.flowConsumerId" placeholder="映射平台名称" class="handle-input"  style="width:120px">
                          <el-option
                            v-for="item in ADStatus"
                            :key="item.id"
                            :label="item.consumerName"
                            :value="item.id">
                          </el-option>
                        </el-select>
                        <el-input style="width:110px" v-model="video.consumerPositionId" size="mini"  placeholder="平台广告位ID" ></el-input>
                        <el-input style="width:120px" v-model="video.consumerPositionName" size="mini"  placeholder="平台广告位名称" ></el-input>
                        <el-input style="width:120px" v-model="video.width" size="mini"  placeholder="广告位宽" ></el-input>
                        <el-input style="width:120px" v-model="video.height" size="mini"  placeholder="广告位高" ></el-input>
                        <el-input style="width:110px" v-model="video.forecastRtbPrice" size="mini"  placeholder="预设RTB出价" ></el-input>
                        <el-select v-model="video.templateId" clearable placeholder="广告位模板" class="handle-input">
                          <el-option
                            v-for="item in template"
                            :key="item.id"
                            :label="item.name"
                            :value="item.id">
                          </el-option>
                        </el-select>
                        <el-select v-model="video.mapperType" clearable placeholder="映射广告位类型" class="handle-input">
                          <el-option
                            v-for="item in AdType"
                            :key="item.value"
                            :label="item.label"
                            :value="item.value">
                          </el-option>
                        </el-select>
                        <el-input style="width:110px" v-model="video.dspFloor" size="mini"  placeholder="平台底价" ></el-input>
                     <el-button type="primary" @click="addImg">添加</el-button>
                     <el-button type="primary"  @click="DeleteImg(index)">删除</el-button>
                    </div>
                </el-form-item>
            </el-form>
            <span slot="footer" class="dialog-footer">
                <el-button @click="editVisible = false">取 消</el-button>
                <el-button type="primary" @click="saveEdit"  v-if="this.readonly !=1">确 定</el-button>
            </span>
        </el-dialog>

  

    </div>
</template>

<script>
import { adPositiongetList,flowconsumergetpage } from "@/api/Api.js";
import { templateAdd,templateGet,templateupdate ,templateupdateStatus,adPositionget,
    appgetall,adPositionmappingswitch,adPositionmapping,listByPosition,flowconsumerlistbymapped
  } from "@/api/Api.js";

const uuid = new Map()
const couID = new Map()
export default {
  name: "basetable",
  data() {
    return {
      templateID:'',
      template:[],
      labelPosition: 'left',
      url: "./static/vuetable.json",
      tableData: [],
      cur_page: 1,
      total: 1,
      ps:10,
      select_name: "",
      select_consumerPositionId:'',
      select_consumerPositionName:'',
      mapPlatform:'',
      status:'',
      del_list: [],
      editVisible: false,
      delVisible: false,
      newVisible: false,
      newaddAD: false,
      template_dialog: false,
      editVisible1:false,
      currentRow: null,
      radio:'',
      matierialType:'',
      app_Name:'',
      form: {
        id:'',
        flowName:'',
        terminalName:'',
        osName:'',
        name:'',
        status:''
      },
       mappingList: [
            {
                appId:'',
                flowPositionId:'',
                consumerPositionId:'',
                consumerPositionName:'',
                width:'',
                height:'',
                forecastRtbPrice:'',
                // forecastCtr:'',
            }
          ],
      idx: -1,
      type: 1,
      readonly:'',
      ADStatus: [
        {
          value: "",
          label: ""
        },
      ],
      ADStatus2: [
        {
          value: "",
          label: ""
        },
      ],
      optionType: [
        {
          value: "",
          label: ""
        },
      ],
      FlowOptions:[
          {
          value: '',
          label: ''
        },
      ],
      FlowOptions2:[
          {
          value: '',
          label: ''
        },
      ],
       optionStatus: [
        {
          value: "",
          label: "全部状态"
        },
        {
          value: "1",
          label: "有效"
        },
        {
          value: "0",
          label: "无效"
        },
      ],
      AdType:[
          {
          value: 4,
          label: '全屏视频'
          },
          {
          value: 5,
          label: '横幅'
          },
          {
          value: 6,
          label: '开屏'
          },
          {
          value: 7,
          label: '信息流'
          },
          {
          value: 217,
          label: '激励视频'
          },
          {
          value: 218,
          label: '插屏'
          },
      ]
  
     
    };
  },
  created() {
        this.readonly = this.$store.state.map.get(this.$store.state.currentPath);
        this.getList();
        // this.getADStatus();
        this.getAppName();
        this.getADStatus2();
        this.getAppName2()
        // this.getTelemate()
  },
 
  methods: {
      getTelemate() {
        let params = {
          id:this.form.id,
          status:1,
          
        }
      listByPosition(params).then(res=>{
        console.log(res)
        let data = res.data;     
          this.template = data.data
          });
    },
    // 格式化数据
    formatterType(row, column) {
      if(row.terminal == 158) {
        return "APP";
      } else if(row.terminal == 22){
        return "PS";
      }else if(row.terminal == 23){
        return "WAP";
      }
    },
    formatterOS(row, column) {
      if(row.os == 182) {
        return "ios";
      } else if(row.os == 183){
        return "android";
      }
    },
        // 开关
    changeStatus(val,row){
             let params = {
                id:row.id,
                mappingSwitch:row.status,
            }
     adPositionmappingswitch(params).then(res=>{
     let data = res.data;
          if(data.code != 'A000000') {
              this.$message.error(data.message);
          }
            //  this.getList();
      });
    },
    // 分页导航
    handleCurrentChange(val) {
      this.cur_page = val;
       let params = {
        name:this.select_name,
        appId:this.app_Name,
        consumerPositionId:this.select_consumerPositionId,
        consumerPositionName:this.select_consumerPositionName,
        flowConsumerId:this.mapPlatform,
        mappingSwitch:this.status,
        cp:this.cur_page,
        ps:this.ps
       }
     adPositiongetList(params).then(res=>{
        let data = res.data;     
       this.tableData = data.data.data
       this.total = data.data.totalItemNum;
      });
    },
    // 广告位映射下拉框
    getADStatus(){
      let params = {
        // pid:this.item.id
        runState:1
      }
        flowconsumergetpage().then(res=>{
          console.log(res)
            let data = res.data; 
             if(data.code != 'A000000') {
              this.$message.error(data.message);
            }    
            this.ADStatus = data.data.data
            this.ADStatus.forEach(element => {
                  couID.set(element.id,element.consumerType)
              });
          });
    },
    getADStatus2(){
        flowconsumergetpage().then(res=>{
            let data = res.data; 
             if(data.code != 'A000000') {
              this.$message.error(data.message);
            }    
            this.ADStatus2 = data.data.data
               let  obj = {
                  id: '',
                  consumerName: '全部平台'
              }
            this.ADStatus2.push(obj)   
          });
    },
    // app名称下拉框
    getAppName(){
        appgetall().then(res=>{
          console.log(res)
            let data = res.data; 
             if(data.code != 'A000000') {
              this.$message.error(data.message);
              return;
            }    
            this.FlowOptions = data.data
          });
    },
    getAppName2(){
        appgetall().then(res=>{
          console.log(res)
            let data = res.data; 
             if(data.code != 'A000000') {
              this.$message.error(data.message);
              return;
            }   
            this.FlowOptions2 = data.data 
              let  obj = {
                  id: '',
                  appName: '全部APP名称'
              }
            this.FlowOptions2.push(obj)   
          });
    },
         //模糊名字
    querySearchName(queryString, cb){
        let params = {
          name:this.select_word,
          cp:1,
          ps:this.ps
          }
         flowconsumergetpage(params).then(res=>{
     let data = res.data;
          console.log(data.data.data)
          // 调用 callback 返回建议列表的数据
        cb(data.data.data);
        // data.data.data.forEach(element => {
        //   uuid.set(element.name,element.id)
        // });
       if(data.code != 'A000000') {
          this.$message.error(data.message);
            } 
        });
    },
     handleSelectName(item){
      this.id = item.id
    },
    // 获取 table
    getList() {
      let params = {
        status:1,
        cp:this.cur_page,
        ps:this.ps
      }
      adPositiongetList(params).then(res=>{
     let data = res.data;     
       this.tableData = data.data.data
       this.total = data.data.totalItemNum;
      });
    },
    // 搜索
    search() {
         this.$refs.pagination.lastEmittedPage = 1
         this.cur_page = 1;
     let params = {
        name:this.select_name,
        appId:this.app_Name,
        consumerPositionId:this.select_consumerPositionId,
        consumerPositionName:this.select_consumerPositionName,
        flowConsumerId:this.mapPlatform,
        mappingSwitch:this.status,
        cp:this.cur_page,
        ps:this.ps
       }
     adPositiongetList(params).then(res=>{
     let data = res.data;     
       this.tableData = data.data.data
       this.total = data.data.totalItemNum;
    });
    },

    // 编辑
    handleEdit(index, row) {
      this.idx = index;
      this.item = row;
      console.log(this.item)
      this.getADStatus()
        let params = {
            id:row.id,
        }
        adPositionget(params).then(res=>{
          console.log(res)
        let data = res.data;
          if(data.code != 'A000000') {
              this.$message.error(data.message);
              return;
          }
            this.form = data.data
            // if(data.data.mappingList != []){
              this.mappingList = data.data.mappingList
            // }
              this.getTelemate()
              this.editVisible = true;
        });

    },
     // 保存编辑
    saveEdit() {
      let params = {
        id:this.form.id,
        mappingList:this.mappingList,
        }
       adPositionmapping(params).then(res=>{
        let data = res.data;
          if(data.code != 'A000000') {
              this.$message.error(data.message);
              return;
          }
           this.search();
           this.editVisible = false;
        });
    },
    handleDelete(index, row) {
      this.idx = index;
      this.delVisible = true;
    },

   
    
   
    saveAD() {
      this.newaddAD = false;
    },
    // 确定删除
    deleteRow() {
      this.tableData.splice(this.idx, 1);
      this.$message.success("删除成功");
      this.delVisible = false;
    },
    // 选择模板
    select_template() {
      this.template_dialog = true;
    },
    
    template_(){

    },
    addImg(){
      // console.log(this.form[0]);return;
      this.mappingList.push({appId:'',consumerPositionId:'',flowPositionId:'',consumerPositionName:'',width:'',height:'',forecastRtbPrice:'',templateId:'',mapperType:''});
      console.log(this.mappingList)
    },
    DeleteImg(i){
        this.mappingList.splice(i,1)
    },
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
  width: 150px;
  display: inline-block;
}
.del-dialog-cnt {
  font-size: 16px;
  text-align: center;
}
.font{
    padding-left: 5px
}
</style>
