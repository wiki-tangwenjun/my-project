<template>
  <d2-container>
    <el-button type="primary" @click="loadingCheckInfo()">无权限</el-button>
  </d2-container>
</template>

<script>
import { $isHaveBtn, $isHavePage} from '@/common/vue-prototype'
export default {
  name: 'userManage',
        data(){
            return {
                
            }
        },
        components: {},
        created(){},
        mounted(){
            $isHavePage("house-evaluate")
        },
        methods: {
            loadingCheckInfo() {
                this.$router.replace({name: '403'});
            }
        }
}
</script>
