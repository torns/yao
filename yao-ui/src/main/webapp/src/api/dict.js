import request from '@/utils/request'

export function saveDict(data) {
    return request({
        url: '/back/dict/save',
        method: 'post',
        data
    })
}

export function editDict(data) {
    return request({
        url: '/back/dict/update',
        method: 'put',
        data
    })
}

export function delDict(ids) {
    return request({
        url: `/back/dict/delByIds/${ids}`,
        method: 'delete',
    })
}

export function saveDictData(data) {
    return request({
        url: '/back/dictData/save',
        method: 'post',
        data
    })
}

export function delDictData(id) {
    return request({
        url: `/back/dictData/delById/${id}`,
        method: 'delete'
    })
}

export function editDictData(data) {
    return request({
        url: '/back/dictData/update',
        method: 'put',
        data
    })
}

// 通过类型获取字典数据
export const getDictDataByCode = (code) => {
    return request({
        url: `/back/dictData/getByCode/${code}`,
        method: 'get',
    })
}

