/*
 * Copyright 2014-2016 f2time.com All right reserved.
 */
package com.iwanvi.adserv.ngx.util;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.iwanvi.adserv.ngx.cfg.MinervaCfg;
import com.iwanvi.nvwa.proto.AdModelsProto.PubRecord;

/**
 * 基于顺序写的广告引擎文件数据库
 * 
 * @author wangwp
 *
 */
public class MinervaDB {
	private static final Logger LOG = LoggerFactory.getLogger(MinervaDB.class);

	private static final File _MINERVA_DB_FILE = new File(
			MinervaCfg.get().getDBPath() + File.separator + "minerva.dat");

	private DataInputStream in;
	private DataOutputStream out;

	private MinervaDB() {
		try {
			if (!_MINERVA_DB_FILE.exists()) {
				_MINERVA_DB_FILE.getParentFile().mkdirs();
				_MINERVA_DB_FILE.createNewFile();
			}
			this.in = new DataInputStream(new FileInputStream(_MINERVA_DB_FILE));
			this.out = new DataOutputStream(new FileOutputStream(_MINERVA_DB_FILE, true));
		} catch (IOException ex) {
			LOG.error("", ex);
		}
	}

	public Iterator<PubRecord> iterator() {
		return new Itr();
	}

	private class Itr implements Iterator<PubRecord> {
		@Override
		public boolean hasNext() {
			try {
				return in.available() > 0;
			} catch (IOException ex) {
				LOG.error("", ex);
			}
			return false;
		}

		@Override
		public void remove() {
			// DO NOTHING
		}

		@Override
		public PubRecord next() {
			return PubIndexUtils.readRecord(in);
		}
	}

	public static MinervaDB open() {
		return new MinervaDB();
	}

	public void write(PubRecord record) {
		try {
			byte[] data = ByteUtils.encode(record.toByteArray());
			org.apache.commons.io.IOUtils.write(data, out);
		} catch (IOException ex) {
			LOG.error("", ex);
		}
	}

	public void close() {
		IOUtils.closeQuietly(out);
		IOUtils.closeQuietly(in);
	}

	public static long stats() {
		return !_MINERVA_DB_FILE.exists() ? -1
				: TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis() - _MINERVA_DB_FILE.lastModified());
	}

	public static void destroy() {
		FileUtils.deleteQuietly(_MINERVA_DB_FILE);
	}
}
