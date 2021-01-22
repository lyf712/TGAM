<template>
  <div id="timingRecord">
    <el-container>
      <el-header style="width: 100%;height: 50px">
        <el-row>
          <el-col :span="4" style="height: 40px" >
           <h3 style="margin-top: 8px;text-align: left">设备：吴伟的iPhone</h3>

          </el-col>
          <el-col :span="8">
            <el-time-select
              placeholder="起始时间"
              disabled=true
              v-model="startTime"
              :picker-options="{
      start: '08:30',
      step: '00:15',
      end: '18:30'
    }">
            </el-time-select>
            <el-time-select
              v-model="endTime"
              disabled=true
              placeholder="结束时间"

              :picker-options="{
      start: '08:30',
      step: '00:15',
      end: '18:30'
    }">
            </el-time-select>
          </el-col>
          <el-col :span="1" style="height: 40px">
            <h3 style="margin-top: 8px;text-align: left">模式：</h3>
          </el-col>
          <el-col :span="4">

            <el-tag>驾驶</el-tag>
              <el-tag type="success">游泳</el-tag>



          </el-col>
          <el-col :span="2" style="height: 40px">
            <span ><h3 style="margin-top: 8px;text-align: left">上传间隔：</h3></span>
          </el-col>

          <el-col :span="4" style="height: 40px">
            <el-input-number value="30" disabled=true></el-input-number>
          </el-col>

        </el-row>

      </el-header>
      <el-main>
        <el-row>
          <span><h2>Raw EEG</h2></span>
        </el-row>

        <el-row :gutter="10">
          <el-col :span="3">
            <el-progress type="circle" v-bind:percentage="rawEEGValues.delta" ></el-progress>
          </el-col>
          <el-col :span="3">
            <el-progress type="circle" v-bind:percentage="rawEEGValues.theta"></el-progress>
          </el-col>
          <el-col :span="3">
            <el-progress type="circle" v-bind:percentage="rawEEGValues.low_alpha" ></el-progress>
          </el-col>
          <el-col :span="3">
            <el-progress type="circle" v-bind:percentage="rawEEGValues.high_alpha" ></el-progress>
          </el-col>
          <el-col :span="3">
            <el-progress type="circle" v-bind:percentage="rawEEGValues.low_beta" ></el-progress>
          </el-col>
          <el-col :span="3">
            <el-progress type="circle" v-bind:percentage="rawEEGValues.high_beta"></el-progress>
          </el-col>
          <el-col :span="3">
            <el-progress type="circle" v-bind:percentage="rawEEGValues.low_gamma" ></el-progress>
          </el-col>
          <el-col :span="3">
            <el-progress type="circle" v-bind:percentage="rawEEGValues.middle_gamma"></el-progress>
          </el-col>
        </el-row>

        <el-row style="height: 100px"></el-row>

        <el-row :gutter="20">
          <el-col :span="12">
            <span><h2>Attention</h2></span>
            <el-card  class="box-card">
              <div id="attentionRecord" style="width: 100%;height: 400px" >


              </div>
            </el-card>
          </el-col>
          <el-col :span="12">
            <span><h2>Meditation</h2></span>
            <el-card  class="box-card">
              <div id="meditationRecord" style="width: 100%;height: 400px" >


              </div>
            </el-card>
          </el-col>
        </el-row>



      </el-main>
    </el-container>
  </div>


</template>

