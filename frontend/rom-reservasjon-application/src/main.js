import Vue from 'vue'
import App from './App.vue'
import VueRouter from 'vue-router'
import Router from "./router/index.js"
import store from './store/index'
import vuetify from './plugins/vuetify'
import * as VueGoogleMaps from "vue2-google-maps"

Vue.config.productionTip = false
Vue.use(VueRouter);
Vue.use(VueGoogleMaps,{
  load: {
    key: 'AIzaSyB8KEwtdxowU0S-VNdmM5fE7lmed8Mlezw',
    libraries:'places',
  }
});

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
