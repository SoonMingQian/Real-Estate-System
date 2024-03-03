<template>
    <div class="filter">
    <button @click="show = !show" class="filter-button">Filter</button>
    <div v-if="show" class="dropdown-list">
        <h6>Status</h6>
        <ul>

        </ul>
    </div>
</div>

</template>

<script>
export default {
    name: "PropertyFilter",
    data(){
        return{
            show: false
        }
    }
}
</script>

<style scoped>
.filter{
    position: relative;
    display: flex;
    align-items: center;
    padding-top: 50px;
}

.filter-button{
    display: flex;
    align-items: center;
    justify-content: center;
}

.dropdown-list{
    position: absolute;
    top: 12;
    right: 0;
    z-index: 10;
    width: 48px;
    background-color: crimson;
}
</style>