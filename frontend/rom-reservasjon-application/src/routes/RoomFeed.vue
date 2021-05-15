<template>
  <div class="room-feed">
      <v-container class="vue-container">
          <v-row align="center" justify="center">
              <v-col cols="3" class="filter-column">
                  <div>
                    <p>Søk etter rom</p>
                    <v-text-field
                        dense
                        hide-details
                        label="Søk"
                        color="#01AB55"
                        outlined
                        v-model="searchValue"
                    ></v-text-field>
                   </div>
                    <div>
                        <p>Område i kart</p>
                        <v-text-field
                            dense
                            hide-details
                            label="Søk etter sted eller adresse"
                            color="#01AB55"
                            outlined
                        ></v-text-field>
                    </div>
                <div>KARTKARTKART</div>
                
                <h2>Tid</h2>
                <v-row>
                    <v-col>
                        <p>start</p>
                        <v-text-field
                            dense
                            hide-details
                            label="Dato"
                            color="#01AB55"
                            outlined
                        ></v-text-field>
                        <vue-timepicker></vue-timepicker>                    
                    </v-col>
                    <v-col>
                        <p>Slutt</p>
                        <v-text-field
                            dense
                            hide-details
                            label="Dato"
                            color="#01AB55"
                            outlined
                        ></v-text-field>  
                        <vue-timepicker></vue-timepicker>                  
                    </v-col>
                </v-row>

                <div>
                    <p>Dato</p>
                    <v-text-field
                        dense
                        hide-details
                        label="Dato"
                        color="#01AB55"
                        outlined
                    ></v-text-field>
                </div>

                <h2>Annet</h2>
                <div>
                    <p>Ustyr</p>
                    <v-text-field
                        dense
                        hide-details
                        label="Utstyr"
                        color="#01AB55"
                        outlined
                    ></v-text-field>
                </div>
                <v-row>
                    <v-col>
                        <v-btn color="#01AB55" block @click="searchButtonClicked"><span>Søk</span></v-btn>                    
                    </v-col>
                    <v-col>
                        <v-btn class="ma-1" color="error" plain block><span>Reset</span></v-btn>                    
                    </v-col>
                </v-row>
              </v-col>
              <v-col cols="6" class="room-list-column" >
                  <div v-for="(room) in rooms" :key="room.id" >
                    <v-row align="center" justify="center">
                        <v-col>
                            <h1>{{ room.name }}</h1>
                            <p>{{ room.address }}</p>
                        </v-col>
                        <v-col>
                            <p>{{ room.description }}</p>
                        </v-col>
                        <v-col>
                            <v-btn color="#01AB55" @click="goToRoomButtonClicked(room.id)"><span>Gå til rom</span></v-btn>               
                        </v-col>
                    </v-row>
                  </div>
              </v-col>
          </v-row>
      </v-container>
  </div>
</template>

<script>
import VueTimepicker from 'vue2-timepicker'
import 'vue2-timepicker/dist/VueTimepicker.css'

export default {
    name: "RoomFeed",
    components: {
        VueTimepicker
    },
    data(){
        return {
            searchValue: "",
            placeValue: "",
            startTimeValue: "",
            endTimeValue: "",
            dateValue: "",
            equipmentValue: "",
            isNoFilters: true,
            filteredList: []
        }
    },
    computed: {
        rooms(){
            if(this.isNoFilters){
                return this.$store.getters.getRooms;
            }else{
                return this.filteredList;
            }
        },
    },
    methods: {
        goToRoomButtonClicked(roomId){
            this.$router.push(`/rooms/${roomId}`);
        },
        searchButtonClicked(){
            if(this.searchValue !== "" || this.placeValue !== "" || this.startTimeValue !== ""
                || this.endTimeValue !== "" || this.dateValue !== "" || this.equipmentValue !== ""){
                this.filteredList = this.$store.getters.getRooms.filter((room) => {
                    return room.name.includes(this.searchValue);
                });
                this.isNoFilters = false;
                console.log(this.filteredList);
            }
        }
    }
}
</script>

<style scoped>
.room-feed {
    height: 100vh;
    width: 100%;
    background-color: #192138;
}

.vue-container{
    max-width: 100vw;
}

.filter-column {
    border: 1px solid white;
}

.room-list-column {
    border: 1px solid white;
}
</style>