<template>
  <div class="reservationAdminPage">
    <ReservationAdminPage />
  </div>
</template>

<script>
import ReservationAdminPage from "../components/Reservations/ReservationAdminPage.vue";

export default {
  name: "Reservation",
  components: {
    ReservationAdminPage,
  },
  data() {
    return {};
  },
  mounted(){
      if(!this.$store.getters.isAuthenticated){
          this.$router.push("/");
      }
  }
};
</script>

<style>
.reservationAdminPage {
  background-color: #192138;
  height: 100vh;
}
</style>
