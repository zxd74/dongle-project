<template>
    <div id="left">
        <el-button @click="gotoPath('/users')">goto User Manage</el-button>
        <el-button @click="gotoPath('/questions')">goto Question Manage</el-button>
    </div>
</template>

<script>

export default {
    name:'Left',
    methods:{
        gotoPath(url){
            console.log('goto ' + url)
            this.$router.push(url)
        }
    }
}
</script>