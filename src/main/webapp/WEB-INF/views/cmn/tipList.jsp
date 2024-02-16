<%@ page language="java" contentType="text/html;charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix="fmt"%>
<!--무게시글-->
<c:if test="${tipList == null}">
    <div class="content-wrap boardList">
        <section class="content-frame boardList">
             <div class="header-section">
                 <div class="title">TIP</div>
                 <div class="content-top-group">
                    <button type="button" onClick="location.href='tipBoard'"></button>
                </div>
             </div>
            <img src ="../resources/image/nonPosting.png" class="noPost">
        </section>
    </div>
</c:if>

<!--유게시글-->
<c:if test="${tipList != null}">
    <div class="content-wrap boardList">
        <section class="content-frame boardList">
            <div class="header-section">
                <div class="title">TIP</div>
                <div class="content-top-group">
                   <c:if test="${side_gubun ne 'Y'}">
                        <button type="button" onClick="location.href='tipBoard'" ></button>
                   </c:if>
                </div>
            </div>
            <div class="board-list">
                <div class="board-line board-header">
                    <div class="board-cell board-no">
                    </div>
                    <div class="board-cell board-category purple2">
                         <!-- controller 에 전해줄 form 생성,
                              카테고리 옵션과 제목 정렬 옵션이 동시에 이뤄질 수 있기 때문에 하나의 form 태그로 진행-->
                         <form action="/tipList" method="get">
                            <!--카테고리 조회 -->
                             <select id="category" name="category" class="category-select" onchange="this.form.submit()">
                                 <option value="" ${empty selectedCategory ? 'selected' : ''}>카테고리</option>
                                 <option value="FRONT" ${selectedCategory eq 'FRONT' ? 'selected' : ''}>FRONT</option>
                                 <option value="BACK" ${selectedCategory eq 'BACK' ? 'selected' : ''}>BACK</option>
                                 <option value="DB" ${selectedCategory eq 'DB' ? 'selected' : ''}>DB</option>
                             </select>
                    </div>
                    <div class="board-cell board-title">
                        <!--제목 정렬 옵션 -->
                             <select id="subject" name="subject" class="subject-select" onchange="this.form.submit()">
                                 <option value="" ${empty selectedSubject ? 'selected' : ''}>제목(가나다)</option>
                                 <option value="ASC" ${selectedSubject eq 'ASC' ? 'selected' : ''}>오름차순</option>
                                 <option value="DESC" ${selectedSubject eq 'DESC' ? 'selected' : ''}>내림차순</option>
                             </select>


                    </div>
                    <div class="board-cell board-like gray">
                        댓글수
                    </div>
                    <div class="board-cell board-like gray" onclick="changeOrder()">
                        좋아요
                    </div>
                    </form>
                     <!-- controller 에 전해줄 form 끝-->
                    <div class="board-cell board-like gray">
                        스크랩
                    </div>
                    <div class="board-cell board-writer gray">
                        작성자
                    </div>
                    <div class="board-cell board-date gray">
                        날짜
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
                        <!--좋아요 수-->
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
                        <div class="board-cell board-writer gray">
                            <p class="writer"><c:out value="${tip.writer}" /><br></p>
                            <ul class="person-function list">
                                <li><a href="https://m196.mailplug.com/member/login?host_domain=wiselab.co.kr" target='_blank'>메일 전송</a></li>
                                <li><a onclick="gatherMemTip('${tip.writer}')">작성팁 모아보기</a></li>
                            </ul>
                        </div>
                        <div class="board-cell board-date gray">
                            <fmt:formatDate value="${tip.regDate}" pattern="yyyy-MM-dd"/>
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

<script>

    function changeOrder() {
        var orderInput = document.getElementById("likeOrder");
        //asc라면 desc로 변경한 후 폼을 제출
        //클릭이벤트로 해아하지않나?
        //getter에서 처리?
        if (orderInput.value === "DESC") {
            orderInput.value = "ASC";
        } else {
            orderInput.value = "DESC";
        }
        document.getElementById("likeOrderForm ").submit(); // 폼 제출
    }

</script>

<form id="likeOrderForm" action="/tipList" method="get">
    <input type="hidden" id="likeOrder" name="likeOrder" value="asc">
</form>
<!--
제목순, 좋아요순, 스크랩순은 and로 이으면 정렬 우선순위가 옳지 않음
url 상 & 로 연결하지 말고 ORDER BY는 하나로만 해야함
FORM을 따로 생성해야할듯

null 값나옴..갑자기..

-->