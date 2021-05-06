<template>
    <div class="app-mian">
        <el-container>
            <el-aside>
                <Menu></Menu>
            </el-aside>
            <el-container>
                <el-header>
                    <Head></Head>
                </el-header>
                <el-main>   
                    <Content></Content>
                </el-main>
            </el-container>
        </el-container>
    </div>
</template>

<script>
import Content from './layout/content';
import Head from './layout/head';
import Menu from './layout/menu';
export default {
    name: "Main",
    components: {
        Menu,
        Head,
        Content
    },
    data(){
		return {
			input: ''
		}
    },
    created(){},
    mounted(){
       
    },
    methods: {

    }
};
</script>
<style lang="scss">
.app-mian {
    position: absolute;
    width: 100%;
    height: 100%;
    .el-container {
        height: 100%;
        .el-aside {
            width: 245px !important;
            overflow: hidden;
            // border: 2px solid #409EFF;
        }
        .el-header {
            height: 70px !important;
            line-height: 70px;
            border: 1px solid #67C23A;
        }
        .el-main {
             border: 1px solid #F56C6C;
        }
    }
    
}
</style>