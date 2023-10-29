<template>
    <div class="table">
        <div class="container">
              <div class="handle-box">
                      <el-input v-model="select_word" placeholder="请输入图书分级名称" class="handle-input mr10"></el-input>
                      <el-button type="primary" icon="search" @click="search">搜索</el-button>
                      <el-button type="primary" icon="search" @click="create">新建图书分级</el-button>
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
      

        </div>

      <!-- 编辑弹出框 -->
        <el-dialog title="编辑" :visible.sync="editVisible" width="40%">
            <el-form ref="form" :model="form" label-width="25%">
                <el-form-item label="类别名称">
                    <el-input v-model="form.name"></el-input>
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
            <el-form ref="form" :model="form" label-width="25%">
                <el-form-item label="类别名称">
                    <el-input v-model="form.name"></el-input>
                </el-form-item>
                <el-form-item label="类别描述">
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
      
  


        
    </div>
</template>

<script>
import { industrysselect,industrys,industrysDelete,apppkgspage,apppkgs,apppkgsget,apppkgsDelet } from "@/api/Api.js";
import { bookcategorysselect,bookcategorys,bookcategorysDel} from "@/api/Api.js";

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
      ps:2,
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
        // this.getData2();
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
               bookcategorysselect().then(res=>{
                   console.log(res)
                   data = res.data.data
                   this.data5 = data
                //    console.log(this.data5)
               })
            },
       renderContent(h, { node, data, store }) {
                return (
                <span class="custom-tree-node">
                    <span>{data.name}</span>
                    <span>
                    <el-button size="mini" type="text" on-click={ () => this.append(node, data) } icon="el-icon-plus"></el-button>
                    <el-button size="mini" type="text" on-click={ () => this.edit(node, data) } icon="el-icon-edit"></el-button>
                    <el-button size="mini" type="text" on-click={ () => this.remove(node, data) } icon="el-icon-minus"></el-button>
                    </span>
                </span>);
            },
            // 新建节点
            create(){
                 this.form ={}
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
                        description : this.form.description,
                        parentId: this.node.id
                    }
                    // console.log(newChild)
                   bookcategorys(newChild).then(res=>{
                        console.log(res)
                        this.getData()
                        this.newVisible = false;
                    })
                 }else{
                     let  Child = {
                        name : this.form.name,
                        description : this.form.description,
                    }
                   bookcategorys(Child).then(res=>{
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
                   bookcategorysDel(newChild).then(res=>{
                        console.log(res)
                        this.getData()
                        this.delVisible = false;
                    })
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
                        description : this.form.description,
                        id: this.id.id
                    }
                 console.log(newChild)
                   bookcategorys(newChild).then(res=>{
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
                   bookcategorysselect(newChild).then(res=>{
                        if (res.data.code != "A000000") {
                            this.$message.error(res.data.message);
                            return;
                            }
                         data = res.data.data
                         this.data5 = data
                    })   
            },
           
            saveNames(){
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
   
     .custom-tree-node {
    flex: 1;
    display: flex;
    align-items: center;
    justify-content: space-between;
    font-size: 14px;
    padding-right: 8px;
  }
</style>



