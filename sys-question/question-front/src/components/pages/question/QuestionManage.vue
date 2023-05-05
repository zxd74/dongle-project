<template>
    <div class="question-manage">
        <!-- 试题管理，负责试题增删改查 -->
        <div class="condition">

        </div>
        <div class="contain">
            <el-table :data="questions" border style="width: 100%">
                <el-table-column type="index"/>
                <el-table-column prop="title" label="问题"/>
                <el-table-column prop="type" label="类型" width="60"/>
                <el-table-column prop="cate" label="分类"/>
                <el-table-column prop="tag" label="标签"/>
                <el-table-column label="操作" width="250">
                    <template #default="scope">
                        <el-button @click="review(scope.row)">预览</el-button>
                        <el-button @click="modify(scope.row)">编辑</el-button>
                        <el-button @click="remove(scope.row.id)">删除</el-button>
                    </template>
                </el-table-column>
            </el-table>
        </div>
        <div class="frame" style="width: 0;height: 0;">
            
        </div>
    </div>
</template>

<script>
import {local} from '@/assets/js/api.js'
export default {
    name:'QuestionManage',
    data(){
        return {
            questions:[]
        }
    },
    created(){
        local('/static/questions.json').then(res=>{
            this.questions = res.data
        })
    },
    methods:{
        review(question){
            console.log("review " + question.id)
            console.log(question)
        },
        modify(question){
            console.log("modify " + question.id)
            console.log(question)
        },
        remove(id){
            console.log("remove " + id)
        },
    }
}
</script>