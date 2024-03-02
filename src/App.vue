<template>
  <NavBar />
  <div class="page-wrap">
    <router-view></router-view>
  </div>
</template>

<script>
import NavBar from '@/components/NavBar.vue'
export default {
  name: 'App',
  components: {
    NavBar,
  }
}
</script>

<style scoped>
.page-wrap {
  max-width: 100%;
  margin: 0 auto;
  padding-top: 120px;
  background-color: white;
  height: 100vh;

}
</style>


