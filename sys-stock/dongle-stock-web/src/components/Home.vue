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
<style>
#root{
    width: 100%;
    height: 100%;
    position: fixed;
}
#header{
    width: 100%;
    height: 5%;
    border-bottom: 1px solid blue;
    position: fixed;
}
#slider{
    width: 10%;
    border-right: 1px solid green;
    /* float: left; */
    text-align: left;
    position: fixed;
}
#dongle-content{
    width: 90%;
    left: 10%;
    margin-left: 10px;
    position: absolute;
}
#dongle-content,#slider{
    top:5%;
    height: 93%;
}
#footer{
    position: fixed;
    width: 100%;
    height: 2%;
    bottom: 1px;
    border-top: 1px solid black;
}
.condition{
    width: 100%;
    margin-top: 5px;
    margin-bottom: 5px;
}
</style>