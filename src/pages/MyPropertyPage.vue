<template>
    <ProfileNavBar />
    
    <div class="page-container">
        <PropertyList :properties="properties" />
        <div class="button">
        <button class="add-property">
            <router-link to="my-property/add-property"><font-awesome-icon :icon="['fas', 'plus']" /></router-link>
        </button>
    </div>
</div>
</template>

<script>
import axios from 'axios';
import ProfileNavBar from '../components/ProfileNavBar.vue';
import PropertyList from '../components/PropertyList.vue';
import '../save-page.css'
export default {
    name: "SaleRentPage",
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
            properties: [],
        }
    },
    created() {
        this.fetchProperties();
    },
    methods: {
        async fetchProperties() {
            try {
                const response = await axios.get('http://localhost:8080/properties/sale');
                this.properties = response.data;
                console.log(this.properties);
            } catch (error) {
                console.error(error);
            }
        },
       
    }
}
</script>

<style scoped>

.page-container{
    height: 100%;
    background-color: white;
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
    color: crimson;
}


</style>