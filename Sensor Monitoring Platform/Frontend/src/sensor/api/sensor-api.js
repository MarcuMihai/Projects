import {HOST} from '../../commons/hosts';
import RestApiClient from "../../commons/api/rest-client";

const endpoint = {
    sensor: '/sensor'
};

function getSensors(callback) {
    let request = new Request(HOST.backend_api + endpoint.sensor, {
        method: 'GET',
    });
    console.log(request.url);
    RestApiClient.performRequest(request, callback);
}

function getSensorById(params, callback) {
    let request = new Request(HOST.backend_api + endpoint.sensor + params.id, {
        method: 'GET'
    });
    console.log(request.url);
    RestApiClient.performRequest(request, callback);
}

function postSensor(sensor, id, callback) {
    let request = new Request(HOST.backend_api + endpoint.sensor+"/"+id, {
        method: 'POST',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(sensor)
    });
    console.log("URL: " + request.url);
    RestApiClient.performRequest(request, callback);
}

function deleteSensor(id, callback) {
    let request = new Request(HOST.backend_api + endpoint.sensor + "/"+id, {
        method: 'DELETE'
    });
    console.log(request.url);
    RestApiClient.performRequest(request, callback);
}

function updateSensor(sensor, id, callback) {
    let request = new Request(HOST.backend_api + endpoint.sensor  + "/"+ id, {
        method: 'PUT',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(sensor)
    });
    console.log("URL: " + request.url);
    RestApiClient.performRequest(request, callback);
}

export {
    getSensors,
    getSensorById,
    postSensor,
    deleteSensor,
    updateSensor
};