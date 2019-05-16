import axios from 'axios'
import router from '@/router/routers'
import {Notification, MessageBox} from 'element-ui'
import store from '@/store'
import {getToken} from '@/utils/auth'
import Config from '@/config'
import errorCode from '@/const/errorCode'

// 创建axios实例
const service = axios.create({
    baseURL: Config.baseURL,// api的base_url
    timeout: Config.timeout // 请求超时时间
});

// request拦截器
service.interceptors.request.use(config => {
    if (getToken()) {
        // 让每个请求携带自定义token 请根据实际情况自行修改
        config.headers.common['Authorization'] = 'Bearer ' + getToken()
    }
    const headers = config.headers
    if (headers['content-type'] === 'application/octet-stream;charset=utf-8') {
        return config.data
    }
    return config
}, error => {
    console.log(error);
    Promise.reject(error)
})

// respone拦截器
service.interceptors.response.use(
    response => {

        if (response.data.status == "ERROR") {
            console.error('error:' + response.data.message);
            const code = response.data.code;
            if (code.toString().startsWith("SYS")) {
                errorCode[code] = response.data.message;
            }
            Message({
                message: errorCode[code] || errorCode['default'],
                type: 'error'
            })
        }
        return response.data
    },
    error => {
        if (error.toString().indexOf('Error: timeout') !== -1) {
            Notification.error({
                title: '网络请求超时',
                duration: 2500
            })
            return Promise.reject(error)
        }
        if (error.toString().indexOf('Error: Network Error') !== -1) {
            Notification.error({
                title: '网络请求错误',
                duration: 2500
            })
            return Promise.reject(error)
        }
        console.log(error.toString())
        if (error.toString().indexOf('503') !== -1) {
            Notification.error({
                title: '服务暂时不可用，请稍后再试!',
                duration: 2500
            })
            return Promise.reject(error)
        }

        let code = 0;
        code = error.response.data.code
        if (code === 401) {
            MessageBox.confirm(
                '登录状态已过期，您可以继续留在该页面，或者重新登录',
                '系统提示',
                {
                    confirmButtonText: '重新登录',
                    cancelButtonText: '取消',
                    type: 'warning'
                }
            ).then(() => {
                store.dispatch('LogOut').then(() => {
                    location.reload() // 为了重新实例化vue-router对象 避免bug
                })
            })
        } else if (code === 403) {
            router.push({path: '/401'})
        } else {
            const errorMsg = error.response.data.message;
            console.log('errorMsg:' + errorMsg);
            if (code.toString().startsWith("SYS")) {
                errorCode[code] = error.response.data.message;
            }
            if (errorMsg !== undefined) {
                Notification.error({
                    message: errorCode[code] || errorCode['default'],
                    duration: 2500
                })
            }
        }
        return Promise.reject(error)
    }
)

export default service
