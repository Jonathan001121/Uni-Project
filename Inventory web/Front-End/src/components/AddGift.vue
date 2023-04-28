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
        <li class="breadcrumb-item"><a href="/gifts/">Gifts</a></li>
        <li class="breadcrumb-item active" aria-current="page">New Gift</li>
      </ol>
    </nav>
    <form class="container">
      <div class="row mb-3">
        <label for="title"  class="col-sm-2 col-form-label">Title</label>
        <div class="col-sm-10">
          <input
          required
            v-model="gift.title"
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
            v-model="gift.category"
          >
            <option selected>Open this select menu</option>
            <option value="flower">flower</option>
            <option value="cake">cake</option>
            <option value="electrical product">electrical product</option>
          </select>
        </div>
      </div>

      <div class="row mb-3">
        <label for="imaegURL" class="col-sm-2 col-form-label">image</label>
        <div class="col-sm-10">
          <input
            class="form-control"
            v-model="gift.imageURL"
            placeholder="image URL"
          />
        </div>
      </div>

      <div class="row mb-3">
        <label for="amount" class="col-sm-2 col-form-label">Amount</label>
        <div class="col-sm-10">
          <input class="form-control" type="number" v-model="gift.amount" placeholder="amount" />
        </div>
      </div>

      <div class="row mb-3">
        <label for="unit price" class="col-sm-2 col-form-label">Unit Price(HKD)</label>
        <div class="col-sm-10">
          <input class="form-control" step="0.1"  pattern="^\d+(?:\.\d{1,2})?$" type="number" v-model="gift.price" placeholder="unti price" />
        </div>
      </div>

      <div class="row mb-3">
        <label for="donatedBy" class="col-sm-2 col-form-label">Donated by</label>
        <div class="col-sm-10">
          <input
            class="form-control"
            v-model="gift.donatedBy"
            placeholder="name"
          />
        </div>
      </div>


      <div class="row mb-3">
        <label for="location" class="col-sm-2 col-form-label">Location</label>
        <div class="col-sm-10">
          <input
            v-model="gift.location"
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
            v-model="gift.description"
            placeholder="description show on listing page"
          />
        </div>
      </div>

      <div class="row mb-3">
        <label for="remarks" class="col-sm-2 col-form-label">Address 2</label>
        <div class="col-sm-10">
          <input
            class="form-control"
            v-model="gift.remarks"
            placeholder="Remarks"
          />
        </div>
      </div>

      <button class="btn btn-primary" type="submit" @click="insertGift()">
        Save
      </button>
    </form>
  </div>
</template>

<script>
import { ref } from "vue";
export default {
  name: "AddGift",
  setup() {
    const gift = ref({});

    const insertGift = async function () {
      var r = confirm("Confirm Add?");
      if (r) {
        var response = await fetch("/api/gift/detail", {
          method: "put",
          headers: {
            "Content-Type": "application/json",
          },
          body: JSON.stringify(this.gift),
        });

        if (response.ok) {
          var text = await response.text();
          alert(text);
          location.assign("/gifts");
        } else {
          alert(response.statusText);
        }
      } else {
        alert("cancelled");
      }
    };
    return {
      gift,
      insertGift,
    };
  },
};
</script>

<style>
</style>