<template>
    <div class="add-account">
        <AddAccountBox/>
    </div>
</template>
<script>
    import AddAccountBox from "../components/AddAccountComponents/AddAccountBox.vue";

    export default {
        name: "AddAccount",
        components: {
            AddAccountBox
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
        top: 50%;
        left: 50%;
        transform: translate(-50%, -50%);
    }
</style>