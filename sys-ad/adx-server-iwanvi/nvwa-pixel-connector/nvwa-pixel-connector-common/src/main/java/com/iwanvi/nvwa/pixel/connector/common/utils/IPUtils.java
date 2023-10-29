package com.iwanvi.nvwa.pixel.connector.common.utils;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.TreeMap;
import java.util.Map.Entry;

import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.iwanvi.nvwa.common.utils.PropertyGetter;
import com.iwanvi.nvwa.common.utils.StringUtils;

public class IPUtils {
	
	private static final Logger LOG = LoggerFactory.getLogger(IPUtils.class);
	
	private static final String FILE_PATH_ZENG_DAT = PropertyGetter.getString("path.file.ip");
//	private static final String FILE_PATH_ZENG_DAT = "/data/geoip/iac-i-ip.csv";

	private TreeMap<Long, IPRow> cache = new TreeMap<Long, IPRow>();
	
	private static final String SPLITER_COMMA = ",";
	private static final String SPLITER_POINT = "\\.";

	private static IPUtils instance = null;

	private IPUtils() {
		try {
			load(FILE_PATH_ZENG_DAT);
		} catch (Exception e) {
			LOG.error("load failed. {}", FILE_PATH_ZENG_DAT);
		}
	}

	public synchronized static IPUtils getInstance() {
		if (instance == null) {
			instance = new IPUtils();
		}
		return instance;
	}

	/**
	 * @Title: load
	 * @Description: 载入Ip库文件
	 */
	public void load(String dataFile) throws FileNotFoundException, IOException {
		TreeMap<Long, IPRow> map = new TreeMap<Long, IPRow>();
		BufferedReader file = new BufferedReader(new FileReader(dataFile));
		try {
			//
			String content = null;
			while ((content = file.readLine()) != null) {
				String[] parts = content.split(SPLITER_COMMA);
				if (parts.length < 3) {
					LOG.warn("parse line failed. {}", content);
					continue;
				}
				try {
					IPRow row = new IPRow();
					
					row.setBegin(convertIpToLong(parts[0]));
					row.setEnd(convertIpToLong(parts[1]));
					row.setCode(parts[2]);
					
					map.put(row.getBegin(), row);
				} catch (Exception e) {
					LOG.error("parse line failed. {}", content);
				}
			}
			cache = map;
		} finally {
			try {
				file.close();
			} catch (IOException e) {
			}
		}
	}
	
	public IPRow getRow(Long key) {
		try {
			if (key == null) {
				return null;
			}
			Entry<Long, IPRow> entry = cache.floorEntry(key);
			if (entry == null || entry.getValue() == null) {
				return null;
			}
			return entry.getValue();
		} catch (Exception e) {
			LOG.error("getRow failed. key: {}", key, e);
		}
		return null;
	}
	
	public String getAreaString(String ip) {
		try {
			Long key = convertIpToLong(ip);
			IPRow row = getRow(key);
			if (row == null) {
				return null;
			}

			Long end = row.getEnd();
			if (end == null) {
				return null;
			}
			if (end.compareTo(key) > 0) {
				return row.getCode();
			}
		} catch (Exception e) {
			LOG.error("getAreaString failed. ip: {}", ip, e);
		}
		return null;
	}

	/**
	 * @Title: convertIpToLong
	 * @Description: 转换Ip为256进制整数
	 * @param ip
	 * @return long
	 */
	public static long convertIpToLong(String ip) {
		long intIp = 0;
		try {
			String[] checkIp = ip.split(SPLITER_POINT, 4);
			for (int i = 3, j = 0; i >= 0 && j <= 3; i--, j++) {
				if(NumberUtils.isCreatable(checkIp[j])){
					intIp += Integer.parseInt(checkIp[j]) * Math.pow(256, i);
				}
			}
		} catch (Exception e) {
			LOG.error("convertIpToLong failed. ip: {}", ip, e);
		}
		return intIp;
	}

	public int getAreaCode(String ip) {
		int code = -1;
		try {
			if(StringUtils.isBlank(ip)){
				return code;
			}
			
			String areaCode = getAreaString(ip);
			if(StringUtils.isNotBlank(areaCode) && NumberUtils.isDigits(areaCode)
					&& areaCode.length() > 4){
				areaCode = areaCode.substring(4);
				code = Integer.valueOf(areaCode);
			}
		} catch (Exception e) {
			LOG.error("IPUtils getAreaCode error. ip: {}", ip);
		}
		return code;
	}

	public static void main(String[] args) {
		try {
			long l1 = System.currentTimeMillis();
			int code = getInstance().getAreaCode("182.89.80.224");
			System.out.println((System.currentTimeMillis() - l1) + "--------" + code);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
