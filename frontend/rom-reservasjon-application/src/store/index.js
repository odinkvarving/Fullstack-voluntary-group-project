import Vue from 'vue'
import Vuex from 'vuex'
import VueJwtDecode from 'vue-jwt-decode'

Vue.use(Vuex);

const store = new Vuex.Store({
    state:{
        jwtToken: localStorage.getItem("jwt") || "",
        loggedInAccount: {},
        rooms: [],
        isBusy: false,
        error: "",
    },
    mutations:{
        setJwtToken: (state, jwtToken) => {
            state.jwtToken = jwtToken;
        },
        clearJwtToken: (state) => {
            state.jwtToken = "";
            localStorage.removeItem("jwt");
        },
        setLoggedInAccount: (state, account) => {
            state.loggedInAccount = account;
        },
        clearLoggedInAccount: (state) => {
            state.loggedInAccount = {}
        },
        setRooms: (state, rooms) => {
            state.rooms = rooms;
        },
        clearRooms: (state) => {
            state.rooms = [];
        },
        setBusy: (state) => state.isBusy = true,
        clearBusy: (state) => state.isBusy = false,
        setError: (state, error) => state.error = error,
        clearError: (state) => state.error = "",
    },
    getters:{
        isAuthenticated: (state) => state.jwtToken.length > 0,
        getJwtToken: (state) => state.jwtToken,
        getLoggedInAccount: (state) => state.loggedInAccount,
        getRooms: (state) => state.rooms
    },
    actions:{
        login: async({ commit, state }, authRequest) => {
            try{
                commit("setBusy");
                commit("clearError");
                
                const url = `http://localhost:8080/authenticate`;
                
                const requestOptions = {
                    method: 'POST',
                    headers: { 'Content-Type': 'application/json' },
                    body: JSON.stringify(authRequest)
                }

                await fetch(url, requestOptions)
                    .then(response => response.json())
                    .then(data => {
                        console.log(data.jwt);
                        commit("setJwtToken", data.jwt);
                        localStorage.setItem("jwt", data.jwt);
                    })
                    .catch(error => {
                        console.log("Error when logging in: ");
                        console.log(error);
                    })
                
                let account = await getAccount(state.jwtToken);
                
                commit("setLoggedInAccount", account);

                return true;
                
            }catch(error){
                console.log("CATCHED ERROR");
                console.log(error);
                commit("setError", "Failed to login, error occured");

                return false;
            }finally{
                commit("clearBusy");
            }
        },
        logout: ({ commit }) => {
            commit("clearJwtToken");
            commit("clearLoggedInAccount");
        },
        loadRooms: async({ commit }) => {
            try {
                commit("setBusy");
                commit("clearError");

                const url = "http://localhost:8080/rooms";

                await fetch(url)
                    .then(response => response.json())
                    .then(data => {
                        console.log("Loading in rooms...");
                        console.log(data);
                        commit("setRooms", data);
                    })
                    .catch(error => {
                        console.log("Error when loading rooms:");
                        console.log(error);
                    });
            }catch(error){
                console.log("CATCHED ERROR");
                console.log(error);
            }finally{
                commit("clearBusy");
                console.log("Rooms loaded in sucessfully!");
            }
        }
    }
})


function getAccount(jwtToken){
    
    const accountEmail = VueJwtDecode.decode(jwtToken).sub;
    console.log(accountEmail);

    let url = `http://localhost:8080/accounts/email=${accountEmail}`;

    const requestOptions = {
        methods: 'GET',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${jwtToken}`
        }
    }

    return fetch(url, requestOptions)
        .then(response => response.json())
        .catch(error => {
            console.log("Error getting account info");
            console.log(error);
        })
    
}

export default store;