<template>
    <div class="table">
        <div class="container">
           <div class="handle-box">
              <el-autocomplete
                        class="inline-input"
                        v-model="select_word"
                        value-key="name"
                        :fetch-suggestions="querySearch"
                        placeholder="请输入数据集"
                        @select="handleSelect"
                      ></el-autocomplete>
                <el-button type="primary" icon="search" @click="search">搜索</el-button>
                 <el-button type="primary" icon="search" @click="create" v-if="this.readonly !=1">新建判定规则</el-button>
            </div>
            <el-table :data="tableData" border style="width: 100%" ref="multipleTable">
                <el-table-column prop="id" label="序号" align="center">
                </el-table-column>
                <el-table-column prop="dName" label="数据集" align="center">
                </el-table-column>
                <el-table-column prop="judgeRules" label="标签" align="center">
                </el-table-column>
                <el-table-column prop="tagsName" label="标签组" align="center">
                </el-table-column>
                <el-table-column label="操作" width="150"  align="center" v-if="this.readonly !=1">
                      <template slot-scope="scope">
                          <el-button size="small" type="success" @click="handleDelete(scope.$index, scope.row)">删除</el-button>
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
           <!-- 新建 -->
          <el-dialog title="新建" :visible.sync="newVisible" width="60%">
            <el-form ref="form" :model="form"   status-icon label-width="100px">
                <el-form-item label="数据集:">
                   <el-select v-model="form.dataSet" placeholder="请选择" @change="Getdate">
                        <el-option
                            v-for="item in optionsSet"
                            :key="item.id"
                            :label="item.name"
                            :value="item.id">
                        </el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="标签组:">
                  <el-select v-model="form.labelGroup" placeholder="请选择" @change="GetGroup">
                        <el-option
                            v-for="item in optionsGruop"
                            :key="item.id"
                            :label="item.name"
                            :value="item.id">
                        </el-option>
                    </el-select>
                </el-form-item>
                <el-tag size="medium">标判定规则</el-tag>
                <div class="div" v-for="(b_labei,index) in BT_lable" :key="index">
                    <el-button type="primary" @click="del_BT(index)">X</el-button>
                        <div class="T_lable" v-for="(b_labei1,index1) in b_labei" :key="index1">
                          <el-form-item label="标签:">
                              <el-select v-model="b_labei1.tid" placeholder="请选择">
                                    <el-option
                                        v-for="item in optionsTags"
                                        :key="item.id"
                                        :label="item.name"
                                        :value="item.id">
                                    </el-option>
                                </el-select>
                          </el-form-item>
                          <el-form-item label="字段选择:">
                            <el-select v-model="b_labei1.rid" placeholder="请选择">
                                    <el-option
                                        v-for="item in optionsfield"
                                        :key="item.id"
                                        :label="item.colName"
                                        :value="item.id">
                                    </el-option>
                                </el-select>
                        </el-form-item>
                        <el-form-item label="规则设置:">
                            当数据值<el-select v-model="b_labei1.operation" placeholder="请选择" style="width:45%">
                                    <el-option
                                        v-for="item in options3"
                                        :key="item.value"
                                        :label="item.label"
                                        :value="item.value">
                                    </el-option>
                                </el-select>以下内容时
                        </el-form-item>
                        <el-form-item>
                            <textarea type="text" class="input_" v-model="b_labei1.args"></textarea>
                            <!-- <el-upload
                              class="upload-demo"
                              :data="{type:4}"
                              :action="upload"
                              name="myFile"
                              :on-preview="handlePreview"
                              :on-success="handleAvatarSuccess"
                              :on-remove="handleRemove"
                              :before-remove="beforeRemove"
                              >
                              <el-button size="small" type="primary">上传文本</el-button> -->
                          <!-- </el-upload> -->
                        </el-form-item>
                        <el-form-item>
                            计<el-input v-model="b_labei1.score" style="width:20%"></el-input>分
                            <p class="p"></p>
                            <el-input v-model="b_labei1.sumScL" style="width:20%"></el-input>＜累计分值 ＜<el-input v-model="b_labei1.sumScB" style="width:20%"></el-input>时，确认标签
                                <el-button type="text" @click="add_XT(index)">添加同标签规则</el-button>
                                <el-button type="text" @click="del(index)">删除规则</el-button>
                        </el-form-item>
                    </div>
                     <el-form-item label="规则关系" v-if="b_labei.length > 1">
                            <el-radio v-model="form.relation" :label="1">与</el-radio>
                            <el-radio v-model="form.relation" :label="2">或</el-radio>   
                      </el-form-item>
                </div>
                <el-form-item >
                            <el-button type="text" @click="add_BT">添加不同标签的规则</el-button>
                </el-form-item>
            </el-form> 
            <el-button @click="newVisible = false">取 消</el-button>
            <el-button type="primary" @click="saveNews">提 交</el-button>
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
import {judgelist,dataslist,tagslist,judgeadd,uploadgetCont,judgedrop} from '@/api/Api.js';

