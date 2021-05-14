import Vue from 'vue'
import App from './App.vue'
import VueRouter from 'vue-router'
import Router from "./router/index.js"
import store from './store/index'
import vuetify from './plugins/vuetify'

Vue.config.productionTip = false
Vue.use(VueRouter);

const router = new VueRouter({
  routes: Router,
  mode: 'history'
})

new Vue({
  render: h => h(App),
  store: store,
  router: router,
  vuetify,
}).$mount('#app')
