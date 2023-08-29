<template>
    <div class="main">
        <div class="condition">
            <!-- <el-select>
                <el-option v-for="item in groups" :key="item.id" :value="item.value" :label="item.name"></el-option>
            </el-select> -->
        </div>
        <div class="content">
            <div id="echarts"></div>
        </div>
    </div>
</template>
<script>
import {createEcharts} from '@/assets/js/stock'
import {groupStockAllData} from '@/assets/js/Api'
export default{
    data(){
        return{
            groups:[
                {id:1,name:"测试",value:""}
            ],
            echarts:[],
        }
    },
    created(){
        this.selectGroup("1")
    },
    methods:{
        createEchartElement(num){
            var div = document.createElement('div')
            div.setAttribute("id",'main_' + num)
            div.setAttribute("style","display:inline-block;")
            var main = document.getElementById('echarts').appendChild(div)
            return main
        },
        selectGroup(groupId){
            var that = this;
            groupStockAllData({groupId:groupId}).then(res=>{
                var data = res.data 
                data.forEach(item => {
                    var echart = createEcharts(this.createEchartElement(this.echarts.length),item.data)
                    that.echarts.push(echart)
                });
                
            })
        }
    }
}
</script>