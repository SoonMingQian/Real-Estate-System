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
          <router-link to="/manageUser"><v-list-item prepend-icon="mdi-account" title="Manage Account" value="account"></v-list-item></router-link>
          <router-link :style="{ color: 'inherit', textDecoration: 'none' }" to="/manageProperty"><v-list-item prepend-icon="mdi-home" title="Manage Property" value="property"></v-list-item></router-link>
          <router-link :style="{ color: 'inherit', textDecoration: 'none' }" to="/approve"><v-list-item prepend-icon="mdi-message" title="Request" value="request"></v-list-item></router-link>
          <div v-if="currentUser"><v-list-item prepend-icon="mdi-logout" @click.prevent="logOut" title="Logout" value="logout"></v-list-item></div>
        </v-list>
      </v-navigation-drawer>

      <v-main style="height: auto"><v-data-table
    :headers="headers"
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
        <v-dialog
          v-model="dialog"
          max-width="500px"
        >
          <template v-slot:activator="{ props }">
            <v-btn
              class="mb-2"
              color="primary"
              dark
              v-bind="props"
              @click="isAdding = true; isEditing = false"
            >
              New Account
            </v-btn>
          </template>
          <v-card>
            <v-card-title>
              <span class="text-h5">{{ formTitle }}</span>
            </v-card-title>

            <v-card-text>
              <v-container>
                <v-row>
                  <v-col
                    cols="12"
                    md="4"
                    sm="6"
                  >
                  <label v-if="isEditing">ID: {{ editedItem.id }}</label>
                  </v-col>
                  
                  <v-col
                    cols="12"
                    md="4"
                    sm="6"
                  >
                    <v-text-field
                      v-model="editedItem.firstName"
                      label="First Name"
                    ></v-text-field>
                  </v-col>
                  <v-col
                    cols="12"
                    md="4"
                    sm="6"
                  >
                    <v-text-field    
                      v-model="editedItem.lastName"
                      label="Last Name"
                    ></v-text-field>
                  </v-col>
                  <v-col
                    cols="12"
                    md="4"
                    sm="6"
                  >
                    <v-text-field
                      v-if="isEditing"
                      v-model="editedItem.address"
                      label="Address"
                    ></v-text-field>
                  </v-col>
                  <v-col
                    cols="12"
                    md="4"
                    sm="6"
                  >
                    <v-text-field
                      v-if="isEditing"
                      v-model="editedItem.eircode"
                      label="Eircode"
                    ></v-text-field>
                  </v-col>
                  <v-col
                    cols="12"
                    md="4"
                    sm="6"
                  >
                    <v-text-field
                      v-if="isEditing"
                      v-model="editedItem.nationality"
                      label="Nationality"
                    ></v-text-field>
                  </v-col>
                  <v-col
                    cols="12"
                    md="4"
                    sm="6"
                  >
                  <v-text-field
                      v-if="isAdding"
                      v-model="editedItem.email"
                      label="Email"
                      type = "email"
                    ></v-text-field>
                  </v-col>
                  <v-col
                    cols="12"
                    md="4"
                    sm="6"
                  >
                  <v-text-field
                      v-if="isAdding"
                      v-model="editedItem.password"
                      label="Password"
                      type = "password"
                    ></v-text-field>
                  </v-col>
                  <v-col
                    cols="12"
                    md="4"
                    sm="6"
                  >
                  <v-select
                    v-if="isAdding"
                    v-model="editedItem.role"
                    :items="role"
                    label="Role"
                    multiple
                  ></v-select>
                  </v-col>
                </v-row>
              </v-container>
            </v-card-text>

            <v-card-actions>
              <v-spacer></v-spacer>
              <v-btn
                color="blue-darken-1"
                variant="text"
                @click="close"
              >
                Cancel
              </v-btn>
              <v-btn
                color="blue-darken-1"
                variant="text"
                @click="save"
              >
                Save
              </v-btn>
            </v-card-actions>
          </v-card>
        </v-dialog>
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
        class="me-2"
        size="small"
        @click="editItem(item); isEditing = true; isAdding = false"
      >
      <font-awesome-icon :icon="['far', 'pen-to-square']" />
      </v-icon>
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
    name: 'UserManagementPage',
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
    { title: 'First Name', key: 'firstName', align: 'start' },
    { title: 'Last Name', key: 'lastName', align: 'start' },
    { title: 'Email', key: 'email', align: 'start' },
    { title: 'Address', key: 'address', align: 'start' },
    { title: 'Eircode', key: 'eircode', align: 'start' },
    { title: 'Nationality', key: 'nationality', align: 'start' },
    { title: 'Role', key: 'roles', align: 'start' },
    { title: 'Actions', key: 'actions', sortable: false },
    ],
    users: [],
    editedItem: {
      firstName: "",
      lastName: "",
      nationality: "",
      address: "",
      eircode: "",
      role: [],
      email: "",
      password: "",
      properties: [],
    },
    defaultItem: {
      firstName: '',
      lastName: '',
      email: '',
      address: '',
      eircode: '',
      nationality: '',
      role: [],
    },
    isAdding: false,
    isEditing: false,
    role: ['admin', 'user', 'agent']
  }),

  computed: {
    currentUser(){
      return this.$store.state.auth.user;
    },
    
    formTitle () {
      return this.editedItem.id ? 'Edit User' : 'New User'
    },
    usersWithRoleNames() {
    return this.users.map(user => ({
      ...user,
      roles: (user.roles || []).map(roles => roles.name).join(', ')
    }));
  }
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
    this.checkUserRole()
    this.initialize()
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
      if (!this.currentUser && !this.currentUser['roles'].includes('ROLE_ADMIN')) {
          this.$router.push('/login');
          return;
      }
      try {
        const response = await axios.get('https://real-estate-system-q28g.onrender.com/api/users')
        this.users = response.data
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
  const propertyIds = this.editedItem.properties.map(property => property.id);
  try {
    for (const propertyId of propertyIds) {
      await axios.delete(`https://real-estate-system-q28g.onrender.com/api/deleteproperty/${propertyId}`);
    }
    await axios.delete(`https://real-estate-system-q28g.onrender.com/api/deleteuser/${this.editedItem.id}`);
    this.users = this.users.filter(user => user.id !== this.editedItem.id);
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

    async save () {
      if (this.editedItem.id) {
        try {
        const userToUpdate = {
          firstName: this.editedItem.firstName,
          lastName: this.editedItem.lastName,
          nationality: this.editedItem.nationality,
          address: this.editedItem.address,
          eircode: this.editedItem.eircode
        }
      const response = await axios.put(`https://real-estate-system-q28g.onrender.com/api/updateuser/${this.editedItem.id}`, userToUpdate)
      this.users = this.users.map(user => user.id === this.editedItem.id ? response.data : user)
    } catch (error) {
      console.error(error)
    }
      } else {
        try {
          const userToAdd = {
          firstName: this.editedItem.firstName,
          lastName: this.editedItem.lastName,
          email: this.editedItem.email,
          password: this.editedItem.password,
          role: this.editedItem.role,
          }
          const response = await axios.post('https://real-estate-system-q28g.onrender.com/api/auth/signup', userToAdd)
          this.users.push(response.data)
        } catch (error) {
          console.error(error)
        }
      }
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