<template>
    <div class="table">
        <div class="container">
            <div class="handle-box">
                <el-input v-model="select_word" placeholder="请输入广告位名称" class="handle-input mr10"></el-input>
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
                 <el-button type="primary" icon="search" @click="create">新建广告位包</el-button>
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
                <el-table-column  label="状态"  width="" align="center">
                  <template slot-scope="scope">
                     <el-switch v-model="scope.row.status"  :active-value=1 :inactive-value=0></el-switch>
                  </template>
                </el-table-column>
                <el-table-column label="操作" width="200" align="center">
                    <template slot-scope="scope">
                        <el-button size="small" type="success" @click="handleEdit(scope.$index, scope.row)">编辑</el-button>
                        <!-- <el-button size="small" type="danger" @click="handleDelete(scope.$index, scope.row)">删除</el-button> -->
                        <el-button size="small" type="success" @click="handAD(scope.$index, scope.row)">复制</el-button>
                    </template>
                </el-table-column>
            </el-table>
            <div class="pagination">
                <el-pagination @current-change="handleCurrentChange" layout="total,prev, pager, next,jumper" :total="total">
                </el-pagination>
            </div>
        </div>

        <!-- 编辑弹出框 -->
        <el-dialog title="编辑" :visible.sync="editVisible" width="40%">
            <el-form ref="form" :model="form" label-width="100px">
                <el-form-item label="模板名称:">
                    <el-input v-model="form.name"></el-input>
                </el-form-item>
                <el-form-item label="标题:">
                      限 <el-input style="width:70px" v-model="form.moduleList[0].wordLimit" size="mini"></el-input><span class='font'>字</span>
                </el-form-item>
                <el-form-item label="描述:">
                      限 <el-input style="width:70px" v-model="form.moduleList[1].wordLimit" size="mini"></el-input><span class='font'>字</span>
                </el-form-item>
                <el-form-item label="ICON:">
                      宽 <el-input style="width:70px" v-model="form.moduleList[2].width" size="mini"></el-input><span class='font'>PX</span>
                      高 <el-input style="width:70px" v-model="form.moduleList[2].height" size="mini"></el-input><span class='font'>px</span>
                </el-form-item>
                <el-form-item label="视频:">
                      宽 <el-input style="width:70px" v-model="form.moduleList[3].width" size="mini"></el-input><span class='font'>PX</span>
                      高 <el-input style="width:70px" v-model="form.moduleList[3].height" size="mini"></el-input><span class='font'>px</span>
                      时长 <el-input style="width:70px" v-model="form.moduleList[3].duration" size="mini"></el-input><span class='font'>S</span>
                </el-form-item>
                <el-form-item label="主图片:">
                  <div v-for="(video,index) in form.moduleList[4]" :key="index">
                     宽 <el-input style="width:70px" v-model="video.width" size="mini"></el-input><span class='font'>PX</span>
                     高 <el-input style="width:70px" v-model="video.height" size="mini"></el-input><span class='font'>px</span>
                  </div>
                     <!-- <el-button type="primary" icon="el-icon-plus" @click="addImg"></el-button> -->
                </el-form-item>
            </el-form>
            <span slot="footer" class="dialog-footer">
                <el-button @click="editVisible = false">取 消</el-button>
                <el-button type="primary" @click="saveEdit">确 定</el-button>
            </span>
        </el-dialog>

          <!-- 新建弹出框 -->
        <el-dialog title="新建广告模板:" :visible.sync="newVisible" width="40%">
            <el-form ref="form" :model="form" label-width="100px">
                <el-form-item label="模板名称:">
                    <el-input v-model="form.name"></el-input>
                </el-form-item>
                <el-form-item label="标题:">
                      限 <el-input style="width:70px" v-model="form.moduleList[0].wordLimit" size="mini"></el-input><span class='font'>字</span>
                </el-form-item>
                <el-form-item label="描述:">
                      限 <el-input style="width:70px" v-model="form.moduleList[1].wordLimit" size="mini"></el-input><span class='font'>字</span>
                </el-form-item>
                <el-form-item label="ICON:">
                      宽 <el-input style="width:70px" v-model="form.moduleList[2].width" size="mini"></el-input><span class='font'>PX</span>
                      高 <el-input style="width:70px" v-model="form.moduleList[2].height" size="mini"></el-input><span class='font'>px</span>
                </el-form-item>
                <el-form-item label="视频:">
                      宽 <el-input style="width:70px" v-model="form.moduleList[3].width" size="mini"></el-input><span class='font'>PX</span>
                      高 <el-input style="width:70px" v-model="form.moduleList[3].height" size="mini"></el-input><span class='font'>px</span>
                      时长 <el-input style="width:70px" v-model="form.moduleList[3].duration" size="mini"></el-input><span class='font'>S</span>
                </el-form-item>
                <el-form-item label="主图片:">
                  <div v-for="(video,index) in form.moduleList[4]" :key="index">
                     宽 <el-input style="width:70px" v-model="video.width" size="mini"></el-input><span class='font'>PX</span>
                     高 <el-input style="width:70px" v-model="video.height" size="mini"></el-input><span class='font'>px</span>
                  </div>
                     <el-button type="primary" icon="el-icon-plus" @click="addImg"></el-button>
                </el-form-item>
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
import { templateAdd } from "@/api/Api.js";


