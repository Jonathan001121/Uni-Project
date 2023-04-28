<template>
  <div>
    <nav
      style="
        margin-top: 10px;
        background-color: rgb(250, 250, 250);
        border-radius: 10px;
      "
      aria-label="breadcrumb "
    >
      <ol class="breadcrumb">
        <li class="breadcrumb-item"><a href="/">Home</a></li>
        <li class="breadcrumb-item active" aria-current="page">Materials</li>
      </ol>
    </nav>
    <a href="/material/detail"
      ><button style="float: right" class="breadcrumb-item btn btn-primary">
        ADD
      </button></a
    >

    <div class="">
      <div clsss="row">
        <div
          class="col-12 col-sm-12 col-md-6 col-lg-6 col-xl-4 ratio-4x3"
          v-for="material in materials"
          :key="material._id"
        >
          <div class="card" style="width: 18rem">
            <router-link :to="`/material/detail/${material._id}`"
              ><img
                class="card-img-top"
                :src="material.imageURL"
                alt="Card image cap"
              />
            </router-link>
            <div class="card-body">
              <h5 class="card-title">{{ material.title }}</h5>
              <p class="card-text">
                {{ material.description }}
              </p>
            </div>
          </div>
        </div>
      </div>
    </div>
    <div class="block">
      <nav aria-label="Page navigation example">
        <ul class="pagination">
          <a
            class="page-link"
            @click="fetchPage(Math.max(parseInt(currentPage) - 1, 1))"
            >prev</a
          >
          <a
            class="page-link"
            v-for="i in pages.slice(
              Math.max(currentPage - 2, 0),
              Math.min(lastPage, currentLastPage)
            )"
            :key="i"
            @click="fetchPage(i)"
            v-bind:class="{ active: currentPage == i }"
            >{{ i }}
          </a>
          <a
            class="page-link"
            @click="fetchPage(Math.min(lastPage, parseInt(currentPage) + 1))"
            >next</a
          >
        </ul>
      </nav>
    </div>
  </div>
</template>

<script>
import { ref, onMounted, computed } from "vue";

export default {
  name: "PaginatedMaterials",

  setup() {
    const materials = ref([]);
    const lastPage = ref(0);
    const perPage = ref(6);
    const currentPage = ref(1);
    const currentLastPage = ref(0);
    const pages = computed(() => {
      var pages = [];

      for (var i = 1; i <= lastPage.value; i++) {
        pages.push(i);
      }

      return pages;
    });

    const fetchPage = async function (page) {
      var response = await fetch(
        "/api/materials?perPage=" + perPage.value + "&page=" + page
      );

      if (response.ok) {
        var data = await response.json();
        currentPage.value = data.currentPage;
        currentLastPage.value = parseInt(currentPage.value) + 3;
        materials.value = data.materials;
        lastPage.value = data.pages;
      } else {
        alert(response.statusText);
      }
    };

    onMounted(function () {
      fetchPage(1);
    });

    return {
      materials,
      pages,
      fetchPage,
      lastPage,
      currentPage,
      currentLastPage,
    };
  },
};
</script>

<style>
div {
  display: inline-block;
}
</style>