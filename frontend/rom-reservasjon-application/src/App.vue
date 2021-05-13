<template>
  <v-app>
    <v-app-bar
      app
      color="#222B45"
      dark
      hide-on-scroll
    >
      <div class="d-flex align-center">
        <v-img
          contain
          src="./assets/Logo.png"
          width="40"
        />
      </div>

      <v-spacer></v-spacer>

      <v-btn
        href="#"
        text
      >
        <span>Hjem</span>
      </v-btn>
      <v-btn
        href="#"
        text
      >
        <span>Partytime</span>
      </v-btn>
      <v-btn
        href="#"
        color="#01AB55"
      >
        <span>Logg inn</span>
      </v-btn>
    </v-app-bar>

    <v-main>
      <HelloWorld/>
      <div style="height: 1000px"></div>
    </v-main>
  </v-app>
</template>

<script>
import HelloWorld from './components/HelloWorld';

export default {
  name: 'App',

  components: {
    HelloWorld,
  },

  data: () => ({
    //
  }),
};
</script>
