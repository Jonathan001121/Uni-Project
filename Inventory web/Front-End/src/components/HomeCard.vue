<template>
  <div class="row">
    <div
      class="card col-12 col-sm-6 col-md-6 col-lg-3 col-xl-3"
      style="width: 18rem"
    >
      <a class="col" href="/books"
        ><img class="card-img-top" src="books.jpeg" alt="Card image cap" />
      </a>
      <div class="card-body">
        <h5 class="card-title">Books</h5>
      </div>
    </div>

    <div
      class="card col col-12 col-sm-6 col-md-6 col-lg-3 col-xl-3"
      style="width: 18rem"
    >
    <a class="col" href="/games"
        ><img class="card-img-top" src="games.jpeg" alt="Card image cap" />
      </a>
      <div class="card-body">
        <h5 class="card-title">Games</h5>
      </div>
    </div>

    <div
      class="card col col-12 col-sm-6 col-md-6 col-lg-3 col-xl-3"
      style="width: 18rem"
    >
    <a class="col" href="/gifts"
        >
      <img class="card-img-top" src="gifts.jpeg" alt="Card image cap" />
    </a>
      <div class="card-body">
        <h5 class="card-title">Gifts</h5>
      </div>
    </div>

    <div
      class="card col col-12 col-sm-6 col-md-6 col-lg-3 col-xl-3"
      style="width: 18rem"
    >
    <a class="col" href="/materials"
        >
      <img class="card-img-top" src="materials.jpeg" alt="Card image cap" />
      </a>
      <div class="card-body">
        <h5 class="card-title">Materials</h5>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "HomeCard",
};
</script>
<style scoped>
img {
  margin: auto;
  object-fit: cover;
  width: 200px;
  height: 350px;
}
.card{
    text-align: center;
    margin-right: 5em;
}
</style>
