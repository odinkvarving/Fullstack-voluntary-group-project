<template>
  <v-app-bar color="#222B45" absolute>
    <router-link to="/frontpage">
      <div class="d-flex align-center">
        <v-img contain src="../../assets/Logo.png" width="40" />
      </div>
    </router-link>

    <v-spacer></v-spacer>

    <router-link class="link" to="/frontpage">
      <v-btn text>
        <span>Hjem</span>
      </v-btn>
    </router-link>
    <router-link v-if="isLoggedIn" class="link" to="/roomfeed">
      <v-btn text>
        <span>Se alle rom</span>
      </v-btn>
    </router-link>
    <router-link v-if="isAdmin" class="link" to="/addaccount">
      <v-btn text>
        <span>Lag bruker</span>
      </v-btn>
    </router-link>
    
    <v-btn v-if="isLoggedIn" color="#01AB55" @click="logout">
      <span>Logg ut</span>
    </v-btn>
    <router-link v-else class="link" to="/">
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
    },

    isAdmin() {
      return this.$store.getters.getLoggedInAccount.is_admin;
    }
  },
  methods: {
    logout(){
      this.$store.dispatch("logout");
      if(this.$router.currentRoute.path !== "/"){
        this.$router.push("/");
      }
    }
  }
};
</script>

<style></style>