const tagsID = new Map()
const dataID = new Map()
export default {
  name: "basetable",
  data() {
    return {
      upload: uploadgetCont,
      tableData: [],
      cur_page: 1,
      total: 1,
      ps: 10,
      args:'',
      select_cate: "",
      select_word: "",
      del_list: [],
      newVisible: false,
      delVisible:false,
      // XT_lable:[{}],
      BT_lable:[
        [{}]
      ],
      form: {
        rid:'',
        tid:'',
        labelGroup:'',
        dataSet:"",
        tags:'',
        select:'',
        score:'',
        sumScL:'',
        sumScB:'',
        operation:'',
        args:'',
        relation:'',
   
      },
      optionsSet: [
        {
          value: "",
          label: ""
        },
      ],
      optionsGruop:[
        {
          value: "",
          label: ""
        },
      ],
      optionsTags:[
        {
          value: "",
          label: ""
        },
      ],
      optionsfield:[
        {
          value: "",
          label: ""
        },
      ],
         options3: [
        {
          value: 1,
          label: "等于"
        },
        {
          value: 2,
          label: "不等于"
        },
        {
          value: 3,
          label: "大于"
        },
        {
          value: 4,
          label: "大于等于"
        },
        {
          value: 5,
          label: "小于"
        },
        {
          value: 6,
          label: "小于等于"
        },
        {
          value: 7,
          label: "包含"
        },
         {
          value: 8,
          label: "不包含"
        },
      ],
      value: "1",
      readonly:''
    
    };
  },
  created() {
      this.readonly = this.$store.state.map.get(this.$store.state.currentPath);
      this.getData();
      this.getLable();
      this.getSet();
  },
  
  methods: {
    // 分页导航
    handleCurrentChange(val) {
      this.cur_page = val;
        let params = {
          did:this.did,
          cp: this.cur_page,
          ps: this.ps
      };
      judgelist(params).then(res => {
        let data = res.data;
          console.log(res)
          if (data.code != 'A000000') {
              this.$message.error(data.message);
          }
          this.tableData = data.data.data;
          console.log(this.tableData)
          this.total = data.data.totalItemNum;
      });
    },
         //初始列表
    getData() {
        let params = {
            cp: this.cur_page,
            ps: this.ps
        };
        judgelist(params).then(res => {
          let data = res.data;
            if (data.code != 'A000000') {
                this.$message.error(data.message);
            }
            console.log(res)
            this.tableData = data.data.data;
            this.total = data.data.totalItemNum;
        });
    },
      getSet() {
          let params = {
              cp: this.cur_page,
              ps: this.ps
          };
          dataslist(params).then(res => {
            let data = res.data;
              if (data.code != 'A000000') {
                  this.$message.error(data.message);
              }
              this.optionsSet = data.data.data;
                data.data.data.forEach(element => {
                 dataID.set(element.id,element.definitions);
                 console.log(dataID)
               }); 
          });
      },
      Getdate(){
           this.optionsfield = dataID.get(this.form.dataSet) 
      },

      // 标签
      getLable() {
          let params = {
              cp: this.cur_page,
              ps: this.ps
          };
          tagslist(params).then(res => {
            let data = res.data;
               console.log(data.data.data[0].tagList)
              if (data.code != 'A000000') {
                  this.$message.error(data.message);
              }
              this.optionsGruop = data.data.data;
               data.data.data.forEach(element => {
                 tagsID.set(element.id,element.tagList);
               }); 
              // this.optionsTags = data.data.data[0].tagList
             
          });
      },
      GetGroup(){
           this.optionsTags = tagsID.get(this.form.labelGroup) 
          //  console.log(this.optionsTags)
      },
       // 模糊搜索数据集
      querySearch(queryString, cb) {
       let params = {
            name:this.select_word,
            }
        dataslist(params).then(res=>{
        let data = res.data;
              // console.log(data.data.data)
              // 调用 callback 返回建议列表的数据
            cb(data.data.data);
          if(data.code != 'A000000') {
              this.$message.error(data.message);
          } 
          //  this.dynamicTags = data.data
          //  this.QX_Visible = true;
            });
          },
        handleSelect(item){
            this.did = item.id
            console.log(item)
          },
     // 搜索
    search() {
      this.$refs.pagination.lastEmittedPage = 1
      this.cur_page = 1;
       let params = {
          did:this.did,
          cp: this.cur_page,
          ps: this.ps
      };
      judgelist(params).then(res => {
        let data = res.data;
          console.log(res)
          if (data.code != 'A000000') {
              this.$message.error(data.message);
          }
          this.tableData = data.data.data;
          console.log(this.tableData)
          this.total = data.data.totalItemNum;
      });
    },
 
    // 新建
    create() {
      this.newVisible = true;
      this.form = {};
      this.BT_lable =[[{}]];
    },
    saveNews() {
      if(!this.form.dataSet) {
        this.$message.error("数据集不能为空");
        return;
      }
      if(!this.form.labelGroup) {
        this.$message.error("标签组不能为空");
        return;
      }
      // if(!this.list) {
      //   this.$message.error("规则不能为空");
      //   return;
      // }
      let list = [];
      this.BT_lable.forEach(element => {
        list = list.concat(element)
      });
      console.log(list)
       let params = {
          did:this.form.dataSet,
          tid:this.form.labelGroup,
          rulesList:list
      }
      console.log(params)
      judgeadd(params).then(res => {
        let data = res.data;
          console.log(res)
          if (data.code != 'A000000') {
              this.$message.error(data.message);
          }
          this.newVisible = false;
          this.getData()
      });  
    },
       // 删除
    handleDelete(index, row) {
      console.log(row);
      this.rows = row;
      this.delVisible = true;
    },
    // 确定删除
    deleteRow() {
      let params = {
        id: this.rows.id
      };
      judgedrop(params).then(res => {
        console.log(res);
      let data = res.data;
        if (data.code != "A000000") {
          this.$message.error(data.message);
        } else {
          this.$message.success("删除成功");
          this.delVisible = false;
          this.getData();
        }
      });
    },
    add_XT(i){
        this.BT_lable[i].push({})
    },
    del(i){
        this.BT_lable[i].splice(i,1)
    },
    add_BT(){
        this.BT_lable.push([{}])
    },
    del_BT(i){
        this.BT_lable.splice(i,1)
    },
    handleRemove(file, fileList) {
        console.log(file, fileList);
      },
      handlePreview(file) {
        console.log(file);
      },
      beforeRemove(file, fileList) {
        return this.$confirm(`确定移除 ${ file.name }？`);
      },
      handleAvatarSuccess(res, file) {
        // console.log(res)
          //let data = res;
          // item.args =  data.data
          // console.log(item.args)
          
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
.mrl11{
  width: 300px;
}
.del-dialog-cnt {
  font-size: 16px;
  text-align: center;
}
.header-select {
    margin-bottom: 20px
}
.div{
    width: 50%;
    margin-top: 15px;
    border: 1px silver solid;
    padding: 10px;
}
.input_{
    width: 87%;
    height: 200px;
}
.p{
  margin-top: 20px
}

</style>
<style>
.el-upload--text{
    border: 0px dashed #d9d9d9 !important;
    width: 80px !important;
    height: 32px !important;
  
}
</style>




