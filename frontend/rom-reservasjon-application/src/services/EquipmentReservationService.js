import store from "../store/index"

export const equipmentReservationService = {
    addEquipmentReservation
}

async function addEquipmentReservation(equipmentReservation){
    let url = `http://localhost:8080/equipment-reservations`;

    const requestOptions = {
        method: "POST",
        headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${store.getters.getJwtToken}`
        },
        body: JSON.stringify(equipmentReservation)
    }

    return fetch(url, requestOptions)
        .then(response => response.json())
        .catch(error => console.log(error));
}