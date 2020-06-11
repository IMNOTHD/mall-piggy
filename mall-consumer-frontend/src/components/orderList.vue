<template>
    <div>
        <van-nav-bar
                title="订单列表"
                left-text="返回"
                left-arrow
                @click-left="$router.go(-1)"
        />

        <van-cell-group>
            <van-cell icon="shop-o" v-for="(item, index) in orderList" v-bind:key="index" is-link>
                <template #title>
                    <span>{{orderNameList[index]}}</span>
                    <van-tag type="danger" style="margin-left: 10px">{{getStatusName(orderList[index].status)}}</van-tag>
                </template>
                <template #label>
                    <div style="color: red;">
                        <span class="van-card__price-currency">¥</span><span
                            class="van-card__price-integer">{{ orderList[index].price / 100 }}</span>.<span
                            class="van-card__price-decimal">{{ (orderList[index].price % 100).toString().padStart(2, '0') }}</span>
                    </div>
                    <div>
                        收货地址：{{orderList[index].receiverAddress}}
                    </div>
                </template>
            </van-cell>
        </van-cell-group>
    </div>
</template>

<script>
    import {Event} from "@/components/event";
    import api from "@/api/api";

    export default {
        name: "orderList",
        data() {
            return {
                orderList: [],
                orderDetail: [],
                orderNameList: [],
            }
        },
        mounted() {
            document.title = "订单列表";

            Event.$emit('setTabbar');

            this.getOrderList();
        },
        methods: {
            async getOrderList() {
                let result = await api.orderList();
                if (result.data.code === 200) {
                    this.orderList = result.data.data;

                    for (let i = 0; i < this.orderList.length; i++) {
                        await this.getNameByOrderSn(this.orderList[i].orderSn);
                    }
                } else {
                    this.$toast.fail(result.data.message);
                }
            },
            async getNameByOrderSn(value) {
                let result = await api.orderDetail({
                    orderSn: value
                });
                if (result.data.code === 200) {
                    let snapshotId = result.data.data[0].snapshotId;

                    await this.pushOrderName(snapshotId);
                } else {
                    this.$toast.fail(result.data.message);
                }
            },
            async pushOrderName(value) {
                let result = await api.getSnapshot({
                    id: value
                });
                if (result.data.code === 200) {
                    this.orderNameList.push(result.data.data.name);
                } else {
                    this.$toast.fail(result.data.message);
                }
            },
            getStatusName(value) {
                /**
                 * 0->已提交, 未付款; 1->已付款, 未发货; 2->已发货, 未收货; 3->已收货, 未评价; 4->已评价
                 */
                switch (value) {
                    case 0: return "待付款";
                    case 1: return "待发货";
                    case 2: return "待收货";
                    case 3: return "待评价";
                    case 4: return "已完成";
                }
            }
        }
    }
</script>

<style scoped>

</style>