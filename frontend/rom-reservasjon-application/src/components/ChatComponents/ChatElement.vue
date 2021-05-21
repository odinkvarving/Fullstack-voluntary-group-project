<template>
  <v-container pb-6>
    <v-row>
      <v-card width="1600" color="#222b45" :loading="loading">
        <template slot="progress">
          <v-progress-linear
            color="red lighten-1"
            height="10"
            indeterminate
          ></v-progress-linear>
        </template>
        <v-card-title class="headline">
          {{ name }}
          <v-icon
            v-if="chatMessage.accountId === loggedInAccountId"
            @click="deleteMessage()"
            style="margin-left: auto"
            >mdi-delete</v-icon
          >
        </v-card-title>
        <v-card-text class="white--text">
          {{ message }}
        </v-card-text>
        <v-card-subtitle align="left">
          <span>{{ time }}</span>
        </v-card-subtitle>
      </v-card>
    </v-row>
  </v-container>
</template>

<script>
import { accountService } from "../../services/AccountService.js";
import { roomService } from "../../services/RoomService.js";

export default {
  name: "ChatElement",

  props: {
    chatMessage: {
      type: Object,
      required: true,
    },

    loggedInAccountId: {
      type: Number,
      required: true,
    },
  },

  data() {
    return {
      name: "",
      time: null,
      message: "",
      loading: false,
    };
  },

  async mounted() {
    let account = await accountService.getAccount(this.chatMessage.accountId);
    this.time = this.chatMessage.timeStamp;
    this.message = this.chatMessage.message;
    this.name = account.name;
  },

  methods: {
    async deleteMessage() {
      if (confirm("Er du sikker på at du vil slette denne kommentaren?")) {
        this.loading = true;
        await roomService.deleteMessage(this.chatMessage.id);

      }
    },
  },
};
</script>
<style></style>
