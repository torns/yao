import request from '@/utils/request'

export function initPageData(url, params) {
    return request({
        url: url,
        method: 'post',
        params
    })
}
