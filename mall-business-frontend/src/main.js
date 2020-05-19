import Vue from 'vue'
import App from './App.vue'
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';

Vue.config.productionTip = false

Vue.use(ElementUI)
Vue.use(VueRouter);

const router = new VueRouter({
  mode: "history",
  routes: routers
});

new Vue({
  el: "#app",
  router,
  render: h => h(App)
})
