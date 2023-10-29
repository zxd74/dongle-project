<template>
    <div class="table">
        <div class="container">
            <el-tabs v-model="activeName">
                    <el-tab-pane label="版本" name="first">
                        <div class="handle-box">
                                <el-input v-model="select_word" placeholder="请输入版本号" class="handle-input mr10"></el-input>
                                <el-button type="primary" icon="search" @click="search">搜索</el-button>
                                <el-button type="primary" icon="search" @click="create">新建版本</el-button>
                         </div>
                        <el-table :data="Data" border style="width: 100%" ref="multipleTable">
                                <el-table-column prop="id" label="版本id"   align="center">
                                </el-table-column>
                                <el-table-column prop="name" label="版本号" align="center" >
                                </el-table-column>
                                <!-- <el-table-column label="操作"   align="center">
                                    <template slot-scope="scope">
                                        <el-button size="small" type="danger" @click="remove(scope.$index, scope.row)">删除</el-button>
                                    </template>
                                </el-table-column> -->
                        </el-table>
                         <div class="pagination">
                            <el-pagination @current-change="handleCurrentChange1" 
                            layout="total,prev, pager, next,jumper" 
                            :total="total1" 
                            :current-page="cur_page1" 
                            :page-size="ps1"
                            ref="pagination"
                            >
                            </el-pagination>
                        </div>
                    </el-tab-pane>
                    <el-tab-pane label="渠道" name="second">
                        <div class="handle-box">
                            <el-input v-model="select_number" placeholder="请输入渠道号" style="width: 200px"></el-input>
                            <el-input v-model="select_AppName" placeholder="请输入渠道名称" style="width: 200px"></el-input>
                            <el-input v-model="parentName" placeholder="请输入一级渠道号" style="width: 200px"></el-input>
                            <el-select v-model="type" placeholder="请选择渠道等级" style="width: 200px">
                                <el-option
                                    v-for="item in options_"
                                    :key="item.value"
                                    :label="item.label"
                                    :value="item.value">
                                </el-option>
                            </el-select>
                            <el-button type="primary" icon="search" @click="search2">搜索</el-button>
                            <el-button type="primary" icon="search" @click="createNames">新建渠道</el-button>
                        </div>         
                        <el-table :data="tableData" border style="width: 100%" ref="multipleTable">
                            <!-- <el-table-column prop="id" label="序号"   align="center">
                            </el-table-column> -->
                            <el-table-column prop="name" label="渠道号" align="center" >
                            </el-table-column>
                            <el-table-column prop="cname" label="渠道名称" align="center"  >
                            </el-table-column>
                            <el-table-column prop="parentName" label="一级渠道号" align="center" >
                            </el-table-column>
                            <!-- <el-table-column label="操作"   align="center">
                                <template slot-scope="scope">
                                    <el-button size="small" type="danger" @click="handleDelete2(scope.$index, scope.row)">删除</el-button>
                                </template>
                            </el-table-column> -->
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

  
        <!-- 新建弹出框 -->
        <el-dialog title="新建" :visible.sync="newVisible" width="40%">
            <el-form ref="form" :model="form" label-width="80px">
                <el-form-item label="版本号">
                    <el-input v-model="form.name" placeholder="请用逗号分隔"></el-input>
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
            <!-- 新建渠道-->
        <el-dialog title="新建" :visible.sync="newNnames" width="40%">
            <el-form ref="form" :model="formPkgs" label-width="100px" :label-position="labelPosition">
                <el-form-item label="模板下载">
                    <a href="https://iwanvi.oss-cn-beijing.aliyuncs.com/document/channelTemp/%E6%B8%A0%E9%81%93%E5%AF%BC%E5%85%A5%E6%A8%A1%E6%9D%BF.xlsx">
                         <el-button type="primary" >下载</el-button>
                    </a>
                </el-form-item>
                <el-form-item label="上传渠道号:">
                    <el-input v-model="formPkgs.QdNumber" style="width:50%"></el-input>
                    <el-upload
                        class="upload-demo"                   
                        :action="upload" 
                        name="file"
                        :headers="myHeaders"  
                        :on-success="handleAvatar"
                    >
                     <el-button slot="trigger" size="small" type="primary">上传文件</el-button>
                    </el-upload>
                </el-form-item>
                <!-- <el-form-item label="渠道号">
                    <el-input v-model="formPkgs.pkgName"></el-input>
                </el-form-item> -->
            </el-form>
            <!-- <span slot="footer" class="dialog-footer">
                <el-button @click="newNnames = false">取 消</el-button>
                <el-button type="primary" @click="saveNames">确 定</el-button>
            </span> -->
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

