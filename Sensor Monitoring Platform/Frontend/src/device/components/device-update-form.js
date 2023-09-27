import React from 'react';
import validate from "./validators/device-validators";
import Button from "react-bootstrap/Button";
import * as API_DEVICES from "../api/device-api";
import APIResponseErrorMessage from "../../commons/errorhandling/api-response-error-message";
import {Col, Row} from "reactstrap";
import {FormGroup, Input, Label} from 'reactstrap';
import * as API_USERS from "../../user/api/user-api";

class DeviceUpdateForm extends React.Component {

    constructor(props) {
        super(props);
        this.toggleForm = this.toggleForm.bind(this);
        this.reloadHandler = this.props.reloadHandler;

        this.state = {
            errorStatus: 0,
            error: null,
            users: [],
            devices: [],

            formIsValid: false,

            formControls: {
                id: {
                    value: '',
                    placeholder: '',
                    valid: false,
                    touched: false,
                    validationRules: {
                        minLength: 1,
                        isRequired: true
                    }
                },
                description: {
                    value: '',
                    placeholder: 'Enter the device description',
                    valid: true,
                    touched: true,
                    validationRules: {
                        minLength: 1,
                        isRequired: true
                    }
                },
                address: {
                    value: '',
                    placeholder: 'Enter your address',
                    valid: true,
                    touched: true,
                },
                maximumEnergyConsumption: {
                    value: '',
                    placeholder: 'Enter the value of the maximum energy consumption',
                    valid: true,
                    touched: true,
                    validationRules: {
                        isRequired: true
                    }
                },
                averageEnergyConsumption: {
                    value: '',
                    placeholder: 'Enter the value of the average energy consumption',
                    valid: true,
                    touched: true,
                    validationRules: {
                        isRequired: true
                    }
                },
                user: {
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
        this.fetchUsers();
        this.fetchDevices();
    }

    fetchUsers() {
        return API_USERS.getUsers((result, status, err) => {
            if (result !== null && status === 200) {
                this.setState({
                    users: result,
                });
            } else {
                this.setState(({
                    errorStatus: status,
                    error: err
                }));
            }
        });
    }

    fetchDevices() {
        return API_DEVICES.getDevices((result, status, err) => {

            if (result !== null && status === 200) {
                this.setState({
                    devices: result,
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

    updateDevice(device, id) {
        return API_DEVICES.updateDevice(device, id,(result, status, error) => {
            if (result !== null && (status === 200 || status === 201)) {
                console.log("Successfully updated device with id: " + result);
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
        let device = {
            id:this.state.formControls.id.value,
            description: this.state.formControls.description.value,
            address: this.state.formControls.address.value,
            maximumEnergyConsumption: this.state.formControls.maximumEnergyConsumption.value,
            averageEnergyConsumption: this.state.formControls.averageEnergyConsumption.value,
        };
        this.updateDevice(device, this.state.formControls.user.value);
    }

    render() {
        return (
            <div>
                <FormGroup id='id'>
                    <Label for='idField'> Select device: </Label>
                    <select name='id' id='idField'
                            onChange={l => {
                                this.handleChange(l);
                                this.setState({
                                    id: l.target.value
                                })
                                let selectedDevice=this.state.devices.filter(d=> d.id===l.target.value)[0];
                                this.state.formControls.description.value=selectedDevice.description;
                                this.state.formControls.address.value=selectedDevice.address;
                                this.state.formControls.maximumEnergyConsumption.value=selectedDevice.maximumEnergyConsumption;
                                this.state.formControls.averageEnergyConsumption.value=selectedDevice.averageEnergyConsumption;
                            }}
                            defaultValue={this.state.formControls.id.value}>
                        {<option selected disabled hidden>-Select device-</option>}
                        {
                            this.state.devices.map(d => {
                                return (
                                    <option key={d.id} value={d.id}> {d.id}</option>
                                )
                            })
                        }
                    </select>
                </FormGroup>

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

                <FormGroup id='address'>
                    <Label for='addressField'> Address: </Label>
                    <Input name='address' id='addressField' placeholder={this.state.formControls.address.placeholder}
                           onChange={this.handleChange}
                           defaultValue={this.state.formControls.address.value}
                           touched={this.state.formControls.address.touched ? 1 : 0}
                           valid={this.state.formControls.address.valid}
                           required
                    />
                </FormGroup>

                <FormGroup id='maximumEnergyConsumption'>
                    <Label for='maximumEnergyConsumptionField'> Maximum energy consumption: </Label>
                    <Input name='maximumEnergyConsumption' id='maximumEnergyConsumptionField'
                           placeholder={this.state.formControls.maximumEnergyConsumption.placeholder}
                           min={0} type="number"
                           onChange={this.handleChange}
                           defaultValue={this.state.formControls.maximumEnergyConsumption.value}
                           touched={this.state.formControls.maximumEnergyConsumption.touched ? 1 : 0}
                           valid={this.state.formControls.maximumEnergyConsumption.valid}
                           required
                    />
                </FormGroup>

                <FormGroup id='averageEnergyConsumption'>
                    <Label for='averageEnergyConsumptionField'> Average energy consumption: </Label>
                    <Input name='averageEnergyConsumption' id='averageEnergyConsumptionField'
                           placeholder={this.state.formControls.averageEnergyConsumption.placeholder}
                           min={0} type="number"
                           onChange={this.handleChange}
                           defaultValue={this.state.formControls.averageEnergyConsumption.value}
                           touched={this.state.formControls.averageEnergyConsumption.touched ? 1 : 0}
                           valid={this.state.formControls.averageEnergyConsumption.valid}
                           required
                    />
                </FormGroup>

                <FormGroup id='user'>
                    <Label for='ownerUsernameField'> User: </Label>
                    <select name='user' id='userField'
                            onChange={l => {
                                this.handleChange(l);
                                this.setState({
                                    user: l.target.value
                                })
                            }}
                            defaultValue={'initial'}>
                        <option value={'initial'} selected disabled hidden>-Select user-</option>
                        <option value={'00000000-0000-0000-0000-000000000000'}>Dismiss related user</option>
                        {
                            this.state.users.map(u => {
                                return (
                                    <option key={u.id} value={u.id}> {u.username}</option>
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

export default DeviceUpdateForm;
