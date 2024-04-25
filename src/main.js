import { createApp } from 'vue'
import App from './App.vue'
import * as VueRouter from 'vue-router'
import store from "./store";
//Vuetify import
import 'vuetify/styles'
import { createVuetify } from 'vuetify'
import * as components from 'vuetify/components'
import * as directives from 'vuetify/directives'
//mdi font import
import '@mdi/font/css/materialdesignicons.css'

import SavePage from './pages/SavePage.vue'
import PropertySalePage from './pages/PropertySalePage.vue'
import PropertyRentPage from './pages/PropertyRentPage.vue'
import PropertyDetailPage from './pages/PropertyDetailPage.vue'
import MyPropertyPage from './pages/MyPropertyPage.vue'
import HelpPage from './pages/HelpPage.vue';
import NotFoundPage from './pages/NotFoundPage.vue'
import ProfilePage from './pages/ProfilePage.vue'
import EditPropertyPage from './pages/EditPropertyPage.vue'
import EditMyPropertyPage from './pages/EditMyPropertyPage.vue'
import AddMyPropertyPage from './pages/AddMyPropertyPage.vue'
import AddPropertyPage from './pages/AddPropertyPage.vue'
import ApartmentPage from './pages/ApartmentPage.vue'
import CommercialPage from './pages/CommercialPage.vue'
import SignUpPage from './pages/SignUpPage.vue'
import LoginPage from './pages/LoginPage.vue'
import HomePage from './pages/HomePage.vue'
import ChangePasswordPage from './pages/ChangePasswordPage.vue'
import ForgotPasswordPage from './pages/ForgotPasswordPage.vue'
import UserManagementPage from './pages/UserManagementPage.vue'
import PropertyManagementPage from './pages/PropertyManagementPage.vue'
import ApprovePage from './pages/ApprovePage.vue'
import DashBoardPage from './pages/DashBoardPage.vue'
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
const vuetify = createVuetify({
    components,
    directives,
})
export default createVuetify({
    icons: {
      defaultSet: 'mdi', // This is already the default value - only for display purposes
    },
  })

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
        path: '/help',
        component: HelpPage,
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
        path: '/my-property/edit-property/image/:propertyId',
        component: EditMyPropertyPage,
    },{
        path: '/my-property/edit-property/:propertyId',
        component: EditPropertyPage,
    },{
        path: '/my-property/add-property/:propertyId',
        component: AddMyPropertyPage,
    },{
        path: '/my-property/add-property',
        component: AddPropertyPage,
    },{
        path: '/my-profile',
        component: ProfilePage
    },{
        path: '/login',
        component: LoginPage
    },{
        path: '/register',
        component: SignUpPage
    },{
        path: '/user/changePassword',
        component: ChangePasswordPage,
        props: (route) => ({ token: route.query.token })
    },{
        path: '/forgotPassword',
        component: ForgotPasswordPage,
    },{
        path: '/manageUser',
        component: UserManagementPage,
        meta: { 
            hideNavbar: true, 
        }
    },{
        path: '/manageProperty',
        component: PropertyManagementPage,
        meta: { 
            hideNavbar: true, 
        }
    },{
        path: '/approve',
        component: ApprovePage,
        meta: { 
            hideNavbar: true, 
        }
    },{
        path: '/dashboard',
        component: DashBoardPage,
        meta: { 
            hideNavbar: true, 
        }
    },{
        path: '/:pathMatch(.*)*',
        component: NotFoundPage
    }]
}))
.use(VueAwesomeSwiper)
.use(store)
.use(vuetify)
.component('font-awesome-icon', FontAwesomeIcon)
.mount('#app')
