/*
 * Copyright 2014-2016 f2time.com All right reserved.
 */
package com.iwanvi.adserv.ngx.util;

import java.util.BitSet;
import java.util.Calendar;
import java.util.List;

import com.iwanvi.nvwa.proto.AdModelsProto.SBDStat;

/**
 * 位集工具类
 * 
 * @author wangwp
 */
public final class BitSetUtils {
	
	public static BitSet long2BitSet(long value) {
		BitSet bits = new BitSet();
		int index = 0;
		while (value != 0L) {
			if (value % 2L != 0) {
				bits.set(index);
			}
			++index;
			value = value >>> 1L;
		}
		return bits;
	}

	public static long bitSet2Long(BitSet bitSet) {
		long value = 0L;
		for (int i = 0; i < bitSet.length(); ++i) {
			value += bitSet.get(i) ? (1L << i) : 0L;
		}
		return value;
	}

	public static void main(String[] args) throws Exception {
		SBDStat.Builder builder=SBDStat.newBuilder();
		
		BitSet bits=BitSetUtils.long2BitSet(4294967295L);
		System.out.println(bits);
		System.out.println(bits.length());
		
		for (int i = 0; i < bits.length() / 2; i++) {
			if (bits.get(i * 2)) {
				builder.addDeliveryHours(i);
			}
		}
		
		int hour=builder.getDeliveryHours(builder.getDeliveryHoursCount()-1);
		System.out.println(hour);
//		System.out.println(bits.toString());
		
		
		Calendar cal = Calendar.getInstance();
		hour = cal.get(Calendar.HOUR_OF_DAY);
		int minutes = cal.get(Calendar.MINUTE);
		int seconds = cal.get(Calendar.SECOND);

		SBDStat stat = builder.build();
		/*if (stat == null) {
			return adUnit.getTimeSlots() * 3600;
		}*/

		List<Integer> deliveryHours = stat.getDeliveryHoursList();
		System.out.println(deliveryHours);
		
		int index = 0;
		for (int i = 0; i < deliveryHours.size(); i++) {
			if (deliveryHours.get(i) >= hour) {
				index = i;
				break;
			}
		}
		System.out.println(deliveryHours.size());
		System.out.println("index: "+index+", hour: "+hour);
		int totalDeliveryTimes =deliveryHours.size() * 3600 - (index * 3600) - (minutes * 60 + seconds);
		System.out.println(totalDeliveryTimes);
	}
}
