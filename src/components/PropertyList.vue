<template>
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
                            <span class="status">Status: Approved</span>
                            <span class="edit-property"><font-awesome-icon :icon="['fas', 'pen-to-square']" /></span>
                            <span class="delete-property"><font-awesome-icon :icon="['fas', 'trash']" /></span>
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
export default{
    name: "PropertyList",
    props: ['savedProperty']
}
</script>