<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<div class="content-wrap">
    <section class="content-frame">
        <div class="title bline">TIP 스크랩 모아보기</div>

        <jsp:include page="tipList.jsp">
            <jsp:param value="TipListDTO" name="tipList"/>
            <jsp:param value="categoryList" name="categoryList"/>
        </jsp:include>

    </section>
</div>
