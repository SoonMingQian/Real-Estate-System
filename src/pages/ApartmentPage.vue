<template>
    <SaleList :properties="properties" />
</template>

<script>
import SaleList from '../components/SaleList.vue';
import UserService from '@/services/user.service';
import '../property-for-sale.css'
export default {
    name: 'ApartmentPage',
    components: {
        SaleList,
    },
    data() {
        return {
            properties: [],
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