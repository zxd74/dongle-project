<template>
  <el-container>
    <el-header class="header"><Header :msg="msg"/></el-header>
    <el-container>
      <el-aside class="left"><Left /></el-aside>
      <el-container>
        <el-main class="content"><Content /></el-main>
        <el-footer class="footer"><Footer /></el-footer>
      </el-container>
    </el-container>
  </el-container>
</template>

<script>
import Left from '@/components/commons/layout/Left'
import Header from '@/components/commons/layout/Header'
import Footer from '@/components/commons/layout/Footer'
import Content from '@/components/commons/layout/Content'
import {access} from '@/assets/js/api'
import { ElMessage } from 'element-plus'

export default {
    name: 'Home',
    components: { Header, Left, Footer,Content},
    props:{
      msg: String
    },
    data(){
      return{
        // msg:"Welcome to Dongle System",
        isAccess:false
      }
    },
    created(){
      // access().then(res => {
      //   console.log(res);
      //   if(res.data == "OK"){
      //     this.isAccess = true
      //     this.$message.success("connect success with server")
      //   }
      // });
    },
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
.header {
    width: 100%;
    height: 6%;
    background-color: #409EFF;
    display: inline-block;
}
.left {
    height: 95%;width: 10%;
    position: absolute;
    background-color: #909399;
}
.content{
    left: 10%;height: 92%;width: 90%;
    padding: 5px;
    position: absolute;
}
.footer {
    height: 2%; width: 100%;
    position: absolute;
    bottom: 0;
}
</style>
  