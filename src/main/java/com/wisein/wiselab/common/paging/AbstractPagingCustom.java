package com.wisein.wiselab.common.paging;

import java.util.HashMap;
import java.util.Map;

public abstract class AbstractPagingCustom {
	/*
		카테고리와 제목을 추가해
		페이지가 넘어갔을 때 옵션이 풀리는 오류를 해결했다
	 */
	public String render(PaginationInfo pageInfo) {
			
			StringBuilder sbuilder = new StringBuilder();

			if(pageInfo.isHasPreviousPage() == true) {
				
				Map<String, Object> map1 = new HashMap<String, Object>();
				
				map1.put("currentPageNo", 1);
				map1.put("searchType", pageInfo.getSearchType());
				map1.put("keyword", pageInfo.getKeyword());
				// ============== 추가부분 ==============
				map1.put("category", pageInfo.getCategory());
				map1.put("subject", pageInfo.getSubject());
				map1.put("likeOrder", pageInfo.getLikeOrder());

				sbuilder.append(replacesTag(getFirstPagetag(), pageInfo.getViewAddr()+"?"+makeQueryString(map1)));
			}
			
			if(pageInfo.isHasPreviousPage() == true) {				
				Map<String, Object> map2 = new HashMap<String, Object>();
					
				map2.put("currentPageNo", pageInfo.getFirstPage()-1); 
				map2.put("searchType", pageInfo.getSearchType()); 
				map2.put("keyword", pageInfo.getKeyword());
				// ============== 추가부분 ==============
				map2.put("category", pageInfo.getCategory());
				map2.put("subject", pageInfo.getSubject());
				map2.put("likeOrder", pageInfo.getLikeOrder());
					
				sbuilder.append(replacesTag(getPreviousPagetag(), pageInfo.getViewAddr()+"?"+makeQueryString(map2)));
			}
			
			Map<String, Object> map = new HashMap<String, Object>();
			for(int idx=pageInfo.getFirstPage(); idx<=pageInfo.getLastPage(); idx++) {
				map.put("currentPageNo", idx);
				map.put("searchType", pageInfo.getSearchType()); 
				map.put("keyword", pageInfo.getKeyword());
				// ============== 추가부분 ==============
				map.put("category", pageInfo.getCategory());
				map.put("subject", pageInfo.getSubject());
				map.put("likeOrder", pageInfo.getLikeOrder());

				sbuilder.append(replacesTag(getPageNumTag(), pageInfo.getViewAddr()+"?"+makeQueryString(map) , pageInfo.getCurrentPageNo()==idx ? "active":"", idx+""));
			}
			
			if(pageInfo.isHasNextPage() == true) {
				Map<String, Object> map3 = new HashMap<String, Object>();
					
				map3.put("currentPageNo", pageInfo.getLastPage()+1);
				map3.put("searchType", pageInfo.getSearchType()); 
				map3.put("keyword", pageInfo.getKeyword());
				// ============== 추가부분 ==============
				map3.put("category", pageInfo.getCategory());
				map3.put("subject", pageInfo.getSubject());
				map3.put("likeOrder", pageInfo.getLikeOrder());
					
				sbuilder.append(replacesTag(getNextPagetag(), pageInfo.getViewAddr()+"?"+makeQueryString(map3)));
			}
			
			if(pageInfo.isHasNextPage() == true) {				
				Map<String, Object> map4 = new HashMap<String, Object>();
				
				map4.put("currentPageNo", pageInfo.getTotalPageCount());
				map4.put("searchType", pageInfo.getSearchType()); 
				map4.put("keyword", pageInfo.getKeyword());
				// ============== 추가부분 ==============
				map4.put("category", pageInfo.getCategory());
				map4.put("subject", pageInfo.getSubject());
				map4.put("likeOrder", pageInfo.getLikeOrder());
				
				sbuilder.append(replacesTag(getLastPagetag(), pageInfo.getViewAddr()+"?"+makeQueryString(map4)));
			}
			
			return sbuilder.toString();
	}

	private String replacesTag(String tag, String... arg) {
		
		if ("".equals(tag) || tag == null) return "";

		int i = 0;
		String result = tag;
		
		for (String item : arg) {
			result = result.replace("{" + i + "}", item);
			i++;
		}
		return result;
	}
	
	private String makeQueryString(Map<String,Object> map) {
		
		StringBuilder builder = new StringBuilder();
		int sizeCheck = 0;
		
		for(String key : map.keySet()) {
			if(map.size()-1 == sizeCheck) {
				builder.append(key+"="+map.get(key));
			} else {
				builder.append(key+"="+map.get(key)+"&");
			}
			sizeCheck++;
		}
		return builder.toString();
	}
	
	abstract String getFirstPagetag();
	abstract String getPreviousPagetag();
	abstract String getPageNumTag();
	abstract String getNextPagetag();
	abstract String getLastPagetag();
}
