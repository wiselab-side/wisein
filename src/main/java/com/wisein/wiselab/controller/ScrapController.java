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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
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
                                , HttpServletRequest request
                                , @RequestParam(defaultValue = "N") String reply
                                , @ModelAttribute("QaListDTO") QaListDTO qaListDTO
                                , Model model) throws Exception {
        MemberDTO member = (MemberDTO) session.getAttribute("member");
        String userId = member.getId();

        /* 스크랩 총 개수 */
        qaListDTO.setTotalRecordCount(scrapService.selectQaTotalCount(qaListDTO, userId, request));
        String pagination = PagingTagCustom.render(qaListDTO);

        List<QaListDTO> qaList = scrapService.selectQaScrap(qaListDTO, userId, request);

        String side_gubun = "Y";
        model.addAttribute("side_gubun", side_gubun);
        model.addAttribute("qaList", qaList);
        model.addAttribute("pagination", pagination);

        return "cmn/qaScrapList";
    }

    // TIP Scrap 모아보기
    @GetMapping("/gatherTipScrap")
    public String gatherMemScrap(HttpSession session
                                , HttpServletRequest request
                                , @RequestParam(defaultValue = "N") String reply
                                , @ModelAttribute("TipBoardDTO") TipBoardDTO tipBoardDTO
                                , Model model) throws Exception {
        MemberDTO member = (MemberDTO) session.getAttribute("member");
        String userId = member.getId();

        /* 스크랩 총 개수 */
        tipBoardDTO.setTotalRecordCount(scrapService.selectTipTotalCount(tipBoardDTO, userId, request));
        String pagination = PagingTagCustom.render(tipBoardDTO);

        List<TipBoardDTO> tipList = scrapService.selectTipScrap(tipBoardDTO, userId, request);
        List<TipBoardDTO> categoryList = tipBoardService.categoryList();

        String side_gubun = "Y";
        model.addAttribute("side_gubun", side_gubun);
        model.addAttribute("tipList", tipList);
        model.addAttribute("categoryList", categoryList);
        model.addAttribute("pagination", pagination);

        return "cmn/tipScrapList";
    }
}
