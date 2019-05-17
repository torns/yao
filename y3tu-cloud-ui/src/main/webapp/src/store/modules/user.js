import {login, logout, getUserInfo, mobileLogin,refreshToken} from '@/api/login'
import {getToken, setToken, removeToken, getRefreshToken,setRefreshToken} from '@/utils/auth'
import {setStore, getStore} from '@/utils/store'

const user = {
    state: {
        token: getToken(),
        user: {},
        roles: [],
        // 第一次加载菜单时用到
        loadMenus: false
    },

    mutations: {
        SET_TOKEN: (state, token) => {
            state.token = token
        },
        SET_USER: (state, user) => {
            state.user = user
        },
        SET_ROLES: (state, roles) => {
            state.roles = roles
        },
        SET_LOAD_MENUS: (state, loadMenus) => {
            state.loadMenus = loadMenus
        }
    },

    actions: {
        // 登录
        Login({commit}, userInfo) {
            const username = userInfo.username;
            const password = userInfo.password;
            const rememberMe = userInfo.rememberMe;
            return new Promise((resolve, reject) => {
                login(username, password).then(res => {
                    setToken(res.access_token, rememberMe);
                    setRefreshToken(res.refresh_token);
                    commit('SET_TOKEN', res.access_token);
                    commit('SET_LOAD_MENUS', true)
                    resolve()
                }).catch(error => {
                    reject(error)
                })
            })
        },

        RefreshToken({commit}) {
            return new Promise((resolve, reject) => {
                refreshToken(getRefreshToken()).then(res => {
                    commit('SET_TOKEN', res.access_token);
                    setToken(res.access_token);
                    setRefreshToken(res.refresh_token);
                    resolve()
                }).catch(error => {
                    reject(error)
                })
            })
        },

        //手机号验证码登录
        LoginByPhone({commit}, userInfo) {
            const mobile = userInfo.mobile.trim()
            const code = userInfo.code.trim()
            return new Promise((resolve, reject) => {
                mobileLogin(mobile, code).then(response => {
                    if (response.access_token) {
                        const data = response
                        setToken(data.access_token)
                        commit('SET_TOKEN', data.access_token)
                        resolve(response)
                    }
                    resolve(response)
                }).catch(error => {
                    reject(error)
                })
            })
        },
        // 获取用户信息
        GetUserInfo({commit}) {
            return new Promise((resolve, reject) => {
                getUserInfo().then(response => {
                    const data = response.data
                    setUserInfo(data, commit);
                    resolve(response.data)
                }).catch(error => {
                    reject(error)
                })
            })
        },

        // 登出
        LogOut({commit, state}) {
            return new Promise((resolve, reject) => {
                logout({access_token: state.token}).then(() => {
                    commit('SET_TOKEN', '')
                    commit('SET_ROLES', [])

                    removeToken()
                    resolve()
                }).catch(error => {
                    reject(error)
                })
            })
        },

        // 前端 登出
        FedLogOut({commit}) {
            return new Promise(resolve => {
                commit('SET_TOKEN', '')
                commit('SET_ROLES', [])
                removeToken()
                resolve()
            })
        },
        updateLoadMenus({commit}) {
            return new Promise((resolve, reject) => {
                commit('SET_LOAD_MENUS', false)
            })
        }
    }
};

export const setUserInfo = (user, commit) => {
    // 如果没有任何权限，则赋予一个默认的权限，避免请求死循环
    if (user.roles.length === 0) {
        commit('SET_ROLES', ['ROLE_SYSTEM_DEFAULT'])
    } else {
        commit('SET_ROLES', user.roles)
    }
    commit('SET_USER', user)
}

export default user
