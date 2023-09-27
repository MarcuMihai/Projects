import React from 'react';

import BackgroundImg from '../commons/images/background.jpg';

import {Button, Container, Jumbotron, Modal, ModalBody, ModalHeader} from 'reactstrap';

const backgroundStyle = {
    backgroundPosition: 'center',
    backgroundSize: 'cover',
    backgroundRepeat: 'no-repeat',
    width: "100%",
    height: "1920px",
    backgroundImage: `url(${BackgroundImg})`
};
const textStyle = {color: 'white', };

class Home extends React.Component {

    render() {

        return (

            <div>
                <Jumbotron fluid style={backgroundStyle}>
                    <Container fluid>
                        <h1 className="display-3" style={textStyle}>Online Energy Utility Platform</h1>
                        <p className="lead" style={textStyle}> <b>Enabling real time monitoring of smart devices and sensors in your home.</b> </p>
                    </Container>
                </Jumbotron>

            </div>
        )
    };
}

export default Home
