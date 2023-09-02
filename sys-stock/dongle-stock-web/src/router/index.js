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
      path:'/index',
      redirect:'/'
    },
    {
      path:'/home',
      redirect:'/'
    },
    {
      path:'/',
      component:()=>import('@/components/Home'),
      children:[
        {
          path:'stock-manage',
          component:()=>import('@/components/pages/stock/StockManage'),
        },
        {
          path:'stock-group',
          component:()=>import('@/components/pages/stock/StockGroup'),
        },
        {
          path:'data-manage',
          component:()=>import('@/components/pages/data/DataManage'),
        },
        {
          path:'group-data',
          component:()=>import('@/components/pages/data/GroupStockData'),
        }
      ]
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