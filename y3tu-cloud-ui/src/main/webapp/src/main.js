//polyfill 浏览器兼容性
import '@babel/polyfill'
import Vue from 'vue'

import '@/styles/index.scss' // global css

import App from './App'
import router from './router/routers'

import './router/permission' // permission control

import store from './store'

// 核心插件
import Core from '@/plugin/core'
Vue.use(Core);

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
