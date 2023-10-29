package com.iwanvi.nvwa.crontab.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iwanvi.adserv.ngx.util.AdPositionFloorPriceUtils;
import com.iwanvi.nvwa.common.utils.Constants;
import com.iwanvi.nvwa.crontab.redis.ByteArrayRedisDao;
import com.iwanvi.nvwa.crontab.service.AdPositionPriceService;
import com.iwanvi.nvwa.dao.AdPositionMapper;
import com.iwanvi.nvwa.dao.AdPositionPriceMapper;
import com.iwanvi.nvwa.dao.model.AdPosition;
import com.iwanvi.nvwa.dao.model.AdPositionPrice;
import com.iwanvi.nvwa.dao.model.AdPositionPriceExample;
import com.iwanvi.nvwa.proto.AdModelsProto.AdPositionFloorPrice;

/**
 * 
 * @author wangwp
 */
@Service
public class AdPositionPriceServiceImpl implements AdPositionPriceService {
	private static final Logger LOG = LoggerFactory.getLogger(AdPositionPriceService.class);

	@Autowired
	private ByteArrayRedisDao redisDao;
	@Autowired
	private AdPositionPriceMapper adPositionPriceMapper;
	@Autowired
	private AdPositionMapper adPositionMapper;

	@Override
	public void cacheAdPositionPrice(Integer objectId) {
		AdPositionPriceExample example = new AdPositionPriceExample();
		example.createCriteria().andIdEqualTo(objectId);

		AdPositionPrice adPositionPrice = adPositionPriceMapper.selectByPrimaryKey(objectId);
		if (adPositionPrice != null) {
			AdPosition adp = adPositionMapper.selectByPrimaryKey(adPositionPrice.getPositionId());
			AdPositionFloorPrice adfp = AdPositionFloorPrice.newBuilder().setAdPositionId(adp.getUuid())
					.setAreaLevelId(adPositionPrice.getAreaLevel()).setIndustry(adPositionPrice.getIndustry())
					.setFloorPrice(adPositionPrice.getPrice()).build();

			String cacheKey = StringUtils.join(Constants.REDIS_KEY_PREFIX_ADPFP,
					AdPositionFloorPriceUtils.genHashKey(adfp));
			LOG.debug("==将媒体广告位底价写入缓存, key: {}, value: {}==", cacheKey, adfp.toString());

			redisDao.setWithNeverExpired(cacheKey, adfp.toByteArray());
		}
	}
}
