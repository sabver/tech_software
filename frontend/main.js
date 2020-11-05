import Vue from 'vue'
import App from './App'

import store from './store'

// import VueKatex from 'vue-katex'
// import 'katex/dist/katex.min.css';

// Vue.use(VueKatex)

Vue.config.productionTip = false

Vue.prototype.$store = store

App.mpType = 'app'

const app = new Vue({
	store,
    ...App
})
app.$mount()
