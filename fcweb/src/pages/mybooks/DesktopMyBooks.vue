<script setup>
import {
  HomeOutlined
} from '@ant-design/icons-vue';
import DefaultBackground from "@/coms/all/DefaultBackground.vue";
import {jumphelper} from "@/js/jumphelper";
import { ref, onMounted, provide, watch } from 'vue';
import dayjs from 'dayjs';
import moment from 'moment';
import { CanvasRenderer } from 'echarts/renderers';
import { PieChart, LineChart } from 'echarts/charts';
import {
  TitleComponent,
  TooltipComponent,
  LegendComponent,
  GridComponent,
} from 'echarts/components';
import VChart, { THEME_KEY } from 'vue-echarts';
import { use } from 'echarts/core';
import { getCostByMonthApi, getMonthAnalysisByMonthApi, getMonthAnalysisByYearApi, getYearAnalysisByYearApi, getYearAnalysisByAllApi } from '@/js/apihelper';
import { message } from 'ant-design-vue';
import { dateCalHelperGetTotal } from './utils/dayCal'

/**
 * 公共方法
 */
use([
  CanvasRenderer,
  LineChart,
  PieChart,
  TitleComponent,
  TooltipComponent,
  LegendComponent,
  GridComponent,
]);
provide(THEME_KEY, 'light');
onMounted(()=>{
  if(window.innerWidth > 768 && window.innerWidth/window.innerHeight > 1.3){
    screenSizeType.value = 1;
  }
  updateData()
})

 /**
  * 判断模式
  */
const screenSizeType = ref(0); //0为手机，1为电脑
const currentType = ref(0); //0为月，1为年，2为全部
const time = ref(dayjs(moment().format('YYYY-MM'), 'YYYY-MM'))
const changeSelect = (type)=>{
  currentType.value = type;
}
const updateData = ()=>{
  if(currentType.value === 0){
    getLineDataByMonth();
    getPieDataByMonth();
  }else if(currentType.value === 1){
    getLineDataByYear();
    getPieDataByYear();
  }else if(currentType.value === 2){
    getLineDataByAll();
    getPieDataByAll();
  }
}
watch(currentType, ()=>{
  updateData()
})
watch(time, ()=>{
  updateData()
})


/**
 * API
 */
