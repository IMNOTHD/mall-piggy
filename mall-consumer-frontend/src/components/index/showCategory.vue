<template>
    <div>
        <van-nav-bar
                :title="categoryName"
                left-text="返回"
                left-arrow
                @click-left="$router.push('/')"/>

        <div v-for="(item, index) in categoryProductList" v-bind:key="index">
            <van-card
                    :price="(item['price'] / 100).toFixed(2)"
                    :desc="item['description']"
                    :thumb="getPic(item['pic'])[0]"
                    style="margin: 20px; border-radius: 10px; padding: 10px"
                    @click="$router.push(`/productDetail?item=${item['productSn']}`)">
                <template #title>
                    <b>{{item['name']}}</b>
                </template>
            </van-card>
        </div>
    </div>
</template>

<script>
    import {Event} from "@/components/event";
    import api from "@/api/api";

    export default {
        name: "showCategory",
        data() {
            return {
                categoryName: '',
                categoryProductList: [],
            }
        },
        mounted() {
            Event.$emit('setTabbar');
            document.title = '';

            this.getCategoryName();
            this.getCategoryProduct();
        },
        methods: {
            getPic(str) {
                return str.slice(2, -2).split('","');
            },
            async getCategoryName() {
                let result = await api.getCategoryName({
                    id: this.$route.query["id"]
                });
                if (result.data.code === 200) {
                    this.categoryName = result.data.data;
                    document.title = this.categoryName;
                } else {
                    this.$toast.fail(result.data.message);
                }
            },
            async getCategoryProduct() {
                let result = await api.getCategoryProduct({
                    id: this.$route.query["id"]
                });
                if (result.data.code === 200) {
                    this.categoryProductList = result.data.data;
                } else {
                    this.$toast.fail(result.data.message);
                }
            },
        }
    }
</script>

<style scoped>

</style>