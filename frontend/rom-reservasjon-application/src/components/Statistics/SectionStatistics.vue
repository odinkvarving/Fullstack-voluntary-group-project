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
                label="Velg periode"
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

    <v-row>
      <v-col cols="12">
        <v-card>
          <v-card-text>
            Antall ulike reservasjoner denne perioden: {{ nReservations }}
          </v-card-text>
        </v-card>
      </v-col>
      <v-col>
        <v-container fluid>
          <v-sparkline
            :fill="fill"
            :line-width="width"
            :padding="padding"
            :smooth="radius || false"
            :labels="datesBetween"
            :value="value"
            label-size="4"
            auto-draw
          ></v-sparkline>

          <v-divider></v-divider>
        </v-container>
      </v-col>
    </v-row>
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

      fill: true,
      padding: 10,
      radius: 5,
      value: [8, 4, 6, 12, 3, 12],
      width: 2,
    };
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
        console.log("No dates selected");
      }
    },

    getValueForEachDay() {

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
        arr.forEach((e) => {
          console.log(e);
          this.datesBetween.push(e.toString().substring(4, 15));
        });
      }
    },
  },
};
</script>
