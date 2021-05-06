<template>
    <div class="menu-box">
        <template>
            <div class="menu-title">馨馨出租屋智能管理平台</div>
        </template>
        <el-menu 
            default-active="1-4-1" 
            class="el-menu-vertical-demo" 
            @open="handleOpen" 
            @close="handleClose" 
            background-color="#545c64"
            text-color="#fff"
            active-text-color="#ffd04b"
            :collapse="isCollapse">
            <el-submenu index="1">
                <template slot="title">
                    <i class="el-icon-location"></i>
                    <span slot="title">导航一</span>
                </template>
                <el-menu-item-group>
                    <span slot="title">分组一</span>
                    <el-menu-item index="1-1">选项1</el-menu-item>
                    <el-menu-item index="1-2">选项2</el-menu-item>
                </el-menu-item-group>
                
                <el-submenu index="1-4">
                    <span slot="title">选项4</span>
                    <el-menu-item index="1-4-1">选项1</el-menu-item>
                </el-submenu>
            </el-submenu>
            <el-menu-item index="4">
                <i class="el-icon-setting"></i>
                <span slot="title">导航四</span>
            </el-menu-item>
        </el-menu>
    </div>
</template>

<script>
    export default {
        name: "Menu",
        data(){
            return {
                isCollapse: false
            }
        },
        created(){},
        mounted(){
        
        },
        methods: {
            handleOpen(key, keyPath) {
                console.log(key, keyPath);
            },
            handleClose(key, keyPath) {
                console.log(key, keyPath);
            }
        }
    };
</script>
<style scoped lang="scss">
.menu-box {
    height: 100%;
    background-color: #545c64;
    .menu-title {
        font-size: 16px;
        font-weight: 600; 
        margin-bottom: 20px;
        padding-top: 10px;
        color: white;
        word-break:break-all;
    }
    .el-menu-vertical-demo:not(.el-menu--collapse) {
        width: 230px;
        height: calc(100% - 65px);
    }
    .el-menu--collapse {
        height: calc(100% - 60px) !important;
    }
}

</style>
