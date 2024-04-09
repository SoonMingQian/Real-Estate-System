<template>
<div>
      <h1>Change Password</h1>
      <form @submit.prevent="submitForm">
        <div>
          <label for="email">Enter Email:</label>
          <input type="email" id="email" v-model="email">
        </div>
        <button type="submit">Submit</button>
      </form>
    </div>
</template>

<script>
import axios from 'axios';
export default {
  name: 'ForgotPasswordPage',
  data() {
    return {
      email: ''
    }
  },
    methods: {
        submitForm() {
        this.$store.commit('setEmail', this.email);
        axios.post(`http://localhost:8080/api/user/resetPassword?email=${this.email}`)
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