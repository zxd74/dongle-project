package com.iwanvi.nvwa.common.utils;

import java.util.UUID;

public class UUIDUtils {
	
	public static String getUUID() {
		return UUID.randomUUID().toString().replace("-", "");
	}
	
	public static void main(String[] args) {
		System.out.println(getUUID());
	}
}
