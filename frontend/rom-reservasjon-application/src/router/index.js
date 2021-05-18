import FrontPage from "../routes/FrontPage.vue";
import RoomFeed from "../routes/RoomFeed.vue";
import Room from "../routes/Room.vue";
import Login from "../routes/Login.vue";
import ForgotPassword from "../routes/ForgotPassword.vue";
import ResetPassword from "../routes/ResetPassword.vue";
import AddAccount from "../routes/AddAccount.vue";

const routes = [
    {
        path: "/frontpage",
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
        path: "/",
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
    },
    {
        path: "/addaccount",
        name: "AddAccount",
        component: AddAccount
    }
];
export default routes;