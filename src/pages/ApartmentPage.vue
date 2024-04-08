<template>
    <SaleList  v-if="properties.length" :properties="properties" @updateProperties="properties = $event" />
    <div v-else>
    <img class="img" :src="noproperty" alt="No properties" />
        <h2 class="noproperty">No Property</h2>
    </div>
</template>

<script>
import SaleList from '../components/SaleList.vue';
import UserService from '@/services/user.service';
import '../property-for-sale.css'
import noproperty from '../assets/noproperty.png';
export default {
    name: 'ApartmentPage',
    components: {
        SaleList,
    },
    data() {
        return {
            properties: [],
            noproperty,
        }
    },
    async created() {
        try {
            const response = await UserService.getApartment();
            this.properties = response.data;
        } catch (error) {
            console.error(error);
        }
    },
}
</script>

<style scoped>
.img {
    padding-top: 50px;
    display: block;
    max-width: 30%;
    height: auto;
    margin: 0 auto; /* center the image horizontally */
}

.noproperty {
    text-align: center; /* center the text horizontally */
    font-size: 1.5em; /* make the text larger */
    color: crimson; /* make the text dark gray */
    margin-top: 20px; /* add some space above the text */
}
</style>