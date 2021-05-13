<template>
  <v-app>
    <Navbar />
    <v-main>
      <FrontPage />
    </v-main>
  </v-app>
</template>

<script>
import FrontPage from './routes/FrontPage';
import Navbar from './components/Navbar/Navbar'

export default {
  name: 'App',

  components: {
    FrontPage,
    Navbar,
  },

  data: () => ({
    //
  }),
};
</script>
