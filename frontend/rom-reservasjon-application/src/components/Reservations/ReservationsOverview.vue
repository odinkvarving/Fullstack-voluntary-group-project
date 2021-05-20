<template>
  <div class="reservation-overview">
      <h2>{{ computedDateFormattedDatefns }}</h2>
      <div class="line"></div>
      <div class="time-list">
          <div class="timestamp" v-for="(timestamp, index) in timeList" :key="index">
              <p> {{timestamp}}</p>
              <div class="time-box" v-if="!freeReservations[index].disabled" style="background-color: #192138;"></div>
              <div class="time-box" v-else style="background-color: #C33838"></div>
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
        width: 100%;
        height: 800px;
        background-color: #222B45;
    }

    .reservation-overview h2{
        margin: 15px 0 15px 4%;
    }

    .time-list{
        margin-top: 20px;
    }

    .timestamp{
        display: flex;
        align-items: center;
        margin-left: 5%;
        width: 90%;
    }

    .timestamp p {
        margin-bottom: 0;
        margin-right: 10px;
    }

    .time-box{
        width: 100%;
        border: 1px solid #01AB55;
        opacity: 70%;
        height: 40px;
    }

    .date-picker{
        width: 80%;
    }

    .line{
        width: 90%;
        margin: auto;
        height: 1px;
        background-color: #01AB55;
    }


</style>