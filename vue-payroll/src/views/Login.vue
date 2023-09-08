<template>
    <div class="wrapper">
        <div class="login-box">
            <div class="login-form">
                <div class="login-title">登录</div>
                <el-form :model="user" :rules="rules" ref="userForm">
                    <el-form-item prop="username">
                        <el-input size="medium" prefix-icon="el-icon-user" v-model="user.username"></el-input>
                    </el-form-item>
                    <el-form-item prop="password">
                        <el-input size="medium" prefix-icon="el-icon-lock" show-password v-model="user.password"></el-input>
                    </el-form-item>
                    <el-form-item class="login-actions">
                        <el-button type="primary" size="medium" @click="login">登录</el-button>
                    </el-form-item>
                </el-form>
            </div>
        </div>
    </div>
</template>
<script>
import {setRoutes} from "@/router";

export default {
    name: "Login",
    data() {
        return {
            user: {},
            rules: {
                username: [
                    {required: true, message: '请输入用户名', trigger: 'blur'},
                    {min: 5, max: 20, message: '长度在 5 到 20 个字符', trigger: 'blur'}
                ],
                password: [
                    {required: true, message: '请输入密码', trigger: 'blur'},
                    {min: 8, max: 20, message: '长度在 8 到 20 个字符', trigger: 'blur'}
                ],
            }
        }
    },
    methods: {
        login() {
            this.$refs['userForm'].validate((valid) => {
                if (valid) {  // 表单校验合法
                    this.request.post("/user/login", this.user).then(res => {
                        if (res.code === '200') {
                            localStorage.setItem("user", JSON.stringify(res.data))  // 存储用户信息到浏览器
                            window.sessionStorage.setItem('token',res.data.token)
                            this.$message.success("登录成功")

                            this.$router.push("/")
                        } else {
                            this.$message.error(res.msg)
                        }
                    })
                }
            });
        }
    }
}
</script>

<style scoped>
.wrapper {
    height: 100vh;
    background-image: url('../assets/jane-last-1cH2E09ryXw-unsplash.jpg');
    background-size: cover;
    background-position: center;
    display: flex;
    justify-content: center;
    align-items: center;
}

.login-box {
    background-color: rgba(255, 255, 255, 0.8);
    width: 350px;
    padding: 20px;
    border-radius: 10px;
}

.login-title {
    margin: 20px 0;
    text-align: center;
    font-size: 24px;
    font-weight: bold;
}

.login-actions {
    margin-top: 10px;
    text-align: right;
}
</style>
