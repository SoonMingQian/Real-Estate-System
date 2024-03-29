<template>
   
            <div class="edit-container">
                <div class="card">
                    <div class="top">
                        <div class="container">
                            <div v-if="property && property.files">
                            <div class="image" v-for="(files, index) in property.files" :key="index">
                                <span class="delete" @click="deleteFile(files.id)">&times;</span>
                                <img :src="'data:' + files.contentType + ';base64,' + files.fileData" />
                            </div>
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
                            <img :src="createObjectURL(image.file)" />
                        </div>
                    </div>
                    <button type="button" @click="submitFiles">Submit</button>
                </div>
            </div>

           
</template>

<script>
import axios from 'axios';
export default {
    name: "EditMyPropertyPage",
    data() {
        return {
            images: [],
            isDragging: false,
            property: null, 
        }
    },
    created() {
        this.fetchProperty();
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
                    this.images.push({ name: files[i].name, file: files[i] })
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
                    this.images.push({ name: files[i].name, file: files[i] })
                }
            }
        },
        createObjectURL(file) {
            return URL.createObjectURL(file);
        },
        async fetchProperty() {
            const propertyId = this.$route.params.propertyId;
            console.log(propertyId);
            try {
                const response = await axios.get(`http://localhost:8080/property/${propertyId}`);
                this.property = response.data;
                console.log(this.property);
            } catch (error) {
                console.error("Error fetching property", error);
            }
        },
        async deleteFile(fileId) {
            if(window.confirm("Are you sure you want to delete this image?")){
            try {
                await axios.delete(`http://localhost:8080/file/${fileId}`);
                // Remove the deleted image from the property.images array
                this.property.files = this.property.files.filter(file => file.id !== fileId);
            } catch (error) {
                console.error("Error deleting file", error);
            }
        }
        },
        submitFiles() {
            const formData = new FormData();
            for (let i = 0; i < this.images.length; i++) {
                formData.append('files', this.images[i].file);
            }

            axios.post(`http://localhost:8080/add-property/${this.property.id}/upload`, formData, {
                headers: {
                    'Content-Type': 'multipart/form-data'
                }
            })
                .then(response => {
                    console.log(response.data);
                    this.$router.push({ path: '/my-property' });
                })
                .catch(error => {
                    console.log(error);
                });

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
    cursor: pointer;
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
</style>