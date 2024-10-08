<script setup>
  import Vditor from 'vditor'
  import 'vditor/dist/index.css';
  import {onBeforeUnmount, onMounted, ref} from "vue";
  import {checkManager} from "@/js/jshelper";
  import {bus} from "vue3-eventbus";
  import {getDocDetailApi, updateDocDetail} from "@/js/apihelper";
  import {message} from "ant-design-vue";
  import AiHelperCom from "@/coms/blog/AiHelperCom.vue";

  onMounted(()=>{
    isManager.value = checkManager()
    vditor.value = new Vditor('vditor',options.value)
    bus.on('selectNode',(nodeKey)=>{
      getDocDetail(nodeKey)
    })
  })

  onBeforeUnmount(()=>{
    bus.off('selectNode')
    vditor.value.destroy()
  })

  /**
   * 获取详细内容
   */
  const docNodeKey = ref()
  const getDocDetail = (nodeKey)=>{
    //编辑器未加载完成，延迟获取
    if(!loaded.value){
      setTimeout(()=>{
        getDocDetail(nodeKey)
      },500)
      return
    }
    //获取
    const contentResult = getDocDetailApi(nodeKey)
    contentResult.then(response=>{
      if(response.data.blogKey){
        docNodeKey.value = response.data.blogKey
        if(isManager.value ){
          vditor.value.setValue(decodeURIComponent(response.data.content))
        }else{
          Vditor.preview(document.getElementById(`vditor`), decodeURIComponent(response.data.content), {});
          document.getElementById(`vditor`).style.padding = '0 24px'
        }
      }
    })
  }

  /**
   * 保存内容
   */
  const saveDocDetail = ()=>{
    const postResult = updateDocDetail({
      currentKey: docNodeKey.value,
      newContent: encodeURIComponent(vditor.value.getValue())
    })
    postResult.then(response=>{
      if(response.data){
        message.success('保存成功')
        return
      }
      message.error('保存失败')
    })
  }



  /**
   * 获取当前文本内容
   */
  const getContent = ()=>{
    const tmp = "loading"
    if(isManager.value){
      vditor.value.setValue(tmp)
    }else{
      Vditor.preview(document.getElementById(`vditor`), tmp, {});
      document.getElementById(`vditor`).style.padding = '0 24px'
    }
    loaded.value = true
  }

  /**
   * 编辑器设置
   */
  const isManager = ref(false)
  const vditor = ref()
  const loaded = ref(false)
  const options = ref({
    toolbar:[
       'upload', 'link', 'record', 'export', '|' ,
       'line' , 'quote', '|',
       'bold', 'italic', 'strike', '|',
       'table' , 'list', 'ordered-list', 'check' , 'code' , '|' ,
       'edit-mode', 'outline'
    ],
    preview:{
      maxWidth: 1400
    },
    toolbarConfig:{
      pin: true,
      hide: false
    },
    cache: {
      enable: false
    },
    after: getContent,
    value: 'loading...',
    upload: {
      url: 'http://www.fivecheers.com:1001/file/blogUpLoadFile'
    },
    counter: {
      enable: true
    },
    mode: 'wysiwyg',
  })

</script>

<template>
  <div class="content-ai-container">
    <!--内容-->
    <div class="content-container">
      <div class="content-self" id="vditor">loading...</div>
      <div class="a-container">
        <!-- <a href="/otherblog" >other users</a>
        <a href="https://github.com/Vanessa219/vditor">supported by Vditor</a> -->
      </div>
      <a-button size="small" @click="saveDocDetail" v-if="isManager" type="default">保存</a-button>
    </div>
    <!--AI-->
    <AiHelperCom :doc-from-father="vditor" v-if="isManager" class="ai-container"/>
  </div>
</template>

<style scoped>
  .content-ai-container{
    display: flex;
    position: relative;
    width: 100%;
    height: 100%;
  }
  .content-container{
    flex: 1;
    height: 100%;
    display: flex;
    flex-direction: column;
    position: relative;
  }
  .content-container .a-container{
    position: absolute;
    right: 28px;
    top: 8px;
    z-index: 999;
    min-width: 300px;
    display: flex;
    align-items: center;
    justify-content: end;
  }
  .content-container .a-container a{
    color: #91aefa;
    margin-left: 16px;
  }
  .content-container button{
    position: absolute;
    right: 16px;
    top: 44px;
    z-index: 999;
  }
  .content-container:deep(.vditor-reset){
    padding: 0 16px;
  }
  .content-container .content-self{
    min-height: 90vh;
    border: 0;
    position: relative;
    z-index: 999;
  }
  .content-container .content-self:deep(img){
    max-width: 800px;
    min-width: 200px;
    display: block;
    margin: 0 auto;
  }
  .content-container ::v-deep(.vditor-toolbar) {
    padding: 0;
    line-height: 0;
    border-radius: 0;
    border: 0;
    border-bottom: 1px solid #eee;
    background: linear-gradient(to right bottom, #edf0fc88, #faf3f988);
    backdrop-filter: blur(4px);
  }
</style>
