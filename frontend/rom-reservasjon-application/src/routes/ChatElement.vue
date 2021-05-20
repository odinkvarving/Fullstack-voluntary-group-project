<template>
  <div class="chatbox">
    <div v-if="chatMessage.accountId === loggedInAccountId">
      <button @click="deleteMessage()" class="delete">×</button>
    </div>
    <p class="name">{{ name }}</p>
    <p class="message">{{ message }}</p>
    <p class="time">{{ time }}</p>
  </div>
</template>

<script>
import { accountService } from "../services/AccountService.js";
import { roomService } from "../services/RoomService.js";

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
    };
  },

  created() {
    this.getAccountInfo();
    this.time = this.chatMessage.timeStamp;
    this.message = this.chatMessage.message;
  },

  methods: {
    async getAccountInfo() {
      let account = await accountService.getAccount(this.chatMessage.accountId);
      this.name = account.name;
    },

    async deleteMessage() {
      if (confirm("Vil du virkelig slette denne kommentaren?")) {
        await roomService.deleteMessage(this.chatMessage.id);
      }
    },
  },
};
</script>
<style>
.box {
  margin-bottom: 10px;
  background-color: white;
  padding-left: 15px;
  width: 92%;
  border: solid lightgray 2px;
}
.time {
  opacity: 0.5;
  padding-left: 15px;
  font-size: 12px;
}
.message {
  border-bottom: lightgray 2px solid;
  border-top: 2px lightgray solid;
  padding-bottom: 15px;
  padding-left: 15px;
  margin-right: 15px;
  padding-top: 10px;
  white-space: pre-line;
  line-height: 1;
}

.delete {
  float: right;
  background-color: white;
  color: lightgray;
  border-radius: 12px;
  padding-top: 5px;
  border: none;
  padding-right: 15px;
  font-weight: bold;
  font-size: 25px;
}

.name {
  color: black;
}

.delete:hover {
  color: red;
}
</style>
