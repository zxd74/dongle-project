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
                                  v-for="item in optionsType"
                                  :key="item.id"
                                  :label="item.dicValue"
                                  :value="item.id">
                              </el-option>
                 </el-select>
                 <el-select v-model="value" placeholder="状态">
                  <el-option
                    v-for="item in options_"
                    :key="item.value"
                    :label="item.label"
                    :value="item.value">
                  </el-option>
                </el-select>
                <el-button type="primary" icon="search" @click="search">搜索</el-button>
                <el-button type="primary" icon="search" @click="create">新建广告主</el-button>
            </div>
            <el-table :data="tableData" border style="width: 100%" ref="multipleTable">
                <el-table-column prop="id" label="序号"  width="100" align="center">
                </el-table-column>
                <el-table-column prop="consumerName" label="广告平台名称"  width="160" align="center">
                </el-table-column>
                <el-table-column prop="consumerType" label="类型"  width="" align="center" :formatter=formattermediaType>
                </el-table-column>
                <el-table-column prop="remark" label="备注"  width="" align="center">
                </el-table-column>
                <el-table-column prop="dspId" label="用户名"  width="" align="center">
                </el-table-column>
                <el-table-column prop="token" label="密钥"  width="" align="center">
                </el-table-column>
                <el-table-column  label="状态"  width="" align="center">
                  <template slot-scope="scope">
                     <el-switch v-model="scope.row.runState" :active-value=1 :inactive-value=0 @change="changeStatus($event,scope.row)"></el-switch>
                  </template>
                </el-table-column>
                <el-table-column prop="date" label="广告位管理"  width="" align="center">
                   <template slot-scope="scope">
                      <el-button size="small" type="text" @click="AD_Edit(scope.$index, scope.row)">选择</el-button>
                  </template>
                </el-table-column>
                <el-table-column label="操作" width="150" align="center">
                    <template slot-scope="scope">
                        <el-button size="small" type="success" @click="handleEdit(scope.$index, scope.row)">编辑</el-button>
                        <el-button size="small" type="danger" @click="handleDelete(scope.$index, scope.row)">删除</el-button>
                    </template>
                </el-table-column>
            </el-table>
             <div class="pagination">
                <el-pagination @current-change="handleCurrentChange" layout="total,prev, pager, next,jumper" :total="total">
                </el-pagination>
            </div>
        </div>

        <!-- 编辑弹出框 -->
        <el-dialog title="编辑" :visible.sync="editVisible" width="40%">
            <el-form ref="FormNewData" :model="FormNewData" label-width="100px">
                <el-form-item label="平台名称:">
                  <el-input v-model="FormNewData.consumerName" placeholder="请输入广告平台名称" style="width:80%"></el-input>
                </el-form-item>
                <el-form-item label="平台用户名:">
                  <el-input v-model="FormNewData.dspId" placeholder="请输入广告平台用户名" style="width:80%"></el-input>
                </el-form-item>
                <el-form-item label="平台类型:">
                    <template>
                        <el-radio v-model="FormNewData.consumerType" :label="97">ADX</el-radio>
                        <el-radio v-model="FormNewData.consumerType" :label="98">DSP</el-radio>
                        <el-radio v-model="FormNewData.consumerType" :label="99">第三方聚合</el-radio>
                    </template>
                </el-form-item>
                <el-form-item label="QPS上限:">
                    <el-input v-model="FormNewData.qps" placeholder="请输入QPS上限" style="width:80%"></el-input>
                </el-form-item>
                <el-form-item label="RTB Url:">
                    <el-input v-model="FormNewData.rtbUrl" placeholder="请输入RTB Url" style="width:80%"></el-input>
                </el-form-item>
                <el-form-item label="CookieMappingUrl:">
                    <el-input v-model="FormNewData.cookiemappingurl" placeholder="请输入CookieMappingUrl" style="width:80%"></el-input>
                </el-form-item>
                <el-form-item label="备注:">
                    <el-input v-model="FormNewData.remark" style="width:80%"></el-input>
                </el-form-item>
            </el-form> 
            <span slot="footer" class="dialog-footer">
                <el-button @click="editVisible = false">取 消</el-button>
                <el-button type="primary" @click="saveEdit">确 定</el-button>
            </span>
        </el-dialog>

          <!-- 新建弹出框 -->
        <el-dialog title="新建广告平台" :visible.sync="newVisible" width="40%">
            <el-form ref="FormNewData" :model="FormNewData" label-width="100px">
                <el-form-item label="平台名称:">
                  <el-input v-model="FormNewData.consumerName" placeholder="请输入广告平台名称" style="width:80%"></el-input>
                </el-form-item>
                <el-form-item label="平台用户名:">
                  <el-input v-model="FormNewData.dspId" placeholder="请输入广告平台用户名" style="width:80%"></el-input>
                </el-form-item>
                <el-form-item label="平台类型:">
                    <template>
                        <el-radio v-model="FormNewData.consumerType" :label="97">ADX</el-radio>
                        <el-radio v-model="FormNewData.consumerType" :label="98">DSP</el-radio>
                        <el-radio v-model="FormNewData.consumerType" :label="99">第三方聚合</el-radio>
                    </template>
                </el-form-item>
                <el-form-item label="QPS上限:">
                    <el-input v-model="FormNewData.qps" placeholder="请输入QPS上限" style="width:80%"></el-input>
                </el-form-item>
                <el-form-item label="RTB Url:">
                    <el-input v-model="FormNewData.rtbUrl" placeholder="请输入RTB Url" style="width:80%"></el-input>
                </el-form-item>
                <el-form-item label="CookieMappingUrl:">
                    <el-input v-model="FormNewData.cookiemappingurl" placeholder="请输入CookieMappingUrl" style="width:80%"></el-input>
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

