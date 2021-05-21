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
                  <v-select
                  v-if="inEditMode"
                    :items="freeReservations"
                    label="Start"
                    outlined
                    v-model="startTimeValue"
                  ></v-select>

                  <v-select
                    v-if="inEditMode"
                    :items="toReservations"
                    label="Slutt"
                    outlined
                    :disabled="startTimeValue === null"
                    v-model="endTimeValue"
                  ></v-select>
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
      timeList: [
        "07:00",
        "08:00",
        "09:00",
        "10:00",
        "11:00",
        "12:00",
        "13:00",
        "14:00",
        "15:00",
        "16:00",
        "17:00",
        "18:00",
        "19:00",
        "20:00",
        "21:00",
        "22:00",
        "23:00",
      ],
      startTimeValue: null,
      endTimeValue: null
    };
  },
  computed:{
    allReservations(){
      if(this.section === null){
        return [];
      }else{
        return this.section.inReservations;
      }
    },
    currentDateReservations(){
      if (this.section === null) {
        return [];
      } else {
        return this.allReservations.filter((reservation) => {
          return reservation.from_date.split("T")[0] === this.date;
        });
      }
    },
    freeReservations(){
      let list = [
        { text: "07:00", value: 7, disabled: false },
        { text: "08:00", value: 8, disabled: false },
        { text: "09:00", value: 9, disabled: false },
        { text: "10:00", value: 10, disabled: false },
        { text: "11:00", value: 11, disabled: false },
        { text: "12:00", value: 12, disabled: false },
        { text: "13:00", value: 13, disabled: false },
        { text: "14:00", value: 14, disabled: false },
        { text: "15:00", value: 15, disabled: false },
        { text: "16:00", value: 16, disabled: false },
        { text: "17:00", value: 17, disabled: false },
        { text: "18:00", value: 18, disabled: false },
        { text: "19:00", value: 19, disabled: false },
        { text: "20:00", value: 20, disabled: false },
        { text: "21:00", value: 21, disabled: false },
        { text: "22:00", value: 22, disabled: false },
        { text: "23:00", value: 23, disabled: false },
      ];

      if (this.section === undefined) {
        return list;
      } else {
        for (let i = 0; i < this.currentDateReservations.length; i++) {
          let from_time = this.currentDateReservations[i].from_date
            .split("T")[1]
            .split(":")[0];
          let from_time_hours = parseInt(from_time.split(":")[0]);

          let to_time = this.currentDateReservations[i].to_date
            .split("T")[1]
            .split(":")[0];
          let to_time_hours =
            to_time.split(":")[0] === "00"
              ? 24
              : parseInt(to_time.split(":")[0]);

          let totalHours = to_time_hours - from_time_hours;
          for (let j = 0; j < this.timeList.length; j++) {
            if (from_time === this.timeList[j].split(":")[0]) {
              for (let k = j; k < j + totalHours; k++) {
                list[k].disabled = true;
              }
            }
          }
        }
        return list;
      }
    },
    toReservations() {
      let list = [];
      if (this.startTimeValue === null) {
        return [];
      } else {
        for (
          let i = this.startTimeValue - 6;
          i < this.freeReservations.length;
          i++
        ) {
          list.push({
            text: this.freeReservations[i].text,
            value: this.freeReservations[i].value,
            disabled: this.freeReservations[i].disabled,
          });
          if (this.freeReservations[i].disabled === true) {
            break;
          }
        }
        if (list[list.length - 1].value === 23) {
          list.push({ text: "00:00", value: 24, disabled: false });
        }

        list[list.length - 1].disabled = false;

        return list;
      }
    },
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
