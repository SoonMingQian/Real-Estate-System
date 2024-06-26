<template>
    <div class="add-my-property">
        <h1>Upload Image</h1>

        <div class="add-container">
            <div class="card">
                <div class="top">
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

            </div>

            <button type="button" @click="submitFiles">Submit</button>
        </div>
        <!-- Notification for successful upload -->
        <div v-if="uploadSuccess" class="alert alert-success">
        Upload successful.
        </div>

        <!-- Notification for failed update -->
        <div v-if="updateFailed" class="alert alert-danger">
        Update failed. Please try again.
        </div>
    </div>
</template>

<script>
import UserService from '@/services/user.service';
export default {
    name: "AddMyPropertyPage",
    data() {
        return {
            images: [],
            isDragging: false,
            propertyId: null,
            uploadSuccess: false,
            updateFailed: false,
        }
    },
    computed:{
        currentUser(){
            return this.$store.state.auth.user;
        }
    },
    created() {
        this.propertyId = this.$route.params.propertyId;
        this.checkUserRole();
    },
    methods: {
        checkUserRole() {
            console.log(this.currentUser); 
            if (!this.currentUser['roles'].includes('ROLE_AGENT')) {
                this.$router.push('/login');
          }
        },
        // Method to trigger the file input click event, opening the file selection dialog
        selectFiles() {
            this.$refs.fileInput.click();
        },
        // It checks if any files were selected and if not, it returns without doing anything
        // It then loops through the selected files, checking if each file is an image and if it's not already in the images array
        // If both checks pass, the file is added to the images array
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
        // Method to delete an image from the images array based on its index
        deleteImage(index) {
            this.images.splice(index, 1);
        },
        // It prevents the default behavior, sets isDragging to true, and sets the dropEffect to 'copy'
        onDragover(event) {
            event.preventDefault();
            this.isDragging = true
            event.dataTransfer.dropEffect = "copy"
        },
        // It prevents the default behavior and sets isDragging to false
        onDragleave(event) {
            event.preventDefault();
            this.isDragging = false;
        },
        // Method to handle the drop event
        // It prevents the default behavior, sets isDragging to false, and gets the files from the event dataTransfer
        // It then loops through the files, checking if each file is an image and if it's not already in the images array
        // If both checks pass, the file is added to the images array
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
        // Method to create a URL for a file using the URL.createObjectURL method
        createObjectURL(file) {
            return URL.createObjectURL(file);
        },
        // It calls the addPropertyImg method of the UserService, passing the propertyId and the images array
        submitFiles() {
            UserService.addPropertyImg(this.propertyId, this.images)
                .then(response => {
                    console.log(response.data);
                    this.uploadSuccess = true;
                    setTimeout(() => {
                        this.$router.push('/my-property');
                    }, 2000); 
                    
                })
                .catch(error => {
                    console.log(error);
                    this.updateFailed = true;
                });
        },
    }
}
</script>

<style scoped>


h1 {
    text-align: center;
    margin-bottom: 20px;

}

.add-container {
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

.alert {
  padding: 15px;
  margin-bottom: 20px;
  border: 1px solid transparent;
  border-radius: 4px;
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