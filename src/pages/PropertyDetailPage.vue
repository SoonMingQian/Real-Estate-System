<template>
    <section class="container">
        <div class="slider-wrapper">
            <div class="slider">
                <img id="image1" :src="house.image" />
                <img id="image2" :src="house.image" />
                <img id="image3" :src="house.image" />
            </div>
            <div class="slider-nav">
                <a href="#image1"></a>
                <a href="#image2"></a>
                <a href="#image3"></a>
            </div>
        </div>
    </section>
</template>

<script>
import { houses } from '../temp-data'
export default{
    name: "PropertyDetailPage",
    data(){
        return{
            house: houses.find(house => house.id == this.$route.params.propertyId)
        }
    }
}
</script>