<template>
<div>
<nav class="row navbar  navbar-light bg-light">
        <router-link class="col navbar-brand" to="/">Invertory</router-link>
        <router-link class="col navbar-brand" to="/books">Books</router-link>
        <router-link class="col navbar-brand" to="/games">Games</router-link>
        <router-link class="col navbar-brand" to="/gifts">Gifts</router-link>
        <router-link class="col navbar-brand" to="/materials">Materials</router-link>

    <input class="col form-control mr-sm-2" type="search" v-model="search" placeholder="Search" aria-label="Search">
    <button class="col btn btn-outline-success my-2 my-sm-0" @click="SearchItem()" >Search</button>
  
</nav>
    <router-view/>
</div>
</template>

<script>
import { ref } from "vue";
export default ({
    setup() {
        let search=ref("");
        const SearchItem= async function(){
            location.assign("/search?title="+search.value);
        }
        return{
        search,
        SearchItem
    }
    }
    
})
</script>


<style>


</style>
