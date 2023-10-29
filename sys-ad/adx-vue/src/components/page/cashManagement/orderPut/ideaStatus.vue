<template>
    <div class="table">
        <div class="container">
            <div class="handle-box">
                <el-input v-model="IdeaID" placeholder="请输入创意ID" class="handle-input mr10"></el-input>
                <el-button type="primary" icon="search" @click="search">搜索</el-button>
            </div>
           <el-form ref="form" :model="form" label-width="100px" :label-position="labelPosition">
                    <el-form-item label="创意名称">
                       <span class='blue'>{{form.entityName}}</span>
                    </el-form-item>
                    <el-form-item label="投放名称">
                       <span class='blue'>{{form.putId}}</span>
                    </el-form-item>
                    <el-form-item label="订单名称">
                       <span class='blue'>{{form.orderId}}</span>
                    </el-form-item>
                    <el-form-item label="广告位名称">
                       <span class='blue'>{{form.positionName}}</span>
                    </el-form-item>
                    <el-form-item label="投放日期">
                       <span class='green'>{{form.date}}</span>
                    </el-form-item>
                    <el-form-item label="订单状态">
                       <span class='green' v-if='this.form.orderState == 1'>ok</span>
                       <span class='red' v-if='this.form.orderState != 1'>err</span>
                    </el-form-item>
                    <el-form-item label="订单运行状态">
                       <span class='green' v-if='this.form.orderRun == 1'>ok</span>
                       <span class='red' v-if='this.form.orderRun != 1'>err</span>
                    </el-form-item>
                    <el-form-item label="投放时间状态">
                       <span class='green' v-if='this.form.timeIntervalState == 1'>ok</span>
                       <span class='red' v-if='this.form.timeIntervalState != 1'>err</span>
                    </el-form-item>
                    <el-form-item label="投放运行状态">
                       <span class='green' v-if='this.form.putRun == 1'>ok</span>
                       <span class='red' v-if='this.form.putRun != 1'>err</span>
                    </el-form-item>
                    <el-form-item label="投放时间段">
                       <span class='green'>{{form.timeInterval}}</span>
                    </el-form-item>
                    <el-form-item label="投放状态">
                       <span class='green' v-if='this.form.putState == 1'>ok</span>
                       <span class='red' v-if='this.form.putState != 1'>err</span>
                    </el-form-item>
                    <el-form-item label="创意状态">
                       <span class='green' v-if='this.form.entityState == 1'>ok</span>
                       <span class='red' v-if='this.form.entityState != 1'>err</span>
                    </el-form-item>
                    <el-form-item label="客户审核状态">
                       <span class='green' v-if='this.form.adverAudit == 1'>ok</span>
                       <span class='red' v-if='this.form.adverAudit != 1'>err</span>
                    </el-form-item>
                 
            </el-form>
        </div>


    </div>
</template>

<script>
import { entitycheckEntity } from "@/api/Api.js";
export default {
  name: "basetable",
  data() {
    return {
      form:{
          entityName:'',
          putId:'',
          orderId:'',
          date:'',
          orderState:'',
          orderRun:'',
          timeIntervalState:'',
          putRun:'',
          timeInterval:'',
          putState:'',
          entityState:'',
          adverAudit:'',
      },
      IdeaID: "",
      labelPosition: 'left',
    };
  },
  created() {
    //  this.getList();
    //  this.Datatypes();
  },
  methods: {
   
    // 搜索
     search() {
         this.form = {}
       let params = {
            id:this.IdeaID
         }
      entitycheckEntity(params).then(res=>{
            console.log(res);
             let data = res.data;
                if(data.code != 'A000000') {
                    this.$message.error(data.message);
                    return;
                }
            this.form = res.data.data
            this.form.entityName = this.IdeaID+' : '+res.data.data.entityName
            this.form.putId = res.data.data.putId+' : '+res.data.data.putName
            this.form.orderId = res.data.data.orderId+' : '+res.data.data.orderName
            this.form.date = res.data.data.startDate+' : '+res.data.data.endDate
      });
    },

  
  }
};
</script>

<style scoped>
.handle-box {
  margin-bottom: 20px;
}

.handle-select {
  width: 120px;
}
.handle-input {
  width: 300px;
  display: inline-block;
}
.del-dialog-cnt {
  font-size: 16px;
  text-align: center;
}
.border {
  width: 100%;
  height: 2px;
  background: silver;
}
.mr10{
    width:12%
}
.blue{
    color:blue;
}
.red{
    color:red;
}
.green{
    color:green;
}
</style>


