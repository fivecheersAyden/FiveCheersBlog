<template>
  <div @click="jumpFuc">
    <!--back ground-->
    <p>你看清了这串字，真棒。</p>
    <div class="background"></div>
    <!--hello world, I'm Ayden-->
    <p>Hello World, I'm Ayden</p>
    <!--modal-->
    <a-modal v-model:open="open" title="Visit">
      <template #footer>
        <a-button size="small" key="space" @click="goToSpace">Ayden's space</a-button>
        <a-button size="small" key="submit" @click="goToCodingBlog">Ayden's coding blog</a-button>
      </template>
    </a-modal>
  </div>
</template>

<script setup>
  import { ref } from 'vue';
  import {jumphelper} from "@/js/jumphelper";
  const open = ref(false);
  const jumpFuc = ()=>{
    open.value = true;
  }
  const goToSpace = () => {
    open.value = false;
    jumphelper.jumpToSelect()
  };
  const goToCodingBlog = () => {
    open.value = false;
    jumphelper.jumpToMyBlog()
  };
</script>

<style scoped>
  *{
    user-select: none;
  }
  .background{
    position: fixed;
    left: 0;
    right: 0;
    top: 0;
    bottom: 0;
    background: linear-gradient(to right bottom, #f1f3faaa, #e8f1fcaa, #efe5faaa);
    backdrop-filter: blur(8px);
  }
  p{
    position: absolute;
    top: 50%;
    left: 50%;
    transform: translate(-50%, -50%);
    letter-spacing: 3px;
    white-space: nowrap;
    animation: letter-ani .8s 1;
  }
  @keyframes letter-ani {
    0%{
      opacity: 0;
      letter-spacing: 20px;
    }
  }

</style>
