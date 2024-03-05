package com.wisein.wiselab.dao;


import com.wisein.wiselab.dto.QaListDTO;
import com.wisein.wiselab.dto.ScrapBoardDTO;
import com.wisein.wiselab.dto.TipBoardDTO;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

public interface ScrapDAO {

    /* Scrap 여부 조회 */
    public String TipScrapYN(ScrapBoardDTO dto) throws Exception;

    /* Scrap 여부 조회 */
    public String QaScrapYN(ScrapBoardDTO dto) throws Exception;

    /* Scrap 등록 */
    public void insertScrap(ScrapBoardDTO dto) throws Exception;

    /* Scrap 재등록 */
    public void doScrap(ScrapBoardDTO dto) throws Exception;

    /* Scrap 해제 */
    public void undoScrap(ScrapBoardDTO dto) throws Exception;

    /* Scrap 등록시 게시글 ScrapCount 증가*/
    public void addTipScrapCount(int num) throws Exception;

    /* Scrap 해제시 게시글 ScrapCount 감소 */
    public void delTipScrapCount(int num) throws Exception;

    /* Scrap 등록시 게시글 ScrapCount 증가*/
    public void addQaScrapCount(int num) throws Exception;

    /* Scrap 해제시 게시글 ScrapCount 감소 */
    public void delQaScrapCount(int num) throws Exception;

    /* Scrap 등록 시 원본 게시글 count 증가를 위한 parentNum 조회 */
    public int getScrapParentNum(int num) throws Exception;

    /* TIP Scrap 모아보기 */
    public List<TipBoardDTO> selectTipScrap(Map<String, Object> tipMap) throws Exception;

    /* TIP Scrap Cnt */
    public int selectTipTotalCount(Map<String, Object> tipMap) throws Exception;

    /* QA Scrap 모아보기 */
    public List<QaListDTO> selectQaScrap(Map<String, Object> qaMap) throws Exception;

    /* QA Scrap Cnt */
    public int selectQaTotalCount(Map<String, Object> qaMap) throws Exception;

}