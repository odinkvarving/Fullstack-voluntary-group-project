<template>
    <div class="add-account-box">
        <v-container>
            <v-row>
                <v-col class="col" cols="12">
                    <h1>Lag en ny bruker</h1>
                    <p class="white--text text--secondary">Skriv inn brukerinfo nedenfor</p>
                    <v-alert
                        type="error"
                        :value="isErrorVisible"
                        transition="scale-transition">
                        Bruker ble ikke lagt til
                    </v-alert>
                    <v-alert
                        :value="isPopUpVisible"
                        type="success"
                        transition="scale-transition">
                        Bruker ble lagt til
                    </v-alert>
                </v-col>
                <v-col class="col" cols="12">
                    <p>Skriv inn navn</p>
                    <v-text-field
                        label="Navn"
                        v-model="nameInput"
                        v-on:keyup.enter="addAccount"
                        outlined color="green"
                        :error-messages="nameError"/>
                </v-col>
                <v-col class="col" cols="12">
                    <p>Skriv inn e-post</p>
                    <v-text-field
                        label="E-post"
                        v-model="emailInput"
                        v-on:keyup.enter="addAccount"
                        outlined color="green"
                        :error-messages="emailError"/>
                </v-col>
                <v-col class="col" cols="12">
                    <p>Skriv inn telefonnummer</p>
                    <v-text-field
                        label="Tlf"
                        maxlength="8"
                        v-model="phoneInput"
                        v-on:keyup.enter="addAccount"
                        outlined color="green"
                        :error-messages="phoneError"
                        oninput="this.value = this.value.replace(/[^0-9.]/g, '').replace(/(\..*)\./g, '$1');"/>
                </v-col>
                <v-col class="col" cols="12">
                    <p>Skriv inn passord</p>
                    <v-text-field
                        label="Passord" 
                        maxlength="30" 
                        v-model="passwordInput" 
                        v-on:keyup.enter="addAccount" 
                        outlined color="green" 
                        :error-messages="passwordError"/>
                </v-col>
                <v-col class="col" cols="12" lg="6" >
                    <p>Velg utløpsdato</p>
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
                            v-model="expirationDateInput"
                            label="Dato"
                            persistent-hint
                            prepend-icon="mdi-calendar"
                            v-bind="attrs"
                            v-on="on"
                            :error-messages="expirationDateError"
                        ></v-text-field>
                        </template>
                        <v-date-picker
                            v-model="expirationDateInput"
                            no-title
                            @input="menu = false"
                        ></v-date-picker>
                    </v-menu>
                </v-col>
                <v-col cols="6" align="end">
                    <v-btn color="green" @click="addAccount">
                        <span>Lag ny bruker</span>
                    </v-btn>
                </v-col>
            </v-row>
        </v-container>
    </div>
</template>
<script>
import { adminService } from "../../services/AdminService.js"

/**
 * AddAccountBox is a component which represents the action of creating a new account.
 * 
 * @author Scott Rydberg Sonen
 */

export default {
    name: "AddAccountBox",

    data() {
        return {
            isEmailValid: true,
            nameInput: "",
            emailInput: "",
            phoneInput: "",
            passwordInput: "",
            expirationDateInput: null,
            isPopUpVisible: false,
            isErrorVisible: false,
            message: "",
            nameError: "",
            emailError: "",
            phoneError: "",
            passwordError: "",
            expirationDateError: "",
            menu: false,
            addRequestSent: false,
            isAccountAdded: false,
        }
    },

    async mounted() {
        adminService.isLoggedIn();
        await adminService.isAdmin();
    },

    methods: {
        /**
         * addAccount is a function which firstly validates all new account input.
         * If input is valid, the add function is called.
         */
        addAccount() {
            this.isEmailValid = true;
            if (!this.validateInput()) {
                return;
            }
            this.add();
        },

        /**
         * validateInput validates all parameters created by the admin.
         * If atleast one of the fields are invalid, the function will return false and error(s).
         */
        validateInput() {
            let isValid = true;
            if (this.nameInput === "") {
                this.nameError = "Navne-feltet kan ikke stå tomt";
                isValid = false;
            } else this.nameError = "";
            if (!this.validateEmail()) {
                this.emailError = "E-post-feltet kan ikke stå tomt";
                isValid = false;
            } else this.emailError = "";
            if (this.phoneInput.length !== 8) {
                this.phoneError = "Telefonnummeret må ha åtte siffer";
                isValid = false;
            } else this.phoneError = "";
            if (this.passwordInput.length < 8 || this.passwordInput.length >= 30) {
                this.passwordError = "Passordet må være mellom 8 og 30 tegn";
                isValid = false;
            } else this.passwordError = "";
            if (this.expirationDateInput === null) {
                this.expirationDateError = "Utløpsdato-feltet kan ikke stå tomt";
                isValid = false;
            }
            return isValid;
        },

        /**
         * validateEmail is a function which validates email,
         *  to see if the email is using a valid format.
         */
        validateEmail() {
            const re = /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
            return re.test(String(this.emailInput).toLowerCase());
        },

        /**
         * add is a function which sends a POST request.
         * This request contains the new account.
         */
        async add() {
            console.log(this.$store.getters.getLoggedInAccount);
            console.log(this.$store.getters.getJwtToken);

            const account = {
                name: this.nameInput,
                email: this.emailInput,
                password: this.passwordInput,
                phone: this.phoneInput,
                is_admin: false,
                expiration_date: new Date(this.expirationDateInput)
            }

            const requestOptions = {
                method: "POST",
                headers: {
                    "Content-Type": "application/json",
                    "Authorization": `Bearer ${this.$store.getters.getJwtToken}`
                },
                body: JSON.stringify(account)
            }
            await fetch("http://localhost:8080/accounts", requestOptions)
                .then(response => response.json())
                .then(data => {
                    this.addRequestSent = true;
                    this.isAccountAdded = data;
                    if (this.isAccountAdded) {
                        this.isPopUpVisible = true;
                    } else {
                        this.isErrorVisible = true;
                    }
                })
                .catch(error => console.error(error))

            location.reload();
        }
    }
}
</script>
<style scoped>
    #add-account-box {
        height: 500px;
        width: 700px;
    }
    .col {
        margin-top: -20px;
    }
</style>