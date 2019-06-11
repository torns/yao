import request from '@/utils/request'

export function saveDict(data) {
    return request({
        url: '/upms/dict/save',
        method: 'post',
        data
    })
}

export function editDict(data) {
    return request({
        url: '/upms/dict/update',
        method: 'put',
        data
    })
}

export function delDict(ids) {
    return request({
        url: `/upms/dict/delByIds/${ids}`,
        method: 'delete',
    })
}

export function saveDictData(data) {
    return request({
        url: '/upms/dictData/save',
        method: 'post',
        data
    })
}

export function delDictData(id) {
    return request({
        url: `/upms/dictData/delById/${id}`,
        method: 'delete'
    })
}

export function editDictData(data) {
    return request({
        url: '/upms/dictData/update',
        method: 'put',
        data
    })
}

// 通过类型获取字典数据
export const getDictDataByCode = (code) => {
    return request({
        url: `/upms/dictData/getByCode/${code}`,
        method: 'get',
    })
}

