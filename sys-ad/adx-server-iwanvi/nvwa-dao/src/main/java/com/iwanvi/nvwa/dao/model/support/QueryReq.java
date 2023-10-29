package com.iwanvi.nvwa.dao.model.support;

/**
 * @author wangweiping
 *
 */
public abstract class QueryReq<E> {
	private static final int DEFAULT_PAGE_SIZE = 20;

	protected Integer pageNo;
	protected Integer pageSize;

	public Integer getPageNo() {
		return pageNo;
	}

	public void setPageNo(Integer pageNo) {
		this.pageNo = pageNo;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public final E toExample() {
		initPageInfoIfNeed();
		return buildExample();
	}

	public void initPageInfoIfNeed() {
		if (pageNo != null && pageNo > 0)
			pageNo = pageNo - 1;
		if (pageNo == null)
			pageNo = 0;
		if (pageSize == null)
			pageSize = DEFAULT_PAGE_SIZE;
	}

	public abstract E buildExample();
}
