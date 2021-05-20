<template>
  <div class="room-feed">
          <div class="container">
              <div class="filter-column">
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
                        <GmapAutocomplete @place_changed="setPlace" style="width: 100%; height: 30px; color: white;" placeHolder="Legg til sted..." :selectFirstOnEnter="true" />
                    </div>
                    <div>
                    <GmapMap
                        :center="{lat:lat, lng:lng}"
                        :zoom="10"
                        map-type-id="roadmap"
                        style="width: 100%; height: 350px"
                        :options="{
                            disableDefaultUI: true
                        }"
                        >
                        <GmapMarker
                            :position="{lat:lat, lng:lng}"
                            :clickable="true"
                            @click="center={lat:lat, lng:lng}"
                        />
                        <GmapCircle
                            :center="{lat:lat, lng:lng}"
                            :radius="placeRadius*1000"
                            :visible="true"
                            :options="{fillColor:'red',fillOpacity:0.15}"
                        ></GmapCircle>
                        </GmapMap>
                    </div>
                    <v-row style="margin-top: 20px" align="center" justify="center">
                        <v-slider
                            max="100"
                            min="1"
                            style="margin: 0 10px;"
                            v-model="placeRadius"
                        ></v-slider>
                        <p >{{ placeRadius }} km</p>
                    </v-row>
                
                    <h3>Tidspunkt</h3>
                    <v-menu
                        ref="menu"
                        v-model="menu"
                        :close-on-content-click="false"
                        :return-value.sync="dateValue"
                        transition="scale-transition"
                        offset-y
                        min-width="auto"
                    >
                        <template v-slot:activator="{ on, attrs }">
                        <v-text-field
                            v-model="dateValue"
                            label="Dato"
                            prepend-inner-icon="mdi-calendar"
                            readonly
                            v-bind="attrs"
                            v-on="on"
                            outlined
                        ></v-text-field>
                        </template>
                        <v-date-picker
                        v-model="dateValue"
                        no-title
                        scrollable
                        >
                        <v-spacer></v-spacer>
                        <v-btn
                            text
                            color="primary"
                            @click="menu = false"
                        >
                            Cancel
                        </v-btn>
                        <v-btn
                            text
                            color="primary"
                            @click="$refs.menu.save(dateValue)"
                        >
                            OK
                        </v-btn>
                        </v-date-picker>
                    </v-menu>
                    <v-row>
                        <v-col cols="6">
                                <v-select
                                :items="timeValues"
                                label="Start"
                                outlined
                                v-model="startTimeValue"
                                ></v-select>
                        </v-col>
                        <v-col cols="6">
                                <v-select
                                :items="timeValues"
                                label="Slutt"
                                outlined
                                v-model="endTimeValue"
                                ></v-select>
                        </v-col>
                    </v-row>

                    <h2>Annet</h2>
                    <div>
                        <p>Ustyr</p>
                        <v-text-field
                            dense
                            hide-details
                            label="Utstyr"
                            color="#01AB55"
                            outlined
                            v-model="equipmentValue"
                        ></v-text-field>
                    </div>
                    <v-row>
                        <v-col>
                            <v-btn color="#01AB55" block @click="searchButtonClicked"><span>Søk</span></v-btn>                    
                        </v-col>
                        <v-col>
                            <v-btn v-if="!isNoFilters" class="ma-1" color="error" plain block @click="resetButtonClicked"><span>Reset</span></v-btn>                    
                        </v-col>
                    </v-row>
              </div>
              <div class="room-list-column" >
                  <div v-for="(room) in rooms" :key="room.id" >
                    <v-row align="center" justify="center">
                        <v-col>
                            <h1>{{ room.name }}</h1>
                            <p>{{ room.address }}</p>
                            <v-row class="number-info-row">
                                <p>Plasser: {{ room.max_persons }}</p>
                                <p>Størrelse: {{ room.size }}</p>
                            </v-row>
                        </v-col>
                        <v-spacer></v-spacer>
                        <v-col>
                            <p>{{ room.description }}</p>
                        </v-col>
                        <v-spacer></v-spacer>
                        <v-col>
                            <v-btn color="#01AB55" @click="goToRoomButtonClicked(room.id)"><span>Gå til rom</span></v-btn>               
                        </v-col>
                    </v-row>
                  </div>
              </div>
          </div>
  </div>
</template>

<script>

