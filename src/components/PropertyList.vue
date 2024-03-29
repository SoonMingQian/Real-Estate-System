<template>
    <div class="save-container">
        <div class="collection-main-content">
            <div v-if="properties.length > 0">
                <div class="property-card" v-for="property in properties" :key="property.id">
                    <div class="property-card-content">

                        <img v-if="property.files && property.files.length > 0"
                            :src="'data:' + property.files[0].contentType + ';base64,' + property.files[0].fileData"
                            alt="{{ property.propertyName }}" class="property-img" />
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
                            <span class="status">Status: {{ property.status }}</span>
                            <router-link class="property-link" :to="`/my-property/edit-property/${property.id}`">
                                <span class="edit-property"><font-awesome-icon
                                        :icon="['fas', 'pen-to-square']" /></span>
                            </router-link>

                            <span class="delete-property" @click="deleteProperty(property.id)"><font-awesome-icon
                                    :icon="['fas', 'trash']" /></span>
                        </div>
                    </div>
                </div>
            </div>
            <div v-if="properties.length === 0">
                <h2>no content</h2>
            </div>
        </div>
    </div>
</template>

<script>
import axios from 'axios';
export default {
    name: "PropertyList",
    props: ['properties'],
    methods: {
        deleteProperty(propertyId) {
            axios.delete(`http://localhost:8080/deleteproperty/${propertyId}`)
                .then(response => {
                    console.log(response.data);
                    // Emit an event to the parent component
                    this.$emit('property-deleted');
                })
                .catch(error => {
                    console.error(error);
                });
        }
    }
}
</script>