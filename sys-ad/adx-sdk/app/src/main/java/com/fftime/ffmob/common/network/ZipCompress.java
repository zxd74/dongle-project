package com.fftime.ffmob.common.network;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 * Zip 压缩、解压工具类
 * 
 * @author MR.Z
 * 
 */
public class ZipCompress {

  /**
   * GZIP 压缩
   * 
   * @param data
   * @return 如果data == null 或者 空数组 则直接返回 data
   */
  public static byte[] compressByGzip(byte[] data) {
    if (data == null || 0 == data.length) {
      return data;
    }
    byte[] gzipData = null;
    ByteArrayOutputStream out = new ByteArrayOutputStream();
    GZIPOutputStream gzip = null;
    try {
      gzip = new GZIPOutputStream(out);
      gzip.write(data);
      gzip.finish();
      gzipData = out.toByteArray();
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      try {
        if (gzip != null) {
          gzip.close();
        }

        if (out != null) {
          out.close();
        }
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    return gzipData;
  }

  /**
   * GZIP 解压
   * 
   * @param compressedData
   * @return 如果compressedData == null 或者空数组 则直接返回compressData
   */
  public static byte[] decompressByGzip(byte[] compressedData) {
    if (compressedData == null || compressedData.length == 0) {
      return compressedData;
    }
    ByteArrayInputStream in = new ByteArrayInputStream(compressedData);
    GZIPInputStream gzip = null;
    ByteArrayOutputStream out = new ByteArrayOutputStream();

    byte[] buffer = new byte[1024];
    int len = 0;
    byte[] gzipData = null;
    try {
      gzip = new GZIPInputStream(in);
      while ((len = gzip.read(buffer)) != -1) {
        out.write(buffer, 0, len);
      }
      out.flush();
      gzipData = out.toByteArray();
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      try {
        if (gzip != null) {
          gzip.close();
        }

        if (in != null) {
          in.close();
        }

        if (out != null) {
          out.close();
        }
      } catch (Exception e) {
        e.printStackTrace();
      }
    }

    return gzipData;
  }
}
