<template>
    <div class="table">
        <div class="container">
            <el-tabs v-model="activeName">
                    <el-tab-pane label="行业黑名单" name="first">
                                <!-- <div class="handle-box">
                                    <el-input v-model="select_industrys" placeholder="请输入行业标签" class="handle-input mr10"></el-input>
                                    <el-button type="primary" icon="search" @click="search">搜索</el-button>
                                    <el-button type="primary" icon="search" @click="create">新建黑名单</el-button>
                                </div>       
                                <el-table :data="DataIndustrys" border style="width: 100%" ref="multipleTable">
                                    <el-table-column prop="id" label="序号"   align="center">
                                    </el-table-column>
                                    <el-table-column prop="industryName" label="行业标签名称" align="center" >
                                    </el-table-column>
                                    <el-table-column label="状态" width align="center">
                                        <template slot-scope="scope">
                                            <el-switch
                                            v-model="scope.row.state"
                                            :active-value="1"
                                            :inactive-value="0"
                                            @change="changeStatus($event,scope.row)"
                                            ></el-switch>
                                        </template>
                                    </el-table-column>
                                    <el-table-column label="操作"   align="center">
                                        <template slot-scope="scope">
                                            <el-button size="small" type="success" @click="handleEdit(scope.$index, scope.row)">编辑</el-button>
                                            <el-button size="small" type="danger" @click="handleDelete(scope.$index, scope.row)">删除</el-button>
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
                                </div>   -->
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
                                <el-button type="primary" @click="saveNews">确 定</el-button>
                         </div>
                             
                    </el-tab-pane>
                    <el-tab-pane label="竞品标签" name="second">
                        <div class="handle-box">
                            <el-input v-model="productsName" placeholder="请输入禁品标签名称" class="handle-input mr10"></el-input>
                            <el-button type="primary" icon="search" @click="searchTitle">搜索</el-button>
                            <el-button type="primary" icon="search" @click="createProductsName">新建黑名单</el-button>
                        </div>         
                        <el-table :data="products" border style="width: 100%" ref="multipleTable">
                            <el-table-column prop="id" label="序号"   align="center">
                            </el-table-column>
                            <el-table-column prop="productsName" label="竞品标签名称" align="center" >
                            </el-table-column>
                            <el-table-column label="操作"   align="center">
                                <template slot-scope="scope">
                                    <el-button size="small" type="danger" @click="handleDelete2(scope.$index, scope.row)">删除</el-button>
                                </template>
                            </el-table-column>
                        </el-table>
                        <div class="pagination">
                                    <el-pagination @current-change="handleCurrentChange2" 
                                    layout="total,prev, pager, next,jumper" 
                                    :total="total2" 
                                    :current-page="cur_page2" 
                                    :page-size="ps2"
                                    ref="pagination"
                                    >
                                    </el-pagination>
                        </div>  
                    </el-tab-pane>
                    <el-tab-pane label="应用黑名单" name="third">
                        <div class="handle-box">
                            <el-input v-model="select_word" placeholder="请输入应用名称" class="handle-input mr10"></el-input>
                            <el-button type="primary" icon="search" @click="searchApp">搜索</el-button>
                            <el-button type="primary" icon="search" @click="createNames">新建黑名单</el-button>
                        </div>         
                        <el-table :data="tableAppp" border style="width: 100%" ref="multipleTable">
                            <el-table-column prop="id" label="序号"   align="center">
                            </el-table-column>
                            <el-table-column prop="name" label="应用包名" align="center" >
                            </el-table-column>
                            <el-table-column prop="appName" label="应用名称" align="center" >
                            </el-table-column>
                            <el-table-column label="操作"   align="center">
                                <template slot-scope="scope">
                                    <el-button size="small" type="danger" @click="handleDelete3(scope.$index, scope.row)">删除</el-button>
                                </template>
                            </el-table-column>
                        </el-table>
                        <div class="pagination">
                            <el-pagination @current-change="handleCurrentChange3" 
                            layout="total,prev, pager, next,jumper" 
                            :total="total3" 
                            :current-page="cur_page3" 
                            :page-size="ps3"
                            ref="pagination"
                            >
                            </el-pagination>
                        </div>  
                    </el-tab-pane>
             </el-tabs>
        </div>

       <!-- 行业黑名单 编辑弹出框 -->
        <el-dialog title="编辑" :visible.sync="editVisible" width="40%">
             <el-form ref="form" :model="form" label-width="80px">
                  <el-form-item label="行业标签名称">
                    <el-input v-model="form.name" @focus="Geeterr"></el-input>
                </el-form-item>
            </el-form>
            <span slot="footer" class="dialog-footer">
                <el-button @click="editVisible = false">取 消</el-button>
                <el-button type="primary" @click="saveEdit">确 定</el-button>
            </span>
        </el-dialog>
        <!-- 行业黑名单 新建弹出框 -->
        <el-dialog title="新建" :visible.sync="newVisible" width="40%">
            <el-form ref="form" :model="form" label-width="80px">
                <el-form-item label="行业标签名称">
                    <el-input v-model="form.name" @focus="Geeterr"></el-input>
                </el-form-item>
                <!-- <el-form-item label="备注">
                    <el-input v-model="form.url"></el-input>
                </el-form-item> -->
            </el-form>
            <span slot="footer" class="dialog-footer">
                <el-button @click="newVisible = false">取 消</el-button>
                <el-button type="primary" @click="saveNews">确 定</el-button>
            </span>
        </el-dialog>
         <!-- <el-dialog title="行业标签" :visible.sync="newTree" width="30%">
                 <div class="container">
                                <div class="custom-tree-container">
                                    <div class="block">
                                        <el-tree
                                        :data="data5"
                                        show-checkbox
                                        :props="props"
                                        node-key="pid"
                                        ref="tree"
                                        default-expand-all
                                        :expand-on-click-node="false"
                                         @check-change="handleCheckChange"
                                         :check-strictly="true"
                                        >
                                        </el-tree>
                                    </div>
                                </div>
                 </div>
            <span slot="footer" class="dialog-footer">
                <el-button @click="newTree = false">取 消</el-button>
                <el-button type="primary" @click="saveTree">确 定</el-button>
            </span>
        </el-dialog> -->
         <!-- 行业黑名单 删除提示框 -->
        <el-dialog title="提示" :visible.sync="delVisible" width="300px" center>
            <div class="del-dialog-cnt">删除不可恢复，是否确定删除？</div>
            <span slot="footer" class="dialog-footer">
                <el-button @click="delVisible = false">取 消</el-button>
                <el-button type="primary" @click="deleteRow">确 定</el-button>
            </span>
        </el-dialog>

            <!-- 禁品标签 弹出框 -->
        <el-dialog title="新建" :visible.sync="newNnames" width="40%">
            <el-form ref="form" :model="form2" label-width="25%">
                <el-form-item label="竞品标签名称">
                    <el-input v-model="form2.productsName"></el-input>
                </el-form-item>
            </el-form>
            <span slot="footer" class="dialog-footer">
                <el-button @click="newNnames = false">取 消</el-button>
                <el-button type="primary" @click="saveNames">确 定</el-button>
            </span>
        </el-dialog>
           <!-- 禁品标签 删除提示框 -->
        <el-dialog title="提示" :visible.sync="delNames" width="300px" center>
            <div class="del-dialog-cnt">删除不可恢复，是否确定删除？</div>
            <span slot="footer" class="dialog-footer">
                <el-button @click="delNames = false">取 消</el-button>
                <el-button type="primary" @click="deleteNames">确 定</el-button>
            </span>
        </el-dialog>

         <!-- 应用黑名单 新建提示框 -->
        <el-dialog title="新建" :visible.sync="newApply" width="40%">
            <el-form ref="form" :model="form" label-width="100px">
                   <el-autocomplete
                        class="inline-input"
                        v-model="state1"
                        value-key="appName"
                        :fetch-suggestions="querySearch"
                        placeholder="请输入内容"
                        @select="handleSelect"
                    ></el-autocomplete>
                    <div class="div">
                        <el-tag
                            :key="tag.id"
                            v-for="tag in dynamicTags"
                            closable
                            :disable-transitions="false"
                            @close="handleClose(tag)">
                            {{tag.appName+' | '+tag.name}}
                        </el-tag>
                    </div>
            </el-form>
            <span slot="footer" class="dialog-footer">
                <el-button @click="newApply = false">取 消</el-button>
                <el-button type="primary" @click="saveApply">确 定</el-button>
            </span>
        </el-dialog>

           <!-- 应用黑名单 删除提示框 -->
        <el-dialog title="提示" :visible.sync="AppVisible" width="300px" center>
            <div class="del-dialog-cnt">删除不可恢复，是否确定删除？</div>
            <span slot="footer" class="dialog-footer">
                <el-button @click="AppVisible = false">取 消</el-button>
                <el-button type="primary" @click="deleteApp">确 定</el-button>
            </span>
        </el-dialog>

        
    </div>
