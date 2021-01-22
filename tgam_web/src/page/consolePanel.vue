
<template >

  <div id="charts">
    <el-container>
      <el-header>
        <el-input v-model="date"></el-input>
      </el-header>

      <el-main>
        <el-row :gutter="20" >
          <el-col :span="12">
            <el-card  class="box-card">
              <div id="attention" style="width: 100%;height: 400px" >


              </div>
            </el-card>
          </el-col>
          <el-col :span="12" >
            <el-card  class="box-card">
              <div id="meditation" style="width: 100%;height: 400px" ></div>
            </el-card>
          </el-col>
        </el-row>

        <el-row :gutter="20" style="margin-top: 20px" >
          <el-col>
            <el-card class="box-card">
              <div id="raw_eeg" style="width: 100%;height: 400px" ></div>
            </el-card>
          </el-col>
        </el-row>
      </el-main>

    </el-container>
  </div>
</template>



<script>



export default {
  name: "consolePanel",
  data(){
    return{
      date:'2020-12-29',
      attentionChart :{},
      meditationChart:{},
      rawEEGChart:{},
      attentionOption:{},
      meditationOption:{},
      rawEEGOption:{},
      selectTimestamp:""
    }
  },
  methods:{
    drawAttentionChart(){
      this.attentionChart  =  this.$echarts.init(document.getElementById("attention"))
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
        this.meditationChart = this.$echarts.init(document.getElementById("meditation"))
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
    drawRawEEGDataChart(){
      this.rawEEGChart = this.$echarts.init(document.getElementById("raw_eeg"))
      this.rawEEGOption = {
        title:{ text:'RAW-EEG原始脑电数据'},
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

          name: 'raw-eeg theta',
          type: 'line',
          data: []
        },{

          name: 'raw-eeg delta',
          type: 'line',
          data: []
        },{

          name: 'raw-eeg low-alpha',
          type: 'line',
          data: []
        },{

          name: 'raw-eeg high-alpha',
          type: 'line',
          data: []
        },{

          name: 'raw-eeg low-beta',
          type: 'line',
          data: []
        },{

          name: 'raw-eeg high-beta',
          type: 'line',
          data: []
        },{

          name: 'raw-eeg low-gamma',
          type: 'line',
          data: []
        },{

          name: 'raw-eeg middle-gamma',
          type: 'line',
          data: []
        }]

      }
      this.rawEEGChart.setOption(this.rawEEGOption)
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
    updateRawEEGChart(timestamp,values){
      this.rawEEGOption.xAxis.data.push(timestamp);
      for(let i =0 ;i<values.length ;i++){
        this.rawEEGOption.series[i].data.push(values[i])
      }
      this.rawEEGOption.dataZoom[0].startValue=this.rawEEGOption.xAxis.data.length-5;
      this.rawEEGOption.dataZoom[0].endValue=this.rawEEGOption.xAxis.data.length;
      this.rawEEGChart.setOption(this.rawEEGOption);
    }
  },
  mounted() {
    console.log(this.$store.state.info)
    this.drawAttentionChart();
    this.drawMeditationChart();
    this.drawRawEEGDataChart();
    let that = this;
    let loading = this.$loading({
      lock: true,
      text: '请求数据中'
    });
    that.$axios.post('record/queryData',{
      index:this.$store.state.info.index,
      date:this.$store.state.info.date,
      username:'Tom22'
    }).then(res=>{

      res.data.data.forEach((item,index,array)=>{
        let attention = item.attention;
        let meditation = item.meditation;
        let timestamp = item.timestamp;
        let raw_eeg = item.raw_eeg;
        this.updateRawEEGChart(timestamp,raw_eeg)
        this.updateAttentionChart(timestamp,attention)
        this.updateMeditationChart(timestamp,meditation)
      })
      loading.close();

    })
  }


}



</script>

<style scoped>

.box-card{
  height: 400px;
}

</style>
