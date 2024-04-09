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
        setEmail(state, email) {
          state.email = email;
        },
      },
    plugins: [createPersistedState()],
});

export default store;