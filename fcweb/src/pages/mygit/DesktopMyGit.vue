<script setup>
import {
  HomeOutlined,
  PlusOutlined
} from '@ant-design/icons-vue';
import {jumphelper} from "@/js/jumphelper";
import {bus} from "vue3-eventbus";
import {onMounted, ref} from "vue";
import {deleteMyGit, selectMyGit} from "@/js/apihelper";
import {message} from "ant-design-vue";
import {timeCorrect} from "@/js/jshelper";
import NewGitCom from "@/coms/mygit/NewGitCom.vue";
// import DefaultBackground from "@/coms/all/DefaultBackground.vue";

const newVideoShow = ()=>{
  bus.emit('newVideoShow')
  cardShowing.value = true
}
const videoList = ref([])
const currentProps = ref({
  firstIndex: 0,
  length: 2
})
const pushVideos = async () => {
  try {
    let scrollElem = scrollDiv.value;
    var currentTop = scrollElem.scrollTop;
    const getResults = selectMyGit(currentProps.value);
    const response = await getResults;
    if (response.data.length > 0) {
      console.log(response);
      for (const datum of response.data) {
        const tmp = datum;
        tmp.time = timeCorrect(datum.time);
        videoList.value.push(tmp);
      }
      currentProps.value.firstIndex += currentProps.value.length;

      // 等待数据push完成后再滚动
      await new Promise(resolve => setTimeout(resolve, 100));
      scrollElem.scrollTo({ top: currentTop, behavior: 'auto' });
    } else {
      message.warn('后面没有了');
    }
  } catch (error) {
    console.log(error);
    message.error('查询视频失败');
  }
};

onMounted(()=>{
  if (localStorage.getItem('managerSecret') && localStorage.getItem('managerSecret') === 'nzt100years'){
    isManager.value = true
  }
  pushVideos()
  bus.on('setCardShowingFalse',()=>{
    window.location.reload();
  })
})
const deleteMG = (gitId)=>{
  const postResult = deleteMyGit(gitId)
  postResult.then(response=>{
    if (response.data){
      message.success('删除成功')
      videoList.value = videoList.value.filter(item => item.gitId !== gitId)
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
/*const downloadFile = (fileUrl)=>{
  window.location.href = fileUrl
}*/
const cardShowing = ref(false)
const isManager = ref(false)
const scrollDiv = ref()

</script>

<template>
  <div class="videos-background">
    <HomeOutlined style="color: #fff" @click="jumphelper.jumpToSelect()" class="home_icon"/>
    <h4>- 作品集 -</h4>
    <div class="de-background"></div>
    <!-- <DefaultBackground/> -->
    <div ref="scrollDiv" class="scroll-container">
      <div v-for="(item, index) in videoList" :key="index" class="video-line">
        <div class="left-container">
          <vue3VideoPlay
              v-if="!cardShowing"
              v-bind="getOptions(item.gitVideo)"
              :preload="false"
          />
        </div>
        <div class="right-container">
          <h5>{{ item.time }}</h5>
          <p>{{ item.gitContent }}</p>
          <div class="a-container">
            <a v-if="item.gitUrl" :href="item.gitUrl">项目地址</a>
            <a v-if="item.runUrl" :href="item.runUrl">运行地址</a>
          </div>
          <h5 class="delete-btn" @click="deleteMG(item.gitId)" v-if="isManager">删除</h5>
        </div>
      </div>
      <a-button style="color: #fff;" type="text" @click="pushVideos">- 更多 -</a-button>
    </div>
    <div @click="newVideoShow" v-if="!cardShowing && isManager" class="add-container">
      <PlusOutlined />
    </div>
    <NewGitCom/>
  </div>
</template>

<style scoped>
.videos-background{
  background-color: transparent;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: space-between;
}
.videos-background h4{
  font-weight: bold;
  letter-spacing: 4px;
  width: 100%;
  color: #fff;
  z-index: 9;
  text-align: center;
  height: 42px;
  margin-top: 4px;
  display: flex;
  align-items: center;
  justify-content: center;
  animation: right-fade-in .8s 1;
  border-bottom: 1px solid #fff;
}
.de-background{
  position: fixed;
  left: 0;
  right: 0;
  top: 0;
  bottom: 0;
  background: linear-gradient(to right bottom, #10286b, #48136b);
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
  background: #ffffffaa;
  border-radius: 8px;
  margin-bottom: 12px;
  position: relative;
  white-space: pre-line;
  display: flex;
  justify-content: space-around;
  backdrop-filter: blur(4px);
  width: calc(100% - 16px);
  border: #ddd 1px solid;
  box-shadow: 4px 4px 10px #ffffff11;
}
.left-container{
  flex: 1;
  padding: 12px;
}
.right-container{
  width: 360px;
  padding: 12px;
}
.delete-btn{
  position: absolute;
  right: 8px;
  top: 8px;
  text-decoration: underline;
  color: #666;
  cursor: pointer;
}
.a-container{
  margin: -8px 0 8px;
}
.a-container a{
  margin-right: 12px;
  color: #5471c0;
}
.a-container a:visited{
  color: #272396;
}
</style>
