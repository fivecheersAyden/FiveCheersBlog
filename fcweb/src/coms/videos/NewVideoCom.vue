<script setup>

import {UploadOutlined} from "@ant-design/icons-vue";
import {message} from "ant-design-vue";
import {onMounted, ref} from "vue";
import {bus} from "vue3-eventbus";
import {insertNewVideo} from "@/js/apihelper";

onMounted(()=>{
  bus.on('newVideoShow', ()=>{
    newVideoShow.value = true;
  })
})
const newVideoHide = ()=>{
  newVideoShow.value = false
  bus.emit('setCardShowingFalse')
}
const newVideoShow = ref(false)
const newVideo = ref({
  videoUrl:null,
  videoContent: null
})
const handleUploadChange = (info)=>{
  if (info.file.status === 'done') {
    message.success(`文件上传成功`);
    newVideo.value.videoUrl = info.file.response
  } else if (info.file.status === 'error') {
    message.error(`${info.file.name} 文件上传失败`);
  }
}
const insertNewVideoSure = ()=>{
  if (newVideo.value.videoContent && newVideo.value.videoUrl){
    const postResult = insertNewVideo(newVideo.value)
    postResult.then(response=>{
      if (response.data){
        message.success('添加成功')
        newVideoHide()
      }else{
        message.error('添加失败')
        console.log(response)
      }
    }).catch(e=>{
      console.log(e)
      message.error('添加失败')
    })
  }else {
    message.warn('请先上传文件并填入简介')
  }
}

</script>

<template>
  <transition name="fade">
    <div v-if="newVideoShow" class="new-video-container">
      <div class="card-container">
        <a-upload
            action="http://www.fivecheers.com:1001/file/defaultUpLoadFile"
            @change="handleUploadChange">
          <a-button style="margin-top: 8px">
            <upload-outlined></upload-outlined>
            上传单个视频、图片或文件
          </a-button>
        </a-upload>
        <a-textarea placeholder="输入简述" style="width: 240px" :auto-size="{ minRows: 3, maxRows: 5 }" v-model:value="newVideo.videoContent"/>
        <div class="btns-line">
          <a-button type="primary" @click="insertNewVideoSure">确定</a-button>
          <a-button @click="newVideoHide">取消</a-button>
        </div>
      </div>
    </div>
  </transition>
</template>

<style scoped>
  .new-video-container{
    position: fixed;
    left: 0;
    top: 0;
    right: 0;
    bottom: 0;
    background-color: #00000011;
    backdrop-filter: blur(8px);
  }
  .card-container{
    position: fixed;
    top: 60px;
    left: 50%;
    transform: translateX(-50%);
    width: 320px;
    height: 320px;
    box-shadow: 0 0 12px #00000044;
    background-color: #ffffff;
    backdrop-filter: blur(8px);
    border-radius: 8px;
    animation: topDropAni .8s 1;
    display: flex;
    align-items: center;
    justify-content: space-around;
    flex-direction: column;
    padding: 28px 20px 20px 20px;
  }
  .btns-line{
    width: 100%;
    display: flex;
    align-items: center;
    justify-content: space-around;
  }
</style>
