/**
 * 
 */
package com.iwanvi.adserv.ngx.router;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.iwanvi.adserv.ngx.BiddingContext;
import com.iwanvi.adserv.ngx.util.ThreadPools;
import com.iwanvi.nvwa.proto.BiddingProto.BiddingReq;

/**
 * @author wangweiping
 *
 */
public class DspLoggers {

	enum LogEvent {
		BID,CRID, WIN, NOBID, TIMEOUT, ERROR, OVER_QPS, LOWER_FLOOR, BID_PRICE, TIME, AREA,LANDURL, CODE
	}

	private static final Logger LOG = LoggerFactory.getLogger("dsp");
	private static final String LOG_DATE_PATTERN = "yyyyMMddHHmmss";

	private static final ConcurrentMap<String, Map<String, DspLog>> dspLoggers = new ConcurrentHashMap<>();

	public static void registerLogger(BiddingReq req) {
		dspLoggers.put(req.getId(), new ConcurrentHashMap<>());
	}

	public static void unregisterLogger(String reqId) {
		dspLoggers.remove(reqId);
	}

	public static DspLog createOrGetDspLog(BiddingReq req, String dspId) {
		Map<String, DspLog> dspLogHolder = dspLoggers.get(req.getId());
		if (dspLogHolder == null)
			return null;
		if (!dspLoggers.containsKey(dspId)) {
			dspLogHolder.put(dspId, new DspLog(req, dspId));
		}
		return dspLogHolder.get(dspId);
	}
	
	public static void createOrGetDspLog(BiddingReq req, List<String> dspIds) {
        Map<String, DspLog> dspLogHolder = dspLoggers.get(req.getId());
        for (String dspId:dspIds) {
            if (!dspLoggers.containsKey(dspId)) {
                dspLogHolder.put(dspId, new DspLog(req, dspId));
            }
        }
    }

	public static void time(BiddingReq req, String dspId, long time) {
		setLogEvent(LogEvent.TIME, req, dspId, time);
	}

	public static void bid(BiddingReq req, String dspId) {
		setLogEvent(LogEvent.BID, req, dspId);
	}

	public static void crid(BiddingReq req, String dspId,String crid) {
		setLogEvent(LogEvent.CRID, req, dspId,crid);
	}

	public static void win(BiddingReq req, String dspId, int winPrice) {
		setLogEvent(LogEvent.WIN, req, dspId, winPrice);
	}

	public static void nobid(BiddingReq req, String dspId) {
		setLogEvent(LogEvent.NOBID, req, dspId);
	}

	public static void timeout(BiddingReq req, String dspId) {
		setLogEvent(LogEvent.TIMEOUT, req, dspId);
	}

	public static void error(BiddingReq req, String dspId) {
		setLogEvent(LogEvent.ERROR, req, dspId);
	}

	public static void overQps(BiddingReq req, String dspId) {
		setLogEvent(LogEvent.OVER_QPS, req, dspId);
	}

	public static void lowerFloor(BiddingReq req, String dspId) {
		setLogEvent(LogEvent.LOWER_FLOOR, req, dspId);
	}

	public static void bidPrice(BiddingReq req, String dspId, int bidPrice) {
		setLogEvent(LogEvent.BID_PRICE, req, dspId, bidPrice);
	}

	public static void area(BiddingReq req, String dspId, Object... args) {
		setLogEvent(LogEvent.AREA, req, dspId, args);
	}

	public static void landUrl(BiddingReq req, String dspId,String landUrl){
		setLogEvent(LogEvent.LANDURL,req,dspId,landUrl);
	}

	public static void code(BiddingReq req, String dspId, String code){
		setLogEvent(LogEvent.CODE,req,dspId,code);
	}

	public static void setLogEvent(LogEvent event, BiddingReq req, String dspId, Object... args) {
		Map<String, DspLog> dspLogHolder = dspLoggers.get(req.getId());
		if (dspLogHolder == null || !dspLogHolder.containsKey(dspId))
			return;

		DspLog dspLog = dspLogHolder.get(dspId);
		switch (event) {
		case BID:
			dspLog.bid(true);
			break;
		case CRID:
			if (args != null && args.length > 0) {
				dspLog.crid(StringUtils.defaultString(args[0].toString()));
			}
			break;
		case WIN:
			dspLog.win(true);
			if (args != null && args.length > 0) {
				dspLog.winPrice(NumberUtils.toInt(args[0].toString()));
			}
			break;
		case NOBID:
			dspLog.nobid(true);
			break;
		case TIMEOUT:
			dspLog.timeout(true);
			break;
		case ERROR:
			dspLog.error(true);
			break;
		case OVER_QPS:
			dspLog.qps(true);
			break;
		case LOWER_FLOOR:
			dspLog.lower(true);
			break;
		case BID_PRICE:
			dspLog.bidPrice(NumberUtils.toInt(args[0].toString()));
			break;
		case TIME:
			dspLog.time(NumberUtils.toLong(args[0].toString()));
			break;
		case LANDURL:
			dspLog.landUrl(StringUtils.defaultString(args[0].toString()));
			break;
		case CODE:
				dspLog.code(StringUtils.defaultString(args[0].toString()));
				break;
		default:
			break;
		}
	}

