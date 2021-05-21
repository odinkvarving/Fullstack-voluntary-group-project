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
import 'material-design-icons-iconfont/dist/material-design-icons.css'

export default {
  name: 'App',

  components: {
    Navbar,
  },
  async mounted(){
    if(this.$store.getters.isAuthenticated){
      await this.$store.dispatch("getAccountInfo");
    }
    await this.$store.dispatch("loadRooms");
    await this.$store.dispatch("loadSections");
    await this.$store.dispatch("loadEquipment");
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
