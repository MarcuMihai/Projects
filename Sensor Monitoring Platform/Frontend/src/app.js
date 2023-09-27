import React from 'react'
import {BrowserRouter as Router, Route, Switch} from 'react-router-dom'
import NavigationBar from './navigation-bar'
import Home from './home/home';
import ErrorPage from './commons/errorhandling/error-page';
import styles from './commons/styles/project-style.css';
import UserContainer from "./user/user-container";
import DeviceContainer from "./device/device-container";
import SensorContainer from "./sensor/sensor-container";
import UserDevicesContainer from "./user/user-devices-container";
import Bar from "./charts/Bar";

class App extends React.Component {

    render() {

        return (
            <div className={styles.back}>
                <Router>
                    <div>
                        <NavigationBar/>
                        <Switch>

                            <Route
                                exact
                                path='/'
                                render={() => <Home/>}
                            />

                            {localStorage.getItem("loggedUser") !== null &&
                            localStorage.getItem("loggedUserType") === ('administrator') &&
                            <Route
                                exact
                                path='/user'
                                render={() => <UserContainer/>}
                            />}

                            {localStorage.getItem("loggedUser") !== null &&
                            localStorage.getItem("loggedUserType") === ('administrator') &&
                            <Route
                                exact
                                path='/device'
                                render={() => <DeviceContainer/>}
                            />}

                            {localStorage.getItem("loggedUser") !== null &&
                            localStorage.getItem("loggedUserType") === ('administrator') &&
                            <Route
                                exact
                                path='/sensor'
                                render={() => <SensorContainer/>}
                            />}

                            {localStorage.getItem("loggedUser") !== null &&
                            localStorage.getItem("loggedUserType") === ('client') &&
                            <Route
                                exact
                                path={'/user/'+localStorage.getItem("loggedUserId").value+"/devices"}
                                render={() => <UserDevicesContainer/>}
                            />}

                            {localStorage.getItem("loggedUser") !== null &&
                            localStorage.getItem("loggedUserType") === ('client') &&
                            <Route
                                exact
                                path={'/rpc'}
                                render={() => <Bar/>}
                            />}

                            {/*Error*/}
                            <Route
                                exact
                                path='/error'
                                render={() => <ErrorPage/>}
                            />

                            <Route render={() => <ErrorPage/>}/>
                        </Switch>
                    </div>
                </Router>
            </div>
        )
    };
}

export default App
