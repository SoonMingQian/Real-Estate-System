import axios from "axios";

const API_URL = "https://real-estate-system-q28g.onrender.com/api/auth/";

class AuthService{
    login(user){
        return axios
        .post(API_URL + "signin", {
            email: user.email,
            password: user.password
        })
        .then(response => {
            if(response.data.token){
                localStorage.setItem("user", JSON.stringify(response.data));
            }
            return response.data;
        });
    }

    logout(){
        localStorage.removeItem("user");
    }

    register(user){
        return axios.post(API_URL + "signup", {
            firstName: user.firstName,
            lastName: user.lastName,
            email: user.email,
            password: user.password,
            role: user.role
        });
    }   
}

export default new AuthService();