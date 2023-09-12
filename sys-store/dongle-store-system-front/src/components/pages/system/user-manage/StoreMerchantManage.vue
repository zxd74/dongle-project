<template>
    <div class='user-manage'>
        <div class="condition">
            <el-input v-model="userId" placeholder="请输入ID或名称" />
            <el-button type="primary" icon="el-icon-search" @click="search">搜索</el-button>
            <!-- <router-link to="">用户审核管理</router-link> -->
        </div>
        <div class="table">
            <el-table :data="users">
                <el-table-column prop="uuid" label="商户ID" />
                <el-table-column prop="name" label="商户名" />
                <el-table-column prop="phone" label="手机号" />
                <el-table-column prop="email" label="邮箱" />
                <!-- <el-table-column prop="group" label="用户组" /> -->
                <el-table-column prop="status" label="状态" />
                <el-table-column prop="audit-status" label="审核状态" />
                <el-table-column label="注册时间">
                    <template slot-scope="scope">
                        <span>{{ scope.row.cdt | dateFormat }}</span>
                    </template>
                </el-table-column>
                <!-- <el-table-column label="操作">
                    <template slot-scope="scope">
                        <el-button type="text" size="small" @click="handleClick(scope.row)">查看</el-button>
                    </template>
                </el-table-column> -->
            </el-table>
        </div>
    </div>
</template>
<script>
export default {
    name: 'StoreMerchantManage',
    data() {
        return {
        }
    },
    methods: {},
};
</script>