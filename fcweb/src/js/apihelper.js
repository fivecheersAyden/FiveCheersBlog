import axios from "axios";
import {checkManager} from "@/js/jshelper";

//const gdMapKey = '1caf32ee46ec234c69a6a49e47db53e0'
// const baseApi = 'http://localhost:1001'
const baseApi = 'http://www.fivecheers.com:1001'

/**
 * 基础接口
 * @returns {Promise<axios.AxiosResponse<any>>}
 */
//获取IP信息
export const getIpAddress = ()=>{
    const getUrl = 'https://ip.useragentinfo.com/json?ip='
    return axios.get(getUrl)
}
//记录访问操作
export const postLog = (logOp)=>{
    if(checkManager()){
        return
    }
    const postUrl = baseApi + '/log/postNewLog'
    const logUUID = localStorage.getItem('UUID')
    const logIpAddress = localStorage.getItem('ipAddress')
    return axios.post(
        postUrl,
        null,
        {
            params:{
                logOp:logOp,
                logUUID:logUUID,
                logIpAddress:logIpAddress
            }
        }
    )
}
//比对管理员密码
export const checkManagerPassword = (password)=>{
    const postUrl = baseApi + '/params/checkManagerSecret'
    return axios.post(
        postUrl,
        null,
        {
            params:{
                password: password
            }
        })
}


/**
 * 我
 */
//获取基础信息
export const getMeList = ()=>{
    const getUrl = baseApi + '/me/getList'
    return axios.get(getUrl)
}
//更改基础信息
export const changeMeList = (key)=>{
    const postUrl = baseApi + '/me/changeKey'
    return axios.post(
        postUrl,
        null,
        {
            params:{
                type: key.type,
                value: key.value
            }
        }
    )
}

/**
 * 挑战板
 */
//获取所有计划
export const getPlanAllPlans = ()=>{
    const getUrl = baseApi + '/plan/getAllPlans'
    return axios.get(getUrl)
}
//增加新计划
export const addNewPlan = (plan)=>{
    const postUrl = baseApi + '/plan/addNewPlan'
    return axios.post(
        postUrl,
        null,
        {
            params:{
                year: plan.year,
                planName: plan.planName,
                planDetail: plan.planDetail,
                planCompleted: plan.planCompleted,
                planImg: plan.planImg
            }
        }
    )
}
//修改计划
export const changePlanApi = (plan)=>{
    const postUrl = baseApi + '/plan/changePlan'
    return axios.post(
        postUrl,
        null,
        {
            params:{
                planId: plan.planId,
                year: plan.year,
                planName: plan.planName,
                planDetail: plan.planDetail,
                planCompleted: plan.planCompleted,
                planImg: plan.planImg
            }
        }
    )
}

/**
 * 空间
 */
//增加一条
export const insertSpaceApi = (newSpace)=>{
    const postUrl = baseApi+'/space/insert'
    return axios.post(postUrl,null,{
        params:{
            spaceContent:newSpace.spaceContent,
            spaceImgs:newSpace.spaceImgs,
            spaceFiles:newSpace.spaceFiles
        }
    })
}

//删除一条
export const deleteSpaceApi = (spaceId)=>{
    const postUrl = baseApi + '/space/delete'
    return axios.post(postUrl, null, {
        params:{
            spaceId: spaceId
        }
    })
}

//更改一条
export const updateSpaceApi = (newSpace)=>{
    const postUrl = baseApi + '/space/update'
    return axios.post(postUrl, newSpace)
}

//查询n条
export const selectSpaceApi = (e)=>{
    const getUrl = baseApi + '/space/select'
    return axios.get(getUrl, {
        params:{
            startIndex: e.startIndex,
            length: e.length
        }
    })
}

//点赞
export const likeSpaceApi = (spaceId)=>{
    const postUrl = baseApi + '/space/like'
    return axios.post(postUrl, null, {
        params:{
            spaceId:spaceId
        }
    })
}

/**
 * 视频
 */
