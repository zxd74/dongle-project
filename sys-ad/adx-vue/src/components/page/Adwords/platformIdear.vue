<template>
    <div class="table">
        <div class="container">
            <div class="handle-box">
                 <el-input v-model="select_extid" placeholder="请输入平台创意ID" class="inline-input"></el-input>
                 <el-input v-model="select_name" placeholder="请输入创意名称" class="inline-input"></el-input>

                   <el-autocomplete
                    class="inline-input"
                    v-model="select_adDSP"
                    value-key="consumerName"
                    :fetch-suggestions="querySearchAD"
                    placeholder="请输平台名称"
                    @select="handleSelectAD"
                  ></el-autocomplete>

                   <el-autocomplete
                    class="inline-input"
                    v-model="select_endName"
                    value-key="name"
                    :fetch-suggestions="querySearchName"
                    placeholder="请输入广告主名称"
                    @select="handleSelectName"
                  ></el-autocomplete>

                 <el-select v-model="value" placeholder="状态"   class="inline-input">
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
                <el-table-column prop="id" label="创意ID"  width="100" align="center">
                </el-table-column>
                <el-table-column prop="dspCreativeId" label="平台创意ID"   align="center">
                </el-table-column>
                <el-table-column prop="name" label="创意名称"  width="160" align="center" >
                </el-table-column>
                <el-table-column prop="consumerName" label="平台"  width="160" align="center">
                </el-table-column>
                <el-table-column prop="advertiserName" label="广告主"  width="" align="center">
                </el-table-column>
                <el-table-column prop="examinesStatus" label="审核状态"  width="" align="center" :formatter=formattermediaType>
                </el-table-column>
                <el-table-column  label="状态"  width="" align="center">
                  <template slot-scope="scope">
                     <el-switch v-model="scope.row.status" :active-value=1 :inactive-value=0 @change="changeStatus($event,scope.row)"></el-switch>
                  </template>
                </el-table-column>
                <el-table-column label="操作"  align="center">
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

       

        <!-- 审核提示框 -->
         <el-dialog title="广告创意审核"  :visible.sync="AD_Visible" width="60%" center>
             <el-form ref="FormNewData" :model="FormNewData" label-width="100px" :label-position="labelPosition">
                <el-form-item label="创意名称:">
                  <el-input v-model="FormNewData.name" style="width:80%" disabled=""></el-input>
                </el-form-item>
                <el-form-item label="平台标识:">
                    <el-input v-model="FormNewData.dspId" style="width:80%" disabled=""></el-input>
                </el-form-item>
                <el-form-item label="平台创意ID:">
                    <el-input v-model="FormNewData.extId" style="width:80%" disabled=""></el-input>
                </el-form-item>
                <el-form-item label="广告主名称:">
                    <el-input v-model="FormNewData.advertiserName" disabled="" placeholder="" style="width:80%"></el-input>
                </el-form-item>
                 <el-form-item label="落地页:">
                    <el-input v-model="FormNewData.landpage" disabled="" placeholder="" style="width:80%"></el-input>
                </el-form-item>
                <el-form-item label="广告位ID:">
                    <el-input v-model="FormNewData.positionId" disabled="" placeholder="" style="width:80%"></el-input>
                </el-form-item>
                <el-form-item label="过期时间:">
                    <el-input v-model="FormNewData.expireDate" disabled="" placeholder="" style="width:80%"></el-input>
                </el-form-item>
                <el-form-item label="贴片:">
                      <img :src="FormNewData.entityurl" class="img" alt="" v-if="checkFileType(FormNewData.entityurl) == 'pic' ">
                      <video :src="FormNewData.entityurl" class="img" v-if="checkFileType(FormNewData.entityurl) == 'mp4' " autoplay  controls="controls"></video>
                </el-form-item>
                <el-form-item label="素材预览:">
                      <img :src="FormNewData[item]" alt="" class="img" v-for="(item,index) in imgs" :key="index" v-if="FormNewData[item]"> 
                </el-form-item>
                <el-form-item label="* 行业:">
                    <el-select v-model="FormNewData.industry" placeholder="行业"  class="handle-input">
                        <el-option
                        v-for="item in options"
                        :key="item.id"
                        :label="item.name" 
                        :value="item.id">
                        </el-option>
                      </el-select>
                </el-form-item>
                <el-form-item label="* 审核状态:">
                    <el-radio-group v-model="FormNewData.examinesStatus">
                      <el-radio :label="1">通过</el-radio>
                      <el-radio :label="2">未通过</el-radio>
                    </el-radio-group>
                      <el-input v-model="FormNewData.examinesRemarks" placeholder="未通过理由"  style="width:80%"></el-input>                
                </el-form-item>
             </el-form>
            <span slot="footer" class="dialog-footer">
                <el-button @click="AD_Visible = false">取 消</el-button>
                <el-button type="primary" @click="ADmanageDataEdit"  v-if="this.readonly !=1">保 存</el-button>
            </span>
        </el-dialog>
    </div>
</template>

<script>
var ids = new Map();
var names = new Map();
import { industryManage,advertiserDspadd,upload,flowconsumergetpage,advertiserDspget } from "@/api/Api.js";
import { advertiserDspexamines,advertiserDspupdate } from "@/api/Api.js";

