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
  top: 0;
  left: 0;
  right: 0;
  height: 200px;
  display: flex;
  align-items: center;
  justify-content: space-around;
  animation: top-fade-in .8s 1;
}
.friend-window img{
  height: calc(100% - 36px);
  border-radius: 12px;
  margin: 32px 12px 0 12px;
}
.friend-window .btn-container{
  flex: 1;
  height: calc(100% - 72px);
  margin-right: -4%;
  margin: 60px 0 0 12px;
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
  left: 0;
  width: 100vw;
  height: calc(100vh - 220px);
  animation: bottom-fade-in .8s 1;
}

</style>