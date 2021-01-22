<template>
<div>

  <el-container class="home_container">


    <el-container class="home_bottom_container">

      <el-aside class="home_aside"  :width="aside_width">
        <el-menu  class="aside_menu" :collapse="aside_collapse" @select="">
          <el-submenu index="1">
            <template slot="title">
              <i class="el-icon-user"></i>
              <span slot="title">我的账户</span>

            </template>
            <el-menu-item-group>
              <el-menu-item>
                <el-avatar></el-avatar>
                <span >{{this.$store.state.userName}}</span>
              </el-menu-item>
                <el-menu-item index="1-1" @click="click('Setting','设置')">设置</el-menu-item>
                <el-menu-item index="1-2" @click="click('UserPage','账号')">账号</el-menu-item>


            </el-menu-item-group>
          </el-submenu>
          <el-submenu index="2">
            <template slot="title">
              <i class="el-icon-menu"></i>
              <span slot="title">我的记录</span>
            </template>
            <el-menu-item-group>
                <el-menu-item index="2-1" @click="click('DataShow','历史记录')">历史记录</el-menu-item>
                <el-menu-item index="2-2" @click="click('TimingRecord','实时控制面板')">实时控制面板</el-menu-item>
            </el-menu-item-group>




          </el-submenu>

        </el-menu>
      </el-aside>

      <el-main class="home_main">
        <el-container class="main_container" >
          <el-header class="main_header" :height="main_header_height">

              <el-tabs v-model="activeName" type="card" closable class="main_tabs" @tab-remove="handleRemove" @tab-click="handleClick">
                <el-tab-pane
                  v-for="(item, index) in editableTabs"
                  :key="item.name"
                  :label="item.title"
                  :name="item.name"
               >

                </el-tab-pane>
            </el-tabs>
          </el-header>

          <el-main class="main_container_main" style="padding: 0">
            <keep-alive>
              <component :is="currentComponent" @routerToDataShow="routeToDataShow"></component>
            </keep-alive>

            <router-view></router-view>
          </el-main>
        </el-container>
      </el-main>
    </el-container>
  </el-container>
</div>

</template>

<script>

import DataShow from "./DataShow";
import Setting from "./Setting";
import TimingRecord from "./TimingRecord";
import UserPage from "./UserPage";
import consolePanel from "./consolePanel";

export default {
  data() {
    return {
      currentComponent:"DataShow",
      activeName:'DataShow',
      aside_collapse:true,
      aside_width:'65px',
      main_header_height:'50px',
      editableTabsValue: '2',
      editableTabs: [{
        title: '首页',
        name: 'DataShow',
        component:'DataShow',
        closable:false
      }
    ]
    }
  },
  components:{
    DataShow,
    Setting,
    UserPage,
    TimingRecord
  }
  ,
  methods: {
    addTab(title,component){
      this.editableTabs.push({
        title:title,
        name: component,
        component: component
      })
        this.activeName = title
        this.currentComponent = component
    },
    handleRemove(name){
      console.log(name)
      this.editableTabs.forEach((item,index,arr)=>{
        if(item.name == name){
          console.log(index)
          arr.splice(index,1)

          if(this.currentComponent == item.component){
            this.activeName =arr[index-1].name;
            this.currentComponent = arr[index-1].component
          }
        }
      })


    },
    handleClick(tab){

      let index = this.IndexForTitle(this.activeName)
      this.currentComponent = this.editableTabs[index].component;
    },
    openAsideMenu(){
      this.aside_width = '200px'
      this.aside_collapse = false
    },
    closeAsideMenu(){
      this.aside_width = '65px'
      this.aside_collapse = true
    },
    handleSelect(index,indexPath){

    },
    click(component,title){
    //  console.log(this.IndexForTitle(component));
      if(this.IndexForTitle(component) == -1){
        this.addTab(title,component);
      }else{
        this.activeName = component
        this.currentComponent = component
      }

    },
    IndexForTitle(name){
      let result = -1;
      this.editableTabs.forEach((item,index,arr)=>{
        //console.log('name =' + item.name)
        if(item.name == name){
         result = index;

        }

      });
      return result

    },
    routeToDataShow(index,date){
      console.log('dsds')
      this.addTab("数据记录",'TimingRecord');
    }
  }
}
</script>

<style>
  .home_container {
    height: 100%;
    position: absolute;
    top: 0px;
    left: 0px;
    width: 100%;
  }
  .home_main{


    padding: 0;

  }



  .aside_menu{
    height: 100%;

  }



  .main_header{
    padding: 0;
    margin: 0;
    width: 100%;

  }

.main_container{

  width: 100%;
  height: 100%;
  padding: 0;
  margin: 0;

}


</style>
