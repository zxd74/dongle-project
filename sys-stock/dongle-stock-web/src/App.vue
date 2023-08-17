<template>
  <div id="app">
    <home></home>
  </div>
</template>

<script>
import Home from '@/components/Home'
export default {
  components:{Home},
  name: 'App',
}
</script>

<style>
#app {
  font-family: 'Avenir', Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
}
body{
  margin: 0;
}
</style>
