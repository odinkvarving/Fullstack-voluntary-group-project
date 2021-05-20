<template>
  <div class="chat">
    <v-container>
      <v-row no-gutters>
        <v-col sm="2" class="scrollable">
          <div v-for="message in messages" :key="message.id">
            <ChatElement
              :chatMessage="message"
              :loggedInAccountId="accountId"
            />
          </div>
          <div class="chat-input">
            <textarea
              type="text"
              class="input"
              id="message-field"
              placeholder="Legg til en kommentar"
              rows="2"
              v-model="message"
            />
            <button @click="sendMessage">✔</button>
          </div>
        </v-col>
      </v-row>
    </v-container>
  </div>
</template>

<script>
import { roomService } from "../services/RoomService.js";
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
      this.timerId = setInterval(() => this.loadMessages(), 10000);
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
<style>
.input {
  padding: 0 10px;
  border: solid 2px #eca82b;
  outline: none;
}
#message-field {
  resize: none;
}
.chat-input {
  display: grid;
  grid-template-columns: 75% auto;
  margin-right: 8%;
  display: fixed;
}

#chat {
  width: 80vw;
  margin-bottom: 100px;
}
</style>
