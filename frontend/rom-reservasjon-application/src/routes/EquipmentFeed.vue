<template>
  <div class="equipment-feed">
    <v-container>
      <v-row align="center" justify="center">
        <v-col>
          <v-autocomplete
            v-model="equipmentValue"
            :items="equipmentList"
            item-text="name"
            outlined
            placeholder="Søk etter utstyr..."
          >
          </v-autocomplete>
        </v-col>
      </v-row>
      <v-row align="center" justify="center">
        <v-col
          cols="6"
          sm="4"
          v-for="equipment in filteredEquipmentList"
          :key="equipment.id"
        >
          <v-card @click="equipmentClicked(equipment.id)" color="#222b45">
            <v-card-title class="justify-center">
              {{ equipment.name }}
            </v-card-title>
          </v-card>
        </v-col>
      </v-row>
    </v-container>
  </div>
</template>

<script>
export default {
  name: "EquipmentFeed",
  data() {
    return {
      equipmentValue: "",
    };
  },
  computed: {
    equipmentList() {
      return this.$store.getters.getEquipment;
    },
    filteredEquipmentList() {
      if (this.equipmentValue === "" || this.equipmentValue === null) {
        return this.$store.getters.getEquipment;
      } else {
        return this.equipmentList.filter((e) =>
          e.name.includes(this.equipmentValue)
        );
      }
    },
  },
  methods: {
    equipmentClicked(equipmentId) {
      this.$router.push(`/equipmentfeed/${equipmentId}`);
    },
  },
};
</script>

<style scoped>
.equipment-feed {
  min-height: 100vh;
  padding-top: 50px;
  background-color: #192138;
}
</style>
