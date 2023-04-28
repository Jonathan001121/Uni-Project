<template>
    <div >
        <PaginatedGames/>
    </div>
  </template>
  
  <script>
  // @ is an alias to /src
  import PaginatedGames from '@/components/PaginatedGames.vue'

  
  export default {
    name: 'GamesView',
    components: {
        PaginatedGames,
    }
  }
  </script>
 