import { createStore } from "vuex";
import { auth } from "./auth.module";
import createPersistedState from "vuex-persistedstate";

const store = createStore({
    modules: {
        auth,
    },
    state:{
        email: '',
    },
    mutations: {
      // Mutation to set the email in the state
        setEmail(state, email) {
          state.email = email;
        },
      },
    plugins: [createPersistedState()],
});

export default store;