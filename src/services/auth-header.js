export default function authHeader() {
    // Retrieve the user object from local storage and parse it from JSON to a JavaScript object
    let user = JSON.parse(localStorage.getItem('user'));

    // If the user object exists and it has a token property, return an object with an Authorization header containing the token
    // The 'Bearer' scheme is typically used for JWT (JSON Web Token) authentication
    if (user && user.token) {
        console.log('Auth header:', { Authorization: 'Bearer ' + user.Token }); 
        return { Authorization: 'Bearer ' + user.token };
    } else {
        return {};
    }
}