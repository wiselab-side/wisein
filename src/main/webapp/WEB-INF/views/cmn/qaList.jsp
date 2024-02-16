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
                <!-- 좋아요 정렬 버튼-->
                <button id="sortButton">
                    ▼
                </button>

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
    var sortOrder = 'desc'; // 정렬 방식 기본값: 내림차순
    // 버튼의 클릭 이벤트 처리
    document.getElementById("sortButton").addEventListener("click", function() {
        var boardList = document.querySelector('.board-qaList');
        var qaItems = boardList.querySelectorAll('.board-line');

        // 정렬 함수 정의
        function sortFunction(a, b) {
            var likeCountA = a.querySelector('.board-like .like-count');
            var likeCountB = b.querySelector('.board-like .like-count');

            // 좋아요 수를 가져오기 전에 null 체크
            likeCountA = likeCountA ? parseInt(likeCountA.textContent.trim() || '0') : 0;
            likeCountB = likeCountB ? parseInt(likeCountB.textContent.trim() || '0') : 0;

            // 정렬 방식에 따라 순서 변경
            if (sortOrder === 'asc') {
                return likeCountA - likeCountB;
            } else {
                return likeCountB - likeCountA;
            }
        }

        var sortedList = Array.from(qaItems).sort(sortFunction);

        // 클릭할 때마다 정렬 방식 변경
        sortOrder = (sortOrder === 'asc') ? 'desc' : 'asc';

        // 기존 목록 초기화 후 정렬 목록 다시 추가
        boardList.innerHTML = '';
        sortedList.forEach(function(item) {
            boardList.appendChild(item);
        });
    });
</script>