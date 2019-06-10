import request from '@/utils/request'

/**
 *  根据用户id查询用户能访问的菜单
 * @param userId
 * @constructor
 */
export function getMenu(userId) {
    return request({
        url: '/upms/resource/menu/tree/'+userId,
        method: 'get',
    })
}

export function getMenuTree() {
    return request({
        url: '/upms/resource/menu/getAllMenuTree',
        method: 'get'
    })
}

export function saveReource(resource) {
    return request({
        url: '/upms/resource/save',
        method: 'post',
        data: resource
    })
}

export const updateReource = (resource) => {
    return request({
        url: '/upms/resource/update',
        method: 'post',
        data: resource
    })
}


export const deleteResourceById = (ids) => {
    return request({
        url: `upms/resource/delByIds/${ids}`,
        method: 'delete'
    })
}
