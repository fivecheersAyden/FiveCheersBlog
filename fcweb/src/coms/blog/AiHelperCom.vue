<script setup>
  import {LeftOutlined, RightOutlined} from "@ant-design/icons-vue"
  import {onMounted, ref, defineProps, onUnmounted} from "vue";
  import {message} from "ant-design-vue";
  import Vditor from "vditor";

  onMounted(()=>{
    initWs()
  })

  onUnmounted(()=>{
    ws.value.close()
  })

  /**
   * 收缩展开
   * @type {Ref<UnwrapRef<boolean>>}
   */
  const hide = ref(true);
  const changeFold = () => {
   hide.value = !hide.value;
  };

  /**
   * 发送
   */
  const sendWithDoc = ref(false);
  const inputValue = ref("");

  const msgList = ref([
    {
      role: 'gpt',
      content: 'Ayden你今天也好棒！'
    },
  ])

  /**
   * ws
   */
  const props = defineProps({
    docFromFather: {
      type: String,
      required: true
    }
  })
  const currentMode = ref(0)
  const running = ref(false)
  const done = ref(true)
  const ws = ref(null)
  const connected = ref(true)
  const initWs = ()=>{
    if(typeof(WebSocket) === "undefined"){
      alert("您的浏览器不支持socket")
      return
    }
    ws.value = new WebSocket("ws://www.fivecheers.com:1011")
    ws.value.onerror = ()=>{
      connected.value = false
      message.error('websocket连接失败，请先开启服务')
      hide.value = true
    }
    ws.value.onopen = ()=>{
      connected.value = true
      message.success('websocket连接成功')
    }
    ws.value.onmessage = (e)=>{
      const res = e.data.replace(/^data:\s*/, '')
      if(res.length > 7){
        console.log(JSON.parse(res))
        let jsonRes = JSON.parse(res)
        //首次收到回复
        if(done.value && !running.value){
          msgList.value.push({
            role: 'gpt',
            content: jsonRes.choices[0].delta.content
          })
          done.value = false
          running.value = true
        }
        //续写回复
        if(running.value){
          console.log(jsonRes.choices[0].delta.content)
          msgList.value[msgList.value.length-1].content += jsonRes.choices[0].delta.content
        }
      }else if(res === "[DONE]"){
        const index = Number(msgList.value.length-1)
        const msgId = 'msg-' + index
        Vditor.preview(document.getElementById(msgId), msgList.value[index].content, {})
        if(currentMode.value === 1){
          props.docFromFather?.setValue(props.docFromFather?.getValue() + msgList.value[index].content)
        }
        done.value = true
        running.value = false
      }
    }
    ws.value.onclose = ()=>{
      connected.value = false
      message.info('websocket连接关闭')
      hide.value = true
    }
  }
  const send = (mode)=>{
    if(inputValue.value.length < 1){
      message.warn('输入不能为空')
      return
    }
    currentMode.value = mode
    if(connected.value){
      let docDetail = ""
      if(sendWithDoc.value){
        if(mode === 1){
          docDetail = "\n你的任务是帮我在文档的末尾生成补全内容，文档如下:" + props.docFromFather?.getValue()
        }else{
          docDetail = "\n你的任务是根据我的文档生成对话的回复，文档如下:" + props.docFromFather?.getValue()
        }
      }
      msgList.value.push({
        role: 'user',
        content: inputValue.value + docDetail
      })
      inputValue.value = ""
      ws.value.send(JSON.stringify({
        "model": "generalv3.5",
        "messages": msgList.value,
        "stream": true
      }))
    }else{
      message.warn('请先保证websocket打开')
    }
  }

</script>

<template>
  <div class="ai-container" :style="{minWidth: hide ? '24px' : '520px'}">
    <!--收缩展开-->
    <div class="fold-btn">
      <LeftOutlined @click="changeFold" v-if="hide"></LeftOutlined>
      <RightOutlined @click="changeFold" v-else></RightOutlined>
    </div>
    <!--AI对话-->
    <div class="ai-content" v-if="!hide">
      <!--消息栏-->
      <div class="msg-container">
        <div class="msg-scroll">
          <div v-for="(msg, index) in msgList" :id="'msg-'+index" :key="index" :class="msg.role==='user' ? 'usr-msg' : 'gpt-msg'">
            {{ msg.content }}
          </div>
        </div>
      </div>
      <!--状态栏-->
      <div class="state-container">
        <!--携带文档内容-->
        <a-switch style="margin-left: 8px" v-model:checked="sendWithDoc"></a-switch>
        <div style="font-size: 14px; color: #666">携带文档</div>
        <div style="flex: 1"></div>
        <!--TODO补写-->
        <a-button :disabled="running" @click="send(1)">补写</a-button>
        <!--发送-->
        <a-button :disabled="running" @click="send(0)" type="primary">发送</a-button>
      </div>
      <!--输入栏-->
      <div class="input-container">
        <a-textarea
            v-model:value="inputValue"
            placeholder="supported by spark LLM."
            :auto-size="{ minRows: 2, maxRows: 5 }"
        />
      </div>
    </div>
  </div>
</template>

<style scoped>
  .ai-container{
    height: 100%;
    background: linear-gradient(to right bottom, #fcf9fb, #f4f3fa, #f4f4fc);
    transition: .6s;
    position: relative;
  }
  .fold-btn{
    position: absolute;
    top: 0;
    left: 0;
    width: 20px;
    bottom: 0;
    display: flex;
    align-items: center;
    justify-content: center;
    padding-left: 4px;
    color: #888;
  }
  .ai-content{
    position: absolute;
    left: 24px;
    right: 8px;
    top: 8px;
    bottom: 16px;
    border-radius: 8px;
    display: flex;
    flex-direction: column;
  }

  .ai-content .msg-container{
    flex: 1;
    position: relative;
    width: 100%;
    overflow-y: scroll;
  }
  .ai-content .msg-container .msg-scroll{
    display: inline;
    height: 100%;
    width: 100%;
  }
  .ai-content .msg-container .msg-scroll .usr-msg{
    background-color: #c8d4ea22;
    margin: 8px 4px 12px 60px;
    padding: 8px;
    border: 1px solid #a9888e33;
    color: #484848;
    border-radius: 8px 8px 0 8px;
    line-height: 22px;
    white-space: pre-line;
  }
  .ai-content .msg-container .msg-scroll .gpt-msg{
    background-color: #fff;
    margin: 8px 60px 12px 4px;
    padding: 8px;
    border: 1px solid #848b9a33;
    color: #484848;
    border-radius: 8px 8px 8px 0;
    line-height: 22px;
    white-space: pre-line;
  }

  .ai-content .state-container{
    width: 100%;
    height: 64px;
    display: flex;
    align-items: center;
    justify-content: space-between;
    border-top: 1px solid #eee;
    padding-top: 8px;
    margin-top: 8px;
  }
  .ai-content .state-container button{
    margin: 0 8px;
  }
  .ai-content .input-container{
    width: 100%;
    min-height: 60px;
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: end;
  }

</style>
