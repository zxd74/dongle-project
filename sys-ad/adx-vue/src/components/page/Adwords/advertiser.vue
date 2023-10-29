<template>
    <div class="table">
        <div class="container">
            <div class="handle-box">
                 <el-autocomplete
                    class="inline-input"
                    v-model="select_adname"
                    value-key="consumerName"
                    :fetch-suggestions="querySearchAD"
                    placeholder="广告平台名称"
                    @select="handleSelectAD"
                  ></el-autocomplete>
                 <el-autocomplete
                    class="inline-input"
                    v-model="select_word"
                    value-key="name"
                    :fetch-suggestions="querySearchName"
                    placeholder="广告主名称"
                    @select="handleSelectName" 
                  ></el-autocomplete>
                 <el-select v-model="value" placeholder="状态">
                  <el-option
                    v-for="item in options_"
                    :key="item.value"
                    :label="item.label"
                    :value="item.value">
                  </el-option>
                </el-select>
                <el-button type="primary" icon="search" @click="search">搜索</el-button>
                <!-- <el-button type="primary" icon="search" @click="create">新建广告主</el-button> -->
            </div>
            <el-table :data="tableData" border style="width: 100%" ref="multipleTable">
                <el-table-column prop="id" label="序号"  width="100" align="center">
                </el-table-column>
                <el-table-column prop="name" label="广告主名称"  width="160" align="center">
                </el-table-column>
                <el-table-column prop="dspAdvertiserId" label="广告主ID"  width="" align="center" >
                </el-table-column>
                <el-table-column prop="consumerName" label="平台"  width="" align="center">
                </el-table-column>
                <el-table-column prop="examinesStatus" label="审核状态"  width="" align="center" :formatter=formattermediaType>
                </el-table-column>
                <el-table-column  label="状态"  width="" align="center">
                  <template slot-scope="scope">
                     <el-switch v-model="scope.row.status" :active-value=1 :inactive-value=0 @change="changeStatus($event,scope.row)"></el-switch>
                  </template>
                </el-table-column>
                <el-table-column label="操作" width="150" align="center">
                    <template slot-scope="scope">
                        <!-- <el-button size="small" type="success" @click="handleEdit(scope.$index, scope.row)">编辑</el-button> -->
                        <el-button size="small" type="success" @click="AD_Edit(scope.$index, scope.row)">审核</el-button>
                        <!-- <el-button size="small" type="danger" @click="handleDelete(scope.$index, scope.row)">删除</el-button> -->
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
        <el-dialog title="编辑" :visible.sync="editVisible" width="40%">
            <el-form ref="FormNewData" :model="FormNewData" label-width="100px" :label-position="labelPosition">
                <el-form-item label="广告主名称:">
                  <el-input v-model="FormNewData.name" style="width:80%"></el-input>
                </el-form-item>
                <el-form-item label="行业:">
                    <el-select v-model="FormNewData.industryType"  class="handle-input">
                        <el-option
                        v-for="item in options"
                        :key="item.id"
                        :label="item.dicValue"
                        :value="item.id">
                        </el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="平台名称:">
                    <el-select v-model="FormNewData.dspId"  class="handle-input">
                        <el-option
                        v-for="item in optionsDSP"
                        :key="item.id"
                        :label="item.consumerName"
                        :value="item.id">
                        </el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="联系人:">
                    <el-input v-model="FormNewData.linkman"  style="width:80%"></el-input>
                </el-form-item>
                <el-form-item label="联系电话:">
                    <el-input v-model="FormNewData.tel" style="width:80%"></el-input>
                </el-form-item>
                <el-form-item label="联系地址:">
                    <el-input v-model="FormNewData.address"  style="width:80%"></el-input>
                </el-form-item>
                <el-form-item label="营业执照:">
                      <el-input v-model="FormNewData.businesslicense" placeholder=""  style="width:80%"></el-input>
                      <el-upload
                        class="upload-demo"
                        :data="{type:6}"
                        :action="upload"
                        name="myFile"
                        :on-preview="handlePreview"
                        :on-success="handleAvatarSuccess"
                        :on-remove="handleRemove"
                        :before-remove="beforeRemove"
                       
                        >
                        <el-button size="small" type="primary">点击上传</el-button>
                        <div slot="tip" class="el-upload__tip"></div>
                      </el-upload>
                  </el-form-item>
                  <el-form-item label="其他资质:">
                      <el-input v-model="FormNewData.qualifications" placeholder=""  style="width:80%"></el-input>
                      <el-upload
                        class="upload-demo"
                        :data="{type:6}"
                        :action="upload"
                        name="myFile"
                        :on-preview="handlePreview"
                        :on-success="handleAvatarSuccess_to"
                        :on-remove="handleRemove"
                        :before-remove="beforeRemove"
                       
                        >
                        <el-button size="small" type="primary">点击上传</el-button>
                        <div slot="tip" class="el-upload__tip"></div>
                      </el-upload>
                  </el-form-item>
            </el-form>
            <span slot="footer" class="dialog-footer">
                <el-button @click="editVisible = false">取 消</el-button>
                <el-button type="primary" @click="saveEdit">确 定</el-button>
            </span>
        </el-dialog>

          <!-- 新建弹出框 -->
        <el-dialog title="新建广告主" :visible.sync="newVisible" width="40%">
            <el-form ref="FormNewData" :model="FormNewData" label-width="100px" :label-position="labelPosition">
                <el-form-item label="* 广告主名称:">
                  <el-input v-model="FormNewData.name" style="width:80%"></el-input>
                </el-form-item>
                <el-form-item label="* 行业:">
                    <el-select v-model="FormNewData.industryType"  class="handle-input">
                        <el-option
                        v-for="item in options"
                        :key="item.id"
                        :label="item.dicValue"
                        :value="item.id">
                        </el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="* 平台名称:">
                    <el-select v-model="FormNewData.dspId"  class="handle-input">
                        <el-option
                        v-for="item in optionsDSP"
                        :key="item.id"
                        :label="item.consumerName"
                        :value="item.id">
                        </el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="联系人:">
                    <el-input v-model="FormNewData.linkman" style="width:80%"></el-input>
                </el-form-item>
                <el-form-item label="联系电话:">
                    <el-input v-model="FormNewData.tel" style="width:80%"></el-input>
                </el-form-item>
                <el-form-item label="联系地址:">
                    <el-input v-model="FormNewData.address"  style="width:80%"></el-input>
                </el-form-item>
                <el-form-item label="* 营业执照:">
                      <el-input v-model="FormNewData.businesslicense" placeholder=""  style="width:60%"></el-input>
                      <el-upload
                        class="upload-demo"
                        :data="{type:6}"
                        :action="upload"
                        name="myFile"
                        :on-preview="handlePreview"
                        :on-success="handleAvatarSuccess"
                        :on-remove="handleRemove"
                        :before-remove="beforeRemove"
                      
                        >
                        <el-button size="small" type="primary">点击上传</el-button>
                        <div slot="tip" class="el-upload__tip"></div>
                      </el-upload>
                  </el-form-item>
                  <el-form-item label="其他资质:">
                      <el-input v-model="FormNewData.qualifications" placeholder=""  style="width:60%"></el-input>
                      <el-upload
                        class="upload-demo"
                        :data="{type:6}"
                        :action="upload"
                        name="myFile"
                        :on-preview="handlePreview"
                        :on-success="handleAvatarSuccess_to"
                        :on-remove="handleRemove"
                        :before-remove="beforeRemove"
                      
                        >
                        <el-button size="small" type="primary">点击上传</el-button>
                        <div slot="tip" class="el-upload__tip"></div>
                      </el-upload>
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

      

        <!-- 审核提示框 -->
         <el-dialog title="广告主审核"  :visible.sync="AD_Visible" width="40%" center>
             <el-form ref="FormNewData" :model="FormNewData" label-width="100px" :label-position="labelPosition">
                <el-form-item label="广告主名称:">
                  <el-input v-model="FormNewData.name" style="width:80%" disabled=""></el-input>
                </el-form-item>
                <el-form-item label="行业:">
                    <el-select v-model="FormNewData.industryType" disabled=""  class="handle-input">
                        <el-option
                        v-for="item in options"
                        :key="item.id"
                        :label="item.dicValue"
                        :value="item.id">
                        </el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="平台名称:">
                    <el-select v-model="FormNewData.dspId" disabled=""  class="handle-input">
                        <el-option
                        v-for="item in optionsDSP"
                        :key="item.id"
                        :label="item.dspId"
                        :value="item.id">
                        </el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="联系人:">
                    <el-input v-model="FormNewData.linkman" disabled="" placeholder="" style="width:80%"></el-input>
                </el-form-item>
                <el-form-item label="联系电话:">
                    <el-input v-model="FormNewData.tel" disabled="" placeholder="" style="width:80%"></el-input>
                </el-form-item>
                <el-form-item label="联系地址:">
                    <el-input v-model="FormNewData.address" disabled="" placeholder="" style="width:80%"></el-input>
                </el-form-item>
                <el-form-item label="营业执照:">
                      <el-input v-model="FormNewData.businesslicense" placeholder="" disabled=""  style="width:80%"></el-input>
                      <img :src="FormNewData.businesslicense" alt="" class="img"> 
                  </el-form-item>
                <el-form-item label="其他资质:">
                      <el-input v-model="FormNewData.qualifications" placeholder="" disabled=""  style="width:80%"></el-input>
                </el-form-item>
                <el-form-item label="审核状态:">
                    <el-radio-group v-model="FormNewData.examinesStatus">
                      <el-radio :label="1">通过</el-radio>
                      <el-radio :label="2">未通过</el-radio>
                   </el-radio-group>
                      <el-input v-model="FormNewData.examinesRemarks" placeholder="未通过理由"  style="width:80%"></el-input>                
                </el-form-item>
             </el-form>
            <span slot="footer" class="dialog-footer">
                <el-button @click="AD_Visible = false">取 消</el-button>
                <el-button type="primary" @click="ADmanageDataEdit" v-if="this.readonly !=1">保 存</el-button>
            </span>
        </el-dialog>
    </div>