//增加一条
export const insertNewVideo = (newVideo)=>{
    const postUrl = baseApi + '/video/insert'
    return axios.post(postUrl, null, {
        params:{
            videoContent: newVideo.videoContent,
            videoUrl: newVideo.videoUrl
        }
    })
}
//删除一条
export const deleteVideoApi = (videoId)=>{
    const postUrl = baseApi + '/video/delete'
    return axios.post(postUrl, null, {
        params:{
            videoId: videoId
        }
    })
}
//分页查询
export const selectVideos = (videoProps)=>{
    const getUrl = baseApi + '/video/select'
    return axios.get(getUrl, {
        params:{
            startIndex: videoProps.firstIndex,
            length: videoProps.length
        }
    })
}

/**
 * mygit
 */
//分页查询
export const selectMyGit = (videoProps)=>{
    const getUrl = baseApi + '/mygit/select'
    return axios.get(getUrl, {
        params:{
            startIndex: videoProps.firstIndex,
            length: videoProps.length
        }
    })
}
//删除一条
export const deleteMyGit = (gitId)=>{
    const postUrl = baseApi + '/mygit/delete'
    return axios.post(postUrl, null, {
        params:{
            gitId: gitId
        }
    })
}
//增加一条
export const insertNewMyGit = (newMyGit)=>{
    const postUrl = baseApi + '/mygit/insert'
    return axios.post(postUrl, null, {
        params:{
            gitContent: newMyGit.gitContent,
            gitVideo: newMyGit.gitVideo,
            gitUrl: newMyGit.gitUrl,
            runUrl: newMyGit.runUrl
        }
    })
}

/**
 * 藏宝图
 */
//获取死亡信息
export const getAmIDead = ()=>{
    const getUrl = baseApi + '/amidead/getAmidead'
    return axios.get(getUrl)
}
//更新时间
export const refreshDeadTime = ()=>{
    const postUrl = baseApi + '/amidead/updateTime'
    return axios.post(postUrl)
}
//宝藏已经被发现
export const refreshDeadGift = ()=>{
    const postUrl = baseApi + '/amidead/updateGiftStill'
    return axios.post(postUrl)
}

/**
 * Blog
 */
//获取所有列表
export const getAllBlogKeys = ()=>{
    const getUrl = baseApi + '/blog/getAllBlogKeys'
    return axios.get(getUrl)
}

//增加一个同层文件
export const addNewBrotherDoc = (currentKey)=>{
    const postUrl = baseApi + '/blog/addNewBrotherDoc'
    return axios.post(
        postUrl,
        null,
        {
            params:{
                currentKey: currentKey
            }
        }
    )
}

//增加一个子文件
export const addNewChildDoc = (currentKey)=>{
    const postUrl = baseApi + '/blog/addNewChildDoc'
    return axios.post(
        postUrl,
        null,
        {
            params:{
                currentKey: currentKey
            }
        }
    )
}

//删除文件
export const deleteDoc = (currentKey)=>{
    const getUrl = baseApi + '/blog/deleteDoc'
    return axios.get(getUrl,{
        params:{
            currentKey: currentKey
        }
    })
}

//改名
export const renameDoc = (currentKey, newName)=>{
    const postUrl = baseApi + '/blog/resetName'
    return axios.post(
        postUrl,
        null,
        {
            params:{
                currentKey: currentKey,
                newName: newName
            }
        }
    )
}

//获取文档全部信息
export const getDocDetailApi = (currentKey)=>{
    const postUrl = baseApi + '/blog/getDocDetail'
    return axios.post(postUrl,null,{
        params:{
            currentKey: currentKey
        }
    })
}

//更改文档内容
export const updateDocDetail = (e)=>{
    const postUrl = baseApi + '/blog/updateDocDetail'
    return axios.post(postUrl,e,null)
}

/**
 * BlogOther
 */
//获取用户是否存在
export const checkUserExists = (username)=>{
    const getUrl = baseApi + '/blogother/getIfUser'
    return axios.get(getUrl, {
        params:{
            user: username
        }
    })
}

//获取所有列表
export const getAllBlogKeysOther = (user)=>{
    const getUrl = baseApi + '/blogother/getAllBlogKeys'
    return axios.get(getUrl,{
        params:{
            user: user
        }
    })
}

