import Vue from 'vue'
import Router from 'vue-router'
import layout from "../page/layout";
import DataShow from "../page/DataShow";
import Setting from "../page/Setting";
import consolePanel from "../page/consolePanel";
import UserPage from "../page/UserPage";
import TimingRecord from "../page/TimingRecord";
import Login from "../page/Login";
import LOGIN from "../components/LOGIN";
import SignIn from "../page/SignIn";

Vue.use(Router)

export default new Router({

  base:'/tgam_web/',
  routes: [
    {
      path:'/signIn',
      name:'signIn',
      component: SignIn
    },
    {
      path: '/',
      name: 'login',
      component:LOGIN
    },
    {
      path: '/DataShow/consolePanel',
      name: 'consolePanel',
      component: consolePanel,
      meta:{
        keepAlive :true
      }
    },
    {

      path: '/content',
      name: 'layout' ,
      component: layout,
      children:[
        {
          path: 'dataShow',
          name: 'dataShow',
          component: DataShow,
          meta:{
            keepAlive :true
          }
        },

        {
          path: 'setting',
          name: 'setting',
          component: Setting

        },
        {
          path: 'userPage',
          name: 'userPage',
          component: UserPage
        },
        {
          path: 'timingRecord',
          name: 'timingRecord',
          component: TimingRecord,
          meta:{
            keepAlive :true
          }
        }
      ]
    },



  ]
})
