<template>
    <div class="container">
        <div class="condition">
            <el-input autosize  type="text" v-model="condition.name" style="width:200px;">商品名称</el-input>
            <el-select v-model="condition.type" placeholder="请选择商品分类">
                    <el-option v-for="item in types" :key="'type-' + item.value" :label="item.name" :value="item.value" />
                </el-select>
            <el-button type="primary" @click="search">搜索</el-button>
        </div>
        <div class="content">
            <div class="table">
                <el-table :data="produts" title="商品列表" style="width: 100%">
                    <el-table-column prop="id" label="ID" width="180"></el-table-column>
                    <el-table-column prop="name" label="商品名称" width="180"></el-table-column>
                    <el-table-column prop="category" label="商品分类"></el-table-column>
                    <el-table-column prop="image" label="图片" width="180"></el-table-column>
                    <el-table-column prop="images" label="轮播图">
                        <template slot-scope="scope">
                            <!-- 轮播图 -->
                        </template>
                    </el-table-column>
                    <el-table-column prop="desc" label="描述"></el-table-column>
                    <el-table-column prop="price" label="价格" width="180" />
                    <el-table-column prop="stock" label="库存" width="180"/>
                    <el-table-column prop="status" label="状态" width="180"></el-table-column>
                    <el-table-column prop="audti-status" label="审核状态" width="180"></el-table-column>
                    <el-table-column prop="cdt" label="创建时间" width="180"></el-table-column>
                    <el-table-column prop="udt" label="更新时间" width="180"></el-table-column>
                    <el-table-column label="操作" width="180">
                        <template slot-scope="scope">
                            <el-button type="danger" size="mini" @click="handlerQuery(scope.$index, scope.row)">数据</el-button>
                            <el-button type="primary" size="mini" @click="handleEdit(scope.$index, scope.row)">编辑</el-button>
                            <el-button type="danger" size="mini" @click="handleDelete(scope.$index, scope.row)">删除</el-button>
                        </template>
                    </el-table-column>

                </el-table>
            </div>
        </div>
    </div>
</template>
<script>
export default {
    name: 'ProductManage',
    data() {
        return {
            produts:[],
            condition:{},
            types:[],
        }
    },
    created(){
        console.log('product manage created')
    },
    methods:{
        search(){

        },
        handleEdit(index, row) {
            console.log(index, row);
        },
        handleDelete(index, row) {
            console.log(index, row);
        },
        handlerQuery(index, row) {
            console.log(index, row);
        }
    }
}
</script>