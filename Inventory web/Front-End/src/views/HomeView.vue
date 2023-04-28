<template>
  <div class="home">
  
    <HelloWorld msg="Inventory system"/>
    <HomeCard/>
  </div>
</template>

<script>
// @ is an alias to /src
import HelloWorld from '@/components/HelloWorld.vue'
import HomeCard from '@/components/HomeCard.vue'

export default {
  name: 'HomeView',
  components: {
    HelloWorld,
    HomeCard,
    
  }
}
</script>
<style scoped>

</style>
