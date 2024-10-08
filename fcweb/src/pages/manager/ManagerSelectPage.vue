<script setup>
import {
  HomeOutlined,
} from '@ant-design/icons-vue';
import {jumphelper} from "@/js/jumphelper";
import {onMounted, onUnmounted, ref} from 'vue';
import SelectDefaultCom from "@/coms/select/SelectDefaultCom.vue";
import {bus} from "vue3-eventbus";
import MeChangeCom from "@/coms/manager/MeChangeCom.vue";
import PlanInsertCom from "@/coms/manager/PlanInsertCom.vue";
import PlanChangeCom from "@/coms/manager/PlanChangeCom.vue";
import SpaceInsertCom from "@/coms/manager/SpaceInsertCom.vue";
import SpaceUpdateCom from "@/coms/manager/SpaceUpdateCom.vue";
import {message} from "ant-design-vue";
const activeKey = ref(null);
onMounted(()=>{
  bus.on('closeSelectCard',()=>{
    Object.keys(comsOn.value).forEach(key => {
      comsOn.value[key] = false;
    });
  })
})
onUnmounted(()=>{
  bus.off('closeSelectCard')
})
const selectCom = (comName)=>{
  switch (comName){
    case 'default': comsOn.value.default = true;
      break;
    case 'meChange': comsOn.value.meChange = true;
      break;
    case 'planInsert': comsOn.value.planInsert = true;
      break;
    case 'planChange': comsOn.value.planChange = true;
      break;
    case 'spaceInsert': comsOn.value.spaceInsert = true;
      break;
    case 'spaceUpdate': comsOn.value.spaceUpdate = true;
      break;
  }
}
const comsOn = ref({
  default:false,
  meChange: false,
  planInsert: false,
  planChange: false,
  spaceInsert: false,
  spaceUpdate: false
})

const logOut = ()=>{
  localStorage.removeItem('managerSecret')
  message.success('已退出登录')
}

</script>

<template>
  <div class="manager_select_background">
    <HomeOutlined @click="jumphelper.jumpToSelect()" class="home_icon"/>
    <h3 class="manager_select_head">选择功能</h3>
    <div class="manager_select_container">
      <a-collapse v-model:activeKey="activeKey" accordion>
        <a-collapse-panel key="1" header="关于我">
          <div class="manager_select_panel">
            <a-button @click="selectCom('meChange')">修改资料</a-button>
            <a-button @click="selectCom('default')">default</a-button>
          </div>
        </a-collapse-panel>
        <a-collapse-panel key="2" header="我的开源">
          <div class="manager_select_panel">

          </div>
        </a-collapse-panel>
        <a-collapse-panel key="3" header="我的空间">
          <div class="manager_select_panel">
            <a-button @click="selectCom('spaceInsert')">发布说说</a-button>
            <a-button @click="selectCom('spaceUpdate')">编辑说说</a-button>
          </div>
        </a-collapse-panel>
        <a-collapse-panel key="4" header="人生逆旅">
          <div class="manager_select_panel">
            <a-button @click="selectCom('planInsert')">增加年度挑战</a-button>
            <a-button @click="selectCom('planChange')">编辑年度挑战</a-button>
          </div>
        </a-collapse-panel>
        <a-collapse-panel key="5" header="吃吃喝喝">
          <div class="manager_select_panel">

          </div>
        </a-collapse-panel>
        <a-collapse-panel key="6" header="旅行足迹">
          <div class="manager_select_panel">

          </div>
        </a-collapse-panel>

        <a-collapse-panel key="7" header="头脑风暴">
          <div class="manager_select_panel">

          </div>
        </a-collapse-panel>
        <a-collapse-panel key="8" header="音乐">
          <div class="manager_select_panel">

          </div>
        </a-collapse-panel>
        <a-collapse-panel key="9" header="给我留言">
          <div class="manager_select_panel">

          </div>
        </a-collapse-panel>
        <a-collapse-panel key="10" header="退出登录">
          <div class="manager_select_panel">
            <a-button @click="logOut">退出登录</a-button>
          </div>
        </a-collapse-panel>
      </a-collapse>
    </div>
    <transition name="fade">
      <MeChangeCom v-if="comsOn.meChange"/>
    </transition>
    <transition name="fade">
      <SelectDefaultCom v-if="comsOn.default"/>
    </transition>
    <transition name="fade">
      <PlanInsertCom v-if="comsOn.planInsert"/>
    </transition>
    <transition name="fade">
      <PlanChangeCom v-if="comsOn.planChange"/>
    </transition>
    <transition name="fade">
      <SpaceInsertCom v-if="comsOn.spaceInsert"/>
    </transition>
    <transition name="fade">
      <SpaceUpdateCom v-if="comsOn.spaceUpdate"/>
    </transition>

  </div>
</template>

<style scoped>
.manager_select_background{
  background: linear-gradient(to right bottom, #f7fafcaa, #eef5fcaa, #f7ecfaaa);
}
.manager_select_background_img{
  position: fixed;
  bottom: 72px;
  right: 36px;
  width: 60%;
  opacity: .1;
  animation: cheer .8s 1;
}
@keyframes cheer {
  0%{
    transform: rotateZ(25deg) scale(.4) translate(300%, 50%);
  }
}
.manager_select_head{
  position: fixed;
  left: 50%;
  top: 14px;
  height: 40px;
  transform: translate(-50%, 0);
  letter-spacing: 4px;
}
.manager_select_container{
  position: fixed;
  left: 50%;
  top: 50%;
  transform: translate(-50%, -50%);
  width: calc(100% - 12px);
  animation: right-fade-in .4s 1;
  max-width: 600px;
}
.manager_select_panel{
  display: flex;
  flex-wrap: wrap;
  justify-content: space-around; /* 左右铺开 */
}
.manager_select_panel button{
  margin: 4px 0;
}
</style>
