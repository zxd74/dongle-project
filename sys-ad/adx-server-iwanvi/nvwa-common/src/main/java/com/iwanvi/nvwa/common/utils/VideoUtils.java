package com.iwanvi.nvwa.common.utils;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class VideoUtils {
    private static final Logger logger = LoggerFactory.getLogger(VideoUtils.class);

    public static String runCmd(String command) {
        InputStream stderr = null;
        InputStreamReader isr = null;
        BufferedReader br = null;
        try {
            Runtime rt = Runtime.getRuntime();
            Process proc = rt.exec(command);
            stderr = proc.getErrorStream();
            isr = new InputStreamReader(stderr);
            br = new BufferedReader(isr);
            String line = null;
            StringBuffer result = new StringBuffer();

            while ( (line = br.readLine()) != null) {
                result.append(line);
            }
            logger.info("ffmpeg result : {}",result);
            return result.toString();
        } catch (Throwable t) {
            logger.error("run cmd error",t);
        } finally {
            try {
                if (stderr != null) {
                    stderr.close();
                }
                if (isr != null) {
                    isr.close();
                }
                if (br != null) {
                    br.close();
                }
            } catch (IOException e) {
                logger.error("close io error",e);
            }
        }
        return null;
    }


    public static float readTime(String file) {
        String cmd = "ffmpeg -i "+file;
        String result = runCmd(cmd);
        String regexDuration = "Duration: (.*?), start: (.*?), bitrate: (\\d*) kb\\/s";
        Pattern pattern = Pattern.compile(regexDuration);
        Matcher m = pattern.matcher(result);
        if (m.find()) {
            float time = getTimelen(m.group(1));
            logger.info(file+",视频时长："+time+", 开始时间："+m.group(2)+",比特率："+m.group(3)+"kb/s");
            return time;
        }
        return 0;
    }

    public static String readSize(String file) {
        String cmd = "ffmpeg -i "+file;
        String result = runCmd(cmd);
        String regexDuration = "Video: (.*?), (.*?), (\\d*)x(\\d*)(.*?), (\\d*.?\\d*) fps";
        Pattern pattern = Pattern.compile(regexDuration);
        Matcher m = pattern.matcher(result);
        if (m.find()) {
            String size = m.group(3)+"x"+m.group(4);
            logger.info(file+",格式："+m.group(1)+", 尺寸："+m.group(3)+"x"+m.group(4));
            return size;
        }
        return null;
    }

    //格式:"00:00:10.68"
    private static float getTimelen(String timelen){
        float min=0;
        String strs[] = timelen.split(":");
        if (strs[0].compareTo("0") > 0) {
            min+=Integer.valueOf(strs[0])*60*60;//秒
        }
        if(strs[1].compareTo("0")>0){
            min+=Integer.valueOf(strs[1])*60;
        }
        if(strs[2].compareTo("0")>0){
            min+=Float.valueOf(strs[2]);
        }
        return min;
    }

}