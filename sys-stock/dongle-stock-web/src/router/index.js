import Vue from 'vue'
import VueRouter from 'vue-router'

const VueRouterPush = VueRouter.prototype.push
VueRouter.prototype.push = function push(to) {
    return VueRouterPush.call(this, to).catch(err => err)
}

Vue.use(VueRouter)
const router = new VueRouter({
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

// router.afterEach((to, from, failure) => {
//   if (failure == false) {
//       console.log("访问地址无效" + to.fullPath)
//       router.push('/404')
//   }
// })
export default router;