/**
 * 系统全局配置
 */
export default {

    /**
     * 统一请求后端路径
     */
    //baseURL: "http://132.232.11.210:2019",
    baseURL:"http://127.0.0.1:2019",
    /**
     * 模式 DEV:开发 PROD:生产
     */
    mode: 'DEV',
    /**
     * 记住密码状态下的token在Cookie中存储的天数，默认1天
     */
    tokenCookieExpires: 1,
    /**
     * 记住密码状态下的密码在Cookie中存储的天数，默认1天
     */
    passCookieExpires: 1,
    /**
     * 此处修改网站名称
     */
    webName: 'Yao',
    /**
     * token key
     */
    TokenKey: 'YADMIN-TOKEN-KEY',
    /**
     * refreshToken key
     */
    RefreshTokenKey: 'YADMIN-REFRESH-TOKEN-KEY',

    /**
     * 请求超时时间，毫秒（默认2分钟）
     */
    timeout: 1200000,

    /**
     * 是否显示 tagsView
     */
    tagsView: true,

    /**
     * 固定头部
     */
    fixedHeader: false,

    /**
     * 是否显示logo
     */
    sidebarLogo: true,

    /**
     * 是否显示设置的悬浮按钮
     */
    settingBtn: true
}
