import { createApp } from 'vue'
import App from './App.vue'
import * as VueRouter from 'vue-router'
import SavePage from './pages/SavePage.vue'
import PropertySalePage from './pages/PropertySalePage.vue'
import PropertyRentPage from './pages/PropertyRentPage.vue'
import PropertyDetailPage from './pages/PropertyDetailPage.vue'
import MyPropertyPage from './pages/MyPropertyPage.vue'
import NotFoundPage from './pages/NotFoundPage.vue'
import ProfilePage from './pages/ProfilePage.vue'
import EditMyPropertyPage from './pages/EditMyPropertyPage.vue'
import AddMyPropertyPage from './pages/AddMyPropertyPage.vue'
import ApartmentPage from './pages/ApartmentPage.vue'
import CommercialPage from './pages/CommercialPage.vue'
import SignUpPage from './pages/SignUpPage.vue'
import LoginPage from './pages/LoginPage.vue'
import HomePage from './pages/HomePage.vue'
//Font Awesome import
import { library } from '@fortawesome/fontawesome-svg-core'
import { fas } from '@fortawesome/free-solid-svg-icons'
import { far } from '@fortawesome/free-regular-svg-icons'
import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome'
//Swiper import
import SwiperClass, { Pagination } from 'swiper'
import VueAwesomeSwiper from 'vue-awesome-swiper'
import 'swiper/css'
import 'swiper/css/navigation'

SwiperClass.use(Pagination)
library.add(fas, far);

createApp(App)
.use(VueRouter.createRouter({
    history: VueRouter.createWebHistory(process.env.BASE_URL),
    routes: [{
        path: '/save',
        component: SavePage,
    },{
        path: '/',
        component: HomePage,
    },{
        path: '/property-for-sale',
        component: PropertySalePage,
    },{
        path: '/property-for-rent',
        component: PropertyRentPage,
    },{
        path: '/property-listing/:propertyId',
        component: PropertyDetailPage,
    },{
        path: '/apartment',
        component: ApartmentPage,
    },{
        path: '/commercial',
        component: CommercialPage,
    },{
        path: '/my-property',
        component: MyPropertyPage,
    },{
        path: '/my-property/edit-property/:propertyId',
        component: EditMyPropertyPage,
    },{
        path: '/my-property/add-property',
        component: AddMyPropertyPage,
    },{
        path: '/my-profile',
        component: ProfilePage
    },{
        path: '/login',
        component: LoginPage
    },{
        path: '/sign-up',
        component: SignUpPage
    },{
        path: '/:pathMatch(.*)*',
        component: NotFoundPage
    }]
}))
.use(VueAwesomeSwiper)
.component('font-awesome-icon', FontAwesomeIcon)
.mount('#app')
