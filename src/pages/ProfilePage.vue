<template>
    <ProfileNavBar />
    <div class="my-profile">
        <h1>My Profile</h1>
        <p>First Name: {{ currentUser.firstName }}</p>
        <input v-model="currentUser.firstName" placeholder="New first name">
        <p>Last Name: {{ currentUser.lastName }}</p>
        <input v-model="currentUser.lastName" placeholder="New last name">
        <p>Nationality: {{ currentUser.nationality }}</p>
        <input v-model="currentUser.nationality" placeholder="Enter your nationality">
        <p>Address: {{ currentUser.address }}</p>
        <input v-model="currentUser.address" placeholder="Enter your address line">
        <p>Eircode: {{ currentUser.eircode }}</p>
        <input v-model="currentUser.eircode" placeholder="Enter your EIRCODE">
        <p>Email: {{ currentUser.email }}</p>
        <button class="button">Save Changes</button>
    </div>
</template>

<script>
import ProfileNavBar from '@/components/ProfileNavBar.vue';
export default {
    name: "ProfilePage",
    components:{
        ProfileNavBar,
    },
    computed:{
        currentUser(){
            return this.$store.state.auth.user;
        }
    },
    mounted(){
        if(!this.currentUser){
            this.$router.push('/login');
        }
    }
}
</script>

<style scoped>
.my-profile{
    padding-left: 40%;
    padding-top: 80px;
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
