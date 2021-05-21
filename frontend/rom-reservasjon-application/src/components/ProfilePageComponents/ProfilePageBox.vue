<template>
    <v-container class="profile-page-box">
        <v-row align="center" justify="center" v-if="!isDataReady">
            <v-progress-circular
                indeterminate
                color="green"
                size="150"
                :width="5"
                style="margin-top: 200px"
            ></v-progress-circular>
        </v-row>
        <v-col v-if="isDataReady">
            <v-row>
                <h1>Profilside</h1>
            </v-row>
            <div class="info-box">
                <v-row>
                    <v-col cols="3">Navn: </v-col>
                    <v-col>{{ account.name }}</v-col>
                </v-row>
                <v-row>
                    <v-col cols="3">E-post: </v-col>
                    <v-col>{{ account.email }}</v-col>
                </v-row>
                <v-row>
                    <v-col cols="3">Telefon: </v-col>
                    <v-col>{{ account.phone }}</v-col>
                </v-row>
            </div>
        </v-col>
    </v-container>
</template>
<script>
import { accountService } from "../../services/AccountService.js";

/**
 * ProfilePageBox is a component which represents the account info display of an account
 * 
 * @author Scott Rydberg Sonen
 */

export default {
    name: "ProfilePageBox",

    data() {
        return {
            account: {},
            isDataReady: false
        }
    },

    async mounted() {
        await this.getAccount(this.$route.params.id);
        this.isDataReady = true;
    },

    methods: {
        /**
         * getAccount is a function which calls getAccount function in accountService,
         *  and uses and id as parameter.
         */
        async getAccount(id) {
            this.account = await accountService.getAccount(id);
        }
    }
}
</script>
<style scoped>
    .info-box {
        background-color: #222B45;
        width: 50%;
        margin-top: 20px;
        padding: 20px;
        border-radius: 4px;
    }
    @media (max-width: 1000px) {
        .info-box {
            width: 80%;
        }
    }
    @media (max-width: 600px) {
        .info-box {
            width: 100%;
        }
    }
</style>