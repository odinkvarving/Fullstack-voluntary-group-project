<template>
  <v-container fill-height pt-16>
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
                <v-col cols="6" v-if="!inEditMode"> {{ section }} </v-col>
                <v-col cols="6" v-else>
                  <v-text-field
                    color="green"
                    outlined
                    hide-details
                    height="22px"
                    :placeholder="section"
                  ></v-text-field
                ></v-col>
              </v-row>

              <v-row>
                <v-col cols="6">
                  Antall
                </v-col>
                <v-col cols="6" v-if="!inEditMode">
                  {{ reservation.number_of_people }}
                </v-col>
                <v-col cols="6" v-else>
                  <v-text-field
                    color="green"
                    type="number"
                    outlined
                    hide-details
                    height="22px"
                    :placeholder="reservation.number_of_people"
                  ></v-text-field
                ></v-col>
              </v-row>

              <v-row>
                <v-col cols="6">
                  Dato
                </v-col>
                <v-col cols="6" v-if="!inEditMode"> {{ date }} </v-col>
                <v-col cols="6" v-else>
                  <v-menu
                    ref="menu"
                    v-model="menu"
                    :close-on-content-click="false"
                    :return-value.sync="date"
                    transition="scale-transition"
                    offset-y
                    min-width="auto"
                  >
                    <template v-slot:activator="{ on, attrs }">
                      <v-text-field
                        v-model="date"
                        prepend-icon="mdi-calendar"
                        readonly
                        v-bind="attrs"
                        v-on="on"
                      ></v-text-field>
                    </template>
                    <v-date-picker v-model="date" no-title scrollable>
                      <v-spacer></v-spacer>
                      <v-btn text color="primary" @click="dateMenu = false">
                        Cancel
                      </v-btn>
                      <v-btn
                        text
                        color="primary"
                        @click="$refs.menu.save(date)"
                      >
                        OK
                      </v-btn>
                    </v-date-picker>
                  </v-menu>
                </v-col>
              </v-row>

              <v-row>
                <v-col cols="6">
                  Klokkeslett
                </v-col>
                <v-col cols="6" v-if="!inEditMode"> {{ time }} </v-col>
                <v-col cols="6" v-else
                  ><v-menu
                    ref="menu"
                    v-model="startTimeMenu"
                    :close-on-content-click="false"
                    :nudge-right="40"
                    :return-value.sync="time"
                    transition="scale-transition"
                    offset-y
                    max-width="290px"
                    min-width="290px"
                  >
                    <template v-slot:activator="{ on, attrs }">
                      <v-text-field
                        v-model="time"
                        prepend-icon="mdi-clock-time-four-outline"
                        readonly
                        v-bind="attrs"
                        v-on="on"
                      ></v-text-field>
                    </template>
                    <v-time-picker
                      v-if="startTimeMenu"
                      v-model="time"
                      full-width
                      @click:minute="$refs.menu.save(time)"
                      format="24hr"
                    ></v-time-picker> </v-menu
                ></v-col>
              </v-row>

              <v-row>
                <v-col cols="6">
                  Beskrivelse
                </v-col>
                <v-col cols="12" v-if="!inEditMode">
                  <v-card outlined color="#222b45">
                    <v-card-text>
                      {{ description }}
                    </v-card-text>
                  </v-card>
                </v-col>
                <v-col cols="12" v-else>
                  <v-card outlined color="#222b45">
                    <v-card-text>
                      <v-textarea
                        filled
                        color="green"
                        :placeholder="description"
                      ></v-textarea>
                    </v-card-text>
                  </v-card>
                </v-col>
              </v-row>
              <v-row>
                <v-col align="center">
                  <v-btn color="green" v-if="inEditMode">
                    Fullfør
                  </v-btn>
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
          <v-col cols="12" md="6" align="center">
            <v-btn @click="editButtonClicked" color="green"
              >Endre reservasjon</v-btn
            >
          </v-col>
          <v-col cols="12" md="6" align="center">
            <v-btn @click="deleteReservation" color="error"
              >Slett reservasjon</v-btn
            >
          </v-col>
        </v-row>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
import { reservationService } from "../../services/ReservationService";
import { adminService } from "../../services/AdminService";
export default {
  name: "ReservationAdminPage",
  data() {
    return {
      reservation: null,
      reservationId: null,
      account: null,
      accountId: null,

      roomName: "Navn",
      section: "data",
      amount: "",
      date: new Date().toISOString().substr(0, 10),
      time: null,
      description: "Lorem",

      username: "",
      userEmail: "",
      userPhone: "",

      isDataReady: false,
      inEditMode: false,
      startTimeMenu: false,
      dateMenu: false,
    };
  },

  async mounted() {
    adminService.isLoggedIn();
    await adminService.isAdmin();

    this.reservationId = parseInt(this.$route.params.reservationId);
    this.reservation = await reservationService.getReservation(
      this.reservationId
    );

    console.log(this.reservation)
    console.log(this.reservation.accountId);
    this.accountId = this.reservation.accountId;

    //this.findTime();
    this.isDataReady = true;


  },

  methods: {
    findTime() {
      this.date = this.reservation.from_date.slice(0, 10).replaceAll("-", "/");
      let startTime = this.reservation.from_date.slice(11, 16);
      let endTime = this.reservation.to_date.slice(11, 16);
      this.time = startTime + " - " + endTime;
    },

    editButtonClicked() {
      this.inEditMode = !this.inEditMode;
      console.log("Edit mode: " + this.inEditMode);
    },

    editReservation() {},

    async deleteReservation() {
      await reservationService.deleteReservation(this.reservationId);
    },

    async updateReservation() {
      await reservationService.updateReservation(
        this.reservationId,
        this.reservation
      );
      //TODO: Legg til onClick
    },
  },
};
</script>

<style scoped>
>>> .v-text-field .v-input__control .v-input__slot {
  min-height: auto !important;
  display: flex !important;
  align-items: center !important;
}
</style>
