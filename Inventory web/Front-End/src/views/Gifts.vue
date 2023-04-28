<template>
    <div >
        <PaginatedGifts/>
    </div>
  </template>
  
  <script>
  // @ is an alias to /src
  import PaginatedGifts from '@/components/PaginatedGifts.vue'

  
  export default {
    name: 'GiftsView',
    components: {
        PaginatedGifts,
    }
  }
  </script>
 
  