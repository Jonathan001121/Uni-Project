<template>
  <div class="row">
  <div class="hello col">
    <h1>{{ msg }}</h1>
    <p>This is a modified jumbotron that occupies the entire horizontal space of it parent.</p>
    
  </div>
  <apexchart class="col" width="380" type="pie" :options="options" :series="series"></apexchart>
</div>
</template>

<script>
import { ref , onMounted} from "vue";

  
    
export default {
  name: 'HelloWorld',
  props: {
    msg: String
  },
  setup() {
        const options = ref({});
        const series = ref([]);
        onMounted(async () => {

var response = await fetch("/api/aggregate/groupby");

if (response.ok) {
    var res = await response.json();
    series.value = res.map(a => a.count);
    options.value = { labels: res.map(a => a._id) };
}
});
        return {
            options, series
        }
    }
  
}

</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
.hello{
  border: 1px ridge black;
  float:left;
  height:150px;
  width:50px;
  margin:2em 2em 2em 2em;
}

</style>
