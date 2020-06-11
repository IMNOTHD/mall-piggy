import React, {ReactDOM} from "react";

class Sales extends React.Component {
    state = {
        sec: new Date().getSeconds(),
        min: new Date().getMinutes(),
        hour: new Date().getHours(),
    }
    private _timer: any;

    componentDidMount() {
        this._timer = setInterval(() => {
            const now = {
                hour: new Date().getHours(),
                min: new Date().getMinutes(),
                sec: new Date().getSeconds(),
            };
            this.setState({
                sec: now.sec,
                min: now.min,
                hour: now.hour
            });
        }, 1000);
    }

    render() {
        return (
            <div>
                <div style={{fontSize: '30px'}}>
                    现在是
                    <br />
                    北京时间 <span>{this.state.hour}</span>：<span>{this.state.min.toString().padStart(2, '0')}</span>：<span>{this.state.sec.toString().padStart(2, '0')}</span>
                </div>
            </div>
        );
    }

    componentWillUnmount() {
        clearInterval(this._timer);
    }
}

export default Sales;