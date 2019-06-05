import request from '@/utils/request'

export function page(params) {
    return request({
        url: '/upms/user/page',
        method: 'post',
        data: params
    })
}

export function del(id) {
    return request({
        url: '/upms/user/delByIds/' + id,
        method: 'delete'
    })
}

export function get(id) {
    return request({
        url: '/upms/user/get/' + id,
        method: 'get'
    })
}

export function eidt(obj) {
    return request({
        url: '/upms/user/edit',
        method: 'put',
        data: obj
    })
}

export function save(obj) {
    return request({
        url: '/upms/user/save',
        method: 'post',
        data: obj
    })
}
