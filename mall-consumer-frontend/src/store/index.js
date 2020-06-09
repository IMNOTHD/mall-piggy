import Vue from 'vue'
import vuex from 'vuex'
Vue.use(vuex);

export default new vuex.Store({
    state: {
        orderParam: [],
        orderPrice: 0,
    },
    mutations: {
        setOrderParam(state, value) {
            state.orderParam = value;

            let price = 0;
            state.orderParam.forEach((e) => {
                price += e.price * e.selectedNum
            });
            state.orderPrice = price;
        },
        changeProductSelectNum(state, productSn, num) {
            state.orderParam.forEach(value => {
                if (value === productSn) {
                    value.selectedNum = num;
                }
            });

            let price = 0;
            state.orderParam.forEach((e) => {
                price += e.price * e.selectedNum
            });
            state.orderPrice = price;
        },
    },
    actions: {

    }
})