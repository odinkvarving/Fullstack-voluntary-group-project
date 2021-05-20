<template>
  <div class="section">
      <div class="title">
          <h1>{{section.name}}</h1>
          <p>{{ section.description }}</p>
      </div>
      <div class="row">
        <div class="time-selection">
            <h3 style="margin-bottom: 20px; opacity: 80%">1. Velg dato</h3>
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
                    label="Dato"
                    v-bind="attrs"
                    v-on="on"
                    outlined
                    @click:clear="date = null"
                    ></v-text-field>
                </template>
                <v-date-picker
                    v-model="date"
                    @change="menu1 = false"
                ></v-date-picker>
                </v-menu>

                <h3 v-if="date !== '' && date !== null" style="opacity: 80%; margin: 10px 0">2. Tidspunkt</h3>
                <v-select
                v-if="date !== '' && date !== null"
                :items="freeReservations"
                label="Start"
                outlined
                v-model="startTimeValue"
                ></v-select>

                <v-select
                :items="toReservations"
                v-if="date !== '' && date !== null"
                label="Slutt"
                outlined
                :disabled="startTimeValue === null"
                v-model="endTimeValue"
                ></v-select>
            </div>
            <h3 v-if="endTimeValue !== null && date !== null" style="opacity: 80%; margin: 10px 0">3. Deltakere</h3>
            <v-text-field
            v-if="endTimeValue !== null && date !== null"
            dense
            hide-details
            label="Antall personer"
            color="green"
            outlined
            type="number"
            v-model="nPersons"
            min="0"
            ></v-text-field>
            <v-btn class="reserve-button" v-if="nPersons && date !== null" color="green" @click="registerReservation" :loading="loading"><span>Reserver</span></v-btn>
        </div>
        <div class="reservations-overview">
                <ReservationsOverview :reservations="reservations" :freeReservations="freeReservations" :date="date" :timeList="timeList"/>
        </div>
      </div>
  </div>
</template>

<script>
import ReservationsOverview from "../components/Reservations/ReservationsOverview"
import moment from 'moment'
import { format, parseISO } from 'date-fns'
import { reservationService } from "../services/ReservationService"

