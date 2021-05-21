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
                        @keyup.enter="searchButtonClicked"
                    ></v-text-field>
                   </div>
                    <div>
                        <p style="margin: 30px 0 0 0">Område i kart</p>
                        <div>
                            <GmapAutocomplete @keyup.enter="searchButtonClicked" @place_changed="setPlace" class="place-auto-input" placeHolder="Legg til sted..." :selectFirstOnEnter="true" />
                        </div>
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
                        :close-on-content-click="true"
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
                        :min="new Date().toISOString().substr(0, 10)"
                        @change="$refs.menu.save(dateValue)"
                        >
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
                                :items="endTimeList"
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
                            @keyup.enter="searchButtonClicked"
                        ></v-text-field>
                    </div>
                    <v-row style="margin: 20px 0">
                        <v-col style="padding: 10px 0" cols="5">
                            <v-btn color="#01AB55" block @click="searchButtonClicked">
                                <v-icon left>
                                    mdi-magnify
                                </v-icon>
                                <span>Søk</span>
                            </v-btn>                    
                        </v-col>
                        <v-spacer></v-spacer>
                        <v-col style="padding: 10px 0" cols="5">
                            <v-btn v-if="!isNoFilters" color="error" plain block @click="resetButtonClicked">
                                <v-icon left>
                                    mdi-close
                                </v-icon>
                                <span>Reset</span>
                            </v-btn>                    
                        </v-col>
                    </v-row>
              </div>
              <h2 class="results-tag">Resultater: </h2>
              <div class="room-list-column" >
                  <div v-for="(room) in rooms" :key="room.id" class="room-container" @click="goToRoom(room.id) ">
                    <v-row align="center" justify="center">
                        <v-col lg="5" cols="12">
                            <h1>{{ room.name }}</h1>
                            <p>{{ room.address }}</p>
                            <v-row class="number-info-row">
                                <p>Plasser: {{ room.max_persons }}</p>
                                <p>Størrelse: {{ room.size }}</p>
                            </v-row>
                        </v-col>
                        <v-spacer></v-spacer>
                        <v-col lg="5" cols="12">
                            <p>{{ room.description }}</p>
                        </v-col>
                        <v-spacer></v-spacer>
                        <v-col lg="2" cols="12">
                            <v-btn class="room-button" color="#01AB55" @click.stop="goToRoom(room.id)"><span>Gå til rom</span></v-btn>               
                        </v-col>
                    </v-row>
                  </div>
              </div>
          </div>
  </div>
</template>

<script>
/**
 * RoomFeed gives an overview of all rooms.
 * 
 * @author Magnus Bredeli
 */
import moment from "moment";

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
        /**
         * rooms returns a list of rooms (filtered or not).
         */
        rooms(){
            if(this.isNoFilters){
                return this.$store.getters.getRooms;
            }else{
                return this.filteredList;
            }
        },

        /**
         * roomSize returns size of room.
         */
        roomSize(room){
            console.log(room);
            let size = 0;

            return size;
        },

        endTimeList(){
            return this.timeValues.filter(time => {
                return parseInt(time.split(":")[0]) > this.startTimeValue.split(":")[0];
            });
        },
        lat(){
            if(this.placeValue.geometry){
                return this.placeValue.geometry.location.lat();
            }else{
                return 63.446827;
            }
        },
        lng(){
            if(this.placeValue.geometry){
                return this.placeValue.geometry.location.lng();
            }else{
                return 10.421906;
            }
        }
    },
    methods: {
        /**
         * goToRoom routes the user to given room by id.
         */
        goToRoom(roomId){
            this.$router.push(`/rooms/${roomId}`);
        },
        /**
         * searchButtonClicked handles the search after button is clicked.
         */
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
                    let dateFrom = moment(`${this.dateValue} ${this.startTimeValue}:00`, "YYYY-MM-DD HH:mm:ss");
                    let dateTo = moment(`${this.dateValue} ${this.endTimeValue}:00`, "YYYY-MM-DD HH:mm:ss");

                    this.filteredList = this.filteredList.filter(room => {
                        return room.sections.filter(section => {
                            let containsValidTime = true;
                            section.inReservations.forEach(reservation => {
                                let resFromArr = reservation.from_date.split("T");
                                let resToArr = reservation.to_date.split("T");
                                let reservationFromMoment = moment(`${resFromArr[0]} ${resFromArr[1]}`, "YYYY-MM-DD HH:mm:ss");
                                let reservationToMoment = moment(`${resToArr[0]} ${resToArr[1]}`, "YYYY-MM-DD HH:mm:ss");  
                                
                                if((reservationFromMoment.isBefore(dateTo) && reservationFromMoment.isAfter(dateFrom)) || 
                                    reservationToMoment.isAfter(dateFrom) && reservationToMoment.isBefore(dateTo) ||
                                    (reservationFromMoment.isBefore(dateFrom) && reservationToMoment.isAfter(dateTo)) ||
                                    reservationFromMoment.isSame(dateFrom) || reservationToMoment.isSame(dateTo)){
                                        containsValidTime = false;
                                }
                            });
                            return containsValidTime;
                        }).length > 0;
                    });
                }
                
                if(this.equipmentValue !== ""){
                    this.filteredList = this.filteredList.filter(room => {
                        return room.sections.filter(section => section.equipmentDescription.includes(this.equipmentValue)).length > 0
                    })
                }

                this.isNoFilters = false;
            }else{
                this.isNoFilters = true;
            }
        },

        /**
         * resetButtonClicked handles reset of filtering.
         */
        resetButtonClicked(){
            this.searchValue = "";
            this.placeValue = "";
            this.startTimeValue = "";
            this.endTimeValue = "";
            this.dateValue = "";
            this.equipmentValue = "";
            this.isNoFilters = true;
        },

        /**
         * setPlace sets place.
         */
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
    padding-top: 30px;
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
    width: 24%;
}

.place-auto-input{
    margin: 20px 0;
    border: 1px solid #4B505E;
    border-radius: 5px;
    color: white;
    width: 100%;
    padding: 8px 0 8px 12px;
    transition: 0.2s;
}

.place-auto-input:hover {
    transition: 0.2s;
    border-color: white;
}

.results-tag{
    margin: 30px auto 0 auto;
    display: none;
}

.room-list-column {
    width: 67%;
    margin-right: 50px;
}

.number-info-row{
    margin: 0;
}

.number-info-row p{
    font-size: 16px;
    opacity: 70%;
    margin: 0 15px 0 0;
}

.room-container{
    background-color: rgba(0, 0, 0, 0.23);
    width: 100%;
    padding: 20px;
    margin: 20px 0;
    border-radius: 20px;
    box-shadow: 0 2px 4px 0 rgba(0, 0, 0, 0.2), 0 3px 10px 0 rgba(0, 0, 0, 0.19);
    transition: 0.2s;
}

.room-container:hover{
    cursor: pointer;
    background-color: rgba(0, 0, 0, 0.3);
    transition: 0.2s;
}

.room-button{
    width: 100%;
}

.search-button{
    margin: 20px 0;
}


@media (max-width: 1264px) {
    .room-container{
        text-align: center;
        width: 80%;
    }

    .number-info-row {
        display: flex;
        flex-direction: column;
    }

    .room-list-column{
        display: flex;
        flex-direction: column;
        align-items: center;
        width: 100%;
    }
    
    .container{
        flex-direction: column;
    }

    .filter-column{
        width: 80%;
        margin: auto;

    }
    .results-tag{
        display: block;
    }
}

</style>