import Vue from 'vue'
import Router from 'vue-router'

Vue.use(Router)
const router = new Router({
  mode:'hash',
  routes: [
    {
      path: '/',
      component:()=>import('@/components/pages/HomeData')
    },
    {
      path:'/index',
      redirect:'/'
    },
    {
      path:'/home',
      redirect:'/'
    },
    {
      path:'/stock-manage',
      component:()=>import('@/components/pages/stock/StockManage'),
    },
    {
      path:'/data-manage',
      component:()=>import('@/components/pages/data/DataManage'),
    },
    {
      path:'/404',
      name:'404',
      component:{template:"<div>404</div>"}
    }
  ]
})

// router.beforeEach(async (to,from)=>{
//   return true
// })

router.afterEach((to, from, failure) => {
  if (failure == false) {
      console.log("访问地址无效" + to.fullPath)
      router.push('/404')
  }
})
export default router;