/** 生成一个随机数 **/
function randomNum(min, max) {
    return Math.floor(Math.random() * (max - min) + min);
}
/** 生成一个随机色 **/
function randomColor(min, max) {
    let r = randomNum(min, max);
    let g = randomNum(min, max);
    let b = randomNum(min, max);
    return "rgb(" + r + "," + g + "," + b + ")";
}

/**
 * 绘制验证码图片
 * @param {*} num 验证码
 * @param {*} elementId 验证码框框
 */
export function drawPic(num, elementId) {
    let canvas = document.getElementById(elementId);
    let width = canvas.width;
    let height = canvas.height;
    let ctx = canvas.getContext('2d');
    ctx.textBaseline = 'bottom';

    /**绘制背景色**/
    ctx.fillStyle = randomColor(180, 240); //颜色若太深可能导致看不清
    ctx.fillRect(0, 0, width, height);
    /**绘制文字**/
    let txt = num;
    ctx.fillStyle = randomColor(50, 160); //随机生成字体颜色
    ctx.font = randomNum(25, 35) + 'px SimHei'; //随机生成字体大小
    let x = 22;
    let y = 35;
    let deg = randomNum(-10, 8);
    //修改坐标原点和旋转角度
    ctx.translate(x, y);
    ctx.rotate(deg * Math.PI / 180);
    ctx.fillText(txt, 0, 0);
    // //恢复坐标原点和旋转角度
    ctx.rotate(-deg * Math.PI / 180);
    ctx.translate(-x, -y);
    /* } */
    /* /**绘制干扰线**/
    for (let i = 0; i < 8; i++) {
        ctx.strokeStyle = randomColor(40, 180);
        ctx.beginPath();
        ctx.moveTo(randomNum(0, width), randomNum(0, height));
        ctx.lineTo(randomNum(0, width), randomNum(0, height));
        ctx.stroke();
    }
    /**绘制干扰点**/
    for (let i = 0; i < 100; i++) {
        ctx.fillStyle = randomColor(0, 255);
        ctx.beginPath();
        ctx.arc(randomNum(0, width), randomNum(0, height), 1, 0, 2 * Math.PI);
        ctx.fill();
    }
}