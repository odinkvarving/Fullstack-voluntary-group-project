import Login from "./Login.vue";
import FrontPage from "./FrontPage.vue";
import ForgotPassword from "./ForgotPassword.vue";

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
    }
];
export default routes;