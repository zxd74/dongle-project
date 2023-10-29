<template>
    <div class="login-wrap">
        <div class="ms-title">女娲后台管理系统</div>
        <div class="ms-login">
            <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="0px" class="demo-ruleForm">
                <el-form-item prop="username">
                    <el-input v-model="ruleForm.username" placeholder="请输入用户名"></el-input>
                </el-form-item>
                <el-form-item prop="password">
                    <el-input type="password" placeholder="请输入密码" v-model="ruleForm.password"
                              @keyup.enter.native="submitForm('ruleForm')"  autocomplete="new-password"></el-input>
                </el-form-item>
                <div class="login-btn">
                    <el-button type="primary" @click="submitForm">登录</el-button>
                </div>
            </el-form>
        </div>
    </div>
</template>

<script>
    import {loginlogin} from "@/api/Api.js";


    export default {
        data: function () {
            return {
                muens: {},

                ruleForm: {
                    username: '',
                    password: ''
                },
                rules: {
                    username: [
                        {required: true, message: '请输入用户名', trigger: 'blur'}
                    ],
                    password: [
                        {required: true, message: '请输入密码', trigger: 'blur'}
                    ]
                }
            }
        },
        methods: {
            submitForm() {
                let params = {
                    userName: this.ruleForm.username,
                    password: this.ruleForm.password
                }
                loginlogin(params).then(res => {
                    if (res.data.code != 'A000000') {
                        this.$message.error(res.data.message);
                    }
                    let muens = res.data.data.a;
                    this.$store.commit('setMenus', muens);
                    localStorage.setItem('menuData', JSON.stringify(muens))
                    localStorage.setItem('userName', res.data.data.u.userName)
                    localStorage.setItem('t', res.data.data.t)
                    localStorage.setItem('type', res.data.data.u.type)
                    this.$router.push({path: muens[0].subs[0].index})
                    
                });
            },
        }
    }
</script>

<style scoped>
    .login-wrap {
        position: relative;
        width: 100%;
        height: 100%;
    }

    .ms-title {
        position: absolute;
        top: 50%;
        width: 100%;
        margin-top: -230px;
        text-align: center;
        font-size: 30px;
        color: #fff;

    }

    .ms-login {
        position: absolute;
        left: 50%;
        top: 50%;
        width: 300px;
        height: 160px;
        margin: -150px 0 0 -190px;
        padding: 40px;
        border-radius: 5px;
        background: #fff;
    }

    .login-btn {
        text-align: center;
    }

    .login-btn button {
        width: 100%;
        height: 36px;
    }
</style>
