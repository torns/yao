import axios from 'axios'
import {
    Message
} from 'element-ui'
import store from '../store'
import errorCode from '@/const/errorCode'

// 创建axios实例
const service = axios.create({
    baseURL: 'http://127.0.0.1:2019', // api的base_url
    timeout: 10000 // 请求超时时间
})

// request拦截器
service.interceptors.request.use(config => {
    if (store.getters.token) {
        // 让每个请求携带自定义token 请根据实际情况自行修改
        config.headers.common['Authorization'] = 'Bearer ' + store.getters.token
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
            if(code.toString().startsWith("SYS")){
                errorCode[code]=response.data.message;
            }
            Message({
                message: errorCode[code] || errorCode['default'],
                type: 'error'
            })
        }
        return response.data
    },
    error => {

        if (error.response.data.status == "ERROR") {
            console.error('error:' + error.response.data.message);
            const code = error.response.data.code;
            if(code.toString().startsWith("SYS")){
                errorCode[code]=error.response.data.message;
            }
            Message({
                message: errorCode[code] || errorCode['default'],
                type: 'error'
            })
        }
        return Promise.reject(error)
    }
)

export default service
