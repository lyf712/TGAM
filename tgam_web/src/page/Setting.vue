<template>
  <div id="setting">
    <el-form ref="form" :model="sizeForm" label-width="150px" size="mini">
      <el-form-item label="允许移动端同步数据" style="width: 30%">
        <el-switch></el-switch>
      </el-form-item>

    </el-form>

  </div>
</template>

<script>
export default {
name: "Setting",
  data() {
    return {
      sizeForm: {
        name: '',
        region: '',
        date1: '',
        date2: '',
        delivery: false,
        type: [],
        resource: '',
        desc: ''
      }
    };
  },
  methods: {
    onSubmit() {
      console.log('submit!');
    }
  }

}
</script>

<style scoped>

</style>
