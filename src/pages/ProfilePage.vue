<template>
    <div class="my-profile">
        <h1>My Profile</h1>
        <p>First Name: {{ profile.firstName }}</p>
        <input v-model="profile.firstName" placeholder="New first name">
        <p>Last Name: {{ profile.lastName }}</p>
        <input v-model="profile.lastName" placeholder="New last name">
        <p>Nationality: {{ profile.nationality }}</p>
        <input v-model="profile.nationality" placeholder="Enter your nationality">
        <p>Address: {{ profile.address }}</p>
        <input v-model="profile.address" placeholder="Enter your address line">
        <p>Eircode: {{ profile.eircode }}</p>
        <input v-model="profile.eircode" placeholder="Enter your EIRCODE">
        <p>Email: {{ profile.email }}</p>
        <button class="button">Save Changes</button>
    </div>
</template>

<script>


export default {
    name: "ProfilePage",
    data() {
        return {
            profile: {
                firstName: 'John',
                lastName: 'Aly',
                nationality: 'Malaysian' ,
                email: 'john@gmail.com',
                address: [],
                eircode: [],    
            }
        }
    }
}
</script>

<style scoped>
.my-profile{
    padding-left: 40%;
    padding-top: 50px;
   background-color: white;
}

.my-profile input{
    border-radius: 8px;
    border-style: 0px solid;
    width: 300px;
    height: 30px;
    margin-bottom: 25px;
}

.button{
    margin-top: 25px;
    border-radius: 8px;
    background-color: crimson;
    color: white;
    height: 30px;
    margin-bottom: 40px;
}
</style>