export default {
  name: "basetable",
  data() {
    return {
      url: "./static/vuetable.json",
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
        id: "",
        classify:'',
        dspId:'',
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
      },
      // 广告位管理form
      ADmanageData: {
        platformName:'',
      
      
      },
      form: {},
      idx: -1,
      flow_type: 1,
      type: 1,
      Access: "API",
      display_form: 1,

      optionsType:[
         {
          value: '',
          label: ''
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
      value: "",
      types:[],
    };
  },
  created() {
     this.getList();
     this.Datatypes();
  },
 
  methods: {
       // 开关
    changeStatus(val,row){
       let item = row;
             let params = {
                id:item.id,
                runState:item.runState,
            // consumerName:item.consumerName,
            // consumerType:item.consumerType,
            // remark:item.remark,
            }
     flowconsumerupdate(params).then(res=>{
      //let data = res.data;
          // if(data.code != 'A000000') {
          //     this.$message.error(data.message);
          // }
          console.log(res)
            //  this.getList();
      });
    },
    // 分类
    Datatypes(){
        let par = {
        gid : 96
      }
       diclist(par).then(res=>{
     let data = res.data;     
       this.optionsType = data.data
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
      flowconsumergetpage(params).then(res=>{
     let data = res.data;     
       this.tableData = data.data.data
       this.total = data.data.totalItemNum;
      // console.log(res)
      });
    },
    // 搜索
     search() {
       let params = {
         consumerName:this.select_word,
         consumerType:this.FormNewData.classify,
         runState:this.value,
         currentPage:this.cur_page,
         pageSize:this.ps
         }
      flowconsumergetpage(params).then(res=>{
     let data = res.data;     
       this.tableData = data.data.data
       this.total = data.data.totalItemNum;
      // console.log(this.total)
      });
    },
    // 编辑
    handleEdit(index, row) {
      this.idx = index;
      let item = row;
      this.FormNewData = {
            id:item.id,
            consumerName:item.consumerName,
            dspId:item.dspId,
            consumerType:item.consumerType,
            qps:item.qps,
            rtbUrl:item.rtbUrl,
            cookiemappingurl:item.cookiemappingurl,
            remark:item.remark,
      };
      this.editVisible = true;
    },
    // 保存编辑
    saveEdit() {
        let params = {
            id:this.FormNewData.id,
            consumerName:this.FormNewData.consumerName,
            dspId:this.FormNewData.dspId,
            consumerType:this.FormNewData.consumerType,
            qps:this.FormNewData.qps,
            rtbUrl:this.FormNewData.rtbUrl,
            cookiemappingurl:this.FormNewData.cookiemappingurl,
            remark:this.FormNewData.remark,
            }
        flowconsumerupdate(params).then(res=>{
     let data = res.data;
          if(data.code != 'A000000') {
              this.$message.error(data.message);
          }
          this.editVisible = false;
          this.getList();
      });
   
    },
   
     // 删除
    handleDelete(index, row) {
       this.delVisible = true;
        this.row = row;
    },
      // 确定删除
    deleteRow() {
      let params = {id:this.row.id}
      flowconsumerdel(params).then(res=>{
        let data = res.data;
          if(data.code != 'A000000') {
              this.$message.error(data.message);
              return
          }else{
              this.$message.success("删除成功");
              this.delVisible = false;
          }
          this.getList();
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
           console.log(params)
        flowconsumerupdate(params).then(res=>{
          //  if(res.code != 'A000000') {
          //     this.$message.error(res.message);
          // }
          this.AD_Visible = false;
          // this.getList();
      });
      
    },
    
    // 新建
    create() {
      this.newVisible = true;
      this.FormNewData = {};
    },
    //确认
    saveNews() {
      if(!this.FormNewData.consumerName) {
        this.$message.error("平台名称不能为空");
        return;
      }
      if(!this.FormNewData.dspId) {
        this.$message.error("用户名不能为空");
        return;
      }
      if(!this.FormNewData.consumerType) {
        this.$message.error("类型不能为空");
        return;
      }
      if(!this.FormNewData.qps) {
        this.$message.error("qps不能为空");
        return;
      }
      if(!this.FormNewData.rtbUrl) {
        this.$message.error("RTB Url不能为空");
        return;
      }
      if(!this.FormNewData.cookiemappingurl) {
        this.$message.error("cookiemappingurl不能为空");
        return;
      }
        let params = {
            consumerName:this.FormNewData.consumerName,
            dspId:this.FormNewData.dspId,
            consumerType:this.FormNewData.consumerType,
            qps:this.FormNewData.qps,
            rtbUrl:this.FormNewData.rtbUrl,
            cookiemappingurl:this.FormNewData.cookiemappingurl,
            remark:this.FormNewData.remark,
            }
      flowconsumeradd(params).then(res=>{
     let data = res.data;
          // console.log(res)
       if(data.code != 'A000000') {
          this.$message.error(data.message);
       }
       // 掉查询接口
       this.newVisible = false;
       this.getList();
        });
    },
      //模糊名字
    querySearchName(queryString, cb){
        let params = {
          consumerName:this.select_word,
          currentPage:this.cur_page,
          pageSize:this.ps
          }
         flowconsumergetpage(params).then(res=>{
     let data = res.data;
          console.log(data)
          // 调用 callback 返回建议列表的数据
        cb(data.data.data);
       if(data.code != 'A000000') {
          this.$message.error(data.message);
            } 
        });
    },
      handleSelectName(item){
      // console.log(item);
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
</style>
<style>
.el-form-item__content {
  margin-left: 150px !important;
}
</style>

