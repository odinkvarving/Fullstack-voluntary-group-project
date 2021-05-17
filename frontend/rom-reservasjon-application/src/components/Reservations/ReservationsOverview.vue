<template>
  <div class="reservation-overview">
      <h2>{{ computedDateFormattedDatefns }}</h2>
      <div>------------------------</div>
      <div class="time-list">
          <div class="timestamp" v-for="(timestamp, index) in timeList" :key="index">
              <p> {{timestamp}}</p>
              <div class="time-box" v-if="freeReservations[index]" style="background-color: green"></div>
              <div class="time-box" v-else style="background-color: red"></div>
          </div>
      </div>
      {{ currentDateReservations }}
      {{freeReservations}}
  </div>
</template>

<script>
import { format, parseISO } from 'date-fns'

export default {
    name: "ReservationOverview",
    props: {
        reservations: Array,
        date: String
    },
    data(){
        return{
            timeList: [
                '07:00', '08:00', '09:00', '10:00', '11:00',
                '12:00', '13:00', '14:00', '15:00', '16:00', 
                '17:00', '18:00', '19:00', '20:00', '21:00',
                '22:00','23:00',
            ],
            isFreeList:[
                true, true, true, true, true,
                true, true, true, true, true,
                true, true, true, true, true,
                true, true
            ]
        }
    },
    computed: {
        currentDateReservations(){
            if(this.reservations === undefined){
                return [];
            }else{
                return this.reservations.filter(reservation => {
                    return reservation.from_date.split("T")[0] === this.date;
                });
            }
        },
        freeReservations(){
            let isFreeList = [...this.isFreeList]
            if(this.reservations === undefined){
                return [];
            }else{
                for(let i = 0; i < this.currentDateReservations.length; i ++){
                    let from_time = this.currentDateReservations[i].from_date.split("T")[1].split(":")[0];
                    let from_time_hours = parseInt(from_time.split(":")[0]);

                    let to_time = this.currentDateReservations[i].to_date.split("T")[1].split(":")[0];
                    let to_time_hours = parseInt(to_time.split(":")[0]);

                    let totalHours = to_time_hours - from_time_hours;
                    for(let j = 0; j < this.timeList.length; j ++ ){
                        if(from_time === this.timeList[j].split(":")[0]){
                            for(let k = j; k < (j + totalHours); k ++){
                                isFreeList[k] = false;
                            }
                        }
                    }
                }
                return isFreeList;
            }
        },
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