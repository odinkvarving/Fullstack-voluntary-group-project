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
            Følelsen når du rygger over kompisen din med tanksen og du må putte
            han i en jar fordi han er helt potetmos.
          </p>
          <v-row>
            <router-link class="link" to="/roomfeed">
              <v-col cols="6">
                <v-btn color="green"><span>Se alle rom</span></v-btn>
              </v-col>
            </router-link>
          </v-row>
        </v-col>
        <v-col cols="12" sm="6">
          <v-img :src="require('../assets/minimalist-room.png')"> </v-img>
        </v-col>
      </v-row>
    </v-container>
<!--
    <div class="selection-field">
      <v-container pt-10 pb-10>
        <v-row align="center">
          <v-col cols="4" xs="4" sm="2">
            <v-text-field
              dense
              hide-details
              label="Start"
              color="green"
              outlined
            ></v-text-field>
          </v-col>
          <v-col cols="4" xs="4" sm="2">
            <v-text-field
              dense
              hide-details
              label="Slutt"
              color="green"
              outlined
            ></v-text-field>
          </v-col>
          <v-col cols="4" xs="4" sm="2">
            <v-text-field
              dense
              hide-details
              label="Dato"
              color="green"
              outlined
            ></v-text-field>
          </v-col>

          <v-spacer></v-spacer>

          <v-col cols="4" xs="4" sm="2" order="first" order-sm="0">
            <v-text-field
              dense
              hide-details
              label="Søk"
              color="green"
              outlined
            ></v-text-field>
          </v-col>
          <v-col cols="8" xs="8" sm="2" order="first" order-sm="0">
            <v-btn color="green"><span>Søk</span></v-btn>
          </v-col>
        </v-row>
        <v-row align="center">
          <v-col cols="4" xs="4" sm="2">
            <v-text-field
              dense
              hide-details
              label="Område"
              color="green"
              outlined
            ></v-text-field>
          </v-col>
          <v-col cols="4" xs="4" sm="2">
            <v-text-field
              dense
              hide-details
              label="Bygning"
              color="green"
              outlined
            ></v-text-field>
          </v-col>
          <v-col cols="4" xs="4" sm="2">
            <v-text-field
              dense
              hide-details
              label="Romtype"
              color="green"
              outlined
            ></v-text-field>
          </v-col>
        </v-row>
        <v-row align="center">
          <v-col cols="4" xs="4" sm="2">
            <v-text-field
              dense
              hide-details
              label="Utstyr"
              color="green"
              outlined
            ></v-text-field>
          </v-col>
        </v-row>
        <v-row align="center">
          <v-col cols="4" xs="4" sm="2">
            <v-btn color="green"><span>Finn rom</span></v-btn>
          </v-col>
        </v-row>
      </v-container>
    </div>
    -->
  </div>
</template>

<script>
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
