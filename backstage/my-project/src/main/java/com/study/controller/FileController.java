package com.study.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import com.study.config.FileConfig;
import com.study.error.ErrorCode;
import com.study.error.ReturnValue;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Resource;

@Slf4j
@Api(value = "文件上传", tags = "文件相关接口")
@RestController
@RequestMapping("/file")
public class FileController {
	@Resource
	FileConfig fileConfig;

	@ApiOperation(value = "文件上传", notes = "通过http的Multipart方式上传文件")
	@PostMapping(value = "/upload", headers = "content-type=multipart/form-data")
	public ReturnValue<String> upload(@ApiParam(value = "上传的文件", required = true) MultipartFile file) throws IOException {
		if (file.isEmpty()) {
			return new ReturnValue<String>(ErrorCode.ERROR_INVALID_PARAM, "请选择文件!");
		}
		String fileName = file.getOriginalFilename();

		try {
			// 获取根目录
			File path = new File(ResourceUtils.getURL("classpath:").getPath());
			
			if (!path.exists()) {
				path = new File("");
			}
			
			File upload = new File(path.getAbsolutePath(), "static/file/");
			if (!upload.exists())
				upload.mkdirs();

			String filePath = upload + "/" + fileName;

			File localFile = new File(filePath);
			
			FileOutputStream out = new FileOutputStream(localFile);
			localFile.createNewFile();
			out.write(file.getBytes());

			log.info(localFile.getAbsolutePath());
			if (out != null) {
				out.flush();
				out.close();
			}
			
			return new ReturnValue<String>(fileConfig.getFileString(false) + fileName);
		} catch (Exception e) {
			log.error("上传文件出错：{}", e.getMessage());
			return new ReturnValue<String>(ErrorCode.ERROR_INVALID_PARAM, "上传出错!");
		}
	}
}