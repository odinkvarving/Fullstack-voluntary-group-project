<template>
    <div class="reset-password">
        <ResetPasswordBox/>
    </div>
</template>
<script>
    import ResetPasswordBox from "../components/ForgotPasswordComponents/ResetPasswordBox.vue";
    
    /**
     * ResetPassword is a router for password resetting.
     * To access this router, the user needs to acquire a unique URL,
     *  created by a 75 character long string.
     * The URL is sent to the account email.
     * 
     * @author Scott Rydberg Sonen
     */

    export default {
        name: "ResetPassword",
        components: {
            ResetPasswordBox
        }
    }
</script>
<style scoped>
    .reset-password {
        background-color: #192138;
        position: fixed;
        height: 100%;
        width: 100%;
        padding-left: 30vw;
        padding-top: 250px;
    }
</style>