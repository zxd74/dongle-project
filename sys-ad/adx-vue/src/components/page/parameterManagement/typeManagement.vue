<template>
    <div class="table">
        <div class="container">
            <div class="handle-box">
                <el-input v-model="select_word" placeholder="机型名称" class="handle-input mr10"></el-input>
                <el-button type="primary" icon="search" @click="search">搜索</el-button>
                <!-- <el-button type="primary" icon="search" @click="edit_HY">行业管理</el-button> -->
                <!-- <el-button type="primary" icon="search" @click="edit_DY">地域管理</el-button> -->
                <!-- <el-button type="primary" icon="search" @click="newgrade">添加地域等级</el-button> -->
                <el-button type="primary" icon="search" @click="create" v-if="this.readonly !=1">新建机型</el-button>
            </div>
            <el-table :data="tableData" border style="width: 100%" ref="multipleTable">
                <el-table-column prop="id" label="序号" width="100"  align="center">
                </el-table-column>
                <el-table-column prop="modelName" label="机型名称" align="center" >
                </el-table-column>
                <el-table-column label="操作"   width="200"  align="center">
                    <template slot-scope="scope">
                        <el-button size="small" type="danger" @click="handleDelete2(scope.$index, scope.row)">删除</el-button>
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

       

          <!-- 新建弹出框 -->
        <el-dialog title="新建机型" :visible.sync="newVisible" width="40%">
            <el-form ref="form" :model="form" label-width="100px">
                   <!-- <el-autocomplete
                        class="inline-input"
                        v-model="state1"
                        value-key="modelName"
                        :fetch-suggestions="querySearch"
                        placeholder="请输入内容"
                        @select="handleSelect"
                    ></el-autocomplete> -->
                    <el-input v-model="modelName" placeholder="请输入机型" class="handle-input mr10"></el-input>
                    <el-button @click="handleSelect">添 加</el-button>
                    <div class="div">
                        <el-tag
                            :key="tag"
                            v-for="tag in dynamicTags"
                            closable
                            :disable-transitions="false"
                            @close="handleClose(tag)">
                            {{tag}}
                        </el-tag>
                    </div>
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
            <el-button type="primary" @click="delGrable">确 定</el-button>
          </span>
        </el-dialog>
    </div>
</template>

<script>
import { industryManage,adPositionPriceaddIndustry,diclist,areaGroupgetList,areaGroupadd,areaGroupgetOptionalArea } from "@/api/Api.js";
import { areaGroupget,areaGroupupdate,adPositiongetAiKaPosition ,adPositionPriceadd,adPositionPricegetList,adPositionPricegetupdate} from "@/api/Api.js";
import { flowsourcegetall,adPositionPriceaupdate,adPositiondeleteIndustry,areaGroupdelete } from "@/api/Api.js";

import { devicemodelspage,devicemodels,devicemodelsDel } from "@/api/Api.js";


import treeTransfer from 'el-tree-transfer' // 引入

