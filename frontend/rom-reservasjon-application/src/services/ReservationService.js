import store from "../store/index"

export const reservationService = {
    getReservations,
    getSectionsReservations,
    addReservation,
    addRoomReservation,
    deleteReservation,
    updateReservation
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

async function getSectionsReservations(roomId, sectionId){
    let url = `http://localhost:8080/rooms/${roomId}/sections/${sectionId}`;

    const requestOptions = {
        headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${store.getters.getJwtToken}`
        }
    }

    return fetch(url, requestOptions)
        .then(response => response.json())
        .catch(error => console.log(error));
}

async function addReservation(reservation){
    let url = `http://localhost:8080/reservations`;

    const requestOptions = {
        method: "POST",
        headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${store.getters.getJwtToken}`
        },
        body: JSON.stringify(reservation)
    }

    return fetch(url, requestOptions)
        .then(response => response.json())
        .catch(error => console.log(error));
}

async function addRoomReservation(reservations){
    let url = `http://localhost:8080/reservations/room`;

    const requestOptions = {
        method: "POST",
        headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${store.getters.getJwtToken}`
        },
        body: JSON.stringify(reservations)
    }

    return fetch(url, requestOptions)
        .then(response => response.json())
        .catch(error => console.log(error));
}

async function deleteReservation(reservationId){
    let url = `http://localhost:8080/reservations/${reservationId}`;

    const requestOptions = {
        method: "DELETE",
        headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${store.getters.getJwtToken}`
        },
    }

    return fetch(url, requestOptions)
        .then(response => response.json())
        .catch(error => console.log(error));
}

async function updateReservation(reservationId, reservation){
    let url = `http://localhost:8080/reservations/${reservationId}`;

    const requestOptions = {
        method: "PUT",
        headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${store.getters.getJwtToken}`
        },
        body: JSON.stringify(reservation)
    }

    return fetch(url, requestOptions)
        .then(response => response.json())
        .catch(error => console.log(error));
}
