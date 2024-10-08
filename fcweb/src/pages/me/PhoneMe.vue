<script setup>
// import {
//   HomeOutlined,
// } from '@ant-design/icons-vue';
import {jumphelper} from "@/js/jumphelper";
//import { WechatOutlined,QqOutlined,GithubOutlined } from '@ant-design/icons-vue';
import {message} from "ant-design-vue";
import {onMounted, ref} from "vue";
import {getMeList} from "@/js/apihelper";
import DefaultBackground from "@/coms/all/DefaultBackground.vue";

const name = ref(null);
const contact_wechat = ref(null);
const contact_qq = ref(null)
const contact_github = ref(null)
const photo = ref(null)
const frontEnd = ref([])
const rearEnd = ref([])
const ai = ref([])
const other = ref([])

onMounted(()=>{
  const meListResult = getMeList()
  meListResult.then(response=>{
    name.value = response.data.name
    contact_wechat.value = response.data.contact_wechat
    contact_qq.value = response.data.contact_qq
    contact_github.value = response.data.contact_github
    photo.value = response.data.photo
    frontEnd.value = response.data.intro_1.split(',')
    rearEnd.value = response.data.intro_2.split(',')
    ai.value = response.data.intro_3.split(',')
    other.value = response.data.intro_4.split(',')
  }).catch(e=>{
    console.log(e)
    message.error('获取数据失败')
  })
})

const copyContact = (type, event)=>{
  event.stopPropagation();
  let copyText = null;
  switch (type){
    case 0: copyText = contact_wechat.value
      break;
    case 1: copyText = contact_qq.value
      break;
    case 2: copyText = contact_github.value
      break;
  }
  const textArea = document.createElement("textarea");
  textArea.value = copyText;
  document.body.appendChild(textArea);
  textArea.select();
  try {
    document.execCommand('copy');
    message.success('复制成功');
  } catch (err) {
    console.error('复制失败', err);
    message.error('复制失败');
  }
  document.body.removeChild(textArea);
}
const open = ref(false)
const jumpFuc = ()=>{
  open.value = true;
}
const goToSpace = () => {
    open.value = false;
    jumphelper.jumpToSelect()
  };
</script>

<template>
  <div @click="jumpFuc">
    <!-- <HomeOutlined style="color: #fff" @click="jumphelper.jumpToSelect()" class="home_icon"/> -->
    <DefaultBackground/>
    <!--modal-->
    <a-modal v-model:open="open" title="Visit">
      <template #footer>
        <a-button size="small" key="space" @click="goToSpace">Ayden's space</a-button>
      </template>
    </a-modal>
    <!--顶部照片、简介-->
    <div class="top-container">
      <!--介绍-->
      <div class="intro-container">
        <h1>Hello, <br/>I'm Ayden</h1>
        <p>时啟轩</p>
        <p>I'm a software developer, a book lover, as well as a traveler.</p>
      </div>
      <!--图片-->
      <div class="photo-container">
        <img src="@/assets/ink.png" alt="null">
        <div class="photo-background"></div>
        <div class="photo-self">
          <img :src="photo" alt="">
        </div>
      </div>
    </div>
    <!--底部我的经验-->
    <svg class="ground-container" height='2000' width="900" version='1.1' xmlns='http://www.w3.org/2000/svg'>
      <path d="M0 150
                 C60 130,120 130, 180 150
                 S300 170,360 150
                 S480 130,540 150
                 S660 170,720 150
                 S840 130,900 150
                 V2000 H0 Z" fill="#1c2a42ee"  stroke-width="3">
        <animateTransform attributeName="transform" attributeType="XML" type="translate" from="0" to="-360" dur="6s" repeatCount="indefinite"></animateTransform>
      </path>
      <path d="M0 150
                 C60 120,120 120, 180 150
                 S300 180,360 150
                 S480 120,540 150
                 S660 180,720 150
                 S840 120,900 150
                 V2000 H0 Z" fill="#2d426588"  stroke-width="3">
        <animateTransform attributeName="transform" attributeType="XML" type="translate" from="0" to="-360" dur="4s" repeatCount="indefinite"></animateTransform>
      </path>
    </svg>
    <div class="dec-container">
      <div class="dec1">

      </div>
      <div class="dec2">

      </div>
      <div class="dec3">

      </div>
    </div>
    <!--具体内容-->
    <div class="myexp-container">
      <div class="exp-container">
        <h3>- Vocational Skills -</h3>
        <div class="exp-grid-container">
          <div v-for="(item, index) in frontEnd" :key="index" class="exp-grid">
            <h4>{{item}}</h4>
          </div>
        </div>
      </div>
      <div class="exp-container">
        <h3>- Representative Works -</h3>
        <div class="exp-grid-container" style="grid-template-columns: repeat(2, 1fr)">
          <div v-for="(item, index) in rearEnd" :key="index" class="exp-grid">
            <h4>{{item}}</h4>
          </div>
        </div>
      </div>
      <div class="exp-container">
        <h3>- Favorite Books -</h3>
        <div class="exp-grid-container-book">
          <div v-for="(item, index) in ai" :key="index" class="exp-grid">
            <h4>{{item}}</h4>
          </div>
        </div>
      </div>
      <div class="exp-container">
        <h3>- Other -</h3>
        <div class="exp-grid-container">
          <div v-for="(item, index) in other" :key="index" class="exp-grid">
            <a @click.stop style="color: #fff;" :href="item">My Github</a>
          </div>
        </div>
      </div>
    </div>
    <!--联系方式-->
    <div class="contact-container">
      <div @click="copyContact(0,$event)">Wechat</div>
      <div @click="copyContact(1,$event)">Email</div>
      <div @click="copyContact(2,$event)">Telegram</div>
    </div>
  </div>
