import store from "../store/index"

export const roomService = {
    getRooms,
    getRoomsSections
}

async function getRooms(){
    let url = "http://localhost:8080/rooms";

    const requestOptions = {
        headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${store.getters.getToken}`
        }
    }

    return fetch(url, requestOptions)
        .then(response => response.json())
        .catch(error => console.log(error));
}

async function getRoomsSections(roomId){
    let url = `http://localhost:8080/rooms/${roomId}/sections`;

    const requestOptions = {
        headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${store.getters.getToken}`
        }
    }

    return fetch(url, requestOptions)
        .then(response => response.json())
        .catch(error => console.log(error));
}