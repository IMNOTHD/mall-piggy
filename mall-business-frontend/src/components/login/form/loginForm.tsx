import React, {ReactDOM} from "react";
import {Form, Input, Button, Checkbox, Modal} from 'antd';
import Api from "api/api";
import { createBrowserHistory } from 'history';

const history = createBrowserHistory({forceRefresh:true});

const layout = {
    labelCol: {span: 6},
    wrapperCol: {span: 16},
};
const remLayout = {
    wrapperCol: {offset: 6, span: 16},
}
const tailLayout = {
    wrapperCol: {offset: 10, span: 16},
};

function error(message) {
    Modal.error({
        title: message,
    });
}

class LoginForm extends React.Component {

    render() {
        const onFinish = async values => {
            let result = await Api.login({
                account: values.account,
                password: values.password
            });
            if (result.data.code === 200) {
                history.push("/");
            } else {
                error(result.data.message);
            }

        };

        const onFinishFailed = errorInfo => {
        };

        return (
            <Form
                {...layout}
                name="login"
                initialValues={{remember: true}}
                onFinish={onFinish}
                onFinishFailed={onFinishFailed}>
                <Form.Item
                    label="帐号"
                    name="account"
                    rules={[{required: true, message: 'Please input your account!'}]}>
                    <Input/>
                </Form.Item>

                <Form.Item
                    label="密码"
                    name="password"
                    rules={[{required: true, message: 'Please input your password!'}]}>
                    <Input.Password/>
                </Form.Item>

                <Form.Item {...remLayout} name="remember" valuePropName="checked">
                    <Checkbox>Remember me</Checkbox>
                </Form.Item>

                <Form.Item {...tailLayout}>
                    <Button type="primary" htmlType="submit">
                        登录
                    </Button>
                </Form.Item>
            </Form>
        )
    }
}

export default LoginForm;