import request from '@/utils/request'

/**
 * 根据用户id查询用户能访问的菜单
 * @param userId
 */
export function getMenu(userId) {
    return request({
        url: '/back/resource/menu/tree/' + userId,
        method: 'get',
    })
}

export function getMenuTree() {
    return request({
        url: '/back/resource/menu/getAllMenuTree',
        method: 'get'
    })
}

export function saveReource(resource) {
    return request({
        url: '/back/resource/save',
        method: 'post',
        data: resource
    })
}

export const updateReource = (resource) => {
    return request({
        url: '/back/resource/update',
        method: 'post',
        data: resource
    })
}


export const deleteResourceById = (ids) => {
    return request({
        url: `back/resource/delByIds/${ids}`,
        method: 'delete'
    })
}
