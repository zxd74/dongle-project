package com.iwanvi.nvwa.web.util;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Map;

import com.iwanvi.nvwa.common.utils.*;

import ws.schild.jave.EncoderException;
import ws.schild.jave.InputFormatException;
import ws.schild.jave.MultimediaInfo;
import ws.schild.jave.MultimediaObject;

import org.apache.commons.net.ftp.FTPClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import com.google.common.collect.Maps;
import com.iwanvi.nvwa.common.exception.ServiceException;

public enum UploadHandler {
	IMAGE(1, ".jpg,.png,.jpeg,.bmp,.gif", "jpg"), FLASH(2, ".swf", "flash"), SDK(3, ".jar,.zip", "sdk"),
	APP(4, ".apk", "app"), VIDEO(5, ".flv,.f4v,.mp4", "video"),
	DOCUMENT(6, ".xlsx,.zip,.pptx,.jpg,.png,.jpeg,.pdf,.xls,.ppt,.doc,.docx", "doc");

	private Integer type;
	private String format;
	private String path;
	private static final Logger logger = LoggerFactory.getLogger(UploadHandler.class);

	private UploadHandler(Integer type, String format, String path) {
		this.type = type;
		this.format = format;
		this.path = path;
	}

	public Integer getType() {
		return type;
	}

	public String getFormat() {
		return format;
	}

	public String getPath() {
		return path;
	}

	public String uploadFile(MultipartFile file) throws Exception {
		String fileName = file.getOriginalFilename();
		boolean checkFormat = checkFormat(fileName);
		if (!checkFormat) {
			throw new ServiceException("文件类型错误");
		}
		String suffix = fileName.substring(fileName.lastIndexOf(Constants.SIGN_POINT));
		String uuid = UUIDUtils.getUUID().substring(Constants.INTEGER_0, Constants.INTEGER_5);
		String time = DateUtils.format(new Date(), DateUtils.FORMAT_YMDHMS).substring(Constants.INTEGER_8);
		String newName = StringUtils.concat(time, uuid, suffix);
		String ftpRelativePath = StringUtils.concat(WebConstants.FTP_PATH, path, Constants.SIGN_OBLIQUELINE,
				DateUtils.format(new Date(), DateUtils.SHORT_FORMAT), Constants.SIGN_OBLIQUELINE, newName);
		logger.info("UploadHandler uploadFile ftpRelativePath:{}", ftpRelativePath);
		FTPClient ftpClient = FtpUtil.getFTPClient();
		boolean uploadComplete = FtpUtil.uploadFile(ftpClient, ftpRelativePath, newName, file.getInputStream());
		if (!uploadComplete) {
			throw new ServiceException("upload file failed");
		}
		return WebConstants.UPLOAD_URL_HOST + ftpRelativePath;
	}

	public boolean checkFormat(String fileName) {
		if (StringUtils.isNotBlank(fileName)) {
			fileName = fileName.toLowerCase();
		}
		if (format.contains(Constants.SIGN_COMMA)) {
			String[] formats = format.split(Constants.SIGN_COMMA);
			for (String f : formats) {
				if (fileName.endsWith(f)) {
					return true;
				}
			}
		} else if (fileName.endsWith(format)) {
			return true;
		}
		return false;
	}

	private void checkFileName(String name) {
		int count = 0;
		char[] c = name.toCharArray();
		for (int i = 0; i < c.length; i++) {
			String len = Integer.toBinaryString(c[i]);
			if (len.length() > 8)
				count++;
		}
		if (count > 0) {
			throw new ServiceException("文件名不支持中文,请修改.");
		}
	}

	public float getTimeByVideo(String path) throws IOException, InputFormatException, EncoderException {
		float time = VideoUtils.readTime(path);
		return time;
	}

	public boolean checkVideoTime(String filePath, Integer s) throws InputFormatException, IOException, EncoderException {
		String videoPath = filePath.replace(WebConstants.UPLOAD_URL_HOST, WebConstants.FTP_UPLOAD_PATH);
		float duration = getTimeByVideo(videoPath);
		if (duration > s * 1f) {
			File deleteFile = new File(videoPath);
			boolean isDelete = deleteFile.delete();
			if (!isDelete) {
				throw new ServiceException("delete file failed");
			}
			return false;
		}
		return true;
	}

}