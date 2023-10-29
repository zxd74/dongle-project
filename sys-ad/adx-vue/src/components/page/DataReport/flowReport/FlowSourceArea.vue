<template>
    <div class="box"  v-loading="loading">
        <div v-if="this.index == 1">
            <div class="block">
                <div class="header-select">
                        <el-tag size="medium">日期:</el-tag>
                        <el-date-picker
                            :picker-options="pickerOptions1"
                                v-model="sDay"
                                type="date"
                                placeholder="选择日期"
                                format="yyyy 年 MM 月 dd 日"
                                value-format="yyyyMMdd">
                            </el-date-picker> -
                            <el-date-picker
                            :picker-options="pickerOptions1"
                                v-model="eDay"
                                type="date"
                                placeholder="选择日期"
                                format="yyyy 年 MM 月 dd 日"
                                value-format="yyyyMMdd">
                            </el-date-picker>                
                            <el-select
                                style="width:300px"
                                v-model="systemAD"
                                multiple
                                filterable
                                default-first-option
                                placeholder="请输入地区名称">
                                <el-option
                                v-for="item in optionsOs"
                                :key="item.value"
                                :label="item.lable"
                                :value="item.value">
                                </el-option>
                            </el-select>
                            <el-button type="primary" @click="BtnEchers">搜 索</el-button>
                            <el-button type="primary" icon="search" @click="exportExcel">导 出</el-button>
                </div>
            </div>
            <el-select v-model="custom" multiple placeholder="自定义显示" @change="handCustom" style="width:100%">
                                <el-option
                                    v-for="item in optionsCustom"
                                    :key="item.value"
                                    :label="item.label"
                                    :value="item.value">
                                </el-option>
            </el-select>
            <el-table :data="tableData" border style="width: 100%" ref="multipleTable"  key="tableDataInstall1">
                <el-table-column prop="areaName" label="地区">
                </el-table-column>
                <el-table-column prop="req" label="请求量" v-if="this.custom.indexOf('req') > -1">
                </el-table-column>
                <el-table-column prop="requv" label="请求UV" v-if="this.custom.indexOf('reqUV') > -1">
                </el-table-column> 
                <el-table-column prop="exp" label="展现量" v-if="this.custom.indexOf('exp') > -1">
                </el-table-column>
                <el-table-column prop="expuv" label="展现UV" v-if="this.custom.indexOf('expUV') > -1">
                </el-table-column>
                <el-table-column prop="exp_rate" label="展现率%" v-if="this.custom.indexOf('exp_rate') > -1">
                </el-table-column>
                <el-table-column prop="clk" label="点击量" v-if="this.custom.indexOf('clk') > -1">
                </el-table-column>
                <el-table-column prop="clkuv" label="点击uv" v-if="this.custom.indexOf('clkUV') > -1">
                </el-table-column>
                <el-table-column prop="clk_rate" label="点击率%" v-if="this.custom.indexOf('clk_rate') > -1">
                </el-table-column>
                <el-table-column prop="expPer" label="人均曝光数" v-if="this.custom.indexOf('expPer') > -1">
                </el-table-column>
                <el-table-column prop="investment" label="收入" v-if="this.custom.indexOf('investment') > -1">
                </el-table-column>
                <el-table-column prop="cpm" label="cpm" v-if="this.custom.indexOf('cpm') > -1">
                </el-table-column>
                <el-table-column prop="cpc" label="cpc" v-if="this.custom.indexOf('cpc') > -1">
                </el-table-column>
                <el-table-column prop="clkDesire" label="点击意愿" v-if="this.custom.indexOf('clkDesire') > -1">
                </el-table-column>
                <el-table-column label=""  width="150" align="center">
                  <template slot-scope="scope">
                    <el-button size="mini" type="success" @click="handleEdit(scope.$index, scope.row,)">详细</el-button>
                  </template>
                </el-table-column>
            </el-table>
        </div>
        <div v-if="this.index ==2">
            <div class="header-select" style="margin-bottom: 3%;">
                    <el-button type="primary" @click="Goback">返回上一页</el-button>
                    <el-button type="primary" icon="search" @click="exportExcel2">导 出</el-button>
            </div>
            <el-table :data="tableData2" border style="width: 100%"  key="tableDataInstall2">
                <el-table-column prop="creDay" label="时间">
                </el-table-column>
                <el-table-column prop="areaName" label="地区">
                </el-table-column>
                <el-table-column prop="req" label="请求量" >
                </el-table-column>
                <el-table-column prop="requv" label="请求UV" >
                </el-table-column> 
                <el-table-column prop="exp" label="展现量" >
                </el-table-column>
                <el-table-column prop="expuv" label="展现UV" >
                </el-table-column>
                <el-table-column prop="exp_rate" label="展现率%" >
                </el-table-column>
                <el-table-column prop="clk" label="点击量" >
                </el-table-column>
                <el-table-column prop="clkuv" label="点击uv" >
                </el-table-column>
                <el-table-column prop="clk_rate" label="点击率%" >
                </el-table-column>
                <el-table-column prop="expPer" label="人均曝光数" >
                </el-table-column>
                <el-table-column prop="investment" label="收入" >
                </el-table-column>
                <el-table-column prop="cpm" label="cpm" >
                </el-table-column>
                <el-table-column prop="cpc" label="cpc" >
                </el-table-column>
                <el-table-column prop="clkDesire" label="点击意愿" >
                </el-table-column>
            </el-table>
        </div>
    </div>
