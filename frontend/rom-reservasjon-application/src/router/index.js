import FrontPage from "../routes/FrontPage.vue";
import RoomFeed from "../routes/RoomFeed.vue";
import Room from "../routes/Room.vue"
import Section from "../routes/Section.vue"
import Login from "../routes/Login.vue";
import ForgotPassword from "../routes/ForgotPassword.vue";
import ResetPassword from "../routes/ResetPassword.vue";

const routes = [
    {
        path: "/",
        name: "FrontPage",
        component: FrontPage
    },
    {
        path: "/roomfeed",
        name: "Room feed",
        component: RoomFeed
    },
    {
        path: "/rooms/:id",
        name: "Room",
        component: Room
    },
    {
        path: "/rooms/:roomId/sections/:sectionId",
        name: "Section",
        component: Section
    },
    {
        path: "/login",
        name: "Login",
        component: Login
    },
    {
        path: "/forgotpassword",
        name: "ForgotPassword",
        component: ForgotPassword
    },
    {
        path: "/resetpassword/:suffix",
        name: "ResetPassword",
        component: ResetPassword
    }
];
export default routes;