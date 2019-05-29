import request from '@/utils/request'

export function page(params) {
    return request({
        url: '/upms/user/page',
        method: 'post',
        data: params
    })
}

export function delObj(id) {
    return request({
        url: '/upms/user/delByIds/' + id,
        method: 'delete'
    })
}

export function getObj(id) {
    return request({
        url: '/upms/user/get/' + id,
        method: 'get'
    })
}

export function putObj(obj) {
    return request({
        url: '/upms/user/edit',
        method: 'put',
        data: obj
    })
}

export function addObj(obj) {
    return request({
        url: '/upms/user/save',
        method: 'post',
        data: obj
    })
}
