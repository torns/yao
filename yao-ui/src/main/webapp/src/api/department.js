import request from '@/utils/request'

export function getDepartmentTree() {
    return request({
        url: '/back/department/tree',
        method: 'get'
    })
}

export function save(data) {
    return request({
        url: '/back/department/save',
        method: 'post',
        data
    })
}

export function update(data) {
    return request({
        url: '/back/department/update',
        method: 'post',
        data
    })
}

export function del(ids) {
    return request({
        url: `/back/department/delByIds/${ids}`,
        method: 'delete',
    })
}
