<%@ page language="java" contentType="text/html;charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<!--무게시글-->
<c:if test="${tipList == null}">
    <div class="content-wrap boardList">
        <section class="content-frame boardList">
             <div class="header-section">
                 <div class="title">TIP</div>
                 <div class="content-top-group">
                    <button type="button" onClick="go_regTipPage('${fn:replace(param.searchType, "'", "\\'")}')"></button>
                </div>
             </div>
            <img src ="../resources/image/nonPosting.png" class="noPost">
        </section>
    </div>
</c:if>

<!--유게시글-->

<c:if test="${tipList != null}">
    <div class="content-wrap boardList">
        <c:choose>
            <c:when test="${param.reply == 'Y'}"></c:when>
            <c:when test="${param.who == 'null' && param.reply == 'N'}">
                <div class="board-type">스크랩 모아보기</div>
            </c:when>
            <c:when test="${param.who != null && param.reply == 'null' || param.who != null && param.gather == 'Y'}">
                <div class="board-type">작성 글 모아보기</div>
            </c:when>
            <c:otherwise></c:otherwise>
        </c:choose>
        <section class="content-frame boardList">
            <div class="header-section">
                <div class="title">TIP</div>
                <div class="content-top-group">
                   <c:if test="${side_gubun ne 'Y'}">
                       <button type="button" onClick="go_regTipPage('${fn:replace(param.searchType, "'", "\\'")}')"></button>
                   </c:if>
                </div>
            </div>
            <div class="board-list">
                <div class="board-line board-header">
                    <div class="board-cell board-no">
                    </div>
                    <div class="board-cell board-category purple2">
                        <p class="category-select">
                            <select id="selectOption" name="category" onchange="selectSearchType()">
                                <option hidden="">카테고리</option>
                                <c:forEach items="${categoryList}" var="categoryList">
                                    <option value="${categoryList.category}" <c:if test="${param.searchType eq categoryList.category}">selected</c:if>><c:out value="${categoryList.category}"/></option>
                                </c:forEach>
                            </select>
                        </p>
                        <!--
                        <ul class="person-function">
                            <li><a href="#">FRONT</a></li>
                            <li><a href="#">BACK</a></li>
                            <li><a href="#">DB</a></li>
                        </ul>
                        <span class="material-icons">
                                expand_more
                        </span>
                        -->
                    </div>
                    <div class="board-cell board-title" onclick="sort(event)" id="subject" value="subject">
                        <value="subject">제목(가나다)
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

                    <c:if test="${param.reply eq 'Y'}">
                        <div class="board-cell board-like gray" onclick="sort(event)" value="commCnt">
                            댓글수
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
                    <c:if test="${param.reply ne 'Y'}"></c:if>

                    <div class="board-cell board-like gray" onclick="sort(event)" value="likeCount">
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
                    <div class="board-cell board-like gray" onclick="sort(event)" value="scrapCount">
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
                    <div class="board-cell board-writer gray" onclick="sort(event)" value="writer" name="writer">
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
                    <div class="board-cell board-date gray" onclick="sort(event)" value="regDate">
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
                    <div class="board-cell board-count gray" onclick="sort(event)" value="count">
                        조회
                        <c:if test="${param.sort eq 'count' && param.order eq 'desc'}">
                            <span class="material-icons" id="order-icons-desc">
                                    expand_more
                            </span>
                        </c:if>
                        <c:if test="${param.sort eq 'count' && param.order eq 'asc'}">
                            <span class="material-icons" id="order-icons">
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

                <c:forEach var="tip" items="${tipList}">
                    <div class="board-line">
                        <div class="board-cell board-no">
                            <c:out value="${tip.num}" />
                        </div>
                        <div class="board-cell board-category purple">
                            <c:out value="${tip.category}" />
                        </div>
                        <div class="board-cell board-title">
                            <a href="/tipDetail?num=${tip.num}"><c:out value="${tip.subject}" /></a>
                        </div>
                        <!--댓글수-->
                        <c:if test="${param.reply eq 'Y'}">
                            <c:if test="${tip.commCnt == 0}">
                                <div class="board-cell board-like gray">
                                    <span class="material-icons">comment</span>${tip.commCnt}
                                </div>
                            </c:if>
                            <c:if test="${tip.commCnt != 0}">
                                <div class="board-cell board-like purple2">
                                    <span class="material-icons">comment</span>${tip.commCnt}
                                </div>
                            </c:if>
                        </c:if>
                        <c:if test="${param.reply ne 'Y'}"></c:if>
                        <!--좋아요-->
                        <c:if test="${tip.likeCount == 0}">
                            <div class="board-cell board-like gray">
                                <span class="material-icons">thumb_up</span>${tip.likeCount}
                            </div>
                        </c:if>
                        <c:if test="${tip.likeCount != 0}">
                            <div class="board-cell board-like purple2">
                                <span class="material-icons">thumb_up</span>${tip.likeCount}
                            </div>
                        </c:if>
                        <!--북마크-->
                        <c:if test="${tip.scrapCount == 0}">
                            <div class="board-cell board-like gray">
                                <span class="material-icons" style="max-width:24px;">bookmarks</span>${tip.scrapCount}
                            </div>
                        </c:if>
                        <c:if test="${tip.scrapCount != 0}">
                            <div class="board-cell board-like purple2">
                                <span class="material-icons" style="max-width:24px;">bookmarks</span>${tip.scrapCount}
                            </div>
                        </c:if>
                        <!--작성자-->
                        <div class="board-cell board-writer gray">
                            <p class="writer"><c:out value="${tip.writer}" /><br></p>
                            <ul class="person-function list">
                                <li><a href="https://m196.mailplug.com/member/login?host_domain=wiselab.co.kr" target='_blank'>메일 전송</a></li>
                                <li><a onclick="gatherMemTip('${tip.writer}')">작성팁 모아보기</a></li>
                            </ul>
                        </div>
                        <!--날짜-->
                        <div class="board-cell board-date gray">
                            <fmt:formatDate value="${tip.regDate}" pattern="yyyy-MM-dd"/>
                        </div>
                        <!--조회수-->
                        <div class="board-cell board-count gray">
                            ${tip.count}
                        </div>
                    </div>
                </c:forEach>
            </div>
        </section>

        <!--페이징-->
        <ul class="pageno-group">
            <div class="pagination">
                ${pagination}
            </div>
        </ul>
    </div>
</c:if>

<script src="resources/js/tipBoard.js"></script>

