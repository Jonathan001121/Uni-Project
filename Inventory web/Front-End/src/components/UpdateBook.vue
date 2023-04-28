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
        <li class="breadcrumb-item"><a href="/books/">Books</a></li>
        <li class="breadcrumb-item active" aria-current="page">Book Detail</li>
      </ol>
    </nav>
    <form class="container">
      <div class="row mb-3">
        <label for="title" class="col-sm-2 col-form-label">Title</label>
        <div class="col-sm-10">
          <input
            v-model="book.title"
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
            v-model="book.category"
          >
            <option selected>Open this select menu</option>
            <option value="Crime">Crime</option>
            <option value="Classics">Classics</option>
            <option value="Fairy tales">Fairy tales</option>
            <option value="Horror">Horror</option>
            <option value="Adventure stories">Adventure stories</option>
            <option value="Historical fiction">Historical fiction</option>
            <option value="Fantasy">Fantasy</option>
          </select>
        </div>
      </div>

      <div class="row mb-3">
        <label for="author" class="col-sm-2 col-form-label">Author</label>
        <div class="col-sm-10">
          <input
            class="form-control"
            v-model="book.author"
            placeholder="author name"
          />
        </div>
      </div>

      <div class="row mb-3">
        <label for="publisher" class="col-sm-2 col-form-label">Publisher</label>
        <div class="col-sm-10">
          <input
            class="form-control"
            v-model="book.publisher"
            placeholder="publisher"
          />
        </div>
      </div>

      <div class="row mb-3">
        <label for="year" class="col-sm-2 col-form-label">Year</label>
        <div class="col-sm-10">
          <input class="form-control" v-model="book.year" placeholder="year" />
        </div>
      </div>

      <div class="row mb-3">
        <label for="location" class="col-sm-2 col-form-label">Location</label>
        <div class="col-sm-10">
          <input
            v-model="book.location"
            class="form-control"
            placeholder="location name in warehouse"
          />
        </div>
      </div>

      <div class="row mb-3">
        <label for="ISBN" class="col-sm-2 col-form-label">ISBN</label>
        <div class="col-sm-10">
          <input
            class="form-control"
            v-model="book.ISBN"
            placeholder="ISBN number"
          />
        </div>
      </div>

      <div class="row mb-3">
        <label for="imaegURL" class="col-sm-2 col-form-label">image</label>
        <div class="col-sm-10">
          <input
            class="form-control"
            v-model="book.imageURL"
            placeholder="image URL"
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
            v-model="book.description"
            placeholder="description show on listing page"
          />
        </div>
      </div>

      <div class="row mb-3">
        <label for="remarks" class="col-sm-2 col-form-label">Remark</label>
        <div class="col-sm-10">
          <input
            class="form-control"
            v-model="book.remarks"
            placeholder="internal remarks"
          />
        </div>
      </div>

      <button class="btn btn-primary" type="submit" @click="updateBook()">
        Update
      </button>
      <button class="btn btn-danger" type="button" @click="deleteBook()">
        Delete
      </button>
    </form>
  </div>
</template>

<script >
import { ref, onMounted } from "vue";
import { useRoute } from "vue-router";
export default {
  name: "UpdateBook",
  setup() {
    const book = ref({});
    const route = useRoute();
    onMounted(async () => {
      var response = await fetch("/api/book/detail/" + route.params.id);
      if (response.ok) {
        book.value = await response.json();
      }
    });

    const updateBook = async function () {
      // event.preventDefault();
      var r = confirm("Confirm Update?");
      if (r) {
        delete book.value._id;

        var response = await fetch("/api/book/detail/" + route.params.id, {
          method: "put",
          headers: {
            // 'Content-Type': 'application/x-www-form-urlencoded',
            "Content-Type": "application/json",
          },
          // body: new URLSearchParams(new FormData(event.target))
          body: JSON.stringify(this.book),
        });

        if (response.ok) {
          var text = await response.text();
          alert(text);
          location.assign("/books");
        } else {
          alert(response.statusText);
        }
      } else {
        alert("cancelled");
      }
    };

    const deleteBook = async function () {
      var r = confirm("Confirm Delete?");

      if (r) {
        var response = await fetch("/api/book/detail/" + route.params.id, {
          method: "delete",
        });

        if (response.ok) {
          alert("book deleted");
          location.assign("/books");
        } else {
          alert(response.statusText);
        }
      } else {
        alert("cancelled");
      }
    };

    return {
      book,
      updateBook,
      deleteBook,
    };
  },
};
</script>