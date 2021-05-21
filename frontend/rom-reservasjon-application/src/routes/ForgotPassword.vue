<template>
    <div class="forgot-password">
        <ForgotPasswordBox/>
    </div>
</template>
<script>
    import ForgotPasswordBox from "../components/ForgotPasswordComponents/ForgotPasswordBox.vue";
    
    /**
     * ForgotPassword is a router for a page the user will be sent to
     *  if the user has forgotten their password.
     * 
     * @author Scott Rydberg Sonen
     */

    export default {
        name: "ForgotPassword",
        components: {
            ForgotPasswordBox
        }
    }
</script>
<style scoped>
    .forgot-password {
        background-color: #192138;
        position: fixed;
        height: 100%;
        width: 100%;
        padding-left: 30vw;
        padding-top: 250px;
    }
    
</style>