export default {
  name: "basetable",
  data() {
   const generateData2 = _ => {
        var data = [];
        var cities = ['上海', '北京', '广州', '深圳', '南京', '西安', '成都'];
        var pinyin = ['shanghai', 'beijing', 'guangzhou', 'shenzhen', 'nanjing', 'xian', 'chengdu'];
        cities.forEach((city, index) => {
          data.push({
            label: city,
            key: index,
            pinyin: pinyin[index]
          });
        });
        return data;
      };
    
      
     
    return {
      modelName:'',
      title:["城市", "包含城市"],
      mode: "transfer", 
      fromData:[],
      toData:[],
      data2: [],
      value2: [],
      mediaName:'',
      territory:'',
      state1:'',
      tableData: [],
      data:[],
      cur_page: 1,
      total: 1,
      ps: 10,
      select_cate: "",
      select_word: "",
      del_list: [],
      checkList:[],
      state1:'',
      value_dy:'',
      grade:'',
      city:'',
      DY_Visible1:false,
      delVisible1:false,
      editVisible1:false,
      is_search: false,
      editVisible: false,
      HY_Visible: false,
      DY_Visible: false,
      delVisible: false,
      newVisible: false,
      newaddAD: false,
      template_dialog: false,
      currentRow: null,
      industryType:'',
      dynamicTags: [],
      adPosition:'',
      editPrice:'',
      form: {
        name: "",
        date: "",
        address: "",
        switch: false,
        quanxian:'sss',
        price:'',
      },
        form_dy: {
         DY_manage:'编辑',
         region:'',
      },
      cities:[],
      idx: -1,
      flow_type: 1,
      type: 1,
      system: 1,
      Access: "API",
      display_form: 1,
      // 添加广告位选择流量源
      optionsType:[
         {
          value: 22,
          label: 'PC'
        },
         {
          value: 23,
          label: 'wap'
        },
        {
          value: 158,
          label: 'APP'
        },
      ],
      options_dy:[
          {
          value: '',
          label: ''
        },
      ],
      options:[
         {
          value: 1,
          label: '启用'
        },
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
      options_ad:[
         {
          value: '',
          label: ''
        },
      ],
      value: "",
      region:'',
      readonly:'',

      
    };
  },
  created() {
    this.readonly = this.$store.state.map.get(this.$store.state.currentPath);
    this.getList();
    // this.getIndustry();
    // this.getadPositionget();
    // this.editDY();
  },

  methods: {
       getList() {
              let params  = {
                    pageNo:this.cur_page,
                    pageSize:this.ps
              }
               devicemodelspage(params).then(res=>{
                   console.log(res)
                     let  data = res.data
                    if(data.code != "A000000"){
                        this.$message.error(data.message)
                        return
                    }
                   this.tableData = data.data.data
                   this.total = data.data.totalItemNum
               })
            },
                 //模糊名字
        querySearch(queryString, cb){
            let params = {
              name:this.state1,
              pageNo:this.cur_page,
              pageSize:this.ps
              }
            devicemodelspage(params).then(res=>{
        let data = res.data;
              console.log(data.data.data)
              // 调用 callback 返回建议列表的数据
            cb(data.data.data);
          if(data.code != 'A000000') {
              this.$message.error(data.message);
                } 
            });
        },
            // 添加
        handleSelect(item) {
                console.log(item)
                // let id = item.id
                // let obj = item.modelName
                this.dynamicTags.push(this.modelName)
            },
        handleClose(tag) {
                console.log(tag)
                this.dynamicTags.splice(this.dynamicTags.indexOf(tag), 1);
          },
                // 新建
        create() {
            this.newVisible = true;
            this.state1 = '';
            this.modelName = '',
            this.dynamicTags = [];
          },
          // 确认
        saveNew() {
          let params = {
              modelName:this.dynamicTags.join(',')
          }
            devicemodels(params).then(res=>{
            let data = res.data;
                // console.log(res)
              if(data.code != 'A000000') {
                this.$message.error(data.message);
                }
                this.newVisible = false;
                this.getList();
            });  
          },
          handleDelete2(index,row){
                    this.delVisible = true
                    this.ids = row
          },
            // 确定删除
          delGrable(){
                    let  newChild = {
                       id:this.ids.id
                    }
                   devicemodelsDel(newChild).then(res=>{
                        console.log(res)
                        this.getList()
                        this.delVisible = false;
                    })
          },
          search() {
                this.$refs.pagination.lastEmittedPage = 1
                this.cur_page = 1;
                let params = {
                    pageNo:this.cur_page,
                    pageSize:this.ps,
                    name:this.select_word
                    }
                devicemodelspage(params).then(res=>{
              let data = res.data;
                if(data.code != 'A000000') {
                    this.$message.error(data.message);
                    return;
                  }     
                this.tableData = data.data.data
                this.total = data.data.totalItemNum;
                });
              },
                // 分页导航
            handleCurrentChange(val) {
              this.cur_page = val;
                    let params = {
                 pageNo:this.cur_page,
                    pageSize:this.ps,
                    name:this.select_word
                    }
                devicemodelspage(params).then(res=>{
              let data = res.data;
                if(data.code != 'A000000') {
                    this.$message.error(data.message);
                    return;
                  }     
                this.tableData = data.data.data
                this.total = data.data.totalItemNum;
                });
              
            },
 
   
   
  },
  components:{ treeTransfer } // 注册
};
</script>

<style scoped>
.handle-box {
  margin-bottom: 20px;
}

.handle-select {
  width: 120px;
}
.div{
  margin: 0px 0px 10px 14px;
}
.border{
  display: flex
}
.border1{
  flex: 1;
  width: 200px;
  height: 300px;
  border: 1px solid seagreen;
}
.border2{
  flex: 1;
  width: 200px;
  height: 300px;
  border: 1px solid seagreen;
}

.handle-input {
  width: 160px;
  display: inline-block;
}
.del-dialog-cnt {
  font-size: 16px;
  text-align: center;
}
.el-button+.el-button{
  margin-left: 1px;
}
.add_HY{
  display: flex;
  padding-top: 20px
}
.border{
  width: 100%;
  height: 2px;
  margin-top: 20px;
  margin-bottom: 20px
}
.mrl11{
  width: 203px;
}
.price{
  width: 203px;
  color: green
}
.right{
  /* margin-left: 44%;
  margin-top: -12%; */
}
.dialog-footer{
    float: right;
    margin-top: -30px;
}
.div{
  padding: 20px;

}


</style>
<style>
  input,button,select,textarea{outline:none}
  textarea{resize:none}
  .filter-tree{
        display: none
}
.transfer{
    height: 400px !important;
}
</style>

