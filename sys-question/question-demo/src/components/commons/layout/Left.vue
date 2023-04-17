<template>
    <div class="left">
        <el-button @click="gotoPath('/user')">goto User</el-button>
    </div>
</template>

<script>

export default {
    name:'Left',
    methods:{
        gotoPath(url){
            console.log(url)
            console.log(this.$router)
            this.$router.push(url)
        }
    }
}
</script>