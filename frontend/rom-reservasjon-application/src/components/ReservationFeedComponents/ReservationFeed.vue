<template>
    <div class="reservation-feed">
        <v-container class="reservation-feed-container">
            <h1>Romreservasjoner</h1>
            <v-row class="big-menu">
                <v-row>
                    <v-col cols="4">
                        <v-app-bar color="#192138" flat>
                            <v-app-bar-nav-icon @click="drawer = true"/>
                            <v-toolbar-title>Filtrer</v-toolbar-title>
                            <v-btn color="green" @click="clearFilter()" style="margin-left: 30px">
                                <span>Tøm filter</span>
                            </v-btn>
                        </v-app-bar>
                    </v-col>
                    <v-col cols="5" align="end">
                        <v-app-bar color="#192138" flat>
                            <v-select
                                :items="items"
                                item-text="value"
                                item-value="id"
                                label="Sorter"
                                outlined
                                v-model="sortKey"
                                :on-change="sort()"
                                color="green"
                                style="margin: auto;"/>
                            <v-btn color="green" @click="clearSort()" style="margin-left: 30px">
                                <span>Tøm sortering</span>
                            </v-btn>
                        </v-app-bar>
                    </v-col>
                </v-row>
            </v-row>
            <v-col class="small-menu">
                    <v-app-bar color="#192138" flat>
                        <v-app-bar-nav-icon @click="drawer = true"/>
                        <v-toolbar-title>Filtrer</v-toolbar-title>
                        <v-btn color="green" @click="clearFilter()" style="margin-left: 30px">
                            <span>Tøm filter</span>
                        </v-btn>
                    </v-app-bar>
                </v-col>
            <v-col class="small-menu">
                <v-app-bar color="#192138" flat>
                    <v-select
                        :items="items"
                        item-text="value"
                        item-value="id"
                        label="Sorter"
                        outlined
                        v-model="sortKey"
                        :on-change="sort()"
                        color="green"
                        style="margin: auto;"/>
                    <v-btn color="green" @click="clearSort()" style="margin-left: 30px">
                        <span>Tøm sortering</span>
                    </v-btn>
                </v-app-bar>
            </v-col>
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
                        outlined
                        label="Dato"
                        persistent-hint
                        prepend-inner-icon="mdi-calendar"
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
            match: true,
            items: [
                { id: 1, value: "Tidspunkt tidligst-senest" },
                { id: 2, value: "Tidspunkt senest-tidligst" },
                { id: 3, value: "Antall personer lav-høy"},
                { id: 4, value: "Antall personer høy-lav"}],
            sortKey: 0,
            prevSortKey: 0,
        }
    },

    /**
     * mounted declares reservations, sections and accounts, so the arrays can be passed as props to ReservationBox.
     * isDataReady is a flag which turns true when both reservations and rooms arrays are fetched.
     */
    async mounted() {
        adminService.isLoggedIn();
        await adminService.isAdmin();

        this.reservations = await reservationService.getReservations();
        this.reservations = this.reservations.sort((a,b) => {
            return new Date(a.reservation.from_date) - new Date(b.reservation.from_date);
        });
        this.accounts = await accountService.getAccounts();
        //this.sections = this.$store.getters.getSections; When page is refreshed, sections don't render
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

        /**
         * filter is a function which filters reservations list based on filter input
         */
        filter() {
            let list = this.filteredReservations;
            let chosenFilter = 0;
            if (this.sectionFilter !== "") {
                list = this.filterBySection(list);
                chosenFilter++;
                console.log("Section filtered: " + list);
            }
            if (this.accountFilter !== "") {
                list = this.filterByAccount(list);
                chosenFilter++;
                console.log("Account filtered: " + list);
            }
            if (this.dateFilter !== "") {
                list = this.filterByDate(list);
                chosenFilter++;
                console.log("Date filtered: " + list);
            }
            if ((chosenFilter !== 0) && (list.length === 0)) this.match = false;
            this.filteredReservations = list;
            this.sectionFilter = this.accountFilter = this.dateFilter = "";
            this.drawer = false;
        },

        /**
         * filterBySection is a function which filters a list based on section filter
         */
        filterBySection(list) {
            let section = null;
            this.sections.forEach(sec => {
                if (this.sectionFilter === sec.name) {
                    section = sec;
                }
            });
            if (!section) return [];

            console.log("Section: " + section.name);
            return list.filter(res => {
                return res.sectionId === section.id;
            });
        },

        /**
         * filterByAccount is a function which filters a list based on account filter
         */
        filterByAccount(list) {
            let account = null;
            this.accounts.forEach(acc => {
                if (this.accountFilter === acc.email) {
                    account = acc;
                }
            });
            if (!account) return [];

            console.log("Account: " + account.email);
            return list.filter(res => {
                return res.accountId === account.id;
            });
        },

        /**
         * filterByDate is a function which filters a list based on date filter
         */
        filterByDate(list) {
            return list.filter(res => {
                return res.reservation.from_date.slice(0,10) === this.dateFilter;
            });
        },

        /**
         * clearFilter is a function which clears all filters and refreshes the feed
         */
        clearFilter() {
            this.filteredReservations = this.reservations;
            this.match = true;
        },

        /**
         * sort is a function which sorts a list based on sort key.
         * We also check if previous key is equal to the new key,
         *  so we don't get an infinite loop on select option change.
         */
        sort() {
            if (this.sortKey !== this.prevSortKey) {
                switch(this.sortKey) {
                    case 1: {
                        this.filteredReservations = this.sortByTimeEarliest();
                        this.prevSortKey = this.sortKey;
                        break;
                    }
                    case 2: {
                        this.filteredReservations = this.sortByTimeLatest();
                        this.prevSortKey = this.sortKey;
                        break;
                    }
                    case 3: {
                        this.filteredReservations = this.sortByPeopleAsc();
                        this.prevSortKey = this.sortKey;
                        break;
                    }
                    case 4: {
                        this.filteredReservations = this.sortByPeopleDesc();
                        this.prevSortKey = this.sortKey;
                        break;
                    }
                }
            }
        },

        /**
         * sortByTimeEarliest is a function which sorts filteredReservations list by time earliest to latest
         */
        sortByTimeEarliest() {
            return this.filteredReservations.sort((a,b) => {
                return new Date(a.reservation.from_date) - new Date(b.reservation.from_date);
            });
        },

        /**
         * sortByTimeLatest is a function which sorts filteredReservations list by time latest to earliest
         */
        sortByTimeLatest() {
            return this.filteredReservations.sort((a,b) => {
                return new Date(b.reservation.from_date) - new Date(a.reservation.from_date);
            });
        },

        /**
         * sortByPeopleAsc is a function which sorts filteredReservations list by number of people lowest to highest
         */
        sortByPeopleAsc() {
            return this.filteredReservations.sort((a,b) => {
                return a.reservation.number_of_people - b.reservation.number_of_people;
            });
        },

        /**
         * sortByPeopleDesc is a function which sorts filteredReservations list by number of people highest to lowest
         */
        sortByPeopleDesc() {
            return this.filteredReservations.sort((a,b) => {
                return b.reservation.number_of_people - a.reservation.number_of_people;
            });
        },

        /**
         * clearFilter is a function which clears sort and refreshes the feed with default sorting
         */
        clearSort() {
            this.filteredReservations = this.sortByTimeEarliest();
            this.sortKey = 0;
        }
    }
}
</script>
<style scoped>
    .reservation-feed {
        background-color: #192138;
        height: 100vh;
    }
    h1 {
        color: white;
    }
    .feed {
        cursor: pointer;
    }
    .big-menu {
        display: block;
    }
    .small-menu {
        display: none;
    }
    @media (max-width: 1263px) {
        .big-menu {
            display: none;
        }
        .small-menu {
            display: block;
        }
    }
</style>