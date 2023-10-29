<template>
    <div class="table">
        <div class="container">
            <div class="handle-box">
                <!-- <el-input v-model="select_word" placeholder="请输入广告模板名称" class="handle-input mr10"></el-input> -->
                <el-autocomplete
                    class="inline-input"
                    v-model="select_word"
                    value-key="name"
                    :fetch-suggestions="querySearchName"
                    placeholder="请输入广告模板名称"
                    @select="handleSelectName" 
                ></el-autocomplete>
                <el-select v-model="matierialType" placeholder="素材类型" class="handle-input">
                  <el-option
                    v-for="item in optionType"
                    :key="item.value"
                    :label="item.label"
                    :value="item.value">
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
                 <el-button type="primary" icon="search" @click="create"  v-if="this.readonly !=1">新建广告模板</el-button>
            </div>
            <el-table :data="tableData" border style="width: 100%" ref="multipleTable">
                <el-table-column prop="id" label="ID"  width="100" align="center">
                </el-table-column>
                <el-table-column prop="name" label="广告模板名称"  width="" align="center">
                </el-table-column>
                <!-- <el-table-column prop="materialType" label="类型"  width="" align="center">
                </el-table-column> -->
                <el-table-column prop="materialName" label="素材类型"  width="" align="center">
                </el-table-column>
                <el-table-column prop="positionNum" label="在用广告位数量"  width="" align="center">
                </el-table-column>
                <el-table-column  label="状态"  width="" align="center"  v-if="this.readonly !=1">
                  <template slot-scope="scope">
                     <el-switch v-model="scope.row.status"  :active-value=1 :inactive-value=0  @change="changeStatus($event,scope.row)"></el-switch>
                  </template>
                </el-table-column>
                <el-table-column label="操作" width="150" align="center">
                    <template slot-scope="scope">
                        <el-button size="small" type="success" @click="handleEdit(scope.$index, scope.row)">查看</el-button>
                        <!-- <el-button size="small" type="danger" @click="handleDelete(scope.$index, scope.row)">删除</el-button> -->
                        <!-- <el-button size="small" type="success" @click="handAD(scope.$index, scope.row)">复制</el-button> -->
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
        <el-dialog title="编辑" :visible.sync="editVisible" width="50%">
            <el-form ref="form" :model="form" label-width="100px" :label-position="labelPosition">
                <el-form-item label="* 模板名称:">
                    <el-input v-model="form.name" class="handle-input"></el-input>
                </el-form-item>
                <el-form-item label="* 模版类型:">
                   <el-radio-group v-model="form.type" @change="seachTelepalat" disabled>
                    <el-radio :label="7">信息流</el-radio>
                    <el-radio :label="217">激励视频</el-radio>
                  </el-radio-group>
                </el-form-item>
                <div v-if="this.form.type == 7||this.form.type == 217">
                   <!-- <div  v-for="(video,index) in form.moduleList" :key="index"> -->
                        <el-form-item label="标题:" v-if="this.form.type != 217">
                              限 <el-input style="width:70px" v-model="form.moduleList[0].wordLimit" size="mini"  type="number"></el-input><span class='font'>字</span>
                        </el-form-item> 
                        <el-form-item label="描述:" v-if="this.form.type != 217">
                              限 <el-input style="width:70px" v-model="form.moduleList[1].wordLimit" size="mini"  type="number" ></el-input><span class='font'>字</span>
                        </el-form-item>
                        <el-form-item label="按钮文案:" v-if="form.moduleList[2] && this.form.type != 217">
                              限 <el-input style="width:70px" v-model="form.moduleList[7].wordLimit" size="mini"  type="number"></el-input><span class='font'>字</span>
                        </el-form-item>
                        <el-form-item label="头像:" v-if="this.form.type != 217">
                              宽 <el-input style="width:70px" v-model="form.moduleList[2].width" size="mini"  type="number" ></el-input><span class='font'>PX</span>
                              高 <el-input style="width:70px" v-model="form.moduleList[2].height" size="mini"  type="number" ></el-input><span class='font'>px</span>
                        </el-form-item>
                        <el-form-item label="视频:"  v-if="this.form.type == 217">
                              宽 <el-input style="width:120px" v-model="form.moduleList[3].width" size="mini" type="number" ></el-input><span class='font'>PX</span>
                              高 <el-input style="width:120px" v-model="form.moduleList[3].height" size="mini" type="number" ></el-input><span class='font'>px</span>
                              时长 <el-input style="width:120px" v-model="form.moduleList[3].duration" size="mini" type="number" ></el-input><span class='font'>S</span>
                        </el-form-item>
                        <el-form-item label="主图片1:">
                              宽 <el-input style="width:120px" v-model="form.moduleList[4].width" size="mini" type="number" ></el-input><span class='font'>PX</span>
                              高 <el-input style="width:120px" v-model="form.moduleList[4].height" size="mini" type="number" ></el-input><span class='font'>px</span>
                        </el-form-item>
                        <el-form-item label="主图片2:" v-if="form.moduleList[5] && this.form.type != 217">
                              宽 <el-input style="width:120px" v-model="form.moduleList[5].width" size="mini" type="number" ></el-input><span class='font'>PX</span>
                              高 <el-input style="width:120px" v-model="form.moduleList[5].height" size="mini" type="number"></el-input><span class='font'>px</span>
                         <!-- <el-button type="primary" icon="el-icon-minus" @click="DeleteImg(form.moduleList[5])"></el-button> -->
                        </el-form-item>
                        <el-form-item label="主图片3:" v-if="form.moduleList[6] && this.form.type != 217">
                              宽 <el-input style="width:120px" v-model="form.moduleList[6].width" size="mini" type="number"></el-input><span class='font'>PX</span>
                              高 <el-input style="width:120px" v-model="form.moduleList[6].height" size="mini" type="number"></el-input><span class='font'>px</span>
                             <!-- <el-button type="primary" icon="el-icon-minus" @click="DeleteImg(form.moduleList[6])"></el-button> -->
                        </el-form-item>
                        <!-- <el-button type="primary" icon="el-icon-plus" @click="addImg(index)" v-if="!form.moduleList[6]"></el-button> -->
                    <!-- </div> -->
                </div> 
            </el-form> 
            <span slot="footer" class="dialog-footer">
                <el-button @click="editVisible = false">取 消</el-button>
                <el-button type="primary" @click="saveEdit"  v-if="this.readonly !=1">确 定</el-button>
            </span>
        </el-dialog>

      

          <!-- 新建弹出框 -->
        <el-dialog title="新建广告模板:" :visible.sync="newVisible" width="50%">
            <el-form ref="form" :model="form" label-width="100px" :label-position="labelPosition">
                <el-form-item label="* 模板名称:">
                    <el-input v-model="form.name" class="handle-input"></el-input>
                </el-form-item>
                <el-form-item label="* 模版类型:">
                   <el-radio-group v-model="form.type">
                    <el-radio :label="7">信息流</el-radio>
                    <el-radio :label="217">激励视频</el-radio>
                  </el-radio-group>
                </el-form-item>
                <div v-if="this.form.type == 7||this.form.type == 217">
                   <!-- <div  v-for="(video,index) in form.moduleList" :key="index"> -->
                        <el-form-item label="标题:" v-if="this.form.type != 217">
                              限 <el-input style="width:70px" v-model="form.moduleList[0].wordLimit" size="mini"  type="number"></el-input><span class='font'>字</span>
                        </el-form-item> 
                        <el-form-item label="描述:" v-if="this.form.type != 217">
                              限 <el-input style="width:70px" v-model="form.moduleList[1].wordLimit" size="mini"  type="number" ></el-input><span class='font'>字</span>
                        </el-form-item>
                        <el-form-item label="按钮文案:" v-if="form.moduleList[2] && this.form.type != 217">
                              限 <el-input style="width:70px" v-model="form.moduleList[2].wordLimit" size="mini"  type="number"></el-input><span class='font'>字</span>
                        </el-form-item>
                        <el-form-item label="头像:" v-if="this.form.type != 217">
                              宽 <el-input style="width:70px" v-model="form.moduleList[3].width" size="mini"  type="number" ></el-input><span class='font'>PX</span>
                              高 <el-input style="width:70px" v-model="form.moduleList[3].height" size="mini"  type="number" ></el-input><span class='font'>px</span>
                        </el-form-item>
                        <el-form-item label="视频:"  v-if="this.form.type == 217">
                              宽 <el-input style="width:120px" v-model="form.moduleList[4].width" size="mini" type="number" ></el-input><span class='font'>PX</span>
                              高 <el-input style="width:120px" v-model="form.moduleList[4].height" size="mini" type="number" ></el-input><span class='font'>px</span>
                              时长 <el-input style="width:120px" v-model="form.moduleList[4].duration" size="mini" type="number" ></el-input><span class='font'>S</span>
                        </el-form-item>
                        <el-form-item label="主图片1:">
                              宽 <el-input style="width:120px" v-model="form.moduleList[5].width" size="mini" type="number" ></el-input><span class='font'>PX</span>
                              高 <el-input style="width:120px" v-model="form.moduleList[5].height" size="mini" type="number" ></el-input><span class='font'>px</span>
                        </el-form-item>
                        <el-form-item label="主图片2:" v-if="form.moduleList[5] && this.form.type != 217">
                              宽 <el-input style="width:120px" v-model="form.moduleList[6].width" size="mini" type="number" ></el-input><span class='font'>PX</span>
                              高 <el-input style="width:120px" v-model="form.moduleList[6].height" size="mini" type="number"></el-input><span class='font'>px</span>
                         <!-- <el-button type="primary" icon="el-icon-minus" @click="DeleteImg(form.moduleList[5])"></el-button> -->
                        </el-form-item>
                        <el-form-item label="主图片3:" v-if="form.moduleList[6] && this.form.type != 217">
                              宽 <el-input style="width:120px" v-model="form.moduleList[7].width" size="mini" type="number"></el-input><span class='font'>PX</span>
                              高 <el-input style="width:120px" v-model="form.moduleList[7].height" size="mini" type="number"></el-input><span class='font'>px</span>
                             <!-- <el-button type="primary" icon="el-icon-minus" @click="DeleteImg(form.moduleList[6])"></el-button> -->
                        </el-form-item>
                        <!-- <el-button type="primary" icon="el-icon-plus" @click="addImg(index)" v-if="!form.moduleList[6]"></el-button> -->
                    <!-- </div> -->
                </div> 
                <div  v-if="this.form.type == 217">
                     
                    <!-- <el-form-item label="素材参数:">
                         <el-input type="textarea"  v-model="form.code" size="mini" placeholder="请输入代码"></el-input>
                    </el-form-item> -->
                </div>
            </el-form> 
            <span slot="footer" class="dialog-footer">
                <el-button @click="newVisible = false">取 消</el-button>
                <el-button type="primary" @click="saveNew">确 定</el-button>
            </span>
        </el-dialog>

        <!-- 删除提示框 -->
        <!-- <el-dialog title="提示" :visible.sync="delVisible" width="300px" center>
            <div class="del-dialog-cnt">删除不可恢复，是否确定删除？</div>
            <span slot="footer" class="dialog-footer">
                <el-button @click="delVisible = false">取 消</el-button>
                <el-button type="primary" @click="deleteRow">确 定</el-button>
            </span>
        </el-dialog> -->
        
    </div>
