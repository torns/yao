import {login, logout, getUserInfo, mobileLogin} from '@/api/login'
import {getToken, setToken, removeToken} from '@/utils/auth'
import {setStore, getStore} from '@/utils/store'

const user = {
    state: {
        token: getToken(),
        user: {},
        roles: [],
        permissions: getStore({name: 'permissions'}) || {}
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
        SET_PERMISSIONS: (state, permissions) => {
            const list = {}
            for (let i = 0; i < permissions.length; i++) {
                list[permissions[i]] = true
            }
            state.permissions = list
            setStore({
                name: 'permissions',
                content: state.permissions,
                type: 'session'
            })
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
                    setToken(res.data.access_token, rememberMe);
                    commit('SET_TOKEN', res.data.access_token);
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
                    if (data.roles && data.roles.length > 0) { // 验证返回的roles是否是一个非空数组
                        commit('SET_ROLES', data.roles)
                    } else {
                        reject('getInfo: roles must be a non-null array !')
                    }
                    commit('SET_NAME', data.username)
                    commit('SET_AVATAR', data.avatar)
                    let permissions = data.resources.map(resource => resource.permission);
                    commit('SET_PERMISSIONS', permissions);
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

                    commit('SET_NAME', [])

                    commit('SET_AVATAR', [])

                    commit('SET_PERMISSIONS', {})

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
                commit('SET_PERMISSIONS', {})
                removeToken()
                resolve()
            })
        }
    }
};

export const setUserRole = (res, commit) => {
    // 如果没有任何权限，则赋予一个默认的权限，避免请求死循环
    if (res.roles.length === 0) {
        commit('SET_ROLES', ['ROLE_SYSTEM_DEFAULT'])
    } else {
        commit('SET_ROLES', res.roles)
    }
    commit('SET_USER', res)
}

export default user