<script>
export default {
  name: "TimingRecord",
  props:{
    index:'1',
    date:'2020-12-29'
  },
  data(){
  return {
    timer:{},
    timeout:0,
    recording:false,
    attentionChart :{},
    meditationChart:{},
    attentionOption:{},
    meditationOption:{},
    endTime: "13:09",
    startTime: "12:34",
    rawEEGValues:{
      theta:0,
      delta:0,
      low_alpha:0,
      high_alpha:0,
      low_beta:0,
      high_beta:0,
      low_gamma:0,
      middle_gamma:0
    }
  }
  },
  methods:{
    drawAttentionChart(){
      this.attentionChart  =  this.$echarts.init(document.getElementById("attentionRecord"))
      this.attentionOption={
        title: { text: 'Attention注意力' },
        tooltip: {},
        dataZoom:[{
          type:"slider",
          startValue:4,
          endValue:7,


        }],
        xAxis: {
          type: "category",
          data: []
        },
        yAxis: { },
        series: [{

          name: 'Esense值',
          type: 'line',
          data: []
        }]
      };
      this.attentionChart.setOption(this.attentionOption);
    },
    drawMeditationChart(){
      this.meditationChart = this.$echarts.init(document.getElementById("meditationRecord"))
      this.meditationOption={
        title:{ text:'Meditation冥想值'},
        tooltip: {},
        dataZoom:[{
          type:"slider",
          startValue:4,
          endValue:7,


        }],
        xAxis: {
          type: "category",
          data: []
        },
        yAxis: { },
        series: [{

          name: 'Esense值',
          type: 'line',
          data: []
        }]
      }
      this.meditationChart.setOption(this.meditationOption);
    },
    updateRawEEG(values){
      this.rawEEGValues.delta = values[0]
      this.rawEEGValues.theta = values[1]
      this.rawEEGValues.low_alpha = values[2]
      this.rawEEGValues.high_alpha = values[3]
      this.rawEEGValues.low_beta = values[4]
      this.rawEEGValues.high_beta = values[5]
      this.rawEEGValues.low_gamma = values[6]
      this.rawEEGValues.middle_gamma = values[7]
    },
    updateAttentionChart(timestamp,value){
      this.attentionOption.xAxis.data.push(timestamp);
      this.attentionOption.series[0].data.push(value);
      this.attentionOption.dataZoom[0].startValue=this.attentionOption.xAxis.data.length-5;
      this.attentionOption.dataZoom[0].endValue=this.attentionOption.xAxis.data.length;
      this.attentionChart.setOption(this.attentionOption);
    },
    updateMeditationChart(timestamp,value){
      this.meditationOption.xAxis.data.push(timestamp);
      this.meditationOption.series[0].data.push(value);
      this.meditationOption.dataZoom[0].startValue=this.meditationOption.xAxis.data.length-5;
      this.meditationOption.dataZoom[0].endValue=this.meditationOption.xAxis.data.length;
      this.meditationChart.setOption(this.meditationOption);
    },
    test(){

        if(this.timeout<=0){
          window.clearInterval(this.timer)
          this.$confirm('记录已结束', '提示', {
            confirmButtonText: '确定',
            type: 'warning'
          })
          return
        }
        this.updateRawEEG([
          Math.ceil(Math.random()*100),
          Math.ceil(Math.random()*100),
          Math.ceil(Math.random()*100),
          Math.ceil(Math.random()*100),
          Math.ceil(Math.random()*100),
          Math.ceil(Math.random()*100),
          Math.ceil(Math.random()*100),
          Math.ceil(Math.random()*100)])
        this.updateMeditationChart(new Date(),Math.ceil(Math.random()*255))
        this.updateAttentionChart(new Date(),Math.ceil(Math.random()*255))
      this.timeout--
    }

  },


  mounted() {
    this.drawMeditationChart();
    this.drawAttentionChart();




      }
      ,
  activated() {
    if(!this.recording){
      let that = this;
      that.$axios.post('/record/queryFlag',{

      }).then(res=> {
        if (res.data.FlagInfo[1].flag == 1){

          this.$confirm('检测到有正在进行的记录, 是否同步?', '提示', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning'
          }).then(() => {
            this.$message({
              type: 'success',
              message: '开始同步'
            });
            this.timer =  window.setInterval(this.test,1000)
            this.recording = true
            this.timeout = 60;
          })


        }else{
          this.$confirm('没检测到有正在进行的记录', '提示', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning'
          })
        }
      })


    }

  }

}


</script>

<style scoped>

.el-tag{
  float: left;
  height: 40px;
}

</style>
