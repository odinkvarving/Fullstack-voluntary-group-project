<template>
  <div class="section">
      {{ section }}
        <div class="date-picker">
        <v-menu
            v-model="menu1"
            :close-on-content-click="false"
            max-width="290"
            >
          <template v-slot:activator="{ on, attrs }">
            <v-text-field
              :value="computedDateFormattedMomentjs"
              clearable
              prepend-inner-icon="mdi-calendar"
              append-icon="mdi-menu-down"
              label="Dato"
              v-bind="attrs"
              v-on="on"
              @click:clear="date = null"
            ></v-text-field>
          </template>
          <v-date-picker
            v-model="date"
            @change="menu1 = false"
          ></v-date-picker>
        </v-menu>
      </div>
      <ReservationsOverview :reservations="reservations" :date="date" />
  </div>
</template>

<script>
import ReservationsOverview from "../components/Reservations/ReservationsOverview"
import moment from 'moment'
import { format, parseISO } from 'date-fns'

export default {
    name: "Section",
    components: {
        ReservationsOverview
    },
    data(){
        return{
            date: "",
            menu1: false,
        }
    },
    computed: {
        section(){
            if(this.$store.getters.getRooms.length !== 0){
                let room = this.$store.getters.getRooms.filter((room) =>{
                    return room.id === parseInt(this.$route.params.roomId);
                })[0];
                return room.sections.filter(section => section.id === parseInt(this.$route.params.sectionId))[0];
            }else{
                return {};
            }
        },
        reservations(){
            console.log(this.section);
            return this.section.inReservations;
        },
        computedDateFormattedMomentjs () {
            return this.date ? moment(this.date).format('dddd, MMMM Do YYYY') : ''
        },
        computedDateFormattedDatefns () {
            return this.date ? format(parseISO(this.date), 'EEEE, MMMM do yyyy') : ''
        },
    }
}
</script>

<style>
    .section{
        background-color: #192138;
        min-height: 100vh;
        padding-top: 100px;
    }
</style>