const getLineDataByMonth = ()=>{
  const getResult = getCostByMonthApi({
    year: time.value.year(),
    month: time.value.month()+1
  })
  getResult.then(res=>{
    if(res.data){
      //标题、坐标
      option3.value.title.text = `${time.value.year()}-${time.value.month()+1} 全月花销分析`
      option3.value.xAxis.data = []
      for (let index = 0; index < 31; index++) {
        option3.value.xAxis.data.push(index+1)
      }
      //处理数据
      const resultArray = dateCalHelperGetTotal(res.data)
      option3.value.series[0].data = resultArray.total
      option3.value.series[1].data = resultArray.in
      option3.value.series[2].data = resultArray.out
      option3.value.series[3].data = resultArray.eat
      option3.value.series[4].data = resultArray.fun
      option3.value.series[5].data = resultArray.house
      option3.value.series[6].data = resultArray.travel
      option3.value.series[7].data = resultArray.life
      option3.value.series[8].data = resultArray.big
      option3.value.series[9].data = resultArray.other
      return
    }
    message.error('获取失败')
  })
}
const getPieDataByMonth = ()=>{
  const getResult = getMonthAnalysisByMonthApi({
    year: time.value.year(),
    month: time.value.month()+1
  })
  getResult.then(res=>{
    if(res.data){
      //总分析
      allAnalysis.value.mode = res.data.year + '-' + res.data.month
      allAnalysis.value.inout = res.data.totalCost
      allAnalysis.value.in = res.data.inNum
      allAnalysis.value.out = res.data.outNum
      //饼图
      option2.value.title.text = `${res.data.year }-${res.data.month} 支出比例分析`
      option2.value.series[0].name = '总支出' + -1 * res.data.outNum
      option2.value.series[0].data[0].value = -1 * Math.floor(res.data.outNum * res.data.eatPercent)
      option2.value.series[0].data[1].value = -1 * Math.floor(res.data.outNum * res.data.funPercent)
      option2.value.series[0].data[2].value = -1 * Math.floor(res.data.outNum * res.data.housePercent)
      option2.value.series[0].data[3].value = -1 * Math.floor(res.data.outNum * res.data.travelPercent)
      option2.value.series[0].data[4].value = -1 * Math.floor(res.data.outNum * res.data.livingPercent)
      option2.value.series[0].data[5].value = -1 * Math.floor(res.data.outNum * res.data.pricedPercent)
      option2.value.series[0].data[6].value = -1 * Math.floor(res.data.outNum * res.data.otherPercent)
      return
    }
    message.error('获取失败')
  })

}
const getLineDataByYear = ()=>{
  const getResult = getMonthAnalysisByYearApi(time.value.year())
  getResult.then(res=>{
    if(res.data){
      option3.value.title.text = `${time.value.year()} 全年花销分析`
      option3.value.xAxis.data = []
      for (let index = 0; index < 10; index++) {
        option3.value.series[index].data = []
      }
      for (let index = 0; index < 12; index++) {
        option3.value.xAxis.data.push(index+1)
        let hasData = false
        for (let i = 0; i < res.data.length; i++) {
          const element = res.data[i];
          if(element.month === index+1){
            hasData = true
            option3.value.series[0].data.push(element.totalCost)
            option3.value.series[1].data.push(element.inNum)
            option3.value.series[2].data.push(-1 * element.outNum)
            option3.value.series[3].data.push(Math.floor(element.eatPercent * -1 * element.outNum))
            option3.value.series[4].data.push(Math.floor(element.funPercent * -1 * element.outNum))
            option3.value.series[5].data.push(Math.floor(element.housePercent * -1 * element.outNum))
            option3.value.series[6].data.push(Math.floor(element.travelPercent * -1 * element.outNum))
            option3.value.series[7].data.push(Math.floor(element.livingPercent * -1 * element.outNum))
            option3.value.series[8].data.push(Math.floor(element.pricedPercent * -1 * element.outNum))
            option3.value.series[9].data.push(Math.floor(element.otherPercent * -1 * element.outNum))
          }
        }
        if(!hasData){
          option3.value.series[0].data.push(0)
          option3.value.series[1].data.push(0)
          option3.value.series[2].data.push(0)
          option3.value.series[3].data.push(0)
          option3.value.series[4].data.push(0)
          option3.value.series[5].data.push(0)
          option3.value.series[6].data.push(0)
          option3.value.series[7].data.push(0)
          option3.value.series[8].data.push(0)
          option3.value.series[9].data.push(0)
          
        }
      }
      return
    }
    message.error('获取失败')
  })
}
const getPieDataByYear = ()=>{
  const getResult = getYearAnalysisByYearApi(time.value.year())
  getResult.then(res=>{
    if(res.data){
      //总分析
      allAnalysis.value.mode = res.data.year
      allAnalysis.value.inout = res.data.totalCost
      allAnalysis.value.in = res.data.inNum
      allAnalysis.value.out = res.data.outNum
      //饼图
      option2.value.title.text = `${res.data.year } 支出比例分析`
      option2.value.series[0].name = '总支出' + -1 * res.data.outNum
      option2.value.series[0].data[0].value = -1 * Math.floor(res.data.outNum * res.data.eatPercent)
      option2.value.series[0].data[1].value = -1 * Math.floor(res.data.outNum * res.data.funPercent)
      option2.value.series[0].data[2].value = -1 * Math.floor(res.data.outNum * res.data.housePercent)
      option2.value.series[0].data[3].value = -1 * Math.floor(res.data.outNum * res.data.travelPercent)
      option2.value.series[0].data[4].value = -1 * Math.floor(res.data.outNum * res.data.livingPercent)
      option2.value.series[0].data[5].value = -1 * Math.floor(res.data.outNum * res.data.pricedPercent)
      option2.value.series[0].data[6].value = -1 * Math.floor(res.data.outNum * res.data.otherPercent)
      return
    }
    message.error('获取失败')
  })
}
const getLineDataByAll = ()=>{
  message.info('建设中')
  const getResult = getYearAnalysisByAllApi(time.value.year())
  getResult.then(res=>{
    if(res.data){
      console.log(res.data)
      
      return
    }
    message.error('获取失败')
  })
}
const getPieDataByAll = ()=>{
  
}


 /**
  * 总体分析
  */
