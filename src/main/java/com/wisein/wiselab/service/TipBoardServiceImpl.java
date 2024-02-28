package com.wisein.wiselab.service;

import com.wisein.wiselab.common.FileUtils;
import com.wisein.wiselab.dao.MemberDAO;
import com.wisein.wiselab.dao.TipBoardDAO;
import com.wisein.wiselab.dto.TipBoardDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class TipBoardServiceImpl implements TipBoardService {

    @Autowired
    private TipBoardDAO dao;

    @Autowired
    private FileUtils fileUtils;

    @Autowired
    private MemberDAO memDao;

    /* TipBoard 다건조회 */
    @Override
    public List<TipBoardDTO> selectTipList(TipBoardDTO dto) throws Exception {
        List<TipBoardDTO> tipList = new ArrayList<>();
        tipList = (List<TipBoardDTO>) dao.selectTipList(dto);
        return tipList;
    }

    /* TipBoard 작성글 모아보기 */
    @Override
    public List<TipBoardDTO> selectMemberTipList(TipBoardDTO dto) throws Exception {
        List<TipBoardDTO> tipList = new ArrayList<>();
        tipList = (List<TipBoardDTO>) dao.selectMemberTipList(dto);
        return tipList;
    }

    @Override
    public List<TipBoardDTO> selectScrapTipList(TipBoardDTO dto) throws Exception {
        return dao.selectScrapTipList(dto);
    }

    /* TipBoard 단건조회 */
    @Override
    public TipBoardDTO selectTipOne(TipBoardDTO dto) throws Exception {
        return dao.selectTipOne(dto);
    }

    /* TipBoard 게시글 등록 */
    @Override
    public void insertTipBoard(TipBoardDTO dto) throws Exception {
        dao.insertTipBoard(dto);
    }

    /* TipBoard 게시글 삭제 */
    @Override
    public void deleteTipBoard(int num) throws Exception {
        dao.deleteTipBoard(num);
    }

    /* TipBoard 게시글 수정 */
    @Override
    public void updateTipBoard(TipBoardDTO dto) throws Exception {
        dao.updateTipBoard(dto);
    }

    /* TipBoard 게시글 번호 조회 */
    @Override
    public int selectTipPostNum(TipBoardDTO dto) throws Exception {
        return dao.selectTipPostNum(dto);
    }

    /* 게시글 개수 조회 */
    @Override
    public int selectBoardTotalCount(TipBoardDTO dto) throws Exception {
        return dao.selectBoardTotalCount(dto);
    }

    /* 모아보기 게시글 개수 조회 */
    @Override
    public int selectMemberTipTotalCount(TipBoardDTO dto) throws Exception {
        return dao.selectMemberTipTotalCount(dto);
    }

    /* 작성자 meetLink */
    @Override
    public String selectMeetLink(int num) throws Exception {
        return dao.selectMeetLink(num);
    }

}
