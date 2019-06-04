//引入ElementUI和样式
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
import 'normalize.css/normalize.css'// A modern alternative to CSS resets


//引入i18n
import locale from 'element-ui/lib/locale/lang/zh-CN'

// 组件
import '@/components'

// 功能插件
import pluginError from '@/plugin/error'

import {setStore, getStore, removeStore} from '@/utils/store'
import {copyObj} from '@/utils/util'
import {isEmpty, isNotEmpty} from "@/utils/validate";


export default {
    install(Vue, options) {
        //ElementUI
        Vue.use(ElementUI);
        Vue.use(ElementUI, {locale});

        // 插件
        Vue.use(pluginError)

        // 设置为 false 以阻止 vue 在启动时生成生产提示。
        // https://cn.vuejs.org/v2/api/#productionTip
        Vue.config.productionTip = false


        // 挂载全局使用的方法
        Vue.prototype.setStore = setStore;
        Vue.prototype.getStore = getStore;
        Vue.prototype.removeStore = removeStore;
        Vue.prototype.copyObj = copyObj;
        Vue.prototype.isEmpty = isEmpty;
        Vue.prototype.isNotEmpty = isNotEmpty;

    }
}