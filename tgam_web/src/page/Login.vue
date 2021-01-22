


<template>
  <div id="login-container" v-loading="loading">
    <div class="hold-space-div"></div>
    <h3 id="title">集群设备管理云平台</h3>
    <el-form id="login-form"  ref="loginForm" label-position="left">
      <el-form-item prop="username">
        <el-input name="username" type="text"  placeholder="用户名"
                 >
          <i slot="prefix" class="iconfont icon-yonghuming"></i>
        </el-input>
      </el-form-item>
      <el-form-item prop="password">
        <el-input name="password"  placeholder="密码"  >
          <i slot="prefix" class="iconfont icon-mima"></i>
          <i slot="suffix" class="iconfont icon-yincangmima"  ></i>
          <i slot="suffix" class="iconfont icon-xianshimima"  ></i>
        </el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" style="width:100%;" :loading="loading" >
          登录
        </el-button>
      </el-form-item>
      <div id="tips">
        <span >服务器状态：<span v-bind:style="{'font-weight':'bold'}">待获取</span></span>
        <span >服务器状态：<span v-bind:style="{color:'#00ff00', 'font-weight':'bold'}">正常</span></span>
        <span >服务器状态：<span v-bind:style="{color:'#ff0000', 'font-weight':'bold'}">异常</span></span>
        <span>延迟：<span v-bind:style="{'font-weight':'bold'}"></span></span>
      </div>
    </el-form>
    <div class="hold-space-div"></div>
    <div  id="elegant-sentences">
      <div id="elegant-sentences-content">

      </div>
      <div id="elegant-sentences-inscribe">

      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "Login",
  data(){
    return{
      windowWidth :document.documentElement.clientWidth,
      windowHeight:document.documentElement.clientHeight
    }
  }
}
</script>

<style scoped>
#login-container {
  display: flex;
  flex-direction: column;
  justify-content: center;
  height: 100%;
  width: 100%;
  position: absolute;
  z-index: 100;
  color: #fff;
}

#title {
  min-width: 580px;
  font-size: 56px;
  text-align: center;
  font-weight: bold;
}

#login-form {
  max-width: 350px;
  min-width: 250px;
  margin: 0 auto;
  width: 25%;
}

#tips {
  display: flex;
  justify-content: space-between;
}

i {
  color: grey;
}

.hold-space-div {
  flex: 1
}

#elegant-sentences {
  padding-top: 25px;
  margin: 0 auto;
  max-width: 660px;
  min-width: 30%;
}

#elegant-sentences-content {
  width: 100%;
}

#elegant-sentences-inscribe {
  margin-top: 15px;
  width: 100%;
  text-align: right;
}
</style>
