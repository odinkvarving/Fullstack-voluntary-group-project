import store from "../store/index"

export const reservationService = {
    getReservations
}

function getReservations(){
    let url = "http://localhost:8080/reservations";

    const requestOptions = {
        headers: {
            'Authorization': `Bearer ${store.getters.getJwtToken}`
        }
    }

    return fetch(url, requestOptions)
        .then(response => response.json())
        .catch(error => console.log(error));
}