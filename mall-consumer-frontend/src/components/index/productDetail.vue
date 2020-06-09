<template>
    <div>
        <van-nav-bar
                title="商品详情"
                left-text="返回"
                left-arrow
                @click-left="$router.go(-1)"/>

        <van-swipe :autoplay="3000" height="300">
            <van-swipe-item v-for="(image, index) in picList" :key="index" style="background-color: #f8f8f8">
                <img v-lazy="image" class="swipe-image"/>
            </van-swipe-item>
        </van-swipe>

        <van-cell size="large" center style="vertical-align:middle;">
            <template #title>
                <van-tag mark type="danger" style="margin-right: 10px; float: left;">热销</van-tag>
                <span style="font-size: 22px; float: left;"><b>{{ product.name }}</b></span>
            </template>
        </van-cell>

        <van-cell-group style="margin-top: 10px">
            <van-cell title="销量" color="red" :value="product.sale" size="large">
                <template #icon>
                    <van-icon name="fire-o" style="line-height: inherit; margin-right: 5px;" color="red"/>
                </template>
            </van-cell>
        </van-cell-group>

        <van-cell-group style="margin-top: 10px">
            <van-cell title="评价" color="red" size="large">
                <template #icon>
                    <van-icon name="comment-o" style="line-height: inherit; margin-right: 5px;" color="blue"/>
                </template>
            </van-cell>
            <van-cell>
                暂时还没有评价哦
            </van-cell>
        </van-cell-group>

        <van-cell-group style="margin-top: 10px">
            <van-cell title="产品介绍" color="red" size="large">
                <template #icon>
                    <van-icon name="goods-collect-o" style="line-height: inherit; margin-right: 5px;"
                              color="yellowgreen"/>
                </template>
            </van-cell>
            <van-cell>
                {{ product.description }}
            </van-cell>
        </van-cell-group>

        <van-sku
                v-model="show"
                :sku="sku"
                :goods="goods"
                :hide-stock="sku.hide_stock"
                @buy-clicked="onBuyClicked"
                :show-add-cart-btn="false"
                :close-on-click-overlay="true"/>

        <van-goods-action>
            <van-goods-action-icon icon="shop-o" text="店铺"/>
            <van-goods-action-icon icon="cart-o" text="购物车" @click="$router.push('/cart')"/>
            <van-goods-action-button type="warning" text="加入购物车" @click="addCart"/>
            <van-goods-action-button type="danger" text="立即购买" @click="show=true"/>
        </van-goods-action>
    </div>
</template>

<script>
    import {Event} from "@/components/event";
    import api from "@/api/api";
    import Vue from 'vue';
    import {Lazyload} from 'vant';

    Vue.use(Lazyload);
    export default {
        name: "productDetail",
        data() {
            return {
                product: {},
                picList: [],
                show: false,
                sku: {
                    tree: [],
                    list: [],
                    price: '1.00',
                    stock_num: 227,
                    collection_id: 2261,
                    none_sku: true,
                    messages: [],
                    hide_stock: false
                },
                goods: {}
            }
        },
        mounted() {
            Event.$emit('setTabbar');
            document.title = "商品详情";

            this.getProduct();
        },
        methods: {
            async addCart() {
                let result = await api.addCart({
                    productSn: this.$route.query['item']
                });
                if (result.data.code === 200) {
                    this.$toast.success(result.data.message);
                } else {
                    this.$toast.fail(result.data.message);
                }
            },
            getPic(str) {
                return str.slice(2, -2).split('","');
            },
            onBuyClicked: function (value) {
                this.$store.commit('setOrderParam', [{
                    selectedNum: value.selectedNum,
                    productSn: this.$route.query['item'],
                    pic: this.picList[0],
                    price: this.product.price,
                    name: this.product.name,
                }]);
                this.$router.push('/confirmOrder')
            },
            async getProduct() {
                let result = await api.getProduct({
                    productSn: this.$route.query['item']
                });
                if (result.data.code === 200) {
                    this.product = result.data.data;
                    this.picList = this.getPic(this.product.pic);
                    this.goods.picture = this.picList[0];
                    this.sku.price = (this.product.price / 100).toFixed(2);
                    this.sku.stock_num = this.product.stock;
                } else {
                    this.$toast.fail(result.data.message);
                }
            }
        }
    }
</script>

<style scoped>
    .swipe-image {
        position: relative;
        max-height: 100%;
        max-width: 100%;
        width: auto;
        height: auto;
        top: 50%;
        left: 50%;
        -webkit-transform: translateX(-50%) translateY(-50%);
        transform: translateX(-50%) translateY(-50%);
    }
</style>