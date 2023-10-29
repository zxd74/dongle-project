package com.iwanvi.nvwa.common.utils;

import com.aliyun.openservices.log.Client;
import com.aliyun.openservices.log.common.Consts;
import com.aliyun.openservices.log.common.LogContent;
import com.aliyun.openservices.log.common.LogItem;
import com.aliyun.openservices.log.common.QueriedLog;
import com.aliyun.openservices.log.response.GetHistogramsResponse;
import com.aliyun.openservices.log.response.GetLogsResponse;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class AliyunLogUtil {
	private static final Logger logger = LoggerFactory.getLogger("AliyunLogUtil");

	private static Client client;

	public static long getTodayNumByTypeAndParams(String logType, String query, int from, int to) {
		long sum = 0;
		if (StringUtils.isNotBlank(logType)) {
			Client client = getLogClient();
			try {
				GetHistogramsResponse rep = null;
				int i = 0;
				while (true) {
					if (i > 3) {
						break;
					}
					i++;
					rep = client.GetHistograms(Constants.ALIYUN_LOG_PROJECT, logType, from, to, StringUtils.EMPTY, query);
					if (rep != null && rep.IsCompleted()) {
						sum = rep.GetTotalCount();
						break;
					}
					Thread.sleep(100);
				}
            } catch (Exception e) {
                logger.error("query aliyun log error", e);
			}
		}
		return sum;
	}
	
	public static long getTodayNumByTypeAndParamsWithSql(String logType, String query, int from, int to) {
		long sum = 0;
		if (StringUtils.isNotBlank(logType)) {
			Client client = getLogClient();
			try {
				GetLogsResponse rep = null;
				int i = 0;
				while (true) {
					if (i > 3) {
						break;
					}
					i++;
					rep = client.GetLogs(Constants.ALIYUN_LOG_PROJECT, logType, from, to, StringUtils.EMPTY, query);
					if (rep != null && rep.IsCompleted()) {
						QueriedLog log = rep.GetLogs().get(0);
						LogItem item = log.GetLogItem();
						LogContent content = item.GetLogContents().get(0);
						
						if (content.GetValue().equals("null")) {
							continue;
						}
						
						sum = Long.parseLong(content.GetValue());
						break;
					}
					Thread.sleep(100);
				}
			} catch (Exception e) {
				logger.error("query aliyun log error", e);
			}
		}
        logger.info(logType + "----" + query + "---" + sum);
		return sum;
	}

	public static List<Map<String, Object>> getQuotaWithSql(String logStore, String query, int from, int to) {
        List<Map<String, Object>> resultList = Lists.newArrayList();
        Map<String, Object> resultMap;
		Client client = getLogClient();
		try {
			GetLogsResponse rep = null;
			while (true) {
				rep = client.GetLogs(Constants.ALIYUN_LOG_PROJECT, logStore, from, to, StringUtils.EMPTY, query);
				if (rep != null && rep.IsCompleted()) {
					List<QueriedLog> logList = rep.GetLogs();
                    if (CollectionUtils.isNotEmpty(logList)) {
                        for (QueriedLog log : logList) {
                            resultMap = Maps.newHashMap();
                            LogItem item = log.GetLogItem();
                            List<LogContent> list = item.GetLogContents();
                            if (CollectionUtils.isNotEmpty(list)) {
                                for (LogContent content : list) {
                                    if (content.GetValue().equals("null")) {
                                        continue;
                                    }
                                    resultMap.put(content.GetKey(), content.GetValue());
                                }
                            }
                            resultList.add(resultMap);
                        }
                    }
                    break;
				} else {
				    logger.error("qeury aliyun log error, query is not completed");
                }
				Thread.sleep(300);
			}
		} catch (Exception e) {
			logger.error("query aliyun log error", e);
		}
        logger.info(logStore + "----" + query + "---" + JsonUtils.TO_JSON(resultList));
		return resultList;
	}

	public static Client getLogClient() {
		if (client == null) {
			client = new Client(Constants.ALIYUN_LOG_ENDPOINT, Constants.ALIYUN_LOG_ACCESSKEYID,
					Constants.ALIYUN_LOG_ACCESSKEYSECRET, null, true,
                    6, 5000, 30000);
		}
		return client;
	}

	public static List<String> getDistinctItem(String logStore, String query, String distinctItem, int from, int to) {
		List<String> list = null;
		try {
			String queryDistinct = StringUtils.concat(query, Constants.FORMMAT_DISTINCT);
			queryDistinct = StringUtils.buildString(queryDistinct, distinctItem);
			GetLogsResponse rep = null;
			int i = 0;
			while (true) {
				if (i > 3) {
					break;
				}
				i++;
				rep = getLogClient().GetLogs(Constants.ALIYUN_LOG_PROJECT, logStore, from, to, StringUtils.EMPTY, queryDistinct);
				if (rep != null && rep.IsCompleted()) {
					list = new ArrayList<String>();
					for (QueriedLog log : rep.GetLogs()) {
						LogItem item = log.GetLogItem();
						for (LogContent content : item.GetLogContents()) {
							if (content.GetValue().equals("null")) {
								continue;
							}
							list.add(content.GetValue());
						}
					}
					if (!list.isEmpty()) {
						break;
					}
				}
				Thread.sleep(100);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

    public static void main(String[] args) {
        int from = (int) (DateUtils.getFirstSecondOfDay(new Date()).getTime() / 1000);
        int to = (int) (new Date().getTime() / 1000);
//        String query = "*| select dspId,sum(timeout),sum(nobid),sum(error),sum(win),sum(qps),sum(lower),sum(bid) " +
//                "where dspId in ('toutiao','360') and timeout in (0,1) and win in (0,1) and nobid in (0,1) " +
//                "and error in (0,1) and qps in (0,1) and lower in (0,1) and bid in (0,1) group by dspId";
//        List list = getQuotaWithSql("ads-dsp", query, from, to);
        String query = "*| select dspId, count(*), sum(cost) group by dspId";
        List list = getQuotaWithSql("ads-exp", query, from, to);
        logger.info(JsonUtils.TO_JSON(list));
    }
}
