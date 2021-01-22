// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import consolePanel from "./page/consolePanel";
import router from './router'
import ElementUI from 'element-ui'
import echarts from 'echarts'
import axios from 'axios'


import store from "./store";
import BootstrapVue from "bootstrap-vue";
import Vuex from 'vuex'

import 'element-ui/lib/theme-chalk/index.css'


Vue.use(ElementUI);
Vue.use(BootstrapVue)
Vue.prototype.$echarts = echarts
Vue.prototype.$axios = axios
//'/api'
axios.defaults.baseURL = "http://182.92.65.135:8081/tgam"
Vue.config.productionTip = false



/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  store,
  components: { App },
  template: '<App/>'
})
