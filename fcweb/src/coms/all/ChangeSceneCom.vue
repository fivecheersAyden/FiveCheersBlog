<script setup>
import {defineExpose, ref} from 'vue'

  const ani = ref(false)
  const startAni = ()=>{
    ani.value = true
    setTimeout(()=>{
      ani.value = false
    },2100)
  }
  defineExpose({
    startAni
  })
</script>

<template>
  <div v-if="ani" class="scene-container">
    <ul>
      <li></li>
      <li></li>
      <li></li>
      <li></li>
      <li></li>
    </ul>
  </div>
</template>

<style scoped>
  .scene-container{
    position: fixed;
    left: 0;
    right: 0;
    display: flex;
    align-items: center;
    justify-content: center;
    width: 100vw;
    height: 100vh;
  }
  .scene-container ul{
    width: 240vw;
    height: 240vh;
    display: flex;
    justify-content: space-between;
  }
  .scene-container ul li{
    height: calc(100% + 24px);
    border-radius: 24px;
    animation: up-ani 2.6s 1 ease-in-out;
  }
  .scene-container ul li:nth-child(1){
    background-color: #91aefa;
    flex: 1;
  }
  .scene-container ul li:nth-child(2){
    background-color: #f7fa91;
    flex: 1;
    animation-delay: -.2s;
  }
  .scene-container ul li:nth-child(3){
    background-color: #91e7fa;
    flex: 1;
    animation-delay: -.1s;
  }
  .scene-container ul li:nth-child(4){
    background-color: #f58fa1;
    flex: 1;
    animation-delay: -.3s;
  }
  @keyframes up-ani {
    0%{
      opacity: 0;
      transform: translateY(60%);
    }
    50%{
      opacity: 1;
    }
    100%{
      transform: translateY(-100%);
    }
  }


</style>