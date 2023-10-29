package com.iwanvi.nvwa.pixel.connector.common.service;

import com.aliyun.openservices.log.Client;
import com.aliyun.openservices.log.common.LogContent;
import com.aliyun.openservices.log.common.LogItem;
import com.aliyun.openservices.log.common.QueriedLog;
import com.aliyun.openservices.log.response.GetHistogramsResponse;
import com.aliyun.openservices.log.response.GetLogsResponse;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.iwanvi.nvwa.common.utils.Constants;
import com.iwanvi.nvwa.common.utils.StringUtils;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AliyunLogService {
	
	private static final Logger LOG = LoggerFactory.getLogger("AliyunLogService");

	private static Client client = new Client(Constants.ALIYUN_LOG_ENDPOINT, Constants.ALIYUN_LOG_ACCESSKEYID,
			Constants.ALIYUN_LOG_ACCESSKEYSECRET, null, true, 6, 5000, 30000);

	public static long getCount(String logStore, String query, int from, int to) {
		long sum = 0;
		try {
			GetHistogramsResponse rep = null;
			while (true) {
				rep = client.GetHistograms(Constants.ALIYUN_LOG_PROJECT, logStore, from, to, StringUtils.EMPTY, query);
				if (rep != null && rep.IsCompleted()) {
					sum = rep.GetTotalCount();
					break;
				}
				Thread.sleep(200);
			}
        } catch (Exception e) {
			LOG.error("getCount error. logStore: {}, query: {}", logStore, query, e);
		}
		return sum;
	}
	
	public static long getCountWithSql(String logStore, String query, int from, int to) {
		long sum = 0;
		try {
			GetLogsResponse rep = null;
			while (true) {
				rep = client.GetLogs(Constants.ALIYUN_LOG_PROJECT, logStore, from, to, StringUtils.EMPTY, query);
				if (rep != null && rep.IsCompleted()) {
					QueriedLog log = rep.GetLogs().get(0);
					LogItem item = log.GetLogItem();
					LogContent content = item.GetLogContents().get(0);
					
					if (content.GetValue().equals("null")) {
						break;
					}
					
					sum = Long.parseLong(content.GetValue());
					break;
				}
				Thread.sleep(200);
			}
		} catch (Exception e) {
			LOG.error("getCountWithSql error. ", e);
		}
		return sum;
	}

	public static List<Map<String, Object>> getQuotaWithSql(String logStore, String query, int from, int to) {
        List<Map<String, Object>> resultList = Lists.newArrayList();
        Map<String, Object> resultMap;
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
					LOG.error("qeury aliyun log error, query is not completed");
                }
				Thread.sleep(300);
			}
		} catch (Exception e) {
			LOG.error("getQuotaWithSql error. ", e);
		}
		return resultList;
	}

	public static List<String> getDistinctItem(String logStore, String query, String distinctItem, int from, int to) {
		List<String> list = null;
		try {
			String queryDistinct = StringUtils.concat(query, Constants.FORMMAT_DISTINCT);
			queryDistinct = StringUtils.buildString(queryDistinct, distinctItem);
			
			GetLogsResponse rep = null;
			while (true) {
				rep = client.GetLogs(Constants.ALIYUN_LOG_PROJECT, logStore, from, to, StringUtils.EMPTY, queryDistinct);
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
				Thread.sleep(200);
			}
		} catch (Exception e) {
			LOG.error("getDistinctItem error. ", e);
		}
		return list;
	}

}
