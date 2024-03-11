import axios from 'axios';

const API_URL = 'http://localhost:8080/api/auth/';

class AuthService{
    //POST email, password and save JWT to localstorage
    login(user){
        return axios.post(API_URL + 'login',{
            email: user.email,
            password: user.password
        })
        .then(response => {
            if(response.data.accessToken){
                localStorage.setItem('user', JSON.stringify(response.data))
            }
            return response.data;
        })
    }

    //remove JWT from localstorage
    logout(){
        localStorage.removeItem('user');
    }

    //POST firstname, lastname, email, password
    register(user){
        return axios.post(API_URL + 'register', {
            firstName: user.firstName,
            lastName: user.lastName,
            email: user.email,
            password: user.password
        })
    }
}

export default new AuthService();