<template>
    <div class="reset-password">
        <ResetPasswordBox/>
    </div>
</template>
<script>
    import ResetPasswordBox from "../components/ForgotPasswordComponents/ResetPasswordBox.vue";
    
    export default {
        name: "ResetPassword",
        components: {
            ResetPasswordBox
        }
    }
</script>
<style scoped>
    .reset-password {
        position: fixed;
        top: 40%;
        left: 50%;
        transform: translate(-50%, -50%);
    }
</style>