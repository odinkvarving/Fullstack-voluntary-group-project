<template>
  <v-container fill-height>
    <v-row>
      <v-col cols="12">
        <h1>A4-112, Realfagbygget, Gløshaugen</h1>
      </v-col>
      <v-col cols="12" md="6">
        <v-card  outlined color="#222b45">
          <v-card-text>
            <v-container>
              <v-row>
                <v-col cols="6">
                  Seksjon
                </v-col>
                <v-col cols="6">Data</v-col>
              </v-row>

              <v-row>
                <v-col cols="6">
                  Antall
                </v-col>
                <v-col cols="6">Data</v-col>
              </v-row>

              <v-row>
                <v-col cols="6">
                  Dato
                </v-col>
                <v-col cols="6">Data</v-col>
              </v-row>

              <v-row>
                <v-col cols="6">
                  Klokkeslett
                </v-col>
                <v-col cols="6">Data</v-col>
              </v-row>

              <v-row>
                <v-col cols="6">
                  Beskrivelse
                </v-col>
                <v-col cols="12">
                  <v-card  outlined color="#222b45">
                    <v-card-text>
                      Lorem ipsum, dolor sit amet consectetur adipisicing elit.
                      Pariatur, odit dolorum placeat ipsum sed obcaecati
                      corrupti, perspiciatis laboriosam fuga consequuntur quos?
                      Eos labore aspernatur quibusdam ipsum delectus deleniti
                      quae atque.
                    </v-card-text>
                  </v-card>
                </v-col>
              </v-row>
            </v-container>
          </v-card-text>
        </v-card>
      </v-col>
      <v-col cols="12" md="6">
        <v-card  outlined color="#222b45">
          <v-card-text>
            <v-col cols="12">
              <h2>Bruker</h2>
            </v-col>
            <v-col>
              <v-row>
                <v-col cols="6">Navn</v-col>
                <v-col>Data</v-col>
              </v-row>
              <v-row>
                <v-col cols="6">E-post</v-col>
                <v-col>Data</v-col>
              </v-row>
              <v-row>
                <v-col cols="6">Telefonnummer</v-col>
                <v-col>Data</v-col>
              </v-row>
            </v-col>
          </v-card-text>
        </v-card>
        <div style="height: 24px"></div>
        <v-card  outlined color="#222b45">
          <v-card-text>
            <v-container>
              <v-row>
                <v-col cols="12">
                  <h2>Utstyr</h2>
                </v-col>
                <v-col>
                  <v-row>
                    <v-col cols="6">Data</v-col>
                    <v-col cols="6">Data</v-col>
                    <v-col cols="6">Data</v-col>
                  </v-row>
                </v-col>
              </v-row>
            </v-container>
          </v-card-text>
        </v-card>
        <v-row justify="space-between" class="pt-5">
          <v-col cols="6">
            <v-btn color="green">Endre reservasjon</v-btn>
          </v-col>
          <v-col cols="6" align="end">
            <v-btn color="error">Slett reservasjon</v-btn>
          </v-col>
        </v-row>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
export default {
  name: "ReservationAdminPage",
  data() {
    return {
        roomName: "",
        section: "",
        amount: "",
        date: "",
        time: "",
        description: "",

        username: "",
        userEmail: "",
        userPhone: "",

        equipment: [],
    };
  },

  computed: {

  },

  methods: {

  }
};
</script>

<style></style>
