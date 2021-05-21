<template>
  <div>
    <v-navigation-drawer
      v-model="sideNav"
      app
      left
      color="#222B45"
      disable-resize-watcher
    >
      <v-list>
        <router-link class="link" to="/frontpage">
          <v-list-item link>
            <v-list-item-title class="white--text text-overline">Hjem</v-list-item-title>
          </v-list-item>
        </router-link>

          <router-link v-if="isLoggedIn" class="link" to="/roomfeed">
        <v-list-item link>
            <v-list-item-title class="white--text text-overline">Se alle rom</v-list-item-title>
        </v-list-item>
          </router-link>

          <router-link v-if="isLoggedIn" class="link" to="/equipmentfeed">
        <v-list-item link>
            <v-list-item-title class="white--text text-overline">Se alt utstyr</v-list-item-title>
        </v-list-item>
          </router-link>

          <router-link v-if="isAdmin" class="link" to="/addaccount">
        <v-list-item link>
            <v-list-item-title class="white--text text-overline">Lag bruker</v-list-item-title>
        </v-list-item>
          </router-link>

        <router-link v-if="isAdmin && isLoggedIn" class="link" to="/reservations">
          <v-list-item link>
            <v-list-item-title class="white--text text-overline">Reservasjoner</v-list-item-title>
          </v-list-item>
        </router-link>

        <v-list-item v-if="isLoggedIn" @click="handleListClick(1)">
          <v-list-item-title class="white--text text-overline">Min profil</v-list-item-title>
        </v-list-item>

        <v-list-item link v-if="isLoggedIn" @click="logout">
          <v-list-item-title class="white--text text-overline">Logg ut</v-list-item-title>
        </v-list-item>
          <router-link v-else class="link" to="/">
        <v-list-item link>
            <v-list-item-title class="white--text text-overline">Logg inn</v-list-item-title>
        </v-list-item>
          </router-link>
      </v-list>
    </v-navigation-drawer>

    <v-app-bar color="#222B45" absolute app>
      <router-link to="/frontpage">
        <div class="d-flex align-center">
          <v-img contain src="../../assets/Logo.png" width="40" />
        </div>
      </router-link>

      <v-spacer></v-spacer>
      <v-app-bar-nav-icon
        class="hidden-md-and-up"
        @click.stop="sideNav = !sideNav"
      ></v-app-bar-nav-icon>

      <div class="nav-elements hidden-sm-and-down">
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
        <router-link v-if="isLoggedIn" class="link" to="/equipmentfeed">
          <v-btn text>
            <span>Se alt utstyr</span>
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
        <v-menu
          offset-y
          v-if="isLoggedIn"
          :close-on-content-click="closeOnContentClick"
        >
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
          <v-list color="#222B45">
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
      </div>
    </v-app-bar>
  </div>
</template>

<script>
/**
 * Navbar is the apps navigation bar, and contains routing to all other components.
 * 
 * @author Mattias Agentoft Eggend
 */

export default {
  name: "Navbar",
  components: {},

  data() {
    return {
      sideNav: false,
      items: [
        { id: 1, title: "Profil" },
        { id: 2, title: "Logg ut" },
      ],
      closeOnContentClick: true,
    };
  },

  computed: {
    /**
     * IsLoggedIn checks if the user is logged in.
     */
    isLoggedIn() {
      return this.$store.getters.isAuthenticated;
    },

    /**
     * isAdmin checks if the logged in user has admin rights.
     */
    isAdmin() {
      return this.$store.getters.getLoggedInAccount.is_admin;
    },
  },

  methods: {
    /**
     * handleListClick contains routing information on the dropdown component on the navbar.
     */
    handleListClick(id) {
      switch (id) {
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

    /**
     * toProfilePage routes the user to the users own profile page, based on the user that is logged in.
     */
    toProfilePage() {
      const account = this.$store.getters.getLoggedInAccount;
      this.$router.push({ name: "ProfilePage", params: { id: account.id } });
    },

    /**
     * logout signs out the user, and routes to the login site.
     */
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
