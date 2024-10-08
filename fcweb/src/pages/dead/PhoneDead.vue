<script setup>
import {
  HomeOutlined,LeftOutlined, MoreOutlined
} from '@ant-design/icons-vue';
import {jumphelper} from "@/js/jumphelper";
import DefaultBackground from "@/coms/all/DefaultBackground.vue";
import {ref} from "vue";
import ChangeSceneCom from "@/coms/all/ChangeSceneCom.vue";
import BuildingCom from "@/coms/all/BuildingCom.vue";

/**
 * 切换菜单显示状态
 * @type {Ref<UnwrapRef<boolean>>}
 */
const showMenuBtn = ref(false)
const changeShowMenu = ()=>{
  showMenuBtn.value = !showMenuBtn.value
}
/**
 * 改变当前筛选
 */
const changeFilter = (index)=> {
  //动画过渡
  changeShowMenu()
  changeSceneCom.value.startAni()
  //更改标题
  switch (index){
    case 0: showTitle.value='饭否'
          break;
    case 1: showTitle.value='药方'
          break;
    case 2: showTitle.value='茶经'
          break;
    case 3: showTitle.value='酒谱'
          break;
  }
}
const changeSceneCom = ref()

/**
 * 展示内容
 */
const showTitle = ref('饭否')
//const allList = ref([])
//const showList = ref([])

</script>

<template>
  <div>
    <HomeOutlined style="color: #fff" @click="jumphelper.jumpToSelect()" class="home_icon"/>
    <div style="position: fixed; left: 50%; top: 50%; transform: translate(-50%, -50%)">建设中，请移步主页->旧版于眉（这是一个上滑网页！）</div>
    <default-background/>
    <!--导航-->
    <div class="menu-container-out">
      <div @click="changeShowMenu" class="menu-btn"><MoreOutlined /></div>
      <div class="menu-container">
        <div @click="changeFilter(0)" v-if="showMenuBtn"><LeftOutlined style="margin: 0 2px 0 -4px"/>吃饭</div>
        <div @click="changeFilter(1)" v-if="showMenuBtn"><LeftOutlined style="margin: 0 2px 0 -4px"/>吃药</div>
        <div @click="changeFilter(2)" v-if="showMenuBtn"><LeftOutlined style="margin: 0 2px 0 -4px"/>喝茶</div>
        <div @click="changeFilter(3)" v-if="showMenuBtn"><LeftOutlined style="margin: 0 2px 0 -4px"/>喝酒</div>
      </div>
    </div>
    <!--转场动画-->
    <ChangeSceneCom ref="changeSceneCom"/>
    <!--建设中-->
    <BuildingCom/>
  </div>
</template>

<style scoped>
  .menu-container-out{
    position: fixed;
    top: 12px;
    right: 16px;
    height: 160px;
    width: 100px;
    display: flex;
    align-items: center;
    justify-content: space-between;
  }
  .menu-btn{
    color: #fff;
    font-weight: bold;
    font-size: 24px;
    height: 100%;
    margin-top: 8px;
    flex: 1;
    text-align: end;
  }
  .menu-container{
    display: flex;
    height: 100%;
    width: auto;
    flex-direction: column;
    align-items: center;
    justify-content: space-between;
  }
  .menu-container div{
    height: 32px;
    width: 84px;
    background: transparent;
    display: flex;
    align-items: center;
    justify-content: center;
    border-radius: 4px;
    letter-spacing: 2px;
    font-weight: bold;
    animation: right-in-ani .8s 1;
    position: relative;
  }
  .menu-container div::after{
    content: "";
    display: block;
    position: absolute;
    left: 0;
    top:0;
    width: 100%;
    height: 100%;
    opacity: 0;
    transition: all 0.5s;
    box-shadow: 0 0 10px 40px #75adf6;
    border-radius: 4em;
  }
  .menu-container div:active::after{
    opacity: 1;
    transition: 0s;
    box-shadow: 0 0 0 0 #a475f6;
  }
  .menu-container div:nth-child(1){
    border: 2px solid #75adf6;
    color: #75adf6;
    animation-delay: -.6s;
  }
  .menu-container div:nth-child(2){
    border: 2px solid #75adf6;
    color: #75adf6;
    animation-delay: -.4s;
  }
  .menu-container div:nth-child(3){
    border: 2px solid #a475f6;
    color: #a475f6;
    animation-delay: -.2s;
  }
  .menu-container div:nth-child(4){
    border: 2px solid #a475f6;
    color: #a475f6;
  }
  @keyframes right-in-ani {
    0%{
      transform: translateX(100%);
    }
    40%{
      transform: translateX(100%);
    }
  }
</style>