export default function authHeader(){
    //Check Local Storage for user item
    let user = JSON.parse(localStorage.getItem('user'));

    //if there is a logged in user with JWT token, return HTTP Authroization header
    if(user && user.accessToken){
        return{ Authorization: 'Bearer ' + user.accessToken};
    }
    //Else it return an empty object
    else{
        return{};
    }
}