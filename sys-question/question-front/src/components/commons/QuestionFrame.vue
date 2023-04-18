<template>
    <div class="question-frame" :questionIndex="question.idx">
        <div class="question-info">
            <el-icon @click="$emit('del')"><Close /></el-icon>
            <label class="info" :questionNum="question.qn">第{{question.qn}}题：</label>
            <el-input class="info" v-model="questionInfo.desc" :value="question.desc"></el-input>
            <el-select class="select" v-model="questionInfo.type">
                <el-option value="0" >类型0</el-option>
                <el-option value="1" >类型1</el-option>
            </el-select>
        </div>
        <div class="question-desc">
            <!-- 详细题型处理 -->
        </div>
    </div>
</template>
<script>
// import {Question} from '@/assets/js/model.ts'
export default {
    name:'question-frame',
    props:{
        question:{
            // type:Any,
            require:true
        },
    },
    setup(){
        
    },
    data(){
        return{
            type:0,
            questionInfo:{},
        }
    },
    created(){
        // 根据题型不同处理显示不同框架
        this.type = 1
        this.questionInfo = this.question
    },
    methods:{
        update(){
            console(this.questionInfo)
            this.$emit("update:questions",this.questionInfo)
        },
    }
}
</script>
<style scoped>
.question-frame{
    text-align: left;
    border-bottom: 1px solid;
    padding-top: 20px;
    padding-left: 20px;
}
.info{
    display: inline;
}
.select{
    display: inline-block;
}

</style>