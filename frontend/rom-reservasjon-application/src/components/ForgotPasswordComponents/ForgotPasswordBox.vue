<template>
    <div>
        
        <div class="input-box">
            <h1 class="header">Glemt passord?</h1>
            <p class="sub-title">Skriv inn e-postadressen din nedenfor</p>
            <div class="input-container">
            <p v-if="!isEmailValid">Skriv inn en gyldig e-postadresse</p>
            <input type="text" class="input" id="email" placeholder="E-post" v-model="emailInput" v-bind:disabled="isSent" v-on:keyup.enter="postAddress"/>
            </div>
            <div v-if="isSent" class="info">
            <p>Hvis {{ emailInput }} er registrert vil det bli sendt en e-post dit</p>
            </div>
            <button id="submit-btn" @click="postAddress" v-bind:disabled="isSent">Send</button>
        </div>
    </div>
</template>
<script>

    export default {
    name: "ForgotPasswordBox",

    data() {
        return {
            isEmailValid: true,
            emailInput: "",
            isSent: false,
        };
    },

    methods: {
        postAddress() {
            this.isEmailValid = true;
            if (!this.validateEmail()) {
                this.isEmailValid = false;
            }
            if (this.isEmailValid) {
                this.send();
                this.isSent = true;
            }
        },

        redirectPage() {
            setTimeout(() => this.$router.push("/"), 1000);
        },

        validateEmail() {
            const re = /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
            return re.test(String(this.emailInput).toLowerCase());
        },

        async send() {
            await fetch("http://localhost:8080/reset/" + this.emailInput, {
                method: "POST",
            });
        },
    },
    };
</script>
<style scoped>
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