import { industrysselect,industrys,industrysDelete,apppkgspage,apppkgs,apppkgsget,apppkgsDelet } from "@/api/Api.js";
import { appversionspage,appversions,appversionsDel,batchImport,
appchannelspage,appchannels,appchannelsDel,appversionspagePage
 } from "@/api/Api.js";

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
      parentName:'',
      type:'',
      labelPosition: 'left',
      activeName: 'first',
      props: {
            label: 'name',
            children: 'children',
        },
      data5:'',
      data5: JSON.parse(JSON.stringify(data)),
      tableData: [],
      Data:[],
      cur_page: 1,
      total: 1,
      ps:10,
      total1:1,
      cur_page1:1,
      ps1:10,
      
      upload:batchImport,
      select_word: '',
      select_AppName:'',
      select_number:'',
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
        QdNumber:'',
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
      options_: [
         {
          value: 0,
          label: "不限"
        },
        {
          value: 2,
          label: "二级渠道"
        },
        {
          value: 1,
          label: "一级渠道"
        },
      ],
        myHeaders: {token: localStorage.getItem('t')}
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
        // 获取版本列表
        getData() {
                 let par  = {
                    pageNo:this.cur_page1,
                    pageSize:this.ps1
              }
               appversionspage(par).then(res=>{
                    let  data = res.data
                    if(data.code != "A000000"){
                        this.$message.error(data.message)
                        return
                    }
                    this.Data = data.data.data
                    this.total1 =data.data.totalItemNum
               })
            },
            search(){
                    this.$refs.pagination.lastEmittedPage = 1
                    this.cur_page1 = 1;
                let  newChild = {
                    name:this.select_word
                }
                appversionspagePage(newChild).then(res=>{
                       let  data = res.data
                    if (data.code != "A000000") {
                        this.$message.error(data.message);
                        return;
                        }
                        this.Data = data.data.data
                        this.total1 = data.data.totalItemNum
                })   
            },
                  // 分页导航
            handleCurrentChange1(val) {
                    this.cur_page1 = val;
                    let params = {
                        pageNo:this.cur_page1,
                        pageSize:this.ps1
                    }
                appversionspage(params).then(res=>{
                    let  data = res.data
                        if(data.code != "A000000"){
                            this.$message.error(data.message)
                            return
                        } 
                    this.Data = data.data.data
                    this.total1 = data.data.totalItemNum
                });
            },
                // 新建版本
            create(){
                 this.form ={}
                 this.newVisible = true
            },
            saveNews(){
                // let par = /^\-?[1-9]\d*.?\d*$/g;
                //     if(!par.test(this.form.name)){
                //         return;
                //     }
                    let  params = {
                        name : this.form.name,
                    }
                   appversions(params).then(res=>{
                        let  data = res.data
                        if(data.code != "A000000"){
                            this.$message.error(data.message)
                            return
                        } 
                        this.getData()
                        this.newVisible = false;
                    })
            },
             //删除 
            remove(node,data) {
                this.node= node;
                this.data = data;
                this.delVisible = true 
            },
             // 确定删除
            deleteRow(){
                    let  newChild = {
                       id:this.data.id
                    }
                   appversionsDel(newChild).then(res=>{
                        this.getData()
                        this.delVisible = false;
                    })
            },
        // 获取渠道列表
        getData2() {
              let par  = {
                    pageNo:this.cur_page,
                    pageSize:this.ps
              }
               appchannelspage(par).then(res=>{
                    let  data = res.data
                    if(data.code != "A000000"){
                        this.$message.error(data.message)
                        return
                    }
                    this.tableData = data.data.data
                    this.total =data.data.totalItemNum
               })
            },
            // 新建
            createNames(){
                this.formPkgs = {}
                this.newNnames = true
            },
            saveNames(){
                     let  params = {
                        name : this.formPkgs.pkgName,
                        cname : this.formPkgs.appName,
                    }
                   appchannels(params).then(res=>{
                       let  data = res.data
                        if(data.code != "A000000"){
                            this.$message.error(data.message)
                            return
                        } 
                        this.getData2()
                        this.newNnames = false;
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
                   appchannelsDel(params).then(res=>{
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
                    name:this.select_number,
                    cname:this.select_AppName,
                    parentName:this.parentName,
                    type:this.type
              }
               appchannelspage(par).then(res=>{
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
                    pageNo:this.cur_page,
                    pageSize:this.ps
                    }
                appchannelspage(params).then(res=>{
                    let  data = res.data
                        if(data.code != "A000000"){
                            this.$message.error(data.message)
                            return
                        } 
                    this.tableData = data.data.data
                    this.total = data.data.totalItemNum

                });
            }, 
            handleAvatar(res, file) {

                let data = res;
                if (data.code != 'A000000') {
                    this.$message.error(data.message);
                    return;
                } else {
                    this.$message('上传成功')
                    this.newNnames = false
                    this.getData2()
                }
                this.$forceUpdate()
            }
  
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
    .mr10{
        width: 200px;
    }
     .custom-tree-node {
    flex: 1;
    display: flex;
    align-items: center;
    justify-content: space-between;
    font-size: 14px;
    padding-right: 8px;
  }
      .el-upload--text {
        border: 0px dashed #d9d9d9 !important;
        width: 80px !important;
        height: 32px !important;
    }

    .upload-demo {
        display: inline-flex;
    }

    .el-upload-list {
        display: none;
    }
</style>



