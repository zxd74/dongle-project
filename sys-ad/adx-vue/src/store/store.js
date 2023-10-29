import Vuex from 'vuex'
import Vue from 'vue';

Vue.use(Vuex)

const store = new Vuex.Store({
    state: {
        menus: [],
        map: new Map(),
        currentPath: "",
    },
    mutations: {
        setMenus (state,menus) {
          state.menus = menus;
        },
        setMap (state,map) {
            state.map = map;
        },
        setPath(state,path) {
            state.currentPath = path;
          
        }
        
    }
})

export default store