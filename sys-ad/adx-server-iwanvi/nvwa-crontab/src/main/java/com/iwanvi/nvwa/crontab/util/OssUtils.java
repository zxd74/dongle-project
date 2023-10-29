package com.iwanvi.nvwa.crontab.util;

import java.io.IOException;
import java.util.Date;


import com.iwanvi.nvwa.common.utils.Constants;
import com.iwanvi.nvwa.common.utils.DateUtils;
import com.iwanvi.nvwa.common.utils.StringUtils;

public class OssUtils {
	public static String uploadFile(String dataPath) throws IOException {
		

		String[] split = dataPath.split(",");
		// 文件上传到自己的服务器后，需要上传到阿里oss
		String cdnRelativePath = StringUtils.concat(
				DateUtils.format(new Date(), DateUtils.SHORT_FORMAT), Constants.SIGN_OBLIQUELINE, split[split.length - Constants.INTEGER_1]);
		OssHandler ossHandler = new OssHandler();
		OssHandler.uploadFile(ossHandler.getClient(),Constants.OSS_BUCKETNAME,cdnRelativePath, dataPath);

		return CrontabConstants.UPLOAD_URL_HOST + cdnRelativePath;
	}
}
