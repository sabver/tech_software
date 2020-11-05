import Vue from 'vue'
import Vuex from 'vuex'
import {getToken,addToken,addUser,getUser} from '../service.js'
Vue.use(Vuex)

const store = new Vuex.Store({
    state: {
		hasLogin:false,
		user:getUser(),
    },
    mutations: {
        login(state, data) {
			addToken(data.token)
			addUser(data.user)
			state.user = data.user            
            state.hasLogin = true;
        },
        logout(state) {
			delToken('')
            state.user = {}
            state.hasLogin = false;
        }
    }
})

export default store
