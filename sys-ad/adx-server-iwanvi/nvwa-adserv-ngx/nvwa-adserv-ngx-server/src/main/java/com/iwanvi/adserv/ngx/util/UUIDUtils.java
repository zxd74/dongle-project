/*
 * Copyright 2019 iwanvi.com All right reserved.
 */
package com.iwanvi.adserv.ngx.util;

import java.io.File;
import java.io.FileInputStream;
import java.util.UUID;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.io.FileUtils;

import com.google.common.hash.Hashing;
import com.iwanvi.nvwa.rtb.proto.NvwaRtb.BidRequest;
import com.iwanvi.nvwa.rtb.proto.NvwaRtb.BidRequest.App;

import io.netty.util.CharsetUtil;

/**
 * @author weiping wang
 *
 */
public final class UUIDUtils {

	public static String uuid() {
		return UUID.randomUUID().toString().replace(Constants.SIGN_HYPHEN, 
				Constants.EMPTY);
	}
	
	public static void main(String[] args) throws Exception {
		String md5 = Hex.encodeHexString(Hashing.md5().hashString("123456", CharsetUtil.UTF_8).asBytes());
		System.out.println(md5);
		
		
		
		/*
		 * BidRequest.Builder brb = BidRequest.newBuilder(); brb.setId(uuid());
		 * brb.setApp(App.newBuilder().setBundle("com.iqiyi").setId("appid"));
		 * 
		 * FileUtils.writeByteArrayToFile(new File("d:\\rtb.bin"),
		 * brb.build().toByteArray());
		 */
		
		BidRequest b = BidRequest.parseFrom(new FileInputStream("d:\\rtb.bin"));
		System.out.println(b.getId());
	}
}
