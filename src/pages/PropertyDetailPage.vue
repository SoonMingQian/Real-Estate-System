<template>
    <div v-if="property">
        <div class="detail-grid-wrap">
            <section class="img">
                <swiper class="swiper" :modules="modules" :pagination="{ clickable: true }">
                    <swiper-slide v-for="(files, index) in property.files" :key="index">
                        <img :src="'data:' + files.contentType + ';base64,' + files.fileData" />
                    </swiper-slide>
                </swiper>
            </section>

            <div class="container">
                <div class="list">
                    <div class="left-cont">
                        <div class="listing-overview">
                            <div class="location-info">
                                <h1 class="title">{{ property.propertyName }}</h1>
                                <p class="full-address">{{ property.propertyAddress }}</p>
                                <div class="labels"></div>
                                <hr class="horizontal-divider">
                            </div>

                            <div class="listing-info">
                                <div class="price">
                                    <h2 class="amount">€ {{ property.price }}</h2>
                                </div>
                                <div class="amentities">
                                    <div class="amentity">
                                        <i class="amentity-bedroom"><font-awesome-icon :icon="['fas', 'bed']" /></i>
                                        <h4 class="amentity-bedroom-text">{{ property.numOfBed }} bed</h4>
                                    </div>

                                    <div class="amentity">
                                        <i class="amentity-bathroom"><font-awesome-icon :icon="['fas', 'toilet']" /></i>
                                        <h4 class="amentity-bathroom-text">{{ property.numOfBath }} bath</h4>
                                    </div>

                                    <div class="amentity">
                                        <i class="amentity-sqft"><font-awesome-icon :icon="['far', 'square']" /></i>
                                        <h4 class="amentity-sqft-text">{{ property.sqft }} sqft</h4>
                                    </div>
                                </div>
                                <hr class="horizontal-divider">
                            </div>
                        </div>
                        <section class="about-section">
                            <div class="description-block">
                                <h2 class="title">About this property</h2>
                                <h3 class="description">{{ property.description }}</h3>
                            </div>
                        </section>
                        <hr class="horizontal-divider">
                        <section class="about-features">
                            <div class="features-block">
                                <h2 class="title">Unit Features</h2>
                                <div v-for="(facility, index) in property.facilities" :key="index">
                                <h3 class="description"><font-awesome-icon :icon="['fas', 'check']" /> {{ facility.name }}</h3>
                            </div>
                            </div>
                        </section>
                    </div>
                    <div class="contact-section">

                        <div class="action-list-button">
                            <button class="icon-btn" aria-label="add to favourite">
                                <font-awesome-icon :icon="['far', 'heart']" />
                            </button>
                            <span>Shortlist</span>
                        </div>

                        <div class="sticky-side">
                            <div class="contact-agent-card">
                                <div class="agent-info">
                                    <h2 class="name">{{ property.propertyName }}</h2>
                                </div>
                                <div class="card-body">
                                    <button class="email-agent">
                                        <font-awesome-icon :icon="['far', 'envelope']" /> Send Enquiry
                                    </button>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    
</template>

<script>
import axios from 'axios';
import { Pagination } from 'swiper'

import '../property-detail-page.css'
import 'swiper/css'
import 'swiper/css/pagination'
export default {
    name: "PropertyDetailPage",
    setup() {
        return {
            modules: [Pagination],

        }
    },
    data() {
        return {
            property: null,
        }
    },
    async created() {
        await this.fetchProperty();
    },
    methods: {
        async fetchProperty() {
            try {
                const response = await axios.get(`http://localhost:8080/property/${this.$route.params.propertyId}`);
                this.property = response.data;
            } catch (error) {
                console.error(error);
            }
        },

    },
   
}
</script>
