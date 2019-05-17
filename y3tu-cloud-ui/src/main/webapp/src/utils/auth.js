import Cookies from 'js-cookie'
import Config from '@/config'


const TokenKey = Config.TokenKey;

export function getToken() {
    return Cookies.get(TokenKey)
}

export function setToken(token, rememberMe) {
    if (rememberMe) {
        return Cookies.set(TokenKey, token, {expires: Config.tokenCookieExpires})
    } else {
        return Cookies.set(TokenKey, token)
    }
}

export function setRefreshToken(refreshToken) {
    return Cookies.set(Config.RefreshTokenKey, refreshToken)
}

export function getRefreshToken(refreshToken) {
    return Cookies.get(Config.RefreshTokenKey)
}

export function removeToken() {
    Cookies.remove(TokenKey);
    Cookies.remove(Config.RefreshTokenKey);
}
