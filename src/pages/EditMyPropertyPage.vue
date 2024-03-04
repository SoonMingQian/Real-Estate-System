<template>
    <div class="edit-my-property">
        <h1>Edit {{ house.houseName }}</h1>

        <div class="edit-container">
            <div class="card">
                <div class="top">
                    <div class="container">
                        <div class="image" v-for="(image, index) in house.images" :key="index">
                            <span class="delete" @click="deleteImage">&times;</span>
                            <img :src="image.url" />
                        </div>
                    </div>
                    <p>Drag & drop images</p>
                </div>
                <div class="drag-area" @dragover.prevent="onDragover" @dragleave.prevent="onDragleave"
                    @drop.prevent="onDrop">
                    <span v-if="!isDragging">Drag & drop image here or
                        <span class="select" role="button" @click="selectFiles">Choose</span>
                    </span>
                    <div v-else class="select">Drop images here</div>
                    <input name="file" type="file" class="file" ref="fileInput" multiple @change="onFileSelect">
                </div>
                <div class="container">
                    <div class="image" v-for="(image, index) in images" :key="index">

                        <span class="delete" @click="deleteImage">&times;</span>

                        <img :src="image.url" />
                    </div>
                </div>
                
            </div>
            <div class="other-container">
                <div class="other">
                    <p>Name: {{ house.houseName }}</p>
                    <input class="other-input" v-model="house.houseName" placeholder="House name" required>
                    <p>Address: {{ house.houseAddress }}</p>
                    <input class="other-input" v-model="house.houseAddress" placeholder="House address" required>
                    <p>Price: {{ house.price }}</p>
                    <input class="other-input" v-model="house.price" placeholder="Price" type="number" required>
                    <p>Number of Bedroom: {{ house.numOfBedroom }}</p>
                    <input class="other-input" v-model="house.numOfBedroom" placeholder="Number of Bedroom" type="number" min="0" required>
                    <p>Number of Bathroom: {{ house.numOfBathroom }}</p>
                    <input class="other-input" v-model="house.numOfBathroom" placeholder="Number of Bathroom" type="number" min="0" required>
                    <p>Sqft: {{ house.sqft }}</p>
                    <input class="other-input" v-model="house.sqft" placeholder="Sqft" type="number" min="0" required>
                    <p>Description</p>
                    <p style="white-space: pre-line;">{{ house.description }}</p>
                    <textarea v-model="house.description" placeholder="Description" required></textarea>
                    <div class="unit-feature">
                        <p>Unit Features: {{ house.features }}</p>
                        <input type="checkbox" id="air-conditioning" value="Air-Conditioning" v-model="house.features">
                        <label for="air-conditioning">Air-Conditioning</label><br>
                        <input type="checkbox" id="balcony" value="Balcony" v-model="house.features">
                        <label for="balcony">Balcony</label><br>
                        <input type="checkbox" id="wifi" value="WIFI" v-model="house.features">
                        <label for="wifi">WIFI</label><br>
                        <input type="checkbox" id="washing-machine-dryer" value="Washing Machine / Dryer"
                            v-model="house.features">
                        <label for="washing-machine-dryer">Washing Machine / Dryer</label><br>
                        <input type="checkbox" id="oven-microwave" value="Oven / Microwave" v-model="house.features">
                        <label for="oven-microwave">Oven / Microwave</label><br>
                        <input type="checkbox" id="water-heater" value="Water Heater" v-model="house.features">
                        <label for="water-heater">Water Heater</label><br>
                        <input type="checkbox" id="fridge" value="Fridge" v-model="house.features">
                        <label for="fridge">Fridge</label><br>
                        <input type="checkbox" id="furnished" value="Furnished" v-model="house.features">
                        <label for="furnished">Furnished</label><br>
                    </div>
                </div>
            </div>
            <button type="button">Save Changes</button>
        </div>
    </div>
</template>

