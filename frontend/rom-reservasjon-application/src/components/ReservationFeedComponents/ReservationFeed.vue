<template>
    <div class="reservation-feed">
        <v-container class="reservation-feed-container">
            <h1>Romreservasjoner</h1>
            <v-app-bar color="#192138" flat>
                <v-app-bar-nav-icon @click="drawer = true"/>
                <v-toolbar-title>Filter</v-toolbar-title>
                <v-btn color="green" @click="clearFilter()" style="margin-left: 30px">
                    <span>Tøm filter</span>
                </v-btn>
            </v-app-bar>
            <v-navigation-drawer
                v-model="drawer"
                absolute
                temporary
                color="#222B45"
                width="330"
                style="margin-top: 65px;">
                <v-text-field
                    label="Seksjon"
                    v-model="sectionFilter"
                    v-on:keyup.enter="filter()"
                    outlined color="green"
                    clearable
                    style="margin: 20px"/>
                <v-text-field
                    label="Bruker-email"
                    v-model="accountFilter"
                    v-on:keyup.enter="filter()"
                    outlined color="green"
                    clearable
                    style="margin: 20px"/>
                <v-menu
                    ref="menu"
                    v-model="menu"
                    :close-on-content-click="false"
                    transition="scale-transition"
                    offset-y
                    max-width="290px"
                    min-width="auto">
                    <template v-slot:activator="{ on, attrs }">
                    <v-text-field
                        v-model="dateFilter"
                        label="Dato"
                        persistent-hint
                        prepend-icon="mdi-calendar"
                        v-bind="attrs"
                        v-on="on"
                        v-on:keyup.enter="filter()"
                        style="margin: 20px;"/>
                    </template>
                    <v-date-picker
                        v-model="dateFilter"
                        no-title
                        @input="menu = false"
                    ></v-date-picker>
                </v-menu>
                <v-col align="end">
                    <v-btn color="green" @click="filter()">
                        <span>Søk</span>
                    </v-btn>
                </v-col>
            </v-navigation-drawer>
            
            <div v-if="match">
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
                    <div class="feed" v-for="r in filteredReservations" :key="r.reservation.id" @click="handleReservationClicked(r)">
                        <ReservationBox :reservation="r" :accounts="accounts" :sections="sections" v-on:finished-loading="checkLoadingStatus"/>
                    </div>
                </v-row>
            </div>
            <div v-if="!match">
                <h2 style="color: #01AB55">Fant ingen reservasjoner med valgte filter</h2>
            </div>
        </v-container>
    </div>
</template>
<script>
import ReservationBox from "./ReservationBox.vue";
import { reservationService } from "../../services/ReservationService.js";
import { adminService } from "../../services/AdminService.js";
import { accountService } from "../../services/AccountService.js";
import { sectionService } from "../../services/SectionService.js";

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
            accounts: [],
            sections: [],
            filteredReservations: [],
            isDataReady: false,
            count: 0,
            finishedLoading: false,
            drawer: false,
            menu: false,
            sectionFilter: "",
            accountFilter: "",
            dateFilter: "",
            match: true
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
        this.accounts = await accountService.getAccounts();
        //this.sections = this.$store.getters.getSections; When page is updated, sections don't render
        this.sections = await sectionService.getSections();
        this.filteredReservations = this.reservations;
        
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

        /**
         * checkLoadingStatus is a function which checks if all reservations are loaded
         */
        checkLoadingStatus() {  
            this.count++;
            if (this.count == this.reservations.length) this.finishedLoading = true;
        },

        filter() {
            let list = this.reservations;
            let chosenFilter = false;
            if (this.sectionFilter !== "") {
                list = this.filterBySection(list);
                chosenFilter = true;
                console.log("Section filtered: " + list);
            }
            if (this.accountFilter !== "") {
                list = this.filterByAccount(list);
                chosenFilter = true;
                console.log("Account filtered: " + list);
            }
            if (this.dateFilter !== "") {
                list = this.filterByDate(list);
                chosenFilter = true;
                console.log("Date filtered: " + list);
            }
            if (chosenFilter && (list === this.reservations)) this.match = false;
            this.filteredReservations = list;
            this.sectionFilter = this.accountFilter = this.dateFilter = "";
            this.drawer = false;
        },

        filterBySection(list) {
            let section = null;
            this.sections.forEach(sec => {
                if (this.sectionFilter === sec.name) {
                    section = sec;
                }
            });
            if (!section) return list;

            console.log("Section: " + section.name);
            return list.filter(res => {
                return res.sectionId === section.id;
            });
        },

        filterByAccount(list) {
            let account = null;
            this.accounts.forEach(acc => {
                if (this.accountFilter === acc.email) {
                    account = acc;
                }
            });
            if (!account) return list;

            console.log("Account: " + account.email);
            return list.filter(res => {
                return res.accountId === account.id;
            });
        },

        filterByDate(list) {
            return list.filter(res => {
                return res.reservation.from_date.slice(0,10) === this.dateFilter;
            });
        },

        clearFilter() {
            this.filteredReservations = this.reservations;
            this.match = true;
        }
    }
}
</script>
<style scoped>
    .reservation-feed {
        background-color: #192138;
        padding-top: 60px;
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