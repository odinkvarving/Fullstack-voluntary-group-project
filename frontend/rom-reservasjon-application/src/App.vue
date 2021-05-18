<template>
  <v-app>
    <Navbar />
    <v-main>
      <router-view />
    </v-main>
  </v-app>
</template>

<script>

import Navbar from './components/Navbar/Navbar'

export default {
  name: 'App',

  components: {
    Navbar,
  },
  mounted(){
    if(this.$store.getters.isAuthenticated){
      this.$store.dispatch("getAccountInfo");
    }
    this.$store.dispatch("loadRooms");
  }
}
</script>

<style>
.link {
  text-decoration: none;
}
</style>
