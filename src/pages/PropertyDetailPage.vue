<template>
    <div v-if="house">
        <div class="detail-grid-wrap">
            <section class="img" >
                <swiper class="swiper" :modules="modules" :pagination="{ clickable: true }">
                    <swiper-slide v-for="(image, index) in house.images" :key="index">
                        <img :src="image"/>
                    </swiper-slide>  
                </swiper>
            </section>

            <div class="container">
                <div class="list">
                    <div class="left-cont">
                        <div class="listing-overview">
                            <div class="location-info">
                                <h1 class="title">{{ house.houseName }}</h1>
                                <p class="full-address">{{ house.houseAddress }}</p>
                                <div class="labels"></div>
                                <hr class="horizontal-divider">
                            </div>

                            <div class="listing-info">
                                <div class="price">
                                    <h2 class="amount">€ {{ house.price }}</h2>
                                </div>
                                <div class="amentities">
                                    <div class="amentity">
                                        <i class="amentity-bedroom"><font-awesome-icon :icon="['fas', 'bed']" /></i>
                                        <h4 class="amentity-bedroom-text">{{ house.numOfBedroom }} bed</h4>
                                    </div>

                                    <div class="amentity">
                                        <i class="amentity-bathroom"><font-awesome-icon :icon="['fas', 'toilet']" /></i>
                                        <h4 class="amentity-bathroom-text">{{ house.numOfBathroom }} bath</h4>
                                    </div>

                                    <div class="amentity">
                                        <i class="amentity-sqft"><font-awesome-icon :icon="['far', 'square']" /></i>
                                        <h4 class="amentity-sqft-text">{{ house.sqft }} sqft</h4>
                                    </div>
                                </div>
                                <hr class="horizontal-divider">
                            </div>
                        </div>
                        <section class="about-section">
                            <div class="description-block">
                                <h2 class="title">About this property</h2>
                                <h3 class="description"></h3>
                            </div>
                        </section>
                        <hr class="horizontal-divider">
                        <section class="about-features">
                            <div class="features-block">
                                <h2 class="title">Unit Features</h2>
                                <h3 class="description"><font-awesome-icon :icon="['fas', 'check']" />{{ }}</h3>
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
                                    <h2 class="name">{{ house.houseName }}</h2>
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
    <div v-else>
        <NotFoundPage/>
    </div>
</template>

<script>
import { Pagination } from 'swiper'
import { houses } from '../temp-data'
import NotFoundPage from './NotFoundPage.vue'
import '../property-detail-page.css'
  import 'swiper/css'
  import 'swiper/css/pagination'
export default {
    name: "PropertyDetailPage",
    setup() {
        return {
            modules: [Pagination]
        }
    },
    data() {
        return {
            house: houses.find(house => house.id == this.$route.params.propertyId)
        }
    },
    components:{
        NotFoundPage
    },
}
</script>



