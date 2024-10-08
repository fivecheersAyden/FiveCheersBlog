import { createApp } from 'vue'
import App from './App.vue'
import Particles from "vue3-particles";
import router from "@/router/router";
import Antd from 'ant-design-vue';
import 'ant-design-vue/dist/reset.css';
import eventBus from "vue3-eventbus";
import vue3videoPlay from "vue3-video-play"; // 引入组件
import "vue3-video-play/dist/style.css"; // 引入css

const app = createApp(App)
app.use(Particles)
app.use(router)
app.use(Antd)
app.use(eventBus)
app.use(vue3videoPlay);
app.mount('#app')
