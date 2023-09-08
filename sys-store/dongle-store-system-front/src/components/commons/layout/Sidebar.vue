<template>
    <div class="sidebar">
        <template v-for="item in menus">
            <el-button type="button" @click="goto(item.url)" :permission="item.perm">{{item.name}}</el-button>
        </template>
    </div>
</template>
<script>
export default {
    name: 'Sidebar',
    data() {
        return {
            menus:[
                {url:'/data-manage',name:'数据管理',perm:"*"},
                {url:'/product-manage',name:'商品管理',perm:"*"},
                {url:'/order-manage',name:'订单管理',perm:"*"},
                {url:'/user-manage',name:'会员管理',perm:"*"},
                {url:'/sys-manage',name:'系统管理',perm:"*"},
                {url:'/auth-manage',name:'权限管理',perm:"*"},
                {url:'/log-manage',name:'日志管理',perm:"*"},
            ],
        }
    },
    methods: {
        goto(url) {
            console.log('goto:',url)
            if(this.$router.match(url)){

            }
            var that = this
            this.$router.push(url).catch(()=>{})
        },
    }
}
</script>