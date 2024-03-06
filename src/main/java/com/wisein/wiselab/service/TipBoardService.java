package com.wisein.wiselab.service;

import com.wisein.wiselab.dto.TipBoardDTO;
import java.util.List;

public interface TipBoardService {

    /* TipBoard 다건조회 */
    public List<TipBoardDTO> selectTipList(TipBoardDTO dto)  throws Exception;

    /* TipBoard 작성글 모아보기 */
    public List<TipBoardDTO> selectMemberTipList(TipBoardDTO dto)  throws Exception;

    /* TipBoard 단건조회 */
    public TipBoardDTO selectTipOne(TipBoardDTO dto) throws Exception;

    /* TipBoard 게시글 등록 */
    public void insertTipBoard(TipBoardDTO dto) throws Exception;

    /* TipBoard 게시글 삭제 */
    public void deleteTipBoard(int num) throws Exception;

    /* TipBoard 게시글 수정 */
    public void updateTipBoard(TipBoardDTO dto) throws Exception;

    /* TipBoard 게시글 번호 조회 */
    public int selectTipPostNum(TipBoardDTO dto) throws Exception;

    /* 게시글 총 개수 */
    public int selectBoardTotalCount(TipBoardDTO dto) throws Exception;

    /* 모아보기 게시글 개수 조회 */
    public int selectMemberTipTotalCount(TipBoardDTO dto) throws Exception;

    /* 작성자 meetLink */
    public String selectMeetLink(int num) throws Exception;

    /* 카테고리 조회 */
    public List<TipBoardDTO> categoryList() throws Exception;

    /* 조회수 증가 */
    public void updateCount(int num, String userId) throws Exception;

}
