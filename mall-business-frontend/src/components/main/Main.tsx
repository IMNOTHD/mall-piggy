import {Layout, Menu, Breadcrumb, notification, Dropdown, Button} from 'antd';
import {
    DesktopOutlined,
    PieChartOutlined,
    FileOutlined,
    TeamOutlined,
    UserOutlined,
    LaptopOutlined,
    NotificationOutlined,
    DownOutlined,
} from '@ant-design/icons';
import React, {ReactDOM} from "react";
import './Main.css'
import { createBrowserHistory } from 'history';
import Api from "api/api";
import cookie from 'react-cookies';

const { Header, Content, Footer, Sider } = Layout;
const { SubMenu } = Menu;
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
        <Menu.Item key="logout" icon={<UserOutlined />}>
            Log Out
        </Menu.Item>
    </Menu>
);

class Main extends React.Component {
    state = {
        collapsed: false,
        username: '',
    };

    onCollapse = collapsed => {
        this.setState({ collapsed });
    };

    async componentDidMount() {
        let result = await Api.info();
        if (result.data.code !== 200) {
            cookie.remove('admin_token');
            loginHistory.push("/login");
        } else {
            this.setState({ username: result.data.data.username })
        }
    }

    render() {
        return (
            <Layout style={{ minHeight: '100vh' }}>
                <Header>
                    <Dropdown overlay={menu} className="logo">
                        <Button ghost type="link">
                            { this.state.username } <DownOutlined />
                        </Button>
                    </Dropdown>
                </Header>
                <Layout>
                    <Sider collapsible collapsed={this.state.collapsed} onCollapse={this.onCollapse}>
                        <Menu
                            mode="inline"
                            defaultSelectedKeys={['1']}
                            defaultOpenKeys={['sub1']}
                            style={{ height: '100%' }}>
                            <SubMenu key="sub1" icon={<UserOutlined />} title="subnav 1">
                                <Menu.Item key="1">option1</Menu.Item>
                                <Menu.Item key="2">option2</Menu.Item>
                                <Menu.Item key="3">option3</Menu.Item>
                                <Menu.Item key="4">option4</Menu.Item>
                            </SubMenu>
                            <SubMenu key="sub2" icon={<LaptopOutlined />} title="subnav 2">
                                <Menu.Item key="5">option5</Menu.Item>
                                <Menu.Item key="6">option6</Menu.Item>
                                <Menu.Item key="7">option7</Menu.Item>
                                <Menu.Item key="8">option8</Menu.Item>
                            </SubMenu>
                            <SubMenu key="sub3" icon={<NotificationOutlined />} title="subnav 3">
                                <Menu.Item key="9">option9</Menu.Item>
                                <Menu.Item key="10">option10</Menu.Item>
                                <Menu.Item key="11">option11</Menu.Item>
                                <Menu.Item key="12">option12</Menu.Item>
                            </SubMenu>
                        </Menu>
                    </Sider>
                    <Layout className="site-layout">
                        <Content style={{ margin: '0 16px' , overflow: 'initial' }}>
                            <Breadcrumb style={{ margin: '16px 0' }}>
                                <Breadcrumb.Item>User</Breadcrumb.Item>
                                <Breadcrumb.Item>Bill</Breadcrumb.Item>
                            </Breadcrumb>
                            <div className="site-layout-background" style={{ padding: 24, minHeight: 360 }}>
                                Bill is a cat.
                            </div>
                        </Content>
                        <Footer style={{ textAlign: 'center' }}>Ant Design ©2018 Created by Ant UED</Footer>
                    </Layout>
                </Layout>
            </Layout>
        );
    }
}

export default Main