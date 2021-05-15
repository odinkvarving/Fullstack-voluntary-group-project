<template>
    <div id="add-account-box">
        <v-container>
            <v-row>
                <v-cols cols="12">
                    <h1>Lag en ny bruker</h1>
                    <p class="white--text text--secondary">Skriv inn brukerinfo nedenfor</p>
                    <v-alert type="error" :value="isErrorVisible" transition="scale-transition">{{ message }}</v-alert>
                    <v-alert type="success" :value="isPopUpVisible" transition="scale-transition">{{ message }}</v-alert>
                </v-cols>
                <v-cols cols="12">
                    <p>Skriv inn navn</p>
                    <v-text-field label="Navn" v-model="nameInput" v-on:keyup.enter="addAccount" outlined color="green"/>
                </v-cols>
                <v-cols cols="12">
                    <p>Skriv inn e-post</p>
                    <v-text-field label="E-post" v-model="emailInput" v-on:keyup.enter="addAccount" outlined color="green"/>
                </v-cols>
                <v-cols cols="12">
                    <p>Skriv inn telefonnummer</p>
                    <v-text-field label="Tlf" v-model="phoneNrInput" v-on:keyup.enter="addAccount" outlined color="green"/>
                </v-cols>
                <v-cols cols="12">
                    <p>Skriv inn passord</p>
                    <v-text-field label="Passord" v-model="passwordInput" v-on:keyup.enter="addAccount" outlined color="green"/>
                </v-cols>
            </v-row>
        </v-container>
        

    </div>
</template>
<script>
    export default {
        name: "AddAccountBox",

        data() {
            return {
                nameInput: "",
                emailInput: "",
                phoneNrInput: "",
                passwordInput: "",
                isPopUpVisible: false,
                isErrorVisible: false,
                message: "",
            }
        },

        methods: {
            addAccount() {

            }
        }
    }
</script>