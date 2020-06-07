import React from "react";
import {Avatar, Button, Card, Descriptions, InputNumber, List, message, Modal, Pagination, Space} from "antd";
import Api from "api/api";
import Meta from "antd/es/card/Meta";
import {
    SwapOutlined,
    EditOutlined,
    DeleteOutlined,
} from '@ant-design/icons';

const IconText = ({icon, text, color = 'rgba(0, 0, 0, 0.45)'}) => (
    <Space style={{color: `${color}`}}>
        {React.createElement(icon)}
        {text}
    </Space>
);

class TotalControl extends React.Component {
    state = {
        dataList: [],
        count: 0,
        pageCurrent: 1,
        visible: false,
        confirmLoading: false,
        tmpStock: 0,
        tmpProductSn: '',
    }

    changePage = async page => {
        let countResult = await Api.getCount();
        if (countResult.data.code === 200) {
            this.setState({count: countResult.data.data.count});
            this.setState({pageCurrent: page});
        } else {
            message.error(countResult.data.message);
        }

        let listResult = await Api.getProductList({
            page: page,
            pageSize: 8,
        });
        if (listResult.data.code === 200) {
            this.setState({dataList: listResult.data.data});
        } else {
            message.error(listResult.data.message);
        }
    }

    getData = async (page) => {
        let countResult = await Api.getCount();
        if (countResult.data.code === 200) {
            this.setState({count: countResult.data.data.count});
            this.setState({pageCurrent: 1});
        } else {
            message.error(countResult.data.message);
        }

        let listResult = await Api.getProductList({
            page: page,
            pageSize: 8,
        });
        if (listResult.data.code === 200) {
            this.setState({dataList: listResult.data.data});
        } else {
            message.error(listResult.data.message);
        }
    }

    onUpdatePublish = async (productSn) => {
        let result = await Api.changePublish({productSn: productSn});
        if (result.data.code === 200) {
            await this.getData(this.state.pageCurrent);
            message.success(result.data.message);
        } else {
            message.error(result.data.message);
        }
    }

    onUpdateStock = async (productSn, stock) => {
        let result = await Api.changeStock({productSn: productSn, stock: stock});
        if (result.data.code === 200) {
            await this.getData(this.state.pageCurrent);
            message.success(result.data.message);
        } else {
            message.error(result.data.message);
        }
    }

    onDeleteProduct = async (productSn) => {
        let result = await Api.deleteProduct({productSn: productSn});
        if (result.data.code === 200) {
            await this.getData(this.state.pageCurrent);
            message.success(result.data.message);
        } else {
            message.error(result.data.message);
        }
    }

    showModal = (productSn, stock) => {
        console.log(productSn, stock);
        this.setState({tmpProductSn: productSn});
        this.setState({tmpStock: stock});

        this.setState({
            visible: true,
        });
    };

    handleOk = async () => {
        this.setState({
            confirmLoading: true,
        });
        await this.onUpdateStock(this.state.tmpProductSn, this.state.tmpStock);
        this.setState({
            visible: false,
            confirmLoading: false,
        });
    };

    handleCancel = () => {
        this.setState({
            visible: false,
        });
    };

    async componentDidMount() {
        await this.getData(1);
    }

    onChange = (stock) => {
        this.setState({tmpStock: stock});
    }

    getPic(str) {
        return str.slice(2, -2).split('","');
    }

    render() {
        return (
            <div>
                <Modal
                    title="库存"
                    visible={this.state.visible}
                    onOk={this.handleOk}
                    confirmLoading={this.state.confirmLoading}
                    onCancel={this.handleCancel}>
                    <InputNumber min={0} max={999999} defaultValue={this.state.tmpStock} onChange={this.onChange}/>
                </Modal>
                <List
                    itemLayout="vertical"
                    size="large"
                    dataSource={this.state.dataList}
                    renderItem={item => (
                        <List.Item
                            key={item['title']}
                            actions={[
                                <Button type="link" onClick={() => this.onUpdatePublish(item['productSn'])}><IconText icon={SwapOutlined} text='更换上架状态'/></Button>,
                                <Button type="link" onClick={() => this.showModal(item['productSn'], item['stock'])}><IconText icon={EditOutlined} text='修改库存'/></Button>,
                                <Button type="link" onClick={() => this.onDeleteProduct(item['productSn'])}><IconText icon={DeleteOutlined} text='删除商品'
                                                              color={'red'}/></Button>,
                            ]}
                            extra={
                                <img
                                    alt="product img" src={this.getPic(item['pic'])[0]}
                                    style={{width: 'auto', height: '300px'}}
                                />
                            }>
                            <List.Item.Meta
                                title={<h4>{item['name']}</h4>}
                                description={`销量：${item['sale']}`}
                            />
                            <Descriptions bordered column={1}>
                                <Descriptions.Item label="价格">{(item['price'] / 100).toFixed(2)}</Descriptions.Item>
                                <Descriptions.Item label="上架状态">{item['publishStatus'] ? '是' : '否'}</Descriptions.Item>
                                <Descriptions.Item label="库存">{item['stock']}</Descriptions.Item>
                                <Descriptions.Item label="商品描述">{item['description']}</Descriptions.Item>
                            </Descriptions>
                        </List.Item>
                    )}
                />
                <Pagination current={this.state.pageCurrent} total={this.state.count} pageSize={8}
                            showSizeChanger={false} onChange={this.changePage}
                            style={{textAlign: 'right'}}/>
            </div>
        )
    }
}

export default TotalControl;