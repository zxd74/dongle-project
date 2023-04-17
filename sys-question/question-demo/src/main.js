import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
import '@/assets/css/main.css'
import 'element-plus/theme-chalk/dark/css-vars.css'

createApp(App)
    .use(ElementPlus,{size: 'small'})
    .use(router)
    .mount('#app');


