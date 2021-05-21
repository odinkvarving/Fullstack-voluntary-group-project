import store from "../store/index"

export const reservationService = {
    getReservations,
    getReservation,
    getSectionsReservations,
    addReservation,
    addRoomReservation,
    deleteReservation,
    updateReservation,
    findReservationsBySectionId,
}

/**
 * getReservations is a function which returns all reservations.
 * 
 * @returns list of reservations.
 */
async function getReservations(){
    let url = "http://localhost:8080/reservations";

    const requestOptions = {
        headers: {
            'Authorization': `Bearer ${store.getters.getJwtToken}`
        }
    }

    return await fetch(url, requestOptions)
        .then(response => response.json())
        .catch(error => console.log(error));
}

/**
 * getReservation is a function which returns a reservation based on given id.
 * 
 * @param {Number} reservationId: id of requested reservation.
 * @returns reservation with given id.
 */
async function getReservation(reservationId){
    let url = `http://localhost:8080/reservations/${reservationId}`;

    const requestOptions = {
        headers: {
            'Authorization': `Bearer ${store.getters.getJwtToken}`
        }
    }

    return await fetch(url, requestOptions)
        .then(response => response.json())
        .catch(error => console.log(error));
}

/**
 * getSectionsReservations is a function which returns all reservations registered with given room id and section id.
 * 
 * @param {Number} roomId: id of room.
 * @param {Number} sectionId: id of section.
 * @returns list of reservation for given section
 */
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

/**
 * addReservation is a function which adds a reservation,
 *  by sending a POST request containing the reservation.
 * 
 * @param {Object} reservation: the reservation which will be added.
 * @returns true if it was added, false if it was not added.
 */
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

/**
 * addRoomReservation is a function which adds a room reservation.
 * This reservation is a reservation for an entire room, and not a section.
 * 
 * @param {list of Object} reservations: list of reservations for the room.
 * @returns true if it was added, false if it was not added.
 */
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

/**
 * deleteReservation is a function which deletes a reservation with given id.
 * 
 * @param {Number} reservationId: id of reservation which will be deleted.
 * @returns true if it was deleted, false if deleting failed.
 */
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

/**
 * updateReservation is a function which updates a reservation with given id.
 * 
 * @param {Number} reservationId: id of reservation which will be updated.
 * @param {Object} reservation: the updated version of reservation which will overwrite the older version.
 * @returns Object reservation which was updated.
 */
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

/**
 * findReservationsBySectionId is a function which returns reservations registered with given section id.
 * 
 * @param {Number} sectionId: id of section.
 * @returns list of reservations for section.
 */
async function findReservationsBySectionId(sectionId) {
    let url = `http://localhost:8080/reservations/sections/${sectionId}`;

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