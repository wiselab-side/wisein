package com.wisein.wiselab.controller;

import com.wisein.wiselab.common.paging.AbstractPagingCustom;
import com.wisein.wiselab.common.paging.PaginationInfo;
import com.wisein.wiselab.dto.*;
import com.wisein.wiselab.service.*;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Controller
public class tipController {

    @Autowired
    TipBoardService tipBoardService;

    @Autowired
    CommentService commentService;

    @Autowired
    LikeService likeService;

    @Autowired
    ScrapService scrapService;

    @Autowired
    CommonService commonService;


    private final AbstractPagingCustom PagingTagCustom;

    //다건 조회
    @GetMapping(value="/tipList")
    public String tipList (HttpSession session
            , @ModelAttribute("TipBoardDTO") TipBoardDTO dto
            , @RequestParam(name="category", required = false) String category
            , @RequestParam(name="subject", required = false) String subject
            , @RequestParam(name="likeOrder", required = false) String likeOrder
            , @RequestParam(name="scrapOrder", required = false) String scrapOrder
             ,@RequestParam(name="orderValue", required = false) String orderValue
            , Model model) throws Exception {
        /*
        카테고리 및 제목 정렬 처리
        작성자 : 이영주
         날짜  : 2024.02.05 ~ 2024.02.07
         내용  : form을 통해 카테고리/제목 정렬 기준을 받아온다
                 두가지 옵션이 동시에 실행될 수 있도록 값을 저장한다
         */
        /*if(likeOrder == null){
            likeOrder = "DESC";
            dto.setLikeOrder("DESC");
        }*/
        //수정때문에 세션저장해둔것 지움
        session.removeAttribute("TipBoardDTO");



        List<TipBoardDTO> tipList = new ArrayList<>();
        tipList = tipBoardService.selectTipList(dto);

        //likeOrder 값이 존재한다면
        /*if(likeOrder != null){
            dto.setLikeOrder(likeOrder);
            dto.setScrapOrder("");
        } else if(scrapOrder != null){
            //scrapOrder 값이 존재한다면
            dto.setScrapOrder(scrapOrder);
            dto.setLikeOrder("");
        } else if(likeOrder != null && scrapOrder != null){
            dto.setScrapOrder("");
            dto.setLikeOrder("");
        }*/

        System.out.println("~~~~~~~~~~CONTROLLER - likeOrder : " + likeOrder);
        System.out.println("~~~~~~~~~~CONTROLLER - scrapOrder : " + scrapOrder);;
        System.out.println("~~~~~~~~~~CONTROLLER - orderValue : " + orderValue);

        dto.setTotalRecordCount(tipBoardService.selectBoardTotalCount(dto));
        String pagination = PagingTagCustom.render(dto);


        if(tipList.isEmpty()){
            tipList = null;
        }



        model.addAttribute("tipList", tipList);
        // ============== 추가부분 ==============
        model.addAttribute("pagination", pagination);
        model.addAttribute("orderValue", orderValue);
        model.addAttribute("selectedCategory", category);
        model.addAttribute("selectedSubject", subject);
        model.addAttribute("likeOrder", likeOrder);
        model.addAttribute("scrapOrder", scrapOrder);

        return "cmn/tipList";
    }


