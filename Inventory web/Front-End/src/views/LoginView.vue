<template>
    <div >
        <LoginV/>
    </div>
  </template>
  
  <script>
  // @ is an alias to /src
  import LoginV from '@/components/LoginV.vue'

  
  export default {
    name: 'LoginView',
    components: {
       LoginV,
    }
  }
  </script>