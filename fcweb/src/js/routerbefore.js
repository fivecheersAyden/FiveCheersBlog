import { v4 as uuidv4 } from 'uuid';
import router from "@/router/router";
import {getIpAddress} from "@/js/apihelper";
import { checkManager } from './jshelper';
import { message } from 'ant-design-vue';
/**
 * 路由前检查函数
 * @param path
 * @param next
 */
export const routerbefore = (path,next)=>{
    //获取UUID，ip，time
    const UUID = localStorage.getItem('UUID')
    const ipAddress = localStorage.getItem('ipAddress')
    const visitTime = localStorage.getItem('visitTime')
    //没有UUID则赋予UUID
    if (!UUID){
        localStorage.setItem('UUID',uuidv4())
    }
    //没有ipAddress则赋予ipAddress
    if (!ipAddress){
        setIpAddress()
    }
    //没有visitTime则赋予visitTime
    if (!visitTime){
        setVisitTime()
    }
    //visitTime过期则更新ipAddress和visitTime
    else if (visitTime !== getNowTime()){
        setVisitTime()
        setIpAddress()
    }
    //检查是否管理员
    if(!checkManager()){
        const managerPathes = ['/mybooks', '/dead']
        if(managerPathes.includes(path)){
            message.warn('权限不足')
            return
        }
    }
    //检查是手机还是电脑并跳转
    if (path.match('/desktop/*') || path.match('/phone/*')){
        next()
    }else{
        const devicetype = (window.innerWidth || document.documentElement.clientWidth || document.body.clientWidth) < 768 ? 'phone' : 'desktop';
        router.replace(`/${devicetype}${path}`)
    }
}

const setIpAddress = ()=>{
    const postResult = getIpAddress()
    postResult.then(response=>{
        const ipAddress =
            'location：'+response.data.country+response.data.province+response.data.area+response.data.city+
            ' ip：'+response.data.ip+
            ' isp：'+response.data.isp
        localStorage.setItem('ipAddress',ipAddress)
    }).catch(e=>{
        console.log('获取用户ip出错'+e)
    })
}

const setVisitTime = ()=>{
    localStorage.setItem('visitTime',getNowTime())
}

const getNowTime = ()=>{
    const date = new Date()
    const year = date.getFullYear()
    const month = String(date.getMonth() + 1).padStart(2,'0')
    const day = String(date.getDate()).padStart(2, '0')
    const nowTime = `${year}-${month}-${day}`
    return nowTime
}