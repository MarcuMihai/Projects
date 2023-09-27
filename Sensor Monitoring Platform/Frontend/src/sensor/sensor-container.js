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
import SensorForm from "./components/sensor-form";
import SensorDeleteForm from "./components/sensor-delete-form";
import SensorUpdateForm from "./components/sensor-update-form";
import * as API_SENSORS from "./api/sensor-api"
import SensorTable from "./components/sensor-table";

class SensorContainer extends React.Component {

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
        this.fetchSensors();
    }

    fetchSensors() {
        return API_SENSORS.getSensors((result, status, err) => {

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
        this.fetchSensors();
    }

    reloadAfterDelete() {
        this.setState({
            isLoaded: false
        });
        this.toggleDeleteForm();
        this.fetchSensors();
    }

    reloadAfterUpdate() {
        this.setState({
            isLoaded: false
        });
        this.toggleUpdateForm();
        this.fetchSensors();
    }

    render() {
        return (
            <div>
                <CardHeader>
                    <strong> Sensor Management </strong>
                </CardHeader>
                <Card>
                    <br/>
                    <Row>
                        <Col sm={{size: '8', offset: 1}}>
                            <Button color="primary" onClick={this.toggleForm}>Add Sensor </Button>
                            <Button color="primary" onClick={this.toggleDeleteForm}>Delete Sensor </Button>
                            <Button color="primary" onClick={this.toggleUpdateForm}>Update Sensor </Button>
                        </Col>
                    </Row>
                    <br/>
                    <Row>
                        <Col sm={{size: '8', offset: 1}}>
                            {this.state.isLoaded && <SensorTable tableData = {this.state.tableData}/>}
                            {this.state.errorStatus > 0 && <APIResponseErrorMessage
                                errorStatus={this.state.errorStatus}
                                error={this.state.error}
                            />   }
                        </Col>
                    </Row>
                </Card>

                <Modal isOpen={this.state.selected} toggle={this.toggleForm}
                       className={this.props.className} size="lg">
                    <ModalHeader toggle={this.toggleForm}> Add Sensor: </ModalHeader>
                    <ModalBody>
                        <SensorForm reloadHandler={this.reload}/>
                    </ModalBody>
                </Modal>

                <Modal isOpen={this.state.selectedDelete} toggle={this.toggleDeleteForm}
                       className={this.props.className} size="lg">
                    <ModalHeader toggle={this.toggleDeleteForm}> Delete Sensor: </ModalHeader>
                    <ModalBody>
                        <SensorDeleteForm reloadHandler={this.reloadAfterDelete}/>
                    </ModalBody>
                </Modal>

                <Modal isOpen={this.state.selectedUpdate} toggle={this.toggleUpdateForm}
                       className={this.props.className} size="lg">
                    <ModalHeader toggle={this.toggleUpdateForm}> Update Sensor: </ModalHeader>
                    <ModalBody>
                        <SensorUpdateForm reloadHandler={this.reloadAfterUpdate}/>
                    </ModalBody>
                </Modal>

            </div>
        )

    }
}


export default SensorContainer;
