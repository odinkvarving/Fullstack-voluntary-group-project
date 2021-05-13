<template>
  <div>
    <router-view />
  </div>
</template>

<script>

export default {
  name: "App",
  components: {
  },
  /*data(){
    return {
      welcomeText: this.$store.state.welcomeText
    }
  }*/
}
</script>

<style>
  @import url("https://fonts.googleapis.com/css2?family=Inter"); /* :wght@300;400&display=swap */

  body {
    background-color: #192138;
    font-family: "Inter";
    color: white;
  }
</style>