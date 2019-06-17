import router from './routers'
import store from '@/store'
import Config from '@/config'
import NProgress from 'nprogress' // progress bar
import 'nprogress/nprogress.css'// progress bar style
import {getToken} from '@/utils/auth' // getToken from cookie

NProgress.configure({showSpinner: false})// NProgress Configuration

const whiteList = ['/login']// no redirect whitelist

router.beforeEach((to, from, next) => {
    if (to.meta.title) {
        document.title = to.meta.title + ' - ' + Config.webName
    }
    NProgress.start() // start progress bar
    if (getToken()) {
        // 已登录且要跳转的页面是登录页
        if (to.path === '/login') {
            next({path: '/'})
            NProgress.done() // if current page is dashboard will not trigger	afterEach hook, so manually handle it
        } else {
            // 判断当前用户是否已拉取完user_info信息
            if (store.getters.roles.length === 0) {
                // 拉取user_info
                store.dispatch('GetUserInfo').then(res => {
                    store.dispatch('GetMenu', res.id);
                    next()
                }).catch((err) => {
                    console.log(err)
                })
            } else {
                next()
            }
        }
    } else {
        /* has no token*/
        if (whiteList.indexOf(to.path) !== -1) { // 在免登录白名单，直接进入
            next()
        } else {
            next(`/login?redirect=${to.path}`) // 否则全部重定向到登录页
            NProgress.done()
        }
    }
})


router.afterEach(() => {
    NProgress.done() // finish progress bar
})
