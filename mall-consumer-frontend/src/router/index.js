const routers = [
    {
        path: '/index',
        name: 'index',
        component: () => import('@/components/index/Index')
    }, {
        path: '/me',
        name: 'me',
        component: () => import('@/components/me/Me')
    }, {
        path: '/cart',
        name: 'cart',
        component: () => import('@/components/cart/Cart')
    }, {
        path: '*',
        redirect: '/index'
    }
];
export default routers