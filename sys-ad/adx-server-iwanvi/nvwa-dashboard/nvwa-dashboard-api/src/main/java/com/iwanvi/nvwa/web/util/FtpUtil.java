package com.iwanvi.nvwa.web.util;

import com.iwanvi.nvwa.common.exception.ServiceException;
import com.iwanvi.nvwa.common.utils.Constants;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

import java.io.*;
import java.net.SocketException;

public class FtpUtil {
    /**
     * 获取FTPClient对象
     *
     * @param ftpHost     FTP主机服务器
     * @param ftpPassword FTP 登录密码
     * @param ftpUserName FTP登录用户名
     * @param ftpPort     FTP端口 默认为21
     * @return
     */
    public static FTPClient getFTPClient(String ftpHost, String ftpUserName,
                                         String ftpPassword, int ftpPort) {
        FTPClient ftpClient;
        try {
            ftpClient = new FTPClient();
            ftpClient.connect(ftpHost, ftpPort);// 连接FTP服务器
            ftpClient.login(ftpUserName, ftpPassword);// 登陆FTP服务器
            if (!FTPReply.isPositiveCompletion(ftpClient.getReplyCode())) {
                ftpClient.disconnect();
                throw new ServiceException("can not connect to FTP，please check your account or password.");
            }
        } catch (SocketException e) {
            throw new ServiceException("address of FTP is wrong", e);
        } catch (IOException e) {
            throw new ServiceException("port of FTP is wrong", e);
        }
        return ftpClient;
    }

    public static FTPClient getFTPClient() {
        FTPClient ftpClient;
        try {
            ftpClient = new FTPClient();
            ftpClient.connect(WebConstants.FTP_HOST, WebConstants.FTP_PORT);
            ftpClient.login(WebConstants.FTP_USERNAME, WebConstants.FTP_PASSWORD);
            if (!FTPReply.isPositiveCompletion(ftpClient.getReplyCode())) {
                ftpClient.disconnect();
                throw new ServiceException("can not connect to FTP，please check your account or password.");
            }
        } catch (SocketException e) {
            throw new ServiceException("address of FTP is wrong", e);
        } catch (IOException e) {
            throw new ServiceException("port of FTP is wrong", e);
        }
        return ftpClient;
    }

    /**
     * Description: 从FTP服务器下载文件
     * @param ftpClient FTP客户端
     * @param ftpPath FTP服务器中文件所在路径 格式： ftptest/aa
     * @param localPath 下载到本地的位置 格式：H:/download
     * @param fileName 文件名称
     */
    public static void downloadFtpFile(FTPClient ftpClient, String ftpPath, String localPath, String fileName) {
        try {
            ftpClient.setControlEncoding("UTF-8"); // 中文支持
            ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
            ftpClient.enterLocalPassiveMode();
            ftpClient.changeWorkingDirectory(ftpPath);
            File localFile = new File(localPath + File.separatorChar + fileName);
            OutputStream os = new FileOutputStream(localFile);
            ftpClient.retrieveFile(fileName, os);
            os.close();
            ftpClient.logout();
        } catch (FileNotFoundException e) {
            throw new ServiceException("file not found", e);
        } catch (SocketException e) {
            throw new ServiceException("connect to FTP filed", e);
        } catch (IOException e) {
            throw new ServiceException("read file filed", e);
        }
    }

    /**
     * Description: 向FTP服务器上传文件
     * @param ftpClient FTP客户端
     * @param ftpPath  FTP服务器中文件所在路径
     * @param fileName ftp文件名称
     * @param input 文件流
     * @return 成功返回true，否则返回false
     */
    public static boolean uploadFile(FTPClient ftpClient, String ftpPath, String fileName, InputStream input) {
        boolean success = false;
        try {
            int reply = ftpClient.getReplyCode();
            if (!FTPReply.isPositiveCompletion(reply)) {
                ftpClient.disconnect();
                return success;
            }
            ftpClient.setControlEncoding("UTF-8"); // 中文支持
            ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
            ftpClient.enterLocalPassiveMode();
            mkDir(ftpClient, ftpPath);
            ftpClient.storeFile(fileName, input);
            input.close();
            ftpClient.logout();
            success = true;
        } catch (Exception e) {
            throw new ServiceException("upload file to FTP error",e);
        } finally {
            if (ftpClient.isConnected()) {
                try {
                    ftpClient.disconnect();
                } catch (IOException ioe) {
                }
            }
        }
        return success;
    }

    private static void mkDir(FTPClient ftpClient, String path) throws Exception {
        String[] dirNames = path.split(Constants.SIGN_OBLIQUELINE);
        for (int i = 0; i < dirNames.length - 1; i++) {
            FTPFile[] dirs = ftpClient.listFiles(dirNames[i]);
            if (dirs.length == Constants.INTEGER_0) {
                ftpClient.makeDirectory(dirNames[i]);
                System.out.println("make dir " + dirNames[i]);
            }
            ftpClient.changeWorkingDirectory(dirNames[i]);
            System.out.println("cd dir " + dirNames[i]);
        }
    }
}
