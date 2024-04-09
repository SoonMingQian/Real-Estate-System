<template>
    <div class="wrapper">
        <h1>Sign Up</h1>
        <form @submit.prevent="handleRegister">
            <div v-if="!successful">
                <input type="text" v-model="user.firstName" placeholder="First Name" required>
                <input type="text" v-model="user.lastName" placeholder="Last Name" required>
                <input type="email" v-model="user.email" placeholder="Email" required>
                <input type="password" v-model="user.password" placeholder="Password" required>
                <input type="password" v-model="user.repassword" placeholder="Re-enter Password" required>
                <label><input type="checkbox" @change="handleRoleChange" :checked="user.role.includes('agent')"> Are you an agent?</label>
                <button type="submit" :disabled="passwordInvalid">Sign Up</button>
            </div>
        </form><br>
        <div v-if="passwordError" class="alert alert-danger">
            {{ passwordError }}
        </div>

        <div v-if="nameError" class="alert alert-danger">
            {{ nameError }}
        </div>

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
                password: "",
                repassword: "",
                role: [],
            }
        }
    },
    computed: {
        loggedIn() {
            return this.$store.state.auth.status.loggedIn;
        },
        passwordInvalid() {
            if(this.user.password.length < 8 || this.user.password != this.user.repassword) {
                return true;
            }else{
                return false;
            }
        },
        passwordError() {
            if (this.user.password.length < 8) {
                return "Password must be at least 8 characters long.";
            }
            if (this.user.password != this.user.repassword) {
                return "Passwords do not match.";
            }
            return "";
        },
        nameValid() {
            const nameRegex = /^[A-Za-z]+$/;
            return nameRegex.test(this.user.firstName) && nameRegex.test(this.user.lastName);
        },
        nameError() {
            if (!this.nameValid) {
                return "First name and last name must only contain alphabets.";
            }
            return "";
        }
        
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

            const nameRegex = /^[A-Za-z]+$/;
            if (!nameRegex.test(this.user.firstName) || !nameRegex.test(this.user.lastName)) {
                this.message = "First name and last name must only contain alphabets.";
                this.loading = false;
                return;
            }
            console.log(this.user);
            store.dispatch("auth/register", this.user).then(
                (data) => {
                    this.message = data.message;
                    this.successful = true;
                    this.loading = false;
                    console.log(data.message)
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
        handleRoleChange(e) {
        if (e.target.checked) {
            this.user.role = ["agent"]; // set role to ["agent"] when checkbox is checked
        } else {
            this.user.role = []; // set role to an empty array when checkbox is unchecked
        }
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

form input[type="checkbox"] {
    width: auto; /* Reset the width */
    vertical-align: middle; /* Align it vertically in the middle */
    margin: 0 10px 0 0; /* Add some margin to the right */
}

form label {
    display: inline-block; /* Make the label inline-block */
    vertical-align: middle; /* Align it vertically in the middle */
}

.alert {
    padding: 10px;
    margin-bottom: 20px;
    border: 1px solid transparent;
    border-radius: 4px;
}

.alert-danger {
    color: #a94442;
    background-color: #f2dede;
    border-color: #ebccd1;
}
</style>