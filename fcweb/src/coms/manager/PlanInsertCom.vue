<script setup>
import {UploadOutlined} from '@ant-design/icons-vue'
import {bus} from "vue3-eventbus";
import {ref} from "vue";
import {message} from "ant-design-vue";
import {addNewPlan} from "@/js/apihelper";

const closeSelectCard = ()=>{
  bus.emit('closeSelectCard')
}
const plan = ref({
  year:null,
  planName:null,
  planDetail:null,
  planCompleted:null,
  planImg:null
})
const handleUploadChange = (info)=>{
  if (info.file.status === 'done') {
    message.success(`文件上传成功，photo值已更改`);
    plan.value.planImg = info.file.response
  } else if (info.file.status === 'error') {
    message.error(`${info.file.name} 文件上传失败`);
  }
}
const insertNewPlan = ()=>{
  if (Object.values(plan.value).every(value => value !== null && value !== undefined)){
    const postResult = addNewPlan(plan.value)
    postResult.then(response=>{
      if (response.data){
        message.success('增加成功')
        plan.value = {
          year:null,
          planName:null,
          planDetail:null,
          planCompleted:null,
          planImg:null
        }
      }else{
        message.error('增加失败')
      }
    }).catch(e=>{
      console.log(e)
      message.error('未知错误')
    })
  }else{
    message.warn('请输入完整信息')
  }
}
</script>

<template>
  <div class="select_com_background">
    <h2>- 新增挑战 -</h2>
    <div @click="closeSelectCard()" style="position: fixed; left: 0; right: 0; top: 0; bottom: 0"></div>
    <div class="select_com_card">
      <div class="select_com_line">
        <h3>年份</h3>
        <a-input-number style="width: 180px" v-model:value="plan.year"/>
      </div>
      <a-divider/>
      <div class="select_com_line">
        <h3>挑战名</h3>
        <a-input style="width: 180px" v-model:value="plan.planName"/>
      </div>
      <div class="select_com_line">
        <h3>详情</h3>
        <a-input style="width: 180px" v-model:value="plan.planDetail"/>
      </div>
      <div class="select_com_line">
        <h3>进度</h3>
        <a-input style="width: 180px" v-model:value="plan.planCompleted"/>
      </div>
      <a-divider/>
      <div class="select_com_line">
        <h3>图片</h3>
        <a-input style="width: 180px" v-model:value="plan.planImg"/>
      </div>
      <a-upload
          action="http://www.fivecheers.com:1001/file/defaultUpLoadFile"
          @change="handleUploadChange">
        <a-button style="margin-top: 8px">
          <upload-outlined></upload-outlined>
          上传图片
        </a-button>
      </a-upload>
      <a-divider/>
      <a-button @click="insertNewPlan" type="primary" size="large" class="sure_btn">确定</a-button>
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
  max-width: 600px;
  width: calc(100% - 12px);
}
.select_com_line{
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 12px 8px;
}
.select_com_line h3{
  font-weight: bold;
  margin: 0;
}
.sure_btn{
  position: absolute;
  bottom: 32px;
  left: 50%;
  transform: translateX(-50%);
  width: 180px;
}

</style>
