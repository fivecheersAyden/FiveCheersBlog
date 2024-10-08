<script setup>
import {onMounted, ref} from "vue";
import {LeftOutlined, RightOutlined, DownOutlined} from "@ant-design/icons-vue";
import {checkManager} from "@/js/jshelper";
import {keyToJSONObject} from './utils/keyToJsonobject'
import {addNewBrotherDoc, addNewChildDoc, deleteDoc, getAllBlogKeys, renameDoc} from "@/js/apihelper";
import {message} from "ant-design-vue";
import {bus} from "vue3-eventbus";

onMounted(async () => {
  isManager.value = await checkManager();
  //获取所有列表并生成对象
  const getMenuResult = getAllBlogKeys()
  getMenuResult.then(response=>{
    const finalResult = keyToJSONObject(response.data)
    menuList.value = finalResult.JSONObj
    allNodeKeys.value = finalResult.allNodeKeys
    selectNode('1-0-0-0')
  }).catch(e=>{
    message.error('获取列表失败')
    console.log(e)
  })
})

/**
 * 更新文件名
 */
const open = ref(false);
const treeKey = ref()
const newName = ref()
const showModal = (treeKeyRef) => {
  open.value = true;
  treeKey.value = treeKeyRef
};

const handleOk = () => {
  open.value = false;
  const postResult = renameDoc(treeKey.value, newName.value)
  postResult.then(response=> {
    if (response.data) {
      message.success('重命名成功')
      refreshMenu()
      return
    }
    message.error('重命名失败')
  })
};


/**
 * 刷新列表
 */
const refreshMenu = ()=>{
  menuList.value = []
  const getMenuResult = getAllBlogKeys()
  getMenuResult.then(response=>{
    const finalResult = keyToJSONObject(response.data)
    menuList.value = finalResult.JSONObj
    allNodeKeys.value = finalResult.allNodeKeys
  }).catch(e=>{
    message.error('获取列表失败')
    console.log(e)
  })
}

/**
 * 左键
 */
const selectNode = () => {
  bus.emit('selectNode',selectedKeys.value[0])
  isStretch.value = false
}

/**
 * 右键
 */
const onContextMenuClick = (treeKey, menuKey) => {
  console.log(`treeKey: ${treeKey}, menuKey: ${menuKey}`);
  switch (menuKey){
    case '1':{
      //增加一个同层文件
      const postReault = addNewBrotherDoc(treeKey)
      postReault.then(response=>{
        if(response.data){
          message.success('创建成功')
          refreshMenu()
          return
        }
        message.error('创建失败')
      })
      break;
    }
    case '2':{
      //增加一个子文件
      const postReault = addNewChildDoc(treeKey)
      postReault.then(response=>{
        if(response.data){
          message.success('创建成功')
          refreshMenu()
          return
        }
        message.error('创建失败')
      })
      break;
    }
    case '3':
    {
      //重命名
      showModal(treeKey)
      break;
    }
    case '4':
    {
      //删除
      const postReault = deleteDoc(treeKey)
      postReault.then(response=>{
        if(response.data){
          message.success('删除成功')
          refreshMenu()
          return
        }
        message.error('删除失败')
      })
      break;
    }
  }
};

/**
 * 无函数交互数据
 * @type {Ref<UnwrapRef<boolean>>}
 */
const menuList = ref([])
const isStretch = ref(false);
const isEditting = ref(false);
const searchValue = ref('');
const isManager = ref(false);
const allNodeKeys = ref([])
const expandedKeys = ref([])
const selectedKeys = ref(['1-0-0-0'])
</script>

