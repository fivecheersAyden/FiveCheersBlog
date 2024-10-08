<script setup>
import {
    HomeOutlined,
    LeftOutlined,
    RightOutlined
} from '@ant-design/icons-vue';
import {jumphelper} from "@/js/jumphelper";
import {onMounted, ref} from "vue";
import {message} from "ant-design-vue";
import {getAmIDead, getPlanAllPlans, refreshDeadGift, refreshDeadTime} from "@/js/apihelper";
import {checkAmIDead, timeCorrect} from "@/js/jshelper";

onMounted(()=>{
  initTargets()
  getInfo()
})
//计划
const initTargets = ()=>{
  //获取allYearTargets
  const allTargetsResult = getPlanAllPlans()
  allTargetsResult.then(response=>{
    allYearTargets.value = response.data
    currentYear.value = new Date().getFullYear()
    getCurrentYearTargets()
  }).catch(e=>{
    console.log(e)
    message.error('获取挑战板数据失败')
  })
}
const getCurrentYearTargets = ()=>{
  const tmp = allYearTargets.value.filter(item => item.year === currentYear.value)
  if (tmp.length >0){
    currentYearTargets.value = tmp
  }
}
const currentYear = ref(null)
const allYearTargets = ref([])
const currentYearTargets = ref([{planImg:null}])
const currentYearTargetIndex = ref(0)
const handleYearChange = (value)=>{
  if (value === 'last'){
    console.log(currentYear.value)
  }
}
const lastYear = ()=>{
  getCurrentYearTargets()
  if (currentYearTargets.value.length <1){
    currentYear.value += 1
    nextYear()
    message.warn('前面没有了')
  }else{
    currentYearTargetIndex.value = 0
  }
}
const nextYear = ()=>{
  getCurrentYearTargets()
  if (currentYearTargets.value.length <1){
    currentYear.value -=1;
    lastYear()
    message.warn('后面没有了')
  }else{
    currentYearTargetIndex.value = 0
  }
}
const lastTarget = ()=>{
  if (currentYearTargetIndex.value === 0){
    currentYearTargetIndex.value = currentYearTargets.value.length - 1
  }else{
    currentYearTargetIndex.value -= 1
  }
}
const nextTarget = ()=>{
  if (currentYearTargetIndex.value === currentYearTargets.value.length - 1){
    currentYearTargetIndex.value = 0
  }else{
    currentYearTargetIndex.value += 1
  }
}

//我死没死
const getInfo = ()=>{
  //获取信息
  const getResponse = getAmIDead()
  getResponse.then(response=>{
    deadInfo.value = response.data
    deadInfo.value.time = timeCorrect(deadInfo.value.time)
    dead.value = checkAmIDead(deadInfo.value.time)
  })
  if (localStorage.getItem('managerSecret') && localStorage.getItem('managerSecret') === 'nzt100years'){
    manager.value = true
  }
}
const deadInfo = ref('')
const dead = ref(false)
const manager = ref(false)
const showCheck = ref(true)

const cancleCheck = ()=>{
  showCheck.value = false
}
const checkBtn = ()=>{
  const postResult = refreshDeadTime()
  postResult.then(response=>{
    if (response.data){
      message.success('更新成功')
      getInfo()
      showCheck.value = false
    }
  })
}
const openModal = ref(false)
const giftPassword = ref('')
const updateGift = ()=>{
  openModal.value = true
}
const handleUpdateGift = ()=>{
  if (Number(giftPassword.value) === deadInfo.value.amIDead){
    const postResult = refreshDeadGift()
    postResult.then(response=>{
      if (response.data){
        message.success('更新成功')
        jumphelper.jumpToSelect()
      }else{
        message.error('未知错误，请重试')
      }
    })
  }else{
    message.error('密码错误')
  }
}
const downloadFile = ()=>{
  window.location.href = deadInfo.value.giftUrl
}


</script>

<template>
  <div class="plan_container">
    <HomeOutlined style="color: #fff" @click="jumphelper.jumpToSelect()" class="home_icon"/>
    <!--背景-->
    <div style="color: #fff" class="background-container">
      <img :src="currentYearTargets[currentYearTargetIndex].planImg">
      <div></div>
    </div>
    <!--占位-->
    <div class="time-container">
      <a-select @change="handleYearChange" v-model:value="currentYear" style="width: 120px">
        <a-select-option :value="currentYear-1" @click="lastYear">上一年</a-select-option>
        <a-select-option :value="currentYear+1" @click="nextYear">下一年</a-select-option>
      </a-select>
    </div>
    <!--目标卡片-->
    <div class="phone-plan-card-out">
      <LeftOutlined style="font-weight: bold; font-size: 18px; color: #fff" @click="lastTarget"/>
      <div class="phone_plan_card">
        <div class="phone_plan_card_img">
          <img :src="currentYearTargets[currentYearTargetIndex].planImg">
        </div>
        <div class="phone_plan_card_txt">
          <div>
            <h3>{{ currentYear }} {{currentYearTargetIndex+1}}/{{currentYearTargets.length}}</h3>
            <h2>{{ currentYearTargets[currentYearTargetIndex].planName }}</h2>
            <h4>{{ currentYearTargets[currentYearTargetIndex].planCompleted }}</h4>
          </div>
        </div>
      </div>
      <RightOutlined style="font-weight: bold; font-size: 18px; color: #fff" @click="nextTarget"/>
    </div>
    <!--目标细节-->
    <div class="phone-plan-detail">
      {{ currentYearTargets[currentYearTargetIndex].planDetail }}
    </div>
    <!--没死打卡-->
    <div v-if="!dead && manager && showCheck" class="check-container">
      <h3>您似乎还活着，要打卡吗？</h3>
      <div>
        <a-button @click="checkBtn" type="primary">打卡</a-button>
        <a-button @click="cancleCheck">取消</a-button>
      </div>
    </div>
    <!--死了-->
    <div v-if="dead && showCheck" class="dead-container">
      <h3>{{ deadInfo.lastWord }}</h3>
      <h4 v-if="deadInfo.giftStill" @click="updateGift">东西还在</h4>
      <h4 v-else>来晚了，东西已经没了</h4>
      <div>
        <a-button @click="downloadFile" type="primary">下载遗书</a-button>
        <a-button @click="cancleCheck">取消</a-button>
      </div>
    </div>
    <a-modal v-model:open="openModal" title="输入密码" @ok="handleUpdateGift">
      <a-input-number v-model:value="giftPassword" style="width: 78vw"/>
    </a-modal>
  </div>
