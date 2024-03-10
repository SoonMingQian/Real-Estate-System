<template>
<h1>Hello</h1>
</template>

<script>
import axios from 'axios';
export default{
    name: 'HomePage',
    async created(){
      const response = await axios.get('login', {
        headers:{
            Authorization: 'Bearer' + localStorage.getItem('token')
        }
      });
      console.log(response)
    }
}


</script>