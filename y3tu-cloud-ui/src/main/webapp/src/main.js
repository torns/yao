//polyfill 浏览器兼容性
import '@babel/polyfill'
import Vue from 'vue'

import 'normalize.css/normalize.css'// A modern alternative to CSS resets

import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
import locale from 'element-ui/lib/locale/lang/zh-CN' // lang i18n

import '@/styles/index.scss' // global css

import App from './App'
import router from './router/routers'

import './router/permission' // permission control

import store from './store'

import '@/icons' // icon

Vue.use(ElementUI, {locale});

import * as filters from './filters'

Vue.config.productionTip = false;

Object.keys(filters).forEach(key => {
    Vue.filter(key, filters[key])
});

new Vue({
    el: '#app',
    router,
    store,
    render: h => h(App)
});
