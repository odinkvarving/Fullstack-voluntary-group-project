<template>
    <div class="profile-page">
        <ProfilePageBox class="profile-page-box"/>
    </div>
</template>
<script>
import ProfilePageBox from "../components/ProfilePageComponents/ProfilePageBox.vue";

export default {
    name: "ProfilePage",
    components: {
        ProfilePageBox
    }
}
</script>
<style scoped>
    .profile-page {
        background-color: #192138;
        height: 100%;
        padding: 80px;
    }
    @media (max-width: 1000px) {
        .profile-page {
            padding: 80px 20px;
        }
    }
</style>