import { entityDspgetList,entityDspget, entityDspexamines,advertiserDspgetList,entityDspupdate} from "@/api/Api.js";

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
      select_extid:'',
      select_name:'',
      select_adDSP:'',
      select_endName:'',
      editVisible: false,
      delVisible: false,
      newVisible: false,
      AD_Visible: false,
      type_: "",
      imgs:[
        'threadPic1',
        'threadPic2',
        'threadPic3',
        'threadPic4',
        'threadPic5',
        'threadPic6',
        'threadPic7',
        'threadPic8',
        'threadPic9',
      ],
      // form
      FormNewData: {
        id: "",
        name:'',
        dspId:'',
        entityurl:'',
        extId: "",
        status:'',
        landpage: "",
        positionId: "",
        expireDate:'',
        examinesStatus:"",
        businesslicense: "",
        examinesRemarks:'',
        advertiserName:'',
        terminal:'',
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
    //  this.getIndustry();
    //  this.getDSP();
  },
 
  methods: {
   checkFileType: function(filename) {  
    let suffix = filename.substring(filename.lastIndexOf('.') + 1).toLowerCase();
            var type="";
            switch (suffix) {
                case 'mp4': 
                    type = 'mp4'; 
                    break;
                case 'ppt': 
                case 'pptx':
                case 'doc':
                case 'docx':
                case 'pdf':
                    type = 'office';
                    break;
                case 'jpg':
                case 'jpeg':
                case 'png':
                case 'bmp':
                case 'gif':
                    type = 'pic';
                    break;
            }
            return type;
  },
         // 行业选项
      getIndustry() {
        industryManage().then(res=>{
      let data = res.data;     
        this.options = data.data
        
        });
      },
       // 状态开关
    changeStatus(val,row){
       let item = row;
             let params = {
                id:item.id,
                status:item.status,
            }
     entityDspupdate(params).then(res=>{
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
         dspCreativeId:this.select_extid,
         name:this.select_name,
         consumerId:names.get(this.select_adDSP),
         advertiserId:ids.get(this.select_endName),
         status:this.value,
         cp:this.cur_page,
         ps:this.ps
         }
        
      entityDspgetList(params).then(res=>{
     let data = res.data;     
       this.tableData = data.data.data
       this.total = data.data.totalItemNum;
      
      });
    },
    getList() {
      let params = {cp:this.cur_page,ps:this.ps}
      entityDspgetList(params).then(res=>{           
      let data = res.data; 
      if(data.code != 'A000000') {
          this.$message.error(data.message);
          return;
        }     
       this.tableData = data.data.data
       this.total = data.data.totalItemNum;

      });
    },
       // 模糊平台名称
     querySearchAD(queryString, cb){
        let params = {
          consumerName:this.select_adDSP,
          cp:this.cur_page,
          ps:this.ps
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
  
    //广告主名称
    querySearchName(queryString, cb){
        let params = {
          name:this.select_endName,
          currentPage:this.cur_page,
          pageSize:this.ps
          }
         advertiserDspgetList(params).then(res=>{
     let data = res.data;
         
          // 调用 callback 返回建议列表的数据
        cb(data.data.data);
        data.data.data.forEach(element => {
           ids.set(element.name,element.id);
        });
       if(data.code != 'A000000') {
          this.$message.error(data.message);
            } 
        });
    },
    handleSelectName(item){
      this.id = item.id
    },
     
  
    // 搜索
     search() {
        this.$refs.pagination.lastEmittedPage = 1
        this.cur_page = 1;
       let params = {
         dspCreativeId:this.select_extid,
         name:this.select_name,
         consumerId:names.get(this.select_adDSP),
         advertiserId:ids.get(this.select_endName),
         status:this.value,
         cp:this.cur_page,
         ps:this.ps
         }
        
      entityDspgetList(params).then(res=>{
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
      });
    },
       // DSP选项
    getDSP() {
      flowconsumergetpage().then(res=>{
     let data = res.data;     
       this.optionsDSP = data.data.data
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
        let params ={
          id:row.id
        }
      entityDspget(params).then(res=>{
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
          if(this.FormNewData.examinesRemarks == ''){
            this.$message.error("未通过理由不能为空");
            return;
          }     
        } 
        if(this.FormNewData.industry ==''){
            this.$message.error("行业不能为空");
            return;
        }  
           let params = {
            id:this.FormNewData.id,
            examinesStatus:this.FormNewData.examinesStatus,
            examinesRemarks:this.FormNewData.examinesRemarks,
            industry:this.FormNewData.industry
            } 
        entityDspexamines(params).then(res=>{
      let data = res.data;
           if(data.code != 'A000000') {
              this.$message.error(data.message);
          }
          this.AD_Visible = false;
          this.search();
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

        let params = {
            name:this.FormNewData.name,
            industryType:this.FormNewData.industryType,
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
    handleAvatarSuccess(res, file) {
         let data = JSON.parse(res);
          this.FormNewData.businesslicense = data.data.url
      },
    handleAvatarSuccess_to(res, file) {
         let data = JSON.parse(res);
          this.FormNewData.qualifications = data.data.url
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
.inline-input{
  width: 150px;
}
 .img{
    display: block;
    width: 500px;
    /* height: 400px; */
}
</style>


