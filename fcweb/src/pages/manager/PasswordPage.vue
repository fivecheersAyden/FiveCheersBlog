<script setup>
import {
  HomeOutlined,
} from '@ant-design/icons-vue';
import {jumphelper} from "@/js/jumphelper";
import BackgroundCom from "@/coms/select/BackgroundCom.vue";
import {checkManagerPassword} from "@/js/apihelper";
import {message} from "ant-design-vue";
import {ref} from "vue";

const password = ref(null)
const checkPassword = ()=>{
  const checkResult = checkManagerPassword(password.value)
  checkResult.then(response=>{
    if (response.data){
      message.success('管理员验证成功')
      localStorage.setItem('managerSecret','nzt100years')
      jumphelper.jumpToManagerSelect()
    }else {
      message.error('密码错误')
    }
  }).catch(e=>{
    console.log(e)
    message.error('请求出错')
  })
}
</script>

<template>
  <div class="manager_password_background">
    <BackgroundCom/>
    <HomeOutlined style="color: white" @click="jumphelper.jumpToSelect()" class="home_icon"/>
    <div class="password_card">
      <h2>跳转验证</h2>
      <a-divider style="margin: 0; background-color: #ddd; width: 80%" />
      <a-input-password v-model:value="password" placeholder="请输入密码" size="large" style="width: 98%; margin: 32px 0"/>
      <a-button @click="checkPassword" size="large" type="primary" style="width: 50%; ">确定</a-button>
    </div>
  </div>
</template>

<style scoped>
.manager_password_background{
  background-color: #fdfdfd;
}
.password_card{
  width: 90%;
  max-width: 480px;
  height: auto;
  background-color: #eeeeee33;
  position: fixed;
  left: 50%;
  top: 50%;
  transform: translate(-50%, -50%);
  display: flex;
  flex-direction: column;
  align-items: center;
  padding: 0 12px;
  animation: jump-out .6s 1;
  justify-content: space-around;
}
.password_card h2{
  margin: 20px 0;
  color: white;
  font-weight: bold;
  letter-spacing: 4px;
}
.password_card button{
  margin-bottom: 20px;
}
</style>
