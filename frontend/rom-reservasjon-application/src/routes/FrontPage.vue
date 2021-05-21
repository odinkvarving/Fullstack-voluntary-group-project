<template>
  <div class="frontpage">
    <v-container>
      <v-row align="center" justify="center" class="pt-10">
        <v-col cols="12" sm="6" class="header-text">
          <h1 class="white--text">
            Finn et keegt rom for<br />deg og dine
            <span style="color: #01AB55">behov</span>
          </h1>
          <p class="white--text text--secondary text-justify">
            Gjennom oss kan du enkelt finne og reservere et rom til deg og dine kollegaer,
            i tillegg til å reservere utstyr.
          </p>
          <v-row>
            <router-link class="link" to="/roomfeed">
              <v-col cols="6">
                <v-btn color="green" >
                  <v-icon left>
                    mdi-magnify
                  </v-icon>
                  <span>Se alle rom</span>
                </v-btn>
              </v-col>
            </router-link>
            <router-link class="link" to="/equipmentfeed">
              <v-col cols="6">
                <v-btn color="green">
                  <v-icon left>
                    mdi-magnify
                  </v-icon>
                  <span>Se alt utstyr</span>
                </v-btn>
              </v-col>
            </router-link>
          </v-row>
        </v-col>
        <v-col cols="12" sm="6">
          <v-img :src="require('../assets/minimalist-room.png')"> </v-img>
        </v-col>
      </v-row>
    </v-container>
  </div>
</template>

<script>
/**
 * @author Mattias Agentoft Eggen
 */
export default {
  name: "FrontPage",
  components: {},
  computed: {
    isLoggedIn(){
      return this.$store.getters.isAuthenticated;
    }
  }
};
</script>

<style scoped>
.frontpage {
  background-color: #192138;
  min-height: 100vh;
}
h1 {
  font-size: 3em;
}
@media (max-width: 700px) {

  h1{
    font-size: 1.7em;
  }

  .header-text {
    margin-top: 40px;
    display: flex;
    flex-direction: column;
    align-items: center;
  }
}
</style>
