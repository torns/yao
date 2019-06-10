import request from '@/utils/request'

export function add(data) {
    return request({
        url: 'api/dict',
        method: 'post',
        data
    })
}

export function del(id) {
    return request({
        url: 'api/dict/' + id,
        method: 'delete'
    })
}

export function edit(data) {
    return request({
        url: 'api/dict',
        method: 'put',
        data
    })
}

// 通过类型获取字典数据
export const getDictDataByCode= (code) => {
    return request({
        url: `/upms/dictData/getByCode/${code}`,
        method: 'get',
    })
}

