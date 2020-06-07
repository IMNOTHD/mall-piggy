import axios from "axios"
import qs from "qs"

axios.defaults.withCredentials = true;

const baseURL = 'http://192.168.2.157:8080';

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
}