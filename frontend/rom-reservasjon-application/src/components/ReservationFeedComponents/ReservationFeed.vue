<template>
    <div class="reservation-feed">
        <div class="reservation-feed-container">
            <h1>Romreservasjoner</h1>
            <div v-if="isDataReady && rooms">
                <div class="feed" v-for="r in reservations" :key="r.id" @click="handleReservationClicked(r)">
                    <ReservationBox :reservation="r" :rooms="rooms"/>
                </div>
            </div>
        </div>
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
            rooms: [],
            isDataReady: false,
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
        this.rooms = this.$store.getters.getRooms;
        
        this.isDataReady = true;
    },

    methods: {
        /**
         * handleReservationClicked is a function which redirects the admin to reservation page.
         * The id is passed through the URL path.
         */
        handleReservationClicked(reservation) {
            this.$router.push({name: "Reservation", params: {reservationId: reservation.id}}); 
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
</style>