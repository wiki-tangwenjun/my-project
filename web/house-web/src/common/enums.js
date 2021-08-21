
export const errorEnume = [
    {"code": -1, "name": "未找到或不存在"},
    {"code": -2, "name": "超时"},
    {"code": -3, "name": "无效参数"},
    {"code": -4, "name": "验证码错误或已失效"},
    {"code": -5, "name": "内存溢出"},
    {"code": -6, "name": "对象已存在"},
    {"code": -7, "name": "用户名或密码不正确"},
    {"code": -8, "name": "删除对象失败"},
    {"code": -9, "name": "无效列表"},
    {"code": -10, "name": "未登录或会话过期"},
    {"code": -11, "name": "接口不支持"},
    {"code": -12, "name": "权限不足"},
    {"code": -13, "name": "服务暂不可用"},
    {"code": -14, "name": "用户已登录"},
    {"code": -15, "name": "访问IP无权限"},
    {"code": -16, "name": "写文件失败"},
    {"code": -17, "name": "读文件失败"},
    {"code": -18, "name": "上传文件失败"},
    {"code": -19, "name": "类型强制转换异常"},
    {"code": -20, "name": "数组负下标异常"},
    {"code": -21, "name": "数组下标越界异常"},
    {"code": -22, "name": "用户不存在"},
    {"code": -23, "name": "权限认证失败"},
    {"code": -24, "name": "token已过时或无效"}
];

/*
* 取出枚举
*/
// 根据名字获取枚举项
export function name_GetEnums(enumsItem, name) {
    for (var i = 0; i < enumsItem.length; i++) {
        if (enumsItem[i].name == name) return enumsItem[i];
    }
    return {
        "code": "",
        "name": ""
    };
 };
 
 // 根据编号获取枚举项
 export function code_GetEnums(enumsItem, code) {
    for (var i = 0; i < enumsItem.length; i++) {
        if (enumsItem[i].code == code) return enumsItem[i];
    }
    return {
        "code": "",
        "name": ""
    };
 };