import AuthService from '@/services/auth.service';

const user = JSON.parse(localStorage.getItem('user'));
// Initialize the initial state based on whether a user is present in local storage
const initialState = user
  ? { status: { loggedIn: true }, user }
  : { status: { loggedIn: false }, user: null };

// Define the auth Vuex module
export const auth = {
    namespaced: true,
    state: initialState,
    actions:{
        // Action for user login
        login({ commit }, user){
            return AuthService.login(user).then(
                // If login is successful, commit the loginSuccess mutation
                user => {
                    commit('loginSuccess', user);
                    return Promise.resolve(user);
                },
                // If login fails, commit the loginFailure mutation
                error => {
                    commit('loginFailure');
                    return Promise.reject(error);
                }
            );
        },
        // Action for user logout
        logout({ commit }){
            AuthService.logout();
            commit('logout');
        },
        // Action for user registration
        register({ commit }, user){
            return AuthService.register(user).then(
                // If registration is successful, commit the registerSuccess mutation
                response => {
                    commit('registerSuccess');
                    return Promise.resolve(response.data);
                },
                // If registration fails, commit the registerFailure mutation
                error => {
                    commit('registerFailure');
                    return Promise.reject(error);
                }
            );
        },
    },
    mutations:{
        // Mutation for successful login
        loginSuccess(state, user){
            state.status.loggedIn = true;
            state.user = user;
            state.email = user.email;
        },
        // Mutation for failed login
        loginFailure(state){
            state.status.loggedIn = false;
            state.user = null;
        },
        // Mutation for user logout
        logout(state){
            state.status.loggedIn = false;
            state.user = null;
            state.email = null;
        },
        // Mutation for successful registration
        registerSuccess(state){
            state.status.loggedIn = false;
        },
        // Mutation for failed registration
        registerFailure(state){
            state.status.loggedIn = false;
        },
    }
}