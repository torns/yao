import request from '@/utils/request'
import qs from 'qs'

export function login(username, password) {
    var grantType = 'password'
    var clientId = 'cloud'
    var clientSecret = 'cloud'
    var param = qs.stringify({
        username: username,
        password: password,
        client_id: clientId,
        client_secret: clientSecret,
        grant_type: grantType
    });
    return request({
        url: '/token/oauth/token',
        method: 'post',
        data: param
    })
}

export function sendMobileCode(mobile) {
    return request({
        url: '/upms/user/mobile/' + mobile,
        method: 'get'
    })
}

export function mobileLogin(mobile, code) {
    var grantType = 'mobile'
    var clientId = 'cloud'
    var clientSecret = 'cloud'
    var param = qs.stringify({
        mobile: mobile,
        code: code,
        client_id: clientId,
        client_secret: clientSecret,
        grant_type: grantType
    })
    return request({
        url: '/auth/mobile/token',
        method: 'post',
        data: param
    })
}

export function getUserInfo() {
    return request({
        url: '/auth/user',
        method: 'get',
    })
}

export function logout(token) {
    return request({
        url: '/auth/token',
        method: 'put',
        data: token
    })
}
