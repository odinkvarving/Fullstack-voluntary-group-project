import store from "../store/index"

export const accountService = {
    getAccount
}


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