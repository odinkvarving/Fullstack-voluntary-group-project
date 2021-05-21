<template>
  <div>
    <v-navigation-drawer v-model="sideNav" app left height="260px" color="#222B45">
      <v-list>
        <v-list-item>
          <router-link class="link" to="/frontpage">
            <v-btn text>
              <v-list-item-title>Hjem</v-list-item-title>
            </v-btn>
          </router-link>
        </v-list-item>

        <v-list-item>
          <router-link v-if="isLoggedIn" class="link" to="/roomfeed">
            <v-btn text>
              <v-list-item-title>Se alle rom</v-list-item-title>
            </v-btn>
          </router-link>
        </v-list-item>

        <v-list-item>
          <router-link v-if="isAdmin" class="link" to="/addaccount">
            <v-btn text>
              <v-list-item-title>Lag bruker</v-list-item-title>
            </v-btn>
          </router-link>
        </v-list-item>

        <v-list-item>
          <router-link v-if="isAdmin" class="link" to="/reservations">
            <v-btn text>
              <v-list-item-title>Reservasjoner</v-list-item-title>
            </v-btn>
          </router-link>
        </v-list-item>

        <v-list-item>
          <v-btn v-if="isLoggedIn" text @click="logout">
            <v-list-item-title>Logg ut</v-list-item-title>
          </v-btn>
          <router-link v-else class="link" to="/">
            <v-btn text>
              <v-list-item-title>Logg inn</v-list-item-title>
            </v-btn>
          </router-link>
        </v-list-item>
      </v-list>
    </v-navigation-drawer>

    <v-app-bar color="#222B45" absolute app>
      <router-link to="/frontpage">
        <div class="d-flex align-center">
          <v-img contain src="../../assets/Logo.png" width="40" />
        </div>
      </router-link>

      <v-spacer></v-spacer>
      <v-app-bar-nav-icon class="hidden-sm-and-up" @click.stop="sideNav = !sideNav"></v-app-bar-nav-icon>

      <div class="nav-elements hidden-xs-only">
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

        <v-btn v-if="isLoggedIn" color="#01AB55" @click="logout">
          <span>Logg ut</span>
        </v-btn>
        <router-link v-else class="link" to="/">
          <v-btn color="#01AB55">
            <span>Logg inn</span>
          </v-btn>
        </router-link>
      </div>
    </v-app-bar>
  </div>
</template>

<script>
export default {
  name: "Navbar",
  components: {},

  data() {
    return {
      sideNav: false,
    };
  },
  computed: {
    isLoggedIn() {
      return this.$store.getters.isAuthenticated;
    },

    isAdmin() {
      return this.$store.getters.getLoggedInAccount.is_admin;
    },
  },
  methods: {
    logout() {
      this.$store.dispatch("logout");
      if (this.$router.currentRoute.path !== "/") {
        this.$router.push("/");
      }
    },
  },
};
</script>

<style></style>
