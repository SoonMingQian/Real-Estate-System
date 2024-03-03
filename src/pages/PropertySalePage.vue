<template>
    <PropertyFilter />
    <SaleList :houses="houses"/>
</template>

<script>
import SaleList from '../components/SaleList.vue';
import PropertyFilter from '../components/PropertyFilter.vue'
import { houses } from '../temp-data.js';
import '../property-for-sale.css'
export default {
    name: "PropertySalePage",
    components:{
        SaleList,
        PropertyFilter
    },
    data() {
        return {
            houses,
        }
    }
}

</script>

