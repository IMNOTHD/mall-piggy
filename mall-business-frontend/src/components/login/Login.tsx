import React, {ReactDOM} from "react";
import {Card} from "antd";
import './login.css'
import LoginForm from "./form/loginForm";
import RegisterForm from "./form/registerForm";
import { createBrowserHistory } from 'history';
import cookie from 'react-cookies';

const history = createBrowserHistory({forceRefresh:true});
const tabListNoTitle = [
    {
        key: 'login',
        tab: '登录',
    },
    {
        key: 'register',
        tab: '注册',
    },
];

const contentListNoTitle = {
    login:
        <LoginForm />,
    register:
        <RegisterForm />,
};

class Login extends React.Component {
    state = {
        noTitleKey: 'login',
    };

    componentDidMount() {
        const token = cookie.load('admin_token');
        console.log(token);
        if (typeof token !== "undefined") {
            history.push("/");
        }
    }

    onTabChange = (key, type) => {
        console.log(key, type);
        this.setState({[type]: key});
    };

    render() {
        return (
            <main className="login-container" style={{background: '#ececec'}}>
                <div style={{ display: 'flex', justifyContent: 'center', alignItems: 'center', minHeight: '100vh' }}>
                    <Card
                        style={{width: 500}}
                        tabList={tabListNoTitle}
                        activeTabKey={this.state.noTitleKey}
                        onTabChange={key => {
                            this.onTabChange(key, 'noTitleKey');
                        }}>
                        {contentListNoTitle[this.state.noTitleKey]}
                    </Card>
                </div>
            </main>
        );
    }
}

export default Login