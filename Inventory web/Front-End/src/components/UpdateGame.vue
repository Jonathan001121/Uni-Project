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
        <li class="breadcrumb-item"><a href="/games/">Games</a></li>
        <li class="breadcrumb-item active" aria-current="page">Game Detail</li>
      </ol>
    </nav>
    <form class="container">
      <div class="row mb-3">
        <label for="title" class="col-sm-2 col-form-label">Title</label>
        <div class="col-sm-10">
          <input
            v-model="game.title"
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
            v-model="game.category"
          >
            <option selected>Open this select menu</option>
            <option value="Puzzle">Puzzle</option>
            <option value="Adventure">Adventure</option>
            <option value="Strategy">Strategy</option>
            <option value="Horror">Horror</option>
            <option value="Role-playing">Role-playing</option>
            <option value="Sport">Sport</option>
            <option value="Action">Action</option>
          </select>
        </div>
      </div>

      <div class="row mb-3">
        <label for="imaegURL" class="col-sm-2 col-form-label">image</label>
        <div class="col-sm-10">
          <input
            class="form-control"
            v-model="game.imageURL"
            placeholder="image URL"
          />
        </div>
      </div>

      <div class="row mb-3">
        <label for="publisher" class="col-sm-2 col-form-label">Publisher</label>
        <div class="col-sm-10">
          <input
            class="form-control"
            v-model="game.publisher"
            placeholder="publisher"
          />
        </div>
      </div>

      <div class="row mb-3">
        <label for="quantity" class="col-sm-2 col-form-label">Quantity</label>
        <div class="col-sm-10">
          <input
            class="form-control"
            type="number"
            v-model="game.quantity"
            placeholder="quantity"
          />
        </div>
      </div>

      <div class="row mb-3">
        <label for="location" class="col-sm-2 col-form-label">Location</label>
        <div class="col-sm-10">
          <input
            v-model="game.location"
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
            v-model="game.description"
            placeholder="description show on listing page"
          />
        </div>
      </div>

      <div class="row mb-3">
        <label for="remarks" class="col-sm-2 col-form-label">Address 2</label>
        <div class="col-sm-10">
          <input
            class="form-control"
            v-model="game.remarks"
            placeholder="Remarks"
          />
        </div>
      </div>

      <button class="btn btn-primary" type="submit" @click="updateGame()">
        Update
      </button>
      <button class="btn btn-danger" type="button" @click="deleteGame()">
        Delete
      </button>
    </form>
  </div>
</template>


<script >
import { ref, onMounted } from "vue";
import { useRoute } from "vue-router";
export default {
  name: "UpdateGame",
  setup() {
    const game = ref({});
    const route = useRoute();
    onMounted(async () => {
      var response = await fetch("/api/game/detail/" + route.params.id);
      if (response.ok) {
        game.value = await response.json();
      }
    });

    const updateGame = async function () {
      // event.preventDefault();
      var r = confirm("Confirm Update?");
      if (r) {
        delete game.value._id;

        var response = await fetch("/api/game/detail/" + route.params.id, {
          method: "put",
          headers: {
            // 'Content-Type': 'application/x-www-form-urlencoded',
            "Content-Type": "application/json",
          },
          // body: new URLSearchParams(new FormData(event.target))
          body: JSON.stringify(this.game),
        });

        if (response.ok) {
          var text = await response.text();
          alert(text);
          location.assign("/games");
        } else {
          alert(response.statusText);
        }
      } else {
        alert("cancelled");
      }
    };

    const deleteGame = async function () {
      var r = confirm("Confirm Delete?");

      if (r) {
        var response = await fetch("/api/game/detail/" + route.params.id, {
          method: "delete",
        });

        if (response.ok) {
          alert("game deleted");
          location.assign("/games");
        } else {
          alert(response.statusText);
        }
      } else {
        alert("cancelled");
      }
    };

    return {
      game,
      updateGame,
      deleteGame,
    };
  },
};
</script>