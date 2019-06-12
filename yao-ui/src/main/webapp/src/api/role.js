import request from '@/utils/request'

export const page = (params) => {
    return request({
        url: '/back/role/page',
        method: 'post',
        data: params
    })
}

/**
 * 获取全部角色数据
 */
export const getAll=()=>{
    return request({
        url: '/back/role/getAll',
        method: 'get',
    })
}

export const saveRole = (role) => {
    return request({
        url: '/back/role/save',
        method: 'post',
        data: role
    })
}

export const updateRole = (role) => {
    const {id, name, roleCode, description} = role
    return request({
        url: '/back/role/update',
        method: 'put',
        data: {id, name, roleCode, description}
    })
}
export const delRoleByIds = (id) => {
    return request({
        url: `/back/role/delByIds/${id}`,
        method: 'delete'
    })
}

export const editRoleDepartment = (roleId, dataType, departmentIds) => {
    return request({
        url: `/back/role/editRoleDepartment/`,
        method: 'post',
        data: {
            roleId: roleId,
            dataType: dataType,
            departmentIds: departmentIds
        }
    })
}

export const editRoleResource = (roleId, resourceIds) => {
    let params = {
        roleId: roleId,
        resourceIds: resourceIds
    };
    return request({
        url: `/back/role/editRoleResource`,
        method: 'post',
        data: params
    })
}


