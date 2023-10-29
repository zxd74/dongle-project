import Vue from 'vue';
import App from './App';
import router from './router';
import axios from 'axios';
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css'; 
import './util/util.js';
import store from './store/store.js';

  // 默认主题
// import '../static/css/theme-green/index.css';       // 浅绿色主题
import "babel-polyfill";
// import echarts from 'echarts';
Vue.use(ElementUI, { size: 'small' });
Vue.prototype.$axios = axios;
// Vue.prototype.$echarts = echarts 

//使用钩子函数对路由进行权限跳转
router.beforeEach((to, from, next) => {
  console.log(localStorage.getItem('t'))
    const role = localStorage.getItem('t');
    if(!role && to.path !== '/login'){
        next('/login');
    }else{
      next()
    }
})



new Vue({
    router,
    store,
    render: h => h(App)
}).$mount('#app');
