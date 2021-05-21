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
      <v-alert
        :value="isErrorVisible"
        type="error"
        transition="scale-transition"
        style="width: 100%; margin-top: 40px"
      >
        {{ message }}
      </v-alert>
      <v-alert
        :value="isPopUpVisible"
        type="success"
        transition="scale-transition"
        style="width: 100%; margin-top: 20px"
      >
        {{ message }}
      </v-alert>
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
                          prepend-inner-icon="mdi-calendar"
                          readonly
                          v-bind="attrs"
                          v-on="on"
                          outlined
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
                  <v-col cols="5">
                  <v-select
                    v-if="inEditMode"
                      :items="freeReservations"
                      label="Start"
                      outlined
                      v-model="startTimeValue"
                    ></v-select>
                  </v-col>
                  <v-col cols="5">
                  <v-select
                    v-if="inEditMode"
                    :items="toReservations"
                    label="Slutt"
                    outlined
                    :disabled="startTimeValue === null"
                    v-model="endTimeValue"
                  ></v-select>
                  </v-col>
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
                    <v-btn color="green" v-if="inEditMode" @click="updateReservation">
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
      endTimeValue: null,
      isErrorVisible: false,
      isPopUpVisible: false,
      message: ""
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
              let text = "Oppdatt";
              for (let k = j; k < j + totalHours; k++) {
                if(list[k].value === parseInt(this.reservation.reservation.from_date.split("T")[1].split(":")[0])){
                  text = "Nåværende booking"
                }
                list[k].text = `${list[k].text}: ${text}`
              }
            }
          }
        }
        return list;
      }
    },
    toReservations() {
      let list = [];
      this.freeReservations.forEach(time => {
        if(time.value > this.startTimeValue){
           list.push({text: time.text, value: time.value, disabled: false});
        }
      })
      list.push({ text: "00:00", value: 0, disabled: false });
      return list;
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
        this.isErrorVisible = false;
        this.isPopUpVisible = false;

        let result = await reservationService.deleteReservation(this.reservationId);
        if(result === true){
            console.log("Reservation successfully deleted!");
            this.message = "Sletting av reservasjon vellykket!";
            this.isPopUpVisible = true;
            this.section.inReservations = this.section.inReservations.filter(res => {
              return res.id = this.reservationId
            })
            this.$router.push("/reservations");
        }else{
            console.log("Something went wrong when deleting reservation...");
            this.message = "Upsi! Noe gikk galt når vi prøvde å slette reservasjonen"
            this.isErrorVisible = true;
        }
      }
    },
    findOverlappingReservations(){
      let overlappingList = [];
      this.currentDateReservations.forEach(res => {
        let resFromHour = parseInt(res.from_date.split("T")[1].split(":")[0]);
        let resToHour = parseInt(res.to_date.split("T")[1].split(":")[0]);
        if((this.startTimeValue < resToHour && this.endTimeValue > resToHour) || (this.startTimeValue < resFromHour && this.endTimeValue > resFromHour)
          || (this.startTimeValue < resFromHour && this.endTimeValue > resToHour) || this.startTimeValue === resFromHour || this.endTimeValue === resToHour){
            if(this.reservationId !== res.id){
              overlappingList.push(res);
            }
        }
      })
      return overlappingList;
    },
    async updateReservation() {
      this.isPopUpVisible = false;
      this.isErrorVisible = false;

      let overLappingReservations = this.findOverlappingReservations();
      console.log(overLappingReservations);

      let isOk = true;
      if(overLappingReservations.length > 0){
        isOk = false;
        if(confirm("Denne bookingen overlapper med eksisterende bookinger.\nEr du sikker du vil overkjøre dette?")){
          isOk = true;
        }
      }

      if(isOk){
        let startTimeValueString =
                this.startTimeValue < 10
                  ? `0${this.startTimeValue}`
                  : `${this.startTimeValue}`;
              let endTimeValueString =
                this.endTimeValue < 10
                  ? `0${this.endTimeValue}`
                  : `${this.endTimeValue}`;
  
            let reservation = {
                id: this.reservationId,
                from_date: `${this.date}T${startTimeValueString}:00`,
                to_date: `${this.date}T${endTimeValueString}:00`,
                number_of_people: this.reservation.reservation.number_of_people,
                account: {
                id: this.accountId
                }
            };
  
            if(overLappingReservations.length > 0){
              overLappingReservations.forEach(overlappingReservation => {
                reservationService.deleteReservation(overlappingReservation.id)
              })
            }
  
            let result = await reservationService.updateReservation(
              this.reservationId,
              reservation
            );
  
            if(result !== null){
              console.log("Reservation successfully updated!");
              this.message = "Endring av reservasjon vellykket! Overlappende reservasjoner er slettet og brukerne er varslet."
              this.isPopUpVisible = true;
              this.$store.dispatch("loadRooms");
            }else{
              console.log("Something went wrong when updating reservation...");
              this.message = "Upsi! Noe gikk galt når vi prøvde å oppdatere reservasjonen"
              this.isErrorVisible = true;
            }
      }
    },
  },
};
</script>

<style scoped>

</style>
