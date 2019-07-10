/**
 * 根据用户是否具有资源表的permission字段的值；
 * 如果有表示有此页面元素(按钮)的权限，页面展示此元素，
 * 如果无表示没有此页面元素(按钮)的权限，页面上屏蔽此元素
 */
import store from '@/store'

export default {
    install(Vue, options) {

        Vue.directive('permission', {
            inserted(el, binding, vnode) {
                const {value} = binding

                //获取登录用户所具备的资源权限
                const user = store.getters && store.getters.user
                const resources = user.resources;


                if (value && value instanceof Array && value.length > 0) {
                    const permissions = value;

                    //判断 v-permission 标签定义的权限用户是否拥有
                    const hasPermission = resources.some(resource => {
                        return permissions.includes(resource.permission);
                    });

                    if (!hasPermission) {
                        //如果没有此按钮的权限 那么隐藏按钮
                        el.parentNode && el.parentNode.removeChild(el)
                    }
                } else {
                    throw new Error(`You don't have access!`)
                }
            }
        })
    }

};

