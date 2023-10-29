/*
 * Copyright 2014-2016 f2time.com All right reserved.
 */
package com.iwanvi.adserv.ngx.util;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.iwanvi.adserv.ngx.cfg.MinervaCfg;
import com.iwanvi.adserv.ngx.repo.RepositoryFactory;
import com.iwanvi.nvwa.proto.AdModelsProto.AdCommonConfig;
import com.iwanvi.nvwa.proto.AdModelsProto.AdPlan;
import com.iwanvi.nvwa.proto.AdModelsProto.AdPositionFloorPrice;
import com.iwanvi.nvwa.proto.AdModelsProto.AdTypeConfig;
import com.iwanvi.nvwa.proto.AdModelsProto.AdUnit;
import com.iwanvi.nvwa.proto.AdModelsProto.Advertiser;
import com.iwanvi.nvwa.proto.AdModelsProto.Agent;
import com.iwanvi.nvwa.proto.AdModelsProto.AgentFloorPriceConfig;
import com.iwanvi.nvwa.proto.AdModelsProto.App;
import com.iwanvi.nvwa.proto.AdModelsProto.AreaLevel;
import com.iwanvi.nvwa.proto.AdModelsProto.Dsp;
import com.iwanvi.nvwa.proto.AdModelsProto.DspCreative;
import com.iwanvi.nvwa.proto.AdModelsProto.PubRecord;
import com.iwanvi.nvwa.proto.AdModelsProto.SspAdPosition;
import com.iwanvi.nvwa.proto.CommonProto.EntityType;

/**
 * 
 * @author wangwp
 */
public class PubIndexUtils {
	private static final Logger LOGGER = LoggerFactory.getLogger(PubIndexUtils.class);

	public static PubRecord record(AreaLevel areaLevel) {
		return PubRecord.newBuilder().setEntityType(EntityType.kAreaLevel).setData(areaLevel.toByteString()).build();
	}

	public static PubRecord record(AdPositionFloorPrice adfp) {
		return PubRecord.newBuilder().setEntityType(EntityType.kAdPositionFloorPrice).setData(adfp.toByteString())
				.build();
	}

	public static PubRecord record(Dsp dsp) {
		return PubRecord.newBuilder().setEntityType(EntityType.kDsp).setData(dsp.toByteString()).build();
	}

	public static PubRecord record(DspCreative dspCreative) {
		return PubRecord.newBuilder().setEntityType(EntityType.kDspCreative).setData(dspCreative.toByteString())
				.build();
	}

	public static PubRecord record(Agent agent) {
		return PubRecord.newBuilder().setEntityType(EntityType.kAgent).setData(agent.toByteString()).build();
	}

	public static PubRecord record(Advertiser advertiser) {
		return PubRecord.newBuilder().setEntityType(EntityType.kAdvertiser).setData(advertiser.toByteString()).build();
	}

	public static PubRecord record(AgentFloorPriceConfig agentFloorPriceConfig) {
		return PubRecord.newBuilder().setEntityType(EntityType.kAgentFloorPriceConfig)
				.setData(agentFloorPriceConfig.toByteString()).build();
	}

	public static PubRecord record(AdPlan adPlan) {
		return PubRecord.newBuilder().setEntityType(EntityType.kAdPlan).setData(adPlan.toByteString()).build();
	}

	public static PubRecord record(AdUnit adUnit) {
		return PubRecord.newBuilder().setEntityType(EntityType.kAdUnit).setData(adUnit.toByteString()).build();
	}

	public static PubRecord record(AdTypeConfig adPosition) {
		return PubRecord.newBuilder().setEntityType(EntityType.kAdTypeConfig).setData(adPosition.toByteString())
				.build();
	}

	public static PubRecord record(AdCommonConfig syscfg) {
		return PubRecord.newBuilder().setEntityType(EntityType.kCommonConfig).setData(syscfg.toByteString()).build();
	}

	public static PubRecord record(App app) {
		return PubRecord.newBuilder().setEntityType(EntityType.kApp).setData(app.toByteString()).build();
	}

	public static PubRecord record(SspAdPosition sspAdPosition) {
		return PubRecord.newBuilder().setEntityType(EntityType.kSspAdPosition).setData(sspAdPosition.toByteString())
				.build();
	}

	public static PubRecord readRecord(DataInputStream in) {
		byte[] sizeBytes = new byte[4];
		try {
			in.read(sizeBytes);
			int length = ByteUtils.bytes2int(sizeBytes);
			byte[] data = new byte[length];

			in.read(data);
			return PubRecord.newBuilder().mergeFrom(data).build();
		} catch (IOException ex) {
			LOGGER.error("", ex);
		}
		return null;
	}

	public static void flushMinervaData(String file) {
		try {
			loadAllDataToMem(file);
		} catch (Exception ex) {
			LOGGER.error("==导入广告引擎全量数据异常==", ex);
		}
	}

	private static void loadAllDataToMem(String file) {
		RepositoryFactory.getRepository().impFromPubIndexFile(file);
	}

	public static void downloadAndLoadAdNgxData(String fileName) {
		String datestr = new SimpleDateFormat("yyyyMMddHH").format(new Date());
		String savePath = String.format("%s%s%s%s%s", MinervaCfg.get().getLocalFilePath(), File.separator, datestr,
				File.separator, UUID.randomUUID().toString().replace("-", ""));

		LOGGER.info(savePath);
		OkHttpUtils.writeBodyToFile(fileName, savePath);
		// ScpUtil.download(notifyMsg.getFileName(), savePath);
		SBDStatHolder.clear();
		PubIndexUtils.flushMinervaData(savePath);
	}
	
	public static void main(String[] args) throws Exception {
		DataInputStream in = new DataInputStream(new FileInputStream("d:/ent_20190617000200.dat"));
		while(in.available()>0) {
			PubRecord r = PubIndexUtils.readRecord(in);
			if(r.getEntityType()==EntityType.kDsp) {
				System.out.println(Dsp.parseFrom(r.getData()));
			}
		}
	}
}
