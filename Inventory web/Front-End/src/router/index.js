import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '../views/HomeView.vue'

const routes = [
  {
    path: '/',
    name: 'home',
    component: HomeView
  },
  {
    path: '/books',
    name: 'books',
    component: () => import( '../views/Books.vue')
  },
  {
    path: '/search',
    name: 'search',
    component: () => import( '../views/SearchResults.vue')
  },  {
    path: '/book/detail',
    name: 'AddBook',
    component: () => import( '../views/AddBookView.vue')
  },{
    path: '/game/detail',
    name: 'AddGame',
    component: () => import( '../views/AddGameView.vue')
  },{
    path: '/gift/detail',
    name: 'AddGift',
    component: () => import( '../views/AddGiftView.vue')
  },{
    path: '/material/detail',
    name: 'AddMaterial',
    component: () => import( '../views/AddMaterialView.vue')
  },{
    path: '/book/detail/:id/',
    name: 'UpdateBook',
    component: () => import( '../views/UpdateBookView.vue')
  },{
    path: '/game/detail/:id/',
    name: 'UpdateGame',
    component: () => import( '../views/UpdateGameView.vue')
  },{
    path: '/gift/detail/:id/',
    name: 'UpdateGift',
    component: () => import( '../views/UpdateGiftView.vue')
  },{
    path: '/material/detail/:id/',
    name: 'UpdateMaterial',
    component: () => import( '../views/UpdateMaterialView.vue')
  },
  {
    path: '/games',
    name: 'games',
    component: () => import( '../views/Games.vue')
  },
  {
    path: '/gifts',
    name: 'gifts',
    component: () => import( '../views/Gifts.vue')
  },
  {
    path: '/materials',
    name: 'materials',
    component: () => import( '../views/Materials.vue')
  },
  {
    path: '/login',
    name: 'login',
    component: () => import( '../views/LoginView.vue')
  }
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

export default router
