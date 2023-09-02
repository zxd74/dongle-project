<template>
    <div class="main">
        <div class="condition">
            <el-button @click="queryAllGroup" type="primary" round>查询</el-button>
            <el-button @click="addGroup" type="primary" round>添加</el-button>
        </div>
        <div class="content">
            <el-table :data="groups" stripe border>
                <el-table-column prop="groupId" label="分组ID"/>
                <el-table-column prop="groupName" label="分组名"/>
                <el-table-column label="操作">
                    <template slot-scope="group">
                        <el-button @click="gotoGroupStock(group.row.groupId)" type="primary" round>分组数据</el-button>
                        <el-button @click="lookGroup(group.row)" type="info" round>信息</el-button>
                        <el-button @click="deleteGroup(group.$index,group.row.groupId)" type="danger" round>删除</el-button>
                        <el-button @click="updateGroup(group.row)" type="warning" round>更新</el-button>
                    </template>
                </el-table-column>
            </el-table>

            <!-- 添加分组 -->
            <el-dialog  title="添加分组" :visible.sync="groupVisible">
                <el-form :model="group" :label-position="labelPosition">
                    <el-form-item label="分组名称">
                        <el-input v-model="group.groupName"></el-input>
                    </el-form-item>
                    <el-form-item>
                        <el-button @click="saveGroup" type="primary" round>保存</el-button>
                        <el-button @click="groupVisible=false;group={}">取消</el-button>
                    </el-form-item>
                </el-form>
            </el-dialog >

            <!-- 分组股票管理 -->
            <el-dialog title="分组股票信息" :visible.sync="groupStockVisible">
                <el-form :model="group" :label-position="labelPosition">
                    <el-form-item label="分组名称">
                        {{group.groupName}}
                        <el-button @click="addStock">添加股票</el-button>
                    </el-form-item>
                    <el-form-item label="股票列表">
                        <el-table :data="group.stocks">
                            <el-table-column label="股票代码" prop="code"/>
                            <el-table-column label="股票名称" prop="name"/>
                            <el-table-column>
                                <template slot-scope="stock">
                                    <el-button @click="removeGroupStock(stock.$index,stock.row.code)" type="danger" round>删除</el-button>
                                </template>
                            </el-table-column>
                        </el-table>
                    </el-form-item>
                </el-form>
            </el-dialog >

            <!-- 添加股票 -->
            <el-dialog title="添加股票" :visible.sync="stockVisible">
                <el-form :model="stock" :label-position="labelPosition">
                    <el-form-item label="分组名称">
                        <el-select v-model="stock.index">
                            <el-option v-for="(item,index) in stocks" :key='"stock_"+item.code' :index="index" :label="item.name + '-' + item.code" :value="index" />
                        </el-select>
                    </el-form-item>
                    
                    <el-form-item>
                        <el-button @click="saveGroupStock" type="primary" round>添加</el-button>
                        <el-button @click="stockVisible=false;stock={}">取消</el-button>
                    </el-form-item>
                </el-form>
            </el-dialog >
        </div>
    </div>
</template>
<script>
import {
    allStockGroup,
    saveStockGroup,
    deleteStockGroup,
    getStockGroup,
    allStocks,
    addGroupStock,
    deleteGroupStock
} from '@/assets/js/Api'
export default{
    data(){
        return{
            groups:[],
            stocks:[],
            groupVisible:false,
            groupStockVisible:false,
            stockVisible:false,
            labelPosition:'left',
            group:{},
            stock:{},
        }
    },
    created(){
        this.queryAllGroup()
        this.queryAllStock()
    },
    methods:{
        queryAllStock(){
            allStocks().then(res=>{
                this.stocks = res.data
            })
        },
        queryAllGroup(){
            allStockGroup().then(res=>{
                this.groups = res.data
            })
        },
        gotoGroupStock(groupId){
            this.$router.push({path:'/group-data/',query:{groupId:groupId}})
        },
        lookGroup(group){
            this.groupStockVisible = true
            var that = this
            getStockGroup({groupId:group.groupId}).then(res=>{
                that.group = res.data
            })
        },
        addGroup(){
            this.group = {}
            this.groupVisible = true
        },
        deleteGroup(index,groupId){
            var groups = this.groups
            deleteStockGroup({groupId:groupId}).then(res=>{
                if(res.data){
                    groups.splice(index,1)
                }
            })
        },
        updateGroup(group){
            this.group = group
            this.groupVisible = true
        },
        saveGroup(){
            var group = {
                groupId:this.group.groupId,
                groupName:this.group.groupName,
            }
            saveStockGroup(group).then(res=>{
                if(res.data){
                    this.queryAllGroup()
                    this.groupVisible=false
                }
            })
        },

        addStock(){
            this.stock = {}
            this.stockVisible = true
        },
        saveGroupStock(){
            var stock = this.stocks[this.stock.index]
            var group = {
                groupId:this.group.groupId,
                stocks:[{code: stock.code}]
            }
            var that = this
            addGroupStock(group).then(res=>{
                if(res.data){
                    if(that.group.stocks == undefined) that.group.stocks = []
                    that.group.stocks.push(stock)
                }
                that.stockVisible=false
            })
        },
        removeGroupStock(index,code){
            var group = {
                groupId:this.group.groupId,
                stocks:[{code: code}]
            }
            var stocks = this.group.stocks
            deleteGroupStock(group).then(res=>{
                if(res.data){
                    stocks.splice(index,1)
                }
            })
        }
    }
}

</script>