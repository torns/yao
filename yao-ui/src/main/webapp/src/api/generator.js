import request from '@/utils/request'

export function getTables(data) {
    return request({
        url: 'back/generator/getTables',
        data,
        method: 'post'
    })
}

export function getTable(tableName) {
    return request({
        url: `back/generator/getTable/${tableName}`,
        method: 'get'
    })
}