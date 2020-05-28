<template>
  <div id="app" ref="app">
    <div class="page-wrap" :style="{ height: `${() => {showTabbar ? clientHeight - 50 : clientHeight}}px` }">
      <router-view></router-view>
    </div>

    <van-tabbar v-model="active" :fixed="true" v-show="showTabbar">
      <van-tabbar-item name="index" icon="home-o" to="/">首页</van-tabbar-item>
      <van-tabbar-item name="cart" icon="cart-o" to="/cart">购物车</van-tabbar-item>
      <van-tabbar-item name="me" icon="contact" to="/me">我的</van-tabbar-item>
    </van-tabbar>
  </div>
</template>

<script>
  import routers from "@/router";
  export default {
    name: 'App',
    data() {
      return {
        active: 'index',
        clientHeight: '',
        showTabbar: true
      };
    },
    mounted() {
      this.clientHeight = document.documentElement.clientHeight;
      window.onresize = () => {
        this.clientHeight = document.documentElement.clientHeight;
      };
      // 加载时根据url重定向
      let path = this.$route.path;
      for (let route of routers) {
        if (path === route['path']) {
          this.active = route['name'];
          break;
        }
      }
    },
    methods: {
      makeTabbarShow() {
        this.showTabbar = true;
      },
      makeTabbarNotShow() {
        this.showTabbar = false;
      }
    }
  }
</script>

<style>
  html, body {
    width: 100%;
    height: 100%;
    margin: 0;
  }
  .page-wrap {
    background-color: #f3f3f3;
    overflow: auto;
  }
</style>