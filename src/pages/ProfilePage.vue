<template>
    <ProfileNavBar />
    <form class="my-profile" @submit="updateUser">
        <h1>My Profile</h1>
        <p>First Name: {{ user.firstName }}</p>
        <input v-model="user.firstName" placeholder="New first name">
        <p>Last Name: {{ user.lastName }}</p>
        <input v-model="user.lastName" placeholder="New last name">
        <p>Nationality: {{ user.nationality }}</p>
        <input v-model="user.nationality" placeholder="Enter your nationality">
        <p>Address: {{ user.address }}</p>
        <input v-model="user.address" placeholder="Enter your address line">
        <p>Eircode: {{ user.eircode }}</p>
        <input v-model="user.eircode" placeholder="Enter your EIRCODE">
        <p>Email: {{ user.email }}</p>
        <button class="button">Save Changes</button>
    </form>
</template>

<script>
import UserService from '@/services/user.service';
import ProfileNavBar from '@/components/ProfileNavBar.vue';
export default {
    name: "ProfilePage",
    components: {
        ProfileNavBar,

    },
    data() {
        return {
            user: {
                firstName: "",
                lastName: "",
                nationality: "",
                address: "",
                eircode: ""
            }
        }
    },
    computed: {
        currentUser() {
            return this.$store.state.auth.user;
        }
    },
    created() {
        this.fetchProfile();
    },
    methods: {
        async fetchProfile() {
            if (!this.currentUser) {
                this.$router.push('/login');
                return;
            }
            try {
                const response = await UserService.getProfile(this.currentUser.id);
                this.user = response.data;

            } catch (error) {
                console.error(error);
            }
        },
        async updateUser() {
            event.preventDefault();
            try {
                const response = await UserService.updateProfile(this.currentUser.id, this.user);
                this.user = response.data;
            } catch (error) {
                console.error(error);
            }
        }
    }
}
</script>

<style scoped>
.my-profile {
    padding-left: 40%;
    padding-top: 80px;
    background-color: white;
}

.my-profile input {
    border-radius: 8px;
    border-style: 0px solid;
    width: 300px;
    height: 30px;
    margin-bottom: 25px;
}

.button {
    margin-top: 25px;
    border-radius: 8px;
    background-color: crimson;
    color: white;
    height: 30px;
    margin-bottom: 40px;
}
</style>
