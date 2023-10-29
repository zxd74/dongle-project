<template>
  <div>
      <!-- <iframe id="script"></iframe> -->
  </div>
</template>
<script>
export default {
  name: "basetable",
  data() {
    return {
      labelPosition: 'left',
      url: "./static/vuetable.json",
    };
  },
  created() {
      console.log(this.$route.params.script)
      document.write(this.$route.params.script)
  },

  methods: {
  }
};
</script>