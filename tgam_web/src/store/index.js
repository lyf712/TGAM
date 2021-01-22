import Vue from 'vue'
import Vuex from 'vuex'
import fa from "element-ui/src/locale/lang/fa";

//挂载Vuex
Vue.use(Vuex)

//创建VueX对象
const store = new Vuex.Store({
  state:{
    userName:"",
    password:"",
    online:false,
    token:"",
    info:{}
  },
  mutations:{
    login(state,userInfo){
      console.log(userInfo);
      state.userName = userInfo.userName;
      state.password = userInfo.password;
      state.token = userInfo.token;
      state.online =userInfo.online;
    },
    logout(state){
      state.userName = '';
      state.password = '';
      state.token = '';
      state.online = false;
    },
    dataShow(state,info){
      state.info = info
    }
  },
  actions:{
    a_login(context,userInfo){
      console.log(userInfo);
      context.commit("login",userInfo
        );
    },
    a_logout(context){
      context.commit("logout");
    },
    a_dataShow(context,info){
      context.commit('dataShow',info)
    }
  }
})

export default store
