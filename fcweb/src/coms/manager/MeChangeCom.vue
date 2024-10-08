<script setup>
import {UploadOutlined} from '@ant-design/icons-vue'
import {bus} from "vue3-eventbus";
import {computed, onMounted, reactive} from "vue";
import {message} from "ant-design-vue";
import {changeMeList, getMeList} from "@/js/apihelper";

const closeSelectCard = ()=>{
  bus.emit('closeSelectCard')
}
onMounted(()=>{
  const listResult = getMeList()
  listResult.then(response=>{
    Object.assign(list, response.data);
  }).catch(e=>{
    console.log(e)
    message.error('请求出错')
  })
})
const objectKeys = computed(() => Object.keys(list));
const list = reactive({})
const changeKey = (type, value)=>{
  const changeResult = changeMeList({
    type:type,
    value:value
  })
  changeResult.then(response=>{
    if (response.data){
      message.success('更改成功')
    }else{
      message.error('更改失败')
    }
  }).catch(e=>{
    console.log(e)
    message.error('请求出错')
  })
}
const handleUploadChange = (info)=>{
  if (info.file.status === 'done') {
    message.success(`文件上传成功，photo值已更改`);
    list.photo = info.file.response
  } else if (info.file.status === 'error') {
    message.error(`${info.file.name} 文件上传失败`);
  }
}
</script>

<template>
  <div class="select_com_background">
    <div @click="closeSelectCard()" style="position: fixed; left: 0; right: 0; top: 0; bottom: 0"></div>
    <h2>- 修改我的资料 -</h2>
    <div class="select_com_card">
      <div class="select_com_line" v-for="key in objectKeys" :key="key">
        <div>
          <h3>{{ key }}</h3>
          <a-button @click="changeKey(key, list[key])" type="primary">更改</a-button>
        </div>
        <div>
          <a-input v-model:value="list[key]"></a-input>
        </div>
        <a-divider/>
      </div>
      <a-upload
          action="http://www.fivecheers.com:1001/file/defaultUpLoadFile"
          @change="handleUploadChange">
        <a-button>
          <upload-outlined></upload-outlined>
          上传图片
        </a-button>
      </a-upload>
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
  width: calc(100% - 12px);
  transform: translate(-50%, -50%);
  max-height: 580px;
  height: calc(100% - 88px);
  background: #fff;
  border-radius: 8px;
  padding: 16px 12px;
  overflow-y: scroll;
  overflow-x: hidden;
  max-width: 600px;
}
.select_com_line h3{
  font-weight: bold;
  margin: 0;
}
.select_com_line div:nth-child(1){
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 12px 8px;
}
.select_com_line div:nth-child(2){

}

</style>
