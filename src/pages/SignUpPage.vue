<template>
<div class="wrapper">
    <h1>Sign Up</h1>
    <form @submit.prevent="handleRegister">
        <input type="text" v-model="user.firstName" placeholder="First Name">
        <input type="text" v-model="user.lastName" placeholder="Last Name">
        <input type="email" v-model="user.email" placeholder="Email">
        <input type="password" v-model="user.password" placeholder="Password">
        <input type="password" placeholder="Re-enter Password">
        <button type="submit">Sign Up</button>
    </form>

    <div class="account">
        Already have an account?<router-link class="account-link" to="/login">Login Here</router-link>
    </div>
</div>

</template>

<script>
export default {
    name:"SignUpPage",
  data() {
    return {
      user: {
        firstName: '',
        lastName: '',
        email: '',
        password: ''
      },
      confirmPassword: ''
    };
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
      // Dispatch the register action
      this.$store.dispatch('auth/register', this.user)
        .then(() => {
          // Registration successful, do something if needed
          console.log('Registration successful');
          this.$router.push("/login");
          // Optionally, you can redirect the user to another page
        })
        .catch(error => {
          // Registration failed, handle the error
          console.error('Registration failed:', error);
          // Optionally, you can display an error message to the user
        });
    }
  }
};
</script>

<style scoped>
.wrapper{
    width: 330px;
    padding: 2rem 1rem;
    margin: 50px auto;
    background-color: white;
    border-radius: 10px;
    text-align: center;
    box-shadow: 0 20px 35px rgba(0,0,0,0.1);
}

h1{
    font-size: 2rem;
    color: black;
    margin-bottom: 1.2rem;
}

form input{
    width: 70%;
    outline: none;
    padding: 1px solid white;
    padding: 12px 20px;
    margin-bottom: 10px;
    border-radius: 20px;
    background: #e4e4e4;
}

button{
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

button :hover{
    background: black;
}

input:focus{
    border: 1px solid rgb(192, 192, 192);
}

.account{
    font-size: 0.8rem;
    margin-top: 1.4rem;
    color: #636363;
}

.account-link{
    color: crimson;
    text-decoration: none;  
}
</style>