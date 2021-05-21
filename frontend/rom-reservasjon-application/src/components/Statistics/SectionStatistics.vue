<template>
  <div class="statistics">
    <v-container>
      <v-row align="center">
        <v-col cols="12" sm="5">
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
                :label="!isDatesSelected ? 'Velg periode' : dates[0] + ' - ' + dates[1]"
                prepend-icon="mdi-calendar"
                readonly
                v-bind="attrs"
                v-on="on"
                outlined
                hide-details
              ></v-text-field>
            </template>
            <v-date-picker
              v-model="dates"
              range
              @change="datesChosen()"
            ></v-date-picker>
          </v-menu>
        </v-col>
      <v-col cols="12" sm="7">
        <v-card color="#222b45">
          <v-card-text v-if="isDatesSelected">
            <span>
            Antall ulike reservasjoner denne perioden:

            </span>

            <span class="text-h5" style="padding-left: 10px">{{ nReservations }}</span>
          </v-card-text>
        </v-card>
      </v-col>
      </v-row>
    </v-container>

    <v-row>
      <v-col>
        <v-container fluid>
          <v-sparkline
            :fill="fill"
            :line-width="width"
            :padding="padding"
            :smooth="radius || false"
            :labels="datesBetweenLabels"
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
import moment from "moment";

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
      datesBetweenLabels: [],
      datesBetween: [],
      menu2: false,
      isDatesSelected: false,

      fill: true,
      padding: 10,
      radius: 5,
      width: 2,
    };
  },
  computed: {
    nReservations(){
      if(this.isDatesSelected){
        let dateFrom;
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

        return this.section.inReservations.filter(reservation => {
          let reservationFromMoment = moment(reservation.from_date.split("T")[0], "YYYY-MM-DD");

          if((reservationFromMoment.isAfter(dateFrom) && reservationFromMoment.isBefore(dateTo)) || reservationFromMoment.isSame(dateFrom) || reservationFromMoment.isSame(dateTo)){
            return true;
          }else{
            return false;
          }
        }).length;
      }else{
        return 0;
      }
    },
    value(){
      let valueList = [];
      this.datesBetween.forEach(date => {
        let currentDateReservations = this.section.inReservations.filter(res => {
          let date1 = res.from_date.split("T")[0];
          let month = `${date.getMonth()+1}`;
          if(parseInt(month) < 10){
            month = `0${date.getMonth()+1}`
          }
          let day = `${date.getDate()}`;
          if(parseInt(day) < 10){
            day = `0${date.getDate()}`
          }
          let date2 = `${date.getFullYear()}-${month}-${day}`;
          return date1 === date2;
        })
        let hours = 0;
        currentDateReservations.forEach(res => {
          let toHours = parseInt(res.to_date.split("T")[1].split(":")[0]);
          let fromHours = parseInt(res.from_date.split("T")[1].split(":")[0]);

          hours += (toHours - fromHours)
        });
        valueList.push(hours);
      })
      return valueList;
    }
  },
  methods: {
    datesChosen() {
      this.isDatesSelected = true;
      this.getDatesBetween();
    },
    getDatesBetween() {
      if (this.isDatesSelected) {
        this.datesBetween = [];
        this.datesBetweenLabels = [];
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
          console.log(e.toString().substring(4, 15));
          this.datesBetweenLabels.push(e.toString().substring(4, 15));
          this.datesBetween.push(e);
        });
      }
    },
  },
};
</script>
