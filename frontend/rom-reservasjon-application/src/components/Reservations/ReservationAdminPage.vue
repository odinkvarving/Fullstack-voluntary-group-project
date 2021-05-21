<template>
  <div>
    <v-container v-if="!isDataReady">
      <v-row align="center" justify="center">
        <v-progress-circular
          indeterminate
          color="green"
          :size="70"
        ></v-progress-circular>
      </v-row>
    </v-container>
    <v-container v-else fill-height>
      <v-row>
        <v-col cols="12">
          <span class="text-h4">{{ roomName }}</span>
        </v-col>
        <v-col cols="12" sm="6">
          <v-card outlined color="#222b45">
            <v-card-text>
              <v-container>
                <v-row>
                  <v-col cols="6">
                    Seksjon
                  </v-col>
                  <v-col cols="6"><span class="font-weight-bold">{{
                      section.name
                    }}</span></v-col
                  >
                </v-row>

                <v-row>
                  <v-col cols="6">
                    Antall
                  </v-col>
                  <v-col cols="6"
                    ><span class="font-weight-bold">
                      {{ reservation.reservation.number_of_people }}</span
                    >
                  </v-col>
                </v-row>

                <v-row>
                  <v-col cols="6">
                    Dato
                  </v-col>
                  <v-col cols="6" v-if="!inEditMode"
                    ><span class="font-weight-bold">
                      {{ reservation.reservation.from_date.slice(0, 10) }}
                    </span></v-col
                  >
                  <v-col cols="6" v-else>
                    <v-menu
                      ref="menu"
                      v-model="dateMenu"
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
                          color="green"
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
                  <v-col cols="6" v-if="!inEditMode"
                    ><span class="font-weight-bold"> {{ time }} </span></v-col
                  >
                </v-row>

                <v-row>
                  <v-col cols="6">
                    Beskrivelse
                  </v-col>
                  <v-col cols="12">
                    <v-card outlined color="#222b45">
                      <v-card-text
                        ><span class="font-weight-bold">
                          {{ section.description }}</span
                        >
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
                <span class="text-h5 font-weight-medium">Bruker</span>
              </v-col>
              <v-col>
                <v-row>
                  <v-col cols="6">Navn</v-col>
                  <v-col
                    ><span class="font-weight-bold">{{
                      account.name
                    }}</span></v-col
                  >
                </v-row>
                <v-row>
                  <v-col cols="6">E-post</v-col>
                  <v-col
                    ><span class="font-weight-bold">{{
                      account.email
                    }}</span></v-col
                  >
                </v-row>
                <v-row>
                  <v-col cols="6">Telefonnummer</v-col>
                  <v-col>
                    <span class="font-weight-bold">{{
                      account.phone
                    }}</span></v-col
                  >
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
                    <span class="text-h5 font-weight-medium">Utstyr</span>
                  </v-col>
                  <v-col>
                    <v-row>
                      <v-col cols="12"
                        ><span class="font-weight-bold">
                          {{ section.equipmentDescription }}
                        </span></v-col
                      >
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
  </div>
</template>

<script>
import { reservationService } from "../../services/ReservationService";
import { adminService } from "../../services/AdminService";
import { accountService } from "../../services/AccountService";
import { sectionService } from "../../services/SectionService";
export default {
  name: "ReservationAdminPage",
  data() {
    return {
      reservation: null,
      reservationId: null,
      account: null,
      accountId: null,
      section: null,
      sectionId: null,

      roomName: null,
      date: null,
      time: null,

      isDataReady: false,
      inEditMode: false,
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

    this.accountId = this.reservation.accountId;
    this.account = await accountService.getAccount(this.accountId);

    this.sectionId = this.reservation.sectionId;
    this.section = await sectionService.getSection(this.sectionId);

    this.findTime();

    this.isDataReady = true;
  },

  methods: {
    findTime() {
      this.date = this.reservation.reservation.from_date.slice(0, 10);
      let startTime = this.reservation.reservation.from_date.slice(11, 16);
      let endTime = this.reservation.reservation.to_date.slice(11, 16);
      this.time = startTime + " - " + endTime;
    },

    editButtonClicked() {
      this.inEditMode = !this.inEditMode;
    },

    editReservation() {},

    async deleteReservation() {
      if (confirm("Er du sikker på at du vil slette reservasjonen?")) {
        await reservationService.deleteReservation(this.reservationId);
      }
    },

    async updateReservation() {
      let reservation = {
        reservation: {
          id: this.reservationId,
          from_date: this.from_date,
          to_date: this.to_date,
          number_of_people: this.number_of_people,
        },
        accountId: this.accountId,
        sectionId: this.sectionId,
      };

      await reservationService.updateReservation(
        this.reservationId,
        reservation
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
