import request from '@/utils/request'

export function page(params) {
    return request({
        url: '/back/user/page',
        method: 'post',
        data: params
    })
}

export function del(id) {
    return request({
        url: '/back/user/delByIds/' + id,
        method: 'delete'
    })
}

export function get(id) {
    return request({
        url: '/back/user/get/' + id,
        method: 'get'
    })
}

export function edit(obj) {
    return request({
        url: '/back/user/edit',
        method: 'put',
        data: obj
    })
}

export function save(obj) {
    return request({
        url: '/back/user/save',
        method: 'post',
        data: obj
    })
}
