<template>
   <div class="wrapper">
        <h1>Change Password</h1>
        <form @submit.prevent="submitForm">
          <input type="password" id="password" v-model="password" placeholder="Password">
          <input type="password" id="confirmPassword" v-model="confirmPassword" placeholder="Confirm Password"> 
            <button type="submit">Submit</button>
        </form><br>
    </div>
</template>

  
  <script>
  import axios from 'axios';
  export default {
    name: 'ChangePasswordPage',
    data() {
      return {
        password: '',
        confirmPassword: ''
      }
    },
    created() {
        if (this.$store.state.email) {
            console.log('Email is stored in the Vuex store:', this.$store.state.email);
        } else {
            console.log('Email is not stored in the Vuex store');
        }
        },
    methods: {
        submitForm() {
            const urlParams = new URLSearchParams(window.location.search);
            const token = urlParams.get('token');
            const email = this.$store.state.email;

            axios.post('http://localhost:8080/api/user/savePassword', {
            email: email,
            newPassword: this.password,
            token: token
            })
            .then(response => {
            console.log('Response:', response.data);
            this.$router.push('/login');
            })
            .catch(error => {
            console.error('Error:', error);
            });
        }
  }
}
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