<template>
    <div>
        <v-container id="password-reset-box">
            <v-col cols="12">
                <v-col cols="6" align="end">
                    <v-btn color="red" @click="returnPage">
                        <span>X</span>
                    </v-btn>
                </v-col>
                <h1>Tilbakestill passord</h1>
                <p class="text--white text--secondary">Skriv inn et nytt passord</p>
                <v-alert
                    :value="isPopUpVisible"
                    type="success"
                    transition="scale-transition"
                    style="width: 50%">
                    Passordene er like, passord resettes
                </v-alert>
                <v-alert
                    :value="isErrorVisible"
                    type="error"
                    transition="scale-transition"
                    style="width: 50%">
                    {{ message }}
                </v-alert>
                <v-col class="new-password">
                    <p>Skriv inn nytt passord</p>
                    <v-text-field
                        label="Nytt passord"
                        v-model="passwordInput"
                        v-on:keyup.enter="reset"
                        outlined color="green"
                        style="width: 50%"/>
                </v-col>
                <v-col class="confirm-password">
                    <p>Gjenta passord</p>
                    <v-text-field
                        label="Gjenta passord"
                        v-model="confirmationInput"
                        v-on:keyup.enter="reset"
                        outlined color="green"
                        style="width: 50%"/>
                </v-col>
                <v-col cols="6" align="end">
                    <v-btn color="green" @click="reset">
                        <span>Fullfør</span>
                    </v-btn>
                </v-col>
            </v-col>
        </v-container>
    </div>
</template>
<script>
/**
 * ResetPasswordBox is a component which represents the action of resetting a password.
 * Functionality of this component is based on some code from Systemutvikling 2 project.
 * 
 * @author Scott Rydberg Sonen
 */

export default {
    name: "ResetPasswordBox",

    data() {
        return {
            passwordInput: "",
            confirmationInput: "",
            isPopUpVisible: false,
            isErrorVisible: false,
            isPasswordValid: true,
            isPasswordsEqual: true,
            message: "",
            account: {},
            suffix: this.$route.params.suffix
        }
    },

    async mounted() {
        const requestOptions = {
            method: "GET"
        }
        await fetch("http://localhost:8080/reset/" + this.suffix, requestOptions)
            .then(response => response.json())
            .then(data => {
                this.account = data;
                console.log("TEST");
                console.log(this.account);
            })
            .catch(error => console.error(error));
    },

    methods: {
        /**
         * reset is a function which firstly validates password,
         *  to see if the password has a valid length.
         * Then the function checks whether or not the password and confirmation password are equal.
         * If they are equal and valid, resetPassword function will run.
         */
        reset() {
            console.log(this.account);
            this.isPopUpVisible = false;
            this.isErrorVisible = false;
            if (!this.validatePassword()) {
                this.message = "Passordet må være mellom 8 og 30 tegn";
                this.isErrorVisible = true;
                return;
            }
            if (!this.validateEquality()) {
                this.message = "Passordene er ikke like";
                this.isErrorVisible = true;
                return;
            }

            this.resetPassword();
        },

        /**
         * resetPassword is a function which sends a PUT request.
         * The request contains a new password for updating latest password.
         */
        async resetPassword() {
            this.account.password = this.passwordInput

            const requestOptions = {
                method: "PUT",
                body: this.account
            }

            return await fetch(`http://localhost:8080/accounts/${this.account.id}`, requestOptions)
                .then(response => response.json())
                .catch(error => console.error(error));
        },

        /**
         * validatePassword is a function which checks if password input has a valid length.
         */
        validatePassword() {
            return this.passwordInput.length >= 8 && this.passwordInput.length <= 30;
        },

        /**
         * validateEquality is a function which checks if password and confirmation password are equal.
         */
        validateEquality() {
            return this.passwordInput === this.confirmationInput;
        },

        /**
         * closePopUp is a function which closes error pop up.
         */
        closePopUp() {
            this.message = "";
            this.isErrorVisible = false;
            document.getElementById("password-reset-box").style.filter = "none";
        },

        /**
         * redirectPage is a function which pushes user to the first page (login) with timeout.
         */
        redirectPage() {
            setTimeout(() => this.$router.push("/"), 3000);
        },

        /**
         * returnPage is a function which pushes user to the first page (login) WITHOUT timeout.
         */
        returnPage() {
            this.$router.push("/");
        }
    }
}
</script>