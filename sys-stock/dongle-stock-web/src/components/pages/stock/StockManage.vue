<template>
    <div id="stock-manage">
        <div class="condition">
            <el-select v-model="code" placeholder="请选择股票" filterable @change="queryAllStockData">
                <el-option v-for="item in allStock" :key='"stock_"+item.code' :value="item.code" :label="handlerLabel(item)"></el-option>
            </el-select>
            <!-- <el-button @click="queryAllStockData()"  type="primary">查询</el-button> -->
        </div>
        <div id="echarts">
            <div id="main"></div>
        </div>
    </div>
</template>
<script>
import {stockAllData,allStocks,stockInfo} from '@/assets/js/Api'
import {createEcharts,formatTitle} from '@/assets/js/stock'
export default{
    data(){
        return{
            code:undefined,
            allStock:[],
            stock:{}
        }
    },
    created(){
        this.queryAllStock()
    },
    mounted(){
        this.queryAllStockData(this.$route.query.code)
    },
    methods:{
        handlerLabel(stock){
            return formatTitle(stock)
        },
        queryAllStock(){
            allStocks().then(res=>{
                this.allStock = res.data
            })
        },
        queryAllStockData(code){
            var that = this
            if(code == undefined) code = this.code
            stockInfo({code:code}).then(res=>{
                that.stock = res.data
                if(this.stock!={} || this.stock!='')
                    return new Promise(res=>res())
            }).then(()=>{
                stockAllData({code:code}).then(res=>{
                    createEcharts(document.getElementById("main"),res.data,formatTitle(that.stock))
                })
            })
        }
    }
}
</script>