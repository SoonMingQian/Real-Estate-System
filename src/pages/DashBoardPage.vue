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
          <router-link :style="{ color: 'inherit', textDecoration: 'none' }" to="/manageUser"><v-list-item prepend-icon="mdi-account" title="Manage Account" value="account"></v-list-item></router-link>
          <router-link :style="{ color: 'inherit', textDecoration: 'none' }" to="/manageProperty"><v-list-item prepend-icon="mdi-home" title="Manage Property" value="property"></v-list-item></router-link>
          <router-link :style="{ color: 'inherit', textDecoration: 'none' }" to="/approve"><v-list-item prepend-icon="mdi-message" title="Request" value="request"></v-list-item></router-link>
          <div v-if="currentUser"><v-list-item prepend-icon="mdi-logout" @click.prevent="logOut" title="Logout" value="logout"></v-list-item></div>
        </v-list>
      </v-navigation-drawer>

      <v-main style="height: auto">
        <h2 style="display: flex; justify-content: center; align-items: center;">Number of property based on the sale type</h2>
        <div style="display: flex; justify-content: center; align-items: center; height: 350px;">
         
        <Bar
          id="my-chart-id"
          :options="chartOptions"
          :data="chartData"
        />
  </div>
  <br>
  <h2 style="display: flex; justify-content: center; align-items: center;">Number of property based on the house type</h2>
  <div style="display: flex; justify-content: center; align-items: center; height: 350px;">
    
    <Bar
      id="my-chart-id"
      :options="chartOptions"
      :data="chartData1"
    />
  </div>
  </v-main>
    </v-layout>
  </v-card>
    
</template>
  
<script>
import { Bar } from 'vue-chartjs'
import { Chart as ChartJS, Title, Tooltip, Legend, BarElement, CategoryScale, LinearScale, ArcElement } from 'chart.js'
import axios from 'axios'
ChartJS.register(Title, Tooltip, Legend, BarElement, CategoryScale, LinearScale, ArcElement)
  
export default {
  name: 'DashBoardPage',
  components: { Bar },
  data() {
    return {
          chartData: {
          labels: [],
          datasets: []
        },
        chartData1: {
          labels: [],
          datasets: []
        },
        chartOptions: {
          responsive: true
        }
      }
    },
    mounted() {
      this.checkUserRole();
      this.fetchSaleType()
      this.fetchPropertyType()
    },
    computed:{
        currentUser(){
            return this.$store.state.auth.user;
        }
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
      async fetchSaleType() {
        try {
          // Fetch data from your API
          const response = await axios.get('http://localhost:8080/api/counts/saletype')
          const data = response.data
          // Assuming your API response is an object with 'sale' and 'rent' properties
          const saleData = data.sale
          const rentData = data.rent
  
          // Update chartData
          this.chartData = {
            labels: ['Properties For Sale', 'Properties For Rent'],
            datasets: [
              {
                label: ['Properties Sale Type'],
                backgroundColor: ['blue', 'green'], // Colors for sale and rent bars
                data: [saleData, rentData]
              }
            ]
          }
        } catch (error) {
          console.error('Error fetching data:', error)
        }
      },
      async fetchPropertyType() {
        if (!this.currentUser && !this.currentUser['roles'].includes('ROLE_ADMIN')) {
                this.$router.push('/login');
                return;
        }
        try {
          // Fetch data from your API
          const response = await axios.get('http://localhost:8080/api/counts/propertytype')
          const data = response.data
          console.log(data)
          // Assuming your API response is an object with 'sale' and 'rent' properties
          const houseData = data.house
          const apartmentData = data.apartment
          const officeData = data.office
          const parkingData = data.parking
          const restaurantBarData = data['restaurant/bar']
  
          // Update chartData
          this.chartData1 = {
            labels: ['House', 'Apartment', 'Office', 'Parking', 'Restaurant/Bar'],
            datasets: [
              {
                label: 'Property Types',
                backgroundColor: ['blue', 'green', 'red', 'yellow', 'orange'], // Colors for sale and rent bars
                data: [houseData, apartmentData, officeData, parkingData, restaurantBarData]
              }
            ]
          }
        } catch (error) {
          console.error('Error fetching data:', error)
        }
      },
    }
  }
  </script>

<style scoped>
.router-link-active {
  text-decoration: none; /* Remove underline */
  color: crimson; /* Inherit color */
}
</style>
  