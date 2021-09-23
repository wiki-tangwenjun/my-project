<template>
    <d2-container>
        <div class="error-box" id="error-box">
        <img class="image-box" src="./image/403.png"> 
            <div  class="right-wrap">
                <p >抱歉，您无权限访问该页面！<br/>详情请联系管理员:<br/><3156826935@qq.com></p>
            </div>
        </div>
    </d2-container>
</template>

<script>
export default {
  name: 'error'
}
</script>
<style lang="scss">
    #error-box {
        width: 630px;
        margin: 25vh auto auto;
        display: -webkit-box;
        display: -ms-flexbox;
        display: flex;
        -webkit-box-align: center;
        -ms-flex-align: center;
        align-items: center;
        -webkit-box-pack: justify;
        -ms-flex-pack: justify;
        justify-content: space-between;
        .image-box {
            width: 300px;
        }
        .right-wrap {
            margin-left: 20px;
            >p {
                color: #808695;
                font-size: 20px;
                line-height: 28px;
            }
        }
        
    }
</style>
