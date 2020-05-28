import React from 'react';
import ReactDOM, {render} from 'react-dom';
import './index.css';
import 'antd/dist/antd.css';
import * as serviceWorker from './serviceWorker';
import {Route, Link, BrowserRouter as Router, BrowserRouter} from 'react-router-dom'
import Main from "./components/main/Main"
import Login from "./components/login/Login";
import {createBrowserHistory} from 'history';

const App = () => (
    <div>
        <div>
            <Route exact path="/" component={Main} />
            <Route path="/login" component={Login} />
        </div>

    </div>
)

ReactDOM.render(
    <BrowserRouter>
        <App />
    </BrowserRouter>,
    document.getElementById('root')
);




// If you want your app to work offline and load faster, you can change
// unregister() to register() below. Note this comes with some pitfalls.
// Learn more about service workers: https://bit.ly/CRA-PWA
serviceWorker.unregister();