</template>

<script>
let id = 1000;
var currentNode = null;
var  mySet =  new Set()

// import { UserList } from "@/api/Api.js";
// import { UserAdd } from "@/api/Api.js";
// import { UserUpdate } from "@/api/Api.js";
// import { UserDelete } from "@/api/Api.js";
// import { UserGetAuth } from "@/api/Api.js";
// import { GetallFlowsource } from "@/api/Api.js";

import { industrysblacklistspage,industrysselect,industrysblacklists,industrysblacklistsDelete,
industrysblacklistsStateOn,industrysblacklistsStateOff
} from "@/api/Api.js";
import { competingproductspage,competingproducts,competingproductsDel
} from "@/api/Api.js";
import { apppkgblacklistspage,appblanklistsDel,Newapppkgblacklists,industryblacklistsInfo,apppkgsselect
} from "@/api/Api.js";
var data = [
                // {
                //     "id": 2,
                //     "name": "测试主菜单1",
                //     "pid": 0,
                //     "url": "/menu/son1",
                //     "state": 1,
                //     "createTime": "2019-01-28T07:36:56.000+0000",
                //     "updateTime": "2019-01-28T08:01:44.000+0000",
                //     "child": [
                //         {
                //         "id": 3,
                //         "name": "测试子菜单1-1",
                //         "pid": 2,
                //         "url": "/menu/son1-1",
                //         "state": 1,
                //         "createTime": "2019-01-28T09:23:20.000+0000",
                //         "updateTime": null,
                //         "child": []
                //         }
                //     ]
                // }
                ];
