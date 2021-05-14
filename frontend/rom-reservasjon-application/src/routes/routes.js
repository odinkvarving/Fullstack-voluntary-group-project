import Login from "./Login.vue";
import FrontPage from "./FrontPage.vue";
import ForgotPassword from "./ForgotPassword.vue";
import ResetPassword from "./ResetPassword.vue";

const routes = [
    {
        path: "/",
        name: "Login",
        component: Login
    },
    {
        path: "/frontpage",
        name: "FrontPage",
        component: FrontPage
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