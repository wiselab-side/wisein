package com.wisein.wiselab.dto;

import com.wisein.wiselab.common.paging.PaginationInfo;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class ScrapBoardDTO extends PaginationInfo {
    private int idx;
    private int boardIdx;
    private String boardType;
    private String userId;
    private String delYn;

}