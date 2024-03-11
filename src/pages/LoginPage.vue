<template>
<div class="wrapper">
    <h1>Login</h1>
    <form @submit.prevent="handleLogin">
        <input  v-model="user.email" type="email" placeholder="Email">
        <input v-model="user.password" type="password" placeholder="Password">
        <button type="submit">Login</button>
    </form>
    
    <div class="account">
        Dont have an account?<router-link class="account-link" to="/sign-up">Sign Up Here</router-link>
    </div>
</div>



</template>

<script>
export default{
    name: 'LoginPage',
    data(){
        return{
            user:{
                email:'',
                password: ''
            }
        }
    },
    computed: {
    loggedIn() {
      return this.$store.state.auth.status.loggedIn;
    },
  },
  
    methods:{
        handleLogin(){
            this.$store.dispatch('auth/login', this.user)
            .then(() => {
            // Registration successful, do something if needed
            console.log('Login successful');
            this.$router.push("/my-profile");
            // Optionally, you can redirect the user to another page
            })
            .catch(error => {
            // Registration failed, handle the error
            console.error('Registration failed:', error);
            // Optionally, you can display an error message to the user
            });
        }
    }
}
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