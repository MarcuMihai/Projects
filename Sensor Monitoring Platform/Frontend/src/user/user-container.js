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
import UserForm from "./components/user-form";
import UserDeleteForm from "./components/user-delete-form";
import UserUpdateForm from "./components/user-update-form";

import * as API_USERS from "./api/user-api"
import UserTable from "./components/user-table";

class UserContainer extends React.Component {

    constructor(props) {
        super(props);
        this.toggleForm = this.toggleForm.bind(this);
        this.toggleDeleteForm = this.toggleDeleteForm.bind(this);
        this.toggleUpdateForm = this.toggleUpdateForm.bind(this);
        this.reload = this.reload.bind(this);
        this.reloadAfterDelete = this.reloadAfterDelete.bind(this);
        this.reloadAfterUpdate = this.reloadAfterUpdate.bind(this);
        this.state = {
            selected: false,
            selectedDelete: false,
            selectedUpdate: false,
            collapseForm: false,
            tableData: [],
            isLoaded: false,
            errorStatus: 0,
            error: null
        };
    }

    componentDidMount() {
        this.fetchUsers();
    }

    fetchUsers() {
        return API_USERS.getUsers((result, status, err) => {

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

    toggleForm() {
        this.setState({selected: !this.state.selected});
    }

    toggleDeleteForm() {
        this.setState({selectedDelete: !this.state.selectedDelete});
    }

    toggleUpdateForm() {
        this.setState({selectedUpdate: !this.state.selectedUpdate});
    }

    reload() {
        this.setState({
            isLoaded: false
        });
        this.toggleForm();
        this.fetchUsers();
    }

    reloadAfterDelete() {
        this.setState({
            isLoaded: false
        });
        this.toggleDeleteForm();
        this.fetchUsers();
    }

    reloadAfterUpdate() {
        this.setState({
            isLoaded: false
        });
        this.toggleUpdateForm();
        this.fetchUsers();
    }

    render() {
        return (
            <div>
                <CardHeader>
                    <strong> User Management </strong>
                </CardHeader>
                <Card>
                    <br/>
                    <Row>
                        <Col sm={{size: '8', offset: 1}}>
                            <Button color="primary" onClick={this.toggleForm}>Add User </Button>
                            <Button color="primary" onClick={this.toggleDeleteForm}>Delete User </Button>
                            <Button color="primary" onClick={this.toggleUpdateForm}>Update User </Button>
                        </Col>
                    </Row>
                    <br/>
                    <Row>
                        <Col sm={{size: '8', offset: 1}}>
                            {this.state.isLoaded && <UserTable tableData = {this.state.tableData}/>}
                            {this.state.errorStatus > 0 && <APIResponseErrorMessage
                                errorStatus={this.state.errorStatus}
                                error={this.state.error}
                            />   }
                        </Col>
                    </Row>
                </Card>

                <Modal isOpen={this.state.selected} toggle={this.toggleForm}
                       className={this.props.className} size="lg">
                    <ModalHeader toggle={this.toggleForm}> Add User: </ModalHeader>
                    <ModalBody>
                        <UserForm reloadHandler={this.reload}/>
                    </ModalBody>
                </Modal>

                <Modal isOpen={this.state.selectedDelete} toggle={this.toggleDeleteForm}
                       className={this.props.className} size="lg">
                    <ModalHeader toggle={this.toggleDeleteForm}> Delete User: </ModalHeader>
                    <ModalBody>
                        <UserDeleteForm reloadHandler={this.reloadAfterDelete}/>
                    </ModalBody>
                </Modal>

                <Modal isOpen={this.state.selectedUpdate} toggle={this.toggleUpdateForm}
                       className={this.props.className} size="lg">
                    <ModalHeader toggle={this.toggleUpdateForm}> Update User: </ModalHeader>
                    <ModalBody>
                        <UserUpdateForm reloadHandler={this.reloadAfterUpdate}/>
                    </ModalBody>
                </Modal>

            </div>
        )

    }
}

export default UserContainer;
