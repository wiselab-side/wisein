package com.wisein.wiselab.service;

import com.wisein.wiselab.dao.ScrapDAO;
import com.wisein.wiselab.dao.TipBoardDAO;
import com.wisein.wiselab.dto.QaListDTO;
import com.wisein.wiselab.dto.ScrapBoardDTO;
import com.wisein.wiselab.dto.TipBoardDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class ScrapServiceImpl implements ScrapService {

    @Autowired
    private ScrapDAO dao;

    @Autowired
    private TipBoardDAO tipBoardDAO;


    /* Scrap 여부 조회 */
    @Override
    public String TipScrapYN(ScrapBoardDTO dto) throws Exception {
        return dao.TipScrapYN(dto);
    }

    /* Scrap 여부 조회 */
    @Override
    public String QaScrapYN(ScrapBoardDTO dto) throws Exception {
        return dao.QaScrapYN(dto);
    }

    /* Scrap 등록 */
    @Override
    public void insertScrap(ScrapBoardDTO dto) throws Exception {
        dao.insertScrap(dto);
    }

    /* Scrap 재등록 */
    @Override
    public void doScrap(ScrapBoardDTO dto) throws Exception {
        dao.doScrap(dto);
    }

    /* Scrap 해제 */
    @Override
    public void undoScrap(ScrapBoardDTO dto) throws Exception {
        dao.undoScrap(dto);
    }

    /* Scrap 등록시 게시글 ScrapCount 증가 */
    @Override
    public void addTipScrapCount(int num) throws Exception {
        dao.addTipScrapCount(num);
    }

    /* Scrap 해제시 게시글 ScrapCount 감소 */
    @Override
    public void delTipScrapCount(int num) throws Exception {
        dao.delTipScrapCount(num);
    }

    /* Scrap 등록시 게시글 ScrapCount 증가 */
    @Override
    public void addQaScrapCount(int num) throws Exception {
        dao.addQaScrapCount(num);
    }

    /* Scrap 해제시 게시글 ScrapCount 감소 */
    @Override
    public void delQaScrapCount(int num) throws Exception {
        dao.delQaScrapCount(num);
    }

    /* Scrap 등록 시 원본 게시글 count 증가를 위한 parentNum 조회 */
    @Override
    public int getScrapParentNum(int num) throws Exception {
        return dao.getScrapParentNum(num);
    }

    /* TIP Scrap 모아보기 */
    @Override
    public List<TipBoardDTO> selectTipScrap(TipBoardDTO tipBoardDTO, String userId, HttpServletRequest request) throws Exception {
        Map<String, Object> tipMap = new HashMap<String, Object>();
        String searchTypeParam = request.getParameter("searchType");
        String sortParam = request.getParameter("sort");
        String orderParam = request.getParameter("order");

        if(searchTypeParam == null) {
            tipBoardDTO.setCategory("all");
            tipMap.put("searchType", "all");
        } else {
            tipBoardDTO.setCategory(searchTypeParam);
            tipMap.put("searchType", searchTypeParam);
        }

        if(sortParam != null && orderParam != null) {
            tipMap.put("sort", sortParam);
            tipMap.put("order", orderParam);
        }
        System.out.println("sort 랑 order 랑 값이 있니? " + sortParam + orderParam);

        tipMap.put("userId", userId);
        tipMap.put("tipBoardDTO", tipBoardDTO);

        System.out.println("@@@@@ 여기는 serviceImpl .. tipMap 의 tipBoardDTO ??? " + tipMap.get("tipBoardDTO"));
        return dao.selectTipScrap(tipMap);
    }

    /* TIP Scrap Cnt */
    @Override
    public int selectTipTotalCount(TipBoardDTO tipBoardDTO, String userId, HttpServletRequest request) throws Exception {
        Map<String, Object> tipMap = new HashMap<String, Object>();
        String searchTypeParam = request.getParameter("searchType");

        if(searchTypeParam == null) {
            tipBoardDTO.setCategory("all");
            tipMap.put("searchType", "all");
        } else {
            tipBoardDTO.setCategory(searchTypeParam);
            tipMap.put("searchType", searchTypeParam);
        }

        tipMap.put("userId", userId);
        tipMap.put("tipBoardDTO", tipBoardDTO);
        System.out.println("@@@@@ tipMap 의 tipBoardDTO ??? " + tipMap.get("tipBoardDTO"));
        return dao.selectTipTotalCount(tipMap);
    }

    /* QA Scrap 모아보기 */
    @Override
    public List<QaListDTO> selectQaScrap(String userId) throws Exception {
        return dao.selectQaScrap(userId);
    }

    /* QA Scrap Cnt */
    @Override
    public int selectQaTotalCount(QaListDTO qaListDTO) throws Exception {
        return dao.selectQaTotalCount(qaListDTO);
    }

}
