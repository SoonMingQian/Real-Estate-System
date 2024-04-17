<template>
  
    <v-card>
      <v-layout>
        <v-navigation-drawer
          expand-on-hover
          rail
        >
          <v-list>
            <v-list-item
              prepend-icon="mdi-account"
              title="Admin"
            ></v-list-item>
          </v-list>
  
          <v-divider></v-divider>
  
          <v-list density="compact" nav>
            <router-link :style="{ color: 'inherit', textDecoration: 'none' }" to="/dashboard"><v-list-item prepend-icon="mdi-chart-bar" title="Dashboard" value="dashboard"></v-list-item></router-link>
            <router-link :style="{ color: 'inherit', textDecoration: 'none' }" to="/manageUser"><v-list-item prepend-icon="mdi-account" title="Manage Account" value="account"></v-list-item></router-link>
            <router-link to="/manageProperty"><v-list-item prepend-icon="mdi-home" title="Manage Property" value="property"></v-list-item></router-link>
            <router-link :style="{ color: 'inherit', textDecoration: 'none' }" to="/approve"><v-list-item prepend-icon="mdi-message" title="Request" value="request"></v-list-item></router-link>
            <div v-if="currentUser"><v-list-item prepend-icon="mdi-logout" @click.prevent="logOut" title="Logout" value="logout"></v-list-item></div>
        </v-list>
        </v-navigation-drawer>
  
        <v-main style="height: auto"><v-data-table
      :headers="headers"
      :items="properties"
      :sort-by="[{ key: 'id', order: 'asc' }]"
      class="my-data-table no-space"
    >
   
      <template v-slot:[`item.image`]="{ item }">
        <v-img v-for="(file, index) in item.files" :key="index" :src="'data:' + file.contentType + ';base64,' + file.fileData" ></v-img>
      </template>
      <template v-slot:[`item.facilities`]="{ item }">
        {{ item.facilities.map(facility => facility.name).join(', ') }}
      </template>
      <template v-slot:top>
        <v-toolbar
          flat
        >
          <v-toolbar-title>Property Table</v-toolbar-title>
          <v-divider
            class="mx-4"
            inset
            vertical
          ></v-divider>
          <v-spacer></v-spacer>
          <v-dialog v-model="dialogDelete" max-width="500px">
            <v-card>
              <v-card-title class="text-h5">Are you sure you want to delete this item?</v-card-title>
              <v-card-actions>
                <v-spacer></v-spacer>
                <v-btn color="blue-darken-1" variant="text" @click="closeDelete">Cancel</v-btn>
                <v-btn color="blue-darken-1" variant="text" @click="deleteItemConfirm">OK</v-btn>
                <v-spacer></v-spacer>
              </v-card-actions>
            </v-card>
          </v-dialog>
        </v-toolbar>
      </template>
      <template v-slot:[`item.actions`]="{ item }">
        <v-icon
          size="small"
          @click="deleteItem(item)"
        >
        <font-awesome-icon :icon="['fas', 'eraser']" />
        </v-icon>
      </template>
      <template v-slot:no-data>
        <v-btn
          color="primary"
          @click="initialize"
        >
          Reset
        </v-btn>
      </template>
    </v-data-table></v-main>
      </v-layout>
    </v-card>
    
  
  </template>
  
  <script>
  import axios from 'axios';
  export default{
      name: 'PropertyManagementPage',
      beforeCreate() {
        this.$root.showNavBar = false;
      },
      beforeUnmount() {
          this.$root.showNavBar = true;      
      },
      data: () => ({
      dialog: false,
      dialogDelete: false,
      headers: [
      { title: 'ID', key: 'id', align: 'start' },
      { title: 'Image ', key: 'image', align: 'start' },
      { title: 'Property Name', key: 'propertyName', align: 'start' },
      { title: 'Address', key: 'propertyAddress', align: 'start' },
      { title: 'Num Of Bath', key: 'numOfBath', align: 'start' },
      { title: 'Num Of Bed', key: 'numOfBed', align: 'start' },
      { title: 'Description', key: 'description', align: 'start' },
      { title: 'Sqft', key: 'sqft', align: 'start' },
      { title: 'Price', key: 'price', align: 'start' },
      { title: 'Property Type', key: 'propertyType', align: 'start' },
      { title: 'Sale Type', key: 'saleType', align: 'start' },
      { title: 'Facilities', key: 'facilities', align: 'start'},
      { title: 'Actions', key: 'actions', sortable: false },
      ],
      properties: [],
      editedItem: {
        propertyName: '',
        propertyAddress: '',
        numOfBath: '',
        numOfBed: '',
        description: '',
        sqft: '',
        price: '',
        propertyType: '',
        saleType: '',
        facilities: [],
      },
    }),
 
    watch: {
      dialog (val) {
        val || this.close()
      },
      dialogDelete (val) {
        val || this.closeDelete()
      },
    },
  
    created () {
      this.checkUserRole()
      this.initialize()
    },
    computed: {
      currentUser(){
        return this.$store.state.auth.user;
      },
    },
  
    methods: {
      checkUserRole() {
            console.log(this.currentUser); 
            if (!this.currentUser['roles'].includes('ROLE_ADMIN')) {
                this.$router.push('/login');
          }
      },
      logOut() {
            this.$store.dispatch('auth/logout');
            this.$router.push('/login');
      },
      async initialize () {
        try {
          const response = await axios.get('http://localhost:8080/api/properties')
          this.properties = response.data
          console.log(this.users)
        } catch (error) {
          console.error(error)
        }
      },
  
      editItem (item) {
        this.editedItem = Object.assign({}, item)
        this.dialog = true
      },
  
      deleteItem (item) {
        this.editedItem = Object.assign({}, item)
        this.dialogDelete = true
      },
  
      async deleteItemConfirm () {
      try {
        await axios.delete(`http://localhost:8080/api/deleteproperty/${this.editedItem.id}`);
        this.closeDelete();
      } catch (error) {
        console.error(error);
      }
    },
  
      close () {
        this.dialog = false
        this.$nextTick(() => {
          this.editedItem = Object.assign({}, this.defaultItem)
        })
      },
  
      closeDelete () {
        this.dialogDelete = false
        this.$nextTick(() => {
          this.editedItem = Object.assign({}, this.defaultItem)
        })
      },
      async update(){
        
      this.close()
      },
    },
      
  }
  </script>

<style scoped>
.router-link-active {
  text-decoration: none; /* Remove underline */
  color: crimson; /* Inherit color */
}
</style>