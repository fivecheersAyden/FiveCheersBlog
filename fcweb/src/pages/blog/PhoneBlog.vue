<template>
  <div class="blog-page-container">
    <!--树形折叠导航栏，可折叠，管理员可编辑-->
    <BlogMenuPhoneCom class="blog-menu-com"/>
    <!--滚动页面，包括文章属性、博客区（md组件）、评论区-->
    <div class="scroll-page">
      <BlogContentPhoneCom/>
      <BlogCommentCom/>
    </div>
    <LeftOutlined @click="jumphelper.jumpToSelect()" style="transform: translateX(-14px)" class="home_icon"/>
  </div>
</template>

<script setup>
import {
  LeftOutlined,
} from '@ant-design/icons-vue';
import {jumphelper} from "@/js/jumphelper";
import BlogCommentCom from "@/coms/blog/BlogCommentCom.vue";
import BlogContentPhoneCom from "@/coms/blog/BlogContentPhoneCom.vue";
import BlogMenuPhoneCom from "@/coms/blog/BlogMenuPhoneCom.vue";

</script>

<style scoped>
.blog-page-container{
  position: fixed;
  left: 0;
  top: 0;
  right: 0;
  bottom: 0;
  display: flex;
  align-items: center;
  justify-content: space-between;
  animation: top-fade-in .6s 1;
}
.blog-menu-com{
  height: 100%;
}
.scroll-page{
  flex: 1;
  height: 100%;
  background-color: #fdfdfd;
  display: flex;
  align-items: center;
  justify-content: start;
  position: relative;
  flex-direction: column;
  overflow-y: scroll;
  overflow-x: hidden;
}
</style>
