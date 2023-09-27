import React from 'react'
import logo from './commons/images/icon.png';

import {
    Button, Container,
    DropdownItem,
    DropdownMenu,
    DropdownToggle, Modal, ModalBody, ModalHeader,
    Nav,
    Navbar,
    NavbarBrand,
    NavLink,
    UncontrolledDropdown
} from 'reactstrap';
import LoginForm from "./home/login-form";

const textStyle = {
    color: 'white',
    textDecoration: 'none'
};

class NavigationBar extends React.Component {
    constructor(props) {
        super(props);
        this.toggleForm = this.toggleForm.bind(this);
        this.reload = this.reload.bind(this);
        this.state = {
            selected: false,
            verifyLogin: false
        }
    }

    reload() {
        this.toggleForm();
    }

    toggleForm() {
        this.setState({selected: !this.state.selected});
    }

    setVerifyLogin = (value) => {
        this.setState({verifyLogin: value});
    }

    render() {

        this.state.verifyLogin = localStorage.getItem("loggedUser") !== null;

        return (
            <div>
                <Navbar color="dark" light expand="md">
                    <NavbarBrand href="/">
                        <img src={
                            logo
                        }

                             width=
                                 {
                                     "50"
                                 }
                             height=
                                 {
                                     "40"
                                 }
                        />
                    </NavbarBrand>
                    <Nav className="mr-auto" navbar>
                        <p className="lead">
                            {!this.state.verifyLogin ? (
                                <Button id={"login"} color="primary" onClick={() => {
                                    this.toggleForm();
                                }}>Login</Button>
                            ) : (
                                <Button id={"logout"} color="primary" href="/"
                                        onClick={() => {
                                            localStorage.removeItem("loggedUser");
                                            localStorage.removeItem("loggedUserType");
                                            localStorage.removeItem("loggedUserId");
                                            this.setState({verifyLogin: false});
                                        }}>Logout</Button>
                            )}
                        </p>
                        {this.state.verifyLogin && (
                            <UncontrolledDropdown id="navigation" nav inNavbar>
                                <DropdownToggle style={textStyle} nav caret>
                                    Menu
                                </DropdownToggle>
                                <DropdownMenu right>

                                    {localStorage.getItem("loggedUserType") === "administrator" && (
                                        <DropdownItem>
                                            <NavLink href="/user">Users</NavLink>
                                        </DropdownItem>)}

                                    {localStorage.getItem("loggedUserType") === "administrator" && (
                                        <DropdownItem>
                                            <NavLink href="/device">Devices</NavLink>
                                        </DropdownItem>)}

                                    {localStorage.getItem("loggedUserType") === "administrator" && (
                                        <DropdownItem>
                                            <NavLink href="/sensor">Sensors</NavLink>
                                        </DropdownItem>)}

                                    {localStorage.getItem("loggedUserType") === "client" && (
                                        <DropdownItem>
                                            <NavLink
                                                href={"/user/" + localStorage.getItem("loggedUserId").value + "/devices"}>My
                                                devices</NavLink>
                                        </DropdownItem>)}

                                    {localStorage.getItem("loggedUserType") === "client" && (
                                        <DropdownItem>
                                            <NavLink href="/rpc">My history consumption</NavLink>
                                        </DropdownItem>)}

                                </DropdownMenu>
                            </UncontrolledDropdown>)}

                        <Modal isOpen={this.state.selected} toggle={this.toggleForm}
                               className={this.props.className} size="lg">
                            <ModalHeader toggle={this.toggleForm}> Login: </ModalHeader>
                            <ModalBody>
                                <LoginForm
                                    reloadHandler={this.reload}
                                    setVerifyLogin={this.setVerifyLogin}
                                />
                            </ModalBody>
                        </Modal>

                    </Nav>
                </Navbar>
            </div>);
    }
}

export default NavigationBar
