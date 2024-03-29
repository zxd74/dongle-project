import Vue from 'vue'
import Router from 'vue-router'

Vue.use(Router)

 const router = new Router({
  mode: 'history',
  routes: [
    {
      path: '/',
      meta: { title: '首页' },
      component: () => import('@/components/Home'),
      children: [
        {
          path: 'product-manage',
          component: () => import('@/components/pages/products/ProductManage'),
          meta: { title: '商品管理' }
        },
        {
          path: 'product-detail',
          component: () => import('@/components/pages/products/ProductDetail'),
          meta: { title: '商品详情' }
        },
        {
          path: 'data-manage',
          component: () => import('@/components/pages/data/DataManage'),
          meta: { title: '数据管理' }
        },
        {
          path: 'user-manage',
          component: () => import('@/components/pages/user-manage/UserManage'),
          meta: { title: '用户管理' },
          children:[
            {
              path: 'sys',
              component: () => import('@/components/pages/user-manage/SystemUserManage'),
              meta: { title: '系统用户管理' },
            },
            {
              path: 'users',
              component: () => import('@/components/pages/user-manage/StoreUserManage'),
              meta: { title: '会员管理' },
            },
            {
              path: 'merchants',
              component: () => import('@/components/pages/user-manage/StoreMerchantManage'),
              meta: { title: '商户管理' },
            }
          ]
        }
      ]
    },
    {
      path:"/index",
      redirect:"/",
    },{
      path: '/home',
      redirect:"/",
    },
    {
      path: '/login',
      component: () => import('@/components/Login'),
      meta: { title: '登录' }
    },
    {
      path: '/404',
      component: () => import('@/components/404'),
      meta: { title: '404' }
    },
  ]
})

router.beforeEach((to, from, next) => {
  // 设置页面标题
  document.title = to.meta.title || 'Dongle Store';
  next()
})
router.afterEach((to,from) => {

})

export default router