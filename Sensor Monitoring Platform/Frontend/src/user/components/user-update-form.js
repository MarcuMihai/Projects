import React from 'react';
import validate from "./validators/user-validators";
import Button from "react-bootstrap/Button";
import * as API_USERS from "../api/user-api";
import APIResponseErrorMessage from "../../commons/errorhandling/api-response-error-message";
import {Col, Row} from "reactstrap";
import {FormGroup, Input, Label} from 'reactstrap';

class UserUpdateForm extends React.Component {

    constructor(props) {
        super(props);
        this.toggleForm = this.toggleForm.bind(this);
        this.reloadHandler = this.props.reloadHandler;

        this.state = {
            errorStatus: 0,
            error: null,
            users: [],

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
                username: {
                    value: '',
                    placeholder: 'Enter the new username',
                    valid: true,
                    touched: true,
                    validationRules: {
                        minLength: 1,
                        isRequired: true
                    }
                },
                password: {
                    value: '',
                    placeholder: 'Enter the new password',
                    valid: true,
                    touched: true,
                    validationRules: {
                        minLength: 1,
                        isRequired: true
                    }
                },
                name: {
                    value: '',
                    placeholder: 'Enter the new name',
                    valid: true,
                    touched: true,
                    validationRules: {
                        minLength: 2,
                        isRequired: true
                    }
                },
                address: {
                    value: '',
                    placeholder: 'Enter the new address',
                    valid: true,
                    touched: true,
                },
                birthDate: {
                    value: '',
                    placeholder: 'Enter the new birth date(Year-Month-Day)',
                    valid: true,
                    touched: true,
                    validationRules: {
                        isRequired: true
                    }
                },
                type: {
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
    }

    fetchUsers() {
        return API_USERS.getUsers((result, status, err) => {

            if (result !== null && status === 200) {
                this.setState({
                    users: result,
                    user:result[0].id
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

    updateUser(user) {
        return API_USERS.updateUser(user, (result, status, error) => {
            if (result !== null && (status === 200 || status === 201)) {
                console.log("Successfully updated user with id: " + result);
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
        let user = {
            id:this.state.formControls.id.value,
            username: this.state.formControls.username.value,
            password: this.state.formControls.password.value,
            name: this.state.formControls.name.value,
            address: this.state.formControls.address.value,
            birthDate: this.state.formControls.birthDate.value,
            type: this.state.formControls.type.value
        };
        console.log(user);
        this.updateUser(user);
    }

    render() {
        return (
            <div>
                <FormGroup Id='id'>
                    <Label for='idField'> Select user: </Label>
                    <select name='id' id='idField' placeholder={this.state.formControls.id.placeholder}
                            onChange={l => {
                                this.handleChange(l);
                                this.setState({
                                    id: l.target.value
                                })
                                let selectedUser=this.state.users.filter(u=> u.id===l.target.value)[0];
                                this.state.formControls.username.value=selectedUser.username;
                                this.state.formControls.password.value=selectedUser.password;
                                this.state.formControls.name.value=selectedUser.name;
                                this.state.formControls.address.value=selectedUser.address;
                                this.state.formControls.birthDate.value=selectedUser.birthDate;
                            }}
                            defaultValue={this.state.formControls.id.value}>
                        {<option selected disabled hidden>-Select user-</option>}
                        {
                            this.state.users.map(u => {
                                return (
                                    <option key={u.id} value={u.id}> {u.username}</option>
                                )
                            })
                        }
                    </select>
                </FormGroup>

                <FormGroup id='username'>
                    <Label for='usernameField'> Username: </Label>
                    <Input name='username' id='usernameField' placeholder={this.state.formControls.username.placeholder}
                           onChange={this.handleChange}
                           defaultValue={this.state.formControls.username.value}
                           touched={this.state.formControls.username.touched ? 1 : 0}
                           valid={this.state.formControls.username.valid}
                           required
                    />
                    {this.state.formControls.username.touched && !this.state.formControls.username.valid &&
                    <div className={"error-message row"}> * Username must have at least 1 character </div>}
                </FormGroup>

                <FormGroup id='password'>
                    <Label for='passwordField'> Password: </Label>
                    <Input name='password' id='passwordField' placeholder={this.state.formControls.password.placeholder}
                           onChange={this.handleChange}
                           defaultValue={this.state.formControls.password.value}
                           touched={this.state.formControls.password.touched ? 1 : 0}
                           valid={this.state.formControls.password.valid}
                           required
                    />
                    {this.state.formControls.password.touched && !this.state.formControls.password.valid &&
                    <div className={"error-message row"}> * The password must have at least 1 character </div>}
                </FormGroup>

                <FormGroup id='name'>
                    <Label for='nameField'> Name: </Label>
                    <Input name='name' id='nameField' placeholder={this.state.formControls.name.placeholder}
                           onChange={this.handleChange}
                           defaultValue={this.state.formControls.name.value}
                           touched={this.state.formControls.name.touched ? 1 : 0}
                           valid={this.state.formControls.name.valid}
                           required
                    />
                    {this.state.formControls.name.touched && !this.state.formControls.name.valid &&
                    <div className={"error-message row"}> * Name must have at least 2 characters </div>}
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

                <FormGroup id='birthDate'>
                    <Label for='birthDateField'> Birth date: </Label>
                    <Input name='birthDate' id='birthDateField'
                           placeholder={this.state.formControls.birthDate.placeholder}
                           type={"date"}
                           onChange={this.handleChange}
                           defaultValue={this.state.formControls.birthDate.value}
                           touched={this.state.formControls.birthDate.touched ? 1 : 0}
                           valid={this.state.formControls.birthDate.valid}
                           required
                    />
                </FormGroup>

                <FormGroup id='type'>
                    <Label for='typeField'> Type: </Label>
                    <select name='type' id='typeField'
                            onChange={l => {
                                this.handleChange(l);
                                this.state.formControls.type.value = l.target.value
                            }}
                            defaultValue={this.state.formControls.type.value}>
                        {<option selected hidden>-Select the user's role-</option>}
                        {<option value={"administrator"}> administrator</option>}
                        {<option value={"client"}> client</option>}
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

export default UserUpdateForm;
