<template>
    <div >
        <PaginatedSearchResults/>
    </div>
  </template>
  
  <script>
  // @ is an alias to /src
  import PaginatedSearchResults from '@/components/PaginatedSearchResults.vue'

  
  export default {
    name: 'SearchResultsView',
    components: {
        PaginatedSearchResults,
    }
  }
  </script>
 