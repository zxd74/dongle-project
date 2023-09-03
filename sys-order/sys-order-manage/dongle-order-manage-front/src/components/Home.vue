<template>
  <div class="home">
    <el-container>
      <el-header>Header</el-header>
      <el-container>
        <el-aside width="200px">Menus</el-aside>
        <el-main>
          Main
          <router-view/>
        </el-main>
      </el-container>
      <el-footer>Footer</el-footer>
    </el-container>
    
  </div>
</template>

<script>
export default {
  name: 'Home',
  data () {
    return {
    }
  }
}
</script>
