<template>
  <div class="page-wrap" >
    <NavBar v-if="!$route.meta.hideNavbar"/>
    <router-view></router-view>
  </div>
</template>

<script>

import NavBar from '@/components/NavBar.vue'
export default {
  name: 'App',
  components: {
    NavBar,
  },
}
</script>

<style scoped>

.page-wrap {
  max-width: 100%;
  margin: 0 auto;
  background-color: white;
  min-height: 90vh;
  height: auto;

}
</style>


