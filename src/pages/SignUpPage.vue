<template>
    <div class="wrapper">
        <h1>Sign Up</h1>
        <form @submit.prevent="handleRegister">
            <div v-if="!successful">
                <input type="text" v-model="user.firstName" placeholder="First Name">
                <input type="text" v-model="user.lastName" placeholder="Last Name">
                <input type="email" v-model="user.email" placeholder="Email">
                <input type="password" v-model="user.password" placeholder="Password">
                <input type="password" placeholder="Re-enter Password">
                <button type="submit">Sign Up</button>
            </div>
        </form>

        <div v-if="message" class="alert" :class="successful ? 'alert-success' : 'alert-danger'">
            {{ message }}
        </div>
        <div class="account">
            Already have an account?<router-link class="account-link" to="/login">Login Here</router-link>
        </div>
    </div>

</template>

<script>
import store from '@/store';
export default {
    name: "SignUpPage",
    data() {
        return {
            successful: false,
            loading: false,
            message: "",
            user: {
                firstName: "",
                lastName: "",
                email: "",
                password: ""
            }
        }
    },
    computed: {
        loggedIn() {
            return this.$store.state.auth.status.loggedIn;
        },
    },
    mounted() {
        if (this.loggedIn) {
            this.$router.push("/my-profile");
        }
    },
    methods: {
        handleRegister() {
            this.message = "";
            this.successful = false;
            this.loading = true;

            store.dispatch("auth/register", this.user).then(
                (data) => {
                    this.message = data.message;
                    this.successful = true;
                    this.loading = false;
                    this.$router.push("/login");
                },
                (error) => {
                    this.message =
                        (error.response &&
                            error.response.data &&
                            error.response.data.message) ||
                        error.message ||
                        error.toString();
                    this.successful = false;
                    this.loading = false;
                }
            );
        },
    },
};
</script>

<style scoped>
.wrapper {
    width: 330px;
    padding: 2rem 1rem;
    margin: 50px auto;
    background-color: white;
    border-radius: 10px;
    text-align: center;
    box-shadow: 0 20px 35px rgba(0, 0, 0, 0.1);
}

h1 {
    font-size: 2rem;
    color: black;
    margin-bottom: 1.2rem;
}

form input {
    width: 70%;
    outline: none;
    padding: 1px solid white;
    padding: 12px 20px;
    margin-bottom: 10px;
    border-radius: 20px;
    background: #e4e4e4;
}

button {
    font-size: 1rem;
    margin-top: 1.8rem;
    padding: 10px 0;
    border-radius: 20px;
    outline: none;
    border: none;
    width: 90%;
    color: white;
    background: crimson;
    cursor: pointer;
}

button :hover {
    background: black;
}

input:focus {
    border: 1px solid rgb(192, 192, 192);
}

.account {
    font-size: 0.8rem;
    margin-top: 1.4rem;
    color: #636363;
}

.account-link {
    color: crimson;
    text-decoration: none;
}
</style>