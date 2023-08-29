<template>
    <div id="slider">
        <el-button class="el-icon-menu" :style="backColor"  @click="handlerOpen"></el-button>
        <el-menu default-active="2" :collapse="isCollapse">
            <el-menu-item index="1" @click="goto('/home')">
                <i class="el-icon-s-home"></i>
                <span slot="title">首页</span>
            </el-menu-item>
            <el-submenu index="2">
                <template slot="title">
                    <i class="el-icon-s-marketing"></i>
                    <span slot="title">股票管理</span>
                </template>
                <el-menu-item-group>
                    <el-menu-item index="2-1" @click="goto('/stock-manage')">股票管理</el-menu-item>
                </el-menu-item-group>
            </el-submenu>
            <el-submenu index="3">
                <template slot="title" >
                    <i class="el-icon-s-data"></i>
                    <span slot="title">数据管理</span>
                </template>
                <el-menu-item-group>
                    <el-menu-item index="3-1" @click="goto('/data-manage')">数据管理</el-menu-item>
                    <el-menu-item index="3-2" @click="goto('/group-data')">分组数据</el-menu-item>
                </el-menu-item-group>
            </el-submenu>
        </el-menu>
    </div>
</template>
<script>
export default{
    name:'Slider',
    data(){
        return{
            isCollapse:false,
            backColor:"background-color:blue;",
            menus:[
                {
                    "url":'/home',
                    "name":'首页',
                },
                {
                    "url":'',
                    "name":'股票管理',
                    "menus":[
                        {
                            "url":'/stock-manage',
                            "name":'股票管理',
                        },
                    ]
                },
                {
                    "name":'数据管理',
                    "menus":[
                        {
                            "url":'/data/data-manage',
                            "name":'数据管理',
                        },
                        {
                            "url":'/data/group-data',
                            "name":'分组数据',
                        },
                    ]
                }
            ],
        };
    },
    methods:{
        goto(url){
            this.$router.push(url)
        },
        handlerOpen(){
            this.isCollapse = !this.isCollapse
            this.backColor = this.isCollapse?"background-color:blue;":"background-color:black"
        }
    }
}
</script>