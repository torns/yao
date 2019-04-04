import request from '@/plugin/axios'

export function GetMenu () {
  return request({
    url: '/admin/menu/userMenu',
    method: 'get'
  })
}

export function fetchTree (query) {
  return request({
    url: '/admin/menu/allTree',
    method: 'get',
    params: query
  })
}

export function addObj (obj) {
  return request({
    url: '/admin/menu/',
    method: 'post',
    data: obj
  })
}

export function getObj (id) {
  return request({
    url: '/admin/menu/' + id,
    method: 'get'
  })
}

export function delObj (id) {
  return request({
    url: '/admin/menu/' + id,
    method: 'delete'
  })
}

export function putObj (obj) {
  return request({
    url: '/admin/menu/',
    method: 'put',
    data: obj
  })
}
