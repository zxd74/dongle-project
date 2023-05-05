<template>
    <div>
        <!-- 试卷详情，负责试题增改查 -->
        <el-button @click="addQuestion()">添加试题</el-button>
        <div id="questions"  >
            <question-frame v-for="(item, index) in questions" :key='"com_" + index' :id='"question_" + index' :question="item" @del="delQuestion(index)"/>
        </div>

        <div class="options">
            <el-button @click="savenQuestions()">保存</el-button>
        </div>
    </div>
</template>

<script>
import QuestionFrame from '@/components/commons/QuestionFrame.vue';
export default {
    name:'Paper',
    components:{QuestionFrame},
    data(){
        return {
            num:0,
            index:0,
            questions:[]
        }
    },
    methods:{
        addQuestion(){
            var question = {
                idx:this.index++,
                qn:++this.num,
            }
            this.questions.push(question)
        },
        delQuestion(idx){
            console.log(this.questions[idx])
            this.num--
            this.index--
            this.questions.splice(idx,1)
            for(var i=idx;i<this.questions.length;i++){
                this.questions[i].idx--
                this.questions[i].qn--
            }
        },
        savenQuestions(){
            console.log(this.questions)
        },
    }
}
</script>
<style scoped>
#question{
    width: 100%;
}
.options{
    position: absolute;
    bottom: 0;
    text-align: center;
}
</style>