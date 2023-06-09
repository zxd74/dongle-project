import { createRouter, createWebHistory } from 'vue-router'
import {canUserAccess} from '@/assets/js/common.js'
import Login from '@/components/pages/Login'
import Page_404 from '@/components/pages/404'

const routes = [
    {
        path:'/',
        children:[
            {
                path:'/home',
                redirect:'/',
            },
            {
                path:'/users',
                // ()=> import() 代路由懒加载
                component:() => import('@/components/pages/user/UserManage'),
                meta:{
                    title:"User Manage"
                },
            },
            {
                path:'/questions',
                component:() => import('@/components/pages/question/QuestionManage'),
                meta:{
                    title:"Question Manage"
                },
            },
            {
                path:'/questions/types',
                component:() => import('@/components/pages/question/QuestionType'),
                meta:{
                    title:"Question Type"
                }
            },
            {
                path:'/exams',
                component:() => import('@/components/pages/exam/ExamManage'),
                meta:{
                    title:"Exama Manage"
                },
            },
            {
                path:'/data',
                component:() => import('@/components/pages/data/Data'),
                meta:{
                    title:"Data"
                },
            },
        ]
    },
    {
        path:'/404',
        component:Page_404
    },
    {
        path:'/login',
        component:Login
    }
]

const router = createRouter({
    history: createWebHistory(),
    routes,
})
router.beforeEach(async (to,from)=>{
    const canAccess = await canUserAccess()
    var url = to.fullPath
    if(url != '/login' && !canAccess){
        console.log("无权限访问")
        console.log(to)
        return '/login'
    }else if(url == '/login' && canUserAccess){
        return '/'
    }
    return true
})

// router.beforeEach(async (to,from,next)=>{
//     const canAccess = await canUserAccess()
//     console.log(to)
//     next()
// })

router.afterEach((to, from, failure) => {
    if (failure == false) {
        console.log("访问地址无效" + to.fullPath)
        router.push('/404')
    }
})

export default router;