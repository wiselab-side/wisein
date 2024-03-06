package com.wisein.wiselab.dao;
import com.wisein.wiselab.dto.TipBoardDTO;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class TipBoardDAOImpl implements TipBoardDAO {

    @Autowired
    private SqlSession sql;

    private static final String NS = "com.wisein.wiselab.mapper.tipBoardMapper";

    /* TipBoard 다건조회 */
    @Override
    public List<TipBoardDTO> selectTipList(TipBoardDTO dto) {
        return sql.selectList(NS + ".selectTipList", dto);
    }

    /* TipBoard 작성글 모아보기 */
    @Override
    public List<TipBoardDTO> selectMemberTipList(TipBoardDTO dto) {
        return sql.selectList(NS + ".selectMemberTipList", dto);
    }

    /* TipBoard 단건조회 */
    @Override
    public TipBoardDTO selectTipOne(TipBoardDTO dto) throws Exception {
        return sql.selectOne(NS + ".selectTipOne", dto);
    }

    /* TipBoard 게시글 등록 */
    @Override
    public void insertTipBoard(TipBoardDTO dto) throws Exception {
        sql.insert(NS + ".insertTipBoard", dto);
    }

    /* TipBoard 게시글 삭제 */
    @Override
    public void deleteTipBoard(int num) throws Exception {
        sql.update(NS+ ".deleteTipBoard", num);
    }

    /* TipBoard 게시글 수정 */
    @Override
    public void updateTipBoard(TipBoardDTO dto) throws Exception {
        sql.update(NS+ ".updateTipBoard", dto);
    }

    /* TipBoard 게시글 번호 조회 */
    @Override
    public int selectTipPostNum(TipBoardDTO dto) throws Exception {
        return sql.selectOne(NS+ ".selectTipPostNum", dto);
    }

    /* TipBoard 다음 게시글 번호 조회*/
    @Override
    public int selectNextTipNum() throws Exception{
        return sql.selectOne(NS + ".selectNextTipNum");
    }

    /* 전체 게시글 개수 조회 */
    @Override
    public int selectBoardTotalCount(TipBoardDTO dto) throws Exception {
        return sql.selectOne(NS + ".selectBoardTotalCount", dto);
    }

    /* 모아보기 게시글 개수 조회 */
    @Override
    public int selectMemberTipTotalCount(TipBoardDTO dto) throws Exception {
        return sql.selectOne(NS + ".selectMemberTipTotalCount", dto);
    }

    /* 작성자 meetLink */
    @Override
    public String selectMeetLink(int num) throws Exception {
        return sql.selectOne(NS + ".selectMeetLink", num);
    }

    /* 카테고리 조회 */
    @Override
    public List<TipBoardDTO> categoryList() throws Exception {
        return sql.selectList(NS + ".categoryList");
    }

    /* 조회수 증가 */
    @Override
    public void updateCount(Map<String, Object> countMap) throws Exception {
        sql.update(NS+ ".updateCount", countMap);
    }
}