<template>
    <div style="height: 100%">
        <van-tabs v-model="active" animated swipeable style="height: 100%">
            <van-tab title="登录">
                <div>
                    <van-form @submit="onSubmitLogin" >
                        <van-field
                                size="large"
                                v-model="login.account"
                                name="用户名"
                                label="用户名"
                                placeholder="手机号/帐号"
                                :rules="[{ required: true, message: '请填写用户名' }]"/>
                        <van-field
                                v-model="login.password"
                                type="password"
                                name="密码"
                                label="密码"
                                placeholder="密码"
                                :rules="[{ required: true, message: '请填写密码' }]"/>
                        <div style="margin: 16px;">
                            <van-button round block type="info" native-type="submit">
                                提 交
                            </van-button>
                        </div>
                    </van-form>
                </div>
            </van-tab>
            <van-tab title="注册">
                <div>
                    <van-form @submit="onSubmitRegister" >
                        <van-field
                                size="large"
                                v-model="register.username"
                                name="帐号"
                                label="帐号"
                                placeholder="帐号"
                                :rules="[{ required: true, message: '请填写用户名' }]"/>
                        <van-field
                                size="large"
                                type="tel"
                                v-model="register.phone"
                                name="手机号"
                                label="手机号"
                                placeholder="手机号"
                                :rules="[{ required: true, message: '请填写用户名' }]"/>
                        <van-field
                                v-model="register.password"
                                type="password"
                                name="密码"
                                label="密码"
                                placeholder="密码"
                                :rules="[{ required: true, message: '请填写密码' }]"/>
                        <div style="margin: 16px;">
                            <van-button round block type="info" native-type="submit">
                                提 交
                            </van-button>
                        </div>
                    </van-form>
                </div>
            </van-tab>
        </van-tabs>
    </div>
</template>

<script>
    import {Event} from "@/components/event";
    import api from "@/api/api";

    export default {
        name: "login",
        data() {
            return {
                login: {
                    account: '',
                    password: '',
                },
                register: {
                    username: '',
                    phone: '',
                    password: '',
                }
            }
        },
        mounted() {
            Event.$emit('setTabbar');
        },
        methods: {
            async onSubmitLogin() {
                let result = await api.login(this.login);
                if (result.data.code === 200) {
                    this.$router.push("/me");
                } else {
                    this.$dialog.alert({message: result.data.message});
                }
            },
            async onSubmitRegister() {
                let result = await api.register(this.register);
                if (result.data.code === 200) {
                    await api.login({
                        account: this.register.username,
                        password: this.register.password,
                    })
                    this.$router.push("/me");
                } else {
                    this.$dialog.alert({message: result.data.message});
                }
            },
        }
    }
</script>

<style scoped>
    >>> .van-tabs__content {
        height: 100%;
    }

    >>> .van-tab__pane {
        display: flex;
        justify-content: center;
        flex-direction: column;
        height: 80%;
    }

    >>> .van-field {
        margin: 16px;
        border-radius: 999px;
    }

    >>> .van-cell {
        width: auto;
    }
</style>