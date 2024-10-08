<script setup>
import {
  HomeOutlined, LikeOutlined,
} from '@ant-design/icons-vue';
import {jumphelper} from "@/js/jumphelper";
import {onMounted, ref} from "vue";
import {likeSpaceApi, postLog, selectSpaceApi} from "@/js/apihelper";
import {timeCorrect} from "@/js/jshelper";
import {message} from "ant-design-vue";
import {bus} from "vue3-eventbus";
import ImgCom from "@/coms/all/ImgCom.vue";
onMounted(()=>{
  getSpace()
  getLikedData()
  message.info('欢迎访问我的空间:D')
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
    if (response.data.length>0){
      spaces.value = spaces.value.concat(response.data)
    }else{
      message.warn('后面没有了，更早的请看旧版于眉顶部“我的空间”')
    }

  }).catch(e=>{
    console.log(e)
    message.error('未知错误')
  })
}
const showSpaceImg = (imgUrl)=>{
  bus.emit('showImg',imgUrl)
}
const downloadFile = (spaceIndex, fileIndex)=>{
  const fileUrl = spaces.value[spaceIndex].spaceFiles[fileIndex]
  window.location.href = fileUrl
}
const likeSpace = (spaceId, spaceIndex)=>{
  if (beforeLike(spaceId)){
    const postResult = likeSpaceApi(spaceId)
    postResult.then(response=>{
      if (response.data){
        saveLikedData()
        message.success('谢谢喜欢:D')
        postLog('空间点赞，spaceId为'+spaceId)
        spaces.value[spaceIndex].likeNum += 1
      }else {
        message.error('点赞失败')
      }
    }).catch(e=>{
      message.error('未知错误')
      console.log(e)
    })
  }else{
    message.warn('每天只能给每条说说点10个赞')
  }

}
const getMore = ()=>{
  currentParams.value.startIndex += currentParams.value.length;
  getSpace()
}

const likedData = ref([])
const getLikedData = ()=>{
  if (localStorage.getItem('likedData')){
    likedData.value = JSON.parse(localStorage.getItem('likedData'))
  }else{
    localStorage.setItem('likedData','[]')
    likedData.value = JSON.parse(localStorage.getItem('likedData'))
  }
}
const saveLikedData = ()=>{
  const likedDataString = JSON.stringify(likedData.value)
  localStorage.setItem('likedData', likedDataString)
}
const beforeLike = (spaceId)=>{
  const filteredArray = likedData.value.filter(item => item.spaceId === spaceId);
  console.log(filteredArray)
  if (filteredArray.length>0){
    for (const filteredArrayElement of filteredArray) {
      if (filteredArrayElement.time === getNowTime() && filteredArrayElement.num >= 10){
        return false
      }else if (filteredArrayElement.time !== getNowTime()){
        filteredArrayElement.time = getNowTime()
        filteredArrayElement.num = 1
        return true
      }else{
        filteredArrayElement.num += 1
        return true
      }
    }
  }else{
    likedData.value.push({
      spaceId: spaceId,
      userId: localStorage.getItem('UUID'),
      time: getNowTime(),
      num: 1
    })
    return true
  }
  return false
}
const getNowTime = ()=>{
  const date = new Date()
  const year = date.getFullYear()
  const month = String(date.getMonth() + 1).padStart(2,'0')
  const day = String(date.getDate()).padStart(2, '0')
  const nowTime = `${year}-${month}-${day}`
  return nowTime
}


</script>

