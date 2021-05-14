import FrontPage from "../routes/FrontPage.vue";
import RoomFeed from "../routes/RoomFeed.vue";
import Room from "../routes/Room.vue"
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