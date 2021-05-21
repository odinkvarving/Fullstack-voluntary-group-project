<template>
  <div class="equipment-feed">
      <v-container>
      <v-row align="center" justify="center">
          <v-autocomplete
            v-model="equipmentValue"
            :items="equipmentList"
            item-text="name"
            outlined
            placeholder="Søk etter utstyr..."
          >
          </v-autocomplete>
      </v-row>
      <v-row v-for="equipment in filteredEquipmentList" :key="equipment.id" align="center" justify="center">
          <div @click="equipmentClicked(equipment.id)">
            {{equipment.name}}
          </div>
      </v-row>
    </v-container>
  </div>
</template>

<script>
export default {
    name: "EquipmentFeed",
    data(){
        return {
            equipmentValue: ""
        }
    },
    computed: {
        equipmentList(){
            return this.$store.getters.getEquipment;
        },
        filteredEquipmentList(){
            if(this.equipmentValue === "" || this.equipmentValue === null){
                return this.$store.getters.getEquipment;
            }else{
                return this.equipmentList.filter(e => e.name.includes(this.equipmentValue));
            }
        }
    },
    methods: {
        equipmentClicked(equipmentId){
            this.$router.push(`/equipmentfeed/${equipmentId}`);
        }
    }
}
</script>

<style scoped>

.equipment-feed{
    min-height: 100vh;
    padding-top: 100px;
    background-color: #192138;
}

</style>