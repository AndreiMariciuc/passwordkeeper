import Vue from 'vue'
import App from './App.vue'
import vuetify from './plugins/vuetify'
import router from './router'
import VueSession from 'vue-session'
import VueClipboard from 'vue-clipboard2'

VueClipboard.config.autoSetContainer = true // add this line
Vue.use(VueClipboard)
Vue.use(VueSession)

Vue.config.productionTip = false

new Vue({
    router,
    vuetify,
    render: h => h(App)
}).$mount('#app')
