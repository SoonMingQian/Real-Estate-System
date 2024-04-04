<template>
  <SaleList :properties="properties" @updateProperties="properties = $event" />
</template>

<script>
import SaleList from '../components/SaleList.vue';
import '../property-for-sale.css'
import UserService from '@/services/user.service';
export default {
  name: "PropertySalePage",
  components: {
    SaleList,
  },
  data() {
    return {
      properties: [],
    }
  },
  async created() {
    try {
      const response = await UserService.getSaleProperties();
      this.properties = response.data;
    } catch (error) {
      console.error(error);
    }
  },
}

</script>
