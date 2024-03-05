package com.wisein.wiselab.dto;

import com.wisein.wiselab.common.paging.PaginationInfo;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@EqualsAndHashCode(callSuper=false)
@Data
public class TipBoardDTO extends PaginationInfo  {
    private int num;
    private int commCnt;
    private String category;
    private String writer;
    private String subject;
    private String content;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date regDate;
    private Date updDate;
    private String delYn;
    private int count;
    private int parentNum;
    private int likeCount;
    private int scrapCount;
    private String sort;
    private String order;
}