</template>

<script>
import { qasumByDay,getAreas,qasumByDaydetail } from "@/api/Api.js";
// import Schart from "vue-schart";
// 引入 ECharts 主模块
var echarts = require("echarts/lib/echarts");

require("echarts/lib/chart/line");
require("echarts/lib/component/legend");
require("echarts/lib/component/tooltip");
require("echarts/lib/component/title");

const options = [];

export default {
  name: "basecharts",
  components: {
    // Schart
  },
  data: () => ({
    pickerOptions1: {
        disabledDate(time) {
        const lastMonthTime = new Date().setMonth(new Date().getMonth() - 1)
        return time.getTime() > Date.now() || time < lastMonthTime
        },
    },
    index:1,
    loading:false,
    sDay:'',
    eDay:'',
    radio:'',
    tableData:[],
    tableData2:[],
    checkAll: false,
    isIndeterminate: true,
    flowSource:'',
    isAll:'',
    checkList:[],
    types:null,
    checkAll:false,
    from:{

    },
    classify:'',
    optionsType:[
         {
          value: '',
          label: ''
        }
      ],
    value: '选项1',
    value2:'',
      custom:['req','exp','exp_rate','clk','clk_rate','investment'],
     value3:'',
    optionsCustom:[
         {
          value: 'req',
          label: '请求量'
         },
        {
          value: 'reqUV',
          label: '请求UV'
         },
         {
          value: 'exp',
          label: '展现量'
         },
          {
          value: 'expUV',
          label: '展现UV'
         },
           {
          value: 'exp_rate',
          label: '展现率'
         },
        {
          value: 'clk',
          label: '点击量'
         },
          {
          value: 'clk_rate',
          label: '点击率'
         },
          {
          value: 'expPer',
          label: '人均曝光'
         },
        {
          value: 'cpm',
          label: 'cpm'
         },
        {
          value: 'cpc',
          label: 'cpc'
         },
        {
          value: 'clkDesire',
          label: '点击意愿'
         },
          {
          value: 'investment',
          label: '收入'
         },
      ],
     form:{},
     index:1,
     optionsOs:[
         {
          value: '',
          label: ''
        }
      ],
      systemAD:[]

  }),

 

  created() {
    this.getAreas()
  },
  methods: {
    handCustom(value){
    },
    // 城市下拉框
    getAreas(){
        getAreas().then(res=>{
          this.optionsOs = res.data
          });
    },
    //  查询流量源
     BtnEchers(){
         this.loading = true
         let params = {
             sDate:this.sDay,
             eDate:this.eDay,
             areas:this.systemAD.join(','),
         }
        qasumByDay(params).then(res=>{
            console.log(res)
            let data = res.data
            if(data.code != 'A000000') {
                this.$message.error(data.message);
                return;
            }
            this.tableData = data.data 
            this.loading = false               
          });
     },
     handleEdit(index,row){
         let item = row
        console.log(item)
         this.index = 2
         this.loading = true
         let params = {
             sDate:this.sDay,
             eDate:this.eDay,
             area:item.area,
         }
        qasumByDaydetail(params).then(res=>{
            console.log(res)
            let data = res.data
            if(data.code != 'A000000') {
                this.$message.error(data.message);
                return;
            }
            this.tableData2 = data.data 
            this.loading = false               
          });
     },
     Goback(){
         this.index =1
     },
     exportExcel(){
            var wb = XLSX.utils.table_to_book(document.querySelector('#tableData'))

            var wbout = XLSX.write(wb, { bookType: 'xlsx', bookSST: true, type: 'array' })

            try {
              FileSaver.saveAs(new Blob([wbout], { type: 'application/octet-stream' }), '汇总数据.xlsx')
            } catch (e) { if (typeof console !== 'undefined') console.log(e, wbout) }

            return wbout
     },
    exportExcel2(){
            var wb = XLSX.utils.table_to_book(document.querySelector('#tableData2'))

            var wbout = XLSX.write(wb, { bookType: 'xlsx', bookSST: true, type: 'array' })

            try {
              FileSaver.saveAs(new Blob([wbout], { type: 'application/octet-stream' }), '分日数据.xlsx')
            } catch (e) { if (typeof console !== 'undefined') console.log(e, wbout) }

            return wbout
     },
     
    },
};
</script>

<style scoped>
.block {
  margin-bottom: 3%;
}

</style>
