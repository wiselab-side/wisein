package com.wisein.wiselab.service;

import com.wisein.wiselab.common.FileUtils;
import com.wisein.wiselab.dao.MemberDAO;
import com.wisein.wiselab.dao.QaListDAO;
import com.wisein.wiselab.dto.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.util.ArrayList;
import javax.servlet.http.HttpSession;
import java.util.Iterator;
import java.util.List;

@Service
public class QaListServiceImpl implements QaListService {

    @Autowired
    private QaListDAO dao;

    @Autowired
    private FileUtils fileUtils;

    @Autowired
    private MemberDAO memDao;

    /*
     * 작성자 : 이형근
     * QA 목록 조회
     * param : QaListDTO
     * return : qaList
     * 날짜 : 2022-04-03
     * 수정자 : 박혜림
     * 수정일 : 2022-06-04
     * */
    @Override
    public List<QaListDTO> selectQaList(QaListDTO qaListDTO) throws Exception {
        List<QaListDTO> qaList = new ArrayList<>();
        int boardTotalCount = dao.selectBoardTotalCount(qaListDTO);

        if(boardTotalCount > 0) {
            qaList = (List<QaListDTO>) dao.selectQaList(qaListDTO);
        }

        return qaList;
    }

    /*
     * 작성자 : 이형근
     * QA 목록 조회
     * param : QaListDTO
     * return : qaList
     * 날짜 : 2022-04-03
     * 수정자 : 박혜림
     * 수정일 : 2022-06-04
     * */
    @Override
    public List<QaListDTO> selectQuestionsList(QaListDTO qaListDTO) throws Exception {
        List<QaListDTO> qaList = new ArrayList<>();
        qaList = (List<QaListDTO>) dao.selectQuestionsList(qaListDTO);
        return qaList;
    }

    /*
     * 작성자 : 이형근
     * QA 목록 조회
     * param : QaListDTO
     * return : qaList
     * 날짜 : 2022-04-03
     * 수정자 : 박혜림
     * 수정일 : 2022-06-04
     * */
    @Override
    public List<QaListDTO> selectCommentList(QaListDTO qaListDTO) throws Exception {
        List<QaListDTO> qaList = new ArrayList<>();
        qaList = (List<QaListDTO>) dao.selectCommentList(qaListDTO);
        return qaList;
    }

    /*
     * 작성자 : 이형근
     * QaBoard Insert
     * param : QaListDTO
     * return :
     * 날짜 : 2022-05-14
     * */
    @Override
    public void insertQaBoard(QaListDTO qaListDTO) throws Exception {
        dao.insertQaBoard(qaListDTO);
    }

    /*
     * 작성자 : 이형근
     * num에 해당하는 qa조회
     * param : QaListDTO
     * return : QaListDTO
     * 날짜 : 2022-05-18
     * */
    @Override
    public QaListDTO selectQaOne(QaListDTO qaListDTO) throws Exception {
        dao.updateCount(qaListDTO.getNum());
        return dao.selectQaOne(qaListDTO);
    }

    /*
     * 작성자 : 이형근
     * num에 해당하는 qa삭제
     * param : Integer
     * return :
     * 날짜 : 2022-05-29
     * */
    @Override
    public void deleteQaBoard(int num) throws Exception {
        dao.deleteQaBoard(num);
    }

    /* 게시글 삭제시 댓글도 삭제 */
    @Override
    public void deleteCommentQaBoard(int num) throws Exception {
        dao.deleteCommentQaBoard(num);
    }

    /*
     * 작성자 : 박혜림
     * 게시글 총 개수
     * param : QaListDTO
     * return : int
     * 날짜 : 2022-06-04
     * */
    @Override
    public int selectBoardTotalCount(QaListDTO qaListDTO) throws Exception {
        return dao.selectBoardTotalCount(qaListDTO);
    }

    /*
     * 작성자 : 이형근
     * num에 해당하는 게시글 subject, content 수정
     * param : QaListDTO
     * return :
     * 날짜 : 2022-05-29
     * */
    @Override
    public void updateQaBoard(QaListDTO qaListDTO) throws Exception {
        dao.updateQaBoard(qaListDTO);
    }

