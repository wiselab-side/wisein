package com.wisein.wiselab.common.paging;

import lombok.Getter;
import lombok.Setter;

public class PaginationInfo {
	//카테고리, 제목 추가
	@Setter
	private String category;
	@Setter
	@Getter
	private String subject;
	private static int RECORDSPERPAGE = 10;
	private static int PAGESIZE       = 10;
	
	private int currentPageNo = 1;
	private int totalRecordCount;
	private int viewRecordsPerPage = RECORDSPERPAGE;
	
	private String viewAddr;
	
	private String searchType;
	private String keyword;

	@Setter
	@Getter
	private String likeOrder;
	@Setter
	@Getter
	private String scrapOrder;
	@Getter
	@Setter
	private String sortOrder;

	//=========== 추가 부분 ===========
	public String getCategory() {
		return category == null ? "all" : category;
	}



	/*public String getSubject() {
		return subject == null ? "all" : subject;
	}*/

	//public String getLikeOrder(){ return likeOrder == null ? "DESC" : likeOrder;}
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

	//pageCount
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
