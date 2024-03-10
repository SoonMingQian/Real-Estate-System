<template>
<div class="wrapper">
    <h1>Login</h1>
    <form @submit.prevent="loginAccount">
        <input type="email" v-model="user.email" placeholder="Email">
        <input type="password" v-model="user.password" placeholder="Password">
        <button type="submit">Login</button>
    </form>
    
    <div class="account">
        Dont have an account?<router-link class="account-link" to="/sign-up">Sign Up Here</router-link>
    </div>
</div>



</template>

<script>
import axios from 'axios';
export default{
    name: 'LoginPage',
    data(){
        return{
            user:{
                email: '',
                password: ''
            }
        }
    },
    methods:{
        async loginAccount(){
            await axios.post('http://localhost:8080/login', {
                email: this.user.email,
                password: this.user.password
            })
            .then(response => {
            if(response.data.token){
                localStorage.setItem('token', response.data.token);
                console.log('Successfully Login');
                this.$router.push('/property-for-sale')
            }else{
                console.log('Invalid email or password');
            }
        })
        .catch(error => {
            console.error(error)
            // Handle other errors, like network issues, server errors, etc.
        })   
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