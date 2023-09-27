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
import DeviceForm from "./components/device-form";
import DeviceDeleteForm from "./components/device-delete-form";
import DeviceUpdateForm from "./components/device-update-form";
import * as API_DEVICES from "./api/device-api"
import DeviceTable from "./components/device-table";

class DeviceContainer extends React.Component {

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
        this.fetchDevices();
    }

    fetchDevices() {
        return API_DEVICES.getDevices((result, status, err) => {

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
        this.fetchDevices();
    }

    reloadAfterDelete() {
        this.setState({
            isLoaded: false
        });
        this.toggleDeleteForm();
        this.fetchDevices();
    }

    reloadAfterUpdate() {
        this.setState({
            isLoaded: false
        });
        this.toggleUpdateForm();
        this.fetchDevices();
    }

    render() {
        return (
            <div>
                <CardHeader>
                    <strong> Device Management </strong>
                </CardHeader>
                <Card>
                    <br/>
                    <Row>
                        <Col sm={{size: '8', offset: 1}}>
                            <Button color="primary" onClick={this.toggleForm}>Add Device </Button>
                            <Button color="primary" onClick={this.toggleDeleteForm}>Delete Device </Button>
                            <Button color="primary" onClick={this.toggleUpdateForm}>Update Device </Button>
                        </Col>
                    </Row>
                    <br/>
                    <Row>
                        <Col sm={{size: '8', offset: 1}}>
                            {this.state.isLoaded && <DeviceTable tableData={this.state.tableData}/>}
                            {this.state.errorStatus > 0 && <APIResponseErrorMessage
                                errorStatus={this.state.errorStatus}
                                error={this.state.error}
                            />}
                        </Col>
                    </Row>
                </Card>

                <Modal isOpen={this.state.selected} toggle={this.toggleForm}
                       className={this.props.className} size="lg">
                    <ModalHeader toggle={this.toggleForm}> Add Device: </ModalHeader>
                    <ModalBody>
                        <DeviceForm reloadHandler={this.reload}/>
                    </ModalBody>
                </Modal>

                <Modal isOpen={this.state.selectedDelete} toggle={this.toggleDeleteForm}
                       className={this.props.className} size="lg">
                    <ModalHeader toggle={this.toggleDeleteForm}> Delete Device: </ModalHeader>
                    <ModalBody>
                        <DeviceDeleteForm reloadHandler={this.reloadAfterDelete}/>
                    </ModalBody>
                </Modal>

                <Modal isOpen={this.state.selectedUpdate} toggle={this.toggleUpdateForm}
                       className={this.props.className} size="lg">
                    <ModalHeader toggle={this.toggleUpdateForm}> Update Device: </ModalHeader>
                    <ModalBody>
                        <DeviceUpdateForm reloadHandler={this.reloadAfterUpdate}/>
                    </ModalBody>
                </Modal>

            </div>
        )

    }
}


export default DeviceContainer;
