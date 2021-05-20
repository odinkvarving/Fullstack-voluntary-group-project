import store from "../store/index"

export const roomService = {
    getRooms,
    getSectionMessages,
    sendMessage,
    deleteMessage,
    updateMessage,
    getMessageById,
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

async function getSectionMessages(roomId, sectionId) {
    let url = `http://localhost:8080/rooms/${roomId}/sections/${sectionId}/messages`;

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

async function getMessageById(messageId) {
    let url = `http://localhost:8080/messages/${messageId}`;

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

async function sendMessage(roomId, sectionId, message) {
    let url = `http://localhost:8080/rooms/${roomId}/sections/${sectionId}/messages`;

    const requestOptions = {
        method: "POST",
        headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${store.getters.getJwtToken}`
        },
        body: JSON.stringify(message)
    }

    return fetch(url, requestOptions)
        .then(response => response.json())
        .catch(error => console.log(error));
}

async function deleteMessage(messageId) {
    let url = `http://localhost:8080/messages/${messageId}`;

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

async function updateMessage(messageId, message) {
    let url = `http://localhost:8080/messages/${messageId}`;

    const requestOptions = {
        method: "PUT",
        headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${store.getters.getJwtToken}`
        },
        body: JSON.stringify(message)
    }

    return fetch(url, requestOptions)
        .then(response => response.json())
        .catch(error => console.log(error));
}
