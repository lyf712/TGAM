<template>
  <div id="DataShow" >
    <el-row>
      <el-calendar v-model="value">
        <template slot="dateCell" slot-scope="{date,data}">
          <p :class="data.isSelected ? 'is-selected' : ''">
            {{data.day.split('-').slice(1).join('-')}} {{data.isSelected?queryRecordInfo():''}}
          </p>
        </template>
      </el-calendar>
    </el-row>
   <el-row :gutter="30">
     <el-col :span="10" :offset=1>
       <el-card class="box-card">
         <div slot="header" class="clearfix" style="text-align: left">
           <h2 >{{this.value.getFullYear()+'年'+(this.value.getMonth()+1)+"月"+this.value.getDate()+'日'}}</h2>
           <p>{{describe}}</p>

         </div>
         <div v-for="o in 4" :key="o" class="text item" style="text-align: left" >

         </div>
       </el-card>
     </el-col>

     <el-col :span="13">

         <el-timeline style="width: 500px;float:right;margin-right: 50px;text-align: left">
           <el-timeline-item
             v-for="(item,index) in activities"
             :key="index"
             :timestamp="item.timestamp"
           >
             <el-card class="record_card" >
               <div slot="header" class="clearfix">
                 <span>{{activities[index].title}}</span>
                 <el-button style="float: right; padding: 3px 0" type="text" @click="clickCardAtIndex(index)">查看记录</el-button>
               </div>
               <div  class="text item">
                 <p>{{'开始时间：'+activities[index].beginDate}}</p>
                 <p>{{'结束时间：'+activities[index].endDate}}</p>
                 <p>{{'模式：'+activities[index].pattern}}</p>
               </div>

             </el-card>
           </el-timeline-item>
         </el-timeline>


     </el-col>
   </el-row>





  </div>
</template>

<script>

import TimingRecord from "./TimingRecord";
  export default {
    data() {
      return {
        describe:'无记录',
        value: new Date(),
        reverse: true,
        activities: [
        ]
      }
    },
    components:{
      TimingRecord
    },
    methods:{
      test(){


        return '';
      },
      queryRecordInfo(){
        this.activities = [];
        let that = this;
        that.$axios.post('/record/queryDataInfo',{
            date:this.value.getFullYear()+'-'+(this.value.getMonth()+1)+"-"+this.value.getDate(),
            username:'Tom22'
        }).then(res=>{
            res.data.info.forEach((item,index,array)=>{
              this.activities.push({
                beginDate:item.beginDate,
                endDate:item.endDate,
                pattern: item.pattern,
                title: 'APP记录',
                timestamp: item.startTime
              })
            });
            this.queryRecordNum();
        })
      },
      queryRecordNum(){
        if(this.activities.length>0)
          this.describe='当日有'+this.activities.length+'条记录'
        else
          this.describe= '无记录';
      },
      clickCardAtIndex(index){
        this.$store.dispatch('a_dataShow',{
          index:index,
          date:this.value.getFullYear()+'-'+(this.value.getMonth()+1)+"-"+this.value.getDate(),
          startTime:this.activities[index].beginDate,
          endTime:this.activities[index].endDate,
          pattern:this.activities[index].pattern
        })
       this.$router.push({
         name:'consolePanel'
       })
      }
    }
  }
</script>

<style scoped>

.record_card{
  margin: 10px;
  width: 80%;
}

#timestamp{
  font-size: 20px;
}

.is-selected{
  color: cornflowerblue;
}

</style>
