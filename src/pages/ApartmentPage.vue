<template>
    <SaleList :properties="properties"/>
</template>

<script>
import SaleList from '../components/SaleList.vue';
import axios from 'axios';
import '../property-for-sale.css'
export default{
    name: 'ApartmentPage',
    components:{
        SaleList,
    },
    data() {
        return {
            properties: [],
        }
    },
    created() {
        this.fetchProperties();
    },
    methods: {
        async fetchProperties() {
            try {
                const response = await axios.get('http://localhost:8080/apartment');
                this.properties = response.data;
            } catch (error) {
                console.error(error);
            }
        },
       
    }
}
</script>