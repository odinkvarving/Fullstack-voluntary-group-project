<template>
    <div class="login">
        <LoginBox/>
    </div>
</template>
<script>
    import LoginBox from "../components/LoginComponents/LoginBox.vue";

    export default {
        name: "Login",
        components: {
            LoginBox,
        }
    }
</script>
<style>
    .login {
        position: fixed;
        top: 40%;
        left: 50%;
        transform: translate(-50%, -50%);
    }
</style>