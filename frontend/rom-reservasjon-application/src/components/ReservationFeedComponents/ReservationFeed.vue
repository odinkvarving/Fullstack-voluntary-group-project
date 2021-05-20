<template>
    <div class="reservation-feed">
        <v-container class="reservation-feed-container">
            <h1>Romreservasjoner</h1>
            <v-row align="center" justify="center" v-show="!finishedLoading">
                <v-progress-circular
                    indeterminate
                    color="green"
                    size="150"
                    :width="5"
                    style="margin-top: 200px"
                ></v-progress-circular>
            </v-row>
            <v-row v-if="isDataReady && sections" v-show="finishedLoading">
                <div class="feed" v-for="r in reservations" :key="r.id" @click="handleReservationClicked(r)">
                    <ReservationBox :reservation="r" :sections="sections" v-on:finished-loading="checkLoadingStatus"/>
                </div>
            </v-row>
        </v-container>
    </div>
</template>
<script>
import ReservationBox from "./ReservationBox.vue";
import { reservationService } from "../../services/ReservationService.js";
import { adminService } from "../../services/AdminService.js"

/**
 * ReservationFeed is a component which represents the reservation feed visible for admins only
 * 
 * @author Scott Rydberg Sonen
 */

export default {
    name: "ReservationFeed",

    components: {
        ReservationBox
    },

    data() {
        return {
            reservations: [],
            sections: [],
            isDataReady: false,
            count: 0,
            finishedLoading: false,
        }
    },

    /**
     * mounted declares reservations and rooms, so the arrays can be passed as props to ReservationBox.
     * isDataReady is a flag which turns true when both reservations and rooms arrays are fetched.
     */
    async mounted() {
        adminService.isLoggedIn();
        await adminService.isAdmin();

        this.reservations = await reservationService.getReservations();
        this.sections = this.$store.getters.getSections;
        
        this.isDataReady = true;
    },

    methods: {
        /**
         * handleReservationClicked is a function which redirects the admin to reservation page.
         * The id is passed through the URL path.
         */
        handleReservationClicked(reservation) {
            this.$router.push({name: "Reservation", params: {reservationId: reservation.reservation.id}}); 
        },

        checkLoadingStatus() {  
            this.count++;
            if (this.count == this.reservations.length) this.finishedLoading = true;
        }
    }
}
</script>
<style scoped>
    .reservation-feed {
        background-color: #192138;
        padding: 80px;
        height: 100vh;
    }
    h1 {
        color: white;
    }
    .feed {
        cursor: pointer;
    }
    @media (max-width: 600px) {
        .reservation-feed {
            background-color: #192138;
            padding: 80px 10%;
            height: 100vh;
        }
    }
</style>