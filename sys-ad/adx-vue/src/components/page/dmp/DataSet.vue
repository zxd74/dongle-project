<template>
    <div class="table">
        <div class="container">
            <div class="handle-box">
                 <el-autocomplete
                    class="inline-input"
                    v-model="select_word"
                    value-key="name"
                    :fetch-suggestions="querySearchCustomer"
                    placeholder="请输入名称搜索"
                    @select="handleSelectCustomer"
                  ></el-autocomplete>
                <el-button type="primary" icon="search" @click="search">搜索</el-button>
                 <el-button type="primary" icon="search" @click="create" v-if="this.readonly !=1">新建</el-button>
            </div>
            <el-table :data="tableData" border style="width: 100%" ref="multipleTable">
                <el-table-column prop="name" label="数据集名称" align="center">
                </el-table-column>
                <el-table-column prop="sName" label="数据来源" align="center">
                </el-table-column>
                <el-table-column  label="状态"  width="" align="center" v-if="this.readonly !=1">
                  <template slot-scope="scope">
                     <el-switch v-model="scope.row.status" :active-value=1 :inactive-value=0   @change="changeStatus($event,scope.row)"></el-switch>
                  </template>
                </el-table-column>
                <!-- <el-table-column label="操作"  align="center"> -->
                    <!-- <template slot-scope="scope"> -->
                        <!-- <el-button size="small" type="success" @click="handleEdit(scope.$index, scope.row)">编辑</el-button>
                        <el-button size="small" type="danger" @click="handleDelete(scope.$index, scope.row)">删除</el-button> -->
                    <!-- </template> -->
                <!-- </el-table-column> -->
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
        <el-dialog title="新建" :visible.sync="newVisible" width="60%">
            <el-form ref="form" :model="form"  status-icon label-width="100px">
                <el-form-item label="* 选择数据源:">
                  <el-select v-model="optionsList" placeholder="请选择">
                        <el-option
                            v-for="item in options"
                            :key="item.id"
                            :label="item.name"
                            :value="item.id">
                        </el-option>
                  </el-select>
                </el-form-item>
                <el-form-item label="* 数据集名称:">
                    <el-input class="mr10" v-model="form.datasName" placeholder="请输入数据集名称"></el-input>
                </el-form-item>
                <el-form-item label="* 选择分隔符:">
                     <el-radio v-model="form.delimiter" :label="1">制表符</el-radio>
                     <el-radio v-model="form.delimiter" :label="2">分号</el-radio>
                     <el-radio v-model="form.delimiter" :label="3">逗号</el-radio>
                     <el-radio v-model="form.delimiter" :label="4">空格</el-radio>
                     <el-radio v-model="form.delimiter" :label="5">其他</el-radio>
                     <el-input v-model="form.custom" placeholder="" style="width:10%"></el-input>
                    <el-button type="primary" @click="preview">预览</el-button>
                </el-form-item>
                <el-tag>数据集预览</el-tag>
                <div class="div" v-if="this.modules == 1">
                  <!-- <div class="mr11">
                     <el-radio-group v-model="majorkey">
                        <el-radio :label="1" v-for="(item,index) in rowline" :key="index">是否为主键</el-radio>
                      </el-radio-group>
                  </div> -->
                  <!-- <div>
                    <el-input  v-model="rowLine" placeholder="A列-字符串" class="mr11"></el-input>
                  </div> -->
                  <!-- <div>
                      <el-select v-model="selectName" placeholder="请选择" class="mr11">
                            <el-option
                                v-for="item in optionsType"
                                :key="item.value"
                                :label="item.label"
                                :value="item.value">
                            </el-option>
                      </el-select>
                  </div> -->
                  <div class="container1">
                    <div v-for="(item,index) in list" :key="index" >
                      <div v-for="(item1,index1) in item" :key="index1" class="item">
                        <div v-if="index==0">
                          <div>
                            <el-radio-group v-model="majorkey">
                                <el-radio :label="index1" >是否为主键</el-radio>
                            </el-radio-group>
                          </div>
                          <div>
                            <el-input  v-model="rowLine[index1]" placeholder="请定义列名" value=" " class="mr11"></el-input>
                          </div>
                          <div>
                            <el-select v-model="selectName[index1]" placeholder="请选择" class="mr11">
                                  <el-option
                                      v-for="item in optionsType"
                                      :key="item.value"
                                      :label="item.label"
                                      :value="item.value">
                                  </el-option>
                            </el-select>
                          </div>
                        </div>
                        <span>{{item1}}</span>
                      </div>
                    </div>
                  </div>
                  <!-- <el-table :data="list" border style="width: 100%"  ref="multipleTable">
                    <el-table-column  label=""  width="100%">
                      <template slot-scope="scope"> -->
                        <!-- <el-table :data="scope.row" border style="width: 100%"  ref="multipleTable">
                           <el-table-column label=""  width="200px">
                            <template slot-scope="scope">
                                <span>{{scope.row}}</span>
                            </template>
                           </el-table-column>
                        </el-table>   -->
                        <!-- <el-row :gutter="20"> -->
                        <!-- <el-col :span="6" v-for="(item,index) in scope.row" :key="index">
                           <span >{{item}}</span>
                        </el-col>   -->
                        <!-- </el-row> -->

                      <!-- </template>
                    </el-table-column> -->
                    <!-- <el-table-column prop="date" label=""  width="200px">
                    </el-table-column>
                    <el-table-column prop="address" label=""  width="200px">
                    </el-table-column>
                    <el-table-column prop="address" label=""  width="200px">
                    </el-table-column>
                    <el-table-column prop="address" label=""  width="200px">
                    </el-table-column> -->
                  <!-- </el-table>  -->
                </div>
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
import {dataslist,datasourcelist,dataspreview,datasadd,datasupdate} from '@/api/Api.js';

