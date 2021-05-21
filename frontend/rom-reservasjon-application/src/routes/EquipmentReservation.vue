<template>
  <div class="equipment-reservation">
    <router-link to="/equipmentfeed">
      <v-btn
        style="margin-left: 30px; position: absolute; top: 30px; left: 0px;"
        color="#01AB55"
      >
        <v-icon left>
          mdi-arrow-left-bold
        </v-icon>
        <span>Tilbake</span>
      </v-btn>
    </router-link>
    <v-container>
      <v-row align="center" justify="center">
        <h1>Velg leieperiode</h1>
      </v-row>


      <v-row justify="center" class="pt-6">
          <v-date-picker
            v-model="dates"
            :min="new Date().toISOString().substr(0, 10)"
            range
            :allowed-dates="allowedDates"
            color="green lighten-1"
            no-title
          ></v-date-picker>
      </v-row>
        <v-row justify="center" class="pt-8">
            <v-btn color="green" @click="registerEquipmentReservation"
              ><span>Reserver</span></v-btn>
        </v-row>



    </v-container>
  </div>
</template>

<script>
/**
 *  EquipmentReservation is a component for making reservations of a single equipment.
 *
 * @author Magnus Bredeli
 */
import moment from "moment";
import { equipmentReservationService } from "../services/EquipmentReservationService";

export default {
  name: "EquipmentReservation",
  data() {
    return {
      dates: [],
    };
  },
  computed: {
      /**
       * equipment returns equipment given a specific id.
       */
    equipment() {
      return this.$store.getters.getEquipment.filter(
        (e) => e.id === parseInt(this.$route.params.id)
      )[0];
    },

    /**
     * equipmentReservations returns the equipments reservation status if defined.
     */
    equipmentReservations() {
      if (this.equipment === undefined) {
        return [];
      } else {
        return this.equipment.inReservations;
      }
    }
  },
    methods: {
        /**
         * allowedDates tells us which dates the equipment isn't occupied
         */
        allowedDates(val){
            let date = moment(val, "YYYY-MM-DD");
            let isValid = true;
            this.equipmentReservations.forEach(reservation => {
                let reservationFromMoment = moment(reservation.from_date.split("T")[0], "YYYY-MM-DD");
                let reservationToMoment = moment(reservation.to_date.split("T")[0], "YYYY-MM-DD");
                if(date.isAfter(reservationFromMoment.subtract(1, "day")) && date.isBefore(reservationToMoment.add(1, "day"))){
                    isValid = false;
                }
            })

            return isValid;
        },
        /**
         * registerEquipmentReservation registers a reservation on the given equipment.
         */
        async registerEquipmentReservation(){
            
            let dateFrom;
            if(this.dates.length === 1){
                this.dates = ["", this.dates[0]];
            }
            if(this.dates[0] === ""){
                dateFrom = moment(new Date().toISOString().substr(0, 10), "YYYY-MM-DD")
            }else{
                dateFrom = moment(this.dates[0], "YYYY-MM-DD");
            }
            let dateTo = moment(this.dates[1], "YYYY-MM-DD");

            if(dateFrom.isAfter(dateTo)){
                dateFrom = moment(this.dates[1], "YYYY-MM-DD");
                dateTo = moment(this.dates[0], "YYYY-MM-DD");
            }

            let isOverlapping = false;
            this.equipmentReservations.forEach(reservation => {
                let reservationFromMoment = moment(reservation.from_date.split("T")[0], "YYYY-MM-DD");
                let reservationToMoment = moment(reservation.to_date.split("T")[0], "YYYY-MM-DD");

                if((dateFrom.isAfter(reservationFromMoment) && dateFrom.isBefore(reservationToMoment)) || 
                    dateTo.isAfter(reservationFromMoment) && dateFrom.isBefore(reservationFromMoment)){
                        isOverlapping = true;
                        return;
                }
            })

            if(isOverlapping){
                console.log("ERROR RESERVATION IS OVERLAPPING");
                alert("Kan ikke reservere utstyret denne datoen da den overlapper en annen reservasjon!");
            }else{
                let equipmentReservation = {
                    from_date: dateFrom.add(1, "day"),
                    to_date: dateTo.add(1, "day"),
                    equipment: {
                        id: this.$route.params.id
                    },
                    account: {
                        id: this.$store.getters.getLoggedInAccount.id
                    }
                }

                let result = await equipmentReservationService.addEquipmentReservation(equipmentReservation);
                console.log(result);
                if(result){
                    console.log("Equipment reservation successfully created");
                    this.equipmentReservations.push({from_date: equipmentReservation.from_date.toISOString(), to_date: equipmentReservation.to_date.toISOString()});
                }else{
                    console.log("Something went wrong ehn trying to create equipment reservation...");
                }
            }
        }
    }
}
</script>

<style scoped>
.equipment-reservation {
  min-height: 100vh;
  padding-top: 100px;
  background-color: #192138;
}
</style>
