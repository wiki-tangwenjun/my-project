package com.wenjun.busines.upload.controller;

import java.io.*;
import java.nio.charset.StandardCharsets;

import com.wenjun.config.FileConfig;
import com.wenjun.handlerException.error.CommonEnum;
import com.wenjun.handlerException.error.ReturnValue;
import com.wenjun.util.FileResource;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @projectName: house
 * @package: com.wenjun.busines.upload.controller
 * @className: IService
 * @author: wen jun tang
 * @description: 文件上传前端控制器
 * @date: 2021年07月14日 14:36
 * @version: 1.0
 */
@Slf4j
@Api(value = "文件上传", tags = "文件相关接口")
@RestController
@RequestMapping("/file")
@SuppressWarnings("all")
public class FileController {
    @Resource
    FileConfig fileConfig;

    @ApiOperation(value = "文件上传", notes = "通过http的Multipart方式上传文件")
    @PostMapping(value = "/upload", headers = "content-type=multipart/form-data")
    public ReturnValue<String> upload(@ApiParam(value = "上传的文件", required = true) MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            return new ReturnValue<String>(CommonEnum.ERROR_INVALID_PARAM, "请选择文件!");
        }
        String fileName = file.getOriginalFilename();

        // 获取根目录
        File path = new File(ResourceUtils.getURL("classpath:").getPath());

        if (!path.exists()) {
            path = new File("");
        }

        File upload = new File(path.getAbsolutePath(), "static/file/");
        if (!upload.exists()) {
            upload.mkdirs();
        }

        String filePath = upload + "/" + fileName;

        File localFile = new File(filePath);

        FileOutputStream out = new FileOutputStream(localFile);
        localFile.createNewFile();
        out.write(file.getBytes());

        log.info(localFile.getAbsolutePath());
        out.flush();
        out.close();

        return new ReturnValue<>(fileConfig.getFileString(false) + fileName);
    }

    @GetMapping("/download/{fileName}")
    public void download(HttpServletRequest request, HttpServletResponse response,
                         @PathVariable(name = "fileName", required = true) String fileName) {
        download(request, response, FileResource.url, fileName);
    }

    /**
     * @param request 响应
     * @param response 响应
     * @param filePath 文件地址
     * @param fileName 文件名称
     * @description: 下载文件，下载之后删除文件
     * @author wen jun tang
     * @date 2021/7/14 14:52
     */
    static protected void download(HttpServletRequest request, HttpServletResponse response, String filePath, String fileName) {
        try {
            // 解决文件名中文乱码问题
            String userAgent = request.getHeader("User-Agent");
            String formFileName = fileName;
            if (userAgent.contains("MSIE") || userAgent.contains("Trident")) {
                formFileName = java.net.URLEncoder.encode(fileName, StandardCharsets.UTF_8);
            } else {
                // 非IE浏览器的处理：
                formFileName = new String(fileName.getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1);
            }

            response.setContentType("multipart/form-data");
            response.setHeader("Content-Disposition", "attachment;filename=" + formFileName);

            File file = ResourceUtils.getFile(filePath + File.separator + fileName);
            OutputStream out = response.getOutputStream();
            InputStream in = new FileInputStream(file);
            int b;
            while ((b = in.read()) != -1) {
                out.write(b);
            }
            in.close();
            out.close();
            file.delete();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
