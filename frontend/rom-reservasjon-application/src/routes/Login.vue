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

<style scoped>
.login {
    background-color: #192138;
    height: 100vh;
    display: flex;
    align-items: center;
    justify-content: center;
}
</style>