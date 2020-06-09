<template>
    <div>
        <van-nav-bar
                title="确认订单"
                left-text="返回"
                left-arrow
                @click-left="$router.go(-1)"/>

        <!--地址选单弹窗-->
        <van-popup v-model="showAddressSelect" round closeable position="bottom"
                   :style="{ height: '80%', paddingTop: '20px' }">
            <van-address-list
                    v-model="chosenAddressId"
                    :list="addressList"
                    default-tag-text="默认"
                    @select="onSelect"
                    @add="onAdd"
                    @edit="onEdit"
            />
        </van-popup>

        <!--地址-->
        <van-cell is-link
                  class="address-card"
                  center
                  @click="showAddressSelect = true">
            <template #icon>
                <template v-if="Object.keys(selectedAddress).length === 0">
                    <van-icon name="add-square" size="30px" color="#1989fa" style="margin-right: 5px"/>
                </template>
            </template>
            <template #title>
                <template v-if="Object.keys(selectedAddress).length === 0">
                    <div style="font-size: 20px">
                        选择地址
                    </div>
                </template>
                <template v-else>
                    <div>
                        <van-tag type="danger" v-show="selectedAddress.isDefault">默认</van-tag>
                        {{ selectedAddress.addressSimple }}
                    </div>
                    <div style="font-size: 18px; margin-top: 3px; margin-bottom: 3px;">
                        {{ selectedAddress.addressDetail }}
                    </div>
                    <div>
                        {{ selectedAddress.name }} {{ selectedAddress.tel }}
                    </div>
                </template>
            </template>
        </van-cell>

        <!--商品列表-->
        <div style="color: #333333; border-radius: 10px; margin: 20px; background-color: #ffffff; height: auto; padding: 15px;"
             v-for="(item, index) in productParam" v-bind:key="index">
            <div style="word-break: break-all; width: 100%">

                <div class="van-card__header" style="width: 100%">
                    <a class="van-card__thumb">
                        <div class="van-image" style="width: 100%; height: 100%;">
                            <img :src="item.pic" class="van-image__img" style="object-fit: cover;">
                        </div>
                    </a>
                    <div class="van-card__content" style="width: 100%">
                        <div>
                            <b>{{ item.name }}</b>
                        </div>
                        <div class="van-card__bottom" style="width: 100%">
                            <div class="van-card__price" style="color: red; float: left;">
                                <div>
                                    <span class="van-card__price-currency">¥</span><span
                                        class="van-card__price-integer">{{ item.price / 100 }}</span>.<span
                                        class="van-card__price-decimal">{{ (item.price % 100).toString().padStart(2, '0') }}</span>
                                </div>
                            </div>
                            <van-stepper v-model="item.selectedNum" style="float: right" @change="onChangeNum" :name="item.productSn"/>
                        </div>
                    </div>
                </div>

            </div>
        </div>

        <van-submit-bar :price="$store.state.orderPrice" button-text="提交订单" @submit="onSubmit"/>
    </div>
</template>

<script>
    import {Event} from "@/components/event";
    import api from "@/api/api";
    import area from "@/api/area";

    export default {
        name: "confirmOrder",
        data() {
            return {
                chosenAddressId: '',
                showAddressSelect: false,
                addressList: [],
                selectedAddress: {},
                productParam: this.$store.state.orderParam,
            }
        },
        mounted() {
            Event.$emit('setTabbar');
            document.title = "确认订单";

            this.getAddressList();
        },
        methods: {
            onChangeNum(value, detail) {
                this.$store.commit('changeProductSelectNum', value, detail);
            },
            onSubmit() {
                this.$toast('submit');
            },
            onAdd() {
                this.$router.push(`/addressEdit?type=create`);
            },
            onEdit(item) {
                this.$router.push(`/addressEdit?type=edit&id=${item.id}`);
            },
            onSelect(value) {
                this.addressList.forEach((e) => {
                    if (e.id === value.id) {
                        this.selectedAddress = {
                            id: e.id,
                            name: e.name,
                            tel: e.tel,
                            addressSimple: e.addressSimple,
                            addressDetail: e.addressDetail,
                            isDefault: e.isDefault,
                        };
                        return;
                    }
                })
            },
            async getAddressList() {
                let result = await api.getAddressList();
                if (result.data.code === 200) {
                    this.addressList = [];
                    result.data.data.forEach((value) => {
                        this.addressList.push({
                            id: value.id,
                            name: value.name,
                            tel: value.phone,
                            addressSimple: `${area.province_list[value.province]}${area.city_list[value.city]}${area.county_list[value.region]}`,
                            addressDetail: `${value.detailAddress}`,
                            address: `${area.province_list[value.province]}${area.city_list[value.city]}${area.county_list[value.region]} ${value.detailAddress}`,
                            isDefault: value.defaultStatus === 1,
                        })
                        if (value.defaultStatus === 1) {
                            this.selectedAddress = {
                                id: value.id,
                                name: value.name,
                                tel: value.phone,
                                addressSimple: `${area.province_list[value.province]}${area.city_list[value.city]}${area.county_list[value.region]}`,
                                addressDetail: `${value.detailAddress}`,
                                address: `${area.province_list[value.province]}${area.city_list[value.city]}${area.county_list[value.region]} ${value.detailAddress}`,
                                isDefault: value.defaultStatus === 1,
                            };
                            this.chosenAddressId = value.id;
                        }
                    })
                } else {
                    this.$dialog.alert({
                        message: result.data.message,
                    })
                }
            }
        }
    }
</script>

<style scoped>
    .title {
        margin-left: 5px;
    }

    .address-card::before {
        position: absolute;
        right: 0;
        bottom: 0;
        left: 0;
        height: 2px;
        background: -webkit-repeating-linear-gradient(135deg, #ff6c6c 0, #ff6c6c 20%, transparent 0, transparent 25%, #1989fa 0, #1989fa 45%, transparent 0, transparent 50%);
        background: repeating-linear-gradient(-45deg, #ff6c6c 0, #ff6c6c 20%, transparent 0, transparent 25%, #1989fa 0, #1989fa 45%, transparent 0, transparent 50%);
        background-size: 80px;
        content: '';
    }
</style>