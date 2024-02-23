package com.wisein.wiselab.controller;

import com.wisein.wiselab.common.paging.AbstractPagingCustom;
import com.wisein.wiselab.dto.MemberDTO;
import com.wisein.wiselab.dto.QaListDTO;
import com.wisein.wiselab.dto.TipBoardDTO;
import com.wisein.wiselab.service.ScrapService;
import com.wisein.wiselab.service.TipBoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Controller
public class ScrapController {

    @Autowired
    ScrapService scrapService;

    @Autowired
    TipBoardService tipBoardService;

    private final AbstractPagingCustom PagingTagCustom;

    // QA Scrap 모아보기
    @GetMapping("/gatherQaScrap")
    public String gatherMemScrap(HttpSession session
                                , QaListDTO qaListDTO
                                , Model model) throws Exception {

        MemberDTO member = (MemberDTO) session.getAttribute("member");
        String userId = member.getId();

        List<QaListDTO> qaList = scrapService.selectQaScrap(userId);

        /* 게시글 총 개수 */
//        tipBoardDTO.setTotalRecordCount(tipBoardService.selectBoardTotalCount(tipBoardDTO));
//        String pagination = PagingTagCustom.render(tipBoardDTO);

        String side_gubun = "Y";
        model.addAttribute("side_gubun", side_gubun);
//        model.addAttribute("pagination", pagination);
        model.addAttribute("qaList", qaList);

        return "cmn/QaScrapList";
    }

    // TIP Scrap 모아보기
    @GetMapping("/gatherTipScrap")
    public String gatherMemScrap(HttpSession session, MemberDTO dto
                                , TipBoardDTO tipBoardDTO
                                , Model model) throws Exception {

        MemberDTO member = (MemberDTO) session.getAttribute("member");
        String userId = member.getId();

        List<TipBoardDTO> tipList = scrapService.selectTipScrap(userId);
        List<TipBoardDTO> categoryList = tipBoardService.categoryList();

        /* 게시글 총 개수 */
        tipBoardDTO.setTotalRecordCount(tipBoardService.selectBoardTotalCount(tipBoardDTO));
        String pagination = PagingTagCustom.render(tipBoardDTO);

        String side_gubun = "Y";
        model.addAttribute("side_gubun", side_gubun);
        model.addAttribute("pagination", pagination);
        model.addAttribute("tipList", tipList);
        model.addAttribute("categoryList", categoryList);

        return "cmn/TipScrapList";
    }
}
