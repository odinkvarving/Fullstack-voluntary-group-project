<template>
    <div>
        <SuccessPopUp class="popup" v-show="isPopUpVisible" :message="message"/>
        <ErrorPopUp class="popup" v-show="isErrorVisible" v-on:close-popup="closePopUp" :message="message"/>
        <div id="input-box">
            <h1 class="header">Glemt passord?</h1>
            <p class="sub-title">Skriv inn e-postadressen din nedenfor</p>
            <div class="input-container">
                <input type="text" class="input" id="email" placeholder="E-post" v-model="emailInput" v-bind:disabled="isSent" v-on:keyup.enter="sendAddress"/>
            </div>
            <button id="submit-btn" @click="sendAddress" v-bind:disabled="isSent">Send</button>
        </div>
    </div>
</template>
<script>
import SuccessPopUp from "../PopUpComponents/SuccessPopUp.vue";
import ErrorPopUp from "../PopUpComponents/ErrorPopUp.vue";

/**
 * ForgotPasswordBox is a component which represents forgot password functionality.
 * 
 * @author Scott Rydberg Sonen
 */

export default {
    name: "ForgotPasswordBox",
    components: {
        SuccessPopUp,
        ErrorPopUp
    },

    data() {
        return {
            isEmailValid: true,
            emailInput: "",
            isSent: false,
            isPopUpVisible: false,
            isErrorVisible: false,
            message: ""
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
                document.getElementById("input-box").style.filter = "blur(5px)";
                this.isEmailValid = false;
                this.message = "E-postadressen er ikke gyldig";
                this.isErrorVisible = true;
            }
            if (this.isEmailValid) {
                document.getElementById("input-box").style.filter = "blur(5px)";
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

            await fetch("http://localhost:8080/reset/" + this.emailInput, requestOptions);
        },

        /**
         * closePopUp is a function which closes error pop up.
         */
        closePopUp() {
            this.message = "";
            this.isErrorVisible = false;
            document.getElementById("input-box").style.filter = "none";
        },
    },
}
</script>
<style scoped>
    .popup {
        position: absolute;
        background-color: #222B45;
        z-index: 2;
        top: 25%;
        left: 15%;
    }
    #input-box {
        position: relative;
        z-index: 1;
    }
    h1 {
        font-size: 48px;
    }
    .sub-title {
        opacity: 50%;
        font-size: 17px;
        font-weight: lighter;
        margin-top: 0;
    }
    .input-container {
        margin-top: 50px;
    }
    .input {
        outline: none;
        width: 100%;
        height: 50px;
        background-color: #192138;
        border: 1px solid rgba(255, 255, 255, 0.5);
        border-radius: 4px;
        padding-left: 3%;
        font-family: "Inter";
        font-size: 18px;
        color: white;
        cursor: text;
        margin-top: 10px;
    }
    .input:focus {
        outline: none;
        border: 1px solid #01AB55;
    }
    #submit-btn {
        background-color: #01AB55;
        color: white;
        font-family: "Inter";
        font-size: 18px;
        height: 40px;
        width: 140px;
        border: none;
        border-radius: 4px;
        float: right;
        cursor: pointer;
        margin-top: 20px;
    }
</style>