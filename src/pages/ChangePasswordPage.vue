<template>
    <div>
      <h1>Change Password</h1>
      <form @submit.prevent="submitForm">
        <div>
          <label for="password">New Password:</label>
          <input type="password" id="password" v-model="password">
        </div>
        <div>
          <label for="confirmPassword">Confirm New Password:</label>
          <input type="password" id="confirmPassword" v-model="confirmPassword">
        </div>
        <button type="submit">Submit</button>
      </form>
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
            })
            .catch(error => {
            console.error('Error:', error);
            });
        }
  }
}
</script>