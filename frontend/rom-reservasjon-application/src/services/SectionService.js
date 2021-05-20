import store from "../store/index"

export const sectionService = {
    getSection,
}

function getSection(sectionId){
    let url = `http://localhost:8080/sections/${sectionId}`;

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