<template>
    <div class="question-type">
        <el-table :data="data">
           <el-table-column prop="id" label="ID"/>
           <el-table-column prop="name" label="类型"/>
           <el-table-column prop="value" label="类型值"/>
           <el-table-column prop="tag" label="标签"/>
           <el-table-column prop="desc" label="描述"/>
        </el-table>
    </div>
</template>

<script>
import { local } from '@/assets/js/api';
export default {
    name:'QuestionType',
    data(){
        return {
            data:[],
        }
    },
    created(){
        local('/static/typs.json').then(res=>{
            this.data = res.data
        })
    }
}
</script>