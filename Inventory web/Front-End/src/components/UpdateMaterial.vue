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
        <li class="breadcrumb-item"><a href="/materials/">Materials</a></li>
        <li class="breadcrumb-item active" aria-current="page">Material Detail</li>
      </ol>
    </nav>
    <form class="container">
      <div class="row mb-3">
        <label for="title"  class="col-sm-2 col-form-label">Title</label>
        <div class="col-sm-10">
          <input
            v-model="material.title"
            class="form-control"
            placeholder="title"
          />
        </div>
      </div>

      <div class="row mb-3">
        <label for="category" class="col-sm-2 col-form-label">Category</label>
        <div class="col-sm-10">
          <select
            class="form-select"
            aria-label="Default select example"
            v-model="material.category"
          >
          <option selected>Open this select menu</option>
            <option value="metal">metal</option>
            <option value="plastic">plastic</option>
            <option value="wood">wood</option>
          </select>
        </div>
      </div>

      <div class="row mb-3">
        <label for="imaegURL" class="col-sm-2 col-form-label">image</label>
        <div class="col-sm-10">
          <input
            class="form-control"
            v-model="material.imageURL"
            placeholder="image URL"
          />
        </div>
      </div>

      <div class="row mb-3">
        <label for="amount" class="col-sm-2 col-form-label">Amount</label>
        <div class="col-sm-10">
          <input class="form-control" type="number" v-model="material.quantity" placeholder="amount" />
        </div>
      </div>

      <div class="row mb-3">
        <label for="location" class="col-sm-2 col-form-label">Location</label>
        <div class="col-sm-10">
          <input
            v-model="material.location"
            class="form-control"
            placeholder="location in warehouse"
          />
        </div>
      </div>

  

      <div class="row mb-3">
        <label for="description" class="col-sm-2 col-form-label"
          >Description</label
        >
        <div class="col-sm-10">
          <input
            class="form-control"
            v-model="material.description"
            placeholder="description show on listing page"
          />
        </div>
      </div>

      <div class="row mb-3">
        <label for="remarks" class="col-sm-2 col-form-label">Address 2</label>
        <div class="col-sm-10">
          <input
            class="form-control"
            v-model="material.remarks"
            placeholder="internal remarks"
          />
        </div>
      </div>
      <button class="btn btn-primary" type="submit" @click="updateMaterial()">
        Update
      </button>
      <button class="btn btn-danger" type="button" @click="deleteMaterial()">
        Delete
      </button>
    </form>
  </div>
</template>


<script >
import { ref, onMounted } from "vue";
import { useRoute } from "vue-router";
export default {
  name: "UpdateMaterial",
  setup() {
    const material = ref({});
    const route = useRoute();
    onMounted(async () => {
      var response = await fetch("/api/material/detail/" + route.params.id);
      if (response.ok) {
        material.value = await response.json();
      }
    });

    const updateMaterial = async function () {
      // event.preventDefault();
      var r = confirm("Confirm Update?");
      if (r) {
        delete material.value._id;

        var response = await fetch("/api/material/detail/" + route.params.id, {
          method: "put",
          headers: {
            // 'Content-Type': 'application/x-www-form-urlencoded',
            "Content-Type": "application/json",
          },
          // body: new URLSearchParams(new FormData(event.target))
          body: JSON.stringify(this.material),
        });

        if (response.ok) {
          var text = await response.text();
          alert(text);
          location.assign("/materials");
        } else {
          alert(response.statusText);
        }
      } else {
        alert("cancelled");
      }
    };

    const deleteMaterial = async function () {
      var r = confirm("Confirm Delete?");

      if (r) {
        var response = await fetch("/api/material/detail/" + route.params.id, {
          method: "delete",
        });

        if (response.ok) {
          alert("material deleted");
          location.assign("/materials");
        } else {
          alert(response.statusText);
        }
      } else {
        alert("cancelled");
      }
    };

    return {
      material,
      updateMaterial,
      deleteMaterial,
    };
  },
};
</script>