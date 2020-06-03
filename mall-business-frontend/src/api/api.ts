import {stringify} from 'qs';
import axios from 'axios';

axios.defaults.withCredentials = true;

const baseURL = 'http://localhost:8080';

class Api {
    async register(params = {}) {
        try {
            let result : any = await axios.post(`${baseURL}/admin/register`, stringify(params));
            return result;
        } catch (err) {
            throw err;
        }
    }

    async login(params = {}) {
        try {
            let result : any = await axios.post(`${baseURL}/admin/login`, stringify(params));
            return result;
        } catch (err) {
            throw err;
        }
    }

    async info(param = {}) {
        try {
            let result : any = await axios.get(`${baseURL}/admin/info`);
            return result;
        } catch (err) {
            throw err;
        }
    }

    async logout(param = {}) {
        try {
            let result : any = await axios.post(`${baseURL}/admin/logout`);
            return result;
        } catch (err) {
            throw err;
        }
    }

    async getProductCategory(param = {}) {
        try {
            let result : any = await axios.get(`${baseURL}/product/category`);
            return result;
        } catch (err) {
            throw err;
        }
    }

    async createProduct(param = {}) {
        try {
            let result : any = await axios.post(`${baseURL}/product/create`, param);
            return result;
        } catch (err) {
            throw err;
        }
    }
}

export default new Api();