    //작성글 모아보기
    @GetMapping(value="/gatherMemTip")
    public String gatherMemTip (HttpServletRequest request
            , @ModelAttribute("TipBoardDTO") TipBoardDTO dto
            , @RequestParam(value="sideCheck", required = false, defaultValue = "N") String sideCheck
            , @RequestParam(value="questionsListWriter", required = false) String questionsListWriter
            , @RequestParam(value="commentListWriter", required = false) String commentListWriter
            , @RequestParam(value="tipWriter", required = false) String tipWriter
            , Model model
            ,@RequestParam(name="category", required = false) String category
            , @RequestParam(name="subject", required = false) String subject
            , @RequestParam(name="likeOrder", required = false) String likeOrder
    ) throws Exception {
        HttpSession session= request.getSession();
        MemberDTO member = (MemberDTO) session.getAttribute("member");
        if(category != null && !category.isEmpty()){
            dto.setCategory(category);
        }
        if(subject != null && !subject.isEmpty()){
            dto.setSubject(subject);
        }

        if(questionsListWriter != null && !questionsListWriter.equals("\"\"")){
            questionsListWriter = questionsListWriter.substring(1);
            questionsListWriter = questionsListWriter.substring(0, questionsListWriter.length()-1);
            dto.setWriter(questionsListWriter);
        }
        if(commentListWriter != null && !commentListWriter.equals("\"\"")){
            commentListWriter = commentListWriter.substring(1);
            commentListWriter = commentListWriter.substring(0, commentListWriter.length()-1);
            dto.setWriter(commentListWriter);
        }
        if(tipWriter != null && !tipWriter.equals("\"\"")){
            tipWriter = tipWriter.substring(1);
            tipWriter = tipWriter.substring(0, tipWriter.length()-1);
            dto.setWriter(tipWriter);
        }

        //석삼 모아보기 첫진입
        if(dto.getWriter() == null && sideCheck.equals("Y")) {
            String check = (String)session.getAttribute("tipWriter");
            if(check != null) {session.removeAttribute("tipWriter");}
            session.setAttribute("tipWriter", member.getId());
        }

        //모아보기 첫진입
        if(dto.getWriter() != null) {
            String check = (String)session.getAttribute("tipWriter");
            if(check != null) {session.removeAttribute("tipWriter");}
            session.setAttribute("tipWriter",dto.getWriter());
        }

        dto.setWriter((String)session.getAttribute("tipWriter"));
        session.setAttribute("tipWriter",dto.getWriter());

        if(null != session.getAttribute("questionsListWriter")){
            session.removeAttribute("questionsListWriter");
        }
        if(null != session.getAttribute("commentListWriter")){
            session.removeAttribute("commentListWriter");
        }

        String side_gubun = "Y";
        model.addAttribute("side_gubun", side_gubun);

        List<TipBoardDTO> tipList = new ArrayList<>();

        tipList = tipBoardService.selectMemberTipList(dto);
        dto.setTotalRecordCount(tipBoardService.selectMemberTipTotalCount(dto));
        String pagination = PagingTagCustom.render(dto);

        model.addAttribute("tipList", tipList);
        model.addAttribute("pagination", pagination);
        model.addAttribute("selectedCategory", category);
        model.addAttribute("selectedSubject", subject);
        model.addAttribute("likeOrder", likeOrder);
        return "cmn/tipList";
    }

    //단건 조회
    @GetMapping(value="/tipDetail")
    public String tipDetail (HttpSession session, TipBoardDTO dto, Model model,  @RequestParam("num") int num) throws Exception {
        //meetLink
        String meetLink = tipBoardService.selectMeetLink(num);

        //댓글 갯수
        CommentDTO CommentDTO = new CommentDTO();
        CommentDTO.setBoardIdx(num);
        CommentDTO.setBoardType("tip");
        int commentNum = commentService.selectCommentTotalCount(CommentDTO);

        //좋아요 체크 확인
        MemberDTO member = (MemberDTO) session.getAttribute("member");
        LikeBoardDTO LikeDTO = new LikeBoardDTO();
        LikeDTO.setUserId((member.getId()));
        LikeDTO.setBoardIdx(num);
        LikeDTO.setBoardType("tip");

        String likeDelYn = likeService.TipLikeYN(LikeDTO);
        if(likeDelYn==null){ likeDelYn = "none"; }

        //스크랩 체크 확인
        ScrapBoardDTO ScrapDTO = new ScrapBoardDTO();
        ScrapDTO.setUserId((member.getId()));
        ScrapDTO.setBoardIdx(num);
        ScrapDTO.setBoardType("tip");
        String scrapDelYn = scrapService.TipScrapYN(ScrapDTO);
        if(scrapDelYn==null){ scrapDelYn = "none"; }

        //팁 단건 내용+코멘트 내용 리스트
        TipBoardDTO TipBoardDTO = tipBoardService.selectTipOne(dto);
        List<CommentDTO> commentList = commentService.selectComment(CommentDTO);

        //사이드바 설정
        String side_gubun = "Y";

        model.addAttribute("tipBoardDTO", TipBoardDTO);
        model.addAttribute("content", TipBoardDTO.getContent());
        model.addAttribute("commentNum", commentNum);
        model.addAttribute("commentList", commentList);
        model.addAttribute("likeDelYn", likeDelYn);
        model.addAttribute("scrapDelYn", scrapDelYn);
        model.addAttribute("memberId", member.getId());
        model.addAttribute("meetLink", meetLink);
        model.addAttribute("side_gubun", side_gubun);
        return "cmn/tipDetail";
    }

    //등록
    @GetMapping(value="/tipBoard")
    public String regTip () throws Exception {
        return "board/tipBoard";
    }

