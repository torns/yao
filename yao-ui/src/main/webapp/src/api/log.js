import request from '@/utils/request'

export const fetchLogPage = (param) => {
    return request({
        url: '/log/log/page',
        method: 'get',
        params: param
    })
}

