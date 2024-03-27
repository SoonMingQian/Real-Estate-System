<template>
    <SaleList :properties="properties"/>
</template>

<script>
import axios from 'axios';
import SaleList from '../components/SaleList.vue';
import '../property-for-sale.css'
export default{
    name: 'CommercialPage',
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
                const response = await axios.get('http://localhost:8080/office');
                this.properties = response.data;
            } catch (error) {
                console.error(error);
            }
        },
       
    }
}
</script>