</template>

<style scoped>
  @import "@/css/animation.css";
  *{
  }
  /*顶部*/
  .contact-container{
    position: fixed;
    right: 12px;
    top: 4px;
    height: 32px;
    width: 80vw;
    display: flex;
    align-items: center;
    justify-content: end;
    z-index: 9999;
    animation: top-fade-in 0.5s 1;
  }
  .contact-container div{
    color: #eee;
    margin-left: 12px;
    font-size: 12px;
    position: relative;
  }
  .contact-container div::before{
    content: "";
    width: 16px;
    height: 2px;
    background-color: #aaaaaadd;
    position: absolute;
    top: 80%;
    left: -4px;
    transform: rotateZ(20deg);
  }
  .top-container{
    position: fixed;
    left: 0;
    right: 0;
    top: 20px;
    height: 240px;
    display: flex;
    align-items: center;
    justify-content: space-around;
  }
  .intro-container{
    flex: 1;
    height: 100%;
    color: #fff;
    margin: 0 4px 0 20px;
    display: flex;
    flex-direction: column;
    justify-content: center;
    animation: left-fade-in .6s 1;
  }
  .intro-container h1{
    font-size: 28px;
    white-space: pre-line;
    word-break: keep-all;
    letter-spacing: 2px;
    font-weight: bold;
    margin-bottom: 8px;
  }
  .intro-container p{
    font-size: 12px;
    color: #ddd;
    word-break: keep-all;
  }
  .photo-container{
    width: 130px;
    height: 130px;
    margin: 0 20px 0 4px;
    display: flex;
    align-items: center;
    justify-content: center;
    position: relative;
  }
  .photo-container img{
    width: 380px;
    transform: translate(-16px, -30px);
  }
  .photo-background{
    position: absolute;
    left: 50%;
    top: 50%;
    transform: translate(-50%, -50%) rotateZ(12deg);
    width: 100px;
    height: 100px;
    background-color: #86a1e8;
    animation: photo-back-ani 1.2s 1;
  }
  @keyframes photo-back-ani {
    0%{
      scale: .8;
      transform: translate(-50%, -50%) rotateZ(-12deg);
    }
  }
  .photo-self{
    position: absolute;
    left: 50%;
    top: 50%;
    transform: translate(-50%, -50%) rotateZ(-4deg);
    background-color: #ddd;
    width: 96px;
    height: 96px;
    display: flex;
    align-items: center;
    justify-content: center;
    overflow: hidden;
    padding: 2px;
    animation: photo-back-ani 1.2s 1;
  }
  .photo-self img{
    width: 100%;
    height: 100%;
    object-fit: cover;
    transform: translate(0, 0);
  }

  /*底部*/
  .ground-container{
    position: fixed;
    bottom: 0;
    left: 0;
    right: 0;
    top: 120px;
    animation: fade-in .6s 1;
  }
  .dec-container{
    position: fixed;
    top: 280px;
    left: 0;
    right: 0;
    bottom: 0;
    animation: move1 ease-in-out 16s infinite;
  }
  @keyframes move1 {
    50% {
      transform: translate(12px, 8px) rotateZ(4deg); /* 随机移动 */
    }
  }
  .dec1{
    position: absolute;
    right: -20px;
    top: 92px;
    width: 120px;
    height: 60px;
    background-color: #1f5f7a;
    transform: rotateZ(12deg);
    animation: right-fade-in .8s 1;
  }
  .dec1::before{
    content: "";
    position: absolute;
    width: 120px;
    height: 60px;
    left: -6px;
    top: -8px;
    background-color: #669bb2;
  }
  .dec2{
    position: absolute;
    bottom: 172px;
    right: 52px;
    width: 72px;
    height: 72px;
    background-color: transparent;
    border-radius: 999px;
    border: 2px solid #aaa;
    animation: move2 12s infinite ease-in-out;
  }
  @keyframes move2 {
    50% {
      transform: translate(32px, 24px); /* 随机移动 */
    }
  }
  .dec3{
    position: absolute;
    left: -20px;
    bottom: -12px;
    width: 140px;
    height: 70px;
    background-color: #887d32;
    transform: rotateZ(-60deg);
    animation: right-fade-in 1s 1;
  }
  .dec3::before{
    content: "";
    position: absolute;
    width: 140px;
    height: 70px;
    left: -8px;
    top: -10px;
    background-color: #e3d472;
  }
  .myexp-container{
    position: fixed;
    bottom: 0;
    left: 0;
    right: 0;
    top: 240px;
    display: flex;
    flex-direction: column;
    align-items: center;
    padding: 60px 20px 12px 20px;
    justify-content: space-around;
  }
  .exp-container{
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: space-around;
    min-height: 72px;
    width: 100%;
    animation: bottom-fade-in 1.6s 1;
  }
  .exp-container:nth-child(1){
    animation-delay: -.9s;
  }
  .exp-container:nth-child(2){
    animation-delay: -.6s;
  }
  .exp-container:nth-child(3){
    animation-delay: -.3s;
  }
  .exp-container h3{
    font-size: 13px;
    color: #ccc;
    margin-bottom: 16px;
  }
  .exp-grid-container{
    flex: 1;
    width: 100%;
    display: grid;
    grid-template-columns: repeat(auto-fit, minmax(100px, 1fr));
    grid-gap: 16px;
  }
  .exp-grid{
    display: flex;
    align-items: center;
    justify-content: center;
  }
  .exp-grid h4{
    color: #f8f8f8;
    margin-bottom: 0;
    letter-spacing: 2px;
    font-weight: bold;
    white-space: nowrap;
  }
  .exp-grid-container-book{
  flex: 1;
  width: 100%;
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(200px, 1fr));
  grid-gap: 16px;
}

</style>
