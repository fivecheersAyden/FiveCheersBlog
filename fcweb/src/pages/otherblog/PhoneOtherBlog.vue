<template>
  <div>
    <LeftOutlined style="margin-left: -8px" @click="jumphelper.jumpToHello()" class="home_icon"/>
    <BuildingCom/>
  </div>
</template>

<script setup>
import BuildingCom from "@/coms/all/BuildingCom.vue";
import {
  LeftOutlined,
} from '@ant-design/icons-vue';
import {jumphelper} from "@/js/jumphelper";

</script>

<style scoped>

</style>
