<template>
    <div id="stock-manage">
        <div class="condition">
            <el-input v-model="code" placeholder="输入股票代码"></el-input>
            <el-button @click="queryAllStockData()"  type="primary">查询</el-button>
        </div>
        <div id="echarts">
        </div>
    </div>
</template>
<script>
import * as echarts from 'echarts'
import {stockAllData,allStocks} from '@/assets/js/Api'
export default{
    data(){
        return{
            code:this.$route.query.code || '',
            mainEacharts:{},
            allStock:[],
        }
    },
    created(){
        this.queryAllStock()
    },
    mounted(){
        if (this.code !== ''){
            this.queryAllStockData()
        }
    },
    methods:{
        queryAllStock(){
            allStocks().then(res=>{
                this.allStock = res.data
            })
        },
        queryAllStockData(){
            if(document.getElementById("main")==undefined){
                var div = document.createElement('div')
                div.setAttribute("id",'main')
                var main = document.getElementById('echarts').appendChild(div)
                this.mainEacharts = echarts.init(main);
                this.mainEacharts.resize({width: 800,height: 400});
            }

            var options = {
                title:{
                    text:'股票数据'
                },
                legend:{},
                tooltip:{},
                xAxis:{
                    data:[],
                },
                yAxis:{},
                series:[]
            }
            stockAllData({code:this.code}).then(res=>{
                var data = res.data
                var dates=[],hights=[],opens=[],prices=[],lows=[]
                data.forEach(key => {
                    dates.push(key.date)
                    hights.push(key.hight)
                    opens.push(key.open)
                    prices.push(key.price)
                    lows.push(key.low)
                });
                options.title.text = this.code
                options.xAxis.data = dates
                options.yAxis.scale = true
                options.series.push({name:'开盘',type:'line',data:opens})
                options.series.push({name:'最高',type:'line',data:hights})
                options.series.push({name:'最低',type:'line',data:lows})
                options.series.push({name:'收盘',type:'line',data:prices})
                this.mainEacharts.setOption(options)
            })
        }
    }
}
</script>