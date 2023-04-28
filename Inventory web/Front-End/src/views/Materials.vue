<template>
    <div >
        <PaginatedMaterials/>
    </div>
  </template>
  
  <script>
  // @ is an alias to /src
  import PaginatedMaterials from '@/components/PaginatedMaterials.vue'

  
  export default {
    name: 'MaterialsView',
    components: {
        PaginatedMaterials,
    }
  }
  </script>
 