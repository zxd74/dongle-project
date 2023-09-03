import Vue from 'vue'
import Router from 'vue-router'

Vue.use(Router)

var pages=[
  {
    path:'/data-manage',
    component:()=>require('@/components/pages/data-manage/DataManage')
  },
  {
    path:'/product-manage',
    component:()=>require('@/components/pages/product-manage/ProductManage')
  },
  {
    path:'/audit-manage',
    component:()=>require('@/components/pages/audit-manage/AuditManage')
  },
]

const router = new Router({
  routes: [
    {
      path: '/',
      children:pages
    },{ 
      path:'/home',
      redirect:'/'
    },{
      path:'/index',
      redirect:'/'
    }
  ]
})
export default router