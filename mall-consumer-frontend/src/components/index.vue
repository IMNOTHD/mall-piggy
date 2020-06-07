<template>
    <div style="overflow:hidden; padding: 0 0 0 85px;">
        <van-sidebar v-model="activeKey" :style="{height: `${clientHeight - 50}px`}"
                     style="display:inline; float:left; margin-left:-85px; background-color: #f7f8fa">
            <van-sidebar-item v-for="(item, index) in categoryList" :key="index" :title="item.label"/>
        </van-sidebar>
        <div style="float:right; width:100%;" :style="{height: `${clientHeight - 50}px`}" >
            <van-grid :gutter="25" :column-num="3" square border clickable style="padding-top: 25px;">
                <van-grid-item v-for="(item, index) in categoryList[activeKey]['children']" :key="index" icon="photo-o" :text="item.label" />
            </van-grid>
        </div>
    </div>
</template>

<script>
    import api from "@/api/api";
    import { Event } from "@/components/event";

    export default {
        name: "index",
        data() {
            return {
                activeKey: 0,
                clientHeight: '',
                categoryList: [],
            }
        },
        mounted() {
            document.title = "首页";
            // 获取浏览器可视区域高度
            this.clientHeight = `${document.documentElement.clientHeight}`;
            window.onresize = function temp() {
                this.clientHeight = `${document.documentElement.clientHeight}`;
            };

            Event.$emit('setTabbar');


            api.getProductCategory().then((res) => {
                this.categoryList = res.data.data;
            })
        },
        methods: {
        }
    }
</script>

<style scoped>
    >>> .van-grid-item__content {
        border-radius: 20px
    }
</style>