export default {
    name: "RoomFeed",
    data(){
        return {
            searchValue: "",
            placeValue: "",
            placeRadius: 10,
            startTimeValue: "",
            endTimeValue: "",
            dateValue: "",
            menu: false,
            modal: false,
            menu2: false,
            equipmentValue: "",
            isNoFilters: true,
            filteredList: [],
            timeValues: [
                '07:00', '08:00', '09:00', '10:00', '11:00',
                '12:00', '13:00', '14:00', '15:00', '16:00', 
                '17:00', '18:00', '19:00', '20:00', '21:00',
                '22:00','23:00',
            ]
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
        roomSize(room){
            console.log(room);
            let size = 0;

            return size;
        },
        lat(){
            if(this.placeValue.geometry){
                return this.placeValue.geometry.location.lat();
            }else{
                return 59.911491;
            }
        },
        lng(){
            if(this.placeValue.geometry){
                return this.placeValue.geometry.location.lng();
            }else{
                return 10.757933;
            }
        }
    },
    methods: {
        goToRoomButtonClicked(roomId){
            this.$router.push(`/rooms/${roomId}`);
        },
        searchButtonClicked(){
            if(this.searchValue !== "" || this.placeValue !== "" || this.startTimeValue !== ""
                || this.endTimeValue !== "" || this.dateValue !== "" || this.equipmentValue !== ""){
                
                this.filteredList = this.$store.getters.getRooms;

                if(this.searchValue !== ""){
                    this.filteredList = this.$store.getters.getRooms.filter((room) => {
                        return room.name.toLowerCase().includes(this.searchValue.toLowerCase());
                    });
                }
                if(this.placeValue !== "" && this.placeValue.geometry !== null){
                    let coords1 = {
                        lat: this.lat,
                        lng: this.lng
                    };
                    this.filteredList = this.filteredList.filter(room => {
                        return this.calcCrow(coords1, {lat: room.lat, lng: room.lng}) <= parseInt(this.placeRadius);
                    })
                }

                if(this.dateValue !== "" && this.startTimeValue !== "" && this.endTimeValue !== ""){
                    return this.filteredList; //TODO: ADD IMPLEMENTATION FOR CHECKING IF THIS TIMEPERIOD IS FREE
                }
                
                if(this.equipmentValue !== ""){
                    this.filteredList = this.filteredList.filter(room => {
                        return room.sections.filter(section => section.equipmentDescription.includes(this.equipmentValue)).length > 0
                    })
                }

                this.isNoFilters = false;
                console.log(this.filteredList);
            }else{
                this.isNoFilters = true;
            }
        },
        resetButtonClicked(){
            this.searchValue = "";
            this.placeValue = "";
            this.startTimeValue = "";
            this.endTimeValue = "";
            this.dateValue = "";
            this.equipmentValue = "";
            this.isNoFilters = true;
        },
        setPlace(place){
            this.placeValue = place;
            console.log(this.placeValue);
        },
        calcCrow(coords1, coords2){
            // km
            let R = 6371;
            let dLat = this.toRad(coords2.lat-coords1.lat);
            let dLon = this.toRad(coords2.lng-coords1.lng);
            let lat1 = this.toRad(coords1.lat);
            let lat2 = this.toRad(coords2.lat);

            let a = Math.sin(dLat/2) * Math.sin(dLat/2) +
                Math.sin(dLon/2) * Math.sin(dLon/2) * Math.cos(lat1) * Math.cos(lat2); 
            let c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a)); 
            let d = R * c;
            return d;
        },
        toRad(Value){
            return Value * Math.PI / 180;
        }
    },
     mounted(){
        if(!this.$store.getters.isAuthenticated){
            this.$router.push("/");
        }
    }
}
</script>

<style scoped>
.room-feed {
    min-height: 100vh;
    width: 100%;
    background-color: #192138;
}

.container{
    padding-top: 100px;
    max-width: 100vw;
    display: flex;
    flex-direction: row;
    justify-content: space-between;
}

.filter-column {
    background-color: rgba(0, 0, 0, 0.23);
    padding: 40px;
    margin-left: 50px;
    border-radius: 20px;
    width: 21%;
    max-height: 1000px;
}

.room-list-column {
    width: 71%;
    margin-right: 50px;
}

.number-info-row{
    margin: 0;
}

.number-info-row p{
    font-size: 10px;
}
</style>