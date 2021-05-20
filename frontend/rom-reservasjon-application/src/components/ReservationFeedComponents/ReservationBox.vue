<template>
    <div class="reservation-box">
        <v-col cols="24">
            <!-- <v-row align="center" justify="center"> -->
                <v-row>
                    <p class="text">ID:</p>
                    <p class="param">{{ reservation.sectionId }}</p>
                </v-row>
                <!-- <hr class="divider"> -->
                <v-row>
                    <p class="text">Dato:</p>
                    <p class="param">{{ date }}</p>
                </v-row>
                <!-- <hr class="divider"> -->
                <v-row>
                    <p class="text">Klokkeslett:</p>
                    <p class="param">{{ startTime }} - {{ endTime }}</p>
                </v-row>
                <!-- <hr class="divider"> -->
                <v-row>
                    <p class="text">Antall personer:</p>
                    <p class="param">{{ reservation.reservation.number_of_people }}</p>
                </v-row>
                
            <!-- </v-row> -->
        </v-col>
        
    </div>
</template>
<script>
import { accountService } from "../../services/AccountService.js";

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
            section: {}
        }
    },

    mounted() {
        this.findTime();
        this.findSection();
        
    },

    computed: {
        account() {
            return accountService.getAccount(this.reservation.accountId);
        }
    },

    methods: {
        /**
         * findTime is a function which converts LocalDateTime object to date and time parts.
         */
        findTime() {
            this.date = this.reservation.reservation.from_date.slice(0,10).replaceAll("-", "/");
            this.startTime = this.reservation.reservation.from_date.slice(11,16);
            this.endTime = this.reservation.reservation.to_date.slice(11,16);
        },

        async findSection() {
            const requestOptions = {
                method: "GET",
                headers: {
                    "Content-Type": "application/json",
                    "Authorization": `Bearer ${this.$store.getters.getJwtToken}`
                }
            }
            await fetch(`http://localhost:8080/sections/${this.reservation.sectionId}`, requestOptions)
                .then(response => response.json())
                .then(data => this.section = data)
                .catch(error => console.error(error));
        },

        // async findAccount() {
        //     const requestOptions = {
        //         method: "GET",
        //         headers: {
        //             "Content-Type": "application/json",
        //             "Authorization": `Bearer ${this.$store.getters.getJwtToken}`
        //         }
        //     }
        //     await fetch(`http://localhost:8080/accounts/${this.reservation.accountId}`, requestOptions)
        //         .then(response => response.json())
        //         .then
        // }
    }
}
</script>
<style scoped>
    .reservation-box {
        background-color: #222B45;
        color: white;
        border: 1px solid #01AB55;
        border-radius: 4px;
        margin: 30px;
        padding: 20px;
    }
    .text {
        opacity: 50%;
        margin-right: 8px;
    }
    .divider {
        height: 1px;
        border: none;
        background-color: #01AB55;
        margin: 5px;
    }
</style>