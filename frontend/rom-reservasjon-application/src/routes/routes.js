import Login from "./Login.vue";
import FrontPage from "./FrontPage.vue";

const routes = [
    {
        path: "/",
        name: "Login",
        component: Login,
    },
    {
        path: "/frontpage",
        name: "FrontPage",
        component: FrontPage,
    }
];
export default routes;