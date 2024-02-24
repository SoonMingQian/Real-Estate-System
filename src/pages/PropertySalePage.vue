<template>
    <h1>Property For Sale</h1>

    <div class="grid-wrap">
        <div class="house-list" v-for="house in houses" :key="house.id">
            <div class="card">
                <div class="card-banner">
                    <div class="img-holder">
                        <img :src="house.image" alt="{{ house.houseName }}" class="img-cover" />
                    </div>
                </div>

                <div class="card-content">
                    <div class="name-but">
                    <h3 class="listing-name">
                        {{ house.houseName }}
                        
                    </h3>
                    <button class="icon-btn" aria-label="add to favourite">
                            <span class="material-symbols-rounded" aria-hidden="true"><font-awesome-icon
                                    :icon="['far', 'heart']" size="lg"/></span>
                        </button>
                    </div>
                    <div class="listing-description">
                    <p class="listing-address">{{ house.houseAddress }}</p>
                    <span class="currency">€</span>
                    <span class="price">{{ house.price }}</span>

                    <div class="listing-features">

                        <div class="listing-room">
                            <span class="room-label">{{ house.numOfBedroom }}</span>
                            <span class="room-icon" aria-hidden="true"><font-awesome-icon :icon="['fas', 'bed']" /></span>
                        </div>

                        <div class="listing-bathroom">
                            <span class="bathroom-label">{{ house.numOfBathroom }}</span>
                            <span class="bathroom-icon" aria-hidden="true"><font-awesome-icon
                                    :icon="['fas', 'toilet']" /></span>
                        </div>

                        <div class="listing-sqft">
                            <span class="sqft-label">{{ house.sqft }}</span>
                            <span class="sqft-icon" aria-hidden="true">sqft</span>
                        </div>
                    </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
import { houses } from '../temp-data.js';
export default {
    name: "PropertySalePage",
    data() {
        return {
            houses,
        }
    }
}
</script>