const allAnalysis = ref({
  mode: '',
  inout: 0,
  in: 0,
  out: 0,
})

 /**
   * 饼图分析（大维度）
   */
   const option2 = ref({
  title: {
    text: '全月折线分析',
    left: 'center',
    textStyle: {
      fontSize: 12
    },
  },
  tooltip: {
    trigger: 'item',
    formatter: '{a} <br/>{b} : {c} ({d}%)',
  },
  legend: {
    orient: 'vertical',
    left: 'left',
    data: ['吃喝', '玩乐', '住宿', '旅行', '生活花销', '大宗商品', '其他'],
    textStyle: {
      fontSize: 8, // 设置字体大小为14
    },
    itemWidth: 16, // 设置色块宽度为20
    itemHeight: 8, // 设置色块高度为14
  },
  series: [
    {
      name: 'Traffic Sources',
      type: 'pie',
      radius: '55%',
      center: ['50%', '60%'],
      data: [
        { value: 335, name: '吃喝' },
        { value: 310, name: '玩乐' },
        { value: 234, name: '住宿' },
        { value: 135, name: '旅行' },
        { value: 1548, name: '生活花销' },
        { value: 1548, name: '大宗商品' },
        { value: 1548, name: '其他' },
      ],
      emphasis: {
        itemStyle: {
          shadowBlur: 10,
          shadowOffsetX: 0,
          shadowColor: 'rgba(0, 0, 0, 0.5)',
        },
      },
    },
  ],
});

/**
 * 折线分析
 */
 const option3 = ref({
  title: {
    text: '堆叠折线图示例',
    left: 'center',
    textStyle: {
      fontSize: 12
    },
  },
  tooltip: {
    trigger: 'axis',
  },
  legend: {
    orient: 'horizontal',
    left: 'center',
    top: '20px',
    data: ['总收支', '收入', '支出', '吃喝', '玩乐', '住宿', '旅行', '生活花销', '大宗商品', '其他'],
    textStyle: {
      fontSize: 8,
    },
    itemWidth: 10,
    itemHeight: 8,
  },
  grid: {
    left: '3%',
    right: '4%',
    bottom: '3%',
    containLabel: true
  },
  xAxis: {
    type: 'category',
    boundaryGap: false,
    data: []
  },
  yAxis: {
    type: 'value'
  },
  series: [
    {
      name: '总收支',
      type: 'line',
      data: []
    },
    {
      name: '收入',
      type: 'line',
      data: []
    },
    {
      name: '支出',
      type: 'line',
      data: []
    },
    {
      name: '吃喝',
      type: 'line',
      data: []
    },
    {
      name: '玩乐',
      type: 'line',
      data: []
    },
    {
      name: '住宿',
      type: 'line',
      data: []
    },
    {
      name: '旅行',
      type: 'line',
      data: []
    },
    {
      name: '生活花销',
      type: 'line',
      data: []
    },
    {
      name: '大宗商品',
      type: 'line',
      data: []
    },
    {
      name: '其他',
      type: 'line',
      data: []
    },
  ],
});
</script>

