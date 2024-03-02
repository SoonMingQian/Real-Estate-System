<template>
    <div class="profile-nav">
        <ul class="profile-navigation">
            <li><router-link class="profile-link" to="my-profile"><font-awesome-icon :icon="['fas', 'user']" /> My Profile</router-link></li>
            <li><router-link class="profile-link" to="save"><font-awesome-icon :icon="['fas', 'heart']" /> Shortlist</router-link></li>
            <li><router-link class="profile-link" to="my-property"><font-awesome-icon :icon="['fas', 'house']" /> Sale/Rent List</router-link></li>
        </ul>
    </div>

</template>

<script>
export default{
    name: "ProfileNavBar",
}
</script>

<style scoped>

.profile-nav{
    position: fixed;
    display: flex;
    flex-direction: row;
    padding-top: 10px;
    background-color: white;
    width: 100%;
    align-items: center;
    justify-content: center;
}

.profile-link{
    color: black;
    text-decoration: none;
    border-bottom: 3px solid transparent;
    transition: .5s ease all;
}

.profile-navigation{
    display: flex;
    align-items: center;
    justify-content: center;
    list-style: none;
}

.profile-navigation li{
   margin-left: 80px;
   margin-right: 80px;
}

.profile-navigation li :hover{
    color: crimson;
    border-color: crimson;
}

a.router-link-active{
    color: crimson;
    border-color: crimson;
}
</style>