<template>
    <div class="table">
        <div class="container">
            <div class="handle-box">
                <!-- <el-input v-model="select_word" placeholder="请输入名称" class="handle-input mr10"></el-input> -->
                  <el-autocomplete
                    class="inline-input"
                    v-model="select_word"
                    value-key="mediaName"
                    :fetch-suggestions="querySearchName"
                    placeholder="请输入名称"
                    @select="handleSelectName"
                  ></el-autocomplete>
                <el-select v-model="value" placeholder="流量源类型"    class="inline-input">
                  <el-option
                    v-for="item in options"
                    :key="item.value"
                    :label="item.label"
                    :value="item.value">
                  </el-option>
                </el-select>
                <!-- <el-input v-model="select_word" placeholder="请输入公司名称" class="handle-input mr10"></el-input> -->
                <el-autocomplete
                    class="inline-input"
                    v-model="companyName"
                    value-key="companyName"
                    :fetch-suggestions="querySearch_companyName"
                    placeholder="请输入公司名称"
                    @select="handleSelect_companyName"
                 ></el-autocomplete>
                 <el-select v-model="optionsState" placeholder="状态"    class="inline-input">
                  <el-option
                    v-for="item in options_"
                    :key="item.value"
                    :label="item.label"
                    :value="item.value">
                  </el-option>
                </el-select>
                <el-button type="primary" icon="search" @click="search">搜索</el-button>
                 <el-button type="primary" icon="search" @click="create" v-if="this.readonly != 1 ">新建流量源</el-button>
            </div>
            <el-table :data="tableData" border style="width: 100%" ref="multipleTable">
                <el-table-column prop="id" label="ID"  width="100" align="center">
                </el-table-column>
                <el-table-column prop="mediaUuid" label="媒体ID"  width="" align="center">
                </el-table-column>
                <el-table-column prop="mediaName" label="流量源名称"  width="" align="center">
                </el-table-column>
                <el-table-column prop="mediaType" label="流量源类型"  width="" align="center" :formatter=formattermediaType>
                </el-table-column>
                <el-table-column prop="joinType" label="接入方式"  width="" align="center" :formatter=formatterjoinType>
                </el-table-column>
                <el-table-column prop="adpCount" label="广告位统计"  width="" align="center">
                </el-table-column>
                <el-table-column prop="companyName" label="公司名称"  width="200" align="center">
                </el-table-column>
                <el-table-column prop="quanxian" label="用户权限"  width="" align="center" v-if="this.readonly !=1">
                   <template slot-scope="scope">
                      <img src="../../../../src/assets/u518.png"  @click="edit_quanxian(scope.$index, scope.row)">
                  </template>
                </el-table-column>
                <el-table-column  label="状态"  width="" align="center" v-if="this.readonly !=1">
                  <template slot-scope="scope">
                     <el-switch v-model="scope.row.runState" :active-value=1 :inactive-value=0 @change="changeStatus($event,scope.row)"></el-switch>
                  </template>
                </el-table-column>
                <el-table-column label="操作" width="150" align="center">
                    <template slot-scope="scope">
                        <el-button size="small" type="success" @click="handleEdit(scope.$index, scope.row)">编辑</el-button>
                        <!-- <el-button size="small" type="danger" @click="handleDelete(scope.$index, scope.row)">删除</el-button> -->
                        <!-- <el-button size="small" type="success" @click="handAD(scope.$index, scope.row)">添加广告位</el-button> -->
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
        <!-- 用户权限弹出框 -->
        <el-dialog title="只有授权的流量管理员可以操作该流量源" :visible.sync="QX_Visible" width="40%">
                  <el-form ref="form" :model="form" label-width="100px">
                      <el-autocomplete
                        class="inline-input"
                        v-model="state1"
                        value-key="userName"
                        :fetch-suggestions="querySearch"
                        placeholder="请输入内容"
                        @select="handleSelect"
                      ></el-autocomplete>
                      <!-- <el-button type="primary" @click="saveNews">搜 索</el-button> -->
                      <div class="div">
                        <el-tag
                          :key="tag.appPkgId"
                          v-for="tag in dynamicTags"
                          closable
                          :disable-transitions="false"
                          @close="handleClose(tag)">
                          {{tag.name}}
                        </el-tag>
                      </div>
                      <span slot="footer" class="dialog-footer">
                          <el-button @click="QX_Visible = false">取 消</el-button>
                          <el-button type="primary" @click="QX_save">确 定</el-button>
                      </span>
                </el-form>
            <!-- <span slot="footer" class="dialog-footer">
                <el-button @click="editVisible2 = false">取 消</el-button>
                <el-button type="primary" @click="saveEdit">确 定</el-button>
            </span> -->
        </el-dialog>

        <!-- 编辑弹出框 -->
        <el-dialog title="编辑" :visible.sync="editVisible" width="45%">
          <el-form ref="form" :model="form" label-width="100px" :label-position="labelPosition">
                <el-form-item label="流量源名称:">
                    <el-input v-model="form.mediaName" placeholder="请输入流量源名称"></el-input>
                </el-form-item>
                <el-form-item label="流量源类型:">
                    <el-radio-group v-model="form.mediaType" size="medium">
                        <el-radio-button label="65" disabled>站点</el-radio-button>
                        <el-radio-button label="66" disabled>ADX</el-radio-button>
                    </el-radio-group>
                </el-form-item>
                <el-form-item label="媒体类型:" v-if="form.mediaType == 66">
                    <template>
                        <el-radio v-model="form.type" disabled :label="68">普通媒体</el-radio>
                        <el-radio v-model="form.type" disabled :label="69">联盟媒体</el-radio>
                    </template>
                </el-form-item>
                <el-form-item label="接入方式:" >
                    <!-- <template> -->
                        <el-radio v-model="form.joinType" disabled :label="71">API</el-radio>
                        <el-radio v-model="form.joinType" disabled :label="72" v-if="form.type == 69">SDK</el-radio>
                    <!-- </template> -->
                </el-form-item>
                <el-form-item label="站点URL:" v-if="form.mediaType == 65">
                    <el-input v-model="form.websiteUrl" placeholder="请输入站点URL"></el-input>
                </el-form-item>
                <el-form-item label="公司名称:">
                    <el-input v-model="form.companyName" placeholder="请输入公司名称"></el-input>
                </el-form-item>
                <el-form-item label="联系地址:">
                    <el-input v-model="form.companyAddr" placeholder="请输入联系地址"></el-input>
                </el-form-item>
                <el-form-item label="联系人:">
                    <el-input v-model="form.companyLinkman" placeholder="请输入联系人"></el-input>
                </el-form-item>
                <el-form-item label="联系方式:">
                    <el-input v-model="form.linkmanTel" placeholder="请输入联系方式"></el-input>
                </el-form-item>
            </el-form>
            <span slot="footer" class="dialog-footer">
                <el-button @click="editVisible = false">取 消</el-button>
                <el-button type="primary" @click="saveEdit" v-if="this.readonly !=1">确 定</el-button>
            </span>
        </el-dialog>

          <!-- 新建弹出框 -->
        <el-dialog title="新建流量源" :visible.sync="newVisible" width="45%">
            <el-form ref="form" :model="form" label-width="100px" :label-position="labelPosition">
                <el-form-item label="* 流量源名称:">
                   <el-input v-model="form.mediaName" placeholder="请输入流量源名称"></el-input>
                </el-form-item>
                <el-form-item label="* 流量源类型:">
                    <el-radio-group v-model="form.mediaType" size="medium">
                        <el-radio-button label="65">站点</el-radio-button>
                        <!-- <el-radio-button label="66">APP</el-radio-button> -->
                        <el-radio-button label="66">ADX</el-radio-button>
                    </el-radio-group>
                </el-form-item>
                <el-form-item label="* 媒体类型:" v-if="form.mediaType == 66">
                    <template>
                        <el-radio v-model="form.type" label="68">普通媒体</el-radio>
                        <el-radio v-model="form.type" label="69">联盟媒体</el-radio>
                    </template>
                </el-form-item>
                <el-form-item label="* 接入方式:" >
                    <!-- <template> -->
                        <el-radio v-model="form.joinType" label="71">API</el-radio>
                        <el-radio v-model="form.joinType" label="72" v-if="form.type == 69">SDK</el-radio>
                    <!-- </template> -->
                </el-form-item>
                <el-form-item label="* 站点URL:" v-if="form.mediaType == 65">
                    <el-input v-model="form.websiteUrl" placeholder="请输入站点URL"></el-input>
                </el-form-item>
                <el-form-item label="* 公司名称:">
                    <el-input v-model="form.companyName" placeholder="请输入公司名称"></el-input>
                </el-form-item>
                <el-form-item label="* 联系地址:">
                    <el-input v-model="form.companyAddr" placeholder="请输入联系地址"></el-input>
                </el-form-item>
                <el-form-item label="* 联系人:">
                    <el-input v-model="form.companyLinkman" placeholder="请输入联系人"></el-input>
                </el-form-item>
                <el-form-item label="* 联系方式:">
                    <el-input v-model="form.linkmanTel" placeholder="请输入联系方式"></el-input>
                </el-form-item>
            </el-form>
            <span slot="footer" class="dialog-footer">
                <el-button @click="newVisible = false">取 消</el-button>
                <el-button type="primary" @click="saveNew">确 定</el-button>
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

