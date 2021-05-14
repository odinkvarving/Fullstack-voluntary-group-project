<template>
    <div class="error-popup">
        <div class="error-popup-content">
            <p id="close-btn" @click="closePopUp">X</p>
            <h3>Et problem oppsto!</h3>
            <p>{{ message }}</p>
        </div>
    </div>
</template>
<script>
    export default {
        name: "ErrorPopUp",

        props: {
            message: {
                Object: String
            }
        },

        methods: {
            closePopUp() {
                this.$emit("close-popup");
            }
        }
    }
</script>
<style scoped>
    .error-popup {
        height: 150px;
        width: 250px;
        background-color: #222B45;
        border: 1px solid #01AB55;
        border-radius: 4px;
        padding: 20px;
    }
    #close-btn {
        float: right;
        cursor: pointer;
    }
    #close-btn:hover {
        color: red;
    }
    h3 {
        margin: 0;
    }
    p {
        margin: 0;
    }
</style>