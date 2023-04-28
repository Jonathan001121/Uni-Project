import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import "bootstrap/dist/css/bootstrap.min.css"
import VueApexCharts from "vue3-apexcharts";
createApp(App).use(router).use(VueApexCharts).mount('#app')
