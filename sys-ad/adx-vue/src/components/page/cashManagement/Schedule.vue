<template>
    <div class="table">
        <div class="container">
            <div class="handle-box">
                <el-input v-model="select_word" placeholder="请输入广告位名称" class="handle-input mr10"></el-input>
                <el-button type="primary" icon="search" @click="search">搜索</el-button>
                 <!-- <el-button type="primary" icon="search" @click="create">新建</el-button> -->
            </div>
              <div>
                <el-select v-model="ad_Customer" placeholder="请选择">
                  <el-option
                    v-for="item in options"
                    :key="item.value"
                    :label="item.label"
                    :value="item.value">
                  </el-option>
                </el-select>
                 <el-select v-model="ad_order" placeholder="请选择">
                  <el-option
                    v-for="item in options"
                    :key="item.value"
                    :label="item.label"
                    :value="item.value">
                  </el-option>
                </el-select>
                 <el-select v-model="ad_flow" placeholder="请选择">
                  <el-option
                    v-for="item in options"
                    :key="item.value"
                    :label="item.label"
                    :value="item.value">
                  </el-option>
                </el-select>
                 <el-select v-model="ad_show" placeholder="请选择">
                  <el-option
                    v-for="item in options"
                    :key="item.value"
                    :label="item.label"
                    :value="item.value">
                  </el-option>
                </el-select>
                 <el-select v-model="Template_name" placeholder="请选择">
                  <el-option
                    v-for="item in options"
                    :key="item.value"
                    :label="item.label"
                    :value="item.value">
                  </el-option>
                </el-select>
              </div>
            <div class="td_color">
                <div class="diamonds"></div>空闲
                <div class="diamonds bg_color"></div>有投放
            </div>
            <div class="table">
              <table cellspacing="0">
                  <thead>
                      <tr>
                        <td colspan="10" style="height: 40px;">
                          <div class="block">
                              <el-date-picker
                                v-model="value4"
                                type="datetimerange"
                                range-separator="至"
                                start-placeholder="开始日期"
                                end-placeholder="结束日期">
                              </el-date-picker>
                          </div>
                        </td>
                        <td>三</td>
                        <td>四</td>
                        <td>五</td>
                        <td>六</td>
                        <td>日</td>
                        <td>一</td>
                        <td>二</td>
                        <td>三</td>
                        <td>四</td>
                        <td>五</td>
                        <td>六</td>
                        <td>日</td>
                        <td>一</td>
                        <td>二</td>
                        <td>三</td>
                        <td>四</td>
                        <td>五</td>
                        <td>六</td>
                        <td>日</td>
                        <td>一</td>
                        <td>二</td>
                        <td>三</td>
                        <td>四</td>
                        <td>五</td>
                        <td>六</td>
                        <td>日</td>
                        <td>一</td>
                        <td>二</td>
                        <td>三</td>
                        <td>四</td>
                        <td>五</td>
                      </tr>
                      <tr>
                        <td colspan='2'>广告位名称</td>
                        <td colspan='2'>模板名称</td>
                        <td colspan='2'>展现形式</td>
                        <td colspan='2'>订单名称</td>
                        <td colspan='2'>客户名称</td>
                        <td>1</td>
                        <td>2</td>
                        <td>3</td>
                        <td>4</td>
                        <td>5</td>
                        <td>6</td>
                        <td>7</td>
                        <td>8</td>
                        <td>9</td>
                        <td>10</td>
                        <td>11</td>
                        <td>12</td>
                        <td>13</td>
                        <td>14</td>
                        <td>15</td>
                        <td>16</td>
                        <td>17</td>
                        <td>18</td>
                        <td>19</td>
                        <td>20</td>
                        <td>21</td>
                        <td>22</td>
                        <td>23</td>
                        <td>24</td>
                        <td>25</td>
                        <td>26</td>
                        <td>27</td>
                        <td>28</td>
                        <td>29</td>
                        <td>30</td>
                        <td>31</td>
                    </tr>            
                  </thead>
                  <tbody>
                    <tr v-for="(item,index) in table_data"  :key="index">
                       <td colspan='2'>{{item.weizhi}}</td>
                       <td colspan='2'>{{item.weizhi1}}</td>
                       <td colspan='2'>{{item.weizhi2}}</td>
                       <td colspan='2'>{{item.weizhi3}}</td>
                       <td colspan='2'>{{item.weizhi4}}</td>
                       <td v-for="(sub_item,i) in item.array"  :key="i">
                           {{sub_item}}
                       </td>
                    </tr>            
                </tbody>
              </table>
            </div>
            <div class="pagination">
                <el-pagination @current-change="handleCurrentChange" layout="prev, pager, next" :total="1000">
                </el-pagination>
            </div>
        </div>

     
    </div>
</template>

<script>
export default {
  name: "basetable",
  data() {
    return {
         options: [{
          value: '选项1',
          label: '黄金糕'
        }, {
          value: '选项2',
          label: '双皮奶'
        }, {
          value: '选项3',
          label: '蚵仔煎'
        }, {
          value: '选项4',
          label: '龙须面'
        }, {
          value: '选项5',
          label: '北京烤鸭'
        }],
        select_word:'',
        ad_Customer: '',
        ad_order:'',
        ad_flow:'',
        ad_show:'',
        Template_name:'',
        value4: [new Date(2000, 10, 10, 10, 10), new Date(2000, 10, 11, 10, 10)],
        table_data:[{'weizhi':"位置1",'weizhi1':"模板1",'weizhi2':"插屏1",'weizhi3':"111",'weizhi4':"222",'array':['','1','','1','','1','','1','','1','','1','','1','','1','','1','','1','','1','','1','','1','','1','','1','']}],
    };
  },
  created() {
    console.log(11);
    
  },
 
  methods: {
    // 分页导航
    handleCurrentChange(val) {
      this.cur_page = val;
    },
    search(){
      console.log(11);
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
.table{
  margin-left: -10px;
  margin-top: 20px;
  border: none;
  border-collapse: collapse;
 
}
table,td {
   text-align: center;
   border: 1px solid rgba(0, 0, 0, 0.336);
}
.el-date-editor{
  border: none
}
.td_color{
    margin-left: 80%;
    margin-top: 20px;
}
.diamonds{
   display: inline-block;
   width: 20px;
   height: 20px;
   border: 1px solid silver;

}
.bg_color{
  background: silver;
}



</style>
