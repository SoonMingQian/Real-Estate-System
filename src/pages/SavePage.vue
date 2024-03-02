<template>
    <ProfileNavBar />
    <div class="save-title">
        <h3><font-awesome-icon :icon="['far', 'heart']" /> Shortlisted</h3>
    </div>
    <div class="save-container">
        <div class="collection-main-content">
            <div v-if="savedProperty.length > 0">
                <div class="property-card" v-for="house in savedProperty" :key="house.id">
                    <div class="property-card-content">
                        <img :src="house.image" alt="{{ house.houseName }}" class="property-img" />
                        <div class="property-content">
                            <h3 class="p-name">{{ house.houseName }}</h3>
                            <p class="p-address">{{ house.houseAddress }}</p>
                            <p class="p-price">€ {{ house.price }}</p>
                            <div class="p-features">
                                <span class="p-room-details">{{ house.numOfBedroom }} <font-awesome-icon
                                        :icon="['fas', 'bed']" /></span>
                                <span class="p-bathroom-details">{{ house.numOfBathroom }} <font-awesome-icon
                                        :icon="['fas', 'toilet']" /></span>
                                <span class="p-sqft-details">{{ house.sqft }} sqft</span>
                            </div>
                            <button class="unsaved">Remove</button>
                        </div>
                    </div>
                </div>
            </div>
            <div v-if="savedProperty.length === 0">
                <h2>no content</h2>
            </div>
        </div>
    </div>
</template>

<script>
import ProfileNavBar from '../components/ProfileNavBar.vue';
import '../save-page.css'
import { savedProperty } from '../temp-data';
export default {
    name: "SavePage",
    components:{
        ProfileNavBar,
    },
    data() {
        return {
            savedProperty,
        }
    }
}
</script>
