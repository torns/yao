import request from '@/utils/request'
import qs from 'qs'

export function login(username, password) {
    let grantType = 'password'
    let clientId = 'cloud'
    let clientSecret = 'cloud'
    let param = qs.stringify({
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

/**
 * 根据refreshToken刷新token
 */
export function refreshToken(refreshToken) {
    let grantType = 'refresh_token'
    let clientId = 'cloud'
    let clientSecret = 'cloud'
    let param = qs.stringify({
        refresh_token: refreshToken,
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
        url: '/back/user/mobile/' + mobile,
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
