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
            <router-link to="/dashboard"><v-list-item prepend-icon="mdi-chart-bar" title="Dashboard" value="dashboard"></v-list-item></router-link>
            <router-link to="/manageUser"><v-list-item prepend-icon="mdi-account" title="Manage Account" value="account"></v-list-item></router-link>
            <router-link to="/manageProperty"><v-list-item prepend-icon="mdi-home" title="Manage Property" value="property"></v-list-item></router-link>
            <router-link to="/approve"><v-list-item prepend-icon="mdi-message" title="Request" value="request"></v-list-item></router-link>
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
          <v-dialog v-model="dialog" max-width="500px">
            <v-card>
              <v-card-title class="text-h5">Approve?</v-card-title>
              <v-card-actions>
                <v-spacer></v-spacer>
                <v-btn color="blue-darken-1" variant="text" @click="close">Cancel</v-btn>
                <v-btn color="blue-darken-1" variant="text" @click="Approve">OK</v-btn>
                <v-spacer></v-spacer>
              </v-card-actions>
            </v-card>
          </v-dialog>
          <v-dialog v-model="dialogDelete" max-width="500px">
            <v-card>
              <v-card-title class="text-h5">Reject?</v-card-title>
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
          @click="editItem(item)"
        >
        <font-awesome-icon :icon="['far', 'thumbs-up']" />
        </v-icon>
        <v-icon
          size="small"
          @click="deleteItem(item)"
        >
        <font-awesome-icon :icon="['far', 'thumbs-down']" />
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
    </v-data-table>
    <v-data-table
      :headers="headers1"
      :items="usersWithRoleNames"
      :sort-by="[{ key: 'id', order: 'asc' }]"
      class="my-data-table no-space"
    >
      <template v-slot:top>
        <v-toolbar
          flat
        >
          <v-toolbar-title>User Table</v-toolbar-title>
          <v-divider
            class="mx-4"
            inset
            vertical
          ></v-divider>
          <v-spacer></v-spacer>
          <v-dialog v-model="dialog" max-width="500px">
            <v-card>
              <v-card-title class="text-h5">Approve?</v-card-title>
              <v-card-actions>
                <v-spacer></v-spacer>
                <v-btn color="blue-darken-1" variant="text" @click="close">Cancel</v-btn>
                <v-btn color="blue-darken-1" variant="text" @click="ApproveUser">OK</v-btn>
                <v-spacer></v-spacer>
              </v-card-actions>
            </v-card>
          </v-dialog>
          <v-dialog v-model="dialogDelete" max-width="500px">
            <v-card>
              <v-card-title class="text-h5">Reject?</v-card-title>
              <v-card-actions>
                <v-spacer></v-spacer>
                <v-btn color="blue-darken-1" variant="text" @click="closeDelete">Cancel</v-btn>
                <v-btn color="blue-darken-1" variant="text" @click="deleteItemConfirmUser">OK</v-btn>
                <v-spacer></v-spacer>
              </v-card-actions>
            </v-card>
          </v-dialog>
        </v-toolbar>
      </template>
      <template v-slot:[`item.actions`]="{ item }">
        <v-icon
          size="small"
          @click="editItem(item)"
        >
        <font-awesome-icon :icon="['far', 'thumbs-up']" />
        </v-icon>
        <v-icon
          size="small"
          @click="deleteItem(item)"
        >
        <font-awesome-icon :icon="['far', 'thumbs-down']" />
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
    </v-data-table>
    </v-main>
      </v-layout>
    </v-card>
    
  
  </template>
  
  <script>
  import axios from 'axios';
  export default{
      name: 'ApprovePage',
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
      { title: 'Status', key: 'status', align: 'start'},
      { title: 'Actions', key: 'actions', sortable: false },
      ],
      headers1: [
        { title: 'ID', key: 'id', align: 'start' },
        { title: 'First Name', key: 'firstName', align: 'start' },
        { title: 'Last Name', key: 'lastName', align: 'start' },
        { title: 'Email', key: 'email', align: 'start' },
        { title: 'Address', key: 'address', align: 'start' },
        { title: 'Eircode', key: 'eircode', align: 'start' },
        { title: 'Nationality', key: 'nationality', align: 'start' },
        { title: 'Role', key: 'roles', align: 'start' },
        { title: 'Status', key: 'status', align: 'start' },
        { title: 'Actions', key: 'actions', sortable: false },
        ],
      users: [],
      properties: [],
    }),
  
    computed: {
      usersWithRoleNames() {
        return this.users.map(user => ({
          ...user,
          roles: (user.roles || []).map(roles => roles.name).join(', ')
        }));
      },
    },
  
    watch: {
      dialog (val) {
        val || this.close()
      },
      dialogDelete (val) {
        val || this.closeDelete()
      },
    },
  
    created () {
      this.initialize()
    },
  
    methods: {
      async initialize () {
        try {
          const propertiesResponse = await axios.get('http://localhost:8080/api/properties/pending');
          this.properties = propertiesResponse.data;

          const usersResponse = await axios.get('http://localhost:8080/api/users/pending'); // replace with your API endpoint
          this.users = usersResponse.data;
        } catch (error) {
          console.error(error);
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
      async Approve () {
        try {
            await axios.put(`http://localhost:8080/api/approve/${this.editedItem.id}`);
            this.closeDelete();
            this.initialize();
        } catch (error) {
            console.error(error);
        }
      },
      async ApproveUser () {
        try {
            await axios.put(`http://localhost:8080/api/approve/user/${this.editedItem.id}`);
            this.closeDelete();
            this.initialize();
        } catch (error) {
            console.error(error);
        }
      },
      
      async deleteItemConfirm () {
        try {
            await axios.put(`http://localhost:8080/api/reject/${this.editedItem.id}`);
            this.closeDelete();
            this.initialize();
        } catch (error) {
            console.error(error);
        } 
      },

      async deleteItemConfirmUser () {
        try {
            await axios.put(`http://localhost:8080/api/reject/user/${this.editedItem.id}`);
            this.closeDelete();
            this.initialize();
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
        this.dialog = false
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