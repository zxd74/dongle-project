<template>
    <div id="data-manage">
        数据管理
        <el-table :data="data">
            <el-table-column label="代码" >
                <template slot-scope="stock">
                    <el-link type="primary" @click="gotoStock(stock.row.code)">{{stock.row.code}}</el-link>
                </template>
            </el-table-column>
            <el-table-column label="股票名称">
                <template slot-scope="stock">
                    <el-link type="primary" @click="gotoStock(stock.row.code)">{{stock.row.name}}</el-link>
                </template>
            </el-table-column>
            <el-table-column prop="date" label="日期"/>
            <el-table-column prop="open" label="开盘"/>
            <el-table-column prop="price" label="收盘"/>
            <el-table-column prop="high" label="最高"/>
            <el-table-column prop="low" label="最低"/>
            <el-table-column label="操作">
                <template slot-scope="stock">
                    <el-link type="primary">查看</el-link>
                </template>
            </el-table-column>
        </el-table>
    </div>
</template>
<script>
import {allStockNewData} from '@/assets/js/Api'
export default{
    data(){
        return{
            data:[],
        }
    },
    created(){
        this.queryAll()
    },
    methods:{
        queryAll(){
            allStockNewData().then(res => {
                this.data = res.data
            })
        },
        gotoStock(code){
            this.$router.push({path:'/stock/stock-manage',query:{code:code}})
        }
    }
}
</script>