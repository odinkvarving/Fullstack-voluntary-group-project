import Vue from 'vue'
import App from './App.vue'
import VueRouter from 'vue-router'
import Routes from "./routes/routes.js"
import store from './store/index'

Vue.config.productionTip = false
Vue.use(VueRouter);

const router = new VueRouter({
  routes: Routes,
  mode: 'history'
})

new Vue({
  render: h => h(App),
  store: store,
  router: router,
}).$mount('#app')
