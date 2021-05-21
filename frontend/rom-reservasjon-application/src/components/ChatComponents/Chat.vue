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
            <span v-if="!sendButtonLoading">Send</span>
            <v-progress-circular
              v-else
              indeterminate
              color="white"
            ></v-progress-circular>
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

/**
 * @author Odin Kvarving
 * @author Mattias Agentoft Eggen
 */

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
      sendButtonLoading: false,
    };
  },

  computed: {},

  async mounted() {
    await this.getInfo();
  },

  methods: {
    /**
     * getInfo is a function which returns relevant info.
     */
    async getInfo() {
      this.pageRoute = this.$route.name;
      this.startTimer();
      this.roomId = this.$route.params.roomId;
      this.sectionId = this.$route.params.sectionId;
    },

    /**
     * startTimer is a function which starts a time interval.
     */
    startTimer() {
      this.timerId = setInterval(() => this.loadMessages(), 2000);
    },

    /**
     * stillOnPage is a function which checks if user is still on chat page.
     */
    stillOnPage() {
      return this.pageRoute === this.$route.name;
    },

    /**
     * loadMessages is a function which loads all messages, by calling getSectionMessages from roomService.
     */
    async loadMessages() {
      if (this.stillOnPage()) {
        this.messages = await roomService.getSectionMessages(
          this.roomId,
          this.sectionId
        );
      } else {
        clearInterval(this.timerId);
      }
    },

    /**
     * sendMessage is a function which saves a message, by calling sendMessage function from roomService.
     */
    async sendMessage() {
      if (this.message.length > 0) {
        this.sendButtonLoading = true;
        setTimeout(() => (this.sendButtonLoading = false), 2000);
        const data = {
          accountId: this.accountId,
          message: this.message,
          sectionId: this.sectionId,
        };
        this.message = "";
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
