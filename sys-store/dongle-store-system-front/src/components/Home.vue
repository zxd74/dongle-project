<template>
  <div class="home">
    Home
      <d-header></d-header>
      <d-side-bar></d-side-bar>
      <d-main></d-main>
      <d-footer></d-footer>
  </div>
</template>

<script>
import DHeader from '@/components/commons/layout/Header'
import DSideBar from '@/components/commons/layout/SideBar'
import DMain from '@/components/commons/layout/Main'
import DFooter from '@/components/commons/layout/Footer'
export default {
  name: 'Home',
  components: {
    DHeader,DSideBar,DFooter,DMain
  },
  data () {
    return {

    }
  }
}
</script>
