<template>
    <div class="table">
        <div class="container">
            <div class="handle-box">
                <el-button type="primary" icon="search" @click="newgrade">添加地域等级</el-button>
            </div>    
            <div>
            <el-form ref="form" :model="form_dy" label-width="80px">
                <div>
                    <el-form-item label="地域等级">
                        <el-select v-model="value_dy" @change="changeGrade" @focus="edit_DY" class="mrl11">
                                    <el-option
                                        v-for="item in options_dy"
                                        :key="item.id"
                                        :label="item.groupName"
                                        :value="item.id">
                                    </el-option>
                        </el-select>
                        <el-button @click="DeleteGrabble" class="btn" type="danger">删除</el-button>
                      </el-form-item>
                      <el-form-item class="right" label="搜索">
                              <el-input v-model="city" class="mrl11" placeholder="搜索未分配"></el-input>
                              <el-button @click="grabble" class="btn">搜索</el-button>
                      </el-form-item>        
                      <el-form-item label="添加城市">
                        <template>
                          <tree-transfer :title="title" :from_data='data2' pid='areaCode' :to_data='value2' :defaultProps="{label:'areaName'}"  
                            @addBtn='add' @removeBtn='remove' :mode='mode' height='540px' filter openAll>
                          </tree-transfer>
                        </template>
                      </el-form-item>
                  </div>
            </el-form>
            <span slot="footer" class="dialog-footer">
                <el-button @click="DY_Visible = false">取 消</el-button>
                <el-button type="primary" @click="saveEdit" v-if="this.readonly !=1">保 存</el-button>
            </span>
            </div>
        </div>
        
        <!-- 添加地域等级 -->
        <el-dialog title="地域等级管理" :visible.sync="DY_Visible1" width="50%">
          <el-form ref="form" :model="form_dy" label-width="80px">
           <div>
              <el-form-item label="增加地域等级">
                  <el-input v-model="grade" class="handle-input" ></el-input>
                  <el-button @click="Add_grade" class="btn" v-if="this.readonly !=1">添 加</el-button>
              </el-form-item>   
              <el-form-item class="right" label="搜索">
                      <el-input v-model="city" class="mrl11" placeholder="搜索未分配"></el-input>
                      <el-button @click="grabble" class="btn">搜索</el-button>
              </el-form-item>        
              <el-form-item label="添加城市">
                <template>
                   <tree-transfer :title="title" :from_data='data2' pid='areaCode' :to_data='value2' :defaultProps="{label:'areaName'}"  
                     @addBtn='add' @removeBtn='remove' :mode='mode' height='540px' filter openAll>
                   </tree-transfer>
                </template>
              </el-form-item>
            </div>
          </el-form>
        </el-dialog>

         <!-- 删除提示框 -->
        <el-dialog title="提示" :visible.sync="delVisible1" width="300px" center>
          <div class="del-dialog-cnt">删除不可恢复，是否确定删除？</div>
          <span slot="footer" class="dialog-footer">
            <el-button @click="delVisible1 = false">取 消</el-button>
            <el-button type="primary" @click="delGrable">确 定</el-button>
          </span>
        </el-dialog>
    </div>
</template>

