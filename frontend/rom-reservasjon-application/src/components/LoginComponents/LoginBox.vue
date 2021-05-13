<template>
    <div>
        <SuccessPopUp class="popup" v-show="isPopUpVisible"/>
        <ErrorPopUp class="popup" v-show="isErrorVisible" v-on:close-popup="closePopUp"/>
        <div id="login-box">
            <h1>Velkommen tilbake</h1>
            <p class="sub-title">Logg inn på kontoen din</p>
            <div class="login-container">
                <div id="email-container">
                    <p class="text">Skriv inn email</p>
                    <input type="text" class="input" id="email" placeholder="Email" v-model="emailInput" v-on:keyup.enter="loginAccount"/>
                </div>
                <div id="password-container">
                    <p class="text">Skriv inn passord</p>
                    <input type="text" class="input" id="password" placeholder="Passord" v-model="passwordInput" v-on:keyup.enter="loginAccount"/>
                </div>
            </div>
            <p class="text" id="forgotten-password">Glemt passord?</p>
            <button id="login-btn" @click="loginAccount">Logg inn</button>
        </div>
    </div>
</template>
<script>
    import SuccessPopUp from "./SuccessPopUp.vue";
    import ErrorPopUp from "./ErrorPopUp.vue";

    export default {
        name: "LoginBox",
        components: {
            SuccessPopUp,
            ErrorPopUp
        },
        data() {
            return {
                isEmailValid: true,
                isPasswordValid: true,
                emailInput: "",
                passwordInput: "",
                isPopUpVisible: false,
                isErrorVisible: false
            }
        },

        methods: {
            loginAccount() {
                document.getElementById("login-box").style.filter = "blur(5px)";
                this.isEmailValid = true;
                this.isPopUpVisible = false;
                this.isErrorVisible = false;
                if (!this.validateEmail()) {
                    this.isEmailValid = false;
                    this.isErrorVisible = true;
                }
                if (this.isEmailValid) {
                    //this.login();
                    this.isPopUpVisible = true;
                }
            },

            validateEmail() {
                const re = /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
                return re.test(String(this.emailInput).toLowerCase());
            },

            async login() {
                const authRequest = {
                    username: this.emailInput,
                    password: this.passwordInput
                }
                let loginResponse = this.$store.dispatch("login", authRequest);

                if (loginResponse.jwtToken) {
                    this.$router.push("/frontpage");
                } else {
                    this.isErrorVisible = true;
                }
            },

            closePopUp() {
                this.isErrorVisible = false;
                document.getElementById("login-box").style.filter = "none";
            }
        }
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
    #login-box {
        position: relative;
        z-index: 1;
    }
    h1 {
        font-size: 48px;
        margin-bottom: 0;
    }
    .sub-title {
        opacity: 50%;
        font-size: 24px;
        font-weight: lighter;
        margin-top: 0;
    }
    .login-container {
        margin-top: 50px;
    }
    #password-container {
        margin-top: 10px;
    }
    .text {
        font-weight: bold;
        font-size: 15px;
    }
    .input {
        outline: none;
        width: 96%;
        height: 50px;
        background-color: #192138;
        border: 1px solid rgba(255, 255, 255, 0.5);
        border-radius: 4px;
        padding-left: 3%;
        font-family: "Inter";
        font-size: 18px;
        color: white;
        cursor: text;
        margin-top: -10px;
    }
    .input:focus {
        outline: none;
        border: 1px solid #01AB55;
    }
    #forgotten-password {
        color: #01AB55;
        cursor: pointer;
        width: 200px;
    }
    #login-btn {
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
        margin-top: -30px;
    }
</style> 