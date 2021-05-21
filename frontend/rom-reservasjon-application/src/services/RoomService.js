import store from "../store/index"

export const roomService = {
    getRooms,
    getSectionMessages,
    sendMessage,
    deleteMessage,
    updateMessage,
    getMessageById,
}

/**
 * getRooms is a function which returns all rooms.
 * 
 * @returns list of all rooms.
 */
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

/**
 * getSectionMessages is a function which returns messages of a section.
 * 
 * @param {Number} sectionId: id of section.
 * @returns list of all messages for given section.
 */

async function getSectionMessages(sectionId) {
    let url = `http://localhost:8080/messages/sections/${sectionId}`;

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
 * getMessageById is a function which returns message with given message id.
 * 
 * @param {Number} messageId: id of requested message.
 * @returns message object with given id.
 */
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

/**
 * sendMessage is a function which saves a message for a specific section.
 * 
 * @param {Number} sectionId: id of section.
 * @param {Object} message: message object which will be saved and displayed.
 * @returns the message object.
 */

async function sendMessage(sectionId, message) {
    let url = `http://localhost:8080/messages/sections/${sectionId}`;

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

/**
 * deleteMessage is a function which deletes a specific message with given id.
 * 
 * @param {Number} messageId: id of message.
 * @returns void.
 */
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

/**
 * updateMessage is a function which updates specific message with given id.
 * 
 * @param {Number} messageId: id of message.
 * @param {Object} message: message object.
 * @returns message object.
 */
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
