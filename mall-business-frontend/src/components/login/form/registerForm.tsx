import React, {ReactDOM} from "react";
import {Form, Input, Button, Checkbox, Modal} from 'antd';
import Api from 'api/api';
import { createBrowserHistory } from 'history';

const history = createBrowserHistory({forceRefresh:true});
const layout = {
    labelCol: { span: 6 },
    wrapperCol: { span: 16 },
};
const tailLayout = {
    wrapperCol: { offset: 10, span: 16 },
};

function error(message) {
    Modal.error({
        title: message,
    });
}

const onFinish = async values => {
    let result = await Api.register({
        username: values.username,
        email: values.email,
        password: values.password
    });
    if (result.data.code === 200) {
        let result = await Api.login({
            account: values.username,
            password: values.password
        });
        if (result.data.code === 200) {
            history.push("/");
        } else {
            error(result.data.message);
        }
    } else {
        error(result.data.message);
    }
};

const onFinishFailed = errorInfo => {
};

class RegisterForm extends React.Component {

    render() {
        document.title = "注册 - 商家后台";

        return (
            <Form
                {...layout}
                name="register"
                onFinish={onFinish}
                onFinishFailed={onFinishFailed}>
                <Form.Item
                    label="帐号"
                    name="username"
                    rules={[{ required: true, message: 'Please input your username!' }]}>
                    <Input />
                </Form.Item>

                <Form.Item
                    label="邮箱"
                    name="email"
                    rules={[{ required: true, message: 'Not a validate email!', type: 'email' }]}>
                    <Input />
                </Form.Item>

                <Form.Item
                    name="password"
                    label="密码"
                    rules={[
                        {
                            required: true,
                            message: 'Please input your password!',
                        },
                    ]}
                    hasFeedback
                >
                    <Input.Password />
                </Form.Item>

                <Form.Item
                    name="confirm"
                    label="确认密码"
                    dependencies={['password']}
                    hasFeedback
                    rules={[
                        {
                            required: true,
                            message: 'Please confirm your password!',
                        },
                        ({ getFieldValue }) => ({
                            validator(rule, value) {
                                if (!value || getFieldValue('password') === value) {
                                    return Promise.resolve();
                                }
                                return Promise.reject('两次输入的密码不一致');
                            },
                        }),
                    ]}
                >
                    <Input.Password />
                </Form.Item>

                <br />

                <Form.Item {...tailLayout}>
                    <Button type="primary" htmlType="submit">
                        注册
                    </Button>
                </Form.Item>
            </Form>
        )
    }
} 

export default RegisterForm