<template>
    <div class="header">
        <div class="collapse-btn">
            <!-- <img src="static/img/img.jpg" alt=""> -->
        </div>
        <!-- 折叠按钮 -->
        <div class="collapse-btn" @click="collapseChage">
              
            <i class="el-icon-menu"></i>
        </div>
        <div class="logo">女娲后台管理系统</div>
       
        <div class="header-right">
            <div class="header-user-con">
                <!-- 全屏显示 -->
                <div class="btn-fullscreen" @click="handleFullScreen">
                    <el-tooltip effect="dark" :content="fullscreen?`取消全屏`:`全屏`" placement="bottom">
                        <i class="el-icon-rank"></i>
                    </el-tooltip>
                </div>
                <!-- <div>
                    <el-tooltip class="item" effect="dark" content="修改密码" placement="top-start">
                        <span class="el-dropdown-link">
                            <a class="span" @click="Editpassword">修改密码</a>
                        </span>
                    </el-tooltip>
                </div> -->
                <!-- 消息中心 -->
                <!-- <div class="btn-bell">
                    <el-tooltip effect="dark" :content="message?`有${message}条未读消息`:`消息中心`" placement="bottom">
                        <router-link to="/tabs">
                            <i class="el-icon-bell"></i>
                        </router-link>
                    </el-tooltip>
                    <span class="btn-bell-badge" v-if="message"></span>
                </div> -->
                <!-- 用户头像 -->
                <!-- <div class="user-avator"><img src="static/img/img.jpg"></div> -->
                <!-- 用户名下拉菜单 -->
                <el-dropdown class="user-name" trigger="click" @command="handleCommand">
                    <span class="el-dropdown-link">
                        {{username}} <i class="el-icon-caret-bottom"></i>
                    </span>
                    <el-dropdown-menu slot="dropdown">
                        <el-dropdown-item divided  command="Editpassword">修改密码</el-dropdown-item>
                        <el-dropdown-item divided  command="loginout">退出登录</el-dropdown-item>                     
                    </el-dropdown-menu>
                </el-dropdown>
                 <!-- 修改密码弹出框 -->
                <el-dialog title="修改密码" :visible.sync="editVisible" width="40%">
                    <el-form ref="form" :model="form" :rules="rules2" status-icon label-width="100px" class="demo-ruleForm">
                        <el-tag type="success">修改密码</el-tag>
                        <el-form-item label="旧密码:" prop="user_name">
                            <el-input v-model="form.oldPwd" placeholder="请输入旧密码" style="width:80%" type="password"></el-input>
                        </el-form-item>
                        <el-form-item label="新密码:" prop="password">
                            <el-input v-model="form.password" placeholder="请设置登录密码" style="width:80%" type="password"></el-input>
                        </el-form-item>
                        <el-form-item label="密码确认:" prop="checkPass">
                            <el-input v-model="form.checkPass" placeholder="请再一次输入登录密码" style="width:80%" type="password"></el-input>
                        </el-form-item>
                    </el-form>
                    <span slot="footer" class="dialog-footer">
                        <el-button @click="editVisible = false">取 消</el-button>
                        <el-button type="primary" @click="saveEdit">确 定</el-button>
                    </span>
                </el-dialog>
            </div>
        </div>
    </div>
