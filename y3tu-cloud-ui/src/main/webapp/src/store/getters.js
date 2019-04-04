const getters = {
  accessToken: state => state.d2admin.user.accessToken,
  refreshToken: state => state.d2admin.user.refreshToken,
  userInfo: state => state.d2admin.user.info,
  roles: state => state.d2admin.user.roles,
  menu: state => state.d2admin.user.menu,
  permissions: state => state.d2admin.user.permissions
}
export default getters
