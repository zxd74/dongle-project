package com.iwanvi.nvwa.openapi.dsp.utils;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

public class FileUtils {

	public static String readFile(String fileName) {
		String result = StringUtils.EMPTY;
		try {
			File file = new File(fileName);
			BufferedReader reader = null;
			reader = new BufferedReader(new FileReader(file));
			String line = null;
			StringBuffer buffer = new StringBuffer();
			while ((line = reader.readLine()) != null) {
				buffer.append(line);
			}
			reader.close();
			result = buffer.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public static void writeToFile(String lineStr, String file, Boolean isAppend) {
		try {
			BufferedWriter bWriter = new BufferedWriter(new FileWriter(file, isAppend));
			bWriter.write(lineStr);
			bWriter.newLine();
			bWriter.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 下载文件
	public static void downloadFile(String path, String url) {
		HttpClient client = null;
		try {
			// 创建HttpClient对象
			client = new DefaultHttpClient();
			// 获得HttpGet对象
			// HttpGet httpGet = getHttpGet(url, null, null);
			HttpGet httpGet = new HttpGet(url);
			// 发送请求获得返回结果
			HttpResponse response = client.execute(httpGet);
			// 如果成功
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				byte[] result = EntityUtils.toByteArray(response.getEntity());
				BufferedOutputStream bw = null;
				try {
					// 创建文件对象
					File f = new File(path);
					// 创建文件路径
					if (!f.getParentFile().exists())
						f.getParentFile().mkdirs();
					// 写入文件
					bw = new BufferedOutputStream(new FileOutputStream(path));
					bw.write(result);
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					try {
						if (bw != null)
							bw.close();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			} else {// 如果失败
				StringBuffer errorMsg = new StringBuffer();
				errorMsg.append("httpStatus:");
				errorMsg.append(response.getStatusLine().getStatusCode());
				errorMsg.append(response.getStatusLine().getReasonPhrase());
				errorMsg.append(", Header: ");
				Header[] headers = response.getAllHeaders();
				for (Header header : headers) {
					errorMsg.append(header.getName());
					errorMsg.append(":");
					errorMsg.append(header.getValue());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				client.getConnectionManager().shutdown();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 
	 * @param filename
	 *            目标文件
	 * @param charset
	 *            目标文件的编码格式
	 */
	public static void readDesc(String filename, String charset) {

		RandomAccessFile rf = null;
		try {
			long l1 = System.currentTimeMillis();
			rf = new RandomAccessFile(filename, "r");
			long len = rf.length();
			long start = rf.getFilePointer();
			long nextend = start + len - 1;
			System.out.println("start------" + start);
			System.out.println("nextend------" + nextend);
			String line;
			rf.seek(nextend);
			int c = -1;
			while (nextend > start) {
				c = rf.read();
				if (c == '\n' || c == '\r') {
					line = rf.readLine();
					if (line != null) {
						System.out.println(new String(line.getBytes("ISO-8859-1"), charset));
					} else {
						System.out.println(line);
					}
					nextend--;
				}
				nextend--;
				rf.seek(nextend);
				if (nextend == 0) {// 当文件指针退至文件开始处，输出第一行
					// System.out.println(rf.readLine());
					System.out.println(new String(rf.readLine().getBytes("ISO-8859-1"), charset));
				}
			}
			System.out.println("elapsed: " + (System.currentTimeMillis() - l1));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rf != null)
					rf.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	

	public static void main(String[] args) {
//		downloadFile("e:/tokens.txt", "http://127.0.0.1/download/temp/tokens.txt");
//		String str = readFile("d:\\1.txt");
		System.out.println("c9f65b4f-3069-4506-b9e0-627dc97d3c77".toUpperCase());
//		readBuffer("/data/temp/jd_ok.log");
//		readFile("d:/CRT/ipad_product.txt");
//		readDesc("d:/CRT/ipad_product.txt", "utf-8");
	}
}
