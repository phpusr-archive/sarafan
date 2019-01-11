import Vue from 'vue'
import Vuex from 'vuex'
import messagesApi from 'api/messages'

Vue.use(Vuex);

export default new Vuex.Store({
    state: {
        messages: frontendData.messages,
        profile: frontendData.profile
    },
    getters: {
        sortedMessages: state =>
            state.messages.sort((a, b) => -(a.id - b.id))
    },
    mutations: {
        addMessageMutation(state, message) {
            state.messages.push(message);
        },
        updateMessageMutation(state, message) {
            const index = state.messages.findIndex(item => item.id === message.id);
            state.messages.splice(index, 1, message);
        },
        removeMessageMutation(state, message) {
            const index = state.messages.findIndex(item => item.id === message.id);
            if (index > -1) {
                state.messages.splice(index, 1);
            }
        },
    },
    actions: {
        async addMessageAction({commit, state}, message) {
            const result = await messagesApi.add(message);
            const data = await result.json();
            const index = state.messages.findIndex(item => item.id === data.id);

            if (index > -1) {
                commit('updateMessageMutation', data);
            } else {
                commit('addMessageMutation', data);
            }
        },
        async updateMessageAction({commit}, message) {
            const result = await messagesApi.update(message);
            const data = await result.json();
            commit('updateMessageMutation', data);
        },
        async removeMessageAction({commit}, message) {
            const result = await messagesApi.remove(message.id);
            if (result.ok) {
                commit('removeMessageMutation', message);
            }
        },
    }
});