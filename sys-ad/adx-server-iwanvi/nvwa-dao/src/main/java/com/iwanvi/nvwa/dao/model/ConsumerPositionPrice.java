package com.iwanvi.nvwa.dao.model;

import java.util.ArrayList;
import java.util.Arrays;

public class ConsumerPositionPrice {
	private Integer id;

	private Integer cid;

	private Integer pid;

	private Integer price;

	private Integer aid;

	private String a_name;

	private String platform;

	private String p_name;

	private Double price_double;

	public Double getPrice_double() {
		price_double = (double) ( (double)getPrice() / 100);
		return price_double;
	}

	public void setPrice_double(Double price_double) {
		this.price_double = price_double;
	}

	public Integer getAid() {
		return aid;
	}

	public void setAid(Integer aid) {
		this.aid = aid;
	}

	public String getA_name() {
		return a_name;
	}

	public void setA_name(String a_name) {
		this.a_name = a_name;
	}

	public String getPlatform() {
		return platform;
	}

	public void setPlatform(String platform) {
		this.platform = platform;
	}

	public String getP_name() {
		return p_name;
	}

	public void setP_name(String p_name) {
		this.p_name = p_name;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getCid() {
		return cid;
	}

	public void setCid(Integer cid) {
		this.cid = cid;
	}

	public Integer getPid() {
		return pid;
	}

	public void setPid(Integer pid) {
		this.pid = pid;
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to
	 * the database table consumer_position_price
	 *
	 * @mbg.generated
	 * @project https://github.com/itfsw/mybatis-generator-plugin
	 */
	public static ConsumerPositionPrice.Builder builder() {
		return new ConsumerPositionPrice.Builder();
	}

	/**
	 * This class was generated by MyBatis Generator. This class corresponds to the
	 * database table consumer_position_price
	 *
	 * @mbg.generated
	 * @project https://github.com/itfsw/mybatis-generator-plugin
	 */
	public static class Builder {
		/**
		 * This field was generated by MyBatis Generator. This field corresponds to the
		 * database table consumer_position_price
		 *
		 * @mbg.generated
		 * @project https://github.com/itfsw/mybatis-generator-plugin
		 */
		private ConsumerPositionPrice obj;

		/**
		 * This method was generated by MyBatis Generator. This method corresponds to
		 * the database table consumer_position_price
		 *
		 * @mbg.generated
		 * @project https://github.com/itfsw/mybatis-generator-plugin
		 */
		public Builder() {
			this.obj = new ConsumerPositionPrice();
		}

		/**
		 * This method was generated by MyBatis Generator. This method sets the value of
		 * the database column consumer_position_price.id
		 *
		 * @param id the value for consumer_position_price.id
		 *
		 * @mbg.generated
		 * @project https://github.com/itfsw/mybatis-generator-plugin
		 */
		public Builder id(Integer id) {
			obj.setId(id);
			return this;
		}

		/**
		 * This method was generated by MyBatis Generator. This method sets the value of
		 * the database column consumer_position_price.cid
		 *
		 * @param cid the value for consumer_position_price.cid
		 *
		 * @mbg.generated
		 * @project https://github.com/itfsw/mybatis-generator-plugin
		 */
		public Builder cid(Integer cid) {
			obj.setCid(cid);
			return this;
		}

		/**
		 * This method was generated by MyBatis Generator. This method sets the value of
		 * the database column consumer_position_price.pid
		 *
		 * @param pid the value for consumer_position_price.pid
		 *
		 * @mbg.generated
		 * @project https://github.com/itfsw/mybatis-generator-plugin
		 */
		public Builder pid(Integer pid) {
			obj.setPid(pid);
			return this;
		}

		/**
		 * This method was generated by MyBatis Generator. This method sets the value of
		 * the database column consumer_position_price.price
		 *
		 * @param price the value for consumer_position_price.price
		 *
		 * @mbg.generated
		 * @project https://github.com/itfsw/mybatis-generator-plugin
		 */
		public Builder price(Integer price) {
			obj.setPrice(price);
			return this;
		}

		/**
		 * This method was generated by MyBatis Generator. This method corresponds to
		 * the database table consumer_position_price
		 *
		 * @mbg.generated
		 * @project https://github.com/itfsw/mybatis-generator-plugin
		 */
		public ConsumerPositionPrice build() {
			return this.obj;
		}
	}

	/**
	 * This enum was generated by MyBatis Generator. This enum corresponds to the
	 * database table consumer_position_price
	 *
	 * @mbg.generated
	 * @project https://github.com/itfsw/mybatis-generator-plugin
	 */
	public enum Column {
		id("id", "id", "INTEGER", false), cid("cid", "cid", "INTEGER", false), pid("pid", "pid", "INTEGER", false),
		price("price", "price", "INTEGER", false);

		/**
		 * This field was generated by MyBatis Generator. This field corresponds to the
		 * database table consumer_position_price
		 *
		 * @mbg.generated
		 * @project https://github.com/itfsw/mybatis-generator-plugin
		 */
		private static final String BEGINNING_DELIMITER = "\"";

		/**
		 * This field was generated by MyBatis Generator. This field corresponds to the
		 * database table consumer_position_price
		 *
		 * @mbg.generated
		 * @project https://github.com/itfsw/mybatis-generator-plugin
		 */
		private static final String ENDING_DELIMITER = "\"";

		/**
		 * This field was generated by MyBatis Generator. This field corresponds to the
		 * database table consumer_position_price
		 *
		 * @mbg.generated
		 * @project https://github.com/itfsw/mybatis-generator-plugin
		 */
		private final String column;

		/**
		 * This field was generated by MyBatis Generator. This field corresponds to the
		 * database table consumer_position_price
		 *
		 * @mbg.generated
		 * @project https://github.com/itfsw/mybatis-generator-plugin
		 */
		private final boolean isColumnNameDelimited;

		/**
		 * This field was generated by MyBatis Generator. This field corresponds to the
		 * database table consumer_position_price
		 *
		 * @mbg.generated
		 * @project https://github.com/itfsw/mybatis-generator-plugin
		 */
		private final String javaProperty;

		/**
		 * This field was generated by MyBatis Generator. This field corresponds to the
		 * database table consumer_position_price
		 *
		 * @mbg.generated
		 * @project https://github.com/itfsw/mybatis-generator-plugin
		 */
		private final String jdbcType;

		/**
		 * This method was generated by MyBatis Generator. This method corresponds to
		 * the database table consumer_position_price
		 *
		 * @mbg.generated
		 * @project https://github.com/itfsw/mybatis-generator-plugin
		 */
		public String value() {
			return this.column;
		}

		/**
		 * This method was generated by MyBatis Generator. This method corresponds to
		 * the database table consumer_position_price
		 *
		 * @mbg.generated
		 * @project https://github.com/itfsw/mybatis-generator-plugin
		 */
		public String getValue() {
			return this.column;
		}

		/**
		 * This method was generated by MyBatis Generator. This method corresponds to
		 * the database table consumer_position_price
		 *
		 * @mbg.generated
		 * @project https://github.com/itfsw/mybatis-generator-plugin
		 */
		public String getJavaProperty() {
			return this.javaProperty;
		}

		/**
		 * This method was generated by MyBatis Generator. This method corresponds to
		 * the database table consumer_position_price
		 *
		 * @mbg.generated
		 * @project https://github.com/itfsw/mybatis-generator-plugin
		 */
		public String getJdbcType() {
			return this.jdbcType;
		}

		/**
		 * This method was generated by MyBatis Generator. This method corresponds to
		 * the database table consumer_position_price
		 *
		 * @mbg.generated
		 * @project https://github.com/itfsw/mybatis-generator-plugin
		 */
		Column(String column, String javaProperty, String jdbcType, boolean isColumnNameDelimited) {
			this.column = column;
			this.javaProperty = javaProperty;
			this.jdbcType = jdbcType;
			this.isColumnNameDelimited = isColumnNameDelimited;
		}

		/**
		 * This method was generated by MyBatis Generator. This method corresponds to
		 * the database table consumer_position_price
		 *
		 * @mbg.generated
		 * @project https://github.com/itfsw/mybatis-generator-plugin
		 */
		public String desc() {
			return this.getEscapedColumnName() + " DESC";
		}

		/**
		 * This method was generated by MyBatis Generator. This method corresponds to
		 * the database table consumer_position_price
		 *
		 * @mbg.generated
		 * @project https://github.com/itfsw/mybatis-generator-plugin
		 */
		public String asc() {
			return this.getEscapedColumnName() + " ASC";
		}

		/**
		 * This method was generated by MyBatis Generator. This method corresponds to
		 * the database table consumer_position_price
		 *
		 * @mbg.generated
		 * @project https://github.com/itfsw/mybatis-generator-plugin
		 */
		public static Column[] excludes(Column... excludes) {
			ArrayList<Column> columns = new ArrayList<>(Arrays.asList(Column.values()));
			if (excludes != null && excludes.length > 0) {
				columns.removeAll(new ArrayList<>(Arrays.asList(excludes)));
			}
			return columns.toArray(new Column[] {});
		}

		/**
		 * This method was generated by MyBatis Generator. This method corresponds to
		 * the database table consumer_position_price
		 *
		 * @mbg.generated
		 * @project https://github.com/itfsw/mybatis-generator-plugin
		 */
		public String getEscapedColumnName() {
			if (this.isColumnNameDelimited) {
				return new StringBuilder().append(BEGINNING_DELIMITER).append(this.column).append(ENDING_DELIMITER)
						.toString();
			} else {
				return this.column;
			}
		}
	}
}