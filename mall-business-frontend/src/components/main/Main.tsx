import {Layout, Menu, Breadcrumb, notification, Dropdown, Button, Alert} from 'antd';
import {
    DesktopOutlined,
    PieChartOutlined,
    FileOutlined,
    TeamOutlined,
    UserOutlined,
    LineChartOutlined,
    LaptopOutlined,
    NotificationOutlined,
    DownOutlined,
} from '@ant-design/icons';
import React, {ReactDOM} from "react";
import './Main.css'
import {createBrowserHistory} from 'history';
import Api from "api/api";
import cookie from 'react-cookies';
import {BrowserRouter as Router, Route, Switch, Link, withRouter} from 'react-router-dom';
import Sales from "components/main/inner/stats/sales";
import OrderControl from "components/main/inner/stats/orderControl";
import NewItem from "components/main/inner/itemControl/newItem";
import TotalControl from "components/main/inner/itemControl/totalControl";

const {Header, Content, Footer, Sider} = Layout;
const {SubMenu} = Menu;
const loginHistory = createBrowserHistory({forceRefresh: true});


async function handleMenuClick(event) {
    if (event.key === "logout") {
        await Api.logout();
        cookie.remove('admin_token');
        loginHistory.push("/login");
    }
}

const menu = (
    <Menu onClick={handleMenuClick}>
        <Menu.Item key="logout" icon={<UserOutlined/>}>
            退出登录
        </Menu.Item>
    </Menu>
);

const componentMap = {
    "stats-sales": <Sales />,
    "stats-orderControl": <OrderControl />,
    "itemControl-newItem": <NewItem />,
    "itemControl-totalControl": <TotalControl />,
}

const menuMap = {
    "stats": {
        "value": "数据管理",
        "items": {
            "stats-sales": "欢迎",
            "stats-orderControl": "订单管理",
        }
    },
    "itemControl": {
        "value": "商品管理",
        "items": {
            "itemControl-newItem": "添加商品",
            "itemControl-totalControl": "库存管理",
        }
    }
}

class Main extends React.Component {
    state = {
        collapsed: false,
        username: "",
        breadcrumbList: ["数据管理", "欢迎"],
        inner: <Sales />,
    };

    onCollapse = collapsed => {
        this.setState({collapsed});
    };

    async componentDidMount() {
        let result = await Api.info();
        if (result.data.code !== 200) {
            cookie.remove('admin_token');
            loginHistory.push("/login");
        } else {
            this.setState({username: result.data.data.username})
        }
    };

    render() {
        document.title = "Console - 商家后台";

        const handleClick = e => {
            this.setState({inner: componentMap[e.key]});
            this.setState({breadcrumbList: [menuMap[e.keyPath[1]].value, menuMap[e.keyPath[1]].items[e.keyPath[0]]]})
        };

        return (
            <Layout style={{minHeight: '100vh'}}>
                <Header>
                    <div className="logo" />
                    <Dropdown overlay={menu} className="username">
                        <Button ghost type="link">
                            {this.state.username} <DownOutlined/>
                        </Button>
                    </Dropdown>
                </Header>
                <Layout>
                    <Sider collapsible collapsed={this.state.collapsed} onCollapse={this.onCollapse}>
                        <Menu
                            onClick={handleClick}
                            mode="inline"
                            defaultSelectedKeys={['stats-sales']}
                            defaultOpenKeys={['stats', 'itemControl']}
                            style={{height: '100%'}}>
                            <SubMenu key="stats" icon={<LineChartOutlined />} title="数据管理">
                                <Menu.Item key="stats-sales">欢迎</Menu.Item>
                                <Menu.Item key="stats-orderControl">订单管理</Menu.Item>
                            </SubMenu>
                            <SubMenu key="itemControl" icon={<LaptopOutlined/>} title="商品管理">
                                <Menu.Item key="itemControl-newItem">添加商品</Menu.Item>
                                <Menu.Item key="itemControl-totalControl">库存管理</Menu.Item>
                            </SubMenu>
                        </Menu>
                    </Sider>
                    <Layout className="site-layout">
                        <Content style={{margin: '0 16px', overflow: 'initial'}}>
                            <Breadcrumb style={{margin: '16px 0'}}>
                                { this.state.breadcrumbList.map((item) => (<Breadcrumb.Item>{ item }</Breadcrumb.Item>)) }
                            </Breadcrumb>
                            <div className="site-layout-background" style={{padding: 24, minHeight: 360, height: "100%"}}>
                                {this.state.inner}
                            </div>
                        </Content>
                        <Footer style={{textAlign: 'center'}}>Piggy Mall Console ©2020 Created by IMNOTHD</Footer>
                    </Layout>
                </Layout>
            </Layout>
        );
    }
}

export default Main