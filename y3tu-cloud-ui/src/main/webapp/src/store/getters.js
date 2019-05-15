const getters = {
    sidebar: state => state.app.sidebar,
    device: state => state.app.device,
    visitedViews: state => state.tagsView.visitedViews,
    cachedViews: state => state.tagsView.cachedViews,
    token: state => state.user.token,
    avatar: state => state.user.avatar,
    name: state => state.user.name,
    roles: state => state.user.roles,
    permission_routers: state => state.permission.routers,
    addRouters: state => state.permission.addRouters,
    permissions: state => state.user.permissions,
    socketApi: state => state.api.socketApi,
    imagesUploadApi: state => state.api.imagesUploadApi,
    updateAvatarApi: state => state.api.updateAvatarApi,
    qiNiuUploadApi: state => state.api.qiNiuUploadApi,
    sqlApi: state => state.api.sqlApi,
    swaggerApi: state => state.api.swaggerApi
};
export default getters
