import axios from "axios";

export default axios.create({
    baseURL: "http://localhost:8080",
    headers:{
        "Content-Type": "application/json"
    }
})

class UserService{
    addUser(){
        return axios.post(baseURL);
    }
}