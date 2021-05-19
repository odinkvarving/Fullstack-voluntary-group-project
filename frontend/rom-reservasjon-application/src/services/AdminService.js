import store from "../store/index"

export const adminService = {
    isAdmin,
    isLoggedIn
}

function isLoggedIn() {
    if (!store.getters.isAuthenticated) {
      this.$router.push("/");
      return;
    }
  }

  async function isAdmin() {
    let isAdmin = store.getters.getLoggedInAccount.is_admin;

    if (isAdmin === undefined) {
      await store.dispatch("getAccountInfo");
      isAdmin = store.getters.getLoggedInAccount.is_admin;
    }

    if (!isAdmin) {
      this.$router.push("/frontpage");
      return;
    }
  }