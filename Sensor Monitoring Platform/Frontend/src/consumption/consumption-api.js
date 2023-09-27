import JsonRpcClient from "react-jsonrpc-client";
import {HOST} from '../commons/hosts';

function getValues(callback){
    const api = new JsonRpcClient({
        endpoint: HOST.backend_api+'/rpc',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
        }
    });
    api.request('getReadValues', localStorage.getItem("loggedUserId")
    ).then(function(response) {
        callback({apiResponse: response})
    }.bind(this))
}

export {
    getValues
}