</template>

<script>
var names = new Map();

import { advertiserDspgetList,industryManage,advertiserDspadd,upload,flowconsumergetpage,advertiserDspget } from "@/api/Api.js";
import { advertiserDspexamines,advertiserDspupdate } from "@/api/Api.js";


export default {
  name: "basetable",
  data() {
    return {
      labelPosition: 'left',
      upload: upload,
      tableData: [],
      cur_page: 1,
      total: 1,
      ps:10,
      select_word: "",
      select_adname:'',
      editVisible: false,
      delVisible: false,
      newVisible: false,
      AD_Visible: false,
      type_: "",
      // form
      FormNewData: {
        id: "",
        name:'',
        industryType:'',
        dspId:'',
        linkman: "",
        status:'',
        tel: "",
        address: "",
        examinesStatus:"",
        businesslicense: "",
        qualifications: '',
        examinesRemarks:'',
        adposId: [],
      },
      optionsType:[
         {
          value: '',
          label: ''
        }
      ],
      options_: [
         {
          value: '',
          label: '全部状态'
        },
        {
          value: 1,
          label: '启用'
        },
        {
          value: 0,
          label: '停用'
        }
      ],
     options: [
        {
          value: '',
          label: ''
        }
      ],
      optionsDSP:[
           {
          value: '',
          label: ''
        }
      ],
      value: "",
      types:[],
      readonly:'',
    };
  },
  created() {
     this.readonly = this.$store.state.map.get(this.$store.state.currentPath);
     this.getList();
     this.getIndustry();
     this.getDSP();
  },
 
  methods: {
       // 状态开关
    changeStatus(val,row){
       let item = row;
             let params = {
                id:item.id,
                status:item.status,
            }
     advertiserDspupdate(params).then(res=>{
     let data = res.data;
          if(data.code != 'A000000') {
              this.$message.error(data.message);
          }
        //   
            //  this.getList();
      });
    },
    // 格式化数据
    formattermediaType(row, column) {
      if(row.examinesStatus == 1) {
        return "审核通过";
      } else if(row.examinesStatus == 3){
        return "待审核";
      }
      else if(row.examinesStatus == 2){
        return "拒绝审核";
      }
    },
    // 分页导航
    handleCurrentChange(val) {
      this.cur_page = val;
         let params = {
         name:this.select_word,
         flowConsumerId:names.get(this.select_adname),
         status:this.value,
         cp:this.cur_page,
         ps:this.ps
         }
      advertiserDspgetList(params).then(res=>{
     let data = res.data;     
       this.tableData = data.data.data
       this.total = data.data.totalItemNum;
      
      });
    },
    getList() {
      let params = {cp:this.cur_page,ps:this.ps}
      advertiserDspgetList(params).then(res=>{
     let data = res.data;     
       this.tableData = data.data.data
       this.total = data.data.totalItemNum;
      });
    },
    //模糊名字
    querySearchName(queryString, cb){
        let params = {
          name:this.select_word,
          flowConsumerId:names.get(this.select_adname),
          cp:this.cur_page,
          ps:this.ps
          }
      advertiserDspgetList(params).then(res=>{
        let data = res.data;
            // 调用 callback 返回建议列表的数据
        cb(data.data.data);
        if(data.code != 'A000000') {
            this.$message.error(data.message);
              } 
          });
    },
    // 模糊平台
     querySearchAD(queryString, cb){
        let params = {
          consumerName:this.select_word,
          currentPage:this.cur_page,
          pageSize:this.ps
          }
         flowconsumergetpage(params).then(res=>{
     let data = res.data; 
          // 调用 callback 返回建议列表的数据         
        cb(data.data.data);
         data.data.data.forEach(element => {
           names.set(element.consumerName,element.id);          
        });
       if(data.code != 'A000000') {
          this.$message.error(data.message);
            } 
        });
    },
      handleSelectAD(item){     
      this.id = item.id
    },
    handleSelectName(item){
      this.id = item.id
    },
    // 搜索
     search() {
        this.$refs.pagination.lastEmittedPage = 1
      this.cur_page = 1;
       let params = {
         name:this.select_word,
         flowConsumerId:names.get(this.select_adname),
         status:this.value,
         cp:this.cur_page,
         ps:this.ps
         }
      advertiserDspgetList(params).then(res=>{
     let data = res.data;     
       this.tableData = data.data.data
       this.total = data.data.totalItemNum;
      
      });
    },
     // 行业选项
    getIndustry() {
      industryManage().then(res=>{
     let data = res.data;     
       this.options = data.data
    //    
      });
    },
      // 平台名称:
    getDSP() {
      flowconsumergetpage().then(res=>{
     let data = res.data;     
       this.optionsDSP = data.data.data
    //    
      });
    },
    // 编辑
    handleEdit(index, row) {
      this.idx = index;
      let item = row;
      this.FormNewData = {
            id:item.id,
            industryType:item.industryType,
            dspId:item.flowConsumerId,
            linkman:item.linkman,
            tel:item.tel,
            address:item.address,
            businesslicense:item.businesslicense,
            name:item.name,
            qualifications:item.qualifications,
      };
      this.editVisible = true;
    },
    // 保存编辑
    saveEdit() {
        let params = {
            id:this.FormNewData.id,
            industryType:this.FormNewData.industryType,
            flowConsumerId:this.FormNewData.dspId,
            linkman:this.FormNewData.linkman,
            tel:this.FormNewData.tel,
            address:this.FormNewData.address,
            businesslicense:this.FormNewData.businesslicense,
            name:this.FormNewData.name,
            qualifications:this.FormNewData.qualifications,
            }
        advertiserDspupdate(params).then(res=>{
     let data = res.data;
          if(data.code != 'A000000') {
              this.$message.error(data.message);
          }
          this.editVisible = false;
          this.search();
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
      // 审核
     AD_Edit(index, row) {
        this.row = row
        // this.FormNewData.id = row.id
        let params ={
          id:row.id
        }
      advertiserDspget(params).then(res=>{
        let data = res.data;
          if(data.code != 'A000000') {
              this.$message.error(data.message);
          }
           this.FormNewData = data.data
           this.AD_Visible = true;
             
      }); 
    },
    // 保存
    ADmanageDataEdit(){
      if(this.FormNewData.examinesStatus == 2) {
        if(!this.FormNewData.examinesRemarks){
          this.$message.error("拒绝理由不能为空");
          return;
        }       
      }
           let params = {
            id:this.FormNewData.id,
            examinesStatus:this.FormNewData.examinesStatus,
            examinesRemarks:this.FormNewData.examinesRemarks,
            }
        advertiserDspexamines(params).then(res=>{
      let data = res.data;
           if(data.code != 'A000000') {
              this.$message.error(data.message);
          }
          this.AD_Visible = false;
          this.getList();
      });
      
    },
    
    // 新建
    create() {
      this.newVisible = true;
      this.FormNewData = {};
    },
    //确认
    saveNews() {
      if(!this.FormNewData.name) {
        this.$message.error("广告主名称不能为空");
        return;
      }
      if(!this.FormNewData.industryType) {
        this.$message.error("行业不能为空");
        return;
      }
      if(!this.FormNewData.dspId) {
        this.$message.error("平台名称不能为空");
        return;
      }
      if(!this.FormNewData.businesslicense) {
        this.$message.error("营业执照不能为空");
        return;
      }

        let params = {
            name:this.FormNewData.name,
            industryType:this.FormNewData.industryType,
            flowConsumerId:this.FormNewData.dspId,
            linkman:this.FormNewData.linkman,
            tel:this.FormNewData.tel,
            address:this.FormNewData.address,
            businesslicense:this.FormNewData.businesslicense,
            qualifications:this.FormNewData.qualifications,
            }
      advertiserDspadd(params).then(res=>{
     let data = res.data;
          
       if(data.code != 'A000000') {
          this.$message.error(data.message);
       }
       // 掉查询接口
       this.newVisible = false;
       this.getList();
        });
    },
   
    handlePreview(file) {
        
      },
    handleRemove(file, fileList) {
        
      },
    beforeRemove(file, fileList) {
        return this.$confirm(`确定移除 ${ file.name }？`);
      },
    handleAvatarSuccess(res, file) {
         
         let data = res;
        if(data.code != 'A000000'){
            this.$message(data.message)
            return;
        }
        
       this.FormNewData.businesslicense = data.data.url
       this.$forceUpdate()
      },
      
    handleAvatarSuccess_to(res, file) {
          let data = res;
        if(data.code != 'A000000'){
            this.$message(data.message)
            return;
        }
        
       this.FormNewData.qualifications = data.data.url
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
 .img{
    display: block;
    width: 300px;
    height: 300px;
}
</style>
<style>
/* .el-form-item__content {
  margin-left: 150px !important;
} */
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

