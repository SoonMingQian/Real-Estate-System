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
                <p>Unit Features:</p>
                <div v-for="feature in features" :key="feature.id">
                    <input type="checkbox" :id="feature.name" :value="feature.id" v-model="feature.checked">
                    <label :for="feature.name">{{ feature.name }}</label>
                </div>
            </div>
            <button type="submit">Save Changes</button>
        </form>
    </div>
    <div v-else>Loading...</div>
</template>

<script>
import UserService from '@/services/user.service';
export default {
    name: "EditPropertyPage",
    data() {
        return {
            property: null,
            features: [
                { id: 1, name: "Air-Conditioning", checked: false },
                { id: 2, name: "Balcony", checked: false },
                { id: 3, name: "WIFI", checked: false },
                { id: 4, name: "Washing Machine / Dryer", checked: false },
                { id: 5, name: "Oven / Microwave", checked: false },
                { id: 6, name: "Water Heater", checked: false },
                { id: 7, name: "Fridge", checked: false },
                { id: 8, name: "Furnished", checked: false }
            ],
        }
    },
    created() {
        this.fetchProperty();
    },
    methods: {
        isFeatureInPropertyFacilities(feature) {
            return this.property.facilities.some(facility => facility.id === feature.id);
        },
        async fetchProperty() {
            const propertyId = this.$route.params.propertyId;
            console.log(propertyId);
            try {
                const response = await UserService.getPropertyDetail(this.$route.params.propertyId);
                this.property = response.data;
                console.log(this.property);

                // Update the `checked` field of each feature
                this.features.forEach(feature => {
                    feature.checked = this.isFeatureInPropertyFacilities(feature);
                });
            } catch (error) {
                console.error(error);
            }
        },
        async updateProperty() {
            try {
                const propertyId = this.$route.params.propertyId;
                this.property.facilities = this.features.filter(feature => feature.checked).map(feature => ({ id: feature.id }));
                await UserService.editProperty(propertyId, this.property);
                this.$router.push(`/my-property/edit-property/image/${propertyId}`);
            } catch (error) {
                console.error("Error updating property", error);
            }
        },
    }
}
</script>