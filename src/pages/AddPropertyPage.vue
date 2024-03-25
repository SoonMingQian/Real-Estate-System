<template>
    <div class="other-container">
        <form @submit.prevent="handleSubmit" class="other">
            <h1>Add Property</h1>
            <p>Sale/Rent <span class="required">*</span></p>
            <select class="other-input" v-model="saleType">
                <option value="sale">Sale</option>
                <option value="rent">Rent</option>
            </select>

            <p>Name <span class="required">*</span></p>
            <input class="other-input" placeholder="House name" v-model="propertyName" required><br>
            <p>Address <span class="required">*</span> </p>
            <input class="other-input" placeholder="House address" v-model="propertyAddress" required><br>
            <p>Price <span class="required">*</span> </p>
            <input class="other-input" placeholder="Price" type="number" v-model="price" required><br>
            <p>Number of Bedroom <span class="required">*</span> </p>
            <input class="other-input" placeholder="Number of Bedroom" v-model="numOfBed" type="number" min="0" required><br>
            <p>Number of Bathroom <span class="required">*</span> </p>
            <input class="other-input" placeholder="Number of Bathroom" v-model="numOfBath" type="number" min="0" required><br>
            <p>Sqft <span class="required">*</span> </p>
            <input class="other-input" placeholder="Sqft" type="number" v-model="sqft" min="0" required><br>
            <p>Description </p>
            <p style="white-space: pre-line;"></p>
            <textarea v-model="description" placeholder="Description"></textarea>
            <div class="unit-feature">
                <p>Unit Features </p>
                <div v-for="feature in features" :key="feature.id">
                    <input type="checkbox" :id="feature.name" :value="feature.id" v-model="facilities">
                    <label :for="feature.name">{{ feature.name }}</label>
                </div>
            </div>
            <div class="button-container">
                <button type="submit">Next</button>
            </div>
            
        </form>
        
    </div>
</template>

<script>
import axios from 'axios';

export default{
    name: "AddPropertyPage",
    data(){
        return{
            saleType: "sale",
            propertyName: "",
            propertyAddress: "",
            price: "",
            numOfBed: "",
            numOfBath: "",
            sqft: "",
            description: "",
            features:[
                {id: 1, name: "Air-Conditioning"},
                {id: 2, name: "Balcony"},
                {id: 3, name: "WIFI"},
                {id: 4, name: "Washing Machine / Dryer"},
                {id: 5, name: "Oven / Microwave"},
                {id: 6, name: "Water Heater"},
                {id: 7, name: "Fridge"},
                {id: 8, name: "Furnished"}
            ],
            facilities: [],
        }
    },
    methods:{
        handleSubmit(){
            const data = {
                saleType: this.saleType,
                propertyName: this.propertyName,
                propertyAddress: this.propertyAddress,
                price: this.price,
                numOfBed: this.numOfBed,
                numOfBath: this.numOfBath,
                sqft: this.sqft,
                description: this.description,
                facilities: this.facilities.map(id => ({ id: id }))
            };

            axios.post("http://localhost:8080/add-property", data)
                .then(response => {
                    console.log(response.data);
                    this.$router.push({ path: '/my-property/add-property/${response.data.id}' });
                })
                .catch(error => {
                    console.log(error);
                });
        }
    }
}

</script>

<style scoped>
.other-container {
    display: flex;
    justify-content: center;
    align-items: center;
    height: 100%;
    padding-top: 30px;
    padding-bottom: 70px;
}


.other {
    width: 70%;
    /* Adjust as needed */
    padding: 20px;
    height: 151vh;
    border-radius: 8px;
    box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);    
}

.other-input{
    width: 60%;
    height: 30px;
}

.other textarea{
    width: 75%;
    height: 120px;
}

.button-container {
    display: flex;
    justify-content: center;
    align-items: center;
}

.other button {
    font-size: 1rem;
    width: 150px;
    height: 30px;
    margin-top: 30px; /* adjust as needed */
    margin-bottom: 20px;
    display: inline-block;
    border-radius: 20px;
    outline: none;
    border: none;
    color: white;
    background: crimson;
    cursor: pointer;
}



.other button :hover{
    background: black;
}

.required {
    color: red;
}

</style>

