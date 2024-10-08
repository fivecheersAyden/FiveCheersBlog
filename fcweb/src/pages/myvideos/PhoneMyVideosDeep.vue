<script setup>
import {
  HomeOutlined,
  PlusOutlined
} from '@ant-design/icons-vue';
import {jumphelper} from "@/js/jumphelper";
import NewVideoCom from "@/coms/videos/NewVideoCom.vue";
import {bus} from "vue3-eventbus";
import {onMounted, ref} from "vue";
import {deleteVideoApi, selectVideos} from "@/js/apihelper";
import {message} from "ant-design-vue";
import {timeCorrect} from "@/js/jshelper";

const newVideoShow = ()=>{
  bus.emit('newVideoShow')
  cardShowing.value = true
}
const videoList = ref([])
const currentProps = ref({
  firstIndex: 0,
  length: 3
})
const pushVideos = ()=>{
  const getResults = selectVideos(currentProps.value)
  getResults.then(response=>{
    if (response.data.length > 0){
      for (const datum of response.data) {
        const tmp = datum
        tmp.time = timeCorrect(datum.time)
        if (tmp.videoUrl && tmp.videoUrl.endsWith('.mp4') || tmp.videoUrl.endsWith('.avi') || tmp.videoUrl.endsWith('.mov')){
          tmp.isVideo = true
        }
        videoList.value.push(tmp)
      }
      currentProps.value.firstIndex += currentProps.value.length
    }else{
      message.warn('后面没有了')
    }
  }).catch(e=>{
    console.log(e)
    message.error('查询视频失败')
  })
}
onMounted(()=>{
  if (localStorage.getItem('managerSecret') && localStorage.getItem('managerSecret') === 'nzt100years'){
    isManager.value = true
  }
  pushVideos()
  bus.on('setCardShowingFalse',()=>{
    window.location.reload();
  })
})
const deleteVideo = (videoId)=>{
  const postResult = deleteVideoApi(videoId)
  postResult.then(response=>{
    if (response.data){
      message.success('删除成功')
      videoList.value = videoList.value.filter(item => item.videoId !== videoId)
    }else{
      message.error('删除失败')
      console.log(response)
    }
  }).catch(e=>{
    message.error('删除失败')
    console.log(e)
  })
}
const getOptions = (videoUrl)=>{
  return {
    width: "100%", //播放器宽度
    height: "auto", //播放器高度
    color: "#409eff", //主题色
    title: "", //视频名称
    src: videoUrl, //视频源
    muted: false, //静音
    webFullScreen: false,
    speedRate: ["0.75", "1.0", "1.25", "1.5", "2.0"], //播放倍速
    autoPlay: false, //自动播放
    loop: false, //循环播放
    mirror: false, //镜像画面
    ligthOff: false, //关灯模式
    volume: 0.3, //默认音量大小
    control: true, //是否显示控制
    controlBtns: [
      "audioTrack",
      "quality",
      "speedRate",
      "volume",
      "setting",
      "pip",
      "pageFullScreen",
      "fullScreen",
    ], //显示所有按钮,
  }
}
const downloadFile = (fileUrl)=>{
  window.location.href = fileUrl
}
const cardShowing = ref(false)
const isManager = ref(false)

</script>

<template>
  <div class="videos-background">
    <HomeOutlined @click="jumphelper.jumpToSelect()" class="home_icon"/>
    <h4>- 互联网不是法外之地 -</h4>
    <div class="scroll-container">
      <div v-for="(item, index) in videoList" :key="index" class="video-line">
        <h5>{{ item.time }}</h5>
        <p>{{ item.videoContent }}</p>
        <h5 v-if="isManager" class="delete-btn" @click="deleteVideo(item.videoId)">删除</h5>
        <div v-if="item.isVideo">
          <vue3VideoPlay
              v-if="!cardShowing"
              v-bind="getOptions(item.videoUrl)"
          />
        </div>
        <div v-else>
          <a-button @click="downloadFile(item.videoUrl)">点击下载文件</a-button>
        </div>
      </div>
      <a-button type="text" @click="pushVideos">- 更多 -</a-button>
    </div>
    <div @click="newVideoShow" v-if="!cardShowing && isManager" class="add-container">
      <PlusOutlined />
    </div>
    <NewVideoCom/>
  </div>
</template>

<style scoped>
  .videos-background{
    background-color: #f5f8ff;
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: space-between;
  }
  .videos-background h4{
    font-weight: bold;
    letter-spacing: 4px;
    width: 100%;
    color: #444;
    text-align: center;
    height: 46px;
    display: flex;
    align-items: center;
    justify-content: center;
    animation: right-fade-in .8s 1;
  }
  .scroll-container{
    flex: 1;
    width: 100%;
    background-color: #f6f6f6;
    overflow-x: hidden;
    overflow-y: scroll;
    display: flex;
    flex-direction: column;
    align-items: center;
  }
  .add-container{
    position: fixed;
    right: 16px;
    bottom: 48px;
    width: 48px;
    height: 48px;
    background-color: #f5f8ff;
    border: 1px solid #5471c088;
    border-radius: 24px;
    font-size: 24px;
    display: flex;
    align-items: center;
    justify-content: center;
    color: #5471c0aa;
    cursor: pointer;
    z-index: 999;
  }
  .video-line{
    padding: 8px 8px 12px 8px;
    background-color: #fff;
    margin-bottom: 4px;
    position: relative;
    width: 100%;
  }
  .delete-btn{
    position: absolute;
    right: 8px;
    top: 8px;
    text-decoration: underline;
    color: #666;
    cursor: pointer;
  }
</style>