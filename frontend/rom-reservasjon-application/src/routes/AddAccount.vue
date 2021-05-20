<template>
    <div class="add-account">
        <AddAccountBox class="add-account-box"/>
    </div>
</template>
<script>
    import AddAccountBox from "../components/AddAccountComponents/AddAccountBox.vue";

    /**
     * AddAccount is a router for adding account functionality for admins.
     * 
     * @author Scott Rydberg Sonen
     */

    export default {
        name: "AddAccount",
        components: {
            AddAccountBox
        },
        mounted(){
            if(!this.$store.getters.isAuthenticated){
                this.$router.push("/");
            }
        }
    }
</script>
<style scoped>
    body {
        background-color: #192138;
    }
    .add-account {
        background-color: #192138;
        position: fixed;
        padding-top: 100px;
        height: 100%;
        width: 100%;
    }
    .add-account-box {
        height: 50%;
        width: 70%;
        margin: auto;
    }
</style>