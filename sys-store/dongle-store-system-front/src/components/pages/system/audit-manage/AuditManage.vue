<template>
    <div class='audit-manage'>
        <div class="condition">
            <el-input v-model="condition.content" placeholder="请输入查询内容" />
            <el-select v-model="condition.type">
                <el-option v-for="item in types" :key="'type-' + item.value" :label="item.name" :value="item.value" />
            </el-select>
            <el-button type="primary" icon="el-icon-search" @click="search">搜索</el-button>
            <!-- <router-link to="">用户审核管理</router-link> -->
        </div>
        <div class="table">
            <el-table :data="data">
                <el-table-column prop="type" label="类型" width="100" />
                <el-table-column prop="content" label="内容" width="100" />
                <el-table-column prop="status" label="状态" width="100" />
                <el-table-column prop="audit-status" label="审核状态" width="100" />
                <el-table-column prop="audit-time" label="审核时间" width="100" />
                <el-table-column prop="audit-user" label="审核人" width="100" />
                <el-table-column prop="audit-remark" label="审核备注" width="100" />
                <el-table-column prop="audit-result" label="审核结果" width="100" />
                <!-- <el-table-column label="操作" width="100">
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
    name: 'AuditManage',
    data() {
        return {
            types:[],
            data:[],
            condition:{}
        }
    },
    methods: {
        search(){
            console.log(this.content)
        },
        
    },
};
</script>