<template>
  <v-app>
    <Navbar />
    <v-main>
      <FrontPage />
    </v-main>
  </v-app>
</template>

<script>
import { roomService } from "./services/RoomService"
import { reservationService } from "./services/ReservationService"
import { accountService } from "./services/AccountService"

import FrontPage from './routes/FrontPage';
import Navbar from './components/Navbar/Navbar'

export default {
  name: 'App',

  components: {
    FrontPage,
    Navbar,
  },
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

      let sectionReservations = await reservationService.getSectionsReservations(1, 1);
      console.log(sectionReservations);
/*
      
      let reservation = {
        from_date: new Date(),
        to_date: new Date(),
        section: {
          id: 2
        },
        account: {
          id: 1
        }
      }
      let newReservation = await reservationService.addReservation(reservation);
      console.log(newReservation); 
/*
      let deleteResult = await reservationService.deleteReservation(36);
      console.log(deleteResult);*/


      let newReservation = {
        from_date: new Date(),
        to_date: new Date(),
        section: {
          id: 1
        },
        account: {
          id: 1
        }
      }
      let updateResult = await reservationService.updateReservation(2, newReservation);
      console.log(updateResult);

      let account = await accountService.getAccount(2);
      console.log(account);
    }
  }
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
