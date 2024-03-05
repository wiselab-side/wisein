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


                    <form action="/scrapMemTip" method="get" id="scrapMemTip">
                        <!--카테고리 조회 -->
                         <select id="category" name="category" class="category-select" onchange="this.form.submit()">
                             <option value="" ${empty category ? 'selected' : ''}>카테고리</option>
                             <option value="FRONT" ${category eq 'FRONT' ? 'selected' : ''}>FRONT</option>
                             <option value="BACK" ${category eq 'BACK' ? 'selected' : ''}>BACK</option>
                             <option value="DB" ${category eq 'DB' ? 'selected' : ''}>DB</option>
                         </select>
                        </div>
                        <div class="board-cell board-title">
                            <!--제목 정렬 옵션 -->
                                 <select id="subject" name="subject" class="subject-select" onchange="this.form.submit()">
                                     <option value="" ${empty subject ? 'selected' : ''}>제목(가나다)</option>
                                     <option value="ASC" ${subject eq 'ASC' ? 'selected' : ''}>오름차순</option>
                                     <option value="DESC" ${subject eq 'DESC' ? 'selected' : ''}>내림차순</option>
                                 </select>
                        </div>

                            <div class="board-cell board-like gray">
                                댓글수
                            </div>

                            <div class="board-cell board-like gray" id="likeOrder" value="${sortValue}" onclick="changeOrder('likeOrder')">
                                좋아요
                            </div>

                            <div class="board-cell board-like gray" id="scrapOrder" value="${sortValue}" onclick="changeOrder('scrapOrder')">
                             스크랩
                            </div>

                           <!--
                            orderValue : 클릭이 된 인자 즉, likeOrder / scrapOrder 둘 중 하나를 넘겨준다
                            sortValue  : 정렬기준 ASC 혹은 DESC를 넘겨준다
                            -->
                           <input type="hidden" name= "orderValue" id="orderValue" value="${orderValue}">
                           <input type="hidden" name= "sortValue" id="sortValue" value="${sortValue}">

                            <div class="board-cell board-writer gray">
                                작성자
                            </div>
                            <div class="board-cell board-date gray">
                                날짜
                            </div>
                    </form>
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


<!--
 [로직 정리]

 좋아요, 스크랩 순으로만 정렬 기능을 한다
 단, 좋아요 / 스크랩 따로 작동하도록 한다
 좋아요순&스크랩순이면 안됨

 클릭을 하면 ASC, DESC가 번갈아 출력되는 작동 방식은 동일하다
 대신 좋아요와 스크랩을 따로 넘겨줘야 하는 것이다

 클릭이 된 값을 넘겨주면 되는 것
 MAPPER 에서 이를 CHOOSE~WHEN 절로 처리하면 된다
-->


<script>
function changeOrder(click_id) {

    // 클릭된 버튼이 좋아요인지 스크랩인지 확인하여 orderValue에 저장
    var orderValue = click_id === 'likeOrder' ? 'LIKE_COUNT' : 'SCRAP_COUNT';
    document.getElementById('orderValue').value = orderValue;

    let form = document.getElementById('scrapMemTip');
    form.submit();
}


 <!-- controller 에 전해줄 form 끝-->

</script>



<!--
클릭을 할때마다(폼을 제출할때마다) 새로고침이 되어 INPUT에서 설정한 초기 VALUE값으로 초기화가 된다
이를 위해 submit 이벤트 동작을 막자 정상적으로 DESC > ASC > DESC >... 로 변경이 되었다
폼을 제출하면 정상처리되지 않기 때문에 AJAX 통신으로 폼을 제출했다.
하지만 페이지 util을 사용하기 위해서는 url이 category=&subject=&likeOrder=... 이와 같이 사용되어야하기 때문에
폼 제출이 필수적인 것 같았다. (혹은 ajax통신으로 url 이동을 하면 되지만 page처리가 어려울 것..)
option의 경우 선택된 값을 전송하면 되지만, 클릭의 경우 불가능하다

-->