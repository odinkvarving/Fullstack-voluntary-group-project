<template>
    <div class="forgot-password">
        <ForgotPasswordBox/>
    </div>
</template>
<script>
    import ForgotPasswordBox from "../components/ForgotPasswordComponents/ForgotPasswordBox.vue";
    
    export default {
        name: "ForgotPassword",
        components: {
            ForgotPasswordBox
        }
    }
</script>
<style scoped>
    .forgot-password {
        position: fixed;
        top: 40%;
        left: 50%;
        transform: translate(-50%, -50%);
    }
</style>