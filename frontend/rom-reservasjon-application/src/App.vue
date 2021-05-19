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
  async mounted(){
    if(this.$store.getters.isAuthenticated){
      await this.$store.dispatch("getAccountInfo");
    }
    this.$store.dispatch("loadRooms");
  }
}
</script>

<style>
  body {
    background-color: #192138;
  }

.link {
  text-decoration: none;
}
</style>
