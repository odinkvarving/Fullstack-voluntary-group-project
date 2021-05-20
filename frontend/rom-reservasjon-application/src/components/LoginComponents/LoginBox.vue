<template>
  <div>
    <div id="login-page">
      <v-container>
        <v-row>
          <v-col cols="12">
            <h1>Velkommen tilbake</h1>
            <p class="white--text text--secondary">Logg på kontoen din</p>
          </v-col>
          <v-col cols="12">
            <div style="position: relative">
              <v-alert
                :value="isErrorVisible"
                type="error"
                transition="scale-transition"
                style="position: absolute; width: 100%; bottom: 0px"
              >
                {{ message }}
              </v-alert>
              <v-alert
                :value="isPopUpVisible"
                type="success"
                transition="scale-transition"
                style="position: absolute; width: 100%; bottom: 0px"
              >
                {{ message }}
              </v-alert>
            </div>
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
              :type="isPasswordVisible ? 'text' : 'password'"
              :append-icon="isPasswordVisible ? 'visibility' : 'visibility_off'"
              label="Passord"
              v-model="passwordInput"
              v-on:keyup.enter="loginAccount"
              outlined
              color="green"
              :error-messages="passwordErrorMessage"
              @click:append="isPasswordVisible = !isPasswordVisible"
            ></v-text-field>
          </v-col>
          <!-- We wanted to implement "forgot password", even though it was not required. -->
          <!-- The mail sending was running smoothly without any problem,
           until one day, when we could not connect to the SMTP server anymore -->
          <!-- We couldn't find a fix for it -->
          <!-- <v-col cols="6">
            <p
              @click="() => this.$router.push('/forgotpassword')"
              class="green--text"
              style="cursor: pointer"
            >
              Glemt passord?
            </p>
          </v-col> -->
          <v-col cols="12" align="end">
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
/**
 * LoginBox is a component which represents the login area on login router.
 * The component enables login for all registered accounts.
 * 
 * @author Scott Rydberg Sonen
 */

export default {
  name: "LoginBox",
  components: {},
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
      isPasswordVisible: false
    };
  },

  methods: {
    /**
     * loginAccount is a function which firstly runs validate-checks for both email and password.
     * If email is valid, login function is run.
     */
    loginAccount() {
      this.isEmailValid = true;
      this.isPopUpVisible = false;
      this.isErrorVisible = false;

      if (!this.validateInput()) {
        return;
      }
      if (!this.validateEmail()) {
        this.message = "E-post eller passord er ugyldig";
        this.isEmailValid = false;
        this.isErrorVisible = true;
      }
      if (this.isEmailValid) {
        this.login();
      }
    },

    /**
     * validateInput is a function which validates email and password,
     *  to see if the input fields are empty or not.
     */
    validateInput() {
      let isValid = true;
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

    /**
     * validateEmail is a function which validates email,
     *  to see if the email is using a valid format.
     */
    validateEmail() {
      const re = /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
      return re.test(String(this.emailInput).toLowerCase());
    },

    /**
     * login is a function which tries to log in account.
     * If the login fails, the user will be noted.
     */
    async login() {
      const authRequest = {
        username: this.emailInput,
        password: this.passwordInput,
      };
      let account = await this.$store.dispatch("login", authRequest);
      console.log("Authenticated: " + account);

      if (account) {
        let date = new Date(account.expiration_date);
        let now = Date.now();
        if (date < now && account.expiration_date) {
          this.message = "Bruker er utløpt";
          this.isErrorVisible = true;
        } else {
          this.message = "Bruker logges inn";
          this.isPopUpVisible = true;
          setTimeout(() => this.$router.push("/frontpage"), 1000);
        }
      } else {
        this.message = "E-post eller passord er ugyldig";
        this.isErrorVisible = true;
      }
    },

    /**
     * closePopUp is a function which closes error pop up.
     */
    closePopUp() {
      this.message = "";
      this.isErrorVisible = false;
      document.getElementById("login-page").style.filter = "none";
    },
  },
};
</script>

<style scoped>
.container {
  max-width: 600px;
}
</style>