<template>
  <div :class="screenSizeType === 0 ? 'phone-container' : 'PC-container'" class="all-container">
    <DefaultBackground/>
    <HomeOutlined style="color: #fff;" @click="jumphelper.jumpToSelect()" class="home_icon"/>
    <!--标题-->
    <h3 v-if="screenSizeType === 0">账本分析</h3>
    <div class="echarts-title-container glass-container">
      <div>时间： {{ allAnalysis.mode }}</div>
      <div>总收支： {{ allAnalysis.inout }}</div>
      <div>收入： {{ allAnalysis.in }}</div>
      <div>支出： {{ allAnalysis.out }}</div>
    </div>
    <div class="echarts-container glass-container">
      <v-chart class="chart" :option="option2" autoresize />
    </div>
    <div class="echarts-container glass-container">
      <v-chart class="chart" :option="option3" autoresize />
    </div>
    <!--选择器-->
    <div class="select-container glass-container">
      <div @click="changeSelect(0)" :class="currentType === 0 ? 'chosen-btn':'unchosen-btn'">按月</div>
      <div @click="changeSelect(1)" :class="currentType === 1 ? 'chosen-btn':'unchosen-btn'">按年</div>
      <div @click="changeSelect(2)" :class="currentType === 2 ? 'chosen-btn':'unchosen-btn'">全部</div>
      <a-date-picker size="small" v-model:value="time" picker="month" />
    </div>
  </div>
</template>

<style scoped>
/** 不同样式 */
.all-container{
  position: fixed;
  left: 0;
  right: 0;
  top: 0;
  bottom: 0;
  background-color: #000
}
.phone-container{
  display: flex;
  flex-direction: column;
  justify-content: space-around;
}
.phone-container h3{
  color: #fff;
  text-align: center;
  margin: 12px 0;
  height: 24px;
}
.phone-container .select-container{
  height: 42px;
  width: calc(100% - 16px);
  margin: 0 8px 12px 8px;
  display: flex;
  justify-content: space-around;
  align-items: center;
}
.phone-container .echarts-container{
  flex: 1;
  width: calc(100% - 16px);
  margin: 0 8px 12px 8px;
}
.phone-container .echarts-title-container{
  height: 80px;
  width: calc(100% - 16px);
  margin: 0 8px 12px 8px;
  display: flex;
  flex-wrap: wrap;
  justify-content: space-around;
  align-items: center;
}
.phone-container .echarts-title-container div{
  width: 40%;
  height: 32px;
  text-align: center;
  display: flex;
  justify-content: center;
  align-items: center;
  color: #fff;
  font-size: 14px;
}
.PC-container .select-container{
  justify-content: space-around;
  align-items: center;
  display: flex;
  position: absolute;
  top: 12px;
  left: 50%;
  height: 42px;
  width: 400px;
  transform: translateX(-50%);
}
.PC-container .echarts-container{
  position: absolute;
}
.PC-container .echarts-title-container{
  left: 12px;
  bottom: 12px;
  width: calc(50% - 18px);
  height: calc(50% - 36px);
  position: absolute;
  display: flex;
  align-items: center;
  justify-content: space-around;
}
.PC-container .echarts-container:nth-of-type(4){
  height: calc(50% - 60px);
  top: 68px;
  width: calc(100% - 24px);
  left: 12px;
}
.PC-container .echarts-container:nth-of-type(3){
  right: 12px;
  bottom: 12px;
  width: calc(50% - 18px);
  height: calc(50% - 36px);
}

/**通用样式 */
.glass-container{
  background-color: #f8f8f8aa;
  padding: 4px 8px;
  border-radius: 12px;
  backdrop-filter: blur(10px);
}
.chosen-btn{
  font-size: 14px;
  transition: .4s;
  color: #0059ffe4;
}
.unchosen-btn{
  font-size: 12px;
  color: #fff;
  transition: .4s;
}
.echarts-container{
  display: flex;
  flex-direction: column;
  align-items: center;
}
.echarts-container .chart{
  width: 100%;
  height: 100%;
}






</style>