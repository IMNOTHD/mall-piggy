import React from "react";
import {Card, List, message, Pagination} from "antd";
import Api from "api/api";
import Meta from "antd/es/card/Meta";

const data = new Array;
for (let i = 0; i < 24; i++) {
    data.push({
        href: 'https://ant.design',
        title: `ant design part ${i}`,
        avatar: 'https://zos.alipayobjects.com/rmsportal/ODTLcjxAfvqbxHnVXCYX.png',
        description:
            'Ant Design, a design language for background applications, is refined by Ant UED Team.',
        content:
            'We supply a series of design principles, practical patterns and high quality design resources (Sketch and Axure), to help people create their product prototypes beautifully and efficiently.',
    });
}

class TotalControl extends React.Component {
    state = {
        dataList: [],
        count: 0,
        pageCurrent: 1,
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

    async componentDidMount() {
        await this.getData(1);
    }

    getPic(str) {
        return str.slice(2, -2).split('","');
    }

    render() {
        return (
            <div>
                <List
                    grid={{
                        gutter: 16,
                        xs: 1,
                        sm: 2,
                        md: 4,
                        lg: 4,
                        xl: 6,
                        xxl: 3,
                    }}
                    dataSource={this.state.dataList}
                    renderItem={item => (
                        <List.Item>
                            <Card
                                cover={
                                    <img alt="product img" src={this.getPic(item['pic'])[0]} style={{maxWidth: '500px', width: 'auto', height: 'auto'}} />
                                }>
                                <Meta
                                    title="Card title"
                                    description="This is the description"
                                />
                            </Card>
                        </List.Item>
                    )}
                />
                <Pagination current={this.state.pageCurrent} total={this.state.count} pageSize={8} showSizeChanger={false} onChange={this.changePage}
                            style={{textAlign: 'right'}}/>
            </div>
        )
    }
}

export default TotalControl;