import React from 'react';
import APIResponseErrorMessage from "../commons/errorhandling/api-response-error-message";
import {
    Button,
    Card,
    CardHeader,
    Col,
    Modal,
    ModalBody,
    ModalHeader,
    Row
} from 'reactstrap';

import * as API_USERS from "./api/user-api"
import UserDevicesTable from "./components/user-devices-table";

class UserDevicesContainer extends React.Component {

    constructor(props) {
        super(props);
        this.reload = this.reload.bind(this);
        this.state = {
            collapseForm: false,
            tableData: [],
            isLoaded: false,
            errorStatus: 0,
            error: null
        };
    }

    componentDidMount() {
        this.fetchUserDevices();
    }

    fetchUserDevices() {
        let id;
        if(localStorage.getItem("loggedUser")!==null && localStorage.getItem("loggedUserType")==="client"){
            id=localStorage.getItem("loggedUserId");
        }
        else id=null;
        return API_USERS.getDevicesByUserId(id,(result, status, err) => {

            if (result !== null && status === 200) {
                this.setState({
                    tableData: result,
                    isLoaded: true
                });
            } else {
                this.setState(({
                    errorStatus: status,
                    error: err
                }));
            }
        });
    }

    reload() {
        this.setState({
            isLoaded: false
        });
        this.fetchUserDevices();
    }

    render() {
        return (
            <div>
                <CardHeader>
                    <strong> User Devices Management </strong>
                </CardHeader>
                <Card>
                    <Row>
                        <Col sm={{size: '8', offset: 1}}>
                            {this.state.isLoaded && <UserDevicesTable tableData = {this.state.tableData}/>}
                            {this.state.errorStatus > 0 && <APIResponseErrorMessage
                                errorStatus={this.state.errorStatus}
                                error={this.state.error}
                            />   }
                        </Col>
                    </Row>
                </Card>
            </div>
        )

    }
}

export default UserDevicesContainer;