export default {
  name: "basetable",
  data() {
    var validatePass2 = (rule, value, callback) => {
        if (value === '') {
          callback(new Error('请再次输入密码'));
        } else if (value !== this.form.password) {
          callback(new Error('两次输入密码不一致!'));
        } else {
          callback();
        }
      };
    return {
      activeName: 'first',
      props: {
            label: 'name',
            children: 'children',
        },
    //   data5:'',
      par:[],
      checkedKey:[],
      data5: JSON.parse(JSON.stringify(data)),
      state1:'',
      tableData: [],
      DataIndustrys:[],
      products:[],
      tableAppp:[],
      dynamicTags:[],
      cur_page: 1,
      total: 1,
      total2: 2,
      ps:10,
      cur_page2: 1,
      ps2:10,
      cur_page3: 1,
      ps3:10,
      total3: 2,
      select_industrys:'',
      productsName:'',
      select_word: "",
      is_search: false,
      editVisible: false,
      delVisible: false,
      newVisible: false,
      template_dialog: false,
      currentRow: null,
      newNnames:false,
      delNames:false,
      newTree:false,
      newApply:false,
      AppVisible:false,

      form: {
        url:'',
        name: "",
        blackList:'',
        date: "",
        industryID:'',
      },
      form2: {
        id:'',
        productsName:'',
      },
 
    };
  },
  created() {
    this.getData();
    this.getIndustrys();
    this.getProductss();
    this.getTableAppp();
  },

  methods: {
    //   行业tree
        getData() {
               industrysselect().then(res=>{
                   console.log(res)
                   data = res.data.data
                   this.data5 = data
                //    console.log(this.data5)
                      //    勾选tree
                industryblacklistsInfo().then(res=>{
                    let data = res.data
                    this.checkedKey = data.data
                    console.log(this.checkedKey)
                    this.$forceUpdate()
                    this.data5 = Object.assign([], this.data5);
                    })
               })
            },
    // 行业黑名单列表
         getIndustrys() {
              let par  = {
                    pageNo:this.cur_page,
                    pageSize:this.ps
              }
               industrysblacklistspage(par).then(res=>{
                //    console.log(res)
                     let  data = res.data
                    if(data.code != "A000000"){
                        this.$message.error(data.message)
                        return
                    }
                   this.DataIndustrys = data.data.data
                   this.total =data.data.totalItemNum
                   
               })
            },
            // 选择行业id
            handleCheckChange(data,checked,indeterminate) {
                console.log(checked)
                console.log(data)
                let arr = []
                for(var i in data){
                    if(checked != false){
                         mySet.add(data.id)
                    }else if(checked != true){
                         mySet.delete(data.id)
                    }
 
                }
                this.par = Array.from(mySet)
                    this.form.name = data.name
                    this.form.industryID = data.id
                },
            // 保存行业黑名单
            saveNews(){
               industrysblacklists(this.par).then(res=>{
                   console.log(res)
                     let  data = res.data
                    if(data.code != "A000000"){
                        this.$message.error(data.message)
                        return
                    }
                //     //    勾选tree
                // industryblacklistsInfo().then(res=>{
                //     console.log(res)
                //     let data = res.data
                //     this.checkedKey = data.data
                //     })
               })
            },
             //删除 
            handleDelete(index,row) {
                this.delVisible = true 
                this.ids = row
            },
             // 确定删除
            deleteRow(){
                    let  newChild = {
                       id:this.ids.id
                    }
                   industrysblacklistsDelete(newChild).then(res=>{
                        console.log(res)
                        this.getIndustrys()
                        this.delVisible = false;
                    })
            },
             // 编辑
            handleEdit(index,row){
                console.log(row)
                this.getData();
                this.editVisible = true
                this.form.name = row.industryName
                this.form.id = row.id
                this.form.industryID = row.industryId
            },
            // 保存编辑
            saveEdit() {
                 let par  = {
                    industryName: this.form.name,
                    industryId: this.form.industryID,
                    id:this.form.id
              }
               industrysblacklists(par).then(res=>{
                //    console.log(res)
                     let  data = res.data
                    if(data.code != "A000000"){
                        this.$message.error(data.message)
                        return
                    }
                    this.editVisible = false
                    this.getIndustrys();
              
               })
            },
            // 行业开关
            changeStatus(val,row){
                console.log(row)
                  let item = row;
                  if(item.state == 1){
                           let par  = {
                                    id:item.id
                            }
                            industrysblacklistsStateOn(par).then(res=>{
                                console.log(res)
                                    let  data = res.data
                                    if(data.code != "A000000"){
                                        this.$message.error(data.message)
                                        return
                                    }
                                    // this.editVisible = false
                                    // this.getIndustrys();
                            
                            })
                  }else if(item.state == 0){
                            let par  = {
                                    id:item.id
                            }
                            industrysblacklistsStateOff(par).then(res=>{
                                console.log(res)
                                    let  data = res.data
                                    if(data.code != "A000000"){
                                        this.$message.error(data.message)
                                        return
                                    }
                                    // this.editVisible = false
                                    // this.getIndustrys();
                            
                            })
                  }
            },
            // 行业搜索
            search(){
                this.$refs.pagination.lastEmittedPage = 1
                this.cur_page = 1;
                let par  = {
                    pageNo:this.cur_page,
                    pageSize:this.ps,
                    name:this.select_industrys    
                    
              }
               industrysblacklistspage(par).then(res=>{
                   console.log(res)
                     let  data = res.data
                    if(data.code != "A000000"){
                        this.$message.error(data.message)
                        return
                    }
                   this.DataIndustrys = data.data.data
                   this.total =data.data.totalItemNum
               })
            },
            handleCurrentChange(val){
                    this.cur_page = val;
                    let params = {
                    pageNo:this.cur_page,
                    pageSize:this.ps,
                    name:this.select_industrys    
                    }
                industrysblacklistspage(params).then(res=>{
                    let  data = res.data
                        if(data.code != "A000000"){
                            this.$message.error(data.message)
                            return
                        } 
                    this.DataIndustrys = data.data.data
                    this.total = data.data.totalItemNum
                // console.log(res)
                });
            },
           // 竞品标签列表
           getProductss() {
              let par  = {
                    pageNo:this.cur_page2,
                    pageSize:this.ps2
              }
               competingproductspage(par).then(res=>{
                   console.log(res)
                     let  data = res.data
                    if(data.code != "A000000"){
                        this.$message.error(data.message)
                        return
                    }
                   this.products = data.data.data
                   this.total2 = data.data.totalItemNum
               })
            },
             // 竞品标签分页
            handleCurrentChange2(val){
                    this.cur_page2 = val;
                    let params = {
                    pageNo:this.cur_page2,
                    pageSize:this.ps2,
                    productsName:this.productsName    
                    }
                competingproductspage(params).then(res=>{
                    let  data = res.data
                        if(data.code != "A000000"){
                            this.$message.error(data.message)
                            return
                        } 
                    this.products = data.data.data
                    this.total2 = data.data.totalItemNum
                });
            },
            createProductsName(){
                    this.form2 = {}
                    this.newNnames = true
            },
            saveNames(){
                let pars  = {
                    productsName: this.form2.productsName,
              }
               competingproducts(pars).then(res=>{
                   console.log(res)
                     let  data = res.data
                    if(data.code != "A000000"){
                        this.$message.error(data.message)
                        return
                    }
                    this.newNnames = false
                    this.getProductss();
               })
            },
            handleDelete2(index,row){
                    this.delNames = true
                    this.ids = row
            },
            // 确定删除
            deleteNames(){
                    let  newChild = {
                       id:this.ids.id
                    }
                   competingproductsDel(newChild).then(res=>{
                        console.log(res)
                        this.getProductss()
                        this.delNames = false;
                    })
            },
            // 竞品标签搜索
            searchTitle(){
                this.$refs.pagination.lastEmittedPage = 1
                this.cur_page2 = 1;
                let par  = {
                    pageNo:this.cur_page2,
                    pageSize:this.ps2,
                    name:this.productsName    
                    
              }
              competingproductspage(par).then(res=>{
                   console.log(res)
                     let  data = res.data
                    if(data.code != "A000000"){
                        this.$message.error(data.message)
                        return
                    }
                   this.products = data.data.data
                   this.total2 = data.data.totalItemNum
               })
            }, 
              // 应用包名列表
           getTableAppp() {
              let par  = {
                    pageNo:this.cur_page3,
                    pageSize:this.ps3
              }
               apppkgblacklistspage(par).then(res=>{
                   console.log(res)
                     let  data = res.data
                    if(data.code != "A000000"){
                        this.$message.error(data.message)
                        return
                    }
                   this.tableAppp = data.data.data
                   this.total3 = data.data.totalItemNum
               })
            },
            // 应用包名搜索
            searchApp(){
                this.$refs.pagination.lastEmittedPage = 1
                this.cur_page3 = 1;
                let par  = {
                    pageNo:this.cur_page3,
                    pageSize:this.ps3,
                    name:this.select_word    
                    
              }
              apppkgblacklistspage(par).then(res=>{
                   console.log(res)
                     let  data = res.data
                    if(data.code != "A000000"){
                        this.$message.error(data.message)
                        return
                    }
                   this.tableAppp = data.data.data
                   this.total3 = data.data.totalItemNum
               })
            },
            // 应用包名分页
            handleCurrentChange3(val){
                    this.cur_page3 = val;
                    let params = {
                        pageNo:this.cur_page3,
                        pageSize:this.ps3,
                        name:this.select_word    
                    }
                apppkgblacklistspage(params).then(res=>{
                    let  data = res.data
                        if(data.code != "A000000"){
                            this.$message.error(data.message)
                            return
                        } 
                    this.tableAppp = data.data.data
                    this.total3 = data.data.totalItemNum
                });
            },
            handleDelete3(index,row){
                    this.AppVisible = true
                    this.ids = row
            },
                // 确定删除
            deleteApp(){
                    let  newChild = {
                       id:this.ids.id
                    }
                   appblanklistsDel(newChild).then(res=>{
                        console.log(res)
                        this.getTableAppp()
                        this.AppVisible = false;
                    })
            },
            // 新建
            createNames(){
                this.newApply = true
                this.state1 = ''
                this.dynamicTags = []
            },
            saveApply(){
                if(this.dynamicTags.length <= 0){
                    this.$message('应用不能为空')
                    return;
                }
                Newapppkgblacklists(this.dynamicTags).then(res=>{
                    let data = res.data;
                    if(data.code != 'A000000') {
                        this.$message.error(data.message);
                        return;
                    }
                    this.getTableAppp()
                    this.newApply = false;
                })
            },
             // 模糊搜索应用包名列表
            querySearch(queryString, cb) {
            let params = {
                    name:this.state1,
                    }
            apppkgsselect(params).then(res=>{
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
                console.log(item)
                let params = {
                    appPkgId:item.id,
                    name:item.pkgName,
                    appName:item.appName
                }
                this.dynamicTags.push(params)
            },
            handleClose(tag) {
                console.log(tag)
                this.dynamicTags.splice(this.dynamicTags.indexOf(tag), 1);
            },
            Geeterr(){
                this.newTree = true  
            },
            saveTree(){
                this.newTree = false  
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
  width: 300px;
  display: inline-block;
}
.del-dialog-cnt {
  font-size: 16px;
  text-align: center;
}
.border_{
      padding-top: 10px;
}
.div{
  padding: 20px;

}

</style>
<style>
    .el-form-item__content{
        margin-left:''
    }
     .custom-tree-node {
    flex: 1;
    display: flex;
    align-items: center;
    justify-content: space-between;
    font-size: 14px;
    padding-right: 8px;
  }
</style>



