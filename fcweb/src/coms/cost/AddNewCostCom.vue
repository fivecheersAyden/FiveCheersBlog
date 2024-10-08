<script setup>
import {ref, defineEmits} from "vue";
import moment from 'moment';
import {addNewCostApi} from "@/js/apihelper";
import {message} from "ant-design-vue";

  const countDate = ref(moment())
  const countNum = ref(0)
  const addonBeforeValue = ref('-')
  const countType = ref('吃喝')
  const countDetail = ref()

  const emit = defineEmits(['add-cost'])

  const postFuc = ()=>{
    const postData = {
      countId: null,
      countTime: formatDate(new Date(countDate.value.toString())),
      countNum: addonBeforeValue.value === '-' ? countNum.value * -1 : countNum.value,
      countDetail: countDetail.value,
      countType: countType.value
    }
    const postResult = addNewCostApi(postData)
    console.log(postData)
    postResult.then(response=>{
      if(response.data){
        message.success('记录成功')
        emit('add-cost')
      }else{
        message.error('请求失败')
      }
    }).catch((e)=>{
      message.error('请求错误')
      console.log(e)
    })
  }

const formatDate = (date)=>{
  const year = date.getFullYear();
  const month = String(date.getMonth() + 1).padStart(2, '0');
  const day = String(date.getDate()).padStart(2, '0');
  const hours = String(date.getHours()).padStart(2, '0');
  const minutes = String(date.getMinutes()).padStart(2, '0');
  const seconds = String(date.getSeconds()).padStart(2, '0');
  return `${year}-${month}-${day}T${hours}:${minutes}:${seconds}.001`
}

</script>

<template>
  <div class="add-container">
    <a-date-picker class="date-input" v-model:value="countDate" />
    <a-input-number class="money-input" v-model:value="countNum">
      <template #addonBefore>
        <a-select v-model:value="addonBeforeValue" style="width: 60px">
          <a-select-option value="add">+</a-select-option>
          <a-select-option value="minus">-</a-select-option>
        </a-select>
      </template>
    </a-input-number>
    <a-select class="type-input" v-model:value="countType">
      <a-select-option value="入账">入账</a-select-option>
      <a-select-option value="吃喝">吃喝</a-select-option>
      <a-select-option value="玩乐">玩乐</a-select-option>
      <a-select-option value="住宿">住宿</a-select-option>
      <a-select-option value="旅行">旅行</a-select-option>
      <a-select-option value="生活花销">生活花销</a-select-option>
      <a-select-option value="大宗商品">大宗商品</a-select-option>
      <a-select-option value="其他">其他</a-select-option>
    </a-select>
    <a-textarea class="detail-input" v-model:value="countDetail" placeholder="账单详情"/>
    <a-button @click="postFuc" type="primary" style="width: 100%; height: 36px; margin-top: 4px">确定</a-button>
  </div>
</template>

<style scoped>
  .add-container{
    position: absolute;
    left: 0;
    top: 0;
    right: 0;
    bottom: 0;
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: space-around;
    padding: 36px 24px;
  }
  :deep(.ant-select-selector){
    background-color: #ffffff !important;
  }
  .money-input{
    width: calc(100% - 100px);
    margin-left: 100px;
    position: relative;
  }
  .money-input::before{
    content: "金额（元）：";
    position: absolute;
    left: -100px;
    top: 50%;
    transform: translateY(-50%);
    color: #fff;
  }
  .type-input{
    width: calc(100% - 100px);
    margin-left: 100px;
    position: relative;
  }
  .type-input::before{
    content: "类型：";
    position: absolute;
    left: -100px;
    top: 50%;
    transform: translateY(-50%);
    color: #fff;
  }
  .detail-input{
    flex: 0.6;
    resize: none;
  }
  .date-input{
    width: calc(100% - 100px);
    margin-left: 100px;
    position: relative;
  }
  .date-input::before{
    content: "时间：";
    position: absolute;
    left: -100px;
    top: 50%;
    transform: translateY(-50%);
    color: #fff;
  }
</style>