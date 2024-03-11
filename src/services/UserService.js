//Define a service for accessing data 

import axios from "axios";
import authHeader from "./AuthHeader";

const API_URL = 'http://localhost:8080/api/test/';

class userService{
    getSaveList(){
        return axios.get(API_URL + 'user' + '/save', {headers: authHeader()})
    }
}

export default new userService();