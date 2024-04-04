export default function authHeader() {
    let user = JSON.parse(localStorage.getItem('user'));

    if (user && user.token) {
        console.log('Auth header:', { Authorization: 'Bearer ' + user.Token }); 
        return { Authorization: 'Bearer ' + user.token };
    } else {
        return {};
    }
}