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
          <trend-chart
            v-if="dataset"
            :datasets="[{ data: dataset }]"
            :labels="{
              xLabels: xLabels,
              yLabels: 3,
            }"
            :min="0"
            :grid="{
              verticalLines: true,
              verticalLinesNumber: 1,
              horizontalLines: true,
              horizontalLinesNumber: 1,
            }"
          />
        </v-col>
      </v-row>
    </v-container>
  </div>
</template>

<script>
import { reservationService } from "../../services/ReservationService.js"

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
      menu2: false,
      isDatesSelected: false,
      nReservations: null,
    };
  },

  methods: {

    datesChosen() {
      this.isDatesSelected = true;
    },

    async fetchData() {
        let result = await reservationService.getSectionsReservations(this.$route.params.roomId, this.$route.params.sectionId);


        this.nReservations = result.filter
    }
  },
};
</script>
