import Vue from 'vue'
import VueResource from 'vue-resource'
import BootstrapVue from 'bootstrap-vue'
import App from 'pages/App.vue'
import {connect} from './util/ws'
import Vuetify from 'vuetify'

import 'bootstrap/dist/css/bootstrap.css'
import 'bootstrap-vue/dist/bootstrap-vue.css'
import 'vuetify/dist/vuetify.min.css'

if (frontendData.profile) {
    connect();
}

Vue.use(VueResource);
Vue.use(BootstrapVue);
Vue.use(Vuetify);

new Vue({
    el: '#app',
    render: a => a(App)
});
