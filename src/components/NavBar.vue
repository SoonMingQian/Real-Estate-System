<template>
    <header :class="{ 'scrolled-nav': scrolledNav }">
        <nav>
            <div class="branding">
                <router-link class="link" to="/">
                <img :src="logo" alt="" />
                <h1>Estate Eagle</h1>
            </router-link>
            </div>
            <ul v-show="!mobile" class="navigation">
                <li><router-link class="link" to="/property-for-sale">Buy</router-link></li>
                <li><router-link class="link" to="/property-for-rent">Rent</router-link></li>
                <li><router-link class="link" to="/apartment">Apartment</router-link></li>
                <li><router-link class="link" to="/commercial">Commercial</router-link></li>
                <li><router-link class="link" to="/help">Help</router-link></li>
                <div v-if="!currentUser">
                    <li><router-link class="link" to="/login">Login</router-link></li>
                </div>
                <div v-else>
                    <li><router-link class="link" to="/my-profile">My Profile</router-link></li>

                </div>
                <div v-if="currentUser">
                    <li><span class="nav-link" @click.prevent="logOut"><font-awesome-icon
                                :icon="['fas', 'right-from-bracket']" /></span></li>
                </div>

            </ul>
            <div class="icon">
                <i @click="toggleMobileNav" v-show="mobile" class="far fa-bars"
                    :class="{ 'icon-active': mobileNav }"><font-awesome-icon :icon="['fas', 'bars']" /></i>
            </div>
            <transition name="mobile-nav">
                <ul v-show="mobileNav" class="dropdown-nav">
                    <li><router-link class="link" to="/property-for-sale">Buy</router-link></li>
                    <li><router-link class="link" to="/property-for-rent">Rent</router-link></li>
                    <li><router-link class="link" to="/apartment">Apartment</router-link></li>
                    <li><router-link class="link" to="/commercial">Commercial</router-link></li>
                    <li><router-link class="link" to="/help">Help</router-link></li>
                    <div v-if="!currentUser">
                        <li><router-link class="link" to="/login">Login</router-link></li>
                    </div>
                    <div v-else>
                        <li><router-link class="link" to="/my-profile">My Profile</router-link></li>

                    </div>
                    <div v-if="currentUser">
                        <li><span class="nav-link" @click.prevent="logOut"><font-awesome-icon
                                    :icon="['fas', 'right-from-bracket']" /></span></li>
                    </div>

                </ul>
            </transition>
        </nav>
    </header>
</template>

<script>

import logo from '@/assets/logo.png'
export default {
    name: "NavBar",
    data() {
        return {
            logo,
            scrolledNav: null,
            mobile: null,
            mobileNav: null,
            windowWidth: null,
        }
    },
    created() {
        // Add event listener for window resize
        window.addEventListener('resize', this.checkScreen)
        this.checkScreen();
    },
    mounted() {
        window.addEventListener('scroll', this.updateScroll)
    },
    computed: {
         // Get the current user from the Vuex store
        currentUser() {
            return this.$store.state.auth.user;
        }
    },
    methods: {
        // Toggle the mobile navigation
        toggleMobileNav() {
            this.mobileNav = !this.mobileNav;
        },
        // Update the scroll state of the nav bar
        updateScroll() {
            const scrollPosition = window.scrollY
            if (scrollPosition > 50) {
                this.scrolledNav = true;
            }
            this.scrolledNav = false
        },
        // Check the screen size and update the mobile state
        checkScreen() {
            this.windowWidth = window.innerWidth;
            if (this.windowWidth <= 750) {
                this.mobile = true;
                return;
            }
            this.mobile = false;
            this.mobileNav = false;
            return;
        },
        logOut() {
            this.$store.dispatch('auth/logout');
            this.$router.push('/login');
        }
    }
}
</script>

<style lang="scss" scoped>
header {
    z-index: 99;
    width: 100%;
    transition: .5s ease all;
    background-color: white;
    box-shadow: 2px 2px 4px rgba(0, 0, 0, 0.3);
    ;

    nav {
        position: relative;
        display: flex;
        flex-direction: row;
        padding: 19px 0;
        transition: .5s ease all;
        width: 90%;
        margin: 0 auto;

        @media (min-width: 1140px) {
            max-width: 1140px;
        }

        ul,
        .link {
            font-weight: 500;
            color: black;
            list-style: none;
            text-decoration: none;
            font-weight: bold;
        }

        li {
            text-transform: uppercase;
            padding: 16px;
            margin-left: 16px;
        }

        .link {
            font-size: 14px;
            transition: .5s ease all;
            padding-bottom: 4px;
            border-bottom: 3px solid transparent;

            &:hover {
                color: crimson;
                border-color: crimson;
            }
        }

        a.router-link-active {
            color: crimson;
            border-color: crimson;
        }

        .branding {
            display: flex;
            align-items: center;

            img {
                width: 50px;
                transition: .5s ease all;
            }
        }

        .navigation {
            display: flex;
            align-items: center;
            flex: 1;
            justify-content: flex-end;
        }

        .icon {
            display: flex;
            align-items: center;
            position: absolute;
            top: 0;
            right: 24px;
            height: 100%;

            i {
                cursor: pointer;
                font-size: 24px;
                transition: .8s ease all;
            }
        }

        .icon-active {
            transform: rotate(180deg);
        }

        .dropdown-nav {
            display: flex;
            flex-direction: column;
            position: fixed;
            widows: 100%;
            max-width: 250px;
            height: 100%;
            background-color: white;
            top: 0;
            left: 0;

            li {
                margin-left: 0;

                .link {
                    color: black;
                }
            }
        }

        .mobile-nav-enter-active,
        .mobile-nav-leave-active {
            transition: 1s ease all;
        }

        .mobile-nav-enter-from,
        .mobile-nav-leave-to {
            transform: translateX(-250px);
        }

        .mobile-nav-enter-to {
            transform: translateX(0);
        }
    }
}

.scrolled-nav {
    background-color: black;
    box-shadow: 0 4px 6px -1px rgba(0, 0, 0, 0.1), 0 2px 4px -1px rgba(0, 0, 0, 0.06);

    nav {
        padding: 8px 0;
    }

    .branding {
        img {
            width: 40px;
            box-shadow: 0 4px 6px -1px rgba(0, 0, 0, 0.1), 0 2px 4px -1px rgba(0, 0, 0, 0.06);
        }
    }
}
</style>