    /*
     * 작성자 : 이형근
     * qaboard 이미지 url
     * param :
     * return :
     * 날짜 : 2022-06-09
     * */
    @Override
    public String imgUrlReg(MultipartHttpServletRequest multipartHttpServletRequest, HttpSession session, Model model) throws Exception {
        if(ObjectUtils.isEmpty(multipartHttpServletRequest) == false) {

            Iterator<String> iterator = multipartHttpServletRequest.getFileNames();
            String name;
            while(iterator.hasNext()) {
                name = iterator.next();
                List<MultipartFile> list = multipartHttpServletRequest.getFiles(name);
                for(MultipartFile multipartFile : list) {

                    String contType =  multipartFile.getContentType();
                    String[] contArr = contType.split("/");
                    String extension = contArr[1];
                }

            }
        }

        QaListDTO qaListDTO = (QaListDTO) session.getAttribute("qaListDTO");
        QaListDTO chkNum = new QaListDTO();
        if(qaListDTO != null){
            System.out.println("qaListDTO : " + qaListDTO.toString());
            chkNum = dao.selectQaNum(qaListDTO);
        }

        System.out.println("chkNum : " + chkNum.toString());

        String brdRef = "";
        if(chkNum.getNum() == 0){
            brdRef =  "qa||"+dao.selectQaNum2();
        }
        else {
            brdRef =  "qa||"+chkNum.getNum();
        }

        //List<FileDTO> list = fileUtils.parseFileInfo(brdRef, "image", multipartHttpServletRequest);
//        if(CollectionUtils.isEmpty(list) == false) {
//            memDao.insertMemFileList(list);
//        }
//        String imgUrl = list.get(0).getFilePath();

        session.removeAttribute("qaListDTO");

        //return imgUrl;
        return "";
    }

    /*
     * 작성자 : 이형근
     * qaboard num select
     * param : qaListDTO
     * return :
     * 날짜 : 2022-06-09
     * */
    @Override
    public QaListDTO selectQaNum(QaListDTO qaListDTO) throws Exception {
        return dao.selectQaNum(qaListDTO);
    }

    /*
     * 작성자 : 이형근
     * QaBoard Comment Insert
     * param : QaListDTO
     * return :
     * 날짜 : 2022-06-12
     * */
    @Override
    public void insertCommentQaBoard(QaListDTO qaListDTO) throws Exception {
        dao.insertCommentQaBoard(qaListDTO);
    }

    /*
     * 작성자 : 이형근
     * QA 댓글 목록 조회
     * param : int num
     * return : qaList
     * 날짜 : 2022-06-12
     * */
    @Override
    public List<QaListDTO> selectCommentQaList(int num) throws Exception {
        return dao.selectCommentQaList(num);
    }

    /*
     * 작성자 : 이형근
     * 게시글 좋아요 클릭 시
     * param : boardNum, num
     * return : int
     * 날짜 : 2022-07-24
     * */
    @Override
    public LikeBoardDTO checkLikeQaBoard(LikeBoardDTO qa) throws Exception {
        return dao.checkLikeQaBoard(qa);
    }

    /*
     * 작성자 : 이형근
     * 게시글 좋아요 클릭 시
     * param : boardNum, num
     * return : int
     * 날짜 : 2022-07-24
     * */
    @Override
    public ScrapBoardDTO checkScrapQaBoard(ScrapBoardDTO qa) throws Exception {
        return dao.checkScrapQaBoard(qa);
    }

    /*
     * 작성자 : 이형근
     * 게시글 좋아요 클릭 시
     * param : boardNum, num
     * return : int
     * 날짜 : 2022-07-24
     * */
    @Override
    public int insertLikeQaBoard(LikeBoardDTO qa) throws Exception {
        return dao.insertLikeQaBoard(qa);
    }

    /*
     * 작성자 : 이형근
     * 게시글 좋아요 클릭 시
     * param : boardNum, num
     * return : int
     * 날짜 : 2022-07-24
     * */
    @Override
    public int insertScrapQaBoard(ScrapBoardDTO qa) throws Exception {
        return dao.insertScrapQaBoard(qa);
    }

    /*
     * 작성자 : 이형근
     * 게시글 좋아요 클릭 시
     * param : boardNum, num
     * return : int
     * 날짜 : 2022-07-24
     * */
    @Override
    public int updateLikeQaBoard(LikeBoardDTO qa) throws Exception {
        return dao.updateLikeQaBoard(qa);
    }

    /*
     * 작성자 : 이형근
     * 게시글 좋아요 클릭 시
     * param : boardNum, num
     * return : int
     * 날짜 : 2022-07-24
     * */
    @Override
    public int updateScrapQaBoard(ScrapBoardDTO qa) throws Exception {
        return dao.updateScrapQaBoard(qa);
    }

