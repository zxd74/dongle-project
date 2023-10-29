<template>
  <div class="table">
    <div class="container">
      <div class="header-select">
        <template>
          <el-input v-model="idear_id" placeholder="创意ID" class="handle-input mr10"></el-input>
          <el-input v-model="idear_name" placeholder="创意名称" class="handle-input mr10"></el-input>
          <el-autocomplete
            class="inline-input"
            v-model="state1"
            value-key="fullName"
            :fetch-suggestions="querySearch"
            placeholder="客户名称"
            @select="handleSelect"
          ></el-autocomplete>
          <el-select v-model="optionsState" placeholder="状态">
            <el-option
              v-for="item in options"
              :key="item.value"
              :label="item.label"
              :value="item.value"
            ></el-option>
          </el-select>
          <el-button type="primary" icon="search" @click="search">搜索</el-button>
        </template>
      </div>
      <el-table :data="tableData" border style="width: 100%" ref="multipleTable">
        <el-table-column prop="id" label="创意ID" align="center"></el-table-column>
        <el-table-column prop="entityName" label="创意名称" align="center"></el-table-column>
        <el-table-column prop="orderName" label="计划名称" align="center"></el-table-column>
        <el-table-column prop="putName" label="投放名称" align="center"></el-table-column>
        <el-table-column prop="posName" label="广告位" align="center"></el-table-column>
        <el-table-column prop="adverName" label="客户" align="center"></el-table-column>
        <!-- <el-table-column prop="agentName" label="代理商" align="center"></el-table-column> -->
        <el-table-column prop="entityState" label="状态" align="center" :formatter="formatterType"></el-table-column>
        <el-table-column label="操作" align="center" v-if="this.readonly !=1">
          <template slot-scope="scope">
            <el-button
              icon="el-icon-edit"
              size="small"
              type="success"
              @click="handleEdit(scope.$index, scope.row)"
            ></el-button>
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
    <el-dialog title="创意审核" :visible.sync="editVisible" width="60%">
      <el-form ref="form" :model="form" status-icon label-width="100px"  :label-position="labelPosition">
        <el-form-item label="创意名称:">{{entityName}}</el-form-item>
        <el-form-item label="广告位名称:">{{posName}}</el-form-item>
        <el-form-item label="落地页:">{{landUrl}}</el-form-item>
        <el-form-item label="投放时间段:">{{timeInterval}}</el-form-item>
        <el-form-item label="投放日期:">
          <!-- {{beginTime-endTime}} -->
          {{Time}}
        </el-form-item>
        <el-form-item label="APP定向:">{{entityType}}</el-form-item>
        <el-form-item label="广告主:">{{adverName}}</el-form-item>
        <el-form-item label="物料地址:">{{entityUrl}}</el-form-item>
        <!-- <el-form-item label="物料预览:">
                    <div class="bg_img" v-for="(item,index) in list_url" :key="index">
                        <img :src="entityUrl_img" alt="" v-if="this.layout == 'jpg'||this.layout == 'png'||this.layout == 'jpeg'||this.layout == 'gif'">
                        <video :src="entityUrl_mp4" class="video" v-if="this.layout == 'mp4'"></video>
                    </div>
        </el-form-item>-->
        <el-form-item label="标题:" v-if="map[1]">
          <el-input v-model="map[1].moduleValue" style="width:80%"></el-input>
        </el-form-item>
        <el-form-item label="描述:" v-if="map[2]">
          <el-input v-model="map[2].moduleValue"></el-input>
        </el-form-item>
        <el-form-item label="头像:" v-if="map[3]">
          <!-- <el-input v-model="form.userPortrait" style="width:50%"></el-input> -->
          <a :href="map[3].moduleValue" target="_block">
            <img :src="map[3].moduleValue" class="bg_img">
          </a>
        </el-form-item>
        <el-form-item label="视频:" v-if="map[4]" target="_block">
          <!-- <el-input v-model="form.entityUrl"></el-input> -->
          <a :href="map[4].moduleValue" target="_blank">
            <video :src="map[4].moduleValue" class="video"></video>
          </a>
        </el-form-item>
        <el-form-item label="图片1:" v-if="map[5]">
          <!-- <el-input v-model="form.threadPic1"></el-input> -->
          <a :href="map[5].moduleValue" target="_blank">
            <img :src="map[5].moduleValue" class="bg_img">
          </a>
        </el-form-item>
        <el-form-item label="图片2:" v-if="map[6]">
          <!-- <el-input v-model="form.threadPic1"></el-input> -->
          <a :href="map[6].moduleValue" target="_blank">
            <img :src="map[6].moduleValue" class="bg_img">
          </a>
        </el-form-item>
        <el-form-item label="图片3:" v-if="map[7]">
          <a :href="map[7].moduleValue" target="_blank">
            <img :src="map[7].moduleValue" class="bg_img">
          </a>
        </el-form-item>
        <el-form-item label="图片4:" v-if="map[8]">
          <a :href="map[8].moduleValue" target="_blank">
            <img :src="map[8].moduleValue" class="bg_img">
          </a>
        </el-form-item>
        <el-form-item label="图片5:" v-if="map[9]">
          <a :href="map[9].moduleValue" target="_blank">
            <img :src="map[9].moduleValue" class="bg_img">
          </a>
        </el-form-item>
        <el-form-item label="图片6:" v-if="map[10]">
          <a :href="map[10].moduleValue" target="_blank">
            <img :src="map[10].moduleValue" class="bg_img">
          </a>
        </el-form-item>
        <el-form-item label="图片7:" v-if="map[11]">
          <a :href="map[11].moduleValue" target="_blank">
            <img :src="map[11].moduleValue" class="bg_img">
          </a>
        </el-form-item>
        <el-form-item label="图片8:" v-if="map[12]">
          <a :href="map[12].moduleValue" target="_blank">
            <img :src="map[12].moduleValue" class="bg_img">
          </a>
        </el-form-item>
        <el-form-item label="图片9:" v-if="map[13]">
          <a :href="map[13].moduleValue" target="_blank">
            <img :src="map[13].moduleValue" class="bg_img">
          </a>
        </el-form-item>
        <el-form-item v-if="map[14]" label="激励视频"> 
              <el-button type="primary" @click="hand()" target="_blank">查看</el-button>
        </el-form-item>
        <el-form-item label="行业:">{{industryName}}</el-form-item>
      </el-form>
      <span class="footer">
        <el-button type="primary" @click="saveEditPass()">确 定(通过）</el-button>
        <el-button @click="saveEditReject()">取 消（拒绝）</el-button>
        <el-input v-model="auditComments" placeholder="请输入拒绝理由" style="width:40%"></el-input>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import { auditentitypages } from "@/api/Api.js";
import { auditentityauditinfo } from "@/api/Api.js";
import { auditentityaudit } from "@/api/Api.js";

import { CustomerList } from "@/api/Api.js";


const uuid = new Map();
export default {
  name: "basetable",
  data() {
    return {
      labelPosition: 'left',
      url: "./static/vuetable.json",
      tableData: [],
      map: {
        1: null,
        2: null,
        3: null,
        4: null,
        5: null,
        6: null,
        7: null,
        8: null,
        9: null,
        10: null,
        11: null,
        12: null,
        13: null
      },
      cur_page: 1,
      total: 1,
      ps: 10,
      select_cate: "",
      idear_id: "",
      idear_name: "",
      del_list: [],
      state1: "",
      is_search: false,
      editVisible: false,
      delVisible: false,
      newVisible: false,
      newaddAD: false,
      template_dialog: false,
      currentRow: null,
      add_time: "",
      auditComments: "",
      entityName: "",
      entityType: "",
      landUrl: "",
      timeInterval: "",
      Time: "",
      mediaName: "",
      adverName: "",
      agentName: "",
      entityUrl: "",
      entityUrl_img: "",
      entityUrl_mp4: "",
      list_url: [],
      posName:'',
      industryName:'',
      fileList2: [
        {
          name: "food.jpeg",
          url:
            "https://fuss10.elemecdn.com/3/63/4e7f3a15429bfda99bce42a18cdd1jpeg.jpeg?imageMogr2/thumbnail/360x360/format/webp/quality/100"
        },
        {
          name: "food2.jpeg",
          url:
            "https://fuss10.elemecdn.com/3/63/4e7f3a15429bfda99bce42a18cdd1jpeg.jpeg?imageMogr2/thumbnail/360x360/format/webp/quality/100"
        }
      ],
      form: {
        posName:'',
        name: "",
        date: "",
        switch: false,
        add_time: "",
        orderName: "",
        putNAme: "",
        landUrl: "",
        entityName: "",
        imageUrl: "",
        threadTitle: "",
        threadContent: "",
        entityUrl: "",
        userPortrait: "",
        threadPic1: "",
        threadPic2: "",
        threadPic3: "",
        threadPic4: "",
        threadPic5: "",
        threadPic6: "",
        threadPic7: "",
        threadPic8: "",
        threadPic9: ""
      },
      id: "",
      idx: -1,
      type: 1,
      types: null,
      flow_management: null,
      data_report: null,
      optionsState: "",
      layout: "",
      options: [
        {
          value: "",
          label: "全部状态"
        },
        {
          value: "1",
          label: "通过"
        },
        {
          value: "2",
          label: "人工审核未通过"
        },
        {
          value: "3",
          label: "人工待审核"
        },
        {
          value: "13",
          label: "机审待审核"
        },
        {
          value: "14",
          label: "机审未通过"
        },
        {
          value: "15",
          label: "黑名单"
        },
        {
          value: "16",
          label: "创意待修改"
        },
      ],

      value: "1",
      readonly:''
    };
  },
  created() {
    this.readonly = this.$store.state.map.get(this.$store.state.currentPath);
    this.getList();
  },

  methods: {
     hand(){
         this.$router.push({
             name:'index', 
             params:{
                 script:this.map[14].moduleValue,
                 }
             })
    },
    // 格式化数据
    formatterType(row, column) {
      if (row.entityState == 1) {
        return <span class="bule">通过</span>;
      } else if (row.entityState == 2) {
        return <span class="red">人工审核未通过</span>;
      } else if (row.entityState == 3) {
        return <span class="yeloow">人工待审核</span>;
      } else if (row.entityState == 13) {
        return <span class="green">机审待审核</span>;
      }else if (row.entityState == 14) {
        return <span class="green">机审未通过</span>;
      }else if (row.entityState == 15) {
        return <span class="green">黑名单</span>;
      }else if (row.entityState == 16) {
        return <span class="green">创意待修改</span>;
      }
    },
    // 分页导航
    handleCurrentChange(val) {
      this.cur_page = val;
        let params = {
        id: this.idear_id,
        entityName: this.idear_name,
        adverId: uuid.get(this.state1),
        entityState: this.optionsState,
        cp: this.cur_page,
        ps: this.ps
      };
      auditentitypages(params).then(res => {
        let data = res.data;
        this.tableData = res.data.data.data;
        this.total = res.data.data.totalItemNum;
        console.log(res);
      });
    },

    getList() {
      let params = { cp: this.cur_page, ps: this.ps };
      auditentitypages(params).then(res => {
        console.log(res)
        let data = res.data;
        this.tableData = res.data.data.data;
        this.total = res.data.data.totalItemNum;
        console.log(res);
      });
    },
    // 搜索
    search() {
      let  reg=/^\+?[1-9][0-9]*$/;
      if(this.idear_id != ''){
          if(!reg.test(this.idear_id)){
          this.$message('请输入正确的ID')
          return;
        }
      }
    
      this.$refs.pagination.lastEmittedPage = 1
      this.cur_page = 1;
      let params = {
        id: this.idear_id,
        entityName: this.idear_name,
        custId: uuid.get(this.state1),
        entityState: this.optionsState,
        cp: this.cur_page,
        ps: this.ps
      };
      auditentitypages(params).then(res => {
         let data = res.data;
        this.tableData = res.data.data.data;
        this.total = res.data.data.totalItemNum;
      });
    },
    // 模糊搜索客户名称
    querySearch(queryString, cb) {
      let params = { type: 2, cp: this.cur_page, ps: this.ps,fullName:this.state1 };
      CustomerList(params).then(res => {
      let data = res.data;
        // 调用 callback 返回建议列表的数据
         data.data.data.forEach(element => {
          uuid.set(element.fullName, element.id);  
        });
        cb(data.data.data);
        if (data.code != "A000000") {
          this.$message.error(data.message);
        }
        //  this.dynamicTags = data.data
        //  this.QX_Visible = true;
      });
    },
    handleSelect(item) {
      // console.log(item);
      this.ids = item.id;
    },

    formatter(row, column) {
      return row.address;
    },
    filterTag(value, row) {
      return row.tag === value;
    },
    // 编辑
    handleEdit(index, row) {
      this.map = {
        1: null,
        2: null,
        3: null,
        4: null,
        5: null,
        6: null,
        7: null,
        8: null,
        9: null,
        10: null,
        11: null,
        12: null,
        13: null,
        14: null
      }
      let item = row;
      let params = { id: item.id };
      this.id = item.id;
      auditentityauditinfo(params).then(res => {
        console.log(res)
        let list = res.data.data.moduleRelations;
        list.forEach(element => {
          // if(element.mid>=5) {
          // }
          this.map[element.mId] = element;
          this.form.putNAme = res.data.data.pid;
          this.form.landUrl = res.data.data.landUrl;
          this.form.entityName = res.data.data.entityName;
          this.form.threadTitle = res.data.data.threadTitle;
          this.form.threadContent = res.data.data.threadContent;
          this.form.entityUrl = res.data.data.entityUrl;
               

          this.form.userPortrait = res.data.data.userPortrait;
          this.form.threadPic1 = res.data.data.threadPic1;
          this.form.threadPic2 = res.data.data.threadPic2;
          this.form.threadPic3 = res.data.data.threadPic3;
          this.form.threadPic4 = res.data.data.threadPic4;
          this.form.threadPic5 = res.data.data.threadPic5;
          this.form.threadPic6 = res.data.data.threadPic6;
          this.form.threadPic7 = res.data.data.threadPic7;
          this.threadPic8 = res.data.data.threadPic8;
          this.form.threadPic9 = res.data.data.threadPic9;
        });
        console.log(this.map)

        this.entityName = res.data.data.entityName;
        this.posName = res.data.data.posName;
        this.entityType = res.data.data.appName;
        this.landUrl = res.data.data.landUrl;
        this.timeInterval = res.data.data.timeInterval;
        this.Time =
          res.data.data.beginTime.substring(0, 10) +
          "至" +
          res.data.data.endTime.substring(0, 10);
        this.mediaName = res.data.data.mediaName;
        this.adverName = res.data.data.adverName;
        this.agentName = res.data.data.agentName;
        // this.entityUrl = res.data.data.entityUrl;
        if(res.data.data.entityUrl == null){
              this.entityUrl = res.data.data.threadPic1;
          }else{
              this.entityUrl = res.data.data.entityUrl;
          }
        this.auditComments =res.data.data.auditComments
        this.industryName =res.data.data.industryName
        // //  视频图片判断
        //   let suffix = res.data.result.entityUrl;
        //   let flieArr = suffix.split('.');
        //   suffix = flieArr[flieArr.length - 1];
        //   if(suffix == 'jpg'||suffix == 'png'||suffix == 'jpeg'||suffix == 'gif'){
        //     this.entityUrl_img = res.data.result.entityUrl;

        //   } else if(suffix == 'mp4'){
        //      this.entityUrl_mp4 = res.data.result.entityUrl;
        //   }
        //   this.layout =suffix
        //   console.log(this.layout)
            //  this.GetIframe()
      });
 
      this.editVisible = true;
    },
     render: function() {
        function renderWord() {
            this.ctx.clearRect(0, 0, this.canvas.width, this.canvas.height)
            this.ctx.drawImage(this.video, 0, 0, this.canvas.width, this.canvas.height)
            wordObj.forEach(function (item, index) {
                item.draw()
            })
            setTimeout(renderWord.bind(this), 0)
        }
        setTimeout(renderWord.bind(this), 0)
    },
    GetIframe(){
      var iframe = document.getElementById("script");
      iframe.srcdoc=this.map[14].moduleValue;
      // var doc = document.all ?
      // iframe.contentWindow.document : iframe.contentDocument;
      // doc.open();
      // doc.write(this.map[14].moduleValue);
      // doc.close();
      //Iframe.innerHTML = this.map[14].moduleValue;

    },
    //通过
    saveEditPass() {
      let params = {
        objId: this.id,
        auditState: 1
      };
      auditentityaudit(params).then(res => {
        if (res.data.code != "A000000") {
          this.$message.error(res.data.message);
        }
        setTimeout(() => {
          
        }, 1000);
      this.editVisible = false;
      this.search();
      });
   
    },
    //拒绝
    saveEditReject() {
      if (this.auditComments == "") {
        this.$message.error("拒绝理由不能为空");
        return;
      }
      let params = {
        objId: this.id,
        auditState: 0,
        auditComments: this.auditComments
      };

      auditentityaudit(params).then(res => {
        if (res.data.code != "A000000") {
          this.$message.error(res.data.message);
        }
         setTimeout(() => {
          
        }, 1000);
        // console.log(res)
        this.search();
        this.editVisible = false;
      });
    },
    handleDelete(index, row) {
      this.idx = index;
      this.delVisible = true;
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
      this.form = {};
    },
    saveNews() {
      this.tableData.push(this.form);
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
    template_() {},
    handleRemove(file, fileList) {
      console.log(file, fileList);
    },
    handlePreview(file) {
      console.log(file);
    },

    matchType(fileName) {
      // 后缀获取
      var suffix = "";
      // 获取类型结果
      var result = "";
      try {
        var flieArr = fileName.split(".");
        suffix = flieArr[flieArr.length - 1];
      } catch (err) {
        suffix = "";
      }
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
  width: 200px;
  display: inline-block;
}
.del-dialog-cnt {
  font-size: 16px;
  text-align: center;
}
.header-select {
  margin-bottom: 20px;
}
.mr10 {
  width: 180px;
}
.bg_img {
  width: 450px;
  height: 300px;
  /* background: skyblue */
}
.video {
  width: 450px;
  height: 300px;
}
.footer {
  margin-left: 100px;
}
.img_ {
  width: 80px;
  height: 40px;
}
.inline-input {
  width: 180px;
}
.bule {
  color: #006621;
  font-weight: bold;
}
.red {
  color: red;
  font-weight: bold;
}
.yeloow {
  color: #ff9f00;
  font-weight: bold;
}
.green {
  color: bisque;
  font-weight: bold;
}
</style>