<script>
import { houses } from '../temp-data'
export default {
    name: "EditMyPropertyPage",
    data() {
        return {
            house: houses.find(house => house.id == this.$route.params.propertyId),
            images: [],
            isDragging: false,

        }
    },
    methods: {
        selectFiles() {
            this.$refs.fileInput.click();
        },
        onFileSelect(event) {
            const files = event.target.files;
            if (files.length === 0) {
                return;
            }
            for (let i = 0; i < files.length; i++) {
                if (files[i].type.split("/")[0] != "image")
                    continue;
                if (!this.images.some((e) => e.name === files[i].name)) {
                    this.images.push({ name: files[i].name, url: URL.createObjectURL(files[i]) })
                }
            }
        },
        deleteImage(index) {
            this.images.splice(index, 1);
        },
        onDragover(event) {
            event.preventDefault();
            this.isDragging = true
            event.dataTransfer.dropEffect = "copy"
        },
        onDragleave(event) {
            event.preventDefault();
            this.isDragging = false;
        },
        onDrop(event) {
            event.preventDefault();
            this.isDragging = false;
            const files = event.dataTransfer.files;
            for (let i = 0; i < files.length; i++) {
                if (files[i].type.split("/")[0] != "image")
                    continue;
                if (!this.images.some((e) => e.name === files[i].name)) {
                    this.images.push({ name: files[i].name, url: URL.createObjectURL(files[i]) })
                }
            }
        }
    }
}
</script>

<style scoped>
.edit-container {
    width: 70%;
    margin: 0 auto;

}

.card {
    width: 100%;
    padding: 10px;
    box-shadow: 0 0 5px white;
    border-radius: 5px;
    overflow: hidden;
}

.card .top {
    text-align: center;
}

.card p {
    font-weight: bold;
    color: crimson;
}

button {
    outline: 0;
    border: 0;
    color: white;
    border-radius: 4px;
    font-weight: 400;
    padding: 9px 13px;
    margin-bottom: 30px;
    width: 100%;
    background: crimson;
}

.card .drag-area {
    height: 150px;
    border-radius: 5px;
    border: 2px dashed crimson;
    background: white;
    color: crimson;
    display: flex;
    justify-content: center;
    align-items: center;
    user-select: center;
    -webkit-user-select: none;
    margin-top: 10px;
}

.card .drag-area .visble {
    font-size: 18px;
}

.card .select {
    color: blue;
    margin-left: 5px;
    cursor: pointer;
    transition: 0.4s;
}

.card .select:hover {
    opacity: 0.6;
}

.card .container {
    width: 100%;
    height: auto;
    display: flex;
    justify-content: flex-start;
    align-items: flex-start;
    flex-wrap: wrap;
    position: relative;
    margin-bottom: 8px;
    padding-top: 15px;
}

.card .container .image {
    width: 75px;
    margin-right: 5px;
    height: 75px;
    position: relative;
    margin-bottom: 8px;
}

.card .top .image {
    width: 75px;
    margin-right: 5px;
    height: 75px;
    position: relative;
    margin-bottom: 8px;
}

.card .top .image img {
    width: 100%;
    height: 100%;
    border-radius: 5px;
}

.card .top .image span {
    position: absolute;
    top: -2px;
    right: 9px;
    font-size: 20px;
    cursor: pointer;
}

.card .container .image img {
    width: 100%;
    height: 100%;
    border-radius: 5px;
}

.card .container .image span {
    position: absolute;
    top: -2px;
    right: 9px;
    font-size: 20px;
    cursor: pointer;
}

.card input,
.card .drag-area .on-drop,
.card .drag-area.dragover .visible {
    display: none;
}

.delete {
    z-index: 999;
    color: crimson;
}

.other-container {
    display: flex;
    justify-content: center;
    align-items: center;
    height: auto;
    padding-bottom: 30px;
}


.other {
    width: 100%;
    /* Adjust as needed */
    padding: 20px;
    background-color: #f0f0f0;
    border-radius: 8px;
    box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
}

.other-input{
    width: 60%;
    height: 30px;
    border-radius: 3px;
}

.other textarea{
    width: 75%;
    height: 120px;
}


</style>