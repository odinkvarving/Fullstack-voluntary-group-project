<template>
    <div>
        <SuccessPopUp class="popup" v-show="isPopUpVisible" :message="message"/>
        <ErrorPopUp class="popup" v-show="isErrorVisible" v-on:close-popup="closePopUp" :message="message"/>
        <div id="password-reset-box">
            <button class="close-btn" @click="returnPage">X</button>
            <h1>Tilbakestill passord</h1>
            <p class="sub-title">Skriv inn et nytt passord</p>
            <div class="input-container">
                <div class="new-password">
                    <p class="text">Skriv inn nytt passord</p>
                    <input type="text" class="input" placeholder="Nytt passord" v-model="passwordInput" v-on:keyup.enter="reset"/>
                </div>
                <div class="confirm-password">
                    <p class="text">Gjenta passord</p>
                    <input type="text" class="input" placeholder="Gjenta passord" v-model="confirmationInput" v-on:keyup.enter="reset"/>
                </div>
            </div>
            <button class="reset-btn" @click="reset">Fullfør</button>
        </div>
    </div>
</template>
<script>
    import SuccessPopUp from "../PopUpComponents/SuccessPopUp.vue";
    import ErrorPopUp from "../PopUpComponents/ErrorPopUp.vue";

    export default {
        name: "ResetPasswordBox",
        components: {
            SuccessPopUp,
            ErrorPopUp
        },

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

        async created() {
            const requestOptions = {
                method: "GET"
            }
            await fetch("http://localhost:8080/reset/" + this.suffix, requestOptions)
                .then(response => response.json())
                .then(data => {
                    this.account = data;
                    console.log("TEST");
                    console.log(this.account.password);
                })
                .catch(error => console.error(error));
        },

        methods: {
            reset() {
                console.log(this.account);
                this.isPopUpVisible = false;
                this.isErrorVisible = false;
                if (!this.validatePassword()) {
                    document.getElementById("password-reset-box").style.filter = "blur(5px)";
                    this.message = "Passordet må være mellom 8 og 30 tegn";
                    this.isErrorVisible = true;
                    return;
                }
                if (!this.validateEquality()) {
                    document.getElementById("password-reset-box").style.filter = "blur(5px)";
                    this.message = "Passordene er ikke like";
                    this.isErrorVisible = true;
                    return;
                }

                
                /*const requestOptions = {
                    method: "PUT",
                    body: this.passwordInput
                }*/
            },

            validatePassword() {
                return this.passwordInput.length >= 8 && this.passwordInput.length <= 30;
            },

            validateEquality() {
                return this.passwordInput === this.confirmationInput;
            },

            closePopUp() {
                this.message = "";
                this.isErrorVisible = false;
                document.getElementById("password-reset-box").style.filter = "none";
            },

            redirectPage() {
                setTimeout(() => this.$router.push("/"), 3000);
            },

            returnPage() {
                this.$router.push("/");
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
    #password-reset-box {
        position: relative;
        z-index: 1;
    }
    .close-btn {
        float: right;
        width: 40px;
        height: 40px;
        background-color: #993939;
        border-radius: 4px;
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
    .new-password {
        margin-top: 50px;
    }
    .confirm-password {
        margin-top: 20px;
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
    .reset-btn {
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