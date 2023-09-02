<template>
    <!-- 首页框架 -->
    <div id="home">
        <dongle-header></dongle-header>
        <dongle-slider></dongle-slider>
        <div id="dongle-content">
            <router-view></router-view> 
        </div>
        <dongle-footer></dongle-footer>
    </div>
</template>

<script>
import DongleHeader from './common/Header.vue'
import DongleSlider from './common/Slider.vue'
import DongleFooter from './common/Footer.vue'

export default{
    name:"Home",
    components:{ DongleHeader,DongleFooter,DongleSlider},
}
</script>