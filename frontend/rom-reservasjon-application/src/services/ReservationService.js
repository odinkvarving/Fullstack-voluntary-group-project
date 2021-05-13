import store from "../store/index"

export const reservationService = {
    getReservations,
    getSectionsReservations,
    addReservation,
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

async function addReservation(){
    
}
