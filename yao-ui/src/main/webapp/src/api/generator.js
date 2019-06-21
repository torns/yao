import request from '@/utils/request'

export function updateGenConfig(data) {
    return request({
        url: 'api/genConfig',
        data,
        method: 'put'
    })
}