import React from 'react';
import validate from "./validators/user-validators";
import Button from "react-bootstrap/Button";
import * as API_USERS from "../api/user-api";
import APIResponseErrorMessage from "../../commons/errorhandling/api-response-error-message";
import {Col, Row} from "reactstrap";
import {FormGroup, Input, Label} from 'reactstrap';

class UserDeleteForm extends React.Component {

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
                }
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
                    id:result[0].id
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

    deleteUser(id) {
        return API_USERS.deleteUser(id, (result, status, error) => {
            if (result !== null && (status === 200 || status === 201)) {
                console.log("Successfully deleted user with id: " + result);
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
        this.deleteUser(this.state.formControls.id.value);
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
                                console.log(l.target.value)
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

export default UserDeleteForm;
