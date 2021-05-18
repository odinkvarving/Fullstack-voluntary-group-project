<template>
  <div class="reservation-overview">
      <h2>{{ computedDateFormattedDatefns }}</h2>
      <div class="time-list">
          <div class="timestamp" v-for="(timestamp, index) in timeList" :key="index">
              <p> {{timestamp}}</p>
              <div class="time-box" v-if="!freeReservations[index].disabled" style="background-color: green"></div>
              <div class="time-box" v-else style="background-color: red"></div>
          </div>
      </div>
  </div>
</template>

<script>
import { format, parseISO } from 'date-fns'

export default {
    name: "ReservationOverview",
    props: {
        reservations: Array,
        date: String,
        freeReservations: Array,
        timeList: Array
    },
    computed: {

        computedDateFormattedDatefns () {
            return this.date ? format(parseISO(this.date), 'EEEE, MMMM do yyyy') : ''
        },
    }
}
</script>

<style scoped>
    .reservation-overview{
        border: 2px solid #01AB55;
        border-radius: 4px;
        width: 400px;

    }

    .timestamp{
        display: flex;
    }

    .time-box{
        width: 80%;
        border: 1px solid #01AB55;
    }

    .date-picker{
        width: 80%;
    }
</style>