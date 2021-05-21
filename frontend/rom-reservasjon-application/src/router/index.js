import FrontPage from "../routes/FrontPage.vue";
import RoomFeed from "../routes/RoomFeed.vue";
import Room from "../routes/Room.vue";
import Section from "../routes/Section.vue";
import EquipmentFeed from "../routes/EquipmentFeed.vue";
import EquipmentReservation from "../routes/EquipmentReservation.vue";
import Login from "../routes/Login.vue";
import ForgotPassword from "../routes/ForgotPassword.vue";
import ResetPassword from "../routes/ResetPassword.vue";
import AddAccount from "../routes/AddAccount.vue";
import Reservations from "../routes/Reservations.vue";
import Reservation from "../routes/Reservation.vue";
import ProfilePage from "../routes/ProfilePage.vue";

const routes = [
  {
    path: "/frontpage",
    name: "FrontPage",
    component: FrontPage,
  },
  {
    path: "/roomfeed",
    name: "Room feed",
    component: RoomFeed,
  },
  {
    path: "/rooms/:id",
    name: "Room",
    component: Room,
  },
  {
    path: "/rooms/:roomId/sections/:sectionId",
    name: "Section",
    component: Section,
  },
  {
    path: "/equipmentfeed",
    name: "Equipment feed",
    component: EquipmentFeed
  },
  {
    path: "/equipmentfeed/:id",
    name: "Equipment feed",
    component: EquipmentReservation
  },
  {
    path: "/",
    name: "Login",
    component: Login,
  },
  {
    path: "/forgotpassword",
    name: "ForgotPassword",
    component: ForgotPassword,
  },
  {
    path: "/resetpassword/:suffix",
    name: "ResetPassword",
    component: ResetPassword,
  },
  {
    path: "/addaccount",
    name: "AddAccount",
    component: AddAccount,
  },
  {
    path: "/reservations",
    name: "Reservations",
    component: Reservations,
  },
  {
    path: "/reservations/:reservationId",
    name: "Reservation",
    component: Reservation,
  },
  {
    path: "/profilepage/:id",
    name: "ProfilePage",
    component: ProfilePage,
  }
];
export default routes;
