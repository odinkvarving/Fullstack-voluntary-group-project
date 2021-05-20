<template>
  <div class="chat">
    <v-container>
      <v-row align="center">
        <v-col cols="10">
          <v-text-field
            v-model="message"
            placeholder="Skriv en kommentar"
            outlined
            hide-details
            color="green"
            v-on:keyup.enter="sendMessage"
          >
          </v-text-field>
        </v-col>

        <v-col cols="2">
          <v-btn rounded color="green" @click="sendMessage">
            <span>Send</span>
          </v-btn>
        </v-col>
        <v-col>
          <div v-for="message in messages" :key="message.id">
            <ChatElement
              :chatMessage="message"
              :loggedInAccountId="accountId"
            />
          </div>
        </v-col>
      </v-row>
    </v-container>
  </div>
</template>

<script>
import { roomService } from "../../services/RoomService.js";
import ChatElement from "./ChatElement.vue";

export default {
  name: "Chat",

  components: {
    ChatElement,
  },

  props: {
    section: {
      type: Object,
      required: true,
    },

    accountId: {
      type: Number,
      required: true,
    },
  },

  data() {
    return {
      messages: {},
      message: "",
      timerId: null,
      pageRoute: "",
      roomId: null,
      sectionId: null,
    };
  },

  computed: {},

  async mounted() {
    await this.getInfo();
  },

  methods: {
    async getInfo() {
      this.pageRoute = this.$route.name;
      this.startTimer();
      this.roomId = this.$route.params.roomId;
      this.sectionId = this.$route.params.sectionId;
    },

    startTimer() {
      this.timerId = setInterval(() => this.loadMessages(), 2000);
    },

    stillOnPage() {
      return this.pageRoute === this.$route.name;
    },

    async loadMessages() {
      if (this.stillOnPage()) {
        this.messages = await roomService.getSectionMessages(
          this.roomId,
          this.sectionId
        ); //await necessary?
      } else {
        clearInterval(this.timerId);
      }
    },

    async sendMessage() {
      if (this.message.length > 0) {
        const data = {
          accountId: this.accountId,
          message: this.message,
          sectionId: this.sectionId,
        };
        this.message = "";
        console.log("ROOM ID: " + this.$route.params.roomId);
        console.log("SECTION ID" + this.$route.params.sectionId);
        console.log("ACCOUNT ID" + this.accountId);
        await roomService.sendMessage(
          this.$route.params.roomId,
          this.$route.params.sectionId,
          data
        );
      }
    },
  },
};
</script>
<style></style>
