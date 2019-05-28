import request from '@/utils/request'


export const getIp = () => {
    return request({
        url: '/upms/common/getIp',
        method: 'get',
    })
}

export const getWeather = () => {
    return request({
        url: '/upms/common/getWeather',
        method: 'get',
    })
}

export const getCity = () => {
    return request({
        url: '/upms/common/getCity',
        method: 'get',
    })
}