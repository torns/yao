import request from '@/utils/request'

export function fetchList(query) {
    return request({
        url: '/upms/user/getByPage',
        method: 'post',
        params: query
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
