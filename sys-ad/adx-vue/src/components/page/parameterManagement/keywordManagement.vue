<template>
    <div class="table">
        <div class="container">
            <el-tabs v-model="activeName">
                    <el-tab-pane label="行业标签" name="first">
                        <div class="handle-box">
                                <el-input v-model="select_word" placeholder="请输入行业标签" class="handle-input mr10"></el-input>
                                <el-button type="primary" icon="search" @click="search">搜索</el-button>
                                <el-button type="primary" icon="search" @click="create">新建主节点</el-button>
                         </div>
                         <div class="containerCenter">
                                <div class="custom-tree-container">
                                    <div class="block">
                                        <!-- <p>使用 render-content</p> -->
                                        <el-tree
                                        :data="data5"
                                        show-checkbox
                                        :props="props"
                                        node-key="id"
                                        ref="tree"
                                        default-expand-all
                                        :expand-on-click-node="false"
                                        :render-content="renderContent">
                                        </el-tree>
                                    </div>
                                </div>
                        </div>
                    </el-tab-pane>
                    <el-tab-pane label="应用包名" name="second">
                        <div class="handle-box">
                            <el-input v-model="select_AppName" placeholder="请输入应用名称或包名" class="handle-input mr10"></el-input>
                            <el-button type="primary" icon="search" @click="search2">搜索</el-button>
                            <el-button type="primary" icon="search" @click="createNames">新建应用名称或包名</el-button>
                        </div>         
                        <el-table :data="tableData" border style="width: 100%" ref="multipleTable">
                            <el-table-column prop="id" label="序号"   align="center">
                            </el-table-column>
                            <el-table-column prop="appName" label="应用名称" align="center" >
                            </el-table-column>
                            <el-table-column prop="pkgName" label="应用包名" align="center"  >
                            </el-table-column>
                            <el-table-column prop="blanklist" label="是否已添加到黑名单" align="center" :formatter=formattere>
                            </el-table-column>
                            <el-table-column label="操作"   align="center">
                                <template slot-scope="scope">
                                    <el-button size="small" type="success" @click="handleEdit2(scope.$index, scope.row)">编辑</el-button>
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
                    </el-tab-pane>
             </el-tabs>
        </div>

      <!-- 编辑弹出框 -->
        <el-dialog title="编辑" :visible.sync="editVisible" width="40%">
            <el-form ref="form" :model="form" label-width="25%"   :label-position="labelPosition">
                <el-form-item label="* 类别名称">
                    <el-input v-model="form.name"></el-input>
                </el-form-item>
                <el-form-item label="* 是否添加到黑名单">
                    <el-radio v-model="form.blanklist" :label="true">是</el-radio>
                    <el-radio v-model="form.blanklist" :label="false">否</el-radio>
                </el-form-item>
                <el-form-item label="备注">
                    <el-input v-model="form.description"></el-input>
                </el-form-item>
            </el-form>
            <span slot="footer" class="dialog-footer">
                <el-button @click="editVisible = false">取 消</el-button>
                <el-button type="primary" @click="saveEdit">确 定</el-button>
            </span>
        </el-dialog>
        <!-- 新建弹出框 -->
        <el-dialog title="新建" :visible.sync="newVisible" width="40%">
            <el-form ref="form" :model="form" label-width="25%"  :label-position="labelPosition">
                <el-form-item label="* 类别名称">
                    <el-input v-model="form.name"></el-input>
                </el-form-item>
                <el-form-item label="* 是否添加到黑名单">
                    <el-radio v-model="form.blanklist" :label="true" @change="seach">是</el-radio>
                    <el-radio v-model="form.blanklist" :label="false">否</el-radio>
                </el-form-item>
                <el-form-item label="备注">
                    <el-input v-model="form.description"></el-input>
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
            <!-- 新建应用名称或包名弹出框 -->
        <el-dialog title="新建" :visible.sync="newNnames" width="40%">
            <el-form ref="form" :model="formPkgs" label-width="80px">
                <el-form-item label="应用名称">
                    <el-input v-model="formPkgs.appName"></el-input>
                </el-form-item>
                <el-form-item label="应用包名">
                    <el-input v-model="formPkgs.pkgName"></el-input>
                </el-form-item>
                <el-form-item label="是否添加到黑名单">
                    <el-radio v-model="formPkgs.blanklist" :label="true"  @change="refresh">是</el-radio>
                    <el-radio v-model="formPkgs.blanklist" :label="false"  @change="refresh">否</el-radio>
                </el-form-item>
                <!-- <el-form-item label="备注">
                    <el-input v-model="form.description"></el-input>
                </el-form-item> -->
            </el-form>
            <span slot="footer" class="dialog-footer">
                <el-button @click="newNnames = false">取 消</el-button>
                <el-button type="primary" @click="saveNames">确 定</el-button>
            </span>
        </el-dialog>

                 <!-- 编辑应用名称或包名弹出框 -->
        <el-dialog title="新建" :visible.sync="editNnames" width="40%">
            <el-form ref="form" :model="formPkgs" label-width="80px">
               <el-form-item label="应用名称">
                    <el-input v-model="formPkgs.appName"></el-input>
                </el-form-item>
                <el-form-item label="应用包名">
                    <el-input v-model="formPkgs.pkgName"></el-input>
                </el-form-item>
                <el-form-item label="是否添加到黑名单">
                    <el-radio v-model="formPkgs.blanklist" :label="true">是</el-radio>
                    <el-radio v-model="formPkgs.blanklist" :label="false">否</el-radio>
                </el-form-item>
            </el-form>
            <span slot="footer" class="dialog-footer">
                <el-button @click="editNnames = false">取 消</el-button>
                <el-button type="primary" @click="saveedit">确 定</el-button>
            </span>
        </el-dialog>

           <!-- 删除应用名称或包名提示框 -->
        <el-dialog title="提示" :visible.sync="delNames" width="300px" center>
            <div class="del-dialog-cnt">删除不可恢复，是否确定删除？</div>
            <span slot="footer" class="dialog-footer">
                <el-button @click="delNames = false">取 消</el-button>
                <el-button type="primary" @click="deleteNames">确 定</el-button>
            </span>
        </el-dialog>


        
    </div>
