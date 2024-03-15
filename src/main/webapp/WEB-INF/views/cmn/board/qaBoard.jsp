<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<head>
    <script src="/resources/js/qaBoard.js"></script>
</head>

<c:set var="category" value="${qaListDTO.category}" />
<c:set var="writer" value="${member.id}" />
    <form autocomplete="off" id="qaBoardForm">
       <div class="content-wrap">
            <div class="matzip-write">
                <div class="content-inner-box">
                     <div class="select-wrap">
                         <select id="category" name="category" >
                             <option value="FRONT" <c:if test="${param.category eq 'FRONT'}">selected</c:if>>FRONT</option>
                             <option value="BACK"  <c:if test="${param.category eq 'BACK'}">selected</c:if>>BACK</option>
                             <option value="DB"    <c:if test="${param.category eq 'DB'}">selected</c:if>>DB</option>
                         </select>
                    </div>
                    <p>
                        <input type="text" size="210" id="subject" name="subject" placeholder="제목을 입력하세요" value="${qaListDTO.subject}">
                    </p>
                </div>

                <div id="contents">
                    <div id="editor">${qaListDTO.content}</div>
                    <div id="viewer"></div>

                    <input type="hidden" id="num" name="num" value="${qaListDTO.num}">
                    <input type="hidden" id="parentNum" name="parentNum" value="${qaListDTO.parentNum}">
                    <input type="hidden" id="content" name="content">
                    <input type="hidden" id="updGubun" name="updGubun" value="N">
                </div>

                <div class="button-wrap">
                    <c:if test="${empty qaListDTO.subject}">
                        <input type="button" value="등록" onclick="regQa('${fn:replace(writer, "'", "\\'") }','${fn:replace(category, "'", "\\'") }')">
                    </c:if>
                    <c:if test="${!empty qaListDTO.subject}">
                        <input type="button" value="수정" onclick="update()">
                    </c:if>
                    <input type="button" value="취소" onclick="cancel()">
               </div>
            </div>
       </div>
</form>