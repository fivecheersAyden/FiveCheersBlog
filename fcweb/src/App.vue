<template>
  <div id="#app">
    <router-view class="root_router_view" />
  </div>
</template>

<script setup>
  import {onMounted} from "vue";
  import {postLog} from "@/js/apihelper";

  onMounted(()=>{
    const devicetype = (window.innerWidth || document.documentElement.clientWidth || document.body.clientWidth) < 768 ? 'phone' : 'desktop';
    postLog(`访问五盏-${devicetype}`)
  })


</script>

<style>
  @import "@/css/allstyle.css";
  @import "@/css/animation.css";
  .root_router_view{
    position: fixed;
    left: 0;
    right: 0;
    top: 0;
    bottom: 0;
  }
</style>