//增加一个同层文件
export const addNewBrotherDocOther = (currentKey, user)=>{
    const postUrl = baseApi + '/blogother/addNewBrotherDoc'
    return axios.post(
        postUrl,
        null,
        {
            params:{
                currentKey: currentKey,
                user: user
            }
        }
    )
}

//增加一个子文件
export const addNewChildDocOther = (currentKey, user)=>{
    const postUrl = baseApi + '/blogother/addNewChildDoc'
    return axios.post(
        postUrl,
        null,
        {
            params:{
                currentKey: currentKey,
                user: user
            }
        }
    )
}

//删除文件
export const deleteDocOther = (currentKey, user)=>{
    const getUrl = baseApi + '/blogother/deleteDoc'
    return axios.get(getUrl,{
        params:{
            currentKey: currentKey,
            user: user
        }
    })
}

//改名
export const renameDocOther = (currentKey, newName, user)=>{
    const postUrl = baseApi + '/blogother/resetName'
    return axios.post(
        postUrl,
        null,
        {
            params:{
                currentKey: currentKey,
                newName: newName,
                user: user
            }
        }
    )
}

//获取文档全部信息
export const getDocDetailApiOther = (currentKey, user)=>{
    const postUrl = baseApi + '/blogother/getDocDetail'
    return axios.post(postUrl,null,{
        params:{
            currentKey: currentKey,
            user: user
        }
    })
}

//更改文档内容
export const updateDocDetailOther = (e)=>{
    const postUrl = baseApi + '/blogother/updateDocDetail'
    return axios.post(postUrl,e,null)
}

/**
 * Cost
 */
//增加账单
export const addNewCostApi = (postData)=>{
    const postUrl = baseApi + '/cost/addNewCost'
    return axios.post(postUrl, postData,{
        headers: {
            'Content-Type': 'application/json' // 设置请求头
        }
    })
}

export const getAllCostSumApi = ()=>{
    const getUrl = baseApi + '/cost/getAllCostSum'
    return axios.get(getUrl)
}

export const getCostSum = ()=>{
    const getUrl = baseApi + '/cost/getCostSum'
    return axios.get(getUrl)
}

export const getCostByPage = (e)=>{
    const getUrl = baseApi + '/cost/getCostByPage'
    return axios.get(getUrl,{
        params:{
            startIndex: e.startIndex,
            length: e.length
        }
    })
}

export const deleteCostApi = (countId)=>{
    const postUrl = baseApi + '/cost/deleteCost'
    return axios.post(postUrl, null,{
        params:{
            countId: countId
        }
    })
}

export const updateCostApi = (postData)=>{
    const postUrl = baseApi + '/cost/updateCost'
    return axios.post(postUrl, postData,{
        headers: {
            'Content-Type': 'application/json' // 设置请求头
        }
    })
}

export const getCostByMonthApi = (e)=>{
    const getUrl = baseApi + '/cost/getCostByMonth'
    return axios.get(getUrl, {
        params:{
            year: e.year,
            month: e.month
        }
    })
}

export const getCostByYearApi = (year)=>{
    const getUrl = baseApi + '/cost/getCostByYear'
    return axios.get(getUrl, {
        params:{
            year: year,
        }
    })
}

export const getMonthAnalysisByMonthApi = (e)=>{
    const getUrl = baseApi + '/cost/getMonthAnalysisByMonth'
    return axios.get(getUrl, {
        params:{
            year: e.year,
            month: e.month
        }
    })
}

export const getMonthAnalysisByYearApi = (year)=>{
    const getUrl = baseApi + '/cost/getMonthAnalysisByYear'
    return axios.get(getUrl, {
        params:{
            year: year
        }
    })
}

export const getYearAnalysisByYearApi = (year)=>{
    const getUrl = baseApi + '/cost/getYearAnalysisByYear'
    return axios.get(getUrl, {
        params:{
            year: year
        }
    })
}

export const getYearAnalysisByAllApi = (year)=>{
    const getUrl = baseApi + '/cost/getYearAnalysisByAll'
    return axios.get(getUrl, {
        params:{
            year: year
        }
    })
}

