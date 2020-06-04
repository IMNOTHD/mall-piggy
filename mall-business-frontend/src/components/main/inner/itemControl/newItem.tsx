import React, {ReactDOM} from "react";
import {
    Button,
    Cascader,
    Form,
    Input,
    InputNumber,
    message,
    Modal,
    notification,
    Switch,
    Upload,
    Result,
    Space, Popconfirm
} from "antd";
import {
    UploadOutlined,
    QuestionCircleOutlined,
} from '@ant-design/icons';
import axios from "axios";
import Api from "api/api";
import {FormInstance} from 'antd/lib/form';
import ImgCrop from 'antd-img-crop';

const {TextArea} = Input;
const layout = {
    labelCol: {span: 4},
    wrapperCol: {span: 16},
};
const tailLayout = {
    wrapperCol: {offset: 4, span: 16},
};

const uploadProps = {
    action: 'http://localhost:8080/product/upload',
    headers: {
        "Content-Type": "multipart/form-data",
    },
    multiple: false,
};

const customRequest = ({action, data, file, filename, headers, onError, onProgress, onSuccess, withCredentials,}) => {
    // EXAMPLE: post form-data with 'axios'
    // eslint-disable-next-line no-undef
    const formData = new FormData();
    if (data) {
        Object.keys(data).forEach(key => {
            formData.append(key, data[key]);
        });
    }
    formData.append(filename, file);

    axios.post(action, formData, {
        withCredentials,
        headers,
        onUploadProgress: ({total, loaded}) => {
            onProgress({percent: Math.round(loaded / total * 100).toFixed(2)}, file);
        },
    })
        .then(({data: response}) => {
            if (response.success === true) {
                onSuccess({
                    "name": response.data.filename,
                    "status": "done",
                    "url": response.data.url,
                    "thumbUrl": response.data.url,
                }, file);
                message.success("Upload success.");
            } else if (response.code === "image_repeated") {
                onSuccess({
                    "status": "done",
                    "url": response.images,
                    "thumbUrl": response.images,
                }, file);
                message.success("Upload success.");
            } else {
                onError({
                    "status": "error",
                }, file);
                message.error(response.message);
            }

        })
        .catch(onError);

    return {
        abort() {
            console.log('upload progress is aborted.');
        },
    };
}

class NewItem extends React.Component {
    state = {
        fileList: [],
        uploadButtonDisable: false,
        productCategory: [],
        successConfirmModalVisible: false,
    }

    formRef = React.createRef<FormInstance>();

    async componentDidMount() {
        let result = await Api.getProductCategory();
        console.log("result", result);
        if (result.data.code !== 200) {
            message.error(result.data.message);
        } else {
            this.setState({productCategory: result.data.data});
        }
    };

    onFinish = async values => {
        console.log(values);
        if (values.publishStatus === true && values.stock === 0) {
            notification.error({
                message: "错误",
                description: "库存为0时不得上架",
            })
        } else {
            values.price = Math.floor(values.price * 100);
            let fileList: string[] = [];
            values.upload.fileList.forEach((element) => {
                fileList.push(element.response.url);
            });
            values.upload = {
                fileList: fileList
            }
            let result = await Api.createProduct(values);
            if (result.data.code === 200) {
                this.setState({successConfirmModalVisible: true});
            } else {
                notification.error({
                    message: "添加商品时发生错误",
                    description: result.data.message,
                    duration: 0,
                })
            }
        }
    };

    handleChange = info => {
        let fileList = [...info.fileList];

        if (this.state.fileList.length === 5) {
            message.info("已到达上传图片的上限，新上传的图片将会替换掉最老的图片")
        }
        // 1. Limit the number of uploaded files
        // Only to show two recent uploaded files, and old ones will be replaced by the new
        fileList = fileList.slice(-5);

        // 2. Read from response and show file link
        fileList = fileList.map(file => {
            if (file.response) {
                // Component will show file.url as link
                file.url = file.response.url;
            }
            return file;
        });

        this.setState({fileList});
    };

    onReset = () => {
        if (this.formRef.current !== undefined) {
            this.formRef.current?.resetFields();
            this.setState({fileList: []})
        }
    }

    onConfirmSubmitSuccess = () => {
        this.setState({successConfirmModalVisible: false});
        this.onReset();
    }

    render() {
        return (
            <div>
                <Modal
                    visible={this.state.successConfirmModalVisible}
                    footer={[]}
                    centered>
                    <Result
                        status="success"
                        title="商品添加成功"
                        extra={[
                            <Button type="primary" key="confirm" onClick={this.onConfirmSubmitSuccess}>
                                Confirm
                            </Button>,
                        ]}
                    />
                </Modal>
                <Form {...layout} ref={this.formRef} onFinish={this.onFinish}>
                    <Form.Item name={['name']} label="商品名" rules={[{required: true}]}>
                        <Input/>
                    </Form.Item>
                    <Form.Item name={['stock']} label="库存" initialValue={0}
                               rules={[{type: 'number', min: 0, max: 999999}]}>
                        <InputNumber min={0} max={999999}/>
                    </Form.Item>
                    <Form.Item name={['price']} label="价格" rules={[{required: true}]}>
                        <InputNumber min={0} max={21474836.47} step={0.01} precision={2}/>
                    </Form.Item>
                    <Form.Item name={['productCategoryId']} label="商品分类" rules={[{required: true}]}>
                        <Cascader options={this.state.productCategory}/>
                    </Form.Item>
                    <Form.Item name={['publishStatus']} label="上架状态" initialValue={false} rules={[{required: true}]}>
                        <Switch defaultChecked={false}/>
                    </Form.Item>
                    <Form.Item name={['upload']} label="商品图片" rules={[{required: true}]}>
                        <ImgCrop rotate>
                            <Upload
                                {...uploadProps}
                                accept="image/*"
                                fileList={this.state.fileList}
                                customRequest={customRequest}
                                onChange={this.handleChange}
                                listType="picture"
                                disabled={this.state.uploadButtonDisable}>
                                <Button>
                                    <UploadOutlined/> Click to Upload
                                </Button>
                            </Upload>
                        </ImgCrop>
                    </Form.Item>
                    <Form.Item name={['description']} label="商品描述" rules={[{required: true}]}>
                        <TextArea rows={5}/>
                    </Form.Item>
                    <Form.Item {...tailLayout}>
                        <Space>
                            <Button type="primary" htmlType="submit">
                                Submit
                            </Button>
                            <Popconfirm
                                title="Are you sure reset?"
                                onConfirm={this.onReset}
                                okText="Yes"
                                cancelText="No"
                                placement="bottom"
                                icon={<QuestionCircleOutlined style={{color: 'red'}}/>}>
                                <Button danger type="primary">
                                    Reset
                                </Button>
                            </Popconfirm>
                        </Space>
                    </Form.Item>
                </Form>
            </div>
        )
    }
}

export default NewItem;