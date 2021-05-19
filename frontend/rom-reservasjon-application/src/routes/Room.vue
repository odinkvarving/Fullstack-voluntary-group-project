<template>
  <div class="room">
    <div class="top-header">
      <div class="title">
      <h1>{{ room.name }}</h1>
      <p>{{ room.address }}</p>
      </div>
      <v-btn color="green" v-if="!showRoomReservation" @click="roomReservationClicked"><span>Reserver hele rommet</span></v-btn>
      <v-btn color="red" v-else @click="roomReservationClicked"><span>Lukk</span></v-btn>
      <p class="or-text">eller velg seksjon nedenfor</p>
    </div>

    <div v-if="showRoomReservation">
      <Section :allSections="room.sections" />
    </div>
    <v-container>
      <v-row>
        <v-col lg="3" md="4" xs="12" v-for="section in room.sections" :key="section.id">
          <div class="section-container" @click="sectionClicked(section.id)">
            <h2>{{ section.name }}</h2>
            <p style="opacity: 75%">{{ section.description }}</p>
            <div class="line"/>
            <div class="section-footer">
                <p>{{ section.max_persons }} plasser</p>
            </div>
          </div>
        </v-col>
      </v-row>
    </v-container>
  </div>
</template>

<script>
import Section from "./Section"

export default {
  name: "Room",
  components: {
    Section
  },
  data(){
    return {
      showRoomReservation: false,
    }
  },
  computed: {
    room(){
      return this.$store.getters.getRooms.filter((room) => room.id === parseInt(this.$route.params.id))[0] || [];
    }
  },
  methods: {
    sectionClicked(sectionId){
      this.$router.push(`/rooms/${this.$route.params.id}/sections/${sectionId}`)
    },
    roomReservationClicked(){
      if(this.showRoomReservation){
        this.showRoomReservation = false;
      }else{
        this.showRoomReservation = true;
      }
    }
  }
}
</script>

<style scoped>
  .room{
    background-color: #192138;
    min-height: 100vh;
    padding-top: 100px;
  }

  .section-container{
        border: 1px solid #01AB55;
        height: 100%;
        width: 100%;
        border-radius: 5px;
        padding: 20px 20px 0 20px;
        display: flex;
        flex-direction: column;
        align-items: center;
        justify-content: center;
  }

  .section-container:hover{
    cursor: pointer;
  }

  .section-container p{
    margin: 20px 0;
  }

  .top-header{
    text-align: center;
  }

  .top-header .title{
    margin-bottom: 40px;
  }

  .top-header .title h1{
    font-size: 40px;
  }

  .top-header .title p{
    font-size: 20px;
    opacity: 70%;
  }

  .or-text{
    font-size: 16px;
    opacity: 70%;
    margin-top: 40px;
  }
</style>