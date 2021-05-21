<template>
  <div>
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
      <router-link v-if="isAdmin" class="link" to="/reservations">
        <v-btn text>
          <span>Reservasjoner</span>
        </v-btn>
      </router-link>
      
      
        <v-menu offset-y v-if="isLoggedIn" :close-on-content-click="closeOnContentClick">
          <template v-slot:activator="{ on, attrs }">
            <v-btn
              color="#222B45"
              dark
              elevation="0"
              v-bind="attrs"
              v-on="on"
              width="80px"
            >
              <v-icon>account_box</v-icon>
            </v-btn>
          </template>
          <v-list>
            <v-list-item
              v-for="item in items"
              :key="item.id"
              @click="handleListClick(item.id)"
            >
              <v-list-item-title>{{ item.title }}</v-list-item-title>
            </v-list-item>
          </v-list>
        </v-menu>
      <router-link v-else class="link" to="/">
        <v-btn color="#01AB55">
          <span>Logg inn</span>
        </v-btn>
      </router-link>
    </v-app-bar>
  </div>
</template>

<script>
export default {
  name: "Navbar",
  data() {
    return {
      items: [
        { id: 1, title: "Profil" },
        { id: 2, title: "Logg ut" }],
      closeOnContentClick: true,
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
    handleListClick(id) {
      switch(id) {
        case 1: {
          this.toProfilePage();
          break;
        }
        case 2: {
          this.logout();
          break;
        }
      }
    },

    toProfilePage() {
      const account = this.$store.getters.getLoggedInAccount;
      if (!this.$router.currentRoute.path === `/profilepage/${account.id}`) {
        this.$router.push({ name: "ProfilePage", params: { id: account.id }});
      }
    },

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
