<template>
    <div class="user-manage">
        <div class="condition">

        </div>
        <div class="contain">
            <el-table :data="users" border style="width: 100%">
                <el-table-column type="index"/>
                <el-table-column prop="name" label="名称" />
                <el-table-column prop="age" label="年龄" width="60"/>
                <el-table-column label="性别" width="60">
                    <template #default="scope">
                        <label v-if="scope.row.sex==1">男</label>
                        <label v-else-if="scope.row.sex==2">女</label>
                        <label v-else>未知</label>
                    </template>
                </el-table-column>
                <el-table-column prop="phone" label="电话" width="150"/>
                <el-table-column prop="email" label="邮箱"/>
                <el-table-column label="操作">
                    <template #default="scope">
                        <el-button @click="review(scope.row)">预览</el-button>
                        <el-button @click="modify(scope.row)">编辑</el-button>
                        <el-button @click="remove(scope.row.id)">删除</el-button>
                    </template>
                </el-table-column>
            </el-table>
        </div>
    </div>
</template>

<script>
import {local} from '@/assets/js/api.js'
export default {
    name:"UserManage",
    data(){
        return{
            users:[]
        }
    },
    created(){
        local('/static/users.json').then(res=>{
            this.users = res.data
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
<style scoped>

</style>