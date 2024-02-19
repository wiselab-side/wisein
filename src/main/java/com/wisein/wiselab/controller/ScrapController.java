package com.wisein.wiselab.controller;

import com.wisein.wiselab.service.ScrapService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
public class ScrapController {

    @Autowired
    ScrapService scrapService;

    // Scrap 모아보기
    @GetMapping("/gatherMemScrap")
    public String gatherMemScrap() {


        return "cmn/scrapList";
    }
}
