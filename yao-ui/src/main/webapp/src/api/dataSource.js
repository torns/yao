import request from '@/utils/request'

/**
 * 获取全部数据
 */
export function getAll() {
    return request({
        url: 'back/report/dataSource/getAll',
        method: 'get',
    })
}

/**
 * 分页查询
 * @param params
 */
export function page(params) {
    return request({
        url: 'back/report/dataSource/page',
        method: 'post',
        data: params
    })
}

/**
 * 获取单条数据
 * @param id
 */
export function get(id) {
    return request({
        url: `back/report/dataSource/get/${id}`,
        method: 'get'
    })
}

/**
 * 新增保存数据
 * @param obj
 */
export function save(obj) {
    return request({
        url: 'back/report/dataSource/save',
        method: 'post',
        data: obj
    })
}

/**
 * 更新数据
 * @param obj
 */
export function update(obj) {
    return request({
        url: 'back/report/dataSource/update',
        method: 'put',
        data: obj
    })
}

/**
 * 根据主键删除数据
 * @param id
 */
export function delById(id) {
    return request({
        url: `back/report/dataSource/delById/${id}`,
        method: 'delete'
    })
}

/**
 * 根据主键批量删除数据
 * @param ids
 */
export function delByIds(ids) {
    return request({
        url: `back/report/dataSource/delByIds/${ids}`,
        method: 'delete',
    })
}