<template>
  <div class="menu-com" :style="isStretch ? 'width: 80vw' : 'width:24px'">
    <!--样式定义-->
    <div v-if="isStretch" class="menu-btns">
      <a-button v-if="expandedKeys.length > 0" @click="()=>{expandedKeys = []}" type="text">收起全部</a-button>
      <a-button v-else @click="()=>{expandedKeys = allNodeKeys}" type="text">展开全部</a-button>
      <a-button v-if="isEditting && isManager" @click="()=>{isEditting = false}" type="text">编辑模式</a-button>
      <a-button v-if="!isEditting && isManager" @click="()=>{isEditting = true}" type="text">搜索模式</a-button>
    </div>
    <!--章节展示-->
    <div v-if="isStretch" class="menu-self">
      <a-input-search v-if="!isEditting" v-model:value ="searchValue" placeholder="搜索" class="search"/>
      <a-tree @select="selectNode" @click="({ key: menuKey }) => onContextMenuClick(treeKey, menuKey)" :show-line="true" v-model:expanded-keys="expandedKeys" :tree-data="menuList" v-model:selected-keys="selectedKeys">
        <!--收缩展开图标-->
        <template #switcherIcon="{ switcherCls }"><down-outlined :class="switcherCls" /></template>
        <!--搜索-->
        <template v-if="!isEditting" #title="{ title }">
          <span v-if="title.indexOf(searchValue) > -1">
            {{ title.substring(0, title.indexOf(searchValue)) }}
            <span style="color: #f50">{{ searchValue }}</span>
            {{ title.substring(title.indexOf(searchValue) + searchValue.length) }}
          </span>
          <span v-else>
            {{ title }}
          </span>
        </template>
        <!--右键（仅编辑模式）-->
        <template v-else #title="{ key: treeKey, title }">
          <a-dropdown v-if="isEditting" :trigger="['contextmenu']">
            <span>{{ title }}</span>
            <template #overlay>
              <a-menu @click="({ key: menuKey }) => onContextMenuClick(treeKey, menuKey)">
                <a-menu-item key="1">增加一个同层文件</a-menu-item>
                <a-menu-item key="2">增加一个子文件</a-menu-item>
                <a-menu-item key="3">重命名</a-menu-item>
                <a-menu-item key="4">删除</a-menu-item>
              </a-menu>
            </template>
          </a-dropdown>
        </template>
      </a-tree>
    </div>
    <!--展开收缩按钮-->
    <div class="open-btn">
      <LeftOutlined v-if="isStretch" @click="()=>{isStretch = false}" />
      <RightOutlined v-else @click="()=>{isStretch = true}"/>
    </div>
    <!--命名-->
    <a-modal v-model:open="open" title="Basic Modal" @ok="handleOk">
      <a-input v-model:value="newName" placeholder="输入文档名"/>
    </a-modal>
  </div>
</template>

<style scoped>
.menu-com{
  background-color: #eee;
  position: relative;
  overflow: hidden;
  background: linear-gradient(to right bottom, #edf1f688, #efedf888, #f4ebf688);
  transition: .6s;
}
/*目录选项*/
.menu-com .menu-btns{
  position: absolute;
  top: 0;
  left: 56px;
  right: 0;
  height: 44px;
  display: flex;
  align-items: center;
  justify-content: end;
}
.menu-com .menu-btns button{
  color: #555;
}
/*目录本身*/
.menu-com .menu-self{
  position: absolute;
  left: 4px;
  right: 0;
  top: 44px;
  bottom: 0;
  overflow-y: scroll;
  overflow-x: hidden;
}
.menu-com .menu-self .search{
  margin: 4px 8px 0 4px;
  width: calc(100% - 12px);
}
.menu-com .menu-self :deep(.ant-tree){
  background: #00000000 !important;
  transition: .4s;
  margin-top: 8px;
}
.menu-com .menu-self :deep(.ant-tree-node-content-wrapper){
  width: 360px;
}
.menu-com .menu-self :deep(.ant-tree-node-selected){
  background-color: #66a2de;
  color: #fff;
}
/*收缩展开按钮*/
.menu-com .open-btn{
  position: absolute;
  height: 100%;
  width: 24px;
  right: 0;
  top: 0;
  display: flex;
  align-items: center;
  justify-content: center;
  color: #999;
  z-index: 999;
}
</style>