</template>

<script>


import { UserList } from "@/api/Api.js";
import { UserAdd } from "@/api/Api.js";
import { UserUpdate } from "@/api/Api.js";
import { UserDelete } from "@/api/Api.js";
import { UserGetAuth } from "@/api/Api.js";
import { GetallFlowsource } from "@/api/Api.js";


import { industrysselect,industrys,industrysDelete,apppkgspage,apppkgs,apppkgsget,apppkgsDelet } from "@/api/Api.js";
let id = 1000;
var currentNode = null;
var data = [
                // {
                //             "id": 2,
                //             "name": "测试主菜单1",
                //             "pid": 0,
                //             "url": "/menu/son1",
                //             "state": 1,
                //             "createTime": "2019-01-28T07:36:56.000+0000",
                //             "updateTime": "2019-01-28T08:01:44.000+0000",
                //             "child": [
                //                 {
                //                 "id": 3,
                //                 "name": "测试子菜单1-1",
                //                 "pid": 2,
                //                 "url": "/menu/son1-1",
                //                 "state": 1,
                //                 "createTime": "2019-01-28T09:23:20.000+0000",
                //                 "updateTime": null,
                //                 "child": []
                //                 }
                //             ]
                //     }
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
      labelPosition: 'left',
      activeName: 'first',
      props: {
            label: 'name',
            children: 'children',
        },
      data5:'',
      data5: JSON.parse(JSON.stringify(data)),
      tableData: [],
      cur_page: 1,
      total: 1,
      ps:10,
      select_word: "",
      select_AppName:'',
      is_search: false,
      editVisible: false,
      delVisible: false,
      newVisible: false,
      template_dialog: false,
      currentRow: null,
      newNnames:false,
      editNnames:false,
      delNames:false,

      form: {
        description:'',
        name: "",
        blacklist:'',
      },
     formPkgs: {
        appName:'',
        pkgName: "",
        blanklist:'',
        id:'',
      },
      rules2: {
          userName: [
            { required: true, message: '请输入用户名', trigger: 'blur' },
          ],
           password: [
            { required: true, message: '请输入用户名', trigger: 'blur' },
          ],
          checkPass: [
             { validator: validatePass2,message: '两次输入不一致', trigger: 'blur' }
          ],
        },
    };
  },
  created() {
        this.getData();
        this.getData2();
  },

  methods: {
         formattere(row, column) {
            if(row.blanklist == true) {
                return "是";
            } else if(row.blanklist == false){
                return "否";
            }
        },
        // 获取tree
        getData() {
               industrysselect().then(res=>{
                   console.log(res)
                   data = res.data.data
                   this.data5 = data
                //    console.log(this.data5)
               })
            },
        // 获取应用列表
        getData2() {
              let par  = {
                    pageNo:this.cur_page,
                    pageSize:this.ps
              }
               apppkgspage(par).then(res=>{
                    console.log(res)
                    let  data = res.data
                    if(data.code != "A000000"){
                        this.$message.error(data.message)
                        return
                    }
                    this.tableData = data.data.data
                    this.total =data.data.totalItemNum
               })
            },
               // 分页导航
            handleCurrentChange(val) {
                    this.cur_page = val;
                    let params = {
                    // putName:this.select_name,
                    // oid:uuid.get(this.select_word),
                    // status:this.value,
                    pageNo:this.cur_page,
                    pageSize:this.ps
                    }
                apppkgspage(params).then(res=>{
                    let  data = res.data
                        if(data.code != "A000000"){
                            this.$message.error(data.message)
                            return
                        } 
                    this.tableData = data.data.data
                    this.total = data.data.totalItemNum
                // console.log(res)
                });
            },
       renderContent(h, { node, data, store }) {
                return (
                <span class="custom-tree-node">
                    <span>{data.name}</span>
                    <span>
                    <el-button size="mini" type="text" on-click={ () => this.append(node, data) } icon="el-icon-plus"></el-button>
                    <el-button size="mini" type="text" on-click={ () => this.edit(node, data) } icon="el-icon-edit"></el-button>
                    </span>
                </span>);
            },
            seach(){
                   this.$forceUpdate() 
            },
            // 新建节点
            create(){
                 this.form ={}
                 this.form.blanklist = false
                 this.newVisible = true
            },
             // 新建子节点
            append(node,data) {
                this.node= data;
                currentNode = node;
                this.form ={}
                this.newVisible = true
            },
             saveNews(){
                //  console.log(this.node)
                 if(this.node){
                    let  newChild = {
                        name : this.form.name,
                        blanklist:this.form.blanklist,
                        description : this.form.description,
                        parentId: this.node.id
                    }
                    // console.log(newChild)
                   industrys(newChild).then(res=>{
                        console.log(res)
                        this.getData()
                        this.newVisible = false;
                    })
                 }else{
                     let  Child = {
                        name : this.form.name,
                        blanklist:this.form.blanklist,
                        description : this.form.description,
                    }
                   industrys(Child).then(res=>{
                       console.log(res)
                       let  data = res.data
                       if(data.code != "A000000"){
                           this.$message.error(data.message)
                           return
                       }
                        this.getData()
                        this.newVisible = false;
                    })
  
                 } 
            },
             //删除 
            remove(node,data) {
                this.node= node;
                this.data = data;
                console.log(this.data)
                this.delVisible = true 
            },
             // 确定删除
            deleteRow(){
                    let  newChild = {
                       id:this.data.id
                    }
                   industrysDelete(newChild).then(res=>{
                        console.log(res)
                        this.getData()
                        this.delVisible = false;
                    })
                //  const parent = this.node.parent;
                //  const children = parent.data.child || parent.data;
                //  const index = children.findIndex(d => d.pid === this.data.pid);
                //  children.splice(index, 1);
                //  console.log(this.data)
                //  this.delVisible = false 
                //  this.node = null;
                //  this.data = null;
            },
             // 编辑
            edit(node,data){
                console.log(data)
                this.node = node;
                const currentNode = data
                console.log(currentNode)
                this.form = JSON.parse(JSON.stringify(currentNode));
                this.id = currentNode
                this.editVisible = true
            },
            // 保存编辑
            saveEdit() {
                  let  newChild = {
                        name : this.form.name,
                        blanklist:this.form.blanklist,
                        description : this.form.description,
                        id: this.id.id
                    }
                 console.log(newChild)
                   industrys(newChild).then(res=>{
                        console.log(res)
                        this.getData()
                        this.editVisible = false;
                    })
                //    let  newChild = {
                //         name : this.form.name,
                //         url : this.form.url,
                //     }
                //     console.log(this.node)
                //     this.node.data = newChild
                //     this.editVisible = false

            },
            search(){
                    let  newChild = {
                        name:this.select_word,
                    }
                   industrysselect(newChild).then(res=>{
                        if (res.data.code != "A000000") {
                            this.$message.error(res.data.message);
                            return;
                            }
                         data = res.data.data
                         this.data5 = data
                    })   
            },
            createNames(){
                this.formPkgs = {}
                this.newNnames = true
                this.formPkgs.blanklist = false
            },
            saveNames(){
                if(!this.formPkgs.appName){
                      this.$message.error('应用名称不能为空');
                    return;
                }
                if(!this.formPkgs.pkgName){
                    this.$message.error('应用包名不能为空');
                    return;
                }
                     let  params = {
                        appName : this.formPkgs.appName,
                        blanklist:this.formPkgs.blanklist,
                        pkgName : this.formPkgs.pkgName,
                    }
                   apppkgs(params).then(res=>{
                        console.log(res)
                        this.getData2()
                        this.newNnames = false;
                    })
            },
            refresh(){
                this.$forceUpdate()
            },
            handleEdit2(index,row){
                  let  params = {
                      id:row.id
                    }
                   apppkgsget(params).then(res=>{
                        console.log(res)
                        let data = res.data
                        if (data.code != "A000000") {
                            this.$message.error(data.message);
                            return;
                        }
                        this.formPkgs = data.data
                        this.editNnames = true;
                    })
            },
            saveedit(){
                     let  params = {
                        id:this.formPkgs.id,
                        appName : this.formPkgs.appName,
                        blanklist:this.formPkgs.blanklist,
                        pkgName : this.formPkgs.pkgName,
                    }
                   apppkgs(params).then(res=>{
                        console.log(res)
                        let data = res.data
                        if (data.code != "A000000") {
                            this.$message.error(data.message);
                            return;
                        }
                        this.getData2()
                        this.editNnames = false;
                    })
            },
            handleDelete2(index,row){
                    this.delNames = true
                    this.row = row;
            },
            deleteNames(){
                    let  params = {
                        id:this.row.id,
                    }
                   apppkgsDelet(params).then(res=>{
                        console.log(res)
                        let data = res.data
                        if (data.code != "A000000") {
                            this.$message.error(data.message);
                            return;
                        }
                        this.getData2()
                        this.delNames = false;
                    })
            },
               search2(){
                    this.$refs.pagination.lastEmittedPage = 1
                    this.cur_page = 1;
                    let par  = {
                    pageNo:this.cur_page,
                    pageSize:this.ps,
                    name:this.select_AppName
              }
               apppkgspage(par).then(res=>{
                    console.log(res)
                    let  data = res.data
                    if(data.code != "A000000"){
                        this.$message.error(data.message)
                        return
                    }
                    this.tableData = data.data.data
                    this.total =data.data.totalItemNum
               }) 
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
.containerCenter{
    width: 50%;
    border: 1px slategrey;
    border-style:dotted;
    padding: 10px;
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



