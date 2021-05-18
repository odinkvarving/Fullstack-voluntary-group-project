<template>
    <div class="success-popup">
        <div class="success-popup-content">
            <h3>Operasjonen var vellykket!</h3>
            <p>{{ message }}</p>
        </div>
    </div>
</template>
<script>
    /**
     * SuccessPopUp is a component which represents a custom made success pop up.
     * The pop up receives a message from parent component,
     *  which is displayed in the pop up.
     * 
     * @author Scott Rydberg Sonen
     */

    export default {
        props: {
            message: {
                Object: String
            }
        }
    }
</script>
<style scoped>
    .success-popup {
        height: 150px;
        width: 250px;
        background-color: #222B45;
        border: 1px solid #01AB55;
        border-radius: 4px;
        padding: 20px;
    }
    h3 {
        margin: 0;
    }
    p {
        margin: 0;
    }
</style>