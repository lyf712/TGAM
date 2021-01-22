<template>
  <div id="UserPage">
    <el-form ref="form" :model="User" label-width="80px" size="mini">
      <el-form-item label="用户名" style="width: 30%">
        <el-input v-model="User.name" v-bind:disabled="componentControl.notAllowModify" ></el-input>
      </el-form-item>
      <el-form-item label="地区">
        <el-select v-model="User.region" placeholder="请选择区域" style="float: left" v-bind:disabled="componentControl.notAllowModify">
          <el-option label="区域一" value="shanghai"></el-option>
          <el-option label="区域二" value="beijing"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="生日">
        <el-col :span="4">
          <el-date-picker type="date"  v-model="User.birthday" style="width: 100%;" v-bind:disabled="componentControl.notAllowModify" @change="caculateAge" ></el-date-picker>
        </el-col>

      </el-form-item>

      <el-form-item label="年龄" >
        <el-input-number v-model="User.age" controls-position="right"  :min="0" :max="200" style="float: left"  :disabled=true></el-input-number>

      </el-form-item>
      <el-form-item label="职业" >
        <el-input v-model="User.job"  style="float: left;width: 10%"  :disabled=true></el-input>

      </el-form-item>

      <el-form-item label="身高" >
        <el-input-number v-model="User.height" controls-position="right"  :min="0" :max="250" style="float: left"  v-bind:disabled="componentControl.notAllowModify"></el-input-number>
      </el-form-item>
      <el-form-item label="体重" >
        <el-input-number v-model="User.weight" controls-position="right"  :min="30" :max="1000" style="float: left"  v-bind:disabled="componentControl.notAllowModify"></el-input-number>
      </el-form-item>
      <el-form-item size="large">
        <el-button  style="float: left" @click="clickRefreshButton">刷新</el-button>
        <el-button  style="float: left" @click="modify">修改</el-button>
        <el-button type="primary" @click="save" style="float: left" v-show="componentControl.showSave">保存</el-button>

      </el-form-item>
    </el-form>

  </div>
</template>

<script>
export default {
name: "UserPage",
  data() {
    return {
      componentControl:{
        showSave: false,
        notAllowModify:true
      },
      User: {

        name: '',
        region: '中国',
        birthday: '',
        height: 0,
        weight: 0,
        age:0,
        job:""


      },
      loading:{},
      savingLoading:{},
      receiveResponse:true,
      receiveSaveResponse:true

    };
  },
  methods:{
    clickRefreshButton(){
      if(this.receiveResponse) {
        this.receiveResponse = false;
        this.loading = this.$loading({
          lock: true,
          text: '请求数据中'
        });
        setTimeout(() => {
          this.loading.close();
          if (!this.receiveResponse) {
            this.$notify({
              title: '请求失败，请检查网络',
              type: "warning"
            })
            this.receiveResponse = true
          }

        }, 4000);

        this.getUser();
      }else{
        this.$notify({
          title:'正在刷新，请勿重复点击',
          type:"warning"
        })
      }
    },
    modify(){
      this.componentControl.notAllowModify=false;
      this.componentControl.showSave = true;

    },
    save(){

      if(this.receiveSaveResponse) {
        this.receiveSaveResponse = false;
        this.componentControl.notAllowModify=true;
        this.componentControl.showSave = false;

        this.savingLoading = this.$loading({
          lock: true,
          text: '正在保存'
        });
        setTimeout(() => {
          this.loading.close();
          if (!this.receiveSaveResponse) {
            this.$notify({
              title: '保存失败，请检查网络',
              type: "warning"
            })
            this.receiveSaveResponse = true
          }

        }, 5000);

        let that = this
        that.$axios.post('/userManager/updateUser', {
          userName: this.User.name,
          job: this.User.job,
          weight: this.User.weight,
          height: this.User.height,
          birthday: this.User.birthday,
          age: this.User.age
        }).then(res => {
          that.receiveSaveResponse = true;
          that.$notify({
            title: '保存成功',
            type: "success"
          })
          this.clickRefreshButton();
        })
      }else{
        this.$notify({
          title:'正在保存，请勿重复点击',
          type:"warning"
        })
      }

    },
    caculateAge(){
      let currentData = new Date();
      let birthdayDate = new Date(this.User.birthday);
      let timeInterval = currentData.getTime() - birthdayDate.getTime();
      this.User.age =  parseInt(timeInterval/(1000*60*60*24*365));
    },
    getUser(){
      let that = this;
      that.$axios.post('/userManager/queryUserByName',{
        username:this.$store.state.userName
      }).then(
        res=>{
          console.log(res.data[0].user)
          this.User.name = res.data[0].user.userName
          this.User.age = res.data[0].user.age
          this.User.job = res.data[0].user.job
          this.User.birthday = res.data[0].user.birthday
          this.User.height = res.data[0].user.height
          this.User.weight = res.data[0].user.weight


          this.loading.close()
          this.receiveResponse = true;
        }
      )
    }
  },
  mounted() {
      this.clickRefreshButton();

  }

}
</script>

<style scoped>

</style>
