<template>
    <div >
        <PaginatedBooks/>
    </div>
  </template>
  
  <script>
  // @ is an alias to /src
  import PaginatedBooks from '@/components/PaginatedBooks.vue'

  
  export default {
    name: 'BooksView',
    components: {
        PaginatedBooks,
    }
  }
  </script>
 
  