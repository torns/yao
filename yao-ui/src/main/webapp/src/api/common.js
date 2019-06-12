import request from '@/utils/request'


export const getIp = () => {
    return request({
        url: '/back/common/getIp',
        method: 'get',
    })
}

export const getWeather = () => {
    return request({
        url: '/back/common/getWeather',
        method: 'get',
    })
}

export const getCity = () => {
    return request({
        url: '/back/common/getCity',
        method: 'get',
    })
}