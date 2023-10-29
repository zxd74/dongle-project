package com.iwanvi.nvwa.pixel.connector.crontab.consumer;

import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.iwanvi.nvwa.common.redis.server.RedisDao;
import com.iwanvi.nvwa.common.utils.Constants;
import com.iwanvi.nvwa.common.utils.JsonUtils;
import com.iwanvi.nvwa.common.utils.StringUtils;
import com.iwanvi.nvwa.dao.QuotaDidMapper;
import com.iwanvi.nvwa.dao.model.QuotaDid;
import com.iwanvi.nvwa.pixel.connector.common.utils.NvwaUtils;
import com.iwanvi.nvwa.pixel.connector.common.utils.PixelConstants;
import com.iwanvi.nvwa.pixel.connector.common.utils.ThreadDistribution;

@Transactional
@Service("redisConsumer")
public class RedisConsumer {
	
	private static final Logger LOG = LoggerFactory.getLogger("RedisConsumer");
	
	private static final int SEEP_TIME = 5 * 1000;
	
	@Resource
	private RedisDao redisDao;
	
	@Resource
	private QuotaDidMapper quotaDidMapper;
	
	@PostConstruct
	public void consume(){
		try {
			LOG.info("======consume start======");
			saveClickDid();
			pushToQueue();
		} catch (Exception e) {
			LOG.error("consume error. ", e);
		}
	}
	
	private void saveClickDid(){
		try {
			new Thread(new Runnable() {
				@Override
				public void run() {
					while (true) {
						try {
							String value = redisDao.rightPop(PixelConstants.KEY_REDIS_CLICK);
							if(StringUtils.isBlank(value)){
								Thread.sleep(SEEP_TIME);
							} else {
								Map<String, Object> map = JsonUtils.parse2Map(value);
								String did = NvwaUtils.obj2Empty(map.get("did"));
								String ipStr = NvwaUtils.obj2Empty(map.get("ip"));
								
								String areaCode = NvwaUtils.obj2Empty(map.get("areaCode"));
								if(StringUtils.isBlank(did)){
									LOG.info("saveQuotaDid exception did is null. value: {}", value);
									continue;
								}
								String mediaId = NvwaUtils.obj2Empty(map.get("mediaId"));
								Long clkTime = NvwaUtils.obj2long(map.get("clkTime"));
								Integer cid = NvwaUtils.obj2int(map.get("cid"));
								QuotaDid quotaDid = new QuotaDid();
								quotaDid.setFsId(mediaId);
								quotaDid.setClkTime(clkTime);
								quotaDid.setCreId(cid);
								quotaDid.setIsActive(Constants.INTEGER_0);
								quotaDid.setDid(did);
								quotaDid.setIp(getIp(ipStr));
								quotaDid.setAreaCode(areaCode);
								
								int daoResult = quotaDidMapper.insertSelective(quotaDid);
								LOG.info("saveQuotaDid. daoResult: {}, quotaDid: {}", daoResult, JsonUtils.TO_JSON(quotaDid));
								Thread.sleep(10);
							}
						} catch (Exception e) {
							LOG.error("saveClickDid did click error. ", e);
						}
					}
				}
			}).start();
		} catch (Exception e) {
			LOG.error("saveClickDid error. ", e);
		}
	}
	
	private String getIp(String ipStr){
		String result = StringUtils.EMPTY;
		try {
			if(StringUtils.isBlank(ipStr)){
				return result;
			}
			StringTokenizer st = new StringTokenizer(ipStr, Constants.SIGN_COMMA);
			result = st.nextToken();
			if(StringUtils.isNotBlank(result)){
				result = result.trim();
			}
		} catch (Exception e) {
			LOG.error("getIp error. ip: {}", ipStr, e);
		}
		return result;
	}
	
	public void pushToQueue() {
		try {
			LOG.info("=======pushToQueue=====");
			ThreadDistribution.getInstance().getScheduler().scheduleAtFixedRate(new Runnable() {
				@Override
				public void run() {
					Set<String> overNotifications = redisDao.setMembers(PixelConstants.KEY_REDIS_OVER_NOTIFY);
					try {
						if (overNotifications == null || overNotifications.isEmpty()) {
							return;
						}
						redisDao.setRemove(PixelConstants.KEY_REDIS_OVER_NOTIFY, overNotifications.toArray());
						redisDao.leftPushAll(PixelConstants.KEY_REDIS_OVER_NOTIFY_TEMP, overNotifications.toArray(new String[] {}));
						
						LOG.info("leftPushAll: {}", overNotifications.toString());
					} catch (Exception e) {
						LOG.error("pushToQueue error. overNotifications: {}", e, overNotifications == null ? "null" : overNotifications.toString());
					}
				}
			}, 10, 3, TimeUnit.SECONDS);
		} catch (Exception e) {
			LOG.info("pushToQueue error. ", e);
		}
	}
}
