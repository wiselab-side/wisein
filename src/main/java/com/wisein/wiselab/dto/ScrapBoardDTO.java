package com.wisein.wiselab.dto;

import com.wisein.wiselab.common.paging.PaginationInfo;
import lombok.Data;

@Data
public class ScrapBoardDTO {
    private int idx;
    private int boardIdx;
    private String boardType;
    private String userId;
    private String delYn;
}