import { flowsourcegetall } from "@/api/Api.js";
import { flowsourceadd } from "@/api/Api.js";
import { flowsourceget } from "@/api/Api.js";
import { flowsourcedel } from "@/api/Api.js";
import { flowsourcegetusers } from "@/api/Api.js";
import { flowsourceupdate } from "@/api/Api.js";

import { flowsourcegetgrand } from "@/api/Api.js";
import { flowsourcegrand } from "@/api/Api.js";
import { flowsourcemovegrand } from "@/api/Api.js";

export default {
  name: "basetable",
  data() {
    return {
      labelPosition: 'left',
      url: "./static/vuetable.json",
      tableData: [],
      cur_page: 1,
      total: 1,
      ps:10,
      companyName: "",
      select_word: "",
      del_list: [],
      is_search: false,
      editVisible: false,
      QX_Visible: false,

      delVisible: false,
      newVisible: false,
      newaddAD: false,
      template_dialog: false,
      currentRow: null,
      runState:'',
      state1:'',
      dynamicTags:[],
      form: {
        id: "",
        mediaName: "",
        mediaType: "",
        type: '',
        joinType:'',
        websiteUrl:'',
        companyName:'',
        companyAddr:'',
        companyLinkman:'',
        linkmanTel:'',
      },
      idx: -1,
      flow_type: 1,
      type: 1,
      system: 1,
      Access: "API",
      display_form: 1,
      // 添加广告位选择流量源
      options: [
        {
          value: "65",
          label: "站点"
        },
        {
          value: "66",
          label: "ADX"
        },
      ],
       options_: [
        {
          value: "0",
          label: "未开启"
        },
        {
          value: "1",
          label: "开启"
        },
      ],
      value: "",
      optionsState:'',
      readonly:'',

    };
  },
  // created() {
  //   this.getData();
  // },
  created() {
       this.readonly = this.$store.state.map.get(this.$store.state.currentPath);
       this.getList();
  },

  methods: {
       // 格式化数据
    formattermediaType(row, column) {
      if(row.mediaType == 65) {
        return "站点";
      } else if(row.mediaType == 66){
        return "ADX";
      }
    },
      formatterjoinType(row, column) {
      if(row.joinType == 71) {
        return "API";
      } else if(row.joinType == 72){
        return "SDK";
      }
    },
    // 开关
    changeStatus(val,row){
             let params = {
                id:row.id,
                runState:row.runState,
                 mediaName:row.mediaName,
            mediaType:row.mediaType,
            type:row.type,
            joinType:row.joinType,
            companyName:row.companyName,
            companyAddr:row.companyAddr,
            companyLinkman:row.companyLinkman,
            linkmanTel:row.linkmanTel
            }
     flowsourceupdate(params).then(res=>{
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
       mediaName:this.select_word,
       mediaType:this.value,
       companyName:this.companyName,
       runState:this.optionsState,
       currentPage:this.cur_page,
       pageSize:this.ps
       }
      flowsourcegetall(params).then(res=>{
      let data = res.data;
        this.tableData = data.data.data
      });
    },

     getList() {
      let params = {currentPage:this.cur_page,pageSize:this.ps}
      flowsourcegetall(params).then(res=>{
        let data = res.data;
          if(data.code != 'A000000') {
              this.$message.error(data.message);
              return;
          }
        this.tableData = data.data.data
        this.total = data.data.totalItemNum;
      });
    },
     // 搜索
    search() {
      this.$refs.pagination.lastEmittedPage = 1
      this.cur_page = 1;
      let params = {
       mediaName:this.select_word,
       mediaType:this.value,
       companyName:this.companyName,
       runState:this.optionsState,
       currentPage:this.cur_page,
       pageSize:this.ps
       }
      flowsourcegetall(params).then(res=>{
      let data = res.data;
        this.tableData = data.data.data
        this.total = data.data.totalItemNum;
      });
    },
    //模糊名字
    querySearchName(queryString, cb){
        let params = {
          mediaName:this.select_word,
          currentPage:this.cur_page,
          pageSize:this.ps
          }
         flowsourcegetall(params).then(res=>{
     let data = res.data;
          console.log(data.data.data)
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
    handleSelect_companyName(item){
      // console.log(item);
      this.id = item.id
    },
    // 模糊公司
    querySearch_companyName(queryString, cb){
        let params = {
          companyName:this.companyName,
          currentPage:this.cur_page,
          pageSize:this.ps
          }
         flowsourcegetall(params).then(res=>{
     let data = res.data;
          console.log(data.data.data)
          // 调用 callback 返回建议列表的数据
        cb(data.data.data);
       if(data.code != 'A000000') {
          this.$message.error(data.message);
            }
        });
    },
    formatter(row, column) {
      return row.address;
    },
    filterTag(value, row) {
      return row.tag === value;
    },
    // 编辑
    handleEdit(index, row) {
      this.idx = index;
      let item = row;
      console.log(item)
      this.form = {
            id:item.id,
            type:item.type,
            mediaName:item.mediaName,
            mediaType:item.mediaType,
            type:item.type,
            joinType:item.joinType,
            companyName:item.companyName,
            companyAddr:item.companyAddr,
            companyLinkman:item.companyLinkman,
            linkmanTel:item.linkmanTel
      };
      this.editVisible = true;
    },
    // 保存编辑
    saveEdit() {
       let params = {
            id:this.form.id,
            mediaName:this.form.mediaName,
            mediaType:this.form.mediaType,
            type:this.form.type,
            joinType:this.form.joinType,
            companyName:this.form.companyName,
            companyAddr:this.form.companyAddr,
            companyLinkman:this.form.companyLinkman,
            linkmanTel:this.form.linkmanTel
            }
     flowsourceupdate(params).then(res=>{
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
      flowsourcedel(params).then(res=>{
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
    // 点击添加广告位跳转
    // handAD() {
    //   this.$router.push({path:'adSense'})
    // },
    delAll() {
      const length = this.multipleSelection.length;
      let str = "";
      this.del_list = this.del_list.concat(this.multipleSelection);
      for (let i = 0; i < length; i++) {
        str += this.multipleSelection[i].name + " ";
      }
      this.$message.error("删除了" + str);
      this.multipleSelection = [];
    },
    handleSelectionChange(val) {
      this.multipleSelection = val;
    },

    // 新建
    create() {
      this.newVisible = true;
      this.form = {};
    },
    // 确认
    saveNew() {
        // 参数判断
      if(!this.form.mediaName) {
        this.$message.error("流量源名称不能为空");
        return;
      }
      if(!this.form.mediaType) {
        this.$message.error("流量源类型不能为空");
        return;
      }
      if(!this.form.joinType) {
        this.$message.error("接入方式不能为空");
        return;
      }
      if(!this.form.companyName) {
        this.$message.error("公司名不能为空");
        return;
      }
      if(!this.form.companyAddr) {
        this.$message.error("地址不能为空");
        return;
      }
      if(!this.form.companyLinkman) {
        this.$message.error("联系人不能为空");
        return;
      }
      if(!this.form.linkmanTel) {
        this.$message.error("联系方式不能为空");
        return;
      }
      let params = {
            mediaName:this.form.mediaName,
            mediaType:this.form.mediaType,
            type:this.form.type,
            joinType:this.form.joinType,
            companyName:this.form.companyName,
            companyAddr:this.form.companyAddr,
            companyLinkman:this.form.companyLinkman,
            linkmanTel:this.form.linkmanTel
            }
            console.log(this.form.mediaName)
       flowsourceadd(params).then(res=>{
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

     // 权限弹出
    edit_quanxian(index,row){
      this.id = row.id;
        let params = {
            aid:row.id,
            }
      flowsourcegetgrand(params).then(res=>{
      let data = res.data;
            console.log(res)
        if(data.code != 'A000000') {
            this.$message.error(data.message);
        }
        this.dynamicTags = data.data
        this.QX_Visible = true;
      });
    },
    // 模糊搜索权限
    querySearch(queryString, cb) {
       let params = {
            uname:this.state1,
            }
      flowsourcegetusers(params).then(res=>{
     let data = res.data;
          console.log(data.data)
          // 调用 callback 返回建议列表的数据
        cb(data.data);
       if(data.code != 'A000000') {
          this.$message.error(data.message);
          }
        });
      },
       // 添加
      handleSelect(item) {
        console.log(item);
         let params = {
            aid:this.id,
            uid:item.id
            }
        flowsourcegrand(params).then(res=>{
      let data = res.data;
            // console.log(res)
        if(data.code != 'A000000') {
            this.$message.error(data.message);
          }
        let parm =  {
         aid:this.id,
          }
        flowsourcegetgrand(parm).then(res=>{
      let data = res.data;
        if(data.code != 'A000000') {
            this.$message.error(data.message);
        }
          this.dynamicTags = data.data
        console.log(this.dynamicTags);
         });
       });
      },
    // 权限删除
      handleClose(tag) {
        console.log(tag)
         let params = {
            uid:tag.uid,
             aid:this.id,
            }
        flowsourcemovegrand(params).then(res=>{
      let data = res.data;
            console.log(res)
        if(data.code != 'A000000') {
            this.$message.error(data.message);
            return;
          }
        let parm =  {
          aid:this.id,
        }
        flowsourcegetgrand(parm).then(res=>{
      let data = res.data;

        if(data.code != 'A000000') {
            this.$message.error(data.message);
        }
        this.dynamicTags = data.data
         });
       });
      },
    // 权限保存
    QX_save(){
         this.QX_Visible = false;
    },
    saveAD() {
      this.newaddAD = false;
    },

    // 选择模板
    select_template() {
      this.template_dialog = true;
    },

    template_(){

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
  width: 200px;
  display: inline-block;
}
.del-dialog-cnt {
  font-size: 16px;
  text-align: center;
}
 .el-tag + .el-tag {
    margin-left: 10px;
  }
  .div{
  padding: 20px;

}
.inline-input{
  width: 160px;
}
</style>
<style>
  .must::before{
    content: "*";
    color: #f56c6c;
    margin-right: 4px;
  }
  /* .must{
    border: 1px solid crimson;
  } */

</style>