    @ResponseBody
    @PostMapping(value="/tipBoard")
    public ResponseEntity regTip ( HttpSession session, @RequestBody Map<String, String> data) throws Exception {
        TipBoardDTO dto = new TipBoardDTO();
        MemberDTO member = (MemberDTO) session.getAttribute("member");

        dto.setWriter(member.getId());
        dto.setCategory(data.get("category"));
        dto.setSubject(data.get("subject"));
        dto.setContent(data.get("content"));

        tipBoardService.insertTipBoard(dto);

        //이미지 난수 게시글로 변경 ex) tip||sj2s10 => tip||22
        String brdNum = data.get("brdNum");
        int postNum = tipBoardService.selectTipPostNum(dto);

        FileDTO file = new FileDTO();

        file.setRefNum("tip||" + postNum);
        file.setTemRefHash("tip||" + brdNum);
        commonService.updateHash(file);

        return new ResponseEntity<>("success", HttpStatus.OK);
    }

    //삭제
    @GetMapping(value="/delTip")
    public String delTip (@RequestParam("num") int num) throws Exception {
        CommentDTO CommentDTO = new CommentDTO();
        CommentDTO.setBoardIdx(num);
        CommentDTO.setBoardType("tip");
        String brdRef = "tip||"+num;

        tipBoardService.deleteTipBoard(num);
        commentService.deleteAllComment(CommentDTO);
        commonService.deleteAllImg(brdRef);

        return "redirect:/tipList";
    }


    //수정
    @GetMapping(value="/updTip")
    public String updTip (TipBoardDTO dto, Model model, HttpSession session) throws Exception {
        dto = tipBoardService.selectTipOne(dto);
        model.addAttribute("TipBoardDTO", dto);
        session.setAttribute("TipBoardDTO", dto);
        return "board/tipBoard";
    }

    @ResponseBody
    @PostMapping(value="/updTip")
    public ResponseEntity updTip(HttpSession session, @RequestBody Map<String, String> data) throws Exception {
        TipBoardDTO dto = new TipBoardDTO();

        dto.setNum(Integer.parseInt(data.get("num")));
        dto.setCategory(data.get("category"));
        dto.setSubject(data.get("subject"));
        dto.setContent(data.get("content"));

        tipBoardService.updateTipBoard(dto);
        session.removeAttribute("TipBoardDTO");
        return new ResponseEntity<>("success", HttpStatus.OK);
    }


    //댓글 리스트
    @ResponseBody
    @GetMapping(value = "/selTipComm")
    public JSONObject tipSelComment (@RequestParam String boardIdx, @RequestParam String boardType) throws Exception {
        CommentDTO dto = new CommentDTO();

        dto.setBoardIdx(Integer.parseInt(boardIdx));
        dto.setBoardType(boardType);

        //댓글 리스트
        List<CommentDTO> commentList = commentService.selectComment(dto);

        JSONObject response = new JSONObject();
        response.put("commentList",commentList);

        return response;
    }

    //댓글 등록
    @ResponseBody
    @PostMapping(value = "/regTipComm")
    public JSONObject tipRegComment (HttpSession session, @RequestBody Map<String, String> data) throws Exception {
        MemberDTO member = (MemberDTO) session.getAttribute("member");
        CommentDTO dto = new CommentDTO();

        dto.setWriter(member.getId());
        dto.setBoardIdx(Integer.parseInt(data.get("boardIdx")));
        dto.setBoardType(data.get("boardType"));
        dto.setContent(data.get("content"));

        //댓글등록
        commentService.insertComment(dto);

        //댓글 리스트
        List<CommentDTO> commentList = commentService.selectComment(dto);

        JSONObject response = new JSONObject();
        response.put("commentList",commentList);

        return response;
    }

    //댓글 삭제
    @ResponseBody
    @PostMapping(value = "/delTipComm")
    public JSONObject tipDelComment (@RequestBody Map<String, String> data) throws Exception {
        CommentDTO dto = new CommentDTO();

        dto.setNum(Integer.parseInt(data.get("num")));
        dto.setBoardIdx(Integer.parseInt(data.get("boardIdx")));
        dto.setBoardType(data.get("boardType"));

        commentService.deleteComment(dto);

        //댓글 리스트
        List<CommentDTO> commentList = commentService.selectComment(dto);

        JSONObject response = new JSONObject();
        response.put("commentList", commentList);

        return response;
    }

