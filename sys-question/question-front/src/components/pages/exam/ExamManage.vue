<template>
    <div>
        <!-- 考试管理 -->
        ExamManage
        <el-table :data="exams">
           <el-table-column prop="id" label="ID" width="50"/>
           <el-table-column prop="name" label="考试名称"/>
           <el-table-column prop="paper" label="试卷名"/>
           <el-table-column label="时间">
                <template #default="scoped">
                    {{scoped.row.start}} - {{ scoped.row.end }}
                </template>
           </el-table-column>
           <el-table-column label="状态" width="80">
                <template #default="scoped">
                    {{ handleStatus(scoped.row.status) }}
                </template>
           </el-table-column>
           <el-table-column prop="desc" label="描述"/>
        </el-table>
    </div>
</template>

<script>
import { local } from '@/assets/js/api';
export default {
    name:'ExamManage',
    data(){
        return {
            exams:[],
            examStatus:[
                {status:0,desc:"未操作"},
                {status:1,desc:"已开启"},
                {status:2,desc:"已停止"},
                {status:3,desc:"已开考"},
                {status:4,desc:"已结束"},
                {status:5,desc:"已判卷"},
            ]
        }
    },
    created(){
        local('/static/exams.json').then(res=>{
            this.exams = res.data
        })
    },
    methods:{
        handleStatus(status){
            let desc = "未知";
            this.examStatus.forEach(e=>{
                if(e.status == status){
                    desc = e.desc;
                    return
                }
            })
            return desc
        }
    }
}
</script>