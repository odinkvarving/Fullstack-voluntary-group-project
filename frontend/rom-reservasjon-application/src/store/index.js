import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex);

const store = new Vuex.Store({
    state:{
        jwtToken: "",
        isBusy: false,
        error: "",
        apiURL: "http://localhost:8080/"
    },
    mutations:{
        setJwtToken: (state, jwtToken) => {
            state.jwtToken = jwtToken;
        },
        clearJwtToken: (state) => {
            state.jwtToken = "";
        },
        setBusy: (state) => state.isBusy = true,
        clearBusy: (state) => state.isBusy = false,
        setError: (state, error) => state.error = error,
        clearError: (state) => state.error = "",
    },
    getters:{
        isAuthenticated: (state) => state.jwtToken.length > 0,
        getToken: (state) => state.token
    },
    actions:{
        login: async({ commit }, authRequest) => {
            try{
                commit("setBusy");
                commit("clearError");
                
                const url = "http://localhost:8080/authenticate";
                
                const requestOptions = {
                    method: 'POST',
                    headers: { 'Content-Type': 'application/json' },
                    body: JSON.stringify(authRequest)
                }

                await fetch(url, requestOptions)
                    .then(response => response.json())
                    .then(data => {
                        commit("setJwtToken", data);
                    })
                    .catch(error => {
                        console.log("Error when logging in: ");
                        console.log(error);
                    })
            }catch(error){
                console.log("CATCHED ERROR");
                console.log(error);
                commit("setError", "Failed to login, error occured");
            }finally{
                commit("clearBusy");
            }
        }
    }
})

export default store;