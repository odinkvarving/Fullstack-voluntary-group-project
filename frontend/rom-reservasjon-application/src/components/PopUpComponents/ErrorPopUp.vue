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
    /**
     * ErrorPopUp is a component which represents a custom made error pop up.
     * The pop up receives a message from a parent component,
     *  and displays it in the pop up.
     * 
     * @author Scott Rydberg Sonen
     */

    export default {
        name: "ErrorPopUp",

        props: {
            message: {
                Object: String
            }
        },

        methods: {
            /**
             * closePopUp is a function which emits close-popup.
             * This is to tell the parent component when the
             *  pop up is closed by the user.
             */
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