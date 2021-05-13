<template>
  <v-app>
    <v-app-bar
      app
      color="primary"
      dark
    >
      <div class="d-flex align-center">
        <v-img
          alt="Vuetify Logo"
          class="shrink mr-2"
          contain
          src="https://cdn.vuetifyjs.com/images/logos/vuetify-logo-dark.png"
          transition="scale-transition"
          width="40"
        />

        <v-img
          alt="Vuetify Name"
          class="shrink mt-1 hidden-sm-and-down"
          contain
          min-width="100"
          src="https://cdn.vuetifyjs.com/images/logos/vuetify-name-dark.png"
          width="100"
        />
      </div>

      <v-spacer></v-spacer>

      <v-btn
        href="https://github.com/vuetifyjs/vuetify/releases/latest"
        target="_blank"
        text
      >
        <span class="mr-2">Latest Release</span>
        <v-icon>mdi-open-in-new</v-icon>
      </v-btn>
    </v-app-bar>

    <v-main>
      <button @click="testButtonClicked">Test button</button>
    </v-main>
  </v-app>
</template>

<script>
import { roomService } from "./services/RoomService"
import { reservationService } from "./services/ReservationService"

export default {
  name: "App",
  components: {},
  methods: {
    async testButtonClicked(){
      let authenticationRequest = {
        username: "admin@admin.no",
        password: "admin123"
      }

      await this.$store.dispatch("login", authenticationRequest);

      console.log(this.$store.getters.getLoggedInAccount);
      console.log(this.$store.getters.getJwtToken);

      let rooms = await roomService.getRooms();
      console.log(rooms);

      let reservations = await reservationService.getReservations();
      console.log(reservations);
    }
  }
};
</script>
