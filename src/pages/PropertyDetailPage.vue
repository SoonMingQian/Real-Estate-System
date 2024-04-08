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
                                    <h3 class="description"><font-awesome-icon :icon="['fas', 'check']" /> {{
                                        facility.name }}</h3>
                                </div>
                            </div>
                        </section>
                    </div>
                    <div class="contact-section">

                        <div class="action-list-button">
                            <button class="icon-btn" aria-label="add to favourite" @click.prevent.stop="toggleShortlist(userId, property.id)">
                                <font-awesome-icon
                                    :icon="isShortlisted(property.id) ? ['fas', 'heart'] : ['far', 'heart']" size="lg"
                                    :class="{ 'pink-heart': isShortlisted(property.id) }" />
                            </button>
                            <span>Shortlist</span>
                        </div>

                        <div class="sticky-side">
                            <div class="contact-agent-card">
                                <div class="agent-info">
                                    <h2 class="name">{{ property.propertyName }}</h2>
                                </div>
                                <div class="card-body">
                                    <button class="email-agent"  @click="showForm = !showForm">
                                        <font-awesome-icon :icon="['far', 'envelope']" /> Send Enquiry
                                    </button>

                                    <div v-if="showForm" class="email-form">
                                        <button class="close-button" @click="showForm = false"><font-awesome-icon :icon="['fas', 'x']" /></button>
                                        
                                        <form @submit.prevent="sendEmail">
                                        <h1>Send Enquiry</h1>
                                        <label>Email: {{ email }}</label>
                                        

                                        <label>Message</label>
                                        <textarea name="message" v-model="message" cols="30" rows="5" placeholder="Message" required></textarea>
                                        <button type="submit">Send</button>
                                    </form>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div v-else>
        <NotFoundPage />
    </div>

</template>

<script>
import UserService from '@/services/user.service';
import { Pagination } from 'swiper'
import NotFoundPage from './NotFoundPage.vue'
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
            shortlistedProperties: [],
            showForm: false,
            name: '',
            email: '',
            message: '',
        }
    },
    components: {
        NotFoundPage
    },
    computed:{
        currentUser(){
            return this.$store.state.auth.user;
        }
    },
    async created() {
        await this.fetchProperty();
        if(this.currentUser){
            this.fetchShortlistedProperties(this.currentUser.id);
        }
        UserService.getEmailByPropertyId(this.$route.params.propertyId)
            .then(response => {
                this.email = response.data;
            })
            .catch(error => {
                console.error(error);
        });
       
    },
    methods: {
        async fetchProperty() {
            try {
                const response = await UserService.getPropertyDetail(this.$route.params.propertyId);
                this.property = response.data;
                console.log(this.property);
            } catch (error) {
                console.error(error);
            }
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
        },
        sendEmail() {
            const subject = encodeURIComponent('Enquiry');
            const body = encodeURIComponent(this.message);
            window.location.href = `mailto:${this.email}?subject=${subject}&body=${body}`;
        },
    },
}
</script>

<style scoped>

.email-form {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(0, 0, 0, 0.8);
  display: flex;
  justify-content: center;
  align-items: center;
  padding: 20px;
  box-sizing: border-box;
  z-index: 1000; /* Add a high z-index value */
}

.email-form form {
  background-color: #fff; /* Give the actual form a white background */
  padding: 20px;
  border-radius: 5px;
  max-width: 500px; /* Limit the form width */
  width: 100%; /* Make the form take up all available width */
}

.email-form label {
  display: block;
  margin-bottom: 5px;
}

.email-form input,
.email-form textarea {
  width: 95%;
  padding: 10px;
  margin-bottom: 10px;
  border: 1px solid #ccc;
  border-radius: 4px;
}

.email-form button {
  padding: 10px 20px;
  background-color: crimson;
  color: white;
  border: none;
  border-radius: 4px;
  cursor: pointer;
}

.email-form button:hover {
  background-color: #45a049;
}

.close-button {
  position: absolute;
  top: 20px;
  right: 20px;
  background: none;
  border: none;
  font-size: 2em;
  color: white;
  cursor: pointer;
}
</style>
