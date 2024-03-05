<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

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

                <!-- 카테고리 -->
                <div class="board-cell board-category purple2">
                     <!-- controller 에 전해줄 form 생성,
                          카테고리 옵션과 제목 정렬 옵션이 동시에 이뤄질 수 있기 때문에 하나의 form 태그로 진행-->
                     <form action="/scrapMemQna" method="get" id="scrapMemQna">
                        <!--카테고리 조회 -->
                         <select id="category" name="category" class="category-select" onchange="this.form.submit()">
                             <option value="" ${empty category ? 'selected' : ''}>카테고리</option>
                             <option value="FRONT" ${category eq 'FRONT' ? 'selected' : ''}>FRONT</option>
                             <option value="BACK" ${category eq 'BACK' ? 'selected' : ''}>BACK</option>
                             <option value="DB" ${category eq 'DB' ? 'selected' : ''}>DB</option>
                         </select>
                </div>
                <div class="board-cell board-title">
                    <!--제목 정렬 -->
                     <select id="subject" name="subject" class="subject-select" onchange="this.form.submit()">
                         <option value="" ${empty subject ? 'selected' : ''}>제목(가나다)</option>
                         <option value="ASC" ${subject eq 'ASC' ? 'selected' : ''}>오름차순</option>
                         <option value="DESC" ${subject eq 'DESC' ? 'selected' : ''}>내림차순</option>
                     </select>

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
                </form>
               <!-- controller 에 전해줄 form 끝-->

                <div class="board-cell board-writer gray">
                    작성자
                </div>
                <div class="board-cell board-date gray">
                    날짜
                </div>
            </div>

        <div class="board-qaList">
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

                    <c:if test="${qa.likeCount == 0}">
                        <div class="board-cell board-like gray">
                            <span class="material-icons">thumb_up</span>${qa.likeCount}
                        </div>
                    </c:if>
                    <!-- 좋아요 수 -->
                    <c:if test="${qa.likeCount != 0}">
                        <div class="board-cell board-like gray">
                            <span class="material-icons">thumb_up</span>
                            <!-- js에서 사용하기 위해 span 태그 생성-->
                            <span class="like-count">${qa.likeCount}</span>
                        </div>
                    </c:if>

                    <c:if test="${qa.scrapCount == 0}">
                        <div class="board-cell board-scrap gray">
                            <span class="material-icons" style="max-width:24px;">bookmarks</span>${qa.scrapCount}
                        </div>
                    </c:if>
                    <!-- 스크랩 수 -->
                    <c:if test="${qa.scrapCount != 0}">
                        <div class="board-cell board-scrap purple2">
                            <span class="material-icons" style="max-width:24px;">bookmarks</span>${qa.scrapCount}
                        </div>
                    </c:if>


                    <!-- 작성자명 -->
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
      </div>
    </section>
    <ul class="pageno-group">
	    <div class="pagination">
	    	${pagination}
    	</div>
    </ul>
</div>
</c:if>

<!-- 자바스크립트로 정렬 구현 (페이지 이동 시 적용은 안됨) -->
<script>
   function changeOrder(click_id) {
       // 클릭된 버튼이 좋아요인지 스크랩인지 확인한 후 orderValue에 저장
       var orderValue = click_id === 'likeOrder' ? 'LIKE_COUNT' : 'SCRAP_COUNT';
       document.getElementById('orderValue').value = orderValue;
       let form = document.getElementById('scrapMemQna');
       form.submit();
   }
</script>




