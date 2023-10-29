<template>
    <div class="table">
        <div class="container">
            <div class="handle-box">
                <el-input v-model="select_word" placeholder="请输入投放名称" class="handle-input mr10"></el-input>
                <el-button type="primary" icon="search" @click="search">搜索</el-button>
                 <el-button type="primary" icon="search" @click="create">新建投放</el-button>
            </div>

            <el-table :data="data" border style="width: 100%" ref="multipleTable">
                <el-table-column prop="date" label="序号" width="150" align="center">
                </el-table-column>
                <el-table-column prop="date" label="投放名称" align="center">
                </el-table-column>
                <el-table-column  label="状态"  width="" align="center">
                  <template slot-scope="scope">
                     <el-switch v-model="scope.row.switch"></el-switch>
                  </template>
                </el-table-column>
                <el-table-column label="操作"   align="center">
                    <template slot-scope="scope">
                        <el-button size="small" type="success" @click="handleEdit(scope.$index, scope.row)">编辑</el-button>
                        <el-button size="small" type="danger" @click="handleDelete(scope.$index, scope.row)">删除</el-button>
                    </template>
                </el-table-column>
            </el-table>
            <div class="pagination">
              <el-pagination @current-change="handleCurrentChange" layout="total,prev, pager, next,jumper" :total="total">
                </el-pagination>
            </div>
        </div>

        <!-- 编辑弹出框 -->
        <el-dialog title="编辑" :visible.sync="editVisible" width="50%">
            
            <span slot="footer" class="dialog-footer">
                <el-button @click="editVisible = false">取 消</el-button>
                <el-button type="primary" @click="saveEdit">确 定</el-button>
            </span>
        </el-dialog>

          <!-- 新建弹出框 -->
        <el-dialog title="新建订单" :visible.sync="newVisible" width="65%">
          <el-tabs v-model="activeName" @tab-click="handleClick">
            <el-tab-pane label="基本信息" name="second_1">
              <el-form ref="form" :model="form"   status-icon label-width="100px">
                    <el-form-item label="投放类型:">
                      抄底投放
                    </el-form-item>
                    <el-form-item label="投放名称:">
                        <el-input v-model="form.user_name" style="width:40%"></el-input>
                    </el-form-item>
              </el-form> 
            </el-tab-pane>
            <el-tab-pane label="广告位" name="second_2">
                  <el-form ref="form" :model="form"  status-icon label-width="100px">
                          <el-form-item label="媒体类型:">
                                <el-radio label="1" v-model="form.media_type">普通媒体</el-radio>
                                <el-radio label="2" v-model="form.media_type">联盟媒体</el-radio>
                          </el-form-item>
                          <el-form-item label="媒体:">

                          </el-form-item>
                          <el-form-item label="广告位:">
                                <el-radio label="1" v-model="form.AD_position">推荐页tag-小卡图文-原生广告</el-radio>
                                <el-radio label="2" v-model="form.AD_position">推荐页tag-大卡图文-原生广告</el-radio>
                                <el-radio label="3" v-model="form.AD_position">推荐页tag-三图-原生广告</el-radio>
                          </el-form-item>
                          <el-form-item label="查找方式:" v-if="form.media_type == 2">
                                <el-radio label="1" v-model="form.find_location1">APP</el-radio>
                                <el-radio label="2" v-model="form.find_location1">广告位</el-radio>
                          </el-form-item>
                          <el-form-item label="广告位:" v-if="form.find_location == 2">
                                
                          </el-form-item>
                          <el-form-item label="app资源:">
                                <el-checkbox v-model="form.checked1">app分类-汽车</el-checkbox>
                                <el-checkbox v-model="form.checked2">行园汽车</el-checkbox>
                                <el-checkbox v-model="form.checked3">app分类-新闻资讯</el-checkbox>
                                <el-checkbox v-model="form.checked4">行园汽车</el-checkbox>
                          </el-form-item>
                          <el-form-item label="是否过滤已选app:" v-if="form.media_type == 2">
                                <el-radio label="1" v-model="form.guolv_APP">否</el-radio>
                                <el-radio label="2" v-model="form.guolv_APP">是</el-radio>
                          </el-form-item>
                          <el-form-item label="广告位:" v-if="form.media_type == 2">
                                <el-radio label="1" v-model="form.find_location2">插屏600*500</el-radio>
                                <el-radio label="2" v-model="form.find_location2">开屏640*960</el-radio>
                                <el-radio label="3" v-model="form.find_location2">贴片15s*500</el-radio>
                                <el-radio label="4" v-model="form.find_location2">原生190*130（20字内描述，素材-50k）-原生广告*960</el-radio>
                                <el-radio label="5" v-model="form.find_location2">原生640*360（20字标题，20字描述）-原生广告</el-radio>
                          </el-form-item>
                          <el-form-item label="是否开启系统优化:" v-if="form.media_type == 2">
                                <el-radio label="1" v-model="form.xtyh">否</el-radio>
                                <el-radio label="2" v-model="form.xtyh">是</el-radio>
                                <span class="span">*只有做了激活上报对接的才有效</span>
                          </el-form-item>
                          <el-form-item label="预设CPA:" v-if="form.media_type == 2">
                                <el-radio label="1" v-model="form.cpa">否</el-radio>
                                <el-radio label="2" v-model="form.cpa">是</el-radio>
                          </el-form-item>
                   </el-form> 
            </el-tab-pane>
            <el-tab-pane label="确认提交" name="second_3">
                   <el-form ref="form" :model="form"  status-icon>
                       <el-form-item label="推广计划:">
                                <span>{{v_planName}}</span>
                       </el-form-item>
                      <el-form-item label="推广单元:">
                                <span>{{v_unitName}}</span>
                      </el-form-item>
                       <el-form-item label="投放方式:">
                                 <span>{{v_deliveryMode}}</span>
                       </el-form-item>
                      <el-form-item label="价格:">
                                <span>{{v_price}}</span>
                       </el-form-item>
                       <el-form-item label="每日限额:">
                               <span>{{v_unitLimit}}</span>
                       </el-form-item>
                       <el-form-item label="投放日期:">
                                <span>{{v_star_end}}</span>
                       </el-form-item>
                       <el-form-item label="投放类型:">
                                <span>{{v_extensionType}}</span>
                       </el-form-item>
                       <el-form-item label="曝光上报地址:">
                                <span>{{v_impMonitorUrl}}</span>
                       </el-form-item>
                       <el-form-item label="点击上报地址:">
                                 <span>{{v_clkMonitorUrl}}</span>
                       </el-form-item>
                       <el-form-item label="扩展参数(ext_creative_id):">
                                 <span>{{v_extCreativeId}}</span>
                       </el-form-item>
                       <el-form-item label="广告位:">
                                <span>{{v_adCollection}}</span>
                       </el-form-item>
                       <el-form-item label="定向设置:">
                               <div class='r_div'>
                                  <el-form-item label="性别:">
                                    <span>{{11}}</span>
                                  </el-form-item>
                                  <el-form-item label="操作系统:">
                                    <span>{{11}}</span>
                                  </el-form-item>
                                  <el-form-item label="运营商:">
                                    <span>{{11}}</span>
                                  </el-form-item>
                                  <el-form-item label="网络:">
                                    <span>{{11}}</span>
                                  </el-form-item>
                                  <el-form-item label="年龄:">
                                    <span>{{11}}</span>
                                  </el-form-item>
                                  <el-form-item label="学历:">
                                    <span>{{11}}</span>
                                  </el-form-item>
                                  <el-form-item label="行为:">
                                    <span>{{11}}</span>
                                  </el-form-item>
                                  <el-form-item label="兴趣:">
                                    <span>{{11}}</span>
                                  </el-form-item>
                                  <el-form-item label="设备品牌过滤:">
                                    <span>{{11}}</span>
                                  </el-form-item>
                                  <el-form-item label="终端:">
                                    <span>{{11}}</span>
                                  </el-form-item>
                                  <el-form-item label="地区:">
                                    <span>{{11}}</span>
                                  </el-form-item>
                                  <el-form-item label="规则:">
                                    <span>{{11}}</span>
                                  </el-form-item>
                               </div>
                       </el-form-item>
                      
                   </el-form> 
            </el-tab-pane>
          </el-tabs>
            
           <span slot="footer" class="dialog-footer">
                <el-button style="margin-top: 12px;" @click="last_" v-if="activeName != 'second_1'">上一步</el-button>
                <el-button style="margin-top: 12px;" @click="next_" v-if="activeName != 'second_3'">下一步</el-button>
                <!-- <el-button @click="newVisible = false">取 消</el-button> -->
                <el-button type="primary" @click="saveNews" v-if="activeName == 'second_3'">确 定</el-button>
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
// import picker from "../../../../components/common/picker/index.vue";
// import diqu from '../../../../components/common/diqu.vue'


