import { createRouter, createWebHistory } from 'vue-router';
import {routerbefore} from "@/js/routerbefore";
import PhoneSelect from "@/pages/select/PhoneSelect.vue";
import DesktopSelect from "@/pages/select/DesktopSelect.vue";
import PhoneDead from "@/pages/dead/PhoneDead.vue";
import DesktopDead from "@/pages/dead/DesktopDead.vue";
import PhoneMyBooks from "@/pages/mybooks/PhoneMyBooks.vue";
import DesktopMyBooks from "@/pages/mybooks/DesktopMyBooks.vue";
import PhoneMyGit from "@/pages/mygit/PhoneMyGit.vue";
import DesktopMyGit from "@/pages/mygit/DesktopMyGit.vue";
import PhoneMyMusic from "@/pages/mymusic/PhoneMyMusic.vue";
import DesktopMyMusic from "@/pages/mymusic/DesktopMyMusic.vue";
import PhoneMyPlan from "@/pages/myplan/PhoneMyPlan.vue";
import DesktopMyPlan from "@/pages/myplan/DesktopMyPlan.vue";
import PhoneMySpace from "@/pages/myspace/PhoneMySpace.vue";
import PhoneMyTravel from "@/pages/mytravel/PhoneMyTravel.vue";
import PhoneMyVideos from "@/pages/myvideos/PhoneMyVideos.vue";
import DesktopMyVideos from "@/pages/myvideos/DesktopMyVideos.vue";
import PhoneMe from "@/pages/me/PhoneMe.vue";
import DesktopMe from "@/pages/me/DesktopMe.vue";
import DesktopMySpace from "@/pages/myspace/DesktopMySpace.vue";
import PasswordPage from "@/pages/manager/PasswordPage.vue";
import ManagerSelectPage from "@/pages/manager/ManagerSelectPage.vue";
import PhoneMyVideosDeep from "@/pages/myvideos/PhoneMyVideosDeep.vue";
import DesktopMyTravel from "@/pages/mytravel/DesktopMyTravel.vue";
// import HelloPage from "@/pages/hello/HelloPage.vue";
import DeskBolg from "@/pages/blog/DeskBolg.vue";
import PhoneBlog from "@/pages/blog/PhoneBlog.vue";
import DeskOtherBolg from "@/pages/otherblog/DeskOtherBolg.vue";
import PhoneOtherBlog from "@/pages/otherblog/PhoneOtherBlog.vue";

const routes = [
    //初始化页面
    // {
    //     path: '/desktop/',
    //     component: HelloPage
    // },
    // {
    //     path: '/phone/',
    //     component: HelloPage
    // },
    {
        path: '/',
        redirect: '/me'
    },
    //Blog
    {
        path: '/desktop/blog',
        component: DeskBolg
    },
    {
        path: '/phone/blog',
           component: PhoneBlog
    },
    //other blog
    //Blog
    {
        path: '/desktop/otherblog',
        component: DeskOtherBolg
    },
    {
        path: '/phone/otherblog',
        component: PhoneOtherBlog
    },
    //选择页面
    {
        path:'/phone/select',
        component: PhoneSelect
    },
    {
        path: '/desktop/select',
        component: DesktopSelect
    },
    //墓志铭
    {
        path:'/phone/dead',
        component: PhoneDead
    },
    {
        path:'/desktop/dead',
        component:  DesktopDead
    },
    //我
    {
        path:'/phone/me',
        component: PhoneMe
    },
    {
        path: '/desktop/me',
        component: DesktopMe
    },
    //书架
    {
        path:'/phone/mybooks',
        component:PhoneMyBooks
    },
    {
        path:'/desktop/mybooks',
        component:DesktopMyBooks
    },
    //我的开源
    {
        path:'/phone/mygit',
        component:PhoneMyGit
    },
    {
        path:'/desktop/mygit',
        component:DesktopMyGit
    },
    {
        path: '/phone/myvideosdeep',
        redirect: '/mygit'
    },
    //音乐
    {
        path:'/phone/mymusic',
        component:PhoneMyMusic
    },
    {
        path:'/desktop/mymusic',
        component:DesktopMyMusic
    },
    //挑战板
    {
        path:'/phone/myplan',
        component:PhoneMyPlan
    },
    {
        path:'/desktop/myplan',
        component:DesktopMyPlan
    },
    //我的空间
    {
        path:'/phone/myspace',
        component:PhoneMySpace
    },
    {
        path:'/desktop/myspace',
        component:DesktopMySpace
    },
    //旅行日志
    {
        path:'/phone/mytravel',
        component:PhoneMyTravel
    },
    {
        path:'/desktop/mytravel',
        component:DesktopMyTravel
    },
    //视频
    {
        path:'/phone/leaveMsg',
        component:PhoneMyVideos
    },
    {
        path:'/desktop/leaveMsg',
        component:DesktopMyVideos
    },
    {
        path: '/phone/myvideos/deep',
        component: PhoneMyVideosDeep
    },
    //管理员
    {
        path: '/phone/managerpassword',
        component: PasswordPage
    },
    {
        path: '/phone/managerselect',
        component: ManagerSelectPage
    }
];

const router = createRouter({
    history: createWebHistory(),
    routes
});

router.beforeEach((to, from, next) => {
    routerbefore(to.path,next)
});

export default router;
