import { createApp } from 'vue'
import App from './App.vue'
import * as VueRouter from 'vue-router'
import SavePage from './pages/SavePage.vue'
import PropertySalePage from './pages/PropertySalePage.vue'
import PropertyDetailPage from './pages/PropertyDetailPage.vue'
import NotFoundPage from './pages/NotFoundPage.vue'
//Font Awesome import
import { library } from '@fortawesome/fontawesome-svg-core'
import { fas } from '@fortawesome/free-solid-svg-icons'
import { far } from '@fortawesome/free-regular-svg-icons'
import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome'
//Swiper import
import SwiperClass, { Navigation } from 'swiper'
import VueAwesomeSwiper from 'vue-awesome-swiper'
import 'swiper/css'
import 'swiper/css/navigation'

SwiperClass.use(Navigation)
library.add(fas, far);

createApp(App)
.use(VueRouter.createRouter({
    history: VueRouter.createWebHistory(process.env.BASE_URL),
    routes: [{
        path: '/save',
        component: SavePage,
    }, {
        path: '/property-for-sale',
        component: PropertySalePage,
    }, {
        path: '/property-for-sale/:propertyId',
        component: PropertyDetailPage,
    },{
        path: '/:pathMatch(.*)*',
        component: NotFoundPage
    }]
}))
.use(VueAwesomeSwiper)
.component('font-awesome-icon', FontAwesomeIcon)
.mount('#app')
