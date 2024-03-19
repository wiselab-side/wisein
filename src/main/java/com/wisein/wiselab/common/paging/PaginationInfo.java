package com.wisein.wiselab.common.paging;

public class PaginationInfo {
	
	private static int RECORDSPERPAGE = 10;
	private static int PAGESIZE       = 10;
	
	private int currentPageNo = 1;
	private int totalRecordCount;
	private int viewRecordsPerPage = RECORDSPERPAGE;
	
	private String viewAddr;
	
	private String searchType;
	private String keyword;

	private String category;

	private String sort;

	private String order;

	private String reply;

	private String who;

	public String getWho() {
		return who;
	}

	public void setWho(String who) {
		this.who = who;
	}

	public String getReply() {
		return reply;
	}

	public void setReply(String reply) {
		this.reply = reply;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getViewAddr() {
		return viewAddr != null ? viewAddr.replace("/", "") : null;
	}
	public void setViewAddr(String viewAddr) {
		this.viewAddr = viewAddr;
	}
	public int getCurrentPageNo() {
		return currentPageNo;
	}
	public void setCurrentPageNo(int currentPageNo) {
		this.currentPageNo = currentPageNo > 0 ? currentPageNo : 1;
	}
	public void setTotalRecordCount(int totalRecordCount) {
		this.totalRecordCount = totalRecordCount;
	}
	public String getSearchType() {
		return searchType == null ? "all" : searchType;
	}
	public void setSearchType(String searchType) {
		this.searchType = searchType;
	}
	public String getKeyword() {
		return keyword == null ? "" : keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	
	public int getFirstPage() {
		return ((currentPageNo - 1) / PAGESIZE) * (PAGESIZE) + 1;
	}
	public int getLastPage() {
		int calcPageSize = (((this.currentPageNo - 1) / PAGESIZE) + 1) * PAGESIZE;
		return getTotalPageCount() < calcPageSize ? getTotalPageCount() : calcPageSize ;
	}
	public int getTotalPageCount() {
		return (int) Math.ceil((this.totalRecordCount / (double)PAGESIZE));
	}
	public int getFirstRecordIndex() {
		return (currentPageNo - 1) * getViewRecordsPerPage();
	}
	public int getLastRecordIndex() {
		return currentPageNo * getViewRecordsPerPage();
	}
	public boolean isHasPreviousPage() {
		return getFirstPage() == 1 ? false : true;
	}
	public boolean isHasNextPage() {
		return (getLastPage() * getViewRecordsPerPage()) < totalRecordCount ? true : false;
	}

	public int getViewRecordsPerPage() {
		return viewRecordsPerPage < 1 ? RECORDSPERPAGE : viewRecordsPerPage;
	}

	public void setViewRecordsPerPage(int viewRecordsPerPage) {
		this.viewRecordsPerPage = viewRecordsPerPage;
	}
}
