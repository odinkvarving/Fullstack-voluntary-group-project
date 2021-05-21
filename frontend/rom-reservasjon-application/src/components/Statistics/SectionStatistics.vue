<template>
  <div class="statistics">
    <v-container>
      <v-row align="center">
        <v-col cols="10">
          <v-menu
            v-model="menu2"
            :close-on-content-click="false"
            :nudge-right="40"
            transition="scale-transition"
            offset-y
            min-width="auto"
          >
            <template v-slot:activator="{ on, attrs }">
              <v-text-field
                v-model="dates"
                label="Picker without buttons"
                prepend-icon="mdi-calendar"
                readonly
                v-bind="attrs"
                v-on="on"
                outlined
              ></v-text-field>
            </template>
            <v-date-picker
              v-model="dates"
              range
              @change="datesChosen()"
            ></v-date-picker>
          </v-menu>
          <div id="section-reservations">
            <div class="section-reservations-control" v-if="isDatesSelected">
              <strong>Date range:</strong>

              <option>{{ dates[0] }} - {{ dates[1] }}</option>
            </div>
          </div>
          
        </v-col>
      </v-row>
    </v-container>
  </div>
</template>

<script>
import { reservationService } from "../../services/ReservationService.js";

export default {
  name: "SectionStatistics",

  props: {
    section: {
      type: Object,
      required: true,
    },
  },

  data() {
    return {
      dates: [],
      datesBetween: [],
      menu2: false,
      isDatesSelected: false,
      nReservations: null,
    };
  },

  computed: {

  },

  methods: {

    datesChosen() {
      this.isDatesSelected = true;
      this.fetchData();
      this.getDatesBetween();
    },

    async fetchData() {
      if (this.isDatesSelected) {
        let result = await reservationService.findReservationsBySectionId(
          this.$route.params.sectionId
        );

        this.nReservations = result.filter((res) => {
          return (
            new Date(res.reservation.from_date).getTime() >=
              new Date(this.dates[0]).getTime() &&
            new Date(this.dates[1]).getTime() >=
              new Date(res.reservation.to_date).getTime()
          );
        }).length;

        console.log(
          "\nNumber of reservations in this range: " + this.nReservations
        );
      } else {
        console.log("No dates have been chosen yet");
      }
    },

    getDatesBetween() {
      if (this.isDatesSelected) {
        var start = Date.parse(this.dates[0]);
        var end = Date.parse(this.dates[1]);
        for (
          var arr = [], dt = new Date(start);
          dt <= end;
          dt.setDate(dt.getDate() + 1)
        ) {
          arr.push(new Date(dt));
        }
        arr.forEach(e => {
            this.datesBetween.push(e);
        });
        this.datesBetween.forEach(e => {
            console.log(e);
        })
      }
    },
  },
};
</script>
