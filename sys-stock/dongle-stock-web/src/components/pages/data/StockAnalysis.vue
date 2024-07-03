<template>
    <div class='stock-analysis'>
        <div class="condition">
            <el-select v-model="code" placeholder="请选择股票" filterable @change="queryAllStockData">
                <el-option v-for="item in allStock" :key='"stock_"+item.code' :value="item.code" :label="handlerLabel(item)"></el-option>
            </el-select>
        </div>
        <div id="echarts">
            <!-- 显示股票数据 -->
            <div id="main"></div>
            <!-- 副图，显示各指标差值数据 -->
            <div id="sub"></div>
        </div>
        <div id="table">
            <el-table :data="stockData" border style="width: 100%">
                <el-table-column prop="date" label="日期" width="180"></el-table-column>
                <el-table-column prop="open" label="开盘价" width="180"></el-table-column>
                <el-table-column prop="open_gap" label="开盘差" width="180"></el-table-column>
                <el-table-column prop="high" label="最高价" width="180"></el-table-column>
                <el-table-column prop="high_gap" label="最高差" width="180"></el-table-column>
                <el-table-column prop="low" label="最低价" width="180"></el-table-column>
                <el-table-column prop="low_gap" label="最低差" width="180"></el-table-column>
                <el-table-column prop="price" label="收盘价" width="180"></el-table-column>
                <el-table-column prop="price_gap" label="收盘差" width="180"></el-table-column>
                <el-table-column label="当日最差" width="180">
                    <template slot-scope="scope">
                       {{ (scope.row.high-scope.row.low).toFixed(2) }}
                    </template>
                </el-table-column>
                <el-table-column prop="pre_gap" label="前日最差" width="180"></el-table-column>
            </el-table>
        </div>
    </div>
</template>
<script>
import {stockAllData,allStocks,stockInfo} from '@/assets/js/Api'
import {createEchartsForStock,formatTitle,createEchartsForStockByGrap} from '@/assets/js/stock'
export default {
    name: 'StockAnalysis',
    data(){
        return{
            code:undefined,
            allStock:[],
            stock:{},
            stockData:[]
        }
    },
    created(){
        this.queryAllStock()
    },
    mounted(){
        var code  = this.$route.query.code
        if(code != undefined) {
            this.code = code
            this.queryAllStockData()
        }
    },
    methods:{
        dongle(a,b){
            
        },
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
                    var data = res.data
                    createEchartsForStock(document.getElementById("main"),data,formatTitle(that.stock))
                    // 差值处理
                    data.sort((a,b)=>{ // a为后者，b为前一个
                        a.high_gap=(a.high-b.high).toFixed(2)
                        a.open_gap=(a.open-b.open).toFixed(2)
                        a.price_gap=(a.price-b.price).toFixed(2)
                        a.low_gap=(a.low-b.low).toFixed(2)
                        a.pre_gap =(a.high-b.low).toFixed(2)
                        if(!b.pre_gap) b.pre_gap=0
                        return 0
                    })
                    
                    createEchartsForStockByGrap(document.getElementById("sub"),data,formatTitle(that.stock)+"\nGap 差值")
                    that.stockData = data
                })
            })
        }
    }
};
</script>