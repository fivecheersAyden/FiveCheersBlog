<script setup>
import {CloseOutlined, UploadOutlined} from '@ant-design/icons-vue'
import {bus} from "vue3-eventbus";
import {onMounted, ref} from "vue";
import {deleteSpaceApi, selectSpaceApi, updateSpaceApi} from "@/js/apihelper";
import {message} from "ant-design-vue";
import {timeCorrect} from "@/js/jshelper";

const closeSelectCard = ()=>{
  bus.emit('closeSelectCard')
}
onMounted(()=>{
  getSpace()
})
const currentParams = ref({
  startIndex: 0,
  length: 4,
})
const spaces = ref([])
const getSpace = ()=>{
  const getResult = selectSpaceApi(currentParams.value)
  getResult.then(response=>{
    for (const tmpSpace of response.data) {
      tmpSpace.spaceTime = timeCorrect(tmpSpace.spaceTime)
    }
    spaces.value = spaces.value.concat(response.data)
  }).catch(e=>{
    console.log(e)
    message.error('未知错误')
  })
}
const getMore = ()=>{
  currentParams.value.startIndex += currentParams.value.length;
  getSpace()
}
//一条
const deleteImg = (spaceIndex, imgIndex)=>{
  spaces.value[spaceIndex].spaceImgs.splice(imgIndex, 1)
}
const handleUploadImgChange = (info)=>{
  if (info.file.status === 'done') {
    message.success(`文件上传成功，img值已更改`);
    spaces.value[currentUploadImgIndex.value].spaceImgs.push(info.file.response)
  } else if (info.file.status === 'error') {
    message.error(`${info.file.name} 文件上传失败`);
  }
}
const currentUploadImgIndex = ref(null)
const changeCurrentUploadImgIndex = (spaceIndex)=>{
  currentUploadImgIndex.value = spaceIndex
}
const deleteFile = (spaceIndex, fileIndex)=>{
  spaces.value[spaceIndex].spaceFiles.splice(fileIndex, 1)
}
const handleUploadFileChange = (info)=>{
  if (info.file.status === 'done') {
    message.success(`文件上传成功，file值已更改`);
    spaces.value[currentUploadFileIndex.value].spaceFiles.push(info.file.response)
  } else if (info.file.status === 'error') {
    message.error(`${info.file.name} 文件上传失败`);
  }
}
const currentUploadFileIndex = ref(null)
const changeCurrentUploadFileIndex = (spaceIndex)=>{
  currentUploadFileIndex.value = spaceIndex
}
const updateSpace = (spaceIndex)=>{
  const tmpSpace = JSON.parse(JSON.stringify(spaces.value[spaceIndex]));
  tmpSpace.spaceTime = null;
  const postResult = updateSpaceApi(tmpSpace)
  postResult.then(response=>{
    if (response.data){
      message.success('更改成功')
    }else{
      message.error('更改失败')
    }
  }).catch(e=>{
    console.log(e)
    message.error('未知错误')
  })
}
const deleteSpace = (spaceIndex)=>{
  const spaceId = spaces.value[spaceIndex].spaceId
  const postResult = deleteSpaceApi(spaceId)
  postResult.then(response=>{
    if (response.data){
      message.success('删除成功')
      spaces.value.splice(spaceIndex, 1)
    }else{
      message.error('删除失败')
    }
  }).catch(e=>{
    console.log(e)
    message.error('未知错误')
  })
}
const downloadFile = (spaceIndex, fileIndex)=>{
  const fileUrl = spaces.value[spaceIndex].spaceFiles[fileIndex]
  window.location.href = fileUrl
}

</script>

<template>
  <div class="select_com_background">
    <div @click="closeSelectCard()" style="position: fixed; left: 0; right: 0; top: 0; bottom: 0"></div>
    <h2>- 编辑说说 -</h2>
    <div class="select_com_card">
      <div v-for="(space, spaceIndex) in spaces" :key="spaceIndex" class="space_line">
        <h4>{{ space.spaceTime }}</h4>
        <a-textarea :auto-size="{ minRows: 2, maxRows: 99 }" v-model:value="space.spaceContent"/>
        <!--图片-->
        <h5>图片</h5>
        <div v-if="space.spaceImgs[0] !== ''" class="imgs_container">
          <div v-for="(item, imgIndex) in space.spaceImgs" :key="imgIndex">
            <div style="position: absolute; left: 0; right: 0; top: 0; bottom: 0; background-color: #aaaaaa11"></div>
            <CloseOutlined @click="deleteImg(spaceIndex, imgIndex)" style="position: absolute; right: 8px; top: 8px"/>
            <img :src="item" alt="">
          </div>
        </div>
        <a-upload
            action="http://www.fivecheers.com:1001/file/defaultUpLoadFile"
            @change="handleUploadImgChange">
          <a-button @click="changeCurrentUploadImgIndex(spaceIndex)" style="margin-top: 8px">
            <upload-outlined></upload-outlined>
            上传图片
          </a-button>
        </a-upload>
        <!--文件-->
        <h5>文件</h5>
        <div v-if="space.spaceFiles[0] !== ''" class="files_container">
          <div v-for="(item, fileIndex) in space.spaceFiles" :key="fileIndex">
            <a-button @click="downloadFile(spaceIndex, fileIndex)">点击下载</a-button>
            <CloseOutlined @click="deleteFile(spaceIndex, fileIndex)"/>
          </div>
        </div>
        <a-upload
            action="http://www.fivecheers.com:1001/file/defaultUpLoadFile"
            @change="handleUploadFileChange">
          <a-button @click="changeCurrentUploadFileIndex(spaceIndex)" style="margin-top: 8px">
            <upload-outlined></upload-outlined>
            上传文件
          </a-button>
        </a-upload>
        <!--按钮-->
        <div class="btn_container">
          <a-button type="primary" @click="updateSpace(spaceIndex)">确定</a-button>
          <a-button type="primary" danger @click="deleteSpace(spaceIndex)">删除</a-button>
        </div>
        <a-divider/>
      </div>
      <!--查看更多-->
      <div class="btn_container">
        <a-button type="text" @click="getMore">查看更多</a-button>
      </div>
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
  width: calc(100% - 12px);
  max-width: 600px;
}
.space_line h4{
  margin: 8px;
  letter-spacing: 2px;
  font-weight: bold;
}
.space_line h5{
  margin: 12px 8px 4px 8px;
  letter-spacing: 2px;
  font-weight: bold;
}
.imgs_container{
  display: flex;
  flex-wrap: wrap;
  justify-content: space-around;
  margin-top: 12px;
}
.imgs_container div{
  width: 36vw;
  height: 36vw;
  overflow: hidden;
  display: flex;
  align-items: center;
  justify-content: center;
  position: relative;
  margin: 8px;
}
.imgs_container img{
  min-width: 100%;
  min-height: 100%;
  object-fit: cover;
}
.files_container div{
  margin: 8px 0;
  display: flex;
  align-items: center;
  justify-content: space-between;
}
.btn_container{
  margin-top: 16px;
  display: flex;
  align-items: center;
  justify-content: space-around;
}
</style>
