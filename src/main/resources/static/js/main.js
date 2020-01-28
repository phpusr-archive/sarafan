import Vue from 'vue'
import Vuetify from 'vuetify'
import '@babel/polyfill'
import 'api/resource'
import router from 'router/router'
import App from 'pages/App.vue'
import store from 'store/store'
import {connect} from './util/ws'
import * as Sentry from '@sentry/browser'
import * as Integrations from '@sentry/integrations'

import 'vuetify/dist/vuetify.min.css'

Sentry.init({
    dsn: 'https://6133a0f497ec4fd0b8bbe73bdde1f652@sentry.io/2035827',
    integrations: [new Integrations.Vue({Vue, attachProps: true})],
})

if (profile) {
    Sentry.configureScope(scope =>
        scope.setUser({
            id: profile.id,
            username: profile.name
        })
    )

    connect()
}

Vue.use(Vuetify)

new Vue({
    el: '#app',
    store,
    router,
    render: a => a(App)
})