	private static String now() {
		return DateFormatUtils.format(System.currentTimeMillis(), LOG_DATE_PATTERN);
	}

	public static void printLog(BiddingReq req) {
		try {
			Map<String, DspLog> dspLogHolder = dspLoggers.get(req.getId());
			if (dspLogHolder.isEmpty()) {
				return;
			}
			String now = now();
			ThreadPools.concurrentDoTask(() -> {
				dspLogHolder.forEach((dspid, dspLog) -> LOG.info("{}|{}|{}", now, req.getId(), dspLog));
			});
		} catch (Throwable ex) {
			// DO NOTHING
		} finally {
			dspLoggers.remove(req.getId());
		}
	}

	public static class DspLog {
		private final String dspId;
		private String appId; // 应用id
		private String posId; // 广告位id
		private String channel; // 渠道

		private boolean bid; // 竞价
		private boolean nobid; // 放弃竞价
		private boolean timeout; // 超时
		private boolean error; // 异常
		private boolean qps; // 超过qps限制
		private boolean lower; // 低于底价
		private boolean win;
		private int winPrice;
		private int bidPrice;
		private long time;
		private String crid;
		private String landUrl; // 落地页地址
		private String code;

		private DspLog(BiddingReq req, String dspId) {
			this.dspId = dspId;
			this.appId = req.getPosInfo(0).getAppId().toStringUtf8();
			this.posId = req.getPosInfo(0).getPosId().toStringUtf8();

			BiddingContext ctx = new BiddingContext(req);
			this.channel = ctx.getUserExtProperty("dx_channel");
		}

		public DspLog time(long time) {
			this.time = time;
			return this;
		}

		public DspLog appId(String appId) {
			this.appId = appId;
			return this;
		}

		public DspLog posId(String posId) {
			this.posId = posId;
			return this;
		}

		public DspLog channel(String channel) {
			this.channel = channel;
			return this;
		}

		public DspLog bid(boolean bid) {
			this.bid = bid;
			return this;
		}

		public DspLog nobid(boolean nobid) {
			this.nobid = nobid;
			return this;
		}

		public DspLog timeout(boolean timeout) {
			this.timeout = timeout;
			return this;
		}

		public DspLog error(boolean error) {
			this.error = error;
			return this;
		}

		public DspLog qps(boolean qps) {
			this.qps = qps;
			return this;
		}

		public DspLog lower(boolean lower) {
			this.lower = lower;
			return this;
		}

		public DspLog win(boolean win) {
			this.win = win;
			return this;
		}

		public DspLog winPrice(int price) {
			this.winPrice = price;
			return this;
		}

		public DspLog bidPrice(int price) {
			this.bidPrice = price;
			return this;
		}

		public DspLog crid(String crid) {
			this.crid = crid;
			return this;
		}

		public DspLog landUrl(String landUrl){
			this.landUrl = landUrl;
			return this;
		}

		public DspLog code(String code){
			this.code = code;
			return this;
		}

		@Override
		public String toString() {
			// dspid|appid|posid|channel|bid|win|nobid|timeout|error|qps|lower|winPrice|bidPrice|rt|crid
//			return String.format("%s|%s|%s|%s|%s|%s|%s|%s|%s|%s|%s|%s|%s|%s|%s|%s", dspId, appId, posId, channel, bid ? 1 : 0,
//					win ? 1 : 0, nobid ? 1 : 0, timeout ? 1 : 0, error ? 1 : 0, qps ? 1 : 0, lower ? 1 : 0,
//					this.winPrice, this.bidPrice, this.time,this.crid,this.landUrl);
			return String.format("%s|%s|%s|%s|%s|%s|%s|%s|%s|%s|%s|%s|%s|%s|%s|%s", dspId, appId, posId, channel, bid ? 1 : 0,
					win ? 1 : 0, nobid ? 1 : 0, timeout ? 1 : 0, error ? 1 : 0, qps ? 1 : 0, lower ? 1 : 0,
					this.winPrice, this.bidPrice, this.time,this.crid,this.code);
		}
	}
}
