<template>
 <div class="edit-property-page" v-if="property">
        <h2>Edit Property {{ property.propertyName }}</h2>
        <form @submit.prevent="updateProperty">
            <p>Name: {{ property.propertyName }} <span class="required">*</span></p>
            <input type="text" v-model="property.propertyName" required>
            <p>Address: {{ property.propertyAddress }} <span class="required">*</span> </p>
            <input type="text" v-model="property.propertyAddress" required>
            <p>Price: € {{ property.price }} <span class="required">*</span> </p>
            <input class="other-input" placeholder="Price" type="number" v-model="property.price" required><br>
            <p>Number of Bedroom: {{ property.numOfBed }} <span class="required">*</span> </p>
            <input class="other-input" placeholder="Number of Bedroom" v-model="property.numOfBed" type="number" min="0"
                required><br>
            <p>Number of Bathroom: {{ property.numOfBath }} <span class="required">*</span> </p>
            <input class="other-input" placeholder="Number of Bathroom" v-model="property.numOfBath" type="number"
                min="0" required><br>
            <p>Sqft: {{ property.sqft }} <span class="required">*</span> </p>
            <input class="other-input" placeholder="Sqft" type="number" v-model="property.sqft" min="0" required><br>
            <p>Description </p>

            <p style="white-space: pre-line;"></p>
            <textarea v-model="property.description" placeholder="Description"></textarea>
            <div class="unit-feature">
                <p>Unit Features: {{ property.facilities }} </p>
                <div v-for="feature in features" :key="feature.id">
                    <input type="checkbox" :id="feature.name" :value="feature.id"
                        :checked="isFeatureInPropertyFacilities(feature)">
                    <label :for="feature.name">{{ feature.name }}</label>
                </div>
            </div>
            <button type="submit">Save Changes</button>
        </form>
    </div>
    <div v-else>Loading...</div>
</template>

<script>
import axios from 'axios';
export default {
    name: "EditPropertyPage",
    data() {
        return {
            property: null,
            features: [
                { id: 1, name: "Air-Conditioning" },
                { id: 2, name: "Balcony" },
                { id: 3, name: "WIFI" },
                { id: 4, name: "Washing Machine / Dryer" },
                { id: 5, name: "Oven / Microwave" },
                { id: 6, name: "Water Heater" },
                { id: 7, name: "Fridge" },
                { id: 8, name: "Furnished" }
            ],
        }
    },
    created() {
        this.fetchProperty();
    },
    methods:{
        isFeatureInPropertyFacilities(feature) {
            return this.property.facilities.some(facility => facility.id === feature.id);
        },
        async fetchProperty() {
            const propertyId = this.$route.params.propertyId;
            console.log(propertyId);
            try {
                const response = await axios.get(`http://localhost:8080/property/${propertyId}`);
                this.property = response.data;
                console.log(this.property);
            } catch (error) {
                console.error("Error fetching property", error);
            }
        },
        async updateProperty() {
            try {
                const propertyId = this.$route.params.propertyId;
                await axios.put(`http://localhost:8080/updateproperty/${propertyId}`, this.property);
                this.$router.push(`/my-property/edit-property/image/${propertyId}`);
            } catch (error) {
                console.error("Error updating property", error);
            }
        },
    }
}
</script>