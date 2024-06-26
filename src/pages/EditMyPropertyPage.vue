<template>
<div class="add-my-property">
        <h1>Upload Image</h1>
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
            <!-- Notification for successful upload -->
            <div v-if="uploadSuccess" class="alert alert-success">
            Upload successful.
            </div>

            <!-- Notification for failed update -->
            <div v-if="updateFailed" class="alert alert-danger">
            Update failed. Please try again.
            </div>
        </div>
    </div>
</div>

</template>

<script>
import UserService from '@/services/user.service';
export default {
    name: "EditMyPropertyPage",
    data() {
        return {
            images: [],
            isDragging: false,
            property: null,
            uploadSuccess: false,
            updateFailed: false,
        }
    },
    created() {
        this.fetchProperty();
        this.checkUserRole();
    },
    computed:{
        currentUser(){
            return this.$store.state.auth.user;
        }
    },
    methods: {
        checkUserRole() {
            console.log(this.currentUser); 
            if (!this.currentUser['roles'].includes('ROLE_AGENT')) {
                this.$router.push('/login');
            }
        },
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
                const response = await UserService.getEditProperty(this.$route.params.propertyId);
                this.property = response.data;
                console.log(this.property);
            } catch (error) {
                console.error(error);
            }
        },
        async deleteFile(fileId) {
            if (window.confirm("Are you sure you want to delete this image?")) {
                try {
                    await UserService.deleteFile(fileId);
                    // Remove the deleted image from the property.images array
                    this.property.files = this.property.files.filter(file => file.id !== fileId);
                } catch (error) {
                    console.error("Error deleting file", error);
                }
            }
        },
        async submitFiles() {
            try {
                const propertyId = this.property.id;
                if (this.images && this.images.length > 0) {
                    await UserService.editPropertyImg(propertyId, this.images);
                }
                this.uploadSuccess = true;
                setTimeout(() => {
                    this.$router.push('/my-property');
                }, 2000); 
            } catch (error) {
                console.error("Error updating property image", error);
                this.updateFailed = true;
            }
        },

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

.alert-success {
  color: #3c763d;
  background-color: #dff0d8;
  border-color: #d6e9c6;
}

.alert-danger {
  color: #a94442;
  background-color: #f2dede;
  border-color: #ebccd1;
}
</style>