export default {
  name: "basetable",
  data() {
    return {
      url: "./static/vuetable.json",
      tableData: [],
      cur_page: 1,
      total: 1,
      ps:10,
      select_word: "",
      del_list: [],
      editVisible: false,
      delVisible: false,
      newVisible: false,
      newaddAD: false,
      template_dialog: false,
      currentRow: null,
      radio:'',
      matierialType:'',
      status:'',
      form: {
        name:'',
        moduleList: [
          {wordLimit:''},{wordLimit:''},{width:'',height:''},
          {width:'',height:'',duration:''},[{width:'',height:''}]]
      },
      idx: -1,
      flow_type: 1,
      type: 1,
      system: 1,
      Access: "API",
      display_form: 1,
      // 类型
      optionType: [
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
          value: "4",
          label: "视频"
        },
        {
          value: "5",
          label: "图片"
        }
      ],
       optionStatus: [
        {
          value: "1",
          label: "有效"
        },
        {
          value: "2",
          label: "无效"
        },
      ],
     
    };
  },
  created() {
        this.getList();
  },
 
  methods: {
    // 分页导航
    handleCurrentChange(val) {
      this.cur_page = val;
      this.getList();
    },
    // 获取 table
    getList() {
      let params = {
        status:1,
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
     let params = {status:this.status,name:this.select_word,materialName:this.matierialType}
     templateList(params).then(res=>{
     let data = res.data;     
       this.tableData = data.data.data
    });
    },
    formatter(row, column) {
      return row.address;
    },
    filterTag(value, row) {
      return row.tag === value;
    },
    // 编辑
    handleEdit(index, row) {
      this.idx = index;
      this.item = row;
      let map= new Map();let imgs = [];
      for(var i=0;i<row.templateModuleRelationList.length;i++) {
        if(row.templateModuleRelationList[i].mid >=5) {
          imgs.push(row.templateModuleRelationList[i]);
        }
        map.set(row.templateModuleRelationList[i].mid,row.templateModuleRelationList[i])
      }
      this.form = {
        moduleList: [
          {wordLimit:map.get(0)?map.get(0).wordLimit:''},
          {wordLimit:map.get(1)?map.get(1).wordLimit:''},
          {width:map.get(2)?map.get(2).width:'',height:map.get(2)?map.get(2).height:''},
          {width:map.get(3)?map.get(3).width:'',height:map.get(3)?map.get(3).height:'',
          duration: map.get(4)?map.get(3).duration:''},
          imgs.length >0?imgs:[{width:'',height:''}]
        ]
      }
      this.editVisible = true;
    },
     // 保存编辑
    saveEdit() {
      // let params = {
      //   id:this.form.id,
      //   type:this.form.type,
      //   status:this.form.status,
      //   password: this.form.password,
      //   linkMan:this.form.linkMan,
      //   phone:this.form.phone,
      //   remark:this.form.remark,
      //   authsList:JSON.stringify(this.types)
      //   }
    
      //  UserUpdate(JSON.stringify(params)).then(res=>{
      //   let data = res.data;
      //     if(data.code != 'A000000') {
      //         this.$message.error(data.message);
      //     }
      //        this.getList();
      //   });
      this.editVisible = false;
    },
    handleDelete(index, row) {
      this.idx = index;
      this.delVisible = true;
    },
    // 点击添加广告位
    handAD() {
      this.newaddAD = true;
    },
    delAll() {
      const length = this.multipleSelection.length;
      let str = "";
      this.del_list = this.del_list.concat(this.multipleSelection);
      for (let i = 0; i < length; i++) {
        str += this.multipleSelection[i].name + " ";
      }
      this.$message.error("删除了" + str);
      this.multipleSelection = [];
    },
    handleSelectionChange(val) {
      this.multipleSelection = val;
    },
   
    // 新建
    create() {
      this.newVisible = true;
      this.form = { 
        name:'',
        moduleList: [
          {wordLimit:'',mid:1},{wordLimit:'',mid:2},{width:'',height:'',mid:3},
          {width:'',height:'',duration:'',mid:4},[{width:'',height:''}]]
      };
    },
        // 新建确认
    saveNew() {
      if(!this.form.name) {
        this.$message.error("模版名称不能为空");
        return;
      }
      if(this.form.moduleList.length ==0 ) {
        this.$message.error("组件不能为空");
        return;
      }
      let params = {
        moduleList: this.form.moduleList,
        name: this.form.name,
      }
      templateAdd(JSON.stringify(params)).then(res=>{
      let data = res.data;     
        //  this.tableData = data.data.data
        console.log(res)
        });
        this.newVisible = false;
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
    // 选择模板
    select_template() {
      this.template_dialog = true;
    },
    handleCurrentChange(val) {
        this.currentRow = val;
    },
    template_(){

    },
    addImg(){
      // console.log(this.form[0]);return;
      this.form.moduleList[4].push({width:'',height:''});
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
