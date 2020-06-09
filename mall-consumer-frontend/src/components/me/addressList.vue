<template>
    <div>
        <van-nav-bar
                title="收货地址"
                left-text="返回"
                left-arrow
                @click-left="$router.go(-1)"
        />

        <van-address-list
                v-model="chosenAddressId"
                :list="addressList"
                default-tag-text="默认"
                @add="onAdd"
                @edit="onEdit"
                :switchable="false"
        />
    </div>
</template>

<script>
    import api from "@/api/api";
    import area from "@/api/area";
    import {Event} from "@/components/event";

    export default {
        name: "addressList",
        data() {
            return {
                chosenAddressId: '1',
                addressList: [],
            };
        },
        mounted() {
            Event.$emit('setTabbar');

            this.getAddressList();
        },
        methods: {
            onAdd() {
                this.$router.push(`/addressEdit?type=create`);
            },
            onEdit(item) {
                this.$router.push(`/addressEdit?type=edit&id=${item.id}`);
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
                            address: `${area.province_list[value.province]}${area.city_list[value.city]}${area.county_list[value.region]} ${value.detailAddress}`,
                            isDefault: value.defaultStatus === 1,
                        })
                    })
                }
            }
        },
    }
</script>

<style scoped>

</style>