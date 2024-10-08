<script setup>
import {
  HomeOutlined,
} from '@ant-design/icons-vue';
import {jumphelper} from "@/js/jumphelper";
import DefaultBackground from "@/coms/all/DefaultBackground.vue";
import BuildingCom from "@/coms/all/BuildingCom.vue";


</script>

<template>
  <div>
    <HomeOutlined @click="jumphelper.jumpToSelect()" class="home_icon" style="color: #fff"/>
    <DefaultBackground/>
    <BuildingCom/>
  </div>
</template>

<style scoped>
  .password-container{
    position: fixed;
    top: 60px;
    left: 50%;
    transform: translateX(-50%);
    width: 320px;
    height: 200px;
    box-shadow: 0 0 12px #00000044;
    background-color: #ffffff33;
    backdrop-filter: blur(8px);
    border-radius: 8px;
    animation: topDropAni .8s 1;
    display: flex;
    align-items: center;
    justify-content: space-around;
    flex-direction: column;
    padding: 28px 20px 20px 20px;
  }
</style>