export default {
  name: "basetable",
  data() {
    return {
      url: "./static/vuetable.json",
      tableData: [],
      cur_page: 1,
      select_cate: "",
      select_word: "",
      del_list: [],
      is_search: false,
      editVisible: false,
      delVisible: false,
      newVisible: false,
      newaddAD: false,
      template_dialog: false,
      currentRow: null,
      idx: -1,
      activeName: "second_1",
      time:'',
      hour:'',
      hour2:'',
      unitLimit:'',
      deliveryMode:'1',
      price:'',
         v_planName:'',
        v_unitName:'',
        v_deliveryMode:'',
        v_price:'',
        v_unitLimit:'',
        v_star_end:'',
        v_extensionType:'',
        v_impMonitorUrl:'',
        v_clkMonitorUrl:'',
        v_extCreativeId:'',
        v_adCollection:'',
        
      click_hour:[{hour:'',hour2:''}],
       hours: [
          {
            value: "1",
            label: "1"
          },
          {
            value: "2",
            label: "2"
          },
          {
            value: "3",
            label: "3"
          },
          {
            value: "4",
            label: "4"
          },
          {
            value: "5",
            label: "5"
          },
          {
            value: "6",
            label: "6"
          },
          {
            value: "7",
            label: "7"
          },
          {
            value: "8",
            label: "8"
          },
          {
            value: "9",
            label: "9"
          },
          {
            value: "10",
            label: "10"
          },
          {
            value: "1",
            label: "11"
          },
          {
            value: "12",
            label: "12"
          },
          {
            value: "13",
            label: "13"
          },
          {
            value: "14",
            label: "14"
          },
          {
            value: "15",
            label: "15"
          },
          {
            value: "16",
            label: "16"
          },
          {
            value: "17",
            label: "17"
          },
          {
            value: "18",
            label: "18"
          },
          {
            value: "19",
            label: "19"
          },
          {
            value: "20",
            label: "20"
          },
          {
            value: "21",
            label: "21"
          },
          {
            value: "22",
            label: "22"
          },
          {
            value: "23",
            label: "23"
          },
          {
            value: "24",
            label: "24"
          },
        ],
     
      form: {
        name: "",
        date: "",
        switch: true,
        add_time: "",
        options1: "",
        type: 1,
        types: null,
        flow_management: null,
        data_report: null,
        make_address1: [{ make_address: "" }],
        click_address1: [{ click_address: "" }],
        click_shebei1: [{ click_shebei: "" }],
        put_PDB: "1",
        charge_mode: 1,
        number_facility: 1,
        number_ip: 1,
        number_id: 2,
        number_guiyin: 1,
        put_type: 1,
        age: "1",
        checked: false,
        sex: "1",
        Education: "1",
        Action: "1",
        Crowd: "1",
        interest: "1",
        channel: "1",
        order: "1",
        position: "1",
        guolv: "1",
        zdlx: "1",
        diqu: "1",
        system: "1",
        yys: "1",
        network: "1",
        direction: "1",
        rule: "",
        media_type:'1',
        AD_position:'1',
        find:'1',
        APP_zy:'',
        find_location1:'1',
        guolv_APP:'1',
        xtyh:'1',
        cpa:'',
        time:'',
        time_frame:'1',
        syID:'',
     
        options: [
          {
            value: "1",
            label: "流量管理员"
          },
          {
            value: "2",
            label: "广告客户"
          },
          {
            value: "3",
            label: "直客客户"
          },
          {
            value: "4",
            label: "代理商"
          }
        ],
        value: "1"
      },
      options1: "1",
      options2: "2",
      options3: "3",
      options: [
        {
          value: "1",
          label: "流量管理员"
        },
        {
          value: "2",
          label: "广告客户"
        },
        {
          value: "3",
          label: "直客客户"
        },
        {
          value: "4",
          label: "代理商"
        }
      ]
    };
  },
  created() {
    this.getData();
  },
  components: {
    picker: picker,
    diqu:diqu,
  },
  computed: {
    data() {
      return this.tableData.filter(d => {
        let is_del = false;
        for (let i = 0; i < this.del_list.length; i++) {
          if (d.name === this.del_list[i].name) {
            is_del = true;
            break;
          }
        }
        if (!is_del) {
          // if (
          //   d.address.indexOf(this.select_cate) > -1 &&
          //   (d.name.indexOf(this.select_word) > -1 ||
          //     d.address.indexOf(this.select_word) > -1)
          // ) {
          return d;
          // }
        }
      });
    }
  },
  methods: {
    // 分页导航
    handleCurrentChange(val) {
      this.cur_page = val;
      this.getData();
    },
    // 获取 easy-mock 的模拟数据
    getData() {
      // 开发环境使用 easy-mock 数据，正式环境使用 json 文件
      if (process.env.NODE_ENV === "development") {
        this.url = "/ms/table/list";
      }

      this.$axios
        .post(this.url, {
          page: this.cur_page
        })
        .then(res => {
          this.tableData = res.data.list;
        });
    },
    search() {
      this.is_search = true;
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
    // 保存编辑
    saveEdit() {
      this.$set(this.tableData, this.idx, this.form);
      this.editVisible = false;
      this.$message.success(`修改第 ${this.idx + 1} 行成功`);
    },
    // 新建
    create() {
      this.newVisible = true;
      this.form = {
         name: "",
        date: "",
        switch: true,
        add_time: "",
        options1: "",
        type: 1,
        types: null,
        flow_management: null,
        data_report: null,
        make_address1: [{ make_address: "" }],
        click_address1: [{ click_address: "" }],
        click_shebei1: [{ click_shebei: "" }],
        put_PDB: "1",
        charge_mode: 1,
        number_facility: 1,
        number_ip: 1,
        number_id: 2,
        number_guiyin: 1,
        put_type: 1,
        age: "1",
        checked: false,
        sex: "1",
        Education: "1",
        Action: "1",
        Crowd: "1",
        interest: "1",
        channel: "1",
        order: "1",
        position: "1",
        guolv: "1",
        zdlx: "1",
        diqu: "1",
        system: "1",
        yys: "1",
        network: "1",
        direction: "1",
        rule: "",
        media_type:'1',
        AD_position:'1',
        find:'1',
        APP_zy:'',
        find_location1:'1',
        guolv_APP:'1',
        xtyh:'1',
        cpa:'',
        time:'',
        time_frame:'1',
         hour:'',
         hour2:'',
         click_hour:[{hour:'',hour2:''}],
         unitLimit:'1',
         deliveryMode:'1',
         price:'',
        options: [
          {
            value: "1",
            label: "流量管理员"
          },
          {
            value: "2",
            label: "广告客户"
          },
          {
            value: "3",
            label: "直客客户"
          },
          {
            value: "4",
            label: "代理商"
          }
        ],
        value: "1"
      };
      this.activeName = "second_1";
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
    handleCurrentChange(val) {
      this.currentRow = val;
    },
    template_() {},
    handleClick(tab, event) {
      console.log(tab, event);
    },
    make_address() {
      this.form.make_address1.push({ make_address: "" });
    },
    click_address() {
      this.form.click_address1.push({ click_address: "" });
    },
    click_shebei() {
      console.log(this.form);
      this.form.click_shebei1.push({ click_shebei: "" });
    },
    add_hours(){
        this.form.click_hour.push({ hour: "",hour2:"" });
    },
    last_(){
        let value = this.activeName.split("_");
        this.activeName = value[0]+"_"+parseInt(parseInt(value[1])-1)
    },
    next_(){
        let value = this.activeName.split("_");
        this.activeName = value[0]+"_"+parseInt(parseInt(value[1])+1)
        console.log(  this.activeName)
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
  margin-bottom: 20px;
}
.btn_plan {
  text-align: center;
  margin: 0 auto;
  width: 100px;
  height: 50px;
  background-color: #249cd3;
  border: 1px silver solid;
  color: aliceblue;
  line-height: 50px;
}
.btn_plan_2 {
  text-align: center;
  margin: 0 auto;
  width: 100px;
  height: 50px;
  background-color: #ffffff;
  border: 1px silver solid;
  color: black;
  line-height: 50px;
  margin-top: 20px;
}
.span{
  color: red
}
.el-checkbox+.el-checkbox{
  margin-left: 0px;
}
.btn_right{
    float: right;
}
.r_div{
    margin-left: 80px
}
</style>