export default {
  name: "basetable",
  data() {
    return {
      url: "./static/vuetable.json",
      tableData: [],
      data:[],
      cur_page: 1,
      total: 1,
      ps: 10,
      select_word: "",
      majorkey:[],
      rowLine:[],
      selectName:[],
      del_list: [],
      is_search: false,
      editVisible: false,
      delVisible: false,
      newVisible: false,
      newaddAD: false,
      template_dialog: false,
      currentRow: null,
      add_time:"",
      modules:0,
      form: {
        name: "",
        date: "",
        switch: false,
        add_time:"",
        data_name:'',
        data_supplier:'1',
        data_describe:'',
        data_Url:[{url:''}],
        delimiter:'',
        custom:'',
        datasName:'',
      },
      list: [],
      idx: -1,
      type: 1,
      types: null,
      flow_management: null,
      data_report:null,
      optionsList:'',
      options: [
        {
          value: "",
          label: ""
        },
      ],
      optionsType:[
        {
          value: "2",
          label: "数值"
        },
        {
          value: "1",
          label: "文本"
        },
        {
          value: "3",
          label: "日期"
        },
      ],
      value: "1",
      readonly:''

    };
  },
  created() {
    this.readonly = this.$store.state.map.get(this.$store.state.currentPath); 
    this.getData();
    this.getType();
  },
  methods: {
     getType(){
       let params = {
           status: 1,
           cp: this.cur_page,
           ps: this.ps
          }
      datasourcelist(params).then(res=>{
     let data = res.data;
       this.options = data.data.data
      // console.log(data)
      });
    },
      // 模糊客户名称
    querySearchCustomer(queryString, cb){
        let params = {
          name :this.select_word,
          }
         dataslist(params).then(res=>{
     let data = res.data;
          // console.log(data)
          // 调用 callback 返回建议列表的数据
        cb(data.data.data);
        //   data.data.data.forEach(element => {
        //   uuid.set(element.fullName,element.id)
        // });
       if(data.code != 'A000000') {
          this.$message.error(data.message);
            }
        });
    },
    handleSelectCustomer(item){
      // console.log(item);
      this.id = item.id
    },
       // 开关
    changeStatus(val, row) {
        let params = {
            id: row.id,
            status: row.status
        };
        datasupdate(params).then(res => {
          let data = res.data;
            if (data.code != 'A000000') {
                this.$message.error(data.message);
            }
            // this.getData();
        });
    },
    // 分页导航
    handleCurrentChange(val) {
      this.cur_page = val;
       let params = {
         name:this.select_word,
         cp: this.cur_page,
         ps: this.ps
      }
        dataslist(params).then(res => {
          let data = res.data;
            if (data.code != 'A000000') {
                this.$message.error(data.message);
            }
            this.tableData = data.data.data;
            this.total = data.data.totalItemNum;
        });
    },
       //初始列表
      getData() {
          let params = {
              cp: this.cur_page,
              ps: this.ps
          };
          dataslist(params).then(res => {
            let data = res.data;
              if (data.code != 'A000000') {
                  this.$message.error(data.message);
              }
              this.tableData = data.data.data;
              this.total = data.data.totalItemNum;
          });
      },
    search() {
       this.$refs.pagination.lastEmittedPage = 1
       this.cur_page = 1;
      let params = {
         name:this.select_word,
         cp: this.cur_page,
         ps: this.ps
      }
        dataslist(params).then(res => {
          let data = res.data;
            if (data.code != 'A000000') {
                this.$message.error(data.message);
            }
            this.tableData = data.data.data;
            this.total = data.data.totalItemNum;
        });

    },
    preview(){
      this.modules = 1
       let params = {
             sid:this.optionsList,
             signCode:this.form.delimiter,
             sign:this.form.custom
          };
      dataspreview(params).then(res => {
      let data = res.data;
        if (data.code != 'A000000') {
            this.$message.error(data.message);
        }
        this.list = data.data
        // this.total = data.data.totalItemNum;
        console.log(res)
      });
    },
    formatter(row, column) {
      return row.address;
    },
    filterTag(value, row) {
      return row.tag === value;
    },
    handleEdit(index, row) {
      this.idx = index;
      const item = this.tableData[index];
      this.form = {
        name: item.name,
        date: item.date,
        address: item.address
      };
      this.editVisible = true;
    },
    handleDelete(index, row) {
      this.idx = index;
      this.delVisible = true;
    },
    // 保存编辑
    saveEdit() {
      this.$set(this.tableData, this.idx, this.form);
      this.editVisible = false;
      this.$message.success(`修改第 ${this.idx + 1} 行成功`);
    },
    // 新建
    create() {
      this.newVisible = true;
      this.optionsList ='';
      this.form = {};
      this.data =[];
      this.modules = 0;
    },
    saveNews() {
        if (!this.optionsList) {
            this.$message.error("数据源不能为空");
            return;
        }
        if (!this.form.datasName) {
            this.$message.error("数据集名称不能为空");
            return;
        }
        let isKey = this.majorkey,
        colName = this.rowLine,
        colType = this.selectName;
        let defintions = [];
        for(let i = 0; i < colName.length; i++) {
          let itemData = {};
          if (!colName[i] || !colType[i]) {
            continue;
          }
          itemData['colName'] = colName[i];
          itemData['col'] = i;
          itemData['colType'] = colType[i];
          itemData['isKey'] = 0;
          if (i == isKey) {
            itemData['isKey'] = 1;
          }
          defintions.push(itemData);
        }
       let params = {
        // did:this.optionsList,
        definitions:defintions,
        delId:this.form.delimiter,
        delimiter :this.form.custom,
        name:this.form.datasName,
        sid:this.optionsList
      }
    datasadd(params).then(res=>{
      let data = res.data;
         if(data.code != 'A000000') {
              this.$message.error(data.message);
          }
          this.newVisible = false;
          this.getData()
      });
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
    template_(){

    },
     data_address() {
      this.form.data_Url.push({ url: "" });
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
.header-select {
    margin-bottom: 20px
}
.mr10{
  display: inline-block;
  width: 200px;
}
.mr11{
   width: 200px;
}
.div{
  /* width: 45%; */
  overflow-x:scroll
}

.container1{
    width: 100%;
    overflow-x: scroll;
    overflow-y: hidden;
    white-space: nowrap;
}
.container1 .item{
    display:inline-block;
    box-sizing: border-box;
    width:25%;
    /* padding: 5px; */
    border: 1px solid silver;

}
.container1 .item span{
   display: block;
   width: 200px;
   height: 24px;
   overflow: hidden;
   text-overflow: ellipsis
}
</style>




