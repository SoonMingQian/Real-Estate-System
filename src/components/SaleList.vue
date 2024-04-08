<template>
    <div class="sidebar" :class="{ active: showFilter }">
        <button class="close-btn" @click="showFilter = false"><font-awesome-icon :icon="['fas', 'xmark']" /></button>
        <h1>Filter</h1>
        <ul>
            <li>Property Type</li>
            <li><select class="other-input" v-model="propertyType">
                    <option value="" disabled selected>Select property type</option>
                    <option value="house">House</option>
                    <option value="apartment">Apartment</option>
                    <option value="office">Office</option>
                    <option value="parking">Parking</option>
                    <option value="restaurant/bar">Restaurant/Bar</option>
                </select>
            </li>
            <li>Price</li>
            <li><input type="number" class="input" v-model="minPrice" placeholder="Min"></li>
            <li><input type="number" class="input" v-model="maxPrice" placeholder="Max"></li>
            <li>Beds</li>
            <li><input type="number" class="input" v-model="minNumOfBed" placeholder="Min"></li>
            <li><input type="number" class="input" v-model="maxNumOfBed" placeholder="Max"></li>
            <li>Bathrooms</li>
            <li><input type="number" class="input" v-model="minNumOfBath" placeholder="Min"></li>
            <li><input type="number" class="input" v-model="maxNumOfBath" placeholder="Max"></li>
            <li>Sale Type</li>
            <li><select class="other-input" v-model="saleType">
                    <option value="" disabled selected>Select sale type</option>
                    <option value="sale">Sale</option>
                    <option value="rent">Rent</option>
                </select>
            </li>
            <li>Facilities</li>
            <li>
                <div v-for="feature in features" :key="feature.id">
                    <input type="checkbox" :id="feature.name" :value="feature.id" v-model="facilities">
                    <label :for="feature.name">{{ feature.name }}</label>
                </div>
            </li>
        </ul>
        <div class="reset" @click="resetFilter">Reset</div>
        <button class="apply" @click="getFilteredProperties">Apply</button>
    </div>



    <div class="grid-wrap">
        <div class="center-content">
            <input type="text" v-model="search" placeholder="Search">
            <button @click="showFilter = !showFilter">Filter</button>
        </div>
        <div class="house-list" v-for="property in filteredProperties" :key="property.id">
            <router-link class="property-link" :to="'/property-listing/' + property.id">
                <div class="card">
                    <div class="card-banner">
                        <div class="img-holder">
                            <img :src="'data:' + property.files[0].contentType + ';base64,' + property.files[0].fileData"
                                alt="{{ property.propertyName }}" class="img-cover" />
                        </div>
                    </div>

                    <div class="card-content">
                        <div class="name-but">
                            <h3 class="listing-name">
                                {{ property.propertyName }}
                            </h3>
                            <button class="icon-btn" aria-label="add to favourite"
                                @click.prevent.stop="toggleShortlist(userId, property.id)">
                                <span class="material-symbols-rounded" aria-hidden="true">
                                    <font-awesome-icon
                                        :icon="isShortlisted(property.id) ? ['fas', 'heart'] : ['far', 'heart']"
                                        size="lg" :class="{ 'pink-heart': isShortlisted(property.id) }" />
                                </span>
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
import UserService from '@/services/user.service';
import axios from 'axios';
export default {
    name: "SaleLis",
    props: ['properties', 'userId'],
    emits: ['updateProperties'],
    data() {
        return {
            search: '',
            propertyType: '',
            minPrice: null,
            maxPrice: null,
            minNumOfBed: null,
            maxNumOfBed: null,
            minNumOfBath: null,
            maxNumOfBath: null,
            saleType: '',
            facilities: [],
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
            showFilter: false,
            shortlistedProperties: []
        }
    },
    created() {
        if (this.currentUser) {
            this.fetchShortlistedProperties(this.currentUser.id);
        }
    },
    methods: {
        resetFilter() {
            this.propertyType = '';
            this.minPrice = null;
            this.maxPrice = null;
            this.minNumOfBed = null;
            this.maxNumOfBed = null;
            this.minNumOfBath = null;
            this.maxNumOfBath = null;
            this.saleType = '';
            this.facilities = [];
        },
        getFilteredProperties() {
            const params = {
                propertyType: this.propertyType,
                minPrice: this.minPrice,
                maxPrice: this.maxPrice,
                minNumOfBed: this.minNumOfBed,
                maxNumOfBed: this.maxNumOfBed,
                minNumOfBath: this.minNumOfBath,
                maxNumOfBath: this.maxNumOfBath,
                saleType: this.saleType,
                facilityId: this.facilities
            };

            // Remove unset parameters
            Object.keys(params).forEach(key => {
                if (params[key] === '' || params[key] === null || params[key].length === 0) {
                    delete params[key];
                }
            });

            axios.get('http://localhost:8080/api/filter', {
                params,
                paramsSerializer: params => {
                    return Object.keys(params).map(key => {
                        if (Array.isArray(params[key])) {
                            return params[key].map(value => `${key}=${value}`).join('&');
                        } else {
                            return `${key}=${params[key]}`;
                        }
                    }).join('&');
                }
            })
                .then(response => {
                    console.log(response.data);
                    this.$emit('updateProperties', response.data);
                })
                .catch(error => {
                    console.error(error);
                });
        },
        toggleShortlist(userId, propertyId) {
            if (this.isShortlisted(propertyId)) {
                this.removeFromShortlist(userId, propertyId);
            } else {
                this.addToShortlist(userId, propertyId);
            }
        },
        addToShortlist(userId, propertyId) {
            if (!this.currentUser) {
                this.$router.push('/login');
                return;
            }
            this.shortlistedProperties.push({ id: propertyId });
            UserService.addToShortlist(this.currentUser.id, propertyId)
                .then(response => {
                    console.log(response.data);
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
        fetchShortlistedProperties() {
            UserService.getShortlistedProperties(this.currentUser.id)
                .then(response => {
                    this.shortlistedProperties = response.data;
                })
                .catch(error => {
                    console.error(error);
                });
        },
        isShortlisted(propertyId) {
            return this.shortlistedProperties.some(property => property.id === propertyId);
        }
    },
    computed: {
        filteredProperties() {
            return this.properties.filter(property => {
                return property.propertyName.toLowerCase().includes(this.search.toLowerCase())
            });
        },
        currentUser() {
            return this.$store.state.auth.user;
        }
    }
}
</script>

<style scoped>
.sidebar {
    position: fixed;
    top: 0;
    left: 0;
    width: 250px;
    height: 100%;
    overflow-y: auto;
    background-color: #fff;
    padding: 20px;
    transform: translateX(-100%);
    transition: transform 0.3s ease-in-out;
    box-shadow: 2px 0 5px rgba(0, 0, 0, 0.3);
}

.sidebar.active {
    transform: translateX(0);
}

.sidebar h1 {
    font-size: 1.5rem;
    padding-top: 100px;
}

.sidebar ul {
    list-style: none;
    padding: 0;
}

.input {
    margin-bottom: 10px;
    height: 30px;
}

.sidebar ul li select {
    margin-bottom: 10px;
    height: 30px;
}

.reset {
    cursor: pointer;
    color: crimson;
    font-size: 1rem;
    margin-top: 20px;
    margin-bottom: 20px;
}

.reset :hover {
    color: black;
}

.close-btn {
    padding-top: 120px;
    position: absolute;
    top: 10px;
    right: 10px;
    border: none;
    font-size: 20px;
    display: inline-block;
    border-radius: 20px;
    outline: none;
    color: white;
    background: crimson;
    cursor: pointer;
}

.close-btn :hover {
    color: black;
}

.apply {
    font-size: 1rem;
    width: 150px;
    height: 30px;
    margin-bottom: 30px;
    display: inline-block;
    border-radius: 20px;
    outline: none;
    border: none;
    color: white;
    background: crimson;
    cursor: pointer;
}

.center-content {
    display: flex;
    justify-content: center;
    margin-bottom: 30px;
}

.center-content input {
    height: 30px;
    width: 300px;
    margin-right: 20px;
    border-radius: 10px
}

.center-content button {
    width: 100px;
    background-color: crimson;
    color: white;
    border: none;
    border-radius: 10px;
    cursor: pointer;
}

.center-content button:hover {
    color: black;
}

.pink-heart {
    color: pink;
}
</style>