import store from "../store/index"

export const accountService = {
    getAccounts,
    getAccount
}

/**
 * getAccounts is a function which returns all accounts
 * 
 * @returns all registered accounts
 */
async function getAccounts(){
    let url = "http://localhost:8080/accounts";

    const requestOptions = {
        method: "GET",
        headers: {
            'Authorization': `Bearer ${store.getters.getJwtToken}`
        }
    }

    return await fetch(url, requestOptions)
        .then(response => response.json())
        .catch(error => console.log(error));
}

/**
 * getAccount is a function which returns an account based on given id
 * 
 * @param {Number} accountId: id of requested account
 * @returns account with given id
 */
async function getAccount(accountId){
    let url = `http://localhost:8080/accounts/${accountId}`;

    const requestOptions = {
        headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${store.getters.getJwtToken}`
        }
    }

    return await fetch(url, requestOptions)
        .then(response => response.json())
        .catch(error => console.log(error));
}