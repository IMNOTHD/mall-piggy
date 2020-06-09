<template>
    <div style="height: 100%">
        <van-checkbox-group v-model="checkedList" @change="onCheckBoxChange" ref="checkboxGroup">
            <van-cell-group>
                <van-cell v-for="(item, index) in productParam" clickable :key="item" @click="toggle(index)"
                          style="padding: 0">
                    <template #title>
                        <div style="color: #333333; border-radius: 10px; margin: 20px 20px 20px 10px; background-color: #ffffff; height: auto; padding: 15px;">
                            <div style="word-break: break-all; width: 100%"
                                 @click="$router.push(`/productDetail?item=${item.productSn}`)">

                                <div class="van-card__header" style="width: 100%">
                                    <a class="van-card__thumb">
                                        <div class="van-image" style="width: 100%; height: 100%;">
                                            <img :src="item.pic" class="van-image__img" style="object-fit: cover;">
                                        </div>
                                    </a>
                                    <div class="van-card__content" style="width: 100%">
                                        <div style="width: 100%">
                                            <div style="float: left">
                                                <b>{{ item.name }}</b>
                                            </div>
                                            <div style="float: right" @click.stop="">
                                                <van-button type="danger" size="mini" @click="onDeleteCartItem(item)">
                                                    删除
                                                </van-button>
                                            </div>
                                        </div>
                                        <div class="van-card__bottom" style="width: 100%">
                                            <div class="van-card__price" style="color: red; float: left;">
                                                <div>
                                                    <span class="van-card__price-currency">¥</span><span
                                                        class="van-card__price-integer">{{ item.price / 100 }}</span>.<span
                                                        class="van-card__price-decimal">{{ (item.price % 100).toString().padStart(2, '0') }}</span>
                                                </div>
                                            </div>
                                            <div @click.stop="">
                                                <van-stepper v-model="item.selectedNum" style="float: right"
                                                             @change="onChangeNum"
                                                             :name="item.productSn"/>
                                            </div>
                                        </div>
                                    </div>
                                </div>

                            </div>
                        </div>
                    </template>
                    <template #icon>
                        <van-checkbox :name="item" ref="checkboxes" style="margin-left: 10px"/>
                    </template>
                </van-cell>
            </van-cell-group>
        </van-checkbox-group>

        <div style="position: absolute; bottom: 0; width: 100%; height: 100px;">
            <div style="height: 50px; width: 100%; background-color: rgb(247, 248, 250); align-items: center; display: flex">
                    <div style="text-align: left; padding-left: 16px;">
                        <van-checkbox v-model="allChecked" @change="onAllCheckChange">全选</van-checkbox>
                    </div>
                    <div style="flex: 1; text-align: right; padding-right: 16px;">
                        <van-button round type="danger" class="van-submit-bar__button van-submit-bar__button--danger"
                                    style="font-size: 16px">结 算
                        </van-button>
                    </div>
            </div>
        </div>
    </div>
</template>

<script>

    import {Event} from "@/components/event";
    import api from "@/api/api";

    export default {
        name: "cart",
        data() {
            return {
                allChecked: false,
                cartList: [],
                productParam: [],
                checkedList: [],
            }
        },
        mounted() {
            document.title = "购物车";
            Event.$emit('setTabbar');

            this.getCartList();
        },
        methods: {
            onCheckBoxChange() {
                if (this.checkedList.length === this.productParam.length) {
                    this.allChecked = true;
                } else {
                    this.allChecked = false;
                }
            },
            onAllCheckChange() {
                if (this.allChecked === false) {
                    this.$refs.checkboxGroup.toggleAll(true);
                } else {
                    this.$refs.checkboxGroup.toggleAll(false);
                }
            },
            async onDeleteCartItem(item) {
                let result = await api.deleteCart({
                    productSn: item.productSn,
                });
                if (result.data.code === 200) {
                    this.productParam = [];
                    await this.getCartList();
                    this.$toast.success(result.data.message);
                } else {
                    this.$toast.fail(result.data.message);
                }
            },
            async onChangeNum(value, detail) {
                let result = await api.setCartNum({
                    productSn: detail.name,
                    selectedNum: value,
                });
                if (result.data.code === 200) {
                    this.productParam = [];
                    await this.getCartList();
                } else {
                    this.$toast.fail(result.data.message);
                }
            },
            toggle(index) {
                this.$refs.checkboxes[index].toggle();
            },
            async getCartList() {
                let result = await api.getCartList();
                if (result.data.code === 200) {
                    this.cartList = result.data.data;
                } else {
                    this.$toast.fail(result.data.message);
                }

                await this.setProductParamList();
            },
            getPic(str) {
                return str.slice(2, -2).split('","');
            },
            async setProductParamList() {
                for (const value of this.cartList) {
                    let result = await api.getProduct({
                        productSn: value.productSn
                    });
                    if (result.data.code === 200) {
                        this.productParam.push(result.data.data);
                        this.productParam[this.productParam.length - 1].pic = this.getPic(this.productParam[this.productParam.length - 1].pic)[0];
                        this.productParam[this.productParam.length - 1].selectedNum = value.selectedNum;
                    }
                }
            }
        }
    }
</script>

<style scoped>
    >>> .van-cell {
        background-color: transparent;
    }

    >>> .van-cell-group {
        background-color: transparent;
    }
</style>