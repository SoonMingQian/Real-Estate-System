<template>
    <header :class="{ 'scrolled-nav': scrollPosition}">
        <nav>
            <div class="branding">
                <img src="logo" alt="" />
            </div>
            <ul v-show="!mobile" class="navigation">
            <li><router-link class="link" to="property-for-sale">Buy</router-link></li>
            <li><router-link class="link" to="">Rent</router-link></li>
            <li><router-link class="link" to="">Sell</router-link></li>
            <li><router-link class="link" to="">Contact</router-link></li>
            </ul>
            <div class="icon">
               <i @click="toggleMobileNav" v-show="mobile" class="far fa-bars" :class="{'icon-active': mobileNav }"><font-awesome-icon :icon="['fas', 'bars']" /></i> 
            </div>
            <transition name="mobile-nav">
                <ul v-show="mobileNav" class="dropdown-nav">
            <li><router-link class="link" to="property-for-sale">Buy</router-link></li>
            <li><router-link class="link" to="">Rent</router-link></li>
            <li><router-link class="link" to="">Sell</router-link></li>
            <li><router-link class="link" to="">Contact</router-link></li>
            </ul>
            </transition>
        </nav>
    </header>
</template>

<script>
import '../navbar.css'
import logo from '@/assets/logo.png'
export default {
    name: "NavBar",
    data() {
        return {
            logo,
        }
    },
}


</script>