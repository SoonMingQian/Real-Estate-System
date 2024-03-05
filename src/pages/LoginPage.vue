<template>
<div class="wrapper">
    <h1>Login</h1>
    <form action="#">
        <input type="email" placeholder="Email">
        <input type="password" placeholder="Password">
    </form>
    <button>Login</button>
    <div class="account">
        Dont have an account?<router-link class="account-link" to="/sign-up">Sign Up Here</router-link>
    </div>
</div>



</template>

<script>
export default{
    name: 'LoginPage'
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