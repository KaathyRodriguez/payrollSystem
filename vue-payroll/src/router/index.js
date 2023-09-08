import Vue from 'vue'
import VueRouter from 'vue-router'
import store from "@/store";

Vue.use(VueRouter)

const routes = [
  {
    path: '/login',
    name: '登录',
    component: () => import('../views/Login.vue')
  },
  {
    path: '/404',
    name: '404',
    component: () => import('../views/404.vue')
  },
  {
    path: '/',
    component: () => import('../views/Manage.vue'),
    redirect: "/home",
    children: [
      {path: 'home', name: '首页', component: () => import('../views/HomeView.vue')},
      {path: 'user', name: '用户管理', component: () => import('../views/User.vue')},
      {path: 'staff', name: '教职工信息', component: () => import('../views/Staff.vue')},
      {path: 'personnelData', name: '人事数据', component: () => import('../views/PersonnelData.vue')},
      {path: 'salary', name: '工资信息', component: () => import('../views/Salary.vue')},
      {path: 'financialStatement', name: '财务报表',component:()=>import('../views/FinancialStatement.vue')}
    ],
  }

]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

// 提供一个重置路由的方法
export const resetRouter = () => {
  router.matcher = new VueRouter({
    mode: 'history',
    base: process.env.BASE_URL,
    routes
  })
}

router.beforeEach((to, from, next) => {
  localStorage.setItem("currentPathName", to.name)  // 设置当前的路由名称
  store.commit("setPath")
  // 如果访问的是登录界面则直接放行
  if (to.path === '/login') return next()
  //获取用户页面token信息
  // let token = localStorage.getItem("user")
  let token = window.sessionStorage.getItem('token')
  window.sessionStorage.setItem('token',token)
  //如果token数据为null 则跳转到指定路径
  if (!token) return next("/login")
  next()
})

export default router
