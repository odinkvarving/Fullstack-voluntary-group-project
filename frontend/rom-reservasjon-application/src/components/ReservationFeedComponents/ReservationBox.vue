<template>
    <div class="reservation-box">
        <v-row align="center" justify="center">
            <p class="text">ID:</p>
            <p class="param">{{ reservation.id }}</p>
            <v-spacer></v-spacer>
            <p class="divider">|</p>
            <v-spacer></v-spacer>
            <p class="text">Dato:</p>
            <p class="param">{{ date }}</p>
            <v-spacer></v-spacer>
            <p class="divider">|</p>
            <v-spacer></v-spacer>
            <p class="text">Klokkeslett:</p>
            <p class="param">{{ startTime }} - {{ endTime }}</p>
            <v-spacer></v-spacer>
            <p class="divider">|</p>
            <v-spacer></v-spacer>
            <p class="text">Antall personer:</p>
            <p class="param">{{ reservation.number_of_people }}</p>
        </v-row>
    </div>
</template>
<script>
/**
 * ReservationBox is a component which represents a single reservation in reservation feed.
 * 
 * @author Scott Rydberg Sonen
 */

export default {
    name: "ReservationBox",

    props: {
        reservation: {
            type: Object,
            required: true
        },
        rooms: {
            type: Array,
            required: true
        }
    },

    data() {
        return {
            sectionName: "",
            email: "",
            date: "",
            startTime: "",
            endTime: "",
        }
    },

    mounted() {
        this.findTime();
    },

    methods: {
        /**
         * findTime is a function which converts LocalDateTime object to date and time parts.
         */
        findTime() {
            this.date = this.reservation.from_date.slice(0,10).replaceAll("-", "/");
            this.startTime = this.reservation.from_date.slice(11,16);
            this.endTime = this.reservation.to_date.slice(11,16);
        }
    }
}
</script>
<style scoped>
    .reservation-box {
        background-color: #222B45;
        color: white;
        border: 1px solid #01AB55;
        border-radius: 4px;
        height: 60px;
        margin-top: 50px;
        padding: 10px 100px;
    }
    .text {
        opacity: 50%;
        margin-right: 8px;
    }
    .param {
        font-size: 20px;
    }
    .divider {
        font-size: 40px;
        color: #01AB55;
    }
</style>