<template>
  <Header :msg="msg"/>
  <Left />
  <Content />
  <Footer />
  <!-- <div class="common-layout">
    <el-container>
      <el-header class="header"><Header :msg="msg"/></el-header>
      <el-container>
        <el-aside class="left"><Left /></el-aside>
        <el-container>
          <el-main>
            <Content></Content>
          </el-main>
          <el-footer class="footer"><Footer /></el-footer>
        </el-container>
      </el-container>
    </el-container>
  </div> -->
</template>

<script>
import Left from '@/components/commons/layout/Left'
import Header from '@/components/commons/layout/Header'
import Footer from '@/components/commons/layout/Footer'
import Content from '@/components/commons/layout/Content'

export default {
    name: 'Home',
    components: { Header, Left, Footer,Content},
    props:{
      msg: String
    },
    data(){
      return{
        // msg:"Welcome to Dongle System",
      }
    }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
h3 {
    margin: 40px 0 0;
}


</style>
  