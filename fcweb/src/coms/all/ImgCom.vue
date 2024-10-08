<script setup>
  import {onMounted, ref} from "vue";
  import {bus} from "vue3-eventbus";

  onMounted(()=>{
    bus.on('showImg', (imgUrl)=>{
      showImgUrl.value=imgUrl
      imgOn.value = true
    })
  })
  const showImgUrl = ref('')
  const imgOn = ref(false)
  const closeImg = ()=>{
    imgOn.value = false
  }
</script>

<template>
  <transition name="fade">
    <div class="img_background" v-if="imgOn">
      <div>
        <div @click="closeImg" style="position: fixed; left: 0; right: 0; top: 0; bottom: 0"></div>
        <img :src="showImgUrl" alt="">
      </div>
    </div>
  </transition>
</template>

<style scoped>
  .img_background{
    z-index: 999;
    position: fixed;
    left: 0;
    top: 0;
    right: 0;
    bottom: 0;
    background-color: #00000044;
    display: flex;
    align-items: center;
    justify-content: center;
    animation: fade-in .5s 1;
    backdrop-filter: blur(4px);
  }
  .img_background div{
    width: calc(100% - 24px);
    height: calc(100% - 24px);
    display: flex;
    align-items: center;
    justify-content: center;
    position: relative;
  }
  .img_background img{
    max-width: 100%;
    max-height: 100%;
    object-fit: contain;
  }
  .close_btn{
    position: absolute;
    right: 0;
    top: 0;
    color: white;
    font-size: 20px;
  }
</style>
