<template>
    <div id="left">
        <label class="menu" v-for="(menu, index) in menus" :key="'menu_' + index" @click="gotoPath(menu.url)">{{menu.name}}</label>
    </div>
</template>

<script>
import {local} from '@/assets/js/api.js'
export default {
    name:'Left',
    data(){
        return{
            menus:[]
        }
    },
    created(){
        local('/static/menus.json').then(res=>{
            this.menus = res.data
        })
    },
    methods:{
        gotoPath(url){
            console.log('goto ' + url)
            this.$router.push(url)
        }
    }
}
</script>

<style scoped>
#left{
    text-align: left;
    padding-left: 20px;
    padding-top: 20px;
}
.menu{
    display: block;
}
</style>