<template>
  <div class="frontpage">
    <v-container>
      <v-row align="center" justify="center">
        <v-col cols="12" sm="6">
          <h1 class="white--text">
            Finn et keegt rom for<br />deg og dine
            <span style="color: #01AB55">behov</span>
          </h1>
          <p class="white--text text--secondary text-justify">
            Følelsen når du rygger over kompisen din med tanksen og du må putte
            han i en jar fordi han er helt potetmos.
          </p>
          <v-row>
            <v-col cols="6">
              <v-btn color="#01AB55"><span>Se alle rom</span></v-btn>
            </v-col>
          </v-row>
        </v-col>
        <v-col cols="12" sm="6">
          <v-img :src="require('../assets/minimalist-room.png')"> </v-img>
        </v-col>
      </v-row>
    </v-container>

    <div class="selection-field">
      <v-container pt-10 pb-10>
        <v-row align="center">
          <v-col cols="4" sm="2">
            <v-text-field
              dense
              hide-details
              label="Start"
              color="#01AB55"
              outlined
            ></v-text-field>
          </v-col>
          <v-col cols="4" sm="2">
            <v-text-field
              dense
              hide-details
              label="Slutt"
              color="#01AB55"
              outlined
            ></v-text-field>
          </v-col>
          <v-col cols="4" sm="2">
            <v-text-field
              dense
              hide-details
              label="Dato"
              color="#01AB55"
              outlined
            ></v-text-field>
          </v-col>

          <v-spacer></v-spacer>

          <v-col cols="4" sm="2" order-xs="first">
            <v-text-field
              dense
              hide-details
              label="Søk"
              color="#01AB55"
              outlined
            ></v-text-field>
          </v-col>
          <v-col cols="4" sm="2" order-xs="first">
            <v-btn color="#01AB55"><span>Søk</span></v-btn>
          </v-col>
        </v-row>
        <v-row align="center">
          <v-col cols="4" sm="2">
            <v-text-field
              dense
              hide-details
              label="Område"
              color="#01AB55"
              outlined
            ></v-text-field>
          </v-col>
          <v-col cols="4" sm="2">
            <v-text-field
              dense
              hide-details
              label="Bygning"
              color="#01AB55"
              outlined
            ></v-text-field>
          </v-col>
          <v-col cols="4" sm="2">
            <v-text-field
              dense
              hide-details
              label="Romtype"
              color="#01AB55"
              outlined
            ></v-text-field>
          </v-col>
        </v-row>
        <v-row align="center">
          <v-col cols="4" sm="2">
            <v-text-field
              dense
              hide-details
              label="Utstyr"
              color="#01AB55"
              outlined
            ></v-text-field>
          </v-col>
        </v-row>
        <v-row align="center">
          <v-col cols="4" sm="2">
            <v-btn color="#01AB55"><span>Finn rom</span></v-btn>
          </v-col>
        </v-row>
      </v-container>
    </div>
  </div>
</template>

<script>
export default {
  name: "FrontPage",
  components: {},
  data() {
    return {};
  },
};
</script>

<style scoped>
.frontpage {
  background-color: #192138;
}

.selection-field {
  background-color: #222b45;
}

h1 {
  font-size: 3em;
}
</style>
