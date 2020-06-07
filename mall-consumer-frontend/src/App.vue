<template>
    <div id="app" ref="app">
        <div class="page-wrap" :style="{ height: `${clientHeight - (showTabbar ? 50 : 0)}px` }">
            <router-view></router-view>
        </div>

        <van-tabbar v-model="active" v-show="showTabbar">
            <van-tabbar-item name="index" icon="home-o" to="/">首页</van-tabbar-item>
            <van-tabbar-item name="cart" icon="cart-o" to="/cart">购物车</van-tabbar-item>
            <van-tabbar-item name="me" icon="contact" to="/me">我的</van-tabbar-item>
        </van-tabbar>
    </div>
</template>

<script>
    import { Event } from "@/components/event";

    export default {
        name: 'App',
        data() {
            return {
                active: 'index',
                clientHeight: '',
                showTabbar: true,
            };
        },
        mounted() {
            this.clientHeight = document.documentElement.clientHeight;
            window.onresize = () => {
                this.clientHeight = document.documentElement.clientHeight;
            };

            Event.$on('setTabbar', () => {
                let path = this.$route.path;

                switch (path) {
                    case '/':
                        this.active = "index";
                        this.showTabbar = true;
                        break;
                    case '/cart':
                        this.active = "cart";
                        this.showTabbar = true;
                        break;
                    case '/me':
                        this.active = "me";
                        this.showTabbar = true;
                        break;
                    default:
                        this.showTabbar = false;
                        break;
                }
            });


            Event.$emit('setTabbar');
        },
        methods: {

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
