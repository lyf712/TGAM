<template>

  <div class="content_container">
    <el-container>
      <el-main>
        <div class="form_div">
          <el-card class="card">
            <el-form class="form" label-width="70px" style="text-align: left" label-position="left" >
            <h2>Sign In</h2>
              <br>
              <br>
            <el-form-item label="用户名" required>
              <el-input placeholder="用户名" class="input" v-model="userName"></el-input>
            </el-form-item>
            <el-form-item label="密码" required>
              <el-input placeholder="密码" class="input" v-model="password"></el-input>
            </el-form-item>
              <el-form-item label="手机号" required>
                <el-input placeholder="手机号" class="input_phone" v-model="phoneNumber"></el-input>
                <el-button class="button" type="info" @click="sendMessage">{{this.text}}</el-button>
              </el-form-item>
              <el-form-item label="验证码" required>
                <el-input placeholder="验证码" class="input_phone" v-model="confirm"></el-input>
              </el-form-item>
              <el-form-item>
                <el-button type="primary" @click="signIn">注册</el-button>
              </el-form-item>
            </el-form>
          </el-card>

        </div>

      </el-main>
    </el-container>
  </div>

</template>

<script>
export default {
  name: "SignIn",
  computed:{
    divClass:'content_container'
  },
  data(){
  return{
    timer:{},
    total:60,
    text:'发送短信',
    send:false,
    userName:'',
    password:'',
    confirm:'',
    phoneNumber:''
  }
  },
  methods:{
    signIn(){


      let that = this
      that.$axios.post('/verifyCode',{
        number:this.phoneNumber,
        code:this.confirm
      }).then(res=>{
        if(res.data[1].code==1){
          that.$axios.post('/userManager/insertOne',{
            userName:this.userName,
            password:this.password
          }).then(res=>{
              if(res.data[0].code==1){
                this.$confirm('注册成功是否跳转登录', '提示', {
                  confirmButtonText: '确定',
                  cancelButtonText: '取消',
                  type: 'warning'
                }).then(() => {
                    this.$router.push('/')
                  })
              }else{
                this.$notify({
                  title:'注册失败',
                  type:"warning"
                });
              }
          })
        }else{

        }

      })

    },
    sendMessage(){
     if(!this.send){
        let that = this
        that.$axios.post('/loginBySMS',{
          number:this.phoneNumber
        })
        this.total = 60;
        this.send = true;
       this.timer= window.setInterval(this.textChange,1000);

      }


    },
    textChange(){
      if(this.total>=0){
        this.text = '重新发送'+'（'+this.total+'秒后'+'）'
        this.total --;
      }else {
        window.clearInterval(this.timer)
        this.send = false;
        this.text = '发送短信'
      }

    }
  }
}
</script>

<style scoped>

.form_div{
  display: flex;
  flex-direction: column;
  justify-content: center;
  min-width: 200px;
  min-height: 400px;
  position: absolute;
  background-color: red;
  height: 90%;
  width: 90%;
  top: 5%;
  left: 5%;



}

.content_container{
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
.card{
  width: 100%;
  height: 100%;
  min-height: 400px;
  min-width: 200px;
  margin: 0 auto;
}

.input_phone{
  width: 70%;
  height: 100%;
  float: left;
}

.form{
  width: 70%;
  height: 80%;
  margin-left: 15%;


}

.button{


}

</style>
