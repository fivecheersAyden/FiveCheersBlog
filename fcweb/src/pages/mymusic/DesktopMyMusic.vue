<script setup>
import {
  HomeOutlined,
} from '@ant-design/icons-vue';
import {jumphelper} from "@/js/jumphelper";
import { ref } from 'vue';
// import DefaultBackground from "@/coms/all/DefaultBackground.vue";
// import BuildingCom from "@/coms/all/BuildingCom.vue";

const showToast = ref(true);
setTimeout(() => {
  showToast.value = false;
}, 10000);

</script>

<template>
  <div class="all-container">
    <HomeOutlined @click="jumphelper.jumpToSelect()" style="color: #fff" class="home_icon"/>
    <div class="friend-window">
      <div class="btn-container">
        <h1>My Playlist</h1>
        <a href="https://open.spotify.com/playlist/55q2MGwZrpagHajWOzNFqm" target="_blank">
          跳转 Spotify 播放
        </a>
      </div>
      <img :src="require('@/assets/spotify_code.jpg')" alt="" >
    </div>
    <iframe
      class="spotify-window"
      title="Spotify Embed: Recommendation Playlist"
      :src="'https://open.spotify.com/embed/playlist/55q2MGwZrpagHajWOzNFqm?utm_source=generator&theme=0'"
      frameborder="0"
      allow="autoplay; clipboard-write; encrypted-media; fullscreen; picture-in-picture"
      loading="lazy"
    />
  </div>
</template>

<style scoped>
@import "@/css/animation.css";
.all-container{
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: space-between;
  overflow-y: scroll;
  background: linear-gradient(to right bottom, #211b77, #971c5e)
}
.friend-window{
  position: fixed;
  top: 20vh;
  left: 40px;
  width: 220px;
  height: 60vh;
  display: flex;
  flex-direction: column;
  align-items: end;
  justify-content: center;
  animation: left-fade-in .8s 1;
}
.friend-window img{
  width: 60%;
  max-width: 120px;
  min-width: 100px;
  border-radius: 12px;
  
}
.friend-window .btn-container{
  height: 120px;
  margin: 0 0 24px 0;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
}
.friend-window .btn-container h1{
  color: #fff;
  white-space: nowrap;
}
.friend-window .btn-container a{
  color: #fff;
  margin-left: 32px;
}
.spotify-window{
  position: fixed;
  bottom: 0;
  right: 0;
  width: calc(100vw - 320px);
  height: 100vh;
  animation: right-fade-in .8s 1;
  min-width: 600px;
}

</style>