<script setup>
import {LeftOutlined, RightOutlined, UploadOutlined} from '@ant-design/icons-vue'
import {bus} from "vue3-eventbus";
import {onMounted, ref} from "vue";
import {message} from "ant-design-vue";
import {changePlanApi, getPlanAllPlans} from "@/js/apihelper";

const closeSelectCard = ()=>{
  bus.emit('closeSelectCard')
}
onMounted(()=>{
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
})
const getCurrentYearTargets = ()=>{
  currentYearTargets.value = allYearTargets.value.filter(item => item.year === currentYear.value)
}
const currentYearTargets = ref([])
const allYearTargets = ref(null)
const currentYear = ref(null)
const planId = ref(null)
const handleUploadChange = (info)=>{
  if (info.file.status === 'done') {
    currentYearTargets.value.filter(item => item.planId === Number(planId.value)).forEach(item => {
      item.planImg = info.file.response
      message.success(`文件上传成功，photo值已更改`);
    })
  } else if (info.file.status === 'error') {
    message.error(`${info.file.name} 文件上传失败`);
  }
}
const changePlan = ()=>{
  currentYearTargets.value.filter(item => item.planId === Number(planId.value)).forEach(item => {
    const postResult = changePlanApi(item)
    postResult.then(response=>{
      if (response.data){
        message.success('修改成功')
      }else{
        message.error('修改失败')
      }
    }).catch(e=>{
      console.log(e)
      message.error('请求失败')
    })
  })
}
const lastYear = ()=>{
  planId.value = null
  currentYear.value -= 1
  getCurrentYearTargets()
  if (currentYearTargets.value.length <1){
    nextYear()
    message.warn('前面没有了')
  }
}
const nextYear = ()=>{
  planId.value = null
  currentYear.value += 1
  getCurrentYearTargets()
  if (currentYearTargets.value.length <1){
    lastYear()
    message.warn('后面没有了')
  }
}
</script>

<template>
  <div class="select_com_background">
    <h2>- 编辑挑战 -</h2>
    <div @click="closeSelectCard()" style="position: fixed; left: 0; right: 0; top: 0; bottom: 0"></div>
    <div class="select_com_card">
      <div class="select_card_title">
        <LeftOutlined @click="lastYear"/>
        <h3 style="margin-bottom: 0">{{ currentYear }}</h3>
        <RightOutlined @click="nextYear"/>
      </div>
      <a-collapse v-model:activeKey="planId" accordion>
        <a-collapse-panel style="position: relative" v-for="item in currentYearTargets" :key="item.planId" :header="item.planName">
          <div class="select_panel_img_container">
            <img :src="item.planImg">
            <div></div>
          </div>
          <div class="select_com_line">
            <h3>名字：</h3>
            <a-input style="width: 180px" v-model:value="item.planName"/>
          </div>
          <div class="select_com_line">
            <h3>年份：</h3>
            <a-input style="width: 180px" v-model:value="item.year"/>
          </div>
          <div class="select_com_line">
            <h3>细节：</h3>
            <a-input style="width: 180px" v-model:value="item.planDetail"/>
          </div>
          <div class="select_com_line">
            <h3>进度：</h3>
            <a-input style="width: 180px" v-model:value="item.planCompleted"/>
          </div>
          <div class="select_com_line">
            <h3>图片：</h3>
            <a-input style="width: 180px" v-model:value="item.planImg"/>
          </div>
          <a-upload
              action="http://www.fivecheers.com:1001/file/defaultUpLoadFile"
              @change="handleUploadChange">
            <a-button style="margin-top: 8px">
              <upload-outlined></upload-outlined>
              上传图片
            </a-button>
          </a-upload>
          <div class="select_com_line_btn">
            <a-button @click="changePlan" type="primary" size="large">确定</a-button>
          </div>
        </a-collapse-panel>
      </a-collapse>
    </div>
  </div>
</template>

<style scoped>
.select_com_background{
  position: fixed;
  left: 0;
  right: 0;
  top: 0;
  bottom: 0;
  background: #00000044;
  animation: fade-in .5s 1;
}
.select_com_card{
  position: absolute;
  left: 50%;
  top: 50%;
  transform: translate(-50%, -50%);
  max-height: 580px;
  height: calc(100% - 88px);
  background: #fff;
  border-radius: 8px;
  padding: 16px 12px;
  overflow-y: scroll;
  overflow-x: hidden;
  display: flex;
  width: calc(100% - 12px);
  flex-direction: column;
  max-width: 600px;
}
.select_com_background h2{
  position: absolute;
  top: calc(25vh - 145px);
  left: 50%;
  transform: translateX(-50%);
  color: white;
  letter-spacing: 6px;
  font-size: 20px;
  white-space: nowrap;
}
.select_card_title{
  height: 36px;
  display: flex;
  align-items: center;
  justify-content: space-around;
  margin-bottom: 12px;
}
.select_com_line{
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 4px 0;
}
.select_com_line h3{
  font-weight: bold;
  margin: 0;
  z-index: 999;
}
.select_panel_img_container{
  position: absolute;
  left: 0;
  right: 0;
  top: 0;
  bottom: 0;
  background-color: #888;
  overflow: hidden;
  display: flex;
  justify-content: center;
  align-items: center;
}
.select_panel_img_container img{
  min-height: 100%;
  min-width: 100%;
}
.select_panel_img_container div{
  background-color: #eeeeee88;
  position: absolute;
  left: 0;
  right: 0;
  top: 0;
  bottom: 0;
  backdrop-filter: blur(4px);
}
::v-deep .ant-collapse-header{
  z-index: 999;
}
.select_com_line_btn{
  display: flex;
  align-items: center;
  justify-content: center;
  padding: 16px 0 8px 0;
}
.select_com_line_btn button{
  width: 160px;
}

</style>
