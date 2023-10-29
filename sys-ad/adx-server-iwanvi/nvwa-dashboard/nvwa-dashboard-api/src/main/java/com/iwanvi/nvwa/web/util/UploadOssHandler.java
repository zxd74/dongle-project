package com.iwanvi.nvwa.web.util;

import com.iwanvi.nvwa.common.exception.ServiceException;
import com.iwanvi.nvwa.common.utils.*;
import org.springframework.web.multipart.MultipartFile;
import ws.schild.jave.EncoderException;
import ws.schild.jave.InputFormatException;

import java.io.File;
import java.io.IOException;
import java.util.Date;


public enum UploadOssHandler {
    IMAGE(1, ".jpg,.png,.jpeg,.bmp,.gif", "image")
    , FLASH(2, ".swf", "flash")
    , SDK(3, ".jar,.zip", "sdk")
    , APP(4, ".apk", "app")
    , VIDEO(5, ".flv,.f4v,.mp4","video")
    , DOCUMENT(6, ".xlsx,.zip,.pptx,.jpg,.pdf,.xls,.ppt","document");

    private Integer type;
    private String format;
    private String path;

    private UploadOssHandler(Integer type, String format, String path) {
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

    public String uploadFile(MultipartFile file) throws IOException {
        String fileName = file.getOriginalFilename();
        boolean checkFormat = checkFormat(fileName);
        if (!checkFormat) {
            throw new ServiceException("文件类型错误");
        }
//        checkFileName(fileName);
//        String imageRealFileName = null;
        String fileSuffix = null;
        if(this.equals(IMAGE) || this.equals(VIDEO)){
            fileSuffix = fileName.substring(fileName.lastIndexOf(Constants.SIGN_POINT)
                    , fileName.length());
            String uuid = UUIDUtils.getUUID();
            fileName = uuid + Constants.TEMP_SUFFIX + fileSuffix;
//            imageRealFileName = uuid + fileSuffix;
        }
        String filePath = StringUtils.concat(WebConstants.FTP_UPLOAD_PATH, path, Constants.SIGN_OBLIQUELINE,
                DateUtils.format(new Date(), DateUtils.SHORT_FORMAT), Constants.SIGN_OBLIQUELINE, fileName);

        File newFile = new File(filePath);
        if(!newFile.getParentFile().exists()){
            newFile.getParentFile().mkdirs();
        }
        file.transferTo(newFile);
//        if(this.equals(IMAGE) && imageRealFileName != null){
//            String newFilePath = filePath.replace(fileName, imageRealFileName);
//            ImageUtils.compressImage(filePath, newFilePath);
//            fileName = MD5Utils.MD5(new File(newFilePath))+fileSuffix;
//            filePath = newFilePath;
//        }
        //文件上传到自己的服务器后，需要上传到阿里oss
        String cdnRelativePath =  StringUtils.concat(path, Constants.SIGN_OBLIQUELINE, DateUtils.format(new Date(), DateUtils.SHORT_FORMAT)
                , Constants.SIGN_OBLIQUELINE, fileName);
        OssHandler ossHandler = new OssHandler();
        ossHandler.uploadFile(ossHandler.getClient(),Constants.OSS_BUCKETNAME,cdnRelativePath,filePath);
        //删除本地文件
        newFile.delete();
        return WebConstants.UPLOAD_URL_HOST + cdnRelativePath;
    }

    public boolean checkFormat(String fileName){
        if(format.contains(Constants.SIGN_COMMA)){
            String[] formats = format.split(Constants.SIGN_COMMA);
            for(String f : formats){
                if(fileName.endsWith(f)){
                    return true;
                }
            }
        }else if(fileName.endsWith(format)){
            return true;
        }
        return false;
    }

    private void checkFileName(String name) {
        int count = 0;
        char[] c = name.toCharArray();
        for(int i = 0; i < c.length; i ++)
        {
            String len = Integer.toBinaryString(c[i]);
            if(len.length() > 8)
                count ++;
        }
        if (count > 0) {
            throw new ServiceException("文件名不支持中文,请修改.");
        }
    }
    public float getTimeByVideo(String path) throws Exception{
        float time = VideoUtils.readTime(path);
        return time;
    }

    public boolean checkVideoTime(String filePath, Integer s) throws Exception {
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
