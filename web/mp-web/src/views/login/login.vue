<template>
    <div class="login-box">
        <div class="login-title-box-1 item">
            <h1>欢迎</h1>
        </div>
         <div class="login-title-box-2 item">
            <h2>XXX租房智能管理平台</h2>
        </div>
        <div class="login-content-box">
            <div class="title-box">
                <span>登录</span>
            </div>
            <div class="content-box">
                <el-form :model="ruleForm" status-icon :rules="rules" ref="ruleForm" label-width="70px" class="form-to-login">
                    <el-form-item label="账号：" prop="account">
                        <el-input type="text" v-model="ruleForm.account" autocomplete="off" style="width: 300px;"></el-input>
                    </el-form-item>
                    <el-form-item label="密码：" prop="password">
                        <el-input type="password" v-model="ruleForm.password" autocomplete="off" style="width: 300px;"></el-input>
                    </el-form-item>
                    <el-form-item>
                        <el-button type="primary" @click="tologin()">提交</el-button>
                    </el-form-item>
                </el-form>
            </div>
            <div class="error-box">
                {{errorMessage}}
            </div>
        </div>
    </div>
</template>

<script>
import Verify from 'vue2-verify'
export default {
    name: "Login",
    data() {
      var validatePass = (rule, value, callback) => {
        if (value === '') {
          callback(new Error('请输入账号'));
        } else {
          if (this.ruleForm.account !== '') {
            this.$refs.ruleForm.validateField('account');
          }
          callback();
        }
      };
      var validatePass2 = (rule, value, callback) => {
        if (value === '') {
          callback(new Error('请输入密码'));
        } else {
          callback();
        }
      };
      return {
        ruleForm: {
          account: '',
          password: '',
        },
        rules: {
          account: [
            { validator: validatePass, trigger: 'blur' }
          ],
          password: [
            { validator: validatePass2, trigger: 'blur' }
          ]
        },
        errorMessage: ''
      };
    },
    created(){},
    mounted(){
       
    },
    methods: {
        tologin() {
            if (!this.loginForm.account || !this.loginForm.password) {
                return this.showMsg("用户名或密码不能为空!");
            }
            this.$refs.loginForm.validate(valid => {
                if (valid) {
                    this.loginMsg = "正在登录,请稍候...";
                    this.isLoading = true;
                }
            });
        }
    }
};
</script>
<style lang="scss" scoped>
.login-box {
    position: absolute;
    margin-left: 0;
    height: 100%;
    width: 100%;
    background-size: cover;
    background-image: url('../../assets/login-bk.jpg');
        .item {
        position: absolute;
        top: 106px;
        left: 260px;
        color: #5c6b88;
    }
    .login-title-box-2 {
        top: 146px;
        left: 305px;
        color: #909399;
    }
    .login-content-box {
        position: absolute;
        height: 330px;
        width: 420px;
        right: 100px;
        top: 106px;
        border: 1px solid #909399;
        background-color:rgba(255,255,255,0);
        .title-box {
            text-align: left;
            margin-left: 20px;
            font-size: 30px;
            color: antiquewhite;
        }
        .content-box {
            margin-top: 25px;
            .form-to-login {
                ::v-deep .el-input {
                    position: static;
                    height: 50px;
                    font-size: 16px;
                    display: inline;    
                }
               .el-form-item {
                   margin-top: 25px;
                   ::v-deep .el-form-item__label {
                        color: rgb(37, 35, 35);
                        font-size: 18px;
                        padding: 0;
                    }
                    ::v-deep .el-form-item__content {
                        width: 316px;
                    }
                    ::v-deep .el-form-item__error {
                        font-weight: 900;
                        font-size: 15px;
                    }
                    ::v-deep .el-button {
                        width: 100%;
                        height: 45px;
                        font-size: 17px;
                        margin-top: 20px;   
                    }
               }
            }
        }
        .error-box {
            color: red;
            font-size: 18px;
            font-weight: 600;
            text-align: left;
            margin-left: 30px;
        }
    }
}
</style>