    /*
     * 작성자 : 이형근
     * 게시글 좋아요 클릭 시 신규/기본 여부 체크
     * param : boardNum, num
     * return :
     * 날짜 : 2022-07-24
     * */
    @Override
    public List<LikeBoardDTO> selectLikeQaBoardList(MemberDTO member) throws Exception{
        return dao.selectLikeQaBoardList(member);
    }

    /*
     * 작성자 : 이형근
     * 게시글 좋아요 클릭 시 신규/기본 여부 체크
     * param : boardNum, num
     * return :
     * 날짜 : 2022-07-24
     * */
    @Override
    public List<ScrapBoardDTO> selectScrapQaBoardList(MemberDTO member) throws Exception{
        return dao.selectScrapQaBoardList(member);
    }

    /*
     * 작성자 : 이형근
     * 게시글 좋아요 클릭 시 신규/기본 여부 체크
     * param : boardNum, num
     * return :
     * 날짜 : 2022-07-24
     * */
    @Override
    public LikeBoardDTO selectOneLikeQaBoard(LikeBoardDTO member) throws Exception{
        return dao.selectOneLikeQaBoard(member);
    }

    /*
     * 작성자 : 이형근
     * 게시글 좋아요 클릭 시 신규/기본 여부 체크
     * param : boardNum, num
     * return :
     * 날짜 : 2022-07-24
     * */
    @Override
    public ScrapBoardDTO selectOneScrapQaBoard(ScrapBoardDTO member) throws Exception{
        return dao.selectOneScrapQaBoard(member);
    }

    /* 게시글 채택시 원본 채택여부 업데이트 */
    @Override
    public void adoptQaBoard(QaListDTO dto) throws Exception {
        dao.adoptQaBoard(dto);
    }

    /* 게시글 채택시 채택된 댓글 채택여부 업데이트 */
    @Override
    public void adoptCommentQaBoard(QaListDTO dto) throws Exception {
        dao.adoptCommentQaBoard(dto);
    }

    @Override
    public void likeAddCount(LikeBoardDTO likeDTO) throws Exception {
        dao.likeAddCount(likeDTO);
    }

    @Override
    public void likeMinusCount(LikeBoardDTO likeDTO) throws Exception {
        dao.likeMinusCount(likeDTO);
    }

    @Override
    public int selectAdpCount(QaListDTO qaListDTO) throws Exception{
        return dao.selectAdpCount(qaListDTO);
    }

    /* like 등록 시 원본 게시글 count 증가 */
    @Override
    public void addQaLikeCount(int parentNum) throws Exception {
        dao.addQaLikeCount(parentNum);
    }

    /* like 해제 시 원본 게시글 count 감소 */
    @Override
    public void delQaLikeCount(int parentNum) throws Exception {
        dao.delQaLikeCount(parentNum);
    }

    /* scrap 등록 시 원본 게시글 count 증가 */
    @Override
    public void addQaScrapCount(int parentNum) throws Exception {
        dao.addQaScrapCount(parentNum);
    }

    /* scrap 등록 시 원본 게시글 count 감소 */
    @Override
    public void delQaScrapCount(int parentNum) throws Exception {
        dao.delQaScrapCount(parentNum);
    }

    /* 게시글 수정 후 detail 조회를 위한 원본게시글 num조회 */
    @Override
    public int getParentNum(QaListDTO qaListDTO) throws Exception {
        return dao.getParentNum(qaListDTO);
    }

    /* 작성자 meetLink */
    @Override
    public String selectMeetLink(int num) throws Exception {
        return dao.selectMeetLink(num);
    }

    /* 모아보기 페이징조회 */
    @Override
    public int selectMemberQaTotalCount(QaListDTO dto) throws Exception {
        return dao.selectMemberQaTotalCount(dto);
    }

    /* 댓글 모아보기 페이징조회 */
    @Override
    public int selectMemberQaCommentTotalCount(QaListDTO dto) throws Exception {
        return dao.selectMemberQaCommentTotalCount(dto);
    }

    /* 댓글 삭제 시 좋아요 삭제*/
    @Override
    public void deleteLikeQaBoard(int num) throws Exception {
        dao.deleteLikeQaBoard(num);
    }

    /* 댓글 삭제 시 스크랩 삭제*/
    @Override
    public void deleteScrapQaBoard(int num) throws Exception {
        dao.deleteScrapQaBoard(num);
    }
}
