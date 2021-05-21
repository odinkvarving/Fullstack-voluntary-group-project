import store from "../store/index"

export const equipmentReservationService = {
    addEquipmentReservation
}

/**
 * addEquipmentReservation is a function which adds equipment reservation,
 *  by sending a POST request containing the equipment reservation.
 * 
 * @param {Object} equipmentReservation: the equipment reservation.
 * @returns true if equipment reservation was added, or false if it was not added.
 */
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