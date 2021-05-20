<template>
    <div class="reservation-box">
        <v-col cols="24">
            <!-- <v-row align="center" justify="center"> -->
                <v-row>
                    <p class="text">ReservasjonsID:</p>
                    <p class="param">{{ reservation.reservation.id }}</p>
                </v-row>
                <v-row>
                    <p class="text">SeksjonsID:</p>
                    <p class="param">{{ section.id }}</p>
                </v-row>

                <v-row>
                    <p class="text">Seksjonsnavn:</p>
                    <p class="param">{{ section.name }}</p>
                </v-row>
                <!-- <hr class="divider"> -->

                <!-- <v-row>
                    <p class="text">Bruker-email:</p>
                    <p class="param">{{ account.email }}</p>
                </v-row> -->

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
// import { sectionService } from "../../services/SectionService.js";
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
        sections: {
            type: Array,
            required: true
        },
    },

    data() {
        return {
            sectionName: "",
            email: "",
            date: "",
            startTime: "",
            endTime: "",
            section: {},
            account: {},
        }
    },

    mounted() {
        this.findTime();
        this.findSection();
        this.$emit("finished-loading");
    },

    methods: {
        /**
         * findTime is a function which converts LocalDateTime object to date and time parts.
         */
        findTime() {
            this.date = this.reservation.reservation.from_date
                .slice(0,10)
                .split("-")
                .reverse()
                .toString()
                .replaceAll(",",".");
            this.startTime = this.reservation.reservation.from_date.slice(11,16);
            this.endTime = this.reservation.reservation.to_date.slice(11,16);
        },

        /**
         * findSection is a function which finds section by given id
         */
        findSection() {
            for (let i = 0; i < this.sections.length; i++) {
                if (this.sections[i].id === this.reservation.sectionId) {
                    this.section = this.sections[i];
                }
            }
        },

        /**
         * findAccount is a function which calls getAccount function in accountService with given account ID
         */
        async findAccount() {
            this.account = await accountService.getAccount(this.reservation.accountId);
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