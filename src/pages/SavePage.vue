<template>
    <ProfileNavBar />
    <div class="save-title">
        <h3><font-awesome-icon :icon="['far', 'heart']" /> Shortlisted</h3>
    </div>
    <div class="save-container">
        <div class="collection-main-content">
            <div v-if="shortlistedProperties.length > 0">
                <div class="property-card" v-for="property in shortlistedProperties" :key="property.id">
                    <router-link class="property-link" :to="'/property-listing/' + property.id">
                    <div class="property-card-content">
                        <img :src="'data:' + property.files[0].contentType + ';base64,' + property.files[0].fileData"
                                alt="{{ property.propertyName }}" class="img-cover" />
                        <div class="property-content">
                            <h3 class="p-name">{{ property.propertyName }}</h3>
                            <p class="p-address">{{ property.propertyAddress }}</p>
                            <p class="p-price">€ {{ property.price }}</p>
                            <div class="p-features">
                                <span class="p-room-details">{{ property.numOfBed }} <font-awesome-icon
                                        :icon="['fas', 'bed']" /></span>
                                <span class="p-bathroom-details">{{ property.numOfBath }} <font-awesome-icon
                                        :icon="['fas', 'toilet']" /></span>
                                <span class="p-sqft-details">{{ property.sqft }} sqft</span>
                            </div>
                            <button class="unsaved" @click.prevent.stop="removeFromShortlist(userId, property.id)">Remove</button>
                        </div>
                    </div>
                </router-link>
                </div>
            </div>
            <div v-if="shortlistedProperties.length === 0">
                <img class="img" :src="noproperty" alt="No properties" />
                <h2 class="noproperty">No Property</h2>
            </div>
        </div>
    </div>
</template>

<script>
import ProfileNavBar from '../components/ProfileNavBar.vue';
import '../save-page.css'
import UserService from '@/services/user.service';
import noproperty from '../assets/noproperty.png';
export default {
    name: "SavePage",
    components:{
        ProfileNavBar,
    },
    data() {
        return {
            shortlistedProperties: [],
            noproperty,
        }
    },
    computed:{
        currentUser(){
            return this.$store.state.auth.user;
        }
    },
    created() {
        this.fetchShortlistedProperties(this.currentUser.id);
    },
    methods:{
        fetchShortlistedProperties() {
            UserService.getShortlistedProperties(this.currentUser.id)
                .then(response => {
                    this.shortlistedProperties = response.data;
                })
                .catch(error => {
                    console.error(error);
                });
        },
        removeFromShortlist(userId, propertyId) {
            UserService.removeFromShortlist(this.currentUser.id, propertyId)
                .then(response => {
                    // remove the propertyId from the local state
                    this.shortlistedProperties = this.shortlistedProperties.filter(property => property.id !== propertyId);
                    console.log(response.data);
                })
                .catch(error => {
                    console.error(error);
                });
        },
    }
}
</script>

<style scoped>
.img {
    padding-top: 50px;
    display: block;
    max-width: 30%;
    height: auto;
    margin: 0 auto; /* center the image horizontally */
}

.noproperty {
    text-align: center; /* center the text horizontally */
    font-size: 1.5em; /* make the text larger */
    color: crimson; /* make the text dark gray */
    margin-top: 20px; /* add some space above the text */
}
</style>
