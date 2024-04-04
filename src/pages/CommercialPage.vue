<template>
    <SaleList :properties="properties" />
</template>

<script>
import UserService from '@/services/user.service';
import SaleList from '../components/SaleList.vue';
import '../property-for-sale.css'
export default {
    name: 'CommercialPage',
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
            const response = await UserService.getCommercial();
            this.properties = response.data;
        } catch (error) {
            console.error(error);
        }
    },
}
</script>