    //댓글 수정
    @ResponseBody
    @PostMapping(value = "/udpTipComm")
    public JSONObject tipUpdComment (@RequestBody Map<String, String> data) throws Exception {
        CommentDTO dto = new CommentDTO();

        dto.setNum(Integer.parseInt(data.get("num")));
        dto.setBoardIdx(Integer.parseInt(data.get("boardIdx")));
        dto.setBoardType(data.get("boardType"));
        dto.setContent(data.get("content"));

        commentService.updateComment(dto);

        //댓글 리스트
        List<CommentDTO> commentList = commentService.selectComment(dto);


        JSONObject response = new JSONObject();
        response.put("commentList", commentList);

        return response;
    }

    //like 등록
    @ResponseBody
    @PostMapping(value = "/regLikeTip")
    public JSONObject tipRegLike (HttpSession session, @RequestBody Map<String, String> data) throws Exception {
        MemberDTO member = (MemberDTO) session.getAttribute("member");
        LikeBoardDTO dto = new LikeBoardDTO();

        dto.setUserId(member.getId());
        dto.setBoardIdx(Integer.parseInt(data.get("boardIdx")));
        dto.setBoardType(data.get("boardType"));

        likeService.insertLike(dto);
        likeService.addTipLikeCount(Integer.parseInt(data.get("boardIdx")));

        String likeDelYn = likeService.TipLikeYN(dto);

        JSONObject response = new JSONObject();
        response.put("likeDelYn", likeDelYn);

        return response;
    }

    //like 상태 변경
    @ResponseBody
    @PostMapping(value = "/udpLikeTip")
    public JSONObject tipUdpLike (HttpSession session, @RequestBody Map<String, String> data) throws Exception {
        MemberDTO member = (MemberDTO) session.getAttribute("member");
        LikeBoardDTO dto = new LikeBoardDTO();

        dto.setUserId(member.getId());
        dto.setBoardIdx(Integer.parseInt(data.get("boardIdx")));
        dto.setBoardType(data.get("boardType"));

        String likeDelYn = likeService.TipLikeYN(dto);

        if(likeDelYn.equals("N")){ //해제
            likeService.undoLike(dto);
            likeService.delTipLikeCount(Integer.parseInt(data.get("boardIdx")));
        }else{//재등록
            likeService.doLike(dto);
            likeService.addTipLikeCount(Integer.parseInt(data.get("boardIdx")));
        }

        likeDelYn = likeService.TipLikeYN(dto);

        JSONObject response = new JSONObject();
        response.put("likeDelYn", likeDelYn);

        return response;
    }

    //scrap 등록
    @ResponseBody
    @PostMapping(value = "/regScrapTip")
    public JSONObject tipRegScrap (HttpSession session, @RequestBody Map<String, String> data) throws Exception {
        MemberDTO member = (MemberDTO) session.getAttribute("member");
        ScrapBoardDTO dto = new ScrapBoardDTO();

        dto.setUserId(member.getId());
        dto.setBoardIdx(Integer.parseInt(data.get("boardIdx")));
        dto.setBoardType(data.get("boardType"));

        scrapService.insertScrap(dto);
        scrapService.addTipScrapCount(Integer.parseInt(data.get("boardIdx")));

        String scrapDelYn = scrapService.TipScrapYN(dto);
        JSONObject response = new JSONObject();
        response.put("scrapDelYn", scrapDelYn);

        return response;
    }

    //scrap 상태 변경
    @ResponseBody
    @PostMapping(value = "/udpScrapTip")
    public JSONObject tipUdpScrap (HttpSession session, @RequestBody Map<String, String> data) throws Exception {
        MemberDTO member = (MemberDTO) session.getAttribute("member");
        ScrapBoardDTO dto = new ScrapBoardDTO();

        dto.setUserId(member.getId());
        dto.setBoardIdx(Integer.parseInt(data.get("boardIdx")));
        dto.setBoardType(data.get("boardType"));

        String scrapDelYn = scrapService.TipScrapYN(dto);

        if(scrapDelYn.equals("N")){ //해제
            scrapService.undoScrap(dto);
            scrapService.delTipScrapCount(Integer.parseInt(data.get("boardIdx")));
        }else{//재등록
            scrapService.doScrap(dto);
            scrapService.addTipScrapCount(Integer.parseInt(data.get("boardIdx")));
        }

        scrapDelYn = scrapService.TipScrapYN(dto);
        JSONObject response = new JSONObject();
        response.put("scrapDelYn", scrapDelYn);

        return response;
    }

}
