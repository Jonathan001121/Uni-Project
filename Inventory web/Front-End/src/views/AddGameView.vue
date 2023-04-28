<template>
    <div >
        <AddGame/>
    </div>
  </template>
  
  <script>
  // @ is an alias to /src
  import AddGame from '@/components/AddGame.vue'

  
  export default {
    name: 'AddGameView',
    components: {
        AddGame,
    }
  }
  </script>
  <style scoped>
  
  </style>
  