</template>

<style scoped>
@import "@/css/animation.css";
/*背景*/
.background-container{
  position: fixed;
  left: 0;
  right: 0;
  top: 0;
  bottom: 0;
  display: flex;
  align-items: center;
  justify-content: center;
}
.background-container img{
  min-height: 100%;
  min-width: 100%;
  object-fit: cover;
}
.background-container div{
  position: absolute;
  left: 0;
  right: 0;
  top: 0;
  bottom: 0;
  backdrop-filter: blur(8px);
  background-color: #aaaaaa44;
}
.plan_container{
  display: flex;
  align-items: center;
  flex-direction: column;
  justify-content: space-between;
}
/*选择时间*/
.time-container{
  width: 100%;
  margin-top: 12px;
  display: flex;
  align-items: center;
  justify-content: end;
  padding-right: 20px;
}
:deep(.ant-select-selector){
  border: 1px solid #d7d7d7 !important;
}
/*卡片*/
.phone-plan-card-out{
  width: 100%;
  position: relative;
  height: 500px;
  display: flex;
  align-items: center;
  justify-content: space-around;
  animation: jumpOut-1 .8s 1;
}
.phone_plan_card{
  overflow: hidden;
  width: 86%;
  height: 500px;
  border-radius: 16px;
  background-color: #fff;
  box-shadow: 0 0 16px #00000044;
  position: relative;
}
@keyframes jumpOut-1 {
  0%{
    transform: translate(0, 50%) scale(0.4);
    opacity: 0;
  }
  20%{
    transform: translate(0, 50%) scale(0.4);
    opacity: 0;
  }
}
.phone_plan_card_img{
  position: absolute;
  left: 0;
  top: 0;
  right: 0;
  height: 380px;
  overflow: hidden;
  display: flex;
  align-items: center;
  justify-content: center;
}
.phone_plan_card_img img{
  min-height: 100%;
  min-width: 100%;
  object-fit: cover;
}
.phone_plan_card_txt{
  position: absolute;
  bottom: 0;
  left: 0;
  right: 0;
  top: 380px;
  display: flex;
  justify-content: center;
  align-items: center;
  padding: 0 12px;
}
.phone_plan_card_txt div{
  height: 74%;
  white-space: nowrap;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: space-around;
  color: #666;
}
.phone_plan_card_txt h2{
  margin: 0;
  letter-spacing: 8px;
  font-weight: bold;
  color: #383838;
}
.phone_plan_card_txt h3{
  margin: 0;
  letter-spacing: 4px;
  color: #888;
}
.phone_plan_card_txt h4{
  margin: 0;
  letter-spacing: 4px;
  color: #888;
}
/*完成细节*/
.phone-plan-detail{
  z-index: 999;
  color: #fff;
  letter-spacing: 2px;
  font-size: 16px;
  width: 100%;
  text-align: center;
  display: flex;
  align-items: center;
  justify-content: center;
  background-color: #94abe888;
  padding: 16px;
  animation: bottom-fade-in .8s 1;
}
/*死没死*/
.check-container{
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  width: 320px;
  height: 200px;
  background: #ffffff88;
  backdrop-filter: blur(8px);
  border-radius: 8px;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  animation: jump-out 1s 1;
  z-index: 999;
  box-shadow: 4px 4px 12px #00000022;
}
.check-container h3{
  margin-top: 32px;
}
.check-container div{
  width: 300px;
  margin-top: 24px;
  display: flex;
  align-items: center;
  justify-content: space-around;
}
.dead-container{
  position: fixed;
  left: 0;
  right: 0;
  top: 0;
  bottom: 0;
  backdrop-filter: blur(4px);
  background-color: #00000044;
  z-index: 999;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  padding: 12px 20px;
}
.dead-container h3{
  color: #fff;
  white-space: pre-line;
  font-size: 16px;
  line-height: 22px;
  letter-spacing: 1px;
  width: 100%;
}
.dead-container h4{
  white-space: pre-line;
  color: #eee;
  width: 100%;
  margin: 4px 0;
  text-align: center;
  text-decoration: underline;
  font-size: 14px;
}
.dead-container div{
  width: 100%;
  margin-top: 24px;
  display: flex;
  align-items: center;
  justify-content: space-between;
}
</style>