</template>
<script>
import {authsignout,userresetPwd} from "@/api/Api.js";


    import bus from '../common/bus';
    export default {
        data() {
          var validatePass2 = (rule, value, callback) => {
                if (value === '') {
                    callback(new Error('请再次输入密码'));
                } else if (value != this.form.password) {
                    callback(new Error('两次输入密码不一致!'));
                } else {
                    callback();
                }
            };
        return {
                collapse: false,
                fullscreen: false,
                name: '',
                message: 2,
                editVisible:false,
                form:{
                    oldPassword:'',
                    password:'',
                    checkPass:'',
                    oldPwd:'',
                    newPwd:'',
                },
                  rules2: {
                    userName: [
                        {required: true, message: '请输入旧名', trigger: 'blur'},
                    ],
                    password: [
                        {required: true, message: '请输入新密码', trigger: 'blur'},
                    ],
                    checkPass: [
                        {validator: validatePass2, message: '两次输入不一致', trigger: 'blur'}
                    ],
                },
            }
        },
        computed:{
            username(){
                let username = localStorage.getItem('userName');
                return username ? username : this.name;
            }
        },
         created() {
                // this.getList();
            },
        methods:{
            // 修改密码
            Editpassword(){
                this.form ={}
                this.editVisible = true
                
            },
            saveEdit(){
                if(!this.form.oldPwd) {
                    this.$message.error("旧不能为空");
                    return;
                }
                if(!this.form.password) {
                    this.$message.error("新密码不能为空");
                    return;
                }
                if(!this.form.checkPass) {
                    this.$message.error("新密码不能为空");
                    return;
                }
              let params ={
                oldPwd:this.form.oldPwd,
                newPwd:this.form.checkPass
              }
            userresetPwd(params).then(res => {
                if (res.data.code != 'A000000') {
                    this.$message.error(res.data.message);
                }   
                 this.$message('修改成功');  
                 this.editVisible = false     
            });

            },
            deleteRow(){

            },
            // 用户名下拉菜单选择事件
            handleCommand(command) {
                if(command == 'loginout'){
                   let params ={
                            token:localStorage.getItem('t')
                        }
                    authsignout(params).then(res => {
                    if (res.data.code != 'A000000') {
                        this.$message.error(res.data.message);
                    }                 
                    localStorage.removeItem('menuData')
                    localStorage.removeItem('userName')
                    localStorage.removeItem('t') 
                    this.$router.push({path:'/login'}) 
                  });
                }else if(command == "Editpassword") {
                    this.editVisible = true;
                }
                
            },
            // 侧边栏折叠
            collapseChage(){
                this.collapse = !this.collapse;
                bus.$emit('collapse', this.collapse);
            },
            // 全屏事件
            handleFullScreen(){
                let element = document.documentElement;
                if (this.fullscreen) {
                    if (document.exitFullscreen) {
                        document.exitFullscreen();
                    } else if (document.webkitCancelFullScreen) {
                        document.webkitCancelFullScreen();
                    } else if (document.mozCancelFullScreen) {
                        document.mozCancelFullScreen();
                    } else if (document.msExitFullscreen) {
                        document.msExitFullscreen();
                    }
                } else {
                    if (element.requestFullscreen) {
                        element.requestFullscreen();
                    } else if (element.webkitRequestFullScreen) {
                        element.webkitRequestFullScreen();
                    } else if (element.mozRequestFullScreen) {
                        element.mozRequestFullScreen();
                    } else if (element.msRequestFullscreen) {
                        // IE11
                        element.msRequestFullscreen();
                    }
                }
                this.fullscreen = !this.fullscreen;
            }
        },
        mounted(){
            if(document.body.clientWidth < 1500){
                // this.collapseChage();
            }
        }
    }
</script>
<style scoped>
    .header {
        position: relative;
        box-sizing: border-box;
        width: 100%;
        height: 70px;
        font-size: 22px;
        color: #fff;
    }
    .collapse-btn{
        float: left;
        padding: 0 21px;
        cursor: pointer;
        line-height: 70px;
    }
    .header .logo{
        float: left;
        width:250px;
        line-height: 70px;
    }
    .header-right{
        float: right;
        padding-right: 50px;
    }
    .header-user-con{
        display: flex;
        height: 70px;
        align-items: center;
    }
    .btn-fullscreen{
        transform: rotate(45deg);
        margin-right: 5px;
        font-size: 24px;
    }
    .btn-bell, .btn-fullscreen{
        position: relative;
        width: 30px;
        height: 30px;
        text-align: center;
        border-radius: 15px;
        cursor: pointer;
    }
    .btn-bell-badge{
        position: absolute;
        right: 0;
        top: -2px;
        width: 8px;
        height: 8px;
        border-radius: 4px;
        background: #f56c6c;
        color: #fff;
    }
    .btn-bell .el-icon-bell{
        color: #fff;
    }
    .user-name{
        margin-left: 10px;
        margin-top: 10px;
    }
    .user-avator{
        margin-left: 20px;
    }
    .user-avator img{
        display: block;
        width:40px;
        height:40px;
        border-radius: 50%;
    }
    .el-dropdown-link{
        color: #fff;
        cursor: pointer;
    }
    .el-dropdown-menu__item{
        text-align: center;
    }
    .span{
        font-size: 14px;
        vertical-align:text-bottom
    }
</style>
