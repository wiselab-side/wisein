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
                    <button type="button" onClick="location.href='/qaBoard'" ></button>
                </div>
             </div>
            <img src ="../resources/image/nonPosting.png" class="noPost">
        </section>
    </div>
</c:if>

<c:if test="${qaList != null}">
<div class="content-wrap boardList">
    <section class="content-frame boardList">
        <div class="header-section">
            <div class="title">QA</div>
            <div class="content-top-group">
                <c:if test="${side_gubun ne 'Y'}">
                    <button type="button" onClick="location.href='/qaBoard'" ></button>
                </c:if>
            </div>
        </div>
        <div class="board-list">
            <div class="board-line board-header">
                <div class="board-cell board-no" value="num" onClick="sort(event)">
                </div>
                <div class="board-cell board-category purple2">
                    <p class="category-select">
                        <select id="selectOption" name="category" onchange="selectSearchType()">
                            <option hidden="" selected>카테고리</option>
                            <option value="all">전체</option>
                            <option value="FRONT">FRONT</option>
                            <option value="BACK">BACK</option>
                            <option value="DB">DB</option>
                        </select>
                    </p>
                    <!--
                    <span class="material-icons">expand_more</span>
                    -->
                </div>
                <div class="board-cell board-title" value="subject" onClick="sort(event)">
                    제목(가나다)
                    <!--
                    <span class="material-icons">
                        expand_more
                    </span>
                    -->
                </div>
                <div class="board-cell board-answer gray" value="commCnt" onClick="sort(event)">
                    답변
                </div>
                <div class="board-cell board-like gray" value="likeCount" onClick="sort(event)">
                    좋아요
                </div>
                <div class="board-cell board-scrap gray" value="scrapCount" onClick="sort(event)">
                    스크랩
                </div>
                <div class="board-cell board-writer gray" value="writer" onClick="sort(event)">
                    작성자
                </div>
                <div class="board-cell board-date gray" value="regDate" onClick="sort(event)">
                    날짜
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
                    <div class="board-cell board-answer gray">
                        <c:if test="${qa.adpYn eq 'Y'}">
                            <span class="material-icons" style="color:purple;">check_circle</span>${qa.commCnt}
                        </c:if>
                        <c:if test="${qa.adpYn eq 'N'}">
                            <span class="material-icons purple2">help_outline</span>${qa.commCnt}
                        </c:if>
                    </div>

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