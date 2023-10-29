package com.iwanvi.nvwa.web.util;

import com.aliyun.oss.*;
import com.aliyun.oss.model.*;
import com.iwanvi.nvwa.common.utils.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;

public class OssHandler {
    public static OSSClient client ;
    private static final Logger logger = LoggerFactory.getLogger(OssHandler.class);

    public void OssHandler(){
        // 使用默认的OSS服务器地址创建OSSClient对象。
        client = new OSSClient(Constants.OSS_ENDPOINT,Constants.OSS_ACCESS_ID, Constants.OSS_ACCESS_KEY);
    }

    public void OssHandler(String accessId,String accessKey){
        // 使用默认的OSS服务器地址创建OSSClient对象。指定域
        client = new OSSClient(Constants.OSS_ENDPOINT,accessId, accessKey);
    }

    public void OssHandler(String endPoint){
        // 使用默认的OSS服务器地址创建OSSClient对象。
        client = new OSSClient(endPoint,Constants.OSS_ACCESS_ID, Constants.OSS_ACCESS_KEY);
    }

    // 创建Bucket.
    public static void ensureBucket(OSSClient client, String bucketName)
            throws OSSException, ClientException {

        try {
            // 创建bucket
            client.createBucket(bucketName);
        } catch (ServiceException e) {
            if (!OSSErrorCode.BUCKES_ALREADY_EXISTS.equals(e.getErrorCode())) {
                // 如果Bucket已经存在，则忽略
                throw e;
            }
        }
    }

    // 删除一个Bucket和其中的Objects
    public static void deleteBucket(OSSClient client, String bucketName)
            throws OSSException, ClientException {

        ObjectListing ObjectListing = client.listObjects(bucketName);
        List<OSSObjectSummary> listDeletes = ObjectListing
                .getObjectSummaries();
        for (int i = 0; i < listDeletes.size(); i++) {
            String objectName = listDeletes.get(i).getKey();
            // 如果不为空，先删除bucket下的文件
            client.deleteObject(bucketName, objectName);
        }
        client.deleteBucket(bucketName);
    }

    // 把Bucket设置为所有人可读
    private static void setBucketPublicReadable(OSSClient client, String bucketName)
            throws OSSException, ClientException {
        //创建bucket
        client.createBucket(bucketName);

        //设置bucket的访问权限，public-read-write权限
        client.setBucketAcl(bucketName, CannedAccessControlList.PublicRead);
    }

    // 上传文件
    public static void uploadFile(OSSClient client, String bucketName, String key, String filename)
            throws OSSException, ClientException, FileNotFoundException {
        File file = new File(filename);

        ObjectMetadata objectMeta = new ObjectMetadata();
        objectMeta.setContentLength(file.length());
        // 可以在metadata中标记文件类型
        String suffix = filename.substring(filename.lastIndexOf(Constants.SIGN_POINT) + 1);
        if (UploadHandler.IMAGE.getFormat().contains(suffix)) {
            objectMeta.setContentType("image/jpeg");
        } else if(suffix.equals("mp4")){
            objectMeta.setContentType("video/mp4");
        } else if (UploadHandler.VIDEO.getFormat().contains(suffix)) {
            objectMeta.setContentType("video/mpeg4");
        }

        InputStream input = new FileInputStream(file);
        client = new OSSClient(Constants.OSS_ENDPOINT,Constants.OSS_ACCESS_ID, Constants.OSS_ACCESS_KEY);
        PutObjectResult result = client.putObject(bucketName, key, input, objectMeta);
        logger.info("oss put result : "+result.getETag());
    }

    public static void uploadFile(OSSClient client, String bucketName, String key, File file)
            throws OSSException, ClientException, FileNotFoundException {
        ObjectMetadata objectMeta = new ObjectMetadata();
        objectMeta.setContentLength(file.length());
        // 可以在metadata中标记文件类型
        //objectMeta.setContentType("image/jpeg");

        InputStream input = new FileInputStream(file);
        PutObjectResult result = client.putObject(bucketName, key, input, objectMeta);
        logger.info("oss put result : "+result.getETag());
    }

    public static void uploadFile(OSSClient client, String bucketName, String key, File file,String contentType)
            throws OSSException, ClientException, FileNotFoundException {
        ObjectMetadata objectMeta = new ObjectMetadata();
        objectMeta.setContentLength(file.length());
        // 可以在metadata中标记文件类型  image/jpeg
        objectMeta.setContentType(contentType);

        InputStream input = new FileInputStream(file);
        PutObjectResult result = client.putObject(bucketName, key, input, objectMeta);
        logger.info("oss put result : "+result.getETag());
    }

    // 下载文件
    public static void downloadFile(OSSClient client, String bucketName, String key, String filename)
            throws OSSException, ClientException {
        client.getObject(new GetObjectRequest(bucketName, key),
                new File(filename));
    }

    public OSSClient getClient(){
        return this.client;
    }
}
