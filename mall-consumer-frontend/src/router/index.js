import index from "@/components/index";
import cart from "@/components/cart";
import me from "@/components/me";
import login from "@/components/me/login";

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
    }
];
export default routers