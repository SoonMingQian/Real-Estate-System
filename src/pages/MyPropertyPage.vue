<template>
    <ProfileNavBar />
    <div class="page-container">
        <PropertyList :properties="properties" @property-deleted="fetchProperties" />
        <div v-if="properties.length === 0">
            <img class="img" :src="noproperty" alt="No properties" />
            <h2 class="noproperty">No Property</h2>
        </div>
        <div class="button">
            <button class="add-property">
                <router-link to="my-property/add-property"><font-awesome-icon :icon="['fas', 'plus']" /></router-link>
            </button>
        </div>
    </div>
</template>

<script>
import UserService from '@/services/user.service';
import ProfileNavBar from '../components/ProfileNavBar.vue';
import PropertyList from '../components/PropertyList.vue';
import noproperty from '../assets/noproperty.png';
import '../save-page.css'
export default {
    name: "SaleRentPage",
    components: {
        PropertyList,
        ProfileNavBar,
    },
    style: {
        styleObject: {
            color: 'red',
        }
    },
    data() {
        return {
            properties: [],
            noproperty,
        }
    },
    computed:{
        currentUser(){
            return this.$store.state.auth.user;
        }
    },
    async created() {
        await this.fetchProperties();
    },
    methods: {
        async fetchProperties() {
            if (!this.currentUser) {
                this.$router.push('/login');
                return;
            }
            try {
                const response = await UserService.getMyProperties(this.currentUser.id);
                this.properties = response.data;
            } catch (error) {
                console.error(error);
            }
        },
    },
}
</script>

<style scoped>
.page-container {
    height: 100%;
    background-color: white;
    padding-top: 30px;
    padding-left: 10px;
    padding-right: 10px;
}

.button {
    display: flex;
    justify-content: center;
}

.add-property {
    background-color: white;
    width: 80px;
    border-radius: 6px;
    cursor: pointer;
    transition: 0.5s;
}

.add-property :hover {
    color: crimson;
}

.img {
    display: block;
    margin-left: auto;
    margin-right: auto;
    width: 10%;
    height: 20%;
    padding-top: 100px;
}

.noproperty {
    text-align: center;
    color: crimson;
    font-size: 20px;
    padding-top: 10px;
    padding-bottom: 80px;
}
</style>