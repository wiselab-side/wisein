<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!--
<c:if test="${empty questionsListWriter && empty commentListWriter}">
<div class="content-wrap boardList">
</c:if>
<c:if test="${not empty questionsListWriter || not empty commentListWriter}">
<div class="content-wrap boardList" style="max-width: 1300px;">
</c:if>
-->

<c:if test="${qaList == null}">
    <div class="content-wrap boardList">
        <section class="content-frame boardList">
             <div class="header-section">
                 <div class="title">QA</div>
                 <div class="content-top-group">
                    <button type="button" onclick="go_regQaPage('${fn:replace(param.searchType, "'", "\\'") }')" ></button>
                </div>
             </div>
            <img src ="../resources/image/nonPosting.png" class="noPost">
        </section>
    </div>
</c:if>

<c:if test="${qaList != null}">
<div class="content-wrap boardList">
    <c:choose>
        <c:when test="${param.reply == 'Y'}"></c:when>
        <c:when test="${param.who == 'null' && param.reply == 'N'}">
            <div class="board-type">스크랩 모아보기</div>
        </c:when>
        <c:when test="${param.who != null}">
            <div class="board-type">작성 글 모아보기</div>
        </c:when>
        <c:otherwise></c:otherwise>
    </c:choose>
    <section class="content-frame boardList">
        <div class="header-section">
            <div class="title">QA</div>
            <div class="content-top-group">
                <c:if test="${side_gubun ne 'Y'}">
                    <button type="button" onclick="go_regQaPage('${fn:replace(param.searchType, "'", "\\'") }')" ></button>
                </c:if>
            </div>
        </div>
        <div class="board-list">
            <div class="board-line board-header">
                <div class="board-cell board-no" value="num" onclick="sort(event)">
                </div>
                <div class="board-cell board-category purple2">
                    <p class="category-select">
                        <select id="selectOption" name="category" onchange="selectSearchType()">
                            <option hidden="">카테고리</option>
                            <option value="all"   <c:if test="${param.searchType eq 'all'}">selected</c:if>>전체</option>
                            <option value="FRONT" <c:if test="${param.searchType eq 'FRONT'}">selected</c:if>>FRONT</option>
                            <option value="BACK"  <c:if test="${param.searchType eq 'BACK'}">selected</c:if>>BACK</option>
                            <option value="DB"    <c:if test="${param.searchType eq 'DB'}">selected</c:if>>DB</option>
                        </select>
                    </p>
                    <!--
                    <span class="material-icons">expand_more</span>
                    -->
                </div>
                <div class="board-cell board-title" value="subject" onclick="sort(event)">
                    제목(가나다)
                    <c:if test="${param.sort eq 'subject' && param.order eq 'desc'}">
                        <span class="material-icons" id="order-icons-desc">
                                expand_more
                        </span>
                    </c:if>
                    <c:if test="${param.sort eq 'subject' && param.order eq 'asc'}">
                        <span class="material-icons" id="order-icons">
                                expand_more
                        </span>
                    </c:if>
                    <c:if test="${param.sort ne 'subject'}">
                        <span id="no-order">
                                -
                        </span>
                    </c:if>
                </div>

                <c:if test="${param.reply eq 'N'}"></c:if>
                <c:if test="${param.reply ne 'N'}">
                    <div class="board-cell board-answer gray" value="commCnt" onclick="sort(event)">
                        답변
                        <c:if test="${param.sort eq 'commCnt' && param.order eq 'desc'}">
                            <span class="material-icons" id="order-icons-desc">
                                    expand_more
                            </span>
                        </c:if>
                        <c:if test="${param.sort eq 'commCnt' && param.order eq 'asc'}">
                            <span class="material-icons" id="order-icons">
                                    expand_more
                            </span>
                        </c:if>
                        <c:if test="${param.sort ne 'commCnt'}">
                            <span id="no-order">
                                    -
                            </span>
                        </c:if>
                    </div>
                </c:if>

                <div class="board-cell board-like gray" value="likeCount" onclick="sort(event)">
                    좋아요
                    <c:if test="${param.sort eq 'likeCount' && param.order eq 'desc'}">
                        <span class="material-icons" id="order-icons-desc">
                                expand_more
                        </span>
                    </c:if>
                    <c:if test="${param.sort eq 'likeCount' && param.order eq 'asc'}">
                        <span class="material-icons" id="order-icons">
                                expand_more
                        </span>
                    </c:if>
                    <c:if test="${param.sort ne 'likeCount'}">
                        <span id="no-order">
                                -
                        </span>
                    </c:if>
                </div>
                <div class="board-cell board-scrap gray" value="scrapCount" onclick="sort(event)">
                    스크랩
                    <c:if test="${param.sort eq 'scrapCount' && param.order eq 'desc'}">
                        <span class="material-icons" id="order-icons-desc">
                                expand_more
                        </span>
                    </c:if>
                    <c:if test="${param.sort eq 'scrapCount' && param.order eq 'asc'}">
                        <span class="material-icons" id="order-icons">
                                expand_more
                        </span>
                    </c:if>
                    <c:if test="${param.sort ne 'scrapCount'}">
                    <span id="no-order">
                            -
                    </span>
                </c:if>
                </div>
                <div class="board-cell board-writer gray" value="writer" onclick="sort(event)">
                    작성자
                    <c:if test="${param.sort eq 'writer' && param.order eq 'desc'}">
                        <span class="material-icons" id="order-icons-desc">
                                expand_more
                        </span>
                    </c:if>
                    <c:if test="${param.sort eq 'writer' && param.order eq 'asc'}">
                        <span class="material-icons" id="order-icons">
                                expand_more
                        </span>
                    </c:if>
                    <c:if test="${param.sort ne 'writer'}">
                        <span id="no-order">
                                -
                        </span>
                    </c:if>
                </div>
                <div class="board-cell board-date gray" value="regDate" onclick="sort(event)">
                    날짜
                    <c:if test="${param.sort eq 'regDate' && param.order eq 'desc'}">
                        <span class="material-icons" id="order-icons-desc">
                                expand_more
                        </span>
                    </c:if>
                    <c:if test="${param.sort eq 'regDate' && param.order eq 'asc'}">
                        <span class="material-icons" id="order-icons">
                                expand_more
                        </span>
                    </c:if>
                    <c:if test="${param.sort ne 'regDate'}">
                        <span id="no-order">
                                -
                        </span>
                    </c:if>
                </div>
                <div class="board-cell board-count gray" value="count" onclick="sort(event)">
                    조회
                    <c:if test="${param.sort eq 'count' && param.order eq 'desc'}">
                        <span class="material-icons" id="order-icons-desc">
                                expand_more
                        </span>
                    </c:if>
                    <c:if test="${param.sort eq 'count' && param.order eq 'asc'}">
                        <span class="material-icons" id="order-icons" >
                                expand_more
                        </span>
                    </c:if>
                    <c:if test="${param.sort ne 'count'}">
                        <span id="no-order">
                                -
                        </span>
                    </c:if>
                </div>
            </div>

            <c:forEach var="qa" items="${qaList}">
                <div class="board-line ">
                    <div class="board-cell board-no">
                        <c:out value="${qa.num}" />
                    </div>
                    <div class="board-cell board-category purple">
                        <c:out value="${qa.category}" />
                    </div>
                    <div class="board-cell board-title">
                        <a href="/qaDetail?num=${qa.num}&parentNum=${qa.parentNum}"><c:out value="${qa.subject}" /></a>
                    </div>
                    <c:if test="${param.reply eq 'N'}"></c:if>
                    <c:if test="${param.reply ne 'N'}">
                        <div class="board-cell board-answer gray">
                            <c:if test="${qa.adpYn eq 'Y'}">
                                <a href="/qaDetail?num=${qa.num}&parentNum=${qa.parentNum}">
                                    <span class="material-icons" style="color:purple;">check_circle</span>${qa.commCnt}
                                </a>
                            </c:if>
                            <c:if test="${qa.adpYn eq 'N'}">
                                <a href="/qaDetail?num=${qa.num}&parentNum=${qa.parentNum}">
                                    <span class="material-icons purple2">help_outline</span>${qa.commCnt}
                                </a>
                            </c:if>
                        </div>
                    </c:if>

                    <c:if test="${qa.likeCount == 0}">
                        <div class="board-cell board-like gray">
                            <span class="material-icons">thumb_up</span>${qa.likeCount}
                        </div>
                    </c:if>
                    <c:if test="${qa.likeCount != 0}">
                        <div class="board-cell board-like purple2">
                            <span class="material-icons">thumb_up</span>${qa.likeCount}
                        </div>
                    </c:if>

                    <c:if test="${qa.scrapCount == 0}">
                        <div class="board-cell board-scrap gray">
                            <span class="material-icons" style="max-width:24px;">bookmarks</span>${qa.scrapCount}
                        </div>
                    </c:if>
                    <c:if test="${qa.scrapCount != 0}">
                        <div class="board-cell board-scrap purple2">
                            <span class="material-icons" style="max-width:24px;">bookmarks</span>${qa.scrapCount}
                        </div>
                    </c:if>

                    <div class="board-cell board-writer gray">
                        <p class="writer"><c:out value="${qa.writer}" /><br>
                        </p>
                        <ul class="person-function  list">
                            <li><a href="https://m196.mailplug.com/member/login?host_domain=wiselab.co.kr" target='_blank'>메일 전송</a></li>
                            <li><a href="#" onclick="questionsList_btn('${fn:replace(qa.writer, "'", "\\'") }');" id="questionsList_btn">질문 모아 보기</a></li>
                            <li><a href="#" onclick="commentList_btn('${fn:replace(qa.writer, "'", "\\'") }');" id="commentList_btn">답변 모아 보기</a></li>
                        </ul>
                    </div>
                    <div class="board-cell board-date gray">
                        <c:out value="${qa.regDate}" />
                    </div>
                    <div class="board-cell board-count gray">
                        <c:out value="${qa.count}" />
                    </div>
                </div>
            </c:forEach>

        </div>
    </section>
    <ul class="pageno-group">
	    <div class="pagination">
	    	${pagination}
    	</div>
    </ul>
</div>
</c:if>

<script src="resources/js/qaBoard.js"></script>