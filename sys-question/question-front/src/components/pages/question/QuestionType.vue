<template>
    <div class="question type">
        <el-table data="data">
           <el-table-column prop="id" label="ID"/>
           <el-table-column prop="name" label="类型"/>
           <el-table-column prop="value" label="类型值"/>
        </el-table>
    </div>
</template>

<script>

export default {
    name:'QuestionType',
    data(){
        return {
            data:[],

        }
    },
}
</script>