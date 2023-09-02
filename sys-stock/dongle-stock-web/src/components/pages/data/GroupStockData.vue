<template>
    <div class="main">
        <div class="condition">
            <el-select v-model="groupId" placeholder="请选择分组" @change="selectGroup">
                <el-option v-for="item in groups" :key='"group_"+item.groupId' :value="item.groupId" :label="item.groupName"></el-option>
            </el-select>
            <el-button @click="$router.push('/stock-group')" type="primary" round>分组管理</el-button>
        </div>
        <div class="content">
            <div id="echarts">
                <div v-for="(item,index) in echarts" :id='"echarts_" + index' :key='"echarts_" + index'
                    style="display: inline-block;"></div>

            </div>
        </div>
    </div>
</template>
<script>
import {createEcharts} from '@/assets/js/stock'
import {groupStockAllData,allStockGroup} from '@/assets/js/Api'
export default{
    data(){
        return{
            groups:[],
            echarts:[],
            groupId:undefined
        }
    },
    created(){
        this.queryAllGroup()
        this.selectGroup(this.$route.query.groupId)
    },
    methods:{
        queryAllGroup(){
            allStockGroup().then(res=>{
                this.groups = res.data
            })
        },
        selectGroup(groupId){
            var that = this;
            if(groupId == undefined) groupId = that.groupId
            groupStockAllData({groupId:groupId}).then(res=>{
                var data = res.data 
                if (data != '')
                    that.echarts = data
                return new Promise(res => res())
            }).then(()=>{
                that.echarts.forEach((item,index)=>{
                    createEcharts(document.getElementById('echarts_'+index),item.data,item.name + '('+item.code+')')
                })
            })
        }
    }
}
</script>