<template>
    <div class='login'>
        <d-header>
            <template>
                <div class='logo'>
                    Test
                </div>
            </template>
        </d-header>
        <div class='login-form' style="text-align: center;">
            <el-form :model="loginForm" status-icon ref="loginForm" :rules="loginRules" class='login-form-container'>
                <el-form-item>
                    <el-radio-group v-model="loginForm.type">
                        <el-radio label="0" border selected>用户</el-radio>
                        <el-radio label="1" border>商户</el-radio>
                        <el-radio label="2" border>管理员</el-radio>
                    </el-radio-group>
                </el-form-item>
                <el-form-item>
                    <el-input v-model="loginForm.loginId" placeholder="账号名/手机/邮箱"></el-input>
                </el-form-item>
                <el-form-item >
                    <el-input type="password" v-model="loginForm.password" placeholder="请输入登录密码" show-password></el-input>
                </el-form-item>
                <el-form-item>
                    <el-button type="primary" @click="login">登录</el-button>
                </el-form-item>
            </el-form>
        </div>
        <d-footer></d-footer>
    </div>
</template>
<script>
import {DHeader,DFooter} from '@/components/commons/layout.js';
export default {
    name: 'Login',
    components:{
        DHeader,DFooter
    },
    data() {
        var validatePass = (rule, value, callback) => {
            if (value === '') {
                callback(new Error('请输入密码'));
            } else {
                if (this.ruleForm.checkPass !== '') {
                    this.$refs.ruleForm.validateField('checkPass');
                }
                callback();
            }
        };
        var validateLoginId = (rule, value, callback) => {
            if (value === '') {
                callback(new Error('请输入账号名/手机/邮箱'));
            } else {
                callback();
            }
        };
        return {
            loginForm:{},
            loginRules:{
                password:[{validator:validatePass, trigger:'blur'}],
                loginId:[{validator:validateLoginId, trigger:'blur'}],
            },
        }
    },
    methods: {
        login(){
            console.log(this.loginForm)
            this.$refs.loginForm.validate((valid)=>{
                if(valid){
                    console.log('submit!')
                    // 校验内容，有效可以提交至服务
                }
            })
        },
    },
};
</script>
<style scoped>
.login{
    width: 100%;
    height: 100%;
    background-color: #ecf8ff;
}
.login-form {
    background-color: #ecf8ff;
    background-image: url(https://gw.alicdn.com/imgextra/i3/O1CN01iyYdem1GQd1yGgA0a_!!6000000000617-0-tps-2500-600.jpg);
    width: 100%;
    margin-top: 10vh;
    padding-bottom: 5vh;
    padding-top: 5vh;
    & button{
        width: 100%;
    }
}
.login-form-container {
    padding: 10px;
    width: 400px;
    position: relative;
    right: -60vw;
    background-color: white;
    
}
</style>