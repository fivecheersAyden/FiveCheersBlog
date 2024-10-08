<script setup>
import {UploadOutlined} from '@ant-design/icons-vue'
import {bus} from "vue3-eventbus";
import {message} from "ant-design-vue";
import {ref} from "vue";
import {insertSpaceApi} from "@/js/apihelper";

const closeSelectCard = ()=>{
  bus.emit('closeSelectCard')
}
const newSpace = ref({
  spaceContent: null,
  spaceImgs: '',
  spaceFiles: ''
})
const handleImgUploadChange = (info)=>{
  if (info.file.status === 'done') {
    message.success(`文件上传成功，imgs值已更改`);
    newSpace.value.spaceImgs += info.file.response + ','
  } else if (info.file.status === 'error') {
    message.error(`${info.file.name} 文件上传失败`);
  }
}
const handleFileUploadChange = (info)=>{
  if (info.file.status === 'done') {
    message.success(`文件上传成功，files值已更改`);
    newSpace.value.spaceFiles += info.file.response + ','
  } else if (info.file.status === 'error') {
    message.error(`${info.file.name} 文件上传失败`);
  }
}

const insertFuc = ()=>{
  const postResult = insertSpaceApi(newSpace.value)
  postResult.then(response=>{
    if (response.data){
      message.success('上传成功')
    }else{
      message.error('上传失败')
    }
  }).catch(e=>{
    console.log(e)
    message.error('响应错误')
  })
}
</script>

<template>
  <div class="select_com_background">
    <div @click="closeSelectCard()" style="position: fixed; left: 0; right: 0; top: 0; bottom: 0"></div>
    <h2>- 发布说说 -</h2>
    <div class="select_com_card">
      <div>
        <h3>内容</h3>
        <a-textarea :auto-size="{ minRows: 2, maxRows: 99 }" v-model:value="newSpace.spaceContent"/>
      </div>
      <a-divider/>
      <div>
        <h3>图片</h3>
        <a-upload
            action="http://www.fivecheers.com:1001/file/defaultUpLoadFile"
            @change="handleImgUploadChange">
          <a-button style="margin-top: 8px">
            <upload-outlined></upload-outlined>
            上传图片
          </a-button>
        </a-upload>
      </div>
      <a-divider/>
      <div>
        <h3>文件</h3>
        <a-upload
            action="http://www.fivecheers.com:1001/file/defaultUpLoadFile"
            @change="handleFileUploadChange">
          <a-button style="margin-top: 8px">
            <upload-outlined></upload-outlined>
            上传文件
          </a-button>
        </a-upload>
      </div>
      <a-divider/>
      <a-button @click="insertFuc" class="sure_btn" type="primary" size="large">确定</a-button>
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
.select_com_line h3{
  font-weight: bold;
}
.select_com_card div{
  margin: 12px 8px;
}
.sure_btn{
  position: absolute;
  left: 50%;
  transform: translateX(-50%);
  width: 160px;
  margin-top: 12px;
}
</style>
