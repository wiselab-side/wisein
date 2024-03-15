<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
    <div class="content-wrap">
        <!--카테고리-->
        <div class="matzip-write">
            <div class="title">TIP</div>
            <div class="content-inner-box">
                <!--신규일경우-->
                <c:if test="${empty TipBoardDTO.category}">
                    <select name="category" id="category">
                        <option value="FRONT" <c:if test="${param.searchType eq 'FRONT'}">selected</c:if>>FRONT</option>
                        <option value="BACK"  <c:if test="${param.searchType eq 'BACK'}">selected</c:if>>BACK</option>
                        <option value="DB"    <c:if test="${param.searchType eq 'DB'}">selected</c:if>>DB</option>
                    </select>
                </c:if>
                <!--수정일경우-->
                <c:if test="${!empty TipBoardDTO.category}">
                    <select name="category" id="category">
                        <option value="FRONT" <c:if test="${TipBoardDTO.category == 'FRONT'}">selected</c:if>>Front</option>
                        <option value="BACK"  <c:if test="${TipBoardDTO.category == 'BACK'}">selected</c:if>>Back</option>
                        <option value="DB"    <c:if test="${TipBoardDTO.category == 'DB'}">selected</c:if>>DB</option>
                    </select>
                </c:if>

                <!--제목-->
                <p><input type="text" size="210" id='subject' name='subject' placeholder="제목을 입력하세요" value="${TipBoardDTO.subject}" required></p>
            </div>


            <div id="contents">
                <div id="editor">
                    ${TipBoardDTO.content}
                </div>
                <div id="viewer"></div>
            </div>

            <!--등록/수정/취소버튼-->
            <div class="button-wrap">
                <c:if test="${empty TipBoardDTO.subject}">
                    <input type="button" value="등록" onclick="reg()">
                </c:if>
                <c:if test="${!empty TipBoardDTO.subject}">
                    <input type="button" value="수정" onclick="udp()">
                </c:if>
                <input type="button" value="취소" onclick="cancel()">
           </div>
        </div>
    </div>