<script>
import { industryManage,adPositionPriceaddIndustry,diclist,areaGroupgetList,areaGroupadd,areaGroupgetOptionalArea } from "@/api/Api.js";
import { areaGroupget,areaGroupupdate,adPositiongetAiKaPosition ,adPositionPriceadd,adPositionPricegetList,adPositionPricegetupdate} from "@/api/Api.js";
import { flowsourcegetall,adPositionPriceaupdate,adPositiondeleteIndustry,areaGroupdelete } from "@/api/Api.js";

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
      title:["城市", "包含城市"],
      mode: "transfer", 
      fromData:[],
      toData:[],
      data2: [],
      value2: [],
      mediaName:'',
      territory:'',

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
    this.getIndustry();
    this.getadPositionget();
    this.editDY();
  },

  methods: {
  
 
      // 编辑价格
      newPrice(index, row) {
          this.editVisible1 = true
          let item = row;
          this.form = {
              price: item.price,
              id: item.id,
          }
      },
      // 保存编辑价格
      savePrice() {
          let params = {
              price: this.form.price,
              id: this.form.id,
          }
          adPositionPriceaupdate(params).then(res => {
            let data = res.data;
              if (data.code != 'A000000') {
                  this.$message.error(data.message);
                  return
              } else {
                  this.$message.success("修改成功");
              }
              this.getList();
              this.editVisible1 = false
          });
      },
       //模糊名字
    querySearchName(queryString, cb){
        let params = {
          mediaName:this.select_word,
          currentPage:this.cur_page,
          pageSize:this.ps
          }
         flowsourcegetall(params).then(res=>{
     let data = res.data;
          console.log(data.data.data)
          // 调用 callback 返回建议列表的数据
        cb(data.data.data);
       if(data.code != 'A000000') {
          this.$message.error(data.message);
            } 
        });
    },
    handleSelectName(item){
      // console.log(item);
      this.id = item.id
    },
    // 监听穿梭框组件添加
    add(fromData,toData,obj){
        // 树形穿梭框模式transfer时，返回参数为左侧树移动后数据、右侧树移动后数据、移动的        {keys,nodes,halfKeys,halfNodes}对象
        // 通讯录模式addressList时，返回参数为右侧收件人列表、右侧抄送人列表、右侧密送人列表
        console.log("fromData:", fromData);
        console.log("toData:", toData);
        this.value2 = toData;
        console.log("obj:", obj);
      },
      // 监听穿梭框组件移除
    remove(fromData,toData,obj){
        // 树形穿梭框模式transfer时，返回参数为左侧树移动后数据、右侧树移动后数据、移动的{keys,nodes,halfKeys,halfNodes}对象
        // 通讯录模式addressList时，返回参数为右侧收件人列表、右侧抄送人列表、右侧密送人列表
        console.log("fromData:", fromData);
        console.log("toData:", toData);
        this.value2 = toData;
        console.log("obj:", obj);
      },
     formattermediaType(row, column) {
      if(row.sellType == 111) {
        return "CPM";
      } else if(row.sellType == 112){
        return "CPC";
      }
    },
    // handleChange(value, direction, movedKeys) {
    //   if(direction == 'left') {
    //     this.leftKeys = this.leftKeys.concat(movedKeys);
    //   }else {
    //     movedKeys.forEach(element => {
    //       this.leftKeys.remove(element);
    //     });
    //   }
    //   console.log(this.leftKeys);
    //   // console.log(value, direction, movedKeys);
    // },
    // 分页导航
    handleCurrentChange(val) {
      this.cur_page = val;
          let params = {
         industry:this.industryType,
         areaLevel:this.territory,
         terminal:this.value,
         positionName:this.select_word,
         cp:this.cur_page,
         ps:this.ps
          }
      adPositionPricegetList(params).then(res=>{
     let data = res.data;
      if(data.code != 'A000000') {
          this.$message.error(data.message);
          return;
        }     
       this.data = data.data.data
       this.total = data.data.totalItemNum;
      });
     
    },
     getList() {
      let params = {
          cp:this.cur_page,
          ps:this.ps
        }
        adPositionPricegetList(params).then(res=>{
     let data = res.data;     
       this.data = data.data.data
       this.total = data.data.totalItemNum;
      console.log(data)
      });
    },
         // 广告位选项
      getadPositionget() {
        adPositiongetAiKaPosition().then(res=>{
      let data = res.data;     
        this.options_ad = data.data
         console.log(data);
        });
      },
          // 行业选项
      getIndustry() {
        industryManage().then(res=>{
      let data = res.data;     
        this.options = data.data
         console.log(this.options);
        });
      },
   

    search() {
      this.$refs.pagination.lastEmittedPage = 1
      this.cur_page = 1;
       let params = {
         industry:this.industryType,
         areaLevel:this.territory,
         terminal:this.value,
         positionName:this.select_word,
         cp:this.cur_page,
         ps:this.ps
          }
      adPositionPricegetList(params).then(res=>{
     let data = res.data;
      if(data.code != 'A000000') {
          this.$message.error(data.message);
          return;
        }     
       this.data = data.data.data
       this.total = data.data.totalItemNum;
      });
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
   
    // 新建底价
    create() {
      this.newVisible = true;
      this.form = {};
    },
    // 确认
    saveNew() {
         // 参数判断
      if(!this.form.price) {
        this.$message.error("价格名称不能为空");
        return;
      }
      if(!this.adPosition) {
        this.$message.error("广告位不能为空");
        return;
      }
      let params = {
          positionId:this.adPosition,
          price:this.form.price
        }
      adPositionPriceadd(params).then(res=>{
      let data = res.data;
          // console.log(res)
        if(data.code != 'A000000') {
          this.$message.error(data.message);
          }
          this.newVisible = false;
          this.getList();
      });  
   
    },
     // 行业管理
    edit_HY() {
       let params = {
          gid:26
        }
      diclist(params).then(res=>{
      let data = res.data;
          // console.log(res)
        if(data.code != 'A000000') {
          this.$message.error(data.message);
          }
         this.dynamicTags = data.data
         this.HY_Visible = true;
      });    
    },
    // 添加行业
    Add_HY(){
        let params = {
          industryName:this.state1
        }
      adPositionPriceaddIndustry(params).then(res=>{
      let data = res.data;
          console.log(res)
        if(data.code != 'A000000') {
          this.$message.error(data.message);
          return;
          }
         this.edit_HY();
         this.getList();
      });
    },
        // 行业删除
        handleClose(tag) {
            let params = {
                industryId: tag.id,
            }
            adPositiondeleteIndustry(params).then(res => {
                let data = res.data;
                if (data.code != 'A000000') {
                    this.$message.error(data.message);
                    return;
                }
              this.edit_HY();
            });
        },
     // 搜索地域管理
    editDY() {
      this.value_dy ='';
      this.grade ='';
      this.city='';
      this.data2 = [];
      this.value2 = [];
      // 地域等级
        let params = {
          groupType:2
        }
      areaGroupgetList(params).then(res=>{
      let data = res.data;
        console.log(res)
        if(data.code != 'A000000') {
          this.$message.error(data.message);
          }
         this.options_dy = data.data
      });
    },
     // 地域管理
    edit_DY() {
      this.value_dy ='';
      this.grade ='';
      this.city='';
      this.data2 = [];
      this.value2 = [];
      // 地域等级
        let params = {
          groupType:2
        }
      areaGroupgetList(params).then(res=>{
      let data = res.data;
        console.log(res)
        if(data.code != 'A000000') {
          this.$message.error(data.message);
          return;
          }
         this.options_dy = data.data
         this.DY_Visible = true;
      });
    },
    newgrade(){
        this.value_dy ='';
        this.grade ='';
        this.city='';
        this.data2 = [];
        this.value2 = [];
        this.DY_Visible1 = true
    },
    // 添加地域等级
    Add_grade(){
        if (!this.grade) {
            this.$message.error("等级不能为空");
            return;
        }
          let keys = '';
          this.value2.forEach(element => {
            keys= keys + element.id+","
          });
        let params = {
          groupType:2,
          groupName :this.grade,
          areaIds:keys
        }
      areaGroupadd(params).then(res=>{
      let data = res.data;
        console.log(res)
        if(data.code != 'A000000') {
          this.$message.error(data.message);
          return;
          }
         this.$message('添加成功');
        //  this.edit_DY();
         this.DY_Visible1 = false;
      });
    },
    // 删除地域等级
    DeleteGrabble(){
      this.delVisible1 = true;
    },
    delGrable(){
      if(!this.value_dy){
          this.$message('删除等级不能为空')
          return;
      }
         let params = {
        areaGroupId: this.value_dy
      };
      areaGroupdelete(params).then(res => {
        console.log(res);
        //let data = res;
        if (res.data.code != "A000000") {
          this.$message.error(res.data.message);
        } else {
          this.$message.success("删除成功");
          this.editDY();
          this.delVisible1 = false;
        }
      });
    },

// 城市等级搜索
    changeGrade(){
         let params = {
          id:this.value_dy
        }
      areaGroupget(params).then(res=>{
      let data = res.data;
        console.log(res)
        if(data.code != 'A000000') {
          this.$message.error(data.message);
          return;
          }
        //   let keys =[];
        //   data.data.areaList.forEach(element => {
        //     keys.push(element.id);
        //   });
        // this.leftKeys = keys;
        if(!data.data){
          this.value2 =[]
        }else{
          this.value2 = data.data.areaList
        }
      });
    },
    // 搜索
     grabble(query, item) {
          let params = {
            areaName:this.city
          }
          areaGroupgetOptionalArea(params).then(res=>{
          let data = res.data; 
            let array =[];
            this.data2 = data.data;
            // this.cities = data.data;
            // data.data = data.data.filter(item => item.isDistribution!=1 )
            // data.data.forEach(element => {
            //      array.push(element.id);
            // });
            // this.data2 = this.data2.concat(data.data);
            // this.value2 = array;
          });

        },
         // 地域保存
      saveEdit() {
        let keys = '';
        this.value2.forEach(element => {
          keys= keys + element.id+","
        });
        let params = {
            groupType:2,
            id:this.value_dy,
            areaIds:keys
          }
          console.log(params)
        areaGroupupdate(params).then(res=>{
        let data = res.data;
            // console.log(res)
          if(data.code != 'A000000') {
            this.$message.error(data.message);
            return;
            }
            this.$message('保存成功')
            this.DY_Visible = false;     
        });
      },
    

    priceEDit() {
       this.delVisible = true;
    },
    
    Editmark() {
       let params = {
            price:this.editPrice
          }
      adPositionPricegetupdate(params).then(res=>{
        let data = res.data;
            // console.log(res)
          if(data.code != 'A000000') {
            this.$message.error(data.message);
            }
             this.delVisible = false;
             this.getList();
        });

    },
    // 选择模板
    select_template() {
      this.template_dialog = true;
    },
    template_(){

    }
   
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
    margin-top: -10px;
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

