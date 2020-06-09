<template>
    <div>
        <!--会员card-->
        <div style="color: #333333; padding: 20px; border-radius: 10px; margin: 20px; background-color: #ffffff; height: auto;">
            <div style="padding-top: 5px; padding-bottom: 15px; display: inline-block; padding-left: 20px">
                <span style="font-size: 20px; font-weight: 700; font-family: 'Noto Serif SC', serif;">{{ memberData.nickname === undefined ? memberData.username : memberData.nickname }}</span>
            </div>
        </div>

        <!--地址管理card-->
        <van-cell-group style="margin: 20px; border-radius: 10px;">
            <van-cell
                    is-link
                    @click="$router.push('/addressList')"
                    size="large"
                    style="border-radius: 10px">
                <template #title>
                    <span>地址管理</span>
                </template>
                <template #icon>
                    <van-icon name="location-o" style="line-height: inherit; margin-right: 8px;" color="yellowgreen"/>
                </template>
            </van-cell>
        </van-cell-group>

        <!--登出card-->
        <van-cell-group style="margin: 20px; border-radius: 10px;">
            <van-cell
                    is-link
                    @click="log"
                    size="large"
                    style="border-radius: 10px">
                <template #title>
                    <span>{{ logData.label }}</span>
                </template>
                <template #icon>
                    <van-icon :name="logData.icon" style="line-height: inherit; margin-right: 8px;" :color="logData.iconColor"/>
                </template>
            </van-cell>
        </van-cell-group>
    </div>
</template>

<script>
    import api from "@/api/api";
    import { Event } from "@/components/event";

    export default {
        name: "me",
        data() {
            return {
                memberData: {},
                logData: {}
            }
        },
        mounted() {
            document.title = "我的";

            Event.$emit('setTabbar');

            this.getMemberInfo();
        },
        methods: {
            async log() {
                if (this.logData.label === "登出") {
                    await this.logout();
                } else {
                    // push到登录页面
                    this.$router.push("/login");
                }
            },
            async logout() {
                let result = await api.logout();
                if (result.data.code === 200) {
                    this.memberData = result.data.data;
                    this.$toast.success('登出成功');
                } else {
                    this.$toast.fail(result.data.message);
                }
                this.$cookies.remove("member_token");

                await this.getMemberInfo();
            },
            async getMemberInfo() {
                let result = await api.getMemberData();
                if (result.data.code === 200) {
                    this.memberData = result.data.data;
                    this.logData = {
                        label: "登出",
                        icon: "arrow-down",
                        iconColor : "red"
                    }
                } else {
                    this.$cookies.remove("member_token");
                    this.memberData = {
                        username: "未登录"
                    }
                    this.logData = {
                        label: "登入",
                        icon: "arrow-up",
                        iconColor : "blue"
                    }
                }

            },
        }
    }
</script>

<style scoped>

</style>