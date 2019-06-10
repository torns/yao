import request from '@/utils/request'

export function getDepartmentTree() {
    return request({
        url: '/upms/department/tree',
        method: 'get'
    })
}

export function save(data) {
    return request({
        url: '/upms/department/save',
        method: 'post',
        data
    })
}

export function update(data) {
    return request({
        url: '/upms/department/update',
        method: 'post',
        data
    })
}

export function del(ids) {
    return request({
        url: `/upms/department/delByIds/${ids}`,
        method: 'delete',
    })
}
