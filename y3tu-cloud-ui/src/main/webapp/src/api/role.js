import request from '@/utils/request'

export const page = (params) => {
    return request({
        url: '/upms/role/page',
        method: 'post',
        data: params
    })
}

export const saveRole = (role) => {
    return request({
        url: '/upms/role/save',
        method: 'post',
        data: role
    })
}

export const updateRole = (role) => {
    const {id, name, roleCode, description} = role
    return request({
        url: '/upms/role/update',
        method: 'put',
        data: {id, name, roleCode, description}
    })
}
export const delRoleByIds = (id) => {
    return request({
        url: `/upms/role/delByIds/${id}`,
        method: 'delete'
    })
}

export const editRoleDepartment = (roleId, dataType, departmentIds) => {
    return request({
        url: `/upms/role/editRoleDepartment/`,
        method: 'post',
        params: {
            roleId: roleId,
            dataType: dataType,
            departmentIds: departmentIds
        }
    })
}

export const editRoleResource = (roleId, resourceIds) => {
    return request({
        url: `/upms/role/editRoleResource`,
        method: 'post',
        params: {
            roleId: roleId,
            resourceIds: resourceIds
        }
    })
}


