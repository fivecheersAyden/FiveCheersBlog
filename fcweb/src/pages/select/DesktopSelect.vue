<script setup>
import SelectCardCom from "@/coms/select/SelectCardCom.vue";
import BackgroundCom from "@/coms/select/BackgroundCom.vue";
import {LeftOutlined} from "@ant-design/icons-vue";
import {jumphelper} from "@/js/jumphelper";
import {checkManager} from "@/js/jshelper";

const beforeJumpToManager = ()=>{
  if(checkManager()){
    jumphelper.jumpToManagerSelect()
  }else{
    jumphelper.jumpToManagerPassword()
  }

}
</script>

<template>
  <div>
    <BackgroundCom/>
    <SelectCardCom class="select_card_pos"/>
    <a-button @click="beforeJumpToManager" class="manager_btn" type="text" size="small">管理入口</a-button>
    <LeftOutlined style="color: #ffffff" @click="jumphelper.jumpToHello()" class="home_icon"/>
  </div>
</template>

<style scoped>
  .select_card_pos{
    position: fixed;
    left: 50%;
    top: 50%;
    transform: translate(-50%, -50%);
    width: 70%;
    min-width: 400px;
    height: 86%;
  }
  .manager_btn{
    position: fixed;
    right: 12px;
    top: 16px;
    color: #fff;
    letter-spacing: 2px;
    animation: right-fade-in 1 .8s;
  }
</style>
