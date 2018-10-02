import Vue from 'vue'
import VueResource from 'vue-resource'
import BootstrapVue from 'bootstrap-vue'
import App from 'pages/App.vue'
import {connect} from './util/ws'

import 'bootstrap/dist/css/bootstrap.css'
import 'bootstrap-vue/dist/bootstrap-vue.css'

connect();

Vue.use(VueResource);
Vue.use(BootstrapVue);

new Vue({
    el: '#app',
    render: a => a(App)
});
