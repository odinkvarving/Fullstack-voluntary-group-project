<template>
    <div class="chat">
        <v-container>
            <v-row no-gutters>
                <v-col sm="2" class="scrollable">
                    <div v-for="message in messages" :key="message.id">
                        <ChatElement :chatMessage="message" :loggedInAccountId="accountId"/>
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
import { accountService } from "../services/AccountService.js";
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
  },

  data() {
    return {
      messages: {},
      message: "",
      timerId: null,
      pageRoute: "",
      accountId: null,
    };
  },

  created() {
    this.pageRoute = this.$route.name;
    this.loadAccountId();
    this.startTimer();
  },

  methods: {
    startTimer() {
      this.timerId = setInterval(() => this.loadMessages(), 10000);
    },

    async loadAccountId() {
      await accountService.getAccount(this.$store.getters.getLoggedInAccount.id).then((data) => {
        this.accountId = data.id;
      });
    },

    stillOnPage() {
      return this.pageRoute === this.$route.name;
    },

    async loadMessages() {
      if (this.stillOnPage()) {
        this.messages = await roomService.getSectionMessages(this.$route.params.roomId, this.$route.params.sectionId); //await necessary?
      } else {
        clearInterval(this.timerId);
      }
    },

    async sendMessage() {
      if (this.message.length > 0) {
        if (this.$store.getters.isAuthenticated) {
          const data = {
            accountId: this.accountId,
            message: this.message,
          };
          this.message = "";
          await roomService.sendMessage(this.$route.params.roomId, this.$route.params.sectionId, data);
        } else {
          console.error("User must be logged in to post message");
        }
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