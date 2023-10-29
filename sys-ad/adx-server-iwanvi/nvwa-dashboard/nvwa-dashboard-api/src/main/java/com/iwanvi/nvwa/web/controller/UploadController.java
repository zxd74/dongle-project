package com.iwanvi.nvwa.web.controller;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Map;

import javax.imageio.ImageIO;

import com.iwanvi.nvwa.web.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.iwanvi.nvwa.common.exception.ServiceException;
import com.iwanvi.nvwa.common.utils.Constants;
import com.iwanvi.nvwa.common.utils.JsonUtils;
import com.iwanvi.nvwa.common.utils.StringUtils;

import ws.schild.jave.EncoderException;
import ws.schild.jave.InputFormatException;
import ws.schild.jave.MultimediaObject;

import com.google.common.collect.Maps;

@RestController
@RequestMapping("/upload")
public class UploadController {

	private static final Logger logger = LoggerFactory.getLogger(UploadController.class);

	@NvwaRespBody
	@RequestMapping("/uploadWithSize")
	public String uploadWithSize(MultipartFile myFile, Integer type, Integer w, Integer h, Integer s) throws Exception {
		String result = StringUtils.EMPTY;
		if (myFile != null && type != null) {
			UploadOssHandler uploadHandler = getUploadHandler(type);
			if (type.equals(UploadHandler.IMAGE.getType()) && w != null && h != null) {
				BufferedImage image = ImageIO.read(myFile.getInputStream());
				if (image.getHeight() != h || image.getWidth() != w) {
					result = JsonUtils.STATUS_FAILED(Constants.RESPONSE_FAILED, "图片尺寸不匹配.");
					logger.info("upload file err!adCollection (" + w + "," + h + "),file (" + image.getWidth() + ","
							+ image.getHeight() + ")");
					return result;
				}
			}
			String path = uploadHandler.uploadFile(myFile);
			if (type.equals(UploadHandler.VIDEO.getType()) && s != null) {
				boolean isQualified = uploadHandler.checkVideoTime(path, s + 1);
				if (!isQualified) {
					result = JsonUtils.STATUS_FAILED(Constants.RESPONSE_FAILED, "视频时长过长");
					return result;
				}
			}
			Map<String, Object> resultMap = Maps.newHashMap();
			resultMap.put("url", path);
			result = JsonUtils.STATUS_OK(resultMap);
			logger.info("upload file success! url: {}", path);
		} else {
			result = JsonUtils.PARAMETER_ERROR();
			logger.info("update file error, myFile {}, type {}", myFile, type);
		}
		return result;
	}

	@RequestMapping("/upload")
	public String upload(MultipartFile myFile, Integer type) {
		String result = StringUtils.EMPTY;
		try {
			if (myFile != null) {
				UploadOssHandler uploadHandler = getUploadHandler(type);
				String path = uploadHandler.uploadFile(myFile);
				Map<String, Object> resultMap = Maps.newHashMap();
				resultMap.put("url", path);
				result = JsonUtils.STATUS_OK(resultMap);
				logger.info("upload file success! url: {}", path);
			} else {
				result = JsonUtils.PARAMETER_ERROR();
			}
		} catch (Exception e) {
			logger.error("upload file error!", e);
			result = JsonUtils.STATUS_FAILED(Constants.RESPONSE_FAILED, e.getMessage());
		}
		return result;
	}

	@RequestMapping("/getCont")
	public String getFileContext(MultipartFile myFile) {
		String result = StringUtils.EMPTY;
		try {
			if (myFile != null) {
				byte[] context = myFile.getBytes();
				result = JsonUtils.STATUS_OK(new String(context));
			} else {
				result = JsonUtils.PARAMETER_ERROR();
			}
		} catch (Exception e) {
			logger.error("get file context error!", e);
			result = JsonUtils.STATUS_FAILED(Constants.RESPONSE_FAILED, e.getMessage());
		}
		return result;
	}

	private UploadOssHandler getUploadHandler(Integer type) {
		if (type <= UploadOssHandler.values().length) {
			return UploadOssHandler.values()[type - 1];
		}
		return null;
	}
}
