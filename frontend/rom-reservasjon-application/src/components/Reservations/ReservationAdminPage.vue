<template>
  <v-container fill-height v-if="isDataReady">
    <v-row>
      <v-col cols="12">
        <h1>{{ roomName }}</h1>
      </v-col>
      <v-col cols="12" sm="6">
        <v-card outlined color="#222b45">
          <v-card-text>
            <v-container>
              <v-row>
                <v-col cols="6">
                  Seksjon
                </v-col>
                <v-col cols="6"> {{ section }} </v-col>
              </v-row>

              <v-row>
                <v-col cols="6">
                  Antall
                </v-col>
                <v-col cols="6"> {{ reservation.number_of_people }} </v-col>
              </v-row>

              <v-row>
                <v-col cols="6">
                  Dato
                </v-col>
                <v-col cols="6"> {{ date }} </v-col>
              </v-row>

              <v-row>
                <v-col cols="6">
                  Klokkeslett
                </v-col>
                <v-col cols="6"> {{ time }} </v-col>
              </v-row>

              <v-row>
                <v-col cols="6">
                  Beskrivelse
                </v-col>
                <v-col cols="12">
                  <v-card outlined color="#222b45">
                    <v-card-text>
                      {{ description }}
                    </v-card-text>
                  </v-card>
                </v-col>
              </v-row>
            </v-container>
          </v-card-text>
        </v-card>
      </v-col>
      <v-col cols="12" sm="6">
        <v-card outlined color="#222b45">
          <v-card-text>
            <v-col cols="12">
              <h2>Bruker</h2>
            </v-col>
            <v-col>
              <v-row>
                <v-col cols="6">Navn</v-col>
                <v-col>{{ username }}</v-col>
              </v-row>
              <v-row>
                <v-col cols="6">E-post</v-col>
                <v-col>{{ userEmail }}</v-col>
              </v-row>
              <v-row>
                <v-col cols="6">Telefonnummer</v-col>
                <v-col>{{ userPhone }}</v-col>
              </v-row>
            </v-col>
          </v-card-text>
        </v-card>
        <div style="height: 24px"></div>
        <v-card outlined color="#222b45">
          <v-card-text>
            <v-container>
              <v-row>
                <v-col cols="12">
                  <h2>Utstyr</h2>
                </v-col>
                <v-col>
                  <v-row>
                    <v-col cols="6">Data</v-col>
                    <v-col cols="6">Data</v-col>
                    <v-col cols="6">Data</v-col>
                  </v-row>
                </v-col>
              </v-row>
            </v-container>
          </v-card-text>
        </v-card>
        <v-row justify="space-between" class="pt-5">
          <v-col cols="12" md="6">
            <v-btn color="green">Endre reservasjon</v-btn>
          </v-col>
          <v-col cols="12" md="6" align="end">
            <v-btn color="error">Slett reservasjon</v-btn>
          </v-col>
        </v-row>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
import { reservationService } from "../../services/ReservationService";
import { adminService } from "../../services/AdminService"
export default {
  name: "ReservationAdminPage",
  data() {
    return {
      reservation: null,

      roomName: "Navn",
      section: "data",
      amount: "",
      date: "",
      time: "",
      description: "Lorem",

      username: "",
      userEmail: "",
      userPhone: "",

      isDataReady: false,
    };
  },

  async mounted() {
    adminService.isLoggedIn();
    await adminService.isAdmin();

    const id = parseInt(this.$route.params.reservationId);
    this.reservation = await reservationService.getReservation(id);
    console.log(this.reservation);

    this.findTime();
    this.isDataReady = true;
  },

  methods: {
    findTime() {
      this.date = this.reservation.from_date.slice(0, 10).replaceAll("-", "/");
      let startTime = this.reservation.from_date.slice(11, 16);
      let endTime = this.reservation.to_date.slice(11, 16);
      this.time = startTime + " - " + endTime;
    },
  },
};
</script>

<style></style>