</template>

<script>
import { templateList } from "@/api/Api.js";
import { templateAdd,templateGet,templateupdate ,templateupdateStatus,templatemodifiable } from "@/api/Api.js";

const uuid = new Map()
export default {
  name: "basetable",
  data() {
    return {
      labelPosition: 'left',
      ids:null,
      url: "./static/vuetable.json",
      tableData: [],
      cur_page: 1,
      total: 1,
      ps:10,
      select_word: "",
      select_PutName:'',
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
      status:'',
      form: {
        name:'',
        type:'',
        moduleList: [
        ],
        code:'',
        id:'',
      },
      index:5,
      idx: -1,
      flow_type: 1,
      type: 1,
      system: 1,
      Access: "API",
      display_form: 1,
      isARR:true,
      readonly:'',
      // 类型
      optionType: [
        {
          value: "",
          label: "全部素材类型"
        },
        {
          value: "1",
          label: "标题"
        },
        {
          value: "2",
          label: "描述"
        },
        {
          value: "3",
          label: "头像"
        },
         {
          value: "5",
          label: "图片"
        },
        {
          value: "4",
          label: "视频"
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
  
     
    };
  },
  created() {
        this.readonly = this.$store.state.map.get(this.$store.state.currentPath);
        this.getList();
  },
 
  methods: {
    seachTelepalat(){
        this.$forceUpdate()
    },
        // 开关
    changeStatus(val,row){
             let params = {
                id:row.id,
                status:row.status,
                name:row.name
            }
     templateupdateStatus(params).then(res=>{
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
        name:this.select_word,
        materialType:this.matierialType,
        status:this.status,
        cp:this.cur_page,
        ps:this.ps
       }
     templateList(params).then(res=>{
     let data = res.data;     
       this.tableData = data.data.data
       this.total = data.data.totalItemNum;
    });
    },
         //模糊名字
    querySearchName(queryString, cb){
        let params = {
          name:this.select_word,
          cp:1,
          ps:this.ps
          }
         templateList(params).then(res=>{
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
        // status:1,
        cp:this.cur_page,
        ps:this.ps
      }
      templateList(params).then(res=>{
     let data = res.data;     
       this.tableData = data.data.data
       this.total = data.data.totalItemNum;
      console.log(res)
      });
    },
    // 搜索
    search() {
         this.$refs.pagination.lastEmittedPage = 1
         this.cur_page = 1;
     let params = {
        name:this.select_word,
        materialType:this.matierialType,
        status:this.status,
        cp:this.cur_page,
        ps:this.ps
       }
     templateList(params).then(res=>{
     let data = res.data;     
       this.tableData = data.data.data
       this.total = data.data.totalItemNum;
    });
    },

    // 编辑
    handleEdit(index, row) {
      this.idx = index;
      this.item = row;
        let params = {
            id:row.id,
        }
        templateGet(params).then(res=>{
          console.log(res)
        let data = res.data;
          if(data.code != 'A000000') {
              this.$message.error(data.message);
              return;
          }
          // this.form.moduleList = data.data.moduleList
          // this.form.name = data.data.name
          // this.form.id = data.data.id
          this.form = data.data
          console.log(this.form)
        });
      this.editVisible = true;
    },
     // 保存编辑
    saveEdit() {
      let  pars = {
          id:this.form.id
      }
      templatemodifiable(pars).then(res=>{
        console.log(res)
         let data = res.data;
          if(data.code != 'A000000') {
              this.$message.error(data.message);
              return;
          }
          if (data.data == 1) {  
            if(this.form.type == 217)   {
                  let params = {
                    id:this.form.id,
                    name:this.form.name,
                    moduleList:this.form.moduleList,
                  }
                  console.log(this.form.moduleList)
              
                templateupdate(params).then(res=>{
                  let data = res.data;
                    if(data.code != 'A000000') {
                        this.$message.error(data.message);
                        return;
                    }
                    this.search();
                  });
                }else{
                  let params = {
                    id:this.form.id,
                    name:this.form.name,
                    moduleList:this.form.moduleList,
                    }
                    console.log(this.form.moduleList)
                
                  templateupdate(params).then(res=>{
                    let data = res.data;
                      if(data.code != 'A000000') {
                          this.$message.error(data.message);
                          return;
                      }
                        this.search();
                    });
                }
          }else if(data.data == 0){
              this.$message.error('此模板不可修改')
              return;
          } 
        });


      this.editVisible = false;
    },
    handleDelete(index, row) {
      this.idx = index;
      this.delVisible = true;
    },
   
    // 新建
    create() {
      this.newVisible = true;
      this.form = { 
        name:'',
        moduleList: [
           {wordLimit:'',mId:1},
           {wordLimit:'',mId:2},
           {wordLimit:'',mId:8},
           {width:'',height:'',mId:3},
           {width:'',height:'',duration:'',mId:4},
           {width:'',height:'',mId:5},
           {width:'',height:'',mId:6},
           {width:'',height:'',mId:7},
       
          ],
      };
    },
        // 新建确认
    saveNew() {
      if(this.form.type == 217){
        console.log(this.form.moduleList[4])
        if(this.form.moduleList[5].width == '' || this.form.moduleList[5].height == '' && this.form.moduleList[4].width == '' || this.form.moduleList[4].height == ''|| this.form.moduleList[4].duration == ''){
              this.$message.error("视频图片不能为空");
              return;
        }
       var params = {
        moduleList: this.form.moduleList,
        name: this.form.name,
        type:this.form.type
        }
      templateAdd(params).then(res=>{
      let data = res.data;     
        //  this.tableData = data.data.data
        console.log(params)
         if(data.code != 'A000000') {
              this.$message.error(data.message);
              return;
          }
          this.newVisible = false;
          this.getList();
        });
      }else if(this.form.type == 7){
          var params = {
    
          moduleList: [
           {wordLimit:this.form.moduleList[0].wordLimit,mId:1},
           {wordLimit:this.form.moduleList[1].wordLimit,mId:2},
           {wordLimit:this.form.moduleList[2].wordLimit,mId:8},
           {width:this.form.moduleList[3].width,height:this.form.moduleList[3].height,mId:3},
          //  {width:'',height:'',duration:'',mId:4},
           {width:this.form.moduleList[5].width,height:this.form.moduleList[5].height,mId:5},
           {width:this.form.moduleList[6].width,height:this.form.moduleList[6].height,mId:6},
           {width:this.form.moduleList[7].width,height:this.form.moduleList[7].height,mId:7},
          ],
   
          
            name: this.form.name,
            type:this.form.type
            }
          templateAdd(params).then(res=>{
          let data = res.data;     
            //  this.tableData = data.data.data
            console.log(params)
            if(data.code != 'A000000') {
                  this.$message.error(data.message);
                  return;
              }
              this.newVisible = false;
              this.getList();
            });
      }
  
     
      },
    // 确定删除
    deleteRow() {
      this.tableData.splice(this.idx, 1);
      this.$message.success("删除成功");
      this.delVisible = false;
    },
    addImg(i){
  
      if(this.form.moduleList.length==5) {
        this.form.moduleList.push({width:'',height:'',mId:6})
      }else if(this.form.moduleList.length==6) {
        this.form.moduleList.push({width:'',height:'',mId:7})
      }
      console.log(this.form.moduleList)
      
    },
    DeleteImg(i){
       console.log(i)
       let list  =  this.form.moduleList
       list.splice(i,1) 
       this.form.moduleList = list
       console.log(list)
       this.$forceUpdate()
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
.font{
    padding-left: 5px
}
</style>
