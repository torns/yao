import request from '@/utils/request'

/**
 *  根据用户id查询用户能访问的菜单
 * @param userId
 * @constructor
 */
export function GetMenu(userId) {
    return request({
        url: '/upms/resource/menu/tree/'+userId,
        method: 'get',
    })
}

export function getAllReource() {
    return request({
        url: '/upms/resource/menu/getAllMenuTree',
        method: 'get'
    })
}

export function saveReource(resource) {
    return request({
        url: '/admin/resource',
        method: 'post',
        data: resource
    })
}

export const updateReource = (resource) => {
    return request({
        url: '/admin/resource',
        method: 'put',
        data: resource
    })
}

export const getResourceById = (id) => {
    return request({
        url: 'admin/resource/id/' + id,
        method: 'get'
    })
}

export const deleteResourceById = (id) => {
    return request({
        url: 'admin/resource/id/' + id,
        method: 'delete'
    })
}
