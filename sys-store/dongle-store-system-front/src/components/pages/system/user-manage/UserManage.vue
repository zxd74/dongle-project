<template>
    <div class='user-manage'>
        <router-view />
    </div>
</template>
<!-- <template>
    <div class='user-manage'>
        <div class="condition">
            <el-input v-model="condition.content" placeholder="请输入ID或名称" />
            <el-select v-model="condition.group" placeholder="请选择用户分组">
                <el-option v-for="item in groups" :key="'group-' + item.value" :label="item.name" :value="item.value" />
            </el-select>
            <el-button type="primary" icon="el-icon-search" @click="search">搜索</el-button>
            <el-button type="primary" icon="el-icon-plus" @click="add">添加</el-button>
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
                        <el-button type="text" size="small" @click="handleClick(scope.row)">查看</el-button>
                        <el-button type="text" size="small" @click="handleClick(scope.row)" v-if="scope.row.group==0">删除</el-button>
                        <el-button type="text" size="small" @click="handleClick(scope.row)" v-if="scope.row.group==0">修改</el-button>
                        <el-button type="text" size="small" @click="handleClick(scope.row)" v-if="scope.row.group==0">权限</el-button>
                    </template>
                </el-table-column>
            </el-table>
        </div>
    </div>
</template> -->
<!-- <script>

export default {
    name: 'SystemUserManage',
    data() {
        return {
            users:[],
            groups:[],
            condition:{},
        }
    },
    methods: {
        getTypes(){
            // 获取用户分组列表

            localStorage.getItem("default-user-groups")
        },
        search(){
            //搜索，获取用户列表
        },
        handleClick(row){
            console.log(row);
        },
        handleAudit(row){
            console.log(row);
        },
    },
};
</script>
    </div>
</template>
<script>
export default {
    name: 'UserManage',
    data() {
        return {
        }
    },
    methods: {},
};
</script> -->