export default {
    name: "Section",
    components: {
        ReservationsOverview
    },
    props: {
        allSections: {
            type: Array,
            required: false
        }
    },
    data(){
        return{
            date: "2021-05-20",
            menu1: false,
            isFreeList:[
                true, true, true, true, true,
                true, true, true, true, true,
                true, true, true, true, true,
                true, true
            ],
            timeList: [
                '07:00', '08:00', '09:00', '10:00', '11:00',
                '12:00', '13:00', '14:00', '15:00', '16:00', 
                '17:00', '18:00', '19:00', '20:00', '21:00',
                '22:00','23:00',
            ],
            startTimeValue: null,
            endTimeValue: null,
            nPersons: null
            ,
            loading: false
        }
    },
    computed: {
        section(){
            if(this.$store.getters.getRooms.length !== 0 && this.$route.params.roomId !== undefined){
                let room = this.$store.getters.getRooms.filter((room) =>{
                    return room.id === parseInt(this.$route.params.roomId);
                })[0];
                return room.sections.filter(section => section.id === parseInt(this.$route.params.sectionId))[0];
            }else{
                return {};
            }
        },
        reservations(){
            if(this.$route.params.sectionId === undefined && this.$route.params.roomId === undefined && this.$store.getters.getRooms.length !== 0){
                let allReservations = [];
                this.allSections.forEach(section => {
                    section.inReservations.forEach(res => {
                        allReservations.push(res);
                    });
                })
                console.log(allReservations);
                return allReservations;
            }
            return this.section.inReservations;
        },
        computedDateFormattedMomentjs () {
            return this.date ? moment(this.date).format('dddd, MMMM Do YYYY') : ''
        },
        computedDateFormattedDatefns () {
            return this.date ? format(parseISO(this.date), 'EEEE, MMMM do yyyy') : ''
        },
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
            let list = [
                {text: '07:00', value: 7, disabled: false},
                {text: '08:00', value: 8, disabled: false},
                {text: '09:00', value: 9, disabled: false},
                {text: '10:00', value: 10, disabled: false},
                {text: '11:00', value: 11, disabled: false},
                {text: '12:00', value: 12, disabled: false},
                {text: '13:00', value: 13, disabled: false},
                {text: '14:00', value: 14, disabled: false},
                {text: '15:00', value: 15, disabled: false},
                {text: '16:00', value: 16, disabled: false},
                {text: '17:00', value: 17, disabled: false},
                {text: '18:00', value: 18, disabled: false},
                {text: '19:00', value: 19, disabled: false},
                {text: '20:00', value: 20, disabled: false},
                {text: '21:00', value: 21, disabled: false},
                {text: '22:00', value: 22, disabled: false},
                {text: '23:00', value: 23, disabled: false},
            ];

            if(this.reservations === undefined){
                return list;
            }else{
                for(let i = 0; i < this.currentDateReservations.length; i ++){
                    let from_time = this.currentDateReservations[i].from_date.split("T")[1].split(":")[0];
                    let from_time_hours = parseInt(from_time.split(":")[0]);

                    let to_time = this.currentDateReservations[i].to_date.split("T")[1].split(":")[0];
                    let to_time_hours = to_time.split(":")[0] === "00" ? 24 : parseInt(to_time.split(":")[0]);

                    let totalHours = to_time_hours - from_time_hours;
                    for(let j = 0; j < this.timeList.length; j ++ ){
                        if(from_time === this.timeList[j].split(":")[0]){
                            for(let k = j; k < (j + totalHours); k ++){
                                list[k].disabled = true;
                            }
                        }
                    }
                }
                return list;
            }
        },
        toReservations(){
            let list = [];
            if(this.startTimeValue === null){
                return []
            }else{
                for(let i = this.startTimeValue - 6; i < this.freeReservations.length; i ++){
                    list.push({text: this.freeReservations[i].text, value: this.freeReservations[i].value, disabled: this.freeReservations[i].disabled});
                    if(this.freeReservations[i].disabled === true){
                        break;
                    }
                }
                if(list[list.length-1].value === 23){
                    list.push({text: '00:00', value: 24, disabled: false});
                }
                
                list[list.length-1].disabled = false;

                return list;
            }
        },
    },
    methods: {
        async registerReservation(){
            if(this.startTimeValue === null || this.endTimeValue === null || this.nPersons === 0){
                console.log("Please fill all fields");
            }else{
                this.loading = true;

                let startTimeValueString = this.startTimeValue < 10 ? `0${this.startTimeValue}` : `${this.startTimeValue}`
                let endTimeValueString = this.endTimeValue < 10 ? `0${this.endTimeValue}` : `${this.endTimeValue}`

                if(this.$route.params.roomId === undefined && this.$route.params.sectionId === undefined){
                    let reservations = [];
                    this.allSections.forEach(section => {
                        reservations.push({
                           from_date: `${this.date}T${startTimeValueString}:00`,
                            to_date: `${this.date}T${endTimeValueString}:00`,
                            number_of_people: this.nPersons,
                            account: {
                                id: this.$store.getters.getLoggedInAccount.id
                            },
                            section: {
                                id: section.id
                            } 
                        });
                    })
                    let result = await reservationService.addRoomReservation(reservations);
                    if(result){
                        console.log("Reservation successfully added");
                        for(let i = 0; i < this.allSections.length; i ++){
                            this.allSections[i].inReservations.push(reservations[i]);
                        }
                    }else{
                        console.log("Something went wrong when creating reservation...");
                    }
                }else{
                    let reservation = {
                        from_date: `${this.date}T${startTimeValueString}:00`,
                        to_date: `${this.date}T${endTimeValueString}:00`,
                        number_of_people: this.nPersons,
                        account: {
                            id: this.$store.getters.getLoggedInAccount.id
                        },
                        section: {
                            id: this.section.id
                        }
                    }
                    let result = await reservationService.addReservation(reservation);
                    if(result){
                        console.log("Reservation successfully added");
                        this.section.inReservations.push(reservation);
                    }else{
                        console.log("Something went wrong when creating reservation...");
                    }
                }
                
                this.startTimeValue = null;
                this.endTimeValue = null;
                this.loading = false;
            }
        }
    }
}
</script>

<style>
    .section{
        background-color: #192138;
        min-height: 100vh;
        padding-top: 100px;
        display: flex;
        flex-direction: column;
        align-items: center;
        justify-content: space-around;
    }

    .title{
        margin: 20px 0 50px 0;
        display: flex;
        flex-direction: column;
        align-items: center;
    }

    .title p{
        margin: 10px 0;
        opacity: 70%;
    }

    .row{
        width: 90%;
        display: flex;
        justify-content: center;
    }
    .time-selection{
        width: 30%;
        margin: 0 50px;
    }

    .reservations-overview{
        width: 30%;
        display: flex;
        justify-content: center;
    }

    .reserve-button{
        width: 100%;
        margin-top: 20px;
    }
</style>