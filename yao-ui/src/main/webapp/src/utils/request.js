import axios from 'axios'
import {Notification, MessageBox} from 'element-ui'
import store from '@/store'
import {getToken, getRefreshToken} from '@/utils/auth'

import Config from '@/config'
import errorCode from '@/const/errorCode'

// 创建axios实例
const service = axios.create({
    baseURL: Config.baseURL,// api的base_url
    timeout: Config.timeout // 请求超时时间
});

let flag_401 = true;

// request拦截器
service.interceptors.request.use(config => {
    if (getToken()) {
        // 让每个请求携带自定义token 请根据实际情况自行修改
        config.headers.common['Authorization'] = 'Bearer ' + getToken()
        // 处理刷新token后重新请求的自定义变量
        config['refresh_token'] = false;
    }
    const headers = config.headers
    if (headers['content-type'] === 'application/octet-stream;charset=utf-8') {
        return config.data
    }
    // 处理刷新token后重新请求的自定义变量
    config['refresh_token'] = false;
    return config

}, error => {
    console.log(error);
    Promise.reject(error)
})

// respone拦截器
service.interceptors.response.use(
    response => {

        if (response.data.status == "ERROR") {
            let code = response.data.code;
            if (code !== undefined) {
                if (Config.mode === 'DEV') {
                    errorCode[code] = response.data.message;
                }
            }
            Notification.error({
                message: errorCode[code] || errorCode['default'],
                duration: 2500
            })
        }

        if (response.data.status === 'WARN') {
            Notification.warning({
                message: response.data.message,
                duration: 2500
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
        if (error.toString().indexOf('503') !== -1) {
            Notification.error({
                title: '服务暂时不可用，请稍后再试!',
                duration: 2500
            })
            return Promise.reject(error)
        }

        let code = error.response.data.code;
        if (code === "401" && !error.response.config.refresh_token) {
            let config = error.response.config;
            config['refresh_token'] = true;
            //如果是token过期，首先用refreshToken去刷新token
            let response = store.dispatch('RefreshToken').then(() => {
                config.headers.Authorization = 'Bearer ' + getToken()
                return service(config)
            }).catch((error) => {
                if(flag_401) {
                    flag_401 = false;
                    //跳转到登录页面
                    MessageBox.confirm(
                        '登录状态已过期，您可以继续留在该页面，或者重新登录',
                        '系统提示',
                        {
                            confirmButtonText: '重新登录',
                            cancelButtonText: '取消',
                            type: 'warning'
                        }
                    ).then(() => {
                        flag_401 = true;
                        store.dispatch('FedLogOut').then(() => {
                            location.reload() // 为了重新实例化vue-router对象 避免bug
                        })
                    },()=>{
                        flag_401 = true;
                    })
                }
            });

            return response;

        } else if (code === "403") {
            Notification.error({
                message: errorCode[code] || errorCode['default'],
                duration: 2500
            })
        } else {
            if (code !== undefined) {
                if (Config.mode === 'DEV') {
                    errorCode[code] = error.response.data.message;
                }
            }
            Notification.error({
                message: errorCode[code] || errorCode['default'],
                duration: 2500
            })
        }
        return Promise.reject(error)
    }
)

export default service