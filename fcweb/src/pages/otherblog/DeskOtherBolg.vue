<template>
  <div>
    <div class="blog-page-container" v-if="userLogined">
      <LeftOutlined @click="jumphelper.jumpToMyBlog()" style="transform: translateX(-14px)" class="home_icon"/>
      <!--树形折叠导航栏，可折叠，管理员可编辑-->
      <OtherBlogMenuCom class="blog-menu-com"/>
      <!--滚动页面，包括文章属性、博客区（md组件）、评论区-->
      <div class="scroll-page">
        <OtherBlogContentCom/>
        <OtherBlogCommentCom/>
      </div>
    </div>
    <div class="unlogined-container" v-else>
      <div>
        <a-input v-model:value="user" placeholder="请输入用户名"/>
        <a-button type="primary" @click="checkUser">登录</a-button>
      </div>
    </div>
  </div>
</template>

<script setup>
import {
  LeftOutlined,
} from '@ant-design/icons-vue';
import {jumphelper} from "@/js/jumphelper";

import {onMounted, ref} from "vue";
import {checkUserExists} from "@/js/apihelper";
import {message} from "ant-design-vue";
import OtherBlogMenuCom from "@/coms/blog/otherblog/OtherBlogMenuCom.vue";
import OtherBlogContentCom from "@/coms/blog/otherblog/OtherBlogContentCom.vue";
import OtherBlogCommentCom from "@/coms/blog/otherblog/OtherBlogCommentCom.vue";

onMounted(()=>{
  user.value = localStorage.getItem('user')
  if(user.value){
    userLogined.value = true
  }
})

const user = ref(null)
const userLogined = ref(false)
const checkUser = ()=>{
  const getResult = checkUserExists(user.value)
  getResult.then(response=>{
    if (response.data){
      message.success('登陆成功，用户已存储')
      localStorage.setItem('user', user.value)
      setTimeout(()=>{
        userLogined.value = true
      },400)
    }
    else{
      message.warn('用户不存在，请联系我增加权限')
    }
  }).catch(e=>{
    console.log(e)
    message.error('未知错误')
  })
}

</script>

<style scoped>
  .unlogined-container{
    position: fixed;
    left: 0;
    right: 0;
    top: 0;
    bottom: 0;
    display: flex;
    align-items: center;
    justify-content: center;
  }
  .unlogined-container div{
    width: 300px;
    height: 120px;
    box-shadow: 0 0 8px #00000022;
    border-radius: 8px;
    display: flex;
    align-items: center;
    justify-content: center;
  }
  .unlogined-container div input{
    width: calc(100% - 120px);
    margin-right: 12px;
  }
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
