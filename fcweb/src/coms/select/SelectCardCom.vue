<script setup>

import {jumphelper} from "@/js/jumphelper";
import {message} from "ant-design-vue";

const jumpToYYM = ()=>{
  message.warn(`5s后跳转...`)
  setTimeout(()=>{
    message.info(`对抗磨损的小妙招`)
  },800)
  setTimeout(()=>{
    window.location.href = 'http://www.fivecheers.com:1002/';
  },3000)
}
</script>

<template>
  <div class="select_card">
    <div class="select_head">
      <img src="../../assets/cheers.png">
      <h1>三盏新茶五盏酒</h1>
      <h2>FIVE CHEERS</h2>
    </div>
    <div class="select_buttons_container">
      <a-divider style="background-color: #aaaaaa88; margin: 0"/>
      <div class="buttons_container">
        <button class="default_select_button" @click="jumphelper.jumpToMyBlog()">工作区</button>
        <button class="default_select_button" @click="jumphelper.jumpToMySpace()">我的空间</button>
        <button class="default_select_button" @click="jumphelper.jumpToMyGit()">作品集</button>
        <button class="default_select_button" @click="jumphelper.jumpToMyPlan()">人生逆旅</button>
        <button class="default_select_button" @click="jumphelper.jumpToMyMusic()">音乐</button>
        <button class="default_select_button building" @click="jumphelper.jumpToMyTravel()">旅行足迹</button>
        <button class="default_select_button building" @click="jumphelper.jumpToLeftAMsg()">给我留言</button>
        <button class="default_select_button manager" @click="jumphelper.jumpToMyBooks()">查账记账</button>
        <button class="default_select_button manager" @click="jumphelper.jumpToDead()">生日</button>
        <button class="default_select_button custom_select_button2" @click="jumpToYYM">旧版于眉</button>
      </div>
    </div>
  </div>
</template>

<style scoped>
  @import "@/css/animation.css";
  .select_card{
    box-shadow: 0 0 12px #645dc244;
    /* background: linear-gradient(to bottom, #978ff144, #9eb9e844); */
    background-color: #ffffff33;
    backdrop-filter: blur(8px);
    border-radius: 12px;
    position: relative;
    animation: topDropAni .8s 1;
    border: 1px solid #eee;
  }
  @keyframes topDropAni {
    0%{
      transform: translate(-50%,-100%) scale(0);
      opacity: 0;
    }
    20%{
      transform: translate(-50%,-100%) scale(0);
      opacity: 0;
    }
  }
  .select_head{
    height: 158px;
    left: 0;
    right: 0;
    top: 0;
    position: absolute;
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
    user-select: none;
    color: white;
  }
  .select_head h1{
    letter-spacing: 6px;
    font-size: 28px;
    font-weight: bold;
    margin-bottom: 8px;
    margin-top: 4px;
    animation: fontSpaceAni1 1.2s 1;
    white-space: nowrap;
  }
  @keyframes fontSpaceAni1 {
    0%{
      letter-spacing: 40px;
    }
    45%{
      letter-spacing: 40px;
    }
  }
  .select_head h2{
    font-size: 16px;
    margin-bottom: 0;
    animation: fontSpaceAni2 1.4s 1;
    white-space: nowrap;
    letter-spacing: 4px;
  }
  @keyframes fontSpaceAni2 {
    0%{
      letter-spacing: 20px;
    }
    45%{
      letter-spacing: 20px;
    }
  }
  .select_head img{
    height: 54px;
    margin-bottom: 4px;
    animation: imgAni 1 1.4s;
  }
  @keyframes imgAni {
    0%{
      transform: translateY(100%);
      opacity: 0;
    }
    60%{
      transform: translateY(100%);
      opacity: 0;
    }
  }
  .select_buttons_container{
    position: absolute;
    bottom: 20px;
    top: 164px;
    left: 12px;
    right: 12px;
  }

  /*按钮*/
  .buttons_container{
    position: absolute;
    height: 100%;
    width: 100%;
    display: grid;
    grid-template-columns: 1fr 1fr 1fr;
    grid-template-rows: 1fr 1fr 1fr 1fr;
    column-gap: 7%;
    row-gap: 15%;
    padding: 32px 2% 12px 2%;
  }
  .default_select_button{
    border: none;
    border-radius: 4px;
    box-shadow: 2px 3px 6px #00000033;
    letter-spacing: 1px;
    font-size: 15px;
    animation: btn-ani 2s 1;
    animation-delay: -0.4s;
    cursor: pointer;
    transition: .4s;
  }
  .default_select_button:hover{
    transform: translate(0, -8px) rotateX(-16deg);
    box-shadow: 2px 4px 12px #c8d4eaaa;
    transition: .4s;
  }
  @keyframes btn-ani {
    0%{
      transform: translateY(-300px);
      opacity: 0;
    }
    60%{
      transform: translateY(-300px);
      opacity: 0;
    }
  }
  .default_select_button:nth-child(1),
  .default_select_button:nth-child(2),
  .default_select_button:nth-child(3){
    color: #fff;
    font-weight: bold;
    letter-spacing: 2px;
    animation-delay: -0.6s;
    position: relative;
    background: transparent;
    overflow: hidden;
  }
  .default_select_button:nth-child(1)::after,
  .default_select_button:nth-child(2)::after,
  .default_select_button:nth-child(3)::after{
    content: "";
    width: 28vw;
    height: 28vw;
    background: linear-gradient(to right bottom, rgba(122, 190, 239, 0.92), rgba(217, 121, 239, 0.92));
    left: calc(50% - 14vw);
    top: calc(50% - 14vw);
    position: absolute;
    z-index: -1;
    animation: rotate-ani 12s infinite linear;
  }
  .default_select_button:nth-last-child(1){
    background: linear-gradient(to right bottom, #bbbbbb, #c9b5b5);
    color: #fff;
    animation-delay: -0.2s;
  }
  .building{
    position: relative;
  }
  .building::after{
    content: "建设中";
    position: absolute;
    left: calc(100% - 32px);
    top: calc(100% - 12px);
    white-space: nowrap;
    color: #86a1e8;
    transform: rotateZ(-16deg);
  }
  .manager{
    position: relative;
  }
  .manager::after{
    content: "管理权限";
    position: absolute;
    left: calc(100% - 32px);
    top: calc(100% - 12px);
    white-space: nowrap;
    color: #e88686;
    transform: rotateZ(-16deg);
  }

</style>
