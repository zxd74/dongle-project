<template>
    <el-button @click="gotoPath('/user')">goto User</el-button>
    <div id="left">

    </div>
</template>

<script>

export default {
    name:'Left',
    methods:{
        gotoPath(url){
            console.log(url)
            this.$router.push(url)
        }
    }
}
</script>