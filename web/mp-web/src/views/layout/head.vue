<template>
    <div class="header-box">
        <div class="menu-btn">
            <div class="menu-is-show" @click="menuIsShow()">
                <span class="el-icon-s-fold" v-if="isShow"></span>
                <span class="el-icon-s-unfold" v-if="unIsShow"></span>
            </div>
        </div>
    </div>
</template>

<script>
export default {
    name: "Head",
    data(){
        return {
            isShow: true,
            unIsShow: false
        }
    },
    created(){},
    mounted(){

    },
    methods: {
        menuIsShow() {
            this.isShow = !this.isShow;
            this.unIsShow = !this.unIsShow;
        },
    },
    computed: {
        
    }
};
</script>
<style scoped lang="scss">
    .header-box {
        .menu-btn {
            position: absolute;
            font-size: 22px;
        }
    }
</style>