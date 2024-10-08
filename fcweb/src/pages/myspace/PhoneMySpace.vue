<script setup>
import {
  HomeOutlined,
  LikeOutlined,
  PlusOutlined
} from '@ant-design/icons-vue';
import SpaceInsertCom from '@/coms/manager/SpaceInsertCom.vue';
import SpaceUpdateCom from '@/coms/manager/SpaceUpdateCom.vue';
import {jumphelper} from "@/js/jumphelper";
import {onMounted, ref} from "vue";
import {likeSpaceApi, postLog, selectSpaceApi} from "@/js/apihelper";
import {checkManager, timeCorrect} from "@/js/jshelper";
import {message} from "ant-design-vue";
import {bus} from "vue3-eventbus";
import ImgCom from "@/coms/all/ImgCom.vue";
onMounted(()=>{
  getSpace()
  getLikedData()
  bus.on('closeSelectCard', ()=>{
    open.value = false
    cardShowing.value = 0
    spaces.value = []
    getSpace()
  })
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
const showSpaceImg = (imgUrl)=>{
   bus.emit('showImg',imgUrl)
}
/**
 * 管理
 */
 const open = ref(false)
 const newSpaceShow = ()=>{
  open.value = true
 }
 const cardShowing = ref(0)
 const insertSpace = ()=>{
  cardShowing.value = 1;
  open.value = false
 }
 const updateSpace = ()=>{
  cardShowing.value = 2;
  open.value = false
 }
</script>

<template>
  <div class="space_background">
    <HomeOutlined @click="jumphelper.jumpToSelect()" class="home_icon"/>
    <h4>- 东坡藏群山 -</h4>
    <div class="space_line_container">
      <div v-for="(space, spaceIndex) in spaces" :key="spaceIndex" class="space_line">
        <h5>{{ space.spaceTime }}</h5>
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
      <!--查看更多-->
      <div class="btn_container">
        <a-button type="text" @click="getMore">- 查看更多 -</a-button>
      </div>
    </div>
    <ImgCom/>
    <!--增加-->
    <div @click="newSpaceShow" v-if="!cardShowing && checkManager()" class="add-container">
      <PlusOutlined />
    </div>
    <a-modal v-model:open="open" title="操作">
      <template #footer>
        <a-button size="small" key="space" @click="insertSpace">新增</a-button>
        <a-button size="small" key="submit" @click="updateSpace">更改</a-button>
      </template>
    </a-modal>
    <transition name="fade">
      <SpaceInsertCom v-if="cardShowing === 1"/>
    </transition>
    <transition name="fade">
      <SpaceUpdateCom v-if="cardShowing === 2"/>
    </transition>
  </div>
</template>

<style scoped>
  .space_background{
    background-color: #f5f8ff;
  }
  .space_background h4{
    font-weight: bold;
    letter-spacing: 4px;
    width: 100%;
    color: #444;
    text-align: center;
    height: 46px;
    display: flex;
    align-items: center;
    justify-content: center;
    animation: top-fade-in .8s 1;
  }
  .space_line_container{
    position: absolute;
    top: 46px;
    left: 0;
    right: 0;
    bottom: 0;
    background-color: #f6f6f6;
    overflow-y: scroll;
    overflow-x: hidden;
    animation: jump-out-1 .6s 1;
  }
  .space_line{
    padding: 16px 8px;
    word-break: break-all;
    background-color: #ffffff;
    margin-bottom: 8px;
  }
  .space_line p{
    white-space: pre-line;
    letter-spacing: 2px;
  }
  .imgs_container{
    display: flex;
    flex-wrap: wrap;
    justify-content: space-between;
  }
  .imgs_container div{
    width: calc(50vw - 24px);
    height: calc(50vw - 24px);
    overflow: hidden;
    display: flex;
    align-items: center;
    justify-content: center;
    position: relative;
    margin: 8px;
    box-shadow: 0 0 4px #00000011;
    border-radius: 4px;
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
  .space_line_container::-webkit-scrollbar {
    width : 0;
    height: 1px;
  }
  .like_container{
    margin: 12px 4px 0 4px;
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
    margin-bottom: 8px;
    display: flex;
    align-items: center;
    justify-content: space-around;
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

</style>