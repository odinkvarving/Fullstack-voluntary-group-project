<template>
    <div id="reservations">
        <ReservationFeed/>
    </div>
</template>
<script>
import ReservationFeed from "../components/ReservationFeedComponents/ReservationFeed.vue";

/**
 * Reservations is a router for the reservations feed.
 * Reservations component is only visible for admins.
 * 
 * @author Scott Rydberg Sonen
 */

export default {
    name: "Reservations",
    components: {
        ReservationFeed
    },
    mounted(){
        if(!this.$store.getters.isAuthenticated){
            this.$router.push("/");
        }
    }
}
</script>