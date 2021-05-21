<template>
    <div>
        <v-container id="input-box" color="#222B45">
            <v-col cols="12">
                <h1>Glemt passord?</h1>
                <p class="text--white text--secondary">Skriv inn e-postadressen din nedenfor</p>
                <v-alert
                    :value="isPopUpVisible"
                    type="success"
                    transition="scale-transition"
                    style="width: 50%">
                    Email er sendt til bruker, dersom email eksisterer
                </v-alert>
                <v-text-field
                    id="email"
                    label="E-post"
                    v-model="emailInput"
                    v-on:keyup.enter="sendAddress"
                    outlined color="green"
                    v-bind:disabled="isSent"
                    :error-messages="emailError"
                    style="width: 50%"/>
                <v-col cols="6" align="end">
                    <v-btn color="green" @click="sendAddress" v-bind:disabled="isSent">
                        <span>Send</span>
                    </v-btn>
                </v-col>
            </v-col>
        </v-container>
    </div>
</template>
<script>
/**
 * ForgotPasswordBox is a component which represents forgot password functionality.
 * Functionality of this component is based on some code from Systemutvikling 2 project.
 * 
 * @author Scott Rydberg Sonen
 */

export default {
    name: "ForgotPasswordBox",

    data() {
        return {
            isEmailValid: true,
            emailInput: "",
            isSent: false,
            isPopUpVisible: false,
            isErrorVisible: false,
            message: "",
            emailError: ""
        };
    },

    methods: {
        /**
         * sendAddress is a function which firstly checks if the email input is of valid format.
         * If the check fails, the user is noted.
         * If the email is of valid format, send function will run.
         */
        sendAddress() {
            this.isEmailValid = true;
            this.isPopUpVisible = false;
            this.isErrorVisible = false;
            if (!this.validateEmail()) {
                this.isEmailValid = false;
                this.message = "E-postadressen er ikke gyldig";
                this.emailError = "E-postadressen er ikke gyldig";
                this.isErrorVisible = true;
            }
            if (this.isEmailValid) {
                this.send();
                this.isSent = true;
                this.message = "E-post er sendt til oppgitt e-post dersom epost er registert\nGår tilbake til login-siden...";
                this.isPopUpVisible = true;
                this.redirectPage();
            }
        },

        /**
         * redirectPage is a function which pushes user to the first page (login) with timeout.
         */
        redirectPage() {
            setTimeout(() => this.$router.push("/"), 3000);
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
         * send is a function which sends a request to backend containing the email.
         * If the email of account is registered in the database,
         *  the email will receive a reset link.
         */
        async send() {
            const requestOptions = {
                method: "POST",
            }

            console.log("Reset email: " + this.emailInput);

            await fetch("http://localhost:8080/reset/" + this.emailInput, requestOptions)
            .then(response => response.json())
            .catch(error => console.error(error));
        },

        /**
         * closePopUp is a function which closes error pop up.
         */
        closePopUp() {
            this.message = "";
            this.isErrorVisible = false;
        },
    },
}
</script>