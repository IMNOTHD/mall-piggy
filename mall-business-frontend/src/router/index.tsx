import React from "react"
import {render} from "react-dom"
import {Router, Route} from "react-router"
import {createBrowserHistory} from "history"

import Main from "../components/main/Main"
import Login from "../components/login/Login";

export default class RouteConfig extends React.Component {
    render() {
        return (
            <Router history={createBrowserHistory()}>
                <Route path='/login' component={Login}/>
                <Route path='/' component={Main}>
                </Route>
            </Router>
        )
    }
}