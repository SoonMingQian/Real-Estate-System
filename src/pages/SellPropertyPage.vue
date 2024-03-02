<template>
    <div class="page-container">
        <PropertyList :savedProperty="savedProperty" />
        <div class="button">
        <button class="add-property"><font-awesome-icon :icon="['fas', 'plus']" /></button>
    </div>
</div>
</template>

<script>
import PropertyList from '../components/PropertyList.vue';
import { savedProperty } from '@/temp-data';
import '../save-page.css'
export default {
    name: "SellPropertyPage",
    components: {
        PropertyList,
    },
    data() {
        return {
            savedProperty,
        }
    }
}
</script>

<style scoped>

.page-container{
    background-color: white ;
}
.button{
    display: flex;
    justify-content: center;
}
.add-property {
    background-color: white;
    width: 80px;
    border-radius: 6px;
    cursor: pointer;
   transition: 0.5s;
}

.add-property :hover{
    transform: scale(1.2);
    color: crimson;
}
</style>