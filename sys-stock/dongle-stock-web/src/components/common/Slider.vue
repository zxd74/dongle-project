<template>
    <div id="slider">
        <el-button class="el-icon-menu" :style="backColor"  @click="handlerOpen"></el-button>
        <el-menu :collapse="isCollapse">
            <template v-for="(item,index) in menus">
                <el-menu-item v-if="item.menus==undefined" :index="index.toString()" @click="goto(item.url)">
                    <i :class="item.icon"></i>
                    <span slot="title">{{item.name}}</span>
                </el-menu-item>
                <el-submenu v-else :index="index.toString()">
                    <template slot="title">
                        <i :class="item.icon"></i>
                        <span slot="title">{{item.name}}</span>
                    </template>
                    <el-menu-item-group>
                        <el-menu-item v-for="(subItem,subIndex) in item.menus" :key='"subItem-"+subIndex'  :index='index + "-" + subIndex' @click="goto(subItem.url)">
                            <i :class="subItem.icon"></i>
                            <span slot="title">{{subItem.name}}</span>
                        </el-menu-item>
                    </el-menu-item-group>
                </el-submenu>
            </template>
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
                    "icon":'el-icon-s-home',
                },
                {
                    "name":'股票管理',
                    "icon":'el-icon-s-marketing',
                    "menus":[
                        {
                            "url":'/stock-manage',
                            "name":'股票管理',
                            "icon":'el-icon-s-marketing',
                        },{
                            "url":'/stock-group',
                            "name":'股票分组',
                            "icon":'el-icon-files',
                        },
                    ]
                },
                {
                    "name":'数据管理',
                    "icon":'el-icon-s-data',
                    "menus":[
                        {
                            "url":'/data-manage',
                            "name":'数据管理',
                            "icon":'el-icon-reading',
                        },
                        {
                            "url":'/group-data',
                            "name":'分组数据',
                            "icon":'el-icon-pie-chart',
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