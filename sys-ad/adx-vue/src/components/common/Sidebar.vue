<template>
    <div class="sidebar">
        <el-menu class="sidebar-el-menu" :collapse="collapse" background-color="#324157"
            text-color="#bfcbd9" active-text-color="#20a0ff" unique-opened router @select="handleSelect"> 
            <template v-for="item in items">
                <template v-if="item.subs">
                    <el-submenu :index="item.index" :key="item.index">
                        <template slot="title">
                            <i :class="item.icon"></i><span slot="title">{{ item.title }}</span>
                        </template>
                        <el-menu-item-group  v-for="(subItem,i) in item.subs" :key="i" :index="subItem.index">
                            <template v-if="!subItem.subs">
                                 <el-menu-item :index="subItem.index" :key="subItem.index">
                                     {{ subItem.title }}
                                 </el-menu-item>
                            </template>
                            <template v-else>
                                <el-submenu :index="subItem.index" :key="subItem.index">
                                <template slot="title">{{ subItem.title }}</template>
                                <el-menu-item v-for="(subItem_,j) in subItem.subs" :key="j" :index="subItem_.index">
                                    {{subItem_.title}}
                                </el-menu-item>
                            </el-submenu>
                            </template>
                        </el-menu-item-group>
                        <!-- <el-menu-item v-for="(subItem,i) in item.subs" :key="i" :index="subItem.index">
                            {{ subItem.title }}
                        </el-menu-item>
                        </el-menu-item-group>
                        <el-menu-item-group v-else>
                            <el-submenu :index="subItem.index" :key="subItem.index">
                                <template slot="title">{{ subItem.title }}</template>
                                <el-menu-item v-for="(subItem_,j) in subItem.subs" :key="j" :index="subItem_.index">
                                    {{subItem_.title}}
                                </el-menu-item>
                            </el-submenu>
                        </el-menu-item-group> -->
                    </el-submenu>
                </template>
                <template v-else>
                    <el-menu-item :index="item.index" :key="item.index">
                        <i :class="item.icon"></i><span slot="title">{{ item.title }}</span>
                    </el-menu-item>
                </template>
            </template>
        </el-menu>
    </div>
</template>

<script>
    import bus from '../common/bus';
    export default {

        data() {
            return {
                collapse: false,
                items: [],
            }
        },
        created(){
            // 通过 Event Bus 进行组件间通信，来折叠侧边栏
            bus.$on('collapse', msg => {
                this.collapse = msg;
            })
            let menus = this.$store.state.menus.length > 0 ? this.$store.state.menus :
                JSON.parse(localStorage.getItem("menuData"))
            this.items = menus;
            let map = new Map();
            menus.forEach(element => {
                element.subs.forEach(_e => {
                    map.set(_e.index,_e.readonly)
                });
            });
            this.$store.commit('setMap', map);
        },
        methods: {
             handleSelect(key, keyPath) {
                this.$store.commit('setPath', key);
             },
        }
    }
</script>

<style scoped>
    .sidebar{
        display: block;
        position: absolute;
        left: 0;
        top: 70px;
        bottom:0;
        overflow-y: scroll;
    }
    .sidebar::-webkit-scrollbar{
        width: 0;
    }
    .sidebar-el-menu:not(.el-menu--collapse){
        width: 220px;
    }
    .sidebar > ul {
        height:100%;
    }
</style>
