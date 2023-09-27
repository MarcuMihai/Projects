import React from 'react';
import validate from "./validators/sensor-validators";
import Button from "react-bootstrap/Button";
import * as API_SENSORS from "../api/sensor-api";
import APIResponseErrorMessage from "../../commons/errorhandling/api-response-error-message";
import {Col, Row} from "reactstrap";
import {FormGroup, Input, Label} from 'reactstrap';

class SensorDeleteForm extends React.Component {

    constructor(props) {
        super(props);
        this.toggleForm = this.toggleForm.bind(this);
        this.reloadHandler = this.props.reloadHandler;

        this.state = {
            errorStatus: 0,
            error: null,
            sensors: [],

            formIsValid: false,

            formControls: {
                id: {
                    value: '',
                    placeholder: '',
                    valid:true,
                    touched: true,
                    validationRules: {
                        isRequired: true
                    }
                }
            }
        };

        this.handleChange = this.handleChange.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
    }

    componentDidMount() {
        this.fetchSensors();
    }

    fetchSensors() {
        return API_SENSORS.getSensors((result, status, err) => {

            if (result !== null && status === 200) {
                this.setState({
                    sensors: result,
                    id: result[0].id
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
        this.setState({collapseForm: !this.state.collapseForm});
    }


    handleChange = event => {

        const name = event.target.name;
        const value = event.target.value;

        const updatedControls = this.state.formControls;

        const updatedFormElement = updatedControls[name];

        updatedFormElement.value = value;
        updatedFormElement.touched = true;
        updatedFormElement.valid = validate(value, updatedFormElement.validationRules);
        updatedControls[name] = updatedFormElement;

        let formIsValid = true;
        for (let updatedFormElementName in updatedControls) {
            formIsValid = updatedControls[updatedFormElementName].valid && formIsValid;
        }

        this.setState({
            formControls: updatedControls,
            formIsValid: formIsValid
        });

    };

    deleteSensor(id) {
        return API_SENSORS.deleteSensor(id, (result, status, error) => {
            if (result !== null && (status === 200 || status === 201)) {
                console.log("Successfully deleted sensor with id: " + result);
                this.reloadHandler();
            } else {
                this.setState(({
                    errorStatus: status,
                    error: error
                }));
            }
        });
    }

    handleSubmit() {
        console.log(this.state.formControls.id.value);
        this.deleteSensor(this.state.formControls.id.value);
    }

    render() {
        return (
            <div>
                <FormGroup id='id'>
                    <Label for='idField'> Select sensor: </Label>
                    <select name='id' id='idField'
                            onChange={l => {
                                this.handleChange(l);
                                this.setState({
                                    id: l.target.value
                                })
                            }}
                            defaultValue={this.state.formControls.id.value}>
                        {<option selected disabled hidden>-Select sensor-</option>}
                        {
                            this.state.sensors.map(s => {
                                return (
                                    <option key={s.id} value={s.id}> {s.id}</option>
                                )
                            })
                        }
                    </select>
                </FormGroup>

                <Row>
                    <Col sm={{size: '5', offset: 8}}>
                        <Button type={"submit"} disabled={!this.state.formIsValid}
                                onClick={this.handleSubmit}> Submit </Button>
                    </Col>
                </Row>

                {
                    this.state.errorStatus > 0 &&
                    <APIResponseErrorMessage errorStatus={this.state.errorStatus} error={this.state.error}/>
                }
            </div>
        );
    }
}

export default SensorDeleteForm;
