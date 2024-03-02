<template>
    <SaleList :houses="houses"/>
</template>

<script>
import SaleList from '../components/SaleList.vue';
import { houses } from '../temp-data.js';
import '../property-for-sale.css'
export default {
    name: "PropertySalePage",
    components:{
        SaleList,
    },
    data() {
        return {
            houses,
        }
    }
}
</script>