<template>
  <div class="space_background">
    <HomeOutlined @click="jumphelper.jumpToSelect()" class="home_icon"/>
    <div>
      <div v-for="(space, spaceIndex) in spaces" :key="spaceIndex" class="space_line">
        <!--背景-->
        <div class="space-line-background">
          <img src="@/assets/bk.png" alt="">
          <img :src="space.spaceImgs[0]">
          <div></div>
        </div>
        <!--时间-->
        <div class="time_container">
          <h3>{{ space.spaceTime }}</h3>
        </div>
        <!--内容-->
        <div class="content_container">
          <p>{{ space.spaceContent }}</p>
          <div v-if="space.spaceImgs[0] !== ''" class="imgs_container">
            <div v-for="(item, imgIndex) in space.spaceImgs" :key="imgIndex">
              <img @click="showSpaceImg(item)" :src="item" alt="">
            </div>
          </div>
          <div v-if="space.spaceFiles[0] !== ''" class="files_container">
            <div v-for="(item, fileIndex) in space.spaceFiles" :key="fileIndex">
              <a-button @click="downloadFile(spaceIndex, fileIndex)">点击下载文件 {{fileIndex + 1}} </a-button>
            </div>
          </div>
          <div class="like_container">
            <LikeOutlined class="like" @click="likeSpace(space.spaceId, spaceIndex)"/>
            {{ space.likeNum }}
          </div>
        </div>
      </div>
      <!--查看更多-->
      <div class="btn_container">
        <a-button type="text" @click="getMore">- 查看更多 -</a-button>
      </div>
    </div>
    <ImgCom/>
  </div>
</template>

<style scoped>
  .space_background{
    background: linear-gradient(to right bottom, #fdfdfd, #f3f6fc);
    overflow-y: scroll;
    overflow-x: hidden;
  }
  .space_line{
    display: flex;
    align-items: flex-start;
    justify-content: space-between;
    margin: 0 52px;
    height: auto;
    position: relative;
  }
  .space_line .space-line-background{
    z-index: -1;
    position: absolute;
    width: calc(100% + 104px);
    left: -52px;
    top: 50%;
    transform: translateY(-50%);
    height: 100%;
    display: flex;
    align-items: center;
    justify-content: center;
    overflow: hidden
  }
  .space_line .space-line-background img{
    position: absolute;
    object-fit: cover;
    min-height: 100%;
    min-width: 100%;
  }
  .space_line .space-line-background div{
    position: absolute;
    height: 100%;
    width: 100%;
    background-color: #E7E1E188;
    backdrop-filter: blur(4px);
  }
  .time_container{
    width: 128px;
    text-align: right;
    line-height: 28px;
    padding-right: 12px;
    letter-spacing: 2px;
    margin-top: 4px;
    animation: right-fade-in 1s 1;
  }
  .content_container{
    flex: 1;
    position: relative;
    border-left: #6767dc 2px solid;
    min-height: 80px;
    padding: 12px 0 32px 12px;
    animation: top-fade-in 1s 1;
    white-space: pre-line;
  }
  .content_container p{
    padding: 0 0 0 8px;
  }
  .imgs_container{
    display: flex;
    flex-wrap: wrap;
  }
  .imgs_container div{
    min-width: 200px;
    min-height: 150px;
    width: calc(32vw - 100px);
    height: calc(24vw - 75px);
    overflow: hidden;
    display: flex;
    align-items: center;
    justify-content: center;
    position: relative;
    margin: 8px;
    box-shadow: 0 0 4px #00000011;
    border-radius: 4px;
    transition: .4s;
  }
  .imgs_container img{
    min-width: 100%;
    min-height: 100%;
    object-fit: cover;
    cursor: pointer;
    box-shadow: 2px 2px 8px #00000044;
  }
  .imgs_container div:hover{
    box-shadow: 0 0 8px #00000022;
    transition: .4s;
  }
  .files_container{
    display: flex;
    flex-wrap: wrap;
  }
  .files_container button{
    margin: 4px 0 12px 8px;
  }
  .like_container{
    margin: 0 8px 0 8px;
    text-align: right;
  }
  .like{
    transition: .2s;
  }
  .like:active{
    transition: .2s;
    transform: scale(1.2);
  }
  .btn_container{
    margin-bottom: 12px;
    display: flex;
    align-items: center;
    justify-content: space-around;
    background: linear-gradient(to bottom, #f2efef, #fff);
  }



</style>
