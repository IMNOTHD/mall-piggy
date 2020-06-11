import index from "@/components/index";
import cart from "@/components/cart";
import me from "@/components/me";
import login from "@/components/me/login";
import addressList from "@/components/me/addressList";
import addressEdit from "@/components/me/addressEdit";
import showCategory from "@/components/index/showCategory";
import productDetail from "@/components/index/productDetail";
import confirmOrder from "@/components/confirmOrder";
import orderList from "@/components/orderList";

const routers = [
    {
        path: '/',
        name: 'index',
        component: index
    }, {
        path: '/me',
        name: 'me',
        component: me
    }, {
        path: '/cart',
        name: 'cart',
        component: cart
    }, {
        path: '/login',
        name: 'login',
        component: login
    }, {
        path: '/addressList',
        name: 'addressList',
        component: addressList
    }, {
        path: '/addressEdit',
        name: 'addressEdit',
        component: addressEdit
    }, {
        path: '/showCategory',
        name: 'showCategory',
        component: showCategory
    }, {
        path: '/productDetail',
        name: 'productDetail',
        component: productDetail
    }, {
        path: '/confirmOrder',
        name: 'confirmOrder',
        component: confirmOrder
    }, {
        path: '/orderList',
        name: 'orderList',
        component: orderList
    }
];
export default routers