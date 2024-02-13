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
                <div class="board-cell board-no">
                </div>
                <!--
                <div class="board-cell board-category purple2">
                    <ul class="person-function">
                        <li><a href="#">FRONT</a></li>
                        <li><a href="#">BACK</a></li>
                        <li><a href="#">DB</a></li>
                    </ul>
                    <span class="material-icons">
                            expand_more
                        </span>
                </div>
                 -->
                <!-- 카테고리 -->
                <div class="board-cell board-category purple2">
                     <!-- controller 에 전해줄 form 생성,
                          카테고리 옵션과 제목 정렬 옵션이 동시에 이뤄질 수 있기 때문에 하나의 form 태그로 진행-->
                     <form action="/qalist" method="get">
                        <!--카테고리 조회 -->
                         <select id="category" name="category" class="category-select" onchange="this.form.submit()">
                             <option value="" ${empty selectedCategory ? 'selected' : ''}>카테고리</option>
                             <option value="FRONT" ${selectedCategory eq 'FRONT' ? 'selected' : ''}>FRONT</option>
                             <option value="BACK" ${selectedCategory eq 'BACK' ? 'selected' : ''}>BACK</option>
                             <option value="DB" ${selectedCategory eq 'DB' ? 'selected' : ''}>DB</option>
                         </select>
                </div>
                <div class="board-cell board-title">
                    <!--제목 정렬 -->
                         <select id="subject" name="subject" class="subject-select" onchange="this.form.submit()">
                             <option value="" ${empty selectedSubject ? 'selected' : ''}>제목(가나다)</option>
                             <option value="ASC" ${selectedSubject eq 'ASC' ? 'selected' : ''}>오름차순</option>
                             <option value="DESC" ${selectedSubject eq 'DESC' ? 'selected' : ''}>내림차순</option>
                         </select>
                    </form>
                    <!-- controller 에 전해줄 form 끝-->
                </div>
                <div class="board-cell board-answer  gray">
                    답변
                </div>
                <div class="board-cell board-like gray">
                    좋아요
                </div>
                <div class="board-cell board-scrap gray">
                    스크랩
                </div>
                <div class="board-cell board-writer gray">
                    작성자
                </div>
                <div class="board-cell board-date gray">
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
