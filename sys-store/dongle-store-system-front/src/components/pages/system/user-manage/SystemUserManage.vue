<template>
    <div class='sys-user-manage'>
        <div class="condition">
            <el-input v-model="userId" placeholder="请输入ID或名称" />
            <el-button type="primary" icon="el-icon-search" @click="search">搜索</el-button>
            <el-button type="primary" icon="el-icon-plus" @click="add">添加</el-button>
            <!-- <router-link to="">用户审核管理</router-link> -->
        </div>
        <div class="table">
            <el-table :data="users">
                <el-table-column prop="uuid" label="账号" />
                <el-table-column prop="name" label="昵称" />
                <el-table-column prop="phone" label="手机号" />
                <el-table-column prop="email" label="邮箱" />
                <el-table-column prop="status" label="状态" />
                <el-table-column prop="group" label="用户组" />
                <el-table-column label="创建时间">
                    <template slot-scope="scope">
                        <span>{{ scope.row.cdt | dateFormat }}</span>
                    </template>
                </el-table-column>
                <el-table-column label="操作">
                    <template slot-scope="scope">
                        <el-button type="text" size="small" @click="look(scope.row)">查看</el-button>
                        <el-button type="text" size="small" @click="delete(scope.row)">删除</el-button>
                        <el-button type="text" size="small" @click="modifty(scope.row)">修改</el-button>
                        <el-button type="text" size="small" @click="auth(scope.row)">权限</el-button>
                    </template>
                </el-table-column>
            </el-table>
        </div>
    </div>
</template>
<script>
export default {
    name: 'SystemUserManage',
    data() {
        return {
            users:[],
            userId:{},
        }
    },
    methods: {
        // 获取用户列表
        getUsers() {
            // 获取用户列表
        },
        look(row){
            console.log(row);
        },
        delete(row){
            console.log(row);
        },
        modifty(row){
            console.log(row);
        },
        auth(row){
            console.log(row);
        },
    },
};
</script>