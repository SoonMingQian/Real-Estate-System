//Define a service for accessing data 

import axios from "axios";
import authHeader from "./AuthHeader";

const API_URL = 'http://localhost:8080/api/test/';

class userService{
    getUserSaveList(){
        return axios.get(API_URL + 'user' + '/save', {headers: authHeader()})
    }

    getAgentSaveList(){
        return axios.get(API_URL + 'agent' + '/save', {headers: authHeader()})
    }

    getAgentSaleRentList(){
        return axios.get(API_URL + 'agent' + '/my-property', {headers: authHeader()})
    }
}

export default new userService();