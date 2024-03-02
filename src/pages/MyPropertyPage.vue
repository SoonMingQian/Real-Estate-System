<template>
    <ProfileNavBar />
    <div class="salerent-title">
        <h3><font-awesome-icon :icon="['fas', 'house']" /> Sale/Rent List</h3>
    </div>
    <div class="page-container">
        <PropertyList :savedProperty="savedProperty" />
        <div class="button">
        <button class="add-property"><font-awesome-icon :icon="['fas', 'plus']" /></button>
    </div>
</div>
</template>

<script>
import ProfileNavBar from '../components/ProfileNavBar.vue';
import PropertyList from '../components/PropertyList.vue';
import { savedProperty } from '@/temp-data';
import '../save-page.css'
export default {
    name: "SellPropertyPage",
    components: {
        PropertyList,
        ProfileNavBar,
    },
    style:{
        styleObject:{
            color: 'red',
        }
    },
    data() {
        return {
            savedProperty,
            isActive: true,
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