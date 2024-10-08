<script setup>

import {defineEmits, onMounted, ref} from "vue";
import {message} from "ant-design-vue";
import {
  deleteCostApi,
  getCostByMonthApi,
  getCostByPage,
  getCostByYearApi,
  getCostSum,
  updateCostApi
} from "@/js/apihelper";
import moment from "moment";
import dayjs from 'dayjs';

const emit = defineEmits(['add-cost'])
const pageNum = ref(1)
const currentPage = ref(1)
const list = ref([])

const getList = ()=>{
  const listResult = getCostByPage({
    startIndex: (currentPage.value-1) * 10,
    length: 10
  })
  listResult.then(res=>{
    list.value = res.data
  }).catch(e=>{
    console.log(e)
    message.error('获取列表失败')
  })
}
const initList = ()=>{
  const getResult = getCostSum()
  getResult.then(res=>{
    pageNum.value = res.data
    currentPage.value = Math.ceil(pageNum.value/10)
    getList()
  }).catch(e=>{
    console.log(e)
    message.error('获取数量失败')
  })
}

const deleteCost = (countId)=>{
  const deleteResult = deleteCostApi(countId)
  deleteResult.then(res=>{
    if (res.data){
      message.success('删除成功')
      getList()
      emit('add-cost')
      return
    }
    message.error('删除失败')
  })
}

const updateCost = (postData)=>{
  let dataToPost = JSON.parse(JSON.stringify(postData))
  dataToPost.countTime = '2024-08-04T12:34:56.789'
  const postResult = updateCostApi(dataToPost)
  postResult.then(res=>{
    if (res.data){
      message.success('更新成功')
      emit('add-cost')
      return
    }
    message.error('更新失败')
  })
}

const yearMonth = ref(dayjs(moment().format('YYYY-MM'), 'YYYY-MM'))
const getListByYear = ()=>{
  const getResult = getCostByYearApi(yearMonth.value.year())
  getResult.then(res=>{
    if(res.data){
      list.value = res.data
      message.success('获取成功')
      return
    }
    message.error('获取失败')
  })
}
const getListByMonth = ()=>{
  const getResult = getCostByMonthApi({
    year: yearMonth.value.year(),
    month: yearMonth.value.month()+1
  })
  getResult.then(res=>{
    if(res.data){
      list.value = res.data
      message.success('获取成功')
      return
    }
    message.error('获取失败')
  })
}

onMounted(()=>{
  initList()
})

</script>

<template>
  <div class="update-container">
    <!--查询方式-->
    <div class="method-container">
      <a-date-picker size="small" v-model:value="yearMonth" picker="month" />
      <a-button type="primary" size="small" @click="getListByYear">按年查询</a-button>
      <a-button type="primary" size="small" @click="getListByMonth">按月查询</a-button>
    </div>
    <!--列表-->
    <div class="list-container">
      <div class="list-item" v-for="(item, index) in list" :key="index">
        <div class="line1">
          <div>{{item.countTime.substring(0, 10)}}</div>
          <a-input size="small" style="flex: 0.9" v-model:value="item.countDetail"/>
          <a-input-number style="width: 48px" size="small" v-model:value="item.countNum"/>
        </div>
        <div class="line2">
          <a-select size="small" style="flex: 0.9" v-model:value="item.countType">
            <a-select-option value="入账">入账</a-select-option>
            <a-select-option value="吃喝">吃喝</a-select-option>
            <a-select-option value="玩乐">玩乐</a-select-option>
            <a-select-option value="住宿">住宿</a-select-option>
            <a-select-option value="旅行">旅行</a-select-option>
            <a-select-option value="生活花销">生活花销</a-select-option>
            <a-select-option value="大宗商品">大宗商品</a-select-option>
            <a-select-option value="其他">其他</a-select-option>
          </a-select>
          <a-button size="small" type="primary" @click="deleteCost(item.countId)" danger>删除</a-button>
          <a-button size="small" type="primary" @click="updateCost(item)">更新</a-button>
        </div>
      </div>
    </div>
    <!--选页-->
    <div class="page-select-container">
      <a-pagination @change="getList" v-model:current="currentPage" :total="pageNum" />
    </div>
  </div>
</template>

<style scoped>
  .update-container{
    position: absolute;
    left: 0;
    top: 0;
    right: 0;
    bottom: 0;
  }
  .method-container{
    position: absolute;
    top: 16px;
    height: 40px;
    left: 8px;
    right: 8px;
    display: flex;
    align-items: center;
    justify-content: space-around;
    border-bottom: 1px solid #eee;
    padding-bottom: 8px;
  }
  .page-select-container{
    position: absolute;
    bottom: 16px;
    right: 16px;
    left: 16px;
    height: 36px;
    display: flex;
    align-items: center;
    justify-content: center;
    white-space: nowrap;
  }
  .page-select-container:deep(.ant-pagination-item-active){
    background-color: transparent;
    border-color: #6291e5;
  }
  .page-select-container:deep(.ant-pagination-item-link){
    color: #fff;
  }
  .page-select-container:deep(a){
    color: #fff;
  }
  .list-container{
    position: absolute;
    bottom: 72px;
    top: 64px;
    left: 16px;
    right: 16px;
    overflow-y: scroll;
    border-radius: 8px;
  }
  .list-item{
    width: 100%;
    margin: 8px 0;
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
    color: #fff;
    height: 68px;
    position: relative;
    padding-right: 4px;
  }
  .list-item .line1{
    height: 24px;
    width: 100%;
    display: flex;
    align-items: center;
    justify-content: space-between;
  }
  .list-item .line2{
    height: 24px;
    width: 100%;
    margin-top: 8px;
    display: flex;
    align-items: center;
    justify-content: space-between;
  }
</style>
