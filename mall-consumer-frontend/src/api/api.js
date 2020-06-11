import axios from "axios"
import qs from "qs"

axios.defaults.withCredentials = true;

const baseURL = 'http://192.168.43.166:8080';

export default {
    // eslint-disable-next-line no-unused-vars
    async getProductCategory(param = {}) {
        let result = await axios.get(`${baseURL}/product/category`);
        return result;
    },

    // eslint-disable-next-line no-unused-vars
    async getMemberData(param = {}) {
        let result = await axios.get(`${baseURL}/member/info`);
        return result;
    },

    async login(param = {}) {
        let result = await axios.post(`${baseURL}/member/login`, qs.stringify(param));
        return result;
    },

    async register(param = {}) {
        let result = await axios.post(`${baseURL}/member/register`, qs.stringify(param));
        return result;
    },

    // eslint-disable-next-line no-unused-vars
    async logout(param = {}) {
        let result = await axios.post(`${baseURL}/member/logout`);
        return result;
    },

    // eslint-disable-next-line no-unused-vars
    async getAddressList(param = {}) {
        let result = await axios.get(`${baseURL}/member/address/list`);
        return result;
    },

    async getAddressById(param = {}) {
        let result = await axios.get(`${baseURL}/member/address/query?${qs.stringify(param)}`);
        return result;
    },

    async updateAddress(param = {}) {
        let result = await axios.post(`${baseURL}/member/address/update`, param);
        return result;
    },

    async createAddress(param = {}) {
        let result = await axios.post(`${baseURL}/member/address/create`, param);
        return result;
    },

    async deleteAddress(param = {}) {
        let result = await axios.post(`${baseURL}/member/address/delete?${qs.stringify(param)}`);
        return result;
    },

    async getCategoryName(param = {}) {
        let result = await axios.get(`${baseURL}/product/category/queryName?${qs.stringify(param)}`);
        return result;
    },

    async getCategoryProduct(param = {}) {
        let result = await axios.get(`${baseURL}/product/category/query?${qs.stringify(param)}`);
        return result;
    },

    async getProduct(param = {}) {
        let result = await axios.get(`${baseURL}/product/query?${qs.stringify(param)}`);
        return result;
    },

    // eslint-disable-next-line no-unused-vars
    async getCartList(param = {}) {
        let result = await axios.get(`${baseURL}/cart/list`);
        return result;
    },

    async addCart(param = {}) {
        let result = await axios.post(`${baseURL}/cart/add`, param);
        return result;
    },

    async setCartNum(param = {}) {
        let result = await axios.post(`${baseURL}/cart/setNum`, param);
        return result;
    },

    async deleteCart(param = {}) {
        let result = await axios.post(`${baseURL}/cart/delete`, param);
        return result;
    },

    async addOrder(param = {}) {
        let result = await axios.post(`${baseURL}/order/add`, param);
        return result;
    },

    // eslint-disable-next-line no-unused-vars
    async orderList(param = {}) {
        let result = await axios.get(`${baseURL}/member/order/list`);
        return result;
    },

    async orderDetail(param = {}) {
        let result = await axios.get(`${baseURL}/order/detail?${qs.stringify(param)}`);
        return result;
    },

    async getSnapshot(param = {}) {
        let result = await axios.get(`${baseURL}/order/snapshot?${qs.stringify(param)}`);
        return result;
    },
}