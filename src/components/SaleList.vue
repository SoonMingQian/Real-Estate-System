<template>
    <input type="text" v-model="search">
    <div class="grid-wrap">
        <div class="house-list" v-for="property in filteredProperties" :key="property.id">
            <router-link class="property-link" :to="'/property-listing/' + property.id">
                <div class="card">
                    <div class="card-banner">
                        <div class="img-holder">
                            <img :src="'data:' + property.files[0].contentType + ';base64,' + property.files[0].fileData" alt="{{ property.propertyName }}" class="img-cover" />
                        </div>
                    </div>

                    <div class="card-content">
                        <div class="name-but">
                            <h3 class="listing-name">
                                {{ property.propertyName }}
                            </h3>
                            <button class="icon-btn" aria-label="add to favourite" @click.stop>
                                <span class="material-symbols-rounded" aria-hidden="true"><font-awesome-icon
                                        :icon="['far', 'heart']" size="lg" /></span>
                            </button>
                        </div>
                        <div class="listing-description">
                            <p class="listing-address">{{ property.propertyAddress }}</p>
                            <span class="currency">€</span>
                            <span class="price">{{ property.price }}</span>
                            <div class="price-divider"></div>
                            <div class="listing-features">

                                <div class="listing-room">
                                    <span class="room-label">{{ property.numOfBed }}</span>
                                    <span class="room-icon" aria-hidden="true"><font-awesome-icon
                                            :icon="['fas', 'bed']" /></span>
                                </div>

                                <div class="listing-bathroom">
                                    <span class="bathroom-label">{{ property.numOfBath }}</span>
                                    <span class="bathroom-icon" aria-hidden="true"><font-awesome-icon
                                            :icon="['fas', 'toilet']" /></span>
                                </div>

                                <div class="listing-sqft">
                                    <span class="sqft-label">{{ property.sqft }}</span>
                                    <span class="sqft-icon" aria-hidden="true">sqft</span>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </router-link>
        </div>
    </div>
</template>

<script>
export default {
    name: "SaleLis",
    props: ['properties'],
    data(){
        return{
            search: '',
            searchProperties: []
        }
    },
    computed: {
        filteredProperties(){
            return this.properties.filter(property => {
                return property.propertyName.toLowerCase().includes(this.search.toLowerCase());
            });
        }
    }
}
</script>