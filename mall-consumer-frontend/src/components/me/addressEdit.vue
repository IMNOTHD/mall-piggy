<template>
    <div>
        <van-nav-bar
                title="编辑地址"
                left-text="返回"
                left-arrow
                @click-left="$router.go(-1)"/>

        <van-address-edit
                :area-list="areaList"
                show-postal
                show-set-default
                :area-columns-placeholder="['请选择', '请选择', '请选择']"
                :address-info="defaultAddress"
                @save="onSave"
                @delete="onDelete"
                :show-delete="showDelete"
        />
    </div>
</template>

<script>
    import area from "@/api/area";
    import api from "@/api/api";
    import {Event} from "@/components/event";

    export default {
        name: "addressEdit",
        data() {
            return {
                areaList: area,
                defaultAddress: {},
                showDelete: false,
            };
        },
        mounted() {
            Event.$emit('setTabbar');

            if (this.$route.query["type"] === "edit") {
                this.getAddressById(this.$route.query["id"]);
                this.showDelete = true;
            }
        },
        methods: {
            async onSave(value) {
                if (this.$route.query["type"] === "edit") {
                    let result = await api.updateAddress({
                        id: value.id,
                        detailAddress: value.addressDetail,
                        province: `${value.areaCode.slice(0, 2)}0000`,
                        city: `${value.areaCode.slice(0, 4)}00`,
                        region: value.areaCode,
                        defaultStatus: value.isDefault ? 1 : 0,
                        name: value.name,
                        postCode: value.postalCode,
                        phone: value.tel
                    });
                    if (result.data.code === 200) {
                        this.$toast.success(result.data.message);
                        this.$router.go(-1);
                    } else {
                        this.$toast.fail(result.data.message);
                    }
                } else {
                    let result = await api.createAddress({
                        detailAddress: value.addressDetail,
                        province: `${value.areaCode.slice(0, 2)}0000`,
                        city: `${value.areaCode.slice(0, 4)}00`,
                        region: value.areaCode,
                        defaultStatus: value.isDefault ? 1 : 0,
                        name: value.name,
                        postCode: value.postalCode,
                        phone: value.tel
                    });
                    if (result.data.code === 200) {
                        this.$toast.success(result.data.message);
                        this.$router.go(-1);
                    } else {
                        this.$toast.fail(result.data.message);
                    }
                }
            },
            async onDelete() {
                this.$toast('delete');
                let result = await api.deleteAddress({
                    id: this.defaultAddress.id
                });
                if (result.data.code === 200) {
                    this.$toast.success(result.data.message);
                    this.$router.go(-1);
                } else {
                    this.$toast.fail(result.data.message);
                }
            },
            async getAddressById(id) {
                let result = await api.getAddressById({
                    id: id
                });
                if (result.data.code === 200) {
                    this.defaultAddress = {
                        id: result.data.data.id,
                        name: result.data.data.name,
                        tel: result.data.data.phone,
                        province: result.data.data.province,
                        city: result.data.data.city,
                        county: result.data.data.region,
                        addressDetail: result.data.data.detailAddress,
                        areaCode: result.data.data.region,
                        postalCode: result.data.data.postCode,
                        isDefault: result.data.data.defaultStatus === 1,
                    }
                } else {
                    this.$notify(result.data.message);
                }
            },
        },
    }
</script>

<style scoped>

</style>