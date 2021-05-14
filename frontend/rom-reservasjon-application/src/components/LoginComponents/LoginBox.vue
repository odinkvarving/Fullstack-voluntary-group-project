<template>
  <div>
    <SuccessPopUp class="popup" v-show="isPopUpVisible" :message="message" />
    <ErrorPopUp
      class="popup"
      v-show="isErrorVisible"
      v-on:close-popup="closePopUp"
      :message="message"
    />
    <div id="login-page">
      <v-container>
        <v-row>
          <v-col cols="12">
            <h1>Velkommen tilbake</h1>
            <p class="white--text text--secondary">Logg på kontoen din</p>
          </v-col>
          <v-col cols="12">
            <p class="text">Skriv inn e-post</p>
            <v-text-field
              label="E-post"
              v-model="emailInput"
              v-on:keyup.enter="loginAccount"
              outlined
              color="green"
              :error-messages="eMailErrorMessage"
            ></v-text-field>
          </v-col>
          <v-col cols="12">
            <p class="text">Skriv inn passord</p>
            <v-text-field
              type="password"
              label="Passord"
              v-model="passwordInput"
              v-on:keyup.enter="loginAccount"
              outlined
              color="green"
              :error-messages="passwordErrorMessage"
            ></v-text-field>
          </v-col>
          <v-col cols="6">
            <p
              @click="() => this.$router.push('/forgotpassword')"
              class="green--text"
              style="cursor: pointer"
            >
              Glemt passord?
            </p>
          </v-col>
          <v-col cols="6" align="end">
            <v-btn color="green" @click="loginAccount"
              ><span>Logg inn</span></v-btn
            >
          </v-col>
        </v-row>
      </v-container>
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
    ErrorPopUp,
  },
  data() {
    return {
      isEmailValid: true,
      isPasswordValid: true,
      emailInput: "",
      passwordInput: "",
      isPopUpVisible: false,
      isErrorVisible: false,
      message: "",
      eMailErrorMessage: "",
      passwordErrorMessage: "",
    };
  },

  methods: {
    loginAccount() {
      this.isEmailValid = true;
      this.isPopUpVisible = false;
      this.isErrorVisible = false;

      if (!this.validateInput()) {
        return;
      }
      if (!this.validateEmail()) {
        document.getElementById("login-page").style.filter = "blur(5px)";
        this.message = "E-post eller passord er ugyldig";
        this.isEmailValid = false;
        this.isErrorVisible = true;
      }
      if (this.isEmailValid) {
        this.login();
      }
    },

    validateInput() {
      var isValid = true;
      if (this.emailInput === "") {
        this.isEmailValid = false;
        this.eMailErrorMessage = "Dette feltet kan ikke stå tomt";
        isValid = false;
      } else {
          this.eMailErrorMessage = "";
      }
      if (this.passwordInput === "") {
        this.isPasswordValid = false;
        this.passwordErrorMessage = "Dette feltet kan ikke stå tomt";
        isValid = false;
      } else {
          this.passwordErrorMessage = "";
      }
      return isValid;
    },

    validateEmail() {
      const re = /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
      return re.test(String(this.emailInput).toLowerCase());
    },

    async login() {
      const authRequest = {
        username: this.emailInput,
        password: this.passwordInput,
      };
      let state = await this.$store.dispatch("login", authRequest);
      console.log("Authenticated: " + state);

      if (state) {
        document.getElementById("login-page").style.filter = "blur(5px)";
        this.message = "Bruker logges inn";
        this.isPopUpVisible = true;
        setTimeout(() => this.$router.push("/frontpage"), 1000);
      } else {
        document.getElementById("login-page").style.filter = "blur(5px)";
        this.message = "E-post eller passord er ugyldig";
        this.isErrorVisible = true;
      }
    },

    closePopUp() {
      this.message = "";
      this.isErrorVisible = false;
      document.getElementById("login-page").style.filter = "none";
    },

    changePasswordVisibility() {
      let inputArea = document.getElementById("password");
      if (inputArea.type === "password") inputArea.type = "text";
      else inputArea.type = "password";
    },
  },
};
</script>

<style scoped>
.container {
  max-width: 600px;
}

.popup {
  position: absolute;
  background-color: #222b45;
  z-index: 2;
  top: 30%;
  left: 40%;
}
</style>
