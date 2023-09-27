import React from 'react';
import Button from "react-bootstrap/Button";
import {Col, Row} from "reactstrap";
import {FormGroup, Input, Label} from 'reactstrap';
import APIResponseErrorMessage from "../commons/errorhandling/api-response-error-message";
import validate from "../user/components/validators/user-validators";
import * as API_USERS from "../user/api/user-api";

class LoginForm extends React.Component {

    constructor(props) {
        super(props);
        this.toggleForm = this.toggleForm.bind(this);
        this.reloadHandler = this.props.reloadHandler;
        this.setVerifyLogin=this.props.setVerifyLogin;

        this.state = {
            errorStatus: 0,
            error: null,

            formIsValid: false,

            formControls: {
                username: {
                    value: '',
                    placeholder: 'Enter your username',
                    valid: false,
                    touched: false,
                    validationRules: {
                        minLength: 1,
                        isRequired: true
                    }
                },
                password: {
                    value: '',
                    placeholder: 'Enter your password',
                    valid: false,
                    touched: false,
                    validationRules: {
                        minLength: 1,
                        isRequired: true
                    }
                }
            }
        };

        this.handleChange = this.handleChange.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
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

    loginUser(user) {
        return API_USERS.loginUser(user, (result, status, error) => {
            if (result !== null && (status === 200 || status === 201)) {
                this.reloadHandler();
                if(result!=null){
                    localStorage.setItem("loggedUser", result);
                    localStorage.setItem("loggedUserType", result.type);
                    localStorage.setItem("loggedUserId", result.id);
                    this.props.setVerifyLogin(true);
                }
            } else {
                console.log("Invalid user credentials!");
                window.alert('Invalid user credentials!');
                this.setState(({
                    errorStatus: status,
                    error: error
                }));
            }
        });
    }

    handleSubmit() {
        let user = {
            username: this.state.formControls.username.value,
            password: this.state.formControls.password.value,
        };
        console.log(user);
        this.loginUser(user);
    }

    render() {
        return (
            <div>

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

                <Row>
                    <Col sm={{size: '5', offset: 5}}>
                        <Button type={"login"} disabled={!this.state.formIsValid}
                                onClick={this.handleSubmit}> Login </Button>
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

export default LoginForm;
