import Vue from 'vue'
import App from './App.vue'
import VueRouter from "vue-router";
import routers from "@/router";
import Vant from "vant";
import "vant/lib/index.css";
import VueCookies from 'vue-cookies';
import { Lazyload } from 'vant';
import Vuex from 'vuex';
import store from "@/store";

Vue.config.productionTip = false

Vue.use(Vant);
Vue.use(Vuex);
Vue.use(VueRouter);
Vue.use(VueCookies);
Vue.use(Lazyload);

const router = new VueRouter({
  mode: "history",
  routes: routers
});

new Vue({
  el: "#app",
  router,
  store,
  render: h => h(App)
});