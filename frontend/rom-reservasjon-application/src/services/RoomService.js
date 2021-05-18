import store from "../store/index"

export const roomService = {
    getRooms,
}

async function getRooms(){
    let url = "http://localhost:8080/rooms";

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
