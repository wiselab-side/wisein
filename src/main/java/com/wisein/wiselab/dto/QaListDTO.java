package com.wisein.wiselab.dto;

import lombok.Data;

import java.util.Date;
import com.wisein.wiselab.common.paging.PaginationInfo;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class QaListDTO extends PaginationInfo {
    private int num;
    private int commCnt;
    private String category;
    private String writer;
    private String subject;
    private String content;
    private String regDate;
    private String updDate;
    private String adpYn;
    private String delYn;
    private int parentNum;
    private int likeCount;
    private int scrapCount;
    private int adpNum;
}

