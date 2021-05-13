<template>
    <div>
        <SuccessPopUp class="popup" v-show="isPopUpVisible" :message="message"/>
        <ErrorPopUp class="popup" v-show="isErrorVisible" v-on:close-popup="closePopUp" :message="message"/>
        <div id="login-box">
            <h1>Velkommen tilbake</h1>
            <p class="sub-title">Logg inn på kontoen din</p>
            <div class="login-container">
                <div id="email-container">
                    <p class="text">Skriv inn e-post</p>
                    <input type="text" class="input" id="email" placeholder="E-post" v-model="emailInput" v-on:keyup.enter="loginAccount"/>
                </div>
                <div id="password-container">
                    <p class="text">Skriv inn passord</p>
                    <input type="password" class="input" id="password" placeholder="Passord" v-model="passwordInput" v-on:keyup.enter="loginAccount"/>
                </div>
            </div>
            <p class="text" id="forgotten-password" @click="() => this.$router.push('/forgotpassword')">Glemt passord?</p>
            <button id="login-btn" @click="loginAccount">Logg inn</button>
        </div>
    </div>
</template>
<script>
    import SuccessPopUp from "../PopUpComponents/SuccessPopUp.vue";
    import ErrorPopUp from "../PopUpComponents/ErrorPopUp.vue";

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
                isErrorVisible: false,
                message: ""
            }
        },

        methods: {
            loginAccount() {
                this.isEmailValid = true;
                this.isPopUpVisible = false;
                this.isErrorVisible = false;
                if (this.emailInput === "" || this.passwordInput === "") {
                    document.getElementById("login-box").style.filter = "blur(5px)";
                    this.message = "E-post- eller passordfeltet kan ikke være tomt";
                    this.isErrorVisible = true;
                    return;
                }
                if (!this.validateEmail()) {
                    document.getElementById("login-box").style.filter = "blur(5px)";
                    this.message = "E-post eller passord er ugyldig";
                    this.isEmailValid = false;
                    this.isErrorVisible = true;
                }
                if (this.isEmailValid) {
                    this.login();
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
                let state = await this.$store.dispatch("login", authRequest);
                console.log("Authenticated: " + state);

                if (state) {
                    document.getElementById("login-box").style.filter = "blur(5px)";
                    this.message = "Bruker logges inn";
                    this.isPopUpVisible = true;
                    setTimeout(() => this.$router.push("/frontpage"), 1000);
                } else {
                    document.getElementById("login-box").style.filter = "blur(5px)";
                    this.message = "E-post eller passord er ugyldig";
                    this.isErrorVisible = true;
                }
            },

            closePopUp() {
                this.message = "";
                this.isErrorVisible = false;
                document.getElementById("login-box").style.filter = "none";
            },

            changePasswordVisibility() {
                let inputArea = document.getElementById("password");
                if (inputArea.type === "password") inputArea.type = "text";
                else inputArea.type = "password";
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
        font-size: 17px;
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
    #forgotten-password {
        color: #01AB55;
        cursor: pointer;
        width: 200px;
        margin-top: 20px;
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
        margin-top: -20px;
    }
</style> 