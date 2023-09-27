import React from 'react';
import validate from "./validators/sensor-validators";
import Button from "react-bootstrap/Button";
import * as API_SENSORS from "../api/sensor-api";
import APIResponseErrorMessage from "../../commons/errorhandling/api-response-error-message";
import {Col, Row} from "reactstrap";
import {FormGroup, Input, Label} from 'reactstrap';
import * as API_DEVICES from "../../device/api/device-api";

class SensorForm extends React.Component {

    constructor(props) {
        super(props);
        this.toggleForm = this.toggleForm.bind(this);
        this.reloadHandler = this.props.reloadHandler;

        this.state = {
            errorStatus: 0,
            error: null,
            devices: [],

            formIsValid: false,

            formControls: {
                description: {
                    value: '',
                    placeholder: 'Enter the sensor description',
                    valid: false,
                    touched: false,
                    validationRules: {
                        minLength: 1,
                        isRequired: true
                    }
                },
                maxValue: {
                    value: '',
                    placeholder: 'Enter the maximum value registered by the sensor',
                    valid: false,
                    touched: false,
                    validationRules: {
                        isRequired: true
                    }
                },
                device: {
                    value: '',
                    placeholder: '',
                    valid: false,
                    touched: false,
                    validationRules: {
                        isRequired: true
                    }
                },
            }
        };

        this.handleChange = this.handleChange.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
    }

    componentDidMount() {
        this.fetchDevices();
    }

    fetchDevices() {
        return API_DEVICES.getDevices((result, status, err) => {

            if (result !== null && status === 200) {
                this.setState({
                    devices: result,
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

    registerSensor(sensor, id) {
        return API_SENSORS.postSensor(sensor, id, (result, status, error) => {
            if (result !== null && (status === 200 || status === 201)) {
                console.log("Successfully inserted sensor with id: " + result);
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
        let sensor = {
            description: this.state.formControls.description.value,
            maxValue: this.state.formControls.maxValue.value
        };

        console.log(sensor);
        this.registerSensor(sensor, this.state.formControls.device.value);
    }

    render() {
        return (
            <div>

                <FormGroup id='description'>
                    <Label for='descriptionField'> Description: </Label>
                    <Input name='description' id='descriptionField'
                           placeholder={this.state.formControls.description.placeholder}
                           onChange={this.handleChange}
                           defaultValue={this.state.formControls.description.value}
                           touched={this.state.formControls.description.touched ? 1 : 0}
                           valid={this.state.formControls.description.valid}
                           required
                    />
                    {this.state.formControls.description.touched && !this.state.formControls.description.valid &&
                    <div className={"error-message row"}> * Description must have at least 1 character </div>}
                </FormGroup>

                <FormGroup id='maxValue'>
                    <Label for='maxValueField'> Maximum value: </Label>
                    <Input name='maxValue' id='maxValueField'
                           placeholder={this.state.formControls.maxValue.placeholder}
                           type="number"
                           onChange={this.handleChange}
                           defaultValue={this.state.formControls.maxValue.value}
                           touched={this.state.formControls.maxValue.touched ? 1 : 0}
                           valid={this.state.formControls.maxValue.valid}
                           required
                    />
                </FormGroup>

                <FormGroup id='device'>
                    <Label for='deviceField'> Device: </Label>
                    <select name='device' id='deviceField'
                            placeholder={this.state.formControls.device.placeholder}
                            size={1}
                            onChange={l => {
                                this.handleChange(l);
                                this.setState({
                                    device: l.target.value
                                })
                            }}
                            defaultValue={'initial'}>
                        <option value={'initial'} selected disabled hidden>-Select device-</option>
                        <option value={'00000000-0000-0000-0000-000000000000'}>Without device</option>
                        {
                            this.state.devices
                                .filter(d=>d.sensor===null)
                                .map(d => {
                                return (
                                    <option key={d.id} value={d.id}> {d.id}</option>
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

export default SensorForm;
