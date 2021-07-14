package com.wenjun.util;

import java.io.File;
import java.io.FileNotFoundException;

import org.springframework.util.ResourceUtils;

/**
 * @projectName: house
 * @package: com.wenjun.util
 * @className: FileResource
 * @author: wen jun tang
 * @description: 文件资源存放类
 * @date: 2021年07月14日 14:44
 * @version: 1.0
 */
public class FileResource {
    public static String url = "";

    public static void fileUrl() throws FileNotFoundException {
        File path = new File(ResourceUtils.getURL("classpath:").getPath());
        // 如果通过jar允许，则使用当前jar包所在路径
        if (!path.exists()) {
            path = new File("");
        }

        path = new File(path.getAbsolutePath(), "static" + File.separator + "file");
        if (!path.exists()) {
            path.mkdirs();
        }

        url = path.getAbsolutePath();
    }
}
