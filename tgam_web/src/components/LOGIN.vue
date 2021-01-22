<template>
  <el-container id="content_container">
    <el-header id="content_container_header">
      <el-row>
        <el-col :span="12" class="header_left">
          <h1 style="line-height: 100%">Stalagmite工作室</h1>
        </el-col>
        <el-col :span="12" class="header_right">
          <div class="header_menu" >
            <el-link icon="el-icon-s-management">文档</el-link>
              <el-link  icon="el-icon-download">App下载</el-link>
              <el-link icon="el-icon-s-promotion">联系我们</el-link>



          </div>
        </el-col>
      </el-row>


    </el-header>
    <el-main id="content_container_main">
      <el-row>
        <el-col :span="16">
          <div class="notice">
            <div id="div_image">
              <img id="app_image" src="../../src/assets/App.png">
            </div>

            <div class="introduction">
              <span style="font-weight: bolder;font-size: 30px">思·念</span>
              <h3>即将上架App Store</h3>
              <el-collapse >
              <el-collapse-item title="让思念看得见" name="1">
                <div>让手机遇见你的大脑——通过蓝牙与你的脑电穿戴设备连接,采集你的注意力值，眨眼值，冥想值和原始脑电数据</div>
                <div>看见你的所想，所思，所念——直观的通过图表观察脑电数据</div>
                <div>不需要专业知识，你的状况我们用三原色告诉你</div>
              </el-collapse-item>
              <el-collapse-item title="世界那么大，我陪你去看看" name="2">
                <div>嘿，你只管开车，剩下的交给我们——驾驶场景下通过脑电数据进行疲劳监测，给予用户操作建议或安全提示</div>
                <div>无论是睡觉，吃饭，打球，开车，约会，你的故事我都能记得——多场景识别，智能场景学习</div>
              </el-collapse-item>
              <el-collapse-item title="七月的风，八月的雨，总有人在远方思念遥远的你" name="3">
                <div>别让她在家里看到你开车打瞌睡哦——web端与移动端实时同步记录数据</div>
              </el-collapse-item>

            </el-collapse>
             <span style="font-size: 10px;font-weight: lighter">功能以最终上架的版本为准，现已支持连接采用神念科技TGAM脑电芯片的穿戴产品，OpenBCI正在测试中</span>
            </div>
          </div>

        </el-col>
        <el-col  :span="8">
          <div class="login_div">
            <el-card id="login_card" shadow="always" v-loading="loading">
              <el-form>
                <el-form-item label-width="auto" >
                </el-form-item>
                <h2>Sign Up</h2>
                <el-form-item>
                  <el-input placeholder="用户名" v-model="userName"></el-input>
                </el-form-item>
                <el-form-item>
                  <el-input placeholder="密码" v-model="password" show-password></el-input>
                </el-form-item>
                <el-form-item>
                  <el-button type="primary" @click="login" >登录</el-button>
                </el-form-item>
                <el-form-item>
                  <el-button type="primary" plain @click="signIn">没有账号？立即注册</el-button>
                </el-form-item>
                <el-form-item >

                </el-form-item>
              </el-form>
              <el-link type="info" id="find_pwd">找回密码</el-link>
            </el-card>
          </div>
        </el-col>
      </el-row>


    </el-main>


  </el-container>


</template>

<script>


export default {
  name: "LOGIN",
  data(){
    return{
        userName:"",
        password:"",
        loading:false
    }
  },
  methods:{
    signIn(){
      this.$router.push('/signIn')
    },
    login(){
        this.loading= true;
       if(this.userName != null && this.password!=null){
         let that = this;
         that.$axios.post("/login",{

             username:this.userName,
             password:this.password,
            deviceType:'pc'

         },{
           headers: {


           }
         }).then(
           res=>{
             let message = res.data[0].message;
             let code = res.data[2].code;
             let type = '';
             if(code == 0){
               type = 'warning'
             }else{
               type = 'success'
               console.log(this.userName)
               console.log(this.password)
               this.$store.dispatch("a_login",{
                   userName:this.userName,
                   password:this.password,
                   token:'',
                   online:true
                 }

               )
               this.$router.push('/content')

             }

             this.$notify({
               title:message,
               type:type
             });
           }
         )
       }
       else{

       }
     }
  }
}
</script>

<style scoped>

#content_container{
  width: 100%;
  min-height: 568px;
  height: 100%;
  background-color: white;
  position: absolute;
  top: 0;
  bottom: 0;
  left: 0;
  right: 0;
}

#content_container_header{

  background-color: white;


}

.header_left{
  display: flex;
  flex-direction: row;
  justify-content: left;
}

.header_right{
  display: flex;
  flex-direction: row-reverse;
  justify-content: right;
}

#login_card{

  margin: 25% 5% auto auto;
  background-color:white;
  min-width: 300px;
  min-height: 400px;
  width: 20%;
  height: 30%;
}

.login_div{
  display: flex;
  flex-direction: column;
  justify-content: center;
}

.el-button{
  width: 100%;
}

#find_pwd{
  float: right;
}
#app_image{
  width: 300px;

}


.notice{
  margin-top: 10%;
  width: 100%;
  display: flex;
  flex-direction:row;
  justify-content: left;
}

.introduction{
  margin-left: 50px;
  width: 50%;
  text-align: left;
  line-height: 50px;
}

#div_image{
  margin-left: 10%;
}

.el-footer{

}

.el-link--inner{
  margin-right: 20px;
}

.header_menu{
  line-height: 64px;
}

</style>
