import store from "../store/index"

export const adminService = {
    isAdmin,
    isLoggedIn
}

/**
 * isLoggedIn is a function which checks whether the user is logged in or not,
 *  by calling isAuthenticated from store.
 * It redirects user to login page if not.
 * 
 * @returns no value, just for efficiency.
 */
function isLoggedIn() {
  if (!store.getters.isAuthenticated) {
    this.$router.push("/");
    return;
  }
}

/**
 * isAdmin is a function which checks whether the user is a registered admin or not.
 * 
 * @returns no value, just for efficiency.
 */
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