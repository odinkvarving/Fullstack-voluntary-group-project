<template>
  <v-app-bar app color="#222B45" dark hide-on-scroll>
    <router-link to="/">
      <div class="d-flex align-center">
        <v-img contain src="../../assets/Logo.png" width="40" />
      </div>
    </router-link>

    <v-spacer></v-spacer>

    <router-link class="link" to="/">
      <v-btn text>
        <span>Hjem</span>
      </v-btn>
    </router-link>
    <router-link class="link" to="/roomfeed">
      <v-btn text>
        <span>Se alle rom</span>
      </v-btn>
    </router-link>

    <router-link v-if="isLoggedIn" class="link" to="/roomfeed">
      <v-btn color="#01AB55">
        <span>Se alle rom</span>
      </v-btn>
    </router-link>
    <router-link v-else class="link" to="/login">
      <v-btn color="#01AB55">
        <span>Logg inn</span>
      </v-btn>
    </router-link>
  </v-app-bar>
</template>

<script>
export default {
  name: "Navbar",
  data() {
    return {
    };
  },
  computed: {
    isLoggedIn(){
      return this.$store.getters.isAuthenticated;
    }
  },
};
</script>

<style></style>
