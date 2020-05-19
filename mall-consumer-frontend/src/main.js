import Vue from 'vue'
import App from './App.vue'
import VueRouter from "vue-router";
import routers from "@/router";
import Vant from "vant";
import "vant/lib/index.css";

Vue.config.productionTip = false

Vue.use(Vant)
Vue.use(VueRouter)

const router = new VueRouter({
  mode: "history",
  routes: routers
})

new Vue({
  el: "#app",
  router,
  render: h => h(App)
})
