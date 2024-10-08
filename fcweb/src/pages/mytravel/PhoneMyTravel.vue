<script setup>
import {
  HomeOutlined,
} from '@ant-design/icons-vue';
import {jumphelper} from "@/js/jumphelper";
import BuildingCom from "@/coms/all/BuildingCom.vue";
</script>

<template>
  <div>
    <HomeOutlined @click="jumphelper.jumpToSelect()" class="home_icon"/>
    <BuildingCom/>
  </div>
</template>

<style scoped>

</style>