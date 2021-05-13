<template>
    <div>
        <SuccessPopUp class="popup" v-show="isPopUpVisible" :message="message"/>
        <ErrorPopUp class="popup" v-show="isErrorVisible" v-on:close-popup="closePopUp" :message="message"/>
        <div id="input-box">
            <h1 class="header">Glemt passord?</h1>
            <p class="sub-title">Skriv inn e-postadressen din nedenfor</p>
            <div class="input-container">
                <input type="text" class="input" id="email" placeholder="E-post" v-model="emailInput" v-bind:disabled="isSent" v-on:keyup.enter="postAddress"/>
            </div>
            <button id="submit-btn" @click="postAddress" v-bind:disabled="isSent">Send</button>
        </div>
    </div>
</template>
<script>
    import SuccessPopUp from "../PopUpComponents/SuccessPopUp.vue";
    import ErrorPopUp from "../PopUpComponents/ErrorPopUp.vue";

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
        postAddress() {
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

        redirectPage() {
            setTimeout(() => this.$router.push("/"), 3000);
        },

        validateEmail() {
            const re = /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
            return re.test(String(this.emailInput).toLowerCase());
        },

        async send() {
            const requestOptions = {
                method: "POST",
            }

            await fetch("http://localhost:8080/reset/" + this.emailInput, requestOptions);
        },

        closePopUp() {
            this.message = "";
            this.isErrorVisible = false;
            document.getElementById("input-box").style.filter = "none";
        },
    },
    };
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