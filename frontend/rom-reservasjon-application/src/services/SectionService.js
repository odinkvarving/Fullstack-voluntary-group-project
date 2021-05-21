import store from "../store/index"

export const sectionService = {
    getSections,
    getSection
}

/**
 * getSections is a function which returns all sections.
 * 
 * @returns list of sections.
 */
async function getSections(){
    let url = 'http://localhost:8080/sections/';

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
 * getSection is a function which returns specific section with given id.
 * 
 * @param {Number} sectionId: id of requested section.
 * @returns section object.
 */
async function getSection(sectionId){
    let url = `http://localhost:8080/sections/${sectionId}`;

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