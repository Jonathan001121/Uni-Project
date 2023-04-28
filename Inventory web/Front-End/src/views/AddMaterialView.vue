<template>
    <div >
        <AddMaterial/>
    </div>
  </template>
  
  <script>
  // @ is an alias to /src
  import AddMaterial from '@/components/AddMaterial.vue'

  
  export default {
    name: 'AddMaterialView',
    components: {
        AddMaterial,
    }
  }
  </script>
  <style scoped>
  
  </style>
  